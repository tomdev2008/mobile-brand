package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;

import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.facade.model.GiftPack;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 商品中心
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午2:38:28
 * </p>
 **************************************************************** 
 */
public interface EIProductManager {

	/**
	 * 
	 * @param commonParas
	 * @param searchParas
	 * @return
	 */
	Map getProductLight(ProductCommonParas commonParas, ProductSearchParas searchParas);
	
	/**
	 * 
	 * @param commonParas
	 * @param searchParas
	 * @return
	 */
	Map searchProduct(ProductCommonParas commonParas, ProductSearchParas searchParas);
	
	//查询商品是否支持礼品包装
	Map getProductSupportWrap(String goodsSn);
	
	//批量查询商品是否支持礼品包装
	Map<String,Boolean>   getProductSupportWrapList(List<String> goodsSns);
    
}
