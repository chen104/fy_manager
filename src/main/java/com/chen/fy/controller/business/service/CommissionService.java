package com.chen.fy.controller.business.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;

public class CommissionService {
	private static final Logger logger = LogManager.getLogger(CommissionService.class);
	public static final CommissionService me = new CommissionService();

	public List<FyBusinessOrder> searchDownloadOrder(String beginDate, String endDate) {
		String param = "and order_date >'" + beginDate + "' and order_date< '" + endDate + "' ";
		String sql = "select *  from  fy_business_order o    where Is_Distribute = 1 and dis_to = 1 " + param
				+ " order by id desc";
		List<FyBusinessOrder> list = FyBusinessOrder.dao.find(sql);
		return list;
	}

	/**
	 * 查找委外单，并下载
	 * @param ids
	 * @param date
	 * @return
	 */
	public File downloadWWsum(String[] ids, String date) {
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String sql = "select *  from  fy_business_order o    where id in  " + sb.toString() + " order by id desc";
		List<FyBusinessOrder> list = FyBusinessOrder.dao.find(sql);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "委外汇总表" + date + ".xlsx");
		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/wwsum.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);
			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 1;

			for (FyBusinessOrder item : list) {

				String cate_tmp = item.getStr("cate_tmp");// 客户
				// cate_tmp = item.getStr("customer");// 客户
				// cate_tmp = CacheKit.get(CacheNameConstant.base_customer_id2key, cate_tmp);

				// String plan_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_name = item.getStr("plan_tmp");// 计划员 plan_nam
				excel.setCellVal(row, 1, plan_name);

				String execu_status = item.getStr("execu_status");// 执行状态
				excel.setCellVal(row, 2, execu_status);

				String customer_no = item.getStr("customer_no");// 紧急状态
				excel.setCellVal(row, 3, customer_no);

				Date order_date = item.getDate("order_date");// 订单日期
				excel.setCellVal(row, 4, order_date);
				excel.setDateCellStyle(row, 4);

				Date delivery_date = item.getDate("delivery_date");// 交货日期
				excel.setCellVal(row, 5, delivery_date);
				excel.setDateCellStyle(row, 5);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 6, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 7, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 8, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 9, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 10, map_no);

				String technology = item.getStr("technology");// 技术条件
				excel.setCellVal(row, 11, technology);

				String machining_require = item.getStr("machining_require");// 加工要求
				excel.setCellVal(row, 12, machining_require);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 13, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 14, unit_tmp);

				Date receive_time = item.getDate("receive_time");// 接收时间
				excel.setCellVal(row, 15, receive_time);
				excel.setDateCellStyle(row, 15);

				Date distribute_time = item.getDate("distribute_time");// 分配时间
				excel.setCellVal(row, 16, distribute_time);
				excel.setDateCellStyle(row, 16);

				Double has_in_quantity = item.getDouble("has_in_quantity");// 入库数量
				excel.setCellVal(row, 17, has_in_quantity);

				Double ww_quantity = item.getDouble("ww_quantity");// 委外挂账数量
				excel.setCellVal(row, 18, ww_quantity);

				Double ww_unquantity = item.getDouble("ww_unquantity");// 未挂账数量
				excel.setCellVal(row, 19, ww_unquantity);

				Double ww_hang_amount = item.getDouble("ww_hang_amount");// 挂账金额
				excel.setCellVal(row, 20, ww_hang_amount);

				Double ww_unhang_amount = item.getDouble("ww_unhang_amount");// 未挂账金额
				excel.setCellVal(row, 21, ww_unhang_amount);

				// Date plan_finsh_time = item.getDate("plan_finsh_time");//入库时间
				// excel.setCellVal(row, 2, plan_finsh_time);

				row++;
			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
		return targetfile;
	}

}
