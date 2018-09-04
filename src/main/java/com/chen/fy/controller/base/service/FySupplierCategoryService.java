package com.chen.fy.controller.base.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FySupplierCategory;
import com.jfinal.plugin.activerecord.Page;

public class FySupplierCategoryService {
	private static final Logger logger = LogManager.getLogger(FySupplierCategoryService.class);
	private static final FySupplierCategory dao = new FySupplierCategory().dao();
	public final static FySupplierCategoryService me = new FySupplierCategoryService();

	public boolean update(FySupplierCategory model) {
		return model.update();
	}

	public boolean delete(FySupplierCategory model) {
		return model.delete();
	}

	public boolean deleteById(Object id) {
		return dao.deleteById(id);
	}

	public Page<FySupplierCategory> paginate(int pageNumber, int pageSize, StringBuilder sb) {
		logger.info("");
		return dao.paginate(pageNumber, pageSize, "select * ", "from fy_supplier_category order by id desc");
	}

	public Page<FySupplierCategory> paginate(int pageNumber, int pageSize, String select, String form) {
		logger.info("");
		return dao.paginate(pageNumber, pageSize, select, form);
	}

	public FySupplierCategory findById(Object id) {
		return dao.findById(id);
	}

	public FySupplierCategory findFirst(Integer id) {
		return dao.findFirst(
				"select c.*,p.name parent_name,p.id parent_id from fy_supplier_category c LEFT JOIN fy_supplier_category p on c.parent_id = p.id  where c.id=?",
				id);
	}

}
