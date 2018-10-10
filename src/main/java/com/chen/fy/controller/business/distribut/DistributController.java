package com.chen.fy.controller.business.distribut;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class DistributController extends BaseController {
	private static final Logger logger = LogManager.getLogger(DistributController.class);
	DistributService service = DistributService.me;

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		String condition = getPara("condition");

		Page<FyBusinessOrder> modelPage = null;
		try {
			modelPage = service.findPage(getPageSize(), getParaToInt("p", 1), condition, key);

			setAttr("keyWord", key);
			keepPara("condition", "keyWord");

			setAttr("modelPage", modelPage);
			render("distribute.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void downloadDistribute() {

	}

	public void toDownload() {

	}

	public void findDownload() {

	}

	public void download() {

	}

	/**
	 * 分配订单
	 */
	public void distributeBatch() {
		String[] ids = getParaValues("ids[]");
		String disTo = getPara("disTo");
		String wwcate = getPara("wwcate");

		StringBuilder sql = new StringBuilder();
		if ("自产".equals(disTo)) {
			// order.setDisTo(false);
			sql.append("update fy_business_order set dis_to = 0 ,distribute_to =  '" + disTo
					+ "' ,is_distribute = 1, distribute_time = now() ,order_status=10,orderby = 1  where id in ");
			com.jfinal.club.common.kit.SqlKit.joinIds(ids, sql);
		} else if ("委外".equals(disTo)) {
			// order.setDisTo(true);
			String str = "update fy_business_order set dis_to = 1 ,distribute_to = '%s' , is_distribute = 1, weiwai_cate ='%s',order_status=1  ,distribute_time = now()  , orderby = 1 where id in ";
			sql.append(String.format(str, disTo, wwcate));
			com.jfinal.club.common.kit.SqlKit.joinIds(ids, sql);
		} else {// 备货
			sql.append("update fy_business_order set dis_to = 3,order_status=30,distribute_to = '" + disTo
					+ "' ,is_distribute = 1, distribute_time = now()  , orderby = 1 where id in ");
			com.jfinal.club.common.kit.SqlKit.joinIds(ids, sql);
		}

		// order.setOrderby(1);
		// order.setHandleStatus("处理中");
		boolean save = false;
		save = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				int update = Db.update(sql.toString());
				if ("备货".equals(disTo)) {
					Date now = new Date();
					String insert = "insert fy_ready_add(add_order_id,create_time) VALUES (?, ?)\n";
					Object[][] param = new Object[ids.length][2];
					for (int i = 0; i < ids.length; i++) {
						param[i][0] = ids[i];
						param[i][1] = now;
					}
					Db.batch(insert, param, ids.length);
				}
				return update == ids.length;
			}
		});
		Ret ret = null;
		if (save) {
			ret = Ret.ok("msg", "分配成功");

		} else {
			ret = Ret.ok("msg", "分配失败");
		}
		renderJson(ret);
	}

	/**
	 * 订单选择图纸文件
	 */
	public void updateOrderMapFile() {
		Integer orderid = getParaToInt("orderId");
		int fileId = getParaToInt("fileId");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(orderid);
		Ret ret = null;
		if (order != null) {
			order.setDraw(fileId);
			boolean re = order.update();

			if (re) {
				ret = Ret.ok("msg", "添加图纸 信息成功");
			} else {
				ret = Ret.fail().set("msg", "添加图纸失败");
			}
		} else {
			ret = Ret.fail("msg", "没找到订单");
		}

		renderJson(ret);
	}

	/**
	 * 测回分配，修改状态为 dis_to =null , handle_status =‘未处理 ’ distribute_to` ,
	 * `distribute_attr ，测回,is_distribute =1
	 */
	public void rollBackOrder() {
		Integer id = getParaToInt("selectId");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		order.setDisTo(null);
		order.setHandleStatus("未处理");
		order.setDistributeTo(null);
		order.setDistributeAttr("撤回");
		order.setIsDistribute(true);
		order.setIsCreatePlan(false);
		order.setReceiveTime(null);

		order.setDistributeTime(null);// 分配时间
		List<Record> list = Db.find("select 1 from fy_business_in_warehouse where order_id = ?", id);
		if (list.size() > 0) {
			renderJson(Ret.fail().set("msg", "已生成入库单，不可撤回"));
		}
		list = Db.find("select 1 from fy_business_purchase where order_id = ?", id);
		if (list.size() > 0) {
			renderJson(Ret.fail().set("msg", "已生采购单，不可撤回"));
		}
		boolean re = order.update();
		if (re) {
			renderJson(Ret.ok().set("msg", "撤回成功"));
		} else {
			renderJson(Ret.fail().set("msg", "撤回失败，请重试"));
		}
	}

	/**
	 * 下载
	 */
	public void downloadDistribut() {

		String[] ids = getParaValues("selectId");
		try {
			File file = service.downloadDistrbut(ids);
			renderFile(file);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		index();
	}

	/**
	 * 撤回委外，自产到分配表
	 */
	public void batchRollback() {
		String[] selectid = getParaValues("selectId");
		try {
			Ret ret = service.rollBack(selectid);
			renderJson(ret);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		renderJson(Ret.ok().set("msg", "撤回异常，请查看运行日志"));
	}

}
