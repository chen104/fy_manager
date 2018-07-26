package com.jfinal.club.common.kit;

import java.math.BigDecimal;

import com.chen.fy.controller.role.Exception.FyException;
import com.chen.fy.model.TaxRate;
import com.jfinal.plugin.ehcache.CacheKit;

public class ContextKit {
	/**
	 * 获得税率
	 * 
	 * @return
	 * @throws FyException
	 */
	public static final BigDecimal getTaxRate() throws FyException {
		Object obj = CacheKit.get("base", "taxRate");
		if (obj instanceof TaxRate) {
			return (BigDecimal) obj;
		} else {
			TaxRate taxRate = TaxRate.dao.findFirst("select * from fy_base_tax_rate");
			if (taxRate == null || taxRate.getMatchValue() == null) {
				throw new FyException("没有设置税率");
			}
			CacheKit.put("base", "taxRate", taxRate.getMatchValue());
			return taxRate.getMatchValue();
		}

	}

}
