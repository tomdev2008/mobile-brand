package com.xiu.mobile.brand.web.service;

import java.util.List;

import com.xiu.mobile.brand.web.bo.PriceQueryBo;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 价格查询接口
 * @AUTHOR kanghuai.zou@xiu.com
 * @DATE 2015-12-15  
 * ***************************************************************
 * </p>
 */
public interface IPriceQueryService {
	
	public List<PriceQueryBo> getPriceMap(String prosn) throws Exception ;
	
}
