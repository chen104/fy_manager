package com.chen.fy;

import java.io.IOException;

import org.apache.commons.lang3.math.NumberUtils;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

public class TaxRateDirective extends Directive {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {

		Object obj = exprList.eval(scope);

		try {
			if (obj != null && NumberUtils.isNumber(obj.toString())) {
				Double rate = Double.valueOf(obj.toString()) * 100;
				writer.write(rate + "%");
			} else {
				writer.write(obj == null ? "" : "不是数字");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}