package com.chen.fy.controller.addition;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessReady;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;

public class ReadyService {
	public final static ReadyService me = new ReadyService();
	public final static FyBusinessReady dao = FyBusinessReady.dao;
	private static final Logger logger = LogManager.getLogger(ReadyService.class);

	public void upload() {
	}

	public String download() {
		return null;
	}

	/**
	 *补单
	 * @param order_id
	 * @param ready_id
	 * @return
	 */
	public Ret selectReady(Integer order_id, Integer ready_id) {
		try {
			FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id);
			FyBusinessReady model = dao.findById(ready_id);

			if (order == null || model == null) {
				return Ret.fail().set("msg", "备货失败，请刷新后在操作");
			}

			Ret ret = null;
			// 更新工作订单号，补单状态
			BigDecimal quantity1 = model.getAddQuantity1();
			String workNo1 = model.getWorkOrderNo1();

			BigDecimal quantity2 = model.getAddQuantity2();
			String workNo2 = model.getWorkOrderNo2();

			BigDecimal quantity3 = model.getAddQuantity3();
			String workNo3 = model.getWorkOrderNo3();
			// 认为是第一次补单
			if (quantity1.doubleValue() == 0 && StringUtils.isEmpty(workNo1)) {
				model.setWorkOrderNo1(order.getWorkOrderNo());
				model.setAddQuantity1(order.getQuantity());
				model.setExecuStatus("备货");
				model.setAddStatus("部分补单");
			} else if (quantity2.doubleValue() == 0 && StringUtils.isEmpty(workNo2)) {
				model.setWorkOrderNo2(order.getWorkOrderNo());
				model.setAddQuantity2(order.getQuantity());
			} else if (quantity3.doubleValue() == 0 && StringUtils.isEmpty(workNo3)) {
				model.setWorkOrderNo3(order.getWorkOrderNo());
				model.setAddQuantity3(order.getQuantity());
			} else {
				ret = Ret.fail().set("msg", "已补单三次，不能再补单");
				return ret;
			}
			if (model.getQuantity() != null) {
				if ((model.getAddQuantity1().doubleValue() + model.getAddQuantity2().doubleValue()
						+ model.getAddQuantity3().doubleValue()) == model.getQuantity().doubleValue()) {
					model.setAddStatus("已补单");
				}
			}
			order.setReadyId(model.getId());
			boolean re = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {

					return order.update() && model.update();
				}
			});
			if (re) {
				ret = Ret.ok().set("msg", "补单完成");
			} else {
				ret = Ret.ok().set("msg", "失败");
			}
			return ret;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return Ret.ok().set("msg", "补单失败 ，请查看日志");
		}
	}
}
