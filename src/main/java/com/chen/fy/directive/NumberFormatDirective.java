package com.chen.fy.directive;

import java.text.DecimalFormat;

import org.apache.commons.lang3.math.NumberUtils;

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
public class NumberFormatDirective extends Directive {
	DecimalFormat format = new DecimalFormat("#,###.##");
	public NumberFormatDirective() {

	}

	/**
	 * 
	 * @param parren 参考 NumberFormat DecimalFormat ，格式化表达式
	 */
	public NumberFormatDirective(String parren) {
		format = new DecimalFormat("#,###.##");
	}

	@Override
	public void exec(Env env, Scope scope, Writer writer) {

		try {

			Object obj = exprList.eval(scope);
			if (obj == null) {
				return;
			}

			if (NumberUtils.isNumber(obj.toString())) {
				String val = obj.toString();
				Double d = Double.valueOf(val);
				writer.write(format.format(d));
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
