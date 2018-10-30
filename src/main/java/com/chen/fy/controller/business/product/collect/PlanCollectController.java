package com.chen.fy.controller.business.product.collect;

import java.util.ArrayList;
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

public class PlanCollectController extends BaseController {
	PlanCollectService service = PlanCollectService.me;
	private static final Logger logger = LogManager.getLogger(PlanCollectController.class);

	Engine engine;

	public PlanCollectController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		String condition = getPara("condition");
		Ret ret = Ret.ok("msg", "加载数据");
		Page<FyBusinessOrder> modelPage = null;
		try {
			modelPage = service.findPage(getPageSize(), getParaToInt("p", 1) + 1, condition, key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ret = Ret.fail("msg", "查看日志");
		}

		HashedMap<String, Object> data = new HashedMap<String, Object>();
		if (modelPage == null) {
			modelPage = new Page<>(new ArrayList<FyBusinessOrder>(), 1, 10, 0, 0);
		}
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/product/collect/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
	}

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		String condition = getPara("condition");
		try {
			Page<FyBusinessOrder> modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key);

			keepPara("condition");

			setAttr("modelPage", modelPage);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		render("list.html");
	}

	public void findUpdatePlan() {
		String[] ids = getParaValues("selectId");
		try {
			List<Record> list = service.tofindPlan(ids);
			setAttr("modelList", list);
			render("updatePlanTime.html");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index();
	}

	/**
	 * 更新计划时间
	 */
	public void batchUpdatePlan() {
		String[] ids = getParaValues("selectId");
		String startTime = getPara("startTime");
		String finishTime = getPara("finishTime");
		String remark = getPara("remark");
		try {
			Ret ret = service.updatePlanTime(ids, startTime, finishTime, remark);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(Ret.fail().set("msg", "请看运行日志"));
	}

}
