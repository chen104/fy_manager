package com.chen.fy.controller.business.commission.execut;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CommisionExecutController extends BaseController {
	private static final Logger logger = LogManager.getLogger(CommisionExecutController.class);

	CommisionExecutService service = CommisionExecutService.me;

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		keepPara("condition", "weiwai_cate");
		String condition = getPara("condition");
		String weiwai_cate = getPara("weiwai_cate");

		Page<FyBusinessOrder> modelPage = null;
		modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key, weiwai_cate);

		setAttr("modelPage", modelPage);
		StringBuilder append = new StringBuilder();
		if (StringUtils.isNotEmpty(key)) {
			append.append("&keyWord=").append(key);
		}
		append.append("&condition=").append(condition);
		if (StringUtils.isNotEmpty(weiwai_cate)) {
			append.append("&weiwai_cate=").append(weiwai_cate);
		}
		setAttr("append", append.toString());
		// setAttr("action", "updatePurchaseCost");
		render("list.html");

	}

	/**
	 * 下载报目表的下载页面
	 */
	public void todownloadAskCost() {
		render("askCostDownload.html");
	}

	public void findDownloadAskCost() {
		String start_date = getPara("start_date");
		String end_date = getPara("end_date");
		String condition = getPara("condition");
		keepPara("start_date", "end_date", "keyWord", "condition");
		String keyword = getPara("keyWord");
		if (keyword != null) {
			keyword = keyword.trim();
		}
		try {
			List<Record> list = service.findDownloadAskCost(start_date, end_date, condition, keyword);
			setAttr("modelList", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());

		}

		render("askCostDownload.html");

	}

	/**
	 * 下载报目表
	 */
	public void downloadAskCost() {
		String[] ids = getParaValues("selectId");
		try {
			File file = service.downloadAskCost(ids);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		render("askCostDownload.html");
	}

	public void edit() {
		String order_id = getPara("order_id");
		keepPara("path");
		System.out.println(getPara("path"));
		if (!NumberUtils.isNumber(order_id)) {
			index();
			return;
		}
		try {
			Record model = service.findEdit(order_id);
			setAttr("model", model);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		index();

	}

	public void update() {
		FyBusinessPurchase model = getModel(FyBusinessPurchase.class, "model");
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}
		renderJson(ret);
		return;
	}

	public void updatePurchaseCost() {

	}

}
