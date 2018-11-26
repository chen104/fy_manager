package com.chen.fy.controller.business.commission.collect;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CommissionCollectService {
	public final static CommissionCollectService me = new CommissionCollectService();

	/**
	 * 委外汇总表
	 * @param pageSize
	 * @param pageIndex
	 * @param condition
	 * @param keyWord
	 * @return
	 */
	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword,
			String inhouse_date_start, String inhouse_date_end) {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,"
				+ "audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name,\n"
				+ "pv.pay_check_result pv_check_result,pv.pay_quantity  pv_pay_quantity , \n"
				+ "pv.pay_hang_date pv_hang_date,pv.pay_date pv_pay_date, \n" + " pass_quantity \n";
		String from = " from  fy_business_order o " + "   left join fy_base_fyfile  f on o.draw = f.id \n"
				+ " Inner join fy_business_purchase audit on o.id = audit.order_id \n"
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no \n"
				+ " LEFT JOIN pay_view pv on pv.order_id = o.id AND is_purchase = 1"
				+ " LEFT JOIN fy_check_collect fc on fc.order_id = o.id " + "  \n ";
		String desc = " order by o.id  desc \n";
		String where = "  where add_status=3 \n";
		String dateformat = "'%Y-%m-%d'";
		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(purchase_delivery_date , NOW()) < 3 AND DATEDIFF(purchase_delivery_date , NOW()) > 0 and o.quantity <> IFNULL(pass_quantity,0) ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(purchase_delivery_date , NOW()) < 0   and IFNULL(pass_quantity,0) <> o.quantity ";
			conditionSb.append(sql);

		}
		if ("inhouse_status".equals(condition)) {
			if ("un_in".equals(keyword)) {
				conditionSb.append(" AND o.has_in_quantity = 0  ");
			} else if ("part_in".equals(keyword)) {
				conditionSb.append(" AND o.has_in_quantity  <  o.quantity AND o.has_in_quantity <> 0  ");
			} else if ("all_in".equals(keyword)) {
				conditionSb.append(" AND o.has_in_quantity  = o.quantity  ");
			}
		}

		if ("inhouse_date".equals(condition)) {
			if (StringUtils.isNotEmpty(inhouse_date_start)) {
				conditionSb.append(" AND o.inhouse_date  > '").append(inhouse_date_start).append("' ");
			}
			if (StringUtils.isNotEmpty(inhouse_date_end)) {
				conditionSb.append(" AND o.inhouse_date  < '").append(inhouse_date_end).append("' ");
			}
		}

		else {
			if (StringUtils.isNotEmpty(keyword)) {
				if ("order_date".equals(condition)) {

					conditionSb.append(String.format(" AND DATE_FORMAT(order_date,%s) = '%s'", dateformat, keyword));

				} else if ("delivery_date".equals(condition)) {

					conditionSb.append(String.format("AND   DATE_FORMAT(delivery_date,%s)= '%s'", dateformat, keyword));

				} else if ("work_order_no".equals(condition)) {
					conditionSb.append(" AND  o.work_order_no like  ");
					conditionSb.append("'%").append(keyword).append("%'");
				} else if ("supplier".equals(condition)) {
					conditionSb.append(" AND  s.name like  ");
					conditionSb.append("'%").append(keyword).append("%'");
				} else {

					conditionSb.append(String.format(" AND  %s like  ", condition));
					conditionSb.append("'%").append(keyword).append("%'");

				}
			}

		}

		if (conditionSb.length() > 0) {
			where = where + conditionSb.toString();
			List<Record> list = Db.find(select + from + where + desc);
			modelPage = new Page<Record>(list, 1, list.size(), 1, list.size());
		} else {
			modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);
		}

		return modelPage;
	}

	/**
	 * 下载委外单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public File downloadFile(String[] ids) throws Exception {
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,"
				+ "audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name,\n"
				+ "pv.pay_check_result pv_check_result,pv.pay_quantity  pv_pay_quantity , \n"
				+ "pv.pay_hang_date pv_hang_date,pv.pay_date pv_pay_date \n";
		String from = " from  fy_business_order o " + "   left join fy_base_fyfile  f on o.draw = f.id \n"
				+ " Inner join fy_business_purchase audit on o.id = audit.order_id \n"
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no \n"
				+ " LEFT JOIN pay_view pv on pv.order_id = o.id AND is_purchase = 1 \n ";
		String where = " WHERE o.id in ";
		String orderby = "  order by o.id  desc   ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		List<Record> list = Db.find(select + from + where + sb.toString() + orderby);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		String current = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");

		File targetfile = new File(parentfile, "委外单" + current + ".xlsx");
		if (targetfile.exists()) {
			targetfile.delete();
		}

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/commision/commision.xlsx")
				.getFile();
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

			String work_order_no = item.getStr("work_order_no");// 工作订单号
			excel.setCellVal(row, 2, work_order_no);

			String delivery_no = item.getStr("delivery_no");// 送货单号
			excel.setCellVal(row, 3, delivery_no);

			String map_no = item.getStr("map_no"); // 图号
			excel.setCellVal(row, 4, map_no);

			String commodity_name = item.getStr("commodity_name");// 名称
			excel.setCellVal(row, 5, commodity_name);

			String filename = item.getStr("total_map_no"); // 总图号
			excel.setCellVal(row, 6, filename);

			Double purchase_cost = item.getDouble("purchase_cost"); // 采购单价
			excel.setCellVal(row, 7, purchase_cost);

			Double purchase_account = item.getDouble("purchase_account"); // 采购总价
			excel.setCellVal(row, 8, purchase_account);

			String supplier_name = item.getStr("supplier_name");// ; 制造商
			excel.setCellVal(row, 9, supplier_name);

			String purchase_no = item.getStr("purchase_no");// ; 订单编码
			excel.setCellVal(row, 10, purchase_no);

			Date plan_time = item.getDate("plan_time");// ; 采购日期
			excel.setCellVal(row, 11, plan_time, "yyyy-MM-dd");

			Date purchase_delivery_date = item.getDate("purchase_delivery_date");// ; 交货日期
			excel.setCellVal(row, 12, purchase_delivery_date, "yyyy-MM-dd");

			Integer has_in_quantity = item.getInt("has_in_quantity");// 入库数量
			excel.setCellVal(row, 13, has_in_quantity);

			String inhouse_status = item.getStr("inhouse_status");// 入库状态
			excel.setCellVal(row, 14, inhouse_status);

			Date inhouse_date = item.getDate("inhouse_date");// 入库时间
			excel.setCellVal(row, 15, inhouse_date);

			String pv_check_result = item.getStr("pv_check_result");// 检验结果
			excel.setCellVal(row, 16, pv_check_result);

			Integer pv_pay_quantity = item.getInt("pv_pay_quantity"); // 挂账数量
			excel.setCellVal(row, 17, pv_pay_quantity);

			Integer quantity = item.getInt("quantity"); // 数量

			if (pv_pay_quantity == null || pv_pay_quantity == 0) {// 挂账状态
				excel.setCellVal(row, 18, "未挂账");
			} else if (quantity == pv_pay_quantity) {
				excel.setCellVal(row, 18, "已挂账");
			} else if (pv_pay_quantity < quantity) {
				excel.setCellVal(row, 18, "部分挂账");
			} else {
				excel.setCellVal(row, 18, "挂账数量异常");
			}


			Date pv_hang_date = item.getDate("pv_hang_date");// 挂账时间
			excel.setCellVal(row, 19, pv_hang_date, "yyyy-MM");

			Date pv_pay_date = item.getDate("pv_pay_date");// 应付期间
			excel.setCellVal(row, 20, pv_pay_date, "yyyy-MM");

			row++;
		}

		excel.save2File(targetfile);
		excel.close();
		return targetfile;
	}
}
