package com.jfinal.club.common.kit;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

public class SupplierNoKit {

	public static String currentDate;
	public static AtomicInteger auomic = new AtomicInteger();
	private static final Logger logger = LogManager.getLogger(SupplierNoKit.class);

	public static String getNo() {
		synchronized (auomic) {
			String no = "CS-" + auomic.incrementAndGet();
			return no;
		}
	}

	public static void init() {

		Record re = Db.findFirst("select max(supplier_no) supplier_no  from fy_base_supplier  ");
		if (re != null) {
			String no = re.getStr("supplier_no");
			if (StringUtils.isNotEmpty(no)) {
				if (no.startsWith("CS-")) {
					no = no.replace("CS-", "");

				}
				if (NumberUtils.isNumber(no)) {
					Integer init = Integer.valueOf(no);
					init++;
					auomic.set(init);
				} else {
					logger.error(" 初始化  厂商编码生成器失败，初始不是数字");
				}
			}
		}
		List<Record> list = Db.find("select id,  supplier_no  from fy_base_supplier where  supplier_no is null ");
		if (list.size() > 0) {
			for (Record e : list) {
				e.set("supplier_no", getNo());
			}
			Db.batchUpdate("fy_base_supplier", list, list.size());

		}

	}

	public static void main(String[] args) {
		for (int i = 0; i < 12; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println(SupplierNoKit.getNo() + "   " + Thread.currentThread().getName());
				}
			}).start();

		}
	}

	/**
	 * 初始化缓存
	 */
	public static HashMap<Integer, String> initSupplierCache() {
		// Object keyName = CacheKit.get();
		List<Record> list = Db.find("select id,name from fy_base_supplier");
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (Record e : list) {
			map.put(e.getInt("id"), e.getStr("name"));
		}
		CacheKit.put("supplier", "key_name", map);
		return map;
	}

	public static String getName(Integer id) {
		Object keyName = CacheKit.get("supplier", "key_name");
		if (keyName == null) {
			keyName = initSupplierCache();
		}
		if(keyName instanceof HashMap) {
			HashMap<Integer, String> hashmap = (HashMap<Integer, String>) keyName;
			String name = hashmap.get(id);
			return name;
		}
		return null;

	}

}
