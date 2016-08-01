/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.core.ei.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.common.util.HttpUtils;
import com.xiu.mobile.core.ei.EIMbrandMgtManager;
import com.xiu.mobile.core.model.GoodsMgtVo;

/** 
 * 
* @Description: TODO(搜索接口) 
* @author haidong.luo@xiu.com
* @date 2015年12月18日 下午6:26:05 
*
 */
@Service("eiMbrandMgtManager")
public class EIMbrandMgtManagerImpl implements EIMbrandMgtManager {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(EIMbrandMgtManagerImpl.class);
	
	private static final String mbrandQueryUrl="http://mbrand.xiu.com/query/price";
	
	private static final String mbrandFilterUrl = "http://mbrand.xiu.com/mutilPro/query";
	
	public void filterGoodsByMbrand(String goodsSns, List<GoodsMgtVo> goodsVoList){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sn", goodsSns);
		try{
			String resultStr = HttpUtils.postMethod(mbrandFilterUrl, paramMap, "utf-8");
			JSONObject json = JSONObject.fromObject(resultStr);
			JSONObject head = (JSONObject)json.get("head");
			String code = (String)head.get("code");
			if("0".equals(code)){
				JSONArray resultJsons = (JSONArray) json.get("data");
				int size = resultJsons.size();
				for (int i = 0; i < size; i++) {
					JSONObject jsonobj = (JSONObject) resultJsons.get(i);
					String goodsId = (String) jsonobj.get("goodsId");
					String goodsSn = (String) jsonobj.get("goodsSn");
					String goodName = (String) jsonobj.get("goodName");
					String img = (String) jsonobj.get("img");
					String brandNameEn = "";
					if(jsonobj.containsKey("brandNameEn")&&!(jsonobj.get("brandNameEn") instanceof JSONNull)){
						brandNameEn = (String) jsonobj.get("brandNameEn");
					}
					String brandNameCn = "";
					if(jsonobj.containsKey("brandNameCn")&&!(jsonobj.get("brandNameCn") instanceof JSONNull)){
						 brandNameCn = (String) jsonobj.get("brandNameCn");
					}
					int onSale = (Integer) jsonobj.get("onSale");
					Double xiuPrice = (Double) jsonobj.get("xiuPrice");
					Double mkPrice = (Double) jsonobj.get("mkPrice");
					Double finalPrice = (Double) jsonobj.get("finalPrice");
					
					GoodsMgtVo goodsVo = new GoodsMgtVo();
					goodsVo.setGoodsId(goodsId);
					goodsVo.setGoodsSn(goodsSn);
					goodsVo.setGoodsName(goodName);
					goodsVo.setGoodsImg(img);
					goodsVo.setBrandEnName(brandNameEn);
					goodsVo.setBrandCNName(brandNameCn);
					goodsVo.setStateOnsale(onSale);
					if(finalPrice<xiuPrice){
						xiuPrice=finalPrice;
					}
					goodsVo.setZsPrice(String.valueOf(xiuPrice));
					goodsVo.setPrice(String.valueOf(mkPrice));
					goodsVo.setDiscount(getDiscountString(mkPrice, xiuPrice));
					// 取消所有活动商品的标志
					goodsVo.setIsActivityGoods(0);
					
					goodsVoList.add(goodsVo);
				}
			}else{
				LOGGER.error("调用mbrand查询商品价格接口异常:code:"+code);
			}
			
		}catch(Exception e){
			LOGGER.error("调用mbrand查询商品价格接口异常"+e.getMessage());
		}
	}
	
	/**
	 * 
	 * 根据市场价和走秀价,计算出折扣信息
	 * (如果走秀价 > 市场价 显示"")
	 * (折扣信息四舍五入处理,如6.54折显示6.5折,7.49折显示7.5折)
	 * 
	 * @param price 市场价
	 * @param zsPrice 走秀价
	 * @return 折扣信息
	 */
	private String getDiscountString(double price, double zsPrice) {
		if (price == 0.0) {
			return "";
		}
		
		if (zsPrice >= price) {
			return "";
		}
		
		long discount = Math.round((zsPrice / price) * 100);
		if (discount % 10 == 0) {
			return "" + (discount / 10) + "折";
		}
		
		return "" + (discount / 10.0) + "折";
	}

}
