package com.xiu.mobile.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.commerce.hessian.server.GoodsCenterService;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.constants.GoodsConstant;
import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.core.dao.TopicDao;
import com.xiu.mobile.core.ei.EIChannelInventoryMgtManager;
import com.xiu.mobile.core.ei.EIMbrandMgtManager;
import com.xiu.mobile.core.model.CrumbsVo;
import com.xiu.mobile.core.model.DeliverInfo;
import com.xiu.mobile.core.model.GoodsDetailSkuMgtItem;
import com.xiu.mobile.core.model.GoodsDetailsMgtVO;
import com.xiu.mobile.core.model.GoodsMgtVo;
import com.xiu.mobile.core.model.GoodsSkuMgtItem;
import com.xiu.mobile.core.model.PriceComponentMgtVo;
import com.xiu.mobile.core.model.TopicFilterItemVO;
import com.xiu.mobile.core.model.TopicFilterVO;
import com.xiu.mobile.core.service.ICrumbsService;
import com.xiu.mobile.core.service.IMemcachedMgtService;
import com.xiu.mobile.core.service.ITopicFilterService;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.sales.common.balance.dataobject.PriceSettlementDO;
import com.xiu.sales.common.balance.dataobject.ProdSettlementDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleActivityResultDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

/**
 * 
* @Description: TODO(卖场过滤) 
* @author haidong.luo@xiu.com
* @date 2016年4月13日 下午3:45:54 
*
 */
@Transactional
@Service("topicFilterService")
public class TopicsFilterServiceImpl implements ITopicFilterService {

	private Logger logger = Logger.getLogger(TopicsFilterServiceImpl.class);


	private final Integer  CACHE_TIME=25;
	
	@Autowired
	private IMemcachedMgtService memcachedService;
	@Autowired
	private TopicDao topicActivityDao;
	@Autowired
	private ICrumbsService crumbsService;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private GoodsCenterService goodsCenterService;
	@Autowired
	private EIMbrandMgtManager eiMbrandMgtManager;
	@Autowired
	private EIChannelInventoryMgtManager eiChannelInventoryMgtManager;
	
	
	/**
	 * 异步处理卖场筛选数据
	 * @param map
	 */
	public List<TopicFilterVO>  getTopicGoodFilter(Map<String,String> map) {
		Map filterMap=new HashMap();
		Map filterNameMap=new HashMap();//筛选名称缓存
		String activityId=map.get("activityId");
		String filterIds=map.get("filterIds");
		String activityIdKey=getActivityFilterKey(activityId);
		
		//查询缓存是否有数据
		memcachedService.deleteTopicFilterList(activityIdKey);
		filterMap= memcachedService.getTopicFilterList(activityIdKey);
		if(filterMap==null){
			filterMap=addTopicGoodFilterCache(activityId);
		}
		filterNameMap=memcachedService.getTopicFilterName(activityIdKey+"_name");
		
    	String[] filterIdArr=filterIds.split(",");
    	StringBuilder filterIdSb=new StringBuilder();
    	for (int i = 0; i < filterIdArr.length; i++) {
    		if(filterIdSb.toString().length()>0){
    			filterIdSb.append(",");
    		}
    		if(!filterIdArr[i].equals("0")){
    			filterIdSb.append(filterIdArr[i]);
    		}
    	}
    	filterIds=filterIdSb.toString();
		
		//检索出所有符合条件的走秀码
		List<String> filterXiuSns=getFilterXiuSn(filterIds,filterMap);
		
		//根据走秀码构造每个筛选类型的数据
		  List<TopicFilterVO> filters=new ArrayList<TopicFilterVO>();
		  TopicFilterVO type=new TopicFilterVO();
          type.setTypeName("分类");
          String categorykey= "TopicActivity"+activityId+"Fileter_category|";
          type= getFilterItems(filterMap,type,categorykey,filterXiuSns,null,filterNameMap);
          filters.add(type);
          
          type=new TopicFilterVO();
          type.setTypeName("品牌");
          String brandNamekey= "TopicActivity"+activityId+"Fileter_brandName|";
          type= getFilterItems(filterMap,type,brandNamekey,filterXiuSns,null,filterNameMap);
          filters.add(type);
          
          type=new TopicFilterVO();
          type.setTypeName("价格");
          String pricekey= "TopicActivity"+activityId+"Fileter_price|";
          type= getFilterItems(filterMap,type,pricekey,filterXiuSns,null,filterNameMap);
          filters.add(type);
          
          type=new TopicFilterVO();
          type.setTypeName("尺码");
          String sizekey= "TopicActivity"+activityId+"Fileter_size|";
          type= getFilterItems(filterMap,type,sizekey,filterXiuSns,filterIds,filterNameMap);
          filters.add(type);
          
          type=new TopicFilterVO();
          type.setTypeName("颜色");
          String colorkey= "TopicActivity"+activityId+"Fileter_color|";
          type= getFilterItems(filterMap,type,colorkey,filterXiuSns,filterIds,filterNameMap);
          filters.add(type);
          
          type=new TopicFilterVO();
          type.setTypeName("发货地");
          String cityNamekey= "TopicActivity"+activityId+"Fileter_cityName|";
          type= getFilterItems(filterMap,type,cityNamekey,filterXiuSns,null,filterNameMap);
          filters.add(type);
          
		return filters;
		
	}
	
