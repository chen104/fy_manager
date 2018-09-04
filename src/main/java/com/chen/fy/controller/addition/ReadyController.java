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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.model.Customer;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessReady;
import com.chen.fy.model.Person;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.ReadyProductNoKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class ReadyController extends BaseController {
	ReadyService service = ReadyService.me;
	private static final Logger logger = LogManager.getLogger(ReadyController.class);
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
		// boolean isMultipart =
		// ServletFileUpload.isMultipartContent(this.getRequest());
		// if (isMultipart) {
		// renderJson(Ret.fail().set("msg", "没有上传文件"));
		// return;
		// }
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

					item.setWorkOrderNo1(workid);

					String DeliveryId = excel.getCellVal(i, 8);// 送货单号
					item.setDeliveryNo1(DeliveryId);

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

					String totalAccount = excel.getCellVal(i, 20);// 数量

					item.setPurchaseQuanity(NumberUtils.isNumber(totalAccount) ? new BigDecimal(totalAccount) : null);

					String supplierUnit = excel.getCellVal(i, 21);// 单位
					item.setPurchaseUnit(supplierUnit);

					String cost = excel.getCellVal(i, 22);// 未税单价
					item.setCost(NumberUtils.isNumber(cost) ? new BigDecimal(cost) : null);

					String amount = excel.getCellVal(i, 23);// 金额
					item.setAmount(NumberUtils.isNumber(amount) ? new BigDecimal(amount) : null);

					String discount = excel.getCellVal(i, 24);// 折扣
					item.setDiscount(NumberUtils.isNumber(discount) ? new BigDecimal(discount) : null);

					String discountAmount = excel.getCellVal(i, 25);// 折后金额
					item.setDiscountAmount(
							NumberUtils.isNumber(discountAmount) ? new BigDecimal(discountAmount) : null);

					String checkResult = excel.getCellVal(i, 26);// 检测结果
					item.setCheckResult(checkResult);

					Date chcekTime = excel.getDateValue(i, 27);// 检测时间
					item.setCheckTime(chcekTime);

					Date inDate = excel.getDateValue(i, 28);// 入库时间1
					item.setInDate1(inDate);

					String inQuantity = excel.getCellVal(i, 29);// 入库数量1
					item.setInQuantity1(NumberUtils.isNumber(inQuantity) ? new BigDecimal(inQuantity) : null);

					Date inDate2 = excel.getDateValue(i, 30);// 入库时间2
					item.setInDate2(inDate2);

					String inQuantity2 = excel.getCellVal(i, 31);// 入库数量2
					item.setInQuantity2(NumberUtils.isNumber(inQuantity2) ? new BigDecimal(inQuantity2) : null);

					Date inDate3 = excel.getDateValue(i, 32);// 入库时间3
					item.setInDate3(inDate3);

					String inQuantity3 = excel.getCellVal(i, 33);// 入库数量3
					item.setInQuantity3(NumberUtils.isNumber(inQuantity3) ? new BigDecimal(inQuantity3) : null);

					Date inDate4 = excel.getDateValue(i, 34);// 入库时间4
					item.setInDate4(inDate4);

					String inQuantity4 = excel.getCellVal(i, 35);// 入库数量4
					item.setInQuantity4(NumberUtils.isNumber(inQuantity4) ? new BigDecimal(inQuantity4) : null);

					Date inDate5 = excel.getDateValue(i, 36);// 入库时间5
					item.setInDate5(inDate5);

					String inQuantity5 = excel.getCellVal(i, 37);// 入库数量5
					item.setInQuantity5(NumberUtils.isNumber(inQuantity5) ? new BigDecimal(inQuantity5) : null);

					Date outDate1 = excel.getDateValue(i, 38);// 出库时间时间1
					item.setOutDate1(outDate1);

					String outQuantity1 = excel.getCellVal(i, 39);// 出库数量1
					item.setOutQuantity1(NumberUtils.isNumber(outQuantity1) ? new BigDecimal(outQuantity1) : null);

					Date outDate2 = excel.getDateValue(i, 40);// 出库时间时间2
					item.setOutDate1(outDate2);

					String outQuantity2 = excel.getCellVal(i, 41);// 出库数量2
					item.setOutQuantity2(NumberUtils.isNumber(outQuantity2) ? new BigDecimal(outQuantity2) : null);

					Date outDate3 = excel.getDateValue(i, 42);// 出库时间时间3
					item.setOutDate3(outDate3);

					String outQuantity3 = excel.getCellVal(i, 43);// 出库数量3
					item.setOutQuantity3(NumberUtils.isNumber(outQuantity3) ? new BigDecimal(outQuantity3) : null);

					Date outDate4 = excel.getDateValue(i, 44);// 出库时间时间4
					item.setOutDate4(outDate4);

					String outQuantity4 = excel.getCellVal(i, 45);// 出库数量4
					item.setOutQuantity4(NumberUtils.isNumber(outQuantity4) ? new BigDecimal(outQuantity4) : null);

					Date outDate5 = excel.getDateValue(i, 46);// 出库时间时间5
					item.setOutDate5(outDate5);

					String outQuantity5 = excel.getCellVal(i, 47);// 出库数量5
					item.setOutQuantity5(NumberUtils.isNumber(outQuantity5) ? new BigDecimal(outQuantity5) : null);

					String hangQuantity1 = excel.getCellVal(i, 48);// 挂账数量
					item.setHangQuantity1(NumberUtils.isNumber(hangQuantity1) ? new BigDecimal(hangQuantity1) : null);

					String hangAccount1 = excel.getCellVal(i, 49);// 挂账金额
					item.setHangAccount1(NumberUtils.isNumber(hangAccount1) ? new BigDecimal(hangAccount1) : null);

					Date hangDate1 = excel.getDateValue(i, 50);// 挂账日期
					item.setHangDate1(hangDate1);

					String hangQuantity2 = excel.getCellVal(i, 51);// 挂账数量2
					item.setHangQuantity2(NumberUtils.isNumber(hangQuantity2) ? new BigDecimal(hangQuantity2) : null);

					String hangAccount2 = excel.getCellVal(i, 52);// 挂账金额2
					item.setHangAccount2(NumberUtils.isNumber(hangAccount2) ? new BigDecimal(hangAccount2) : null);

					Date hangDate2 = excel.getDateValue(i, 53);// 挂账日期2
					item.setHangDate1(hangDate2);

					String hangQuantity3 = excel.getCellVal(i, 54);// 挂账数量3
					item.setHangQuantity3(NumberUtils.isNumber(hangQuantity3) ? new BigDecimal(hangQuantity3) : null);

					String hangAccount3 = excel.getCellVal(i, 55);// 挂账金额3
					item.setHangAccount3(NumberUtils.isNumber(hangAccount3) ? new BigDecimal(hangAccount3) : null);

					Date hangDate3 = excel.getDateValue(i, 56);// 挂账日期3
					item.setHangDate1(hangDate3);

					String hangQuantity4 = excel.getCellVal(i, 57);// 挂账数量4
					item.setHangQuantity4(NumberUtils.isNumber(hangQuantity4) ? new BigDecimal(hangQuantity4) : null);

					String hangAccount4 = excel.getCellVal(i, 58);// 挂账金额4
					item.setHangAccount4(NumberUtils.isNumber(hangAccount4) ? new BigDecimal(hangAccount4) : null);

					Date hangDate4 = excel.getDateValue(i, 59);// 挂账日期4
					item.setHangDate1(hangDate4);

					String hangQuantity5 = excel.getCellVal(i, 60);// 挂账数量5
					item.setHangQuantity5(NumberUtils.isNumber(hangQuantity5) ? new BigDecimal(hangQuantity5) : null);

					String hangAccount5 = excel.getCellVal(i, 61);// 挂账金额5
					item.setHangAccount5(NumberUtils.isNumber(hangAccount5) ? new BigDecimal(hangAccount5) : null);

					Date hangDate5 = excel.getDateValue(i, 62);// 挂账日期5
					item.setHangDate1(hangDate5);

					list.add(new Record().setColumns(item));

				}
				int[] re = Db.batchSave("fy_business_ready", list, 20);

				for (int i = 0; i < re.length; i++) {
					total = total + re[i];
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		ufile.getFile().delete();
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

				String check_result = item.getStr("check_result"); // 检测结果
				excel.setCellVal(row, 26, check_result);

				Date check_time = item.getDate("check_time"); // 检测时间
				excel.setCellVal(row, 27, check_time);

				Date in_date = item.getDate("in_date1"); // 入库时间
				excel.setCellVal(row, 28, in_date);

				Double in_quantity = item.getDouble("in_quantity1"); // 入库数量
				excel.setCellVal(row, 29, in_quantity);

				Date in_date2 = item.getDate("in_date2"); // 入库时间2
				excel.setCellVal(row, 30, in_date2);

				Double in_quantity2 = item.getDouble("in_quantity2"); // 入库数量2
				excel.setCellVal(row, 31, in_quantity2);

				Date in_date3 = item.getDate("in_date3"); // 入库时间3
				excel.setCellVal(row, 32, in_date3);

				Double in_quantity3 = item.getDouble("in_quantity3"); // 入库数量3
				excel.setCellVal(row, 33, in_quantity3);

				Date in_date4 = item.getDate("in_date4"); // 入库时间4
				excel.setCellVal(row, 34, in_date4);

				Double in_quantity4 = item.getDouble("in_quantity4"); // 入库数量4
				excel.setCellVal(row, 35, in_quantity4);

				Date in_date5 = item.getDate("in_date5"); // 入库时间5
				excel.setCellVal(row, 34, in_date5);

				Double in_quantity5 = item.getDouble("in_quantity5"); // 入库数量5
				excel.setCellVal(row, 35, in_quantity5);

				Date out_date = item.getDate("out_date1"); // 出库时间
				excel.setCellVal(row, 36, out_date);

				Double out_quantity = item.getDouble("out_quantity1"); // 出库数量
				excel.setCellVal(row, 37, out_quantity);

				Date out_date2 = item.getDate("out_date2"); // 出库时间2
				excel.setCellVal(row, 38, out_date2);

				Double out_quantity2 = item.getDouble("out_quantity2"); // 出库数量2
				excel.setCellVal(row, 39, out_quantity2);

				Date out_date3 = item.getDate("out_date3"); // 出库时间3
				excel.setCellVal(row, 40, out_date3);

				Double out_quantity3 = item.getDouble("out_quantity3"); // 出库数量3
				excel.setCellVal(row, 41, out_quantity3);

				Date out_date4 = item.getDate("out_date4"); // 出库时间4
				excel.setCellVal(row, 42, out_date4);

				Double out_quantity4 = item.getDouble("out_quantity4"); // 出库数量4
				excel.setCellVal(row, 43, out_quantity4);

				Date out_date5 = item.getDate("out_date5"); // 出库时间4
				excel.setCellVal(row, 44, out_date5);

				Double out_quantity5 = item.getDouble("out_quantity5"); // 出库数量4
				excel.setCellVal(row, 45, out_quantity5);

				Double hang_quantity = item.getDouble("hang_quantity1"); // 挂账数量
				excel.setCellVal(row, 46, hang_quantity);

				Double hang_account = item.getDouble("hang_account1"); // 挂账金额
				excel.setCellVal(row, 47, hang_account);

				Date hang_date = item.getDate("hang_date1"); // 挂账时间
				excel.setCellVal(row, 48, hang_date);

				Double hang_quantity2 = item.getDouble("hang_quantity2"); // 挂账数量2
				excel.setCellVal(row, 49, hang_quantity2);

				Double hang_account2 = item.getDouble("hang_account2"); // 挂账金额2
				excel.setCellVal(row, 50, hang_account2);

				Date hang_date2 = item.getDate("hang_date2"); // 挂账时间2
				excel.setCellVal(row, 51, hang_date2);

				Double hang_quantity3 = item.getDouble("hang_quantity3"); // 挂账数量3
				excel.setCellVal(row, 52, hang_quantity3);

				Double hang_account3 = item.getDouble("hang_account3"); // 挂账金额3
				excel.setCellVal(row, 53, hang_account3);

				Date hang_date3 = item.getDate("hang_date3"); // 挂账时间3
				excel.setCellVal(row, 54, hang_date3);

				Double hang_quantity4 = item.getDouble("hang_quantity4"); // 挂账数量4
				excel.setCellVal(row, 55, hang_quantity4);

				Double hang_account4 = item.getDouble("hang_account4"); // 挂账金额4
				excel.setCellVal(row, 56, hang_account4);

				Date hang_date4 = item.getDate("hang_date4"); // 挂账时间4
				excel.setCellVal(row, 57, hang_date4);

				Double hang_quantity5 = item.getDouble("hang_quantity5"); // 挂账数量4
				excel.setCellVal(row, 58, hang_quantity5);

				Double hang_account5 = item.getDouble("hang_account5"); // 挂账金额4
				excel.setCellVal(row, 59, hang_account5);

				Date hang_date5 = item.getDate("hang_date5"); // 挂账时间4
				excel.setCellVal(row, 60, hang_date5);
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

	/**
	 * 备货接收
	 */
	public void receiveReady() {
		String key = getPara("keyWord");
		keepPara("condition", "keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10,
					"select o.*,r.order_no,r.add_quantity1,r.add_quantity2,r.add_quantity3 ,r.quantity ready_quantity,add_status",
					"from fy_business_order o LEFT JOIN fy_business_ready r\r\n"
							+ "on o.ready_id= r.id where  dis_to = 3 order by  id desc");
		} else {
			String condition = getPara("condition");
			StringBuilder sb = new StringBuilder();
			sb.append(" and ").append(condition).append(" like '").append(key).append("' ");
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10,
					"select o.*,r.order_no,r.add_quantity1,r.add_quantity2,r.add_quantity3,r.quantity ready_quantity ,add_status",
					"from fy_business_order o LEFT JOIN fy_business_ready r\r\n"
							+ "on o.ready_id= r.id where  dis_to = 3 " + sb.toString() + " order by  id desc");
		}
		setAttr("modelPage", modelPage);
		render("reveiveDistribute.html");
	}

	public void searchReady() {
		render("searchReady.html");
	}

	/**
	 * 
	 */
	public void searchReadyJson() {
		Page<FyBusinessReady> modelPage = null;
		Integer order_id = getParaToInt("order_id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id);
		if (order == null) {
			modelPage = FyBusinessReady.dao.paginate(getParaToInt("p", 1), 10, "select * ",
					"from fy_business_ready order by id desc");
			renderJson(modelPage);
			return;
		}
		String commodityName = order.getCommodityName();
		String commoditySpec = order.getCommoditySpec();

		modelPage = FyBusinessReady.dao.paginate(getParaToInt("p", 1), 10, "select r.*,c.name customer_name ",
				"	 from fy_business_ready  r LEFT JOIN fy_base_customer c 		on r.customer=c.id  where commodity_name = ? and commodity_spec=? order by id desc",
				commodityName, commoditySpec);
		// String name = modelPage.getList().get(0).getStr("customer_name");
		// System.out.println(JsonKit.toJson(modelPage));
		renderJson(JsonKit.toJson(modelPage));
	}

	public void addOrderToReady() {
		Integer order_id = getParaToInt("order_id");
		Integer ready_id = getParaToInt("ready_id");

		Ret ret = service.selectReady(order_id, ready_id);
		renderJson(ret);
	}
}
