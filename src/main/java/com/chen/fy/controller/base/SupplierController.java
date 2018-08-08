package com.chen.fy.controller.base;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Supplier;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class SupplierController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<Supplier> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier order by id desc");
		} else {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier  where name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");
	}

	public void save() {
		Supplier customer = getBean(Supplier.class, "model");
		boolean re = customer.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  " + customer.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = Supplier.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Supplier person = Supplier.dao.findById(getParaToInt("id"));
		setAttr("model", person);
		setAttr("action", "update");
		setAttr("title", "修改厂商");
		render("edit.html");
	}

	public void update() {
		Supplier person = getBean(Supplier.class, "model");
		boolean re = person.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   " + person.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void add() {
		setAttr("action", "save");
		setAttr("title", "新建厂商");
		render("edit.html");
	}

	public void searchSupplierJson() {
		String key = getPara("keyWord");
		Page<Supplier> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier order by id desc");
		} else {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier  where name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}
		renderJson(personPage);
	}
}
