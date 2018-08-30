package com.chen.fy.controller.base;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SupplierNoKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class SupplierController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<Supplier> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier order by id desc");
		} else {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier  where name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");
	}

	public void save() {
		Supplier customer = getBean(Supplier.class, "model");
		boolean re = customer.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  " + customer.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = Supplier.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Supplier person = Supplier.dao.findById(getParaToInt("id"));
		setAttr("model", person);
		setAttr("action", "update");
		setAttr("title", "修改厂商");
		render("edit.html");
	}

	public void update() {
		Supplier person = getBean(Supplier.class, "model");
		boolean re = person.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   " + person.getName() + " 信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void add() {
		setAttr("action", "save");
		setAttr("title", "新建厂商");
		render("edit.html");
	}

	public void searchSupplierJson() {
		String key = getPara("keyWord");
		Page<Supplier> personPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier order by id desc");
		} else {
			personPage = Supplier.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_base_supplier  where name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}
		renderJson(personPage);
	}

	public void uploadSupplier() {
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();

				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				List<Supplier> list = new ArrayList<Supplier>();
				int rows = excel.getRowNum() + 1;
				for (int i = 1; i < rows; i++) {
					Supplier item = new Supplier();

					String name = excel.getCellVal(i, 0);// 厂商名称
					if (StringUtils.isEmpty(name)) {
						continue;
					}
					item.setName(name);
					item.setSupplierNo(SupplierNoKit.getNo());

					String code = excel.getCellVal(i, 1);// 统一社会代码
					item.setCode(code);

					String adress = excel.getCellVal(i, 2);// 地址
					item.setAddress(adress);

					String phone = excel.getCellVal(i, 3);// 电话
					item.setPhone(phone);

					String bank_account = excel.getCellVal(i, 4);// 银行账户
					item.setBankAccount(bank_account);

					String bank_no = excel.getCellVal(i, 5);// 银行账号
					item.setBankNo(bank_no);

					String settlement_type = excel.getCellVal(i, 6);// 结算方式
					item.setSettlementType(settlement_type);

					String settlement_ycle = excel.getCellVal(i, 7);// 结算周期
					item.setSettlementCycle(settlement_ycle);

					String contect_peple = excel.getCellVal(i, 8);// 联系人
					item.setContactPerson(contect_peple);

					String contect_type = excel.getCellVal(i, 9);// 联系方式
					item.setContactType(contect_type);

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
}
