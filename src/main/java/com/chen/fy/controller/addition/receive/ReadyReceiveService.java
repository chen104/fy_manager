package com.chen.fy.controller.addition.receive;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyReadyAdd;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ReadyReceiveService {
	public static final ReadyReceiveService me = new ReadyReceiveService();
	private static final Logger logger = LogManager.getLogger(ReadyReceiveService.class);

	public Page<Record> findPage(Integer pageIndex, Integer pageSize, String condition, String key) {
		Page<Record> modelPage = null;
		String select = "select o.*,ra.add_quantity,(quantity - add_quantity)  unadd_quantity,ra.id raId,ra.ready_order_id  ready_order_id";
		String from = "  from fy_business_order o  \n" + "LEFT JOIN  fy_ready_add ra on o.id= ra.add_order_id   ";
		String where = " where  order_status = 30 ";
		String orderby = " order by  o.id desc ";
		if (StringUtils.isEmpty(key)) {
			modelPage = Db.paginate(pageIndex, pageSize, select, from + where + orderby);
		} else {

			// StringBuilder sb = new StringBuilder();
			// sb.append(" and ").append(condition).append(" like '").append(key).append("'
			// ");
			// modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10,
			// "select
			// o.*,r.order_no,r.add_quantity1,r.add_quantity2,r.add_quantity3,r.quantity
			// ready_quantity ,add_status",
			// "from fy_business_order o LEFT JOIN fy_business_ready r\r\n"
			// + "on o.ready_id= r.id where dis_to = 3 " + sb.toString() + " order by id
			// desc");

		}

		return modelPage;
	}

	public Page<Record> findJsonPage(Integer index, Integer pageSize, String map_no) {
		Page<Record> modelPage = null;
		String select = "select o.id,o.commodity_name,map_no,quantity ";
		String from = "  from fy_business_order o   ";
		String where = " where  1 =1  ";// customer_no = '备货'
		String orderby = " order by  o.id desc ";
		if (StringUtils.isNotEmpty(map_no)) {
			map_no = map_no.trim();
			where = where + " AND map_no like '" + map_no + "' ";
		}

		modelPage = Db.paginate(index, pageSize, select, from + where + orderby);
		return modelPage;
	}

	/**选择备货
	 * @param addOrderId
	 * @param readyOrderId
	 * @return
	 */
	public Ret addToReady(Integer addOrderId, Integer readyOrderId) {
		Ret ret = null;
		Record record = Db.findFirst(" select * from fy_ready_add where  ready_order_id = ? AND add_order_id =? ",
				addOrderId,
				readyOrderId);
		if (record != null) {
			return Ret.fail().set("msg", "存在备货，不能在重复现在备货");
		}
		 boolean re = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				String update = " update fy_ready_add set ready_order_id = ? where add_order_id = ? ";
				int u = Db.update(update, readyOrderId, addOrderId);
				return 1 == u;
			}
		 });
//		FyReadyAdd model = new FyReadyAdd();
//		model.setAddOrderId(addOrderId);
//		model.setReadyOrderId(readyOrderId);
//		model.setCreateTime(new Date());
//		model.setAddQuantity(0);
//		boolean re = model.save();
		if (re) {
			Ret.ok();
			ret = Ret.ok("msg", "补货完成");
		} else {
			ret = Ret.fail("msg", "补货失败");
		}

		return ret;
	}

	/**
	 * 补货数量。 要更新备货id
	 * @param order_id 补货订单
	 * @param quantity 补单数量
	 * @return
	 */
	public Ret addQuantity(Integer order_id, Integer quantity) {
		Ret ret = null;
		FyReadyAdd model = FyReadyAdd.dao.findFirst(" select * from fy_ready_add where  add_order_id = " + order_id);

		if (model.getReadyOrderId() == null) {
			ret = Ret.fail("msg", "没有选择备货，不能补货");
			return ret;
		}

		Integer addquantiy = model.getAddQuantity();// 已补货数量

		FyBusinessOrder addmodel = FyBusinessOrder.dao.findById(order_id);
		Integer allquantity = addmodel.getQuantity();

		addquantiy = (addquantiy + quantity);
		if (addquantiy > allquantity) {
			ret = Ret.fail("msg", "补货数量不能超过订单数");
			return ret;
		}
		model.setAddQuantity(addquantiy);
		logger.debug("添加补货 ==> " + addquantiy);
		boolean re = model.update();
		if (re) {
			ret = Ret.ok("msg", "补货完成");
		} else {
			ret = Ret.fail("msg", "补货失败");
		}
		return ret;
	}

	/*
	 * 撤回
	 */
	public Ret rollback(Integer[] orderIds) {
		String update = " update fy_ready_add set ready_order_id = null,add_quantity = 0  where   add_order_id   in ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(orderIds, sb);
		logger.info(" 撤回备货 ===> sql : " + update + sb.toString());
		int del = Db.delete(update + sb.toString());
		if (del == orderIds.length) {
			return Ret.ok("msg", "撤回完成");
		} else {
			return Ret.fail("msg", "撤回" + del + " 条");
		}
	}

}
