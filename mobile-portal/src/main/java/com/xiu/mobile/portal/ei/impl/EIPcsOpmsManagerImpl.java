package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.ei.EIPcsOpmsManager;
import com.xiu.pcs.opms.facade.MutilPartnerService;
import com.xiu.pcs.opms.facade.dto.MutilPartnerInfo;
import com.xiu.pcs.opms.facade.vo.ProdBaseInfoVo;
import com.xiu.pcs.opms.facade.vo.SkuCustomInfoVo;
import com.xiu.pcs.opms.facade.vo.SnCustomInfoVo;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

@Service("eIPcsOpmsManager")
public class EIPcsOpmsManagerImpl implements EIPcsOpmsManager {
	
	private Logger logger = Logger.getLogger(EIPcsOpmsManagerImpl.class);
	
	@Autowired
	MutilPartnerService mutilPartnerService;

	@Override
	public MutilPartnerInfo getMutilPartnerInfo(String goodSn) {
		MutilPartnerInfo mutilPartnerInfo  = null;
		try{
			//非实时接口
			mutilPartnerInfo = mutilPartnerService.getMutilPartnerInfo(goodSn, "WIRELESS");
		}catch(Exception e){
			logger.error("调用一品多商接口异常，goodSn：" + goodSn, e);
		}
		return mutilPartnerInfo;
	}

	@Override
	public Map<String, Object> getSkuRealtime(String goodsSn, String goodsSku) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", false);
		
		SkuCustomInfoVo skuCustomInfoVo = new SkuCustomInfoVo();
		skuCustomInfoVo.setProductSn(goodsSn);
		skuCustomInfoVo.setSkuCode(goodsSku);
		MutilPartnerInfo mutilPartnerInfo  = null;
		try{
			mutilPartnerInfo = mutilPartnerService.getSkuRealtime(skuCustomInfoVo, "WIRELESS");
		}catch(Exception e){
			logger.error("调用一品多商接口获取SKU库存异常，goodsSku：" + goodsSku, e);
		}
		if(mutilPartnerInfo != null
				&& mutilPartnerInfo.getSkuInfo() != null){
			resultMap.put("result", true);
			Map<String, SkuCustomInfoVo> skuMap = mutilPartnerInfo.getSkuInfo();
			for (Iterator<String> iterator = skuMap.keySet().iterator(); iterator.hasNext();) {
				String sku = iterator.next();
				SkuCustomInfoVo skuCustomInfo = skuMap.get(sku);
				ProdBaseInfoVo prodBaseInfoVo = mutilPartnerInfo.getBaseInfo();
				Map<String, SnCustomInfoVo> snMap = mutilPartnerInfo.getSnInfo();
				resultMap.put("isChange", !sku.equals(goodsSku));
				resultMap.put("goodsSku", sku);
				resultMap.put("goodsSn", skuCustomInfo.getProductSn());
				resultMap.put("goodsId", skuCustomInfo.getProductId());
				resultMap.put("stock", skuCustomInfo.getStore());
				resultMap.put("goodsName", "");
				resultMap.put("zsPrice", "");
				if(prodBaseInfoVo != null){
					resultMap.put("goodsName", prodBaseInfoVo.getPrdName());
				}
				if(snMap != null){
					SnCustomInfoVo snCustomInfoVo = snMap.get(skuCustomInfo.getProductSn());
					ItemSettleResultDO itemSettleResultDO = snCustomInfoVo.getItemSettleResultDO();
					if(itemSettleResultDO != null && itemSettleResultDO.getDiscountPrice() > 0){
						resultMap.put("zsPrice", getPrice(String.valueOf(itemSettleResultDO.getDiscountPrice()/100.0)));
					}
				}
				break;
			}
			logger.info("调用一品多商裁决接口，调用参数：goodsSn：" + goodsSn + "，goodsSku：" + goodsSku
					+ "，返回信息：" + resultMap);
		}
		return resultMap;
	}
	
	/**
	 * 处理后缀为.00 和.0的价格
	 * @param price
	 * @return
	 */
	private String getPrice(String price) {
		if(StringUtils.isNotBlank(price)) {
			if(price.endsWith(".00")) {
				price = price.substring(0, price.length() - 3);
			}
			if(price.endsWith(".0")) {
				price = price.substring(0, price.length() - 2);
			}
			if(price.indexOf(".") > -1 && price.endsWith("0")){
				price = price.substring(0, price.length() - 1);
			}
		}
		return price;
	}
	
}
