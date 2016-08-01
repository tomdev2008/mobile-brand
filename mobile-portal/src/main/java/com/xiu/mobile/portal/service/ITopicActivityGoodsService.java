package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.portal.model.ActivityGoodsBo;
import com.xiu.mobile.portal.model.CxListVo;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.QueryActivityVo;
import com.xiu.mobile.portal.model.TopicActivityGoodsVo;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

/**
 * <p>
 * ************************************************************** 
 * @Description: 专题商品业务逻辑接口
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午2:11:11 
 * ***************************************************************
 * </p>
 */
public interface ITopicActivityGoodsService 
{

	List<TopicActivityGoodsVo> getTopicActivityGoodsByCategory(Map<Object, Object> map);

	List<TopicActivityGoodsVo> getAllGoodsCategoryByActivityId(Map<Object, Object> map);

	List<CxListVo> getCxListByActivityId(Map<Object, Object> map);

	/**
	 * 查询专题活动商品列表
	 * @param queryAVo 查询VO
	 * @param deviceParams 设备信息
	 * @return ActivityGoodsResVo
	 * @throws Exception
	 */
	ActivityGoodsBo queryTopicActivityGoodsList(QueryActivityVo queryAVo, Map<String, String> deviceParams) throws Exception;

	/**
	 * 查询热卖商品个数
	 * 
	 * @param map
	 * @return int
	 */
	int getCxGoodsCountByActivityId(Map<Object, Object> map);
	/**
	 * 根据商品idlist查询商品信息
	 * @param productIdList
	 * @return
	 */
	public List<Product> batchLoadProducts(List<Long> productIdList);
	/**
	 * 根据商品走秀码查询商品信息
	 * @param goodsSnBag
	 * @return
	 */
	public List<Product> batchLoadProducts(String goodsSnBag);
	
	public List<ItemSettleResultDO> itemListSettle(List<Product> products);
	
	public GoodsVo assembleGoodsItem(Product product, List<ItemSettleResultDO> resultDOList);
	
	/**
	 * 根据商品id获取中英文品牌名
	 * @param goodIds
	 * @return
	 */
	public List<GoodsVo> getBrandNameByGoodIds(String[] goodIds);

}
