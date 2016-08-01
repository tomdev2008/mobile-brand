package com.xiu.mobile.portal.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.xiu.mobile.core.model.CrumbsVo;
import com.xiu.mobile.core.service.ICrumbsService;
import com.xiu.mobile.core.service.ITopicFilterService;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.DeliverInfo;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.constants.OrderRuleConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ComparatorDiscountAsc;
import com.xiu.mobile.portal.common.util.ComparatorDiscountDesc;
import com.xiu.mobile.portal.common.util.ComparatorFilterItemAsc;
import com.xiu.mobile.portal.common.util.ComparatorPriceAsc;
import com.xiu.mobile.portal.common.util.ComparatorPriceDesc;
import com.xiu.mobile.portal.common.util.ComparatorSizeAsc;
import com.xiu.mobile.portal.common.util.HttpUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.ProductDao;
import com.xiu.mobile.portal.dao.TopicActivityDao;
import com.xiu.mobile.portal.ei.EIMbrandManager;
import com.xiu.mobile.portal.model.GoodsCategoryVO;
import com.xiu.mobile.portal.model.GoodsDetailSkuItem;
import com.xiu.mobile.portal.model.GoodsDetailsVO;
import com.xiu.mobile.portal.model.GoodsSizeVO;
import com.xiu.mobile.portal.model.GoodsSkuItem;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.PriceComponentVo;
import com.xiu.mobile.portal.model.ResultGoodsDO;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.TopicCategoryVO;
import com.xiu.mobile.portal.model.TopicFilterItemVO;
import com.xiu.mobile.portal.model.TopicFilterVO;
import com.xiu.mobile.portal.service.IActivityNoregularService;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.mobile.portal.service.ITopicsExtService;
import com.xiu.sales.common.balance.dataobject.PriceSettlementDO;
import com.xiu.sales.common.balance.dataobject.ProdSettlementDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleActivityResultDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

/**
 * 卖场扩展Service
 * @author coco.long
 * @time	2015-08-21
 */
@Transactional
@Service("topicsExtService")
public class TopicsExtServiceImpl implements ITopicsExtService {

	private Logger logger = Logger.getLogger(TopicsExtServiceImpl.class);

