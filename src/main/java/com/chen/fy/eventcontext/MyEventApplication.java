package com.chen.fy.eventcontext;

import com.chen.fy.event.FylEvent;

import net.dreamlu.event.core.EventListener;

public class MyEventApplication {
	@EventListener
	public void listenTest1Event(FylEvent event) {
		System.out.println("Test1Event：" + event.getSource());
	}


}
