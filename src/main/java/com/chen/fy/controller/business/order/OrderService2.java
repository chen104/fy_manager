package com.chen.fy.controller.business.order;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.Account;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.OrderUploadLog;
import com.jfinal.club.common.kit.ContextKit;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.ICallback;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class OrderService2 {
	private static final Logger logger = LogManager.getLogger(OrderService2.class);

	public final static OrderService2 me = new OrderService2();
	public static HashMap<String, Integer> colhash;

	public OrderService2() {

	}

	/**
	 * 首页查找
	 * @param condition
	 * @param keyWord
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<FyBusinessOrder> find(String condition, String keyWord, Integer page, Integer pageSize,
			String order_date_start, String order_date_end) {
		Page<FyBusinessOrder> modelPage = null;
		StringBuilder conditionSb = new StringBuilder();
		String select = "select o.*,f.originalFileName filename,f.id fileId, \n " 
				+ "  fc.in_time fc_in_time ,\r\n"
				+ "  fc.check_result fc_check_result, fc.check_time fc_check_time,\r\n"
				+ " fc.check_result fc_check_result, \n"
				+ " out_view.v_out_time v_out_time,\r\n" 
				+ "out_view.v_out_quantity v_out_quantity,\r\n"
				+ "out_view.v_transport_no v_transport_no,\r\n"
				+ "out_view.v_transport_company v_transport_company, \n"
				+ " ph.purchase_cost,ph.purchase_account ,su.name supplier_name ,"
				+ " gp.gp_hang_quantity ,gp_hang_amount , gp_hang_date";
		String from = " from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  \n"
				+ " LEFT JOIN fy_check_collect fc on o.id=fc.order_id \n"
				+ " LEFT JOIN out_view on out_view.order_id = o.id  \n"
				+ " LEFT JOIN uploadgetpay gp on gp.delivery_no = o.delivery_no \n "
				+ "  LEFT JOIN fy_business_purchase ph on ph.order_id = o.id  \n"
				+ " LEFT JOIN  fy_base_supplier su on ph.supplier_id = su.id " + "\n";
		String where = " where 1=1 ";
		String desc = " order by o.id desc";

		if (StringUtils.isNotEmpty(order_date_start)) {
			conditionSb.append(String.format(" AND order_date > '%s'", order_date_start));
		}
		if (StringUtils.isNotEmpty(order_date_end)) {
			conditionSb.append(String.format(" AND order_date < '%s'", order_date_end));
		}

		if ("delay_warn".equals(condition)) {
			String sql = " AND   DATEDIFF(delivery_date , NOW()) < 3 and out_quantity = 0 ";
			conditionSb.append(sql);
		}
		if ("delay".equals(condition)) {
			String sql = " AND   DATEDIFF(delivery_date , NOW()) < 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		}
		if ("delivery_date".equals(condition)) {

			conditionSb.append(String.format(" AND  delivery_date = '%s'", keyWord));

		} else if (StringUtils.isNotEmpty(keyWord)) {

			conditionSb.append(String.format(" AND o.%s like  ", condition, keyWord));
			conditionSb.append("'%").append(keyWord).append("%'");
		}
		if (conditionSb.length() > 0) {
			modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select,
					from + where + conditionSb.toString() + desc);
			// modelPage = new Page<FyBusinessOrder>(list, 1, list.size(), 1, list.size());
		} else {
			modelPage = FyBusinessOrder.dao.paginate(page, pageSize, select, from + where + desc);

		}

		return modelPage;
	}

	public List<Record> findDownload(String start_date, String end_date, String condition, String keyword)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		String select = "select o.*,f.originalFileName filename,f.id fileId ";
		String from = "from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  ";
		String desc = "  order by o.id desc ";
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = DateUtils.parseDate(start_date, "yyyy-MM-dd");
			endDate = DateUtils.parseDate(end_date, "yyyy-MM-dd");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (startDate != null && endDate != null) {
			sb.append(" o.order_date > '").append(start_date).append("' and o.order_date < '").append(end_date)
					.append("' ");
		}
		StringBuilder conditionSb = new StringBuilder();
		if (StringUtils.isNotEmpty(keyword)) {

			if ("order_date".equals(condition)) {

				conditionSb.append(String.format("  order_date = '%s'", keyword));

			} else if ("delivery_date".equals(condition)) {

				conditionSb.append(String.format(" delivery_date = '%s'", keyword));

			} else if (StringUtils.isNotEmpty(condition)) {

				conditionSb.append(String.format("  %s like  ", condition, keyword));
				conditionSb.append("'%").append(keyword).append("%'");
			}
		} else {
			if ("delay_warn".equals(condition)) {
				conditionSb.append("    DATEDIFF(delivery_date , NOW()) < 3 and out_quantity = 0 ");

			} else if ("delay".equals(condition)) {
				conditionSb.append("    DATEDIFF(delivery_date , NOW()) < 0 and out_quantity = 0 ");

			}
		}
		StringBuilder where = new StringBuilder();

		if (sb.length() > 0 && conditionSb.length() > 0) {
			where.append("where ").append(sb.toString()).append(" and ").append(conditionSb.toString());
		} else if (sb.length() > 0 && conditionSb.length() == 0) {
			where.append(" where ").append(sb.toString());
		} else if (sb.length() == 0 && conditionSb.length() > 0) {
			where.append(" where ").append(conditionSb.toString());
		}

		List<Record> list = Db.find(select + from + where.toString() + desc);

		return list;
	}

	public File downloanOrder2(String[] ids, Account account) throws Exception {
		String select = "select o.*,f.originalFileName filename,f.id fileId, \n " + "  fc.in_time fc_in_time ,\r\n"
				+ "  fc.check_result fc_check_result, fc.check_time fc_check_time,\r\n"
				+ " fc.check_result fc_check_result, \n" + " out_view.v_out_time v_out_time,\r\n"
				+ "out_view.v_out_quantity v_out_quantity,\r\n" + "out_view.v_transport_no v_transport_no,\r\n"
				+ "out_view.v_transport_company v_transport_company, \n"
				+ " ph.purchase_cost,ph.purchase_account ,su.name supplier_name ,"
				+ " gp.gp_hang_quantity ,gp_hang_amount , gp_hang_date";
		String from = " from  fy_business_order o left join fy_base_fyfile  f on o.draw = f.id  \n"
				+ " LEFT JOIN fy_check_collect fc on o.id=fc.order_id \n"
				+ " LEFT JOIN out_view on out_view.order_id = o.id  \n"
				+ " LEFT JOIN uploadgetpay gp on gp.delivery_no = o.delivery_no \n "
				+ "  LEFT JOIN fy_business_purchase ph on ph.order_id = o.id  \n"
				+ " LEFT JOIN  fy_base_supplier su on ph.supplier_id = su.id " + "\n";
		String desc = "  order by o.id desc ";
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		List<Record> list = Db.find(select + from + " where o.id in " + sb.toString() + desc);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"订单" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/order/order2.xlsx").getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		int row = 1;
		for (Record item : list) {
			if (account.hasColPermission("order", "category_id")) {
				String cate_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 0, cate_tmp);
			}
			if (account.hasColPermission("order", "category_id")) {
				String plan_tmp = item.getStr("plan_tmp");// 计划员
				excel.setCellVal(row, 1, plan_tmp);
			}
			if (account.hasColPermission("order", "execu_status")) {
				String execu_status = item.getStr("execu_status");// 执行状态
				excel.setCellVal(row, 2, execu_status);
			}
			if (account.hasColPermission("order", "customer_no")) {
				String urgent_status = item.getStr("customer_no");// 客户编码
				excel.setCellVal(row, 3, urgent_status);
			}

			if (account.hasColPermission("order", "work_bill_no")) {
				String workOrder_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 4, workOrder_no);
			}

			if (account.hasColPermission("order", "delivery_no")) {
				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 5, delivery_no);
			}

			if (account.hasColPermission("order", "map_no")) {
				String filename = item.getStr("map_no");// 图号
				excel.setCellVal(row, 6, filename);
			}

			if (account.hasColPermission("order", "commodity_name")) {
				String commodity_name = item.getStr("commodity_name");// 名称
				excel.setCellVal(row, 7, commodity_name);
			}

			if (account.hasColPermission("order", "total_map_no")) {
				String map_no = item.getStr("total_map_no");// 总图号
				excel.setCellVal(row, 8, map_no);
			}

			if (account.hasColPermission("order", "quantity")) {
				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 9, quantity);
			}

			if (account.hasColPermission("order", "unit")) {

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 10, unit_tmp);
			}

			if (account.hasColPermission("order", "draw")) {

				String filename = item.getStr("filename");// 型号
				excel.setCellVal(row, 11, filename);
			}

			if (account.hasColPermission("order", "model_no")) {

				String model_no = item.getStr("model_no");// 型号
				excel.setCellVal(row, 12, model_no);
			}

			if (account.hasColPermission("order", "commodity_spec")) {

				String commodity_spec = item.getStr("commodity_spec");// 规格
				excel.setCellVal(row, 13, commodity_spec);
			}


			if (account.hasColPermission("order", "technology")) {

				String technology = item.getStr("technology");// 技术条件
				excel.setCellVal(row, 14, technology);
			}

			if (account.hasColPermission("order", "machining_require")) {

				String machining_require = item.getStr("machining_require");// 质量等级
				excel.setCellVal(row, 15, machining_require);
			}

			if (account.hasColPermission("order", "order_date")) {

				Date order_date = item.getDate("order_date");// 订单日期
				excel.setCellVal(row, 16, order_date);

				excel.setDateCellStyle(row, 16);
			}

			if (account.hasColPermission("order", "delivery_date")) {

				Date delivery_date = item.getDate("delivery_date");// 交货日期
				excel.setCellVal(row, 17, delivery_date);
				excel.setDateCellStyle(row, 17);
			}

			if (account.hasColPermission("order", "un_tax_cost")) {

				String untaxed_cost = item.getStr("untaxed_cost");// 未税单价
				excel.setCellVal(row, 18, untaxed_cost);
			}

			if (account.hasColPermission("order", "amount")) {

				Double amount = item.getDouble("amount");// 金额
				excel.setCellVal(row, 19, amount);
			}

			if (account.hasColPermission("order", "tax_rate")) {

				Double tax_rate = item.getDouble("tax_rate");// 税率
				excel.setCellVal(row, 20, tax_rate);
			}

			if (account.hasColPermission("order", "tax_amount")) {

				Double tax_amount = item.getDouble("tax_amount");// 税额
				excel.setCellVal(row, 21, tax_amount);
			}

			if (account.hasColPermission("order", "tatolAmount")) {

				Double tatol_amount = item.getDouble("tatol_amount");// 含税金额
				excel.setCellVal(row, 22, tatol_amount);
			}

			if (account.hasColPermission("order", "distribute_time")) {

				Date distribute_time = item.getDate("distribute_time");// 分配时间
				excel.setCellVal(row, 23, distribute_time);
				excel.setDateCellStyle(row, 23);

			}

			if (account.hasColPermission("order", "distribute_to")) {
				String distribute_to = item.getStr("distribute_to");// 分配流向
				excel.setCellVal(row, 24, distribute_to);

			}

			if (account.hasColPermission("order", "supplier")) {

				String supplier_name = item.getStr("supplier_name");// 制造商
				excel.setCellVal(row, 25, supplier_name);

			}

			if (account.hasColPermission("order", "purchase_cost")) {

				// Date receive_time = item.getDate("receive_time");// 接收时间
				// excel.setCellVal(row, 25, receive_time);
				//
				// excel.setDateCellStyle(row, 25);
				String purchase_cost = item.getStr("purchase_cost");// 采购单价
				excel.setCellVal(row, 26, purchase_cost);
			}

			if (account.hasColPermission("order", "purchase_account")) {

				// Date receive_time = item.getDate("receive_time");// 接收时间
				// excel.setCellVal(row, 25, receive_time);
				//
				// excel.setDateCellStyle(row, 25);
				String purchase_account = item.getStr("purchase_account");// 采购总价
				excel.setCellVal(row, 27, purchase_account);
			}

			if (account.hasColPermission("order", "receive_time")) {

				Date receive_time = item.getDate("receive_time");// 接收时间
				excel.setCellVal(row, 28, receive_time);
				excel.setDateCellStyle(row, 28);
			}


			if (account.hasColPermission("order", "begin_time")) {

				Date plan_time = item.getDate("plan_time");// 投产时间
				excel.setCellVal(row, 29, plan_time);
				excel.setDateCellStyle(row, 29);

			}

			if (account.hasColPermission("order", "in_time")) {

				Date in_warehouse_time = item.getDate("fc_in_time");// 入库时间
				excel.setCellVal(row, 30, in_warehouse_time);
				excel.setDateCellStyle(row, 30);
			}

			if (account.hasColPermission("order", "check_time")) {

				Date check_time = item.getDate("fc_check_time");// 检测时间
				excel.setCellVal(row, 31, check_time);
				excel.setDateCellStyle(row, 31);

			}

			if (account.hasColPermission("order", "out_time")) {

				Date out_house_date = item.getDate("v_out_time");// 出库时间
				excel.setCellVal(row, 32, out_house_date);
				excel.setDateCellStyle(row, 32);

			}

			if (account.hasColPermission("order", "out_status")) {
				 Integer quantity = item.getInt("quantity");
				 Integer v_out_quantity = item.getInt("v_out_quantity");
				 String out_house_status = null;
				if(v_out_quantity ==null || v_out_quantity==0) {
					out_house_status="未出库";
				}else if(quantity==null){
					out_house_status="订单异常";
				}else if(v_out_quantity <0 ||v_out_quantity >quantity) {
					out_house_status="出库异常";
				}else if(v_out_quantity < quantity) {
					out_house_status="部分出库";
				}else if(v_out_quantity == quantity) {
					out_house_status="已出库";
				}else {
					out_house_status="异常出库数";
				}
				
				excel.setCellVal(row, 33, out_house_status);

			}

			if (account.hasColPermission("order", "send_address")) {

				String send_address = item.getStr("send_address");// 发货地址
				excel.setCellVal(row, 34, send_address);

			}

			if (account.hasColPermission("order", "transport_company")) {

				String transport_company = item.getStr("v_transport_company");// 物流名称
				excel.setCellVal(row, 35, transport_company);

			}

			if (account.hasColPermission("order", "transport_no")) {

				String transport_no = item.getStr("v_transport_no");// 物流单号
				excel.setCellVal(row, 36, transport_no);
			}

			if (account.hasColPermission("order", "hang_date")) {

				Date out_house_date = item.getDate("gp_hang_date");// 挂账时间
				excel.setCellVal(row, 37, out_house_date);
				excel.setDateCellStyle(row, 37);

			}

			if (account.hasColPermission("order", "hang_status")) {
				Integer quantity = item.getInt("quantity");
				Integer gp_hang_quantity = item.getInt("gp_hang_quantity");
				String hang_status = null;
				if (gp_hang_quantity == null || gp_hang_quantity == 0) {
					hang_status = "未挂账";
				} else if (quantity == null) {
					hang_status = "订单异常";
				} else if (gp_hang_quantity < 0 || gp_hang_quantity > quantity) {
					hang_status = "挂账异常";
				} else if (gp_hang_quantity < quantity) {
					hang_status = "部分挂账";
				} else if (gp_hang_quantity == quantity) {
					hang_status = "已挂账";
				} else {
					hang_status = "异常挂账";
				}

				excel.setCellVal(row, 38, hang_status);

			}

			if (account.hasColPermission("order", "hang_quantity")) {

				String hang_quantity = item.getStr("hang_quantity");// 挂账数量
				excel.setCellVal(row, 39, hang_quantity);
			}

			if (account.hasColPermission("order", "hang_amount")) {

				String hang_quantity = item.getStr("hang_amount");// 挂账金额
				excel.setCellVal(row, 40, hang_quantity);
			}

			if (account.hasColPermission("order", "unhang_quantity")) {

				String unhang_quantity = item.getStr("unhang_quantity");// 未挂账
				excel.setCellVal(row, 41, unhang_quantity);
			}

			if (account.hasColPermission("order", "is_finsh_product")) {

				String is_finsh_product = item.getStr("is_finsh_product");// 是否成品
				if (StringUtils.isNotEmpty(is_finsh_product)) {
					is_finsh_product = is_finsh_product.trim();
					excel.setCellVal(row, 42, is_finsh_product);
				}
			}

			row++;
		}

		excel.save2File(targetfile);
		return targetfile;

	}

	/**
	 * 第二修改版本 上传
	 * @param file
	 * @throws Exception 
	 */
	public Integer uploadOrder(File file) throws Exception {

		PIOExcelUtil excel = new PIOExcelUtil(file, 0);
		// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
		// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
		List<Record> list = new ArrayList<Record>();
		int rows = excel.getRowNum() + 1;
		for (int i = 1; i < rows; i++) {

			FyBusinessOrder item = new FyBusinessOrder();
			String catgory = excel.getCellVal(i, 0);// 类别
			item.setCateTmp(catgory);
			String planname = excel.getCellVal(i, 1);// 计划员
			item.setPlanTmp(planname);
			if (StringUtils.isEmpty(planname)) {
				logger.debug(" 计划员 为空 " + i + " ");
				continue;
			}

			String execuStatus = excel.getCellVal(i, 2);// 紧急状态
			item.setExecuStatus(execuStatus);

			String customerNo = excel.getCellVal(i, 3);// 紧急状态
			item.setCustomerNo(customerNo);
			// item.setUrgentStatus(urgentStatus);

			String workid = excel.getCellVal(i, 4);// 工作订单号
			item.setWorkOrderNo(workid);
			// if (StringUtils.isEmpty(workid) && (!"备货".equals(excustatu))) {
			// System.out.println(item);
			// continue;
			// }
			// System.out.println(workid);
			String DeliveryId = excel.getCellVal(i, 5);// 送货单号
			if (StringUtils.isNotEmpty(DeliveryId)) {
				DeliveryId = DeliveryId.trim();
				DeliveryId = StringUtils.chomp(DeliveryId);
			}
			System.out.println(DeliveryId);
			item.setDeliveryNo(DeliveryId);

			String mapNo = excel.getCellVal(i, 6);// 图号 图纸
			item.setMapNo(mapNo);

			String name = excel.getCellVal(i, 7);// 商品名称
			item.setCommodityName(name);


			String totalMapNo = excel.getCellVal(i, 8);// 总图号
			// item.setMapNo(mapNo);
			item.setTotalMapNo(totalMapNo);

			String quantity = excel.getCellVal(i, 9);// 数量
			item.setQuantity(NumberUtils.isNumber(quantity) ? Integer.valueOf(quantity) : null);

			String unit = excel.getCellVal(i, 10);// 单位
			item.setUnitTmp(unit);

			String modelNo = excel.getCellVal(i, 11);// 型号
			item.setModelNo(modelNo);

			String nspec = excel.getCellVal(i, 12);// 商品规格
			item.setCommoditySpec(nspec);

			String tck = excel.getCellVal(i, 13);// 技术条件
			item.setTechnology(tck);

			String maching = excel.getCellVal(i, 14);// 质量等级
			item.setMachiningRequire(maching);

			Date orderdate = excel.getDateValue(i, 15);// 订单日期
			item.setOrderDate(orderdate);

			Date DeliveryDate = excel.getDateValue(i, 16);// 交货日期
			item.setDeliveryDate(DeliveryDate);

			String untaxcost = excel.getCellVal(i, 17);// 未税单价
			item.setUntaxedCost(NumberUtils.isNumber(untaxcost) ? new BigDecimal(untaxcost) : null);

			String account = excel.getCellVal(i, 18);// 金额
			item.setAmount(NumberUtils.isNumber(account) ? new BigDecimal(account) : null);

			String taxRate = excel.getCellVal(i, 19);// 税率
			item.setTaxRate(NumberUtils.isNumber(taxRate) ? new BigDecimal(taxRate) : ContextKit.getTaxRate());

			String taxAccount = excel.getCellVal(i, 20);// 税额
			boolean isnumber = NumberUtils.isNumber(taxAccount);

			item.setTaxAmount(isnumber ? new BigDecimal(taxAccount) : null);

			String totalAccount = excel.getCellVal(i, 21);// 含税金额

			item.setTatolAmount(NumberUtils.isNumber(totalAccount) ? new BigDecimal(totalAccount) : null);

			String sendAddress = excel.getCellVal(i, 22);// 发货地址
			item.setSendAddress(sendAddress);

			String is_finsh_product = excel.getCellVal(i, 23);// 是否成品
			item.setIsFinshProduct(is_finsh_product);

			item.setImportTime(new Date());
			item.setDistributeAttr("首次");
			item.setHandleStatus("未处理");
			item.setHangStatus("未挂账");
			item.setUnhangQuantity(item.getQuantity());

			item.setWwUnquantity(item.getQuantity());
			list.add(new Record().setColumns(item));

		}
		StringBuilder sb = new StringBuilder();
		Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int total = 0;
				try {
					int[] re = Db.batchSave("fy_business_order", list, 20);

					for (int i = 0; i < re.length; i++) {
						total = total + re[i];
					}
					OrderUploadLog log = new OrderUploadLog();
					log.setSucess(total);
					log.save();
					if (total == list.size()) {
						sb.append(total);
						return true;
					} else {
						return false;
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
					return false;
				}

			}
		});

		// Db.update("update fy_business_order set order_status = 40 where execu_status
		// ='备货' AND order_status = 0 ");
		return Integer.valueOf(sb.toString());

	}

	public Ret delteOrder(String[] ids) throws Exception {
		Ret ret = null;
		StringBuilder idsb = new StringBuilder();
		SqlKit.joinIds(ids, idsb);
		String sql = "insert INTO fy_business_order_delete SELECT * from  fy_business_order where id in   "
				+ idsb.toString();
		String delete = " delete from fy_business_order where id in " + idsb.toString();
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {

				Object o = Db.execute(new ICallback() {

					@Override
					public Object call(Connection conn) throws SQLException {
						int in = conn.createStatement().executeUpdate(sql);
						int dnum = conn.createStatement().executeUpdate(delete);
						return in == dnum;
					}
				});
				if (o instanceof Boolean) {
					return (Boolean) o;
				}

				return false;
			}
		});

		if (re) {
			ret = Ret.ok().set("msg", "删除完成");
		} else {
			ret = Ret.ok().set("msg", "删除失败");
		}
		return ret;
	}

	public Integer executWidth(Account account) {
		// if (colhash == null) {
		colhash = new HashMap<String, Integer>();
		// 序号 50
		colhash.put("category_id", 60);
		colhash.put("planer_id", 60);
		colhash.put("execu_status", 100);
		colhash.put("customer_no", 100);
		colhash.put("work_bill_no", 100);
		colhash.put("delivery_no", 120);

		colhash.put("map_no", 150);
		colhash.put("commodity_name", 130);
		colhash.put("total_map_no", 300);
		colhash.put("quantity", 100);
		colhash.put("unit", 35);
		colhash.put("draw", 300);
		colhash.put("model_no", 200);
		colhash.put("commodity_spec", 200);

		colhash.put("technology", 200);
		colhash.put("machining_require", 80);
		colhash.put("order_date", 100);
		colhash.put("delivery_date", 100);
		colhash.put("un_tax_cost", 100);

		colhash.put("amount", 100);
		colhash.put("taxrate", 100);
		colhash.put("tax", 100);
		colhash.put("tatol_amount", 100);
		colhash.put("un_tax_cost", 100);
		colhash.put("distribute_time", 100);

		colhash.put("distribute_to", 100);
		colhash.put("supplier", 300);
		colhash.put("purchase_cost", 100);
		colhash.put("purchase_amount", 100);
		colhash.put("receive_time", 100);
		colhash.put("begin_time", 100);

		colhash.put("in_time", 100);
		colhash.put("check_time", 100);
		colhash.put("out_time", 100);
		colhash.put("out_status", 100);
		colhash.put("send_address", 100);
		colhash.put("transport_company", 200);

		colhash.put("transport_no", 200);
		colhash.put("hang_date", 100);
		colhash.put("hang_status", 100);
		colhash.put("hang_quantity", 100);
		colhash.put("hang_amount", 100);
		colhash.put("unhang_quantity", 100);

		colhash.put("is_finsh_product", 80);
		colhash.put("order_update", 60);
		// }
		Set<String> keys = colhash.keySet();
		Iterator<String> it = keys.iterator();
		Integer width = new Integer("110");// 序号50
		while (it.hasNext()) {
			String key = it.next();
			/**
			 * 判断是否拥有权限
			 */
			if (account.hasColPermission("order", key)) {
				Integer w = colhash.get(key);
				width += w;
			}

		}
		return width;
	}

}
