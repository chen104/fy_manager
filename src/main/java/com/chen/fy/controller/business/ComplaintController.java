package com.chen.fy.controller.business;

import org.apache.commons.lang3.StringUtils;

import com.chen.fy.model.FyComplaint;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.plugin.activerecord.Page;

public class ComplaintController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyComplaint> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyComplaint.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_complaint order by id desc");
		} else {
			modelPage = FyComplaint.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_complaint where name ? order by id desc", "%" + key + "%");
			setAttr("append", "keyWord=" + key);
		}

		setAttr("modelPage", modelPage);
		render("complaint.html");
	}
}
