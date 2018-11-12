package com.chen.fy.controller.business.assist;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.club.common.kit.AssistNoKit;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.template.Engine;

public class AssistController extends BaseController {
	AssistService service = AssistService.me;
	private static final Logger logger = LogManager.getLogger(AssistService.class);
	String oTable = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,\n "
			+ "commodity_spec,map_no,quantity,unit_tmp,technology,\n "
			+ "machining_require,untaxed_cost,order_date,delivery_date, \n" + "execu_status,customer_no"
			+ ",s.name supplier_name ,p.create_time pay_create_time";
	public void toAddassist() {
		Integer id = getParaToInt("selectId");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		setAttr("order", order);
		FyBusinessAssist model = new FyBusinessAssist();
		// TaxRate taxrate = TaxRate.dao.findFirst("SELECT * FROM fy_base_tax_rate WHERE
		// match_value = 0");
		model.setTaxRate(new BigDecimal("0"));
		model.setTaxAmount(new BigDecimal("0"));
		setAttr("model", model);

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
		model.setAssistNo(AssistNoKit.getNo());
		model.setCreateTime(new Date());
		// 更细委外未挂账金额
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				boolean save = model.save();
				// boolean update = order.update();
				return save;

			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "生成外协加工单成功");
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

			// BigDecimal wUnhangAmount =
			// order.getWwUnhangAmount().subtract(old.getAssistAccount());
			// order.setWwUnhangAmount(wUnhangAmount);
		}

		if (model.getAssistAccount() != null) {
			// order.getWwUnhangAmount().add(model.getAssistAccount());
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

	Engine engine;

	public AssistController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}


	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		Page<FyBusinessAssist> modelPage = null;
		Ret ret = Ret.ok("msg", "加载数据");
		String condition = getPara("condition");
		try {

			String select = "select a.* ,s.name supplier," + oTable + " , f.originalFileName filename,f.id fileId";
			String from = " from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id "
					+ " left join fy_base_supplier s on s.id = a.assist_supplier_id "
					+ " left join fy_base_fyfile  f on o.draw = f.id"
					+ " LEFT JOIN fy_business_pay p ON p.is_purchase =0 AND p.parent_id = a.id ";
		if (StringUtils.isEmpty(key)) {
				modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1) + 1, getPageSize(), select,
					from + " order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();

			if ("name".equals(condition)) {
				sb.append(" and s.name like ").append("'%").append(key).append("%' ");
			} else {
				sb.append(" and o.work_order_no like ").append("'%").append(key).append("%' ");
			}
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), getPageSize(), select,
					from + sb.toString() + " order by id desc");
		}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error(e.getMessage());
			ret = Ret.fail("msg", "查看日志");
		}
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		if (modelPage == null) {
			modelPage = new Page<>(new ArrayList<FyBusinessAssist>(), 1, 10, 0, 0);
		}
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		String str = engine.getTemplate("stringTemplet/product/assist/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
	}

	public void assist() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		Page<FyBusinessAssist> modelPage = null;
		keepPara("condition", "keyWord", "order_date");
		Integer pageSize = getParaToInt("pageSize", 10);
		setAttr("pageSize", pageSize);
		setAttr("append", "&pageSize=" + pageSize);
		setAttr("keyWord", key);
		String condition = getPara("condition");

		// String oTable =
		// "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,customer_no,customer_no"
		// + ",s.name supplier_name ,p.create_time pay_create_time";
		String select = "select a.* ,s.name supplier," + oTable + " , f.originalFileName filename,f.id fileId,"
				+ " p.hang_date ,p.pay_date,pay_quantity,p.should_pay ";
		String from = " from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id \n"
				+ " left join fy_base_supplier s on s.id = a.assist_supplier_id \n "
				+ " left join fy_base_fyfile  f on o.draw = f.id \n "
				+ " LEFT JOIN fy_business_pay p ON p.is_purchase =0 AND p.parent_id = a.id \n ";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), pageSize, select,
					from + " order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();

			if ("name".equals(condition)) {
				sb.append(" and s.name like ").append("'%").append(key).append("%' ");
			} else {
				sb.append(" and o.work_order_no like ").append("'%").append(key).append("%' ");
			}
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), pageSize, select,
					from + sb.toString() + " order by id desc");
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

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.delete();

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

	public void createPay() {
		String[] ids = getParaValues("selectId");
		Ret ret;
		try {
			ret = service.createPay(ids);
			renderJson(ret);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(Ret.ok().set("msg", "请查看运行日志"));
	}

	public void downloadFile() {
		String[] ids = getParaValues("downloadId");
		try {
			File file = service.download(ids);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderText("下载发生异常，查看日志");
	}

}