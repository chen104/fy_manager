package com.chen.fy.plugin;

import java.util.List;

import com.jfinal.club.common.CacheNameConstant;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

public class CachInitPlugin implements IPlugin {

	@Override
	public boolean start() {

		return false;
	}

	protected void initIdToName() {
		String sql = "select id,name from fy_base_supplier";
		initId2Name(CacheNameConstant.base_supplier_id2key, "id", "name", sql);

		sql = "select id,`name` from fy_base_customer";
		initId2Name(CacheNameConstant.base_customer_id2key, "id", "name", sql);

	}

	public void initId2Name(String cacheName, String key, String value, String sql) {
		List<Record> list = Db.find(sql);
		for (Record e : list) {
			CacheKit.put(cacheName, e.getStr(key), e.getStr(value));
		}
	}

	@Override
	public boolean stop() {
		CacheKit.getCacheManager().clearAll();
		return false;
	}

}
