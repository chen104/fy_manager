package com.chen.fy.controller.business.commission.audit;

import java.math.BigDecimal;
import java.sql.BatchUpdateException;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.Engine;
import com.jfinal.upload.UploadFile;

public class FyPurchaseAuditController extends BaseController {
	private static final Logger logger = LogManager.getLogger(FyPurchaseAuditController.class);
	FyPurchaseAuditService modelService = FyPurchaseAuditService.me;
	Engine engine;

	public FyPurchaseAuditController() {
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
		String condition = getPara("condition");
		setAttr("condition", condition);
		setAttr("keyWord", key);

		Page<FyBusinessPurchase> accountPage = modelService.paginate(getParaToInt("p", 1), getPageSize(), condition,
				key);
		Double total=0d;
		if(accountPage!=null&&accountPage.getList().size()>0) {
			for (FyBusinessPurchase e : accountPage.getList()) {
				BigDecimal purchaseAcount = e.getPurchaseAccount();
				Double item = purchaseAcount == null ? 0d : purchaseAcount.doubleValue();
				total += item;
			}
		}
		setAttr("purchaseAccount", total);
		setAttr("modelPage", accountPage);
		render("list.html");
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");
		Page<FyBusinessPurchase> modelPage = modelService.paginate(getParaToInt("p", 1) + 1, getPageSize(), condition,
				key);
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/commission/audit/list.jf").renderToString(data);

		Double total = 0d;
		if (modelPage != null && modelPage.getList().size() > 0) {
			for (FyBusinessPurchase e : modelPage.getList()) {
				BigDecimal purchaseAcount = e.getPurchaseAccount();
				Double item = purchaseAcount == null ? 0d : purchaseAcount.doubleValue();
				total += item;
			}
		}
		ret.set("purchaseAccount", total);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
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
		Ret ret = null;
		if (ufile != null) {
			try {
				ret = modelService.uploadRuqest(ufile.getFile());
				renderJson(ret);
				return;
			} catch (BatchUpdateException be) {
				be.printStackTrace();

				logger.error("报错订单");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("文件报错");
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.fail().set("msg", " 查看运行日志"));

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
