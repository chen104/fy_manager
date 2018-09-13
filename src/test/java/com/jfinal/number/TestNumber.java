package com.jfinal.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Test;

public class TestNumber {
	public static void main(String[] args) {
		// Print out a number using the localized number, integer, currency,
		// and percent format for each locale
		Locale[] locales = NumberFormat.getAvailableLocales();
		double myNumber = -1234.56;
		NumberFormat form;
		for (int j = 0; j < 4; ++j) {
			System.out.println("FORMAT");
			for (int i = 0; i < locales.length; ++i) {
				if (locales[i].getCountry().length() == 0) {
					continue; // Skip language-only locales
				}
				System.out.print(locales[i].getDisplayName());
				switch (j) {
				case 0:
					form = NumberFormat.getInstance(locales[i]);
					break;
				case 1:
					form = NumberFormat.getIntegerInstance(locales[i]);
					break;
				case 2:
					form = NumberFormat.getCurrencyInstance(locales[i]);
					break;
				default:
					form = NumberFormat.getPercentInstance(locales[i]);
					break;
				}
				if (form instanceof DecimalFormat) {
					System.out.print(": " + ((DecimalFormat) form).toPattern());
				}
				System.out.print(" -> " + form.format(myNumber));
				try {
					System.out.println(" -> " + form.parse(form.format(myNumber)));
				} catch (ParseException e) {
				}
			}
		}

	}

	@Test
	public void curent() {
		long l = System.currentTimeMillis();
		System.out.println(DateFormatUtils.format(l, "yyyy-MM-dd hh:mm:ss"));
		System.out.println(l);
	}
}
