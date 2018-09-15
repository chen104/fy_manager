package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.controller.business.service.PayService;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class PayController extends BaseController {
	private static final Logger logger = LogManager.getLogger(PayController.class);

	public void indext() {
		pay();
	}

	/**
	 * 应付明细表
	 */
	public void pay() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		Page<FyBusinessPay> modelPage = null;
		setAttr("keyWord", key);
		keepPara("condition");
		Integer pageSize = getParaToInt("pageSize", 10);
		setAttr("pageSize", pageSize);
		setAttr("append", "&pageSize=" + pageSize);
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status";
		String select = "select p.*, s.name supplier_name ," + sql;
		String from = "from  fy_business_pay p left join fy_business_order o on o.id= p.order_id  left join fy_base_supplier s on  p.supplier_id = s.id ";
		String desc = " order by id desc";
		StringBuilder where = new StringBuilder();
		if (StringUtils.isNotEmpty(key)) {
			String condition = getPara("condition");
			if ("hang_date".equals(condition)) {
				Date date = null;
				try {
					date = DateUtils.parseDate(key, "yyyy-MM");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.DATE, -1);
					String start = DateFormatUtils.format(calendar, "yyyy-MM-dd");
					calendar.add(Calendar.DATE, 1);
					calendar.add(Calendar.MONTH, 1);
					String end = DateFormatUtils.format(calendar, "yyyy-MM-dd");
					where.append("where p.hang_date > '").append(start).append("' and p.hang_date < '").append(end)
							.append("' ");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				where.append("where o.").append(condition).append(" like '%").append(key).append("%' ");
			}

		}
		modelPage = FyBusinessPay.dao.paginate(getParaToInt("p", 1), pageSize, select, from + where.toString() + desc);

		setAttr("modelPage", modelPage);
		Double should_pay = 0d;
		Double weiwai_account = 0d;
		for (FyBusinessPay e : modelPage.getList()) {
			if (e.getShouldPay() != null) {
				should_pay += e.getShouldPay().doubleValue();
			}
			if (e.getWeiwaiAccount() != null) {
				weiwai_account += e.getWeiwaiAccount().doubleValue();
			}
		}

		setAttr("should_pay", should_pay);
		setAttr("weiwai_account", weiwai_account);
		render("pay.html");
	}

	public void todownload() {
		setAttr("new", new Date());
		render("download.html");
	}

	// 外协加工单，生成应付单
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

	/*
	 * 新建应付单
	 */
	public void save() {
		FyBusinessPay model = getBean(FyBusinessPay.class, "model");

		// ge
		String hang_date = getPara("hang_date");
		String pay_date = getPara("pay_date");
		try {
			if (StringUtils.isNotEmpty(hang_date)) {

				model.setHangDate(DateUtils.parseDate(hang_date, "yyyy-MM"));

			}
			if (StringUtils.isNotEmpty(pay_date)) {

				model.setPayDate(DateUtils.parseDate(pay_date, "yyyy-MM"));

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setShouldPay(model.getWeiwaiCost().multiply(model.getRealInQuantity()));
		System.out.println(model);
		Ret ret = null;
		boolean re = model.save();
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		// 更新挂账数量与挂账金额
		renderJson(ret);
		renderJson(Ret.ok("msg", "新建信息"));
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyBusinessPay pay = FyBusinessPay.dao
				.findFirst("select  p.*,s.name supplier_name from  fy_business_pay p LEFT JOIN \r\n"
						+ "fy_base_supplier s on s.id = p.supplier_id where p.id = ?", id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(pay.getOrderId());
		setAttr("action", "update");
		setAttr("order", order);
		setAttr("model", pay);
		render("edit.html");
	}

	public void update() {
		// Integer id = getParaToInt("model.id");
		// FyBusinessPay pay = FyBusinessPay.dao.findById(id);
		// String discount = getPara("discount");
		// if (NumberUtils.isNumber(discount)) {
		// pay.setDiscount(new BigDecimal(discount));
		// }
		// String qualityDeduction = getPara("qualityDeduction");
		// if (NumberUtils.isNumber(qualityDeduction)) {
		// pay.setQualityDeduction(new BigDecimal(qualityDeduction));
		// }
		//
		// String payMonth = getPara("payMonth");
		// if (NumberUtils.isNumber(payMonth)) {
		// pay.setPayMonth(Integer.valueOf(payMonth));
		// }
		// pay_date
		// create_date
		FyBusinessPay pay = getModel(FyBusinessPay.class, "model");

		String hang_date = getPara("hang_date");
		String pay_date = getPara("pay_date");
		try {
			if (StringUtils.isNotEmpty(hang_date)) {

				pay.setHangDate(DateUtils.parseDate(hang_date, "yyyy-MM"));

			}
			if (StringUtils.isNotEmpty(pay_date)) {

				pay.setPayDate(DateUtils.parseDate(pay_date, "yyyy-MM"));

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean r = pay.update();
		// 更新应付金额
		Db.update(
				"update fy_business_pay set should_pay = IFNULL(weiwai_account,0) - IFNULL(discount,0) -   IFNULL(quality_deduction,0) \r\n"
						+ "where id = ? ",
				pay.getId());
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
	 * @throws ParseException 
	 */
	// fy/admin/biz/finance/pay
	@Deprecated
	public void inhouseAddPay() throws ParseException {
		// Integer shoupaypireod = getParaToInt("shoupaypireod");
		String datestr = getPara("shoupaypireod");
		if (StringUtils.isEmpty(datestr)) {
			renderJson(Ret.fail().set("msg", "没有选择应付期间"));
			return;
		}
		Date shoupaypireod = DateUtils.parseDate(datestr, "yyyy-MM");
		Date now = new Date();

		if (shoupaypireod.before(now)) {
			renderJson(Ret.fail().set("msg", "应付期间不能是现在以前"));
			return;
		}
		String nows = DateFormatUtils.format(now, "yyyy-MM-00");
		now = DateUtils.parseDate(nows, "yyyy-MM-00");

		String[] inhouseId = getParaValues("inhouseId");
		if (inhouseId == null || inhouseId.length == 0) {
			renderJson(Ret.fail("msg", "没有选择检测单"));
			return;
		}
		try {
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
				item.setPayCreateTime(now);
				item.setPayMonth(shoupaypireod);
				List<FyBusinessPurchase> purchases = FyBusinessPurchase.dao
						.find("select * from fy_business_purchase where order_id = ?", item.getOrderId());
				FyBusinessOrder order = FyBusinessOrder.dao.findById(item.getOrderId());
				// BigDecimal wwUnquantity = order.getWwUnquantity();// 委外未挂账金额
				// BigDecimal wwBigDecimal = order.getWwQuantity();// 委外挂账金额
				// order.setWwQuantity(wwBigDecimal.add(item.getRealInQuantity()));
				// order.setWwUnhangAmount(wwUnquantity.subtract(item.getRealInQuantity()));

				// order.setUnhangQuantity(unhangQuantity);

				for (int j = 0; j < purchases.size(); j++) {
					FyBusinessPurchase purchase = purchases.get(j);
					FyBusinessPay pay = new FyBusinessPay();
					pay.setOrderId(item.getOrderId());
					pay.setParentId(purchase.getId());
					pay.setSupplierId(purchase.getSupplierId());// 厂商
					pay.setOrderNo(purchase.getPurchaseNo());// 订单编号
					pay.setCheckResult(item.getCheckResult());// 检测结果
					pay.setCheckTime(item.getCheckTime());// 检测时间
					// pay.setWeiwaiQuantity(order.getQuantity());// 委外数量 ,
					pay.setWeiwaiCost(purchase.getPurchaseCost()); // 委外单价
					pay.setWeiwaiAccount(purchase.getPurchaseAccount()); // 委外金额
					pay.setInFrom("采购");
					pay.setInWarehouseTime(item.getInTime());// 为实际入库数
					// pay.setRealInQuantity(item.getRealInQuantity());
					BigDecimal PurchaseCost = purchase.getPurchaseCost();
					// BigDecimal InQuantity = item.getRealInQuantity();

					// if (order.getQuantity().doubleValue() !=
					// pay.getRealInQuantity().doubleValue()) {
					// if (purchase.getDiscount() == null || purchase.getDiscount().doubleValue() ==
					// 0) {
					// pay.setDiscount(new BigDecimal("0"));// 折扣
					// } else {
					// Double discount = pay.getDiscount().doubleValue();// 采购折扣
					// Double tmp = (discount / purchase.getPurchaseQuantity().doubleValue());
					// discount = pay.getRealInQuantity().doubleValue() * tmp;
					// pay.setDiscount(new BigDecimal(discount));
					// }
					//
					// } else {
					// pay.setDiscount(purchase.getDiscount());
					//
					// }
					// pay.setShouldPay(PurchaseCost.multiply(InQuantity)
					// .subtract(pay.getDiscount() == null ? new BigDecimal("0") :
					// pay.getDiscount()));// 应付实际金额
					pay.setHangDate(now);
					pay.setCreateTime(now);
					pay.setCreateBy(getLoginAccountId());
					pay.setPayDate(shoupaypireod);

					pay.setInHouseId(item.getId());// 入库Id
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

			}

			// 更新挂账
			//
			// String updateSql = "update fy_business_order o LEFT JOIN (SELECT
			// SUM(tatol_amount) tatol_amount,order_id from fy_business_assist where
			// order_id in "
			// + sb.toString() + " GROUP BY order_id) assist " + "on assist.order_id = o.id
			// "
			// + "LEFT JOIN (SELECT sum(should_pay) should_pay,order_id from fy_business_pay
			// pay where order_id in "
			// + sb.toString() + " GROUP BY order_id) pay " + "ON pay.order_id =o.id "
			// + "LEFT JOIN (SELECT sum(purchase_account) purchase_account,order_id from
			// fy_business_purchase where order_id in "
			// + sb.toString() + " GROUP BY order_id) purchase " + "on o.id =
			// purchase.order_id "
			// + "set o.ww_hang_amount =
			// pay.should_pay,o.ww_unhang_amount=assist.tatol_amount+purchase.purchase_account
			// "
			// + "where id in " + sb.toString();

			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					Db.batchUpdate(list, list.size());
					Db.batchUpdate(updatePurchase, updatePurchase.size());
					Db.batchSave(createList, createList.size());
					Db.getSql("order.updateWWhangAmount");

					return true;
				}
			});
			if (re) {
				renderJson(Ret.ok("msg", "生成成功"));
			} else {
				renderJson(Ret.fail("msg", "生成失败"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	/**
	 * 下载应付单
	 * @throws ParseException
	 */
	public void downloadPay() throws ParseException {
		String date = getPara("date");
		if (StringUtils.isEmpty(date)) {
			setAttr("dateMsg", "没有选择应付期间");
			render("download.html");
			return;
		}
		String supplier = getPara("supplier");
		String sname = getPara("supplier_name");

		keepPara("date", "supplier_name", "supplier");

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "应付单_" + (StringUtils.isEmpty(sname) ? "" : sname) + date + ".xlsx");

		try {
			Date start = DateUtils.parseDate(date, "yyyy-MM");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			// System.out.println(DateFormatUtils.format(calendar, "yyyy-MM-dd"));
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			String startStr = DateFormatUtils.format(calendar, "yyyy-MM-dd");

			calendar.add(Calendar.DAY_OF_YEAR, 1);
			calendar.add(Calendar.MONTH, 1);
			String endStr = DateFormatUtils.format(calendar, "yyyy-MM-dd");

			StringBuilder sb = new StringBuilder();
			sb.append(" where pay_date >'").append(startStr).append("'  and pay_date < '").append(endStr).append("' ");

			if (!StringUtils.isEmpty(supplier)) {
				sb.append(" and supplier_id=").append(supplier).append("  ");
			}
			String sql = "select p.* ,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status,s.name supplier_name"
					+ " from  fy_business_pay p left join fy_business_order o on o.id= p.order_id"
					+ " left join fy_base_supplier s on  p.supplier_id = s.id " + sb.toString() + " order by id desc";
			List<FyBusinessPay> models = FyBusinessPay.dao.find(sql);
			if (models.size() == 0) {
				setAttr("msg", "没有符合条件的应付单");
				render("download.html");
				return;
			}
			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/pay.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);
			int row = 1;
			for (FyBusinessPay item : models) {

				String cate_tmp = item.getStr("cate_tmp"); // 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_tmp = item.getStr("plan_tmp"); // 计划员
				excel.setCellVal(row, 1, plan_tmp);

				String work_order_no = item.getStr("work_order_no"); // 交货日期
				excel.setCellVal(row, 2, work_order_no);

				String delivery_no = item.getStr("delivery_no"); // 送货单号
				excel.setCellVal(row, 3, delivery_no);

				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 4, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 5, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 6, map_no);

				String technology = item.getStr("technology"); // 技术条件
				excel.setCellVal(row, 7, technology);

				String machining_require = item.getStr("machining_require"); // 加工要求
				excel.setCellVal(row, 8, machining_require);

				Double quantity = item.getDouble("quantity"); // 数量
				excel.setCellVal(row, 9, quantity);

				String unit_tmp = item.getStr("unit_tmp"); // 单位
				excel.setCellVal(row, 10, unit_tmp);

				Date receive_time = item.getDate("purchase_date"); // 采购时间
				excel.setCellVal(row, 11, receive_time);

				String check_result = item.getStr("check_result"); // 检测结果
				excel.setCellVal(row, 12, check_result);

				String supplier_name = item.getStr("supplier_name"); // 厂商
				excel.setCellVal(row, 13, supplier_name);

				String order_no = item.getStr("order_no"); // 订单编号
				excel.setCellVal(row, 14, order_no);

				Double weiwai_quantity = item.getDouble("weiwai_quantity"); // 委外数量
				excel.setCellVal(row, 15, weiwai_quantity);

				Double weiwai_cost = item.getDouble("weiwai_cost"); // 委外单价
				excel.setCellVal(row, 16, weiwai_cost);

				Double weiwai_account = item.getDouble("weiwai_account"); // 委外金额
				excel.setCellVal(row, 17, weiwai_account);

				Date in_warehouse_time = item.getDate("in_warehouse_time"); // 入库时间
				excel.setCellVal(row, 18, in_warehouse_time);
				excel.setDateCellStyle(row, 18);

				String in_from = item.getStr("in_from"); // 来源 入库来源
				excel.setCellVal(row, 19, in_from);

				Double real_in_quantity = item.getDouble("real_in_quantity"); // 实际入库数量
				excel.setCellVal(row, 20, real_in_quantity);

				Date check_time = item.getDate("check_time"); // 检测时间
				excel.setCellVal(row, 21, check_time);
				excel.setDateCellStyle(row, 21);

				Double should_pay = item.getDouble("should_pay"); // 应付总额
				excel.setCellVal(row, 22, should_pay);

				Date create_date = item.getDate("create_date"); // 挂账期间
				excel.setCellVal(row, 23, create_date);
				excel.setDateCellStyle(row, 23, "yyyy年MM月");

				Date pay_date = item.getDate("pay_date"); // 应付期间
				excel.setCellVal(row, 24, pay_date);
				excel.setDateCellStyle(row, 24, "yyyy年MM月");

				row++;

			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e);
			renderText(e.getMessage());
		}

		// System.out.println("is file " + file.isFile() + " " + file.getAbsolutePath()
		// + " " + file.getName());

		renderFile(targetfile);
	}

	public void inhouseCreatePay() {
		try {
			String datestr = getPara("shoupaypireod");
			if (StringUtils.isEmpty(datestr)) {
				renderJson(Ret.fail().set("msg", "没有选择应付期间"));
				return;
			}
			Date shoupaypireod;

			shoupaypireod = DateUtils.parseDate(datestr, "yyyy-MM");

			Date now = new Date();

			if (shoupaypireod.before(now)) {
				renderJson(Ret.fail().set("msg", "应付期间不能是现在以前"));
				return;
			}
			String nows = DateFormatUtils.format(now, "yyyy-MM-00");
			now = DateUtils.parseDate(nows, "yyyy-MM-00");

			Integer Id = getParaToInt("id");
			if (Id == null) {
				renderJson(Ret.fail("msg", "没有选择检测单"));
				return;
			}
			Ret ret = PayService.me.inhouseCreatePay(getParaToInt("id"), now, shoupaypireod, getLoginAccountId());
			renderJson(ret);

			return;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "服务异常"));
	}

}
