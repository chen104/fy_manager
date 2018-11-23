package com.chen.fy.controller.addition;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.chen.fy.controller.BaseController;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessOrder;
import com.chen.fy.model.FyBusinessReady;
import com.jfinal.club.common.kit.Constant;
import com.jfinal.club.common.kit.ReadyProductNoKit;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Engine;
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
	Engine engine;

	public ReadyController() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
	}

	public void findJsonPage() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}

		keepPara("keyWord", "condition", "modelId");
		String condition = getPara("condition");
		Page<Record> modelPage = null;
		try {
			modelPage = service.findPage(getParaToInt("p", 1) + 1, getPageSize(), condition, key);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		Ret ret = Ret.ok("msg", "加载数据");
		HashedMap<String, Object> data = new HashedMap<String, Object>();
		data.put("modelPage", modelPage);
		data.put("pageSize", getPageSize());
		engine.addSharedObject("account", getLoginAccount());
		String str = engine.getTemplate("stringTemplet/addtion/ready/list.jf").renderToString(data);
		ret.set("data", str);
		ret.set(Constant.pageIndex, modelPage.getPageNumber());
		ret.set(Constant.pagePageSize, modelPage.getPageSize());
		ret.set(Constant.pageTotalRow, modelPage.getTotalRow());
		ret.set(Constant.pageTotal, modelPage.getTotalPage());
		ret.set(Constant.pageListSize, modelPage.getList().size());
		renderJson(ret);
	}

	public void index() {
		String key = getPara("keyWord");
		if (key != null) {
			key = key.trim();
		}

		keepPara("keyWord", "condition", "modelId");
		String condition = getPara("condition");
		Page<Record> modelPage = service.findPage(getParaToInt("p", 1), getPageSize(), condition, key);
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
				service.uploadFile(file);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		ufile.getFile().delete();
		renderJson(Ret.ok("msg", "添加了" + total + "记录"));
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

			String filename = "";
			if (date != null) {
				filename = date;
			}

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
		if (key != null) {
			key = key.trim();
		}
		setAttr("keyWord", key);
		Page<FyBusinessOrder> modelPage = null;
		setAttr("modelPage", modelPage);
		render("reveive.html");
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
