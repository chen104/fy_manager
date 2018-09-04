package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class WarehouseController extends BaseController {
	private static final Logger logger = LogManager.getLogger(WarehouseController.class);

	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	/**
	 * 库存表，应为对应订单才有库存，库存和订单为同一张表
	 */
	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessInWarehouse> modelPage = null;
		keepPara("keyWord", "condition");
		String select = " select w.id wid ,in_time, real_in_quantity,in_from, check_create_time ,is_create_check,o.*,file.id  fileId ,file.originalFileName filename ";
		String from = " from  fy_business_in_warehouse w left join fy_business_order o on w.order_id = o.id"
				+ " LEFT JOIN fy_base_fyfile file on o.draw = file.id ";
		if (StringUtils.isEmpty(key)) {
			String where = " order by w.id desc ";
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10, select, from + where);

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("where o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			String where = sb.toString() + " order by w.id desc";
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10, select, from + where);

		}

		setAttr("modelPage", modelPage);
		render("inWarehouse.html");
	}

	/**
	 * 生成检测单
	 */
	public void toCheck() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);// 入库单
		Ret ret = null;
		if (model != null) {
			model.setIsCreateCheck(true);
			model.setCheckCreateTime(new Date());
			boolean re = model.update();
			if (re) {
				ret = Ret.ok("msg", "更新成功");

			} else {
				ret = Ret.ok("msg", "更新失败");
			}

		} else {
			ret = Ret.ok("msg", "更新失败");
		}
		renderJson(ret);
	}

	/**
	 * 检测单，检查对应入库，故入库和检测为同一单据
	 */
	public void checkIn() {
		String key = getPara("keyWord");
		if (StringUtils.isNotEmpty(key)) {
			key = key.trim();
		}
		Page<FyBusinessInWarehouse> modelPage = null;
		keepPara("keyWord", "condition");
		String select = " select w.id wid ,in_time, real_in_quantity,in_from, check_create_time ,is_create_check,check_time,check_quantity,check_result,check_handle,is_create_quality,is_create_can_out,create_out_time,is_create_pay,pay_create_time,o.* ,file.id  fileId ,file.originalFileName filename";
		String from = "from fy_business_in_warehouse w left join fy_business_order o on w.order_id = o.id "
				+ " LEFT JOIN fy_base_fyfile file on o.draw = file.id ";

		if (StringUtils.isEmpty(key)) {
			String where = " where is_create_check =1 order by w.id desc";
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10, select, from + where

			);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("and o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			String where = " where is_create_check =1 " + sb.toString() + " order by w.id desc";
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10, select, from + where);
		}
		setAttr("modelPage", modelPage);
		render("checkIn.html");
	}

	/**
	 * 显示检测输入界面
	 */
	public void editCheck() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
		Integer oid = model.getOrderId();
		FyBusinessOrder order = FyBusinessOrder.dao.findById(oid);
		setAttr("order", order);
		setAttr("model", model);
		render("checkEdit.html");
	}

	/**
	 * 输入检测结果
	 */
	public void checkInHouse() {
		Integer id = getParaToInt("id");// 入库单id
		String checkQuantity = getPara("checkQuantity");// 检测数量
		String checkHandle = getPara("checkHandle");// 检测处理
		String checkResult = getPara("checkResult");// 检测结果
		Date checkTime = getParaToDate("checkTime");// 检测时间
		FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);// 入库单
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());// 订单
		Ret ret = null;
		if (model != null) {
			model.setCheckTime(checkTime);
			model.setCheckQuantity(NumberUtils.isNumber(checkQuantity) ? new BigDecimal(checkQuantity) : null);
			model.setCheckResult(checkResult);
			model.setCheckHandle(checkHandle);
			if ("合格".equals(checkResult)) {
				BigDecimal storage = order.getStorageQuantity();
				if (storage == null) {
					order.setStorageQuantity(new BigDecimal(checkQuantity));
				} else {
					order.setStorageQuantity(storage.add(new BigDecimal(checkQuantity)));
				}

			}

			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {

					return model.update() && order.update();
				}
			});

			if (re) {
				ret = Ret.ok("msg", "更新成功");

			} else {
				ret = Ret.ok("msg", "更新失败");
			}

		} else {
			ret = Ret.ok("msg", "更新失败");
		}
		renderJson(ret);
	}

	/**
	 * 委外显示检测输入界面
	 */
	public void wweditCheck() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
		Integer oid = model.getOrderId();
		FyBusinessOrder order = FyBusinessOrder.dao.findById(oid);
		// FyBusinessPurchase purchase = FyBusinessPurchase.dao.find("select * from
		// where ")
		setAttr("order", order);
		setAttr("model", model);
		render("checkEdit.html");
	}

	public void storage() {
		String key = getPara("keyWord");
		Page<FyBusinessInWarehouse> modelPage = null;
		keepPara("keyWord", "condition");
		// select w.id wid ,in_time, real_in_quantity,in_from, check_create_time
		// ,is_create_check,check_time,check_quantity,check_result,check_handle,is_create_quality,is_create_can_out,create_out_time,is_create_pay,pay_create_time,o.*

		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where storage_quantity > 0  order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("and o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where storage_quantity > 0 " + sb.toString() + " order by id desc");
		}
		// from fy_business_in_warehouse w left join fy_business_order o on w.order_id =
		// o.id where is_create_check =1 and check_result='合格' order by w.id desc

		setAttr("modelPage", modelPage);
		render("storage.html");
	}

	public void toGetPay() {
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(getParaToInt("id"));
		model.setIsCreateGetPay(true);

		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "应收明细生成成功");
		} else {
			ret = Ret.fail("msg", "应收明细生成失败");
		}
		renderJson(ret);

	}

	/**
	 * 入库撤回
	 */
	public void rollbackInWarehouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		Double quantity = order.getHasInQuantity().doubleValue() - model.getRealInQuantity().doubleValue();
		order.setHasInQuantity(new BigDecimal(quantity));
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				// 更新未挂账金额
				boolean r = model.delete() && order.update();// 删除入库

				return r;
			}
		});
		if (re) {
			renderJson(Ret.ok().set("msg", "撤回入库完成"));
		} else {
			renderJson(Ret.ok().set("msg", "撤回失败 失败"));
		}

		// if (model.getIsCreateCheck() == null || !model.getIsCreateCheck()) {
		//
		// return;
		// }
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		// if (model.getIsCreateCheck() && "合格".equals(model.getCheckResult())) {
		//
		// // Double storage = order.getStorageQuantity().doubleValue();
		// // if (storage < model.getRealInQuantity().doubleValue()) {
		// // renderJson(Ret.fail().set("msg", "库存不足，不允许撤回"));
		// // return;
		// // } else {
		// // storage = order.getStorageQuantity().doubleValue() -
		// // model.getRealInQuantity().doubleValue();
		// // order.setStorageQuantity(new BigDecimal(storage));
		// // }
		// renderJson(Ret.fail().set("msg", "已检测合格,"));
		// return;
		// }
		/*
		 * Double inquantity = order.getHasInQuantity().doubleValue() -
		 * model.getRealInQuantity().doubleValue(); order.setHasInQuantity(new
		 * BigDecimal(inquantity));
		 * 
		 * // 删除应付单
		 * 
		 * boolean re = Db.tx(new IAtom() { public boolean run() throws SQLException {
		 * // 删除应收单
		 * 
		 * boolean b2 = model.delete() && order.update(); return b2; } }); // 更新未挂账金额
		 * String sql = Db.getSql("order.updateWWhang"); Db.update(sql, id); Ret ret =
		 * null; if (re) { ret = Ret.ok("msg", "撤回成功");
		 * 
		 * } else { ret = Ret.fail("msg", "撤回失败"); } renderJson(ret);
		 */
	}
}
