package com.chen.fy.controller.business;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBusinessBill;
import com.chen.fy.model.FyBusinessGetpay;
import com.chen.fy.model.FyBusinessGetpaybill;
import com.chen.fy.model.FyBusinessPay;
import com.chen.fy.model.FyBusinessPaybill;
import com.chen.fy.model.FyBusinessSumPaybill;
import com.jfinal.club.common.controller.BaseController;
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
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessGetpay.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_getpay order by id desc");
		} else {
			modelPage = FyBusinessGetpay.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_getpay where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("getPay.html");
	}

	/**
	 * 应收结算单
	 */
	public void getbill() {
		String key = getPara("keyWord");
		Page<FyBusinessBill> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessBill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_bill order by id desc");
		} else {
			modelPage = FyBusinessBill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_bill where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("getbill.html");
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
