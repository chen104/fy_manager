package com.chen.fy.controller.business.after;

import java.sql.SQLException;
import java.util.Date;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.chen.fy.model.FyComplaint;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class ComplaintController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		Page<FyComplaint> modelPage = null;
		String condition = getPara("condition");

		keepPara("condition", "keyWord", "order_date");
		Integer pageSize = getParaToInt("pageSize", 10);
		setAttr("pageSize", pageSize);
		setAttr("append", "&pageSize=" + pageSize);
		setAttr("keyWord", key);

		modelPage = FyComplaint.dao.paginate(getParaToInt("p", 1), getPageSize(),
				"select c.*,o.map_tmp,o.commodity_name, o.quantity ",
				"from  fy_complaint  c left  join fy_business_order o on c.order_id = o.id  order by id desc");

		setAttr("modelPage", modelPage);
		render("complaint.html");
	}

	public void add() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("order", order);
		setAttr("out", model);
		setAttr("action", "save");
		render("edit.html");
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyComplaint model = FyComplaint.dao.findById(id);
		Date date = getParaToDate("model.complaintDate");
		model.setComplaintDate(date);

		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		setAttr("order", order);
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");

	}

	public void update() {
		FyComplaint model = getBean(FyComplaint.class, "model");
		Date date = getParaToDate("model.complaintDate");
		model.setComplaintDate(date);
		Ret ret = null;
		boolean re = model.update();
		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void save() {
		FyComplaint model = getBean(FyComplaint.class, "model");
		// Integer id = model.getParentId();
		// FyBusinessOutWarehouse out = FyBusinessOutWarehouse.dao.findById(id);

		Ret ret = null;

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return model.save();
			}
		});

		if (re) {
			ret = Ret.ok("msg", "生成成功");
		} else {
			ret = Ret.fail("msg", "生成失败");
		}
		renderJson(ret);
	}
}
