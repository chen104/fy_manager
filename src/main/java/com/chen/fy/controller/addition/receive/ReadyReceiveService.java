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
		String select = "select o.*,ra.add_quantity,(quantity - IFNULL(add_quantity,0))  unadd_quantity,ra.id raId,ra.ready_order_id  ready_order_id";
		String from = "  from fy_business_order o  \n" + "LEFT JOIN  fy_ready_add ra on o.id= ra.add_order_id   ";
		String where = " where  order_status = 30 ";
		String orderby = " order by  o.id desc ";
		if (StringUtils.isNotEmpty(key)) {
			if ("ready_status".equals(condition)) {
				if("未补单".equals(key)) {
					where += " AND IFNULL(add_quantity,0) = 0 ";
				} else if ("部分补单".equals(key)) {
					where += " AND quantity > add_quantity AND add_quantity > 0 ";
				}
				else {
					where += " AND quantity = add_quantity  ";
				}
			} else {
				where += " AND o." + condition + " like '%" + key + "%' ";
			}

		}
		modelPage = Db.paginate(pageIndex, pageSize, select, from + where + orderby);
		return modelPage;
	}

	public Page<Record> findJsonPage(Integer index, Integer pageSize, String map_no) {
		Page<Record> modelPage = null;
		String select = "select o.id,o.commodity_name,map_no,quantity,execu_status";
		String from = "  from fy_business_order o   ";
		String where = " where  execu_status = '备货' ";// customer_no = '备货'
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
			ret = Ret.ok("msg", "选择备货完成");
		} else {
			ret = Ret.fail("msg", "选择备货失败");
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
	 * 充值备货
	 */
	public Ret reset(Integer[] orderIds) {
		String update = " update fy_ready_add set ready_order_id = null,add_quantity = 0  where   add_order_id   in ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(orderIds, sb);
		logger.info(" 重置备货 ===> sql : " + update + sb.toString());
		int del = Db.update(update + sb.toString());
		if (del == orderIds.length) {
			return Ret.ok("msg", " 重置完成");
		} else {
			return Ret.fail("msg", " 重置 " + del + " 条");
		}
	}

	public Ret rollbackDistribut(String[] ids) {
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

				Db.delete("delete from fy_ready_add where add_order_id in " + idsb.toString());
				logger.debug(" 重置接收备货 sql==> " + update + idsb.toString() + "  \n sql ==> "
						+ "delete from fy_ready_add where add_order_id in " + idsb.toString());
				return re == ids.length;
			}
		});
		if (re) {

			return Ret.ok().set("msg", "重置备货完成");
		} else {
			return Ret.ok().set("msg", "重置失败，刷新之后再重置");
		}
	}
}
