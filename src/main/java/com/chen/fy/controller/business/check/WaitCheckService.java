package com.chen.fy.controller.business.check;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPurchase;
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
		String from = " from  fy_business_order o  "
				+ " INNER JOIN fy_business_in_warehouse inhouse on o.id = inhouse.order_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id  "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no ";
		String where = " where inhouse.check_result is null  ";

		String desc = " order by inhouse.id  desc ";

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
				} else if (StringUtils.isNotEmpty(keyword)) {

					conditionSb.append(String.format(" AND  %s like  ", condition));
					conditionSb.append("'%").append(keyword).append("%'");

				}
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
		Integer pass_quantity = model.getPassQuantity();
		if (pass_quantity == null || pass_quantity == 0) {
			return Ret.fail().set("msg", "通过数量不能为空或为0");
		}

		FyBusinessInWarehouse old = FyBusinessInWarehouse.dao.findById(model.getId());
		Integer in_quantity = old.getInQuantity();
		if (pass_quantity > in_quantity) {
			return Ret.fail().set("msg", "通过数量不能超过");
		}

		Integer order_id = old.getOrderId();
		FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id);

		Integer difference = old.getInQuantity() - (model.getPassQuantity() == null ? 0 : model.getPassQuantity());
		Integer hasin = order.getHasInQuantity() - difference;
		order.setHasInQuantity(hasin);
		Integer storage = order.getStorageQuantity() == null ? 0 : order.getStorageQuantity();
		storage += model.getPassQuantity();
		order.setStorageQuantity(storage);
		Date checkDate = new Date();
		model.setCheckTime(checkDate);
		order.setInhouseDate(checkDate);// 入库时间 检测合格即入库

		Integer unpass = old.getInQuantity() - model.getPassQuantity();
		model.setUnpassQuantity(unpass);

		FyBusinessPay oldpay = FyBusinessPay.dao
				.findFirst("select * from fy_business_pay where parent_id =  " + model.getId());

		final FyBusinessPay pay = new FyBusinessPay();
		if (oldpay != null) {
			pay._setAttrs(oldpay);
		}
		pay.setOrderId(order_id);// 订单id
		pay.setIsPurchase(true);// 委外采购
		pay.setPurchaseDate(model.getDate(""));
		pay.setPayQuantity(model.getPassQuantity());// 通过数量，结算数量
		pay.setCheckResult(model.getCheckResult());// 检测结果
		pay.setCheckTime(model.getCheckTime());// 检测s时间
		pay.setInWarehouseTime(model.getCheckTime());// 入库时间

		FyBusinessPurchase purchase = FyBusinessPurchase.dao
				.findFirst("select * from fy_business_purchase where order_id = " + order_id);

		pay.setSupplierId(purchase.getSupplierId());// 厂商
		pay.setPurchaseName(order.getCommodityName());// 采购名称
		pay.setPurchaseQuantity(purchase.getPurchaseQuantity());// 采购数量
		pay.setPurchaseCost(purchase.getPurchaseCost());// 采购单价
		pay.setPurchaseNo(purchase.getPurchaseNo());// 采购编号
		pay.setPurchaseAmount(purchase.getPurchaseAccount());// 采购总价
		pay.setPurchaseDate(purchase.getPurchaseDate());// 采购时间

		pay.setShouldPay(pay.getPurchaseCost().multiply(new BigDecimal(pay.getPayQuantity())));
		pay.setInFrom("采购");
		pay.setHangDate(pay.getCheckTime());// 挂账时间
		Calendar calender = Calendar.getInstance();
		calender.setTime(pay.getCheckTime());// 当前时间
		calender.add(Calendar.MONTH, 2);// 相隔2个月
		pay.setPayDate(calender.getTime());// 应付期间
		pay.setParentId(model.getId());// 上有单据 ,入库单id
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				Boolean r = null;
				if (pay.getId() == null) {
					r = pay.save();
				} else {
					r = pay.update();
				}

				return order.update() && model.update() && r;
			}
		});

		if (re) {
			return Ret.ok().set("msg", "检测完成");
		} else {
			return Ret.fail().set("msg", "检测失败，刷新页面在重试一下");
		}

	}

}
