package com.chen.fy.controller.business.check;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CheckCollectController extends BaseController {
	CheckCollectService service = CheckCollectService.me;
	private static final Logger logger = LogManager.getLogger(CheckCollectController.class);

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
	 * 撤回
	 */
	public void rollbackCheck() {
		String[] selectId = getParaValues("selectId");
		try {
			Ret ret = service.rollback(selectId);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "运行异常，请查看运行日志"));

	}

}
