package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseOrderUploadLog<M extends BaseOrderUploadLog<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setSucess(java.lang.Integer sucess) {
		set("sucess", sucess);
	}
	
	public java.lang.Integer getSucess() {
		return getInt("sucess");
	}

	public void setError(java.lang.Integer error) {
		set("error", error);
	}
	
	public java.lang.Integer getError() {
		return getInt("error");
	}

	public void setReson(java.lang.String reson) {
		set("reson", reson);
	}
	
	public java.lang.String getReson() {
		return getStr("reson");
	}

}