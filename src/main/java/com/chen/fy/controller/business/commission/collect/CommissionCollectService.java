package com.chen.fy.controller.business.commission.collect;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CommissionCollectService {
	public final static CommissionCollectService me = new CommissionCollectService();

	/**
	 * 委外汇总表
	 * @param pageSize
	 * @param pageIndex
	 * @param condition
	 * @param keyWord
	 * @return
	 */
	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword,
			String inhouse_date_start, String inhouse_date_end) {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id ,o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , s.name supplier_name ";
		String from = " from  fy_business_order o " + "   left join fy_base_fyfile  f on o.draw = f.id "
				+ " Inner join fy_business_purchase audit on o.id = audit.order_id"
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no";
		String desc = " order by o.id  desc ";
		String where = "  where add_status=3 ";
		String dateformat = "'%Y-%m-%d'";
		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		}
		if ("inhouse_status".equals(condition)) {
			if ("un_in".equals(keyword)) {
				conditionSb.append(" AND o.has_in_quantity = 0  ");
			} else if ("part_in".equals(keyword)) {
				conditionSb.append(" AND o.has_in_quantity  <  o.quantity AND o.has_in_quantity <> 0  ");
			} else if ("all_in".equals(keyword)) {
				conditionSb.append(" AND o.has_in_quantity  = o.quantity  ");
			}
		}

		if ("inhouse_date".equals(condition)) {
			if (StringUtils.isNotEmpty(inhouse_date_start)) {
				conditionSb.append(" AND o.inhouse_date  > '").append(inhouse_date_start).append("' ");
			}
			if (StringUtils.isNotEmpty(inhouse_date_end)) {
				conditionSb.append(" AND o.inhouse_date  < '").append(inhouse_date_end).append("' ");
			}
		}

		else {

			if ("order_date".equals(condition)) {

				conditionSb.append(String.format(" AND DATE_FORMAT(order_date,%s) = '%s'", dateformat, keyword));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format("AND   DATE_FORMAT(delivery_date,%s)= '%s'", dateformat, keyword));

			} else if ("work_order_no".equals(condition)) {
				conditionSb.append(" AND  o.work_order_no like  ");
				conditionSb.append("'%").append(keyword).append("%'");
			} else if ("supplier".equals(condition)) {
				conditionSb.append(" AND  s.name like  ");
				conditionSb.append("'%").append(keyword).append("%'");
			} else if (StringUtils.isNotEmpty(keyword)) {

				conditionSb.append(String.format(" AND  %s like  ", condition));
				conditionSb.append("'%").append(keyword).append("%'");

			}

		}

		if (conditionSb.length() > 0) {
			where = where + conditionSb.toString();
			List<Record> list = Db.find(select + from + where + desc);
			modelPage = new Page<Record>(list, 1, list.size(), 1, list.size());
		} else {
			modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);
		}

		return modelPage;
	}
}
