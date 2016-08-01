/**  
 * @Project: xiu
 * @Title: AddressDataList.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-7 下午03:10:41
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.facade.model;

import java.util.List;

import com.xiu.mobile.portal.model.ProvinceRegionCityOutParam;

/** 
 * @ClassName: AddressDataList 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-7 下午03:10:41
 *  
 */
public class AddressDataList {

	private AddressOutParam  addressDetail;
	
	private List<ProvinceRegionCityOutParam> list_province;
	
	private List<ProvinceRegionCityOutParam> list_region;
	
	private List<ProvinceRegionCityOutParam> list_city;
	

	/** 
	 * @return addressDetail 
	 */
	public AddressOutParam getAddressDetail() {
		return addressDetail;
	}

	/**
	 * @param addressDetail the addressDetail to set
	 */
	public void setAddressDetail(AddressOutParam addressDetail) {
		this.addressDetail = addressDetail;
	}

	/** 
	 * @return list_province 
	 */
	public List<ProvinceRegionCityOutParam> getList_province() {
		return list_province;
	}

	/**
	 * @param list_province the list_province to set
	 */
	public void setList_province(List<ProvinceRegionCityOutParam> list_province) {
		this.list_province = list_province;
	}

	/** 
	 * @return list_region 
	 */
	public List<ProvinceRegionCityOutParam> getList_region() {
		return list_region;
	}

	/**
	 * @param list_region the list_region to set
	 */
	public void setList_region(List<ProvinceRegionCityOutParam> list_region) {
		this.list_region = list_region;
	}

	/** 
	 * @return list_city 
	 */
	public List<ProvinceRegionCityOutParam> getList_city() {
		return list_city;
	}

	/**
	 * @param list_city the list_city to set
	 */
	public void setList_city(List<ProvinceRegionCityOutParam> list_city) {
		this.list_city = list_city;
	}
}
