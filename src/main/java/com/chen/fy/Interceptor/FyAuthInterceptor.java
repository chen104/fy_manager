package com.chen.fy.Interceptor;

import com.chen.fy.Constant;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.club.common.model.Account;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class FyAuthInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller c = inv.getController();
		Object o = c.getSessionAttr(Constant.account);
		if (!inv.getActionKey().startsWith("/fy/admin")) {
			inv.invoke();
			return;
		}
		if (o == null) {
			c.redirect("fy");
		}
		if (((Account) o).getId() == 1) {
			inv.invoke();
			return;
		}

		Record record = Db.findFirst(
				"select * from account_role ar INNER JOIN role_permission  rp on ar.roleId= rp.roleId inner JOIN permission p ON rp.permissionId = p.id where ar.accountId = ? and p.actionKey = ?",
				((Account) o).getId(), inv.getActionKey());
		boolean isPjax = "true".equalsIgnoreCase(c.getHeader("X-PJAX"));
		if (record == null) {
			if (isPjax) {
				inv.getController().renderJson(Ret.fail().set("msg", "没有权限"));
			} else {
				inv.getController().render("/_view/atladmin/noAuth.html");
			}
		} else {
			inv.invoke();
		}

	}

}
