package com.chen.fy.controller.business.outhouse;

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

import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.CommonKit;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class OuthouseService {
	public final static OuthouseService me = new OuthouseService();
	private static final Logger logger = LogManager.getLogger(OuthouseService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword,
			String out_date_start, String out_date_end) throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId,ou.* ,ou.id out_id ";
		String from = " from  fy_business_order o " + " INNER JOIN fy_business_out_warehouse ou on ou.order_id = o.id"
				+ "   left join fy_base_fyfile  f on o.draw = f.id ";

		String desc = " order by o.id  desc ";
		String where = "  where  1=1  ";

		if (StringUtils.isNotEmpty(out_date_start)) {
			conditionSb.append(" AND ou.out_time > '").append(out_date_start).append("' ");
		}
		if (StringUtils.isNotEmpty(out_date_end)) {
			conditionSb.append(" AND ou.out_time < '").append(out_date_end).append("' ");
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

	/**
	 *  查找订单出库
	 * @param id
	 * @return
	 */
	public Record findOrderById(Integer id) {
		String sql = " select * from fy_business_order o where id = " + id;
		Record model = Db.findFirst(sql);
		return model;
	}

	/**
	 * 新建出库的订单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Record> findOrderByIds(String[] ids) throws Exception {
		List<Record> list = null;
		StringBuilder sql = new StringBuilder(
				"  select o.* ,f.originalFileName filename,f.id fileId from fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  where o.id in ");
		SqlKit.joinIds(ids, sql);
		sql.append(" order by o.id desc ");
		list = Db.find(sql.toString());
		return list;
	}

	/**
	 * 新建出库单
	 * @return
	 */
	public Ret batchSave(Integer[] order_ids, FyBusinessOutWarehouse model) throws Exception {
		List<Record> list = new ArrayList<Record>();
		List<FyBusinessOrder> orders = new ArrayList<FyBusinessOrder>();
		for (int i = 0; i < order_ids.length; i++) {
			Record record = model.toRecord();
			record.set("order_id", order_ids[i]);
			record.set("parent_id", order_ids[i]);
			FyBusinessOrder order = FyBusinessOrder.dao.findById(order_ids[i]);

			Integer storagequantity = order.getStorageQuantity();

			record.set("out_quantity", storagequantity);

			Integer out_quantity = order.getOutQuantity() == null ? 0 : order.getOutQuantity();
			out_quantity = out_quantity + order.getStorageQuantity();
			order.setOutQuantity(out_quantity);
			order.setStorageQuantity(0);
			list.add(record);
			orders.add(order);
		}
		StringBuilder total = new StringBuilder();
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int[] re = Db.batchSave("fy_business_out_warehouse", list, list.size());
				int[] res = Db.batchUpdate(orders, orders.size());
				total.append(CommonKit.totalInt(re));
				return re.length == res.length;
			}
		});
		if (re) {
			return Ret.ok().set("msg", "新建出库完成 新建" + total.toString() + " 条 出库信息 ");
		} else {
			return Ret.fail().set("msg", " 出库失败 ");
		}

	}

	/**
	 * 撤回
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Ret batchRollback(String[] id) throws Exception {
		String update = "update fy_business_order o INNER JOIN fy_business_out_warehouse ou \r\n"
				+ "on o.id = ou.order_id set out_house_date =null, o.out_quantity  = o.out_quantity - ou.out_quantity ,o.storage_quantity=o.storage_quantity+ou.out_quantity\r\n"
				+ "where ou.id = ";
		int total = 0;
		for (String outId : id) {
			boolean re = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					int re = Db.update(update + outId);
					int d = Db.delete("delete from  fy_business_out_warehouse where id = " + outId);
					return re == 1 && d == 1;
				}
			});
			if (re) {
				total++;
			}
		}

		return Ret.ok().set("msg", "撤回出库完成，共撤回" + total + " 条 出库");
	}

	/**
	 * 下载出库单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public File download(String[] ids) throws Exception {
		String select = " select o.* ,ou.*  ";
		String from = " from  fy_business_order o " + " INNER JOIN fy_business_out_warehouse ou on ou.order_id = o.id";

		String where = " where ou.id in ";
		String desc = " order by ou.id desc ";
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);

		List<Record> list = Db.find(select + from + where + idsb.toString() + desc);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"出库单" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/whouse/outhouse.xlsx").getFile();
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

			String work_order_no = item.getStr("work_order_no");
			excel.setCellVal(row, 2, work_order_no);

			String map_no = item.getStr("map_no");// 图号
			excel.setCellVal(row, 3, map_no);

			String commodity_name = item.getStr("commodity_name");// 名称
			excel.setCellVal(row, 4, commodity_name);

			String total_map_no = item.getStr("total_map_no");// 总图号
			excel.setCellVal(row, 5, total_map_no);

			Integer out_quantity = item.getInt("out_quantity");
			excel.setCellVal(row, 6, out_quantity);

			Date out_time = item.getDate("out_time");// 出货日期
			excel.setCellVal(row, 7, out_time, "yyyy-MM-dd");

			String out_remark = item.getStr("out_remark");// 备注
			excel.setCellVal(row, 8, out_remark);

			row++;
		}

		excel.save2File(targetfile);
		Db.update(" update  fy_business_out_warehouse set is_download = 1 where id in " + idsb.toString());
		return targetfile;
	}

}
