package com.xiu.mobile.core.handler.h5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xiu.mobile.core.common.util.HttpUtils;
import com.xiu.mobile.core.constants.EnumContants.DataType;
import com.xiu.mobile.core.model.WapH5ModuleData;


public abstract class AbstractH5DataTypeHandler {
	
	private static Logger logger = Logger.getLogger(AbstractH5DataTypeHandler.class);
	
	public static int[] XIUSTATIC_NUMS = {6,7,8};
	
	private static final String mbrandFilterUrl = "http://mbrand.xiu.com/mutilPro/query";
	
	/**
	 * 根据数据索引查询数据
	 * @param wapH5ModuleData
	 * @return
	 */
	public abstract Map<String, Object> assembleModuleData(List<WapH5ModuleData> moduleDataList);
	
	
	protected List<WapH5ModuleData> filterGoodsByMbrand(String goodsSns){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("sn", goodsSns);
		
		List<WapH5ModuleData> moduleDataList = new ArrayList<WapH5ModuleData>();
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
//					String goodsSn = (String) jsonobj.get("goodsSn");
					String goodName = (String) jsonobj.get("goodName");
					String img = (String) jsonobj.get("img");
					Double mkPrice = (Double) jsonobj.get("mkPrice");
					Double xiuPrice = (Double) jsonobj.get("xiuPrice");
					Double finalPrice = (Double) jsonobj.get("finalPrice");
					if(finalPrice < xiuPrice){
						mkPrice = xiuPrice;
						xiuPrice = finalPrice;
					}
					
					WapH5ModuleData moduleData = new WapH5ModuleData();
					moduleData.setLink(goodsId);
					moduleData.setTitle(goodName);
					moduleData.setOriginalPrice(getPrice(String.valueOf(mkPrice)));
					moduleData.setFinalPrice(getPrice(String.valueOf(xiuPrice)));
					moduleData.setImg(processImg(goodsId, img));
					moduleData.setDataType(DataType.Goods.getKey());
					
					moduleDataList.add(moduleData);
				}
			}else{
				logger.error("调用mbrand查询商品价格接口异常，goodsSns:" + goodsSns + ", code: " + code);
			}
		}catch(Exception e){
			logger.error("调用mbrand查询商品价格接口异常，goodsSns:" + goodsSns, e);
		}
		return moduleDataList;
	}
	
	/**
	 * 处理商品图片
	 * @param goodsId
	 * @param goodImg
	 * @return
	 */
	protected String processImg(String goodsId, String goodImg){
    	int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()));
		String imgUrl = "http://" + XIUSTATIC_NUMS[index%3] + ".xiustatic.com";
		String[] temp = goodImg.split("/");
		String imgName = temp[temp.length - 1];
		String url = goodImg.split(imgName)[0] + imgName + "/g1_234_312.jpg";
		String[] sName = url.split("upload");

		return imgUrl + "/upload" + sName[1];
	}
	
	/**
	 * 处理后缀为.00 和.0的价格
	 * @param price
	 * @return
	 */
	protected String getPrice(String price) {
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
