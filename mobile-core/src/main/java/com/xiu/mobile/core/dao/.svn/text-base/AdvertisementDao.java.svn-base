package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Advertisement;


/**
 * @author: gaoyuan
 * @description:广告管理
 * @date: 2013-11-14
 */
public interface AdvertisementDao {
	
	public List<Advertisement> getAdvertisementList(Map<Object, Object> map);
	
	public Integer getAdvertisementTotalCount(Map<Object, Object> map);
	
	public int save(Advertisement advertisement);
	
	public int update(Advertisement advertisement);
	
	public int delete(Advertisement advertisement);
	/**
	 * 按广告位id删除
	 * @param advPlaceId
	 * @return
	 */
	public int deleteByAdvPlaceId(Map params);
	/**
	 * 按广告帧id删除
	 * @param advPlaceId
	 * @return
	 */
	public int deleteByAdvFrameId(Map params);
	
	public int isExsitAdv(Advertisement advertisement);
	
	public Advertisement getAdvertisementById(Long id);
	
	/**
	 * 检查绑定的广告帧时间上是否重复
	 * @param map >1是 0否
	 * @return
	 */
	public Integer checkTimeByAdvPlace(Map map);
	
	/**
	 * 按广告帧获取广告
	 * @param map
	 * @return
	 */
	public List<Advertisement> getTimesByAdvFrameId(Map map);
	
}
