package com.jfinal.club.common.kit;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class AssistNoKit {
	private static Calendar calendar = Calendar.getInstance();
	private static Date currentDate = calendar.getTime();
	private static Integer auomic = 1;
	private static final Logger logger = LogManager.getLogger(AssistNoKit.class);

	public static void init() {
		Record record = Db.findFirst("select max(assist_no) assist_no from fy_business_assist where assist_date='"
				+ DateFormatUtils.format(calendar, "yyyy-MM-dd") + "'");
		if (record != null) {
			String assist_no = record.getStr("assist_no");
			if (StringUtils.isNotEmpty(assist_no)) {
				String num = assist_no.substring(10, assist_no.length());
				if (NumberUtils.isNumber(num)) {
					auomic = Integer.valueOf(num);
					auomic++;
				} else {
					logger.warn("外协编码初始化失败，");
				}
			}
		}
	}

	public static String getNo() {
		synchronized (auomic) {
			calendar.setTimeInMillis(System.currentTimeMillis());
			if (!DateUtils.isSameDay(calendar.getTime(), currentDate)) {
				auomic = 1;
				currentDate = calendar.getTime();
			}

			String no = "H" + DateFormatUtils.format(currentDate, "yyyyMMdd") + "-" + auomic;
			auomic++;

			return no;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 12; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println(AssistNoKit.getNo() + " " + Thread.currentThread().getName());
				}
			}).start();

		}
		System.out.println("Hyyyy-MM-dd".length());
		System.out.println("Hyyyy-MM-dd-1".substring(12, "Hyyyy-MM-dd-1".length()));
	}

}
