package com.chen.fy.event;

import net.dreamlu.event.core.ApplicationEvent;

public class FylEvent extends ApplicationEvent<EventEnum> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5581632364167581274L;

	public FylEvent(EventEnum source) {
		super(source);

	}




}
