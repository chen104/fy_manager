package com.chen.fy.controller.base;

import com.chen.fy.model.TaxRate;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;

public class TaxRateController extends BaseController {
	public void index() {
		TaxRate model = TaxRate.dao.findFirst("select * from fy_base_tax_rate");

		setAttr("model", model);
		render("index.html");
	}

	public void save() {
		TaxRate model = getBean(TaxRate.class, "model");
		boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建   信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = TaxRate.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		TaxRate model = TaxRate.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		TaxRate model = getBean(TaxRate.class, "model");
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void add() {
		setAttr("action", "save");
		render("edit.html");
	}
}
