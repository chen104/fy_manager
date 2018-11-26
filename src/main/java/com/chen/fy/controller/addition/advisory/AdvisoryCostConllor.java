package com.chen.fy.controller.addition.advisory;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyAdvisoryCost;
import com.chen.fy.model.Person;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class AdvisoryCostConllor extends BaseController {
	AdisoryCostService service = AdisoryCostService.me;
	public void index() {
		String key = getPara("keyWord");
		Page<FyAdvisoryCost> personPage = null;
		keepPara("keyWord", "condition");

		if (StringUtils.isEmpty(key)) {
			personPage = FyAdvisoryCost.dao.paginate(getParaToInt("p", 1), 10,
					"select c.*,u.name unit_name",
					"from fy_advisory_cost  c " + " left join fy_base_unit u on c.unit= u.id order by id desc");
		} else {
			String condition = getPara("condition");
			StringBuilder sb = new StringBuilder();
			if ("order_come_date".equals(condition)) {
				sb.append("where ").append(condition).append(" = '").append(key).append("' ");
			} else {
				sb.append("where ").append(condition).append(" like '").append(key).append("' ");
			}
			personPage = FyAdvisoryCost.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_advisory_cost  " + sb.toString() + " order by id desc");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", personPage);
		render("list.html");

	}

	public void save() {
		FyAdvisoryCost model = getBean(FyAdvisoryCost.class, "model");
		boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyAdvisoryCost.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyAdvisoryCost model = FyAdvisoryCost.dao.findFirst(
				"select c.*,u.name unit_name  from fy_advisory_cost  c  left join fy_base_unit u on c.unit= u.id where c.id = ?",
				id);

		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		FyAdvisoryCost model = getBean(FyAdvisoryCost.class, "model");
		Date d = getParaToDate("model.orderComeDate");
		model.setOrderComeDate(d);
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改    信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void add() {
		setAttr("action", "save");
		render("edit.html");
	}

	/**
	 * 导入报价表
	 */
	public void upload() {

		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();

				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				List<Record> list = new ArrayList<Record>();
				int rows = excel.getRowNum() + 1;

				for (int i = 1; i < rows; i++) {
					FyAdvisoryCost item = new FyAdvisoryCost();
					String advisoryOrg = excel.getCellVal(i, 0);// 客户
					item.setCustomerNo(advisoryOrg);

					String advisory_persion = excel.getCellVal(i, 1);// 计划员
					item.setPlaner(advisory_persion);
					if (StringUtils.isEmpty(advisory_persion)) {
						continue;
					}

					// 工作订单号
					String workOrderNo = excel.getCellVal(i, 2);// 状态工作订单号
					item.setWorkOrderNo(workOrderNo);

					String deliveryNo = excel.getCellVal(i, 3);// 送货单号
					item.setDeliveryNo(deliveryNo);

					String brandNo = excel.getCellVal(i, 4);// 牌号
					item.setBrandNo(brandNo);

					Date advisory_date = excel.getDateValue(i, 5);// 订单下达日期
					item.setOrderComeDate(advisory_date);

					String status = excel.getCellVal(i, 6);// 状态
					item.setStatus(status);

					String tecnology = excel.getCellVal(i, 7);// 技术条件
					item.setTecnologyRequire(tecnology);

					String commodity_name = excel.getCellVal(i, 8);// 商品名称
					item.set("commodity_name", commodity_name);

					String commodity_spec = excel.getCellVal(i, 9);// 商品规格
					item.set("commodity_spec", commodity_spec);

					String map_no = excel.getCellVal(i, 10);// 总图号，关联图纸
					item.set("map_no", map_no);

					String unit = excel.getCellVal(i, 11);// 单位
					if (StringUtils.isNoneEmpty(unit)) {
						Integer uid = service.getUnit(unit.trim());
						item.set("unit", uid);
					}

					String quantity = excel.getCellVal(i, 12);// 数量',
					item.set("quantity",
							NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : new BigDecimal("0"));

					String base_cost = excel.getCellVal(i, 13);//
					item.set("base_cost",
							NumberUtils.isNumber(base_cost) ? new BigDecimal(base_cost) : new BigDecimal("0"));

					String customer_profit_cost = excel.getCellVal(i, 14);// 客户利润价价
					item.set("customer_profit_cost",
							NumberUtils.isNumber(customer_profit_cost) ? new BigDecimal(customer_profit_cost)
									: new BigDecimal("0"));

					String customer_profit_amount = excel.getCellVal(i, 15);// 客户利润价价
					item.set("customer_profit_amount",
							NumberUtils.isNumber(customer_profit_amount) ? new BigDecimal(customer_profit_amount)
									: new BigDecimal("0"));


					list.add(new Record().setColumns(item));
					i++;

				}
				int[] re = Db.batchSave("fy_advisory_cost", list, 20);

				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));
	}



	public Integer getPerson(String name) {
		Person model = Person.dao.findFirst("select id from fy_base_person where name = ?", name);
		if (model == null) {
			return null;
		} else {
			return model.getId();
		}
	}
}
