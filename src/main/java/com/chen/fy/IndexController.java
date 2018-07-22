package com.chen.fy;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.kit.IpKit;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;

public class IndexController extends BaseController {

	FyLoginService srv = FyLoginService.me;

	public void index() {
		render("login.html");
	}

	public void main() {
		render("atladmin/common/main.html");
	}

	public void goLigonView() {
		render("login.html");
	}

	/**
	 * 登录
	 */
	@Before(FyLoginValidator.class)
	public void doLogin() {
		boolean keepLogin = getParaToBoolean("keepLogin", false);
		String loginIp = IpKit.getRealIp(getRequest());
		Ret ret = srv.login(getPara("userName"), getPara("password"), keepLogin, loginIp, this);
		if (ret.isOk()) {
			// String sessionId = ret.getStr(LoginService.sessionIdName);
			// int maxAgeInSeconds = ret.getInt("maxAgeInSeconds");
			// setCookie(LoginService.sessionIdName, sessionId, maxAgeInSeconds, true);
			setAttr(FyLoginService.loginAccountCacheName, ret.get(FyLoginService.loginAccountCacheName));
			ret.set("returnUrl", getPara("returnUrl", "/")); // 如果 returnUrl 存在则跳过去，否则跳去首页
		}
		renderJson(ret);
	}

	/**
	 * 退出登录
	 */
	@Clear
	@ActionKey("/fy/logout")
	public void logout() {
		// srv.logout(getCookie(LoginService.sessionIdName));
		// removeCookie(LoginService.sessionIdName);
		removeSessionAttr(Constant.account);
		redirect("/fy");
	}
}
