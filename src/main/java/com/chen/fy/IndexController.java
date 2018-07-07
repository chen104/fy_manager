package com.chen.fy;

import com.jfinal.club.common.controller.BaseController;


public class IndexController  extends BaseController{
	
	public void index() {
		render("login.html");
	}

	public void main() {
		render("atladmin/common/main.html");
	}
}
