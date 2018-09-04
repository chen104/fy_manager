package com.chen.fy.controller.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.controller.base.service.FySupplierCategoryService;
import com.chen.fy.model.FySupplierCategory;
import com.chen.fy.model.Supplier;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class SupplierCategoryController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FySupplierCategory.class);
	FySupplierCategoryService modelService = FySupplierCategoryService.me;

	public void index() {
		String key = getPara("keyWord");
		keepPara("keyWord");
		String select = "select c.*,p.name parent_name,p.id parent_id ";
		String from = " from fy_supplier_category c left join  fy_supplier_category p on c.parent_id = p.id";

		Page<FySupplierCategory> modelPage;
		if (StringUtils.isEmpty(key)) {
			String where = " order by c.id desc";
			modelPage = modelService.paginate(getParaToInt("p", 1), getParaToInt("pageSize", 10), select, from + where);
		} else {
			String where = " where c.name like '%" + key + "%'  order by id desc ";
			modelPage = modelService.paginate(getParaToInt("p", 1), getParaToInt("pageSize", 10), select, from + where);
		}
		setAttr("modelPage", modelPage);
		render("list.html");
	}

	public void add() {
		logger.info("");
		setAttr("action", "save");
		render("edit.html");
	}

	public void save() {

		FySupplierCategory model = getBean(FySupplierCategory.class, "model");

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
		Integer id = getParaToInt("id");
		FySupplierCategory model = modelService.findFirst(id);
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");

	}

	public void update() {
		FySupplierCategory model = getBean(FySupplierCategory.class, "model");
		if (model.getId() == model.getParentId()) {
			renderJson(Ret.ok().set("msg", "上一级分类错误"));
			return;
		}
		Integer parent = model.getParentId();
		Boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}
		renderJson(ret);
	}

	public void searchSupplierCateJson() {
		String key = getPara("keyWord");

		Page<Supplier> personPage = null;
		String select = "select * ";
		String from = " from fy_supplier_category c ";

		String where = " ";
		Boolean iscate = getParaToBoolean("iscategory");
		if (iscate != null && iscate) {
			where = " where parent_id = 0  ";
		}
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {

			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, select, from + where + "order by c.id desc");
		} else {
			key = key.trim();
			if (iscate && iscate != null) {
				where = "  where parent_id = 0  and  name like ? order by id desc ";
			} else {
				where = "  where    name like ? order by c.id desc ";
			}
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, select, from + where, "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}
		renderJson(personPage);
	}
}
