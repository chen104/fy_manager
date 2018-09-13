package com.chen.fy.controller.business.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PurchaseNoKit;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class PurchaseService {
	private static final Logger logger = LogManager.getLogger(PurchaseService.class);
	public static final PurchaseService me = new PurchaseService();

	public List<Record> findDownloadList(Date purchase, int supplierId) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(purchase);
		// calendar.add(Calendar.DATE, -1);
		// String start = DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd");
		// calendar.add(Calendar.DATE, 1);
		// calendar.add(Calendar.MONTH, 1);
		String purchase_date = DateFormatUtils.format(purchase, "yyyy-MM-dd");
		try {
			String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
					+ ",can_download";
			List<Record> list = Db.find("select p.*," + sql
					+ " from  fy_business_purchase p left join  fy_business_order o on p.order_id = o.id where    p.supplier_id = "
					+ supplierId + " and purchase_date = '" + purchase_date + "' ");

			return list;
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public File downloadFile(String[] ids, String date, Integer supplier_id) {
		try {
			Supplier supplier = Supplier.dao.findById(supplier_id);
			StringBuilder sb = new StringBuilder();
			SqlKit.joinIds(ids, sb);
			String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
					+ " ,can_download";
			List<Record> list = Db.find("select p.*," + sql
					+ " from  fy_business_purchase p left join  fy_business_order o on p.order_id = o.id where  p.id in "
					+ sb.toString());

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
				File sourceFile = new File(
						this.getClass().getClassLoader().getResource("templet/purchase.xlsx").getFile());
				FileUtils.copyFile(sourceFile, targetfile);

				PIOExcelUtil excel = null;

				excel = new PIOExcelUtil(targetfile, 0);

				String PurchaseNo = PurchaseNoKit.getNo();

				excel.setCellVal(1, 16, supplier.getSupplierNo());
				// excel.setCellVal(2, 16, PurchaseNo);
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

					String purchase_no = item.getStr("purchase_no");// 采购编码
					excel.setCellVal(row, 23, purchase_no);

					String purchase_remark = item.getStr("purchase_remark");// 备注
					excel.setCellVal(row, 24, purchase_remark);
					row++;

				}
				excel.save2File(targetfile);
				filename.add(targetfile.getAbsolutePath());
				excel.close();

			}
			for (Record e : list) {
				e.keep("id", "can_download");
			}
			Db.batchUpdate("fy_business_purchase", list, list.size());
			if (filename.size() == 1) {
				File file = new File(filename.get(0));
				if (file.exists()) {
					System.out.println("下载文件");
				}
				return file;

			}
			String zipname = parentfile.getAbsolutePath() + ".zip";
			String source = parentfile.getAbsolutePath();
			com.jfinal.club.common.kit.ZipCompressor zc = new com.jfinal.club.common.kit.ZipCompressor(zipname);
			zc.compress(source);

			try {
				FileUtils.forceDelete(parentfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return new File(zipname);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}

	}

	/**
	 * 
	 * @param list
	 * @return
	 */
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

}
