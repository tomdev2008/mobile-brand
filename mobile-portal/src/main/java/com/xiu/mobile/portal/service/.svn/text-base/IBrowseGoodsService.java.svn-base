package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.BrowseGoodsModel;

public interface IBrowseGoodsService {
	/**
	 * 添加商品浏览记录
	 * @param map
	 * @return
	 */
	public int addBrowseGoods(Map map);
	
	/**
	 * 删除商品浏览记录
	 * @param ids	商品浏览记录Id串
	 * @return
	 */
	public int deleteBrowseGoods(Map map);
	
	/**
	 * 清空商品浏览记录
	 * @param map
	 * @return
	 */
	public int deleteAllBrowseGoods(Map map);
	
	/**
	 * 获取用户的商品浏览记录
	 * @param map
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
