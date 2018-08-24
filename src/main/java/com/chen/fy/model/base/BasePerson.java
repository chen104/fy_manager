package com.chen.fy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BasePerson<M extends BasePerson<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public void setNativePlace(java.lang.String nativePlace) {
		set("native_place", nativePlace);
	}
	
	public java.lang.String getNativePlace() {
		return getStr("native_place");
	}

	public void setSex(java.lang.Boolean sex) {
		set("sex", sex);
	}
	
	public java.lang.Boolean getSex() {
		return get("sex");
	}

	public void setBirthDate(java.util.Date birthDate) {
		set("birth_date", birthDate);
	}
	
	public java.util.Date getBirthDate() {
		return get("birth_date");
	}

	public void setEntryDate(java.util.Date entryDate) {
		set("entry_date", entryDate);
	}
	
	public java.util.Date getEntryDate() {
		return get("entry_date");
	}

	public void setQuitDate(java.util.Date quitDate) {
		set("quit_date", quitDate);
	}
	
	public java.util.Date getQuitDate() {
		return get("quit_date");
	}

	public void setContactType(java.lang.String contactType) {
		set("contact_type", contactType);
	}
	
	public java.lang.String getContactType() {
		return getStr("contact_type");
	}

	public void setEducation(java.lang.String education) {
		set("education", education);
	}
	
	public java.lang.String getEducation() {
		return getStr("education");
	}

	public void setPoliticalStatus(java.lang.String politicalStatus) {
		set("political_status", politicalStatus);
	}
	
	public java.lang.String getPoliticalStatus() {
		return getStr("political_status");
	}

	public void setAccommodation(java.lang.String accommodation) {
		set("accommodation", accommodation);
	}
	
	public java.lang.String getAccommodation() {
		return getStr("accommodation");
	}

	public void setPost(java.lang.String post) {
		set("post", post);
	}
	
	public java.lang.String getPost() {
		return getStr("post");
	}

	public void setDepartmentId(java.lang.Integer departmentId) {
		set("department_id", departmentId);
	}
	
	public java.lang.Integer getDepartmentId() {
		return getInt("department_id");
	}

	public void setJobStatus(java.lang.String jobStatus) {
		set("job_status", jobStatus);
	}
	
	public java.lang.String getJobStatus() {
		return getStr("job_status");
	}

	public void setCurrentSalary(java.math.BigDecimal currentSalary) {
		set("current_salary", currentSalary);
	}
	
	public java.math.BigDecimal getCurrentSalary() {
		return get("current_salary");
	}

	public void setAdjustBaseSalary1(java.math.BigDecimal adjustBaseSalary1) {
		set("adjust_base_salary1", adjustBaseSalary1);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary1() {
		return get("adjust_base_salary1");
	}

	public void setAdjustDate1(java.util.Date adjustDate1) {
		set("adjust_date1", adjustDate1);
	}
	
	public java.util.Date getAdjustDate1() {
		return get("adjust_date1");
	}

	public void setAdjustBaseSalary2(java.math.BigDecimal adjustBaseSalary2) {
		set("adjust_base_salary2", adjustBaseSalary2);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary2() {
		return get("adjust_base_salary2");
	}

	public void setAdjustDate2(java.util.Date adjustDate2) {
		set("adjust_date2", adjustDate2);
	}
	
	public java.util.Date getAdjustDate2() {
		return get("adjust_date2");
	}

	public void setAdjustBaseSalary3(java.math.BigDecimal adjustBaseSalary3) {
		set("adjust_base_salary3", adjustBaseSalary3);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary3() {
		return get("adjust_base_salary3");
	}

	public void setAdjustBaseSalary4(java.math.BigDecimal adjustBaseSalary4) {
		set("adjust_base_salary4", adjustBaseSalary4);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary4() {
		return get("adjust_base_salary4");
	}

	public void setAdjustBaseSalary5(java.math.BigDecimal adjustBaseSalary5) {
		set("adjust_base_salary5", adjustBaseSalary5);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary5() {
		return get("adjust_base_salary5");
	}

	public void setAdjustDate3(java.util.Date adjustDate3) {
		set("adjust_date3", adjustDate3);
	}
	
	public java.util.Date getAdjustDate3() {
		return get("adjust_date3");
	}

	public void setAdjustDate4(java.util.Date adjustDate4) {
		set("adjust_date4", adjustDate4);
	}
	
	public java.util.Date getAdjustDate4() {
		return get("adjust_date4");
	}

	public void setAdjustDate5(java.util.Date adjustDate5) {
		set("adjust_date5", adjustDate5);
	}
	
	public java.util.Date getAdjustDate5() {
		return get("adjust_date5");
	}

	public void setAdjustBaseSalary6(java.math.BigDecimal adjustBaseSalary6) {
		set("adjust_base_salary6", adjustBaseSalary6);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary6() {
		return get("adjust_base_salary6");
	}

	public void setAdjustDate6(java.util.Date adjustDate6) {
		set("adjust_date6", adjustDate6);
	}
	
	public java.util.Date getAdjustDate6() {
		return get("adjust_date6");
	}

	public void setAdjustBaseSalary7(java.math.BigDecimal adjustBaseSalary7) {
		set("adjust_base_salary7", adjustBaseSalary7);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary7() {
		return get("adjust_base_salary7");
	}

	public void setAdjustDate7(java.util.Date adjustDate7) {
		set("adjust_date7", adjustDate7);
	}
	
	public java.util.Date getAdjustDate7() {
		return get("adjust_date7");
	}

	public void setAdjustBaseSalary8(java.math.BigDecimal adjustBaseSalary8) {
		set("adjust_base_salary8", adjustBaseSalary8);
	}
	
	public java.math.BigDecimal getAdjustBaseSalary8() {
		return get("adjust_base_salary8");
	}

	public void setAdjustDate8(java.util.Date adjustDate8) {
		set("adjust_date8", adjustDate8);
	}
	
	public java.util.Date getAdjustDate8() {
		return get("adjust_date8");
	}

	public void setEthnicGroup(java.lang.String ethnicGroup) {
		set("ethnic_group", ethnicGroup);
	}
	
	public java.lang.String getEthnicGroup() {
		return getStr("ethnic_group");
	}

	public void setDocNo(java.lang.String docNo) {
		set("doc_no", docNo);
	}
	
	public java.lang.String getDocNo() {
		return getStr("doc_no");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}
	
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}
	
	public java.lang.String getPhone() {
		return getStr("phone");
	}

	public void setQualityCheck(java.lang.String qualityCheck) {
		set("quality_check", qualityCheck);
	}
	
	public java.lang.String getQualityCheck() {
		return getStr("quality_check");
	}

	public void setAdministrativeCheck(java.lang.String administrativeCheck) {
		set("administrative_check", administrativeCheck);
	}
	
	public java.lang.String getAdministrativeCheck() {
		return getStr("administrative_check");
	}

	public void setRoomNo(java.lang.String roomNo) {
		set("room_no", roomNo);
	}
	
	public java.lang.String getRoomNo() {
		return getStr("room_no");
	}

	public void setMarriage(java.lang.String marriage) {
		set("marriage", marriage);
	}
	
	public java.lang.String getMarriage() {
		return getStr("marriage");
	}

	public void setRequstOutDate(java.util.Date requstOutDate) {
		set("requst_out_date", requstOutDate);
	}
	
	public java.util.Date getRequstOutDate() {
		return get("requst_out_date");
	}

	public void setEntrySalary(java.math.BigDecimal entrySalary) {
		set("entry_salary", entrySalary);
	}
	
	public java.math.BigDecimal getEntrySalary() {
		return get("entry_salary");
	}

}
