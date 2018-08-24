package com.jfinal.zip;

import java.io.File;
import java.io.IOException;

public class zipTest {
	public static void main(String[] args) throws IOException {

		// ZipCompressor zc = new ZipCompressor(
		// "E:\\jfinalPlace\\jfinal-fy\\src\\test\\java\\com\\jfinal\\zip\\test1.zip");
		// zc.compress("E:\\jfinalPlace\\jfinal-fy\\src\\test\\java\\com\\jfinal\\zip\\purchase.xlsx");
		//
		File f = new File("E:\\jfinalPlace\\jfinal-fy\\src\\test\\java\\com\\jfinal\\zip\\test1.zip");
		System.out.println(f.getAbsolutePath());
	}
}
