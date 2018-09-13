package com.chen.fy.controller.business.commission.receive;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

public class ReceiveController extends BaseController {
	ReceiveService service = ReceiveService.me;
	private static final Logger logger = LogManager.getLogger(ReceiveController.class);

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");
		try {
			Page<FyBusinessOrder> modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key);

			keepPara("keyWord", "condition");

			setAttr("modelPage", modelPage);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		render("receive.html");
	}

	/**
	 *委外接收
	 */
	public void receive() {
		String[] ids = getParaValues("selectId");
		System.out.println();
		try {
			Integer num = service.updateReceive(ids);
			if (num == ids.length) {
				renderJson(Ret.ok().set("msg", "接收完成"));
			} else {
				renderJson(Ret.fail().set("msg", "接收失败"));
			}
			return;
		} catch (Exception e) {

			e.printStackTrace();
			logger.error(e.getMessage());
		}
		renderJson(Ret.fail().set("msg", "接收失败"));

	}

}
