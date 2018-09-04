package com.chen.fy.controller.business.service;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class OrderService {
	private static final Logger logger = LogManager.getLogger(OrderService.class);
	public static final OrderService me = new OrderService();

	/**
	 * 查找下载
	 * @param dateMonth
	 * @return
	 */
	public List<Record> findDownloadList(Date dateMonth) {
		try {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateMonth);
			calendar.add(Calendar.DATE, -1);
			String start = DateFormatUtils.format(calendar, "yyyy-MM-dd");
			calendar.add(Calendar.DATE, 1);
			calendar.add(Calendar.MONTH, 1);
			String end = DateFormatUtils.format(calendar, "yyyy-MM-dd");
			String where = " where  o.order_date >'" + start + "' and o.order_date <'" + end + "' ";
			String sql = "select id,order_date,o.work_order_no,commodity_name,commodity_spec,o.quantity,tax_amount,tatol_amount, hang_status,ugp.hang_amount , ugp.hang_quantity from fy_business_order o INNER JOIN upGetpay ugp "
					+ " on o.delivery_no = ugp.delivery_no " + where;
			List<Record> list = Db.find(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());

		}
		return null;
	}

	public File downloadFileRecord(String date, String[] ids) {
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String sql = "select id,order_date,o.work_order_no,commodity_name,commodity_spec,o.quantity,tax_amount,tatol_amount, hang_status,ugp.hang_amount , ugp.hang_quantity from fy_business_order o INNER JOIN upGetpay ugp "
				+ " on o.delivery_no = ugp.delivery_no  where o.id in " + sb.toString();
		List<Record> list = Db.find(sql);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "订单对账_" + date + ".xlsx");

		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/order_download.xlsx")
					.getFile();
			FileUtils.copyFile(new File(filename), targetfile);

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 1;
			for (Record item : list) {

				Date order_date = item.getDate("order_date");// 类别
				excel.setCellVal(row, 0, order_date);
				excel.setDateCellStyle(row, 0);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 1, work_order_no);

				String commodity_name = item.getStr("commodity_name");// 商品名
				excel.setCellVal(row, 2, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 3, commodity_spec);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 4, quantity);

				Double tax_amount = item.getDouble("tax_amount");// 未税单价
				excel.setCellVal(row, 5, tax_amount);

				Double tatolAmount = item.getDouble("tatol_amount");// 金额
				excel.setCellVal(row, 6, tatolAmount);

				String hang_status = item.getStr("hang_status");// 挂账状态
				excel.setCellVal(row, 7, hang_status);

				Double hang_quantity = item.getDouble("hang_quantity");// 挂账数量
				excel.setCellVal(row, 8, hang_quantity);

				Double hang_amount = item.getDouble("hang_amount");// 挂账总金额
				excel.setCellVal(row, 9, hang_amount);

				row++;
			}

			excel.save2File(targetfile);
			return targetfile;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
