package com.jfinal.club.common.kit;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.time.DateFormatUtils;

public class PurchaseNoKit {

	public static String currentDate;
	public static AtomicInteger auomic;
	static {
		auomic = new AtomicInteger();
		currentDate = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
	}

	public static void update() {
		synchronized (auomic) {
			currentDate = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
			auomic.set(1);

		}
	}

	public static String getNo() {
		synchronized (auomic) {
			String no = "FYL" + currentDate + "-" + auomic.incrementAndGet();
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
