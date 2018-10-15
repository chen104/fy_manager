package com.chen.fy.controller.business.finance.pay;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PaySerivce {
	private static final Logger logger = LogManager.getLogger(PaySerivce.class);
	public static final PaySerivce me = new PaySerivce();

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String key, String condition, String start_date,
			String end_date) throws Exception {
		Page<Record> modelPage = null;
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status";
		String select = "select p.*, s.name supplier_name ," + sql;
		String from = "from  fy_business_pay p left join fy_business_order o on o.id= p.order_id  left join fy_base_supplier s on  p.supplier_id = s.id ";
		String desc = " order by id desc";
		StringBuilder where = new StringBuilder();
		where.append(" where  is_setlled = 0 ");
		if (StringUtils.isNotEmpty(key)) {

			if ("hang_date".equals(condition)) {
				Date date = null;
				try {
					date = DateUtils.parseDate(key, "yyyy-MM");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DATE, -1);
					String start = DateFormatUtils.format(calendar, "yyyy-MM-dd");
					calendar.add(Calendar.DATE, 1);
					calendar.add(Calendar.MONTH, 1);
					String end = DateFormatUtils.format(calendar, "yyyy-MM-dd");
					where.append("AND p.hang_date > '").append(start).append("' and p.hang_date < '").append(end)
							.append("' ");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if ("pay_date".equals(condition)) {

				Date date = null;
				try {
					date = DateUtils.parseDate(key, "yyyy-MM");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DATE, -1);
					String start = DateFormatUtils.format(calendar, "yyyy-MM-dd");
					calendar.add(Calendar.DATE, 1);
					calendar.add(Calendar.MONTH, 1);
					String end = DateFormatUtils.format(calendar, "yyyy-MM-dd");
					where.append("AND p.pay_date > '").append(start).append("' and p.pay_date < '").append(end)
							.append("' ");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				where.append("AND s.name").append(" like '%").append(key).append("%' ");
			}

		}
		modelPage = Db.paginate(currentPage, pageSize, select, from + where.toString() + desc);
		return modelPage;

	}

	/**
	 * 下载应付单
	 * @param ids
	 * @return
	 * @throws Exception 
	 */
	public File downloadPay(String[] ids) throws Exception {
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status";
		String select = "select p.*, s.name supplier_name ," + sql;
		String from = " from  fy_business_pay p left join fy_business_order o on o.id= p.order_id  left join fy_base_supplier s on  p.supplier_id = s.id ";
		String where = " where  p.id in ";
		String desc = " order by id desc";
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);

		List<Record> list = Db.find(select + from + where + idsb.toString() + desc);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"应付单" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/pay/pay_templet.xlsx").getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		int row = 1;
		for (Record item : list) {
			String map_tmp = item.getStr("cate_tmp");// 类别
			excel.setCellVal(row, 0, map_tmp);

			String plan_tmp = item.getStr("plan_tmp");// 计划员
			excel.setCellVal(row, 1, plan_tmp);

			String work_order_no = item.getStr("work_order_no");// 工作订单
			excel.setCellVal(row, 2, work_order_no);

			String delivery_no = item.getStr("delivery_no");// 送货单号
			excel.setCellVal(row, 3, delivery_no);

			String commodity_name = item.getStr("commodity_name");// 名称
			excel.setCellVal(row, 4, commodity_name);

			String map_no = item.getStr("map_no");// 图号
			excel.setCellVal(row, 5, map_no);

			String total_map_no = item.getStr("total_map_no");// 总图号
			excel.setCellVal(row, 6, total_map_no);

			String technology = item.getStr("technology");// 技术条件
			excel.setCellVal(row, 7, technology);

			String machining_require = item.getStr("machining_require");// 质量等级
			excel.setCellVal(row, 8, machining_require);

			Double quantity = item.getDouble("quantity");// 数量
			excel.setCellVal(row, 9, quantity);

			String unit_tmp = item.getStr("unit_tmp");// 单位
			excel.setCellVal(row, 10, unit_tmp);

			Date purchase_date = item.getDate("purchase_date");// 采购日期
			excel.setCellVal(row, 11, purchase_date, "yyyy-MM-dd");

			String check_result = item.getStr("check_result");// 检测结果
			excel.setCellVal(row, 12, check_result);

			String supplier_name = item.getStr("supplier_name");// 供应商
			excel.setCellVal(row, 13, supplier_name);

			String purchase_no = item.getStr("purchase_no");// 采购编号
			excel.setCellVal(row, 14, purchase_no);

			Integer purchase_quantity = item.getInt("purchase_quantity");// 采购数量
			excel.setCellVal(row, 15, purchase_quantity);

			Double purchase_cost = item.getDouble("purchase_cost");// 单价
			excel.setCellVal(row, 16, purchase_cost);

			Double purchase_amount = item.getDouble("purchase_amount");// 采购总价
			excel.setCellVal(row, 17, purchase_amount);

			Date in_warehouse_time = item.getDate("in_warehouse_time");// 入库时间
			excel.setCellVal(row, 18, in_warehouse_time, "yyyy-MM-dd");

			String in_from = item.getStr("in_from");// 入库来源
			excel.setCellVal(row, 19, in_from);

			Double pay_quantity = item.getDouble("pay_quantity");// 应付数量
			excel.setCellVal(row, 20, pay_quantity);

			Date check_time = item.getDate("check_time");// 入库时间
			excel.setCellVal(row, 21, check_time, "yyyy-MM-dd");

			Double should_pay = item.getDouble("should_pay");// 应付金额
			excel.setCellVal(row, 22, should_pay);

			String remark = item.getStr("remark");// 采购编号
			excel.setCellVal(row, 23, remark);

			Date hang_date = item.getDate("hang_date");// 挂账日期
			excel.setCellVal(row, 24, hang_date, "yyyy年MM月");

			Date pay_date = item.getDate("pay_date");// 应付期间
			excel.setCellVal(row, 25, pay_date, "yyyy年MM月");

			row++;
		}

		excel.save2File(targetfile);
		Db.update(" update  fy_business_out_warehouse set is_download = 1 where id in " + idsb.toString());
		return targetfile;
	}

	/**
	 * 对于外协加工单来说，撤回修改 ，外协单状态，
	 * 入库单检测生成的，修改
	 * @param payIds
	 * @return
	 */
	public Ret rollback(String[] payIds) throws Exception {

		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(payIds, idsb);
		// 查找外协单的ID
		String select = "select id ,parent_id from fy_business_pay where is_purchase = 0 and id in ";
		List<Record> list = Db.find(select + idsb.toString());
		List<Integer> assistPayId = new ArrayList<Integer>();
		for (Record e : list) {
			assistPayId.add(e.getInt("parent_id"));
		}
		// 查找采购单的id
		select = "select id ,parent_id,order_id from fy_business_pay where is_purchase = 1 and id in ";
		list = Db.find(select + idsb);
		List<Integer> inhouseId = new ArrayList<Integer>();
		HashSet<Integer> order_ids = new HashSet<Integer>();
		for (Record e : list) {
			inhouseId.add(e.getInt("parent_id"));
			order_ids.add(e.getInt("order_id"));
		}
		if (inhouseId.size() > 0) {
			String check = "select count(1) num from  fy_business_order o LEFT JOIN  \r\n"
					+ "(	select order_id,sum(pass_quantity) pass_quantity  from fy_business_in_warehouse\r\n"
					+ "	where id in (12,1)\r\n" + " GROUP BY order_id\r\n" + ") iw on o.id = iw.order_id\r\n" + " \r\n"
					+ "where o.id in (1)  \r\n" + "";
		}
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int udrow = 0;
				// 更新外协单
				if (assistPayId.size() > 0) {
					StringBuilder assits = new StringBuilder();
					SqlKit.joinIds(assistPayId, assits);
					int u1 = Db.update(
							" update fy_business_assist set  is_create_pay = 0  where id in  " + assits.toString());
					udrow = u1 + udrow;
				}
				// 更新入库检查单，修改为待检测
				if (inhouseId.size() > 0) {
					// 更新库存，
					StringBuilder sb = new StringBuilder();
					SqlKit.joinIds(inhouseId, sb);

					StringBuilder orderSB = new StringBuilder();
					SqlKit.joinIds(order_ids, orderSB);
					String update = "update  fy_business_order o LEFT JOIN  \r\n"
							+ "(	select order_id,sum(pass_quantity) pass_quantity  from fy_business_in_warehouse\r\n"
							+ "	where id in " + sb.toString() + "\r\n" + " GROUP BY order_id\r\n"
							+ ") iw on o.id = iw.order_id\r\n"
							+ " set o.storage_quantity = o.storage_quantity - pass_quantity,o.has_in_quantity = o.has_in_quantity - pass_quantity\r\n"
							+ " where o.id in " + orderSB.toString();
					Db.update(update);
					update = "update fy_business_in_warehouse set check_time = null,check_result = null,pass_quantity = 0,unpass_quantity = 0 \r\n"
							+ " where id in ";

					int u2 = Db.update(update + sb.toString());// 设置为检测单
					udrow = u2 + udrow;

				}
				String delete = " delete from fy_business_pay  where id in " + idsb.toString();
				int delete_num = Db.delete(delete);
				return udrow == delete_num;
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "撤回成功");
		} else {
			ret = Ret.ok().set("msg", "撤回失败");
		}
		return ret;

	}

	/**
	 * 更新应付明细，生成应付结算单
	 * 修改状态位 is_setlled = 1
	 * @param payId
	 * @return
	 */
	public Ret toBill(String[] payId) {
		Ret ret = null;
		StringBuilder paySb = new StringBuilder();
		SqlKit.joinIds(payId, paySb);
		String update = " UPDATE  fy_business_pay SET is_setlled = 1 where id in ";
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int re = Db.update(update + paySb.toString());
				return re == payId.length;
			}
		});

		if (re) {
			ret = Ret.ok().set("msg", "更新完成");
		} else {
			ret = Ret.fail().set("msg", "更新失败");
		}
		return ret;

	}

	/**
	 * 根据pay id查找单据 
	 * @param payid
	 * @return
	 */
	public Record findPayModel(String payid) {
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status";
		String select = " select p.*, s.name supplier_name ," + sql;
		String from = " from  fy_business_pay p left join fy_business_order o on o.id= p.order_id  left join fy_base_supplier s on  p.supplier_id = s.id ";
		String where = " WHERE  p.id = " + payid;
		Record model = Db.findFirst(select + from + where);
		return model;
	}
}
