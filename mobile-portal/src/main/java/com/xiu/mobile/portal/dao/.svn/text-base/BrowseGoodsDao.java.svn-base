package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.BrowseGoodsModel;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION :商品浏览记录
 * @AUTHOR : coco.long@xiu.com 
 * @DATE :2014年12月19日
 * </p>
 ****************************************************************
 */
public interface BrowseGoodsDao {

	/**
	 * 批量添加商品浏览记录
	 * @param map
	 * @return
	 */
	public int addBrowseGoodsBatch(Map map);
	
	/**
	 * 添加商品浏览记录
	 * @param model
	 * @return
	 */
	public int addBrowseGoods(Map map);
	
	/**
	 * 批量删除商品浏览记录
	 * @param ids
	 * @return
	 */
	public int deleteBrowseGoodsBatch(Map map);
	
	/**
	 * 清空商品浏览记录
	 * @param map
	 * @return
	 */
	public int deleteAllBrowseGoods(Map map);
	
	/**
	 * 删除商品浏览记录
	 * @param id
	 * @return
	 */
	public Integer deleteBrowseGoods(Map map);
	
	/**
	 * 获取用户的商品浏览记录
	 * @param userId
	 * @return
	 */
	public List<BrowseGoodsModel> getBrowseGoodsListByUserId(Map map);
	
	/**
	 * 获取商品浏览记录详情
	 * @param id
	 * @return
	 */
	public BrowseGoodsModel getBrowseGoodsById(Map map);
	
	/**
	 * 获取用户商品浏览记录数量
	 * @param map
	 * @return
	 */
	public int getBrowseGoodsTotalByUserId(Map map);
	
	/**
	 * 获取某一件商品的浏览次数
	 * @param map
	 * @return
	 */
	public int getBrowseGoodsCount(Map map);
}
