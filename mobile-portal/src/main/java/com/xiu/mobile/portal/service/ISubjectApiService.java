package com.xiu.mobile.portal.service;

import java.util.Map;


public interface ISubjectApiService {

	/**
	 * 获取专题详情
	 * @param params
	 * @return
	 */
	public Map<String,Object> getSubjectInfo(Map<String,Object> params);
	/**
	 * 大小专题列表
	 * @param params
	 * @return
	 */
	public Map<String,Object> getBigOrSmallSubjectListIndex(Map<String,Object> params);
	
	
	public Map<String,Object> getUserCollectSubjectList(Map<String,Object> params);

}
