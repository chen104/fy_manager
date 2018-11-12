package com.chen.fy.directive;

import java.util.HashSet;

import javax.servlet.http.HttpSession;

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
		if ("execut_update_cost".equals(key)) {
			System.out.println("权限");
		}
		Account account = (Account) ((HttpSession) scope.get("session")).getAttribute("account");
		HashSet<String> allpermis = account.getAllkeyPermission();
		if (allpermis.contains(key)) {
		// FyLoginSessionInterceptor.getThreadLocalAccount();
			if (account.hasPermission(key)) {
				stat.exec(env, scope, writer);
			}
		} else {
			// if (account.hasPermission(key)) {
				stat.exec(env, scope, writer);
			// }
		}

	}

	@Override
	public boolean hasEnd() {
		// TODO Auto-generated method stub
		return true;
	}

}
