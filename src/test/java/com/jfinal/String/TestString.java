package com.jfinal.String;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class TestString {
	public static void main(String[] args) {
		// System.out.println(String.format("%s ", "nihao"));
		//
		// StringBuilder sb = new StringBuilder("123123");
		// sb.deleteCharAt(sb.length() - 1);
		// System.out.println(sb.toString());
		//
		// List<String> deliveryNos = new ArrayList<String>();
		// int row = 0;

		HashSet<String> deliveryNoSet = new HashSet<String>();
		deliveryNoSet.add("1");
		deliveryNoSet.add("2");
		deliveryNoSet.add("3");
		deliveryNoSet.add("4");
		deliveryNoSet.add("5");

		StringBuilder sb = null;

		Iterator<String> iterator = deliveryNoSet.iterator();
		int row = 0;
		List<String> deliveryNos = new ArrayList<>();
		while (iterator.hasNext()) {
			if (row % 10 == 0) {
				if (sb != null) {
					if (sb.length() > 2) {
						sb.deleteCharAt(sb.length() - 1);
					}
					sb.append(")");
					deliveryNos.add(sb.toString());
					sb = new StringBuilder();
					sb.append("(");
				} else {
					sb = new StringBuilder();
					sb.append("(");

				}

			}
			sb.append("'").append(iterator.next()).append("',");
			row++;
		}
		if (sb.length() > 2) {
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
		}
		deliveryNos.add(sb.toString());
		System.out.println(deliveryNos);
	}

	@Test
	public void testInitPerssiom() throws IOException {

	}

}
