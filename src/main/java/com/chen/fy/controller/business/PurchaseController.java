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
import java.text.ParseException;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
import com.jfinal.upload.UploadFile;

public class PurchaseController extends BaseController {
	private static final Logger logger = LogManager.getLogger(PurchaseController.class);

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
		StringBuilder resb = new StringBuilder();
		List<Integer> exsist = new ArrayList<Integer>();
		Date date = new Date();
		/*
		 * 检测是否已生成采购单
		 */
		List<FyBusinessOrder> orders = new ArrayList<FyBusinessOrder>();
		for (Integer S : orderIds) {
			List<Record> list = Db.find("select  1 from fy_business_purchase where order_id = ? and supplier_id = ?", S,
					supplier);
			if (list.size() > 0) {
				exsist.add(S);
				continue;
			}
			FyBusinessPurchase model = new FyBusinessPurchase();

			FyBusinessOrder order = FyBusinessOrder.dao.findById(S);
			orders.add(order);
			order.setReceiveTime(date);
			model.setPurchaseQuantity(order.getQuantity());

			model.setPurchaseTitle(order.getCommodityName());
			model.setOrderId(S);
			model.setParentId(S);
			model.setSupplierId(supplier);
			model.setPurchaseDate(purchase);

			model.setPurchaseNo(PurchaseNoKit.getNo());
			modelList.add(model);

		}
		if (modelList.size() == 0) {
			renderJson(Ret.fail().set("msg", "选择的订单已存在采购单，新建采购失败"));
			return;
		}
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				int[] re = Db.batchSave(modelList, 10);
				int[] up = Db.batchUpdate(orders, orders.size());
				int row = 0;
				for (int i = 0; i < re.length; i++) {
					row = row + re[i];
				}

