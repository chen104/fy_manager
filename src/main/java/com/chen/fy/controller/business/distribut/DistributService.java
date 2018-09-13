package com.chen.fy.controller.business.distribut;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.plugin.activerecord.Page;

public class DistributService {
	private static final Logger logger = LogManager.getLogger(DistributService.class);
	public static final DistributService me = new DistributService();

	public Page<FyBusinessOrder> findPage(Integer pageSize, Integer page, String condition, String keyWord)
			throws Exception {
		Page<FyBusinessOrder> modelPage = null;

		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = "from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where distribute_to is null ";
		String desc = " order by o.id desc,is_distribute desc ";
		StringBuilder conditionSb = new StringBuilder();

		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
			where = where + conditionSb.toString();
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);
			where = where + conditionSb.toString();
		} else {

			if ("order_date".equals(condition)) {

				conditionSb.append(String.format("AND order_date = '%s'", keyWord));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format("and  delivery_date = '%s'", keyWord));

			} else if (StringUtils.isNotEmpty(keyWord)) {

				conditionSb.append(String.format("and  %s like  ", condition));
				conditionSb.append("'%").append(keyWord).append("%'");

			}
			if (StringUtils.isNotEmpty(keyWord)) {
				where = where + conditionSb.toString();
			}
		}
		modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select, from + where + desc);
		return modelPage;
	}

}
