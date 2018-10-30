package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyComplaint<M extends BaseFyComplaint<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setCustomerId(java.lang.Integer customerId) {
		set("customer_id", customerId);
	}
	
	public java.lang.Integer getCustomerId() {
		return getInt("customer_id");
	}

	public void setCustomerName(java.lang.String customerName) {
		set("customer_name", customerName);
	}
	
	public java.lang.String getCustomerName() {
		return getStr("customer_name");
	}

	public void setComplaintDate(java.util.Date complaintDate) {
		set("complaint_date", complaintDate);
	}
	
	public java.util.Date getComplaintDate() {
		return get("complaint_date");
	}

	public void setMapNo(java.lang.String mapNo) {
		set("map_no", mapNo);
	}
	
	public java.lang.String getMapNo() {
		return getStr("map_no");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public void setQuantity(java.lang.Integer quantity) {
		set("quantity", quantity);
	}
	
	public java.lang.Integer getQuantity() {
		return getInt("quantity");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}
	
	public java.lang.String getContent() {
		return getStr("content");
	}

	public void setHandleMethod(java.lang.String handleMethod) {
		set("handle_method", handleMethod);
	}
	
	public java.lang.String getHandleMethod() {
		return getStr("handle_method");
	}

	public void setSolution(java.lang.String solution) {
		set("solution", solution);
	}
	
	public java.lang.String getSolution() {
		return getStr("solution");
	}

	public void setDutyPart(java.lang.String dutyPart) {
		set("duty_part", dutyPart);
	}
	
	public java.lang.String getDutyPart() {
		return getStr("duty_part");
	}

	public void setDutyPerson(java.lang.String dutyPerson) {
		set("duty_person", dutyPerson);
	}
	
	public java.lang.String getDutyPerson() {
		return getStr("duty_person");
	}

	public void setRecordPart(java.lang.String recordPart) {
		set("record_part", recordPart);
	}
	
	public java.lang.String getRecordPart() {
		return getStr("record_part");
	}

	public void setRecordPerson(java.lang.String recordPerson) {
		set("record_person", recordPerson);
	}
	
	public java.lang.String getRecordPerson() {
		return getStr("record_person");
	}

	public void setHandleNo(java.lang.String handleNo) {
		set("handle_no", handleNo);
	}
	
	public java.lang.String getHandleNo() {
		return getStr("handle_no");
	}

	public void setOrderId(java.lang.Integer orderId) {
		set("order_id", orderId);
	}
	
	public java.lang.Integer getOrderId() {
		return getInt("order_id");
	}

	public void setParentId(java.lang.Integer parentId) {
		set("parent_id", parentId);
	}
	
	public java.lang.Integer getParentId() {
		return getInt("parent_id");
	}

}
