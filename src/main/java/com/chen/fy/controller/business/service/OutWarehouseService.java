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

public class OutWarehouseService {
	private static final Logger logger = LogManager.getLogger(OutWarehouseService.class);
	public static final OutWarehouseService me = new OutWarehouseService();

	/**
	 * 查找出库可下载单据
	 * @param dDate
	 * @return
	 */
	public List<Record> findDownloadRecord(Date dDate) {
		try {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dDate);
			calendar.add(Calendar.DATE, -1);
			String start = DateFormatUtils.format(calendar, "yyyy-MM-dd");
			calendar.add(Calendar.DATE, 1);
			calendar.add(Calendar.MONTH, 1);
			String end = DateFormatUtils.format(calendar, "yyyy-MM-dd");
			String ptime = " where  out_time >'" + start + "' and out_time <'" + end + "'";
			String select = "select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id ";

			List<Record> list = Db.find(select + ptime);
			return list;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	/**
	 * 下载出库
	 * @param ids
	 * @param date
	 * @return
	 */
	public File downloadFile(String[] ids, String date) {
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String select = "select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id  where w.id in ";

		List<Record> list = Db.find(select + sb.toString());
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "出库单_" + date + ".xlsx");

		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/outWhouse.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 2;
			for (Record item : list) {
				String cate_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_tmp = item.getStr("plan_tmp");// 计划员
				excel.setCellVal(row, 1, plan_tmp);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 2, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 3, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 4, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 5, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 6, map_no);

				Double quantity = item.getDouble("out_quantity");// 数量
				excel.setCellVal(row, 7, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 8, unit_tmp);

				String transport_company = item.getStr("transport_company");// 运输公司
				excel.setCellVal(row, 9, transport_company);

				String transport_type = item.getStr("transport_type");// 运输方式
				excel.setCellVal(row, 10, transport_type);

				String transport_no = item.getStr("transport_no");// 运输单号
				excel.setCellVal(row, 11, transport_no);

				String receive_address = item.getStr("receive_address");// 收货地址
				excel.setCellVal(row, 12, receive_address);

				row++;
			}

			excel.save2File(targetfile);

			return targetfile;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}

	}

}
