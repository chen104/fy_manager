package com.chen.fy.directive;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.jfinal.plugin.activerecord.Record;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 三个参数
 * 交货前三天含当天，显示黄色 ，警告
 * 
 * @author Administrator
 *
 */
public class CommissionColorDirective extends Directive {
	private Date currentDate;
	Calendar calender;
	private Set<String> work_no_set;
	private boolean isdebug = false;
	public CommissionColorDirective() {
		currentDate = new Date();
		calender = Calendar.getInstance();
		if (isdebug) {
			work_no_set = new HashSet<String>();
			// work_no_set.add("6A-657083-1-1");
			// work_no_set.add("20181115-105");
			// work_no_set.add("6A-653934-1-2");
			// work_no_set.add("6A-653934-1-1");
		}
	}

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTimeInMillis(System.currentTimeMillis());

		try {
			// writer.write("order-delay"); writer.write("order-warn-delay");
			Object obj = exprList.eval(scope);

			// Object obj = scope.get(objs);

			if (obj instanceof Record) {
				Record model = (Record) obj;
				Integer quantity = model.getInt("quantity");
				String work_no = model.getStr("work_order_no");
				// if (work_no_set.contains(work_no)) {
				// System.out.println(model);
				// }
				Integer pass_quantity = model.getInt("pass_quantity");
				if (pass_quantity == null) {
					pass_quantity = new Integer(0);
				}

				Integer has_in_quantity = model.getInt("has_in_quantity");


				if (quantity.intValue() != pass_quantity.intValue()) {
					Date deliverdate = model.getDate("purchase_delivery_date");// 预计完成时间
					if (deliverdate == null) {
						return;
					}
					calender.setTime(deliverdate);
					calender.add(Calendar.DATE, 1);// 当天不算拖期
					Date tmp = calender.getTime();
					if (tmp.before(currentDate)) {// 交货在现在之前，拖期
						writer.write("order-delay");
					} else {
						calender.add(Calendar.DATE, -3);// 当天不算拖期
						tmp = calender.getTime();
						if (tmp.before(currentDate)) {
							writer.write("order-warn-delay");
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
