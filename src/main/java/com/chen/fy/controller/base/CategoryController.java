package com.chen.fy.controller.base;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Category;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class CategoryController extends BaseController {
	public void index() {
		// String key = getPara("keyWord");
		Page<Category> modelPage = null;
		// setAttr("keyWord", key);
		// if (StringUtils.isEmpty(key)) {
		modelPage = Category.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from fy_base_category order by id desc");
		// }
		// else {
		// personPage = Category.dao.paginate(getParaToInt("p", 1), 10, "select * ",
		// "from fy_base_customer where name like ? order by id desc", "%" + key + "%");
		// setAttr("append", "keyWord=" + key);
		// }

		setAttr("modelPage", modelPage);
		setAttr("name", "name");
		render("list.html");
	}

	public void save() {
		Category customer = getBean(Category.class, "model");
		Integer i = Db.queryInt("select 1 from fy_base_category  where name = ?", customer.getName());
		if (i == 1) {
			Ret ret = Ret.ok("msg", "类别" + customer.getName() + "已存在");
			renderJson(ret);
			return;
		}
		boolean re = customer.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  类别成功信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = Category.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Category model = Category.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		Category person = getBean(Category.class, "model");
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
		render("edit.html");
	}

	public void searchCategoryJson() {
		String key = getPara("keyWord");
		Page<Category> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {

			modelPage = Category.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_category order by id desc");
		} else {

			modelPage = Category.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_category  where name like '" + key + "' order by id desc");
		}
		renderJson(modelPage);
	}
}
