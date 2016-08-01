package com.xiu.mobile.simple.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xiu.channel.inventory.api.InventoryService;
import com.xiu.channel.inventory.api.dto.InventorySNTotalResponse;
import com.xiu.channel.inventory.api.dto.InventorySkuTotalResponse;
import com.xiu.channel.inventory.api.dto.QueryInventoryBatchResponse;
import com.xiu.channel.inventory.api.dto.QueryInventoryRequest;
import com.xiu.channel.inventory.api.dto.QueryInventoryResponse;
import com.xiu.channel.inventory.api.dto.QueryInventorySkuBatchResponse;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.server.GoodsCenterService;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.service.IProductService;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 商品service
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月20日 下午2:42:44
 * </p>
 ****************************************************************
 */
@Component
public class ProductServiceImpl implements IProductService {

	private static Log logger = LogFactory.getLog(ProductServiceImpl.class);

	@Autowired
	private GoodsCenterService goodsCenterService;
	@Autowired
	private InventoryService inventoryService;
	//渠道中心限制每次最大100个sku
    private static final int queryPageSizeForChannelWeb = 100;
    
	@Override
	public Product loadProduct(String goodsSn) {
		List<Product> products = batchLoadProductsOld(goodsSn);
		if (products == null || products.isEmpty()) {
			return null;
		}
		return products.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Product> batchLoadProductsOld(String goodsSnBag) {
		ProductSearchParas productSearchParas = new ProductSearchParas();
		productSearchParas.setXiuSnList(goodsSnBag);
		productSearchParas.setPageStep(50);

		ProductCommonParas productCommonParas = new ProductCommonParas();
		productCommonParas.setStoreID(Integer
				.parseInt(GlobalConstants.STORE_ID));

		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [goods] : goodsCenterService.searchProduct - 查询商品详情, goodsn: "
					+ goodsSnBag);
		}

		Map<String, Object> result = goodsCenterService.searchProduct(
				productCommonParas, productSearchParas);

		List<Product> products = (List<Product>) result.get("Products");
		if (products == null || products.isEmpty()) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(products);
		}

		return products;
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> batchLoadProducts(String goodsSnBag) {
		ProductSearchParas productSearchParas = new ProductSearchParas();
		productSearchParas.setXiuSnList(goodsSnBag);
		productSearchParas.setPageStep(50);

		ProductCommonParas productCommonParas = new ProductCommonParas();
		productCommonParas.setStoreID(Integer
				.parseInt(GlobalConstants.STORE_ID));

		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [goods] : goodsCenterService.getProductLight - 查询商品详情, goodsn: "
					+ goodsSnBag);
		}

