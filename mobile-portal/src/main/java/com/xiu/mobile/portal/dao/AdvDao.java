package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.AdvertisementVo;

/**
 * APP更新处理
 * @author wenxiaozhuo
 * 2014-05-14
 */
public interface AdvDao {
	
	/**
	 * 按类型获取广告
	 * @param map
	 * @return
	 */
	public List<AdvertisementVo>  getAdvListByType(Map map);
	/**
	 * 按类型集合获取广告
	 * @param map
	 * @return
	 */
	public List<AdvertisementVo>  getAdvListByTypes(Map map);
	
	/**
	 * 按类型查询广告
	 * @param map
	 * @return
	 */
	public AdvertisementVo getAdvByType(Map map);
	
}
