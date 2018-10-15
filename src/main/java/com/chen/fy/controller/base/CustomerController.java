package com.chen.fy.controller.base;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.controller.base.service.CustomerService;
import com.chen.fy.model.Customer;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class CustomerController extends BaseController {
	CustomerService service = CustomerService.me;

	public void index() {
		String key = getPara("keyWord");
		Page<Customer> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Customer.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_customer order by id desc");
		} else {
			personPage = Customer.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_customer  where name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");
	}

	public void save() {
		Customer customer = getBean(Customer.class, "model");
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

		boolean re = Customer.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Customer person = Customer.dao.findById(getParaToInt("id"));
		setAttr("model", person);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		Customer person = getBean(Customer.class, "model");
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

	public void searchcustomerJson() {
		String key = getPara("keyWord");
		Page<Customer> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Customer.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_customer order by id desc");
		} else {
			personPage = Customer.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_customer  where name like ? order by id desc", "%" + key + "%");

		}
		renderJson(personPage);
	}

	public void download() throws Exception {
		File file = service.download();
		renderFile(file);
	}
}
