package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.GoodsCategoryVO;
import com.xiu.mobile.portal.model.GoodsVo;

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
	 * 查询卖场下所有的商品列表
	 * @param paramMap
	 * @return
	 */
	List<GoodsVo> queryAllActivityGoodsList(Map<String, Object> paramMap);
	
	/**
	 * 查询活动商品总数
	 * 
	 * @param paramMap
	 * @return
	 */
	int queryTopicActivityGoodsTotal(Map<String, Object> paramMap);

	//查询卖场下所有的商品ID
	String getAllGoodsIdByTopicId(String activityId);
	
	//查询卖场下的商品ID列表
	List<String> getGoodsListByTopicId(String activityId);
	
	//查询卖场下的商品goodsSn
	String getAllGoodsSnByTopicAndGoodsIds(Map<String, Object> map);
	
	//查询商品走秀码列表
	List<String> getGoodsSnListByTopicAndGoodsIds(Map<String, Object> map);

	//查询卖场下的商品数量
	int getGoodsCountByTopicId(String activityId);
	
	List<GoodsCategoryVO> goodsCategoryList(String activityId);
	
	//查询商品品牌
	Map<String, Object> getBrandNameByGoods(String goodsId);
	
	//查询商品品牌
	List<GoodsVo> getBrandNameByGoodIds(Map<String, Object> map);
	
	//查询排序的商品列表
	List getOrderGoodsList(Map<String, Object> map);
}
