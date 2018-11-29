package com.jfinal.club.common.kit;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.Unit;
import com.jfinal.plugin.ehcache.CacheKit;

public class CommonKit {
	public static int totalInt(int[] array) {
		int total = 0;
		for (int i = 0; i < array.length; i++) {
			total += array[i];
		}
		return total;
	}

	/**
	 * 名称获取ID
	 * @param unitname
	 * @return
	 */
	public Integer getUintId(String unitname) {
		if(StringUtils.isEmpty(unitname)) {
			return null;
		}
		unitname = unitname.trim();
		Object obj = CacheKit.get("base", "unit_id");
		if(obj ==null) {
			obj =new HashMap<String,Integer>();
		}
		 HashMap<String,Integer> unith = ( HashMap<String,Integer>)obj;
		Integer id = unith.get(unitname);
		if (id == null) {
			Unit model = Unit.dao.findFirst(String.format("select * from fy_base_unit where name = '%s'", unith));
			if (model != null) {
				id = model.getId();
			}
		}
		return id;
	}

}
