package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRoleCol<M extends BaseRoleCol<M>> extends Model<M> implements IBean {

	public void setColumnId(java.lang.Integer columnId) {
		set("column_id", columnId);
	}
	
	public java.lang.Integer getColumnId() {
		return getInt("column_id");
	}

	public void setRoleId(java.lang.Integer roleId) {
		set("roleId", roleId);
	}
	
	public java.lang.Integer getRoleId() {
		return getInt("roleId");
	}

}