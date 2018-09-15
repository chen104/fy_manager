package com.chen.fy.controller.business.commission.audit;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;

public class FyPurchaseAuditController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FyPurchaseAuditController.class);
	FyPurchaseAuditService modelService = FyPurchaseAuditService.me;

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");
		Page<FyBusinessPurchase> accountPage = modelService.paginate(getParaToInt("p", 1), getPageSize(), condition,
				key);
		setAttr("modelPage", accountPage);
		render("list.html");
	}

	public void add() {
		logger.info("");
		render("edit.html");
	}

	public void save() {
		FyBusinessPurchase model = getModel(FyBusinessPurchase.class, "model");
		Boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "新建成功");
		} else {
			ret = Ret.ok().set("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Boolean re = modelService.deleteById(getParaToInt("id"));

		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "新建成功");
		} else {
			ret = Ret.ok().set("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void edit() {
		FyBusinessPurchase model = modelService.findById(getParaToInt("id"));
		setAttr("model", model);
		render("edit.html");

	}

	public void update() {
		FyBusinessPurchase model = getBean(FyBusinessPurchase.class, "model");
		Boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}
		renderJson(ret);
	}

	/**
	 * 上传报价单
	 */
	public void uploadFile() {
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				total = modelService.uploadRuqest(ufile.getFile());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

	/**
	 * 审核采购
	 */
	public void auditPurchase() {
		String[] ids = getParaValues("selectId");
		String auditResult = getPara("audit_status");
		try {
			Ret ret = modelService.audit(ids, auditResult);
			renderJson(ret);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(Ret.fail().set("msg", "审核失败"));
	}

	/**
	 * 更新标志状态位，add_status =1 为审核单据
	 */
	public void updateStatue() {
		String auditId = getPara("audit_id");
		String order_id = getPara("order_id");
		if (!NumberUtils.isNumber(auditId) || (!NumberUtils.isNumber(order_id))) {
			renderJson(Ret.fail("msg", "订单ID数据异常，查看日志"));
			return;
		}
		try {
			/**
			 * order_status 2 是委外执行表，3 是委外审核表 
			 */
			Ret ret = modelService.updateAddStatus(auditId, "1", order_id, "3");

			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		renderJson(Ret.fail("msg", "数据异常，请查看日志"));

	}

	/**
	 * 流转生成采购审核单
	 */
	public void batchUpdateStatus() {
		String[] orderId = getParaValues("selectId");

		try {
			/**
			 * order_status 2 是委外执行表，3 是委外审核表 
			 */
			Ret ret = modelService.batchUpdateStatus(orderId);

			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		renderJson(Ret.fail("msg", "数据异常，请查看日志"));
	}

}
