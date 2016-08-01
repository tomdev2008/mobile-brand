/**  
 * @Project: xiu
 * @Title: ProvinceRegionCityVo.java
 * @Package org.lazicats.xiu.view.address.vo
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午09:57:01
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.model;

/** 
 * @ClassName: ProvinceRegionCityVo 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 上午09:57:01
 *  
 */
public class ProvinceRegionCityVo {
	
	private String paramCode;
	
	private String paramDesc;
	
	private String paramType;
	
	private String parentCode;

	/** 
	 * @return paramCode 
	 */
	public String getParamCode() {
		return paramCode;
	}

	/**
	 * @param paramCode the paramCode to set
	 */
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	/** 
	 * @return paramDesc 
	 */
	public String getParamDesc() {
		return paramDesc;
	}

	/**
	 * @param paramDesc the paramDesc to set
	 */
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	/** 
	 * @return paramType 
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * @param paramType the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	/** 
	 * @return parentCode 
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
