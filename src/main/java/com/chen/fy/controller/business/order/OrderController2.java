package com.chen.fy.controller.business.order;

import java.io.File;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class OrderController2 extends BaseController {
	private static final Logger logger = LogManager.getLogger(OrderController2.class);
	OrderService2 service = OrderService2.me;

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();

		}

		String condition = getPara("condition");
		String order_date_start = getPara("order_date_start");
		String order_date_end = getPara("order_date_end");

		setAttr("append",
				"&keyWord=" + (key == null ? "" : key) + "&condition=" + (condition == null ? "" : condition));
		keepPara("condition", "keyWord", "order_date_start", "order_date_end");
		Page<FyBusinessOrder> modelPage = service.find(condition, key, getParaToInt("p", 1), getPageSize(),
				order_date_start, order_date_end);
		setAttr("modelPage", modelPage);
		render("orderlist2.html");
	}

	public void toDownload() {
		render("download2.html");
	}

	public void findDownload() {
		String start_date = getPara("start_date");
		String end_date = getPara("end_date");
		String condition = getPara("condition");
		keepPara("start_date", "end_date", "keyWord", "condition");
		String keyword = getPara("keyWord");
		if (keyword != null) {
			keyword = keyword.trim();
		}
		try {
			List<Record> list = service.findDownload(start_date, end_date, condition, keyword);
			setAttr("modelList", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());

		}
		render("download2.html");
	}

	public void download() {
		String[] ids = getParaValues("selectId");
		try {
			File file = service.downloanOrder2(ids);
			renderFile(file);

			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		findDownload();
	}

	public void uploadFile() {
		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				total = service.uploadOrder(ufile.getFile());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));

	}

}
