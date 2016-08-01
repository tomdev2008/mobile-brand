/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 上午11:44:16 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.server.GoodsCenterService;
import com.xiu.facade.hession.ProductHessionService;
import com.xiu.facade.model.GiftPack;
import com.xiu.facade.model.Result;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIProductManager;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 商品中心EI实现类 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-28 上午11:44:16 
 * ***************************************************************
 * </p>
 */
@Service
public class EIProductManagerImpl implements EIProductManager {
	private static final Logger LOGGER = Logger.getLogger(EIProductManagerImpl.class);
	
	@Autowired
	private GoodsCenterService goodsCenterService;
	@Autowired
	private ProductHessionService productInfoServiceFacade;

	@Override
	public Map getProductLight(ProductCommonParas commonParas,
			ProductSearchParas searchParas) {
		Assert.notNull(commonParas);
		Assert.notNull(searchParas);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [goods] : goodsCenterService.getProductLight");
			LOGGER.debug("searchParas.getXiuSnList:" + searchParas.getXiuSnList());
			LOGGER.debug("searchParas.getPageStep:" + searchParas.getPageStep());
		}
		
		Map result = null;
		try {
			result = goodsCenterService.getProductLight(commonParas, searchParas);
			if(result.get("Products")!=null){
				List<Product> products = (List<Product>) result.get("Products");
				for (Product product:products) {
					//优先显示英文
					if(product.getBrandOtherName()!=null&&!product.getBrandOtherName().equals("")){
						product.setBrandName(product.getBrandOtherName());
					}
				}	
			}
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROD_GENERAL_ERR, e);
		}

		return result;
	}

	@Override
	public Map searchProduct(ProductCommonParas commonParas, ProductSearchParas searchParas) {
		Assert.notNull(commonParas);
		Assert.notNull(searchParas);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [goods] : goodsCenterService.getProductLight");
			LOGGER.debug("searchParas.getXiuSnList:" + searchParas.getXiuSnList());
			LOGGER.debug("searchParas.getPageStep:" + searchParas.getPageStep());
		}

		Map result = null;
		try {
			result = goodsCenterService.searchProduct(commonParas, searchParas);
		} catch (Exception e) {
			throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROD_GENERAL_ERR, e);
		}
		
		return result;
	}

	/**
	 * 查询商品是否支持礼品包装
	 */
	public Map getProductSupportWrap(String goodsSn) {
		Assert.notNull(goodsSn);
		
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("invoke remote interface [productweb] : productInfoServiceFacade.isSupportProductWrap");
			LOGGER.debug("goodsSn:" + goodsSn);
		}
		
		Map resultMap = new HashMap();
		try {
			boolean result = productInfoServiceFacade.isSupportProductWrap(goodsSn);
			resultMap.put("status", result);
		} catch (Exception e) {
			LOGGER.error("查询商品是否支持礼品包装异常，接口: productInfoServiceFacade.isSupportProductWrap" + e.getMessage());
			resultMap.put("status", false);	//默认false
		}
		return resultMap;
	}

	@Override
	public Map<String,Boolean>  getProductSupportWrapList(List<String> goodsSns) {
		Map<String,Boolean> snResultMap=new HashMap<String,Boolean>();
		Result result = null;
		try {
            // 接口调用
            result = productInfoServiceFacade.isSupportProductWrapByList(goodsSns);
            if(result==null || !result.isSuccess()){
//				LOGGER.error("调用礼品包装goodsSn批量查询接口,className={},method={},入参={}",new Object[]{this.getClass().getName(),"getGiftList",goodsSns.toArray(new String[]{})});
//				throw PortalExceptionFactory.buildPortalEIRuntimeException(ErrConstants.EIErrorCode.EI_INV_GENERAL_ERR, result!=null?result.getErrorCode():"","method=getGiftList goodsSn批量查询礼品包装失败");
			}
			if(result.isSuccess())
			{
				List<GiftPack> gifs=result.getGiftPackResultList();
				if(gifs!=null&&gifs.size()>0){
					for (GiftPack g:gifs) {
						snResultMap.put(g.getProductSn(), g.isSupport());
					}
				}
				return snResultMap;
			}
        } catch (Throwable e) {
        	throw ExceptionFactory.buildEIRuntimeException(
					ErrConstants.EIErrorCode.EI_PROD_GENERAL_ERR, e);
        }
		return null;

	}
	
}
