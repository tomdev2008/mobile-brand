package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.ShoppingCart;

public interface IShoppingCartService {

	/***
	 * 向购物车里添加商品信息
	 * @param shoppingCart
	 */
	public void addGoods(ShoppingCart shoppingCart);
	
	/***
	 * 条件查询购物车里商品信息
	 * @return
	 */
	public List<ShoppingCart> getGoodsList(Map<String, Object> params);
	
	/***
	 * 条件查询购物车里商品信息(分页查询)
	 * @return
	 */
	public List<ShoppingCart> getPageList(Map<String, Object> params);
	
	/***
	 * 查询用户下商品总分类数
	 * @param params
	 * @return
	 */
	public int getPageListCount(Map<String, Object> params);
	
	/***
	 * 查询用户下商品总数
	 * @param params
	 * @return
	 */
	public int getGoodsTotal(Map<String, String> params);
	
	/***
	 * 移除购物车里某商品信息
	 * @param id
	 */
	public void delGoodsById(String id);
	
	/***
	 * 移除购物车里某商品信息
	 * @param params
	 */
	public void delGoodsBySku(Map<String, Object> params);
	
	/***
	 * 移除购物车里某商品信息且移入关注
	 * @param params
	 */
	public void delAndFavorGoodsBySku(Map<String, Object> params);
	
	/***
	 * 更新购物车里商品信息
	 */
	public void updateGoods(ShoppingCart shoppingCart);
	
	/***
	 * 更新购物车里商品信息
	 */
	public void updateShoppingCartGoods(ShoppingCart shoppingCart);
	
	/***
	 * 勾选或反选某用户下的购物车信息
	 * @param params
	 */
	public void updateCheckedAll(Map<String, Object> params);
	
	/**
	 * 查询购物车的商品数量ByGoodsSn
	 * @param map
	 * @return
	 */
	public int getGoodsCountByGoodsSn(Map<String, Object> map);
}
