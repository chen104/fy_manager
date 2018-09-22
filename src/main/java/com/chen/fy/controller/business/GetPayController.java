package com.chen.fy.controller.business;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessOutWarehouse;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PIOExcelUtil.EXCELVERSION;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;

public class GetPayController extends BaseController {
	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOutWarehouse> modelPage = null;
		keepPara("keyWord", "condition");

		if (StringUtils.isEmpty(key)) {

			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id  where is_create_get_pay = 1 order by w.id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			String condition = getPara("condition");
			sb.append(" and  o.").append(condition).append(" like '%").append(key).append("%' ");
			modelPage = FyBusinessOutWarehouse.dao.paginate(getParaToInt("p", 1), 10,
					"select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date",
					"from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id   where is_create_get_pay = 1 "
							+ sb.toString() + "  order by w.id desc");

		}
		setAttr("modelPage", modelPage);
		render("getPay.html");
	}

	public void add() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);

		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());

		// 价格乘以数量，未税金额
		// BigDecimal untax = order.getUntaxedCost().multiply(model.getOutQuantity());
		// model.setUntaxGetpay(untax);
		// BigDecimal tax = untax.multiply(order.getTaxRate());// 税额

		// model.setTax(tax);
		// model.setHangAmount(untax.add(tax));// 挂账金额，应付金额
		// model.setCreateMonth(calendar.get(Calendar.MONTH));
		// calendar.add(Calendar.MONTH, 3);
		// model.setGetpayMonth(calendar.get(Calendar.MONTH));
		setAttr("model", model);
		setAttr("order", order);
		setAttr("action", "save");
		setAttr("isAdd", true);
		render("add.html");
	}

	/**
	 * 新增
	 */
	public void save() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "model");

		// model.setIsCreateGetPay(true);
		// model.setCreateGetpayTime(new Date());
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		// // 挂账数量
		// BigDecimal hangQuantity = order.getHangQuantity();
		//
		// BigDecimal newhangQuantity = model.getOutQuantity().add(hangQuantity);
		// order.setHangQuantity(newhangQuantity);// 挂账数量
		//
		// // 未挂账数量
		// BigDecimal unhangQuantity = order.getUnhangQuantity();
		//
		// // 未挂账
		// BigDecimal newunhangQuantity =
		// unhangQuantity.subtract(model.getOutQuantity());// 未挂账数量
		// order.setUnhangQuantity(newunhangQuantity);

		// if (newunhangQuantity.doubleValue() > 0) {
		// order.setHangStatus("部分挂账");
		// }
		// 挂账数量
		// order.setHangAccount(order.getHangAccount().add(model.getHangAmount()));

		order.setHangTime(new Date());// 最后挂账时间

		boolean re = Db.tx(new IAtom() {

			@Override
			public boolean run() throws SQLException {

				return model.update() && order.update();
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "生成成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	/**
	 * 
	 */
	public void update() {
		FyBusinessOutWarehouse model = getBean(FyBusinessOutWarehouse.class, "model");

		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "更新成功");
		} else {
			ret = Ret.fail().set("msg", "更新失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		FyBusinessOutWarehouse model = FyBusinessOutWarehouse.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("model", model);
		setAttr("order", order);
		setAttr("action", "update");
		render("add.html");
	}

	public void updateDownload() {
		String id = getPara("id");

		int i = Db.update("update fy_business_out_warehouse set can_download = 1 where id = ? ", id);

		Ret ret = null;
		if (i == 1) {
			ret = Ret.ok().set("msg", "生成成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void downloadGetPay() {
		String filePath = null;
		String sql = "select w.*,cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date"
				+ " from  fy_business_out_warehouse w left join fy_business_order o on w.order_id = o.id  where is_create_get_pay = 1 and can_download =1  order by o.id asc";
		List<FyBusinessOutWarehouse> models = FyBusinessOutWarehouse.dao.find(sql);

		try {
			// 读取模板
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("templet/purchase.xlsx");

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(input, 0);
			int row = 1;
			for (FyBusinessOutWarehouse item : models) {

				String cate_tmp = item.getStr("cate_tmp"); // 类别
				excel.setCellVal(row, 0, cate_tmp);

				String plan_tmp = item.getStr("plan_tmp"); // 计划员
				excel.setCellVal(row, 1, plan_tmp);

				Date order_date = item.getDate("order_date"); // 计划员
				excel.setCellVal(row, 2, order_date);

				Date delivery_date = item.getDate("delivery_date"); // 交货日期
				excel.setCellVal(row, 4, delivery_date);

				String work_order_no = item.getStr("work_order_no"); // 交货日期
				excel.setCellVal(row, 5, work_order_no);

				String delivery_no = item.getStr("delivery_no"); // 送货单号
				excel.setCellVal(row, 6, delivery_no);

				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 7, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 8, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 9, map_no);

				String technology = item.getStr("technology"); // 总图号
				excel.setCellVal(row, 10, technology);

				String machining_require = item.getStr("machining_require"); // 总图号
				excel.setCellVal(row, 11, machining_require);

				Double quantity = item.getDouble("quantity"); // s数量
				excel.setCellVal(row, 12, quantity);

				Double untaxed_cost = item.getDouble("untaxed_cost"); // s数量
				excel.setCellVal(row, 13, untaxed_cost);

				Date out_time = item.getDate("out_time"); // 交货日期
				excel.setCellVal(row, 14, out_time);

				Double out_quantity = item.getDouble("out_quantity"); // 出库数量
				excel.setCellVal(row, 15, out_quantity);

				Double tax = item.getDouble("tax"); // 税额
				excel.setCellVal(row, 16, tax);

				Double untax_getpay = item.getDouble("untax_getpay"); // 税额
				excel.setCellVal(row, 17, untax_getpay);

				Double hang_amount = item.getDouble("hang_amount"); // 税额
				excel.setCellVal(row, 18, hang_amount);

				String create_month = item.getStr("create_month"); // 总图号
				excel.setCellVal(row, 19, create_month + "月");

				String getpay_month = item.getStr("getpay_month"); // 应付期间
				excel.setCellVal(row, 20, getpay_month + "月");

				Date create_getpay_time = item.getDate("create_getpay_time"); // 交货日期
				excel.setCellVal(row, 21, create_getpay_time);

				row++;

			}

			String name = System.currentTimeMillis() + "";
			filePath = PathKit.getWebRootPath() + File.separator + "download/excel/" + name;

			excel.save2File(filePath);

			// renderJson(list);
			if (EXCELVERSION.EXCEL_VERSION_2003 == excel.getCurrentVersion()) {
				filePath += ".xls";
			} else {
				filePath += ".xlsx";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(filePath);
		// System.out.println("is file " + file.isFile() + " " + file.getAbsolutePath()
		// + " " + file.getName());

		renderFile(file, "应收明细单" + DateFormatUtils.format(System.currentTimeMillis(), "(yyyy-MM-dd hh:mm:ss).xlsx"));
	}
}
