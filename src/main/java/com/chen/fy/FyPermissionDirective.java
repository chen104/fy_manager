package com.chen.fy;

import java.util.List;

import com.chen.fy.Interceptor.FyLoginSessionInterceptor;
import com.chen.fy.model.Account;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

public class FyPermissionDirective extends Directive {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {

		Object[] param = exprList.evalExprList(scope);
		String key = param[0].toString();
		Account account = FyLoginSessionInterceptor.getThreadLocalAccount();
		if (account.getId() == 1) {// 管理员
			stat.exec(env, scope, writer);
		} else {
			if (account != null) {
				List<String> list = FyAuthService.me.getPermession(account.getId());
				if (list.contains(key)) {
					stat.exec(env, scope, writer);
				}
			}
		}

	}

	@Override
	public boolean hasEnd() {
		// TODO Auto-generated method stub
		return true;
	}

}
