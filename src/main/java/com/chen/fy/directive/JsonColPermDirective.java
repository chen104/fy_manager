package com.chen.fy.directive;

import com.chen.fy.model.Account;
import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

public class JsonColPermDirective extends Directive {
	Account account = null;

	public JsonColPermDirective(Account account) {
		this.account = account;
	}

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		// scope.get("session");获取session
		Object[] param = exprList.evalExprList(scope);
		String ctable = param[0].toString();
		String ckey = param[1].toString();
		// Account account = (Account) ((HttpSession)
		// scope.get("session")).getAttribute("account");
		// // FyLoginSessionInterceptor.getThreadLocalAccount();

		if (account.hasColPermission(ctable, ckey)) {
			stat.exec(env, scope, writer);
		}

	}

	@Override
	public boolean hasEnd() {
		// TODO Auto-generated method stub
		return true;
	}

}
