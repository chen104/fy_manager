package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyBusinessPaybill<M extends BaseFyBusinessPaybill<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setCategoryId(java.lang.Integer categoryId) {
		set("category_id", categoryId);
	}
	
	public java.lang.Integer getCategoryId() {
		return getInt("category_id");
	}

	public void setPlanerId(java.lang.Integer planerId) {
		set("planer_id", planerId);
	}
	
	public java.lang.Integer getPlanerId() {
		return getInt("planer_id");
	}

	public void setWorkOrderNo(java.lang.String workOrderNo) {
		set("work_order_no", workOrderNo);
	}
	
	public java.lang.String getWorkOrderNo() {
		return getStr("work_order_no");
	}

	public void setDeliveryNo(java.lang.String deliveryNo) {
		set("delivery_no", deliveryNo);
	}
	
	public java.lang.String getDeliveryNo() {
		return getStr("delivery_no");
	}

	public void setCommodityName(java.lang.String commodityName) {
		set("commodity_name", commodityName);
	}
	
	public java.lang.String getCommodityName() {
		return getStr("commodity_name");
	}

	public void setCommoditySpec(java.lang.String commoditySpec) {
		set("commodity_spec", commoditySpec);
	}
	
	public java.lang.String getCommoditySpec() {
		return getStr("commodity_spec");
	}

	public void setMapNo(java.lang.Integer mapNo) {
		set("map_no", mapNo);
	}
	
	public java.lang.Integer getMapNo() {
		return getInt("map_no");
	}

	public void setQuantity(java.lang.Integer quantity) {
		set("quantity", quantity);
	}
	
	public java.lang.Integer getQuantity() {
		return getInt("quantity");
	}

	public void setUnit(java.lang.Integer unit) {
		set("unit", unit);
	}
	
	public java.lang.Integer getUnit() {
		return getInt("unit");
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

	public void setWeiwaiQuantity(java.lang.Integer weiwaiQuantity) {
		set("weiwai_quantity", weiwaiQuantity);
	}
	
	public java.lang.Integer getWeiwaiQuantity() {
		return getInt("weiwai_quantity");
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

	public void setTax(java.lang.Integer tax) {
		set("tax", tax);
	}
	
	public java.lang.Integer getTax() {
		return getInt("tax");
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

	public void setHangNo(java.lang.String hangNo) {
		set("hang_no", hangNo);
	}
	
	public java.lang.String getHangNo() {
		return getStr("hang_no");
	}

	public void setHangQuantity(java.lang.Integer hangQuantity) {
		set("hang_quantity", hangQuantity);
	}
	
	public java.lang.Integer getHangQuantity() {
		return getInt("hang_quantity");
	}

	public void setHangAmount(java.math.BigDecimal hangAmount) {
		set("hang_amount", hangAmount);
	}
	
	public java.math.BigDecimal getHangAmount() {
		return get("hang_amount");
	}

	public void setUnhangQuantity(java.lang.Integer unhangQuantity) {
		set("unhang_quantity", unhangQuantity);
	}
	
	public java.lang.Integer getUnhangQuantity() {
		return getInt("unhang_quantity");
	}

	public void setBillTime(java.util.Date billTime) {
		set("bill_time", billTime);
	}
	
	public java.util.Date getBillTime() {
		return get("bill_time");
	}

	public void setIsCreatePaybill(java.lang.Boolean isCreatePaybill) {
		set("is_create_paybill", isCreatePaybill);
	}
	
	public java.lang.Boolean getIsCreatePaybill() {
		return get("is_create_paybill");
	}

	public void setBillCreatePaybill(java.util.Date billCreatePaybill) {
		set("bill_create_paybill", billCreatePaybill);
	}
	
	public java.util.Date getBillCreatePaybill() {
		return get("bill_create_paybill");
	}

}