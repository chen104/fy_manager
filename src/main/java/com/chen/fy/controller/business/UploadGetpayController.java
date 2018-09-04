package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.Interceptor.FyLoginSessionInterceptor;
import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyUploadGetpay;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class UploadGetpayController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FyLoginSessionInterceptor.class);

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

		Double hang_amount = 0d;
		for (FyUploadGetpay e : personPage.getList()) {
			if (e.getHangAmount() != null) {
				hang_amount += e.getHangAmount().doubleValue();
			}
		}
		setAttr("hang_amount", hang_amount);
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
				HashSet<String> deliveryNoSet = new HashSet<String>();
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
					if (StringUtils.isEmpty(name)) {
						continue;
					}

					String Brand_no = excel.getCellVal(i, 4);// 牌号
					item.setBrandNo(Brand_no);// 牌号

					String spec = excel.getCellVal(i, 5);// 规格
					item.setSpec(spec);

					String project_no = excel.getCellVal(i, 6);//
					item.setProjectNo(project_no);// 项目编号

					String unit = excel.getCellVal(i, 7);// 单位
					item.setUnit(unit);

					String quantity = excel.getCellVal(i, 8);// 数量
					item.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : null);

					String cost = excel.getCellVal(i, 9);// 单价
					item.setCost(NumberUtils.isNumber(cost) ? new BigDecimal(quantity) : null);

					String hangquantity = excel.getCellVal(i, 10);// 已挂帐数量
					item.setHangQuantity(NumberUtils.isNumber(hangquantity) ? new BigDecimal(quantity) : null);

					String invoice_stat = excel.getCellVal(i, 11);// 发票挂账状态
					item.setInvoiceStat(invoice_stat);

					String hangAmount = excel.getCellVal(i, 12);// 挂账金额
					item.setHangAmount(NumberUtils.isNumber(hangAmount) ? new BigDecimal(quantity) : null);

					String perchase_person = excel.getCellVal(i, 13);// 采购人
					item.setPerchasePerson(perchase_person);

					String deliveryNo = excel.getCellVal(i, 14);// 送货单号
					item.setDeliveryNo(deliveryNo);
					deliveryNoSet.add(deliveryNo);

					String deliveryIndex = excel.getCellVal(i, 15);// 送货单序号
					item.setDeliveryIndex(deliveryIndex);

					String contract = excel.getCellVal(i, 16);// 送货单序号
					item.setContract(contract);

					list.add(new Record().setColumns(item));

				}
				int[] re = Db.batchSave("fy_upload_getpay", list, 20);

				/**
				 * 
				 */

				List<String> delino = splitdeliveryNoSet(deliveryNoSet);
				for (String e : delino) {
					String sql = String.format(Db.getSql("upgetpay.updateorder"), e);
					System.out.println(sql);
					Db.update(sql);
				}

				String updateHangStatus = Db.getSql("upgetpay.updateHangStatus");
				try {
					Db.update(updateHangStatus);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
				renderText(e.getMessage());
				return;
			}
		}
		ufile.getFile().deleteOnExit();
		// 更新订单反写状态

		// Db.update("");
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

	/**
	 * 避免送货单号过长
	 * @param deliveryNoSet
	 * @return
	 */
	protected List<String> splitdeliveryNoSet(HashSet<String> deliveryNoSet) {
		List<String> deliveryNos = new ArrayList<String>();
		int row = 0;
		StringBuilder sb = null;

		Iterator<String> iterator = deliveryNoSet.iterator();
		while (iterator.hasNext()) {
			if (row % 10 == 0) {
				if (sb != null) {
					if (sb.length() > 2) {
						sb.deleteCharAt(sb.length() - 1);
					}
					sb.append(")");
					deliveryNos.add(sb.toString());
					sb = new StringBuilder();
					sb.append("(");
				} else {
					sb = new StringBuilder();
					sb.append("(");

				}

			}
			sb.append("'").append(iterator.next()).append("',");
			row++;
		}
		if (sb.length() > 2) {
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
		}
		deliveryNos.add(sb.toString());
		return deliveryNos;
	}

}
