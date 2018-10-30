package com.chen.fy.controller.business.waitInhouse;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.Account;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class WaitInhouseService {
	public final static WaitInhouseService me = new WaitInhouseService();
	private static final Logger logger = LogManager.getLogger(WaitInhouseService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword,
			Account accout)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name ,o.id order_id";
		String from = " from  fy_business_order o "
				+ "   left join fy_base_fyfile  f on o.draw = f.id "
				+ " LEFT JOIN	 fy_business_purchase audit on o.id = audit.order_id"
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no";
		String desc = " order by o.id  desc ";
		String where = "  where ( o.has_in_quantity < o.quantity) and (order_status= 4 or order_status=12) ";
		if (accout.hasPermission("house_product_selft") && accout.hasPermission("house_purchase")) {

		} else if (accout.hasPermission("house_purchase")) {
			conditionSb.append(" AND o.dis_to = 1 ");// 委外
		} else if (accout.hasPermission("house_product_selft")) {
			conditionSb.append(" AND o.dis_to = 0 ");// 自产
		}

		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		} else {
			if (StringUtils.isNotEmpty(keyword)) {
				if ("order_date".equals(condition)) {

					conditionSb.append(String.format(" AND DATE_FORMAT(order_date,%s) = '%s'",
							Constant.mysql_date_format, keyword));

				} else if ("delivery_date".equals(condition)) {

					conditionSb.append(String.format("AND  delivery_date = '%s'", keyword));

				} else if ("work_order_no".equals(condition)) {
					conditionSb.append(" AND  o.work_order_no like  ");
					conditionSb.append("'%").append(keyword).append("%'");
				} else if ("supplier_name".equals(condition)) {
					conditionSb.append(" AND  s.name like  '%").append(keyword).append("%' ");
				}
				else {

					conditionSb.append(String.format(" AND  %s like  ", condition));
					conditionSb.append("'%").append(keyword).append("%'");

				}
			}


		}
		where = where + conditionSb.toString();
		modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);

		return modelPage;
	}

	/**
	 * 入库
	 * @param order_id 订单id
	 * @param inhouseQuantity 入库数量
	 * @return
	 */
	public Ret inhouse(Integer order_id, String inhouseQuantity) throws Exception {

		if (!NumberUtils.isNumber(inhouseQuantity)) {
			return Ret.fail("msg", "入库数量不是数字");

		}
		FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id);
		if (order == null) {
			return Ret.fail("msg", "刷新页面之后再入库");

		}
		FyBusinessInWarehouse inhouse = new FyBusinessInWarehouse();
		inhouse.setOrderId(order_id);
		/**
		 * 自产为0，委外为1，备货为3
		 */
		if (order.getDisTo() == 1) {
			inhouse.setInFrom("采购");
		} else if (order.getDisTo() == 0) {
			inhouse.setInFrom("本部");
		}
		inhouse.setInQuantity(Integer.valueOf(inhouseQuantity));
		if (inhouse.getInQuantity() < 0) {
			return Ret.fail("msg", "入库数量为正整数");
		}
		order.setHasInQuantity(order.getHasInQuantity() + inhouse.getInQuantity());
		if (order.getHasInQuantity() > order.getQuantity()) {
			return Ret.fail("msg", "入库数量大于订单数");
		}
		if (order.getHasInQuantity() == order.getQuantity()) {
			order.setInhouseStatus("已入库");
		} else {
			order.setInhouseStatus("部分入库");
		}
		Date date = new Date();
		inhouse.setInTime(date);
		order.setInhouseDate(date);

		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				return inhouse.save() && order.update();
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "入库完成");
		} else {
			ret = Ret.ok().set("msg", "入库失败");
		}
		return ret;

	}
}
