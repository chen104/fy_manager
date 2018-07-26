package com.chen.fy.controller.role.Exception;

/**
 * 自定义异常类
 * 
 * @author Administrator
 *
 */
public class FyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7503556706403459615L;
	String msg;

	public FyException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}
