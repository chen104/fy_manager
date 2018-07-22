package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyBusinessAssist<M extends BaseFyBusinessAssist<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setAssistDate(java.util.Date assistDate) {
		set("assist_date", assistDate);
	}
	
	public java.util.Date getAssistDate() {
		return get("assist_date");
	}

	public void setWorkBillNo(java.lang.String workBillNo) {
		set("work_bill_no", workBillNo);
	}
	
	public java.lang.String getWorkBillNo() {
		return getStr("work_bill_no");
	}

	public void setSupplierId(java.lang.Integer supplierId) {
		set("supplier_id", supplierId);
	}
	
	public java.lang.Integer getSupplierId() {
		return getInt("supplier_id");
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

	public void setMachiningRequire(java.lang.String machiningRequire) {
		set("machining_require", machiningRequire);
	}
	
	public java.lang.String getMachiningRequire() {
		return getStr("machining_require");
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

	public void setSingle(java.lang.Integer single) {
		set("single", single);
	}
	
	public java.lang.Integer getSingle() {
		return getInt("single");
	}

	public void setAssistCost(java.math.BigDecimal assistCost) {
		set("assist_cost", assistCost);
	}
	
	public java.math.BigDecimal getAssistCost() {
		return get("assist_cost");
	}

	public void setAssistAccount(java.math.BigDecimal assistAccount) {
		set("assist_account", assistAccount);
	}
	
	public java.math.BigDecimal getAssistAccount() {
		return get("assist_account");
	}

	public void setTax(java.lang.Integer tax) {
		set("tax", tax);
	}
	
	public java.lang.Integer getTax() {
		return getInt("tax");
	}

	public void setTaxAmount(java.math.BigDecimal taxAmount) {
		set("tax_amount", taxAmount);
	}
	
	public java.math.BigDecimal getTaxAmount() {
		return get("tax_amount");
	}

	public void setTatolAmount(java.math.BigDecimal tatolAmount) {
		set("tatol_amount", tatolAmount);
	}
	
	public java.math.BigDecimal getTatolAmount() {
		return get("tatol_amount");
	}

	public void setAssistSupplierId(java.lang.Integer assistSupplierId) {
		set("assist_supplier_id", assistSupplierId);
	}
	
	public java.lang.Integer getAssistSupplierId() {
		return getInt("assist_supplier_id");
	}

	public void setAssistProcess(java.lang.String assistProcess) {
		set("assist_process", assistProcess);
	}
	
	public java.lang.String getAssistProcess() {
		return getStr("assist_process");
	}

	public void setRunTime(java.lang.Integer runTime) {
		set("run_time", runTime);
	}
	
	public java.lang.Integer getRunTime() {
		return getInt("run_time");
	}

	public void setBacktime(java.util.Date backtime) {
		set("backtime", backtime);
	}
	
	public java.util.Date getBacktime() {
		return get("backtime");
	}

	public void setCheckResult(java.lang.String checkResult) {
		set("check_result", checkResult);
	}
	
	public java.lang.String getCheckResult() {
		return getStr("check_result");
	}

	public void setIsCreatePay(java.lang.Boolean isCreatePay) {
		set("is_create_pay", isCreatePay);
	}
	
	public java.lang.Boolean getIsCreatePay() {
		return get("is_create_pay");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
