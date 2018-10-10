package com.chen.fy.controller.business.distribut;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.club.common.kit.ZipKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class DistributService {
	private static final Logger logger = LogManager.getLogger(DistributService.class);
	public static final DistributService me = new DistributService();

	public Page<FyBusinessOrder> findPage(Integer pageSize, Integer page, String condition, String keyWord)
			throws Exception {
		Page<FyBusinessOrder> modelPage = null;

		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = " from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where distribute_to is null AND order_status = 0";
		String desc = " order by o.id desc,is_distribute desc ";
		StringBuilder conditionSb = new StringBuilder();

		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
			where = where + conditionSb.toString();
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);
			where = where + conditionSb.toString();
		} else {

			if ("order_date".equals(condition)) {

				conditionSb.append(String.format(" AND order_date = '%s'", keyWord));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format(" AND  delivery_date = '%s'", keyWord));

			} else if (StringUtils.isNotEmpty(keyWord)) {

				conditionSb.append(String.format(" AND  %s like  ", condition));
				conditionSb.append("'%").append(keyWord).append("%'");

			}
			if (StringUtils.isNotEmpty(keyWord)) {
				where = where + conditionSb.toString();
			}
		}
		modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select, from + where + desc);
		return modelPage;
	}

	public File downloadDistrbut(String[] ids) throws Exception {
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
		File dirFile = new File(parentfile, "分配报目表" + current);

		dirFile.mkdir();

		File targetfile = new File(dirFile, "订单" + current + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/order/distributAskcost.xlsx")
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
			String cate_tmp = item.getStr("cate_tmp");
			excel.setCellVal(row, 0, cate_tmp);

			String work_order_no = item.getStr("work_order_no");
			excel.setCellVal(row, 1, work_order_no);

			String commodity_name = item.getStr("commodity_name");
			excel.setCellVal(row, 2, commodity_name);

			String commodity_spec = item.getStr("map_no");
			excel.setCellVal(row, 3, commodity_spec);

			String total_map_no = item.getStr("total_map_no");
			excel.setCellVal(row, 4, total_map_no);

			Double quantity = item.getDouble("quantity");
			excel.setCellVal(row, 5, quantity);

			String unit_tmp = item.getStr("unit_tmp");
			excel.setCellVal(row, 6, unit_tmp);

			String delivery_date = item.getStr("delivery_date");
			excel.setCellVal(row, 7, delivery_date);
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
		FileUtils.forceDelete(dirFile);
		return zipFile;
	}

	/**
	 * 把已分配的单据，撤回到待分配中
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Ret rollBack(String[] ids) throws Exception {
		/*
		 * 需要修改的字段 order_status = 0 distribute_attr = null distribute_to = null
		 * is_distribute = 1 plan_time =null plan_finsh_time =null plan_remark = null
		 * distribute_time = null receive_time
		 */

		String update = " update fy_business_order set order_status = 0, distribute_attr = '撤回',\n"
				+ " distribute_to = null,is_distribute = 1, plan_time =null, plan_finsh_time =null,\n"
				+ "plan_remark = null, distribute_time = null,receive_time=null \n," + "dis_to = null "
				+ " where has_in_quantity = 0 AND id in ";
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {

				int re = Db.update(update + idsb.toString());
				Db.update(
						"Insert into fy_business_purchase_callback select * from fy_business_purchase where order_id = "
								+ idsb.toString());
				Db.delete(" delete from fy_business_purchase where  order_id in " + idsb.toString());
				Db.delete("delete from fy_ready_add where add_order_id in " + idsb.toString());
				return re == ids.length;
			}
		});
		if (re) {

			return Ret.ok().set("msg", "撤回完成");
		} else {
			return Ret.ok().set("msg", "撤回失败，刷新之后再撤回");
		}

	}

}
