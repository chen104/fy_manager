package com.chen.fy.controller.business.getpay;

import java.util.ArrayList;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.Interceptor.FyLoginSessionInterceptor;
import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyUploadGetpay;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.Engine;
import com.jfinal.upload.UploadFile;

public class UploadGetpayController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FyLoginSessionInterceptor.class);
	UploadGetpayService service = UploadGetpayService.me;
	Engine engine;

	public UploadGetpayController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		keepPara("keyWord", "condition", "p");
		String condition = getPara("condition");
		if (key != null) {
			key = key.trim();
		}
		Page<FyUploadGetpay> modelPage = null;
		try {
			modelPage = service.findpage(getParaToInt("p", 1) + 1, getPageSize(), condition, key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		if (modelPage == null) {
			modelPage = new Page<>(new ArrayList<FyUploadGetpay>(), 1, 10, 0, 0);
		}
		Double hang_amount = 0d;
		for (FyUploadGetpay e : modelPage.getList()) {
			if (e.getHangAmount() != null) {
				hang_amount += e.getHangAmount().doubleValue();
			}
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/finance/getpay/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		// ret.set("should_pay", should_pay);
		ret.set("hang_amount", hang_amount);

		renderJson(ret);
	}

	public void index() {

		String key = getPara("keyWord");
		keepPara("keyWord", "condition", "p");
		String condition = getPara("condition");
		if (key != null) {
			key = key.trim();
		}
		Page<FyUploadGetpay> personPage = null;
		try {
			personPage = service.findpage(getParaToInt("p", 1), getPageSize(), condition, key);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		if (personPage == null) {
			personPage = new Page<>(new ArrayList<FyUploadGetpay>(), 1, 10, 0, 0);
		}
		Double hang_amount = 0d;
		for (FyUploadGetpay e : personPage.getList()) {
			if (e.getHangAmount() != null) {
				hang_amount += e.getHangAmount().doubleValue();
			}
		}
		setAttr("hang_amount", hang_amount);
		setAttr("modelPage", personPage);
		setAttr("keyWord", key);
		keepPara("condition");
		render("list.html");

	}

	public void updload() {

		UploadFile ufile = getFile();
		try {
			if (ufile != null) {
				
				Ret ret = service.uploadFile(ufile.getFile());
				renderJson(ret);
				return;
			} else {
				renderJson(Ret.fail("msg", "文件为空"));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		// Db.update("");
		renderJson(Ret.fail("msg", "查看运行日志"));
	}

	public void save() {
		FyUploadGetpay model = getBean(FyUploadGetpay.class, "model");
		boolean re = model.save();
		String delevery_no = model.getDeliveryNo();

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建   信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyUploadGetpay.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		FyUploadGetpay model = FyUploadGetpay.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		FyUploadGetpay model = getBean(FyUploadGetpay.class, "model");
		boolean re = model.update();

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改   信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void add() {
		setAttr("action", "save");
		render("edit.html");
	}

	public void toBill() {
		String[] ids = getParaValues("selectId");
		try {
			Ret ret = service.toBill(ids);
			renderJson(ret);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "请查看运行日志"));
	}
}
