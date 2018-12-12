package com.chen.fy.controller.business.assist;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.CommonKit;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class AssistService {
	public final static AssistService me = new AssistService();
	private static final Logger logger = LogManager.getLogger(AssistService.class);
	String oTable = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,\n "
			+ "commodity_spec,map_no,quantity,unit_tmp,technology,\n "
			+ "machining_require,untaxed_cost,order_date,delivery_date, \n" + "execu_status,customer_no"
			+ ",s.name supplier_name ,p.create_time pay_create_time";

	public Page<FyBusinessAssist> findPage(int currentPage, int pageSize, String condition, String key) {
		Page<FyBusinessAssist> modelPage = null;


		String select = "select a.* ,s.name supplier," + oTable + " , f.originalFileName filename,f.id fileId";
		String from = " from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id "
				+ " left join fy_base_supplier s on s.id = a.assist_supplier_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id"
				+ " LEFT JOIN fy_business_pay p ON p.is_purchase =0 AND p.parent_id = a.id ";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessAssist.dao.paginate(currentPage, pageSize, select,
					from + " order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();

			if ("name".equals(condition)) {
				sb.append(" and s.name like ").append("'%").append(key).append("%' ");
			} else {
				sb.append(" and o.work_order_no like ").append("'%").append(key).append("%' ");
			}
			modelPage = FyBusinessAssist.dao.paginate(currentPage, pageSize, select,
					from + sb.toString() + " order by id desc");
		}

		return modelPage;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Record findModel(Integer id) {
		return null;

	}

	/**
	 * 批量生成应付单
	 * 新建应付单，根据外协单据数据、新建应付单，
	 * @return
	 */
	public Ret createPay(String[] assistId) throws Exception {
		Ret ret = null;
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(assistId, idsb);

		List<Record> idList = Db
				.find(
				" select  id from fy_business_pay  where  is_purchase = 0 AND  parent_id in " + idsb.toString());
		if (idList.size() > 0) {
			return Ret.fail().set("msg", " 有单据 已存在应付单 ");
		}

		List<FyBusinessAssist> modelList = FyBusinessAssist.dao.find(
				"select a.*,o.quantity quantity from fy_business_assist a LEFT JOIN  fy_business_order o on o.id = a.order_id where a.id in "
						+ idsb.toString());
		List<FyBusinessPay> createModel = new ArrayList<>();
		Date date = new Date();
		StringBuilder sb = new StringBuilder();
		for (FyBusinessAssist e : modelList) {
			FyBusinessPay item = new FyBusinessPay();
			item.setParentId(e.getId());
			item.setIsPurchase(false);// 不是采购单;
			item.setPurchaseDate(e.getAssistDate());// 采购时间
			item.setCheckResult(e.getCheckResult());
			item.setOrderNo(e.getAssistNo());

			BigDecimal quantity = e.getBigDecimal("quantity");

			item.setPurchaseQuantity(quantity);// 外协数量
			item.setPayQuantity(quantity);// 应付数量
			BigDecimal taxRate = e.getTaxRate();
			

			item.setPurchaseCost(e.getAssistCost());// 单价
			BigDecimal totalAmout = e.getAssistCost().multiply(quantity);
			item.setPurchaseAmount(totalAmout);// 总额
			BigDecimal tax = totalAmout.multiply(taxRate);
			BigDecimal shulpay = tax.add(totalAmout);
			item.setShouldPay(shulpay);// 应付金额

			item.setOrderId(e.getOrderId());// 订单id
			item.setPurchaseNo(e.getAssistNo());// 采购编号
			item.setPurchaseName(e.getAssistProcess());// 加工工序，采购名称
			item.setInFrom("外协");

			item.setSupplierId(e.getAssistSupplierId());// 采购厂商id
			Supplier supplier = Supplier.dao.findById(e.getAssistSupplierId());
			if (supplier == null) {
				sb.append("," + e.getAssistProcess() + " 没有供应商 ");
				continue;
			}
			Integer settlement_cycle = supplier.getSettlementCycle();// 结算周期
			if (settlement_cycle == null) {
				sb.append("," + e.getAssistProcess() + " 厂商没有结算周期  ");
				continue;
			}

			item.setHangDate(date);
			if (settlement_cycle == 1) {// 月结30天
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				if (day > 25) {
					calendar.add(Calendar.MONTH, 2);
				} else {
					calendar.add(Calendar.MONTH, 1);
				}
				item.setPayDate(calendar.getTime());
			} else if (settlement_cycle == 2) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				if (day > 25) {
					calendar.add(Calendar.MONTH, 3);
				} else {
					calendar.add(Calendar.MONTH, 2);
				}
				item.setPayDate(calendar.getTime());
			} else if (settlement_cycle == 3) {
				item.setPayDate(item.getHangDate());
			}

			item.setCreateTime(date);// 生成时间
			item.setCheckTime(e.getBacktime());
			// Date payDate = DateUtils.addMonths(date, 2);
			// item.setPayDate(payDate);
			item.setInWarehouseTime(e.getBacktime());// 回厂时间，为入库时间
			createModel.add(item);//
		}
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int[] re = Db.batchSave(createModel, createModel.size());
				List<Integer> createId = new ArrayList<Integer>();
				for (FyBusinessPay e : createModel) {
					createId.add(e.getId());
				}
				// StringBuilder idsb = new StringBuilder();
				// SqlKit.joinIds(assistId, idsb);
				// int updateNum = Db.update(sql + idsb);
				/**
				 * 更新数量与新建数量不一致，撤回
				 */
				return createModel.size() == CommonKit.totalInt(re);
			}
		});

		if (re) {
			/*
			 * 更新是否已生成应付单
			 */
			String sql = "update fy_business_assist a LEFT JOIN fy_business_pay p\r\n"
					+ " on a.id = p.parent_id and p.is_purchase = 0 \r\n" + " set a.is_create_pay = 1 \r\n"
					+ " where   is_purchase = 0 AND p.parent_id = a.id\r\n";
			Db.update(sql);

			ret = Ret.ok().set("msg", "新建应付单完成 " + createModel.size() + "条，");
		} else {
			ret = Ret.ok().set("msg", "新建应付单失败");
		}
		return ret;

	}

	public File download(String[] ids) throws Exception {
		String select = "select a.* ,s.name supplier," + oTable + " , f.originalFileName filename,f.id fileId";
		String from = " from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id "
				+ " left join fy_base_supplier s on s.id = a.assist_supplier_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id"
				+ " LEFT JOIN fy_business_pay p ON p.is_purchase =0 AND p.parent_id = a.id ";
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);
		String sql = select + from + " WHERE a.id in " + idsb.toString() + " order by a.id ";
		logger.debug(" 下载 外协单 sql ==> " + sql);
		List<Record> list = Db.find(sql);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"外协加工单" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/assist/assist.xlsx").getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		int row = 1;
		for (Record item : list) {
			String cate_tmp = item.getStr("cate_tmp");// 类别
			excel.setCellVal(row, 0, cate_tmp);

			String plan_tmp = item.getStr("plan_tmp");// 计划员
			excel.setCellVal(row, 1, plan_tmp);

			String execu_status = item.getStr("execu_status"); // 执行状态
			excel.setCellVal(row, 2, execu_status);

			String customer_no = item.getStr("customer_no");// 客户编码
			excel.setCellVal(row, 3, customer_no);

			String work_order_no = item.getStr("work_order_no");// 订单单号
			excel.setCellVal(row, 4, work_order_no);

			String delivery_no = item.getStr("delivery_no");// 总图号
			excel.setCellVal(row, 5, delivery_no);

			String map_no = item.getStr("map_no");// 总图号
			excel.setCellVal(row, 6, map_no);

			String commodity_name = item.getStr("commodity_name");// 总图号
			excel.setCellVal(row, 7, commodity_name);

			String total_map_no = item.getStr("total_map_no");// 总图号
			excel.setCellVal(row, 8, total_map_no);

			String quantity = item.getStr("quantity");// 数量
			excel.setCellVal(row, 9, quantity);

			String unit_tmp = item.getStr("unit_tmp");// 总图号
			excel.setCellVal(row, 10, unit_tmp);

			String model_no = item.getStr("model_no");// 型号
			excel.setCellVal(row, 11, model_no);

			String commodity_spec = item.getStr("commodity_spec");// 规格
			excel.setCellVal(row, 12, commodity_spec);

			String technology = item.getStr("technology");// 技术条件
			excel.setCellVal(row, 13, technology);

			String machining_require = item.getStr("machining_require");// 质量等级
			excel.setCellVal(row, 14, machining_require);

			String filename = item.getStr("filename");// 图纸
			excel.setCellVal(row, 15, filename);

			String assist_no = item.getStr("assist_no");//
			excel.setCellVal(row, 16, assist_no);

			Double assist_cost = item.getDouble("assist_cost");// 总
			excel.setCellVal(row, 17, assist_cost);

			Double assist_account = item.getDouble("assist_account");// 总图号
			excel.setCellVal(row, 18, assist_account);

			Double tax_rate = item.getDouble("tax_rate");//
			excel.setCellVal(row, 19, tax_rate);

			String tax_amount = item.getStr("tax_amount");// 税额
			excel.setCellVal(row, 20, tax_amount);

			Double tatol_amount = item.getDouble("tatol_amount");// 含税金额
			excel.setCellVal(row, 21, tatol_amount);

			String supplier_name = item.getStr("supplier_name");// 厂商
			excel.setCellVal(row, 22, supplier_name);

			String assist_process = item.getStr("assist_process");// 外协工序
			excel.setCellVal(row, 23, assist_process);

			Double out_time = item.getDouble("run_time");// 回厂时间
			excel.setCellVal(row, 24, out_time);

			Date backtime = item.getDate("backtime");// 出货日期
			excel.setCellVal(row, 25, backtime, "yyyy-MM-dd");

			String check_result = item.getStr("check_result");// 检测结果
			excel.setCellVal(row, 26, check_result);

			Boolean is_create_pay = item.getBoolean("is_create_pay");//
			if (is_create_pay == null || is_create_pay) {
				excel.setCellVal(row, 27, "未生成");
			} else {
				excel.setCellVal(row, 27, "已生成");
			}

			Date hang_date = item.getDate("hang_date");// 出货日期
			excel.setCellVal(row, 28, hang_date, "yyyy-MM");

			Date pay_date = item.getDate("pay_date");// 应付区间
			excel.setCellVal(row, 29, pay_date, "yyyy-MM");

			Integer pay_quantity = item.getInt("pay_quantity");// 数量
			excel.setCellVal(row, 30, pay_quantity);

			Double should_pay = item.getDouble("should_pay");// 应付金额
			excel.setCellVal(row, 31, should_pay);
			row++;
		}

		excel.save2File(targetfile);

		return targetfile;

	}
}
