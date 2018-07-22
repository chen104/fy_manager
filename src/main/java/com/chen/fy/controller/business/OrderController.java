package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.model.Category;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.OrderUploadLog;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class OrderController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order order by id desc");
		} else {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("list.html");
	}

	public void importFile() {
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();
				new FyBusinessOrder();
				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				List<Record> list = new ArrayList<Record>();
				for (int i = 1; i < excel.getRowNum(); i++) {
					FyBusinessOrder item = new FyBusinessOrder();
					String catgory = excel.getCellVal(i, 0);// 类别
					item.setCateTmp(catgory);
					String planname = excel.getCellVal(i, 1);// 计划员
					item.setPlanTmp(planname);

					String excustatu = excel.getCellVal(i, 2);// 执行状态
					item.setExecuStatus(excustatu);

					String urgentStatus = excel.getCellVal(i, 3);// 紧急状态
					item.setUrgentStatus(urgentStatus);

					Date orderdate = excel.getDateValue(i, 4);// 订单日期
					item.setOrderDate(orderdate);

					Date DeliveryDate = excel.getDateValue(i, 5);// 交货日期
					item.setDeliveryDate(DeliveryDate);

					String workid = excel.getCellVal(i, 6);// 工作订单号
					item.setWorkOrderNo(workid);

					String DeliveryId = excel.getCellVal(i, 7);// 送货单号
					item.setDeliveryNo(DeliveryId);

					String name = excel.getCellVal(i, 8);// 商品名称
					item.setCommodityName(name);

					String nspec = excel.getCellVal(i, 9);// 商品规格
					item.setCommoditySpec(nspec);

					String map = excel.getCellVal(i, 10);// 总图号
					// item.setMapNo(mapNo);
					item.setMapTmp(map);

					String tck = excel.getCellVal(i, 11);// 技术条件
					item.setTechnology(tck);

					String maching = excel.getCellVal(i, 12);// 加工要求
					item.setMachiningRequire(maching);

					String quantity = excel.getCellVal(i, 13);// 数量
					item.setQuantity(NumberUtils.isNumber(quantity) ? Integer.valueOf(quantity) : null);

					String unit = excel.getCellVal(i, 14);// 单位
					item.setUnitTmp(unit);

					String untaxcost = excel.getCellVal(i, 15);// 未税单价
					item.setUntaxedCost(NumberUtils.isNumber(untaxcost) ? new BigDecimal(untaxcost) : null);

					String account = excel.getCellVal(i, 16);// 金额
					item.setAmount(NumberUtils.isNumber(account) ? new BigDecimal(account) : null);

					String taxRate = excel.getCellVal(i, 17);// 税率
					item.setTaxTmp(taxRate);

					String taxAccount = excel.getCellVal(i, 18);// 税额
					boolean isnumber = NumberUtils.isNumber(taxAccount);

					item.setTaxAmount(isnumber ? new BigDecimal(taxAccount) : null);

					String totalAccount = excel.getCellVal(i, 19);// 含税金额

					item.setTatolAmount(NumberUtils.isNumber(totalAccount) ? new BigDecimal(totalAccount) : null);

					item.setImportTime(new Date());
					list.add(new Record().setColumns(item));

				}
				int[] re = Db.batchSave("fy_business_order", list, 20);

				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}
				OrderUploadLog log = new OrderUploadLog();
				log.setSucess(total);
				log.save();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

	public void receive() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order order by id desc");
		} else {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	public void pcm() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order order by id desc");
		} else {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_order where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("pcm.html");
	}

	public void save() {
		FyBusinessOrder customer = getBean(FyBusinessOrder.class, "model");
		boolean re = customer.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  类别成功信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyBusinessOrder.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		FyBusinessOrder model = FyBusinessOrder.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		Category person = getBean(Category.class, "model");
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
		render("edit.html");
	}
}
