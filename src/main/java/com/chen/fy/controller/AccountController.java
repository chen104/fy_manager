package com.chen.fy.controller;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.chen.fy.model.Account;
import com.chen.fy.model.Role;
import com.chen.fy.service.AccountAdminService;
import com.chen.fy.service.AccountUpdateValidator;
import com.jfinal.aop.Before;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

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
		Ret ret = srv.addAccount(account, getRequest());

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
		List<Role> roleList = Role.dao.find("select * from role order by id asc");
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

		// 分配这展示
		Integer unDistribut = Db.queryInt("select count(1) num from fy_business_order where dis_to is null");
		setAttr("unDistribut", unDistribut);

		/**
		 *
		 */
		Integer warn_distribut = Db.queryInt(
				"select  count(1) num  from fy_business_order where dis_to is null  and DATEDIFF(CURDATE(),import_time) > 2");
		setAttr("unDistribut", warn_distribut);

		Integer delay = Db.queryInt(
				" select  count(1) num  from fy_business_order where out_quantity =0  and DATEDIFF(CURDATE(),import_time) > 30 ");
		setAttr("delay", delay);
		
		//订单警告，根据出库数量和交货日期 决定
		String order_warn_sql = Db.getSql("index.getOrderWarnCount");
		Integer order_warn = Db.queryInt(order_warn_sql);
		setAttr("order_warn", order_warn);

		// 订单拖期，根据出库数量和交货日期 决定
		String order_delay_sql = Db.getSql("index.getOrderDelayCount");
		Integer order_delay = Db.queryInt(order_delay_sql);
		setAttr("order_delay", order_delay);

		// 委外警告，根据采购交货时间
		String commision_warn_sql = Db.getSql("index.getCommisionWarnCount");
		Integer commision_warn = Db.queryInt(commision_warn_sql);
		setAttr("commision_warn", commision_warn);

		// 委外拖期，根据采购交货时间
		String commision_delay_sql = Db.getSql("index.getCommisionDelayCount");
		Integer commision_delay = Db.queryInt(commision_delay_sql);
		setAttr("commision_delay", commision_delay);

		// 自产警告，根据采购交货时间
		String product_warn_sql = Db.getSql("index.getProductWarnCount");
		Integer product_warn = Db.queryInt(product_warn_sql);
		setAttr("product_warn", product_warn);

		// 自产拖期，根据采购交货时间
		String product_delay_sql = Db.getSql("index.getProductDelayCount");
		Integer prodect_delay = Db.queryInt(product_delay_sql);
		setAttr("product_delay", prodect_delay);
		/**
		 * 需要传递参数年份，如 2018
		 */
		String findTotal = Db.getSql("order.findTotal");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		// calendar.add(Calendar.YEAR, -1);
		String yyyy = DateFormatUtils.format(calendar, "yyyy");
		List<Record> list = Db.find(findTotal, yyyy);
		setAttr("yyyy", yyyy);
		Double totale = 0d;
		for (Record e : list) {
			Double tem = e.getDouble("amount");
			totale += tem == null ? 0d : tem;
			Double item_total = totale;
			e.set("item_total", item_total);
		}
		setAttr("accont", getLoginAccount());
		setAttr("totalList", list);
		render("../index.html");

	}

	@ActionKey("/fy/admin/total")
	public void findTotal() {
		/**
		 * 需要传递参数年份，如 2018
		 */
		try {
			String findTotal = Db.getSql("order.findTotal");
			String year = getPara("yyyy");
			// calendar.add(Calendar.YEAR, -1);
			List<Record> list = Db.find(findTotal, year);
			Double totale = 0d;
			for (Record e : list) {
				Double tem = e.getDouble("amount");
				totale += tem == null ? 0d : tem;
				Double item_total = totale;
				e.set("item_total", item_total);
			}
			Ret ret = Ret.ok().set("msg", "查找完成");
			ret.set("data", list);
			renderJson(ret);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Ret ret = Ret.fail().set("msg", "查找完成");
		renderJson(ret);
	}
}
