package com.chen.fy.controller.addition.receive;

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

public class ReadyReceiveController extends BaseController {
	ReadyReceiveService service = ReadyReceiveService.me;
	private static final Logger logger = LogManager.getLogger(ReadyReceiveController.class);

	Engine engine;

	public ReadyReceiveController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		keepPara("condition", "keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		String condition = getPara("condition");
		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), condition, key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		engine.addSharedObject("account", getLoginAccount());
		String str = engine.getTemplate("stringTemplet/addtion/readreceive/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageTotal, modelPage.getTotalPage());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
	}

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
		Ret ret = service.reset(order_id);
		renderJson(ret);

	}

	/**
	 * 撤回
	 */
	public void rollbackDistribut() {
		String[] ids = getParaValues("selectId");
		Ret ret = service.rollbackDistribut(ids);
		renderJson(ret);
	}
}
