package com.chen.fy.controller.base;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.Department;
import com.chen.fy.model.Person;
import com.chen.fy.service.PersonService;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class PersonController extends BaseController {
	PersonService service = PersonService.me;

	public void index() {
		String key = getPara("keyWord");
		Page<Person> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = service.paginate(getParaToInt("p", 1));
		} else {
			personPage = service.paginate(getParaToInt("p", 1), key);
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");

	}

	public void edit() {
		Person person = service.dao.findById(getParaToInt("id"));
		setAttr("model", person);
		Integer did = person.getDepartmentId();
		if (did != null) {
			Department part = Department.dao.findById(did);
			setAttr("department", part);
		}
		setAttr("action", "update");
		setAttr("title", "修改人员");
		render("edit.html");
	}

	public void update() {
		Person person = getBean(Person.class, "model");
		boolean re = person.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   " + person.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void test() {

	}

	public void add() {
		setAttr("action", "save");
		setAttr("title", "新建人员");
		render("edit.html");

	}

	public void searchPersonJson() {
		String key = getPara("keyWord");
		Page<Person> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = service.paginate(getParaToInt("p", 1), 6);
		} else {
			personPage = service.paginate(getParaToInt("p", 1), key, 6);
		}
		renderJson(personPage);
	}

	public void searchPersonHtml() {
		String key = getPara("keyWord");
		Page<Person> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = service.paginate(getParaToInt("p", 1), 6);
		} else {
			personPage = service.paginate(getParaToInt("p", 1), key, 6);
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("searchPerson.html");

	}

	public void save() {
		Person person = getBean(Person.class, "model");
		boolean re = person.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  " + person.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = service.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

}
