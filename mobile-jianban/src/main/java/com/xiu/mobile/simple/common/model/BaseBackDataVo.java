/**  
 * @Project: xiu
 * @Title: BackInfoVo.java
 * @Package org.lazicats.xiu.common.vo
 * @Description: TODO
 * @author: yong
 * @date 2013-5-8 下午04:36:27
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.simple.common.model;

import java.io.Serializable;

/** 
 * @ClassName: BackInfoVo 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-8 下午04:36:27
 *  
 */
public class BaseBackDataVo  implements Serializable{
	
	//成功
	private static final String CODE_SUCCESS = "000";
	//超时
	private static final String CODE_OVERTIME = "004";
	
	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 5183033766502994610L;
	
	private String retCode;
	
	private String errorMsg;
	
	/**
	 * 数据请求成功
	 * @Title: isSuccess 
	 * @Description: TODO
	 * @return  
	 * @author: yong
	 * @date: 2013-5-8下午05:29:21
	 */
	public boolean isSuccess(){
		return CODE_SUCCESS.equals(this.retCode);
	}
	
	/**
	 * 用户session失效
	 * @Title: isOverTime 
	 * @Description: TODO
	 * @return  
	 * @author: yong
	 * @date: 2013-5-8下午05:29:35
	 */
	public boolean isOverTime(){
		return CODE_OVERTIME.equals(this.retCode);
	}
	

	/** 
	 * @return retCode 
	 */
	public String getRetCode() {
		return retCode;
	}

	/**
	 * @param retCode the retCode to set
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	/** 
	 * @return errorMsg 
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
