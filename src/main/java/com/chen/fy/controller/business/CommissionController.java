package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBizWwReceive;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class CommissionController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 and is_finish_purchase = 0    order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and ").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ", String.format(
					"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 and is_finish_purchase = 0  %s order by id desc",
					sb.toString()));
		}

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	/**
	 * 
	 */
	@Before(Tx.class)
	public void receiveOrder() {
		Integer id = getParaToInt("id", 1);
		FyBizWwReceive model = FyBizWwReceive.dao.findById(id);
		model.setIsReceive(true);
		Integer pid = model.getParentId();
		FyBusinessDistribute distribut = FyBusinessDistribute.dao.findById(pid);
		distribut.setReceiveTime(new Date());
		Ret ret = null;

		boolean re = model.update();
		boolean save = distribut.update();
		if (re && save) {
			ret = Ret.ok("msg", "接收成功");

		} else {
			ret = Ret.ok("msg", "接收失败");
		}
		renderJson(ret);
	}

	public void sumReceive() {
		String key = getPara("keyWord");
		Page<FyBizWwReceive> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 order by id desc");

		setAttr("modelPage", modelPage);
		render("sumReceive.html");
	}

	public void oneSumCommission() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		keepPara("keyWord", "condition");
		String purchase = "purchase_no,purchase_date,purchase_cost,purchase_account,discount,discount_account";
		String supplier_name = ", bs.name supplier_name";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
					"select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
					"from   fy_business_order   o  left   join fy_business_purchase  p  on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id "
							+ " left join  fy_business_in_warehouse w on o.id = w.order_id"
							+ " left join fy_base_supplier  bs on bs.id = p.supplier_id "
							+ " where dis_to = 1 order by id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and o.").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
					"select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
					"from   fy_business_order   o  inner  join fy_business_purchase  p  on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id "
							+ " left join  fy_business_in_warehouse w on o.id = w.order_id"
							+ " left join fy_base_supplier  bs on bs.id = p.supplier_id "
							+ " where dis_to = 1 order by id desc");

			// modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
			// "select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
			// String.format(
			// "from fy_business_purchase p left join fy_business_order o on p.order_id
			// =o.id left join fy_base_supplier s on p.supplier_id = s.id "
			// + " left join fy_business_in_warehouse w on o.id = w.order_id"
			// + " left join fy_base_supplier bs on bs.id = p.supplier_id "
			// + " where dis_to = 1 %s order by id desc",
			// sb.toString()));
		}

		setAttr("modelPage", modelPage);
		render("oneSumCommission.html");
	}

	public void toInWareHouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = new FyBusinessInWarehouse();
		model.setOrderId(id);
		model.setParentId(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		order.setInWarehouseTime(new Date());
		model.setInTime(order.getInWarehouseTime());
		model.setInFrom("委外");
		order.setIsCreateInHouse(true);

		String inQuantity = getPara("inQuantity");

		if (!NumberUtils.isNumber(inQuantity)) {
			renderJson(Ret.fail().set("msg", "失败"));
			return;
		}
		model.setRealInQuantity(new BigDecimal(inQuantity));
		Ret ret = null;
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.save() && order.update();

			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "生成入库单成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void toAddassist() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		setAttr("order", order);
		setAttr("model", new FyBusinessAssist());

		setAttr("action", "saveAssist");
		render("assist/edit.html");
	}

	public void toEditassist() {
		Integer id = getParaToInt("id");

		FyBusinessAssist model = FyBusinessAssist.dao.findFirst(
				"select a.*,s.name supplier_name from fy_business_assist a left join  fy_base_supplier s on a.assist_supplier_id = s.id  where a.id = ?",
				id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("order", order);
		setAttr("model", model);
		setAttr("action", "updateAssist");
		render("assist/edit.html");
	}

	public void saveAssist() {
		FyBusinessAssist model = getBean(FyBusinessAssist.class, "model");
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.save();

			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "生成入库单成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void updateAssist() {
		FyBusinessAssist model = getBean(FyBusinessAssist.class, "model");
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.update();

			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.fail().set("msg", "更新失败");
		}
		renderJson(ret);
	}

	public void assist() {
		String key = getPara("keyWord");
		Page<FyBusinessAssist> modelPage = null;
		keepPara("keyWord", "condition");
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
				+ ",s.name supplier_name";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), 10, "select a.* ,s.name supplier," + sql,
					"from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id  left join fy_base_supplier s on s.id = a.assist_supplier_id order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			String condition = getPara("condition");
			if ("name".equals(condition)) {
				sb.append(" and s.name like ").append("'%").append(key).append("%' ");
			} else {
				sb.append(" and o.work_order_no like ").append("'%").append(key).append("%' ");
			}
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), 10, "select a.* ," + sql,
					"from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id  left join fy_base_supplier s  on a.assist_supplier_id = s.id "
							+ sb.toString() + " order by id desc");
		}
		setAttr("modelPage", modelPage);
		render("assist.html");
	}

}
