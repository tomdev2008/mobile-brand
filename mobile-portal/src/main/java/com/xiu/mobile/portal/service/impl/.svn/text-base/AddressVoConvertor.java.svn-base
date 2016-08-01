/**  
 * @Project: xiu
 * @Title: AddressVoConvertor.java
 * @Package org.lazicats.xiu.view.address.vo
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 下午01:43:56
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.UserAddressOperationInParam;

/** 
 * @ClassName: AddressVoConvertor 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 下午01:43:56
 *  
 */
@Component
public class AddressVoConvertor {
	
	public UserAddressOperationInParam Converter2AddressInParam(AddressVo addrVo){
		if(null==addrVo){
			return null;
		}
		UserAddressOperationInParam addressIn =new UserAddressOperationInParam();
		
		addressIn.setAddressId(addrVo.getAddressId());
		addressIn.setRcverName(addrVo.getRcverName());
		addressIn.setProvinceCode(addrVo.getProvinceCode());
		addressIn.setRegionCode(addrVo.getRegionCode());
		addressIn.setCityCode(addrVo.getCityCode());
		addressIn.setAddressInfo(addrVo.getAddressInfo());
		addressIn.setPostCode(addrVo.getPostCode());
		addressIn.setMobile(addrVo.getMobile());
		addressIn.setAreaCode(addrVo.getAreaCode());
		addressIn.setPhone(addrVo.getPhone());
		addressIn.setDivCode(addrVo.getDivCode());
		addressIn.setBookerName(addrVo.getBookerName());
		addressIn.setBookerPhone(addrVo.getBookerPhone());
		addressIn.setIsMaster(addrVo.getIsMaster());
		addressIn.setIdCard(addrVo.getIdCard());
		addressIn.setIdHead(addrVo.getIdHead());
		addressIn.setIdTails(addrVo.getIdTails());
		addressIn.setIdentityId(addrVo.getIdentityId());
		
		return addressIn;
	}
	
	
	public AddressVo Converter2AddressVo(AddressOutParam addressOut){
		if(null==addressOut){
			return null;
		}
		AddressVo addrVo =new AddressVo();
		
		addrVo.setAddressId(addressOut.getAddressId());
		addrVo.setRcverName(addressOut.getRcverName());
		addrVo.setProvinceCode(addressOut.getProvinceCode());
		addrVo.setRegionCode(addressOut.getRegionCode());
		addrVo.setCityCode(addressOut.getCityCode());
		addrVo.setAddressInfo(addressOut.getAddressInfo());
		addrVo.setPostCode(addressOut.getPostCode());
		addrVo.setMobile(addressOut.getMobile());
		addrVo.setAreaCode(addressOut.getAreaCode());
		addrVo.setPhone(addressOut.getPhone());
		addrVo.setDivCode(addressOut.getDivCode());
		addrVo.setBookerName(addressOut.getBookerName());
		addrVo.setBookerPhone(addressOut.getBookerPhone());
		addrVo.setIsMaster(addressOut.getIsMaster());
		addrVo.setAddressPrefix(addressOut.getAddressPrefix());
		addrVo.setProvinceCodeDesc(addressOut.getProvinceCodeDesc());
		addrVo.setRegionCodeDesc(addressOut.getRegionCodeDesc());
		addrVo.setCityCodeDesc(addressOut.getCityCodeDesc());
		addrVo.setCityCodeRemark(addressOut.getCityCodeRemark());
		addrVo.setIdentityId(addressOut.getIdentityId());
		
		return addrVo;
	}
	
	
	
	public List<AddressVo> Converter2AddressVos(List<AddressOutParam> addressOuts){
		if(null==addressOuts || addressOuts.size()<1){
			return null;
		}
		List<AddressVo> addrVos =new ArrayList<AddressVo>();
		
		for(int i=0;i<addressOuts.size();i++){
			AddressVo addressVo = this.Converter2AddressVo(addressOuts.get(i));
			addrVos.add(addressVo);
		}
		
		return addrVos;
	}
}
