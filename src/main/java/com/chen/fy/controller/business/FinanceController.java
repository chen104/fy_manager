package com.chen.fy.controller.business;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBusinessBill;
import com.chen.fy.model.FyBusinessGetpay;
import com.chen.fy.model.FyBusinessGetpaybill;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPaybill;
import com.chen.fy.model.FyBusinessSumPaybill;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 财务相关
 * 
 * @author Administrator
 *
 */
public class FinanceController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	/**
	 * 应收明细表
	 */
	public void getPay() {
		String key = getPara("keyWord");
		Page<FyBusinessGetpay> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessGetpay.dao.paginate(getParaToInt("p", 1), 10,
				"select o.*  ,g.out_time , g.out_quantity,g.id getpay_id,g.out_time out_time,g.out_quantity out_quantity,g.is_create_bill is_create_bill,"
						+ "g.bill_create_time bill_create_time",
				"from  fy_business_getpay g left join fy_business_order o on g.order_id = o.id  order by id desc");

		setAttr("modelPage", modelPage);
		render("getPay.html");
	}

	/**
	 * 添加结算单填写界面
	 */
	public void addbill() {

		setAttr("action", "savebill");
		Integer id = getParaToInt("id");
		FyBusinessGetpay model = FyBusinessGetpay.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("model", model);
		setAttr("order", order);
		render("addGetbill.html");

	}

	public void savebill() {
		FyBusinessBill model = getBean(FyBusinessBill.class, "model");
		Integer parentId = model.getParentId();
		FyBusinessGetpay parent = FyBusinessGetpay.dao.findById(parentId);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				parent.setBillCreateTime(new Date());
				parent.setIsCreateBill(true);
				boolean b1 = parent.update();
				boolean b2 = model.save();
				return (b1 & b2);
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
	}

	/**
	 * 应收结算单
	 */
	public void getbill() {
		Page<FyBusinessBill> modelPage = null;
		String sql = ",b.hang_time  bhang_time, b.hang_status bhang_status , b.hang_quantity  bhang_quantity,b.hang_amount bhang_amount,"
				+ "b.unhang_quantity bunhang_quantity ,  b.is_create_paybill, b.paybill_create_time,b.id bid";

		modelPage = FyBusinessBill.dao.paginate(getParaToInt("p", 1), 10, "select o.* " + sql,
				"from  fy_business_bill b left join fy_business_order  o  on b.order_id = o.id  order by id desc");

		setAttr("modelPage", modelPage);
		render("getbill.html");
	}

	public void addgetpaybill() {
		Integer id = getParaToInt("id");
		setAttr("action", "saveGetpaybill");
		FyBusinessBill model = FyBusinessBill.dao.findById(id);
		setAttr("model", model);
		render("addGetpaybill.html");

	}

	public void saveGetpaybill() {
		FyBusinessGetpaybill model = getBean(FyBusinessGetpaybill.class, "model");
		Integer id = getParaToInt("parentId");
		FyBusinessBill parent = FyBusinessBill.dao.findById(id);
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				parent.setPaybillCreateTime(new Date());
				parent.setIsCreatePaybill(true);
				boolean b1 = parent.update();
				boolean b2 = model.save();
				return (b1 & b2);
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
	}

	/**
	 * 应收汇总表
	 */
	public void getpaybill() {
		String key = getPara("keyWord");
		Page<FyBusinessGetpaybill> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessGetpaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_getpaybill order by id desc");
		} else {
			modelPage = FyBusinessGetpaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_getpaybill where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("getPaybill.html");
	}

	/**
	 * 应付明细表
	 */
	public void pay() {
		String key = getPara("keyWord");
		Page<FyBusinessPay> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPay.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_pay order by id desc");
		} else {
			modelPage = FyBusinessPay.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_pay where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("pay.html");
	}

	/**
	 * 应付计算单
	 */
	public void paybill() {
		String key = getPara("keyWord");
		Page<FyBusinessPaybill> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_paybill order by id desc");
		} else {
			modelPage = FyBusinessPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_paybill where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("paybill.html");
	}

	/**
	 * 应付计算单
	 */
	public void sumpaybill() {
		String key = getPara("keyWord");
		Page<FyBusinessSumPaybill> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessSumPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_paybill order by id desc");
		} else {
			modelPage = FyBusinessSumPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_paybill where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("sumPaybill", modelPage);
		render("sumpay.html");
	}
}
