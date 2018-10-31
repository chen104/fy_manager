package com.chen.fy.controller.business.finance.paybill;

import java.io.File;

import org.apache.commons.collections4.map.HashedMap;
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

public class PayBillController extends BaseController {
	PayBillSerivce service = PayBillSerivce.me;
	private static final Logger logger = LogManager.getLogger(PayBillController.class);
	Engine engine;

	public PayBillController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void findJsonPage() {

		Page<Record> modelPage = null;
		setAttr("modelPage", modelPage);
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}

		setAttr("keyWord", key);
		keepPara("condition");
		String condition = getPara("condition");
		Double should_pay = 0d;
		Double weiwai_account = 0d;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), key, condition, getLoginAccount());
			for (Record e : modelPage.getList()) {
				weiwai_account = weiwai_account
						+ (e.getDouble("purchase_amount") == null ? 0 : e.getDouble("purchase_amount"));
				should_pay = should_pay + (e.getDouble("should_pay") == null ? 0 : e.getDouble("should_pay"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/finance/paybill/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		ret.set("should_pay", should_pay);
		ret.set("weiwai_account", weiwai_account);
		renderJson(ret);
	}

	public void index() {
		Page<Record> modelPage = null;
		setAttr("modelPage", modelPage);
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}

		setAttr("keyWord", key);
		keepPara("condition");
		String condition = getPara("condition");
		Double should_pay = 0d;
		Double weiwai_account = 0d;
		try {
			modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), key, condition, getLoginAccount());
			for (Record e : modelPage.getList()) {
				weiwai_account = weiwai_account
						+ (e.getDouble("purchase_amount") == null ? 0 : e.getDouble("purchase_amount"));
				should_pay = should_pay + (e.getDouble("should_pay") == null ? 0 : e.getDouble("should_pay"));
			}
			setAttr("modelPage", modelPage);
			setAttr("should_pay", should_pay);
			setAttr("weiwai_account", weiwai_account);
			render("list.html");
			return;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setAttr("modelPage", new Page<>());
		render("pay.html");
		return;
	}

	/**
	 * 下载应付单
	 * 
	 * @throws Exception
	 */
	public void downloadPay() throws Exception {
		String[] ids = getParaValues("selectId");
		File file = service.downloadPay(ids);
		renderFile(file);
	}

	public void rollback() {
		String[] payids = getParaValues("selectId");
		try {
			Ret ret = service.rollback(payids);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail("msg", "请查看运行日志"));

	}

}
