package com.chen.fy.directive;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.chen.fy.model.FyBusinessOrder;
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
public class OrderColorDirective extends Directive {
	private Date currentDate;
	Calendar calender;
	public OrderColorDirective() {
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

			if (obj instanceof FyBusinessOrder) {
				FyBusinessOrder model = (FyBusinessOrder) obj;
				String execuStatus = model.getExecuStatus();
				if (execuStatus != null) {
					execuStatus = execuStatus.trim();
				}
				// if ("备货".equals(execuStatus)) {
				// return;
				// }
				BigDecimal quantity = model.getQuantity();
				Double v_out_quantity = model.getDouble("v_out_quantity");
				if (v_out_quantity == null) {
					v_out_quantity = 0d;
				}
				if (quantity.doubleValue() != v_out_quantity) {
					Date deliverdate = model.getDeliveryDate();// 交货日期
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
