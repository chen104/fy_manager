package com.chen.fy.controller.base;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.Department;
import com.chen.fy.model.Person;
import com.chen.fy.service.PersonService;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class DepartmentController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<Department> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Department.dao.paginate(getParaToInt("p", 1), 10, "select d.*,p.name dutyName ",
					"from fy_base_department d left join fy_base_person p  on d.duty_person = p.id  order by d.id desc");
		} else {
			personPage = Department.dao.paginate(getParaToInt("p", 1), 10, "select d.*,p.name dutyName ",
					"from fy_base_department d left join fy_base_person p  on d.duty_person = p.id  where part_name like ? order by d.id desc",
					"%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");

	}

	public void add() {
		setAttr("title", "新建部门");
	}

	public void save() {
		Department model = getBean(Department.class, "model");
		boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建 " + model.getPartName() + "部门成功");
		} else {
			ret = Ret.ok("msg", "新建部门失败");
		}
		renderJson(ret);

	}

	public void update() {
		Department model = getBean(Department.class, "model");
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改 " + model.getPartName() + " 部门成功");
		} else {
			ret = Ret.ok("msg", "修改部门失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Department depart = Department.dao.findById(getParaToInt("id"));
		if (depart.getDutyPerson() != null) {
			Person person = PersonService.dao.findById(depart.getDutyPerson());
			setAttr("dutyPerson", person);
		}

		setAttr("model", depart);
		setAttr("title", "修改部门");

	}

	public void delete() {
		boolean re = Department.dao.deleteById(getParaToInt("id"));
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 部门成功");
		} else {
			ret = Ret.ok("msg", "删除部门失败");
		}
		renderJson(ret);

	}

	public void searchDepartJson() {
		String key = getPara("keyWord");
		Page<Department> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Department.dao.paginate(getParaToInt("p", 1), 6, "select d.*,p.name dutyName ",
					"from fy_base_department d left join fy_base_person p  on d.duty_person = p.id  order by d.id desc");
		} else {
			personPage = Department.dao.paginate(getParaToInt("p", 1), 6, "select d.*,p.name dutyName ",
					"from fy_base_department d left join fy_base_person p  on d.duty_person = p.id  where part_name like ? order by d.id desc",
					"%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}
		renderJson(personPage);
	}

}
