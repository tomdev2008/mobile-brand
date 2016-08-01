package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.AskBuyOrderVO;
import com.xiu.mobile.portal.model.ProductTypeBrandVO;

/**
 * 求购Service
 * @author coco.long
 * @time	2015-08-21
 */
public interface IAskBuyService {

	//添加求购信息
	public Map<String, Object> addAskBuyInfo(Map map);
	
	//查询用户订单列表
	public Map getUserOrderList(Map map);
	
	//根据订单ID查询订单详情
	public AskBuyOrderVO getOrderInfoByOrderId(Map map);
	
	//查询最新订单
	public List<String> getNearOrderList(Map map);
	
	//删除求购订单
	public Map<String, Object> deleteOrder(Map map);
	
	//查询产品分类和品牌信息
	public List<ProductTypeBrandVO> getProductTypeAndBrand();
	
//	//查询求购是否有更新
//	public Integer getAskBuyHadNews(Map params);
}
