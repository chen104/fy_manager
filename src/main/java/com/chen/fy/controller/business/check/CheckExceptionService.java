package com.chen.fy.controller.business.check;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CheckExceptionService {
	public final static CheckExceptionService me = new CheckExceptionService();
	private static final Logger logger = LogManager.getLogger(CheckExceptionService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name "
				+ ",inhouse.*,inhouse.in_quantity in_quantity,inhouse.id inhouse_id";
		String from = " from  fy_business_order o  "
				+ " INNER JOIN fy_business_in_warehouse inhouse on o.id = inhouse.order_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id  "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no ";
		String where = " where inhouse.unpass_quantity > 0";

		String desc = " order by inhouse.id  desc ";

		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		} else {

			if ("order_date".equals(condition)) {

				conditionSb.append(
						String.format(" AND DATE_FORMAT(order_date,%s) = '%s'", Constant.mysql_date_format, keyword));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format("AND  delivery_date = '%s'", keyword));

			} else if ("work_order_no".equals(condition)) {
				conditionSb.append(" AND  o.work_order_no like  ");
				conditionSb.append("'%").append(keyword).append("%'");
			} else if (StringUtils.isNotEmpty(keyword)) {

				conditionSb.append(String.format(" AND  %s like  ", condition));
				conditionSb.append("'%").append(keyword).append("%'");

			}

		}

		if (conditionSb.length() > 0) {
			where = where + conditionSb.toString();
			List<Record> list = Db.find(select + from + where + desc);
			modelPage = new Page<>(list, 1, list.size(), 1, list.size());
		} else {
			modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);
		}
		if (modelPage.getList().size() > 0) {
			Object obj = modelPage.getList().get(0).get("check_exception");
			System.out.println(obj);
		}

		return modelPage;

	}

	/**
	 * 下载异常记录表
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public File download(String ids[]) throws Exception {
		String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name "
				+ ",inhouse.*,inhouse.in_quantity in_quantity,inhouse.id inhouse_id";
		String from = " from  fy_business_order o  "
				+ " INNER JOIN fy_business_in_warehouse inhouse on o.id = inhouse.order_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id  "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no ";
		String where = " where inhouse.unpass_quantity > 0 AND inhouse.id in";

		String desc = " order by inhouse.id  desc ";
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);

		List<Record> list = Db.find(select + from + where + idsb.toString() + desc);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"检测异常记录表" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/check/exception.xlsx").getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		int row = 1;
		for (Record item : list) {
			String map_tmp = item.getStr("map_tmp");// 类别
			excel.setCellVal(row, 0, map_tmp);

			String plan_tmp = item.getStr("plan_tmp");// 计划员
			excel.setCellVal(row, 1, plan_tmp);

			String work_order_no = item.getStr("work_order_no");// 工作订单号
			excel.setCellVal(row, 2, work_order_no);

			String map_no = item.getStr("map_no");// 图号
			excel.setCellVal(row, 3, map_no);

			String commodity_name = item.getStr("commodity_name");// 名称
			excel.setCellVal(row, 4, commodity_name);

			String total_map_no = item.getStr("total_map_no");// 总图号
			excel.setCellVal(row, 5, total_map_no);

			String unpass_quantity = item.getStr("unpass_quantity");// 数量
			excel.setCellVal(row, 6, unpass_quantity);

			String unit_tmp = item.getStr("unit_tmp");// 单位
			excel.setCellVal(row, 7, unit_tmp);

			String model_no = item.getStr("model_no");// 型号
			excel.setCellVal(row, 8, model_no);

			String commodity_spec = item.getStr("commodity_spec");// 规格
			excel.setCellVal(row, 9, commodity_spec);

			String technology = item.getStr("technology");// 技术条件
			excel.setCellVal(row, 10, technology);

			String machining_require = item.getStr("machining_require");// 质量等级
			excel.setCellVal(row, 11, machining_require);

			Date order_date = item.getDate("order_date");// 订单日期
			excel.setCellVal(row, 12, order_date, "yyyy-MM-dd");

			String distribute_to = item.getStr("distribute_to");// 分配流向
			excel.setCellVal(row, 13, distribute_to);

			String supplier_name = item.getStr("supplier_name");// 厂商
			excel.setCellVal(row, 14, supplier_name);

			Date check_time = item.getDate("check_time");// 检测日期
			excel.setCellVal(row, 15, check_time, "yyyy-MM-dd");

			String exception_reson = item.getStr("exception_reson");// 不合格原因
			excel.setCellVal(row, 16, exception_reson);

			String check_remark = item.getStr("check_remark");// 备注
			excel.setCellVal(row, 17, check_remark);

			row++;
		}

		excel.save2File(targetfile);

		return targetfile;
	}
}
