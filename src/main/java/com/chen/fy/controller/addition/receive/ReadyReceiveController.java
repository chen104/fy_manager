package com.chen.fy.controller.addition.receive;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ReadyReceiveController extends BaseController {
	ReadyReceiveService service = ReadyReceiveService.me;
	private static final Logger logger = LogManager.getLogger(ReadyReceiveController.class);

	public void index() {
		String key = getPara("keyWord");
		keepPara("condition", "keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		String condition = getPara("condition");
		Page<Record> modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), condition, key);
		setAttr("modelPage", modelPage);
		render("list.html");
	}

	public void findReadyJosn() {
		String key = getPara("keyWord");
		if (StringUtils.isNotEmpty(key)) {
			key = key.trim();
		}
		Page<Record> modelPage = service.findJsonPage(getParaToInt("p", 1), getPageSize(), key);
		renderJson(modelPage);
	}

	/**
	 * 补货关联备货
	 */
	public void addToReady() {
		Integer add_order = getParaToInt("add_order_id");
		Integer ready_order_id = getParaToInt("ready_order_id");
		try {

			Ret ret = service.addToReady(add_order, ready_order_id);
			renderJson(ret);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "检查运行日志"));
	}

	/**
	 * 更新补货数量
	 */
	public void addQuantity() {
		Integer order_id = getParaToInt("order_id");
		Integer addquantity = getParaToInt("addquantity");
		try {
			Ret ret = service.addQuantity(order_id, addquantity);
			renderJson(ret);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "请查看运行日志"));
	}

	/**
	 * 撤回备货
	 */
	public void rollback() {

		Integer[] order_id = getParaValuesToInt("order_id");
		logger.info(" 撤回 备货 " + StringUtils.join(order_id, ","));
		Ret ret = service.rollback(order_id);
		renderJson(ret);

	}
}
