package com.xiu.mobile.portal.common.constants;

public enum SendingPlaceMsgs {

	
	domestic(1, "国内发货"),
	hongkong(2, "香港直发"),
	overseas(3, "海外直发");
	
	private SendingPlaceMsgs(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private int code;
	
	private String message;
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
	


}
