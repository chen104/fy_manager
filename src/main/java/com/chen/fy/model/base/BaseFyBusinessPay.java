package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyBusinessPay<M extends BaseFyBusinessPay<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setCheckResult(java.lang.String checkResult) {
		set("check_result", checkResult);
	}
	
	public java.lang.String getCheckResult() {
		return getStr("check_result");
	}

	public void setSupplierId(java.lang.Integer supplierId) {
		set("supplier_id", supplierId);
	}
	
	public java.lang.Integer getSupplierId() {
		return getInt("supplier_id");
	}

	public void setOrderNo(java.lang.String orderNo) {
		set("order_no", orderNo);
	}
	
	public java.lang.String getOrderNo() {
		return getStr("order_no");
	}

	public void setTatolAmount(java.math.BigDecimal tatolAmount) {
		set("tatol_amount", tatolAmount);
	}
	
	public java.math.BigDecimal getTatolAmount() {
		return get("tatol_amount");
	}

	public void setQualityDeduction(java.math.BigDecimal qualityDeduction) {
		set("quality_deduction", qualityDeduction);
	}
	
	public java.math.BigDecimal getQualityDeduction() {
		return get("quality_deduction");
	}

	public void setInWarehouseTime(java.util.Date inWarehouseTime) {
		set("in_warehouse_time", inWarehouseTime);
	}
	
	public java.util.Date getInWarehouseTime() {
		return get("in_warehouse_time");
	}

	public void setInFrom(java.lang.String inFrom) {
		set("in_from", inFrom);
	}
	
	public java.lang.String getInFrom() {
		return getStr("in_from");
	}

	public void setPayQuantity(java.lang.Integer payQuantity) {
		set("pay_quantity", payQuantity);
	}
	
	public java.lang.Integer getPayQuantity() {
		return getInt("pay_quantity");
	}

	public void setParentId(java.lang.Integer parentId) {
		set("parent_id", parentId);
	}
	
	public java.lang.Integer getParentId() {
		return getInt("parent_id");
	}

	public void setOrderId(java.lang.Integer orderId) {
		set("order_id", orderId);
	}
	
	public java.lang.Integer getOrderId() {
		return getInt("order_id");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setIsDownload(java.lang.Boolean isDownload) {
		set("is_download", isDownload);
	}
	
	public java.lang.Boolean getIsDownload() {
		return get("is_download");
	}

	public void setIsPurchase(java.lang.Boolean isPurchase) {
		set("is_purchase", isPurchase);
	}
	
	public java.lang.Boolean getIsPurchase() {
		return get("is_purchase");
	}

	public void setShouldPay(java.math.BigDecimal shouldPay) {
		set("should_pay", shouldPay);
	}
	
	public java.math.BigDecimal getShouldPay() {
		return get("should_pay");
	}

	public void setHangDate(java.util.Date hangDate) {
		set("hang_date", hangDate);
	}
	
	public java.util.Date getHangDate() {
		return get("hang_date");
	}

	public void setPayDate(java.util.Date payDate) {
		set("pay_date", payDate);
	}
	
	public java.util.Date getPayDate() {
		return get("pay_date");
	}

	public void setPurchaseDate(java.util.Date purchaseDate) {
		set("purchase_date", purchaseDate);
	}
	
	public java.util.Date getPurchaseDate() {
		return get("purchase_date");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setCreateBy(java.lang.Integer createBy) {
		set("create_by", createBy);
	}
	
	public java.lang.Integer getCreateBy() {
		return getInt("create_by");
	}

	public void setCheckTime(java.util.Date checkTime) {
		set("check_time", checkTime);
	}
	
	public java.util.Date getCheckTime() {
		return get("check_time");
	}

	public void setPurchaseName(java.lang.String purchaseName) {
		set("purchase_name", purchaseName);
	}
	
	public java.lang.String getPurchaseName() {
		return getStr("purchase_name");
	}

	public void setPurchaseQuantity(java.lang.Integer purchaseQuantity) {
		set("purchase_quantity", purchaseQuantity);
	}
	
	public java.lang.Integer getPurchaseQuantity() {
		return getInt("purchase_quantity");
	}

	public void setPurchaseCost(java.math.BigDecimal purchaseCost) {
		set("purchase_cost", purchaseCost);
	}
	
	public java.math.BigDecimal getPurchaseCost() {
		return get("purchase_cost");
	}

	public void setPurchaseNo(java.lang.String purchaseNo) {
		set("purchase_no", purchaseNo);
	}
	
	public java.lang.String getPurchaseNo() {
		return getStr("purchase_no");
	}

	public void setPurchaseAmount(java.math.BigDecimal purchaseAmount) {
		set("purchase_amount", purchaseAmount);
	}
	
	public java.math.BigDecimal getPurchaseAmount() {
		return get("purchase_amount");
	}

	public void setIsSetlled(java.lang.Boolean isSetlled) {
		set("is_setlled", isSetlled);
	}
	
	public java.lang.Boolean getIsSetlled() {
		return get("is_setlled");
	}

	public void setUnpassQuantity(java.lang.Integer unpassQuantity) {
		set("unpass_quantity", unpassQuantity);
	}
	
	public java.lang.Integer getUnpassQuantity() {
		return getInt("unpass_quantity");
	}

	public void setIsHasTax(java.lang.String isHasTax) {
		set("is_has_tax", isHasTax);
	}
	
	public java.lang.String getIsHasTax() {
		return getStr("is_has_tax");
	}

}
