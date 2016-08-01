package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.AdvertisementPlace;
import com.xiu.mobile.core.model.Page;

/**
 * @author: gaoyuan
 * @description:广告位管理
 * @date: 2013-11-14
 */
public interface IAdvertisementPlaceService {
	
	public List<AdvertisementPlace> getAdvertisementPlaceList(Map<Object, Object> map,Page<?> page);
	
	public List<AdvertisementPlace> getAdvertisementPlaceListAll(Map<Object, Object> map);
	
	public int save(AdvertisementPlace advertisementPlace);
	
	public int update(AdvertisementPlace advertisementPlace);
	
	public int delete(AdvertisementPlace advertisementPlace);
	
	public AdvertisementPlace getAdvertisementPlaceById(Long id);

}
