package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.AdvertisementPlace;


/**
 * @author: gaoyuan
 * @description:广告位管理
 * @date: 2013-11-14
 */
public interface AdvertisementPlaceDao {
	
	public List<AdvertisementPlace> getAdvertisementPlaceList(Map<Object, Object> map);
	
	public List<AdvertisementPlace> getAdvertisementPlaceListAll(Map<Object, Object> map);
	
	public String getAdvertisementPlaceTotalCount(Map<Object, Object> map);
	
	public int isExistAdvPlace(AdvertisementPlace advertisementPlace);
	
	public int save(AdvertisementPlace advertisementPlace);
	
	public int update(AdvertisementPlace advertisementPlace);
	
	public int delete(AdvertisementPlace advertisementPlace);
	
	public AdvertisementPlace getAdvertisementPlaceById(Long id);

}
