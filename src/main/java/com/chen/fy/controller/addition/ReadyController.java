package com.chen.fy.controller.addition;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Customer;
import com.chen.fy.model.FyBusinessReady;
import com.chen.fy.model.Person;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.ReadyProductNoKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class ReadyController extends BaseController {
	final static String storage = " (IFNULL(in_quantity1,0) \r\n" + "+ IFNULL(in_quantity2,0)\r\n"
			+ " +IFNULL(in_quantity3,0) \r\n" + "+IFNULL(in_quantity4,0) \r\n" + "+IFNULL(in_quantity5,0)  )-\r\n"
			+ "(IFNULL(out_quantity1,0) + \r\n" + "IFNULL(out_quantity2,0) + \r\n" + "IFNULL(out_quantity3,0) + \r\n"
			+ "IFNULL(out_quantity4,0) + \r\n" + "IFNULL(out_quantity5,0)) storage";

	final static String sql = "select r.* , cate.`name` category_name,cu.name customer_name ,person.`name` planer_name,su.`name` supplier_name ,unit.`name` unit_name ,"
			+ storage;
	final static String table = " from fy_business_ready  r "
			+ "LEFT JOIN fy_base_customer cu on r.customer = cu.id  \n"
			+ "LEFT JOIN fy_base_category cate on cate.id= r.category_id  \n"
			+ "LEFT JOIN fy_base_person person on person.id=r.planer_id  \n"
			+ "left join fy_base_supplier su on su.id= r.supplier_id  \n"
			+ "LEFT JOIN fy_base_unit unit on unit.id = r.unit \n";

	public void index() {
		String key = getPara("keyWord");
		Page<FyBusinessReady> modelPage = null;
		keepPara("keyWord", "condition", "modelId");

		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessReady.dao.paginate(getParaToInt("p", 1), 10, sql,
					"from fy_business_ready  r " + "LEFT JOIN fy_base_customer cu on r.customer = cu.id  \n"
							+ "LEFT JOIN fy_base_category cate on cate.id= r.category_id  \n"
							+ "LEFT JOIN fy_base_person person on person.id=r.planer_id  \n"
							+ "left join fy_base_supplier su on su.id= r.supplier_id  \n"
							+ "LEFT JOIN fy_base_unit unit on unit.id = r.unit \n");
		} else {
			String condition = getPara("condition");

			StringBuilder sb = new StringBuilder();
			if ("cutomer".equals(condition)) {
				Integer modelId = getParaToInt("modelId");
				sb.append("r.customer = ").append(modelId).append(" ");
			} else if ("supplier".equals(condition)) {
				Integer modelId = getParaToInt("modelId");
				sb.append(" r.supplier_id = ").append(modelId).append(" ");
			} else if ("order_date".equals(condition)) {
				sb.append("r.").append(condition).append(" = '").append(key).append("' ");
			}

			else {
				sb.append("r.").append(condition).append(" like '%").append(key).append("%' ");
			}
			setAttr("append", "keyWord=" + key);
			modelPage = FyBusinessReady.dao.paginate(getParaToInt("p", 1), 10, sql,
					"from fy_business_ready  r " + "LEFT JOIN fy_base_customer cu on r.customer = cu.id  \n"
							+ "LEFT JOIN fy_base_category cate on cate.id= r.category_id  \n"
							+ "LEFT JOIN fy_base_person person on person.id=r.planer_id  \n"
							+ "left join fy_base_supplier su on su.id= r.supplier_id  \n"
							+ "LEFT JOIN fy_base_unit unit on unit.id = r.unit \n" + " where  " + sb.toString());
		}

		setAttr("modelPage", modelPage);
		render("list.html");

	}

	public void save() {
		FyBusinessReady model = getBean(FyBusinessReady.class, "model");
		if (StringUtils.isEmpty(model.getOrderNo())) {
			model.setOrderNo(ReadyProductNoKit.getNo());
		}
		boolean re = model.save();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "新建  信息成功");
		} else {
			ret = Ret.ok("msg", "新建失败");
		}
		renderJson(ret);
	}

	public void delete() {
		Integer id = getParaToInt("id");

		boolean re = FyBusinessReady.dao.deleteById(id);
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "删除 信息成功");
		} else {
			ret = Ret.ok("msg", "删除失败");
		}
		renderJson(ret);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		String find = sql + table + "where r.id=?";
		FyBusinessReady model = FyBusinessReady.dao.findFirst(find, id);

		setAttr("model", model);
		setAttr("action", "update");
		render("edit.html");
	}

	public void update() {
		FyBusinessReady model = getBean(FyBusinessReady.class, "model");
		boolean re = model.update();
		Ret ret = null;
		if (re) {
			ret = Ret.ok("msg", "修改    信息成功");
		} else {
			ret = Ret.ok("msg", "修改 失败");
		}

		renderJson(ret);
	}

	public void add() {
		FyBusinessReady model = new FyBusinessReady();
		model.setOrderNo(ReadyProductNoKit.getNo());
		setAttr("action", "save");
		setAttr("model", model);
		render("edit.html");
	}

	public void upload() {

		UploadFile ufile = getFile();
		int total = 0;
		if (ufile != null) {
			try {
				File file = ufile.getFile();

				PIOExcelUtil excel = new PIOExcelUtil(file, 0);
				// 类别 计划员 执行状态 紧急状态 订单日期 交货日期 工作订单号 送货单号 商品名称 商品规格 总图号 技术条件
				// 加工要求 数量 单位 未税单价 金额 税率 税额 含税金额
				List<Record> list = new ArrayList<Record>();
				int rows = excel.getRowNum() + 1;
				Date indate = new Date();
				for (int i = 1; i < rows; i++) {
					FyBusinessReady item = new FyBusinessReady();

					String customer = excel.getCellVal(i, 0);// 客户
					Integer customerId = getcustomer(customer);
					item.setCustomer(customerId);

					String catgory = excel.getCellVal(i, 1);// 类别
					Integer cateId = getcategory(catgory);
					item.setCategoryId(cateId);

					String planname = excel.getCellVal(i, 2);// 计划员
					Integer id = getPerson(planname);
					item.setPlanerId(id);

					String excustatu = excel.getCellVal(i, 3);// 执行状态
					item.setExecuStatus(excustatu);

					Date in_tiem = excel.getDateValue(i, 4);// 导入时间
					if (in_tiem == null) {
						item.setImportTime(indate);
					} else {
						item.setImportTime(in_tiem);
					}

					Date orderdate = excel.getDateValue(i, 5);// 订单日期
					item.setOrderDate(orderdate);

					Date DeliveryDate = excel.getDateValue(i, 6);// 交货日期
					item.setDeliveryDate(DeliveryDate);

					String workid = excel.getCellVal(i, 7);// 工作订单号
					if (StringUtils.isEmpty(workid)) {
						System.out.println(item);
						continue;
					}
					item.setWorkOrderNo(workid);

					String DeliveryId = excel.getCellVal(i, 8);// 送货单号
					item.setDeliveryNo(DeliveryId);

					String name = excel.getCellVal(i, 9);// 商品名称
					item.setCommodityName(name);

					String nspec = excel.getCellVal(i, 10);// 商品规格
					item.setCommoditySpec(nspec);

					String map = excel.getCellVal(i, 11);// 总图号
					// item.setMapNo(mapNo);
					item.setMapNo(map);

					String tck = excel.getCellVal(i, 12);// 技术条件
					item.setTechnology(tck);

					String maching = excel.getCellVal(i, 13);// 加工要求
					item.setMachiningRequire(maching);

					String quantity = excel.getCellVal(i, 14);// 数量
					item.setQuantity(NumberUtils.isNumber(quantity) ? new BigDecimal(quantity) : null);

					String unit = excel.getCellVal(i, 15);// 单位
					item.setUnit(unit);

					String untaxcost = excel.getCellVal(i, 16);// 未税单价
					item.setUntaxedCost(NumberUtils.isNumber(untaxcost) ? new BigDecimal(untaxcost) : null);

					String addStatus = excel.getCellVal(i, 17);// 补单状态
					item.setAddStatus(addStatus);

					String supplier_name = excel.getCellVal(i, 18);// 厂商
					Integer supplierId = getsupplier(supplier_name);
					item.setSupplierId(supplierId);

					String orderNo = excel.getCellVal(i, 19);// 订单编码
					item.setOrderNo(orderNo);

					String totalAccount = excel.getCellVal(i, 20);// 含税金额

					item.setPurchaseQuanity(NumberUtils.isNumber(totalAccount) ? new BigDecimal(totalAccount) : null);

					String supplierUnit = excel.getCellVal(i, 21);// 单位
					item.setPurchaseUnit(supplierUnit);

					String cost = excel.getCellVal(i, 22);// 未税单价
					item.setCost(NumberUtils.isNumber(cost) ? new BigDecimal(cost) : null);

					String amount = excel.getCellVal(i, 23);// 未税单价
					item.setAmount(NumberUtils.isNumber(amount) ? new BigDecimal(amount) : null);

					String discount = excel.getCellVal(i, 24);// 未税单价
					item.setDiscount(NumberUtils.isNumber(discount) ? new BigDecimal(discount) : null);

					String discountAmount = excel.getCellVal(i, 25);// 未税单价
					item.setDiscountAmount(
							NumberUtils.isNumber(discountAmount) ? new BigDecimal(discountAmount) : null);

					Date inDate = excel.getDateValue(i, 26);// 入库时间
					item.setInDate1(inDate);

					String inQuantity = excel.getCellVal(i, 27);// 入库数量
					item.setInQuantity1(NumberUtils.isNumber(inQuantity) ? new BigDecimal(inQuantity) : null);

					Date chcekTime = excel.getDateValue(i, 28);// 检测时间
					item.setCheckTime(chcekTime);

					String checkResult = excel.getCellVal(i, 29);// 入库时间
					item.setCheckResult(checkResult);

					Date outDate = excel.getDateValue(i, 30);// 出库时间时间
					item.setOutDate1(outDate);

					String outQuantity = excel.getCellVal(i, 31);// 出库数量
					item.setOutQuantity1(NumberUtils.isNumber(outQuantity) ? new BigDecimal(outQuantity) : null);

					String hangQuantity = excel.getCellVal(i, 32);// 挂账数量
					item.setHangQuantity(NumberUtils.isNumber(hangQuantity) ? new BigDecimal(hangQuantity) : null);

					String hangAccount = excel.getCellVal(i, 33);// 挂账金额
					item.setHangAccount(NumberUtils.isNumber(hangAccount) ? new BigDecimal(hangAccount) : null);

					Date hangDate = excel.getDateValue(i, 34);// 挂账日期
					item.setHangDate(hangDate);

					list.add(new Record().setColumns(item));

				}
				int[] re = Db.batchSave("fy_business_ready", list, 20);

				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ufile.getFile().deleteOnExit();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));
	}

	private Integer getPerson(String name) {
		Person model = Person.dao.findFirst("select id,name from fy_base_person where name =? ", name);
		if (model != null) {
			return model.getId();
		}
		return null;
	}

	private Integer getsupplier(String name) {

		Supplier model = Supplier.dao.findFirst("select id,name from fy_base_supplier where name =? ", name);
		if (model != null) {
			return model.getId();
		}
		return null;

	}

	private Integer getcustomer(String name) {
		Customer model = Customer.dao.findFirst("select id,name from fy_base_customer where name =? ", name);
		if (model != null) {
			return model.getId();
		}
		return null;
	}

	private Integer getcategory(String name) {
		Customer model = Customer.dao.findFirst("select id,name from fy_base_customer where name =? ", name);
		if (model != null) {
			return model.getId();
		}
		return null;
	}

	public void toDownload() {
		render("download.html");
	}

	public void download() {
		String date = getPara("date");
		String supplier = getPara("supplier_id");
		String customer = getPara("customer_id");
		StringBuilder sb = new StringBuilder();
		Calendar calendar = Calendar.getInstance();
		File targetfile = null;
		try {
			if (!StringUtils.isEmpty(date)) {
				calendar.setTime(DateUtils.parseDate(date, "yyyy-MM"));
				calendar.add(Calendar.MONTH, 1);
				String finsh = DateFormatUtils.format(calendar, "yyyy-MM-dd");
				sb.append("r.order_date > '").append(date).append("-00' and r.order_date < '").append(finsh)
						.append("' ");
			}
			if (!StringUtils.isEmpty(supplier)) {

				sb.append("r.supplier_id = ").append(supplier).append(" ");
			}
			if (!StringUtils.isEmpty(customer)) {
				sb.append("r.customer= ").append(customer).append(" ");
			}

			String sql = this.sql + table;
			if (sb.length() > 0) {
				sql = sql + " where  " + sb.toString();
			}
			List<FyBusinessReady> models = FyBusinessReady.dao.find(sql);
			if (models.size() == 0) {
				renderText("没有符合条件的备货记录");
			}

			File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
			if (!parentfile.exists()) {
				parentfile.mkdirs();
			}
			String filename = "";
			if (date != null) {
				filename = date;
			}
			targetfile = new File(parentfile, "备货单_" + filename + ".xlsx");

			// 读取模板

			filename = this.getClass().getClassLoader().getResource("templet/download/addready.xlsx").getFile();
			FileUtils.copyFile(new File(filename), targetfile);

			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);
			int row = 1;
			for (FyBusinessReady item : models) {

				String customer_name = item.getStr("customer_name"); // 客户
				excel.setCellVal(row, 0, customer_name);

				String category_name = item.getStr("category_name"); // 类别
				excel.setCellVal(row, 1, category_name);

				String planer_name = item.getStr("planer_name"); // 计划员
				excel.setCellVal(row, 2, planer_name);

				String execu_status = item.getStr("execu_status"); // 执行状态
				excel.setCellVal(row, 3, execu_status);

				Date import_time = item.getDate("import_time"); // 导入时间
				excel.setCellVal(row, 4, import_time);

				Date order_date = item.getDate("order_date"); // 订单日期
				excel.setCellVal(row, 5, order_date);

				Date delivery_date = item.getDate("delivery_date"); // 交货日期
				excel.setCellVal(row, 6, delivery_date);

				String work_order_no = item.getStr("work_order_no"); // 工作订单号
				excel.setCellVal(row, 7, work_order_no);

				String delivery_no = item.getStr("delivery_no"); // 送货单号
				excel.setCellVal(row, 8, delivery_no);

				String commodity_name = item.getStr("commodity_name"); // 商品名称
				excel.setCellVal(row, 9, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 10, commodity_spec);

				String map_no = item.getStr("map_no"); // 总图号
				excel.setCellVal(row, 11, map_no);

				String technology = item.getStr("technology"); // 加工条件
				excel.setCellVal(row, 12, technology);

				String machining_require = item.getStr("machining_require"); // 加工要求
				excel.setCellVal(row, 13, machining_require);

				Double quantity = item.getDouble("quantity"); // 数量
				excel.setCellVal(row, 14, quantity);

				String unit = item.getStr("unit_name"); // 单位
				excel.setCellVal(row, 15, unit);

				Double untaxed_cost = item.getDouble("untaxed_cost"); // 未税金额
				excel.setCellVal(row, 16, untaxed_cost);

				String add_status = item.getStr("add_status"); // 补单状态
				excel.setCellVal(row, 17, add_status);

				String supplier_name = item.getStr("supplier_name"); // 厂商
				excel.setCellVal(row, 18, supplier_name);

				String order_no = item.getStr("order_no"); // 订单编码
				excel.setCellVal(row, 19, order_no);

				Double purchase_quanity = item.getDouble("purchase_quanity"); // 采购数量
				excel.setCellVal(row, 20, purchase_quanity);

				String purchase_unit = item.getStr("purchase_unit"); // 采购单位
				excel.setCellVal(row, 21, purchase_unit);

				Double cost = item.getDouble("cost"); // 单价
				excel.setCellVal(row, 22, cost);

				Double amount = item.getDouble("amount"); // 金额
				excel.setCellVal(row, 23, amount);

				Double discount = item.getDouble("discount"); // 折扣
				excel.setCellVal(row, 24, discount);

				Double discount_amount = item.getDouble("discount_amount"); // 折后金额
				excel.setCellVal(row, 25, discount_amount);

				Date in_date = item.getDate("in_date"); // 入库时间
				excel.setCellVal(row, 26, in_date);

				Double in_quantity = item.getDouble("in_quantity"); // 入库数量
				excel.setCellVal(row, 27, in_quantity);

				Date check_time = item.getDate("check_time"); // 检测时间
				excel.setCellVal(row, 28, check_time);

				String check_result = item.getStr("check_result"); // 检测结果
				excel.setCellVal(row, 29, check_result);

				Date out_date = item.getDate("out_date"); // 出库时间
				excel.setCellVal(row, 30, out_date);

				Double out_quantity = item.getDouble("out_quantity"); // 出库数量
				excel.setCellVal(row, 31, out_quantity);

				Double hang_quantity = item.getDouble("hang_quantity"); // 挂账数量
				excel.setCellVal(row, 32, hang_quantity);

				Double hang_account = item.getDouble("hang_account"); // 挂账金额
				excel.setCellVal(row, 33, hang_account);

				Date hang_date = item.getDate("hang_date"); // 挂账时间
				excel.setCellVal(row, 34, hang_date);

				row++;

			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			renderText(e.getMessage());
		}

		// System.out.println("is file " + file.isFile() + " " + file.getAbsolutePath()
		// + " " + file.getName());

		renderFile(targetfile);
	}
}
