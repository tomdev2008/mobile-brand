/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午2:13:49 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.common.command.result.Result;
import com.xiu.mobile.portal.common.constants.OrderRuleConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ComparatorDiscountAsc;
import com.xiu.mobile.portal.common.util.ComparatorDiscountDesc;
import com.xiu.mobile.portal.common.util.ComparatorPriceAsc;
import com.xiu.mobile.portal.common.util.ComparatorPriceDesc;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.TopicActivityDao;
import com.xiu.mobile.portal.dao.TopicActivityGoodsDao;
import com.xiu.mobile.portal.ei.EIProductManager;
import com.xiu.mobile.portal.ei.EIPromotionManager;
import com.xiu.mobile.portal.model.ActivityGoodsBo;
import com.xiu.mobile.portal.model.CxListVo;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.QueryActivityVo;
import com.xiu.mobile.portal.model.TopicActivityGoodsVo;
import com.xiu.mobile.portal.service.ITopicActivityGoodsService;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-14 下午2:13:49 
 * ***************************************************************
 * </p>
 */
@Service
public class TopicActivityGoodsServiceImpl implements ITopicActivityGoodsService {
	private static final Logger logger = Logger.getLogger(TopicActivityGoodsServiceImpl.class);
	
	@Autowired
	private EIProductManager eiProductManager;
	@Autowired
	private EIPromotionManager eiPromotionManager;
	
	@Autowired
	private TopicActivityGoodsDao topicActivityGoodsDao;
	@Autowired
	private TopicActivityDao topicActivityDao;

	@Override
	public List<TopicActivityGoodsVo> getTopicActivityGoodsByCategory(Map<Object, Object> map) {
		return topicActivityGoodsDao.getTopicActivityGoodsByCategory(map);
	}

	@Override
	public List<TopicActivityGoodsVo> getAllGoodsCategoryByActivityId(Map<Object, Object> map) {
		return topicActivityGoodsDao.getAllGoodsCategoryByActivityId(map);
	}

	@Override
	public List<CxListVo> getCxListByActivityId(Map<Object, Object> map) {
		return topicActivityGoodsDao.getCxListByActivityId(map);
	}
	
	@Override
	public int getCxGoodsCountByActivityId(Map<Object, Object> map) {
		return topicActivityGoodsDao.getCxCountByActivityId(map);
	}

