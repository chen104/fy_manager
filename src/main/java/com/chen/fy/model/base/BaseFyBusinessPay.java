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

	public void setWeiwaiQuantity(java.math.BigDecimal weiwaiQuantity) {
		set("weiwai_quantity", weiwaiQuantity);
	}
	
	public java.math.BigDecimal getWeiwaiQuantity() {
		return get("weiwai_quantity");
	}

	public void setWeiwaiCost(java.math.BigDecimal weiwaiCost) {
		set("weiwai_cost", weiwaiCost);
	}
	
	public java.math.BigDecimal getWeiwaiCost() {
		return get("weiwai_cost");
	}

	public void setWeiwaiAccount(java.math.BigDecimal weiwaiAccount) {
		set("weiwai_account", weiwaiAccount);
	}
	
	public java.math.BigDecimal getWeiwaiAccount() {
		return get("weiwai_account");
	}

	public void setTaxRate(java.math.BigDecimal taxRate) {
		set("tax_rate", taxRate);
	}
	
	public java.math.BigDecimal getTaxRate() {
		return get("tax_rate");
	}

	public void setTatolAmount(java.math.BigDecimal tatolAmount) {
		set("tatol_amount", tatolAmount);
	}
	
	public java.math.BigDecimal getTatolAmount() {
		return get("tatol_amount");
	}

	public void setDiscount(java.math.BigDecimal discount) {
		set("discount", discount);
	}
	
	public java.math.BigDecimal getDiscount() {
		return get("discount");
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

	public void setRealInQuantity(java.math.BigDecimal realInQuantity) {
		set("real_in_quantity", realInQuantity);
	}
	
	public java.math.BigDecimal getRealInQuantity() {
		return get("real_in_quantity");
	}

	public void setCheckTime(java.util.Date checkTime) {
		set("check_time", checkTime);
	}
	
	public java.util.Date getCheckTime() {
		return get("check_time");
	}

	public void setIsCreateBill(java.lang.Boolean isCreateBill) {
		set("is_create_bill", isCreateBill);
	}
	
	public java.lang.Boolean getIsCreateBill() {
		return get("is_create_bill");
	}

	public void setBillCreateTime(java.util.Date billCreateTime) {
		set("bill_create_time", billCreateTime);
	}
	
	public java.util.Date getBillCreateTime() {
		return get("bill_create_time");
	}

	public void setSupplierName(java.lang.String supplierName) {
		set("supplier_name", supplierName);
	}
	
	public java.lang.String getSupplierName() {
		return getStr("supplier_name");
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

	public void setCreateMonth(java.lang.Integer createMonth) {
		set("create_month", createMonth);
	}
	
	public java.lang.Integer getCreateMonth() {
		return getInt("create_month");
	}

	public void setPayMonth(java.lang.Integer payMonth) {
		set("pay_month", payMonth);
	}
	
	public java.lang.Integer getPayMonth() {
		return getInt("pay_month");
	}

	public void setCanDownload(java.lang.Boolean canDownload) {
		set("can_download", canDownload);
	}
	
	public java.lang.Boolean getCanDownload() {
		return get("can_download");
	}

	public void setIsWw(java.lang.Boolean isWw) {
		set("is_ww", isWw);
	}
	
	public java.lang.Boolean getIsWw() {
		return get("is_ww");
	}

	public void setInHouseId(java.lang.Integer inHouseId) {
		set("in_house_id", inHouseId);
	}
	
	public java.lang.Integer getInHouseId() {
		return getInt("in_house_id");
	}

	public void setShouldPay(java.math.BigDecimal shouldPay) {
		set("should_pay", shouldPay);
	}
	
	public java.math.BigDecimal getShouldPay() {
		return get("should_pay");
	}

	public void setRealAccount(java.math.BigDecimal realAccount) {
		set("real_account", realAccount);
	}
	
	public java.math.BigDecimal getRealAccount() {
		return get("real_account");
	}

	public void setCreateDate(java.util.Date createDate) {
		set("create_date", createDate);
	}
	
	public java.util.Date getCreateDate() {
		return get("create_date");
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

}
