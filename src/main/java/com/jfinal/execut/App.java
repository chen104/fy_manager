package com.jfinal.execut;

import org.apache.commons.lang3.StringUtils;

public class App {
	public static void main(String[] args) {
		Integer[] IN = { 1, 2, 3, 4, 5, 10, 11 };
		System.out.println(StringUtils.join(IN, ","));

	}
}
