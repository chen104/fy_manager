package com.chen.fy.controller.business;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBusinessOutWarehouse;
import com.chen.fy.model.FyBusinessWarehouse;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.plugin.activerecord.Page;

public class WarehouseController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessWarehouse> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_warehouse order by id desc");
		} else {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_warehouse where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("inWarehouse.html");
	}

	public void checkIn() {
		String key = getPara("keyWord");
		Page<FyBusinessWarehouse> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_warehouse order by id desc");
		} else {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_warehouse where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("checkIn.html");
	}

	public void storage() {
		String key = getPara("keyWord");
		Page<FyBusinessWarehouse> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_warehouse order by id desc");
		} else {
			modelPage = FyBusinessWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_warehouse where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("storage.html");
	}

	public void outWhouse() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_out_warehouse order by id desc");
		} else {
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_out_warehouse where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("outWareHouse.html");
	}

}
