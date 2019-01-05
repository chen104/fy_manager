package com.jfinal.club.common.event;

public interface Message {
	/**
	 * 获取事件类型
	 * @return
	 */
	EventEnum getEvent();

	/**
	 * 获取附带数据
	 * @return
	 */
	Object getValue();
}
