package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.CxListVo;
import com.xiu.mobile.portal.model.TopicActivityGoodsVo;

/**
 * <p>
 * ************************************************************** 
 * @Description: 专题商品
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午2:16:38 
 * ***************************************************************
 * </p>
 */
public interface TopicActivityGoodsDao {

	List<TopicActivityGoodsVo> getTopicActivityGoodsByCategory(Map<Object, Object> map);

	List<TopicActivityGoodsVo> getAllGoodsCategoryByActivityId(Map<Object, Object> map);

	List<CxListVo> getCxListByActivityId(Map<Object, Object> map);

	/**
	 * 根据活动ID查询热卖商品数量
	 * 
	 * @param map
	 * @return
	 */
	int getCxCountByActivityId(Map<Object, Object> map);
}
