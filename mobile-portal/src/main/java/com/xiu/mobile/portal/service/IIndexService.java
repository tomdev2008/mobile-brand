package com.xiu.mobile.portal.service;

import java.util.Map;


public interface IIndexService {
	
	/**
	 * 获取首页显示数据信息
	 * @param params
	 * @return
	 */
	public Map<String,Object> getIndexInfo(Map<String,Object> params);

	public Map<String,Object> getNewIndexInfo(Map<String,Object> params);
	

}