//		Map<String, Object> result = goodsCenterService.searchProduct(productCommonParas, productSearchParas);
		Map<String, Object> result = goodsCenterService.getProductLight(productCommonParas, productSearchParas);

		List<Product> products = (List<Product>) result.get("Products");
		if (products == null || products.isEmpty()) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(products);
		}

		return products;
	}

	@Override
	public int queryInventoryBySku(String sku) {
		try {
			QueryInventoryRequest queryInventoryRequest = new QueryInventoryRequest();
			queryInventoryRequest.setSkuCode(sku);
			queryInventoryRequest.setChannelCode(GlobalConstants.CHANNEL_ID);

			if (logger.isDebugEnabled()) {
				logger.debug("invoke remote interface [channel] : inventoryService.inventoryQueryBySkuCodeAndChannelCod - 根据sku查询库存");
				logger.debug("input args >>>> skuCode: "
						+ queryInventoryRequest.getSkuCode());
			}

			QueryInventoryResponse rsp = inventoryService
					.inventoryQueryBySkuCodeAndChannelCode(queryInventoryRequest);

			if (rsp.getCode().intValue() == 1) {
				logger.info(rsp.getQty());
				return rsp.getQty();
			} else {
				logger.error("查询库存出错：" + rsp.toString());
				return -9999;
			}
		} catch (Exception e) {
			logger.error("查询库存出错", e);
			return -9999;
		}
	}

	public GoodsCenterService getGoodsCenterService() {
		return goodsCenterService;
	}

	public void setGoodsCenterService(GoodsCenterService goodsCenterService) {
		this.goodsCenterService = goodsCenterService;
	}

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@Override
	public List<Product> batchLoadProductsLight(String goodsSnBag) {
		// TODO Auto-generated method stub
		ProductSearchParas productSearchParas = new ProductSearchParas();
		productSearchParas.setXiuSnList(goodsSnBag);
		productSearchParas.setPageStep(50);

		ProductCommonParas productCommonParas = new ProductCommonParas();
		productCommonParas.setStoreID(Integer
				.parseInt(GlobalConstants.STORE_ID));

		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [goods] : goodsCenterService.searchProductLight - 查询商品详情, goodsn: "
					+ goodsSnBag);
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> result = goodsCenterService.searchProductLight(
				productCommonParas, productSearchParas);

		@SuppressWarnings("unchecked")
		List<Product> products = (List<Product>) result.get("Products");
		if (products == null || products.isEmpty()) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(products);
		}
		return products;
	}

	@Override
	public Map<String, Integer> queryInventoryBySkuList(List<String> skuCodeRequestList) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (skuCodeRequestList != null) {
			// this.inventoryService.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, skuCodeRequestList.subList(0, 100));
			// this.queryInventoryBySkuList(skuCodeRequestList.subList(100, skuCodeRequestList.size()));
			
			int size = skuCodeRequestList.size();
			// 渠道中心限制每次最大100个sku，这里先计算出循环次数
			int loopNum = size / queryPageSizeForChannelWeb;
			if (size % queryPageSizeForChannelWeb != 0) {
				loopNum++;
			}
			for (int i = 0; i < loopNum; i++) {
				int fromIndex = queryPageSizeForChannelWeb*i;
				int toIndex = size;
				if(i<loopNum-1){
					toIndex = queryPageSizeForChannelWeb*(i+1);
				}
				List queryList = skuCodeRequestList.subList(fromIndex, toIndex);
				QueryInventorySkuBatchResponse rsp = this.inventoryService.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID,queryList);
				if (rsp.getCode().intValue() == 1) {
					List<InventorySkuTotalResponse> list = rsp.getInventoryList();
					if (list != null && list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							// 将sku的库存值放到map中，sukcode为key，库存数量为value
							map.put(list.get(j).getSkuCode(), list.get(j).getQty());
						}
					}
				} else {
					logger.error("查询库存出错：" + rsp.toString());
				}
			}
		

		}
		return map;
	}
	
	
	@Override
	public Map<String, Integer> queryInventoryByCodeList(List<String> codeRequestList) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		if (codeRequestList != null) {
			// this.inventoryService.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, skuCodeRequestList.subList(0, 100));
			// this.queryInventoryBySkuList(skuCodeRequestList.subList(100, skuCodeRequestList.size()));
			
			int size = codeRequestList.size();
			// 渠道中心限制每次最大100个sku，这里先计算出循环次数
			int loopNum = size / queryPageSizeForChannelWeb;
			if (size % queryPageSizeForChannelWeb != 0) {
				loopNum++;
			}
			for (int i = 0; i < loopNum; i++) {
				int fromIndex = queryPageSizeForChannelWeb*i;
				int toIndex = size;
				if(i<loopNum-1){
					toIndex = queryPageSizeForChannelWeb*(i+1);
				}
				List queryList = codeRequestList.subList(fromIndex, toIndex);
				QueryInventoryBatchResponse rsp = this.inventoryService.inventoryQueryByProductCodeList(GlobalConstants.CHANNEL_ID,queryList);
				if (rsp.getCode().intValue() == 1) {
					List<InventorySNTotalResponse> list = rsp.getInventoryList();
					if (list != null && list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							// 将code的库存值放到map中，code为key，库存数量为value
							map.put(list.get(j).getProductCode(),list.get(j).getQty());
						}
					}
				} else {
					logger.error("查询库存出错：" + rsp.toString());
				}
			}
		

		}
		return map;
	}

}
