package com.chen.fy.controller.business;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBizWwReceive;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.plugin.activerecord.Page;

public class CommissionController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order order by id desc");
		} else {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	public void sumReceive() {
		String key = getPara("keyWord");
		Page<FyBizWwReceive> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order order by id desc");
		} else {
			modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("sumReceive.html");
	}

	public void purchase() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_purchase order by id desc");
		} else {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_purchase where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("purchase.html");
	}

	public void oneSumCommission() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_purchase order by id desc");
		} else {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_purchase where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("oneSumCommission.html");
	}

	public void assist() {
		String key = getPara("keyWord");
		Page<FyBusinessAssist> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_assist order by id desc");
		} else {
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_assist where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("assist.html");
	}

}
