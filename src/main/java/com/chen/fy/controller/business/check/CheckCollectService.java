package com.chen.fy.controller.business.check;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CheckCollectService {
	public final static CheckCollectService me = new CheckCollectService();
	private static final Logger logger = LogManager.getLogger(CheckCollectService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = "select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name "
				+ ",inhouse.*,inhouse.in_quantity in_quantity,inhouse.id inhouse_id";
		String from = "from  fy_business_order o  "
				+ " INNER JOIN fy_business_in_warehouse inhouse on o.id = inhouse.order_id "
				+ " left join fy_base_fyfile  f on o.draw = f.id  "
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id "
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no ";
		String where = " where inhouse.check_result is not null ";
		String desc = " order by inhouse.id desc ";
		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		} else {

			if ("order_date".equals(condition)) {

				conditionSb.append(String.format(" AND order_date = '%s'", keyword));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format("AND  delivery_date = '%s'", keyword));

			} else if ("work_order_no".equals(condition)) {
				conditionSb.append(" AND  o.work_order_no like  ");
				conditionSb.append("'%").append(keyword).append("%'");
			} else if (StringUtils.isNotEmpty(keyword)) {

				conditionSb.append(String.format(" AND  %s like  ", condition));
				conditionSb.append("'%").append(keyword).append("%'");

			}

		}
		where = where + conditionSb.toString();
		modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);

		return modelPage;
	}
}