				return row == modelList.size();
			}
		});

		Ret ret = null;
		if (exsist.size() > 0) {
			StringBuilder insb = new StringBuilder();
			SqlKit.joinIds(exsist, insb);
			List<Record> ls = Db
					.find("select commodity_name,work_order_no,s.name supplier_name from fy_business_order o\r\n"
							+ "INNER  JOIN fy_base_supplier s\r\n" + " where o.id in  " + insb + " and s.id=  "
							+ supplier);
			StringBuilder reStr = new StringBuilder();
			reStr.append("新建" + modelList.size() + "条 采购单成功 \n");
			for (Record e : ls) {
				reStr.append(e.get("work_order_no") + " " + e.getStr("commodity_name") + " " + e.getStr("supplier_name")
						+ " \n");
			}
			reStr.append(" 已存在采购单");

			ret = Ret.ok("msg", reStr);
			renderJson(ret);
			return;
		}
		if (re) {
			ret = Ret.ok("msg", "新建" + modelList.size() + "条 采购单成功");
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
			String PurchaseNo = PurchaseNoKit.getNo();

			excel.setCellVal(1, 16, supplier.getSupplierNo());
			excel.setCellVal(2, 16, PurchaseNo);
			excel.setCellVal(3, 16, DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
			excel.setCellVal(4, 16, supplier.getName());
			excel.setCellVal(5, 16, supplier.getPhone());
			excel.setCellVal(6, 16, supplier.getAddress());
			excel.setCellVal(7, 16, supplier.getContactPerson());
			excel.setCellVal(7, 19, supplier.getContactType());

			int row = 11;
			for (Record item : itemlist) {
				item.set("purchase_parent", PurchaseNo);
				item.set("can_download", false);
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
				excel.setCellVal(row, 16, purchase_single_weight);

				Double purchase_weight = item.getDouble("purchase_weight"); // 总重
				excel.setCellVal(row, 17, purchase_weight);

				Double purchase_cost = item.getDouble("purchase_cost"); // 单价
				excel.setCellVal(row, 18, purchase_cost);

				Double purchase_account = item.getDouble("purchase_account"); // 总金额
				excel.setCellVal(row, 19, purchase_account);

				Double discount = item.getDouble("discount"); // 折扣
				excel.setCellVal(row, 20, discount);

				Double discount_account = item.getDouble("discount_account"); // 折后金额
				excel.setCellVal(row, 21, discount_account);

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
		for (Record e : list) {
			e.keep("id", "can_download", "purchase_parent");
		}
		Db.batchUpdate("fy_business_purchase", list, list.size());
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

		FileUtils.forceDelete(parentfile);
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

	public void uploadPurchase() {

	}

	public void tomyDownload() {
		render("mydownload.html");
	}

	public void download() {
		try {
			Integer supplierId = getParaToInt("supplier_id");
			String date = getPara("date");
			String supplier_name = getPara("supplier_name");
			Supplier supplier = Supplier.dao.findById(supplierId);
			if (supplier == null) {
				setAttr("supplierMsg", "没有选择供应商");
				render("mydownload.html");
				return;
			}
			if (StringUtils.isEmpty(date)) {
				setAttr("dateMsg", "没有选择采购月份");
				render("mydownload.html");
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
			// 读取模板
			// InputStream input =
			// this.getClass().getClassLoader().getResourceAsStream("templet/purchase.xlsx");
			File targetfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel/",
					supplier.getName() + "_" + date + ".xlsx");
			if (targetfile.exists()) {
				targetfile.delete();
			}
			FileUtils.copyFile(
					new File(
							this.getClass().getClassLoader().getResource("templet/download/mypurchase.xlsx").getFile()),
					targetfile);
			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);
			//
			// excel.setCellVal(1, 16, "客户编码 : " + supplier.getId());
			// excel.setCellVal(2, 16, "订单编号 : " + PurchaseNoKit.getNo());
			// excel.setCellVal(3, 16, "订单日期 : " +
			// DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
			// excel.setCellVal(4, 16, "厂 商 : " + supplier.getName());
			// excel.setCellVal(5, 16, "电话/传真 : " + supplier.getPhone());
			// excel.setCellVal(6, 16, "地址 :" + supplier.getAddress());
			// excel.setCellVal(7, 16, "联系人 :" + supplier.getContactPerson() + " 联系方式 " +
			// supplier.getContactType());

			int row = 11;
			for (Record item : list) {

				String purchase_no = item.getStr("purchase_no"); // 商品名称
				excel.setCellVal(row, 0, purchase_no);

				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 1, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 2, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 3, map_no);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 4, quantity);

				String cate_tmp = item.getStr("unit_tmp"); // 单位
				excel.setCellVal(row, 5, cate_tmp);

				Double purchase_single_weight = item.getDouble("purchase_single_weight"); // 单件
				excel.setCellVal(row, 6, purchase_single_weight);

				Double purchase_weight = item.getDouble("purchase_weight"); // 总重
				excel.setCellVal(row, 7, purchase_weight);

				Double purchase_cost = item.getDouble("purchase_cost"); // 单价
				excel.setCellVal(row, 8, purchase_cost);

				Double purchase_account = item.getDouble("purchase_account"); // 总金额
				excel.setCellVal(row, 9, purchase_account);

				Double discount = item.getDouble("discount"); // 单价
				excel.setCellVal(row, 10, discount);

				Double discount_account = item.getDouble("discount_account"); // 总金额
				excel.setCellVal(row, 11, discount_account);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 12, work_order_no);

				String purchase_remark = item.getStr("purchase_remark");// 备注
				excel.setCellVal(row, 13, purchase_remark);
				row++;

			}
			excel.save2File(targetfile);
			renderFile(targetfile);
			return;

		} catch (

		ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void upload() {
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();

				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				// excel.setCellVal(1, 16, supplier.getSupplierNo());
				// excel.setCellVal(2, 16, PurchaseNoKit.getNo());
				String supplierNO = excel.getCellVal(1, 16);
				if (StringUtils.isEmpty(supplierNO)) {
					setAttr("msg", "没有厂商编码");
					renderJson(Ret.fail("msg", "没有厂商编码"));
					return;
				}
				Supplier supplier = Supplier.dao.findFirst("select * from fy_base_supplier where supplier_no = ?",
						supplierNO);
				if (supplier == null) {

					renderJson(Ret.fail("msg", "不存在厂商编码"));
					return;
				}

				List<FyBusinessPurchase> update = new ArrayList<FyBusinessPurchase>();

				int row = 11;
				for (; row < 32; row++) {

					// Double purchase_single_weight = item.getDouble("purchase_single_weight"); //
					// 单件
					// excel.setCellVal(row, 6, purchase_single_weight);

					String work_order_no = excel.getCellVal(row, 22);

					if (StringUtils.isEmpty(work_order_no)) {
						continue;
					}
					FyBusinessPurchase item = FyBusinessPurchase.dao.findFirst(
							"select p.*,work_order_no from fy_business_purchase p inner join  fy_business_order o on p.order_id =o.id where supplier_id = ? and o.work_order_no = ? ",
							supplier.getId(), work_order_no);

					item.set("supplier_id", supplier.getId());
					String purchase_single_weight = excel.getCellVal(row, 16);
					if (StringUtils.isEmpty(purchase_single_weight)) {
						purchase_single_weight = "0";
					}
					item.set("purchase_single_weight", purchase_single_weight);

					String purchase_weight = excel.getCellVal(row, 17); // 总重
					if (StringUtils.isEmpty(purchase_weight)) {
						purchase_weight = "0";
					}
					item.set("purchase_weight", purchase_weight);

					String purchase_cost = excel.getCellVal(row, 18);// 单价
					if (StringUtils.isEmpty(purchase_cost)) {
						purchase_cost = "0";
					}
					item.set("purchase_cost", purchase_cost);

					String purchase_account = excel.getCellVal(row, 19);// 采购金额
					if (StringUtils.isEmpty(purchase_account)) {
						purchase_account = "0";
					}
					item.set("purchase_account", purchase_account);

					String discount = excel.getCellVal(row, 20);
					if (StringUtils.isEmpty(discount)) {
						discount = "0";
					}
					item.set("discount", discount);
					// 折后金额

					String discount_account = excel.getCellVal(row, 21);
					if (StringUtils.isEmpty(discount_account)) {
						discount_account = "0";
					}
					item.set("discount_account", discount_account);
					// 工作订单号

					update.add(item);
				}
				int[] re = Db.batchUpdate(update, update.size());
				List<Integer> list = new ArrayList<Integer>();

				for (int i = 0; i < re.length; i++) {
					total += re[i];
					if (re[i] == 0) {
						list.add(i + 1);
						logger.warn(" 采购单导入 存在没有更新成功的，第 " + i + "条，从0开始算  ");
					}
				}
				if (list.size() > 0) {
					StringBuilder sb = new StringBuilder();
					sb.append("添加了" + total + "记录 \n").append("其中  第 ");
					for (Integer i : list) {
						sb.append(i).append(",");
					}
					sb.append(" 条记录更新失败 ");
					renderJson(Ret.ok("msg", sb.toString()));
					return;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
				renderJson(Ret.ok("msg", "文件错误"));
				return;
			}
		}
		ufile.getFile().delete();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));
	}

	public void avaliable(PIOExcelUtil excel, StringBuilder sb) {
		int row = 11;
		for (int i = 1; i < 32; i++) {

			String purchase_single_weight = excel.getCellVal(row, 16);// 单件
			if (NumberUtils.isNumber(purchase_single_weight)) {

			}
			String purchase_weight = excel.getCellVal(row, 17); // 总重

			String purchase_cost = excel.getCellVal(row, 18);// 单价

			String purchase_account = excel.getCellVal(row, 19);// 采购金额

			String discount = excel.getCellVal(row, 20);

			// 折后金额

			String discount_account = excel.getCellVal(row, 21);

			// 工作订单号

			String work_order_no = excel.getCellVal(row, 22);

		}
	}

}
