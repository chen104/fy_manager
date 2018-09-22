package com.chen.fy.controller.business;

import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessBill;
import com.chen.fy.model.FyBusinessGetpaybill;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.chen.fy.model.FyBusinessPaybill;
import com.chen.fy.model.FyBusinessSumPaybill;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

/**
 * 财务相关
 * 
 * @author Administrator
 *
 */
public class FinanceController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	/**
	 * 应收明细表
	 */
	public void getPayTest() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		keepPara("keyWord", "condition");

		if (StringUtils.isEmpty(key)) {

			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id   order by w.id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			String condition = getPara("condition");
			sb.append(" where o.").append(condition).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id "
							+ sb.toString() + "  order by w.id desc");

		}
		setAttr("modelPage", modelPage);
		render("getPay.html");
	}

	/**
	 * 添加结算单填写界面
	 */
	public void addbill() {

		setAttr("action", "savebill");
		Integer id = getParaToInt("id");
		// FyBusinessGetpay model = FyBusinessGetpay.dao.findById(id);
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("model", model);
		setAttr("order", order);
		render("addGetbill.html");

	}

	public void savebill() {
		FyBusinessOutWarehouse tmp = getBean(FyBusinessOutWarehouse.class, "model");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(tmp.getId());
		// model.setBillCreateTime(new Date());
		// model.setIsCreateBill(true);
		// model.setBillQuantity(tmp.getBillQuantity());
		// model.setHangStatus(tmp.getHangStatus());
		// model.setHangAmount(tmp.getHangAmount());
		// model.setHangTime(tmp.getHangTime());
		// model.setUnhangQuantity(tmp.getUnhangQuantity());
		// model.setHangQuantity(tmp.getHangQuantity());

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return model.update();
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
	}

	/**
	 * 应收结算单
	 */
	public void getbill() {
		Page<FyBusinessOutWarehouse> modelPage = null;
		String sql = "cate_tmp,plan_tmp,execu_status,urgent_status,order_date,delivery_date,work_order_no,"
				+ "delivery_no,commodity_name,commodity_spec,map_no,technology,machining_require,"
				+ "quantity,unit_tmp,untaxed_cost,amount,taxRate,tax_amount,tatol_amount";

		modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10, "select w.* ," + sql,
				"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id  where w.is_create_bill = 1  order by w.id desc");

		setAttr("modelPage", modelPage);
		render("getbill.html");
	}

	public void addgetpaybill() {
		Integer id = getParaToInt("id");
		setAttr("action", "saveGetpaybill");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		setAttr("model", model);
		render("addGetpaybill.html");

	}

	public void saveGetpaybill() {
		FyBusinessGetpaybill model = getBean(FyBusinessGetpaybill.class, "model");
		Integer id = getParaToInt("parentId");
		FyBusinessBill parent = FyBusinessBill.dao.findById(id);
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				parent.setPaybillCreateTime(new Date());
				parent.setIsCreatePaybill(true);
				boolean b1 = parent.update();
				boolean b2 = model.save();
				return (b1 & b2);
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
	}

	/**
	 * 应收汇总表
	 */
	public void getpaybill() {
		String key = getPara("keyWord");
		Page<FyBusinessGetpaybill> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessGetpaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_getpaybill order by id desc");
		} else {
			modelPage = FyBusinessGetpaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_getpaybill where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("getPaybill.html");
	}

	/**
	 * 应付计算单
	 */
	public void paybill() {
		String key = getPara("keyWord");
		Page<FyBusinessPaybill> modelPage = null;
		keepPara("keyWord", "condition");

		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_paybill order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			String condition = getPara("condition");
			sb.append(" where o.").append(condition).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_paybill where commodity_name like ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("paybill.html");
	}

	/**
	 * 应付计算单
	 */
	public void sumpaybill() {
		String key = getPara("keyWord");
		Page<FyBusinessSumPaybill> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessSumPaybill.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_paybill order by id desc");

		setAttr("modelPage", modelPage);
		render("sumpay.html");
	}

	/**
	 * 外协添加pay
	 */
	public void addpay() {
		Integer id = getParaToInt("id");
		FyBusinessAssist assist = FyBusinessAssist.dao.findFirst(
				"select a,*,s.name supplier_name from fy_business_assist a left join fy_base_supplier s on s.id = a.assist_supplier_id where a.id= ? ",
				id);

		FyBusinessOrder order = FyBusinessOrder.dao.findById(assist.getOrderId());

		setAttr("assist", assist);
		setAttr("order", order);
		render("");
	}

	/**
	 * 外协pay
	 */
}
