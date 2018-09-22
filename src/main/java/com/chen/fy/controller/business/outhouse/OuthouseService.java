package com.chen.fy.controller.business.outhouse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.CommonKit;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class OuthouseService {
	public final static OuthouseService me = new OuthouseService();
	private static final Logger logger = LogManager.getLogger(OuthouseService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId,ou.*  ";
		String from = " from  fy_business_order o " + " INNER JOIN fy_business_out_warehouse ou on ou.order_id = o.id"
				+ "   left join fy_base_fyfile  f on o.draw = f.id ";

		String desc = " order by o.id  desc ";
		String where = "  where  1=1  ";

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
	 * 撤回出库单
	 */
	public void rollback(Integer id) {
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		Integer oder_id = model.getOrderId();
		FyBusinessOrder order = FyBusinessOrder.dao.findById(oder_id);
		Integer storage = order.getStorageQuantity();
		storage += model.getOutQuantity();
		order.setStorageQuantity(storage);

	}

}
