package com.chen.fy.controller.business.product.collect;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PlanCollectService {
	public static final PlanCollectService me = new PlanCollectService();

	public Page<FyBusinessOrder> findPage(Integer pageSize, Integer page, String condition, String keyWord)
			throws Exception {
		Page<FyBusinessOrder> modelPage = null;

		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = "from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where    dis_to = 0 and order_status=12   ";
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

	/**
	 * 查找计划单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<Record> tofindPlan(String[] ids) throws Exception {
		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = " from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		String where = " where   o.id in  ";
		String desc = " order by o.id desc ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		List<Record> list = Db.find(select + from + where + sb.toString() + desc);
		return list;
	}

	/**
	 * 
	 * @param ids
	 * @param startDate
	 * @param finshDate
	 * @param remark
	 * @return
	 * @throws Exception
	 */
	public Ret updatePlanTime(String[] ids, String startDate, String finshDate, String remark) throws Exception {
		if (StringUtils.isEmpty(startDate)) {
			return Ret.fail().set("msg", "计划开始时间不能为空");
		}
		try {
			startDate = startDate.trim();
			DateUtils.parseDate(startDate, "yyyy-MM-dd");
		} catch (Exception e) {
			return Ret.fail().set("msg", "计划时间格式不对");
		}
		try {
			if (StringUtils.isNotEmpty(finshDate)) {
				finshDate = finshDate.trim();
				DateUtils.parseDate(finshDate, "yyyy-MM-dd");
			}
		} catch (Exception e) {
			return Ret.fail().set("msg", "计划结束时间格式不对");
		}

		String sql = " update  fy_business_order o set   plan_time =  STR_TO_DATE('" + startDate + "','%Y-%m-%d') ";
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(finshDate)) {
			sb.append(",plan_finsh_time =STR_TO_DATE('" + finshDate + "','%Y-%m-%d')");
		}
		if (StringUtils.isNotEmpty(remark)) {
			remark = remark.trim();
			sb.append(",plan_remark = '").append(remark).append("' ");
		}

		sql = sql + sb.toString();
		StringBuilder update = new StringBuilder(sql);
		update.append(" where id in ");
		SqlKit.joinIds(ids, update);
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int re = Db.update(update.toString());
				return re == ids.length;
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.ok().set("msg", "更新失败");
		}

		return ret;
	}

}
