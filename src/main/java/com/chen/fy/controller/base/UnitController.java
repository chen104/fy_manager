package com.chen.fy.controller.base;

import com.chen.fy.model.Unit;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class UnitController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<Unit> personPage = null;
		setAttr("keyWord", key);
		// if (StringUtils.isEmpty(key)) {
		personPage = Unit.dao.paginate(getParaToInt("p", 1), 10, "select * ", "from fy_base_unit order by id desc");
		// }
		// else {
		// personPage = Customer.dao.paginate(getParaToInt("p", 1), 10, "select * ",
		// "from fy_base_tax_rate where name like ? order by id desc", "%" + key + "%");
		// setAttr("append", "keyWord=" + key);
		// }

		setAttr("modelPage", personPage);
		render("list.html");
	}

	public void save() {
		Unit model = getBean(Unit.class, "model");
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

		boolean re = Unit.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Unit model = Unit.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		Unit model = getBean(Unit.class, "model");
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