	@Autowired
	private IMemcachedService memcachedService;
	@Autowired
	private IActivityNoregularService activityNoregularService;
	@Autowired
	private TopicActivityDao topicActivityDao;
	@Autowired
	private EIMbrandManager mbrandManager;
	@Autowired
	private ICrumbsService crumbsService;
	@Autowired
	private GoodsCenterService goodsCenterService;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ITopicFilterService topicFilterService;
	/**
	 * 查询卖场商品列表
	 */
	public Map<String, Object> getTopicGoodsList(Map map) {
		String activityId = (String) map.get("activityId");
		logger.info("查询卖场商品列表-查询卖场商品-begin"+activityId);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String topicKey = getTopicCategoryKey(activityId);	//卖场分类尺码Key
		String goodsKey = getGoodsCategoryKey(activityId);	//商品分类尺码Key
		
		//查询卖场下的商品数量
		logger.info("查询卖场商品列表-查询卖场商品-查询总数-begin"+activityId);
		int totalCount = topicActivityDao.getGoodsCountByTopicId(activityId);
		logger.info("查询卖场商品列表-查询卖场商品-查询总数-end"+activityId);

		if(totalCount != 0) {
			//1.从缓存中获取卖场下的分类尺码信息
			logger.info("查询卖场商品列表-查询卖场商品-缓存中获取卖场下的分类尺码-begin"+activityId);
			List<TopicCategoryVO> topicCategoryList = memcachedService.getTopicCategoryList(topicKey);
			logger.info("查询卖场商品列表-查询卖场商品-缓存中获取卖场下的分类尺码-end"+activityId);
			
			if(topicCategoryList == null) {
				logger.info("查询卖场商品列表-查询卖场商品-缓存中不存在则添加分类尺码缓存-begin"+activityId);
				//缓存中不存在则添加分类尺码缓存
				Map cacheMap = addCategorySizeCache(activityId);
				if(cacheMap.containsKey(XiuConstant.MOBILE_TOPIC_CATEGORY)) {
					topicCategoryList = (List<TopicCategoryVO>) cacheMap.get(XiuConstant.MOBILE_TOPIC_CATEGORY);
				} else {
					topicCategoryList = new ArrayList<TopicCategoryVO>();
				}
				logger.info("查询卖场商品列表-查询卖场商品缓存中不存在则添加分类尺码缓存-end"+activityId);
			}

			//处理“全部”分类选项和商品尺寸“全部”选项
			List<TopicCategoryVO> allTopicCategoryList=new ArrayList<TopicCategoryVO>();
			int allTopicNum=0;
			for (TopicCategoryVO category:topicCategoryList) {
				List<GoodsSizeVO>  allSizes=new ArrayList<GoodsSizeVO>();
				List<GoodsSizeVO>  sizes=category.getSizeList();
				int allSizeNum=0;
				for (GoodsSizeVO sizevo:sizes) {
					allSizeNum+=sizevo.getGoodsNum();
				}
				allTopicNum+=category.getGoodsNum();
				GoodsSizeVO allSizeVo=new GoodsSizeVO();
				allSizeVo.setGoodsNum(allSizeNum);
				allSizeVo.setSizeName("全部");
				allSizes.add(allSizeVo);
				allSizes.addAll(sizes);
				category.setSizeList(allSizes);
			}
			TopicCategoryVO allCategoryVo=new TopicCategoryVO();
			allCategoryVo.setCategoryId(0);
			allCategoryVo.setCategoryName("全部");
			allCategoryVo.setGoodsNum(allTopicNum);
			List<GoodsSizeVO>  allSizes=new ArrayList<GoodsSizeVO>();
			GoodsSizeVO allSizeVo=new GoodsSizeVO();
			allSizeVo.setGoodsNum(allTopicNum);
			allSizeVo.setSizeName("全部");
			allSizes.add(allSizeVo);
			allCategoryVo.setSizeList(allSizes);
			
			allTopicCategoryList.add(allCategoryVo);
			allTopicCategoryList.addAll(topicCategoryList);
			returnMap.put("categoryList", allTopicCategoryList);
			
			//2.从缓存中获取卖场下商品的分类尺码信息
			logger.info("查询卖场商品列表-查询卖场商品-从缓存中获取卖场下商品的分类尺码信息-begin"+activityId);
			List<GoodsCategoryVO> goodsCategoryList = memcachedService.getGoodsCategoryList(goodsKey);
			if(topicCategoryList.size() == 0 && goodsCategoryList == null) {
				logger.info("查询卖场商品列表-查询卖场商品-从缓存中获取卖场下商品的分类尺码信息-缓存为空"+activityId);
				//如果从缓存中获取为空，则从数据库查询
				goodsCategoryList = topicActivityDao.goodsCategoryList(activityId);
			}
			logger.info("查询卖场商品列表-查询卖场商品-从缓存中获取卖场下商品的分类尺码信息-end"+activityId);
			//筛选分类和尺码查询条件
			String categorys = (String) map.get("categorys");
			if(StringUtils.isNotBlank(categorys)) {
				//如果分类条件不为空
				goodsCategoryList = getGoodsCategoryListByCategory(goodsCategoryList, categorys);
			}
			
			String sizes = (String) map.get("sizes");
			if(StringUtils.isNotBlank(sizes)) {
				//如果尺码条件不为空
				goodsCategoryList = getGoodsCategoryListBySize(goodsCategoryList, sizes);
			}
			
			//3.根据筛选出的商品信息查询商品列表
			logger.info("查询卖场商品列表-查询卖场商品-getGoodsList-begin"+activityId);
			List<GoodsVo> goodsList = getGoodsList(goodsCategoryList, map, activityId, returnMap);
			logger.info("查询卖场商品列表-查询卖场商品-getGoodsList-end"+activityId);

			
			returnMap.put("goodsList", goodsList);
		} else {
			//如果卖场下没有商品
			returnMap.put("goodsList", new ArrayList<GoodsVo>());
			returnMap.put("categoryList", new ArrayList<TopicCategoryVO>());
			returnMap.put("totalCount", 0);
		}
		
		//查询卖场信息
		Topic topic = activityNoregularService.getTopicByActId(activityId);
		
		returnMap.put("result", true);
		returnMap.put("topic", topic);
		logger.info("查询卖场商品列表-查询卖场商品-end");
		return returnMap;
	}
	
	

	
	/**
	 * 根据分类条件筛选出商品列表
	 * @param goodsCategoryList
	 * @param categorys 	分类ID串，逗号分隔
	 * @return
	 */
	private List<GoodsCategoryVO> getGoodsCategoryListByCategory(List<GoodsCategoryVO> goodsCategoryList, String categorys) {
		String[] categoryArr = categorys.split(",");
		//转换成分类ID数组
		int[] categoryIdArr = new int[categoryArr.length];
		for(int i = 0; i < categoryArr.length; i++) {
			categoryIdArr[i] = Integer.parseInt(categoryArr[i]);
		}
		
		List<GoodsCategoryVO> resultList = new ArrayList<GoodsCategoryVO>();
		if(goodsCategoryList != null && goodsCategoryList.size() > 0) {
			for(GoodsCategoryVO goods : goodsCategoryList) {
				for(int categoryId : categoryIdArr) {
					Integer cateId = goods.getCategoryId();
					if(cateId != null && cateId.intValue() == categoryId) {
						resultList.add(goods);
						break;
					}
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * 根据尺码条件筛选出商品列表
	 * @param goodsCategoryList
	 * @param sizes		尺码名称串，逗号分隔
	 * @return
	 */
	private List<GoodsCategoryVO> getGoodsCategoryListBySize(List<GoodsCategoryVO> goodsCategoryList, String sizes) {
		String[] sizeArr = sizes.split(",");
		
		List<GoodsCategoryVO> resultList = new ArrayList<GoodsCategoryVO>();	//返回结果列表
		
		if(goodsCategoryList != null && goodsCategoryList.size() > 0) {
			for(GoodsCategoryVO goods : goodsCategoryList) {
				List<String> sizeList = goods.getSizeList();
				if(sizeList != null && sizeList.size() > 0) {
					boolean flag = false;	//是否已添加标志
					for(String sizeName : sizeList) {
						if(flag) {
							break;
						}
						for(String size : sizeArr) {
							if(sizeName != null && sizeName.equals(size)) {
								//如果尺码存在则添加
								resultList.add(goods);
								flag = true;
								break;
							}
						}
					}
				}
			}
		}
		
		return resultList;
	}
	
	/**
	 * 添加分类尺码缓存
	 * @param activityId
	 * @param key
	 * @return
	 */
	private Map addCategorySizeCache(String activityId) {
		Map resultMap = new HashMap();
		List<GoodsCategoryVO> goodsCategoryList = null;
		
		//查询卖场下的所有商品ID
		List<String> goodsIdList = topicActivityDao.getGoodsListByTopicId(activityId);
		if(goodsIdList == null || goodsIdList.size() == 0) {
			return resultMap;
		}
		
		if(goodsIdList.size() <= 100) {
			StringBuffer goodsIds = new StringBuffer();
			for(String goodsIdStr : goodsIdList) {
				goodsIds.append(goodsIdStr).append(",");
			}
			goodsCategoryList = getCategorySizeListFromInterface(goodsIds.toString());	//查询接口
		} else {
			goodsCategoryList = new ArrayList<GoodsCategoryVO>();
			
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < goodsIdList.size(); i++) {
				String goodsId = goodsIdList.get(i);
				sb.append(goodsId).append(",");
				if(i != 0 && i%99 == 0) {
					sb.deleteCharAt(sb.length() - 1);
					List<GoodsCategoryVO> tmpList = getCategorySizeListFromInterface(sb.toString());	//查询接口
					goodsCategoryList.addAll(tmpList);
					
					sb = new StringBuffer();
				}
			}
			
			if(sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
				List<GoodsCategoryVO> tmpList = getCategorySizeListFromInterface(sb.toString());	//查询接口
				goodsCategoryList.addAll(tmpList);
			}
		}
		
		//计算卖场的分类尺码数据
		List<TopicCategoryVO> topicCategoryList = getTopicCategoryByGoodsCategory(goodsCategoryList);
		
		//处理查询商品尺码分类信息中没有返回的商品信息
		if(goodsCategoryList != null && goodsCategoryList.size() > 0 && goodsCategoryList.size() != goodsIdList.size()) {
			//如果商品ID的数量和接口返回的商品数量不一致
			goodsCategoryList = getAssembleGoodsCategoryList(goodsCategoryList, goodsIdList);
		}
		
		String categoryKey = getTopicCategoryKey(activityId);	//卖场分类尺码Key
		String goodsKey = getGoodsCategoryKey(activityId);			//商品分类尺码Key

		//商品尺码列表不为空且数量不为0，则存入缓存
		Map<String, Object> memcachedMap = new HashMap<String, Object>();
		if(topicCategoryList != null && topicCategoryList.size() > 0) {
			memcachedMap.put("key", categoryKey);
			memcachedMap.put(categoryKey, topicCategoryList);
			//添加卖场的分类尺码缓存
			memcachedService.addTopicCategoryCache(memcachedMap);
			resultMap.put(XiuConstant.MOBILE_TOPIC_CATEGORY, topicCategoryList);
		}
		
		if(goodsCategoryList != null && goodsCategoryList.size() > 0) {
			memcachedMap.put("key", goodsKey);
			memcachedMap.put(goodsKey, goodsCategoryList);
			//添加卖场下商品的分类尺码缓存
			memcachedService.addGoodsCategoryCache(memcachedMap);
			resultMap.put(XiuConstant.MOBILE_TOPIC_GOODS, topicCategoryList);
		}
		
		return resultMap;
	}
	
	/**
	 * 从接口查询商品的尺码分类信息
	 * @return
	 */
	private List<GoodsCategoryVO> getCategorySizeListFromInterface(String goodsIds) {
		List<GoodsCategoryVO> goodsCategoryList = null;
		String url = XiuConstant.GET_GOODS_CATEGORYSIZE_BY_GOODS + "?gids="+goodsIds+"&v=2.0";
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date now = new Date();	
		String beginTime = sf.format(now); //开始时间
		String endTime = "";
		try {
			String httpResult = HttpUtils.getMethod(url);
			if(StringUtils.isNotBlank(httpResult)) {
				ResultGoodsDO goodsResult = JsonUtils.json2bean(httpResult, ResultGoodsDO.class);
				goodsCategoryList = goodsResult.getGoods();
			}
		} catch (IOException e) {
			Date end = new Date();
			endTime = sf.format(end);
			logger.error("查询商品的尺码分类信息接口异常，URL："+url);
			logger.error("查询商品的尺码分类信息接口异常，开始时间："+beginTime+" 结束时间："+endTime);
			logger.error("查询商品的尺码分类信息接口异常："+e.getMessage());
		} catch (Exception e1) {
			Date end = new Date();
			endTime = sf.format(end);
			logger.error("查询商品的尺码分类信息接口异常，URL："+url);
			logger.error("查询商品的尺码分类信息接口异常，开始时间："+beginTime+" 结束时间："+endTime);
			logger.error("查询商品的尺码分类信息接口异常："+e1.getMessage());
		}
		
		if(goodsCategoryList == null) {
			//为空处理
			goodsCategoryList = new ArrayList<GoodsCategoryVO>();
		}
		
//		Date end = new Date();
//		endTime = sf.format(end);
//		logger.error("查询商品的尺码分类信息接口，开始时间："+beginTime+" 结束时间："+endTime);
		
		return goodsCategoryList;
	} 
	
	/**
	 * 把商品分类信息组装到新的商品分类列表，以goodsArr的商品为准
	 * @param goodsCategoryList
	 * @param goodsArr
	 * @return
	 */
	private List<GoodsCategoryVO> getAssembleGoodsCategoryList(List<GoodsCategoryVO> goodsCategoryList, List<String> goodsArr) {
		List<GoodsCategoryVO> resultList = new ArrayList<GoodsCategoryVO>();
		for(int i = 0; i < goodsArr.size(); i++) {
			GoodsCategoryVO goods = null;
			for(GoodsCategoryVO vo : goodsCategoryList) {
				if(vo.getGoodsId().equals(goodsArr.get(i))) {
					goods = vo;
					break;
				}
			}
			
			if(goods == null) {
				goods = new GoodsCategoryVO();
				goods.setGoodsId(goodsArr.get(i));
			}
			resultList.add(goods);
		}
		
		return resultList;
	}
	
	/**
	 * 根据卖场下商品的分类尺码计算出卖场的分类尺码数据
	 * @param topicCategoryList
	 * @return
	 */
	private List<TopicCategoryVO> getTopicCategoryByGoodsCategory(List<GoodsCategoryVO> goodsCategoryList) {
		List<TopicCategoryVO> topicCategoryList = new ArrayList<TopicCategoryVO>();
		
		if(goodsCategoryList != null && goodsCategoryList.size() > 0) {
			Map topicMap = new HashMap();
			for(GoodsCategoryVO goodsCategory : goodsCategoryList) {
				if(topicMap.containsKey(goodsCategory.getCategoryId())) {
					//如果已存在
					TopicCategoryVO topicCategory = (TopicCategoryVO) topicMap.get(goodsCategory.getCategoryId());
					int goodsNum = topicCategory.getGoodsNum();
					topicCategory.setGoodsNum(goodsNum + 1);	//商品数量+1
					List<GoodsSizeVO> goodsSizeList = getGoodsSizeList(topicCategory, goodsCategory);
					topicCategory.setSizeList(goodsSizeList);
				} else {
					//如果不存在，构造新对象
					String categoryName = goodsCategory.getCategoryName();
					if(StringUtils.isNotBlank(categoryName)) {
						TopicCategoryVO topicCategory = new TopicCategoryVO();
						topicCategory.setCategoryId(goodsCategory.getCategoryId());
						topicCategory.setCategoryName(goodsCategory.getCategoryName());
						topicCategory.setGoodsNum(1);
						List<GoodsSizeVO> sizeList = getGoodsSizeList(goodsCategory);
						topicCategory.setSizeList(sizeList);
						topicMap.put(topicCategory.getCategoryId(), topicCategory);
					} 
				}
			}
			
			//将map转为list
			topicCategoryList =  new ArrayList<TopicCategoryVO>(topicMap.values());
		}
		
		return topicCategoryList;
	}
	
	/**
	 * 根据商品的分类尺码计算到卖场的分类尺码中
	 * @param topicCategory
	 * @param goodsCategory
	 * @return
	 */
	private List<GoodsSizeVO> getGoodsSizeList(TopicCategoryVO topicCategory, GoodsCategoryVO goodsCategory) {
		List<GoodsSizeVO> goodsSizeList = topicCategory.getSizeList();
		List<String> sizeList = goodsCategory.getSizeList();
		
		if(sizeList != null && sizeList.size() > 0) {
			if(goodsSizeList == null) {
				goodsSizeList = new ArrayList<GoodsSizeVO>();
			}
			for(String size : sizeList) {
				boolean flag = true;
				for(GoodsSizeVO goodsSize : goodsSizeList) {
					if(size.equals(goodsSize.getSizeName())) {
						//如果存在相同的尺码
						flag = false;
						int goodsNum = goodsSize.getGoodsNum();
						goodsSize.setGoodsNum(goodsNum + 1); 
						break;
					}
				}
				
				if(flag) {
					//如果不存在形同的尺码
					GoodsSizeVO goodsSize = new GoodsSizeVO();
					goodsSize.setSizeName(size);
					goodsSize.setGoodsNum(1);
					goodsSizeList.add(goodsSize);
				}
			}
		}
		
		return goodsSizeList;
	}
	
	/**
	 * 根据商品的分类尺码转换成卖场的分类尺码
	 * @param goodsCategory
	 * @return
	 */
	private List<GoodsSizeVO> getGoodsSizeList(GoodsCategoryVO goodsCategory) {
		List<GoodsSizeVO> goodsSizeList = new ArrayList<GoodsSizeVO>();
		
		List<String> sizeList = goodsCategory.getSizeList();
		
		if(sizeList != null && sizeList.size() > 0) {
			for(String size : sizeList) {
				GoodsSizeVO goodsSize = new GoodsSizeVO();
				goodsSize.setSizeName(size);
				goodsSize.setGoodsNum(1);
				goodsSizeList.add(goodsSize);
			}
		}
		
		return goodsSizeList;
	}
	
	/**
	 * 根据筛选出的商品信息查询商品列表
	 * @param goodsCategoryList
	 * @param map
	 * @return
	 */
	private List<GoodsVo> getGoodsList(List<GoodsCategoryVO> goodsCategoryList, Map map, String activityId, Map<String, Object> returnMap) {
//		List<GoodsVo> goodsList = new ArrayList<GoodsVo>();
		
		//查询商品列表
//		String goodsSnBag = getGoodsSnBag(goodsCategoryList, activityId);
//		List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSnBag);
		
		//组装商品数据
//		if (null != products && products.size() > 0) {
//			//获取促销价格
//			List<ItemSettleResultDO> resultDOList = mbrandManager.queryGoodsPrice(goodsSnBag);
//			//如果有获取价格为0的商品
//			List<Product> notPriceProducts =new ArrayList<Product>();
//			for (Product pro:products) {
//				boolean isFindPrice=false;
//			   for (ItemSettleResultDO item:resultDOList) {
//				   if(item.getGoodsSn().equals(pro.getXiuSN())&&item.getDiscountPrice()>=0){
//							   isFindPrice=true;
//				   }
//				}
//			   if(!isFindPrice){
//				   notPriceProducts.add(pro);
//			   }
//			}
//			if(notPriceProducts.size()>0){
//				List<ItemSettleResultDO> newPriceresultDOList = topicActivityGoodsService.itemListSettle(notPriceProducts);
//				for (ItemSettleResultDO newpriceSettle:newPriceresultDOList) {
//					boolean isFind=false;
//					for (ItemSettleResultDO old:resultDOList) {
//						if(newpriceSettle.getGoodsSn().equals(old.getGoodsSn())){
//							old.setDiscountPrice(newpriceSettle.getDiscountPrice());
//							isFind=true;
//						}
//					}
//					if(!isFind){
//						resultDOList.add(newpriceSettle);
//					}
//				}
//			}
//			
//			for (Product product : products) {
//				GoodsVo goods = topicActivityGoodsService.assembleGoodsItem(product, resultDOList);
//				goodsList.add(goods);
//			}
//		}
		
		List<GoodsVo> goodsList = null;
		logger.info("查询卖场商品列表-查询卖场商品-getGoodsList-获取走秀码-begin"+activityId);
		List<String> goodsSnBag = getGoodsSnBag(goodsCategoryList, activityId);
		logger.info("查询卖场商品列表-查询卖场商品-getGoodsList-获取走秀码-end"+activityId);

		if(goodsSnBag != null && goodsSnBag.size() > 0){
			logger.info("查询卖场商品列表-查询卖场商品-getGoodsList-filterGoodsByMbrand-begin"+activityId);
			goodsList = this.filterGoodsByMbrand(goodsSnBag);
			logger.info("查询卖场商品列表-查询卖场商品-getGoodsList-filterGoodsByMbrand-end"+activityId);
		}
		
		
		//过滤掉价格为0的商品
		goodsList = getGoodsListByPriceFilter(goodsList);
		
		//过滤掉是否在售的商品
		String onlyOnSale = (String) map.get("onlyOnSale");
		if(StringUtils.isNotBlank(onlyOnSale) && "Y".equals(onlyOnSale)) {
			if(goodsList != null && goodsList.size() > 0) {
				List<GoodsVo> onSaleList = new ArrayList<GoodsVo>();	//在售商品列表
				for(int i = 0; i < goodsList.size(); i++) {
					GoodsVo goods = goodsList.get(i);
					if(goods.getStateOnsale() == 1) {
						//在售
						onSaleList.add(goods);
					}
				}
				goodsList = onSaleList;
			}
		} 
		
		//处理折扣信息
		if(goodsList != null && goodsList.size() > 0) {
			for(GoodsVo goods : goodsList) {
				if (StringUtils.isNotEmpty(goods.getDiscount()) && goods.getDiscount().indexOf("折") > 0) {
					goods.setDiscount(goods.getDiscount().trim().split("折")[0]);
				} else {
					goods.setDiscount("10");
				}
			}
		}
		
		
		//价格筛选
		Long minPrice = ObjectUtil.getLong(map.get("minPrice"),null);
		Long maxPrice = ObjectUtil.getLong(map.get("maxPrice"),null);
		if(minPrice!=null){
			List<GoodsVo> goodsListTemp=new ArrayList<GoodsVo>();
			minPrice=minPrice*100;
			for (GoodsVo good:goodsList) {
				Double zsPrice = XiuUtil.getPriceDoubleFormat(good.getZsPrice());
				Long zsPriceL= XiuUtil.getPriceLongFormat100(zsPrice);
				if(zsPriceL>=minPrice){
					goodsListTemp.add(good);
				}
			}
			goodsList=new ArrayList<GoodsVo>();
			goodsList.addAll(goodsListTemp);
		}
		if(maxPrice!=null){
			List<GoodsVo> goodsListTemp=new ArrayList<GoodsVo>();
			maxPrice=maxPrice*100;
			for (GoodsVo good:goodsList) {
				Double zsPrice = XiuUtil.getPriceDoubleFormat(good.getZsPrice());
				Long zsPriceL= XiuUtil.getPriceLongFormat100(zsPrice);
				if(zsPriceL<=maxPrice){
					goodsListTemp.add(good);
				}
			}
			goodsList=new ArrayList<GoodsVo>();
			goodsList.addAll(goodsListTemp);
		}
		
		//4.获取筛选后的走秀码，根据筛选走秀码过滤商品
		//查询缓存是否有数据
		String filterIds = ObjectUtil.getString(map.get("filterIds"),null);
        if(filterIds!=null){
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
        	if(!filterIds.equals("")){
	        	String activityIdKey=getActivityFilterKey(activityId);
	        	Map filterMap= memcachedService.getTopicFilterList(activityIdKey);
	        	if(filterMap==null){
//	        		map.put("activityIdKey", activityIdKey);
	        		filterMap=topicFilterService.addTopicGoodFilterCache(activityId);
	        	}
	        	List<String> filterXiuSns=getFilterXiuSn(filterIds,filterMap);
	        	List<GoodsVo> filterGoodsList =new ArrayList<GoodsVo>();
	        	for (int i = 0; i < filterXiuSns.size(); i++) {
					for (int j=0;j<goodsList.size();j++) {
						GoodsVo good=goodsList.get(j);
						if(filterXiuSns.get(i).equals(good.getGoodsSn())){
							filterGoodsList.add(good);
							break; 
						}
					}
				}
	        	goodsList=new ArrayList<GoodsVo>();
	        	goodsList.addAll(filterGoodsList);
        	}
        } 
		
		
		//排序
		String order = (String) map.get("order");
		if(StringUtils.isNotBlank(order) && !"0".equals(order) && goodsList != null && goodsList.size() > 0) {
			orderGoodsList(goodsList, order);
		} else if (StringUtils.isNotBlank(order) && "0".equals(order) && goodsList != null && goodsList.size() > 0) {
			//一品多商项目：这里不需要再排序了，前面从数据库里查询出来的就是排好序的，调用mbrand接口返回的数据也是按传过去的顺序返回的
			//如果这里再排序，则有问题，因为mbrand可能已经把副商品换成了主商品，这里再查表就查不到了
			//默认排序
//			goodsList = getOrderGoodsList(goodsList, activityId);
		}
        
		if(goodsList != null) {
			returnMap.put("totalCount", goodsList.size());
		} else {
			returnMap.put("totalCount", 0);
		}
		
		//分页
		goodsList = getGoodsListByPage(goodsList, map);
		
		//处理图片信息和价格
		if(goodsList != null && goodsList.size() > 0) {
			for(GoodsVo goods : goodsList) {
				if (StringUtils.isNotEmpty(goods.getGoodsImg())) {
					//处理图片
					String orgi = goods.getGoodsImg() + XiuConstant.G1_IMG;
					String iurl = ImageServiceConvertor.removePort(orgi);// 去掉端口号
					iurl = ImageServiceConvertor.replaceImageDomain(iurl);

					//目前图片服务器前缀
					String goodsId =  goods.getGoodsId();
		        	int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()));
					String imgUrl = "http://" + XiuConstant.XIUSTATIC_NUMS[index%3] + ".xiustatic.com";
					String[] temp = goods.getGoodsImg().split("/");
					String imgName = temp[temp.length - 1];
					String url = goods.getGoodsImg().split(imgName)[0] + imgName + "/g1_315_420.jpg";
					String[] sName = url.split("upload");

					goods.setGoodsImg(imgUrl + "/upload" + sName[1]);
				}
				
				//将所有的价格去除多余的0
				filterGoodsZeroPrice(goods);
			}
		}
		
		return goodsList;
	}
	
	private List<GoodsVo> filterGoodsByMbrand(List<String> goodsSnBag) {
		List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
		StringBuilder goodsSns = new StringBuilder();
		for (int i = 1; i <= goodsSnBag.size(); i++) {
			goodsSns.append(goodsSnBag.get(i-1)).append(",");
			if(i % 100 == 0 || i == goodsSnBag.size()){
				goodsSns = goodsSns.deleteCharAt(goodsSns.length() - 1);
				mbrandManager.filterGoodsByMbrand(goodsSns.toString(), goodsVoList);
				goodsSns = new StringBuilder();
			}
		}
		return goodsVoList;
	}




	/**
	 * 获取goodsSn拼串
	 * @param goodsCategoryList
	 * @return
	 */
	private List<String> getGoodsSnBag(List<GoodsCategoryVO> goodsCategoryList, String activityId) {
		StringBuffer sb = new StringBuffer();
		
		if(goodsCategoryList != null && goodsCategoryList.size() > 0) {
			for(GoodsCategoryVO goods : goodsCategoryList) {
				sb.append(goods.getGoodsId()).append(",");
			}
			
			//截取最后一个逗号
			if(sb.length() > 1) {
				sb.deleteCharAt(sb.length() - 1);
			}
		}
		
		String goodsIds = sb.toString();
//		StringBuffer goodsSnBag = new StringBuffer();
		if(StringUtils.isNotBlank(goodsIds)) {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("activityId", activityId);
			paraMap.put("goodsIdArr", goodsIds.split(","));
			List<String> goodsSnList = topicActivityDao.getGoodsSnListByTopicAndGoodsIds(paraMap);
//			if(goodsSnList != null && goodsSnList.size() > 0) {
//				for(String goodsSn : goodsSnList) {
//					goodsSnBag.append(goodsSn).append(",");
//				}
//				goodsSnBag.deleteCharAt(goodsSnBag.length() - 1);
//			}
			return goodsSnList;
		}
		
//		return goodsSnBag.toString();
		return null;
	}
	
	/**
	 * 过滤掉走秀价为零的商品
	 * @param goodsList
	 * @return
	 */
	private List<GoodsVo> getGoodsListByPriceFilter(List<GoodsVo> goodsList) {
		List<GoodsVo> resultList = null;
		
		if(goodsList != null && goodsList.size() > 0) {
			Map<String, String> goodsMap = new HashMap<String, String>();
			for(GoodsVo goods : goodsList) {
				if (null == goods.getZsPrice() || "0.0".equals(goods.getZsPrice())||"0".equals(goods.getZsPrice())) {
					//如果价格为零，则添加至map
					goodsMap.put("goodsId", goods.getGoodsId());
				}
			}
			
			if(goodsMap.keySet().size() > 0) {
				resultList = new ArrayList<GoodsVo>();
				//如果价格为零的商品不为空
				for(GoodsVo goods : goodsList){
					if(!goodsMap.containsKey(goods.getGoodsId())) {
						//如果map中不存在，则添加至返回结果中
						resultList.add(goods);
					}
				}
			} else {
				return goodsList;
			}
		}
		
		return resultList;
	}
	
	private void filterGoodsZeroPrice(GoodsVo goods) {
		if (StringUtils.isNotBlank(goods.getActivityPrice()) && goods.getActivityPrice().indexOf(".") > 0) {
			//活动价
			goods.setActivityPrice(goods.getActivityPrice().replaceAll("0+?$", "").replaceAll("[.]$", ""));
		}
		
		if (StringUtils.isNotBlank(goods.getPrice()) && goods.getPrice().indexOf(".") > 0) {
			//市场价
			goods.setPrice(goods.getPrice().replaceAll("0+?$", "").replaceAll("[.]$", ""));
		}
		
		if (StringUtils.isNotBlank(goods.getZsPrice()) && goods.getZsPrice().indexOf(".") > 0) {
			//走秀价
			goods.setZsPrice(goods.getZsPrice().replaceAll("0+?$", "").replaceAll("[.]$", ""));
		}
	}
	
	/**
	 * 获取分页商品列表数据
	 * @param goodsList
	 * @param map
	 * @return
	 */
	private List<GoodsVo> getGoodsListByPage(List<GoodsVo> goodsList, Map map) {
		List<GoodsVo> resultList = new ArrayList<GoodsVo>();
		
		int startRow = Integer.parseInt(map.get("startRow").toString());
		int endRow = Integer.parseInt(map.get("endRow").toString());
		
		if(goodsList != null && goodsList.size() > 0) {
			int length = goodsList.size() > endRow ? endRow : goodsList.size();
			for(int i = startRow - 1; i < length; i++) {
				resultList.add(goodsList.get(i));
			}
		}
		
		return resultList;
	}
	
	/**
	 * 商品排序
	 * @param order
	 * @param lstGoodsVoCache
	 */
	private void orderGoodsList(List<GoodsVo> lstGoodsVoCache, String order) {
		if (OrderRuleConstant.PRICE_ASC.equals(order)) {
			Collections.sort(lstGoodsVoCache, new ComparatorPriceAsc<GoodsVo>());
		}
		
		if (OrderRuleConstant.PRICE_DESC.equals(order)) {
			Collections.sort(lstGoodsVoCache, new ComparatorPriceDesc<GoodsVo>());
		}
		
		if (OrderRuleConstant.DISCOUNT_ASC.equals(order)) {
			Collections.sort(lstGoodsVoCache, new ComparatorDiscountAsc<GoodsVo>());
		}
		
		if (OrderRuleConstant.DISCOUNT_DESC.equals(order)) {
			Collections.sort(lstGoodsVoCache, new ComparatorDiscountDesc<GoodsVo>());
		}
	}
	
	/**
	 * 获取默认排序的商品列表
	 * @param lstGoodsVoCache
	 * @param activityId
	 * @return
	 */
	private List<GoodsVo> getOrderGoodsList(List<GoodsVo> lstGoodsVoCache, String activityId) {
		List<GoodsVo> resultList = null;
		if(lstGoodsVoCache != null && lstGoodsVoCache.size() > 0) {
			StringBuffer sb = new StringBuffer(); //商品ID拼串
			for(GoodsVo goods : lstGoodsVoCache) {
				sb.append(goods.getGoodsId()).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("activityId", activityId);
			map.put("goodsIdArr", sb.toString().split(","));
			
			//查询排序的商品列表
			List goodsList = topicActivityDao.getOrderGoodsList(map); 
			if(goodsList != null && goodsList.size() > 0) {
				resultList = new ArrayList<GoodsVo>();
				for(int i = 0; i < goodsList.size(); i++) {
					Map goodsMap = (Map) goodsList.get(i);
					String goodsId = goodsMap.get("GOODSID").toString();
					for(GoodsVo vo : lstGoodsVoCache) {
						if(goodsId.equals(vo.getGoodsId())) {
							resultList.add(vo);
							continue;
						}
					}
				}
				
			}
		}
		
		return resultList;
	}
	
	/**
	 * 查询卖场下的分类尺码Key
	 * @param activityId	卖场ID
	 * @return
	 */
	private String getTopicCategoryKey(String activityId) {
		String result = XiuConstant.MOBILE_TOPIC + "." + activityId + "." + XiuConstant.MOBILE_TOPIC_CATEGORY;
		return result;
	}
	
	/**
	 * 查询卖场下商品的分类尺码Key
	 * @param activityId
	 * @return
	 */
	private String getGoodsCategoryKey(String activityId) {
		String result = XiuConstant.MOBILE_TOPIC + "." + activityId + "." + XiuConstant.MOBILE_TOPIC_GOODS;
		return result;
	}

	/**
	 * 移除卖场下的分类尺码缓存
	 */
	public boolean removeTopicMemcache(Map map) {
		String activityId = (String) map.get("activityId");
		
		String topicKey = getTopicCategoryKey(activityId);	//卖场分类尺码Key
		String goodsKey = getGoodsCategoryKey(activityId);		//商品分类尺码Key
		
		map.put("topicKey", topicKey);
		map.put("goodsKey", goodsKey);
		
		//删除卖场的分类尺码和卖场下商品的分类尺码缓存
		memcachedService.deleteTopicAndGoodsCategoryCache(map);
		
		return true;
	}

	
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
//		memcachedService.deleteTopicFilterList(activityIdKey);
		filterMap= memcachedService.getTopicFilterList(activityIdKey);
		if(filterMap==null||filterMap.size()==0){
    		filterMap=topicFilterService.addTopicGoodFilterCache(activityId);
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
		
		Map<String,List<String>> typeFilterMap=new HashMap<String,List<String>>();//相同筛选类保存到同一map list(处理多选)
		
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
					 typeFilterList.addAll(xiuSns);
					 typeFilterMap.put(typeKey, typeFilterList);
				 }
			 }
		}
		
