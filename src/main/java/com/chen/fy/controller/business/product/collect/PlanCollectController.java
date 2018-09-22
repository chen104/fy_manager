package com.chen.fy.controller.business.product.collect;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PlanCollectController extends BaseController {
	PlanCollectService service = PlanCollectService.me;
	private static final Logger logger = LogManager.getLogger(PlanCollectController.class);

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
