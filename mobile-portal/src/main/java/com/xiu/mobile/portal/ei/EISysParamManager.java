package com.xiu.mobile.portal.ei;

import com.xiu.csp.facade.dto.CspResult;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 系统参数外部接口
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月28日 下午4:59:40
 * </p>
 ****************************************************************
 */
public interface EISysParamManager {
	
	public CspResult getListByParamTypeAndParentCode(String paramType,String parentCode);
	
	public CspResult getListByParamType(String paramType);
	
	public String getAddressDescByCode(String code);

	//查询所有的地区参数信息：省、市、区、邮编
	public CspResult getAllRegionParamInfo();
}
