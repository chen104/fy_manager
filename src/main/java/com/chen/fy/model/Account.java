package com.chen.fy.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.chen.fy.model.base.BaseAccount;
import com.chen.util.saft.JsoupFilter;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Account extends BaseAccount<Account> {

	private static final long serialVersionUID = 1L;

	public static final String AVATAR_NO_AVATAR = "x.jpg"; // 刚注册时使用默认头像

	public static final int STATUS_LOCK_ID = -1; // 锁定账号，无法做任何事情
	public static final int STATUS_REG = 0; // 注册、未激活
	public static final int STATUS_OK = 1; // 正常、已激活

	public boolean isStatusOk() {
		return getStatus() == STATUS_OK;
	}

	public boolean isStatusReg() {
		return getStatus() == STATUS_REG;
	}

	public boolean isStatusLockId() {
		return getStatus() == STATUS_LOCK_ID;
	}

	/**
	 * 过滤掉 nickName 中的 html 标记，恶意脚本
	 */
	protected void filter(int filterBy) {
		JsoupFilter.filterAccountNickName(this);
	}

	public Account removeSensitiveInfo() {
		remove("password", "salt");
		return this;
	}

	@SuppressWarnings("unchecked")
	public boolean hasPermission(String key) {
		if (getId() == 1) {
			return true;
		}

		Object obj = CacheKit.get("permission", getId());

		if (obj == null) {
			Integer id = getId();
			String sql = Db.getSql("permission.getPermission");
			List<Record> list = Db.find(sql, id);
			HashSet<String> permission = new HashSet<String>();
			for (Record e : list) {
				permission.add(e.getStr("key"));
			}
			CacheKit.put("permission", getId(), permission);
			obj = permission;
		}
		HashSet<String> permission = (HashSet<String>) obj;
		if (permission.contains(key)) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean hasUriPermission(String key) {
		if (getId() == 1) {
			return true;
		}
		if ("/fy/admin".equals(key)) {
			return true;
		}
		Object obj = CacheKit.get("urlPermission", getId());

		if (obj == null) {
			Integer id = getId();
			String sql = Db.getSql("permission.getUrlPermission");
			List<Record> list = Db.find(sql, id);
			HashSet<String> permission = new HashSet<String>();
			for (Record e : list) {
				permission.add(e.getStr("uri"));
			}
			CacheKit.put("urlPermission", getId(), permission);
			obj = permission;
		}
		HashSet<String> permission = (HashSet<String>) obj;
		if (permission.contains(key)) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean hasColPermission(String ctable, String key) {
		if (getId() == 1) {
			return true;
		}
		Object obj = CacheKit.get("colPermission", getId());

		if (obj == null) {
			Integer id = getId();
			String sql = Db.getSql("permission.listColPermission");
			List<Record> list = Db.find(sql, id);
			HashMap<String, HashSet<String>> permission = new HashMap<String, HashSet<String>>();
			// HashSet<String> set = new HashSet<String>();
			for (Record e : list) {
				String ckey = e.getStr("ctable");
				HashSet<String> set = permission.get(ckey);
				if (set == null) {
					set = new HashSet<String>();
					permission.put(ckey, set);
				}
				set.add(e.getStr("ckey"));
			}
			CacheKit.put("colPermission", getId(), permission);
			obj = permission;
		}
		HashMap<String, HashSet<String>> permission = (HashMap<String, HashSet<String>>) obj;
		HashSet<String> col = permission.get(ctable);
		if (col != null) {
			if (col.contains(key)) {
				return true;
			}
		}

		return false;
	}

}
