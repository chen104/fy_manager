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

package com.chen.fy.controller.role;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chen.fy.model.RoleCol;
import com.jfinal.aop.Before;
import com.jfinal.club._admin.permission.PermissionAdminService;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.model.Permission;
import com.jfinal.club.common.model.Role;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

/**
 * 角色管理控制器
 */
public class RoleAdminController extends BaseController {

	RoleAdminService srv = RoleAdminService.me;

	public void index() {
		Page<Role> rolePage = srv.paginate(getParaToInt("p", 1));
		setAttr("rolePage", rolePage);
		render("list.html");
	}

	public void add() {
		render("add.html");
	}

	@Before(RoleAdminValidator.class)
	public void save() {
		Role role = getBean(Role.class);
		Ret ret = srv.save(role);
		renderJson(ret);
	}

	public void edit() {
		Role role = srv.findById(getParaToInt("id"));
		setAttr("role", role);
		render("edit.html");
	}

	/**
	 * 提交修改
	 */
	@Before(RoleAdminValidator.class)
	public void update() {
		Role role = getBean(Role.class);
		Ret ret = srv.update(role);
		renderJson(ret);
	}

	public void delete() {
		Ret ret = srv.delete(getParaToInt("id"));
		renderJson(ret);
	}

	/**
	 * 分配权限
	 */
	public void assignPermissions() {
		Role role = srv.findById(getParaToInt("id"));
		List<Permission> permissionList = PermissionAdminService.me.getAllPermissions();
		srv.markAssignedPermissions(role, permissionList);
		LinkedHashMap<String, List<Permission>> permissionMap = srv.groupByController(permissionList);

		setAttr("role", role);
		setAttr("permissionMap", permissionMap);
		render("assign_permissions.html");
	}

	/**
	 * 分配权限
	 */
	public void myassignPermissions() {
		Role role = srv.findById(getParaToInt("id"));
		Permission dao = new Permission().dao();
		List<Permission> permissionList = dao.find("select * from permission where isfy = 1  order by controller asc");
		srv.markAssignedPermissions(role, permissionList);
		LinkedHashMap<String, List<Permission>> permissionMap = srv.groupByController(permissionList);
		LinkedHashMap<String, List<Permission>> permissionMap1 = new LinkedHashMap<String, List<Permission>>();
		LinkedHashMap<String, List<Permission>> permissionMap2 = new LinkedHashMap<String, List<Permission>>();
		LinkedHashMap<String, List<Permission>> permissionMap3 = new LinkedHashMap<String, List<Permission>>();

		Set<String> kes = permissionMap.keySet();
		Iterator<String> iterator = kes.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			String k = iterator.next();

			if (i % 3 == 0) {
				permissionMap1.put(k, permissionMap.get(k));
			} else if (i % 3 == 1) {
				permissionMap2.put(k, permissionMap.get(k));
			} else if (i % 3 == 2) {
				permissionMap3.put(k, permissionMap.get(k));
			}
			i++;
		}

		setAttr("role", role);
		setAttr("permissionMap", permissionMap);
		setAttr("permissionMap1", permissionMap1);
		setAttr("permissionMap2", permissionMap2);
		setAttr("permissionMap3", permissionMap3);

		List<RoleCol> rlist = RoleCol.dao.find("select * from  role_col where roleId = ? and table_name = ?",
				getParaToInt("id"), "fy_business_bill");
		Map<String, String> billMap = new HashMap<String, String>();
		for (RoleCol e : rlist) {
			billMap.put(e.getColName(), "1");

		}
		setAttr("bill", billMap);
		render("assign_permissions.html");
	}

	/**
	 * 添加权限
	 */
	public void addPermission() {
		Ret ret = srv.addPermission(getParaToInt("roleId"), getParaToInt("permissionId"));
		renderJson(ret);
	}

	/**
	 * 删除权限
	 */
	public void deletePermission() {
		Ret ret = srv.deletePermission(getParaToInt("roleId"), getParaToInt("permissionId"));
		renderJson(ret);
	}

	public void addColumn() {
		Integer roleid = getParaToInt("roleId");
		String col = getPara("col");
		String table = getPara("table");
		RoleCol model = RoleCol.dao.findFirst(
				"select * from role_col where roleId = ? and col_name = ? and table_name = ?", roleid, col, table);
		// ColPermision permiss = new ColPermision();
		if (model == null) {
			model = new RoleCol();
			model.setRoleId(roleid);
			model.setColName(col);
			model.setTableName(table);
			model.save();
		}
		Ret ret = Ret.ok().set("msg", "添加权限完成");
		renderJson(ret);

	}

	public void deleteColumn() {
		Integer roleid = getParaToInt("roleId");
		String col = getPara("col");
		String table = getPara("table");

		int row = Db.delete("delete from role_col  where roleId = ? and col_name = ? and table_name = ?", roleid, col,
				table);
		Ret ret = Ret.ok().set("msg", "更新权限完成");
		renderJson(ret);
	}

	public static void main(String[] args) {
		String a = "1";
		Integer b = 1;

		System.out.println(Integer.valueOf(a) == b);
	}

	public void getAllCloPermision() {

	}
}
