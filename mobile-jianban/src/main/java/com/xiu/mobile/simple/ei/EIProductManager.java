package com.xiu.mobile.simple.ei;

import java.util.Map;

import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;


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
    
}
