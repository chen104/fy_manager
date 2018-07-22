package com.jfinal.template;

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
	}

}
