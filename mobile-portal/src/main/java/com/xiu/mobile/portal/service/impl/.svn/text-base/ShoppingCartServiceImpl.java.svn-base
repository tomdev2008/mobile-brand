package com.xiu.mobile.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.dao.ShoppingCartDao;
import com.xiu.mobile.portal.model.ShoppingCart;
import com.xiu.mobile.portal.service.IShoppingCartService;

@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements IShoppingCartService{
	
	@Autowired
	private ShoppingCartDao shoppingCartDao;

	@Override
	public void addGoods(ShoppingCart shoppingCart) {
		// TODO Auto-generated method stub
		shoppingCartDao.addGoods(shoppingCart);
	}

	@Override
	public List<ShoppingCart> getGoodsList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shoppingCartDao.getGoodsList(params);
	}

	@Override
	public List<ShoppingCart> getPageList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shoppingCartDao.getPageList(params);
	}

	@Override
	public int getPageListCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return shoppingCartDao.getPageListCount(params);
	}

	@Override
	public void delGoodsById(String id) {
		// TODO Auto-generated method stub
		shoppingCartDao.delGoodsById(id);
	}
	
	@Override
	public void delGoodsBySku(Map<String, Object> params) {
		// TODO Auto-generated method stub
		shoppingCartDao.delGoodsBySku(params);
	}
	
	/***
	 * 移除购物车里某商品信息且移入关注
	 * @param params
	 */
	public void delAndFavorGoodsBySku(Map<String, Object> params){
		//删除购物车商品
		shoppingCartDao.delGoodsBySku(params);
		//关注商品
		
	}
	
	@Override
	public void updateGoods(ShoppingCart shoppingCart) {
		// TODO Auto-generated method stub
		shoppingCartDao.updateGoods(shoppingCart);
	}

	@Override
	public int getGoodsTotal(Map<String, String> params) {
		// TODO Auto-generated method stub
		return shoppingCartDao.getGoodsTotal(params);
	}

	@Override
	public void updateShoppingCartGoods(ShoppingCart shoppingCart) {
		shoppingCartDao.updateShoppingCartGoods(shoppingCart);
		
	}

	@Override
	public void updateCheckedAll(Map<String, Object> params) {
		shoppingCartDao.updateCheckedAll(params);
	}

	public int getGoodsCountByGoodsSn(Map<String, Object> map) {
		Integer result = shoppingCartDao.getGoodsCountByGoodsSn(map);
		if(result == null) {
			result = 0;
		}
		return result;
	}

}
