package com.chen.fy.controller.business.check;

import java.io.File;

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

public class CheckCollectController extends BaseController {
	CheckCollectService service = CheckCollectService.me;
	private static final Logger logger = LogManager.getLogger(CheckCollectController.class);
	Engine engine;
	String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id, \n"
			+ "				o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name \n "
			+ ",inhouse.inhouse_quantity in_quantity,inhouse.id inhouse_id \n";
	public CheckCollectController() {
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

		String condition = getPara("condition");
		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), condition, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/commission/audit/list.jf").renderToString(data);
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

	public void downloadCheckCollect() throws Exception {
		String[] ids = getParaValues("selectId");
		File file = service.download(ids);
		renderFile(file);
	}
}
