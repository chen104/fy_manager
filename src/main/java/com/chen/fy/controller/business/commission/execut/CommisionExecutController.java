package com.chen.fy.controller.business.commission.execut;

import java.io.File;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Engine;

public class CommisionExecutController extends BaseController {
	private static final Logger logger = LogManager.getLogger(CommisionExecutController.class);

	CommisionExecutService service = CommisionExecutService.me;
	Engine engine;

	public CommisionExecutController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		keepPara("condition", "weiwai_cate");
		String condition = getPara("condition");
		String weiwai_cate = getPara("weiwai_cate");

		Page<FyBusinessOrder> modelPage = null;
		modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key, weiwai_cate);

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
		// setAttr("action", "updatePurchaseCost");
		render("list.html");

	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");
		String weiwai_cate = getPara("weiwai_cate");

		Page<FyBusinessOrder> modelPage = null;

		try {
			modelPage = service.findPage(getPageSize(), getParaToInt("p", 1) + 1, condition, key, weiwai_cate);

			Ret ret = Ret.ok("msg", "加载数据");
			HashedMap<String, Object> data = new HashedMap<String, Object>();
			data.put("modelPage", modelPage);
			data.put("pageSize", getPageSize());
			String str = engine.getTemplate("stringTemplet/commission/execut/list.jf").renderToString(data);
			ret.set("data", str);
			ret.set(Constant.pageIndex, modelPage.getPageNumber());
			ret.set(Constant.pagePageSize, modelPage.getPageSize());
			ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
			ret.set(Constant.pageListSize, modelPage.getList().size());
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderJson(Ret.fail("msg", "运行报错，刷新之后再试，或查看日志"));
		}
	}

	/**
	 * 下载报目表的下载页面
	 */
	public void todownloadAskCost() {
		render("askCostDownload.html");
	}

	public void findDownloadAskCost() {
		String start_date = getPara("start_date");
		String end_date = getPara("end_date");
		String condition = getPara("condition");
		keepPara("start_date", "end_date", "keyWord", "condition");
		String keyword = getPara("keyWord");
		if (keyword != null) {
			keyword = keyword.trim();
		}
		try {
			List<Record> list = service.findDownloadAskCost(start_date, end_date, condition, keyword);
			setAttr("modelList", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());

		}

		render("askCostDownload.html");

	}

	/**
	 * 下载报目表
	 */
	public void downloadAskCost() {
		String[] ids = getParaValues("selectId");
		try {
			File file = service.downloadAskCost(ids);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		index();
	}

	public void edit() {
		String order_id = getPara("order_id");
		keepPara("path");
		System.out.println(getPara("path"));
		if (!NumberUtils.isNumber(order_id)) {
			index();
			return;
		}
		try {
			Record model = service.findEdit(order_id);
			setAttr("model", model);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		index();

	}

	public void update() {
		FyBusinessPurchase model = getModel(FyBusinessPurchase.class, "model");

		boolean re = false;
		if (model.getId() == null) {
			re = model.save();
		} else {
			re = model.update();
		}
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}
		int num = Db.update(
				" update  fy_business_purchase  p INNER JOIN  fy_base_supplier s on s.id=p.supplier_id  set p.supplier_no = s.supplier_no where p.supplier_no is null ");
		String sql = "update   fy_business_order o INNER JOIN  fy_business_purchase a \r\n"
				+ " ON a.order_id = o.id \r\n"
				+ " set a.work_order_no = o.work_order_no where a.work_order_no is null ";
		Db.update(sql);
		logger.debug("更新厂商编码 " + num);
		renderJson(ret);
		return;
	}


	public static void main(String[] args) {
		int width = 50 + 60 + 60 + 60 + 100 + 100 + 100 + 150 + 150 + 200 + 650 + 100 + 35 + 200 + 200 + 200 + 80 + 200
				+ 200 + 100 + 200 + 100 + 100 + 100 + 100 + 100;
		System.out.println(width);
	}

}
