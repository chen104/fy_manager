package com.chen.fy.controller.business.outhouse;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.template.Engine;

public class OuthouseController extends BaseController {
	OuthouseService service = OuthouseService.me;
	private static final Logger logger = LogManager.getLogger(OuthouseController.class);
	Engine engine;

	public OuthouseController() {
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
		String out_date_start = getPara("out_date_start");
		String out_date_end = getPara("out_date_end");
		keepPara("out_date_start", "out_date_end");
		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), condition, key, out_date_start,
					out_date_end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/warehouse/outHouse/list.jf").renderToString(data);
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
		String out_date_start = getPara("out_date_start");
		String out_date_end = getPara("out_date_end");
		keepPara("out_date_start", "out_date_end");
		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), condition, key, out_date_start,
					out_date_end);
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
			// if (StringUtils.isEmpty(model.getTransportNo())) {
			// renderJson(Ret.fail().set("msg", "运输单号不能为空"));
			// return;
			// }

			//
			Date out_date = getParaToDate("out.outTime");
			model.setOutTime(out_date);
			if (out_date == null) {

				renderJson(Ret.fail().set("msg", "出库时间不能为空"));
				return;
			}
			Map<String, Object> Waybill = CacheKit.get("outWarehouse", "Waybill");
			if (Waybill == null) {
				Waybill = new HashMap<String, Object>();
			}
			Waybill = model.toRecord().getColumns();
			if (order_id.length == 1) {
				String quantity = getPara("out.quantity");
				try {
					Integer quan = Integer.valueOf(quantity);

					model.setOutQuantity(quan);

					FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id[0]);
					if (quan > order.getStorageQuantity()) {
						renderJson(Ret.fail().set("msg", "出库不能大于库存"));
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.warn(" 出库必须为数字 ");
					renderJson(Ret.fail().set("msg", "出库必须为数字"));
					return;
				}
			}
			CacheKit.put("outWarehouse", "Waybill", Waybill);
			model.setCreateBy(getLoginAccountId());
			model.setUpdateBy(model.getCreateBy());
			model.setCreateTime(new Date());
			model.setUpdateTime(model.getCreateTime());
			Ret ret = service.batchSave(order_id, model);
			renderJson(ret);
		} catch (

		Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			renderJson(Ret.ok().set("msg", "生成  出库单 异常查看运行日志"));
		}

	}

	/**
	 * 下载出库单
	 * @throws Exception
	 */
	public void downloadOut() throws Exception {
		String[] ids = getParaValues("selectId");
		File file = service.download(ids);
		renderFile(file);
	}

	/**
	 * 撤回出库
	 */
	public void rollbackOut() {
		String[] outId = getParaValues("selectId");
		try {
			Ret ret = service.batchRollback(outId);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(Ret.ok().set("msg", "运行异常，请查看日志"));
	}

}
