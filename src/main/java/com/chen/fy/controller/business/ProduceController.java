package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessProduce;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class ProduceController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {

		Page<FyBusinessProduce> modelPage = null;

		modelPage = FyBusinessProduce.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order  where Is_Distribute = 1 and dis_to = 0 and is_create_plan = 0 order by id desc");

		setAttr("modelPage", modelPage);
		render("produce.html");
	}

	/**
	 * 
	 */
	@Before(Tx.class)
	public void receive() {
		Integer id = getParaToInt("id", 1);
		FyBusinessProduce model = FyBusinessProduce.dao.findById(id);
		// model.setIsReceive(true);
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

	public void sumprod() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order  where Is_Distribute = 1 and dis_to = 0    order by id desc");
		setAttr("modelPage", modelPage);
		render("sumProduce.html");
	}

	/**
	 * 生产汇总表生成生产计划表
	 */
	@Before(Tx.class)
	public void toPlanPro() {
		Integer id = getParaToInt("id");
		Date starTime = getParaToDate("beginTime");
		Date finshTime = getParaToDate("finishTime");
		String remark = getPara("remark");
		FyBusinessOrder model = FyBusinessOrder.dao.findById(id);

		model.setPlanTime(starTime);
		model.setPlanFinshTime(finshTime);
		model.setIsCreatePlan(true);
		model.setPlanRemark(remark);
		model.setHangStatus("已处理");
		Ret ret = null;

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return model.update();

			}
		});
		if (re) {
			ret = Ret.ok("msg", "接收成功");

		} else {
			ret = Ret.ok("msg", "接收失败");
		}
		renderJson(ret);

	}

	public void planPro() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order where is_create_plan = 1 order by id desc");

		setAttr("modelPage", modelPage);
		render("producePlan.html");
	}

	public void oneSumPro() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order where  is_create_plan = 1 order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(" and %s like  ", getPara("condition"), key));
			sb.append("'%").append(key).append("%' ");
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ", String
					.format("from  fy_business_order where  is_create_plan = 1 %s order by id desc", sb.toString()));
		}
		setAttr("modelPage", modelPage);
		render("oneSumProduce.html");
	}

	public void toInWareHouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = new FyBusinessInWarehouse();
		model.setOrderId(id);
		model.setParentId(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		order.setInWarehouseTime(new Date());
		model.setInTime(order.getInWarehouseTime());
		model.setInFrom("本部");
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

}
