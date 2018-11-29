package com.chen.fy.controller.business.check;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.jfinal.club.common.kit.ZipKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CheckCollectService {
	public final static CheckCollectService me = new CheckCollectService();
	private static final Logger logger = LogManager.getLogger(CheckCollectService.class);
	String select = "select o.*,p.supplier_id,pass_quantity,unpass_quantity,\n"
			+ " f.originalFileName filename, f.filename realName ,f.id fileId , f.filepath , \n"
			+ "check_time,check_result,s.name supplier_name ,cc.id collect_id	\r\n";
	String from = " 	from fy_check_collect cc\r\n"
			+ " INNER JOIN  fy_business_order o on cc.order_id = o.id\r\n"
			+ " left join fy_base_fyfile  f on o.draw = f.id   \n"
			+ "  LEFT JOIN fy_business_purchase p on p.order_id = cc.order_id\r\n"
			+ "		LEFT JOIN fy_base_supplier s on p.supplier_id = s.id  \r\n";
	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		String where = "  where cc.check_result is not null  ";
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();

		String desc = " order by cc.id desc ";
		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else if ("delay".equals(condition)) {
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
		where = where + conditionSb.toString();
		modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);

		return modelPage;
	}



	public File download(String ids[]) throws Exception {

		String where = " where  cc.id in ";
		String desc = " order by cc.id desc ";
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);

		List<Record> list = Db.find(select + from + where + idsb.toString() + desc);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		String current = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
		File dirFile = new File(parentfile, "检测汇总表" + current);
		if (dirFile.exists()) {
			FileUtils.forceDelete(dirFile);
		}
		dirFile.mkdir();

		File targetfile = new File(dirFile,
				"检测一览表" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/check/check_collect.xlsx")
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
			String map_tmp = item.getStr("cate_tmp");// 类别
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

			String quantity = item.getStr("quantity");// 数量
			excel.setCellVal(row, 6, quantity);

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
			if (StringUtils.isEmpty(supplier_name)) {

				if ("自产".equals(distribute_to)) {
					supplier_name = "发奕林";
				}

			}
			excel.setCellVal(row, 14, supplier_name);

			String has_in_quantity = item.getStr("has_in_quantity");// 入库数量
			excel.setCellVal(row, 15, has_in_quantity);

			String pass_quantity = item.getStr("pass_quantity");// 合格数量
			excel.setCellVal(row, 16, pass_quantity);

			String unpass_quantity = item.getStr("unpass_quantity");// 不合格数量
			excel.setCellVal(row, 17, unpass_quantity);

			Date check_time = item.getDate("check_time");// 检测日期
			excel.setCellVal(row, 18, check_time, "yyyy-MM-dd");

			String exception_reson = item.getStr("exception_reson");// 不合格原因
			excel.setCellVal(row, 19, exception_reson);

			String check_remark = item.getStr("check_remark");// 备注
			excel.setCellVal(row, 20, check_remark);

			row++;
		}

		excel.save2File(targetfile);
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

		if (zipFile.exists()) {
			FileUtils.forceDelete(zipFile);
		}
		ZipKit zipkip = new ZipKit(zipFile);
		zipkip.addDir(dirFile);
		zipkip.close();
		FileUtils.forceDelete(dirFile);
		return zipFile;
	}

	/**
	 * 撤回库存到，待检测
	 * @param ids
	 * @return
	 */
	public Ret rollback(String[] ids) {
		Ret ret = null;
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);
		String findsql = "select order_id from fy_check_collect where id in " + idsb.toString();
		List<Record> Listorder = Db.find(findsql);
		List<Integer> orderlist = new ArrayList<Integer>();
		for (Record e : Listorder) {
			Integer item = e.getInt("order_id");
			orderlist.add(item);
		}

		StringBuilder orderSb = new StringBuilder();
		SqlKit.joinIds(orderlist, orderSb);

		List<Record> list = Db
				.find("select distribute_attr,distribute_to,id from  fy_business_order   where  dis_to = 1  AND id in "
						+ orderSb.toString());
		if (list.size() > 0) {
			ret = Ret.fail().set("msg", "存在委外单，请在应付单撤回");
			return ret;
		}
		String updateSql = Db.getSql("storage.rollbackProductStorage");
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				String sql = String.format(updateSql, orderSb.toString());

				logger.debug("撤回 库存 sql  " + sql);
				int updateCount = Db.update(sql);
				return (updateCount) == (ids.length * 2);
			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "撤回完成");
		} else {
			ret = Ret.ok().set("msg", "撤回失败，刷新之后再撤回");
		}
		return ret;
	}
}