	/**
	 * 获取卖场筛选key
	 * @param activityId
	 * @return
	 */
	public String getActivityFilterKey(String activityId){
		return 	"TopicActivity"+activityId+"Fileter";

	}
	
	/**
	 * 获取筛选后的走秀码
	 * @param filterIds
	 * @param filterMap
	 * @return
	 */
	public List<String> getFilterXiuSn(String filterIds,Map filterMap){
		String filterArr[]= filterIds.split(",");
		List<String> returnFilterXiuSns=new ArrayList<String>();//过滤筛选后的走秀码
		Map<String,List<String>> typeFilterMap=new HashMap<String,List<String>>();
		Map<String,String> typeFilterMapTemp=new HashMap<String,String>();
		
		Map<String,String> filterXiuSnMap=new HashMap<String,String>();
		for (int i = 0; i < filterArr.length; i++) {
			//先按不同筛选分类筛选合适的走秀码
			String typeVlaueKey=filterArr[i];
			String typeKey=typeVlaueKey.substring(0,typeVlaueKey.indexOf("|")+1);
			List<String> typeFilterList=typeFilterMap.get(typeKey);//每种筛选分类
			if(typeFilterList==null){
				typeFilterList=new ArrayList<String>();
			}
			 Map<String,List> typeMap=(Map<String,List>)filterMap.get(typeKey);
			 if(typeMap!=null&&typeMap.size()>0){
				 List<String> xiuSns= typeMap.get(typeVlaueKey);
				 if(xiuSns!=null){
					 if(i==0){
						 returnFilterXiuSns.addAll(xiuSns);
					 }else{
						 returnFilterXiuSns=new ArrayList<String>();
					 }
						for (int j = 0; j < xiuSns.size(); j++) {
							if(i==0){
								typeFilterMapTemp.put(xiuSns.get(j), xiuSns.get(j));
							}else{
								if(typeFilterMapTemp.get(xiuSns.get(j))!=null){
									returnFilterXiuSns.add(xiuSns.get(j));
								}
							}
						}
			    }
			 }
		}
		
		return returnFilterXiuSns;
	}
	
