package com.chen.fy.controller.addition.advisory;

import com.chen.fy.model.FyAdvisoryCost;
import com.chen.fy.model.Unit;

public class AdisoryCostService {
	public final static AdisoryCostService me = new AdisoryCostService();
	public static final FyAdvisoryCost dao = new FyAdvisoryCost();

	public Integer getUnit(String name) {
		Unit u = Unit.dao.findFirst(String.format("select * from  fy_base_unit where name = '%s'", name));
		if (u != null) {
			return u.getId();
		}
		return null;

	}
}
