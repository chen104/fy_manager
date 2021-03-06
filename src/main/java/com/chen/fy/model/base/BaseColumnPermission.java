package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseColumnPermission<M extends BaseColumnPermission<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setTableName(java.lang.String tableName) {
		set("table_name", tableName);
	}
	
	public java.lang.String getTableName() {
		return getStr("table_name");
	}

	public void setThTitle(java.lang.String thTitle) {
		set("th_title", thTitle);
	}
	
	public java.lang.String getThTitle() {
		return getStr("th_title");
	}

	public void setTdKey(java.lang.String tdKey) {
		set("td_key", tdKey);
	}
	
	public java.lang.String getTdKey() {
		return getStr("td_key");
	}

	public void setRoleId(java.lang.Integer roleId) {
		set("role_id", roleId);
	}
	
	public java.lang.Integer getRoleId() {
		return getInt("role_id");
	}

}
