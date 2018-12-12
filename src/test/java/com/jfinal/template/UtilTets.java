package com.jfinal.template;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class UtilTets {
	@Test
	public void dotest() {
		System.out.println(NumberUtils.isNumber("3.2"));
	}

	public static void main(String[] args) {
		System.out.println(NumberUtils.isNumber("3.2 "));
		System.out.println(StringUtils.isNumeric("3.2"));
		DecimalFormat format = new DecimalFormat("#,###.##");
		;
		System.out.println(" " + format.format(Double.valueOf("123123")));// 3.14
		// System.out.println(" " + format.format(3));// 3.14
		// System.out.println(" " + format.format(3.1));// 3.14
		// System.out.println(" " + format.format(123.14));// 3.14
		// System.out.println(" " + format.format(123));// 3.14
		// System.out.println(" " + format.format("123123.12"));// 3.14
		// System.out.println(" " + format.format(123123));// 3.14
	}

}
