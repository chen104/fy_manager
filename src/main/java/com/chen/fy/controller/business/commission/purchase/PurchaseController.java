package com.chen.fy.controller.business.commission.purchase;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.plugin.activerecord.Page;

public class PurchaseController extends BaseController {
	PurchaseService service = PurchaseService.me;
	private static final Logger logger = LogManager.getLogger(PurchaseController.class);

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