	public TopicFilterVO getFilterItems(Map filterMap,TopicFilterVO type,String key,
			List<String> filterXiuSns,String filterIds,Map filterNameMap){
		 Map<String,List> cityNameMap=(Map<String,List>)filterMap.get(key);
		 List<TopicFilterItemVO> allitems=new ArrayList<TopicFilterItemVO>();
         List<TopicFilterItemVO> items=new ArrayList<TopicFilterItemVO>();
         Integer allNums=0;//所有个数
         String filterArr[]=null;
         if(filterIds!=null&&!filterIds.equals("")){
        	  filterArr= filterIds.split(",");
         }
         for (Map.Entry<String,List> entry : cityNameMap.entrySet()) {  
        	 boolean isMacthType=false;
        	 Integer isAllNotMacht=1;
        	 if(filterArr!=null){
        		 for (int i = 0; i < filterArr.length; i++) { 
        			 if(entry.getKey().equals(filterArr[i])){
        				 isMacthType=true;
        			 }else if(!entry.getKey().substring(0, entry.getKey().indexOf("|")+1)
        					 .equals(filterArr[i].substring(0,filterArr[i].indexOf("|")+1))){
        				 isAllNotMacht=1*isAllNotMacht;
        			 }else{
        				 isAllNotMacht=0*isAllNotMacht;
        			 }
        		 }
        	 }else{
        		 isMacthType=true;
        	 }
        	 if(isMacthType||isAllNotMacht==1){
		        	TopicFilterItemVO item=new TopicFilterItemVO();
		         	item.setFilterItemId(entry.getKey());
		         	item.setFilterItemName((String)filterNameMap.get(entry.getKey()));
		         	Integer num=entry.getValue().size();
		         	if(filterXiuSns!=null&&filterXiuSns.size()>0){
		         		num=0;
		         		List<String> xiuSns=(List<String>)entry.getValue();
			         	for (int i = 0; i < xiuSns.size(); i++) {
							for (int j = 0; j < filterXiuSns.size(); j++) {
								if(xiuSns.get(i).equals(filterXiuSns.get(j))){
									num++;
									break;
								}
							}
						}
		         	}
		         	if(num>0){
		         		allNums=allNums+num;
		         		item.setGoodsNum(num);
		         		items.add(item);
		         	}
        	 }
         }  
        TopicFilterItemVO allitem=new TopicFilterItemVO();
        allitem.setFilterItemId("0");
        allitem.setFilterItemName("全部");
        allitem.setGoodsNum(allNums);
        allitems.add(allitem);
        allitems.addAll(items);
        
        type.setFilterItemList(allitems);
        return type;
	}
	
	
	
