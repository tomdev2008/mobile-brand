/**  
 * @Project: xiu
 * @Title: ProvinceRegionCityVoConvertor.java
 * @Package org.lazicats.xiu.view.address.vo
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 下午01:55:00
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xiu.mobile.portal.model.ProvinceRegionCityOutParam;
import com.xiu.mobile.portal.model.ProvinceRegionCityVo;
import com.xiu.mobile.portal.model.QueryAddressParamInParam;

/** 
 * @ClassName: ProvinceRegionCityVoConvertor 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-6 下午01:55:00
 *  
 */
@Component
public class ProvinceRegionCityVoConvertor {
	
	public QueryAddressParamInParam convertor2ProvinceRegionCityIn(ProvinceRegionCityVo prcVo){
		if(prcVo == null){
			return null;
		}
		QueryAddressParamInParam prcIn = new QueryAddressParamInParam();
		
		prcIn.setParamType(prcVo.getParamType());
		prcIn.setParentCode(prcVo.getParentCode());

		return prcIn;
	}
	
	public ProvinceRegionCityVo convertor2ProvinceRegionCityVo(ProvinceRegionCityOutParam prcOut){
		if(prcOut == null){
			return null;
		}
		ProvinceRegionCityVo prcVo = new ProvinceRegionCityVo();
		
		prcVo.setParamCode(prcOut.getParamCode());
		prcVo.setParamDesc(prcOut.getParamDesc());
		prcVo.setParamType(prcOut.getParamType());
		prcVo.setParentCode(prcOut.getParentCode());

		return prcVo;
	}
	
	public List<ProvinceRegionCityVo> convertor2ProvinceRegionCityVos(List<ProvinceRegionCityOutParam> prcOuts){
		if(null==prcOuts || prcOuts.size()<1){
			return null;
		}
		List<ProvinceRegionCityVo> prcVos =new ArrayList<ProvinceRegionCityVo>();
		
		for(int i=0;i<prcOuts.size();i++){
			ProvinceRegionCityVo prcVo = this.convertor2ProvinceRegionCityVo(prcOuts.get(i));
			prcVos.add(prcVo);
		}
		
		return prcVos;
	}
}
