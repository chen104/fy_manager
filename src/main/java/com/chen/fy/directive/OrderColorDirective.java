package com.chen.fy.directive;

import com.chen.fy.model.FyBusinessOrder;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

/**
 * 三个参数
 * 
 * @author Administrator
 *
 */
public class OrderColorDirective extends Directive {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTimeInMillis(System.currentTimeMillis());
		try {

			Object objs = exprList.eval(scope);
			Object obj = scope.get(objs);
			if (obj instanceof FyBusinessOrder) {
				FyBusinessOrder model = (FyBusinessOrder) obj;
				long importTime = model.getImportTime().getTime();
				long now = System.currentTimeMillis();
				long dirDay = now - importTime;
				if (model.getOrderDate() == null) {
					return;
				}
				long orderDate = model.getOrderDate().getTime();
				dirDay = dirDay / 1000 / 60 / 60 / 24;// 天的参数
				long order_dir = (now - orderDate) / 1000 / 60 / 60 / 24;// 天的参数
				if (model.getIsDistribute() == false) {// 未分配的
					if (dirDay > 2) {
						writer.write("order-warn-distribut");
					}
				} else {// 已分配的,28未出库的提示，30天的拖期 ,根据订单日期为准
					if (model.getOutQuantity().doubleValue() == 0) {
						if (order_dir > 30) {
							writer.write("order-delay");
						} else if (order_dir > 28) {
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
