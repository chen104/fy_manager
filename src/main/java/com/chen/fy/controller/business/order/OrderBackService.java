package com.chen.fy.controller.business.order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.ICallback;
import com.jfinal.plugin.activerecord.Page;

public class OrderBackService {
	public final static OrderBackService me = new OrderBackService();

	/**
	 * 首页查找
	 * @param condition
	 * @param keyWord
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<FyBusinessOrder> find(String condition, String keyWord, Integer page, Integer pageSize,
			String order_date_start, String order_date_end) {
		Page<FyBusinessOrder> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = "select o.*,f.originalFileName filename,f.id fileId ";
		String from = " from  fy_business_order_delete o left join fy_base_fyfile  f on o.draw = f.id ";
		String where = " where 1=1 ";
		String desc = " order by o.id desc";

		if (StringUtils.isNotEmpty(order_date_start)) {
			conditionSb.append(String.format(" AND order_date > '%s'", order_date_start));
		}
		if (StringUtils.isNotEmpty(order_date_end)) {
			conditionSb.append(String.format(" AND order_date < '%s'", order_date_end));
		}

		if ("delay_warn".equals(condition)) {
			String sql = " AND   DATEDIFF(delivery_date , NOW()) < 3 and out_quantity = 0 ";
			conditionSb.append(sql);
		}

		if ("delay".equals(condition)) {
			String sql = " AND   DATEDIFF(delivery_date , NOW()) < 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		}
		if ("delivery_date".equals(condition)) {

			conditionSb.append(String.format(" AND  delivery_date = '%s'", keyWord));

		} else if (StringUtils.isNotEmpty(keyWord)) {

			conditionSb.append(String.format(" AND %s like  ", condition, keyWord));
			conditionSb.append("'%").append(keyWord).append("%'");
		}
		if (conditionSb.length() > 0) {
			List<FyBusinessOrder> list = FyBusinessOrder.dao
					.find(select + from + where + conditionSb.toString() + desc);
			modelPage = new Page<FyBusinessOrder>(list, 1, list.size(), 1, list.size());
		} else {
			modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select, from + where + desc);

		}

		return modelPage;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Ret backOrder(String[] ids) throws Exception {
		Ret ret = null;
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);
		String sql = "insert INTO fy_business_order SELECT * from   fy_business_order_delete  where id in   "
				+ idsb.toString();
		String delete = " delete from fy_business_order_delete where id in " + idsb.toString();
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {

				Object o = Db.execute(new ICallback() {

					@Override
					public Object call(Connection conn) throws SQLException {
						int in = conn.createStatement().executeUpdate(sql);
						int dnum = conn.createStatement().executeUpdate(delete);
						return in == dnum;
					}
				});
				if (o instanceof Boolean) {
					return (Boolean) o;
				}

				return false;
			}
		});

		if (re) {
			ret = Ret.ok().set("msg", "恢复完成");
		} else {
			ret = Ret.ok().set("msg", "回复失败");
		}
		return ret;
	}

}
