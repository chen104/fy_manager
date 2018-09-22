package com.chen.fy.controller.business.waitInhouse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class WaitInhouseController extends BaseController {
	WaitInhouseService service = WaitInhouseService.me;
	private static final Logger logger = LogManager.getLogger(WaitInhouseController.class);

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		keepPara("condition", "weiwai_cate");
		String condition = getPara("condition");
		String weiwai_cate = getPara("weiwai_cate");

		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), condition, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setAttr("modelPage", modelPage);
		StringBuilder append = new StringBuilder();
		if (StringUtils.isNotEmpty(key)) {
			append.append("&keyWord=").append(key);
		}
		append.append("&condition=").append(condition);
		if (StringUtils.isNotEmpty(weiwai_cate)) {
			append.append("&weiwai_cate=").append(weiwai_cate);
		}
		setAttr("append", append.toString());
		render("waitList.html");

	}

	/**
	 * 入库
	 */
	public void inhouse() {

		Integer id = getParaToInt("id");
		String inquantity = getPara("inQuantity");
		try {
			Ret ret = service.inhouse(id, inquantity);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "入库失败，请查看日志"));
	}

}
