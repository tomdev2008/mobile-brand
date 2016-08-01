/**  
 * @Project: xiu
 * @Title: AddressUpdateVoConvertor.java
 * @Package org.lazicats.xiu.view.address.vo
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 下午02:03:34
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xiu.mobile.portal.model.AddressDataListVo;
import com.xiu.mobile.portal.model.AddressUpdateOutParam;
import com.xiu.mobile.portal.model.AddressUpdateVo;

/** 
 * @ClassName: AddressUpdateVoConvertor 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 下午02:03:34
 *  
 */
@Component
public class AddressUpdateVoConvertor {
	
	@Resource
	private AddressVoConvertor addressVoConvertor;
	
	@Resource
	private ProvinceRegionCityVoConvertor provinceRegionCityVoConvertor;
	
	public AddressUpdateVo converter2AddressUpdateVo(AddressUpdateOutParam addressUpdateOut){
		
		if(null== addressUpdateOut){
			return null;
		}
		AddressUpdateVo addressUpdateVo = new AddressUpdateVo();
		addressUpdateVo.setTokenId(addressUpdateOut.getTokenId());
		AddressDataListVo addressDataListVo =new AddressDataListVo();
		addressUpdateVo.setAddressDataList(addressDataListVo);
		
		//AddressOutParam addressData = addressUpdateOut.getAddressDataList().getAddressDetail();
		//AddressVo addressVos = addressVoConvertor.Converter2AddressVo(addressData);
		addressDataListVo.setAddressDetail(addressUpdateOut.getAddressDataList().getAddressDetail());
		
		//List<ProvinceRegionCityOutParam> list_province = addressUpdateOut.getAddressDataList().getList_province();
		//List<ProvinceRegionCityVo> pVos = provinceRegionCityVoConvertor.convertor2ProvinceRegionCityVos(list_province);
		addressDataListVo.setList_province(addressUpdateOut.getAddressDataList().getList_province());
		
		//List<ProvinceRegionCityOutParam> list_region = addressUpdateOut.getAddressDataList().getList_region();
		//List<ProvinceRegionCityVo> rVos = provinceRegionCityVoConvertor.convertor2ProvinceRegionCityVos(list_region);
		addressDataListVo.setList_region(addressUpdateOut.getAddressDataList().getList_region());
		
		//List<ProvinceRegionCityOutParam> list_city = addressUpdateOut.getAddressDataList().getList_city();
		//List<ProvinceRegionCityVo>cVos = provinceRegionCityVoConvertor.convertor2ProvinceRegionCityVos(list_city);
		addressDataListVo.setList_city(addressUpdateOut.getAddressDataList().getList_city());
		
		return addressUpdateVo;
	} 
}
