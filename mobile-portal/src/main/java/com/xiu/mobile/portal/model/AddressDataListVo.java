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
package com.xiu.mobile.portal.model;

import java.util.List;

/** 
 * @ClassName: AddressDataList 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-7 下午03:10:41
 *  
 */
public class AddressDataListVo {

	private AddressVo  addressDetail;
	
	private List<ProvinceRegionCityVo> list_province;
	
	private List<ProvinceRegionCityVo> list_region;
	
	private List<ProvinceRegionCityVo> list_city;

	/** 
	 * @return addressDetail 
	 */
	public AddressVo getAddressDetail() {
		return addressDetail;
	}

	/**
	 * @param addressDetail the addressDetail to set
	 */
	public void setAddressDetail(AddressVo addressDetail) {
		this.addressDetail = addressDetail;
	}

	/** 
	 * @return list_province 
	 */
	public List<ProvinceRegionCityVo> getList_province() {
		return list_province;
	}

	/**
	 * @param list_province the list_province to set
	 */
	public void setList_province(List<ProvinceRegionCityVo> list_province) {
		this.list_province = list_province;
	}

	/** 
	 * @return list_region 
	 */
	public List<ProvinceRegionCityVo> getList_region() {
		return list_region;
	}

	/**
	 * @param list_region the list_region to set
	 */
	public void setList_region(List<ProvinceRegionCityVo> list_region) {
		this.list_region = list_region;
	}

	/** 
	 * @return list_city 
	 */
	public List<ProvinceRegionCityVo> getList_city() {
		return list_city;
	}

	/**
	 * @param list_city the list_city to set
	 */
	public void setList_city(List<ProvinceRegionCityVo> list_city) {
		this.list_city = list_city;
	}
}
