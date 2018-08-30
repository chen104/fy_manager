package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

public class OutWarehouseContollor extends BaseController {
	private static final Logger logger = LogManager.getLogger(OutWarehouseContollor.class);

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id   order by w.id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("where  o.").append(getPara("condition")).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id "
							+ sb.toString() + "  order by w.id desc");
		}
		setAttr("modelPage", modelPage);
		render("list.html");
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		setAttr("order", order);
		setAttr("model", model);
		setAttr("action", "/fy/admin/biz/outWhouse/update");
		render("edit.html");
	}

	public void update() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "out");
		FyBusinessOutWarehouse old = FyBusinessOutWarehouse.dao.findById(model.getId());
		if (model.getOutQuantity() == null) {
			Ret ret = Ret.fail("msg", "出库数量不能为空");
			renderJson(ret);
			return;
		}

		System.out.println(model);
		Integer id = model.getOrderId();
		FyBusinessOrder parent = FyBusinessOrder.dao.findById(id);
		if (parent.getStorageQuantity().doubleValue() + old.getOutQuantity().doubleValue() < model.getOutQuantity()
				.doubleValue()) {
			Ret ret = Ret.fail("msg", "出库数量不能超过库存");
			renderJson(ret);
			return;
		}

		// BigDecimal outquantity = model.getOutQuantity();
		// BigDecimal starage = parent.getStorageQuantity().subtract(outquantity);
		// parent.setStorageQuantity(starage);// 库存
		//
		// BigDecimal hasout = parent.getOutQuantity().add(outquantity);
		// parent.setOutQuantity(hasout);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				// boolean b1 = parent.update();
				boolean b2 = model.update();
				return b2;
			}
		});
		String sql = "update fy_business_order o INNER JOIN\r\n "
				+ "(select sum(out_quantity) out_quantity ,order_id from  fy_business_out_warehouse where order_id = ?  GROUP BY order_id ) w\r\n"
				+ " on o.id= w.order_id\r\n "
				+ "set o.out_quantity = w.out_quantity  ,storage_quantity = has_in_quantity - w.out_quantity ";
		Db.update(sql, model.getOrderId());// 更新库存与出库
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "出库成功");
		} else {
			ret = Ret.fail("msg", "出库失败");
		}
		renderJson(ret);
	}

	/**
	 * 跳转出库页面
	 */
	public void add() {
		Integer id = getParaToInt("id");
		// FyBusinessInWarehouse model = FyBusinessInWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		// setAttr("model", model);
		Map<String, Object> Waybill = CacheKit.get("outWarehouse", "Waybill");
		if (Waybill == null) {
			Record record = Db.findFirst(
					"select receive_address,transport_type,transport_company,transport_fee,transport_no,transport_weight "
							+ " from fy_business_out_warehouse order by id desc  limit 1");
			if (record != null) {
				Waybill = record.getColumns();
			}
		}
		setAttr("order", order);
		setAttr("action", "addOut");
		setAttr("waybill", Waybill);
		if (Waybill == null) {
			setAttr("model", Waybill);
		} else {
			setAttr("model", new FyBusinessOutWarehouse());
		}
		setAttr("action", "/fy/admin/biz/outWhouse/save");
		render("edit.html");
	}

	/**
	 * 添加出库
	 */
	public void save() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "out");
		if (model.getOutQuantity() == null) {
			Ret ret = Ret.fail("msg", "出库数量不能为空");
			renderJson(ret);
			return;
		}
		// System.out.println(model);
		Integer id = model.getOrderId();
		FyBusinessOrder parent = FyBusinessOrder.dao.findById(id);
		if (parent.getStorageQuantity().doubleValue() > model.getOutQuantity().doubleValue()) {
			Ret ret = Ret.fail("msg", "出库数量不能超过库存");
			renderJson(ret);
			return;
		}

		// BigDecimal outquantity = model.getOutQuantity();
		// BigDecimal starage = parent.getStorageQuantity().subtract(outquantity);
		// parent.setStorageQuantity(starage);// 库存
		//
		// BigDecimal hasout = parent.getOutQuantity().add(outquantity);
		// parent.setOutQuantity(hasout);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				// boolean b1 = parent.update();
				boolean b2 = model.save();
				return (b2);
			}
		});
		Ret ret = null;
		String sql = "update fy_business_order o INNER JOIN\r\n "
				+ "(select sum(out_quantity) out_quantity ,order_id from  fy_business_out_warehouse where order_id = ?  GROUP BY order_id ) w\r\n"
				+ " on o.id= w.order_id\r\n "
				+ "set o.out_quantity = w.out_quantity  ,storage_quantity = has_in_quantity - w.out_quantity ";
		Db.update(sql, model.getOrderId());// 更新库存与出库
		if (re) {
			ret = Ret.ok("msg", "出库成功");
		} else {
			ret = Ret.fail("msg", "出库失败");
		}
		renderJson(ret);
	}

	/**
	 * 出库下载
	 */
	public void downloadOutWhouse() {
		String date = getPara("date");
		if (StringUtils.isEmpty(date)) {
			setAttr("dateMsg", "没有选择出库月份");
			render("download.html");
			return;
		}
		// String supplier = getPara("supplier_id");
		// String sname = getPara("supplier_name");

		keepPara("date", "supplier_name", "supplier_id");

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "出库单_" + date + ".xlsx");

		try {
			Date start = DateUtils.parseDate(date, "yyyy-MM");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			// System.out.println(DateFormatUtils.format(calendar, "yyyy-MM-dd"));
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			String startStr = DateFormatUtils.format(calendar, "yyyy-MM-dd");

			calendar.add(Calendar.DAY_OF_YEAR, 1);
			calendar.add(Calendar.MONTH, 1);
			String endStr = DateFormatUtils.format(calendar, "yyyy-MM-dd");

			StringBuilder sb = new StringBuilder();
			sb.append(" where out_time >'").append(startStr).append("' and  out_time < '").append(endStr).append("' ");

			// if (!StringUtils.isEmpty(supplier)) {
			// sb.append(" and supplier_id=").append(supplier).append(" ");
			// }
			String select = "select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id";
			String sql = select + sb.toString();
			List<Record> list = Db.find(sql);
			if (list.size() == 0) {
				setAttr("msg", "没有符合条件的出库单");
				render("download.html");
				return;
			}
			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/outWhouse.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			// 读取模板
			// InputStream input =
			// this.getClass().getClassLoader().getResourceAsStream("templet/outWhouse.xlsx");

			int row = 2;
			for (Record item : list) {

				String cate_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_tmp = item.getStr("plan_tmp");// 计划员
				excel.setCellVal(row, 1, plan_tmp);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 2, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 3, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 4, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 5, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 6, map_no);

				Double quantity = item.getDouble("out_quantity");// 数量
				excel.setCellVal(row, 7, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 8, unit_tmp);

				String transport_company = item.getStr("transport_company");// 运输公司
				excel.setCellVal(row, 9, transport_company);

				String transport_type = item.getStr("transport_type");// 运输方式
				excel.setCellVal(row, 10, transport_type);

				String transport_no = item.getStr("transport_no");// 运输单号
				excel.setCellVal(row, 11, transport_no);

				String receive_address = item.getStr("receive_address");// 收货地址
				excel.setCellVal(row, 12, receive_address);

				row++;
			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// File file = new File(targetfile);
		// System.out.println("is file " + file.isFile() + " " + file.getAbsolutePath()
		// + " " + file.getName());
		renderFile(targetfile);

	}

	public void tobatchOut() {

		String out_id[] = getParaValues("out_id");
		if (out_id == null || out_id.length == 0) {
			index();
			return;
		}
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(out_id, sb);
		String sql = "select * from fy_business_order where id in  " + sb.toString();
		List<FyBusinessOrder> modellist = FyBusinessOrder.dao.find(sql);
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		// setAttr("model", model);
		Map<String, Object> Waybill = CacheKit.get("outWarehouse", "Waybill");
		if (Waybill == null) {
			Record record = Db.findFirst(
					"select receive_address,transport_type,transport_company,transport_fee,transport_no,transport_weight "
							+ " from fy_business_out_warehouse order by id desc  limit 1");
			if (record != null) {
				Waybill = record.getColumns();
			}
		}
		setAttr("modelList", modellist);
		setAttr("action", "addOut");
		setAttr("waybill", Waybill);
		render("batchout.html");
	}

	public void avalible(StringBuilder sb) {
		String sql = "select * from fy_business_order where id in  " + sb.toString();
		List<FyBusinessOrder> modellist = FyBusinessOrder.dao.find(sql);
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		// setAttr("model", model);
		Map<String, Object> Waybill = CacheKit.get("outWarehouse", "Waybill");
		if (Waybill == null) {
			Record record = Db.findFirst(
					"select receive_address,transport_type,transport_company,transport_fee,transport_no,transport_weight "
							+ " from fy_business_out_warehouse order by id desc  limit 1");
			if (record != null) {
				Waybill = record.getColumns();
			}
		}
		setAttr("modelList", modellist);
		setAttr("action", "addOut");
		setAttr("waybill", Waybill);
		render("batchout.html");
	}

	/**
	 * 批量生成
	 */
	public void batchSave() {
		try {
			String out_id[] = getParaValues("order_ids");
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(out_id, sb);
			FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "out");
			if (StringUtils.isEmpty(model.getReceiveAddress())) {
				avalible(sb);
				setAttr("receive_address_msg", "收货地址不能为空");
				return;
			}
			if (StringUtils.isEmpty(model.getTransportType())) {
				setAttr("transport_type_msg", "运输方式不能为空");
				avalible(sb);
				return;
			}
			if (StringUtils.isEmpty(model.getTransportCompany())) {
				setAttr("transport_company_msg", "运输公司不能为空");
				avalible(sb);
				return;
			}
			if (StringUtils.isEmpty(model.getTransportNo())) {
				setAttr("transport_no_msg", "运输单号不能为空");

				avalible(sb);
				return;
			}
			if (model.getOutTime() == null) {
				setAttr("out_time_msg", "出库单号不能为空");
				avalible(sb);
				return;
			}

			List<Record> list = new ArrayList<Record>();

			for (int i = 0; i < out_id.length; i++) {
				Record record = model.toRecord();
				record.set("order_id", out_id[i]);
				record.set("parent_id", out_id[i]);
				FyBusinessOrder order = FyBusinessOrder.dao.findById(out_id[i]);
				record.set("out_quantity", order.getStorageQuantity());
				list.add(record);
			}
			int[] re = Db.batchSave("fy_business_out_warehouse", list, list.size());
			int total = 0;
			for (int i = 0; i < re.length; i++) {
				total += re[i];
			}

			// 把订单库存清零
			String update = "update fy_business_order o INNER JOIN \r\n"
					+ "(select sum(out_quantity)  out_quantity ,order_id from fy_business_out_warehouse where order_id in  "
					+ sb.toString() + ")  w\r\n" + "on o.id = w.order_id \r\n"
					+ "set o.out_quantity = w.out_quantity ;";
			Db.update(update);// 更新出库
			Db.update("update fy_business_order  set storage_quantity = storage_quantity - out_quantity "
					+ "where id in  " + sb.toString());
			renderJson(Ret.ok().set("msg", "生成" + total + "条出库单"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			renderJson(Ret.ok().set("msg", "生成 出库单 完成"));
		}

	}

	/*
	 * 出库撤回
	 */
	public void rollbackOutWarehouse() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);

		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		if (order == null) {
			Db.delete("delete from fy_business_getpay where parent_id =?", id);
			model.delete();
			// Db.update(""); 更新库存

			Ret ret = Ret.ok("msg", "撤回成功");
			renderJson(ret);
			return;
		}
		System.out.println(model);

		// 获取出库
		BigDecimal outquantity = model.getOutQuantity();
		// 把出库添加到 库存
		BigDecimal StorageQuantity = order.getStorageQuantity().add(model.getOutQuantity());

		order.setStorageQuantity(StorageQuantity);
		BigDecimal hasout = order.getOutQuantity().subtract(outquantity);
		order.setOutQuantity(hasout);
		model.setIsCreateGetPay(false);

		// 应收信息
		// BigDecimal zelo = new BigDecimal("0");
		// model.setUntaxGetpay(zelo);
		//
		// model.setTax(zelo);
		// model.setHangAmount(zelo);// 挂账金额，应付金额
		// model.setCreateMonth(0);
		// model.setGetpayMonth(0);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				// 删除应收单
				Db.delete("delete from fy_business_getpay where parent_id =?", id);
				boolean b1 = order.update();
				boolean b2 = model.delete();
				return (b1 & b2);
			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "撤回成功");
		} else {
			ret = Ret.fail("msg", "撤回失败");
		}
		renderJson(ret);

	}

	public void toDownload() {
		render("download.html");
	}

}
