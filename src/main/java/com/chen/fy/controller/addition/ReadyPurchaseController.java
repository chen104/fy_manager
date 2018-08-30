package com.chen.fy.controller.addition;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessReady;
import com.chen.fy.model.FyReadyPurchase;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PurchaseNoKit;
import com.jfinal.club.common.kit.ZipCompressor;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ReadyPurchaseController extends BaseController {
	private static final Logger logger = LogManager.getLogger(ReadyPurchaseController.class);
	static final String select = "select r.* , purchase.*,cate.`name` category_name,cu.name customer_name ,person.`name` planer_name,su.`name` supplier_name ,unit.`name` unit_name ";
	static final String from = " from fy_business_ready  r \r\n"
			+ "		Inner join fy_ready_purchase purchase on purchase.ready_order_id =r.id \r\n"
			+ "		LEFT JOIN fy_base_customer cu on r.customer = cu.id  \r\n"
			+ "		LEFT JOIN fy_base_category cate on cate.id= r.category_id  \r\n"
			+ "		LEFT JOIN fy_base_person person on person.id=r.planer_id  \r\n"
			+ "		left join fy_base_supplier su on su.id= r.supplier_id  \r\n"
			+ "		LEFT JOIN fy_base_unit unit on unit.id = r.unit ";

	public void index() {
		Page<FyReadyPurchase> personPage = null;

		personPage = FyReadyPurchase.dao.paginate(1, 10, select, from + " order by purchase.id desc");

		setAttr("modelPage", personPage);
		render("list.html");
	}

	public void add() {
		Integer id = getParaToInt("id");
		String sql = Db.getSql("ready.select");
		FyBusinessReady order = FyBusinessReady.dao.findFirst(sql, id);
		setAttr("order", order);
		FyReadyPurchase model = new FyReadyPurchase();
		model.setCanDownload(true);
		setAttr("model", model);
		setAttr("action", "save");
		render("edit.html");
	}

	public void save() {
		FyReadyPurchase model = getBean(FyReadyPurchase.class, "model");

		FyBusinessReady order = FyBusinessReady.dao.findById(model.getReadyOrderId());
		order.setIsCreatePurchase(false);
		model.setPurchaseNo(PurchaseNoKit.getNo());
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				// TODO Auto-generated method stub
				return order.delete() && model.update();
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

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyReadyPurchase.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		String sql = Db.getSql("ready.select");
		FyReadyPurchase model = FyReadyPurchase.dao.findById(getParaToInt("id"));
		FyBusinessReady order = FyBusinessReady.dao.findFirst(sql, model.getReadyOrderId());
		setAttr("model", model);
		setAttr("order", order);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		FyReadyPurchase model = getBean(FyReadyPurchase.class, "model");
		if (StringUtils.isEmpty(model.getPurchaseNo())) {
			model.setPurchaseNo(PurchaseNoKit.getNo());
		}
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改 成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void toDownload() {
		render("download.html");
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void downloadZip() {
		Integer supplierId = getParaToInt("supplier_id");
		String date = getPara("date");
		String supplier_name = getPara("supplier_name");
		keepPara("date", "supplier_name", "supplierId");
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
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtils.parseDate(date, "yyyy-MM"));
			calendar.add(Calendar.DATE, -1);
			String start = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
			calendar.add(Calendar.DATE, 1);
			calendar.add(Calendar.MONTH, 1);
			String end = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
			String column = "select r.* , purchase.*,name,unit.`name` unit_name " + "  from fy_business_ready  r \r\n"
					+ "	 INNER JOIN fy_ready_purchase purchase on purchase.ready_order_id =r.id\r\n"
					+ "	 LEFT JOIN fy_base_unit unit on unit.id = r.unit ";

			String sql = column + " where purchase_date > '" + start + "' and purchase_date <'" + end
					+ "' and r.supplier_id = " + supplierId;

			List<Record> list = Db.find(sql);
			if (list.size() == 0) {

				setAttr("downloadMsg", "没有符合条件的采购单");
				render("download.html");
				return;
			}

			List<List<Record>> alllist = split(list);
			List<String> filename = new ArrayList<String>();

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

				excel.setCellVal(1, 16, supplier.getSupplierNo());
				excel.setCellVal(2, 16, PurchaseNoKit.getNo());
				excel.setCellVal(3, 16, DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
				excel.setCellVal(4, 16, supplier.getName());
				excel.setCellVal(5, 16, supplier.getPhone());
				excel.setCellVal(6, 16, supplier.getAddress());
				excel.setCellVal(7, 16, supplier.getContactPerson());
				excel.setCellVal(7, 19, supplier.getContactType());
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
					excel.setCellVal(row, 16, purchase_single_weight);

					Double purchase_weight = item.getDouble("purchase_weight"); // 总重
					excel.setCellVal(row, 17, purchase_weight);

					Double purchase_cost = item.getDouble("purchase_cost"); // 单价
					excel.setCellVal(row, 18, purchase_cost);

					Double purchase_account = item.getDouble("purchase_account"); // 总金额
					excel.setCellVal(row, 19, purchase_account);

					// Double discount = item.getDouble("discount"); // 折扣
					// excel.setCellVal(row, 20, discount);
					//
					// Double discount_account = item.getDouble("discount_account"); // 折后金额
					// excel.setCellVal(row, 21, discount_account);

					String work_order_no = item.getStr("work_order_no");// 工作订单号
					excel.setCellVal(row, 22, work_order_no);

					String purchase_remark = item.getStr("purchase_remark");// 备注
					excel.setCellVal(row, 23, purchase_remark);
					row++;

				}
				excel.save2File(targetfile);
				filename.add(targetfile.getAbsolutePath());
				excel.close();

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
		} catch (Exception e) {
			renderText(e.getMessage());
			logger.error(e.getMessage());
			setAttr("downloadMsg", "下载异常");
			render("download.html");
		}

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
