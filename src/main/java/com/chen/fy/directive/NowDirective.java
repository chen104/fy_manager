package com.chen.fy.directive;

import java.io.IOException;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

public class NowDirective extends Directive {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		Object obj = exprList.eval(scope);
		try {
			if (obj != null) {

				writer.write(DateFormatUtils.format(System.currentTimeMillis(), obj.toString()));

			} else {
				writer.write(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
