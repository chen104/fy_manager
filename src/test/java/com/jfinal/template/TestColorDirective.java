package com.jfinal.template;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.Before;
import org.junit.Test;

import com.chen.fy.directive.CommissionColorDirective;
import com.chen.fy.directive.OrderColorDirective;
import com.chen.fy.directive.ProductColorDirective;
import com.chen.fy.directive.TaxRateDirective;
import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class TestColorDirective {
	Engine engine;
	FyBusinessOrder model;
	@Before
	public void setup() {
		engine = new Engine();
		engine.setToClassPathSourceFactory();
		engine.addDirective("orderColor", OrderColorDirective.class);
		engine.addDirective("productColor", ProductColorDirective.class);
		engine.addDirective("commisionColor", CommissionColorDirective.class);
		engine.addDirective("taxRate", TaxRateDirective.class);
		model = new FyBusinessOrder();
		model.setQuantity(12);

	}


	public void testOrderDirective() {
		Calendar calendar = Calendar.getInstance();
		
		String tmpl = " test color #productColor(model) + #(name)  #for(x : modelPage.getList()) #productColor(x)   #end";
		Map<String, Object> data = new HashMap<String, Object>();
		List<FyBusinessOrder> list =new ArrayList<>();
		list.add(model);
		// model.set("v_out_quantity", 12);
		Page<FyBusinessOrder> modelPage = new Page<>(list, 1, 30, 1, 1);
		data.put("model", model);
		data.put("name", "chen104");
		data.put("modelPage", modelPage);

		calendar.add(calendar.DATE, -1);
		 Date date =calendar.getTime();
		System.out.println("3天前   now: " + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")
				+ " delivoty  "
				+ DateFormatUtils.format(date, "yyyy-MM-dd"));

		model.setDeliveryDate(date);
		String terst = engine.getTemplateByString(tmpl).renderToString(data);
		System.out.println("结果： " + terst);

	}

	@Test
	public void testCommisionColor() {
		Calendar calendar = Calendar.getInstance();

		String tmpl = " test color #commisionColor(model) + #(name)  #for(x : modelPage.getList()) #commisionColor(x)   #end";
		Map<String, Object> data = new HashMap<String, Object>();
		List<Record> list = new ArrayList<>();
		Record model = new Record();
		list.add(model);
		// model.set("v_out_quantity", 12);
		Page<Record> modelPage = new Page<Record>(list, 1, 30, 1, 1);
		data.put("model", model);
		data.put("name", "chen104");
		data.put("modelPage", modelPage);

		calendar.add(calendar.DATE, 3);
		Date date = calendar.getTime();
		System.out.println("3天前   now: " + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")
				+ " delivoty  " + DateFormatUtils.format(date, "yyyy-MM-dd"));
		model.set("pass_quantity", 12);
		model.set("quantity", 1);
		model.set("purchase_delivery_date", date);
		String terst = engine.getTemplateByString(tmpl).renderToString(data);
		System.out.println("结果： " + terst);

	}


	public void testProductColor() {
		Calendar calendar = Calendar.getInstance();

		String tmpl = " test color #productColor(model) + #(name)  #for(x : modelPage.getList()) #productColor(x)   #end";
		Map<String, Object> data = new HashMap<String, Object>();
		List<FyBusinessOrder> list = new ArrayList<>();
		list.add(model);
		// model.set("v_out_quantity", 12);
		Page<FyBusinessOrder> modelPage = new Page<>(list, 1, 30, 1, 1);
		data.put("model", model);
		data.put("name", "chen104");
		data.put("modelPage", modelPage);

		calendar.add(calendar.DATE, -1);
		Date date = calendar.getTime();
		System.out.println("3天前   now: " + DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")
				+ " delivoty  " + DateFormatUtils.format(date, "yyyy-MM-dd"));
		model.set("pass_quantity", 12);

		model.setFinishTime(date);
		String terst = engine.getTemplateByString(tmpl).renderToString(data);
		System.out.println("结果： " + terst);

	}
}
