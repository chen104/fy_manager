package com.chen.fy.controller.base.service;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

public class SupplierService {
	public final static SupplierService me = new SupplierService();

	public File Download() throws Exception {
		String select = "select s.*,p.name category_name,p.id category_id ";
		String from = " from fy_base_supplier s left join  fy_supplier_category p on s.category= p.id ";
		String where = "";
		String ordebyr = " order by s.id desc";

		List<Record> list = Db.find(select + from + where + ordebyr);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"供应商信息" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/base/supplier_templet.xlsx")
				.getFile();
		File sourceFile = new File(xlsx);
		if (sourceFile.exists()) {
			System.out.println(sourceFile.getName() + " 存在");
		}
		FileUtils.copyFile(sourceFile, targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);

		int row = 1;
		for (Record item : list) {
			String map_tmp = item.getStr("category_name");// 类别
			excel.setCellVal(row, 0, map_tmp);

			String supplier_no = item.getStr("supplier_no");// 厂商编码
			excel.setCellVal(row, 1, supplier_no);

			String name = item.getStr("name");// 厂商名称
			excel.setCellVal(row, 2, name);

			String code = item.getStr("code");// 统一社会代码
			excel.setCellVal(row, 3, code);

			String address = item.getStr("address");// 地址
			excel.setCellVal(row, 4, address);

			String phone = item.getStr("phone");// 电话
			excel.setCellVal(row, 5, phone);

			String bank_account = item.getStr("bank_account");// 开户行
			excel.setCellVal(row, 6, bank_account);

			String bank_no = item.getStr("bank_no");// 银行账号
			excel.setCellVal(row, 7, bank_no);

			Integer settlement_type = item.getInt("settlement_type");// 结算方式
			if (settlement_type == null) {

			} else if (settlement_type == 1) {
				excel.setCellVal(row, 8, "转账");
			} else if (settlement_type == 2) {
				excel.setCellVal(row, 8, "现金");
			} else if (settlement_type == 3) {
				excel.setCellVal(row, 8, "票据");
			}

			Integer settlement_cycle = item.getInt("settlement_cycle");// 结算周期
			if (settlement_cycle == 1) {
				excel.setCellVal(row, 9, "月结30天");
			} else if (settlement_cycle == 2) {
				excel.setCellVal(row, 9, "月结60天");
			} else if (settlement_cycle == 3) {
				excel.setCellVal(row, 9, "现结");
			}

			String contact_person = item.getStr("contact_person");// 联系人
			excel.setCellVal(row, 10, contact_person);

			String contact_type = item.getStr("contact_type");// 联系人
			excel.setCellVal(row, 11, contact_type);// 联系方式

			String remark = item.getStr("remark");// 备注
			excel.setCellVal(row, 12, remark);

			row++;
		}

		excel.save2File(targetfile);
		return targetfile;

	}

	public Ret deleteBatch(String[] ids) {
		Ret ret = null;
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String delete = " delete from fy_base_supplier where id in " + sb.toString();
		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {
				int re = Db.delete(delete);
				return re == ids.length;
			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "删除成功");
		} else {
			ret = Ret.fail().set("msg", "删除失败");
		}
		return ret;
	}
}
