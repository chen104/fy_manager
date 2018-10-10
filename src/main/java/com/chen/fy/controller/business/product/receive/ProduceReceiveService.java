package com.chen.fy.controller.business.product.receive;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class ProduceReceiveService {
	public static final ProduceReceiveService me = new ProduceReceiveService();

	public Page<FyBusinessOrder> findPage(Integer pageSize, Integer page, String condition, String keyWord)
			throws Exception {
		Page<FyBusinessOrder> modelPage = null;

		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = "from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where    dis_to = 0 and order_status =10   ";
		String desc = " order by distribute_time desc ";
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

				conditionSb.append(
						String.format("AND  DATE_FORMAT(order_date,%s) = '%s'", Constant.mysql_date_format, keyWord));

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

	/**
	 * 更新订单的 接收时间
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public int updateReceive(String[] ids) throws Exception {
		StringBuilder updateSql = new StringBuilder(
				" update fy_business_order set receive_time = NOW(),order_status=11 where id in ");
		SqlKit.joinIds(ids, updateSql);
		return Db.update(updateSql.toString());
	}

}
