package com.chen.fy.controller.business.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.OrderUploadLog;
import com.jfinal.club.common.kit.ContextKit;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class OrderService {
	private static final Logger logger = LogManager.getLogger(OrderService.class);
	public static final OrderService me = new OrderService();

	/**
	 * 查找下载
	 * @param dateMonth
	 * @return
	 */
	public List<Record> findDownloadList(Date dateMonth) {
		try {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateMonth);
			calendar.add(Calendar.DATE, -1);
			String start = DateFormatUtils.format(calendar, "yyyy-MM-dd");
			calendar.add(Calendar.DATE, 1);
			calendar.add(Calendar.MONTH, 1);
			String end = DateFormatUtils.format(calendar, "yyyy-MM-dd");
			String where = " where  o.order_date >'" + start + "' and o.order_date <'" + end + "' ";
			String sql = "select id,order_date,o.work_order_no,commodity_name,commodity_spec,o.quantity,tax_amount,tatol_amount, hang_status,ugp.hang_amount , ugp.hang_quantity from fy_business_order o "
					+ "LEFT JOIN upGetpay ugp " + " on o.delivery_no = ugp.delivery_no " + where;
			List<Record> list = Db.find(sql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());

		}
		return null;
	}

	public File downloadFileRecord(String date, String[] ids) {
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String sql = "select id,order_date,o.work_order_no,commodity_name,commodity_spec,o.quantity,tax_amount,tatol_amount, hang_status,ugp.hang_amount , ugp.hang_quantity from fy_business_order o INNER JOIN upGetpay ugp "
				+ " on o.delivery_no = ugp.delivery_no  where o.id in " + sb.toString();
		List<Record> list = Db.find(sql);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "订单对账_" + date + ".xlsx");

		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/order_download.xlsx")
					.getFile();
			FileUtils.copyFile(new File(filename), targetfile);

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 1;
			for (Record item : list) {

				Date order_date = item.getDate("order_date");// 类别

				excel.setCellVal(row, 0, order_date);
				excel.setDateCellStyle(row, 0);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 1, work_order_no);

				String commodity_name = item.getStr("commodity_name");// 商品名
				excel.setCellVal(row, 2, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 3, commodity_spec);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 4, quantity);

				Double tax_amount = item.getDouble("tax_amount");// 未税单价
				excel.setCellVal(row, 5, tax_amount);

				Double tatolAmount = item.getDouble("tatol_amount");// 金额
				excel.setCellVal(row, 6, tatolAmount);

				String hang_status = item.getStr("hang_status");// 挂账状态
				excel.setCellVal(row, 7, hang_status);

				Double hang_quantity = item.getDouble("hang_quantity");// 挂账数量
				excel.setCellVal(row, 8, hang_quantity);

				Double hang_amount = item.getDouble("hang_amount");// 挂账总金额
				excel.setCellVal(row, 9, hang_amount);

				row++;
			}

			excel.save2File(targetfile);
			return targetfile;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 上传订单
	 * @param file
	 * @throws Exception 
	 */
	public Integer upload(File file) throws Exception {

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
			// item.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) :
			// null);

			String unit = excel.getCellVal(i, 14);// 单位
			item.setUnitTmp(unit);

			String untaxcost = excel.getCellVal(i, 15);// 未税单价
			item.setUntaxedCost(NumberUtils.isNumber(untaxcost) ? new BigDecimal(untaxcost) : null);

			String account = excel.getCellVal(i, 16);// 金额
			item.setAmount(NumberUtils.isNumber(account) ? new BigDecimal(account) : null);

			String taxRate = excel.getCellVal(i, 17);// 税率
			item.setTaxRate(NumberUtils.isNumber(taxRate) ? new BigDecimal(taxRate) : ContextKit.getTaxRate());

			String taxAccount = excel.getCellVal(i, 18);// 税额
			boolean isnumber = NumberUtils.isNumber(taxAccount);

			item.setTaxAmount(isnumber ? new BigDecimal(taxAccount) : null);

			String totalAccount = excel.getCellVal(i, 19);// 含税金额

			item.setTatolAmount(NumberUtils.isNumber(totalAccount) ? new BigDecimal(totalAccount) : null);

			String sendAddress = excel.getCellVal(i, 20);// 发货地址
			item.setSendAddress(sendAddress);

			item.setImportTime(new Date());
			item.setDistributeAttr("首次");
			item.setHandleStatus("未处理");
			item.setHangStatus("未挂账");
			// item.setUnhangQuantity(item.getQuantity());

			// item.setWwUnquantity(item.getQuantity());
			list.add(new Record().setColumns(item));

		}
		int[] re = Db.batchSave("fy_business_order", list, 20);
		int total = 0;
		for (int i = 0; i < re.length; i++) {
			total = total + re[i];
		}
		OrderUploadLog log = new OrderUploadLog();
		log.setSucess(total);
		log.save();
		// Db.update(
		// " update fy_business_order set warn_time = DATE_ADD(import_time,INTERVAL 2
		// DAY) where warn_time is null");
		return total;
	}
}
