package com.chen.fy.controller.business.outhouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

public class OuthouseController extends BaseController {
	OuthouseService service = OuthouseService.me;
	private static final Logger logger = LogManager.getLogger(OuthouseController.class);

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

	public void add() {
		Integer id = getParaToInt("selectId");
		Record model = service.findOrderById(id);
		setAttr("model", model);
		setAttr("title", "新增出库");
		setAttr("action", "");
		render("edit.html");
	}

	public void batchOutHouse() {
		String[] ids = getParaValues("selectId");
		try {
			List<Record> modelList = service.findOrderByIds(ids);
			setAttr("modelList", modelList);
			Map<String, Object> Waybill = CacheKit.get("outWarehouse", "Waybill");
			if (Waybill == null) {
				Waybill = new HashMap<String, Object>();
				Waybill.put("transport_company", "东莞市莞泰货物运输有限公司");

			}
			if (modelList.size() > 0) {
				String send_address = modelList.get(0).getStr("send_address");
				setAttr("send_address", send_address);
				Waybill.put("receive_address", send_address);
			}

			setAttr("waybill", Waybill);
			render("batchout.html");
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAttr("msg", "查看运行日志");
		render("batchout.html");
		return;

	}

	public void batchSave() {
		try {
			Integer order_id[] = getParaValuesToInt("selectId");
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(order_id, sb);
			FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "out");
			if (StringUtils.isEmpty(model.getReceiveAddress())) {
				renderJson(Ret.fail().set("msg", "收货地址不能为空"));

				return;
			}
			if (StringUtils.isEmpty(model.getTransportType())) {

				renderJson(Ret.fail().set("msg", "运输方式不能为空"));
				return;
			}
			if (StringUtils.isEmpty(model.getTransportCompany())) {

				renderJson(Ret.fail().set("msg", "运输公司不能为空"));
				return;
			}
			if (StringUtils.isEmpty(model.getTransportNo())) {
				renderJson(Ret.fail().set("msg", "运输单号不能为空"));
				return;
			}
			if (model.getOutTime() == null) {

				renderJson(Ret.fail().set("msg", "出库单号不能为空"));
				return;
			}
			Map<String, Object> Waybill = CacheKit.get("outWarehouse", "Waybill");
			if (Waybill == null) {
				Waybill = new HashMap<String, Object>();
			}
			Waybill = model.toRecord().getColumns();
			CacheKit.put("outWarehouse", "Waybill", Waybill);
			Ret ret = service.batchSave(order_id, model);
			renderJson(ret);
		} catch (

		Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			renderJson(Ret.ok().set("msg", "生成  出库单 异常查看运行日志"));
		}

	}

}
