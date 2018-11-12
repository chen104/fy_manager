package com.chen.fy.controller.business.after;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Customer;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.chen.fy.model.FyComplaint;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class ComplaintController extends BaseController {

	private static final Logger logger = LogManager.getLogger(ComplaintController.class);
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
				"select c.* ",
				"from  fy_complaint  c  order by id desc");

		setAttr("modelPage", modelPage);
		render("complaint.html");
	}

	public void add() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse out = FyBusinessOutWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(out.getOrderId());
		String sql = String.format(" select * from fy_base_customer where customer_no = '%s' ", order.getCustomerNo());
		Customer customer = Customer.dao.findFirst(sql);


		// String sql ="SELECT c.`name`
		// customer_name,o.map_no,ou.out_quantity,commodity_name from
		// fy_business_out_warehouse ou \r\n" +
		// " INNER JOIN fy_business_order o on ou.order_id = o.id\r\n" +
		// " LEFT JOIN fy_base_customer c on o.customer_no = c.customer_no\r\n" +
		// " where ou.id = " + id;
		// logger.debug("出库新建投诉单 ：==> " + sql);
		// Record model = Db.findFirst(sql);
		// if (model != null) {
		// model.set("complaint_date", new Date());
		// }
		FyComplaint model = new FyComplaint();
		model.setParentId(out.getId());
		model.setQuantity(out.getOutQuantity());
		if (order != null) {
			model.setOrderId(order.getId());
			model.setMapNo(order.getMapNo());
			model.setName(order.getCommodityName());
		}
		if (customer != null) {
			model.setCustomerName(customer.getName());
		}
		model.setComplaintDate(new Date());
		setAttr("model", model);

		setAttr("action", "save");
		render("edit.html");
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyComplaint model = FyComplaint.dao.findById(id);
		// Date date = getParaToDate("model.complaintDate");
		// model.setComplaintDate(date);

		// FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		//
		// setAttr("order", order);
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
		Date date = getParaToDate("model.complaintDate");
		model.setComplaintDate(date);
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
