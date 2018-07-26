package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessProduce;
import com.chen.fy.model.FyBusinessWarehouse;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class ProduceController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {

		Page<FyBusinessProduce> modelPage = null;

		modelPage = FyBusinessProduce.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_produce  where is_receive = 0  order by id desc");

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

	public void sumprod() {
		String key = getPara("keyWord");
		Page<FyBusinessProduce> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessProduce.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_produce  where is_create_plan = 0 order by id desc");
		setAttr("modelPage", modelPage);
		render("sumProduce.html");
	}

	public void toPlanPro() {
		Integer id = getParaToInt("id");
		Date starTime = getParaToDate("beginTime");
		Date finshTime = getParaToDate("finishTime");
		String remark = getPara("remark");
		FyBusinessProduce model = FyBusinessProduce.dao.findById(id);
		model.setPlanTime(starTime);
		model.setPlanFinshTime(finshTime);
		model.setIsCreatePlan(true);
		model.setPlanRemark(remark);
		Ret ret = null;
		boolean re = model.update();

		if (re) {
			ret = Ret.ok("msg", "接收成功");

		} else {
			ret = Ret.ok("msg", "接收失败");
		}
		renderJson(ret);

	}

	public void planPro() {
		String key = getPara("keyWord");
		Page<FyBusinessProduce> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessProduce.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_produce where is_create_plan = 1 order by id desc");

		setAttr("modelPage", modelPage);
		render("producePlan.html");
	}

	public void oneSumPro() {
		String key = getPara("keyWord");
		Page<FyBusinessProduce> modelPage = null;
		setAttr("keyWord", key);
		modelPage = FyBusinessProduce.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_produce order by id desc");

		setAttr("modelPage", modelPage);
		render("oneSumProduce.html");
	}

	public void toInWareHouse() {
		Integer id = getParaToInt("id");
		String inQuantity = getPara("inQuantity");
		if (!NumberUtils.isNumber(inQuantity)) {
			renderJson(Ret.fail().set("msg", "失败"));
			return;
		}

		FyBusinessProduce model = FyBusinessProduce.dao.findById(id);
		FyBusinessWarehouse bhouse = new FyBusinessWarehouse();
		bhouse.setRealInQuantity(new BigDecimal(inQuantity));
		bhouse.setCategoryId(model.getCategoryId());
		bhouse.setCategoryId(model.getCategoryId());
		bhouse.setCategoryTmp(model.getCategoryTmp());
		bhouse.setPlanerId(model.getPlanerId());
		bhouse.setPlanTmp(model.getPlanTmp());
		bhouse.setExecuStatus(model.getExecuStatus());
		bhouse.setUrgentStatus(model.getUrgentStatus());
		bhouse.setOrderDate(model.getOrderDate());

		bhouse.setDeliveryDate(model.getDeliveryDate());
		bhouse.setWorkOrderNo(model.getWorkOrderNo());
		bhouse.setDeliveryNo(model.getDeliveryNo());
		bhouse.setCommodityName(model.getCommodityName());
		bhouse.setCommoditySpec(model.getCommoditySpec());
		bhouse.setMapNo(model.getMapNo());
		bhouse.setTechnology(model.getTechnology());
		bhouse.setMachiningRequire(model.getMachiningRequire());
		bhouse.setQuantity(model.getQuantity());
		bhouse.setUnit(model.getUnit());
		bhouse.setUnitTmp(model.getUnitTmp());
		bhouse.setOrderId(model.getOrderId());
		bhouse.setParentId(model.getId());
		bhouse.setInFrom("本部");
		model.setIsCreateInHouse(true);
		model.setInWarehouseTime(new Date());
		bhouse.setInTime(model.getInWarehouseTime());
		boolean re = model.update();
		boolean re1 = bhouse.save();
		Ret ret = null;
		if (re && re1) {
			ret = Ret.ok().set("msg", "生成入库单成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

}
