package com.chen.fy.controller.business.commission.execut;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.club.common.kit.ZipKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CommisionExecutService {
	public static final CommisionExecutService me = new CommisionExecutService();

	public Page<FyBusinessOrder> findPage(Integer pageSize, Integer page, String condition, String keyWord,
			String weiwai_cate) {
		Page<FyBusinessOrder> modelPage = null;

		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name ";
		String from = " from  fy_business_order o " + "   left join fy_base_fyfile  f on o.draw = f.id "
				+ " left join fy_business_purchase audit on o.work_order_no = audit.work_order_no"
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no";
		String desc = " order by receive_time  desc ";
		String where = "  where Is_Distribute = 1 and dis_to = 1  AND order_status=2 ";

		if (StringUtils.isNotEmpty(weiwai_cate)) {
			conditionSb.append(" AND weiwai_cate = '").append(weiwai_cate).append("' ");
		}

		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		} else {

			if ("order_date".equals(condition)) {

				conditionSb.append(String.format(" AND order_date = '%s'", keyWord));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format("AND  delivery_date = '%s'", keyWord));

			} else if ("work_order_no".equals(condition)) {
				conditionSb.append(" AND  o.work_order_no like  ");
				conditionSb.append("'%").append(keyWord).append("%'");
			} else if (StringUtils.isNotEmpty(keyWord)) {

				conditionSb.append(String.format(" AND  %s like  ", condition));
				conditionSb.append("'%").append(keyWord).append("%'");

			}

		}
		where = where + conditionSb.toString();
		modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select, from + where + desc);
		return modelPage;
	}

	/*
	 * |查找符合条件的单据
	 */
	public List<Record> findDownloadAskCost(String start_date, String end_date, String condition, String keyword)
			throws Exception {
		StringBuilder sb = new StringBuilder();

		String select = " select o.* ,f.originalFileName filename,f.id fileId ";
		String from = " from  fy_business_order o "
				+ " LEFT JOIN payView p on o.id = p.order_id  left join fy_base_fyfile  f on o.draw = f.id ";
		String desc = " order by o.id desc ";
		String where = "  where Is_Distribute = 1 and dis_to = 1 ";
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtils.parseDate(start_date, "yyyy-MM-dd");
			endDate = DateUtils.parseDate(end_date, "yyyy-MM-dd");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (startDate != null) {
			sb.append("AND o.order_date > '").append(start_date).append("' ");
		}

		if (endDate != null) {
			sb.append(" and o.order_date < '").append(end_date).append("' ");

		}

		if (StringUtils.isNotEmpty(keyword)) {

			if ("order_date".equals(condition)) {

				sb.append(String.format("AND  order_date = '%s'", keyword));

			} else if ("delivery_date".equals(condition)) {

				sb.append(String.format("AND delivery_date = '%s'", keyword));

			} else if (StringUtils.isNotEmpty(condition)) {

				sb.append(String.format("AND  %s like  ", condition, keyword));
				sb.append("'%").append(keyword).append("%'");
			}
		} else {
			if ("delay_warn".equals(condition)) {
				sb.append("AND    DATEDIFF(delivery_date , NOW()) < 3 and out_quantity = 0 ");

			} else if ("delay".equals(condition)) {
				sb.append(" AND   DATEDIFF(delivery_date , NOW()) < 0 and out_quantity = 0 ");

			}
		}

		List<Record> list = Db.find(select + from + where.toString() + sb.toString() + desc);

		return list;
	}

	public File downloadAskCost(String[] ids) throws Exception {
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,f.filename realName,f.filepath";
		String from = " from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  ";
		String desc = "  order by o.id desc ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		List<Record> list = Db.find(select + from + " where o.id in " + sb.toString() + desc);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		String current = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
		File dirFile = new File(parentfile, "报目表" + current);

		dirFile.mkdir();

		File targetfile = new File(dirFile, "订单" + current + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/commision/askcost.xlsx").getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		int row = 1;
		for (Record item : list) {
			String cate_tmp = item.getStr("cate_tmp");
			excel.setCellVal(row, 0, cate_tmp);

			String work_order_no = item.getStr("work_order_no");
			excel.setCellVal(row, 1, work_order_no);

			String commodity_name = item.getStr("commodity_name");
			excel.setCellVal(row, 2, commodity_name);

			String commodity_spec = item.getStr("map_no");
			excel.setCellVal(row, 3, commodity_spec);

			String filename = item.getStr("total_map_no");
			excel.setCellVal(row, 4, filename);

			Double quantity = item.getDouble("quantity");
			excel.setCellVal(row, 5, quantity);

			String unit_tmp = item.getStr("unit_tmp");
			excel.setCellVal(row, 6, unit_tmp);

			// String delivery_date = item.getStr("delivery_date");
			// excel.setCellVal(row, 7, delivery_date);

			// String suppiler_no = item.getStr("suppiler_no");
			// excel.setCellVal(row, 7, suppiler_no);
			//
			// String supplier_name = item.getStr("supplier_name");
			// excel.setCellVal(row, 8, supplier_name);
			//
			// Double purchase_cost = item.getDouble("purchase_cost");
			// excel.setCellVal(row, 9, purchase_cost);
			//
			// Double purchase_account = item.getDouble("purchase_account");
			// excel.setCellVal(row, 10, purchase_account);

			row++;
		}

		excel.save2File(targetfile);
		excel.close();

		/**
		 * 拷贝图纸
		 */
		for (Record item : list) {
			String filename = item.getStr("realName");
			String originalFileName = item.getStr("filename");
			String filepath = item.getStr("filepath");
			if (filename != null && originalFileName != null && filepath != null) {
				File sourcefile = new File(filepath, filename);
				if (sourcefile.exists()) {
					FileUtils.copyFile(sourcefile, new File(dirFile, originalFileName));
				}
			}
		}
		File zipFile = new File(dirFile.getParentFile(), dirFile.getName() + ".zip");
		ZipKit zipkip = new ZipKit(zipFile);
		zipkip.addDir(dirFile);
		zipkip.close();
		// FileUtils.forceDelete(dirFile);
		return zipFile;
	}

	public Record findEdit(String order_id) throws Exception {
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no";
		String from = " from  fy_business_order o " + "   left join fy_base_fyfile  f on o.draw = f.id "
				+ " left join  fy_business_purchase  audit on o.work_order_no = audit.work_order_no";
		String where = " where o.id = " + order_id;
		Record model = Db.findFirst(select + from + where);
		return model;
	}

}