package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyUploadGetpay<M extends BaseFyUploadGetpay<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setHangDate(java.util.Date hangDate) {
		set("hang_date", hangDate);
	}
	
	public java.util.Date getHangDate() {
		return get("hang_date");
	}

	public void setMaterials(java.lang.String materials) {
		set("materials", materials);
	}
	
	public java.lang.String getMaterials() {
		return getStr("materials");
	}

	public void setCommodityName(java.lang.String commodityName) {
		set("commodity_name", commodityName);
	}
	
	public java.lang.String getCommodityName() {
		return getStr("commodity_name");
	}

	public void setBrandNo(java.lang.String brandNo) {
		set("Brand_no", brandNo);
	}
	
	public java.lang.String getBrandNo() {
		return getStr("Brand_no");
	}

	public void setSpec(java.lang.String spec) {
		set("spec", spec);
	}
	
	public java.lang.String getSpec() {
		return getStr("spec");
	}

	public void setProjectNo(java.lang.String projectNo) {
		set("project_no", projectNo);
	}
	
	public java.lang.String getProjectNo() {
		return getStr("project_no");
	}

	public void setUnit(java.lang.String unit) {
		set("unit", unit);
	}
	
	public java.lang.String getUnit() {
		return getStr("unit");
	}

	public void setQuantity(java.math.BigDecimal quantity) {
		set("quantity", quantity);
	}
	
	public java.math.BigDecimal getQuantity() {
		return get("quantity");
	}

	public void setCost(java.math.BigDecimal cost) {
		set("cost", cost);
	}
	
	public java.math.BigDecimal getCost() {
		return get("cost");
	}

	public void setHangQuantity(java.math.BigDecimal hangQuantity) {
		set("hang_quantity", hangQuantity);
	}
	
	public java.math.BigDecimal getHangQuantity() {
		return get("hang_quantity");
	}

	public void setInvoiceStat(java.lang.String invoiceStat) {
		set("invoice_stat", invoiceStat);
	}
	
	public java.lang.String getInvoiceStat() {
		return getStr("invoice_stat");
	}

	public void setHangAmount(java.math.BigDecimal hangAmount) {
		set("hang_amount", hangAmount);
	}
	
	public java.math.BigDecimal getHangAmount() {
		return get("hang_amount");
	}

	public void setPerchasePerson(java.lang.String perchasePerson) {
		set("perchase_person", perchasePerson);
	}
	
	public java.lang.String getPerchasePerson() {
		return getStr("perchase_person");
	}

	public void setDeliveryNo(java.lang.String deliveryNo) {
		set("delivery_no", deliveryNo);
	}
	
	public java.lang.String getDeliveryNo() {
		return getStr("delivery_no");
	}

	public void setDeliveryIndex(java.lang.String deliveryIndex) {
		set("delivery_index", deliveryIndex);
	}
	
	public java.lang.String getDeliveryIndex() {
		return getStr("delivery_index");
	}

	public void setInvoiceNo(java.lang.String invoiceNo) {
		set("invoice_no", invoiceNo);
	}
	
	public java.lang.String getInvoiceNo() {
		return getStr("invoice_no");
	}

	public void setCreateBy(java.lang.Integer createBy) {
		set("create_by", createBy);
	}
	
	public java.lang.Integer getCreateBy() {
		return getInt("create_by");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setContract(java.lang.String contract) {
		set("contract", contract);
	}
	
	public java.lang.String getContract() {
		return getStr("contract");
	}

	public void setIsSetlled(java.lang.Boolean isSetlled) {
		set("is_setlled", isSetlled);
	}
	
	public java.lang.Boolean getIsSetlled() {
		return get("is_setlled");
	}

	public void setOrderId(java.lang.Integer orderId) {
		set("order_id", orderId);
	}
	
	public java.lang.Integer getOrderId() {
		return getInt("order_id");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

}
