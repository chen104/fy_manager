package com.chen.fy.controller.business.getpay;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyUploadGetpay;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class UploadGetpayService {

	public static final UploadGetpayService me = new UploadGetpayService();
	private static final Logger logger = LogManager.getLogger(UploadGetpayService.class);
	public Page<FyUploadGetpay> findpage(Integer currentPage, Integer pageSize, String condition, String key)
			throws Exception {
		logger.debug(" 应收查询  = > currentpage = " + currentPage + "  pageSize = " + pageSize + "  condition = "
				+ condition + "  keyword = " + key);
		Page<FyUploadGetpay> personPage = null;
		StringBuilder where = new StringBuilder();
		where.append(" where is_setlled = 0 ");
		if (!StringUtils.isEmpty(key)) {
			if ("delivery_no".equals(condition)) {
				where.append(" AND ").append(condition).append(" like '%").append(key).append("%' ");
			} else if ("hang_date".equals(condition)) {
				Calendar calender = Calendar.getInstance();
				calender.setTimeInMillis(System.currentTimeMillis());
				try {
					Date  date= DateUtils.parseDate(key, "yyyy-MM");

					where.append(" AND '").append(key).append("' = DATE_FORMAT(hang_date,'%Y-%m') ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		personPage = FyUploadGetpay.dao.paginate(currentPage, pageSize, "select *",
				"from fy_upload_getpay " + where.toString() + "order by id desc");
		return personPage;

	}

	public Ret uploadFile(File file) throws Exception {
		int total = 0;
		StringBuilder sb = new StringBuilder();
		int[] re = { 0, 0 };
		try {
		PIOExcelUtil excel = new PIOExcelUtil(file, 0);
		// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
		// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
		List<Record> list = new ArrayList<Record>();
		HashSet<String> deliveryNoSet = new HashSet<String>();
		int rows = excel.getRowNum() + 1;

		for (int i = 1; i < rows; i++) {
			FyUploadGetpay item = new FyUploadGetpay();

			// 挂账日期
			Date hangDate = excel.getDateValue(i, 0);
			item.setHangDate(hangDate);
			// 发票号
			String invoiceNo = excel.getCellVal(i, 1);// 名称
			item.setInvoiceNo(invoiceNo);

			String materials = excel.getCellVal(i, 2);//
			item.setMaterials(materials); // 物料 物料号


			String name = excel.getCellVal(i, 3);// 名称
			item.setCommodityName(name);

			String Brand_no = excel.getCellVal(i, 4);// 牌号
			item.setBrandNo(Brand_no);// 牌号

			String spec = excel.getCellVal(i, 5);// 规格
			item.setSpec(spec);

			String project_no = excel.getCellVal(i, 6);//
			item.setProjectNo(project_no);// 项目编号

			String unit = excel.getCellVal(i, 7);// 单位
			item.setUnit(unit);

			String quantity = excel.getCellVal(i, 8);// 数量
			item.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : null);

			if (!NumberUtils.isNumber(quantity)) {
				logger.warn(" 上传的 的数量不是数字 ");
				sb.append(" 存在数量不是数字 ,");
				continue;

			}


			String cost = excel.getCellVal(i, 9);// 单价
			item.setCost(NumberUtils.isNumber(cost) ? new BigDecimal(quantity) : null);


			if (StringUtils.isEmpty(cost)) {
				logger.warn(" 上传的 单价不是数字 ");
				sb.append(" 存在单价不是数字 ,");
				continue;
			}

			String hangquantity = excel.getCellVal(i, 10);// 已挂帐数量
			item.setHangQuantity(NumberUtils.isNumber(hangquantity) ? new BigDecimal(quantity) : null);

			String invoice_stat = excel.getCellVal(i, 11);// 发票挂账状态
			item.setInvoiceStat(invoice_stat);

			String hangAmount = excel.getCellVal(i, 12);// 挂账金额
			item.setHangAmount(NumberUtils.isNumber(hangAmount) ? new BigDecimal(quantity) : null);

			String perchase_person = excel.getCellVal(i, 13);// 采购人
			item.setPerchasePerson(perchase_person);

			String deliveryNo = excel.getCellVal(i, 14);// 送货单号
			item.setDeliveryNo(deliveryNo);
			deliveryNoSet.add(deliveryNo);

			String deliveryIndex = excel.getCellVal(i, 15);// 送货单序号
			item.setDeliveryIndex(deliveryIndex);

			String contract = excel.getCellVal(i, 16);// 送货单序号
			item.setContract(contract);

			list.add(new Record().setColumns(item));

		}
			re = Db.batchSave("fy_upload_getpay", list, 20);

		/**
		 * 
		 */

		// List<String> delino = splitdeliveryNoSet(deliveryNoSet);
		// for (String e : delino) {
		// String sql = String.format(Db.getSql("upgetpay.updateorder"), e);
		// System.out.println(sql);
		// Db.update(sql);
		// }
		//
		// String updateHangStatus = Db.getSql("upgetpay.updateHangStatus");
		// Db.update(updateHangStatus);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		for (int i = 0; i < re.length; i++) {
			total = total + re[i];
		}

		file.delete();
		if (sb.length() > 0) {
			return Ret.ok("msg", "添加了" + total + "记录 ," + sb.toString());
		} else {
			// 更新订单反写状态
			return Ret.ok("msg", "添加了" + total + "记录");
		}

	}

	/**
	 * 避免送货单号过长
	 * @param deliveryNoSet
	 * @return
	 */
	protected List<String> splitdeliveryNoSet(HashSet<String> deliveryNoSet) {
		List<String> deliveryNos = new ArrayList<String>();
		int row = 0;
		StringBuilder sb = null;

		Iterator<String> iterator = deliveryNoSet.iterator();
		while (iterator.hasNext()) {
			if (row % 10 == 0) {
				if (sb != null) {
					if (sb.length() > 2) {
						sb.deleteCharAt(sb.length() - 1);
					}
					sb.append(")");
					deliveryNos.add(sb.toString());
					sb = new StringBuilder();
					sb.append("(");
				} else {
					sb = new StringBuilder();
					sb.append("(");

				}

			}
			sb.append("'").append(iterator.next()).append("',");
			row++;
		}
		if (sb.length() > 2) {
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
		}
		deliveryNos.add(sb.toString());
		return deliveryNos;
	}

	public Ret toBill(String ids[]) {
		Ret ret = null;

		StringBuilder paySb = new StringBuilder();
		SqlKit.joinIds(ids, paySb);
		String update = " UPDATE  fy_upload_getpay SET is_setlled = 1 where id in ";
		// 查找送货单号
		List<Record> list = Db
				.find("select DISTINCT delivery_no from fy_upload_getpay where id in " + paySb.toString());

		ArrayList<String> deno = new ArrayList<String>();
		for (Record e : list) {
			deno.add(e.getStr("delivery_no"));
		}
		StringBuilder denoSb = new StringBuilder();
		SqlKit.joinIds(deno, denoSb);

		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int re = Db.update(update + paySb.toString());
				// 更新挂账信息
				String update = Db.getSql("order.updateGetpayInfo");
				Db.update(String.format(update, denoSb.toString(), denoSb.toString()));
				return re == ids.length;
			}
		});

		if (re) {
			ret = Ret.ok().set("msg", "更新完成");
		} else {
			ret = Ret.fail().set("msg", "更新失败");
		}
		return ret;
	}

}
