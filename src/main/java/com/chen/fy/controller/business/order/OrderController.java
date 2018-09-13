package com.chen.fy.controller.business.order;

import java.io.File;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.controller.business.service.OrderService;
import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;

public class OrderController extends BaseController {
	private static final Logger logger = LogManager.getLogger(OrderController.class);
	OrderService orderService = OrderService.me;

	/**
	 * 订单表
	 */
	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}

		Page<FyBusinessOrder> modelPage = null;
		String condition = getPara("condition");

		keepPara("condition", "keyWord", "order_date");
		Integer pageSize = getParaToInt("pageSize", 10);
		if (pageSize != 10) {
			CacheKit.put("pageSize", getLoginAccount().getId(), pageSize);
		} else {
			Object obj = CacheKit.get("pageSize", getLoginAccount().getId());
			if (obj != null && NumberUtils.isNumber(obj.toString())) {
				pageSize = Integer.valueOf(obj.toString());
			}
		}
		setAttr("pageSize", pageSize);

		StringBuilder conditionSb = new StringBuilder();
		String select = "select o.*,f.originalFileName filename,f.id fileId ,u.hang_quantity uhang_quantity ,u.hang_amount uhang_amount , o.quantity - IFNULL(u.hang_quantity,0) unhquantity";
		if ("dis_warn".equals(condition)) {// 分配预警，三天未分配,导入时间，后还没有分配
			String sql = " where dis_to is null and DATEDIFF(now() , import_time ) > 3";
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id "
							+ "LEFT JOIN upGetpay u on o.delivery_no = u.delivery_no " + sql + " order by id desc");
			setAttr("modelPage", modelPage);
			render("orderlist.html");
			return;
		}

		if ("delay_warn".equals(condition)) {
			String sql = " where  DATEDIFF(now() , order_date) > 28 and out_quantity = 1 ";
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  "
							+ "LEFT JOIN upGetpay u on o.delivery_no = u.delivery_no " + sql + " order by id desc");
			setAttr("modelPage", modelPage);
			render("orderlist.html");
			return;
		}

		if ("delay".equals(condition)) {
			String sql = " where  DATEDIFF(now() , order_date) > 30 and out_quantity = 1 ";
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id "
							+ " LEFT JOIN upGetpay u on o.delivery_no = u.delivery_no " + sql + " order by id desc");
			setAttr("modelPage", modelPage);
			render("orderlist.html");
			return;
		}

		if ("order_date".equals(condition)) {

			conditionSb.append(String.format("where order_date = '%s'", key));

		} else if ("delivery_date".equals(condition)) {

			conditionSb.append(String.format("where delivery_date = '%s'", key));

		} else if (StringUtils.isNotEmpty(condition)) {

			conditionSb.append(String.format("where %s like  ", condition, key));
			conditionSb.append("'%").append(key).append("%'");
		}

		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id \r\n"
							+ " LEFT JOIN upGetpay u on o.delivery_no = u.delivery_no  order by id desc");
		} else {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					String.format(" from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id \r\n "
							+ " LEFT JOIN upGetpay u on o.delivery_no = u.delivery_no " + " %s order by id desc",
							conditionSb.toString()));

			setAttr("append", "&keyWord=" + key);
		}
		Double totalAmount = 0d;// 含税金额
		Double amount = 0d;
		for (FyBusinessOrder e : modelPage.getList()) {
			if (e.getTatolAmount() != null) {
				totalAmount += e.getTatolAmount().doubleValue();

			}
			if (e.getAmount() != null) {
				amount += e.getAmount().doubleValue();
			}

		}
		if (totalAmount > 0) {
			NumberFormat.getInstance().format(totalAmount);
			setAttr("totalAmount", totalAmount);
		}
		setAttr("amount", amount);

		setAttr("modelPage", modelPage);
		render("orderlist.html");
	}

	/**
	 * 导入订单
	 */
	public void importFile() {

		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				total = orderService.upload(ufile.getFile());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

	/**
	 * 更新订单
	 */
	public void updateOrder() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		String quantity = getPara("quantity");
		String unit = getPara("unit");
		// order.setQuantity(new BigDecimal(quantity));
		order.setUnitTmp(unit);
		boolean re = order.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改数量成功成功");
		} else {
			ret = Ret.ok("msg", "修改失败");
		}
		renderJson(ret);

	}

	/**
	 * 删除订单
	 */
	public void deleteOrder() {
		Integer id = getParaToInt("id");
		// boolean re =

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				try {
					Db.find("select count(1) from  fy_business_purchase where order_id = ?", id);
					Db.find("select count(1) from  fy_business_in_warehouse where order_id = ?", id);

					Db.find("select count(1) from  fy_business_out_warehouse where order_id = ?", id);
					Db.delete("delete from fy_business_order where id = ?", id);
					Db.delete("delete from fy_business_pay where id = ?", id);
					FyBusinessOrder.dao.deleteById(id);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	/**
	 * 接收表
	 */
	public void receive() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select o.*,f.originalFileName filename ",
				"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  where distribute_to is null order by o.id desc,is_distribute desc");

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	public void distribute() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}

		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		keepPara("condition");
		Integer pageSize = getParaToInt("pageSize", 10);
		setAttr("pageSize", pageSize);
		setAttr("append", "&pageSize=" + pageSize);
		String select = "select o.*,originalFileName filename,file.id fileId";
		String from = "from  fy_business_order o   LEFT JOIN fy_base_fyfile file on o.draw = file.id";
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isEmpty(key)) {
			sb.append(" where  distribute_to is null order by o.id desc,is_distribute desc  ");
		} else {
			String condition = getPara("condition");
			if ("delivery_date".equals(condition) || "order_date".equals(condition)) {
				sb.append(" where ").append(condition).append(" = '").append(key).append("' ")
						.append(" and  distribute_to is null order by o.id desc,is_distribute desc ");
			} else {
				sb.append(" where ").append(condition).append(" like '%").append(key).append("%' ")
						.append(" and  distribute_to is null order by o.id desc,is_distribute desc ");
			}
		}
		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), getParaToInt("pageSize", 10), select,
				from + sb.toString());
		setAttr("modelPage", modelPage);
		render("distribute.html");
	}

	public void save() {
		FyBusinessOrder customer = getBean(FyBusinessOrder.class, "model");
		boolean re = customer.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  类别成功信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyBusinessOrder.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		FyBusinessOrder model = FyBusinessOrder.dao.findById(getParaToInt("id"));
		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		// Category person = getBean(Category.class, "model");
		// boolean re = person.update();
		// Ret ret = null;
		// if (re) {
		// ret = Ret.ok("msg", "修改 " + person.getName() + " 信息成功");
		// } else {
		// ret = Ret.ok("msg", "修改 失败");
		// }
		//
		// renderJson(ret);
	}

	public void add() {
		setAttr("action", "save");
		render("edit.html");
	}

	/**
	 * 关联图纸
	 */
	public void updateFile() {
		int orderid = getParaToInt("orderId");
		int fileId = getParaToInt("fileId");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(orderid);
		Ret ret = null;
		if (order != null) {
			order.setDraw(fileId);
			boolean re = order.update();

			if (re) {
				ret = Ret.ok("msg", "添加图纸 信息成功");
			} else {
				ret = Ret.ok("msg", "添加图纸失败");
			}
		} else {
			ret = Ret.ok("msg", "没找到订单");
		}

		renderJson(ret);
	}

	public void toRecieve() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		Ret ret = null;
		if (order != null) {
			order.setReceiveTime(new Date());
			boolean re = order.update();
			if (re) {
				ret = Ret.ok("msg", "接收订单成功").set("date", order.getReceiveTime());
			} else {
				ret = Ret.ok("msg", "接收失败");
			}
		} else {
			ret = Ret.ok("msg", "接收失败");
		}

		renderJson(ret);
	}

	/***
	 * 把接收表转成分配表
	 */
	@Before(Tx.class)
	@Deprecated
	public void toDistribute() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		FyBusinessDistribute businessDistribute = new FyBusinessDistribute();

		Ret ret = null;
		if (order != null) {
			order.setIsDistribute(true);
			boolean re = order.update();
			businessDistribute.setCategoryId(order.getCategoryId());
			businessDistribute.setCategoryTmp(order.getCateTmp());
			businessDistribute.setPlanerId(order.getPlanerId());
			businessDistribute.setPlanTmp(order.getPlanTmp());
			businessDistribute.setExecuStatus(order.getExecuStatus());
			businessDistribute.setUrgentStatus(order.getUrgentStatus());
			businessDistribute.setOrderDate(order.getOrderDate());

			businessDistribute.setDeliveryDate(order.getDeliveryDate());
			businessDistribute.setWorkOrderNo(order.getWorkOrderNo());
			businessDistribute.setDeliveryNo(order.getDeliveryNo());
			businessDistribute.setCommodityName(order.getCommodityName());
			businessDistribute.setCommoditySpec(order.getCommoditySpec());
			// businessDistribute.setMapNo(order.getMapNo());
			businessDistribute.setTechnology(order.getTechnology());
			businessDistribute.setMachiningRequire(order.getMachiningRequire());
			// businessDistribute.setQuantity(order.getQuantity());
			businessDistribute.setUnit(order.getUnit());
			businessDistribute.setUnitTmp(order.getUnitTmp());
			// businessDistribute.setReceiveTime(order.getReceiveTime());
			businessDistribute.setDistributeAttr("首次");
			businessDistribute.setOrderId(order.getId());
			boolean save = businessDistribute.save();
			if (re && save) {
				ret = Ret.ok("msg", "生成分配表成功");

			} else {
				ret = Ret.ok("msg", "生成失败");
			}
		} else {
			ret = Ret.ok("msg", "生成失败");
		}

		renderJson(ret);
	}

	/**
	 * 更新交货时间
	 */
	public void updateOrderDedeliveryDate() {
		Ret ret = null;
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);

		Date delivery_date = getParaToDate("delivery_date");
		if (delivery_date == null) {
			ret = Ret.ok("msg", "交货时间格式错误");

			logger.warn("修改订单交货时间格式错误");

			renderJson(ret);
			return;
		}
		order.setDeliveryDate(delivery_date);

		boolean re = order.update();

		if (re) {
			ret = Ret.ok("msg", "修改 成功");
		} else {
			ret = Ret.ok("msg", "修改失败");
		}
		renderJson(ret);

	}

	public void toDownload() {
		String date = getPara("date");
		if (StringUtils.isEmpty(date)) {
			setAttr("date", DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM"));
		}
		render("download.html");
	}

	/**
	 * 查找符合条件的出库单
	 */
	public void findDownloadRecord() {
		String out_date = getPara("date");
		keepPara("date");
		if (!StringUtils.isEmpty(out_date)) {
			Date dDate = null;
			try {
				dDate = DateUtils.parseDate(out_date, "yyyy-MM");
			} catch (Exception e) {
				logger.error(e.getMessage());
				setAttr("msg", "时间格式错误");
				render("download.html");
				return;
			}

			List<Record> modelList = orderService.findDownloadList(dDate);

			setAttr("modelList", modelList);
			render("download.html");
			return;
		} else {
			setAttr("msg", "订单月份格式错误");
			toDownload();
			return;
		}
	}

	public void downloadList() {
		String date = getPara("date");
		String[] ids = getParaValues("selectId");
		if (!StringUtils.isEmpty(date)) {

			try {
				DateUtils.parseDate(date, "yyyy-MM");
			} catch (Exception e) {
				logger.error(e.getMessage());
				setAttr("msg", "时间格式错误");
				render("download.html");
				return;
			}
		}
		if (ids.length == 0) {
			setAttr("msg", "没有选择订单");
			render("download.html");
			return;
		}

		File file = orderService.downloadFileRecord(date, ids);
		if (file == null) {
			setAttr("msg", "请查看日志");
			render("download.html");
			return;
		}

		renderFile(file);
	}

}
