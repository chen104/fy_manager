package com.chen.fy.controller.business.product.collect;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.club.common.kit.ZipKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PlanCollectService {
	public static final PlanCollectService me = new PlanCollectService();

	public Page<FyBusinessOrder> findPage(Integer pageSize, Integer page, String condition, String keyWord)
			throws Exception {
		Page<FyBusinessOrder> modelPage = null;

		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = " from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where    dis_to = 0 and order_status=12   ";
		String desc = " order by distribute_time desc ";
		StringBuilder conditionSb = new StringBuilder();

		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
			conditionSb.toString();
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);
			conditionSb.toString();
		} else {

			if ("order_date".equals(condition)) {

				conditionSb.append(
						String.format("AND DATE_FORMAT(order_date,%s) = '%s'", Constant.mysql_date_format, keyWord));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format("and  delivery_date = '%s'", keyWord));

			} else if (StringUtils.isNotEmpty(keyWord)) {

				conditionSb.append(String.format("and  %s like  ", condition));
				conditionSb.append("'%").append(keyWord).append("%'");

			}
			if (StringUtils.isNotEmpty(keyWord)) {
				conditionSb.toString();
			}
		}
		if (conditionSb.length() > 0) {
			List<FyBusinessOrder> list = FyBusinessOrder.dao
					.find(select + from + where + conditionSb.toString() + desc);
			modelPage = new Page<FyBusinessOrder>(list, 1, list.size(), 1, list.size());
		} else {
			modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select, from + where + desc);
		}

		return modelPage;

	}

	/**
	 * 查找计划单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Record> tofindPlan(String[] ids) throws Exception {
		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = " from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where   o.id in  ";
		String desc = " order by o.id desc ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		List<Record> list = Db.find(select + from + where + sb.toString() + desc);
		return list;
	}

	/**
	 * 
	 * @param ids
	 * @param startDate
	 * @param finshDate
	 * @param remark
	 * @return
	 * @throws Exception
	 */
	public Ret updatePlanTime(String[] ids, String startDate, String finshDate, String remark) throws Exception {
		if (StringUtils.isEmpty(startDate)) {
			return Ret.fail().set("msg", "计划开始时间不能为空");
		}
		try {
			startDate = startDate.trim();
			DateUtils.parseDate(startDate, "yyyy-MM-dd");
		} catch (Exception e) {
			return Ret.fail().set("msg", "计划时间格式不对");
		}
		try {
			if (StringUtils.isNotEmpty(finshDate)) {
				finshDate = finshDate.trim();
				DateUtils.parseDate(finshDate, "yyyy-MM-dd");
			}
		} catch (Exception e) {
			return Ret.fail().set("msg", "计划结束时间格式不对");
		}

		String sql = " update  fy_business_order o set   plan_time =  STR_TO_DATE('" + startDate + "','%Y-%m-%d') ";
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(finshDate)) {
			sb.append(",plan_finsh_time =STR_TO_DATE('" + finshDate + "','%Y-%m-%d')");
		}
		if (StringUtils.isNotEmpty(remark)) {
			remark = remark.trim();
			sb.append(",plan_remark = '").append(remark).append("' ");
		}

		sql = sql + sb.toString();
		StringBuilder update = new StringBuilder(sql);
		update.append(" where id in ");
		SqlKit.joinIds(ids, update);
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int re = Db.update(update.toString());
				return re == ids.length;
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}

		return ret;
	}

	public File download(String[] ids) throws Exception {
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
		File dirFile = new File(parentfile, "生产一览表" + current);

		dirFile.mkdir();

		File targetfile = new File(dirFile, "订单" + current + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/planCollect/plan_collect.xlsx")
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
			


			String map_no = item.getStr("map_no");
			excel.setCellVal(row, 0, map_no);

			String commodity_name = item.getStr("commodity_name");
			excel.setCellVal(row, 1, commodity_name);

			String total_map_no = item.getStr("total_map_no");
			excel.setCellVal(row, 2, total_map_no);

			Double quantity = item.getDouble("quantity");
			excel.setCellVal(row, 3, quantity);

			String unit_tmp = item.getStr("unit_tmp");
			excel.setCellVal(row, 4, unit_tmp);
			
			
			String cate_tmp = item.getStr("model_no");
			excel.setCellVal(row, 5, cate_tmp);
			
			//commodity_name 
			String commodity_spec = item.getStr("commodity_spec");
			excel.setCellVal(row, 6, commodity_spec);

			String technology = item.getStr("technology");
			excel.setCellVal(row, 7, technology);

			String machining_require = item.getStr("machining_require");
			excel.setCellVal(row, 8, machining_require);
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

}
