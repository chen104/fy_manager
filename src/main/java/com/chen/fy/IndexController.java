package com.chen.fy;

import com.chen.fy.controller.BaseController;
import com.chen.fy.login.LoginService;
import com.chen.fy.model.Account;
import com.chen.fy.model.Session;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.club.common.kit.IpKit;
import com.jfinal.core.ActionKey;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.ehcache.CacheKit;

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
        if (ret.isOk()) {// 账号密码正确
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
        String sessionId = getCookie(LoginService.sessionIdName);
        removeSessionAttr(Constant.account);
        removeCookie(LoginService.sessionIdName);

        if (sessionId != null) {
            CacheKit.remove(LoginService.loginAccountCacheName, sessionId);
            Session.dao.deleteById(sessionId);

        }
        redirect("/fy");
    }

    @ActionKey("/fy/noAuth")
    public void noAuth() {
        render("atladmin/noAuth.html");
    }

    /**
     * 刷新权限
     */
    @ActionKey("/fy/reloadPermission")
    public void reloadPermission() {
        Account model = getSessionAttr(Account.sessionKey);
        String c = getRequest().getHeader("X-Requested-With");
        if (model == null) {
            if (c != null) {
                renderJson(Ret.ok().set("msg", "你还没有登录"));
                return;
            } else {
                redirect("/fy");
                return;
            }
        }
        model.reloadPermission();
        renderJson(Ret.ok().set("msg", "权限刷新完毕"));
    }

}
