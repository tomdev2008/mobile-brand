package com.xiu.mobile.brand.web.dao.model;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(发货地信息模型类) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2015-4-2 下午6:06:15 
 * ***************************************************************
 * </p>
 */

public class DeliverModel {

	/**
	 * 发货地编码
	 */
	private Integer code;
	
	/**
	 * 发货地名称
	 */
	private String name;

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
