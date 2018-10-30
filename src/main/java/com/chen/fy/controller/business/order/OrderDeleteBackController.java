package com.chen.fy.controller.business.order;

import java.math.BigDecimal;
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
import com.jfinal.template.Engine;

public class OrderDeleteBackController extends BaseController {
	private static final Logger logger = LogManager.getLogger(OrderController2.class);
	OrderBackService service = OrderBackService.me;
	Engine engine;

	public OrderDeleteBackController() {
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
		Integer width = OrderService2.me.executWidth(getLoginAccount());
		setAttr("colMap", OrderService2.me.colhash);
		setAttr("width", width);
		render("list.html");
	}

	/**
	 * 恢复删除的订单
	 */
	public void backOrder() {
		String[] ids = getParaValues("selectId");
		try {
			Ret ret = service.backOrder(ids);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(Ret.fail("msg", "查看运行日志"));
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();

		}

		String condition = getPara("condition");
		String order_date_start = getPara("order_date_start");
		String order_date_end = getPara("order_date_end");

		keepPara("condition", "keyWord", "order_date_start", "order_date_end");
		Page<FyBusinessOrder> modelPage = service.find(condition, key, getParaToInt("p", 0) + 1, getPageSize(),
				order_date_start, order_date_end);
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		data.put("colMap", OrderService2.colhash);
		engine.addSharedObject("account", getLoginAccount());
		String str = engine.getTemplate("stringTemplet/order/deleteList.jf").renderToString(data);
		Ret ret = Ret.ok().set("msg", "查询完成");
		ret.set("data", str);
		Double tatol = 0d;
		List<FyBusinessOrder> list = modelPage.getList();
		System.out.println("  page " + Constant.pagePageSize + " " + modelPage.getPageSize());
		for (int i = 0; i < list.size(); i++) {
			FyBusinessOrder model = list.get(i);
			BigDecimal bg = model.getAmount();
			if (bg != null) {
				tatol += bg.doubleValue();
			}

		}
		ret.set("amount", tatol);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
	}

}
