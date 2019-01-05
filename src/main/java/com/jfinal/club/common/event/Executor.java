package com.jfinal.club.common.event;

public interface Executor {
	/**
	 * 处理事件
	 * @param event
	 */
	public void handle(EventEnum event);
}
