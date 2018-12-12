package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseFyBusinessOrder<M extends BaseFyBusinessOrder<M>> extends Model<M> implements IBean {

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

	public void setExecuStatus(java.lang.String execuStatus) {
		set("execu_status", execuStatus);
	}
	
	public java.lang.String getExecuStatus() {
		return getStr("execu_status");
	}

	public void setCustomerNo(java.lang.String customerNo) {
		set("customer_no", customerNo);
	}
	
	public java.lang.String getCustomerNo() {
		return getStr("customer_no");
	}

	public void setOrderDate(java.util.Date orderDate) {
		set("order_date", orderDate);
	}
	
	public java.util.Date getOrderDate() {
		return get("order_date");
	}

	public void setDeliveryDate(java.util.Date deliveryDate) {
		set("delivery_date", deliveryDate);
	}
	
	public java.util.Date getDeliveryDate() {
		return get("delivery_date");
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

	public void setMapNo(java.lang.String mapNo) {
		set("map_no", mapNo);
	}
	
	public java.lang.String getMapNo() {
		return getStr("map_no");
	}

	public void setTechnology(java.lang.String technology) {
		set("technology", technology);
	}
	
	public java.lang.String getTechnology() {
		return getStr("technology");
	}

	public void setMachiningRequire(java.lang.String machiningRequire) {
		set("machining_require", machiningRequire);
	}
	
	public java.lang.String getMachiningRequire() {
		return getStr("machining_require");
	}

	public void setQuantity(java.math.BigDecimal quantity) {
		set("quantity", quantity);
	}
	
	public java.math.BigDecimal getQuantity() {
		return get("quantity");
	}

	public void setUnit(java.lang.Integer unit) {
		set("unit", unit);
	}
	
	public java.lang.Integer getUnit() {
		return getInt("unit");
	}

	public void setUntaxedCost(java.math.BigDecimal untaxedCost) {
		set("untaxed_cost", untaxedCost);
	}
	
	public java.math.BigDecimal getUntaxedCost() {
		return get("untaxed_cost");
	}

	public void setAmount(java.math.BigDecimal amount) {
		set("amount", amount);
	}
	
	public java.math.BigDecimal getAmount() {
		return get("amount");
	}

	public void setTaxRate(java.math.BigDecimal taxRate) {
		set("tax_rate", taxRate);
	}
	
	public java.math.BigDecimal getTaxRate() {
		return get("tax_rate");
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

	public void setImportTime(java.util.Date importTime) {
		set("import_time", importTime);
	}
	
	public java.util.Date getImportTime() {
		return get("import_time");
	}

	public void setDistributeTime(java.util.Date distributeTime) {
		set("distribute_time", distributeTime);
	}
	
	public java.util.Date getDistributeTime() {
		return get("distribute_time");
	}

	public void setReceiveTime(java.util.Date receiveTime) {
		set("receive_time", receiveTime);
	}
	
	public java.util.Date getReceiveTime() {
		return get("receive_time");
	}

	public void setWarnTime(java.util.Date warnTime) {
		set("warn_time", warnTime);
	}
	
	public java.util.Date getWarnTime() {
		return get("warn_time");
	}

	public void setDraw(java.lang.Integer draw) {
		set("draw", draw);
	}
	
	public java.lang.Integer getDraw() {
		return getInt("draw");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setHangTime(java.util.Date hangTime) {
		set("hang_time", hangTime);
	}
	
	public java.util.Date getHangTime() {
		return get("hang_time");
	}

	public void setHangStatus(java.lang.String hangStatus) {
		set("hang_status", hangStatus);
	}
	
	public java.lang.String getHangStatus() {
		return getStr("hang_status");
	}

	public void setHangQuantity(java.math.BigDecimal hangQuantity) {
		set("hang_quantity", hangQuantity);
	}
	
	public java.math.BigDecimal getHangQuantity() {
		return get("hang_quantity");
	}

	public void setUnhangQuantity(java.math.BigDecimal unhangQuantity) {
		set("unhang_quantity", unhangQuantity);
	}
	
	public java.math.BigDecimal getUnhangQuantity() {
		return get("unhang_quantity");
	}

	public void setCateTmp(java.lang.String cateTmp) {
		set("cate_tmp", cateTmp);
	}
	
	public java.lang.String getCateTmp() {
		return getStr("cate_tmp");
	}

	public void setPlanTmp(java.lang.String planTmp) {
		set("plan_tmp", planTmp);
	}
	
	public java.lang.String getPlanTmp() {
		return getStr("plan_tmp");
	}

	public void setMapTmp(java.lang.String mapTmp) {
		set("map_tmp", mapTmp);
	}
	
	public java.lang.String getMapTmp() {
		return getStr("map_tmp");
	}

	public void setUnitTmp(java.lang.String unitTmp) {
		set("unit_tmp", unitTmp);
	}
	
	public java.lang.String getUnitTmp() {
		return getStr("unit_tmp");
	}

	public void setTaxTmp(java.lang.String taxTmp) {
		set("tax_tmp", taxTmp);
	}
	
	public java.lang.String getTaxTmp() {
		return getStr("tax_tmp");
	}

	public void setDistributeTo(java.lang.String distributeTo) {
		set("distribute_to", distributeTo);
	}
	
	public java.lang.String getDistributeTo() {
		return getStr("distribute_to");
	}

	public void setDistributeAttr(java.lang.String distributeAttr) {
		set("distribute_attr", distributeAttr);
	}
	
	public java.lang.String getDistributeAttr() {
		return getStr("distribute_attr");
	}

	public void setIsDistribute(java.lang.Boolean isDistribute) {
		set("is_distribute", isDistribute);
	}
	
	public java.lang.Boolean getIsDistribute() {
		return get("is_distribute");
	}

	public void setOrderby(java.lang.Integer orderby) {
		set("orderby", orderby);
	}
	
	public java.lang.Integer getOrderby() {
		return getInt("orderby");
	}

	public void setIsCreateNext(java.lang.Boolean isCreateNext) {
		set("is_create_next", isCreateNext);
	}
	
	public java.lang.Boolean getIsCreateNext() {
		return get("is_create_next");
	}

	public void setIsCreatePlan(java.lang.Boolean isCreatePlan) {
		set("is_create_plan", isCreatePlan);
	}
	
	public java.lang.Boolean getIsCreatePlan() {
		return get("is_create_plan");
	}

	public void setPlanTime(java.util.Date planTime) {
		set("plan_time", planTime);
	}
	
	public java.util.Date getPlanTime() {
		return get("plan_time");
	}

	public void setPlanFinshTime(java.util.Date planFinshTime) {
		set("plan_finsh_time", planFinshTime);
	}
	
	public java.util.Date getPlanFinshTime() {
		return get("plan_finsh_time");
	}

	public void setPlanRemark(java.lang.String planRemark) {
		set("plan_remark", planRemark);
	}
	
	public java.lang.String getPlanRemark() {
		return getStr("plan_remark");
	}

	public void setInWarehouseTime(java.util.Date inWarehouseTime) {
		set("in_warehouse_time", inWarehouseTime);
	}
	
	public java.util.Date getInWarehouseTime() {
		return get("in_warehouse_time");
	}

	public void setFinishTime(java.util.Date finishTime) {
		set("finish_time", finishTime);
	}
	
	public java.util.Date getFinishTime() {
		return get("finish_time");
	}

	public void setIsCreateInHouse(java.lang.Boolean isCreateInHouse) {
		set("is_create_in_house", isCreateInHouse);
	}
	
	public java.lang.Boolean getIsCreateInHouse() {
		return get("is_create_in_house");
	}

	public void setDisTo(java.lang.Integer disTo) {
		set("dis_to", disTo);
	}
	
	public java.lang.Integer getDisTo() {
		return getInt("dis_to");
	}

	public void setHandleStatus(java.lang.String handleStatus) {
		set("handle_status", handleStatus);
	}
	
	public java.lang.String getHandleStatus() {
		return getStr("handle_status");
	}

	public void setIsFinishPurchase(java.lang.Boolean isFinishPurchase) {
		set("is_finish_purchase", isFinishPurchase);
	}
	
	public java.lang.Boolean getIsFinishPurchase() {
		return get("is_finish_purchase");
	}

	public void setStorageQuantity(java.math.BigDecimal storageQuantity) {
		set("storage_quantity", storageQuantity);
	}
	
	public java.math.BigDecimal getStorageQuantity() {
		return get("storage_quantity");
	}

	public void setOutQuantity(java.math.BigDecimal outQuantity) {
		set("out_quantity", outQuantity);
	}
	
	public java.math.BigDecimal getOutQuantity() {
		return get("out_quantity");
	}

	public void setCustomer(java.lang.Integer customer) {
		set("customer", customer);
	}
	
	public java.lang.Integer getCustomer() {
		return getInt("customer");
	}

	public void setHasInQuantity(java.math.BigDecimal hasInQuantity) {
		set("has_in_quantity", hasInQuantity);
	}
	
	public java.math.BigDecimal getHasInQuantity() {
		return get("has_in_quantity");
	}

	public void setHangAccount(java.math.BigDecimal hangAccount) {
		set("hang_account", hangAccount);
	}
	
	public java.math.BigDecimal getHangAccount() {
		return get("hang_account");
	}

	public void setWwQuantity(java.math.BigDecimal wwQuantity) {
		set("ww_quantity", wwQuantity);
	}
	
	public java.math.BigDecimal getWwQuantity() {
		return get("ww_quantity");
	}

	public void setWwUnquantity(java.math.BigDecimal wwUnquantity) {
		set("ww_unquantity", wwUnquantity);
	}
	
	public java.math.BigDecimal getWwUnquantity() {
		return get("ww_unquantity");
	}

	public void setWwHangAmount(java.lang.Integer wwHangAmount) {
		set("ww_hang_amount", wwHangAmount);
	}
	
	public java.lang.Integer getWwHangAmount() {
		return getInt("ww_hang_amount");
	}

	public void setWwUnhangAmount(java.lang.Integer wwUnhangAmount) {
		set("ww_unhang_amount", wwUnhangAmount);
	}
	
	public java.lang.Integer getWwUnhangAmount() {
		return getInt("ww_unhang_amount");
	}

	public void setSendAddress(java.lang.String sendAddress) {
		set("send_address", sendAddress);
	}
	
	public java.lang.String getSendAddress() {
		return getStr("send_address");
	}

	public void setIsFinsh(java.lang.Boolean isFinsh) {
		set("is_finsh", isFinsh);
	}
	
	public java.lang.Boolean getIsFinsh() {
		return get("is_finsh");
	}

	public void setIsAllInHouse(java.lang.Boolean isAllInHouse) {
		set("is_all_in_house", isAllInHouse);
	}
	
	public java.lang.Boolean getIsAllInHouse() {
		return get("is_all_in_house");
	}

	public void setReadyId(java.lang.Integer readyId) {
		set("ready_id", readyId);
	}
	
	public java.lang.Integer getReadyId() {
		return getInt("ready_id");
	}

	public void setModelNo(java.lang.String modelNo) {
		set("model_no", modelNo);
	}
	
	public java.lang.String getModelNo() {
		return getStr("model_no");
	}

	public void setOutHouseDate(java.util.Date outHouseDate) {
		set("out_house_date", outHouseDate);
	}
	
	public java.util.Date getOutHouseDate() {
		return get("out_house_date");
	}

	public void setWeiwaiCate(java.lang.String weiwaiCate) {
		set("weiwai_cate", weiwaiCate);
	}
	
	public java.lang.String getWeiwaiCate() {
		return getStr("weiwai_cate");
	}

	public void setTotalMapNo(java.lang.String totalMapNo) {
		set("total_map_no", totalMapNo);
	}
	
	public java.lang.String getTotalMapNo() {
		return getStr("total_map_no");
	}

	public void setOrderStatus(java.lang.Integer orderStatus) {
		set("order_status", orderStatus);
	}
	
	public java.lang.Integer getOrderStatus() {
		return getInt("order_status");
	}

	public void setInhouseStatus(java.lang.String inhouseStatus) {
		set("inhouse_status", inhouseStatus);
	}
	
	public java.lang.String getInhouseStatus() {
		return getStr("inhouse_status");
	}

	public void setInhouseDate(java.util.Date inhouseDate) {
		set("inhouse_date", inhouseDate);
	}
	
	public java.util.Date getInhouseDate() {
		return get("inhouse_date");
	}

	public void setIsFinshProduct(java.lang.String isFinshProduct) {
		set("is_finsh_product", isFinshProduct);
	}
	
	public java.lang.String getIsFinshProduct() {
		return getStr("is_finsh_product");
	}

}
