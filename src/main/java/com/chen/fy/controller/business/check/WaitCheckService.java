package com.chen.fy.controller.business.check;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPurchase;
import com.chen.fy.model.FyCheckCollect;
import com.chen.fy.model.FyExceptionRecord;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class WaitCheckService {
	public final static WaitCheckService me = new WaitCheckService();
	private static final Logger logger = LogManager.getLogger(WaitCheckService.class);
	String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id, \n"
			+ "				o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name \n "
			+ ",inhouse.inhouse_quantity in_quantity,inhouse.id inhouse_id \n";
	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String from = " from  fy_business_order o  \n"
				+ " INNER JOIN fy_check_collect inhouse on o.id = inhouse.order_id  \n"
				+ " left join fy_base_fyfile  f on o.draw = f.id \n "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id  \n "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no  \n ";
		String where = " where inhouse.inhouse_quantity > 0 \n   ";

		String desc = " order by inhouse.id  desc  \n ";

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

		String from = " from  fy_business_order o  "
				+ " INNER JOIN fy_check_collect inhouse on o.id = inhouse.order_id "
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
		 = getPara("model.pass_quantity");
		 = getPara("model.check_result");
		 = getParaValues("exception_reson");
		 = getPara("model.check_remark");
	 * @return
	 */
	public synchronized Ret checkInhouse(String modelId, String pass_quantity, String check_result,
			String[] exception_reson, String remark) throws Exception {
		Integer passQuantity = 0;
		try {

			passQuantity = Integer.valueOf(pass_quantity);
		} catch (Exception e) {
			return Ret.fail().set("msg", "通过数量不能为非数字");
		}
		FyCheckCollect model = FyCheckCollect.dao.findById(Integer.valueOf(modelId));
		if (passQuantity > model.getInhouseQuantity()) {
			return Ret.fail().set("msg", "通过数量不能超过入库数量");
		}

		model.setCheckResult(check_result);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		Integer unpassQuantity = model.getInhouseQuantity() - passQuantity;
		/*
		if (unpassQuantity > 0) {
			Integer oldunpass = model.getUnpassQuantity() == null ? 0 : model.getUnpassQuantity();
			if (passQuantity < oldunpass) {
				model.setUnpassQuantity(unpassQuantity + (oldunpass - passQuantity));// 把未通过的求和
			} else {
				model.setUnpassQuantity(unpassQuantity);// 把未通过的求和
			}
		}
		*/
		model.setUnpassQuantity(unpassQuantity);
		Integer storage = order.getStorageQuantity() == null ? 0 : order.getStorageQuantity();
		storage += passQuantity;// 库存
		order.setStorageQuantity(storage);

		Date checkDate = new Date();
		model.setCheckTime(checkDate);
		// order.setInhouseDate(checkDate);// 入库时间 检测合格即入库

		Integer odlpass = model.getPassQuantity() == null ? 0 : model.getPassQuantity();
		model.setPassQuantity(passQuantity + odlpass);// 设置

		String inform = model.getInFrom();
		model.setInhouseQuantity(0);
		if ("本部".equals(inform)) {
			boolean  re = 	Db.tx(new IAtom() {
				
				@Override
				public boolean run() throws SQLException {
					if (unpassQuantity > 0) {// 不合格的需要记录
						FyExceptionRecord exceptionRecord = new FyExceptionRecord();
						exceptionRecord.setOrderId(model.getOrderId());
						exceptionRecord.setCheckRemark(remark);
						exceptionRecord.setExceptionReson(StringUtils.join(exception_reson, ","));
						exceptionRecord.setCheckTime(checkDate);
						exceptionRecord.setExceptionQuantity(unpassQuantity);
						exceptionRecord.save();
					}
					Integer hasInQuantity = model.getPassQuantity();
					order.setHasInQuantity(hasInQuantity);
					return order.update()&&model.update();
				}
			});
		 
			if(re) {
				return Ret.ok().set("msg", "检测完成");
			}
			return Ret.fail().set("msg", "检测失败");
			
		}

		if (passQuantity > 0) {
			final FyBusinessPay pay = new FyBusinessPay();
			String findPurches = String.format(
					" select p.*,s.settlement_cycle from fy_business_purchase p \n"
							+ "Left join fy_base_supplier s on  p.supplier_id = s.id \n" + "where p.order_id = %s ",
					model.getOrderId());

			logger.debug(" 检测委外 查找 采购单单 sql = " + findPurches);
			FyBusinessPurchase purchase = FyBusinessPurchase.dao.findFirst(findPurches);
			if (purchase.getSupplierId() == null) {
				return Ret.fail().set("msg", "厂商不能为空");
			}
			pay.setOrderId(model.getOrderId());// 订单id
			pay.setIsPurchase(true);// 委外采购
			pay.setPurchaseDate(purchase.getPurchaseDate());
			pay.setPayQuantity(passQuantity);// 通过数量，结算数量
			pay.setCheckResult(check_result);// 检测结果
			pay.setCheckTime(model.getCheckTime());// 检测s时间
			pay.setInWarehouseTime(model.getCheckTime());// 入库时间
			pay.setSupplierId(purchase.getSupplierId());// 厂商
			pay.setPurchaseName(order.getCommodityName());// 采购名称
			pay.setPurchaseQuantity(purchase.getPurchaseQuantity());// 采购数量
			pay.setPurchaseCost(purchase.getPurchaseCost());// 采购单价
			pay.setPurchaseNo(purchase.getPurchaseNo());// 采购编号
			pay.setPurchaseAmount(purchase.getPurchaseAccount());// 采购总价
			pay.setPurchaseDate(purchase.getPurchaseDate());// 采购时间
			if (pay.getPurchaseCost() == null) {
				pay.setPurchaseCost(new BigDecimal(0));
			}
			if (pay.getPayQuantity() == null) {
				pay.setPayQuantity(0);
			}
			pay.setShouldPay(pay.getPurchaseCost().multiply(new BigDecimal(pay.getPayQuantity())));
			pay.setInFrom("采购");
			pay.setHangDate(pay.getCheckTime());// 挂账时间
			Integer settlement_cycle = purchase.getInt("settlement_cycle");// 结算周期
			if (settlement_cycle == null) {
				return Ret.fail().set("msg", "供应商没有设置付款方式");
			}
			pay.setUnpassQuantity(model.getUnpassQuantity());
			if (settlement_cycle == 1) {// 月结30天
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(pay.getHangDate());
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				if (day > 25) {
					calendar.add(Calendar.MONTH, 2);
				} else {
					calendar.add(Calendar.MONTH, 1);
				}
				pay.setPayDate(calendar.getTime());
			} else if (settlement_cycle == 2) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(pay.getHangDate());
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				if (day > 25) {
					calendar.add(Calendar.MONTH, 3);
				} else {
					calendar.add(Calendar.MONTH, 2);
				}
				pay.setPayDate(calendar.getTime());
			} else if (settlement_cycle == 3) {
				pay.setPayDate(pay.getHangDate());
			}

			pay.setParentId(model.getId());// 上有单据 ,入库单id

			boolean re = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					Boolean r = null;

					if (unpassQuantity > 0) {// 不合格的需要记录
						FyExceptionRecord exceptionRecord = new FyExceptionRecord();
						exceptionRecord.setOrderId(model.getOrderId());
						exceptionRecord.setCheckRemark(remark);
						exceptionRecord.setExceptionReson(StringUtils.join(exception_reson, ","));
						exceptionRecord.setCheckTime(checkDate);
						exceptionRecord.setSupplierId(purchase.getSupplierId());
						exceptionRecord.setExceptionQuantity(unpassQuantity);
						exceptionRecord.save();
					}
					if (pay.getId() == null) {
						r = pay.save();
					} else {
						r = pay.update();
					}
					Integer hasInQuantity = model.getPassQuantity();
					order.setHasInQuantity(hasInQuantity);
					return order.update() && model.update() && r;
				}
			});

			if (re) {
				return Ret.ok().set("msg", "检测完成");
			} else {
				return Ret.fail().set("msg", "检测失败，刷新页面在重试一下");
			}
		} else {
			boolean re = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {

					if (unpassQuantity > 0) {// 不合格的需要记录
						FyExceptionRecord exceptionRecord = new FyExceptionRecord();
						exceptionRecord.setOrderId(model.getOrderId());
						exceptionRecord.setCheckRemark(remark);
						exceptionRecord.setExceptionReson(StringUtils.join(exception_reson, ","));
						exceptionRecord.setCheckTime(checkDate);
						exceptionRecord.setExceptionQuantity(unpassQuantity);
						Integer hasInQuantity = model.getPassQuantity();
						order.setHasInQuantity(hasInQuantity);
						exceptionRecord.save();
					}

					return order.update() && model.update();
				}
			});
			if (re) {
				return Ret.ok().set("msg", "检测完成");
			} else {

				return Ret.fail().set("msg", "检测失败");
			}
		}

	}

	/**
	 * 待检测撤回到待入库
	 */
	public Ret rollback(String[] checkId) {
		Ret ret = null;
		String ids = SqlKit.getIds(checkId);
		String rollback = Db.getSql("check.rollbackWaitCheck");
		boolean re = Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				String sql = String.format(rollback, ids);
				int ret = Db.update(sql);
				return (ret / 2) == checkId.length;
			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "撤回完成 ");
		} else {
			ret = Ret.ok().set("msg", "撤回失败，刷新之后再撤回 ");
		}
		return ret;
	}

}
