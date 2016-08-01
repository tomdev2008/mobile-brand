package com.xiu.mobile.core.service;

import com.xiu.mobile.core.model.Goods;


/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 商品中心商品信息处理service
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年6月9日 下午7:04:35
 * </p>
 ****************************************************************
 */
public interface IGoodsService {

	/**
	 * 根据走秀码查询商品信息
	 * @param sn
	 * @return
	 */
	public Goods getGoodsBySn(String sn);
	
	/**
	 * 根据商品ID查询商品信息
	 * @param productId
	 * @return
	 */
	public Goods getGoodsById(Long productId);
	
	/**
	 * 商品降价定时任务
	 */
	public void callGoodsPriceReducedTask();
	
	/**
	 * 同步在线商品数量定时任务
	 * 
	 */
	public void callOnlineGoodsTask()throws Exception ;
}
