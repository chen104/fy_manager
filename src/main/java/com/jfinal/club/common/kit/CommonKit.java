package com.jfinal.club.common.kit;

public class CommonKit {
	public static int totalInt(int[] array) {
		int total = 0;
		for (int i = 0; i < array.length; i++) {
			total += array[i];
		}
		return total;
	}

}
