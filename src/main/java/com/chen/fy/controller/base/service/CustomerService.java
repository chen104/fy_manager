package com.chen.fy.controller.base.service;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class CustomerService {
	public static final CustomerService me = new CustomerService();

	public File download() throws Exception {
		String select = "select *  ";
		String from = " from  fy_base_customer ";
		String where = "";
		String ordebyr = " order by id desc";

		List<Record> list = Db.find(select + from + where + ordebyr);

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"应付单" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板
		String xlsx = this.getClass().getClassLoader().getResource("templet/download/base/customer_templet.xlsx")
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
			// 客户名称 统一社会代码 地址 电话 银行账户 银行账号 结算方式 结算周期
			String name = item.getStr("name");// name
			excel.setCellVal(row, 0, name);

			String code = item.getStr("code");// 统一社会代码
			excel.setCellVal(row, 1, code);

			String address = item.getStr("address");// 地址
			excel.setCellVal(row, 2, address);

			String phone = item.getStr("phone");// 电话
			excel.setCellVal(row, 3, phone);

			String bank_account = item.getStr("bank_account");// 银行账户
			excel.setCellVal(row, 4, bank_account);

			String bank_no = item.getStr("bank_no");// 电话
			excel.setCellVal(row, 5, bank_no);

			String settlement_type = item.getStr("settlement_type");// 结算方式
			excel.setCellVal(row, 6, settlement_type);

			String settlement_cycle = item.getStr("settlement_cycle");// 结算周期
			excel.setCellVal(row, 7, settlement_cycle);

			row++;
		}

		excel.save2File(targetfile);
		return targetfile;
	}
}
