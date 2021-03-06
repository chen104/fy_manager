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
import com.jfinal.plugin.ehcache.CacheKit;

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

		// // 如果用户勾选保持登录，暂定过期时间为 3 年，否则为 120 分钟，单位为秒
		// long liveSeconds = keepLogin ? 3 * 365 * 24 * 60 * 60 : 120 * 60;
		// 记住密码为7天 否则一天
		long liveSeconds = keepLogin ? 7 * 24 * 60 * 60 : 24 * 60 * 60;
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
		if (!session.save()) {
			return Ret.fail("msg", "保存 session 到数据库失败，请联系管理员");
		}

		loginAccount.removeSensitiveInfo(); // 移除 password 与 salt 属性值
		loginAccount.put("sessionId", sessionId); // 保存一份 sessionId 到 loginAccount 备用
		loginAccount.reloadPermission();// 登录重新加载权限
		CacheKit.put(loginAccountCacheName, sessionId, loginAccount);// 用户保存到缓存

		createLoginLog(loginAccount.getId(), loginIp);
		controller.setSessionAttr(Constant.account, loginAccount);
		controller.setCookie(sessionIdName, sessionId, 60 * 60 * 24 * 3);
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

	/**
	 * 退出登录
	 */
	public void logout(String sessionId) {
		if (sessionId != null) {
			CacheKit.remove(loginAccountCacheName, sessionId);
			Session.dao.deleteById(sessionId);
		}
	}

	/**
	 * 从数据库重新加载登录账户信息
	 */
	public void reloadLoginAccount(Account loginAccountOld) {
		String sessionId = loginAccountOld.get("sessionId");
		Account loginAccount = accountDao.findFirst("select * from account where id=? limit 1",
				loginAccountOld.getId());
		loginAccount.removeSensitiveInfo(); // 移除 password 与 salt 属性值
		loginAccount.put("sessionId", sessionId); // 保存一份 sessionId 到 loginAccount 备用

		// 集群方式下，要做一通知其它节点的机制，让其它节点使用缓存更新后的数据，
		// 将来可能把 account 用 id : obj 的形式放缓存，更新缓存只需要 CacheKit.remove("account", id) 就可以了，
		// 其它节点发现数据不存在会自动去数据库读取，所以未来可能就是在 AccountService.getById(int id)的方法引入缓存就好
		// 所有用到 account 对象的地方都从这里去取
		CacheKit.put(loginAccountCacheName, sessionId, loginAccount);
	}

}