	/**
	 * 添加过滤数据到缓存
	 * @param map
	 * @return
	 */
	public Map addTopicGoodFilterCache(String activityId){
		logger.info("添加卖场过滤数据到缓存:activityId:"+activityId+"开始");
		String activityIdKey=getActivityFilterKey(activityId);
		Map filterMap=new HashMap();
		//查询卖场下的所有商品ID
		List<String> goodsIdList = topicActivityDao.getGoodsMgtListByTopicId(activityId);
		
		//超过1000个商品则不处理
		if(goodsIdList != null&&goodsIdList.size()>1000){
			return filterMap;
		}
		StringBuffer goodIsSb=new StringBuffer();
		Map activityIdKeyNameMap=new HashMap();
		if(goodsIdList != null && goodsIdList.size() > 0) {
			for(String goodId : goodsIdList) {
				goodIsSb.append(goodId).append(",");
			}
			//截取最后一个逗号
			if(goodIsSb.length() > 1) {
				goodIsSb.deleteCharAt(goodIsSb.length() - 1);
			}
		}
		
		//获取商品的分类品牌、价格、颜色、尺码、来源地
		String goodsIds = goodIsSb.toString();
		    //获取走秀码
		if(StringUtils.isNotBlank(goodsIds)) {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("activityId", activityId);
			String goodArr[]= goodsIds.split(",");
			List<String> goodsSnList = new ArrayList<String>();
			List<String> tempGoodsSnList= new ArrayList<String>();
			Integer length=goodArr.length;
			for (int i = 0; i < length; i++) {
				tempGoodsSnList.add(goodArr[i]);
				if(i>0&&i%500==0){
					paraMap.put("goodsIdArr",tempGoodsSnList);
					List<String> tempResultList = topicActivityDao.getGoodsSnMgtListByTopicAndGoodsIds(paraMap);
					goodsSnList.addAll(tempResultList);
					tempGoodsSnList= new ArrayList<String>();
				}else if(i==length-1){
					paraMap.put("goodsIdArr",tempGoodsSnList);
					List<String> tempResultList  = topicActivityDao.getGoodsSnMgtListByTopicAndGoodsIds(paraMap);
					goodsSnList.addAll(tempResultList);
				}
			}
			StringBuilder sb=new StringBuilder();
			for (int i = 0; i < goodsSnList.size(); i++) {
				if(i>0){
					sb.append(",");
				}
				sb.append(goodsSnList.get(i));
			}
			
			//
			Map fingGoodMap=new HashMap();
			fingGoodMap.put("goodsSns", sb.toString());
			 List<GoodsDetailsMgtVO>  gds=getTopicGoodsByGoodsSn(fingGoodMap);
			 if(gds!=null&&gds.size()>0){
				 Integer gdsize=gds.size();
				 for (int i = 0; i < gdsize; i++) {
					 GoodsDetailsMgtVO gd= gds.get(i);
					 String  goodSn=gd.getGoodsSn();
					 String categoryName="其他";
					 if(gd.getCategoryName()!=null){
					  categoryName=gd.getCategoryName();
					 }
					 String  categoryNameKey=activityIdKey+"_category|";//分类
					 String  categoryNameGoodKey=categoryNameKey+categoryName;
					 String categoryNameGoodKeyCode=categoryNameKey+categoryNameGoodKey.hashCode()+"";
					 activityIdKeyNameMap.put(categoryNameGoodKeyCode, categoryName);
					 getFilterMap(filterMap,goodSn,categoryNameKey,categoryNameGoodKeyCode);
					 
					 List<GoodsDetailSkuMgtItem> gdsku=gd.getSkuList();//颜色 尺码
					 if(gdsku!=null){
						 for (int j = 0; j < gdsku.size(); j++) {
							 //颜色
							 GoodsDetailSkuMgtItem skuitem=gdsku.get(j);
							 String color=skuitem.getColor();
							 String  colorKey=activityIdKey+"_color|";
							 String  colorGoodKey=colorKey+color;
							 String colorGoodKeyCode=colorKey+colorGoodKey.hashCode()+"";
							 activityIdKeyNameMap.put(colorGoodKeyCode, color);
							 getFilterMap(filterMap,goodSn,colorKey,colorGoodKeyCode);
							 
							 //尺码
							 String size=skuitem.getSize();
							 String  sizeKey=activityIdKey+"_size|";
							 String  sizeGoodKey=sizeKey+size;
							 String sizeGoodKeyCode=sizeKey+sizeGoodKey.hashCode()+"";
							 activityIdKeyNameMap.put(sizeGoodKeyCode, size);
							 getFilterMap(filterMap,goodSn,sizeKey,sizeGoodKeyCode);
						 }
					 }
						 
					 String  cityName="其他";
					 if(gd.getDeliverInfo()!=null){
						   cityName=gd.getDeliverInfo().getCity();//来源地
					 }
					 String  cityNameKey=activityIdKey+"_cityName|";
					 String  cityNameGoodKey=cityNameKey+cityName;
					 String cityNameGoodKeyCode=cityNameKey+cityNameGoodKey.hashCode()+"";
					 activityIdKeyNameMap.put(cityNameGoodKeyCode, cityName);
					 getFilterMap(filterMap,goodSn,cityNameKey,cityNameGoodKeyCode);
					 
					String  brandName="其他";
					if(gd.getBrandEnName()!=null){
						brandName=gd.getBrandEnName();
					}else if(gd.getBrandCNName()!=null){
						brandName=gd.getBrandCNName();
					}
					 String  brandNameKey=activityIdKey+"_brandName|";//品牌
					 String  brandNameGoodKey=brandNameKey+brandName;
					 String brandNameGoodKeyCode=brandNameKey+brandNameGoodKey.hashCode()+"";
					 activityIdKeyNameMap.put(brandNameGoodKeyCode, brandName);
					 getFilterMap(filterMap,gd.getGoodsSn(),brandNameKey,brandNameGoodKeyCode);
					 
					 //价格
					 String  price=gd.getZsPrice();//价格
					 price=getPriceRange(price);
					 String  priceKey=activityIdKey+"_price|";
					 String  priceGoodKey=priceKey+price;
					 String priceGoodKeyCode=priceKey+priceGoodKey.hashCode()+"";

					 activityIdKeyNameMap.put(priceGoodKeyCode, price);
					 getFilterMap(filterMap,gd.getGoodsSn(),priceKey,priceGoodKeyCode);
				 }
			 }
		}
		
		memcachedService.addTopicFilterName(activityIdKey+"_name", activityIdKeyNameMap, CACHE_TIME);
		memcachedService.addTopicFilterList(activityIdKey, filterMap, CACHE_TIME);
		logger.info("添加卖场过滤数据到缓存:activityId:"+activityId+"结束");
	    return filterMap;
	}
	
	/**
	 * 获取价格范围
	 * @param price
	 * @return
	 */
	private String getPriceRange(String price){
		String priceRange="";
		Double p=XiuUtil.getPriceDoubleFormat(price);
		if(p<500){
			priceRange="0-500";
		}else if(p>=500&&p<1000){
			priceRange="500-1000";
		}else if(p>=1000&&p<3000){
			priceRange="1000-3000";
		}else if(p>=3000&&p<5000){
			priceRange="3000-5000";
		}else if(p>=5000&&p<8000){
			priceRange="5000-8000";
		}else {
			priceRange="8000以上";
		}
		return priceRange;
	}
	
