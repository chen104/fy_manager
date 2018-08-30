package com.chen.fy.controller.business;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBizWwReceive;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class CommissionController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 and is_finish_purchase = 0    order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and ").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ", String.format(
					"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 and is_finish_purchase = 0  %s order by id desc",
					sb.toString()));
		}

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	/**
	 * 
	 */
	public void sumReceive() {
		String key = getPara("keyWord");
		Page<FyBizWwReceive> modelPage = null;
		setAttr("keyWord", key);
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order o " + "LEFT JOIN payView p on o.id = p.order_id"
							+ "  where Is_Distribute = 1 and dis_to = 1 order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and o.").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");

			modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order o " + "LEFT JOIN payView p on o.id = p.order_id"
							+ "  where Is_Distribute = 1 and dis_to = 1 " + sb.toString() + " order by id desc");
		}
		setAttr("modelPage", modelPage);
		render("sumReceive.html");
	}

	/**
	 * 委外一览表
	 */
	public void oneSumCommission() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		keepPara("keyWord", "condition");
		String purchase = "purchase_no,purchase_date,purchase_cost,purchase_account,discount,discount_account";
		String supplier_name = ", bs.name supplier_name";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
					"select o.* ," + purchase + supplier_name,
					"from   fy_business_order   o  inner  join fy_business_purchase  p  on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id "
							+ " " + " left join fy_base_supplier  bs on bs.id = p.supplier_id "
							+ " where dis_to = 1 order by id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and o.").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
					"select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
					"from   fy_business_order   o  inner  join fy_business_purchase  p  on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id "
							+ " left join  fy_business_in_warehouse w on o.id = w.order_id"
							+ " left join fy_base_supplier  bs on bs.id = p.supplier_id " + " where dis_to = 1 "
							+ sb.toString() + " order by id desc");

			// modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
			// "select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
			// String.format(
			// "from fy_business_purchase p left join fy_business_order o on p.order_id
			// =o.id left join fy_base_supplier s on p.supplier_id = s.id "
			// + " left join fy_business_in_warehouse w on o.id = w.order_id"
			// + " left join fy_base_supplier bs on bs.id = p.supplier_id "
			// + " where dis_to = 1 %s order by id desc",
			// sb.toString()));
		}

		setAttr("modelPage", modelPage);
		render("oneSumCommission.html");
	}

	/**
	 * 委外入库
	 */
	public void toInWareHouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = new FyBusinessInWarehouse();// 入库单
		model.setOrderId(id);
		model.setParentId(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);// 获取订单
		List<Record> list = Db.find(
				"select purchase_account , supplier_id  from fy_business_purchase where (supplier_id is null or purchase_account is null) AND order_id = ?",
				id);

		if (list.size() > 0) {
			renderJson(Ret.fail().set("msg", "采购单信息不完善，没有设置采购金额或没有选择厂商"));
			return;
		}
		Integer num = Db.queryInt("select COUNT(1) num  from fy_business_purchase where order_id=? GROUP BY order_id ",
				id);
		if (num == null || num == 0) {
			renderJson(Ret.fail().set("msg", "没有采购单"));
			return;
		}
		order.setInWarehouseTime(new Date());// 入库时间
		model.setInTime(order.getInWarehouseTime());// 入库时间
		model.setInFrom("委外");
		order.setIsCreateInHouse(true);// 已生成入库状态位

		String inQuantity = getPara("inQuantity");

		if (!NumberUtils.isNumber(inQuantity)) {
			renderJson(Ret.fail().set("msg", "入库不是数量失败"));
			return;
		}
		Double inquantiy = order.getHasInQuantity().doubleValue();
		double newinquantiy = inquantiy + Double.valueOf(inQuantity);
		if (order.getQuantity().doubleValue() < newinquantiy) {
			renderJson(Ret.fail().set("msg", "入库数量大于订单数量"));
			return;
		}

		model.setRealInQuantity(new BigDecimal(inQuantity));// 入库单入库数量
		Ret ret = null;

		// 把入库数反写到订单
		BigDecimal HasInQuantity = order.getHasInQuantity();
		BigDecimal RealInQuantity = model.getRealInQuantity();
		order.setHasInQuantity(HasInQuantity.add(RealInQuantity));// 更改入库数量，不修改库存，没有检测成功的 加入库存

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				boolean r = model.save() && order.update();
				try {
					String sql = Db.getSql("order.updateWWhang");
					Db.update(sql, id);
				} catch (Exception e) {
					return false;
				}
				return r;

			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "生成入库单成功");
			// 更新未挂账金额

		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void toDownloadOneSum() {
		render("downloadOneSum.html");

	}

	/**
	 * 委外一览表，有厂商
	 * @throws ParseException
	 */
	public void downloadOneSum() throws ParseException {
		String date = getPara("date");
		if (StringUtils.isEmpty(date)) {
			renderText("没有选择日期");
		}
		Integer id = getParaToInt("supplier_id");
		Date start = DateUtils.parseDate(date, "yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, 1);
		String ptime = " and order_date >'" + DateFormatUtils.format(start, "yyyy-MM-00") + "' and order_date <'"
				+ DateFormatUtils.format(calendar, "yyyy-MM-00") + "'";

		if (id != null) {
			ptime = ptime + " and p.supplier_id = " + id + " ";
		}
		String sql = "select o.* ,purchase_no,purchase_date,purchase_cost,purchase_account,discount,discount_account, s.name supplier_name  "
				+ "	from   fy_business_order   o  " + " inner  join fy_business_purchase  p  on p.order_id =o.id  "
				+ "left join fy_base_supplier s on p.supplier_id = s.id   " + " where dis_to = 1  " + ptime
				+ " order by id desc";
		List<FyBusinessOrder> list = FyBusinessOrder.dao.find(sql);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "委外一览表" + date + ".xlsx");
		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/wwOneSum.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);
			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 1;

			for (FyBusinessOrder item : list) {

				String cate_tmp = item.getStr("cate_tmp");// 客户
				// cate_tmp = item.getStr("customer");// 客户
				// cate_tmp = CacheKit.get(CacheNameConstant.base_customer_id2key, cate_tmp);

				// String plan_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_name = item.getStr("plan_tmp");// 计划员 plan_nam
				excel.setCellVal(row, 1, plan_name);

				String execu_status = item.getStr("execu_status");// 执行状态
				excel.setCellVal(row, 2, execu_status);

				String urgent_status = item.getStr("urgent_status");// 紧急状态
				excel.setCellVal(row, 3, urgent_status);

				Date order_date = item.getDate("order_date");// 订单日期
				excel.setCellVal(row, 4, order_date);
				excel.setDateCellStyle(row, 4);

				Date delivery_date = item.getDate("delivery_date");// 交货日期
				excel.setCellVal(row, 5, delivery_date);
				excel.setDateCellStyle(row, 5);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 6, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 7, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 8, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 9, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 10, map_no);

				String technology = item.getStr("technology");// 技术条件
				excel.setCellVal(row, 11, technology);

				String machining_require = item.getStr("machining_require");// 加工要求
				excel.setCellVal(row, 12, machining_require);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 13, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 14, unit_tmp);

				String supplier_name = item.getStr("supplier_name");// 厂商
				excel.setCellVal(row, 15, supplier_name);

				String purhcase_no = item.getStr("purhcase_no");// 采购订单号
				excel.setCellVal(row, 16, purhcase_no);

				Double purhcase_quantity = item.getDouble("quantity");// 采购数量
				excel.setCellVal(row, 17, purhcase_quantity);

				Double purchase_cost = item.getDouble("purchase_cost");// 单价
				excel.setCellVal(row, 18, purchase_cost);

				Double purchase_account = item.getDouble("purchase_account");// 金额
				excel.setCellVal(row, 19, purchase_account);

				Double discount = item.getDouble("discount");// 折扣
				excel.setCellVal(row, 20, discount);

				Double discount_account = item.getDouble("discount_account");// 折扣
				excel.setCellVal(row, 21, discount_account);

				// Date plan_finsh_time = item.getDate("plan_finsh_time");//入库时间
				// excel.setCellVal(row, 2, plan_finsh_time);

				row++;
			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderText(e.getMessage());
		}

		if (targetfile.exists()) {
			renderFile(targetfile);
		}
	}

	/*
	 * 
	 */
	public void toDownloadWWsum() {
		setAttr("new", new Date());
		render("downloadWwOrder.html");

	}

	/**
	 * 委外汇总表，没有厂商
	 * @throws ParseException
	 */
	public void downloadWWsum() throws ParseException {
		String date = getPara("date");
		if (StringUtils.isEmpty(date)) {
			renderText("没有选择日期");
		}
		Integer id = getParaToInt("supplier_id");
		Date start = DateUtils.parseDate(date, "yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, 1);
		String ptime = " and order_date >'" + DateFormatUtils.format(start, "yyyy-MM-00") + "' and order_date <'"
				+ DateFormatUtils.format(calendar, "yyyy-MM-00") + "'";

		if (id != null) {
			ptime = ptime + " and p.supplier_id = " + id + " ";
		}
		String sql = "select *  from  fy_business_order o  " + "  where Is_Distribute = 1 and dis_to = 1 " + ptime
				+ " order by id desc";
		List<FyBusinessOrder> list = FyBusinessOrder.dao.find(sql);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "委外一览表" + date + ".xlsx");
		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/wwsum.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);
			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 1;

			for (FyBusinessOrder item : list) {

				String cate_tmp = item.getStr("cate_tmp");// 客户
				// cate_tmp = item.getStr("customer");// 客户
				// cate_tmp = CacheKit.get(CacheNameConstant.base_customer_id2key, cate_tmp);

				// String plan_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_name = item.getStr("plan_tmp");// 计划员 plan_nam
				excel.setCellVal(row, 1, plan_name);

				String execu_status = item.getStr("execu_status");// 执行状态
				excel.setCellVal(row, 2, execu_status);

				String urgent_status = item.getStr("urgent_status");// 紧急状态
				excel.setCellVal(row, 3, urgent_status);

				Date order_date = item.getDate("order_date");// 订单日期
				excel.setCellVal(row, 4, order_date);
				excel.setDateCellStyle(row, 4);

				Date delivery_date = item.getDate("delivery_date");// 交货日期
				excel.setCellVal(row, 5, delivery_date);
				excel.setDateCellStyle(row, 5);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 6, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 7, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 8, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 9, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 10, map_no);

				String technology = item.getStr("technology");// 技术条件
				excel.setCellVal(row, 11, technology);

				String machining_require = item.getStr("machining_require");// 加工要求
				excel.setCellVal(row, 12, machining_require);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 13, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 14, unit_tmp);

				Date receive_time = item.getDate("receive_time");// 接收时间
				excel.setCellVal(row, 15, receive_time);
				excel.setDateCellStyle(row, 15);

				Date distribute_time = item.getDate("distribute_time");// 分配时间
				excel.setCellVal(row, 16, distribute_time);
				excel.setDateCellStyle(row, 16);

				Double has_in_quantity = item.getDouble("has_in_quantity");// 入库数量
				excel.setCellVal(row, 17, has_in_quantity);

				Double ww_quantity = item.getDouble("ww_quantity");// 委外挂账数量
				excel.setCellVal(row, 18, ww_quantity);

				Double ww_unquantity = item.getDouble("ww_unquantity");// 未挂账数量
				excel.setCellVal(row, 19, ww_unquantity);

				Double ww_hang_amount = item.getDouble("ww_hang_amount");// 挂账金额
				excel.setCellVal(row, 20, ww_hang_amount);

				Double ww_unhang_amount = item.getDouble("ww_unhang_amount");// 未挂账金额
				excel.setCellVal(row, 21, ww_unhang_amount);

				// Date plan_finsh_time = item.getDate("plan_finsh_time");//入库时间
				// excel.setCellVal(row, 2, plan_finsh_time);

				row++;
			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderText(e.getMessage());
		}

		if (targetfile.exists()) {
			renderFile(targetfile);
		}
	}

}
