package com.chen.fy.controller.addition;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.model.Category;
import com.chen.fy.model.Customer;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessReady;
import com.chen.fy.model.Person;
import com.chen.fy.model.Supplier;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ReadyService {
	public final static ReadyService me = new ReadyService();
	public final static FyBusinessReady dao = FyBusinessReady.dao;
	private static final Logger logger = LogManager.getLogger(ReadyService.class);

	/**
	 * 备货
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @param key
	 * @return
	 */
	public Page<Record> findPage(Integer pageIndex, Integer pageSize, String condition, String key) {
		Page<Record> modelPage = null;
		String select = "select * ,add_quantity";
		String from = "  from fy_business_order o  left join ready_view on o.id = ready_order_id ";
		String where = " where  execu_status ='备货' "; // customer_no ='备货'
		String orderby = " order by  id desc ";
		if (StringUtils.isEmpty(key)) {
			modelPage = Db.paginate(pageIndex, pageSize, select, from + where + orderby);
		}
		return modelPage;
	}

	/**
	 *补单
	 * @param order_id
	 * @param ready_id
	 * @return
	 */
	public Ret selectReady(Integer order_id, Integer ready_id) {
		try {
			FyBusinessOrder order = FyBusinessOrder.dao.findById(order_id);

			FyBusinessReady model = dao.findById(ready_id);

			if (order == null || model == null) {
				return Ret.fail().set("msg", "备货失败，请刷新后在操作");
			}

			Ret ret = null;

			if (order.getReadyId() != null) {
				return Ret.fail().set("msg", "备货失败，订单已经备货了");
			}
			// 更新工作订单号，补单状态
			BigDecimal quantity1 = model.getAddQuantity1();
			String workNo1 = model.getWorkOrderNo1();

			BigDecimal quantity2 = model.getAddQuantity2();
			String workNo2 = model.getWorkOrderNo2();

			BigDecimal quantity3 = model.getAddQuantity3();
			String workNo3 = model.getWorkOrderNo3();
			// 认为是第一次补单
			if (quantity1.doubleValue() == 0 && StringUtils.isEmpty(workNo1)) {
				model.setWorkOrderNo1(order.getWorkOrderNo());
				model.setDeliveryNo1(order.getDeliveryNo());
				model.setExecuStatus("备货");
				model.setAddStatus("部分补单");
			} else if (quantity2.doubleValue() == 0 && StringUtils.isEmpty(workNo2)) {
				model.setWorkOrderNo2(order.getWorkOrderNo());
				// model.setAddQuantity2(order.getQuantity());
				model.setDeliveryNo2(order.getDeliveryNo());
			} else if (quantity3.doubleValue() == 0 && StringUtils.isEmpty(workNo3)) {
				model.setWorkOrderNo3(order.getWorkOrderNo());
				// model.setAddQuantity3(order.getQuantity());
				model.setDeliveryNo3(order.getDeliveryNo());
			} else {
				ret = Ret.fail().set("msg", "已补单三次，不能再补单");
				return ret;
			}
			if (model.getQuantity() != null) {
				if ((model.getAddQuantity1().doubleValue() + model.getAddQuantity2().doubleValue()
						+ model.getAddQuantity3().doubleValue()) == model.getQuantity().doubleValue()) {
					model.setAddStatus("已补单");
				}
			}

			/**
			 * 数量
			 */
			if (order.getQuantity() != null) {
				Double oquantity = order.getQuantity().doubleValue();
				Double quantity = 0d;
				if (model.getQuantity() != null) {
					quantity = model.getQuantity().doubleValue();
				}
				model.setQuantity(new BigDecimal(quantity + oquantity));
			}

			String cate = order.getCateTmp();
			if (StringUtils.isNotEmpty(cate)) {
				model.setCategoryId(getCategory(cate));
			}
			String planer = order.getPlanTmp();
			if (StringUtils.isNotEmpty(planer)) {
				model.setPlanerId(getPlaner(planer));
			}

			model.setExecuStatus(order.getExecuStatus());
			// model.setUrgentStatus(order.getUrgentStatus());
			model.setOrderDate(order.getOrderDate());
			model.setDeliveryDate(order.getDeliveryDate());

			order.setReadyId(model.getId());
			boolean re = Db.tx(new IAtom() {

				@Override
				public boolean run() throws SQLException {

					return order.update() && model.update();
				}
			});
			if (re) {
				ret = Ret.ok().set("msg", "补单完成");
			} else {
				ret = Ret.ok().set("msg", "失败");
			}
			return ret;
		} catch (Exception e) {
			logger.warn(e.getMessage());
			e.printStackTrace();
			return Ret.ok().set("msg", "补单失败 ，请查看日志");
		}
	}


	public Integer getCategory(String cateName) {
		Category model = Category.dao.findFirst("select * from fy_base_category where  name =? ", cateName.trim());
		if (model == null) {
			model = new Category();
			model.setName(cateName);
			model.save();
		}
		return model.getId();
	}

	public Integer getPlaner(String name) {
		Person model = Person.dao.findFirst("select * from fy_base_person where  name =? ", name.trim());
		if (model == null) {
			model = new Person();
			model.setName(name);
			model.save();
		}
		return model.getId();
	}

	/**
	 * 上传备货
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public int uploadFile(File file) throws Exception {
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
			item.setDiscountAmount(NumberUtils.isNumber(discountAmount) ? new BigDecimal(discountAmount) : null);

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
		int total = 0;
		for (int i = 0; i < re.length; i++) {
			total = total + re[i];
		}
		return total;
	}

	public File downloadFile(String[] ids) throws Exception {

		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile,
				"备货单_" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd") + ".xlsx");

		// 读取模板

		String filename = this.getClass().getClassLoader().getResource("templet/download/addready.xlsx").getFile();
		FileUtils.copyFile(new File(filename), targetfile);

		PIOExcelUtil excel = null;
		excel = new PIOExcelUtil(targetfile, 0);
		int row = 1;
		List<Record> models = Db.find("");
		for (Record item : models) {

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

		return targetfile;
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
}
