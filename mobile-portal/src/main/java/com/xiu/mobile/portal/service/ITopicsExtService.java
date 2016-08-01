package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.TopicFilterVO;

/**
 * 卖场扩展Service
 * @author coco.long
 * @time	2015-08-21
 */
public interface ITopicsExtService {

	//查询卖场商品列表
	public Map<String, Object> getTopicGoodsList(Map map);
	
	//移除卖场分类缓存
	public boolean removeTopicMemcache(Map map);
	
	/**
	 * 获取卖场商品过滤数据
	 * @param map
	 * @return
	 */
	public  List<TopicFilterVO> getTopicGoodFilter(Map<String,String> map);
}
