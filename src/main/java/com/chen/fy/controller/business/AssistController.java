package com.chen.fy.controller.business;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class AssistController extends BaseController {
	public void toAddassist() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		setAttr("order", order);
		setAttr("model", new FyBusinessAssist());

		setAttr("action", "saveAssist");
		setAttr("title", "新建外协加工单");
		render("edit.html");
	}

	public void toEditassist() {
		Integer id = getParaToInt("id");

		FyBusinessAssist model = FyBusinessAssist.dao.findFirst(
				"select a.*,s.name supplier_name from fy_business_assist a left join  fy_base_supplier s on a.assist_supplier_id = s.id  where a.id = ?",
				id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("order", order);
		setAttr("model", model);
		setAttr("title", "修改外协加工单");
		setAttr("action", "updateAssist");
		render("edit.html");
	}

	public void saveAssist() {
		FyBusinessAssist model = getBean(FyBusinessAssist.class, "model");
		// 更细委外未挂账金额
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		if (model.getAssistAccount() != null) {
			order.setWwUnhangAmount(order.getWwUnhangAmount().add(model.getAssistAccount()));
		}
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				boolean save = model.save();
				boolean update = order.update();
				return save && update;

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

	/**
	 * 更新
	 */
	public void updateAssist() {
		FyBusinessAssist model = getBean(FyBusinessAssist.class, "model");

		FyBusinessAssist old = FyBusinessAssist.dao.findById(model.getId());

		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		if (old.getAssistAccount() != null) {

			BigDecimal wUnhangAmount = order.getWwUnhangAmount().subtract(old.getAssistAccount());
			order.setWwUnhangAmount(wUnhangAmount);
		}

		if (model.getAssistAccount() != null) {
			order.getWwUnhangAmount().add(model.getAssistAccount());
		}
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				order.update();
				boolean update = model.update();
				return update;

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

	public void index() {
		assist();
	}

	public void delete() {

		Integer id = getParaToInt("id");
		FyBusinessAssist model = FyBusinessAssist.dao.findById(id);

		/**
		 * 更新挂账
		 */
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		if (model.getAssistAccount() == null) {

			BigDecimal wUnhangAmount = order.getWwUnhangAmount().subtract(model.getAssistAccount());
			order.setWwUnhangAmount(wUnhangAmount);
		}

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.update() && order.update();

			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "删除成功");
		} else {
			ret = Ret.fail().set("msg", "删除失败");
		}
		renderJson(ret);
	}

}
