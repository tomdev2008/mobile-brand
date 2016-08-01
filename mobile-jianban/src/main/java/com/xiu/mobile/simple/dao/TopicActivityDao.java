package com.xiu.mobile.simple.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.simple.model.GoodsVo;

public interface TopicActivityDao {

	/**
	 * 
	 * 取的品牌列表信息
	 */
	List<Map<String, Object>> queryBrandList(Map<String, Object> paramMap);

	List<Map<String, Object>> queryNewAttrValByCatentryId(Map<String, Object> paramMap);

	List<Map<String, Object>> queryOldAttrValByCatentryId(Map<String, Object> paramMap);
	
	/**
	 * 查询热卖商品列表信息
	 * 
	 * @param paramMap
	 * @return
	 */
	List<GoodsVo> queryHotSaleGoodsList(Map<String, Object> paramMap);
	
	/**
	 * 查询热卖商品总数
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryHotSaleGoodsCount(Map<String, Object> paramMap);
	
	/**
	 * 查询活动商品列表信息
	 * 
	 * @param activityId
	 * @return
	 */
	List<GoodsVo> queryTopicActivityGoodsList(Map<String, Object> paramMap);
	

	/**
	 * 查询活动商品总数
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryTopicActivityGoodsTotal(Map<String, Object> paramMap);

}
