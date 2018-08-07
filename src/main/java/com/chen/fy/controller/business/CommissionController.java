package com.chen.fy.controller.business;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.chen.fy.model.FyBizWwReceive;
import com.chen.fy.model.FyBusinessAssist;
import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessPurchase;
import com.chen.fy.model.Supplier;
import com.jfinal.aop.Before;
import com.jfinal.club.common.controller.BaseController;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.PIOExcelUtil.EXCELVERSION;
import com.jfinal.club.common.kit.PurchaseNoKit;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

public class CommissionController extends BaseController {
	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		keepPara("keyWord", "condition");
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 and is_finish_purchase = 0    order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and ").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ", String.format(
					"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 and is_finish_purchase = 0  %s order by id desc",
					sb.toString()));
		}

		setAttr("modelPage", modelPage);
		render("receive.html");
	}

	/**
	 * 
	 */
	@Before(Tx.class)
	public void receiveOrder() {
		Integer id = getParaToInt("id", 1);
		FyBizWwReceive model = FyBizWwReceive.dao.findById(id);
		model.setIsReceive(true);
		Integer pid = model.getParentId();
		FyBusinessDistribute distribut = FyBusinessDistribute.dao.findById(pid);
		distribut.setReceiveTime(new Date());
		Ret ret = null;

		boolean re = model.update();
		boolean save = distribut.update();
		if (re && save) {
			ret = Ret.ok("msg", "接收成功");

		} else {
			ret = Ret.ok("msg", "接收失败");
		}
		renderJson(ret);
	}

	public void toAddPurchase() {
		Integer id = getParaToInt("id");
		FyBusinessOrder model = FyBusinessOrder.dao.findById(id);
		setAttr("model", model);
		render("addPurchase.html");
	}

	public void savePurchase() {

		FyBusinessPurchase model = getBean(FyBusinessPurchase.class, "model");
		model.setPurchaseNo(PurchaseNoKit.getNo());
		// FyBusinessOrder order = FyBusinessOrder.dao.findById(orderid);

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return model.save();
			}
		});
		// System.out.println(model);

		// Integer[] suppliers = getParaValuesToInt("supplier[]");
		// Date purchaseDate = getParaToDate("purchaseDate");
		// String purchaseSingleWeight = getPara("purchaseSingleWeight");
		// String purchaseWeight = getPara("purchaseWeight");
		// String purchaseCost = getPara("purchaseCost");
		// String purchaseAccount = getPara("purchaseAccount");

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建 成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);

	}

	public void sumReceive() {
		String key = getPara("keyWord");
		Page<FyBizWwReceive> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBizWwReceive.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order  where Is_Distribute = 1 and dis_to = 1 order by id desc");

		setAttr("modelPage", modelPage);
		render("sumReceive.html");
	}

	public void toCreatePurchase() {
		String ids[] = getParaValues("selectId");
		// StringBuilder spBuilder = new StringBuilder("[");
		List<String> list = new ArrayList<String>();

		for (String s : ids) {
			// spBuilder.append(s).append(",");
			list.add(s);
		}
		// spBuilder.deleteCharAt(spBuilder.length()-1);
		// spBuilder.setCharAt(spBuilder.length() - 1, ']');
		setAttr("ids", list);
		// setAttr("idToString", spBuilder.toString());
		render("purchaseBatch.html");
	}

	public void createBatchPurchase() {
		Integer[] orderIds = getParaValuesToInt("orderIds");
		List<FyBusinessPurchase> modelList = new ArrayList<FyBusinessPurchase>();
		Integer supplier = getParaToInt("supplierId");
		Date purchase = getParaToDate("purchaseDate");
		for (Integer S : orderIds) {
			FyBusinessPurchase model = new FyBusinessPurchase();
			model.setOrderId(S);
			model.setParentId(S);
			model.setSupplierId(supplier);
			model.setPurchaseDate(purchase);
			model.setPurchaseNo(PurchaseNoKit.getNo());
			modelList.add(model);

		}
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				int[] re = Db.batchSave(modelList, 10);
				int row = 0;
				for (int i = 0; i < re.length; i++) {
					row = row + re[i];
				}
				String updatesql = " update fy_business_order p  set is_finish_purchase = 1  "
						+ "where is_finish_purchase is null " + "and  EXISTS ("
						+ "	select 1 from fy_business_purchase  fp where p.id = fp.order_id\r\n" + ")";
				Db.update(updatesql.toUpperCase());// 已生成采购单
				return row == modelList.size();
			}
		});

		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建 成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);

	}

	public void editPurchase() {
		Integer id = getParaToInt("id");
		keepPara("p");
		FyBusinessPurchase purchase = FyBusinessPurchase.dao.findById(id);
		Supplier supplier = Supplier.dao.findById(purchase.getSupplierId());
		FyBusinessOrder order = FyBusinessOrder.dao.findById(purchase.getOrderId());
		setAttr("order", order);
		setAttr("supplier", supplier);
		setAttr("model", purchase);
		render("purchase/edit.html");
	}

	public void updatePurchase() {
		FyBusinessPurchase model = getBean(FyBusinessPurchase.class, "model");
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "更新成功");
		} else {
			ret = Ret.ok("msg", "更新失败");
		}
		renderJson(ret);
	}

	public void purchase() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		keepPara("keyWord", "condition", "p");
		String condition = getPara("condition");
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
				+ ",s.name supplier_name";
		if (StringUtils.isEmpty(key)) {

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select p.*, " + sql,
					"from  fy_business_purchase p left join  fy_business_order o on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id  order by id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			if ("supplier_name".equals(condition)) {
				sb.append("  s.name like '%").append(key).append("%' ");
			} else {
				sb.append("  o.").append(condition).append(" like '%").append(key).append("%' ");
			}

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10, "select p.*, " + sql,
					"from  fy_business_purchase p left join  fy_business_order o on p.order_id =o.id inner join fy_base_supplier s on p.supplier_id = s.id  where "
							+ sb.toString() + " order by p.id desc");
		}
		setAttr("modelPage", modelPage);
		render("purchase.html");
	}

	public void downloadPurchase() {
		String[] ids = getParaValues("selectPurchase");
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String filePath = null;
		try {
			// 读取模板
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("templet/purchase.xlsx");

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(input, 0);
			/*
			 * 查看是否有多个供应商
			 */
			Supplier supplier = Supplier.dao.findFirst(
					" select s.* from fy_business_purchase  p inner join fy_base_supplier s  on p.supplier_id = s.id where p.id in    "
							+ sb.toString());
			/*
			 * 开始写供应商信息
			 * 
			 */
			// System.out.println(excel.getCellVal(1, 15));
			// System.out.println(excel.getCellVal(1, 16));
			// System.out.println(excel.getCellVal(1, 17));
			excel.setCellVal(1, 16, "客户编码  : " + supplier.getId());
			excel.setCellVal(2, 16, "订单编号 : " + PurchaseNoKit.getNo());
			excel.setCellVal(3, 16, "订单日期 : " + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
			excel.setCellVal(4, 16, "厂     商 : " + supplier.getName());
			excel.setCellVal(5, 16, "电话/传真 : " + supplier.getPhone());
			excel.setCellVal(6, 16, "地址  :" + supplier.getAddress());
			excel.setCellVal(7, 16, "联系人 :" + supplier.getContactPerson() + " 联系方式  " + supplier.getContactType());

			String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
					+ "";
			List<Record> list = Db.find("select " + sql
					+ " from  fy_business_purchase p left join  fy_business_order o on p.order_id = o.id where p.id in "
					+ sb.toString());
			int row = 11;
			for (Record item : list) {
				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 0, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 6, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 10, map_no);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 13, quantity);

				String cate_tmp = item.getStr("unit_tmp"); // 单位
				excel.setCellVal(row, 15, cate_tmp);

				Double purchase_single_weight = item.getDouble("purchase_single_weight"); // 单件
				excel.setCellVal(row, 17, purchase_single_weight);

				Double purchase_weight = item.getDouble("purchase_weight"); // 总重
				excel.setCellVal(row, 19, purchase_weight);

				Double purchase_cost = item.getDouble("purchase_cost"); // 单价
				excel.setCellVal(row, 20, purchase_cost);

				Double purchase_account = item.getDouble("purchase_account"); // 总金额
				excel.setCellVal(row, 21, purchase_account);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 22, work_order_no);

				String purchase_remark = item.getStr("purchase_remark");// 备注
				excel.setCellVal(row, 23, purchase_remark);
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

		renderFile(file);
		Db.update("update  fy_business_purchase set can_download = 0  where id in " + sb.toString());
	}

	public void oneSumCommission() {
		String key = getPara("keyWord");
		Page<FyBusinessPurchase> modelPage = null;
		keepPara("keyWord", "condition");
		String purchase = "purchase_no,purchase_date,purchase_cost,purchase_account,discount,discount_account";
		String supplier_name = ", bs.name supplier_name";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
					"select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
					"from   fy_business_order   o  left   join fy_business_purchase  p  on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id "
							+ " left join  fy_business_in_warehouse w on o.id = w.order_id"
							+ " left join fy_base_supplier  bs on bs.id = p.supplier_id "
							+ " where dis_to = 1 order by id desc");

		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(" and o.").append(getPara("condition")).append(" like ").append("'%").append(key).append("%' ");

			modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
					"select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
					"from   fy_business_order   o  inner  join fy_business_purchase  p  on p.order_id =o.id left join fy_base_supplier s on p.supplier_id = s.id "
							+ " left join  fy_business_in_warehouse w on o.id = w.order_id"
							+ " left join fy_base_supplier  bs on bs.id = p.supplier_id "
							+ " where dis_to = 1 order by id desc");

			// modelPage = FyBusinessPurchase.dao.paginate(getParaToInt("p", 1), 10,
			// "select o.* ,in_time,real_in_quantity," + purchase + supplier_name,
			// String.format(
			// "from fy_business_purchase p left join fy_business_order o on p.order_id
			// =o.id left join fy_base_supplier s on p.supplier_id = s.id "
			// + " left join fy_business_in_warehouse w on o.id = w.order_id"
			// + " left join fy_base_supplier bs on bs.id = p.supplier_id "
			// + " where dis_to = 1 %s order by id desc",
			// sb.toString()));
		}

		setAttr("modelPage", modelPage);
		render("oneSumCommission.html");
	}

	public void toInWareHouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = new FyBusinessInWarehouse();
		model.setOrderId(id);
		model.setParentId(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		order.setInWarehouseTime(new Date());
		model.setInTime(order.getInWarehouseTime());
		model.setInFrom("委外");
		order.setIsCreateInHouse(true);

		String inQuantity = getPara("inQuantity");

		if (!NumberUtils.isNumber(inQuantity)) {
			renderJson(Ret.fail().set("msg", "失败"));
			return;
		}
		model.setRealInQuantity(new BigDecimal(inQuantity));
		Ret ret = null;
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.save() && order.update();

			}
		});
		if (re) {
			ret = Ret.ok().set("msg", "生成入库单成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void toAddassist() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		setAttr("order", order);

		setAttr("action", "saveAssist");
		render("assist/edit.html");
	}

	public void toEditassist() {
		Integer id = getParaToInt("id");
		FyBusinessAssist model = FyBusinessAssist.dao.findById(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(model.getOrderId());
		setAttr("order", order);
		setAttr("model", model);
		setAttr("action", "updateAssist");
		render("assist/edit.html");
	}

	public void saveAssist() {
		FyBusinessAssist model = getBean(FyBusinessAssist.class, "model");
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.save();

			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "生成入库单成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void updateAssist() {
		FyBusinessAssist model = getBean(FyBusinessAssist.class, "model");
		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {
				return model.update();

			}
		});
		Ret ret = null;
		if (re) {
			ret = Ret.ok().set("msg", "生成入库单成功");
		} else {
			ret = Ret.fail().set("msg", "生成失败");
		}
		renderJson(ret);
	}

	public void assist() {
		String key = getPara("keyWord");
		Page<FyBusinessAssist> modelPage = null;
		keepPara("keyWord", "condition");
		String sql = "cate_tmp,plan_tmp,work_order_no,delivery_no,commodity_name,commodity_spec,map_no,quantity,unit_tmp,technology,machining_require,untaxed_cost,order_date,delivery_date,execu_status,urgent_status"
				+ "";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), 10, "select a.* ,s.name supplier," + sql,
					"from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id  left join fy_base_supplier s on s.id = a.assist_supplier_id order by id desc");
		} else {
			StringBuilder sb = new StringBuilder();
			String condition = getPara("condition");
			if ("name".equals(condition)) {
				sb.append(" and s.name like ").append("'%").append(key).append("%' ");
			} else {
				sb.append(" and o.work_order_no like ").append("'%").append(key).append("%' ");
			}
			modelPage = FyBusinessAssist.dao.paginate(getParaToInt("p", 1), 10, "select a.* ," + sql,
					"from  fy_business_assist a left join fy_business_order o on  o.id = a.order_id  left join fy_base_supplier s  on a.assist_supplier_id = s.id "
							+ sb.toString() + " order by id desc");
		}
		setAttr("modelPage", modelPage);
		render("assist.html");
	}

}
