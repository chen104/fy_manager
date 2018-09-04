package com.chen.fy.controller.business;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ProductOneSumController extends BaseController {
	private static final Logger logger = LogManager.getLogger(ProductOneSumController.class);

	public void index() {
		keepPara("condition", "keyWord", "purchase_date");

		Integer pageSize = getParaToInt("pageSize", 30);
		setAttr("pageSize", pageSize);
		String select = "select *,s.name supplier_name,file.id  fileId ,file.originalFileName filename";
		String from = "  from fy_business_order o \r\n" + "LEFT JOIN fy_business_purchase p\r\n"
				+ "ON o.id = p.order_id \r\n" + "LEFT JOIN fy_base_supplier s\r\n" + "on p.supplier_id = s.id "
				+ "LEFT JOIN fy_base_fyfile file on o.draw = file.id ";
		setAttr("append", "&pageSize=" + pageSize);
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");
		if (StringUtils.isEmpty(key) && "purchase_date".equals(condition)) {
			key = getPara("purchase_date");
		}
		Page<Record> modelPage = null;
		if (StringUtils.isEmpty(key)) {
			modelPage = Db.paginate(getParaToInt("p", 1), pageSize, select, from);
		} else {

			String where = "";
			if ("purchase_date".equals(condition)) {
				key = getPara("purchase_date");
				where = " where purchase_date ='" + key + "'";
			} else if ("supplier".equals(condition)) {
				where = " where s.name  like '%" + key + "%' ";
			} else {
				where = " where  " + condition + " like '" + key + "' ";
			}
			modelPage = Db.paginate(getParaToInt("p", 1), pageSize, select, from + where);
		}

		setAttr("modelPage", modelPage);
		render("list.html");
	}
}