	@Override
	public ActivityGoodsBo queryTopicActivityGoodsList(
			QueryActivityVo queryAVo, Map<String, String> deviceParams) throws Exception {
		logger.info("获取商品专题列表：queryAvo="+queryAVo+",deviceParams="+deviceParams);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", 1);
		params.put("pageSize", Integer.MAX_VALUE);
		params.put("activityId", queryAVo.getActivityId());// 活动Id
		params.putAll(deviceParams);
		
		String activityId = queryAVo.getActivityId();

		// 一次性取出所有商品，以便进行价格、折扣排序
		// 查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("activityId", activityId);
		paramMap.put("storeId", GlobalConstants.STORE_ID);
		Tools.addPageInfoToParamMap(paramMap, params);

		List<GoodsVo> goods = new ArrayList<GoodsVo>();
		int goodsNum = 0;

		// 查询热卖商品
		List<GoodsVo> goodHots = topicActivityDao.queryHotSaleGoodsList(paramMap);
		logger.info("查询热卖商品：goodHots.size="+goodHots.size());
		int goodsNumHot = topicActivityDao.queryHotSaleGoodsCount(paramMap);
		goods.addAll(goodHots);
		goodsNum += goodsNumHot;
		
		// 如果为热卖，则只查询热卖信息
		String isHotSale = "热卖".equals(queryAVo.getSubType()) ? "true" : "false";
		
		// 查询专题商品
		if (!"true".equals(isHotSale)) {
			List<GoodsVo> gListAll = topicActivityDao.queryAllActivityGoodsList(paramMap);
			logger.info("查询活动商品列表信息：gListAll.size="+gListAll.size());
			int goodsNumAll = topicActivityDao.queryTopicActivityGoodsTotal(paramMap);
			goods.addAll(gListAll);
			goodsNum += goodsNumAll;
		}

		// 处理商品列表
		List<GoodsVo> goodsList = new ArrayList<GoodsVo>();

		if (null != goods && goods.size() > 0) {
			String goodsSnBag = joinGoodsSn(goods);
			logger.info("拼装后的goodsSnBag="+goodsSnBag);
			goodsList = assembleGoodsList(goodsSnBag);
		}
		goodsList = sort(goods, goodsList);

		ActivityGoodsBo activityGoods = new ActivityGoodsBo();
		activityGoods.setGoodsNum(goodsNum);

		//如果仅仅展示有售的则去掉下架的
		if ("Y".equals(queryAVo.getOnlyOnSale()) && null != goodsList && goodsList.size() > 0) {
		    Integer sale = new Integer(1);
			for (int i = goodsList.size(); i > 0; i--) {
				if (!sale.equals(goodsList.get(i-1).getStateOnsale())) {
					goodsList.remove(i-1);
				}
			}
		}
		
		
		// 用来存放最终返回的商品列表(已按分类信息过滤、已排序、已分页)
		List<GoodsVo> lstGoodsVoFinal = new ArrayList<GoodsVo>();

		if (null != goodsList && goodsList.size() > 0) {
			// 缓存中间商品列表
			List<GoodsVo> lstGoodsVoCache = new ArrayList<GoodsVo>();

			// 如果点击分类，则只展示该分类下的商品
			if (StringUtils.isNotEmpty(queryAVo.getSubType())) {
				// 展示分类商品
				if ("全部".equals(queryAVo.getSubType()) 
						|| "热卖".equals(queryAVo.getSubType())) {
					lstGoodsVoCache = goodsList;
				} else {
					lstGoodsVoCache = getGoodsListByCategory(queryAVo, goodsList);
					logger.info("根据分类信息查询改分类下的商品信息：lstGoodsVoCache.size="+lstGoodsVoCache.size());
					activityGoods.setGoodsNum(lstGoodsVoCache.size());
				}
			}

			if (lstGoodsVoCache.size() > 0) {
				// 排序
				if (StringUtils.isNotEmpty(queryAVo.getOrder())) {

					// 先处理折扣信息，去掉"折"字，以便折扣排序
					for (GoodsVo goodsVo : lstGoodsVoCache) {
						if (StringUtils.isNotEmpty(goodsVo.getDiscount())
								&& goodsVo.getDiscount().indexOf("折") > 0) {
							goodsVo.setDiscount(goodsVo.getDiscount().trim().split("折")[0]);
						} else {
							goodsVo.setDiscount("10");
						}
					}
					orderGoodsList(queryAVo.getOrder(), lstGoodsVoCache);
				}

				// 分页
				lstGoodsVoFinal = toPagination(queryAVo, lstGoodsVoCache);

				for (GoodsVo vo : lstGoodsVoFinal) {

					// 处理图片信息
					if (StringUtils.isNotEmpty(vo.getGoodsImg())) {
						String orgi = vo.getGoodsImg() + XiuConstant.G1_IMG;
						String iurl = ImageServiceConvertor.removePort(orgi);// 去掉端口号
						iurl = ImageServiceConvertor.replaceImageDomain(iurl);

						// 目前图片服务器前缀
						String goodsId =  vo.getGoodsId();
			        	int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()));
						String imgUrl = "http://" + XiuConstant.XIUSTATIC_NUMS[index%3] + ".xiustatic.com";
						String[] temp = vo.getGoodsImg().split("/");
						String imgName = temp[temp.length - 1];
						String url = vo.getGoodsImg().split(imgName)[0] + imgName + "/g1_315_420.jpg";
						String[] sName = url.split("upload");

						vo.setGoodsImg(imgUrl + "/upload" + sName[1]);
					}

					// 将所有的价格去除多余的0
					subZeroAndDot(vo);
				}
			}
		}
		
		activityGoods.setRetCode(XiuConstant.RETCODE_SUCCESS);
		activityGoods.setGoodsList(lstGoodsVoFinal);
		return activityGoods;
	}

	/**
	 * 根据分类信息，返回该分类下的商品列表
	 * 
	 * @param queryAVo
	 * @param goodsVoList
	 * @return List<GoodsVo>
	 */
	private List<GoodsVo> getGoodsListByCategory(
			QueryActivityVo queryAVo, List<GoodsVo> goodsVoList) {
		List<GoodsVo> result = new ArrayList<GoodsVo>();
		Map<Object, Object> paramsC = new HashMap<Object, Object>();
		paramsC.put("activityId", Long.parseLong(queryAVo.getActivityId()));
		paramsC.put("category", queryAVo.getSubType());
		// 根据专题活动ID、分类查询商品类表
		List<TopicActivityGoodsVo> goodsVoListByCategory = getTopicActivityGoodsByCategory(paramsC);
		Map<String, Object> goodsMap = listToMap(goodsVoList);
		
		for (TopicActivityGoodsVo vo : goodsVoListByCategory) {
			GoodsVo gv = (GoodsVo) goodsMap.get(vo.getGoodSn());
			if (null != gv)
				result.add(gv);
		}
		
		return result;
	}
	
	private Map<String, Object> listToMap(List<GoodsVo> goodsList) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (goodsList != null && goodsList.size() > 0) {
			for (GoodsVo goods : goodsList) {
				map.put(goods.getGoodsSn(), goods);
			}
		}
		
		return map;
	}
	
	private void orderGoodsList(String order, List<GoodsVo> lstGoodsVoCache) {
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
	 * 商品列表分页信息
	 * 
	 * @param queryAVo
	 * @param lstGoodsVoCache
	 * @return List<GoodsVo>
	 */
	private List<GoodsVo> toPagination(QueryActivityVo queryAVo, List<GoodsVo> lstGoodsVoCache) {
		List<GoodsVo> lstGoodsVoFinal = new ArrayList<GoodsVo>();
		int pageNum = queryAVo.getPageNum();
		int pageSize = queryAVo.getPageSize();
		
		for (int i = ((pageNum - 1) * pageSize); 
				i < lstGoodsVoCache.size() && i < ((pageNum) * pageSize) && pageNum > 0; i++) {
			lstGoodsVoFinal.add(lstGoodsVoCache.get(i));
		}
		
		return lstGoodsVoFinal;
	}
	
	/**  
	 * 使用java正则表达式去掉多余的0  
	 * @param vo  
	 * @return   
	 */
	private void subZeroAndDot(GoodsVo goods) {
		if (StringUtils.isNotBlank(goods.getActivityPrice()) 
				&& goods.getActivityPrice().indexOf(".") > 0) {
			goods.setActivityPrice(goods.getActivityPrice()
					.replaceAll("0+?$", "").replaceAll("[.]$", ""));
		}
		
		if (StringUtils.isNotBlank(goods.getPrice()) 
				&& goods.getPrice().indexOf(".") > 0) {
			goods.setPrice(goods.getPrice()
					.replaceAll("0+?$", "").replaceAll("[.]$", ""));
		}
		
		if (StringUtils.isNotBlank(goods.getZsPrice()) 
				&& goods.getZsPrice().indexOf(".") > 0) {
			goods.setZsPrice(goods.getZsPrice()
					.replaceAll("0+?$", "").replaceAll("[.]$", ""));
		}
	}
	
	/**
	 * 
	 * 把商品列表中的goodsSn用逗号分隔 
	 * 拼成的字符串,用于批量查询
	 * @param goodsList 商品列表
	 * @return goodsSn用逗号分隔
	 */
	public String joinGoodsSn(List<GoodsVo> goodsList) {
		StringBuffer buffer = new StringBuffer();
		for (GoodsVo good : goodsList) {
			buffer.append(good.getGoodsSn());
			buffer.append(",");
		}
		
		// 去掉最后一个逗号(,)
		buffer.deleteCharAt(buffer.length() - 1);
		
		if (logger.isDebugEnabled()) {
			logger.debug("推荐商品品批量获取详情信息的goodsSn信息:" + buffer.toString());
		}
		
		return buffer.toString();
	}
	
	/**
	 * 组装返回的活动商品信息
	 * (问题:如果从cms数据库返回的goodsSn不存在,调用批量详情接口不会返回该商品详情)
	 * 如:传入20011001,20011002,如果20011002goodsSn不存在,只会返回20011001的详情信息
	 * 
	 * @param goodsList 从cms数据查出的商品列表(但信息不全,批量调用了详情接口和促销接口)
	 * @return
	 * @throws InterfaceHandleException
	 */
	public List<GoodsVo> assembleGoodsList(String goodsSnBag) {
		List<GoodsVo> result = new ArrayList<GoodsVo>();

		List<Product> products = batchLoadProducts(goodsSnBag);
		if (null != products && products.size() > 0) {
			List<ItemSettleResultDO> resultDOList = itemListSettle(products);

			for (Product product : products) {
				result.add(assembleGoodsItem(product, resultDOList));
			}
		}
		return result;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> batchLoadProducts(String goodsSnBag) {
		ProductSearchParas searchParas = new ProductSearchParas();
		searchParas.setXiuSnList(goodsSnBag);
		searchParas.setPageStep(50);

		ProductCommonParas commonParas = new ProductCommonParas();
		commonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));
		
		Map<String, Object> result = eiProductManager.getProductLight(commonParas, searchParas);
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
	@SuppressWarnings("unchecked")
	public List<Product> batchLoadProducts(List<Long> productIdList) {
		ProductSearchParas searchParas = new ProductSearchParas();
		searchParas.setProductIdList(productIdList);
		searchParas.setPageStep(50);

		ProductCommonParas commonParas = new ProductCommonParas();
		commonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));
		
		Map<String, Object> result = eiProductManager.getProductLight(commonParas, searchParas);
		List<Product> products = (List<Product>) result.get("Products");
		logger.info("查询到产品信息：products="+products);
		
		if (products == null || products.isEmpty()) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(products);
		}

		return products;
	}
	
	/**
	 * 
	 * 批量调用促销的接口,用于获取折扣价
	 * 
	 * @param products 批量查询出的详情信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ItemSettleResultDO> itemListSettle(List<Product> products) {
		List<ItemSettleResultDO> paramList = new ArrayList<ItemSettleResultDO>();
		for (Product product : products) {
			if (logger.isDebugEnabled()) {
				logger.debug("----------------------------------------------------------------------");
				logger.debug("product.getXiuSN() = " + product.getXiuSN());
				logger.debug("product.getOnSale() = " + product.getOnSale());
				logger.debug("product.getPrdName() = " + product.getPrdName());
				logger.debug("product.getPrdOfferPrice() = " + product.getPrdOfferPrice());
				logger.debug("product.getPrdActivityPrice() = " + product.getPrdActivityPrice());
				logger.debug("product.getPrdActivityPriceType() = " + product.getPrdActivityPriceType());
			}

			ItemSettleResultDO param = new ItemSettleResultDO();

			// 走秀码
			param.setGoodsSn(product.getXiuSN());

			// 走秀价
			if (product.getPrdOfferPrice() != null) {
				Double goodsPrice = product.getPrdOfferPrice() * 100;
				param.setXiuPrice(goodsPrice.longValue());
			}

			// 活动价
			if (product.getPrdActivityPrice() != null) {
				Double activityPrice = product.getPrdActivityPrice() * 100;
				param.setActivityPrice(activityPrice.longValue());
				param.setActivityPriceType(product.getPrdActivityPriceType());
			} else {
				// 没有活动价传入一个<0的数据
				param.setActivityPrice(-100);
			}

			// 品牌和分类
			param.setBrandId(product.getBrandCode());
			param.setCatId(product.getBCatCode());

			// 来源
			param.setBookFrom(GlobalConstants.STORE_ID);

			// 时间
			param.setNowTime(System.currentTimeMillis());

			//描述：终端用户(1 手机用户 2 电脑用户,多个用逗号分开)
			param.setTerminalUser("1");
			// 把param加入批量查询参数
			paramList.add(param);
		}

		Result result = eiPromotionManager.itemListSettleSrevice(paramList);
		List<ItemSettleResultDO> resultDOList = (List<ItemSettleResultDO>) result.getDefaultModel();

		if (logger.isDebugEnabled()) {
			logger.debug("----------------------------------------------------------------------");
			logger.debug("resultDOList =  " + resultDOList);
		}

		return resultDOList;
	}
	
	/**
	 * 根据商品中心的Product和促销返回的resultDOList 
	 * 组装出每项商品信息 (活动价字段不再返回给客户端,取消了活动商品的概念)
	 * 
	 * @param product 商品中心的product
	 * @param resultDOList 促销中心返回的resultDO列表
	 * @return
	 * @throws InterfaceHandleException
	 */
	public GoodsVo assembleGoodsItem(Product product, List<ItemSettleResultDO> resultDOList) {
		// goodsName + goodsImg + zsPrice + price + discount + isActivityGoods
		GoodsVo good = new GoodsVo();
		
		//添加产品或商品ID
		good.setGoodsId(String.valueOf(product.getInnerID()));
		
		// goodsSn
		String goodsSn = product.getXiuSN();
		good.setGoodsSn(goodsSn);

		/**商品售罄标识*/
		good.setStateOnsale(Integer.parseInt(product.getOnSale()));

		// 商品名称
		// String goodsName = product.getBrandName() + " " + product.getPrdName();
		// 改成只取商品名，不再加品牌前缀 2013/12/20
		String goodsName = product.getPrdName();
		good.setGoodsName(goodsName);

		// 商品图片
		if (!Tools.isEnableImageReplace()) {
			good.setGoodsImg(product.getImgUrl());
		} else {
			// 为了适应镜像环境的端口号
			String imgUrl = product.getImgUrl();
			if (null != imgUrl) {
				imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
				imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
				good.setGoodsImg(product.getImgUrl());
			}
		}

		double zsPrice = getDiscountPrice(goodsSn, resultDOList);
		good.setZsPrice(String.valueOf(zsPrice));

		// 市场价和折扣
		if (product.getPrdListPrice() == null || product.getPrdListPrice().doubleValue() <= 0.0) {
			good.setPrice("");
			good.setDiscount("");
		} else {
			good.setPrice(product.getPrdListPrice().toString());
			String discountStr = getDiscountString(product.getPrdListPrice(), zsPrice);
			good.setDiscount(discountStr);
		}

		// 取消所有活动商品的标志
		good.setIsActivityGoods(0);
		
		//品牌名称
		Map brandMap = topicActivityDao.getBrandNameByGoods(good.getGoodsId());
		if(brandMap != null) {
			String cnName = (String) brandMap.get("CN_NAME");
			String enName = (String) brandMap.get("EN_NAME");
			good.setBrandCNName(cnName);
			good.setBrandEnName(enName);
		}

		return good;
	}
	
	/**
	 * 根据goodsSn去促销中心返回的返回的resultDO列表中匹配出折扣价 
	 * (这个价格会重置走秀价) 如果没有找到匹配到,抛出一个异常信息
	 * 
	 * @param goodsSn 商品编码goodsSn
	 * @param resultDOList 促销中心返回的resultDO列表
	 * @return
	 * @throws InterfaceHandleException
	 */
	public double getDiscountPrice(String goodsSn, List<ItemSettleResultDO> resultDOList) {
		for (ItemSettleResultDO resultDO : resultDOList) {
			if (resultDO.getGoodsSn().equals(goodsSn)) {
				return (resultDO.getDiscountPrice() / 100.0);
			}
		}
		return 0;
//		throw new InterfaceHandleException(GlobalConstants.RET_CODE_SYS_ERROR, "获取商不到商品价格");
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
	public String getDiscountString(double price, double zsPrice) {
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
	
	public List<GoodsVo> sort(List<GoodsVo> src, List<GoodsVo> dest) {
		List<GoodsVo> result = new ArrayList<GoodsVo>();

		if (dest != null && dest.size() > 0) {
			Map<String, GoodsVo> kvList = new HashMap<String, GoodsVo>();
			
			for (GoodsVo destItem : dest) {
				// 过滤掉走秀价为零的商品
				if (null == destItem.getZsPrice() || "0.0".equals(destItem.getZsPrice())||"0".equals(destItem.getZsPrice())) {
					continue;
				}
				
				kvList.put(destItem.getGoodsSn().toString(), destItem);
			}

			for (GoodsVo srcItem : src) {
				GoodsVo item = kvList.get(srcItem.getGoodsSn());
				
				if (item != null) {
					result.add(item);
				}
			}
		}

		return result;
	}
	
	
	public List<GoodsVo> getBrandNameByGoodIds(String[] goodIds){
		Map params=new HashMap();
		params.put("goodsIdArr", goodIds);
		List<GoodsVo> gs = topicActivityDao.getBrandNameByGoodIds(params); 
		return gs;
	}

}
