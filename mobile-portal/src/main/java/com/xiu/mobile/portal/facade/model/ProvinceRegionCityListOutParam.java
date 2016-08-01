/**  
 * @Project: xiu
 * @Title: ProvinceRegionCityListOutParam.java
 * @Package org.lazicats.xiu.model.address
 * @Description: TODO
 * @author: yong
 * @date 2013-5-8 下午05:01:38
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.facade.model;

import java.util.List;

import com.xiu.mobile.portal.common.model.BaseBackDataVo;
import com.xiu.mobile.portal.model.ProvinceRegionCityOutParam;

/** 
 * @ClassName: ProvinceRegionCityListOutParam 
 * @Description: TODO
 * @author: yong
 * @date 2013-5-8 下午05:01:38
 *  
 */
public class ProvinceRegionCityListOutParam extends BaseBackDataVo{

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 8933465009687541371L;
	
	private List<ProvinceRegionCityOutParam> prcList;

	/** 
	 * @return prcList 
	 */
	public List<ProvinceRegionCityOutParam> getPrcList() {
		return prcList;
	}

	/**
	 * @param prcList the prcList to set
	 */
	public void setPrcList(List<ProvinceRegionCityOutParam> prcList) {
		this.prcList = prcList;
	}
}
