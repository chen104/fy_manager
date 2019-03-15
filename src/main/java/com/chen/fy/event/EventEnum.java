package com.chen.fy.event;

public enum EventEnum {
	Inhouse("入库事件"), Outhouse("出库事件"), BillPay("应付结算"), BillGetpay("应收结算");
	private String eventName;
	private String ids;

	EventEnum(String name) {
		eventName = name;
	}

	EventEnum() {

	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public static void main(String[] args) {
		EventEnum event = EventEnum.Inhouse;
		event.setIds("ids");
		System.out.println(event.getEventName() + " " + event.getIds());
	}

}
