package com.chen.fy.controller.business.storage;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class StorageService {
	public final static StorageService me = new StorageService();
	private static final Logger logger = LogManager.getLogger(StorageService.class);

	public Page<Record> findPage(Integer currentPage, Integer pageSize, String condition, String keyword)
			throws Exception {
		Page<Record> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = " select o.* ,f.originalFileName filename,f.id fileId ,audit.* ,audit.id audit_id , \n"
				+ "o.id order_id,o.work_order_no work_order_no,audit.supplier_no supplier_no , \n"
				+ " s.name supplier_name ,o.id order_id,ov.v_out_quantity v_out_quantity\n";
		String from = " from  fy_business_order o \n" + "   left join fy_base_fyfile  f on o.draw = f.id  \n"
				+ " LEFT join fy_business_purchase audit on o.id = audit.order_id \n"
				+ " LEFT JOIN fy_base_supplier s on audit.supplier_no = s.supplier_no \n"
				+ "  LEFT JOIN out_view ov on ov.order_id = o.id ";
		String desc = " order by o.id  desc ";
		String where = "  where o.storage_quantity > 0  ";
		if (StringUtils.isNotEmpty(keyword)) {
			if ("delay_warn".equals(condition)) {
				String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
				conditionSb.append(sql);
			} else

			if ("delay".equals(condition)) {
				String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
				conditionSb.append(sql);

			} else if ("total_map_no".equals(condition)) {
				conditionSb.append("  AND    o.total_map_no  like  '%").append(keyword).append("%' ");
			}

			else {

				if ("order_date".equals(condition)) {

					conditionSb.append(String.format(" AND DATE_FORMAT(order_date,%s) = '%s'",
							Constant.mysql_date_format, keyword));

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
		}
		where = where + conditionSb.toString();
		modelPage = Db.paginate(currentPage, pageSize, select, from + where + desc);

		return modelPage;
	}

	/**
	 * 撤回库存到，待检测
	 * @param ids
	 * @return
	 */
	public Ret rollback(String[] ids) {
		Ret ret = null;
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);
		List<Record> list = Db.find(
				"select distribute_attr,distribute_to,id from  fy_business_order where  dis_to = 1  AND id in "
						+ idsb.toString());
		if (list.size() > 0) {
			ret = Ret.fail().set("msg", "存在委外单，请在应付单撤回");
			return ret;
		}
		String updateSql = Db.getSql("storage.rollbackProductStorage");
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				String  sql = String.format(updateSql, idsb.toString());

				logger.debug("撤回 库存 sql  " + sql);
				int updateCount = Db.update(sql);
				return (updateCount) == (ids.length * 2);
			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "撤回完成");
		} else {
			ret = Ret.ok().set("msg", "撤回失败，刷新之后再撤回");
		}
		return ret;
	}

}
