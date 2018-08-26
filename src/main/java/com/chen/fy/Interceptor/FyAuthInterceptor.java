package com.chen.fy.Interceptor;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.Account;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.Ret;

public class FyAuthInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		String uri = inv.getController().getRequest().getRequestURI();
		Account account = inv.getController().getSessionAttr("account");

		if (!uri.startsWith("/fy/admin") || "/fy/admin".equals(uri) || "/fy/admin/account/tochangPassWord".equals(uri)
				|| "/fy/admin/account/changPassword".equals(uri) || "/fy/admin/account/reloadPermission".equals(uri)) {
			inv.invoke();
			return;
		}
		// ajax选择实体
		if (uri.contains("search")
				&& StringUtils.isNotEmpty(inv.getController().getRequest().getHeader("X-Requested-With"))) {
			inv.invoke();
			return;
		}
		if (account.hasUriPermission(uri)) {
			inv.invoke();
		} else {
			String c = inv.getController().getRequest().getHeader("X-Requested-With");
			// Enumeration<String> enume =
			// inv.getController().getRequest().getHeaderNames();
			// while (enume.hasMoreElements()) {
			// String key = enume.nextElement();
			// System.out.print(key + " = ");
			// System.out.println(inv.getController().getRequest().getHeader(key));
			// }
			// boolean isPjax = "true".equalsIgnoreCase(c);
			if (!StringUtils.isEmpty(c)) {
				inv.getController().renderJson(Ret.fail().set("msg", "没有权限"));
			} else {
				inv.getController().redirect("/fy/noAuth");
			}
		}

	}

}
