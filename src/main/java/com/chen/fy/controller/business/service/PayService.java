package com.chen.fy.controller.business.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.CommonKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class PayService {
	private static final Logger logger = LogManager.getLogger(PayService.class);
	public static final PayService me = new PayService();

	//
	public Ret rollback(Integer id) {
		Ret ret = null;
		Boolean re = FyBusinessPay.dao.deleteById(id);
		if (re) {
			ret = Ret.ok("msg", "撤回成功");
		} else {
			ret = Ret.ok("msg", "撤回失败");
		}
		return ret;
	}

	public Ret inhouseCreatePay(Integer id, Date now, Date payperiod, Integer loginID) {
		try {
			StringBuilder sb = new StringBuilder();
			FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
			model.setCreateTime(now);
			// model.setIsCreatePay(true);
			List<FyBusinessPay> createList = new ArrayList<FyBusinessPay>();
			List<FyBusinessPurchase> updatePurchase = new ArrayList<FyBusinessPurchase>();

			// model.setPayCreateTime(now);
			model.setPayMonth(payperiod);
			List<FyBusinessPurchase> purchases = FyBusinessPurchase.dao
					.find("select * from fy_business_purchase where order_id = ?", model.getOrderId());
			FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
			// BigDecimal wwUnquantity = order.getWwUnquantity();// 委外未挂账金额
			// BigDecimal wwBigDecimal = order.getWwQuantity();// 委外挂账金额
			Double hangAmont = 0d;
			// order.setWwQuantity(wwBigDecimal.add(model.getRealInQuantity()));
			// order.setWwUnhangAmount(wwUnquantity.subtract(model.getRealInQuantity()));
			for (int j = 0; j < purchases.size(); j++) {
				FyBusinessPurchase purchase = purchases.get(j);
				FyBusinessPay pay = new FyBusinessPay();
				pay.setOrderId(model.getOrderId());
				pay.setParentId(purchase.getId());
				pay.setSupplierId(purchase.getSupplierId());// 厂商
				pay.setOrderNo(purchase.getPurchaseNo());// 订单编号
				pay.setCheckResult(model.getCheckResult());// 检测结果

				pay.setCheckTime(model.getCheckTime());// 检测时间
				// pay.setWeiwaiQuantity(order.getQuantity());// 委外数量 ,
				pay.setWeiwaiCost(purchase.getPurchaseCost()); // 委外单价
				pay.setWeiwaiAccount(purchase.getPurchaseAccount()); // 委外金额
				pay.setInFrom("采购");
				pay.setInWarehouseTime(model.getInTime());// 为实际入库数
				// pay.setRealInQuantity(model.getRealInQuantity());
				BigDecimal PurchaseCost = purchase.getPurchaseCost();
				// BigDecimal InQuantity = model.getRealInQuantity();

				if (order.getQuantity().doubleValue() != pay.getRealInQuantity().doubleValue()) {
					// if (purchase.getDiscount() == null || purchase.getDiscount().doubleValue() ==
					// 0) {
					// pay.setDiscount(new BigDecimal("0"));// 折扣
					// } else {
					// Double discount = pay.getDiscount().doubleValue();// 采购折扣
					// Double tmp = (discount / purchase.getPurchaseQuantity().doubleValue());
					// discount = pay.getRealInQuantity().doubleValue() * tmp;
					// pay.setDiscount(new BigDecimal(discount));
					// }

				} else {
					// pay.setDiscount(purchase.getDiscount());

				}
				// pay.setShouldPay(PurchaseCost.multiply(InQuantity)
				// .subtract(pay.getDiscount() == null ? new BigDecimal("0") :
				// pay.getDiscount()));// 应付实际金额
				hangAmont += pay.getShouldPay().doubleValue();
				pay.setHangDate(now);
				pay.setCreateTime(now);
				pay.setCreateBy(loginID);
				pay.setPayDate(payperiod);

				pay.setInHouseId(model.getId());// 入库Id
				pay.setIsWw(true);// 来源是委外
				pay.setPurchaseDate(purchase.getPurchaseDate());

				// purchase.setHangQuantity(purchase.getHangQuantity().add(pay.getRealInQuantity()));//
				// 采购单 已挂账
				// purchase.setUnhangQuantity(purchase.getUnhangQuantity().subtract(pay.getRealInQuantity()));
				// // 采购未挂账
				if (purchase.getUnhangQuantity().doubleValue() > 0) {
					purchase.setHangStatus("部分挂账");
				} else {
					purchase.setHangStatus("已挂账");
				}
				updatePurchase.add(purchase);
				createList.add(pay);

			}
			BigDecimal tem = new BigDecimal(hangAmont);
			// order.setWwHangAmount(order.getWwHangAmount().add(tem));
			// order.setWwUnquantity(order.getWwUnhangAmount().subtract(tem));

			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {

					int[] row1 = Db.batchUpdate(updatePurchase, updatePurchase.size());
					int[] row2 = Db.batchSave(createList, createList.size());
					int tr1 = CommonKit.totalInt(row1);
					int tr2 = CommonKit.totalInt(row2);
					return order.update() && tr1 == updatePurchase.size() && createList.size() == tr2 && model.update();
				}
			});
			if (re) {
				return Ret.ok("msg", "生成成功");
			} else {
				return Ret.fail("msg", "生成失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return Ret.fail("msg", "生成失败");
	}
}
