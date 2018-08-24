package com.chen.fy.controller.business;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PIOExcelUtil.EXCELVERSION;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class WarehouseController extends BaseController {
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
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					" select w.id wid ,in_time, real_in_quantity,in_from, check_create_time ,is_create_check,o.* ",
					" from  fy_business_in_warehouse w left join fy_business_order o on w.order_id = o.id  order by w.id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("and o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					" select w.id wid ,in_time, real_in_quantity,in_from, check_create_time ,is_create_check,o.* ",
					" from  fy_business_in_warehouse w left join fy_business_order o on w.order_id = o.id "
							+ sb.toString() + " order by w.id desc");

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
		Page<FyBusinessInWarehouse> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					" select w.id wid ,in_time, real_in_quantity,in_from, check_create_time ,is_create_check,check_time,check_quantity,check_result,check_handle,is_create_quality,is_create_can_out,create_out_time,is_create_pay,pay_create_time,o.* ",
					"from fy_business_in_warehouse w left join fy_business_order o on w.order_id = o.id  where is_create_check =1 order by w.id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("and o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessInWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					" select w.id wid ,in_time, real_in_quantity,in_from, check_create_time ,is_create_check,check_time,check_quantity,check_result,check_handle,is_create_quality,is_create_can_out,create_out_time,is_create_pay,pay_create_time,o.* ",
					"from fy_business_in_warehouse w inner join fy_business_order o on w.order_id = o.id  where is_create_check =1 "
							+ sb.toString() + " order by w.id desc");
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

	public void wwcheckInHouse() {

	}

	/**
	 * 从检测表中，生成应付明细单
	 */
	public void pay() {

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

	public void toAddOut() {
		Integer id = getParaToInt("id");
		// FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		// setAttr("model", model);
		setAttr("order", order);
		setAttr("action", "addOut");
		render("addOut.html");
	}

	/**
	 * 添加出库
	 */
	public void addOut() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "out");
		System.out.println(model);
		Integer id = model.getOrderId();
		FyBusinessOrder parent = FyBusinessOrder.dao.findById(id);
		BigDecimal outquantity = model.getOutQuantity();
		BigDecimal starage = parent.getStorageQuantity().subtract(outquantity);
		parent.setStorageQuantity(starage);// 库存

		BigDecimal hasout = parent.getOutQuantity().add(outquantity);
		parent.setOutQuantity(hasout);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				boolean b1 = parent.update();
				boolean b2 = model.save();
				return (b1 & b2);
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "出库成功");
		} else {
			ret = Ret.fail("msg", "出库失败");
		}
		renderJson(ret);
	}

	/**
	 * 出库
	 */
	public void outWhouse() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id   order by w.id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("where  o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id "
							+ sb.toString() + "  order by w.id desc");
		}
		setAttr("modelPage", modelPage);
		render("outWareHouse.html");
	}

	public void downloadOutWhouse() {
		getParaValuesToInt("selectId");
		String filePath = null;
		try {
			// 读取模板
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("templet/outWhouse.xlsx");

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(input, 0);

			List<Record> list = Db.find(
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id");
			int row = 2;
			for (Record item : list) {

				String cate_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_tmp = item.getStr("plan_tmp");// 计划员
				excel.setCellVal(row, 1, plan_tmp);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 2, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 3, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 4, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 5, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 6, map_no);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 7, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 8, unit_tmp);
				row++;
			}
			String name = System.currentTimeMillis() + "";
			filePath = PathKit.getWebRootPath() + File.separator + "download/excel/" + name;

			excel.save2File(filePath);

			// renderJson(list);
			if (EXCELVERSION.EXCEL_VERSION_2003 == excel.getCurrentVersion()) {
				filePath += ".xls";
			} else {
				filePath += ".xlsx";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(filePath);
		// System.out.println("is file " + file.isFile() + " " + file.getAbsolutePath()
		// + " " + file.getName());
		renderFile(file, "出库单.xlsx");

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

	/*
	 * 出库撤回
	 */
	public void rollbackOutWarehouse() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);

		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		System.out.println(model);

		BigDecimal outquantity = model.getOutQuantity();
		BigDecimal StorageQuantity = order.getStorageQuantity().add(model.getOutQuantity());
		order.setStorageQuantity(StorageQuantity);
		BigDecimal hasout = order.getOutQuantity().subtract(outquantity);
		order.setOutQuantity(hasout);
		model.setIsCreateGetPay(false);

		// 应收信息
		BigDecimal zelo = new BigDecimal("0");
		model.setUntaxGetpay(zelo);

		model.setTax(zelo);
		model.setHangAmount(zelo);// 挂账金额，应付金额
		model.setCreateMonth(0);
		model.setGetpayMonth(0);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				// 删除应收单
				Db.delete("delete from fy_business_getpay where parent_id =?", id);
				boolean b1 = order.update();
				boolean b2 = model.delete();
				return (b1 & b2);
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "撤回成功");
		} else {
			ret = Ret.fail("msg", "撤回失败");
		}
		renderJson(ret);

	}

	/**
	 * 入库撤回
	 */
	public void rollbackInWarehouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
		if (model.getIsCreateCheck() == null || !model.getIsCreateCheck()) {
			model.delete();
			renderJson(Ret.ok().set("msg", "撤回入库完成"));
			return;
		}
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		if (model.getIsCreateCheck() && "合格".equals(model.getCheckResult())) {

			Double storage = order.getStorageQuantity().doubleValue();
			if (storage < model.getRealInQuantity().doubleValue()) {
				renderJson(Ret.fail().set("msg", "库存不足，不允许撤回"));
				return;
			} else {
				storage = order.getStorageQuantity().doubleValue() - model.getRealInQuantity().doubleValue();
				order.setStorageQuantity(new BigDecimal(storage));
			}
		}

		// 删除应付单

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				// 删除应收单
				Db.delete("delete from fy_business_getpay where parent_id =?", id);

				// 更新订单,
				String updateSql = "update fy_business_order o LEFT JOIN (SELECT SUM(tatol_amount) tatol_amount,order_id from fy_business_assist where order_id = "
						+ model.getOrderId() + " GROUP BY order_id) assist   " + "on assist.order_id = o.id  "
						+ "LEFT JOIN (SELECT sum(should_pay) should_pay,order_id from fy_business_pay pay where  order_id  = "
						+ model.getOrderId() + " GROUP BY order_id) pay  " + "ON pay.order_id =o.id  "
						+ "LEFT JOIN (SELECT sum(purchase_account) purchase_account,order_id from fy_business_purchase where order_id  = "
						+ model.getOrderId() + " GROUP BY order_id) purchase  " + "on o.id = purchase.order_id  "
						+ "set o.ww_hang_amount = pay.should_pay,o.ww_unhang_amount=assist.tatol_amount+purchase.purchase_account  "
						+ "where id  =  " + model.getOrderId();
				Db.update(updateSql);
				boolean b2 = model.delete() && order.update();
				return b2;
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "撤回成功");
		} else {
			ret = Ret.fail("msg", "撤回失败");
		}
		renderJson(ret);
	}
}
