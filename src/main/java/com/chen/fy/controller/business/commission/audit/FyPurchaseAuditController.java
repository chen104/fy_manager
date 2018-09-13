package com.chen.fy.controller.business.commission.audit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyPurchaseAudit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class FyPurchaseAuditController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FyPurchaseAuditController.class);
	FyPurchaseAuditService modelService = FyPurchaseAuditService.me;

	public void index() {
		StringBuilder sb = new StringBuilder();
		Page<FyPurchaseAudit> accountPage = modelService.paginate(getParaToInt("p", 1), getParaToInt("pageSize", 10),
				sb);
		setAttr("modelPage", accountPage);
		render("list.html");
	}

	public void add() {
		logger.info("");
		render("edit.html");
	}

	public void save() {
		FyPurchaseAudit model = getModel(FyPurchaseAudit.class, "model");
		Boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "新建成功");
		} else {
			ret = Ret.ok().set("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Boolean re = modelService.deleteById(getParaToInt("id"));

		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "新建成功");
		} else {
			ret = Ret.ok().set("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void edit() {
		FyPurchaseAudit model = modelService.findById(getParaToInt("id"));
		setAttr("model", model);
		render("edit.html");

	}

	public void update() {
		FyPurchaseAudit model = getBean(FyPurchaseAudit.class, "model");
		Boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}
		renderJson(ret);
	}

}
