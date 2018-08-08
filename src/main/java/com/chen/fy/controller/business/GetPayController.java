package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class GetPayController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		keepPara("keyWord", "condition");

		if (StringUtils.isEmpty(key)) {

			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id  where is_create_get_pay = 1 order by w.id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			String condition = getPara("condition");
			sb.append(" and  o.").append(condition).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id   where is_create_get_pay = 1 "
							+ sb.toString() + "  order by w.id desc");

		}
		setAttr("modelPage", modelPage);
		render("getPay.html");
	}

	public void add() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);

		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		// 价格乘以数量，未税金额
		BigDecimal untax = order.getUntaxedCost().multiply(model.getOutQuantity());
		model.setUntaxGetpay(untax);
		BigDecimal tax = untax.multiply(order.getTaxRate());// 税额

		model.setTax(tax);
		model.setHangAmount(untax.add(tax));// 挂账金额，应付金额
		model.setCreateMonth(calendar.get(Calendar.MONTH));
		calendar.add(Calendar.MONTH, 3);
		model.setGetpayMonth(calendar.get(Calendar.MONTH));
		setAttr("model", model);
		setAttr("order", order);
		setAttr("action", "save");
		setAttr("isAdd", true);
		render("add.html");
	}

	/**
	 * 新增
	 */
	public void save() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "model");

		model.setIsCreateGetPay(true);
		model.setCreateGetpayTime(new Date());
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		// 挂账数量
		BigDecimal hangQuantity = order.getHangQuantity();

		BigDecimal newhangQuantity = model.getOutQuantity().add(hangQuantity);
		order.setHangQuantity(newhangQuantity);// 挂账数量

		// 未挂账数量
		BigDecimal unhangQuantity = order.getUnhangQuantity();

		// 未挂账
		BigDecimal newunhangQuantity = unhangQuantity.subtract(model.getOutQuantity());// 未挂账数量
		order.setUnhangQuantity(newunhangQuantity);

		if (newunhangQuantity.doubleValue() > 0) {
			order.setHangStatus("部分挂账");
		}
		// 挂账数量
		order.setHangAccount(order.getHangAccount().add(model.getHangAmount()));

		order.setHangTime(new Date());// 最后挂账时间

		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {

				return model.update() && order.update();
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "生成成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	/**
	 * 
	 */
	public void update() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "model");

		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.fail().set("msg", "更新失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("model", model);
		setAttr("order", order);
		setAttr("action", "update");
		render("add.html");
	}

	public void updateDownload() {
		String id = getPara("id");

		int i = Db.update("update fy_business_out_warehouse set can_download = 1 where id = ? ", id);

		Ret ret = null;
		if (i == 1) {
			ret = Ret.ok().set("msg", "生成成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}
}
