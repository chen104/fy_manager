package com.chen.fy.controller.business.commission.purchase;

import java.io.File;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.Engine;

public class PurchaseController extends BaseController {
	PurchaseService service = PurchaseService.me;
	private static final Logger logger = LogManager.getLogger(PurchaseController.class);

	Engine engine;

	public PurchaseController() {
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

		Page<FyBusinessPurchase> modelPage = null;
		try {
			modelPage = service.findPage(getPageSize(), getParaToInt("p", 1) + 1, condition, key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/commission/purchase/list.jf").renderToString(data);
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

		Page<FyBusinessPurchase> modelPage = null;
		try {
			modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

		setAttr("modelPage", modelPage);
		StringBuilder append = new StringBuilder();
		if (StringUtils.isNotEmpty(key)) {
			append.append("&keyWord=").append(key);
		}
		append.append("&condition=").append(condition);

		setAttr("append", append.toString());
		render("list.html");
	}

	/**
	 * 下载采购采购单
	 */
	public void downloadPurchase() {
		String[] ids = getParaValues("selectId");
		String supllier = getPara("supplier_id");
		try {
			File file = service.downloadPurchase(ids, supllier);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		setAttr("msg", "下载异常，查看日志");
		index();

	}

}