 		//获取每个筛选分类都存在的走秀码
 		Map<String,String> returnFilterXiuCheckMap=new HashMap<String,String>();
 		Boolean isFirst=true;
 		for (String key:typeFilterMap.keySet()) {
 			List<String> snlist=typeFilterMap.get(key);
 			Integer size=snlist.size();
 			if(isFirst){
 				for (int i = 0; i < size; i++) {
 					returnFilterXiuCheckMap.put(snlist.get(i), snlist.get(i));
				}
 				isFirst=false;
 			}else{
 		 		Map tempReturnFilterXiuCheckMap=new HashMap();
	 			for (int i = 0; i < size; i++) {
					if(returnFilterXiuCheckMap.get(snlist.get(i))!=null){
						tempReturnFilterXiuCheckMap.put(snlist.get(i), snlist.get(i));
					}
				}
	 			returnFilterXiuCheckMap=tempReturnFilterXiuCheckMap;
 			}
		}
 		//获取筛选的走秀码
 		for (String snKey:returnFilterXiuCheckMap.keySet()) {
 			returnFilterXiuSns.add(snKey);
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
         if(cityNameMap!=null){
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
		         
		         //排序
				Collections.sort(items, new ComparatorFilterItemAsc<TopicFilterItemVO>());
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
	
	private List<GoodsDetailsVO> getTopicGoodsByGoodsSn(Map<String, Object> map) {

		 List<GoodsDetailsVO> gds=new ArrayList<GoodsDetailsVO>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String goodsSns = (String) map.get("goodsSns");
		
		String[] goodsSnArr=goodsSns.split(",");
		List<String> goodsSnList=new ArrayList<String>();
		for (int i = 0; i < goodsSnArr.length; i++) {
			goodsSnList.add(goodsSnArr[i]);
		}
		
		try {
			//先从mbrand获取品牌和价格
			List<GoodsVo>	goodsList = this.filterGoodsByMbrand(goodsSnList);
			goodsSns="";
			for (GoodsVo good:goodsList) {
				if(!goodsSns.equals("")){
					goodsSns=goodsSns+",";
				}
				goodsSns=goodsSns+good.getGoodsSn();
			}
			//1.查询商品
			List<Product> productList = batchLoadProducts(goodsSns);
			if(productList == null || productList.size() == 0) {
				//如果没有找到商品
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
				return gds;
			}
			for (int i = 0; i < productList.size(); i++) {
				Product product = productList.get(i);
				String goodsSn=product.getXiuSN();
				String productId = String.format("%07d", product.getInnerID());
				
				//2.组装商品基本信息
				GoodsDetailsVO goodsDetail = new GoodsDetailsVO();
				goodsDetail.setGoodsId(productId);
				goodsDetail.setGoodsSn(goodsSn);
				
				// 商品分类
				String categoryName = getGoodsCategoryName(goodsSn);
				goodsDetail.setCategoryName(categoryName);
				
				// 商品sku列表,包含sku码、sku库存数量、商品组图
				List<GoodsSkuItem> goodSkuList = handleSkus(product.getSkus()); // 尺码颜色
				handleSkuData(goodSkuList, product , goodsDetail, null, false);
				
				//4.发货地信息
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("productId", productId);
				DeliverInfo deliverInfo = productDao.getDeliverInfoByProduct(paraMap);
				goodsDetail.setDeliverInfo(deliverInfo);
				
				GoodsVo good=null;
				for (int j = 0; j < goodsList.size(); j++) {
					if(goodsList.get(j).getGoodsSn().equals(goodsSn)){
						good=goodsList.get(j);
					}
				}
				goodsDetail.setBrandCNName(good.getBrandCNName());
				goodsDetail.setBrandEnName(good.getBrandEnName());
				goodsDetail.setZsPrice(good.getZsPrice());
				gds.add(goodsDetail);
			}
			
			resultMap.put("result", true);
		} catch (Exception e) {
			logger.error("查询商品详情异常，商品goodsSn："+goodsSns+"，错误信息："+e.getMessage());
		}
		
		return gds;
	
	}
	
	
	// 处理sku信息
		private List<GoodsSkuItem> handleSkus(Sku[] skus) {
			List<GoodsSkuItem> skuList = new ArrayList<GoodsSkuItem>();
			
			if (skus != null && skus.length > 0) {
				List<String> skuCodeList = new ArrayList<String>();
				for (Sku sku : skus) {
					skuCodeList.add(sku.getSkuSN());
				}
				// 批量获取SKU的库存信息
//				Map<String, Integer> skuCodeMap = queryInventoryBySku(skuCodeList);
				for (Sku sku : skus) {
					GoodsSkuItem skuItem = new GoodsSkuItem();
					skuItem.setColor(sku.getColorValue());
					skuItem.setSize(sku.getSizeValue());
					skuItem.setSkuSn(sku.getSkuSN());
					// 添加到返回结果中
					skuList.add(skuItem);
				}
			}

			return skuList;
		}
	
	
	public List<Product> batchLoadProducts(String goodsSnBag) {
		ProductSearchParas productSearchParas = new ProductSearchParas();
		productSearchParas.setXiuSnList(goodsSnBag);
		productSearchParas.setPageStep(50);

		ProductCommonParas productCommonParas = new ProductCommonParas();
		productCommonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));

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
	private void handleSkuData(List<GoodsSkuItem> goodSkuList, Product product,
			 GoodsDetailsVO goodsDetail, String mainSku, boolean isMultiPro) {
//		List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType, isMultiPro, goodSkuList, snMap); // 查询sku图
//		findImgList(skuImagesPairs, goodSkuList); // 配对sku及sku的images：为int[] 1,0，以后备用拼
//		
		// 设置颜色、尺码列表
		List<String> colorList = new ArrayList<String>();
		List<String> sizeList = new ArrayList<String>();
		
		Set<String> colorSet = new HashSet<String>();
		Set<String> sizeSet = new HashSet<String>();
		for(GoodsSkuItem skuItem : goodSkuList) {
			colorSet.add(skuItem.getColor());
			sizeSet.add(skuItem.getSize());
		}
		colorList.addAll(colorSet);
		sizeList.addAll(sizeSet);
//		goodsDetail.setColors(colorList); //颜色
		//尺码排序
		Collections.sort(sizeList);	
		Collections.sort(sizeList, new ComparatorSizeAsc<String>());
//		goodsDetail.setSizes(sizeList); //尺码
		
		List<GoodsDetailSkuItem> skuList = new ArrayList<GoodsDetailSkuItem>();
		GoodsSkuItem priSku = goodSkuList.get(0);// 默认第一个
		int primColor = colorList.indexOf(priSku.getColor()); //主SKU颜色索引
		int primSize = sizeList.indexOf(priSku.getSize()); //主SKU尺寸索引
		int goodsStock = 0; //库存
		
		//Sku列表按颜色、尺码排序
		List<GoodsSkuItem> sortSkuList = new ArrayList<GoodsSkuItem>();
		if(sizeList != null && sizeList.size() > 0) {
			for(String size : sizeList) {
				for(String color : colorList) {
					for(GoodsSkuItem sku : goodSkuList) {
						if(size.equals(sku.getSize()) && color.equals(sku.getColor())) {
							sortSkuList.add(sku);
							continue;
						}
					}
				}
			}
			goodSkuList = sortSkuList;
		}
		
		for (GoodsSkuItem sku : goodSkuList) {
			int k = colorList.indexOf(sku.getColor()), l = sizeList.indexOf(sku.getSize());
			if(sku.getStock() > 0) {
				//计算库存
				goodsStock = goodsStock + sku.getStock();
			}
			// 颜色尺码和SKU值对应列表
			String key = "c" + k + "s" + l;
			GoodsDetailSkuItem skuItem = new GoodsDetailSkuItem();
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
		PriceComponentVo priceComponentVo = new PriceComponentVo();
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

		//豪车价格处理
		if (GoodsConstant.BUS.equals(mCatCode)) {
			BigDecimal xiuPrice = new BigDecimal(priceSettlement.getPrice()/100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			if(zsPriceD.compareTo(xiuPrice)==-1){//如果有比走秀价还低的价格，则原价设为走秀价
				String xiuPriceStr = String.valueOf(xiuPrice);
				resultMap.put("price", getPrice(xiuPriceStr));
			}
		} 
		
		return resultMap;
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
