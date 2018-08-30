package com.chen.fy.controller.business.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.CommonKit;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class ProductService {
	public final static ProductService me = new ProductService();
	public final static FyBusinessOrder dao = FyBusinessOrder.dao;
	private static final Logger logger = LogManager.getLogger(ProductService.class);

	/**
	 * 批量生成计划单
	 * @param ids
	 * @param start
	 * @param end
	 * @param remark
	 * @return
	 */
	public Ret batchCreatePlanProduct(Integer[] ids, String start, String end, String remark) {
		Date startPlant = null;
		Date finshPlant = null;
		try {
			startPlant = DateUtils.parseDate(start, "yyyy-MM-dd");
			finshPlant = DateUtils.parseDate(end, "yyyy-MM-dd");
		} catch (Exception e) {
			logger.warn("批量生产自产单据，时间解析错误");
			return Ret.fail().set("msg", "没有开始时间，或结束时间");
		}
		try {
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(ids, sb);
			List<FyBusinessOrder> models = FyBusinessOrder.dao
					.find("select * from fy_business_order where  id in " + sb.toString());
			for (FyBusinessOrder model : models) {
				model.setPlanTime(startPlant);
				model.setPlanFinshTime(finshPlant);
				model.setIsCreatePlan(true);
				model.setPlanRemark(remark);// 备注
				model.setHandleStatus("已进入生产计划");
			}
			Ret ret = null;

			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					int[] re = Db.batchUpdate(models, models.size());
					return CommonKit.totalInt(re) == models.size();

				}
			});
			if (re) {
				ret = Ret.ok("msg", "接收成功");

			} else {
				ret = Ret.ok("msg", "接收失败");
			}
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return Ret.fail().set("msg", "请查看日志");
		}
	}

	/**
	 * 批量生成计划单
	 * @param ids
	 * @param start
	 * @param end
	 * @param remark
	 * @return
	 */
	public Ret batchUpdatePlanProduct(Integer[] ids, String start, String end, String remark) {
		Date startPlant = null;
		Date finshPlant = null;
		try {
			startPlant = DateUtils.parseDate(start, "yyyy-MM-dd");
			finshPlant = DateUtils.parseDate(end, "yyyy-MM-dd");
		} catch (Exception e) {
			logger.warn("批量生产自产单据，时间解析错误");
			return Ret.fail().set("msg", "没有开始时间，或结束时间");
		}
		try {
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(ids, sb);
			List<FyBusinessOrder> models = FyBusinessOrder.dao
					.find("select * from fy_business_order where  id in " + sb.toString());
			for (FyBusinessOrder model : models) {
				model.setPlanTime(startPlant);
				model.setPlanFinshTime(finshPlant);

				model.setPlanRemark(remark);// 备注

			}
			Ret ret = null;

			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					int[] re = Db.batchUpdate(models, models.size());
					return CommonKit.totalInt(re) == models.size();

				}
			});
			if (re) {
				ret = Ret.ok("msg", "更新成功");

			} else {
				ret = Ret.ok("msg", "更新失败");
			}
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return Ret.fail().set("msg", "请查看日志");
		}
	}

	public Ret batchUpdateStartTimePlanProduct(Integer[] ids, String start) {

		try {
			DateUtils.parseDate(start, "yyyy-MM-dd");

		} catch (Exception e) {
			logger.warn("批量生产自产单据，时间解析错误");
			return Ret.fail().set("msg", "没有开始时间，或结束时间");
		}
		try {

			Ret ret = null;
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(ids, sb);
			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					int re = Db.update("update fy_business_order o where in \r\n" + sb.toString()
							+ "set o.plan_time =STR_TO_DATE('" + start + "','%y-%m-%d')\r\n");
					return re == ids.length;

				}
			});
			if (re) {
				ret = Ret.ok("msg", "更新成功");

			} else {
				ret = Ret.ok("msg", "更新失败");
			}
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return Ret.fail().set("msg", "请查看日志");
		}
	}

	public Ret batchUpdateFinshPlanProduct(Integer[] ids, String end) {

		try {
			DateUtils.parseDate(end, "yyyy-MM-dd");

		} catch (Exception e) {
			logger.warn("批量生产自产单据，时间解析错误");
			return Ret.fail().set("msg", "没有开始时间，或结束时间");
		}
		try {

			Ret ret = null;
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(ids, sb);
			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					int re = Db.update("update fy_business_order o where in \r\n" + sb.toString()
							+ "set  o.plan_finsh_time  =STR_TO_DATE('" + end + "','%y-%m-%d')\r\n");
					return re == ids.length;

				}
			});
			if (re) {
				ret = Ret.ok("msg", "更新成功");

			} else {
				ret = Ret.ok("msg", "更新失败");
			}
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return Ret.fail().set("msg", "请查看日志");
		}
	}

	/**
	 * 更新计划时间
	 * @param ids
	 * @param start
	 * @param end
	 * @return
	 */
	public Ret updatePlanTimeProduct(Integer[] ids, String start, String end) {
		StringBuilder set = new StringBuilder();
		try {
			if (StringUtils.isNotEmpty(start)) {
				DateUtils.parseDate(start, "yyyy-MM-dd");
				set.append("o.plan_time =STR_TO_DATE('" + start + "','%Y-%m-%d')");
			} else {

			}
			if (StringUtils.isNotEmpty(end)) {
				DateUtils.parseDate(end, "yyyy-MM-dd");
				if (set.length() > 0) {
					set.append(", o.plan_finsh_time  = STR_TO_DATE('" + end + "','%Y-%m-%d')");
				} else {
					set.append("  o.plan_finsh_time  = STR_TO_DATE('" + end + "','%Y-%m-%d')");
				}
			}

		} catch (Exception e) {
			logger.warn("批量生产自产单据，时间解析错误");
			return Ret.fail().set("msg", "没有开始时间，或结束时间");
		}
		try {

			Ret ret = null;
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(ids, sb);
			boolean re = Db.tx(new IAtom() {
				public boolean run() throws SQLException {
					String exstr = "update fy_business_order o  set   " + set.toString() + " where id in  "
							+ sb.toString();

					int re = Db.update(exstr);
					return re == ids.length;

				}
			});
			if (re) {
				ret = Ret.ok("msg", "更新成功");

			} else {
				ret = Ret.ok("msg", "更新失败");
			}
			return ret;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return Ret.fail().set("msg", "请查看日志");
		}
	}
}
