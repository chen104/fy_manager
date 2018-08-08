package com.chen.fy.controller.base;

import java.math.BigDecimal;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.TaxRate;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class TaxRateController extends BaseController {
	public void index() {
		// TaxRate model = TaxRate.dao.findFirst("select * from fy_base_tax_rate");
		// page
		Page<TaxRate> modelPage = TaxRate.dao.paginate(getParaToInt("p", 1), 10, "select * ", "from fy_base_tax_rate");
		setAttr("modelPage", modelPage);
		render("list.html");
	}

	public void save() {
		TaxRate model = getBean(TaxRate.class, "model");
		String strvalue = model.getTaxRate().replaceAll("%", "");
		Double d = Double.valueOf(strvalue);
		d = d / 100;
		model.setMatchValue(new BigDecimal(d));
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
		setAttr("title", "修改税率");
		render("edit.html");
	}

	public void update() {
		TaxRate model = getBean(TaxRate.class, "model");
		String strvalue = model.getTaxRate().replaceAll("%", "");
		Double d = Double.valueOf(strvalue);
		d = d / 100;
		model.setMatchValue(new BigDecimal(d));
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
		setAttr("title", "添加税率");
		render("edit.html");
	}

	public void searchTaxRateJson() {
		Page<TaxRate> modelPage = null;

		modelPage = TaxRate.dao.paginate(getParaToInt("p", 1), 6, "select *  ",
				" from fy_base_tax_rate order by id desc");
		renderJson(modelPage);
	}
}
