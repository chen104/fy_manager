package com.chen.fy.directive;

import javax.servlet.http.HttpSession;

import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

public class TestDirective extends Directive {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		// TODO Auto-generated method stub
		System.out.println(env);
		System.out.println(scope);
		Object objct = scope.get("session");
		HttpSession session = (HttpSession) objct;
		session.getAttribute("permission");
		System.out.println("  test " + objct);
	}

}
