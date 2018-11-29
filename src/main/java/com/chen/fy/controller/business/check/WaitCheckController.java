package com.chen.fy.controller.business.check;

import java.util.Date;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Engine;

public class WaitCheckController extends BaseController {
	WaitCheckService service = WaitCheckService.me;
	private static final Logger logger = LogManager.getLogger(WaitCheckService.class);
	Engine engine;

	public WaitCheckController() {
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
		keepPara("condition", "weiwai_cate");
		String condition = getPara("condition");

		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), condition, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/warehouse/check/list.jf").renderToString(data);
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
		keepPara("condition", "weiwai_cate");
		String condition = getPara("condition");

		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), condition, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		setAttr("modelPage", modelPage);
		StringBuilder append = new StringBuilder();
		if (StringUtils.isNotEmpty(key)) {
			append.append("&keyWord=").append(key);
		}
		append.append("&condition=").append(condition);
		// if (StringUtils.isNotEmpty(weiwai_cate)) {
		// append.append("&weiwai_cate=").append(weiwai_cate);
		// }
		setAttr("append", append.toString());
		render("waitCheck.html");

	}

	/**
	 * 查找检测的入库
	 */
	public void editCheck() {
		Integer id = getParaToInt("id");
		try {
			Record model = service.findCheckRecordById(id);
			setAttr("model", model);
			setAttr("now", new Date());
			render("check.html");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		setAttr("msg", "请查看日志");
		index();

	}

	/**
	 * 保存检测结果
	 */
	public void check() {
		// String pass_quantity = getPara("pass_quantity");
		// String unpass_quantity = getPara("unpass_quantity");
		// String exception_reson = getPara("exception_reson");
		// String check_remark = getPara("check_remark");
		// String check_result = getPara("check_result");

		String modelId = getPara("model.id");
		String pass_quantity = getPara("model.pass_quantity");
		String check_result = getPara("model.check_result");
		String[] exception_reson = getParaValues("exception_reson");
		String remark = getPara("model.check_remark");

		// FyBusinessInWarehouse model = getModel(FyBusinessInWarehouse.class, "model");
		try {
			Ret ret = service.checkInhouse(modelId, pass_quantity, check_result, exception_reson, remark);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "查看运行日志"));
	}

	public void rollback() {
		String ids[] = getParaValues("selectId");
		Ret ret = service.rollback(ids);
		renderJson(ret);
	}
}
