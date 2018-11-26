package com.chen.fy.controller.business.commission.collect;

import java.io.File;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.CommissionColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Engine;

public class CommissionCollectController extends BaseController {
	public final static CommissionCollectService service = CommissionCollectService.me;
	private static final Logger logger = LogManager.getLogger(CommissionCollectController.class);
	Engine engine;

	public CommissionCollectController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();

		engine.addDirective("commisionColor", CommissionColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		keepPara("condition", "weiwai_cate", "inhouse_date_start", "inhouse_date_end");
		String condition = getPara("condition");
		// String order_date_start = getPara("order_date_start");
		// String order_date_end = getPara("order_date_end");

		String inhouse_date_start = getPara("inhouse_date_start");
		String inhouse_date_end = getPara("inhouse_date_end");

		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), condition, key, inhouse_date_start,
					inhouse_date_end);
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
		// if (StringUtils.isNotEmpty(weiwai_cate)) {
		// append.append("&weiwai_cate=").append(weiwai_cate);
		// }
		setAttr("append", append.toString());
		render("list.html");
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		keepPara("condition", "weiwai_cate", "inhouse_date_start", "inhouse_date_end");
		String condition = getPara("condition");
		String order_date_start = getPara("order_date_start");
		String order_date_end = getPara("order_date_end");

		String inhouse_date_start = getPara("inhouse_date_start");
		String inhouse_date_end = getPara("inhouse_date_end");

		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), condition, key, inhouse_date_start,
					inhouse_date_end);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/commission/collect/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
	}

	public void downloadCollect() {
		String[] ids = getParaValues("downloadId");
		try {
			File file = service.downloadFile(ids);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		index();
	}

}
