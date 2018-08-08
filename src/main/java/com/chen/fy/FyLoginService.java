package com.chen.fy;

import java.util.Date;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Account;
import com.chen.fy.model.Session;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class FyLoginService {
	public static final FyLoginService me = new FyLoginService();

	private Account accountDao = new Account().dao();

	// 存放登录用户的 cacheName
	public static final String loginAccountCacheName = "loginAccount";

	// "jfinalId" 仅用于 cookie 名称，其它地方如 cache 中全部用的 "sessionId" 来做 key
	public static final String sessionIdName = "jfinalId";

	/**
	 * 登录成功返回 sessionId 与 loginAccount，否则返回一个 msg
	 */
	public Ret login(String userName, String password, boolean keepLogin, String loginIp, BaseController controller) {
		userName = userName.toLowerCase().trim();
		password = password.trim();
		Account loginAccount = accountDao.findFirst("select * from account where userName=? limit 1", userName);
		if (loginAccount == null) {
			return Ret.fail("msg", "用户名或密码不正确");
		}
		// if (loginAccount.isStatusLockId()) {
		// return Ret.fail("msg", "账号已被锁定");
		// }
		// if (loginAccount.isStatusReg()) {
		// return Ret.fail("msg", "账号未激活，请先激活账号");
		// }

		String salt = loginAccount.getSalt();
		String hashedPass = HashKit.sha256(salt + password);
		// 未通过密码验证
		if (loginAccount.getPassword().equals(hashedPass) == false) {
			return Ret.fail("msg", "用户名或密码不正确");
		}

		// 如果用户勾选保持登录，暂定过期时间为 3 年，否则为 120 分钟，单位为秒
		long liveSeconds = keepLogin ? 3 * 365 * 24 * 60 * 60 : 120 * 60;
		// 传递给控制层的 cookie
		int maxAgeInSeconds = (int) (keepLogin ? liveSeconds : -1);
		// expireAt 用于设置 session 的过期时间点，需要转换成毫秒
		long expireAt = System.currentTimeMillis() + (liveSeconds * 1000);
		// 保存登录 session 到数据库
		Session session = new Session();
		String sessionId = StrKit.getRandomUUID();
		session.setId(sessionId);
		session.setAccountId(loginAccount.getId());
		session.setExpireAt(expireAt);
		// if (!session.save()) {
		// return Ret.fail("msg", "保存 session 到数据库失败，请联系管理员");
		// }

		if (!session.save()) {
			Record loginLog = new Record().set("accountId", loginAccount.getId()).set("ip", loginIp).set("loginAt",
					new Date());
			Db.save("login_log", loginLog);
		}

		loginAccount.removeSensitiveInfo(); // 移除 password 与 salt 属性值
		loginAccount.put("sessionId", sessionId); // 保存一份 sessionId 到 loginAccount 备用
		// CacheKit.put(loginAccountCacheName, sessionId, loginAccount);

		// createLoginLog(loginAccount.getId(), loginIp);
		controller.setSessionAttr(Constant.account, loginAccount);
		return Ret.ok(sessionIdName, sessionId).set(loginAccountCacheName, loginAccount).set("maxAgeInSeconds",
				maxAgeInSeconds); // 用于设置 cookie 的最大存活时间
	}

	/**
	 * 创建登录日志
	 */
	private void createLoginLog(Integer accountId, String loginIp) {
		Record loginLog = new Record().set("accountId", accountId).set("ip", loginIp).set("loginAt", new Date());
		Db.save("login_log", loginLog);
	}

}
