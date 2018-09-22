package com.chen.fy.controller.business.check;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class WaitCheckController extends BaseController {
	WaitCheckService service = WaitCheckService.me;
	private static final Logger logger = LogManager.getLogger(WaitCheckService.class);

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

		FyBusinessInWarehouse model = getModel(FyBusinessInWarehouse.class, "model");
		try {
			Ret ret = service.checkInhouse(model);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "查看运行日志"));
	}
}
