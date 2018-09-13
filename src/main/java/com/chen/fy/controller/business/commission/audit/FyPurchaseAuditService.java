package com.chen.fy.controller.business.commission.audit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyPurchaseAudit;
import com.jfinal.plugin.activerecord.Page;

public class FyPurchaseAuditService {
	private static final Logger logger = LogManager.getLogger(FyPurchaseAuditService.class);
	private static final FyPurchaseAudit dao = new FyPurchaseAudit().dao();
	public final static FyPurchaseAuditService me = new FyPurchaseAuditService();

	public boolean update(FyPurchaseAudit model) {
		return model.update();
	}

	public boolean delete(FyPurchaseAudit model) {
		return model.delete();
	}

	public boolean deleteById(Object id) {
		return dao.deleteById(id);
	}

	public Page<FyPurchaseAudit> paginate(int pageNumber, int pageSize, StringBuilder sb) {
		logger.info("");
		return dao.paginate(pageNumber, pageSize, "select * ", "from fy_purchase_audit order by id desc");
	}

	public FyPurchaseAudit findById(Object id) {
		return dao.findById(id);
	}

}
