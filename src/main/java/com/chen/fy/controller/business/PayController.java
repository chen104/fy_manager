package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class PayController extends BaseController {

	public void addpay() {
		Integer id = getParaToInt("id");
		FyBusinessAssist assist = FyBusinessAssist.dao.findFirst(
				"select a.*,s.name supplier_name from fy_business_assist a left join fy_base_supplier s on s.id = a.assist_supplier_id where a.id= ? ",
				id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(assist.getOrderId());
		setAttr("action", "save");
		setAttr("order", order);
		setAttr("assist", assist);
		render("add.html");
	}

	public void save() {
		FyBusinessPay model = getBean(FyBusinessPay.class, "model");
		model.setShouldPay(model.getWeiwaiCost().multiply(model.getRealInQuantity()));
		System.out.println(model);
		Ret ret = null;
		boolean re = model.save();
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
		renderJson(Ret.ok("msg", "新建信息"));
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyBusinessPay pay = FyBusinessPay.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(pay.getOrderId());
		setAttr("action", "update");
		setAttr("order", order);
		setAttr("model", pay);
		render("edit.html");
	}

	public void update() {
		Integer id = getParaToInt("model.id");
		FyBusinessPay pay = FyBusinessPay.dao.findById(id);
		String discount = getPara("discount");
		if (NumberUtils.isNumber(discount)) {
			pay.setDiscount(new BigDecimal(discount));
		}
		String qualityDeduction = getPara("qualityDeduction");
		if (NumberUtils.isNumber(qualityDeduction)) {
			pay.setQualityDeduction(new BigDecimal(qualityDeduction));
		}

		String payMonth = getPara("payMonth");
		if (NumberUtils.isNumber(payMonth)) {
			pay.setPayMonth(Integer.valueOf(payMonth));
		}

		boolean r = pay.update();
		Db.update(
				"update fy_business_pay set should_pay = IFNULL(should_pay,0) - IFNULL(discount,0) -   IFNULL(quality_deduction,0) where id = ? ",
				id);
		Ret ret = null;
		if (r) {
			ret = Ret.ok().set("msg", "更新完成");
		} else {
			ret = Ret.fail().set("msg", "更新失败");
		}
		renderJson(ret);
	}

	/**
	 * 更新下载状态
	 */
	public void updateDownload() {
		String id = getPara("id");
		int re = Db.update("update fy_business_pay set can_download = if(can_download = 0,1,0) where id = ?", id);
		Ret ret = null;
		if (re == 1) {
			ret = Ret.ok("msg", "更新下载状态完成");
		} else {
			ret = Ret.fail("msg", "更新失败");
		}
		renderJson(ret);
	}

	/**
	 * 生成应付明细单
	 */
	// fy/admin/biz/finance/pay
	public void inhouseAddPay() {
		Integer shoupaypireod = getParaToInt("shoupaypireod");
		Calendar calender = Calendar.getInstance();
		calender.setTimeInMillis(System.currentTimeMillis());
		int month = calender.get(Calendar.MONTH);
		String[] inhouseId = getParaValues("inhouseId");
		if (inhouseId == null || inhouseId.length == 0) {
			renderJson(Ret.fail("msg", "没有选择检测单"));
			return;
		}
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(inhouseId, sb);
		List<FyBusinessInWarehouse> list = FyBusinessInWarehouse.dao
				.find("select * from fy_business_in_warehouse where id in " + sb);
		Date d = new Date();
		for (FyBusinessInWarehouse e : list) {
			e.setIsCreatePay(true);
			e.setPayCreateTime(d);
		}
		List<FyBusinessPay> createList = new ArrayList<FyBusinessPay>();
		List<FyBusinessPurchase> updatePurchase = new ArrayList<FyBusinessPurchase>();
		for (int i = 0; i < inhouseId.length; i++) {
			FyBusinessInWarehouse item = list.get(i);
			List<FyBusinessPurchase> purchases = FyBusinessPurchase.dao
					.find("select * from fy_business_purchase where order_id = ?", item.getOrderId());
			FyBusinessOrder order = FyBusinessOrder.dao.findById(item.getOrderId());

			for (int j = 0; j < purchases.size(); j++) {
				FyBusinessPurchase purchase = purchases.get(j);
				FyBusinessPay pay = new FyBusinessPay();
				pay.setOrderId(item.getOrderId());
				pay.setParentId(purchase.getId());
				pay.setSupplierId(purchase.getSupplierId());// 厂商
				pay.setOrderNo(purchase.getPurchaseNo());// 订单编号
				pay.setCheckResult(item.getCheckResult());// 检测结果
				pay.setCheckTime(item.getCheckTime());
				pay.setWeiwaiQuantity(order.getQuantity());// 委外数量 ,
				pay.setWeiwaiCost(purchase.getPurchaseCost()); // 委外单价
				pay.setWeiwaiAccount(purchase.getPurchaseAccount()); // 委外金额
				pay.setInFrom("采购");
				pay.setInWarehouseTime(item.getInTime());// 为实际入库数
				pay.setRealInQuantity(item.getRealInQuantity());
				pay.setShouldPay(purchase.getPurchaseCost().multiply(item.getRealInQuantity()));// 应付实际金额
				pay.setCreateMonth(month);// 挂账期间
				pay.setPayMonth(shoupaypireod);

				purchase.setHangQuantity(purchase.getHangQuantity().add(pay.getRealInQuantity()));// 采购单 已挂账
				purchase.setUnhangQuantity(purchase.getUnhangQuantity().subtract(pay.getRealInQuantity())); // 采购未挂账
				if (purchase.getUnhangQuantity().doubleValue() > 0) {
					purchase.setHangStatus("部分挂账");
				} else {
					purchase.setHangStatus("已挂账");
				}
				updatePurchase.add(purchase);
				createList.add(pay);

			}

		}

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				Db.batchUpdate(list, list.size());
				Db.batchUpdate(updatePurchase, updatePurchase.size());
				Db.batchSave(createList, createList.size());

				return true;
			}
		});
		if (re) {
			renderJson(Ret.ok("msg", "生成成功"));
		} else {
			renderJson(Ret.fail("msg", "生成失败"));
		}
	}

}
