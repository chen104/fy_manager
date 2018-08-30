package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyReadyPurchase<M extends BaseFyReadyPurchase<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setPurchaseNo(java.lang.String purchaseNo) {
		set("purchase_no", purchaseNo);
	}
	
	public java.lang.String getPurchaseNo() {
		return getStr("purchase_no");
	}

	public void setPurchaseDate(java.util.Date purchaseDate) {
		set("purchase_date", purchaseDate);
	}
	
	public java.util.Date getPurchaseDate() {
		return get("purchase_date");
	}

	public void setPurchaseSingleWeight(java.math.BigDecimal purchaseSingleWeight) {
		set("purchase_single_weight", purchaseSingleWeight);
	}
	
	public java.math.BigDecimal getPurchaseSingleWeight() {
		return get("purchase_single_weight");
	}

	public void setPurchaseWeight(java.math.BigDecimal purchaseWeight) {
		set("purchase_weight", purchaseWeight);
	}
	
	public java.math.BigDecimal getPurchaseWeight() {
		return get("purchase_weight");
	}

	public void setPurchaseCost(java.math.BigDecimal purchaseCost) {
		set("purchase_cost", purchaseCost);
	}
	
	public java.math.BigDecimal getPurchaseCost() {
		return get("purchase_cost");
	}

	public void setPurchaseAccount(java.math.BigDecimal purchaseAccount) {
		set("purchase_account", purchaseAccount);
	}
	
	public java.math.BigDecimal getPurchaseAccount() {
		return get("purchase_account");
	}

	public void setReadyOrderId(java.lang.Integer readyOrderId) {
		set("ready_order_id", readyOrderId);
	}
	
	public java.lang.Integer getReadyOrderId() {
		return getInt("ready_order_id");
	}

	public void setSupplierId(java.lang.Integer supplierId) {
		set("supplier_id", supplierId);
	}
	
	public java.lang.Integer getSupplierId() {
		return getInt("supplier_id");
	}

	public void setPurchaseRemark(java.lang.String purchaseRemark) {
		set("purchase_remark", purchaseRemark);
	}
	
	public java.lang.String getPurchaseRemark() {
		return getStr("purchase_remark");
	}

	public void setCanDownload(java.lang.Boolean canDownload) {
		set("can_download", canDownload);
	}
	
	public java.lang.Boolean getCanDownload() {
		return get("can_download");
	}

	public void setPurchaseQuantity(java.math.BigDecimal purchaseQuantity) {
		set("purchase_quantity", purchaseQuantity);
	}
	
	public java.math.BigDecimal getPurchaseQuantity() {
		return get("purchase_quantity");
	}

	public void setPurchaseTitle(java.lang.String purchaseTitle) {
		set("purchase_title", purchaseTitle);
	}
	
	public java.lang.String getPurchaseTitle() {
		return getStr("purchase_title");
	}

}
