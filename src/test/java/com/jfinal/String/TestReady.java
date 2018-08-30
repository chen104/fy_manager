package com.jfinal.String;

import java.io.File;

import com.jfinal.club.common.kit.PIOExcelUtil;

public class TestReady {
	public static void main(String[] args) throws Exception {
		File file = new File("E:\\jfinalPlace\\jfinal-fy\\src\\main\\resources\\templet\\upload\\test.xlsx");
		if (file.exists()) {
			System.out.println("文件存在");
		}
		PIOExcelUtil excel = new PIOExcelUtil(file, 0);
		String value = excel.getCellVal(1, 3);
		System.out.println(value);

	}

}
