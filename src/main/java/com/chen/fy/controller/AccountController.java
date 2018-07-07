package com.chen.fy.controller;

import java.util.Date;
import java.util.List;

import com.chen.fy.service.*;
import com.jfinal.aop.Before;

import com.jfinal.club._admin.role.RoleAdminService;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Account;
import com.jfinal.club.common.model.Role;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class AccountController extends BaseController {
	
	AccountAdminService srv = AccountAdminService.me;
	public  void index() {
		list();
	}
	
	public void list() {
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
		// 密码加盐 hash
		String salt = HashKit.generateSaltForSha256();
		String password = HashKit.sha256(salt + account.getPassword());

		// 创建账户
//		Account account = new Account();
//		account.setUserName(userName);
		account.setPassword(password);
		account.setSalt(salt);
//		account.setNickName(nickName);
		account.setStatus(Account.STATUS_REG);
		account.setCreateAt(new Date());
//		account.setIp(ip);
		account.setAvatar(Account.AVATAR_NO_AVATAR);  // 注册时设置默认头像
		boolean re = account.save();
		Ret ret = null;
		if(re) {
			ret = Ret.ok();
		}else {
			ret = Ret.fail("msg","添加失败");
		}
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

}
