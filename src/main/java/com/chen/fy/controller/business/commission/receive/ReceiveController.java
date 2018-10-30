package com.chen.fy.controller.business.commission.receive;

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

public class ReceiveController extends BaseController {
	ReceiveService service = ReceiveService.me;
	private static final Logger logger = LogManager.getLogger(ReceiveController.class);
	Engine engine;

	public ReceiveController() {
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
		try {
			Page<FyBusinessOrder> modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key);

			keepPara("keyWord", "condition");

			setAttr("modelPage", modelPage);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		render("receive.html");
	}

	/**
	 *委外接收
	 */
	public void receive() {
		String[] ids = getParaValues("selectId");
		System.out.println();
		try {
			Integer num = service.updateReceive(ids);
			if (num == ids.length) {
				renderJson(Ret.ok().set("msg", "接收完成"));
			} else {
				renderJson(Ret.fail().set("msg", "接收失败"));
			}
			return;
		} catch (Exception e) {

			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail().set("msg", "接收失败"));

	}

	public static void main(String[] args) {
		int width = 60 + 60 + 60 + 100 + 100 + 100 + 150 + 150 + 130 + 650 + 100 + 35 + 200 + 200 + 200 + 80 + 100 + 100
				+ 100 + 80;
		System.out.println(width);
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");

		Page<FyBusinessOrder> modelPage = null;
		try {
			modelPage = service.findPage(getPageSize(), getParaToInt("p", 1) + 1, condition, key);
			Ret ret = Ret.ok("msg", "加载数据");
			HashedMap<String, Object> data = new HashedMap<String, Object>();
			data.put("modelPage", modelPage);
			data.put("pageSize", getPageSize());
			String str = engine.getTemplate("stringTemplet/commission/receive/list.jf").renderToString(data);
			ret.set("data", str);
			ret.set(Constant.pageIndex, modelPage.getPageNumber());
			ret.set(Constant.pagePageSize, modelPage.getPageSize());
			ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
			ret.set(Constant.pageListSize, modelPage.getList().size());
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson(Ret.fail("msg", "运行报错，刷新之后再试，或查看日志"));
		}
	}

}
