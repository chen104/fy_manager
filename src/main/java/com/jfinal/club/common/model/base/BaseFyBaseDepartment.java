package com.jfinal.club.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyBaseDepartment<M extends BaseFyBaseDepartment<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setPartNo(java.lang.String partNo) {
		set("part_no", partNo);
	}
	
	public java.lang.String getPartNo() {
		return getStr("part_no");
	}

	public void setPartName(java.lang.String partName) {
		set("part_name", partName);
	}
	
	public java.lang.String getPartName() {
		return getStr("part_name");
	}

	public void setDutyPerson(java.lang.Integer dutyPerson) {
		set("duty_person", dutyPerson);
	}
	
	public java.lang.Integer getDutyPerson() {
		return getInt("duty_person");
	}

	public void setDesc(java.lang.String desc) {
		set("desc", desc);
	}
	
	public java.lang.String getDesc() {
		return getStr("desc");
	}

	public void setCreateBy(java.lang.Integer createBy) {
		set("create_by", createBy);
	}
	
	public java.lang.Integer getCreateBy() {
		return getInt("create_by");
	}

	public void setUpdateBy(java.lang.Integer updateBy) {
		set("update_by", updateBy);
	}
	
	public java.lang.Integer getUpdateBy() {
		return getInt("update_by");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
	}
	
	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
