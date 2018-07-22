package com.chen.fy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class FyAuthService {
	public final static FyAuthService me = new FyAuthService();

	public List<String> getRole(int accountId) {
		List<String> list = new ArrayList<String>();
		List<Map<String, String>> ret = Db.query(
				"select `name` name from account_role LEFT JOIN role on roleId = role.id where accountId = ?",
				accountId);
		for (int i = 0; i < ret.size(); i++) {
			list.add(ret.get(i).get("name"));
		}
		return list;
	}

	public List<String> getPermession(int accountId) {
		List<String> list = new ArrayList<String>();
		List<Record> ret = Db.find(
				"select `name` name, pm.actionKey url  from account_role a LEFT JOIN role r on a.roleId = r.id INNER JOIN role_permission  p on a.roleId = p.roleId  LEFT JOIN permission pm on p.permissionId = pm.id where accountId =?",
				accountId);
		for (int i = 0; i < ret.size(); i++) {

			String item = (ret.get(i).get("url") == null ? "" : ret.get(i).get("url").toString());
			list.add(item);

		}

		return list;
	}

	public void hasPermession() {

	}

	public void loginInitAccountAuth() {

	}

}
