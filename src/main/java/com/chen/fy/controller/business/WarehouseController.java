package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.model.FyBusinessGetpay;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.chen.fy.model.FyBusinessWarehouse;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class WarehouseController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessWarehouse> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_warehouse order by id desc");
		} else {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_warehouse where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("inWarehouse.html");
	}

	public void toCheck() {
		Integer id = getParaToInt("id");
		FyBusinessWarehouse model = FyBusinessWarehouse.dao.findById(id);
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

	public void checkIn() {
		String key = getPara("keyWord");
		Page<FyBusinessWarehouse> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_warehouse order by id desc");

		setAttr("modelPage", modelPage);
		render("checkIn.html");
	}

	/**
	 * 显示检测输入界面
	 */
	public void editCheck() {
		Integer id = getParaToInt("id");
		FyBusinessWarehouse model = FyBusinessWarehouse.dao.findById(id);
		setAttr("model", model);
		render("checkEdit.html");
	}

	/**
	 * 输入检测结果
	 */
	public void checkInHouse() {
		Integer id = getParaToInt("id");
		String checkQuantity = getPara("checkQuantity");
		String checkHandle = getPara("checkHandle");
		String checkResult = getPara("checkResult");
		Date checkTime = getParaToDate("checkTime");
		FyBusinessWarehouse model = FyBusinessWarehouse.dao.findById(id);
		Ret ret = null;
		if (model != null) {
			model.setCheckTime(checkTime);
			model.setCheckQuantity(NumberUtils.isNumber(checkQuantity) ? new BigDecimal(checkQuantity) : null);
			model.setCheckResult(checkResult);
			model.setCheckHandle(checkHandle);
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
	 * 从检测表中，生成应付明细单
	 */
	public void pay() {

	}

	public void storage() {
		String key = getPara("keyWord");
		Page<FyBusinessWarehouse> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_warehouse where check_result ='合格' order by id desc");

		setAttr("modelPage", modelPage);
		render("storage.html");
	}

	public void toAddOut() {
		Integer id = getParaToInt("id");
		FyBusinessWarehouse model = FyBusinessWarehouse.dao.findById(id);
		setAttr("model", model);
		render("addOut.html");
	}

	public void addOut() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "out");
		System.out.println(model);
		Integer id = model.getParentId();
		FyBusinessWarehouse parent = FyBusinessWarehouse.dao.findById(id);
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				parent.setCreateOutTime(new Date());
				parent.setIsCreateCanOut(true);
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

	public void outWhouse() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_out_warehouse order by id desc");

		setAttr("modelPage", modelPage);
		render("outWareHouse.html");
	}

	public void toGetPay() {
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(getParaToInt("id"));
		FyBusinessGetpay getpay = new FyBusinessGetpay();
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		getpay.setOrderId(model.getOrderId());
		getpay.setParentId(model.getId());
		getpay.setUntaxCost(order.getUntaxedCost());
		getpay.setOutTime(model.getOutTime());
		getpay.setOutQuantity(model.getOutQuantity());
		model.setIsCreateGetPay(true);
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				boolean b1 = model.update();
				boolean b2 = getpay.save();

				return (b1 & b2);
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "应收明细生成成功");
		} else {
			ret = Ret.fail("msg", "应收明细生成失败");
		}
		renderJson(ret);

	}

}
