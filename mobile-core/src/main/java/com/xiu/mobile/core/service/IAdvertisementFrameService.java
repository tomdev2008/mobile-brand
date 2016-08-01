package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.AdvertisementFrame;

/**
 * 
* @Description: TODO(广告帧管理) 
* @author haidong.luo@xiu.com
* @date 2015年9月8日 下午3:30:08 
*
 */
public interface IAdvertisementFrameService {
	
	public List<AdvertisementFrame> getAdvertisementFrameListAll();
	
	public List<AdvertisementFrame> getAdvertisementFrameList(Map<Object, Object> map);
	
	public int save(AdvertisementFrame advertisementFrame);
	
	public int update(AdvertisementFrame advertisementFrame);
	
	/**修改顺序
	 * ID 转移的id
	 * beId 被转移的id
	 * type 0上移 1下移
	 * @param map
	 * @return
	 */
	public int updateOrder(Map<Object, Object> map);
	
	public int delete(AdvertisementFrame advertisementFrame);
	
	public AdvertisementFrame getAdvertisementFrameById(Long id);

}
