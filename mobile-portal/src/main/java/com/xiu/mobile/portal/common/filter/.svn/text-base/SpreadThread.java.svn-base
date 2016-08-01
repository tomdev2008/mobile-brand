package com.xiu.mobile.portal.common.filter;


import com.xiu.mobile.portal.ei.EIMobileSalesManager;

/**
 * 分享红包
 * @author Administrator
 *
 */
public class SpreadThread extends Thread{
	
	private EIMobileSalesManager manager;
	private String mobile;
	private long userId;
	private String deviceId;
	public SpreadThread(String mobile,long userId,String deviceId,EIMobileSalesManager manager){
		this.mobile=mobile;
		this.userId=userId;
		this.deviceId=deviceId;
		this.manager=manager;
	}
	public void run(){
		manager.findBlacklistByspread(mobile, userId, deviceId);
	}
}
