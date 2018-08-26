package com.chen.fy.controller.business;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PIOExcelUtil.EXCELVERSION;
import com.jfinal.club.common.kit.PurchaseNoKit;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.club.common.kit.ZipCompressor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PurchaseController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		keepPara("keyWord", "condition", "p");
		String condition = getPara("condition");
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
				+ ",s.name supplier_name";
		if (StringUtils.isEmpty(key)) {

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select p.*, " + sql,
					"from  fy_business_purchase p left join  fy_business_order o on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id  order by id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			if ("supplier_name".equals(condition)) {
				sb.append("  s.name like '%").append(key).append("%' ");
			} else {
				sb.append("  o.").append(condition).append(" like '%").append(key).append("%' ");
			}

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select p.*, " + sql,
					"from  fy_business_purchase p left join  fy_business_order o on p.order_id =o.id inner join fy_base_supplier s on p.supplier_id = s.id  where "
							+ sb.toString() + " order by p.id desc");
		}
		setAttr("modelPage", modelPage);
		render("purchase.html");
	}

	public void createBatchPurchase() {
		Integer[] orderIds = getParaValuesToInt("orderIds");
		StringBuilder sb = new StringBuilder();

		SqlKit.joinIds(orderIds, sb);

		List<FyBusinessPurchase> modelList = new ArrayList<FyBusinessPurchase>();
		Integer supplier = getParaToInt("supplierId");
		Date purchase = getParaToDate("purchaseDate");
		if (supplier == null) {
			renderJson(Ret.fail().set("msg", "没有选择供应商"));
			return;
		}

		if (purchase == null) {
			renderJson(Ret.fail().set("msg", "没有选择采购时间"));
			return;
		}
		for (Integer S : orderIds) {
			FyBusinessPurchase model = new FyBusinessPurchase();
			model.setOrderId(S);
			model.setParentId(S);
			model.setSupplierId(supplier);
			model.setPurchaseDate(purchase);
			model.setPurchaseNo(PurchaseNoKit.getNo());
			modelList.add(model);

		}
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				int[] re = Db.batchSave(modelList, 10);
				int row = 0;
				for (int i = 0; i < re.length; i++) {
					row = row + re[i];
				}
				String updatesql = " update fy_business_order p  set is_finish_purchase = 1  "
						+ "where is_finish_purchase is null " + "and  EXISTS ("
						+ "	select 1 from fy_business_purchase  fp where p.id = fp.order_id " + ")";
				Db.update(updatesql);// 已生成采购单
				String updateorder = "update fy_business_order o LEFT JOIN   "
						+ "(SELECT sum(purchase_account) purchase_account,order_id from fy_business_purchase where order_id in "
						+ sb.toString() + ") purchase  " + "on o.id = purchase.order_id   "
						+ "set o.ww_unhang_amount = purchase_account  " + "where o.id  in " + sb.toString();
				Db.update(updateorder);
				// 更新接收时间
				Db.update("update  fy_business_order set receive_time =NOW() where receive_time is null and id in "
						+ sb.toString());
				return row == modelList.size();
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建 成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);

	}

	public void toCreatePurchase() {
		String ids[] = getParaValues("selectId");
		// StringBuilder spBuilder = new StringBuilder("[");
		List<String> list = new ArrayList<String>();

		for (String s : ids) {
			// spBuilder.append(s).append(",");
			list.add(s);
		}
		// spBuilder.deleteCharAt(spBuilder.length()-1);
		// spBuilder.setCharAt(spBuilder.length() - 1, ']');
		setAttr("ids", list);
		// setAttr("idToString", spBuilder.toString());
		render("purchaseBatch.html");
	}

	public void toAddPurchase() {
		Integer id = getParaToInt("id");
		FyBusinessOrder model = FyBusinessOrder.dao.findById(id);
		setAttr("model", model);
		setAttr("now", new Date());
		render("addPurchase.html");
	}

	public void savePurchase() {

		FyBusinessPurchase model = getBean(FyBusinessPurchase.class, "model");
		model.setPurchaseNo(PurchaseNoKit.getNo());
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		// 未挂账金额
		order.setWwUnhangAmount(order.getWwUnhangAmount().add(model.getPurchaseAccount()));
		if (order.getReceiveTime() == null) {
			order.setReceiveTime(new Date());
		}
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return model.save() && order.update();
			}
		});
		// System.out.println(model);

		// Integer[] suppliers = getParaValuesToInt("supplier[]");
		// Date purchaseDate = getParaToDate("purchaseDate");
		// String purchaseSingleWeight = getPara("purchaseSingleWeight");
		// String purchaseWeight = getPara("purchaseWeight");
		// String purchaseCost = getPara("purchaseCost");
		// String purchaseAccount = getPara("purchaseAccount");

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建 成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);

	}

	public void editPurchase() {
		Integer id = getParaToInt("id");
		keepPara("p");
		FyBusinessPurchase purchase = FyBusinessPurchase.dao.findById(id);
		Supplier supplier = Supplier.dao.findById(purchase.getSupplierId());
		FyBusinessOrder order = FyBusinessOrder.dao.findById(purchase.getOrderId());
		setAttr("order", order);
		setAttr("supplier", supplier);
		setAttr("model", purchase);
		render("edit.html");
	}

	public void updatePurchase() {
		FyBusinessPurchase model = getBean(FyBusinessPurchase.class, "model");

		FyBusinessPurchase old = FyBusinessPurchase.dao.findById(model.getId());

		/**
		 * 更新未挂账金额
		 */
		FyBusinessOrder order = FyBusinessOrder.dao.findById(old.getOrderId());
		if (order.getWwUnhangAmount() == null) {
			order.setWwUnhangAmount(new BigDecimal(0));
		}
		if (old.getPurchaseAccount() == null && model.getPurchaseAccount() != null) {

			order.setWwUnhangAmount(order.getWwUnhangAmount().add(model.getPurchaseAccount()));
		} else if (old.getPurchaseAccount() != null && model.getPurchaseAccount() == null) {
			order.setWwUnhangAmount(order.getWwUnhangAmount().subtract(model.getPurchaseAccount()));
		} else if (old.getPurchaseAccount() != null && model.getPurchaseAccount() != null) {
			if (model.getPurchaseAccount().doubleValue() != old.getPurchaseAccount().doubleValue()) {

				order.getWwUnhangAmount().subtract(old.getPurchaseAccount());
				order.setWwUnhangAmount(model.getPurchaseAccount().add(order.getWwUnhangAmount()));
			}

		}

		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "更新成功");
		} else {
			ret = Ret.ok("msg", "更新失败");
		}
		renderJson(ret);
	}

	/**
	 * 下载采购单
	 */
	public void downloadPurchase() {
		String[] ids = getParaValues("selectPurchase");
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String filePath = null;
		try {
			// 读取模板
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("templet/purchase.xlsx");

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(input, 0);
			/*
			 * 查看是否有多个供应商
			 */
			Supplier supplier = Supplier.dao.findFirst(
					" select s.* from fy_business_purchase  p inner join fy_base_supplier s  on p.supplier_id = s.id where p.id in    "
							+ sb.toString());
			if (supplier == null) {
				renderText("没有找到供应商");

			}

			/*
			 * 开始写供应商信息
			 * 
			 */
			// System.out.println(excel.getCellVal(1, 15));
			// System.out.println(excel.getCellVal(1, 16));
			// System.out.println(excel.getCellVal(1, 17));
			excel.setCellVal(1, 16, "客户编码  : " + supplier.getId());
			excel.setCellVal(2, 16, "订单编号 : " + PurchaseNoKit.getNo());
			excel.setCellVal(3, 16, "订单日期 : " + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
			excel.setCellVal(4, 16, "厂     商 : " + supplier.getName());
			excel.setCellVal(5, 16, "电话/传真 : " + supplier.getPhone());
			excel.setCellVal(6, 16, "地址  :" + supplier.getAddress());
			excel.setCellVal(7, 16, "联系人 :" + supplier.getContactPerson() + " 联系方式  " + supplier.getContactType());

			String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
					+ "";
			List<Record> list = Db.find("select " + sql
					+ " from  fy_business_purchase p left join  fy_business_order o on p.order_id = o.id where p.id in "
					+ sb.toString());
			int row = 11;
			for (Record item : list) {
				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 0, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 6, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 10, map_no);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 13, quantity);

				String cate_tmp = item.getStr("unit_tmp"); // 单位
				excel.setCellVal(row, 15, cate_tmp);

				Double purchase_single_weight = item.getDouble("purchase_single_weight"); // 单件
				excel.setCellVal(row, 17, purchase_single_weight);

				Double purchase_weight = item.getDouble("purchase_weight"); // 总重
				excel.setCellVal(row, 19, purchase_weight);

				Double purchase_cost = item.getDouble("purchase_cost"); // 单价
				excel.setCellVal(row, 20, purchase_cost);

				Double purchase_account = item.getDouble("purchase_account"); // 总金额
				excel.setCellVal(row, 21, purchase_account);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 22, work_order_no);

				String purchase_remark = item.getStr("purchase_remark");// 备注
				excel.setCellVal(row, 23, purchase_remark);
				row++;

			}

			String name = System.currentTimeMillis() + "";
			filePath = PathKit.getWebRootPath() + File.separator + "download/excel/" + name;

			excel.save2File(filePath);

			// renderJson(list);
			if (EXCELVERSION.EXCEL_VERSION_2003 == excel.getCurrentVersion()) {
				filePath += ".xls";
			} else {
				filePath += ".xlsx";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(filePath);
		// System.out.println("is file " + file.isFile() + " " + file.getAbsolutePath()
		// + " " + file.getName());

		renderFile(file);
		// 更新已经下载的采购单
		Db.update("update  fy_business_purchase set can_download = 0  where id in " + sb.toString());
	}

	public void delete() {
		Integer id = getParaToInt("id");
		// 更新采购委外的 ，未挂账总额

		boolean re = FyBusinessPurchase.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void toDownload() {

		render("download.html");
	}

	public void downloadZip() throws Exception {
		Integer supplierId = getParaToInt("supplier_id");
		String date = getPara("date");
		String supplier_name = getPara("supplier_name");
		Supplier supplier = Supplier.dao.findById(supplierId);
		if (supplier == null) {
			setAttr("supplierMsg", "没有选择供应商");
			render("download.html");
			return;
		}
		if (StringUtils.isEmpty(date)) {
			setAttr("dateMsg", "没有选择采购月份");
			render("download.html");
			return;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateUtils.parseDate(date, "yyyy-MM"));
		calendar.add(Calendar.DATE, -1);
		String start = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
		calendar.add(Calendar.DATE, 1);
		calendar.add(Calendar.MONTH, 1);
		String end = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");

		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
				+ "";
		List<Record> list = Db.find("select p.*," + sql
				+ " from  fy_business_purchase p left join  fy_business_order o on p.order_id = o.id where can_download = 1 and  p.supplier_id = "
				+ supplierId + " and purchase_date > '" + start + "' and purchase_date < '" + end + "'");
		if (list.size() == 0) {
			keepPara("date", "supplier_name", "supplierId");
			setAttr("downloadMsg", "没有符合条件的采购单");
			render("download.html");
			return;
		}

		List<List<Record>> alllist = split(list);
		List<String> filename = new ArrayList<String>();
		int index = 0;
		String name = System.currentTimeMillis() + "";
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel/" + "采购单" + name);
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		int findex = 0;
		for (List<Record> itemlist : alllist) {

			// 读取模板
			// InputStream input =
			// this.getClass().getClassLoader().getResourceAsStream("templet/purchase.xlsx");
			File targetfile = new File(parentfile, supplier.getName() + (findex++) + ".xlsx");
			FileUtils.copyFile(
					new File(this.getClass().getClassLoader().getResource("templet/purchase.xlsx").getFile()),
					targetfile);
			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			excel.setCellVal(1, 16, "客户编码  : " + supplier.getId());
			excel.setCellVal(2, 16, "订单编号 : " + PurchaseNoKit.getNo());
			excel.setCellVal(3, 16, "订单日期 : " + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
			excel.setCellVal(4, 16, "厂     商 : " + supplier.getName());
			excel.setCellVal(5, 16, "电话/传真 : " + supplier.getPhone());
			excel.setCellVal(6, 16, "地址  :" + supplier.getAddress());
			excel.setCellVal(7, 16, "联系人 :" + supplier.getContactPerson() + " 联系方式  " + supplier.getContactType());

			int row = 11;
			for (Record item : itemlist) {
				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 0, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 6, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 10, map_no);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 13, quantity);

				String cate_tmp = item.getStr("unit_tmp"); // 单位
				excel.setCellVal(row, 15, cate_tmp);

				Double purchase_single_weight = item.getDouble("purchase_single_weight"); // 单件
				excel.setCellVal(row, 18, purchase_single_weight);

				Double purchase_weight = item.getDouble("purchase_weight"); // 总重
				excel.setCellVal(row, 19, purchase_weight);

				Double purchase_cost = item.getDouble("purchase_cost"); // 单价
				excel.setCellVal(row, 20, purchase_cost);

				Double purchase_account = item.getDouble("purchase_account"); // 总金额
				excel.setCellVal(row, 21, purchase_account);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 22, work_order_no);

				String purchase_remark = item.getStr("purchase_remark");// 备注
				excel.setCellVal(row, 23, purchase_remark);
				row++;

			}
			excel.save2File(targetfile);
			filename.add(targetfile.getAbsolutePath());
			excel.close();
			index++;

		}

		if (filename.size() == 1) {
			File file = new File(filename.get(0));
			if (file.exists()) {
				System.out.println("下载文件");
			}
			renderFile(file);
			return;
		}
		String zipname = parentfile.getAbsolutePath() + ".zip";
		String source = parentfile.getAbsolutePath();
		ZipCompressor zc = new ZipCompressor(zipname);
		zc.compress(source);

		FileUtils.forceDeleteOnExit(parentfile);
		renderFile(new File(zipname));

	}

	public List<List<Record>> split(List<Record> list) {

		List<List<Record>> relist = new ArrayList<List<Record>>();
		List<Record> item = new ArrayList<Record>();
		for (int i = 0; i < list.size(); i++) {

			if ((i + 1) % 10 == 0) {
				item.add(list.get(i));
				relist.add(item);
				item = new ArrayList<Record>();

			} else {
				item.add(list.get(i));

			}

		}
		if (list.size() % 10 != 0) {
			relist.add(item);
		}
		return relist;

	}

	protected String compress(List<String> filenames) throws IOException {

		String name = "采购单" + System.currentTimeMillis() + ".zip";
		String filePath = PathKit.getWebRootPath() + File.separator + "download/excel/" + name;

		FileOutputStream f = new FileOutputStream(filePath);
		CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
		ZipOutputStream zos = new ZipOutputStream(csum);
		BufferedOutputStream out = new BufferedOutputStream(zos);
		zos.setComment("采购单压缩");
		ZipEntry entry = null;

		for (String resource : filenames) {
			System.out.println("writing file: " + resource);
			BufferedReader in = new BufferedReader(new FileReader(resource));
			entry = new ZipEntry(resource);
			entry.setComment(resource + " comments");
			zos.putNextEntry(entry);
			int c;
			while ((c = in.read()) != -1) {
				out.write(c);
			}
			in.close();
			out.flush();
		}
		out.close();
		System.out.println("checksum: " + csum.getChecksum().getValue());

		return filePath;
	}

}
