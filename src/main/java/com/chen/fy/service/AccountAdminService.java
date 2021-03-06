/**
 * 请勿将俱乐部专享资源复制给其他人，保护知识产权即是保护我们所在的行业，进而保护我们自己的利益
 * 即便是公司的同事，也请尊重 JFinal 作者的努力与付出，不要复制给同事
 * 
 * 如果你尚未加入俱乐部，请立即删除该项目，或者现在加入俱乐部：http://jfinal.com/club
 * 
 * 俱乐部将提供 jfinal-club 项目文档与设计资源、专用 QQ 群，以及作者在俱乐部定期的分享与答疑，
 * 价值远比仅仅拥有 jfinal club 项目源代码要大得多
 * 
 * JFinal 俱乐部是五年以来首次寻求外部资源的尝试，以便于有资源创建更加
 * 高品质的产品与服务，为大家带来更大的价值，所以请大家多多支持，不要将
 * 首次的尝试扼杀在了摇篮之中
 */

package com.chen.fy.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.chen.fy.login.LoginService;
import com.chen.fy.model.Account;
import com.chen.fy.model.Role;
import com.chen.fy.model.Session;
import com.jfinal.club.common.kit.IpKit;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 账户管理
 */
public class AccountAdminService {

	public static final AccountAdminService me = new AccountAdminService();
	private Account dao = new Account().dao();

	public Page<Account> paginate(int pageNum) {
		return dao.paginate(pageNum, 10, "select *", "from account where id <> 1 order by id desc");
	}

	public Account findById(int accountId) {
		return dao.findById(accountId);
	}

	/**
	 * 注意要验证 nickName 与 userName 是否存在
	 */
	public Ret update(Account account) {
		String nickName = account.getNickName().toLowerCase().trim();
		String sql = "select id from account where lower(nickName) = ? and id != ? limit 1";
		Integer id = Db.queryInt(sql, nickName, account.getId());
		if (id != null) {
			return Ret.fail("msg", "昵称已经存在，请输入别的昵称");
		}

		String userName = account.getUserName().toLowerCase().trim();
		sql = "select id from account where lower(userName) = ? and id != ? limit 1";
		id = Db.queryInt(sql, userName, account.getId());
		if (id != null) {
			return Ret.fail("msg", "邮箱已经存在，请输入别的昵称");
		}
		String password = account.getPassword();
		// 暂时只允许修改 nickName 与 userName
		// account.keep("id", "nickName", "userName");

		if ("****".equals(password)) {
			account.remove("password");
		} else {
			// 密码加盐 hash
			String salt = HashKit.generateSaltForSha256();
			password = HashKit.sha256(salt + account.getPassword());

			// 创建账户
			// Account account = new Account();
			// account.setUserName(userName);
			account.setPassword(password);
			account.setSalt(salt);

		}

		account.update();
		return Ret.ok("msg", "账户更新成功");
	}

	public Ret addAccount(Account account, HttpServletRequest request) {
		Ret ret = null;
		Account list = dao.findFirst("select  1 from account where userName = ?", account.getUserName());
		if (list != null) {
			ret = Ret.fail().set("msg", "用户已存在");
			return ret;
		}
		// 密码加盐 hash
		String salt = HashKit.generateSaltForSha256();
		String password = HashKit.sha256(salt + account.getPassword());

		// 创建账户
		// Account account = new Account();
		// account.setUserName(userName);
		account.setPassword(password);
		account.setSalt(salt);
		// account.setNickName(nickName);
		account.setStatus(Account.STATUS_OK);
		account.setCreateAt(new Date());
		String ip = IpKit.getRealIp(request);
		account.setIp(ip);
		account.setAvatar(Account.AVATAR_NO_AVATAR); // 注册时设置默认头像
		boolean re = account.save();
		if (re) {
			ret = Ret.ok().set("msg", "用户添加成功");
		} else {
			ret = Ret.fail().set("msg", "用户添加失败");
		}

		return ret;
	}

	/**
	 * 锁定账号
	 */
	public Ret lock(int loginAccountId, int lockedAccountId) {
		if (loginAccountId == lockedAccountId) {
			return Ret.fail("msg", "不能锁定自己的账号");
		}

		int n = Db.update("update account set status = ? where id=?", Account.STATUS_LOCK_ID, lockedAccountId);

		// 锁定后，强制退出登录，避免继续搞破坏
		List<Session> sessionList = Session.dao.find("select * from session where accountId = ?", lockedAccountId);
		if (sessionList != null) {
			for (Session session : sessionList) { // 处理多客户端同时登录后的多 session 记录
				LoginService.me.logout(session.getId()); // 清除登录 cache，强制退出
			}
		}

		if (n > 0) {
			return Ret.ok("msg", "锁定成功");
		} else {
			return Ret.fail("msg", "锁定失败");
		}
	}

	/**
	 * 解锁账号
	 */
	public Ret unlock(int accountId) {
		// 如果账户未激活，则不能被解锁
		int n = Db.update("update account set status = ? where status != ? and id = ?", Account.STATUS_OK,
				Account.STATUS_REG, accountId);
		Db.update("delete from session where accountId = ?", accountId);
		if (n > 0) {
			return Ret.ok("msg", "解锁成功");
		} else {
			return Ret.fail("msg", "解锁失败，可能是账户未激活，请查看账户详情");
		}
	}

	/**
	 * 添加角色
	 */
	public Ret addRole(int accountId, int roleId) {
		Record accountRole = new Record().set("accountId", accountId).set("roleId", roleId);
		Db.save("account_role", accountRole);
		return Ret.ok("msg", "添加角色成功");
	}

	/**
	 * 删除角色
	 */
	public Ret deleteRole(int accountId, int roleId) {
		Db.delete("delete from account_role where accountId=? and roleId=?", accountId, roleId);
		return Ret.ok("msg", "删除角色成功");
	}

	/**
	 * 标记出 account 拥有的角色 未来用 role left join account_role 来优化
	 */
	public void markAssignedRoles(Account account, List<Role> roleList) {
		String sql = "select accountId from account_role where accountId=? and roleId=? limit 1";
		for (Role role : roleList) {
			Integer accountId = Db.queryInt(sql, account.getId(), role.getId());
			if (accountId != null) {
				// 设置 assigned 用于界面输出 checked
				role.put("assigned", true);
			}
		}
	}

	public boolean deleteAccount(Integer id) {
		Db.delete("delete from account_role where accountId = ?", id);
		return dao.deleteById(id);

	}

	public Ret changPassword(Integer id, String newpass) {
		Account user = dao.findById(id);
		String salt = HashKit.generateSaltForSha256();
		String password = HashKit.sha256(salt + newpass);
		user.setPassword(password);
		user.setSalt(salt);
		user.setUpdateTime(new Date());
		boolean re = user.update();
		if (re) {
			return Ret.ok().set("msg", "修改密码成功");
		} else {
			return Ret.ok().set("msg", "修改密码成功");
		}

	}
}
