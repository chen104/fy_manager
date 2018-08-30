package com.jfinal.String;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class TestDate {
	public static void main(String[] args) throws ParseException {
		String date = "2018-5";
		Date da = DateUtils.parseDate(date, "yyyy-MM");
		Calendar calender = Calendar.getInstance();
		calender.setTime(da);
		System.out.println(DateFormatUtils.format(calender, "yyyy-MM-dd"));
		calender.add(Calendar.DATE, -1);
		System.out.println(DateFormatUtils.format(calender, "yyyy-MM-dd"));
		calender.add(Calendar.DATE, 1);
		calender.add(Calendar.MONTH, 1);
		System.out.println(DateFormatUtils.format(calender, "yyyy-MM-dd"));
		// 737217
		System.out.println(DateUtils.parseDate("2018-6-7", "yyyy-MM-dd").getTime());
	}
}
