package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyUploadGetpay;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class UploadGetpayController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		keepPara("keyWord", "condition", "p");
		String condition = getPara("condition");
		Page<FyUploadGetpay> personPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			personPage = FyUploadGetpay.dao.paginate(getParaToInt("p", 1), 10, "select *",
					"from fy_upload_getpay order by id desc");
		} else {

			StringBuilder sb = new StringBuilder();
			personPage = FyUploadGetpay.dao.paginate(getParaToInt("p", 1), 10, "select *",
					"from fy_upload_getpay order by id desc");
		}

		setAttr("modelPage", personPage);
		render("list.html");

	}

	public void updload() {

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
					FyUploadGetpay item = new FyUploadGetpay();

					// 挂账日期
					Date hangDate = excel.getDateValue(i, 0);
					item.setHangDate(hangDate);
					// 发票号
					String invoiceNo = excel.getCellVal(i, 1);// 名称
					item.setInvoiceNo(invoiceNo);

					String materials = excel.getCellVal(i, 2);//
					item.setMaterials(materials); // 物料 物料号
					if (StringUtils.isEmpty(materials)) {
						continue;
					}

					String name = excel.getCellVal(i, 3);// 名称
					item.setCommodityName(name);

					String Brand_no = excel.getCellVal(i, 4);// 名称
					item.setBrandNo(Brand_no);// 牌号

					String spec = excel.getCellVal(i, 5);// 规格
					item.setSpec(spec);

					String project_no = excel.getCellVal(i, 6);// 类别
					item.setProjectNo(project_no);// 项目编号

					String unit = excel.getCellVal(i, 7);// 单位
					item.setUnit(unit);

					String quantity = excel.getCellVal(i, 8);// 数量
					item.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : null);

					String cost = excel.getCellVal(i, 9);// 单价
					item.setCost(NumberUtils.isNumber(cost) ? new BigDecimal(quantity) : null);

					String hangquantity = excel.getCellVal(i, 10);// 挂账数量
					item.setHangQuantity(NumberUtils.isNumber(hangquantity) ? new BigDecimal(quantity) : null);

					String hangAmount = excel.getCellVal(i, 11);// 挂账数量
					item.setHangAmount(NumberUtils.isNumber(hangAmount) ? new BigDecimal(quantity) : null);

					String invoice = excel.getCellVal(i, 12);// 发票挂账状态
					item.setInvoiceStat(invoice);

					String puerchase = excel.getCellVal(i, 13);// 发票挂账状态
					item.setPerchasePerson(puerchase);

					String deliveryNo = excel.getCellVal(i, 14);// 送货单号
					item.setDeliveryNo(deliveryNo);

					String deliveryIndex = excel.getCellVal(i, 15);// 送货单序号
					item.setDeliveryIndex(deliveryIndex);

					String contract = excel.getCellVal(i, 16);// 送货单序号
					item.setContract(contract);

					list.add(new Record().setColumns(item));

				}
				int[] re = Db.batchSave("fy_upload_getpay", list, 20);

				Db.update("update fy_business_order o INNER JOIN   "
						+ "(SELECT  max(g.hang_quantity) hquantity ,MAX(cost) cost,g.delivery_no delivery_no  "
						+ "FROM fy_upload_getpay g LEFT JOIN fy_business_order o  on  g.delivery_no = o.delivery_no where o.quantity <> o.hang_quantity GROUP BY g.delivery_no   "
						+ ")  g ON o.delivery_no = g.delivery_no  "
						+ " set  hang_quantity=hquantity,unhang_quantity=quantity - hquantity,hang_account =   cost * hquantity  "
						+ "where o.id=12613  " + "");

				Db.update("update fy_business_order set hang_status='全部挂账' where quantity = hang_quantity;");
				Db.update(
						"update fy_business_order set handle_status='部分挂账' where quantity <> hang_quantity and hang_quantity <> 0;");

				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		// 更新订单反写状态

		Db.update("");
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));
	}

	public void save() {
		FyUploadGetpay model = getBean(FyUploadGetpay.class, "model");
		boolean re = model.save();
		String delevery_no = model.getDeliveryNo();

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建   信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyUploadGetpay.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		FyUploadGetpay model = FyUploadGetpay.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		FyUploadGetpay model = getBean(FyUploadGetpay.class, "model");
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   信息成功");
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