	private List<GoodsDetailsMgtVO> getTopicGoodsByGoodsSn(Map<String, Object> map) {

		 List<GoodsDetailsMgtVO> gds=new ArrayList<GoodsDetailsMgtVO>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String goodsSns = (String) map.get("goodsSns");
		
		String[] goodsSnArr=goodsSns.split(",");
		List<String> goodsSnList=new ArrayList<String>();
		for (int i = 0; i < goodsSnArr.length; i++) {
			goodsSnList.add(goodsSnArr[i]);
		}
		
		try {
			//先从mbrand获取品牌和价格
			List<GoodsMgtVo>	goodsList = this.filterGoodsByMbrand(goodsSnList);
			goodsSns="";
			if(goodsList==null||goodsList.size()==0){
				logger.info("调用mbrand查询商品为空:goodsSns:"+goodsSns);
			}
			for (GoodsMgtVo good:goodsList) {
				if(!goodsSns.equals("")){
					goodsSns=goodsSns+",";
				}
				goodsSns=goodsSns+good.getGoodsSn();
			}
			//1.查询商品
			List<Product> productList = batchLoadProducts(goodsSns);
			if(productList == null || productList.size() == 0) {
				logger.info("调用goodsCenterService查询商品为空:goodsSns:"+goodsSns);
				//如果没有找到商品
				resultMap.put("result", false);
				resultMap.put("errorCode","-1");
				return gds;
			}
			for (int i = 0; i < productList.size(); i++) {
				Product product = productList.get(i);
				String goodsSn=product.getXiuSN();
				String productId = String.format("%07d", product.getInnerID());
				
				//2.组装商品基本信息
				GoodsDetailsMgtVO goodsDetail = new GoodsDetailsMgtVO();
				goodsDetail.setGoodsId(productId);
				goodsDetail.setGoodsSn(goodsSn);
				
				// 商品分类
				String categoryName = getGoodsCategoryName(goodsSn);
				goodsDetail.setCategoryName(categoryName);
				
				// 商品sku列表,包含sku码、sku库存数量、商品组图
				List<GoodsSkuMgtItem> goodSkuList = handleSkus(product.getSkus()); // 尺码颜色
				if(goodSkuList!=null&&goodSkuList.size()>0){//有库存情况
					handleSkuData(goodSkuList, product , goodsDetail, null, false);
					
					//4.发货地信息
					Map<String, Object> paraMap = new HashMap<String, Object>();
					paraMap.put("productId", productId);
					DeliverInfo deliverInfo = goodsDao.getDeliverInfoByProduct(paraMap);
					goodsDetail.setDeliverInfo(deliverInfo);
					
					GoodsMgtVo good=null;
					for (int j = 0; j < goodsList.size(); j++) {
						if(goodsList.get(j).getGoodsSn().equals(goodsSn)){
							good=goodsList.get(j);
						}
					}
					
					if(good!=null){//为空说明mbrand系统没有查询到该商品数据
						goodsDetail.setBrandCNName(good.getBrandCNName());
						goodsDetail.setBrandEnName(good.getBrandEnName());
						goodsDetail.setZsPrice(good.getZsPrice());
					}
					gds.add(goodsDetail);
				}
			}
			
			resultMap.put("result", true);
		} catch (Exception e) {
			logger.error("查询商品详情异常，商品goodsSn："+goodsSns+"，错误信息："+e.getMessage());
		}
		
		return gds;
	
	}
	
