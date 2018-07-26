package com.chen.fy.controller.business;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBizWwReceive;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class CommissionController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBizWwReceive> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_biz_ww_receive order by id desc");

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	/**
	 * 
	 */
	@Before(Tx.class)
	public void receiveOrder() {
		Integer id = getParaToInt("id", 1);
		FyBizWwReceive model = FyBizWwReceive.dao.findById(id);
		model.setIsReceive(true);
		Integer pid = model.getParentId();
		FyBusinessDistribute distribut = FyBusinessDistribute.dao.findById(pid);
		distribut.setReceiveTime(new Date());
		Ret ret = null;

		boolean re = model.update();
		boolean save = distribut.update();
		if (re && save) {
			ret = Ret.ok("msg", "接收成功");

		} else {
			ret = Ret.ok("msg", "接收失败");
		}
		renderJson(ret);
	}

	public void sumReceive() {
		String key = getPara("keyWord");
		Page<FyBizWwReceive> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_biz_ww_receive where Is_Receive = 1  order by id desc");

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
