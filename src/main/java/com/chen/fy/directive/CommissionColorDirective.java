package com.chen.fy.directive;

import java.util.Calendar;
import java.util.Date;

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

	public CommissionColorDirective() {
		currentDate = new Date();
		calender = Calendar.getInstance();
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
				if (quantity != model.getInt("pass_quantity")) {
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
