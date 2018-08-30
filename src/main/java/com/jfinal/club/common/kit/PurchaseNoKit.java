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

public class PurchaseNoKit {
	private static final Logger logger = LogManager.getLogger(PurchaseNoKit.class);
	private static Calendar calendar = Calendar.getInstance();
	public static Date currentDate = calendar.getTime();
	public static Integer auomic = 1;

	public static void init() {
		Record record = Db
				.findFirst("select max(purchase_no) purchase_no from fy_business_purchase  where purchase_date='"
						+ DateFormatUtils.format(calendar, "yyyy-MM-dd") + "'");
		if (record != null) {
			String purchase_no = record.getStr("purchase_no");
			if (StringUtils.isNotEmpty(purchase_no)) {
				String num = purchase_no.substring(12, purchase_no.length());
				if (NumberUtils.isNumber(num)) {
					auomic = Integer.valueOf(num);
					auomic++;
				} else {
					logger.warn("采购编码初始化，没有找到今天初始值，");
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

			String no = "FYL" + DateFormatUtils.format(currentDate, "yyyyMMdd") + "-" + auomic;
			auomic++;
			return no;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 12; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println(PurchaseNoKit.getNo() + "   " + Thread.currentThread().getName());
				}
			}).start();

		}
	}

}
