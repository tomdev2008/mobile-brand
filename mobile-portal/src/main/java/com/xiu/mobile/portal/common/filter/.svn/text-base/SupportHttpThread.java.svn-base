package com.xiu.mobile.portal.common.filter;


import com.xiu.mobile.portal.ei.EIMobileSalesManager;

/**
 * 领取红包
 * @author Administrator
 *
 */
public class SupportHttpThread extends Thread{
	
	private EIMobileSalesManager manager;
	
	private String mobile;
	public SupportHttpThread(String mobile,EIMobileSalesManager manager){
		this.mobile=mobile;
		this.manager=manager;
	}
	public void run(){
		manager.findBlacklistBysupport(mobile);
	}
}