	private List<GoodsMgtVo> filterGoodsByMbrand(List<String> goodsSnBag) {
		List<GoodsMgtVo> GoodsMgtVoList = new ArrayList<GoodsMgtVo>(); 
		StringBuilder goodsSns = new StringBuilder();
		for (int i = 1; i <= goodsSnBag.size(); i++) {
			goodsSns.append(goodsSnBag.get(i-1)).append(",");
			if(i % 100 == 0 || i == goodsSnBag.size()){
				goodsSns = goodsSns.deleteCharAt(goodsSns.length() - 1);
				eiMbrandMgtManager.filterGoodsByMbrand(goodsSns.toString(), GoodsMgtVoList);
				goodsSns = new StringBuilder();
			}
		}
		return GoodsMgtVoList;
	}
	
	
	private void getFilterMap(Map filterMap,String goodSn,String categoryNameKey,String categoryNameGoodKeyCode){
		 if(filterMap.get(categoryNameKey)==null){
			 Map filterCategoryNameMap=new HashMap();
			 List<String>  categoryNames=new ArrayList<String>();
			 categoryNames.add(goodSn);
			 filterCategoryNameMap.put(categoryNameGoodKeyCode, categoryNames);
			 
			 filterMap.put(categoryNameKey, filterCategoryNameMap);
		 }else{
			Map filterCategoryNameMap= (Map)filterMap.get(categoryNameKey);
			if(filterCategoryNameMap.get(categoryNameGoodKeyCode)==null){
				 List<String>  categoryNames=new ArrayList<String>();
				 categoryNames.add(goodSn);
				 filterCategoryNameMap.put(categoryNameGoodKeyCode, categoryNames);
			}else{
				List<String> categoryNames=(List<String> )filterCategoryNameMap.get(categoryNameGoodKeyCode);
				boolean isExist=false;
				for (int i = 0; i < categoryNames.size(); i++) {
					if(categoryNames.get(i).equals(goodSn)){
						isExist=true;
						i=categoryNames.size();
					}
				}
				if(!isExist){
					categoryNames.add(goodSn);
					filterCategoryNameMap.put(categoryNameGoodKeyCode, categoryNames);
				}
			}
		 }
	}
	
	
	// 处理sku信息
		private List<GoodsSkuMgtItem> handleSkus(Sku[] skus) {
			List<GoodsSkuMgtItem> skuList = new ArrayList<GoodsSkuMgtItem>();
			
			if (skus != null && skus.length > 0) {
				List<String> skuCodeList = new ArrayList<String>();
				for (Sku sku : skus) {
					skuCodeList.add(sku.getSkuSN());
				}
				// 批量获取SKU的库存信息
				Map<String, Integer> skuCodeMap = queryInventoryBySku(skuCodeList);
				for (Sku sku : skus) {
					GoodsSkuMgtItem skuItem = new GoodsSkuMgtItem();
					skuItem.setColor(sku.getColorValue());
					skuItem.setSize(sku.getSizeValue());
					skuItem.setSkuSn(sku.getSkuSN());
					// 添加到返回结果中
					if (skuCodeMap.containsKey(sku.getSkuSN()) && skuCodeMap.get(sku.getSkuSN()) != null) {
						skuList.add(skuItem);
					}
				}
			}

			return skuList;
		}
	
	
		
