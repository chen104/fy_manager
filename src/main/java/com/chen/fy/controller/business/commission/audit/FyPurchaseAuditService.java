package com.chen.fy.controller.business.commission.audit;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessPurchase;
import com.chen.fy.model.OrderUploadLog;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class FyPurchaseAuditService {
	private static final Logger logger = LogManager.getLogger(FyPurchaseAuditService.class);
	private static final FyBusinessPurchase dao = new FyBusinessPurchase().dao();
	public final static FyPurchaseAuditService me = new FyPurchaseAuditService();

	public boolean update(FyBusinessPurchase model) {
		return model.update();
	}

	public boolean delete(FyBusinessPurchase model) {
		return model.delete();
	}

	public boolean deleteById(Object id) {
		return dao.deleteById(id);
	}

	public Page<FyBusinessPurchase> paginate(int pageNumber, int pageSize, String condition, String keyWord) {
		logger.info("");

		String select = " SELECT o.*,audit.id audit_id ,purchase_single_weight,purchase_weight,purchase_delivery_date,purchase_cost,purchase_account,\r\n"
				+ "audit_status,audit_time,audit.supplier_no supplier_no,s.name supplier_name ,is_has_tax \n";
		String from = "from fy_business_purchase audit  \n "
				+ " LEFT JOIN fy_business_order o on audit.work_order_no = o.work_order_no \n"
				+ " LEFT JOIN  fy_base_supplier s on audit.supplier_no = s.supplier_no  \n";

		String desc = " order by audit.id desc ";
		String where = "  where add_status = 1 ";

		Page<FyBusinessPurchase> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		} else {
			if (StringUtils.isNotEmpty(keyWord)) {
				if ("order_date".equals(condition)) {

					conditionSb.append(String.format("AND order_date = '%s'", keyWord));

				} else if ("delivery_date".equals(condition)) {

					conditionSb.append(String.format("AND  delivery_date = '%s'", keyWord));

				} else if ("work_order_no".equals(condition)) {
					conditionSb.append(String.format("AND  o.%s like  ", condition));
					conditionSb.append("'%").append(keyWord).append("%'");

				} else if ("supplier_name".equals(condition)) {
					conditionSb.append(" AND  s.name like  ");
					conditionSb.append("'%").append(keyWord).append("%'");

				}

				else {

					conditionSb.append(String.format("AND  %s like  ", condition));
					conditionSb.append("'%").append(keyWord).append("%'");

				}
			}

		}
		where = where + conditionSb.toString();
		modelPage = dao.paginate(pageNumber, pageSize, select, from + where + desc);
		return modelPage;

	}

	public FyBusinessPurchase findById(Object id) {
		return dao.findById(id);
	}

	/**
	 * 上传报目表
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public Ret uploadRuqest(File file) throws Exception {

		PIOExcelUtil excel = new PIOExcelUtil(file, 0);
		// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
		// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
		List<Record> list = new ArrayList<Record>();
		int rows = excel.getRowNum() + 1;
		ArrayList<String> workorderno = new ArrayList<String>();

		for (int i = 1; i < rows; i++) {

			FyBusinessPurchase item = new FyBusinessPurchase();

			String work_no = excel.getCellVal(i, 1);
			item.setWorkOrderNo(work_no);
			if (StringUtils.isEmpty(work_no)) {
				continue;
			}

			workorderno.add(work_no);

			String purchase_quantity = excel.getCellVal(i, 5);// 采购数量
			item.setPurchaseQuantity(
					NumberUtils.isNumber(purchase_quantity) ? new BigDecimal(purchase_quantity) : new BigDecimal(0));

			String purchaseSingleWeight = excel.getCellVal(i, 7);// 单件
			item.setPurchaseSingleWeight(
					NumberUtils.isNumber(purchaseSingleWeight) ? new BigDecimal(purchaseSingleWeight) : null);

			String purchaseWeight = excel.getCellVal(i, 8);
			item.setPurchaseWeight(NumberUtils.isNumber(purchaseWeight) ? new BigDecimal(purchaseWeight) : null);

			String supplierNo = excel.getCellVal(i, 9);
			item.setSupplierNo(supplierNo);

			// String supplier_name = excel.getCellVal(i, 9);
			// item.setSupplierName(supplier_name);

			String cost = excel.getCellVal(i, 11);
			item.setPurchaseCost(NumberUtils.isNumber(cost) ? new BigDecimal(cost) : null);

			String amount = excel.getCellVal(i, 12);
			item.setPurchaseAccount(NumberUtils.isNumber(amount) ? new BigDecimal(amount) : null);

			String ishasTax = excel.getCellVal(i, 13);// 含税/未含税
			item.setIsHasTax(ishasTax);

			Date purchaseDeliveryDate = excel.getDateValue(i, 14);// 交货日期
			item.setPurchaseDeliveryDate(purchaseDeliveryDate);
			item.setAuditStatus("未审核");

			list.add(new Record().setColumns(item));

		}

		/**
		 * 删除已有的价格的单据,addstate是在执行单情况下，
		 */
		StringBuilder delete = new StringBuilder(
				" delete from fy_business_purchase where  add_status = 0 AND work_order_no in  ");

		SqlKit.joinIds(workorderno, delete);

		int nul = Db.delete(delete.toString());
		System.out.println(delete.toString() + " 删除 " + nul);

		StringBuilder worksb = new StringBuilder();
		SqlKit.joinIds(workorderno, worksb);

		/*
		 * addstatus 不唯一，已审核的和已采购，不可改变
		 */
		List<Record> work = Db
				.find(" select work_order_no from  fy_business_purchase where  add_status <>  1 AND work_order_no in  "
				+ worksb.toString());
		List<String> haswor = new ArrayList<String>();

		for (Record e : work) {
			haswor.add(e.get("work_order_no"));
		}

		if (haswor.size() > 0) {
			return Ret.fail().set("msg", "工作订单号重复 " + StringUtils.join(haswor, ","));
		}


		int[] re = Db.batchSave("fy_business_purchase", list, 20);
		// 更新采购单 厂商id
		Db.update(" update fy_business_purchase p INNER JOIN fy_base_supplier s on p.supplier_no = s.supplier_no\r\n"
				+ "SET p.supplier_id = s.id\r\n" + " where p.supplier_id is null\r\n" + " ");

		Db.update("update fy_business_purchase p INNER JOIN\r\n"
				+ "  fy_business_order o on o.work_order_no = p.work_order_no \r\n"
				+ " set p.order_id = o.id where  p.order_id is null ");
		int total = 0;
		for (int i = 0; i < re.length; i++) {
			total = total + re[i];
		}
		OrderUploadLog log = new OrderUploadLog();
		log.setSucess(total);
		log.save();

		return Ret.ok().set("msg", " 更新记录 " + total + " 条");

	}

	/**
	 * 采购计划审核
	 * @param ids
	 * @param audit_status
	 * @return
	 * @throws Exception
	 */
	public Ret audit(String[] ids, String audit_status) throws Exception {
		final StringBuilder sql = new StringBuilder();
		boolean ret = false;
		if ("审核通过".equals(audit_status)) {

			/**
			 * 更新审核状态
			 */
			// sql.append(" update fy_business_purchase set audit_status =
			// '").append(audit_status)
			// .append("' , audit_time = now() where id in ");
			sql.append(
					"update fy_business_order o INNER JOIN fy_business_purchase p on p.work_order_no = o.work_order_no\r\n"
							+ "set o.plan_time =NOW(),p.order_id=o.id,p.purchase_date=NOW(),o.order_status= 4,p.add_status= 3 , audit_status = '审核通过' \r\n"
							+ "where p.id in ");
			SqlKit.joinIds(ids, sql);

			ret = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					int re = Db.update(sql.toString());
					return ids.length * 2 == re;
				}
			});

		} else {
			sql.append(
					"update  fy_business_order o INNER JOIN  fy_business_purchase a ON a.work_order_no = o.work_order_no set o.order_status = 2  ,a.add_status=0 ,audit_time = now(),a.audit_status='审核不通过'  \r\n"
							+ "where a.id in ");
			SqlKit.joinIds(ids, sql);
			ret = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {
					int re = Db.update(sql.toString());
					return ids.length * 2 == re;
				}
			});

		}
		if (ret) {

			return Ret.ok().set("msg", "审核采购计划完成");
		} else {
			return Ret.ok().set("msg", "审核采购计划失败");
		}

	}

	/**
	 * 更新审核单扩展状态位,不是审核状态,add_status =1 为审核单
	 * @param auditId
	 * @param status
	 * @return
	 */
	public Ret updateAddStatus(String auditId, String status, String orderId, String order_status) throws Exception {
		final String sql = String.format(" update fy_business_purchase set add_status = %s where id = %s", status,
				auditId);
		String orderSql = String.format(" update fy_business_order set order_status = %s where id = %s ", order_status,
				orderId);
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int num = Db.update(sql);
				int num1 = Db.update(orderSql);
				return (num == 1 && num == num1);
			}
		});

		if (re) {
			return Ret.ok().set("msg", "生成审核单成功");
		} else {
			return Ret.fail().set("msg", "生成审核单失败");
		}
	}

	/**
	 * 批量更新订单与采购审核单,设置订单 order_status =3 ,采购审核单 add_status=1
	 * @param orderids
	 * @return
	 */
	public Ret batchUpdateStatus(String[] orderids) {
		StringBuilder sql = new StringBuilder(
				"update  fy_business_order o INNER JOIN  fy_business_purchase a ON a.work_order_no = o.work_order_no set o.order_status = 3  ,a.add_status=1,a.audit_status='未审核' ,a.audit_time = null  \r\n"
						+ "where o.id in ");
		SqlKit.joinIds(orderids, sql);
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int num = Db.update(sql.toString());

				return ((num / 2) == orderids.length);
			}
		});
		if (re) {
			return Ret.ok().set("msg", "生成审核单成功");
		} else {
			return Ret.fail().set("msg", "生成审核单失败");
		}

	}
}
