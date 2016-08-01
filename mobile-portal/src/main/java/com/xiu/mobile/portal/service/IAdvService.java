package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.AdvertisementVo;

public interface IAdvService {

	
	/**
	 * 按类型获取广告
	 * @param map
	 * @return
	 */
	public List<AdvertisementVo> getAdvListByType(Map map);
	
	/**
	 * 按类型获集合取广告
	 * @param map
	 * @return
	 */
	public List<AdvertisementVo> getAdvListByTypes(Map map);
	/**
	 * 添加广告日志
	 * @param map
	 * @return
	 */
	public Integer addAdvLog(Map map);
	
	/**
	 * 按类型查询广告
	 * @param map
	 * @return
	 */
	public AdvertisementVo getAdvByType(Map map);

}
