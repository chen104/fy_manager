package com.chen.fy.controller.base;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Department;
import com.chen.fy.model.Person;
import com.chen.fy.service.PersonService;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class PersonController extends BaseController {
	PersonService service = PersonService.me;

	public void index() {
		String key = getPara("keyWord");
		Page<Person> personPage = null;
		setAttr("keyWord", key);
		String condition = getPara("condition");
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isEmpty(key)) {
			personPage = service.paginate(getParaToInt("p", 1));
		} else {
			if ("job_status".equals(condition)) {
				sb.append(" job_status like '%").append(key).append("%'");
			} else if ("name".equals(condition)) {
				sb.append(" name like '%").append(key).append("%'");
			} else if ("dateNumber".equals(condition)) {
				sb.append(" DATEDIFF(CURDATE(),entry_date) =").append(key).append(" ");
			} else if ("part_name".equals(condition)) {
				sb.append(" p.part_name like '%").append(key).append("%'");
			} else if ("room_no".equals(condition)) {
				sb.append(" room_no like '%").append(key).append("%'");
			}
			personPage = service.paginate(getParaToInt("p", 1), sb);
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");

	}

	public void edit() {
		Person person = service.dao.findById(getParaToInt("id"));
		setAttr("model", person);
		Integer did = person.getDepartmentId();
		if (did != null) {
			Department part = Department.dao.findById(did);
			setAttr("department", part);
		}
		setAttr("action", "update");
		setAttr("title", "修改人员");
		render("edit.html");
	}

	public void update() {
		Person person = getBean(Person.class, "model");
		Date adjustDate1 = getParaToDate("model.adjustDate1");
		person.setAdjustDate1(adjustDate1);
		Date adjustDate2 = getParaToDate("model.adjustDate2");
		person.setAdjustDate2(adjustDate2);
		Date adjustDate3 = getParaToDate("model.adjustDate3");
		person.setAdjustDate3(adjustDate3);
		Date adjustDate4 = getParaToDate("model.adjustDate4");
		person.setAdjustDate4(adjustDate4);
		Date adjustDate5 = getParaToDate("model.adjustDate5");
		person.setAdjustDate5(adjustDate5);
		Date adjustDate6 = getParaToDate("model.adjustDate6");
		person.setAdjustDate6(adjustDate6);
		Date adjustDate7 = getParaToDate("model.adjustDate7");
		person.setAdjustDate7(adjustDate7);
		Date adjustDate8 = getParaToDate("model.adjustDate8");
		person.setAdjustDate8(adjustDate8);

		boolean re = person.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   " + person.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void test() {

	}

	public void add() {
		setAttr("action", "save");
		setAttr("title", "新建人员");
		render("edit.html");

	}

	public void searchPersonJson() {
		String key = getPara("keyWord");
		Page<Person> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = service.paginate(getParaToInt("p", 1), 6);
		} else {
			personPage = service.paginate(getParaToInt("p", 1), key, 6);
		}
		renderJson(personPage);
	}

	public void searchPersonHtml() {
		String key = getPara("keyWord");
		Page<Person> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = service.paginate(getParaToInt("p", 1), 6);
		} else {
			personPage = service.paginate(getParaToInt("p", 1), key, 6);
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("searchPerson.html");

	}

	public void save() {
		Person person = getBean(Person.class, "model");
		boolean re = person.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  " + person.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = service.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	/**
	 * 
	 * 导入人员
	 */
	public void importPerson() {

		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();

				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				List<Person> list = new ArrayList<Person>();
				int rows = excel.getRowNum() + 1;
				for (int i = 1; i < rows; i++) {
					Person item = new Person();
					String cate = excel.getCellVal(i, 0);// 类别
					item.setJobStatus(cate);

					String name = excel.getCellVal(i, 1);// 姓名
					if (StringUtils.isEmpty(name)) {
						continue;
					}
					item.setName(name);

					String sex = excel.getCellVal(i, 2);// 性别
					if (sex.equals("男")) {
						item.setSex(true);
					} else if (sex.equals("女")) {
						item.setSex(false);
					}
					String ethnic_group = excel.getCellVal(i, 3);// 民族
					item.setEthnicGroup(ethnic_group);

					Date birthDate = excel.getDateValue(i, 4);// 出生日期
					item.setBirthDate(birthDate);

					String jiguan = excel.getCellVal(i, 5);// 籍贯
					item.setNativePlace(jiguan);

					Date entryDate = excel.getDateValue(i, 6);// 入职日期
					item.setEntryDate(entryDate);

					// Date requestoutDate = excel.getDateValue(i, 6);// 申请离职日期
					// item.setRequstOutDate(requestoutDate);

					Date outDate = excel.getDateValue(i, 7);// 离职日期
					item.setQuitDate(outDate);

					// excel.getCellVal(i,8);//当前时间
					// excel.getCellVal(i,9);//入职天数
					String phone = excel.getCellVal(i, 8);// 联系电话
					item.setContactType(phone);

					String partName = excel.getCellVal(i, 15);// 部门d
					if (StringUtils.isEmpty(partName)) {
						item.setDepartmentId(null);
					} else {
						Integer partId = getDeparetId(partName);
						item.setDepartmentId(partId);
					}

					String post = excel.getCellVal(i, 16);// 岗位职务
					item.setPost(post);

					String education = excel.getCellVal(i, 17);// 学历
					item.setEducation(education);

					String zhegnzhi = excel.getCellVal(i, 18);// 政治面貌
					item.setPoliticalStatus(zhegnzhi);

					String marray = excel.getCellVal(i, 19);// 婚姻
					item.setMarriage(marray);

					String commo = excel.getCellVal(i, 20);// 住宿情况
					item.setAccommodation(commo);

					String room_no = excel.getCellVal(i, 21);// 寝室号e
					item.setRoomNo(room_no);

					String entrySalory = excel.getCellVal(i, 22);// 入职底薪
					item.setEntrySalary(NumberUtils.isNumber(entrySalory) ? new BigDecimal(entrySalory) : null);

					String currentSalory = excel.getCellVal(i, 23);// 当前底薪
					item.setCurrentSalary(NumberUtils.isNumber(currentSalory) ? new BigDecimal(currentSalory) : null);

					Date justDate = excel.getDateValue(i, 24);// 调薪日期
					item.setAdjustDate1(justDate);

					String salory1 = excel.getCellVal(i, 25);// 调薪1
					item.setAdjustBaseSalary1(NumberUtils.isNumber(salory1) ? new BigDecimal(salory1) : null);

					Date justDate2 = excel.getDateValue(i, 26);// 调薪日期2
					item.setAdjustDate2(justDate2);

					String salory2 = excel.getCellVal(i, 27);// 调薪2
					item.setAdjustBaseSalary2(NumberUtils.isNumber(salory2) ? new BigDecimal(salory2) : null);

					Date justDate3 = excel.getDateValue(i, 28);// 调薪日期
					item.setAdjustDate3(justDate3);
					String salory3 = excel.getCellVal(i, 29);// 调薪33
					item.setAdjustBaseSalary3(NumberUtils.isNumber(salory3) ? new BigDecimal(salory3) : null);

					Date justDate4 = excel.getDateValue(i, 30);// 调薪日期4
					item.setAdjustDate4(justDate4);
					String salory4 = excel.getCellVal(i, 31);// 调薪4
					item.setAdjustBaseSalary4(NumberUtils.isNumber(salory4) ? new BigDecimal(salory4) : null);

					Date justDate5 = excel.getDateValue(i, 32);// 调薪日期5
					item.setAdjustDate5(justDate5);
					String salory5 = excel.getCellVal(i, 33);// 调薪5
					item.setAdjustBaseSalary5(NumberUtils.isNumber(salory5) ? new BigDecimal(salory5) : null);

					Date justDate6 = excel.getDateValue(i, 34);// 调薪日期6
					item.setAdjustDate6(justDate6);
					String salory6 = excel.getCellVal(i, 35);// 调薪6
					item.setAdjustBaseSalary6(NumberUtils.isNumber(salory6) ? new BigDecimal(salory6) : null);

					Date justDate7 = excel.getDateValue(i, 36);// 调薪日期7
					item.setAdjustDate7(justDate7);
					String salory7 = excel.getCellVal(i, 37);// 调薪7
					item.setAdjustBaseSalary7(NumberUtils.isNumber(salory7) ? new BigDecimal(salory7) : null);

					Date justDate8 = excel.getDateValue(i, 38);// 调薪日期8
					item.setAdjustDate8(justDate8);
					String salory8 = excel.getCellVal(i, 39);// 调薪8
					item.setAdjustBaseSalary8(NumberUtils.isNumber(salory8) ? new BigDecimal(salory8) : null);

					String quality_check = excel.getCellVal(i, 40);// 质量考核
					item.setQualityCheck(quality_check);

					String administrative_check = excel.getCellVal(i, 41);// 行政考核
					item.setAdministrativeCheck(administrative_check);

					list.add(item);
				}
				int[] re = Db.batchSave(list, list.size());

				for (int i = 0; i < re.length; i++) {
					total += re[i];
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				renderJson(Ret.ok("msg", e.getMessage()));
				return;
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

	public Integer getDeparetId(String name) {
		Department model = Department.dao.findFirst("select id from fy_base_department where part_name = ?", name);
		if (model != null) {
			return model.getId();
		}
		return null;
	}

}
