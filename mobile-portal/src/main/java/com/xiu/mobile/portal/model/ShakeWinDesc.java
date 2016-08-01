package com.xiu.mobile.portal.model;

/**
 * 
 * @ClassName 摇一摇用户中奖描述
 * @Description 
 * @author chenlinyu
 * @date 2014年12月9日 下午3:20:04
 */
public class ShakeWinDesc {
 
	private String userName;
	private String winDesc;
	private String winTime;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getWinDesc() {
		return winDesc;
	}
	public void setWinDesc(String winDesc) {
		this.winDesc = winDesc;
	}

	public String getWinTime() {
		return winTime;
	}

	public void setWinTime(String winTime) {
		this.winTime = winTime;
	}
}
