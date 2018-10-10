package com.chen.fy.controller.business.commission.purchase;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.chen.fy.model.FyBusinessPurchase;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PurchaseNoKit;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class PurchaseService {
	private static final Logger logger = LogManager.getLogger(PurchaseService.class);

	public final static FyBusinessPurchase dao = new FyBusinessPurchase().dao();
	public final static PurchaseService me = new PurchaseService();

	public Page<FyBusinessPurchase> findPage(Integer pageSize, Integer pageIndex, String condition, String keyWord)
			throws Exception {
		Page<FyBusinessPurchase> modelPage;
		StringBuilder conditionSb = new StringBuilder();
		conditionSb.append(" where add_status = 3 ");
		if ("delay_warn".equals(condition)) {
			String sql = "  AND  DATEDIFF(delivery_date , NOW()) < 4 AND DATEDIFF(delivery_date , NOW()) > 0 and out_quantity = 0 ";
			conditionSb.append(sql);
		} else

		if ("delay".equals(condition)) {
			String sql = " AND     DATEDIFF(delivery_date , NOW()) < 0   and out_quantity = 0 ";
			conditionSb.append(sql);

		} else {
			if (StringUtils.isNotEmpty(keyWord)) {

				if ("purchase_delivery_date".equals(condition)) {

					conditionSb.append(String.format(" AND purchase_delivery_date = '%s'", keyWord));

				} else if ("supplier_name".equals(condition)) {
					conditionSb.append(" AND s.name like '%").append(keyWord).append("%' ");
				}

				else if ("delivery_date".equals(condition)) {

					conditionSb.append(String.format("AND  delivery_date = '%s'", keyWord));

				} else if ("order_date".equals(condition)) {

					conditionSb.append(String.format("AND  DATE_FORMAT(order_date,%s) = '%s'",
							Constant.mysql_date_format, keyWord));

				}

				else if ("work_order_no".equals(condition)) {
					conditionSb.append(" AND  o.work_order_no like  ");
					conditionSb.append("'%").append(keyWord).append("%'");
				} else if (StringUtils.isNotEmpty(keyWord)) {

					conditionSb.append(String.format(" AND  %s like  ", condition));
					conditionSb.append("'%").append(keyWord).append("%'");

				}
			}
		}

		String select = " select o.* ,s.name supplier_name,p.*";
		String from = " from   fy_business_purchase  p LEFT JOIN   fy_business_order o  on  p.order_id = o.id  "
				+ " LEFT JOIN fy_base_supplier s on p.supplier_id = s.id";
		String desc = " order by  p.id desc ";
		modelPage = dao.paginate(pageIndex, pageSize, select, from + conditionSb.toString() + desc);
		return modelPage;
	}

	public File downloadPurchase(String[] ids, String supplier_id) throws Exception {
		StringBuilder sb = new StringBuilder();
		String select = " select o.* ,p.*";
		String from = " from   fy_business_purchase  p LEFT JOIN   fy_business_order o  on  p.order_id = o.id  "
				+ " where p.id in ";
		String desc = " order by  p.id desc ";
		Supplier suplier = Supplier.dao.findById(supplier_id);
		SqlKit.joinIds(ids, sb);
		List<Record> list = Db.find(select + from + sb.toString() + desc);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		String current = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd");
		// File dirFile = new File(parentfile, "报目表" + current);
		//
		// dirFile.mkdir();

		File targetfile = new File(parentfile, "采购单" + current + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/purchase/purchase.xlsx").getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		/**
		 * 填写厂商信息
		 */
		String purchase_no = PurchaseNoKit.getNo();// 编号

		excel.setCellVal(2, 19, purchase_no);// 订单编码：
		excel.setCellVal(3, 19, new Date());// 日期：
		excel.setDateCellStyle(3, 19, "yyyy/MM/yy", HorizontalAlignment.LEFT);

		String supplier_name = suplier.getName();// 厂商：
		excel.setCellVal(4, 19, supplier_name);

		String phone = suplier.getPhone();// 电话/传真：
		excel.setCellVal(5, 19, phone);

		String address = suplier.getAddress();
		excel.setCellVal(6, 19, address);

		String personContect = (suplier.getContactPerson() == null ? "" : suplier.getContactPerson())
				+ (suplier.getContactType() == null ? "" : suplier.getContactType()); // 联系人/电话：

		excel.setCellVal(7, 19, personContect);

		int row = 11;
		DataFormat dataformat = excel.getDateFormat();
		for (Record item : list) {
			String map_no = item.getStr("map_no");
			excel.setCellVal(row, 0, map_no);// 图号

			String commodity_name = item.getStr("commodity_name");// 名称
			excel.setCellVal(row, 6, commodity_name);

			String commodity_spec = item.getStr("commodity_spec");// 规格
			excel.setCellVal(row, 10, commodity_spec);
			//
			// String filename = item.getStr("total_map_no");
			// excel.setCellVal(row, 4, filename);

			Double quantity = item.getDouble("quantity");// 数量
			excel.setCellVal(row, 13, quantity);

			String unit_tmp = item.getStr("unit_tmp");
			excel.setCellVal(row, 15, unit_tmp);

			String suppiler_no = item.getStr("purchase_single_weight");
			excel.setCellVal(row, 17, suppiler_no);

			String purchase_weight = item.getStr("purchase_weight");
			excel.setCellVal(row, 18, purchase_weight);

			Double purchase_cost = item.getDouble("purchase_cost");
			excel.setCellVal(row, 19, purchase_cost);

			Double purchase_account = item.getDouble("purchase_account");
			excel.setCellVal(row, 20, purchase_account);

			String is_has_tax = item.getStr("is_has_tax");
			excel.setCellVal(row, 21, is_has_tax);

			String work_order_no = item.getStr("work_order_no");
			excel.setCellVal(row, 22, work_order_no);

			Date purchase_delivery_date = item.getDate("purchase_delivery_date");
			excel.setCellVal(row, 23, purchase_delivery_date);
			CellStyle style = excel.getCellType();
			style.setBorderLeft(BorderStyle.THIN);
			style.setBorderRight(BorderStyle.THIN);
			style.setAlignment(HorizontalAlignment.LEFT);
			style.setDataFormat(dataformat.getFormat("yyyy-MM-dd"));
			excel.setCellStyle(row, 23, style);

			// String suppiler_no = item.getStr("suppiler_no");
			// excel.setCellVal(row, 7, suppiler_no);
			//
			// String supplier_name = item.getStr("supplier_name");
			// excel.setCellVal(row, 8, supplier_name);
			//

			row++;
		}

		excel.save2File(targetfile);
		excel.close();
		StringBuilder updateSql = new StringBuilder(
				" update  fy_business_purchase set is_download = 1,purchase_no = '" + purchase_no + "' where   id in ");
		SqlKit.joinIds(ids, updateSql);
		Db.update(updateSql.toString());

		return targetfile;
	}

	/**
	 * 删除采购单，实际不删除了，通过修状态位实现
	 * @param id
	 * @return
	 */
	public Ret deletePurchase(Integer id) {

		String sql = " update    fy_business_order o INNER JOIN fy_business_purchase p on o.id = p.order_id"
				+ " set o.order_status = 2 ,p.add_status=-1 where p.id =  " + id;

		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int re = Db.update(sql);
				return re == 2;
			}
		});
		if (re) {
			return Ret.ok().set("msg", "删除完成");
		} else {
			return Ret.ok().set("msg", "删除失败");
		}
	}
}
