package com.chen.fy.controller.business.check;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CheckExceptionController extends BaseController {
	CheckExceptionService service = CheckExceptionService.me;
	private static final Logger logger = LogManager.getLogger(CheckExceptionController.class);

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		keepPara("condition", "weiwai_cate");
		String condition = getPara("condition");
		// String order_date_start = getPara("order_date_start");
		// String order_date_end = getPara("order_date_end");
		//
		// String inhouse_date_start = getPara("inhouse_date_start");
		// String inhouse_date_end = getPara("inhouse_date_end");

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
		render("list.html");
	}

	/**
	 * 下载文件
	 * @throws Exception
	 */
	public void downloadException() throws Exception {
		String[] ids = getParaValues("selectId");
		File file = service.download(ids);
		renderFile(file);
	}

}
