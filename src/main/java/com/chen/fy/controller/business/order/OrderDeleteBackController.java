package com.chen.fy.controller.business.order;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class OrderDeleteBackController extends BaseController {
	private static final Logger logger = LogManager.getLogger(OrderController2.class);
	OrderBackService service = OrderBackService.me;

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

}