		/**
		 * 查询库存信息
		 * @param sku
		 * @return
		 */
		public Map<String, Integer> queryInventoryBySku(List<String> skuCodeList) {
			Map<String, Integer> result=new HashMap<String, Integer>();
			try {
				if(skuCodeList!=null&&skuCodeList.size()>0){
					Integer size=skuCodeList.size();
					List<String> tempSku=new ArrayList<String>();
					for (int i = 0; i < size; i++) {
						tempSku.add(skuCodeList.get(i));
						if(i>0&&i%95==0){
							Map<String, Integer> temp= eiChannelInventoryMgtManager
									.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, tempSku);
							result.putAll(temp);
							tempSku=new ArrayList<String>();
						}else if(i==size-1){
							Map<String, Integer> temp= eiChannelInventoryMgtManager
									.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, tempSku);
							result.putAll(temp);
						}
					}
				}
			} catch (Exception e) {
				logger.error("查询库存出错", e);
				return new HashMap<String, Integer>();
			}finally{
				return result;
			}
		}
		
		
	public List<Product> batchLoadProducts(String goodsSnBag) {
		ProductSearchParas productSearchParas = new ProductSearchParas();
		productSearchParas.setXiuSnList(goodsSnBag);
		productSearchParas.setPageStep(50);

		ProductCommonParas productCommonParas = new ProductCommonParas();
		productCommonParas.setStoreID("11");

		if (logger.isDebugEnabled()) {
			logger.debug("invoke remote interface [goods] : goodsCenterService.getProductLight - 查询商品详情, goodsn: " + goodsSnBag);
		}

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
	
	
	/**
	 * 处理商品的Sku数据
	 * @param product
	 * @param deviceType
	 */
	private void handleSkuData(List<GoodsSkuMgtItem> goodSkuList, Product product,
			 GoodsDetailsMgtVO goodsDetail,  String mainSku, boolean isMultiPro) {
//		List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType, isMultiPro, goodSkuList, snMap); // 查询sku图
//		findImgList(skuImagesPairs, goodSkuList); // 配对sku及sku的images：为int[] 1,0，以后备用拼
//		
		// 设置颜色、尺码列表
		List<String> colorList = new ArrayList<String>();
		List<String> sizeList = new ArrayList<String>();
		
		Set<String> colorSet = new HashSet<String>();
		Set<String> sizeSet = new HashSet<String>();
		for(GoodsSkuMgtItem skuItem : goodSkuList) {
			colorSet.add(skuItem.getColor());
			sizeSet.add(skuItem.getSize());
		}
		colorList.addAll(colorSet);
		sizeList.addAll(sizeSet);
//		goodsDetail.setColors(colorList); //颜色
		//尺码排序
		Collections.sort(sizeList);	
//		Collections.sort(sizeList, new ComparatorSizeAsc<String>());
//		goodsDetail.setSizes(sizeList); //尺码
		
		List<GoodsDetailSkuMgtItem> skuList = new ArrayList<GoodsDetailSkuMgtItem>();
		GoodsSkuMgtItem priSku = goodSkuList.get(0);// 默认第一个
		int primColor = colorList.indexOf(priSku.getColor()); //主SKU颜色索引
		int primSize = sizeList.indexOf(priSku.getSize()); //主SKU尺寸索引
		int goodsStock = 0; //库存
		
		//Sku列表按颜色、尺码排序
		List<GoodsSkuMgtItem> sortSkuList = new ArrayList<GoodsSkuMgtItem>();
		if(sizeList != null && sizeList.size() > 0) {
			for(String size : sizeList) {
				for(String color : colorList) {
					for(GoodsSkuMgtItem sku : goodSkuList) {
						if(size.equals(sku.getSize()) && color.equals(sku.getColor())) {
							sortSkuList.add(sku);
							continue;
						}
					}
				}
			}
			goodSkuList = sortSkuList;
		}
		
		for (GoodsSkuMgtItem sku : goodSkuList) {
			int k = colorList.indexOf(sku.getColor()), l = sizeList.indexOf(sku.getSize());
			if(sku.getStock() > 0) {
				//计算库存
				goodsStock = goodsStock + sku.getStock();
			}
			// 颜色尺码和SKU值对应列表
			String key = "c" + k + "s" + l;
			GoodsDetailSkuMgtItem skuItem = new GoodsDetailSkuMgtItem();
			skuItem.setKey(key);
			skuItem.setSkuSn(sku.getSkuSn());
			skuItem.setStock(sku.getStock());
			skuItem.setColor(colorList.get(k));
			skuItem.setSize(sizeList.get(l));
		
			skuList.add(skuItem);
			
			if ((StringUtils.isBlank(mainSku) && 0 == priSku.getStock() && 0 != sku.getStock())
					|| (StringUtils.isNotBlank(mainSku) && sku.getSkuSn().equals(mainSku))) {
				primColor = k;
				primSize = l;
			}
		}
		goodsDetail.setPrimColor(primColor);
		goodsDetail.setPrimSize(primSize);
		goodsDetail.setStock(goodsStock);
		goodsDetail.setSkuList(skuList);
	}
	
	
	/**
	 * 查询价格组成信息
	 * @param product
	 * @return
	 */
	private Map<String, Object> getPriceComponentInfoAndPromotionInfo(ItemSettleResultDO resultDO, String mCatCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 从结算系统获得价格组成
		ProdSettlementDO proSettlement = resultDO.getProdSettlementDO();
		boolean isCustoms = proSettlement.isCustoms();
		PriceSettlementDO priceSettlement = new PriceSettlementDO();
		PriceComponentMgtVo priceComponentVo = new PriceComponentMgtVo();
		// 是否行邮
		priceComponentVo.setIsCustoms(isCustoms);
		if (isCustoms) {
			String activityPriceType = proSettlement.getActivityPriceType();
			// 非0就是用户下单支付的价钱是活动价，显示活动价的价格组成
			if (null != activityPriceType && !"0".equals(activityPriceType)) {
				priceSettlement = proSettlement.getActivityPrice();
				priceComponentVo.setPriceComponentName("活动价");
				priceComponentVo.setTotalPriceComponent(priceSettlement.getPrice() / 100.0);
				priceComponentVo.setBasePrice(priceSettlement.getBasePrice() / 100.0);
				priceComponentVo.setRealCustomsTax(priceSettlement.getRealCustomsTax() / 100.0);
				priceComponentVo.setTransportCost(priceSettlement.getTransportCost() / 100.0);
				priceComponentVo.setEvalCustomsTax(priceSettlement.getEvalCustomsTax() / 100.0);
			} else {
				// 反之就是促销价或走秀价，显示走秀价的价格组成
				priceSettlement = proSettlement.getXiuPrice();
				priceComponentVo.setPriceComponentName("走秀价");
				priceComponentVo.setTotalPriceComponent(priceSettlement.getPrice() / 100.0);
				priceComponentVo.setBasePrice(priceSettlement.getBasePrice() / 100.0);
				priceComponentVo.setRealCustomsTax(priceSettlement.getRealCustomsTax() / 100.0);
				priceComponentVo.setTransportCost(priceSettlement.getTransportCost() / 100.0);
				priceComponentVo.setEvalCustomsTax(priceSettlement.getEvalCustomsTax() / 100.0);
			}
		}
		resultMap.put("priceComponentInfo", priceComponentVo);
		String activePriceType = resultDO.getActivityPriceType();
		String marketPrice = resultDO.getMarketPrice(); //市场价
		//促销活动 WCS特卖活动 
		List<ItemSettleActivityResultDO> activityList = resultDO.getActivityList();
		if(GoodsConstant.WCS_ACT.equals(activePriceType) || (activityList != null && activityList.size() > 0)) {
			//如果有活动，则显示活动价
			if(activityList != null && activityList.size() > 0) {
				String promotionInfo = ""; //促销活动信息
				for (ItemSettleActivityResultDO activity : activityList) {
					//商品详情页只取一级活动
					promotionInfo = activity.getActivityName();
					break;
				}
				resultMap.put("promotionInfo", promotionInfo);
			}
			priceSettlement=proSettlement.getXiuPrice();
			if (0== priceSettlement.getPrice()) {//走秀价为空
				resultMap.put("price", "0");
			} else {
				if(StringUtils.isNotBlank(marketPrice)){
					double doubleMarketPrice = Double.parseDouble(marketPrice);
					BigDecimal marketPriceD = new BigDecimal(doubleMarketPrice / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
					resultMap.put("price", getPrice(String.valueOf(marketPriceD)));
				}else{
					Double xiuPrice = priceSettlement.getPrice()/100.0; //结算系统中的走秀价中包含了关税和国际运费
					resultMap.put("price", getPrice(getPrice(String.valueOf(xiuPrice))));
				}
			}
		} else {
			//商品原价
			if(StringUtils.isBlank(marketPrice)) {
				resultMap.put("price", "0");
			} else {
				double doubleMarketPrice = Double.parseDouble(marketPrice);
				BigDecimal marketPriceD = new BigDecimal(doubleMarketPrice / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
				resultMap.put("price", getPrice(String.valueOf(marketPriceD)));
			}
		}
		//走秀价
		BigDecimal zsPriceD = new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		String zsPrice = String.valueOf(zsPriceD);
		resultMap.put("zsPrice", getPrice(zsPrice));
		
		return resultMap;
	}
	
	
	/**
	 * 查询商品分类ID
	 * @param goodsSn
	 * @return
	 */
	private String getGoodsCategoryId(String goodsSn) {
		String result = null;
		List<CrumbsVo> list = crumbsService.getCrumbsByGoodsSn(goodsSn);
		if(list != null && list.size() > 0)	{
			Integer categoryId = list.get(list.size()-1).getCatalogId();
			result = String.valueOf(categoryId);
	    }
		return result;
	}
	
	/**
	 * 查询商品分类ID
	 * @param goodsSn
	 * @return
	 */
	private String getGoodsCategoryName(String goodsSn) {
		String result = null;
		List<CrumbsVo> list = crumbsService.getCrumbsByGoodsSn(goodsSn);
		if(list != null && list.size() > 0)	{
			 result = list.get(list.size()-1).getCatalogName();
		}
		return result;
	}
	
	
	/**
	 * 处理后缀为.00 和.0的价格
	 * @param price
	 * @return
	 */
	public String getPrice(String price) {
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
