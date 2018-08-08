package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.OrderUploadLog;
import com.jfinal.aop.Before;
import com.jfinal.club.common.kit.ContextKit;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;

public class OrderController extends BaseController {
	public void index() {
		String key = getPara("keyWord");

		Page<FyBusinessOrder> modelPage = null;
		String condition = getPara("condition");

		keepPara("condition", "keyWord", "order_date");

		setAttr("keyWord", key);
		StringBuilder conditionSb = new StringBuilder();
		if ("order_date".equals(condition)) {
			String orderDate = getPara("order_date");
			if (StringUtils.isNotEmpty(orderDate)) {
				conditionSb.append(String.format("where order_date = '%s'", orderDate));
			}

		} else if (StringUtils.isNotEmpty(condition)) {

			conditionSb.append(String.format("where %s like  ", condition, key));
			conditionSb.append("'%").append(key).append("%'");
		}

		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10,
					"select o.*,f.originalFileName filename ",
					"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  order by id desc");
		} else {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					String.format("from fy_business_order %s order by id desc", conditionSb.toString()));

			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("orderlist.html");
	}

	public void importFile() {
		boolean isnumber = false;
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();
				new FyBusinessOrder();
				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				List<Record> list = new ArrayList<Record>();
				for (int i = 1; i < excel.getRowNum(); i++) {
					FyBusinessOrder item = new FyBusinessOrder();
					String catgory = excel.getCellVal(i, 0);// 类别
					item.setCateTmp(catgory);
					String planname = excel.getCellVal(i, 1);// 计划员
					item.setPlanTmp(planname);

					String excustatu = excel.getCellVal(i, 2);// 执行状态
					item.setExecuStatus(excustatu);

					String urgentStatus = excel.getCellVal(i, 3);// 紧急状态
					item.setUrgentStatus(urgentStatus);

					Date orderdate = excel.getDateValue(i, 4);// 订单日期
					item.setOrderDate(orderdate);

					Date DeliveryDate = excel.getDateValue(i, 5);// 交货日期
					item.setDeliveryDate(DeliveryDate);

					String workid = excel.getCellVal(i, 6);// 工作订单号
					if (StringUtils.isEmpty(workid)) {
						System.out.println(item);
						continue;
					}
					item.setWorkOrderNo(workid);

					String DeliveryId = excel.getCellVal(i, 7);// 送货单号
					item.setDeliveryNo(DeliveryId);

					String name = excel.getCellVal(i, 8);// 商品名称
					item.setCommodityName(name);

					String nspec = excel.getCellVal(i, 9);// 商品规格
					item.setCommoditySpec(nspec);

					String map = excel.getCellVal(i, 10);// 总图号
					// item.setMapNo(mapNo);
					item.setMapTmp(map);

					String tck = excel.getCellVal(i, 11);// 技术条件
					item.setTechnology(tck);

					String maching = excel.getCellVal(i, 12);// 加工要求
					item.setMachiningRequire(maching);

					String quantity = excel.getCellVal(i, 13);// 数量
					item.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : null);

					String unit = excel.getCellVal(i, 14);// 单位
					item.setUnitTmp(unit);

					String untaxcost = excel.getCellVal(i, 15);// 未税单价
					item.setUntaxedCost(NumberUtils.isNumber(untaxcost) ? new BigDecimal(untaxcost) : null);

					String account = excel.getCellVal(i, 16);// 金额
					item.setAmount(NumberUtils.isNumber(account) ? new BigDecimal(account) : null);

					String taxRate = excel.getCellVal(i, 17);// 税率
					item.setTaxRate(NumberUtils.isNumber(taxRate) ? new BigDecimal(taxRate) : ContextKit.getTaxRate());

					String taxAccount = excel.getCellVal(i, 18);// 税额
					isnumber = NumberUtils.isNumber(taxAccount);

					item.setTaxAmount(isnumber ? new BigDecimal(taxAccount) : null);

					String totalAccount = excel.getCellVal(i, 19);// 含税金额

					item.setTatolAmount(NumberUtils.isNumber(totalAccount) ? new BigDecimal(totalAccount) : null);

					item.setImportTime(new Date());
					item.setDistributeAttr("首次");
					item.setHandleStatus("未处理");
					item.setHangStatus("未挂账");
					item.setUnhangQuantity(item.getQuantity());
					list.add(new Record().setColumns(item));

				}
				int[] re = Db.batchSave("fy_business_order", list, 20);

				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}
				OrderUploadLog log = new OrderUploadLog();
				log.setSucess(total);
				log.save();
				Db.update(
						" update fy_business_order set  warn_time = DATE_ADD(import_time,INTERVAL 2 DAY) where  warn_time is null");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

	public void updateOrder() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		String quantity = getPara("quantity");
		String unit = getPara("unit");
		order.setQuantity(new BigDecimal(quantity));
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

	public void deleteOrder() {
		Integer id = getParaToInt("id");

		boolean re = FyBusinessOrder.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void receive() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select o.*,f.originalFileName filename ",
				"from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  order by id desc,is_distribute desc");

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	public void distribute() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order   order by orderby asc  , id asc");

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
			businessDistribute.setMapNo(order.getMapNo());
			businessDistribute.setTechnology(order.getTechnology());
			businessDistribute.setMachiningRequire(order.getMachiningRequire());
			businessDistribute.setQuantity(order.getQuantity());
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

	public void distrubite() {
		Integer id = getParaToInt("id");
		String disTo = getPara("disTo");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		order.setDistributeTo(disTo);
		order.setIsDistribute(true);
		order.setDistributeTime(new Date());
		if ("自产".equals(disTo)) {
			order.setDisTo(false);
		} else {
			order.setDisTo(true);
		}

		order.setOrderby(1);
		// order.setHandleStatus("处理中");
		boolean save = false;
		save = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return order.update();
			}
		});
		Ret ret = null;
		if (save) {
			ret = Ret.ok("msg", "分配成功");

		} else {
			ret = Ret.ok("msg", "分配失败");
		}
		renderJson(ret);
	}

	public void distrubiteBatch() {
		String[] ids = getParaValues("ids[]");
		String disTo = getPara("disTo");
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		// order.setDistributeTo(disTo);
		// order.setIsDistribute(true);
		// order.setDistributeTime(new Date());
		StringBuilder sql = new StringBuilder();
		if ("自产".equals(disTo)) {
			// order.setDisTo(false);
			sql.append("update Fy_Business_Order set dis_to = 0 ,distribute_to = '" + disTo
					+ "' ,is_distribute = 1, distribute_time = now() , orderby = 1  where id in ");
			com.jfinal.club.common.kit.SqlKit.joinIds(ids, sql);
		} else {
			// order.setDisTo(true);
			sql.append("update Fy_Business_Order set dis_to = 1 ,distribute_to = '" + disTo
					+ "' ,is_distribute = 1, distribute_time = now()  , orderby = 1 where id in ");
			com.jfinal.club.common.kit.SqlKit.joinIds(ids, sql);
		}

		// order.setOrderby(1);
		// order.setHandleStatus("处理中");
		boolean save = false;
		save = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				int update = Db.update(sql.toString());
				return update == ids.length;
			}
		});
		Ret ret = null;
		if (save) {
			ret = Ret.ok("msg", "分配成功");

		} else {
			ret = Ret.ok("msg", "分配失败");
		}
		renderJson(ret);
	}
}
