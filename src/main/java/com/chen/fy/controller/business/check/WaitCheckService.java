package com.chen.fy.controller.business.check;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class WaitCheckService {
	public final static WaitCheckService me = new WaitCheckService();
	private static final Logger logger = LogManager.getLogger(WaitCheckService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name "
				+ ",inhouse.in_quantity in_quantity,inhouse.id inhouse_id";
		String from = "from  fy_business_order o  "
				+ " INNER JOIN fy_business_in_warehouse inhouse on o.id = inhouse.order_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id  "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no ";
		String where = " where inhouse.check_result is null";

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
		return modelPage;
	}

	/**
	 * 查找 检测的订单信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Record findCheckRecordById(Integer id) throws Exception {
		String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name "
				+ ",inhouse.*,inhouse.in_quantity in_quantity,inhouse.id inhouse_id";
		String from = " from  fy_business_order o  "
				+ " INNER JOIN fy_business_in_warehouse inhouse on o.id = inhouse.order_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id  "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no ";
		String where = " where inhouse.id = " + id;

		String desc = " order by inhouse.id  desc ";
		Record model = null;
		model = Db.findFirst(select + from + where + desc);
		return model;
	}

	/**
	 * 检测入库 ，根据检测更新信息
	 * @param model
	 * @return
	 */
	public synchronized Ret checkInhouse(FyBusinessInWarehouse model) throws Exception {

		FyBusinessInWarehouse old = FyBusinessInWarehouse.dao.findById(model.getId());
		Integer order_id = old.getOrderId();
		FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id);

		Integer difference = old.getInQuantity() - (model.getPassQuantity() == null ? 0 : model.getPassQuantity());
		Integer hasin = order.getHasInQuantity() - difference;
		order.setHasInQuantity(hasin);
		Integer storage = order.getStorageQuantity() == null ? 0 : order.getStorageQuantity();
		storage += model.getPassQuantity();
		order.setStorageQuantity(storage);

		Integer unpass = old.getInQuantity() - model.getPassQuantity();
		model.setUnpassQuantity(unpass);

		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				return order.update() && model.update();
			}
		});

		if (re) {
			return Ret.ok().set("msg", "检测完成");
		} else {
			return Ret.fail().set("msg", "检测失败，刷新页面在重试一下");
		}

	}

}
