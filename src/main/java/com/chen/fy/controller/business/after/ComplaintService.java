package com.chen.fy.controller.business.after;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ComplaintService {
	public final static ComplaintService me = new ComplaintService();
	private static final Logger logger = LogManager.getLogger(ComplaintService.class);

	public Page<Record> findPage(Integer pageIndex, Integer pageSize, String condition, String keyword) {
		Page<Record> modelPage = Db.paginate(pageIndex, pageSize,
				"select c.*,o.map_tmp,o.commodity_name, o.quantity ",
				"from  fy_complaint  c left  join fy_business_order o on c.order_id = o.id  order by id desc");
		return modelPage;
	}
}
