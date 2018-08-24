package com.chen.fy.controller;

import java.util.List;

import com.chen.fy.model.Account;
import com.chen.fy.model.Role;
import com.chen.fy.role.RoleAdminService;
import com.chen.fy.service.AccountAdminService;
import com.chen.fy.service.AccountUpdateValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class AccountController extends BaseController {

	AccountAdminService srv = AccountAdminService.me;

	public void index() {
		list();
	}

	public void list() {
		// String keyword = getPara("keyword");
		Page<Account> accountPage = srv.paginate(getParaToInt("p", 1));
		setAttr("accountPage", accountPage);
		render("list.html");
	}

	public void add() {
		render("add.html");
	}

	public void edit() {
		Account account = srv.findById(getParaToInt("id"));
		setAttr("account", account);
		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(AccountUpdateValidator.class)
	public void update() {
		Account account = getBean(Account.class);

		Ret ret = srv.update(account);
		renderJson(ret);
	}

	public void save() {

		Account account = getBean(Account.class);
		Ret ret = srv.addAccount(account);

		renderJson(ret);
	}

	/**
	 * 账户锁定
	 */
	public void lock() {
		Ret ret = srv.lock(getLoginAccountId(), getParaToInt("id"));
		renderJson(ret);
	}

	/**
	 * 账户解锁
	 */
	public void unlock() {
		Ret ret = srv.unlock(getParaToInt("id"));
		renderJson(ret);
	}

	/**
	 * 分配角色
	 */
	public void assignRoles() {
		Account account = srv.findById(getParaToInt("id"));
		List<Role> roleList = RoleAdminService.me.getAllRoles();
		srv.markAssignedRoles(account, roleList);

		setAttr("account", account);
		setAttr("roleList", roleList);
		render("assign_roles.html");
	}

	/**
	 * 添加角色
	 */
	public void addRole() {
		Ret ret = srv.addRole(getParaToInt("accountId"), getParaToInt("roleId"));
		renderJson(ret);
	}

	/**
	 * 删除角色
	 */
	public void deleteRole() {
		Ret ret = srv.deleteRole(getParaToInt("accountId"), getParaToInt("roleId"));
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("accountId");
		if (id == null) {
			Ret ret = Ret.fail("msg", "添加失败");
			renderJson(ret);
			return;
		}
		Boolean re = srv.deleteAccount(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "删除成功");
		} else {
			ret = Ret.fail("msg", "添加失败");
		}
		renderJson(ret);
	}

	public void tochangPassWord() {
		Account user = getSessionAttr("account");
		setAttr("id", user.getId());
		render("changMypassWord.html");
	}

	/**
	 * 
	 */
	public void changPassword() {
		Integer id = getParaToInt("id");
		String password = getPara("password");
		Ret ret = srv.changPassword(id, password);
		renderJson(ret);
	}

	public void toIndex() {
		render("../index.html");

	}

	@ActionKey("/fy/admin")
	public void adminIndex() {

		Integer unDistribut = Db.queryInt("select count(1) num from fy_business_order where dis_to is null");
		setAttr("unDistribut", unDistribut);

		Integer warn_distribut = Db.queryInt(
				"select  count(1) num  from fy_business_order where dis_to is null  and DATEDIFF(CURDATE(),import_time) > 2");
		setAttr("unDistribut", warn_distribut);

		Integer delay = Db.queryInt(
				" select  count(1) num  from fy_business_order where out_quantity =0  and DATEDIFF(CURDATE(),import_time) > 30 ");
		setAttr("delay", delay);
		render("../index.html");

	}
}
