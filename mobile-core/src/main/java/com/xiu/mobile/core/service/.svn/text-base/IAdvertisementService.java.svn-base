package com.xiu.mobile.core.service;

import java.util.Map;

import com.xiu.mobile.core.model.Advertisement;
import com.xiu.mobile.core.model.Page;


/**
 * @author: gaoyuan
 * @description:广告管理
 * @date: 2013-11-14
 */
public interface IAdvertisementService {
	
	public Map getAdvertisementList(Map<Object, Object> map,Page<?> page);
	
	public Map save(Map params);
	
	public Map update(Map params);
	
	public int delete(Advertisement advertisement);

	public Advertisement getAdvertisementById(Long id);
	
	/**
	 * 根据广告帧获取已经分配的时间段
	 * @param params
	 * @return
	 */
	public String getTimesByAdvFrameId(Map params);
	
}
