package com.chen.fy.controller.business.order;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Engine;
import com.jfinal.upload.UploadFile;

public class OrderController2 extends BaseController {
	private static final Logger logger = LogManager.getLogger(OrderController2.class);
	OrderService2 service = OrderService2.me;
	Engine engine = null;

	public OrderController2() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();

		}

		String condition = getPara("condition");
		String order_date_start = getPara("order_date_start");
		String order_date_end = getPara("order_date_end");

		setAttr("append",
				"&keyWord=" + (key == null ? "" : key) + "&condition=" + (condition == null ? "" : condition));
		keepPara("condition", "keyWord", "order_date_start", "order_date_end");
		Page<FyBusinessOrder> modelPage = service.find(condition, key, getParaToInt("p", 1), getPageSize(),
				order_date_start, order_date_end);
		setAttr("modelPage", modelPage);
		render("orderlist2.html");
	}

	public void findtable() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();

		}

		String condition = getPara("condition");
		String order_date_start = getPara("order_date_start");
		String order_date_end = getPara("order_date_end");

		setAttr("append",
				"&keyWord=" + (key == null ? "" : key) + "&condition=" + (condition == null ? "" : condition));
		keepPara("condition", "keyWord", "order_date_start", "order_date_end");
		Page<FyBusinessOrder> modelPage = service.find(condition, key, getParaToInt("p", 1), getPageSize(),
				order_date_start, order_date_end);
		List<FyBusinessOrder> list = modelPage.getList();
		Double tatol = 0d;
		for (int i = 0; i < modelPage.getPageSize(); i++) {
			FyBusinessOrder model = list.get(i);
			BigDecimal bg = model.getAmount();
			tatol += bg.doubleValue();
			setAttr("amount", tatol);
		}
		setAttr("modelPage", modelPage);
		Integer width = service.executWidth(getLoginAccount());
		setAttr("width", width);
		setAttr("colMap", service.colhash);
		render("table.html");
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();

		}

		String condition = getPara("condition");
		String order_date_start = getPara("order_date_start");
		String order_date_end = getPara("order_date_end");

		setAttr("append",
				"&keyWord=" + (key == null ? "" : key) + "&condition=" + (condition == null ? "" : condition));
		keepPara("condition", "keyWord", "order_date_start", "order_date_end");
		Page<FyBusinessOrder> modelPage = service.find(condition, key, getParaToInt("p", 0) + 1, getPageSize(),
				order_date_start, order_date_end);
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		data.put("colMap", service.colhash);
		engine.addSharedObject("account", getLoginAccount());
		String str = engine.getTemplate("stringTemplet/order.jf").renderToString(data);
		Ret ret = Ret.ok().set("msg", "查询完成");
		ret.set("data", str);
		Double tatol = 0d;
		List<FyBusinessOrder> list = modelPage.getList();
		for (int i = 0; i < modelPage.getPageSize(); i++) {
			FyBusinessOrder model = list.get(i);
			BigDecimal bg = model.getAmount();
			tatol += bg.doubleValue();

		}
		ret.set("amount", tatol);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());

		renderJson(ret);
	}

	public void toDownload() {
		render("download2.html");
	}

	public void findDownload() {
		String start_date = getPara("start_date");
		String end_date = getPara("end_date");
		String condition = getPara("condition");
		keepPara("start_date", "end_date", "keyWord", "condition");
		String keyword = getPara("keyWord");
		if (keyword != null) {
			keyword = keyword.trim();
		}
		try {
			List<Record> list = service.findDownload(start_date, end_date, condition, keyword);
			setAttr("modelList", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());

		}
		render("download2.html");
	}

	public void download() {
		String[] ids = getParaValues("selectId");
		try {
			File file = service.downloanOrder2(ids);
			renderFile(file);

			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		findDownload();
	}

	public void uploadFile() {
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				total = service.uploadOrder(ufile.getFile());
				ufile.getFile().delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

	public void delete() {
		String[] ids = getParaValues("selectId");
		try {
			Ret ret = service.delteOrder(ids);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(Ret.fail("msg", "查看运行日志"));
	}

	public void updateOrderDedeliveryDate() {
		Ret ret = null;
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);

		Date delivery_date = getParaToDate("delivery_date");
		if (delivery_date == null) {
			ret = Ret.ok("msg", "交货时间格式错误");

			logger.warn("修改订单交货时间格式错误");

			renderJson(ret);
			return;
		}
		order.setDeliveryDate(delivery_date);

		boolean re = order.update();

		if (re) {
			ret = Ret.ok("msg", "修改 成功");
		} else {
			ret = Ret.ok("msg", "修改失败");
		}
		renderJson(ret);
	}
}
