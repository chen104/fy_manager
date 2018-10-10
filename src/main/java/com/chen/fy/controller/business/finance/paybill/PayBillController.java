package com.chen.fy.controller.business.finance.paybill;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PayBillController extends BaseController {
	PayBillSerivce service = PayBillSerivce.me;
	private static final Logger logger = LogManager.getLogger(PayBillController.class);

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
			modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), key, condition, null, null);
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
