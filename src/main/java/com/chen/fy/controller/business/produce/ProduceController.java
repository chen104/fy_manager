package com.chen.fy.controller.business.produce;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.chen.fy.controller.BaseController;
import com.chen.fy.controller.business.service.ProductService;
import com.chen.fy.model.FyBusinessDistribute;
import com.chen.fy.model.FyBusinessInWarehouse;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessProduce;
import com.jfinal.aop.Before;
import com.jfinal.club.common.kit.PIOExcelUtil;
import com.jfinal.club.common.kit.SqlKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

/**

 * @author Administrator
 *
 */
public class ProduceController extends BaseController {
	ProductService productService = ProductService.me;

	public void test() {
		renderText(this.getClass().getSimpleName() + "这是一个测试方法");
	}

	public void index() {

		Page<FyBusinessProduce> modelPage = null;

		modelPage = FyBusinessProduce.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order  where Is_Distribute = 1 and dis_to = 0 and is_create_plan = 0 order by id desc");

		setAttr("modelPage", modelPage);
		render("produce.html");
	}

	/**
	 * 
	 */
	@Before(Tx.class)
	public void receive() {
		Integer id = getParaToInt("id", 1);
		FyBusinessProduce model = FyBusinessProduce.dao.findById(id);
		// model.setIsReceive(true);
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

	public void sumprod() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		setAttr("keyWord", key);

		modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, "select * ",
				"from  fy_business_order  where Is_Distribute = 1 and dis_to = 0   and has_in_quantity <> quantity   order by id desc");
		setAttr("modelPage", modelPage);
		render("sumProduce.html");
	}

	/**
	 * 生产汇总表生成生产计划表
	 */

	public void toPlanPro() {
		Integer id = getParaToInt("id");
		Date starTime = getParaToDate("beginTime");
		Date finshTime = getParaToDate("finishTime");
		String remark = getPara("remark");
		FyBusinessOrder model = FyBusinessOrder.dao.findById(id);

		model.setPlanTime(starTime);
		model.setPlanFinshTime(finshTime);
		model.setIsCreatePlan(true);
		model.setPlanRemark(remark);// 备注
		model.setHandleStatus("已进入生产计划");

		Ret ret = null;

		boolean re = Db.tx(new IAtom() {
			public boolean run() throws SQLException {

				return model.update();

			}
		});
		if (re) {
			ret = Ret.ok("msg", "接收成功");

		} else {
			ret = Ret.ok("msg", "接收失败");
		}
		renderJson(ret);

	}

	public void planPro() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}
		Page<FyBusinessOrder> modelPage = null;

		String condition = getPara("condition");

		keepPara("condition", "keyWord", "order_date");
		Integer pageSize = getParaToInt("pageSize", 10);
		setAttr("pageSize", pageSize);
		setAttr("append", "&pageSize=" + pageSize);
		setAttr("keyWord", key);
		String select = "select o.* ,f.originalFileName filename,f.id fileId ";
		String from = " from  fy_business_order o  LEFT JOIN  fy_base_fyfile  f on o.draw = f.id";
		String where = " where  has_in_quantity <> quantity and Is_Distribute = 1 and dis_to = 0 ";
		if (StringUtils.isEmpty(key)) {
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					from + where + "  order by id desc");

		} else {

			StringBuilder sb = new StringBuilder();
			sb.append(" and ").append(condition).append(" like '").append(key).append("' ");
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), pageSize, select,
					from + where + sb.toString() + " order by id desc");

		}
		setAttr("modelPage", modelPage);
		render("producePlan.html");
	}

	public void oneSumPro() {
		String key = getPara("keyWord");
		Page<FyBusinessOrder> modelPage = null;
		keepPara("keyWord", "condition");
		String select = "select * ,file.id  fileId ,file.originalFileName filename";
		String from = "from  fy_business_order o LEFT JOIN" + " fy_base_fyfile file on o.draw = file.id ";
		if (StringUtils.isEmpty(key)) {
			String where = "where  is_create_plan = 1  order by o.id desc";
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, select, from + where);
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(" and %s like  ", getPara("condition"), key));
			sb.append("'%").append(key).append("%' ");
			String where = String.format(" where  is_create_plan = 1 %s order by o.id desc", sb.toString());
			modelPage = FyBusinessOrder.dao.paginate(getParaToInt("p", 1), 10, select, from + where);
		}
		setAttr("modelPage", modelPage);
		render("oneSumProduce.html");
	}

	/**
	 * 生产入库操作入库操作
	 */
	public void toInWareHouse() {
		Integer id = getParaToInt("id");
		FyBusinessInWarehouse model = new FyBusinessInWarehouse();
		model.setOrderId(id);
		model.setParentId(id);
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		if (order == null) {
			renderJson(Ret.fail().set("msg", "订单是否已被删除，删除的订单不能入库"));
		}
		order.setInWarehouseTime(new Date());
		model.setInTime(order.getInWarehouseTime());
		model.setInFrom("本部");
		order.setIsCreateInHouse(true);
		order.setPlanFinshTime(null);
		order.setPlanTime(null);

		String inQuantity = getPara("inQuantity");

		if (!NumberUtils.isNumber(inQuantity)) {// 设置入库数量
			renderJson(Ret.fail().set("msg", "失败，输入的入库数量不是数字"));
			return;
		}
		// if (Double.valueOf(inQuantity) >
		// order.getQuantity().subtract(order.getHasInQuantity()).doubleValue()) {
		// renderJson(Ret.fail().set("msg", "失败，入库数量大于订单数量"));
		// return;
		// }
		// model.setRealInQuantity(new BigDecimal(inQuantity));// 入库单设置已入库实际数量
		// order.setHasInQuantity(order.getHasInQuantity().add(model.getRealInQuantity()));//
		// 订单设置已经入库数量

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

	/**
	 * 修改工作计划时间与备注
	 */
	public void editProducePlan() {
		Integer id = getParaToInt("id");
		FyBusinessOrder order = FyBusinessOrder.dao.findById(id);
		setAttr("order", order);
		setAttr("model", order);
		setAttr("action", "updateProducePlan");
		render("plan/edit.html");
	}

	/**
	 * 更新工作时间与备注
	 */
	public void updateProducePlan() {
		FyBusinessOrder order = getBean(FyBusinessOrder.class, "model");
		boolean re = order.update();
		if (re) {
			renderJson(Ret.ok().set("msg", "更新计划成功"));
		} else {
			renderJson(Ret.ok().set("msg", "更新计划失败"));
		}

	}

	public void toDownload() {
		setAttr("now", new Date());
		render("downloadPlan.html");
	}

	/**
	 * 下载生产一览表
	 * @throws ParseException 
	 */
	public void downloadOneSum() throws ParseException {
		String date = getPara("date");
		if (StringUtils.isEmpty(date)) {
			renderText("没有选择日期");
		}
		Integer id = getParaToInt("customer");
		Date start = DateUtils.parseDate(date, "yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		calendar.add(Calendar.MONTH, 1);
		String ptime = " and order_date >'" + DateFormatUtils.format(start, "yyyy-MM-00") + "' and order_date <'"
				+ DateFormatUtils.format(calendar, "yyyy-MM-00") + "'";

		String sql = "SELECT o.*,c.name customer_name from fy_business_order o "
				+ "LEFT JOIN  fy_base_customer c on o.customer=c.id  where   is_create_plan = 1 " + ptime
				+ " order by id desc";
		List<FyBusinessOrder> list = FyBusinessOrder.dao.find(sql);
		File parentfile = new File(PathKit.getWebRootPath() + File.separator + "download/excel");
		if (!parentfile.exists()) {
			parentfile.mkdirs();
		}
		File targetfile = new File(parentfile, "生产一览表" + date + ".xlsx");
		try {

			// 读取模板
			String filename = this.getClass().getClassLoader().getResource("templet/download/planOneSum.xlsx")
					.getFile();
			FileUtils.copyFile(new File(filename), targetfile);
			PIOExcelUtil excel = null;
			excel = new PIOExcelUtil(targetfile, 0);

			int row = 1;

			for (FyBusinessOrder item : list) {

				String customer_name = item.getStr("customer_name");// 客户
				// cate_tmp = item.getStr("customer");// 客户
				// cate_tmp = CacheKit.get(CacheNameConstant.base_customer_id2key, cate_tmp);

				excel.setCellVal(row, 0, customer_name);

				String plan_tmp = item.getStr("cate_tmp");// 类别
				excel.setCellVal(row, 1, plan_tmp);

				String plan_name = item.getStr("plan_tmp");// 计划员 plan_nam
				excel.setCellVal(row, 2, plan_name);

				String execu_status = item.getStr("execu_status");// 执行状态
				excel.setCellVal(row, 3, execu_status);

				String customer_no = item.getStr("customer_no");// 紧急状态
				excel.setCellVal(row, 4, customer_no);

				Date order_date = item.getDate("order_date");// 订单日期
				excel.setCellVal(row, 5, order_date);
				excel.setDateCellStyle(row, 5);

				Date delivery_date = item.getDate("delivery_date");// 交货日期
				excel.setCellVal(row, 6, delivery_date);
				excel.setDateCellStyle(row, 6);

				String work_order_no = item.getStr("work_order_no");// 工作订单号
				excel.setCellVal(row, 7, work_order_no);

				String delivery_no = item.getStr("delivery_no");// 送货单号
				excel.setCellVal(row, 8, delivery_no);

				String commodity_name = item.getStr("commodity_name");// 商品名称
				excel.setCellVal(row, 9, commodity_name);

				String commodity_spec = item.getStr("commodity_spec");// 商品规格
				excel.setCellVal(row, 10, commodity_spec);

				String map_no = item.getStr("map_no");// 总图号
				excel.setCellVal(row, 11, map_no);

				String technology = item.getStr("technology");// 技术条件
				excel.setCellVal(row, 12, technology);

				String machining_require = item.getStr("machining_require");// 加工要求
				excel.setCellVal(row, 13, machining_require);

				Double quantity = item.getDouble("quantity");// 数量
				excel.setCellVal(row, 14, quantity);

				String unit_tmp = item.getStr("unit_tmp");// 单位
				excel.setCellVal(row, 15, unit_tmp);

				Date plan_time = item.getDate("plan_time");// 预计投产时间
				excel.setCellVal(row, 16, plan_time);

				excel.setDateCellStyle(row, 16);

				Date plan_finsh_time = item.getDate("plan_finsh_time");// 预计完工时间
				excel.setCellVal(row, 17, plan_finsh_time);
				excel.setDateCellStyle(row, 17);

				// Date plan_finsh_time = item.getDate("plan_finsh_time");//入库时间
				// excel.setCellVal(row, 2, plan_finsh_time);

				row++;
			}

			excel.save2File(targetfile);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (targetfile.exists()) {
			renderFile(targetfile);
		} else {
			renderText("没有生产表");
		}
	}

	public static void main(String[] args) {

		System.out.println(DateFormatUtils.format(System.currentTimeMillis(), "'yyyy-MM-00'"));
	}

	/**
	 * 批量生成生产计划单
	 */
	public void batchCreatePlan() {
		Integer[] ids = getParaValuesToInt("orderIds");
		String start = getPara("start");
		String end = getPara("end");
		String remark = getPara("remark");
		Ret ret = productService.batchCreatePlanProduct(ids, start, end, remark);
		renderJson(ret);
	}

	public void batchupdate() {
		Integer[] ids = getParaValuesToInt("orderIds");

		String start = getPara("start");
		String end = getPara("end");
		String remark = getPara("remark");
		Ret ret = productService.batchUpdatePlanProduct(ids, start, end, remark);
		renderJson(ret);
	}

	public void batchUpateStart() {
		Integer[] ids = getParaValuesToInt("orderIds");
		String start = getPara("start");
	}

	public void batchUpateEnd() {
		Integer[] ids = getParaValuesToInt("orderIds");
		String end = getPara("end");
	}

	/**
	 * 生产计划单修改金额
	 */
	public void batchUpdatePlan() {
		Integer[] ids = getParaValuesToInt("orderIds");
		if (ids == null || ids.length == 0) {
			renderJson(Ret.fail().set("msg", "没有选择生产计划单"));
			return;
		}
		String start = getPara("startTime");
		String end = getPara("finishTime");
		String remark = getPara("remark");
		if (StringUtils.isEmpty(start) && StringUtils.isEmpty(end)) {

			renderJson(Ret.fail("msg", "没有选择时间"));
			return;
		}

		Ret ret = productService.updatePlanTimeProduct(ids, start, end, remark);
		renderJson(ret);
	}

	/**
	 * 编辑时间页面
	 */
	public void toUpdateTime() {
		Integer[] ids = getParaValuesToInt("orderIds");
		if (ids == null || ids.length == 0) {
			planPro();
			return;
		}
		StringBuilder sb = new StringBuilder();
		SqlKit.joinIds(ids, sb);
		String sql = "select * from  fy_business_order where  id  in " + sb.toString() + " order by id desc";
		List<FyBusinessOrder> modelList = FyBusinessOrder.dao.find(sql);
		setAttr("modelList", modelList);
		render("updatePlanTime.html");
	}
}
