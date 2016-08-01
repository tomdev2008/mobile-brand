package com.xiu.mobile.portal.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.commerce.hessian.server.GoodsCenterService;
import com.xiu.common.command.result.Result;
import com.xiu.image.biz.dto.GoodsInfoDTO;
import com.xiu.image.biz.hessian.interfaces.SkuImagesPair;
import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.BrandInfoVO;
import com.xiu.mobile.core.model.CrumbsVo;
import com.xiu.mobile.core.model.Goods;
import com.xiu.mobile.core.service.ICrumbsService;
import com.xiu.mobile.portal.common.constants.ArrivalTimeMsgs;
import com.xiu.mobile.portal.common.constants.DeliverInfo;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.constants.SendingPlaceMsgs;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ComparatorSizeAsc;
import com.xiu.mobile.portal.common.util.HttpUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.ImageUtil;
import com.xiu.mobile.portal.common.util.ParseProperties;
import com.xiu.mobile.portal.common.util.StringUtil;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.dao.BookmarkIitemDao;
import com.xiu.mobile.portal.dao.ProductDao;
import com.xiu.mobile.portal.dao.TopicActivityDao;
import com.xiu.mobile.portal.ei.EICSCManager;
import com.xiu.mobile.portal.ei.EIChannelInventoryManager;
import com.xiu.mobile.portal.ei.EIMbrandManager;
import com.xiu.mobile.portal.ei.EIPCSManager;
import com.xiu.mobile.portal.ei.EIPcsOpmsManager;
import com.xiu.mobile.portal.ei.EIProductManager;
import com.xiu.mobile.portal.ei.EIPromotionManager;
import com.xiu.mobile.portal.ei.EImageManager;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.CommentBOResult;
import com.xiu.mobile.portal.model.CommentListResult;
import com.xiu.mobile.portal.model.CommentProdResult;
import com.xiu.mobile.portal.model.CommentResult;
import com.xiu.mobile.portal.model.CommentUserResult;
import com.xiu.mobile.portal.model.FlexibleItem;
import com.xiu.mobile.portal.model.GoodsCommentVO;
import com.xiu.mobile.portal.model.GoodsDetailBo;
import com.xiu.mobile.portal.model.GoodsDetailSkuItem;
import com.xiu.mobile.portal.model.GoodsDetailVo;
import com.xiu.mobile.portal.model.GoodsDetailsVO;
import com.xiu.mobile.portal.model.GoodsInfo;
import com.xiu.mobile.portal.model.GoodsSkuItem;
import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.mobile.portal.model.MktActInfoVo;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.model.PriceCompareVo;
import com.xiu.mobile.portal.model.PriceComponentVo;
import com.xiu.mobile.portal.model.ResultDO;
import com.xiu.mobile.portal.model.ShoppingCartGoodsVo;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.mobile.portal.service.IOrderService;
import com.xiu.mobile.portal.service.IPCSService;
import com.xiu.pcs.opms.facade.dto.MutilPartnerInfo;
import com.xiu.pcs.opms.facade.vo.DeliveryInfoVo;
import com.xiu.pcs.opms.facade.vo.MutilPartnerRel;
import com.xiu.pcs.opms.facade.vo.ProdBaseInfoVo;
import com.xiu.pcs.opms.facade.vo.SkuCustomInfoVo;
import com.xiu.pcs.opms.facade.vo.SnCustomInfoVo;
import com.xiu.sales.common.balance.dataobject.PriceSettlementDO;
import com.xiu.sales.common.balance.dataobject.ProdSettlementDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleActivityResultDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;
import com.xiu.sales.common.tools.DateUtils;

/**
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-13 下午6:40:23 
 * ***************************************************************
 * </p>
 */
@Service("goodsManager")
public class GoodsServiceImpl implements IGoodsService {

	private static Logger logger = Logger.getLogger(GoodsServiceImpl.class);
	
	@Autowired
	private EIProductManager productManager;
	@Autowired
	private EIPromotionManager eiPromotionManager;
	@Autowired
	private EIChannelInventoryManager eiChannelInventoryManager;
	@Autowired
	private EImageManager eImageManager;
	@Autowired
	private EIPCSManager pcsManager;
	@Autowired
	private EICSCManager eiCSCManager;
	
	@Autowired
	private TopicActivityDao topicActivityDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private BookmarkIitemDao bookmarkItemDao;
	
	@Autowired
	private IPCSService pcsService;
	@Autowired
	private GoodsCenterService goodsCenterService;
	@Autowired
	private ICrumbsService crumbsService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	EIPcsOpmsManager eIPcsOpmsManager;

	@Autowired
	private IMemcachedService memcachedService;
	@Autowired
	private EIMbrandManager mbrandManager;


	@Override
	public String getGoodsSnByGoodsId(String goodsId) throws Exception {
		return goodsDao.getGoodsSnByGoodsId(goodsId);
	}

	@Override
	public GoodsDetailBo loadXiuGoodsDetail(
			String goodsSn, Map<String, String> params) throws Exception {
		// 查询xiu接口
		Product product = loadProduct(goodsSn.trim());
		
		// 商品编号
		GoodsDetailBo goodsDetail = new GoodsDetailBo();
		//扩展域信息
		Map fieldExt = product.getFieldEx();
		if (null != fieldExt) {
			String supportRejected = (String) fieldExt.get("rejectedFlag");
			goodsDetail.setSupportRejected(supportRejected);

			String supplierCode = (String) fieldExt.get("realSupplierCode");
			goodsDetail.setSupplierName(getSupplierDisplayName(supplierCode));
		}
		
		GoodsInfo goods = new GoodsInfo();
		goods.setGoodsSn(goodsSn);
		goods.setInnerId(String.format("%07d", product.getInnerID()));
		goods.setGoodsNumber(product.getXiuSN());
		goods.setBrandCode(product.getBrandCode());
		goods.setBrandName(product.getBrandName());

		// 商品名称
		// String prefix = (StringUtils.isNotBlank(product.getBrandName()) ?
		// (product.getBrandName() + " ") : "");
		// String goodsName = prefix + product.getPrdName();
		// 改成只取商品名，不再加品牌前缀 @2013/5/23
		goods.setGoodsName(product.getPrdName());
		goods.setDescription(product.getPrdDesc());
		
		// 设置汽车标识及显示信息
		// 管理分类是汽车
		if (GoodsConstant.BUS.equals(product.getMCatCode())) {
			// 该商品参加了活动
			if (GoodsConstant.WCS_ACT.equals(product.getPrdActivityPriceType())) {
				goodsDetail.setBusDisplayType(GoodsConstant.BUS_IN_ACT);
			} else {// 未参加活动
				goodsDetail.setBusDisplayType(GoodsConstant.BUS_NOT_IN_ACT);
			}
		}
		
		// 商品包装信息
		goods.setPackageDesc(product.getPackageDesc());

		// 商品编辑推荐信息
		goods.setEditWords(product.getEditWords());

		// --- 图片服务器代替 ---
		// 商品图片
		String imgUrl = product.getImgUrl();
		
		// 为了适应镜像环境的端口号
		if (Tools.isEnableImageReplace()) { 
			if (null != imgUrl) {
				imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
				imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
			}
		} 
		
		imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
		goods.setGoodsImgUrl(imgUrl);
		
		// 商品尺码对照图
		String sizeUrl = product.getSizeUrl();
		if (sizeUrl == null) {
			sizeUrl = "";
		}
		sizeUrl = ImageServiceConvertor.removePort(sizeUrl);
		sizeUrl = ImageServiceConvertor.addDetailSize(sizeUrl);
		sizeUrl = ImageServiceConvertor.getGoodsDetail(sizeUrl);
		goods.setSizeUrl(sizeUrl);
		
		// 商品细节信息
		goods.setGoodsDetail(product.getDescription());
		
		// 商品上下架标识
		goodsDetail.setOnSale(product.getOnSale());

		// COD 标识
		goodsDetail.setCodFlag(product.getCodFlag());

		// 商品sku列表,包含商品组图
		String deviceType = params.get("deviceType").toString().trim();
		//尺码颜色和库存
		List<GoodsSkuItem> goodSkus = handleSkus(product.getSkus());
		//查询sku图
		List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType, false, null, null);
		//配对sku及sku的images：为int[] 1,0，以后备用拼
		findImgList(skuImagesPairs, goodSkus);
		//设置到商品详情里
		goodsDetail.setSkuList(goodSkus);

		// 取消所有活动商品的标志
		goodsDetail.setIsActivityGoods(0);
		
		
		// 调用营销中心接口
		ItemSettleResultDO resultDO = invokeSaleInterface(product);
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
		goodsDetail.setPriceComponentVo(priceComponentVo);
		goodsDetail.setProSettlement(proSettlement);
		List<ItemSettleActivityResultDO> activityList = resultDO.getActivityList();
		List<String> list = new ArrayList<String>();
		List<MktActInfoVo> activityItemList=new ArrayList<MktActInfoVo>();
		
		if (
			GoodsConstant.WCS_ACT.equals(product.getPrdActivityPriceType())//WCS特卖活动
			||(activityList != null && activityList.size() > 0)//促销活动
			) 
			{//促销活动
			if (activityList != null && activityList.size() > 0) {
				for (ItemSettleActivityResultDO activity : activityList) {
					list.add(activity.getActivityId());
					MktActInfoVo mktActInfo = new MktActInfoVo();
					mktActInfo.setActivityId(activity.getActivityId());
					mktActInfo.setActivityName(activity.getActivityName());
					mktActInfo.setActivityType(activity.getActivityType());
					activityItemList.add(mktActInfo);
				}
			}
			//有促销活动-显示 促销-最终价 结算系统的走秀价[wcs-走秀价product.getPrdOfferPrice()]
			// 走秀价为null的处理方案，显示为“null”字符串
			
			priceSettlement=proSettlement.getXiuPrice();
			
			if (0== priceSettlement.getPrice()) {//走秀价为空
				goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				//不为空
				Double xiuPrice=priceSettlement.getPrice()/100.0;//结算系统中的走秀价中包含了关税和国际运费
				goodsDetail.setPrice(new BigDecimal(xiuPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}else{//无促销活动
			  //取wcs的市场价
			if (product.getPrdListPrice() == null
					|| product.getPrdListPrice().doubleValue() == 0.0) {
				goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				goodsDetail.setPrice(new BigDecimal(product.getPrdListPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		goodsDetail.setActivityList(list);
		goodsDetail.setActivityItemList(activityItemList);
		
		
		//总之这个字段一定放的是便宜的价格：特卖时放特卖价，促销时放促销价
		goodsDetail.setZsPrice(
				new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP));

		// 出发地的决定字段
		String supplierCode = product.getSupplierCode();
		String spaceFlag = product.getSpaceFlag();
		
		//查询发货地信息 add by coco.long 2015.7.30
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("productId", goods.getInnerId());
		DeliverInfo deliverInfo = productDao.getDeliverInfoByProduct(paraMap);
		
		//修改发送时间信息，与app保持一致，后面再改过来 add by coco.long 2015.08.14
		if(deliverInfo != null) {
			String deliveryTime = deliverInfo.getDeliveryTimeInfo();
			if(StringUtils.isNotBlank(deliveryTime)) {
				deliveryTime = deliveryTime.replace("预计", "");
				deliveryTime = deliveryTime.replace("天送达", "");
				deliverInfo.setDeliveryTime(deliveryTime);
			}
		}
		
		goodsDetail.setDeliverInfo(deliverInfo);
		

		// 设置配货信息
		String tranport = getTransportInfo(supplierCode, spaceFlag);
		//这个app似乎没用到，但是先留着
		goodsDetail.setTranport(tranport);
		
		// 商品属性信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("store_id", GlobalConstants.STORE_ID);
		paramMap.put("catentry_id", product.getInnerID());
		List<FlexibleItem> flexibleAttr = queryGoodsProps(paramMap,goodsSn);
		goodsDetail.setFlexibleAttr(flexibleAttr);

		//是否支持礼品包装
		boolean supportWrapStatus = isProductSupportWrap(goodsSn);
		goodsDetail.setSupportPackaging(supportWrapStatus);
		
		goodsDetail.setGoodsInfo(goods);
		return goodsDetail;
	}
	
	@Override
	public List<GoodsDetailBo> getGoodsDetailBoList(String goodsSnStr, Map<String, String> params) throws Exception {
		List<GoodsDetailBo> goodsDetailBoList = new ArrayList<GoodsDetailBo>();
		// 获取商品中心goodsSn列表信息
		List<Product> productList = batchLoadProductsOld(goodsSnStr);
		if (productList != null && productList.size() > 0) {
			Map<String, ItemSettleResultDO> itemSettleResultDOMap = new HashMap<String, ItemSettleResultDO>();
			// 批量调用促销的接口 并封装在map信息里,用于获取折扣价
			List<ItemSettleResultDO> itemSettleResultDOList =  itemListSettle(productList);
			for (ItemSettleResultDO itemSettleResultDO : itemSettleResultDOList) {
				itemSettleResultDOMap.put(itemSettleResultDO.getGoodsSn(), itemSettleResultDO);
			}
			
			// 封装商品信息
			for (Product product : productList) {
				// 商品编号
				GoodsDetailBo goodsDetail = new GoodsDetailBo();
				GoodsInfo goods = new GoodsInfo();
				goods.setGoodsSn(product.getXiuSN());
				goods.setInnerId(String.format("%07d", product.getInnerID()));
				goods.setGoodsNumber(product.getXiuSN());
				goods.setBrandCode(product.getBrandCode());
				
				// 商品名称
				// String prefix = (StringUtils.isNotBlank(product.getBrandName()) ?
				// (product.getBrandName() + " ") : "");
				// String goodsName = prefix + product.getPrdName();
				// 改成只取商品名，不再加品牌前缀 @2013/5/23
				goods.setGoodsName(product.getPrdName());
				goods.setDescription(product.getPrdDesc());
				
				// 商品包装信息
				goods.setPackageDesc(product.getPackageDesc());
				
				// 商品编辑推荐信息
				goods.setEditWords(product.getEditWords());
				
				// --- 图片服务器代替 ---
				// 商品图片
				String imgUrl = product.getImgUrl();
				
				// 为了适应镜像环境的端口号
				if (Tools.isEnableImageReplace()) { 
					if (null != imgUrl) {
						imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
						imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
					}
				} 
				
				imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
				goods.setGoodsImgUrl(imgUrl);
				
				// 商品尺码对照图
				String sizeUrl = product.getSizeUrl();
				if (sizeUrl == null) {
					sizeUrl = "";
				}
				
				sizeUrl = ImageServiceConvertor.removePort(sizeUrl);
				sizeUrl = ImageServiceConvertor.addDetailSize(sizeUrl);
				sizeUrl = ImageServiceConvertor.getGoodsDetail(sizeUrl);
				goods.setSizeUrl(sizeUrl);
				
				// 商品细节信息
				goods.setGoodsDetail(product.getDescription());
				
				// 商品上下架标识
				goodsDetail.setOnSale(product.getOnSale());
				
				// COD 标识
				goodsDetail.setCodFlag(product.getCodFlag());
				
				// 商品sku列表,包含商品组图
				String deviceType = params.get("deviceType").toString().trim();
				//尺码颜色和库存
				List<GoodsSkuItem> goodSkus = handleSkus(product.getSkus());
				//查询sku图
				List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType, false, null, null);
				//配对sku及sku的images：为int[] 1,0，以后备用拼
				findImgList(skuImagesPairs, goodSkus);
				//设置到商品详情里
				goodsDetail.setSkuList(goodSkus);
				
				// 取消所有活动商品的标志
				goodsDetail.setIsActivityGoods(0);
				
				
				// 调用营销中心接口
				ItemSettleResultDO resultDO = itemSettleResultDOMap.get(product.getXiuSN());
				//从结算系统获得价格组成
				ProdSettlementDO proSettlement = resultDO.getProdSettlementDO();
				boolean isCustoms=proSettlement.isCustoms();
				PriceSettlementDO priceSettlement=new PriceSettlementDO();
				PriceComponentVo priceComponentVo=new PriceComponentVo();
				//是否行邮
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
				goodsDetail.setPriceComponentVo(priceComponentVo);
				goodsDetail.setProSettlement(proSettlement);
				List<ItemSettleActivityResultDO> activityList = resultDO.getActivityList();
				List<String> list = new ArrayList<String>();
				List<MktActInfoVo> activityItemList=new ArrayList<MktActInfoVo>();
				//有促销活动
				if (activityList != null && activityList.size() > 0) {
					for (ItemSettleActivityResultDO activity : activityList) {
						list.add(activity.getActivityId());
						MktActInfoVo mktActInfo=new MktActInfoVo();
						mktActInfo.setActivityId(activity.getActivityId());
						mktActInfo.setActivityName(activity.getActivityName());
						mktActInfo.setActivityType(activity.getActivityType());
						activityItemList.add(mktActInfo);
					}
					//有促销活动-显示 促销-最终价 结算系统的走秀价[wcs-走秀价product.getPrdOfferPrice()]
					// 走秀价为null的处理方案，显示为“null”字符串
					
					priceSettlement=proSettlement.getXiuPrice();
					
					if (0== priceSettlement.getPrice()) {
						goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
					} else {
						Double xiuPrice=priceSettlement.getPrice()/100.0;//结算系统中的走秀价中包含了关税和国际运费
						goodsDetail.setPrice(new BigDecimal(xiuPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}else{
					//无促销活动-显示 促销-最终价 wcs-市场价
					// 市场价为null的处理方案，显示为“null”字符串
					if (product.getPrdListPrice() == null || product.getPrdListPrice().doubleValue() == 0.0) {
						goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
					} else {
						goodsDetail.setPrice(new BigDecimal(product.getPrdListPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}
				goodsDetail.setActivityList(list);
				goodsDetail.setActivityItemList(activityItemList);
				
				// 走秀价
				goodsDetail.setZsPrice(new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				// 出发地的决定字段
				String supplierCode = product.getSupplierCode();
				String spaceFlag = product.getSpaceFlag();
				
				//查询发货地信息 add by coco.long 2015.7.30
				Map<String, String> paraMap = new HashMap<String, String>();
				paraMap.put("productId", goods.getInnerId());
				DeliverInfo deliverInfo = productDao.getDeliverInfoByProduct(paraMap);
				
				//修改发送时间信息，与app保持一致，后面再改过来 add by coco.long 2015.08.14
				if(deliverInfo != null) {
					String deliveryTime = deliverInfo.getDeliveryTimeInfo();
					if(StringUtils.isNotBlank(deliveryTime)) {
						deliveryTime = deliveryTime.replace("预计", "");
						deliveryTime = deliveryTime.replace("天送达", "");
						deliverInfo.setDeliveryTime(deliveryTime);
					}
				}
				
				goodsDetail.setDeliverInfo(deliverInfo);
				
				// 设置配货信息
				String tranport = getTransportInfo(supplierCode, spaceFlag);
				goodsDetail.setTranport(tranport);
				
				// 商品属性信息
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("store_id", GlobalConstants.STORE_ID);
				paramMap.put("catentry_id", product.getInnerID());
				//List<FlexibleItem> flexibleAttr = queryGoodsProps(paramMap, product.getXiuSN());
				//goodsDetail.setFlexibleAttr(flexibleAttr);
				
				
				//限购处理
				goodsDetail.setLimitSaleNum(this.getGoodsLimitSaleNum(product));
				
				// 商品基本信息封装
				goodsDetail.setGoodsInfo(goods);
				
				goodsDetailBoList.add(goodsDetail);
			}
		}
		
		return goodsDetailBoList;
	}
	

	@Override
	public List<GoodsDetailBo> getShoppingCartGoodsDetailBoList(String goodsSnStr, Map<String, String> params) throws Exception {
		List<GoodsDetailBo> goodsDetailBoList = new ArrayList<GoodsDetailBo>();
		// 获取商品中心goodsSn列表信息
//		List<Product> productList = batchLoadProductsOld(goodsSnStr);
		List<Product> productList = batchLoadProducts(goodsSnStr);
		if (productList != null && productList.size() > 0) {
			Map<String, ItemSettleResultDO> itemSettleResultDOMap = new HashMap<String, ItemSettleResultDO>();
			// 批量调用mbrand接口
			List<ItemSettleResultDO> itemSettleResultDOList = mbrandManager.queryGoodsPrice(goodsSnStr);
			//如果有获取价格为0的商品
			List<Product> notPriceProducts =new ArrayList<Product>();
			for (Product pro:productList) {
				boolean isFindPrice=false;
			   for (ItemSettleResultDO item:itemSettleResultDOList) {
				   if(item.getGoodsSn().equals(pro.getXiuSN())&&item.getDiscountPrice()>=0){
							   isFindPrice=true;
				   }
				}
			   if(!isFindPrice){
				   notPriceProducts.add(pro);
			   }
			}
			if(notPriceProducts.size()>0){
				// 批量调用促销的接口 并封装在map信息里,用于获取折扣价
				List<ItemSettleResultDO> newPriceresultDOList = itemListSettle(notPriceProducts);
				for (ItemSettleResultDO newpriceSettle:newPriceresultDOList) {
					boolean isFind=false;
					for (ItemSettleResultDO old:itemSettleResultDOList) {
						if(newpriceSettle.getGoodsSn().equals(old.getGoodsSn())){
							old.setDiscountPrice(newpriceSettle.getDiscountPrice());
							isFind=true;
						}
					}
					if(!isFind){
						itemSettleResultDOList.add(newpriceSettle);
					}
				}
			}
			
			for (ItemSettleResultDO itemSettleResultDO : itemSettleResultDOList) {
				itemSettleResultDOMap.put(itemSettleResultDO.getGoodsSn(), itemSettleResultDO);
			}
			
			// 封装商品信息
			for (Product product : productList) {
				// 商品编号
				GoodsDetailBo goodsDetail = new GoodsDetailBo();
				GoodsInfo goods = new GoodsInfo();
				goods.setGoodsSn(product.getXiuSN());
				goods.setInnerId(String.format("%07d", product.getInnerID()));
				goods.setGoodsNumber(product.getXiuSN());
				goods.setBrandCode(product.getBrandCode());
				
				// 商品名称
				// String prefix = (StringUtils.isNotBlank(product.getBrandName()) ?
				// (product.getBrandName() + " ") : "");
				// String goodsName = prefix + product.getPrdName();
				// 改成只取商品名，不再加品牌前缀 @2013/5/23
				goods.setGoodsName(product.getPrdName());
				goods.setDescription(product.getPrdDesc());
				
				// 商品包装信息
				goods.setPackageDesc(product.getPackageDesc());
				
				// 商品编辑推荐信息
				goods.setEditWords(product.getEditWords());
				
				// --- 图片服务器代替 ---
				// 商品图片
				String imgUrl = product.getImgUrl();
				
				// 为了适应镜像环境的端口号
				if (Tools.isEnableImageReplace()) { 
					if (null != imgUrl) {
						imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
						imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
					}
				} 
				
				imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
				goods.setGoodsImgUrl(imgUrl);
				
//				// 商品尺码对照图
//				String sizeUrl = product.getSizeUrl();
//				if (sizeUrl == null) {
//					sizeUrl = "";
//				}
//				
//				sizeUrl = ImageServiceConvertor.removePort(sizeUrl);
//				sizeUrl = ImageServiceConvertor.addDetailSize(sizeUrl);
//				sizeUrl = ImageServiceConvertor.getGoodsDetail(sizeUrl);
//				goods.setSizeUrl(sizeUrl);
				
//				// 商品细节信息
//				goods.setGoodsDetail(product.getDescription());
				
				// 商品上下架标识
				goodsDetail.setOnSale(product.getOnSale());
				
				// COD 标识
				goodsDetail.setCodFlag(product.getCodFlag());
				
//				 商品sku列表,包含商品组图
//				String deviceType = params.get("deviceType").toString().trim();
				//尺码颜色和库存
				List<GoodsSkuItem> goodSkus = handleSkus(product.getSkus());
				//查询sku图
//				List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType);
				//配对sku及sku的images：为int[] 1,0，以后备用拼
//				findImgList(skuImagesPairs, goodSkus);
				//设置到商品详情里
				goodsDetail.setSkuList(goodSkus);
				
				// 取消所有活动商品的标志
				goodsDetail.setIsActivityGoods(0);
				
				
				// 调用营销中心接口
				ItemSettleResultDO resultDO = itemSettleResultDOMap.get(product.getXiuSN());
				//从结算系统获得价格组成
//				ProdSettlementDO proSettlement = resultDO.getProdSettlementDO();
//				boolean isCustoms=proSettlement.isCustoms();
//				PriceSettlementDO priceSettlement=new PriceSettlementDO();
//				PriceComponentVo priceComponentVo=new PriceComponentVo();
				//是否行邮
//				priceComponentVo.setIsCustoms(isCustoms);
//				if (isCustoms) {
//					String activityPriceType = proSettlement.getActivityPriceType();
//					// 非0就是用户下单支付的价钱是活动价，显示活动价的价格组成
//					if (null != activityPriceType && !"0".equals(activityPriceType)) {
//						priceSettlement = proSettlement.getActivityPrice();
//						priceComponentVo.setPriceComponentName("活动价");
//						priceComponentVo.setTotalPriceComponent(priceSettlement.getPrice() / 100.0);
//						priceComponentVo.setBasePrice(priceSettlement.getBasePrice() / 100.0);
//						priceComponentVo.setRealCustomsTax(priceSettlement.getRealCustomsTax() / 100.0);
//						priceComponentVo.setTransportCost(priceSettlement.getTransportCost() / 100.0);
//						priceComponentVo.setEvalCustomsTax(priceSettlement.getEvalCustomsTax() / 100.0);
//					} else {
//						// 反之就是促销价或走秀价，显示走秀价的价格组成
//						priceSettlement = proSettlement.getXiuPrice();
//						priceComponentVo.setPriceComponentName("走秀价");
//						priceComponentVo.setTotalPriceComponent(priceSettlement.getPrice() / 100.0);
//						priceComponentVo.setBasePrice(priceSettlement.getBasePrice() / 100.0);
//						priceComponentVo.setRealCustomsTax(priceSettlement.getRealCustomsTax() / 100.0);
//						priceComponentVo.setTransportCost(priceSettlement.getTransportCost() / 100.0);
//						priceComponentVo.setEvalCustomsTax(priceSettlement.getEvalCustomsTax() / 100.0);
//					}
//				}
//				goodsDetail.setPriceComponentVo(priceComponentVo);
//				goodsDetail.setProSettlement(proSettlement);
//				List<ItemSettleActivityResultDO> activityList = resultDO.getActivityList();
//				List<String> list = new ArrayList<String>();
//				List<MktActInfoVo> activityItemList=new ArrayList<MktActInfoVo>();
//				//有促销活动
//				if (activityList != null && activityList.size() > 0) {
//					for (ItemSettleActivityResultDO activity : activityList) {
//						list.add(activity.getActivityId());
//						MktActInfoVo mktActInfo=new MktActInfoVo();
//						mktActInfo.setActivityId(activity.getActivityId());
//						mktActInfo.setActivityName(activity.getActivityName());
//						mktActInfo.setActivityType(activity.getActivityType());
//						activityItemList.add(mktActInfo);
//					}
//					//有促销活动-显示 促销-最终价 结算系统的走秀价[wcs-走秀价product.getPrdOfferPrice()]
//					// 走秀价为null的处理方案，显示为“null”字符串
//					
//					priceSettlement=proSettlement.getXiuPrice();
//					
//					if (0== priceSettlement.getPrice()) {
//						goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
//					} else {
//						Double xiuPrice=priceSettlement.getPrice()/100.0;//结算系统中的走秀价中包含了关税和国际运费
//						goodsDetail.setPrice(new BigDecimal(xiuPrice).setScale(2, BigDecimal.ROUND_HALF_UP));
//					}
//				}else{
//					//无促销活动-显示 促销-最终价 wcs-市场价
//					// 市场价为null的处理方案，显示为“null”字符串
//					if (product.getPrdListPrice() == null || product.getPrdListPrice().doubleValue() == 0.0) {
//						goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
//					} else {
//						goodsDetail.setPrice(new BigDecimal(product.getPrdListPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
//					}
//				}
//				goodsDetail.setActivityList(list);
//				goodsDetail.setActivityItemList(activityItemList);
				
				if (product.getPrdListPrice() == null || product.getPrdListPrice().doubleValue() == 0.0) {
					goodsDetail.setPrice(new BigDecimal(0.00).setScale(2, BigDecimal.ROUND_HALF_UP));
				} else {
					goodsDetail.setPrice(new BigDecimal(product.getPrdListPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				// 走秀价
				goodsDetail.setZsPrice(new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP));
				
				// 出发地的决定字段
//				String supplierCode = product.getSupplierCode();
//				String spaceFlag = product.getSpaceFlag();
				
				//查询发货地信息 add by coco.long 2015.7.30
				Map<String, String> paraMap = new HashMap<String, String>();
				paraMap.put("productId", goods.getInnerId());
//				DeliverInfo deliverInfo = productDao.getDeliverInfoByProduct(paraMap);
				
//				//修改发送时间信息，与app保持一致，后面再改过来 add by coco.long 2015.08.14
//				if(deliverInfo != null) {
//					String deliveryTime = deliverInfo.getDeliveryTimeInfo();
//					if(StringUtils.isNotBlank(deliveryTime)) {
//						deliveryTime = deliveryTime.replace("预计", "");
//						deliveryTime = deliveryTime.replace("天送达", "");
//						deliverInfo.setDeliveryTime(deliveryTime);
//					}
//				}
				
//				goodsDetail.setDeliverInfo(deliverInfo);
				
				// 设置配货信息
//				String tranport = getTransportInfo(supplierCode, spaceFlag);
//				goodsDetail.setTranport(tranport);
				
				// 商品属性信息
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("store_id", GlobalConstants.STORE_ID);
				paramMap.put("catentry_id", product.getInnerID());
				//List<FlexibleItem> flexibleAttr = queryGoodsProps(paramMap, product.getXiuSN());
				//goodsDetail.setFlexibleAttr(flexibleAttr);
				
				
				//限购处理
				goodsDetail.setLimitSaleNum(this.getGoodsLimitSaleNum(product));
				
				// 商品基本信息封装
				goodsDetail.setGoodsInfo(goods);
				
				goodsDetailBoList.add(goodsDetail);
			}
		}
		
		return goodsDetailBoList;
	}

	

	@Override
	public GoodsDetailVo viewGoodsDetail(
			String goodsSn, Map<String, String> params) throws Exception {
		GoodsDetailBo gd = loadXiuGoodsDetail(goodsSn, params);
		return tranformToVo(gd);
	}
	
	@Override
	public List<GoodsDetailVo> getGoodsDetailList(String goodsSnStr, Map<String, String> params) throws Exception {
		List<GoodsDetailVo> goodsDetailVoList = new ArrayList<GoodsDetailVo>();
		List<GoodsDetailBo> goodsDetailBoList = getGoodsDetailBoList(goodsSnStr, params);
		for (GoodsDetailBo goodsDetailBo : goodsDetailBoList) {
			// 商品信息详情数据转换成VO对象
			GoodsDetailVo goodsDetailVo = tranformToVo(goodsDetailBo);
			goodsDetailVoList.add(goodsDetailVo);
		}
		return goodsDetailVoList;
	}
	
	@Override
	public List<GoodsDetailVo> getGoodsDetailList(List<GoodsDetailBo> goodsDetailBoList) throws Exception {
		List<GoodsDetailVo> goodsDetailVoList = new ArrayList<GoodsDetailVo>();
		for (GoodsDetailBo goodsDetailBo : goodsDetailBoList) {
			// 商品信息详情数据转换成VO对象
			GoodsDetailVo goodsDetailVo = tranformToVo(goodsDetailBo);
			goodsDetailVoList.add(goodsDetailVo);
		}
		return goodsDetailVoList;
	}

	@Override
	public String getShopcartImg(GoodsDetailBo gd, GoodsSkuItem skuItem) {
		String url = gd.getGoodsInfo().getGoodsImgUrl();
		url = url.substring(0, url.lastIndexOf("/"));
		int[] skuImg = skuItem.getImages();
		
		for (int i = 0; i < skuImg.length; i++) {
			if (GoodsConstant.HAVE_IMG == skuImg[i]) {
				// 该SKU有多张图片时取第一张
				return url + "/" + skuItem.getSkuSn() + "/g" + (i + 1) + "_" + GoodsConstant.SHOPCART_IMG;
			}
		}
		return null;
	}

	@Override
	public List<OrderGoodsVO> buyDirect(String goodsSn, String sku,
			int quantity, Map<String, String> map) throws Exception {
		List<OrderGoodsVO> orderGoodsVOs = new ArrayList<OrderGoodsVO>();
		GoodsDetailBo gd = loadXiuGoodsDetail(goodsSn, map);
		if (null == gd) {
			logger.warn("直接购买找不到商品：" + goodsSn);
			return null;
		}
		// 检查上下架
		if (GoodsConstant.ON_SALE != Integer.valueOf(gd.getOnSale()).intValue()) {
			logger.error(goodsSn + "商品已下架");
			return null;
		}
		// 检查库存
		for (Iterator<GoodsSkuItem> iterator = gd.getSkuList().iterator(); iterator.hasNext();) {
			GoodsSkuItem gSkuItem = iterator.next();
			if (sku.equals(gSkuItem.getSkuSn())) {
				if (gSkuItem.getStock() < quantity) {
					logger.error(goodsSn + "商品库存小于购买数");
					return null;
				}
			}
		}
		GoodsInfo gi = gd.getGoodsInfo();
		OrderGoodsVO orvo = new OrderGoodsVO();
		orvo.setGoodsSn(goodsSn);
		orvo.setSkuCode(sku);
		orvo.setGoodsName(gi.getGoodsName());
		orvo.setGoodsQuantity(quantity);
		orvo.setCodFlag(gd.getCodFlag());
		if (gd.getPrice() != null) {
			orvo.setPrice(gd.getPrice().toString());
		}
		orvo.setZsPrice(gd.getZsPrice().toString());
		orvo.setDiscountPrice(gd.getZsPrice().toString());

		orderGoodsVOs.add(orvo);

		return orderGoodsVOs;
	}

	@Override
	public List<OrderGoodsVO> getOrderGoodsList(String goodsSn, String sku,
			int quantity, Map<String, String> map) throws Exception {
		// 存储返回结果值
		List<OrderGoodsVO> shopList = new ArrayList<OrderGoodsVO>();
		GoodsDetailBo goodsDetail = loadXiuGoodsDetail(goodsSn, map);
		// 商品上架状态
		int iOnSale = Integer.valueOf(goodsDetail.getOnSale()).intValue();
		if (null != goodsDetail && GoodsConstant.ON_SALE == iOnSale) {
			List<GoodsSkuItem> lstGoodsSkuItems = goodsDetail.getSkuList();
			if (null != lstGoodsSkuItems && lstGoodsSkuItems.size() > 0) {
				// 检查库存
				for (GoodsSkuItem gSkuItem : lstGoodsSkuItems) {
					if (sku.equals(gSkuItem.getSkuSn()) && gSkuItem.getStock() >= quantity) {
						GoodsInfo gi = goodsDetail.getGoodsInfo();
						OrderGoodsVO objOrderGoodsVO = new OrderGoodsVO();
						objOrderGoodsVO.setGoodsSn(goodsSn);
						objOrderGoodsVO.setSkuCode(sku);
						objOrderGoodsVO.setGoodsName(gi.getGoodsName());
						objOrderGoodsVO.setGoodsQuantity(quantity);
						objOrderGoodsVO.setCodFlag(goodsDetail.getCodFlag());
						objOrderGoodsVO.setPrice(String.valueOf(goodsDetail.getPrice()));
						objOrderGoodsVO.setZsPrice(String.valueOf(goodsDetail.getZsPrice()));
						objOrderGoodsVO.setDiscountPrice(String.valueOf(goodsDetail.getZsPrice()));
						objOrderGoodsVO.setGoodsImgUrl(gi.getGoodsImgUrl());
						//商品活动Id集合
						objOrderGoodsVO.setActivityList(goodsDetail.getActivityList());
						objOrderGoodsVO.setProdSettlementDO(goodsDetail.getProSettlement());
						shopList.add(objOrderGoodsVO);
					}
				}
			} else {
				logger.info("SKUList 为  NULL。{GoodsSN:" + goodsSn + "}");
			}

		}

		return shopList;
	}
	@Override
	public List<OrderGoodsVO> getShoppingCartGoodsList(List<ShoppingCartGoodsVo> shoppingCartGoodsList, Map<String, String> map) throws Exception {
		// 存储返回结果值
		List<OrderGoodsVO> shopList = new ArrayList<OrderGoodsVO>();
		 
		 for(ShoppingCartGoodsVo shopGoods:shoppingCartGoodsList){
				GoodsDetailBo goodsDetail = loadXiuGoodsDetail(shopGoods.getGoodsSn(), map);
				// 商品上架状态
				int iOnSale = Integer.valueOf(goodsDetail.getOnSale()).intValue();
				if (null != goodsDetail && GoodsConstant.ON_SALE == iOnSale) {
					List<GoodsSkuItem> lstGoodsSkuItems = goodsDetail.getSkuList();
					if (null != lstGoodsSkuItems && lstGoodsSkuItems.size() > 0) {
						// 检查库存
						for (GoodsSkuItem gSkuItem : lstGoodsSkuItems) {
							if (shopGoods.getGoodsSku().equals(gSkuItem.getSkuSn()) && gSkuItem.getStock() >=shopGoods.getQuantity()) {
								GoodsInfo gi = goodsDetail.getGoodsInfo();
								OrderGoodsVO objOrderGoodsVO = new OrderGoodsVO();
								objOrderGoodsVO.setGoodsSn(shopGoods.getGoodsSn());
								objOrderGoodsVO.setSkuCode(shopGoods.getGoodsSku());
								objOrderGoodsVO.setGoodsName(gi.getGoodsName());
								objOrderGoodsVO.setGoodsQuantity(shopGoods.getQuantity());
								objOrderGoodsVO.setCodFlag(goodsDetail.getCodFlag());
								objOrderGoodsVO.setPrice(String.valueOf(goodsDetail.getPrice()));
								objOrderGoodsVO.setZsPrice(String.valueOf(goodsDetail.getZsPrice()));
								objOrderGoodsVO.setDiscountPrice(String.valueOf(goodsDetail.getZsPrice()));
								objOrderGoodsVO.setGoodsImgUrl(gi.getGoodsImgUrl());
								//商品活动Id集合
								objOrderGoodsVO.setActivityList(goodsDetail.getActivityList());
								objOrderGoodsVO.setProdSettlementDO(goodsDetail.getProSettlement());
								shopList.add(objOrderGoodsVO);
							}
						}
					} else {
						logger.info("SKUList 为  NULL。{GoodsSN:" + shopGoods.getGoodsSn() + "}");
					}

				}
		 }
	

		return shopList;
	}
	
	@Override
	public List<OrderGoodsVO> getOrderGoodsList(List<ShoppingCartGoodsVo> shoppingCartGoodsList, List<GoodsDetailBo> goodsDetailBoList) throws Exception {
		// 存储返回结果值
		List<OrderGoodsVO> shopList = new ArrayList<OrderGoodsVO>();
		Map<String, GoodsDetailBo> goodsDetailBoMap = new HashMap<String, GoodsDetailBo>();
		for (GoodsDetailBo goodsDetailBo : goodsDetailBoList) {
			goodsDetailBoMap.put(goodsDetailBo.getGoodsInfo().getGoodsSn(), goodsDetailBo);
		}
		 
		 for(ShoppingCartGoodsVo shopGoods:shoppingCartGoodsList){
				GoodsDetailBo goodsDetail = goodsDetailBoMap.get(shopGoods.getGoodsSn());
				// 商品上架状态
				if(goodsDetail != null) {
					int iOnSale = Integer.valueOf(goodsDetail.getOnSale()).intValue();
					if (GoodsConstant.ON_SALE == iOnSale) {
						List<GoodsSkuItem> lstGoodsSkuItems = goodsDetail.getSkuList();
						if (null != lstGoodsSkuItems && lstGoodsSkuItems.size() > 0) {
							// 检查库存
							for (GoodsSkuItem gSkuItem : lstGoodsSkuItems) {
								if (shopGoods.getGoodsSku().equals(gSkuItem.getSkuSn()) && gSkuItem.getStock() >=shopGoods.getQuantity()) {
									GoodsInfo gi = goodsDetail.getGoodsInfo();
									OrderGoodsVO objOrderGoodsVO = new OrderGoodsVO();
									objOrderGoodsVO.setGoodsSn(shopGoods.getGoodsSn());
									objOrderGoodsVO.setSkuCode(shopGoods.getGoodsSku());
									objOrderGoodsVO.setGoodsName(gi.getGoodsName());
									objOrderGoodsVO.setGoodsQuantity(shopGoods.getQuantity());
									objOrderGoodsVO.setCodFlag(goodsDetail.getCodFlag());
									objOrderGoodsVO.setPrice(String.valueOf(goodsDetail.getPrice()));
									objOrderGoodsVO.setZsPrice(String.valueOf(goodsDetail.getZsPrice()));
									objOrderGoodsVO.setDiscountPrice(String.valueOf(goodsDetail.getZsPrice()));
									objOrderGoodsVO.setGoodsImgUrl(gi.getGoodsImgUrl());
									//商品活动Id集合
									objOrderGoodsVO.setActivityList(goodsDetail.getActivityList());
									objOrderGoodsVO.setProdSettlementDO(goodsDetail.getProSettlement());
									shopList.add(objOrderGoodsVO);
								}
							}
						} else {
							logger.info("SKUList 为  NULL。{GoodsSN:" + shopGoods.getGoodsSn() + "}");
						}

					}
				}
		 }
		return shopList;
	}

	@Override
	public String getRestTime(String goodsId, String goodsSn) {
		String getURL = GoodsConstant.REST_URL + "&goodsId=" + goodsId + "&goodsSn=" + goodsSn;
		String orig = HttpUtil.getContent(getURL);
		
		if (StringUtil.isExist(orig) && orig.startsWith(GoodsConstant.REST_PRE_CALLBACK)) {
			return orig.substring(GoodsConstant.REST_PRE_CALLBACK.length());
		}
		
		return null;
	}

	/*@Override
	public String getGoodsSkuJson(String goodsId, String sku,
			Map<String, String> params) throws Exception {
		GoodsDetailBo gd = loadXiuGoodsDetail(goodsId, params);
		
		if (null != gd) {
			GoodsSkuVo skvo = new GoodsSkuVo();
			skvo.setGoodsId(goodsId);
			skvo.setGoodsSku(sku);
			skvo.setOnSale(Integer.valueOf(gd.getOnSale()).intValue());
			List<GoodsSkuItem> skus = gd.getSkuList();
			
			for (Iterator<GoodsSkuItem> iterator = skus.iterator(); iterator.hasNext();) {
				GoodsSkuItem goodsSkuItem = iterator.next();
				
				if (goodsSkuItem.getSkuSn().equals(sku)) {
					skvo.setStock(goodsSkuItem.getStock());
					break;
				}
			}
			
			return JSON.toJSONString(skvo);
		}
		return null;
	}*/

	/**
	 * 把详情数据转换成VO对象
	 * @param gd
	 * @return
	 */
	private GoodsDetailVo tranformToVo(GoodsDetailBo gd) {
		GoodsDetailVo vo = new GoodsDetailVo();
		GoodsInfo gi = gd.getGoodsInfo();
		// 简介
		vo.setGoodsSn(gi.getGoodsSn());
		vo.setGoodsNumber(gi.getGoodsNumber());
		vo.setGoodsInnerId(gi.getInnerId());
		String imgUrl = gi.getGoodsImgUrl();
		vo.setGoodsImgUrl(imgUrl.substring(0, imgUrl.lastIndexOf("/")));
		vo.setGoodsName(gi.getGoodsName());
		vo.setBrandCode(gi.getBrandCode());
		vo.setBrandName(gi.getBrandName());
		//品牌名称
		Map brandMap = topicActivityDao.getBrandNameByGoods(gi.getInnerId());
		if(brandMap != null) {
			String cnName = (String) brandMap.get("CN_NAME");
			String enName = (String) brandMap.get("EN_NAME");
			vo.setBrandCNName(cnName);
			vo.setBrandEnName(enName);
		}
		// vo.setImgList(getDetailImgList(gd.getSkuList(),
		// vo.getGoodsImgUrl()));
		//构造sky数据，包括图片列表及尺码颜色库存
		setStyleData(vo, gd.getSkuList());
		vo.setZsPrice(gd.getZsPrice().toString());
		
		//无促销就显示折扣，有促销就显示促销内容，不显示折扣
		if(null!=gd.getActivityItemList()&&gd.getActivityItemList().size()>0){
			if (null != gd.getPrice()
					&& !new Double(0.0).equals(gd.getPrice().doubleValue())) {
				vo.setPrice(gd.getPrice().toString());
			} else {
				vo.setPrice("0");
			}
			
		}else{
			if (null != gd.getPrice()
				&& !new Double(0.0).equals(gd.getPrice().doubleValue())) {
			BigDecimal zs = gd.getZsPrice().multiply(new BigDecimal(10));
			BigDecimal ds = zs.divide(gd.getPrice(), 1,	BigDecimal.ROUND_HALF_UP);

			String discount = null;// 折扣
			if (ds.doubleValue() < 10) {
				discount = ds.toString();
				// 去掉尾数0
				if (discount.endsWith(".0")) {
					discount = discount.substring(0, discount.length() - 2);
				}
			}
			vo.setPrice(gd.getPrice().toString());
			vo.setDiscount(discount);
		} else {
			vo.setPrice("0");
		}
	}
	vo.setActivityItemList(gd.getActivityItemList());
		
		// 配送信息
		vo.setTranport(gd.getTranport());
		// 详情
		vo.setGoodsProperties(gd.getFlexibleAttr());
		vo.setDescription(gi.getDescription());
		vo.setGoodsDetail(gi.getGoodsDetail());
		// 尺码表
		vo.setSizeUrl(gi.getSizeUrl());
		vo.setStateOnsale(gd.getOnSale());
		vo.setDeliverInfo(gd.getDeliverInfo());
		vo.setPriceComponentVo(gd.getPriceComponentVo());
		
		//计算库存
		int goodsStock = 0;
		int[][] skuStockArray = vo.getStyleMatrix();
		if(null!=skuStockArray){
		for (int i = 0; i < skuStockArray.length; i++) {
			for (int j = 0; j < skuStockArray[i].length; j++) {
				goodsStock = goodsStock + skuStockArray[i][j];
			}
		}
		vo.setStock(goodsStock);
		}
		
		//退换货及供应商
		vo.setSupportRejected(gd.getSupportRejected());
		vo.setSupplierName(gd.getSupplierName());
		
		//设置汽车展示类型
		vo.setBusDisplayType(gd.getBusDisplayType());
		
		vo.setSupportPackaging(gd.getSupportPackaging()); //是否支持礼品包装
		
		//限购
		vo.setLimitSaleNum(gd.getLimitSaleNum());
		
		return vo;
	}

	/**
	 * 设置规格数据
	 * 
	 * @param vo
	 * @param skuItems
	 */
	private void setStyleData(GoodsDetailVo vo, List<GoodsSkuItem> skuItems) {
		Set<String> colorset = new TreeSet<String>();
		Set<String> sizeSet = new TreeSet<String>();
		List<FlexibleItem> skuList = new ArrayList<FlexibleItem>();
		List<FlexibleItem> imgList = new ArrayList<FlexibleItem>();
		List<String> coList = new ArrayList<String>();
		List<String> sizeList = new ArrayList<String>();
		
		for (Iterator<GoodsSkuItem> iterator = skuItems.iterator(); iterator.hasNext();) {
			GoodsSkuItem gs = iterator.next();
			colorset.add(gs.getColor());
			sizeSet.add(gs.getSize());
		}
		
		coList.addAll(colorset);
		sizeList.addAll(sizeSet);
		// 颜色和尺码
		vo.setColors(coList);
		// 系统默认排序
		Collections.sort(sizeList);
		// 自定义排序
		Collections.sort(sizeList, new ComparatorSizeAsc<String>());
		vo.setSizes(sizeList);
		// System.out.println("c:" + coList);
		// System.out.println("x:" + sizeList);
		// 库存
		int[][] m = new int[sizeList.size()][coList.size()];
		
		for (int i = 0; i < m.length; i++) {
			Arrays.fill(m[i], -1);// 填充为不存在
		}
		
		GoodsSkuItem priSku = skuItems.get(0);// 默认第一个
		int priColor = coList.indexOf(priSku.getColor());
		int priSize = sizeList.indexOf(priSku.getSize());
		
		for (Iterator<GoodsSkuItem> iterator = skuItems.iterator(); iterator.hasNext();) {
			GoodsSkuItem gsi = iterator.next();
			int k = coList.indexOf(gsi.getColor()), l = sizeList.indexOf(gsi.getSize());
			m[l][k] = gsi.getStock();// 写入库存
			// 颜色尺码和SKU值对应列表
			String key = "c" + k + "s" + l;
			skuList.add(new FlexibleItem(key, gsi.getSkuSn()));
			
			if (0 == priSku.getStock() && 0 != gsi.getStock()) {
				priColor = k;
				priSize = l;
			}
			// 构造各sku图片列表
			String im = getDetailImgList(gsi, vo.getGoodsImgUrl());
			imgList.add(new FlexibleItem(key, im));
		}
		vo.setStyleMatrix(m);
		vo.setStyleSku(skuList);
		vo.setPrimColor(priColor);
		vo.setPrimSize(priSize);
		vo.setImgList(imgList);
	}

	/**
	 * 返回商品详情图片列表 主图URL规则
	 * http://image[1-5]{0,1}.zoshow.com/goods/goods<商品创建日期>/<
	 * 走秀码>/<SKU编码>/ori[1-5]_<规则>.jpg
	 * 
	 * @return
	 */
	private String getDetailImgList(GoodsSkuItem gds, String xiuBaseImg) {
		StringBuffer sb = new StringBuffer("[");
		int[] imgs = gds.getImages();
		if(imgs!=null){
			for (int i = 0; i < imgs.length; i++) {
				if (GoodsConstant.HAVE_IMG == imgs[i]) {
					String iurl = xiuBaseImg + "/" + gds.getSkuSn() + "/g"
							+ (i + 1) + "_" + GoodsConstant.SLIDE_IMG;
					
					if (!sb.toString().equals("[")) {
						sb.append(",");
					}
					
					sb.append("\"" + iurl + "\"");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 返回商品详情图片列表 主图URL规则
	 * http://image[1-5]{0,1}.zoshow.com/goods/goods<商品创建日期>/<
	 * 走秀码>/<SKU编码>/ori[1-5]_<规则>.jpg
	 */
	private String getDetailImgList(GoodsSkuItem gds, String xiuBaseImg, String imgEnd) {
		StringBuffer sb = new StringBuffer("[");
		int[] imgs = gds.getImages();
		
		if(imgs != null) {
			for (int i = 0; i < imgs.length; i++) {
				if (GoodsConstant.HAVE_IMG == imgs[i]) {
					String iurl = xiuBaseImg + "/" + gds.getSkuSn() + "/g"
							+ (i + 1) + "_" + imgEnd;
					
					if (!sb.toString().equals("[")) {
						sb.append(",");
					}
					
					sb.append("\"" + iurl + "\"");
				}
			}
			
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * 查找sku列表的图片
	 * 
	 * @param jsonObject
	 * @param gList
	 */
	private void findImgList(List<SkuImagesPair> skuImgsPairs, List<GoodsSkuItem> goodsSkuItems) {
		if(goodsSkuItems != null && skuImgsPairs != null) {
			for (GoodsSkuItem item : goodsSkuItems) {
				for (SkuImagesPair skuImgsPair : skuImgsPairs) {
					if (skuImgsPair.getSkuSn().equals(item.getSkuSn())) {
						// 查找到相应图片
						int[] imgs = skuImgsPair.getImages();
						item.setImages(imgs);
						break;
					}
				}
			}
		}
	}
	
	public Product loadProduct(String goodsSn) {
		List<Product> products = batchLoadProductsOld(goodsSn);
		
		if (products == null || products.isEmpty()) {
			throw ExceptionFactory.buildBusinessException(
					ErrConstants.GeneralErrorCode.BIZ_CODE_NO_GOODS_SN, "商品SN不存在");
		}
		
		return products.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> batchLoadProductsOld(String goodsSnBag) {
		ProductSearchParas searchParas = new ProductSearchParas();
		searchParas.setXiuSnList(goodsSnBag);
		searchParas.setPageStep(50);

		ProductCommonParas commonParas = new ProductCommonParas();
		commonParas.setStoreID(Integer.parseInt(GlobalConstants.STORE_ID));

		Map<String, Object> result = productManager.searchProduct(commonParas, searchParas);
		List<Product> products = (List<Product>) result.get("Products");
		
		if (products == null || products.isEmpty()) {
			return null;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(products);
		}

		return products;
	}
	
	private boolean enableImageCheck = new Boolean(ParseProperties.getPropertiesValue("enable.image.check"));
	boolean ipadImageCheck = new Boolean(ParseProperties.getPropertiesValue("enable.image.check.ipad"));
	
	private List<SkuImagesPair> goodsImages(Product product, 
			int length, String deviceType, boolean isMultiPro, List<GoodsSkuItem> goodSkuList,
			Map<String, SnCustomInfoVo> snCustomsInfoMap) {
		// 特殊处理ipad
		if (GlobalConstants.DEVICE_TYPE_IPAD.equals(deviceType)) {
			if (!ipadImageCheck) {
				return defaultGoodsImages(product);
			}
		}

		// 全局开关
		if (!enableImageCheck) {
			return defaultGoodsImages(product);
		}

		try {
			Map<String, List<String>> snMap = new HashMap<String, List<String>>();
			//一品多商商品，sku集合可能并都不是属于某一个走秀码的，所以这里得拆分
			if(isMultiPro && goodSkuList != null && goodSkuList.size() > 0){
				for (Iterator<GoodsSkuItem> iterator = goodSkuList.iterator(); iterator
						.hasNext();) {
					GoodsSkuItem goodsSkuItem = iterator.next();
					List<String> skuList = snMap.get(goodsSkuItem.getXiuSn());
					if(skuList == null){
						skuList = new ArrayList<String>();
					}
					skuList.add(goodsSkuItem.getSkuSn());
					snMap.put(goodsSkuItem.getXiuSn(), skuList);
				}
			}else{
				Sku[] skus = product.getSkus();
				List<String> skuList = new ArrayList<String>();
				for (int i = 0; i < skus.length; i++) {
					skuList.add(skus[i].getSkuSN());
				}
				snMap.put(product.getXiuSN(), skuList);
			}
			List<SkuImagesPair> returnPairs = new ArrayList<SkuImagesPair>();
			for (Iterator<String> iterator = snMap.keySet().iterator(); iterator.hasNext();) {
				String sn = iterator.next();
				GoodsInfoDTO goodsDTO = new GoodsInfoDTO();
				goodsDTO.setXiuCode(sn);
				List<String> skuCodeList = snMap.get(sn);
				String[] skuCodeArr = new String[skuCodeList.size()];
				goodsDTO.setSkuCodes(skuCodeList.toArray(skuCodeArr));
				
				if(isMultiPro){
					SnCustomInfoVo snCustomInfoVo = snCustomsInfoMap.get(sn);
					goodsDTO.setCreateTimeStr(ImageUtil.parseGoodsCreateDateFromImageUrl(
							snCustomInfoVo.getImgUrl()));
				}else{
					//查询走秀码创建时间
					Goods goods = goodsDao.getGoodsBySn(sn);
					Date createTime = goods.getCreateTime();
					if(createTime != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						goodsDTO.setCreateTimeStr(sdf.format(createTime));
					}
				}
				goodsDTO.setLength(length);
				
				List<SkuImagesPair> pairs = eImageManager.checkImageExists(goodsDTO);
				if(pairs != null && pairs.size() >0){
					returnPairs.addAll(pairs);
				}
			}

			return returnPairs;
		} catch (Exception e) {
			//logger.error("", e);
			logger.error("调用图片检查接口异常： oriImageCheckHessianService.checkImageExists()");
			return defaultGoodsImages(product);
		}
	}
	
	/**
	 * 一个默认值，如果不调用检测图片接口，返回一个默认的组图数据
	 * @param product
	 * @return
	 */
	private List<SkuImagesPair> defaultGoodsImages(Product product) {
		Sku[] skus = product.getSkus();
		List<SkuImagesPair> result = new ArrayList<SkuImagesPair>(skus.length);

		for (int i = 0; i < skus.length; i++) {
			SkuImagesPair pair = new SkuImagesPair();
			pair.setSkuSn(skus[i].getSkuSN());
			pair.setImages(new int[] { 1 });
			result.add(pair);
		}

		return result;
	}
	
	// 是否启用排序，由配置文件读取
	private static Boolean sizeSortEnabled = new Boolean(ParseProperties.getPropertiesValue("size.sort.enabled"));
	// 大小码排序配置Map
	private static Map<String, Double> sizeMap = new LinkedHashMap<String, Double>();
	
	static {
		if (sizeSortEnabled) {
			String sizeConfig = ParseProperties.getPropertiesValue("size.sort.order");
			
			if (sizeConfig != null) {
				String[] sizes = sizeConfig.split(",");
				double sizeSort = -99.0;
				
				for (String size : sizes) {
					sizeMap.put(size.toUpperCase().trim(), sizeSort++);
				}
			}

			logger.info("大小码排序配置：" + sizeMap);
		}
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
			Map<String, Integer> skuCodeMap = queryInventoryBySku(skuCodeList);
			for (Sku sku : skus) {
				GoodsSkuItem skuItem = new GoodsSkuItem();
				skuItem.setColor(sku.getColorValue());
				skuItem.setSize(sku.getSizeValue());
				skuItem.setSkuSn(sku.getSkuSN());
				if (skuCodeMap.containsKey(sku.getSkuSN()) && skuCodeMap.get(sku.getSkuSN()) != null) {
					// 添加sku的库存信息
					skuItem.setStock(skuCodeMap.get(sku.getSkuSN()));
				}else{
					skuItem.setStock(-9999);
				}
				// 添加到返回结果中
				skuList.add(skuItem);
			}
		}

		if (sizeSortEnabled) {
			this.sortSkusBySize(skuList);
		}

		return skuList;
	}
	
	/**
	 * 查询库存信息
	 * @param sku
	 * @return
	 */
	public int queryInventoryBySku(String skuCode) {
		try {
			return eiChannelInventoryManager.inventoryQueryBySkuCodeAndChannelCode(GlobalConstants.CHANNEL_ID, skuCode);
		} catch (Exception e) {
			logger.error("查询库存出错", e);
			return -9999;
		}
	}
	
	/**
	 * 查询库存信息
	 * @param sku
	 * @return
	 */
	public Map<String, Integer> queryInventoryBySku(List<String> skuCodeList) {
		try {
			return eiChannelInventoryManager.inventoryQueryBySkuCodeList(GlobalConstants.CHANNEL_ID, skuCodeList);
		} catch (Exception e) {
			logger.error("查询库存出错", e);
			return new HashMap<String, Integer>();
		}
	}
	
	private void sortSkusBySize(List<GoodsSkuItem> skuList) {
		// 至少2个SKU才处理
		if (skuList == null || skuList.size() < 2) {
			return;
		}

		Collections.sort(skuList, new Comparator<GoodsSkuItem>() {

			/**
			 * 几种形式的尺码：
			 * 大小码：XXS XS S M L XL XXL XXXL
			 * 数字码：1 1.5 12 12.5
			 * 数字+字母： 18 M, 6 M, 12 M
			 * 数字+字母： 18M, 6M, 12M
			 * 
			 * 
			 * @param o1
			 * @param o2
			 * @return
			 */
			@Override
			public int compare(GoodsSkuItem o1, GoodsSkuItem o2) {

				String size1 = ObjectUtils.toString(o1.getSize());
				String size2 = ObjectUtils.toString(o2.getSize());

				double value1 = this.parseSize2Digit(size1);
				double value2 = this.parseSize2Digit(size2);

				return value1 - value2 > 0 ? 1 : -1;
			}

			private double parseSize2Digit(String size) {
				size = size.toUpperCase().trim();

				// 首先是否在sizeMap中配置
				if (sizeMap.get(size) != null) {
					return sizeMap.get(size);
				}

				// 再尝试转为数字
				double value = NumberUtils.toDouble(size, -1);
				if (value != -1.0) {
					return value;
				}

				// 再尝试去掉字母和空格后转为数字
				size = size.replaceAll("([a-zA-Z]+)|(\\s+)", "");
				value = NumberUtils.toDouble(size, -1);
				if (value != -1.0) {
					return value;
				}

				// 将不能处理的放在最后
				return 999999;
			}

		});
	}
	
	public List<FlexibleItem> queryGoodsProps(Map<String, Object> paramMap,String goodsSn) {
		List<FlexibleItem> result = new ArrayList<FlexibleItem>();
		FlexibleItem flexible = new FlexibleItem("商品编号",goodsSn);
		result.add(flexible);
		// 商品属性信息列表
		Map<String, String> propertyMap = new LinkedHashMap<String, String>();
		// 首先获取新属性
		List<Map<String, Object>> items = topicActivityDao.queryNewAttrValByCatentryId(paramMap);
		if (items != null && items.size() > 0) {
			for (Map<String, Object> item : items) {
				// 判断是否为重复重复属性  则拼装成一个值用,分割
				if (propertyMap.containsKey(item.get("NAME"))) {
					String values = propertyMap.get(item.get("NAME"));
					values = values.concat(",").concat(item.get("VALUE").toString());
					propertyMap.put(item.get("NAME").toString(), values);
				}else{
					propertyMap.put(item.get("NAME").toString(), item.get("VALUE").toString());
				}
			}
			
			// 获取商品属性map的keys集合
			Iterator<String> keys = propertyMap.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				String value = propertyMap.get(key);
				FlexibleItem flexibleItem = new FlexibleItem(key,value);
				result.add(flexibleItem);
			}
			return result;
		}

		// 如果没有新属性，再去获取旧属性
		items = topicActivityDao.queryOldAttrValByCatentryId(paramMap);
		if (items != null && items.size() > 0) {
			Map<String, Object> item = items.get(0);
			Object attrs = item.get("ATTRS");
			if (attrs != null && !attrs.toString().trim().isEmpty()) {
				List<FlexibleItem> attrItemsList = parseAttrString(attrs.toString().trim());
				if (attrItemsList!=null) {
					result.addAll(attrItemsList);
				}
			}
		}
		return result;
	}
	
	private List<FlexibleItem> parseAttrString(String str) {
		// 商品属性信息列表
		Map<String, String> propertyMap = new LinkedHashMap<String, String>();
		List<FlexibleItem> result = new ArrayList<FlexibleItem>();
		String[] attrs = str.split("；");
		
		if (attrs == null || attrs.length == 0) {
			return result;
		}
		for (String attr : attrs) {
			if (!attr.trim().isEmpty()) {
				String[] kv = attr.trim().split("：");
				// 判断是否为重复重复属性  则拼装成一个值用,分割
				if (propertyMap.containsKey(kv[0])) {
					String values = propertyMap.get(kv[0]);
					values = values.concat(",").concat(kv[1]);
					propertyMap.put(kv[0], values);
				}else{
					propertyMap.put(kv[0], kv[1]);
				}	
			}
		}
		
		// 获取商品属性map的keys集合
		Iterator<String> keys = propertyMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = propertyMap.get(key);
			FlexibleItem flexibleItem = new FlexibleItem(key,value);
			result.add(flexibleItem);
		}
		
		return result;
	}
	
	/**
	 * 调用营销中心接口===调用结算系统的接口
	 * @param product
	 * @return
	 */
	public ItemSettleResultDO invokeSaleInterface(Product product) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("----------------------------------------------------------------------");
			logger.debug("product.getXiuSN() = " + product.getXiuSN());
			logger.debug("product.getPrdOfferPrice() = " + product.getPrdOfferPrice());
			logger.debug("product.getPrdActivityPrice() = " + product.getPrdActivityPrice());
			logger.debug("product.getPrdActivityPriceType() = " + product.getPrdActivityPriceType());
		}

		ItemSettleResultDO param = new ItemSettleResultDO();
		// 走秀码
		param.setGoodsSn(product.getXiuSN());
		// 走秀价
		if(null == product.getPrdOfferPrice()){
			logger.error("非法商品，走秀价为空，秀走码是：" + product.getXiuSN());
		}
		Double goodsPrice = product.getPrdOfferPrice() * 100;
		param.setXiuPrice(goodsPrice.longValue());

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
		//描述：终端用户(1 手机用户 2 电脑用户,多个用逗号分开)，默认值2
		param.setTerminalUser("1");
		Result result = eiPromotionManager.itemSettleForXmlRpc(param);
		ItemSettleResultDO resultDO = (ItemSettleResultDO) result.getDefaultModel();

		if (logger.isDebugEnabled()) {
			logger.debug("resultDO.getDiscountPrice() / 100.0 = "
					+ (resultDO.getDiscountPrice() / 100.0));
		}

		return resultDO;
	}
	
	/**
	 * 
	 * 获取配货信息
	 * 
	 * @param supplierCode 1528-ebay,null&&'' - 官网
	 * @param spaceFlag 为空时不返回配货信息
	 * @return String
	 */
	public String getTransportInfo(String supplierCode, String spaceFlag) {
		String sendPlace = "";
		String arrivalTime = "";
		String result = "";
		
		if (StringUtils.isNotEmpty(spaceFlag)) {
			int spcFlag = Integer.parseInt(spaceFlag);
			
			// supplierCode值为空时表示 平台商为 官网
			if (StringUtils.isEmpty(supplierCode)) {
				switch (spcFlag) {
				case 0:// 国内
					sendPlace = SendingPlaceMsgs.domestic.getMessage();
					arrivalTime = ArrivalTimeMsgs.domestic.getMessage();
					break;
				case 1:// 香港
					sendPlace = SendingPlaceMsgs.hongkong.getMessage();
					arrivalTime = ArrivalTimeMsgs.hongkong.getMessage();
					break;
				case 2:// 美国
					arrivalTime = ArrivalTimeMsgs.america.getMessage();
					sendPlace = SendingPlaceMsgs.overseas.getMessage();
					break;
				case 3:// 美国
					arrivalTime = ArrivalTimeMsgs.overseasone.getMessage();
					sendPlace = SendingPlaceMsgs.overseas.getMessage();
					break;
				case 4:// 海外二类
					arrivalTime = ArrivalTimeMsgs.overseastwo.getMessage();
					sendPlace = SendingPlaceMsgs.overseas.getMessage();
					break;
				case 5:// 韩风
					arrivalTime = ArrivalTimeMsgs.koreanstyle.getMessage();
					sendPlace = SendingPlaceMsgs.overseas.getMessage();
					break;
				case 6:// 韩国
					arrivalTime = ArrivalTimeMsgs.korea.getMessage();
					sendPlace = SendingPlaceMsgs.overseas.getMessage();
					break;
				}
			} else {
				// supplierCode值为1528 时表示 平台商为 ebay
				if ("1528".equals(supplierCode)) {
					arrivalTime = ArrivalTimeMsgs.overseastwo.getMessage();
					sendPlace = SendingPlaceMsgs.overseas.getMessage();
				}
			}
		}
		result = sendPlace + "，预计" + arrivalTime + "送达";
		// 如果没有值则返回空值
		return StringUtils.isEmpty(sendPlace) ? "" : result;
	}

	@Override
	public Double getZxPrice(String goodsSn){
		try{
		// 查询xiu接口
		Product product =loadProduct(goodsSn);
		// 调用营销中心接口
		ItemSettleResultDO resultDO = invokeSaleInterface(product);
		Double price=(resultDO.getDiscountPrice() / 100.0);
		return price;
		}catch(Exception e){
			logger.error("根据走秀码获取对应商品价格异常:"+e.getMessage());
			return null;
		}
		
	}

	@Override
	public Long getBrandIdByGoodsSn(String goodsSn)throws Exception{
		Long brandId=goodsDao.getBrandIdByGoodsSn(goodsSn);
		return brandId;
	}

	@Override
	public Double getZxPrice(Product product) {
		try {
			// 调用营销中心接口
			ItemSettleResultDO resultDO = invokeSaleInterface(product);
			Double price = (resultDO.getDiscountPrice() / 100.0);
			return price;
		} catch (Exception e) {
			logger.error("根据走秀码获取对应商品价格异常:" + e.getMessage());
			return null;
		}
	}

	@Override
	public List<GoodsSkuItem> getSkuItemsByGoodsSn(String goodsSn) {
		// 查询xiu接口
		Product product = loadProduct(goodsSn.trim());
		List<GoodsSkuItem> goodSkus = handleSkus(product.getSkus());
		//查询sku图
		return goodSkus;
	}

	@Override
	public GoodsSkuItem getSkuItemByGoodsSnAndSku(String goodsSn,String goodsSku) {
		List<GoodsSkuItem> goodSkuList = getSkuItemsByGoodsSn(goodsSn);
		for (GoodsSkuItem goodsSkuItem : goodSkuList) {
			if (goodsSkuItem.getSkuSn().equals(goodsSku)) {
				return goodsSkuItem;
			}
		}
		return null;
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

	
	public String getSupplierDisplayName(String supplierCode)throws Exception{
		String supplierDisplayName  = goodsDao.getSupplierDisplayName(supplierCode);
		return supplierDisplayName;
	}
	
	@SuppressWarnings("unchecked")
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
	 * 查询品牌信息ByGoodsSn
	 * @param goodsSn
	 * @return
	 */
	private BrandInfoVO getBrandInfo(String goodsSn, Long brandId) {
		BrandInfoVO brandInfo = new BrandInfoVO();
		int goodsNum = 0;
		int commentNum = 0;
		try {
			if(brandId == null){
				brandId = getBrandIdByGoodsSn(goodsSn); //查询品牌ID
			}
			//查询品牌信息
			brandInfo = goodsDao.getBrandInfoByBrandId(brandId.toString());
			
			if(brandInfo != null) {
				String logo = brandInfo.getLogo();
				if(StringUtils.isNotBlank(logo)) {
					brandInfo.setLogo(ImageUtil.getBrandLogo(logo));
				}
				
				String url = "";	//接口地址
				
				// 查询商品数量
				url = GlobalConstants.GET_GOODSNUM_BY_BRAND + "?bId="+brandId+"&v=2.0";
				String httpResult = HttpUtils.getMethod(url, 10000, 15000);
				if(StringUtils.isNotBlank(httpResult)) {
					ResultDO goodsResult = JsonUtils.json2bean(httpResult, ResultDO.class);
					String data = goodsResult.getData();
					goodsNum = Integer.parseInt(data);
				}
				
				// 查询评论数量
				url = GlobalConstants.GET_COMMENTNUM_BY_BRAND + "?brandId="+brandId+"&format=jsonp";
				httpResult = HttpUtils.getMethod(url, 10000, 15000);
				if(StringUtils.isNotBlank(httpResult)) {
					try{
						ResultDO commentResult = JsonUtils.json2bean(httpResult, ResultDO.class);
						String data = commentResult.getData();
						commentNum = Integer.parseInt(data);
					}catch (Exception e) {
						logger.error("查询评论数据信息异常，url："+url+"，返回数据："+httpResult+"，错误信息："+e.getMessage());
					} 
				}
				
				brandInfo.setGoodsNum(goodsNum);
				brandInfo.setCommentNum(commentNum);
			}
		} catch (Exception e) {
			logger.error("查询品牌信息异常，商品goodsSn："+goodsSn+"，错误信息："+e.getMessage());
			brandInfo.setGoodsNum(goodsNum);
			brandInfo.setCommentNum(commentNum);
		} 
		return brandInfo;
	}
	
	/**
	 * 查询是否海外商品
	 * @param goodsSn
	 * @return
	 */
	public boolean isGoodsCustoms(String goodsSn) {
		boolean isCustoms = false;
		List<Product> productList = batchLoadProducts(goodsSn);
		if(productList != null && productList.size() > 0) {
			Product product = productList.get(0);
			// 调用营销中心接口
			ItemSettleResultDO resultDO = invokeSaleInterface(product);
			// 从结算系统获得价格组成
			ProdSettlementDO proSettlement = resultDO.getProdSettlementDO();
			isCustoms = proSettlement.isCustoms();	
		}
		return isCustoms;
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
	 * 查询价格组成信息
	 * @param product
	 * @return
	 */
	private PriceComponentVo getPriceComponentInfo(Product product, GoodsDetailsVO goodsDetail) {
		// 调用营销中心接口
		ItemSettleResultDO resultDO = invokeSaleInterface(product);
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
		
		//促销活动 WCS特卖活动 
		List<ItemSettleActivityResultDO> activityList = resultDO.getActivityList();
		if(GoodsConstant.WCS_ACT.equals(product.getPrdActivityPriceType()) || (activityList != null && activityList.size() > 0)) {
			//如果有活动，则显示活动价
			if(activityList != null && activityList.size() > 0) {
				String promotionInfo = ""; //促销活动信息
				for (ItemSettleActivityResultDO activity : activityList) {
					//商品详情页只取一级活动
					promotionInfo = activity.getActivityName();
					break;
				}
				goodsDetail.setPromotionInfo(promotionInfo);
			}
			
			priceSettlement=proSettlement.getXiuPrice();
			
			if (0== priceSettlement.getPrice()) {//走秀价为空
				goodsDetail.setPrice("0");
			} else {
				//不为空
				Double price =product.getPrdListPrice();//商品原价
				if(price!=null){
					goodsDetail.setPrice(getPrice(String.valueOf(price)));
				}else{
					Double xiuPrice=priceSettlement.getPrice()/100.0; //结算系统中的走秀价中包含了关税和国际运费
					goodsDetail.setPrice(getPrice(String.valueOf(xiuPrice)));
				}
			}
		} else {
			//商品原价
			if(product.getPrdListPrice() == null || product.getPrdListPrice().doubleValue() == 0.0) {
				goodsDetail.setPrice("0");
			} else {
				String price = String.valueOf(product.getPrdListPrice());
				goodsDetail.setPrice(getPrice(price));
			}
		}
		//走秀价
		BigDecimal zsPriceD=new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		String zsPrice = String.valueOf(zsPriceD);
		goodsDetail.setZsPrice(getPrice(zsPrice));

		
//		if(zsPriceD<)
		
		//豪车价格处理
//		if (product.getMCatCode()!=null&&GoodsConstant.BUS.equals(product.getMCatCode())) {
		BigDecimal xiuPrice =new BigDecimal(priceSettlement.getPrice()/100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		if(zsPriceD.compareTo(xiuPrice)==-1){//如果有比走秀价还低的价格，则原价设为走秀价
			String xiuPriceStr = String.valueOf(xiuPrice);
			goodsDetail.setPrice(getPrice(xiuPriceStr));
		}
//		} 
		
		//如果走秀价小于1000，则不支持求购
		if(resultDO.getDiscountPrice() < 100000) {
			goodsDetail.setSupportAskBuy(false);
		} else {
			goodsDetail.setSupportAskBuy(true);
		}
		
		return priceComponentVo;
	}
	
	/**
	 * 查询商品比价信息
	 * @param goodsSn
	 * @param goodsDetail
	 * @return
	 */
	private PriceCompareVo getPriceCompareInfo(String goodsSn, String strZsPrice) {
		PriceCompareVo priceCompare = null;
		try{
			priceCompare = pcsService.getPriceCompareBySn(goodsSn);
		}catch(Exception e){
			logger.error("获取比价信息异常，goodsSn: " + goodsSn, e);
		}
		
		if (priceCompare != null) {
			// 非整型数，运算由于精度问题，可能会有误差，使用BigDecimal类型
			BigDecimal zsPrice = new BigDecimal(strZsPrice);
			BigDecimal comparePrice = new BigDecimal(priceCompare.getGoodsRMBPrice());
			int result = zsPrice.compareTo(comparePrice);
			// 如果走秀价大于其他官网价 则不展示
			if (result >= 0) {
				// 清除掉比价数据
				priceCompare = new PriceCompareVo();
				priceCompare.setShowStatus(false);
			}else{
				priceCompare.setShowStatus(true);
			}
		} else {
			// 清除掉比价数据
			priceCompare = new PriceCompareVo();
			priceCompare.setShowStatus(false);
		}
		
		return priceCompare;
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
	 * 处理商品的Sku数据
	 * @param product
	 * @param deviceType
	 */
	private void handleSkuData(List<GoodsSkuItem> goodSkuList, Product product,
			String deviceType, GoodsDetailsVO goodsDetail, Map<String, SnCustomInfoVo> snMap, String mainSku, boolean isMultiPro) {
		List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType, isMultiPro, goodSkuList, snMap); // 查询sku图
		findImgList(skuImagesPairs, goodSkuList); // 配对sku及sku的images：为int[] 1,0，以后备用拼
		
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

		//SKU图片保存器  key:颜色   value:图片地址
		Map<String, List<String>> skuImageListHolder = new HashMap<String, List<String>>();
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
			//一品多商，把不同xiuSn的动态属性冗余到SKU上
			if(snMap != null){
				SnCustomInfoVo snCustomInfoVo = snMap.get(sku.getXiuSn());
				//查询行邮价格组成以及市场价走秀价信息
				Map<String, Object> priceMap = getPriceComponentInfoAndPromotionInfo(snCustomInfoVo.getItemSettleResultDO(), product.getMCatCode());
				skuItem.setGoodsId(snCustomInfoVo.getProductId() == null ? null : snCustomInfoVo.getProductId().toString());
				skuItem.setGoodsSn(snCustomInfoVo.getProductSn());
				skuItem.setGoodsName(snCustomInfoVo.getPrdName());
				skuItem.setPrice((String) priceMap.get("price"));
				skuItem.setZsPrice((String) priceMap.get("zsPrice"));
				skuItem.setSupportPackaging(snCustomInfoVo.isSupportGift());
				skuItem.setPromotionInfo((String) priceMap.get("promotionInfo"));
				//行邮价格组成
				PriceComponentVo priceComponentVo = (PriceComponentVo) priceMap.get("priceComponentInfo");
				skuItem.setPriceComponentInfo(priceComponentVo);
				//发货地信息
				DeliveryInfoVo deliveryInfoVo = snCustomInfoVo.getDeliveryInfo();
				DeliverInfo deliverInfo = new DeliverInfo();
				copyDeliveryInfo(deliveryInfoVo, deliverInfo);
				skuItem.setDeliverInfo(deliverInfo);
				
				//比价信息
				PriceCompareVo priceCompareVo = this.getPriceCompareInfo(snCustomInfoVo.getProductSn(), skuItem.getZsPrice());
				skuItem.setPriceCompareInfo(priceCompareVo);
				
				//如果是主商品，则设置一下
				if(goodsDetail.getGoodsSn().equals(sku.getXiuSn())){
					goodsDetail.setPrice((String) priceMap.get("price"));
					goodsDetail.setZsPrice((String) priceMap.get("zsPrice"));
					goodsDetail.setPromotionInfo((String) priceMap.get("promotionInfo"));
					goodsDetail.setSupportPackaging(snCustomInfoVo.isSupportGift());
					//必须重新new一个对象，否则有问题
					PriceComponentVo newPriceComponentVo = new PriceComponentVo();
					try {
						BeanUtils.copyProperties(newPriceComponentVo, priceComponentVo);
					} catch (Exception e) {
						logger.error("属性复制异常", e);
					}
					goodsDetail.setPriceComponentInfo(newPriceComponentVo);
					//必须重新new一个对象，否则有问题
					deliverInfo = new DeliverInfo();
					copyDeliveryInfo(deliveryInfoVo, deliverInfo);
					goodsDetail.setDeliverInfo(deliverInfo);
					//主商品的比价信息
					PriceCompareVo newPriceCompareVo = new PriceCompareVo();
					try {
						BeanUtils.copyProperties(newPriceCompareVo, priceCompareVo);
					} catch (Exception e) {
						logger.error("属性复制异常", e);
					}
					goodsDetail.setPriceCompareInfo(newPriceCompareVo);
				}
				//支持礼品包装，查询包装商品ID
				if(snCustomInfoVo.isSupportGift()) {
					Map packagingResult = getProductPackagingGoods();
					if(packagingResult != null) {
						String packagingGoodsId = (String) packagingResult.get("goodsId");
						skuItem.setPackagingGoodsId(packagingGoodsId);
						//如果是主商品，则设置一下
						if(goodsDetail.getGoodsSn().equals(sku.getXiuSn())){
							goodsDetail.setPackagingGoodsId(packagingGoodsId);
						}
					}
				}
				
				// 构造各sku图片列表
				skuItem.setImageList(handleSkuImageList(skuImageListHolder, sku, snCustomInfoVo.getImgUrl()));
			}else{
				skuItem.setImageList(handleSkuImageList(skuImageListHolder, sku, goodsDetail.getGoodsImgUrl()));
			}
		
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
	 * 处理SKU图片列表
	 * @param skuImageListHolder
	 * @param sku
	 * @param goodsImgUrl
	 * @return
	 */
	private List<String> handleSkuImageList(Map<String, List<String>> skuImageListHolder, GoodsSkuItem sku,
											String goodsImgUrl){

		List<String> imgList = skuImageListHolder.get(sku.getColor());

		if(CollectionUtils.isNotEmpty(imgList)){
			return imgList;
		}

		if(imgList == null){
			imgList = new ArrayList<String>(5);
			skuImageListHolder.put(sku.getColor(), imgList);
		}

		// 构造各sku图片列表
		goodsImgUrl = goodsImgUrl.substring(0, goodsImgUrl.lastIndexOf("/"));
		String im = getDetailImgList(sku, goodsImgUrl, GoodsConstant.SLIDE_IMG_MAX);
		if(StringUtils.isNotBlank(im)) {
			im = im.replace("[", "").replace("]", "").replace("\"", "");

			imgList.addAll(Arrays.asList(im.split(",")));
		}

		return imgList;
	}
	
	/**
	 * 处理秀客商品详情的Sku数据
	 * @param product
	 * @param deviceType
	 */
	private void handleShowSkuData(List<GoodsSkuItem> goodSkuList, Product product,
			String deviceType, GoodsDetailsVO goodsDetail, Map<String, SnCustomInfoVo> snMap, String mainSku, boolean isMultiPro) {
		List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType, isMultiPro, goodSkuList, snMap); // 查询sku图
		findImgList(skuImagesPairs, goodSkuList); // 配对sku及sku的images：为int[] 1,0，以后备用拼
		
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
		//尺码排序
		Collections.sort(sizeList);	
		Collections.sort(sizeList, new ComparatorSizeAsc<String>());
		
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

		//SKU图片保存器  key:颜色   value:图片地址
		Map<String, List<String>> skuImageListHolder = new HashMap<String, List<String>>();
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
			//一品多商，把不同xiuSn的动态属性冗余到SKU上
			if(snMap != null){
				SnCustomInfoVo snCustomInfoVo = snMap.get(sku.getXiuSn());
				//查询行邮价格组成以及市场价走秀价信息
				Map<String, Object> priceMap = getPriceComponentInfoAndPromotionInfo(snCustomInfoVo.getItemSettleResultDO(), product.getMCatCode());
				skuItem.setGoodsId(snCustomInfoVo.getProductId() == null ? null : snCustomInfoVo.getProductId().toString());
				skuItem.setGoodsSn(snCustomInfoVo.getProductSn());
				skuItem.setGoodsName(snCustomInfoVo.getPrdName());
				skuItem.setPrice((String) priceMap.get("price"));
				skuItem.setZsPrice((String) priceMap.get("zsPrice"));
				skuItem.setSupportPackaging(snCustomInfoVo.isSupportGift());
				
				//如果是主商品，则设置一下
				if(goodsDetail.getGoodsSn().equals(sku.getXiuSn())){
					goodsDetail.setPrice((String) priceMap.get("price"));
					goodsDetail.setZsPrice((String) priceMap.get("zsPrice"));
					goodsDetail.setPromotionInfo((String) priceMap.get("promotionInfo"));
					goodsDetail.setSupportPackaging(snCustomInfoVo.isSupportGift());
				}

				// 构造各sku图片列表
				skuItem.setImageList(handleSkuImageList(skuImageListHolder, sku, snCustomInfoVo.getImgUrl()));
			}else{
				skuItem.setImageList(handleSkuImageList(skuImageListHolder, sku, goodsDetail.getGoodsImgUrl()));
			}
		
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
	 * 处理商品评论
	 */
	private void handleGoodsComment(GoodsDetailsVO goodsDetail) {
		String prodId = goodsDetail.getGoodsId();
		String url = XiuConstant.GET_COMMENTNUM_BY_PROD + "?prodId="+prodId+"&format=jsonp";
		String httpResult;
		try {
			httpResult = HttpUtils.getMethod(url, 10000, 15000);
			if(!StringUtils.isEmpty(httpResult)) {
				ResultDO commentResult = JsonUtils.json2bean(httpResult, ResultDO.class);
				String data = commentResult.getData();
				goodsDetail.setCommentNum(Integer.parseInt(data));
			}
		} catch (Exception e) {
			goodsDetail.setCommentNum(0);
			logger.error("查询商品评论异常，Url："+url+"，错误信息："+e.getMessage());
		}
	}
	
	/**
	 * 获取商品评论评分
	 * @param comBO
	 * @return
	 */
	private double getAveNumberValue(CommentBOResult comBO) {
		double result = 0;
		if(comBO != null) {
			CommentResult comment = comBO.getComment();
			if(comment != null) {
				String aveNumber = comment.getAveNumber();
				if(StringUtils.isNotBlank(aveNumber)) {
					try {
						result = Double.parseDouble(aveNumber);
					} catch (Exception e){
						logger.error("商品评论评分转换异常，aveNumber："+aveNumber+"，错误信息："+e.getMessage());
						return result;
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取评论对象：
	 * 如果评论列表包含5字以上的评论，则取5字以上评论列表中评分最高的评论
	 * 如果都在5字以下，则取评论列表中评分最高的评论
	 * @param comBOList
	 * @return
	 */
	private CommentBOResult getCommentBOByAveAndLength(List<CommentBOResult> comBOList) {
		CommentBOResult result = null; //返回对象
		
		if(comBOList != null && comBOList.size() > 0) {
			if(comBOList.size() == 1) {
				//如果只有一条评论
				return comBOList.get(0);
			}
			
			//返回结果已按评分降序排序
			for(CommentBOResult comBO : comBOList) {
				CommentResult comment = comBO.getComment();
				if(comment != null) {
					String commentContent = comment.getCommentContent();
					if(StringUtils.isNotBlank(commentContent) && commentContent.length() >= 5) {
						//如果评论有5字，则返回
						return comBO;
					}
				}
			}
			
			//如果没有超过5字的评论，则默认返回第一条
			return comBOList.get(0); 
		}
		return result;
	}
	
	/**
	 * 查询商品评论列表
	 * @param goodsId
	 * @param brandId
	 * @return
	 */
	private List<GoodsCommentVO> getGoodsCommentList(String goodsId, String brandId) {
		List<GoodsCommentVO> commentList = new ArrayList<GoodsCommentVO>();
		if(StringUtils.isBlank(goodsId) || StringUtils.isBlank(brandId)) {
			//如果商品ID或者品牌ID为空，则返回
			return commentList;
		}
		String url = XiuConstant.GET_COMMENT_BY_PROD + "?prodId="+goodsId+"&brandId="+brandId+"&searchType=1&pageType=0&catgCode=0&pageNo=1&pageSize=10";
		String httpResult = "";
		try {
			httpResult = HttpUtils.getMethod(url, 10000, 15000);
			if(!StringUtils.isEmpty(httpResult)) {
				CommentListResult commentResult = JsonUtils.json2bean(httpResult, CommentListResult.class);
				List<CommentBOResult> comBOList = commentResult.getComBO(); //接口返回的评论列表数据
				if(comBOList != null && comBOList.size() > 0) {
					GoodsCommentVO commentVO = new GoodsCommentVO(); //返回的商品对象
					CommentBOResult comBO = getCommentBOByAveAndLength(comBOList); //获取评论对象
					if(comBO != null) {
						CommentResult comment = comBO.getComment();
						CommentProdResult commentProd = comBO.getCommentProd();
						CommentUserResult commentUser = comBO.getCommentUser();
						if(comment != null) {
							//评论对象
							commentVO.setAveNumber(String.valueOf(comment.getAveNumber()));
							commentVO.setCommentId(comment.getCommentId());
							String commentDate = comment.getCommentDate();
							if(StringUtils.isNotBlank(commentDate)) {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								String date = format.format(new Date(Long.parseLong(commentDate)));
								commentVO.setCommentDate(date);
							}
							commentVO.setCommentContent(comment.getCommentContent());
						}
						if(commentProd != null) {
							//商品对象
							commentVO.setProdId(commentProd.getProdId());
							commentVO.setProdSku(commentProd.getProdSku());
							commentVO.setProdColor(commentProd.getProdColor());
							commentVO.setProdSize(commentProd.getProdSize());
						}
						if(commentUser != null) {
							//评论用户对象
							commentVO.setUserId(commentUser.getUserId());
							commentVO.setPetName(commentUser.getUserNick());
							commentVO.setIconUrl(commentUser.getIconUrl());
						}
						commentList.add(commentVO); //添加
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询商品评论列表异常，Url："+url+"，错误信息："+e.getMessage());
		}
		
		return commentList;
	}
	
	/**
	 * 查询限购数量，-1表示不支持限购
	 * @param product
	 * @return
	 */
	private int getGoodsLimitSaleNum(ProdBaseInfoVo prodBaseInfoVo) {
		int limitSaleNum = -1;	//默认-1，表示不支持
		String limitSaleFlag = prodBaseInfoVo.getLmtSaleFlg();
		if(StringUtils.isNotBlank(limitSaleFlag)) {
			if(limitSaleFlag.equals("1")) {
				Date lmtSaleStart = DateUtils.parseDate(prodBaseInfoVo.getLmtSaleStartStr(), "yyyy-MM-dd HH:mm:ss");
				Date lmtSaleEnd = DateUtils.parseDate(prodBaseInfoVo.getLmtSaleEndStr(), "yyyy-MM-dd HH:mm:ss");
				Integer lmtSaleNum = prodBaseInfoVo.getLmtSaleCnt();
				Date now = new Date();
				if(lmtSaleNum != null && lmtSaleNum > 0) {
					//限购数量大于0
					if(lmtSaleStart != null && lmtSaleEnd != null ) {
						//如果限购时间不为空，则判断当前时间是否在限购时间范围内
						if(now.after(lmtSaleStart) && now.before(lmtSaleEnd) ) {
							limitSaleNum = lmtSaleNum;
						}
					} else {
						limitSaleNum = lmtSaleNum;
					}
				}
			} 
		} 
		
		return limitSaleNum;
	}
	
	/**
	 * 查询限购数量，-1表示不支持限购
	 * @param product
	 * @return
	 */
	private int getGoodsLimitSaleNum(Product product) {
		int limitSaleNum = -1;	//默认-1，表示不支持
		if(product != null) {
			String limitSaleFlag = product.getLmtSaleFlg();
			if(StringUtils.isNotBlank(limitSaleFlag)) {
				if(limitSaleFlag.equals("1")) {
					Date lmtSaleStart = product.getLmtSaleStart();
					Date lmtSaleEnd = product.getLmtSaleEnd();
					Integer lmtSaleNum = product.getLmtSaleCnt();
					Date now = new Date();
					if(lmtSaleNum != null && lmtSaleNum > 0) {
						//限购数量大于0
						if(lmtSaleStart != null && lmtSaleEnd != null ) {
							//如果限购时间不为空，则判断当前时间是否在限购时间范围内
							if(now.after(lmtSaleStart) && now.before(lmtSaleEnd) ) {
								limitSaleNum = lmtSaleNum;
							}
						} else {
							limitSaleNum = lmtSaleNum;
						}
					}
				} 
			} 
		}
		
		return limitSaleNum;
	}
	
	/**
	 * 查询商品限购数量，-1表示不限购
	 */
	public int getGoodsLimitSaleNum(String goodsSn) {
		List<Product> productList = batchLoadProducts(goodsSn);
		Product product = productList.get(0);
		int result = getGoodsLimitSaleNum(product);
		return result;
	}
	
	/**
	 * 查询商品限购数量，-1表示不限购，查询用户的历史订单
	 */
	public int getGoodsLimitSaleNum(String goodsSn, String userId) {
		int limitSaleNum = - 1;
		if(StringUtils.isBlank(goodsSn)) {
			return limitSaleNum;
		}
		
		Map limitMap = getGoodsLimitSaleInfo(goodsSn); //查询商品限购信息
		
		if(limitMap != null) {
			limitSaleNum = Integer.parseInt(limitMap.get("limitSaleNum").toString());
			
			if(limitSaleNum != -1 && StringUtils.isNotBlank(userId)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("goodsSn", goodsSn);
				map.put("userId", userId);
				
				//限购商品
				if(limitMap.containsKey("lmtSaleStart") && limitMap.containsKey("lmtSaleEnd")) {
					//如果限购时间不为空，则查询此时间范围内用户已购买此商品的数量是否超过限购商品的数量，否则查询所有
					String beginDate = (String) limitMap.get("lmtSaleStart");
					String endDate = (String) limitMap.get("lmtSaleEnd");
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
				}
				
				//查询用户购买商品的数量
				int buyGoodsNum = orderService.getUserBuyGoodsCount(map);
				limitSaleNum = limitSaleNum - buyGoodsNum; //更新限购数量
			}
		}
		return limitSaleNum;
	}
	
	/**
	 * 查询商品的限购信息
	 */
	public Map getOrderLimitSaleInfo(Map map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String goodsSn = (String) map.get("goodsSn");
		int quantity = (Integer) map.get("quantity");
		int limitSaleNum = -1;
		
		Map limitMap = getGoodsLimitSaleInfo(goodsSn); //查询商品限购信息
		
		if(limitMap != null) {
			limitSaleNum = Integer.parseInt(limitMap.get("limitSaleNum").toString());
			if(limitSaleNum != -1 && limitSaleNum < quantity) {
				//是限购商品，且购买数量超过限购数量
				resultMap.put("limitSaleNum", limitSaleNum);
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
				return resultMap;
			}
			
			if(limitSaleNum != -1 && map.containsKey("userId")) {
				//限购商品
				if(limitMap.containsKey("lmtSaleStart") && limitMap.containsKey("lmtSaleEnd")) {
					//如果限购时间不为空，则查询此时间范围内用户已购买此商品的数量是否超过限购商品的数量，否则查询所有
					String beginDate = (String) limitMap.get("lmtSaleStart");
					String endDate = (String) limitMap.get("lmtSaleEnd");
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
				}
				
				//查询用户购买商品的数量
				int buyGoodsNum = orderService.getUserBuyGoodsCount(map);
				if(quantity > limitSaleNum - buyGoodsNum) {
					resultMap.put("limitSaleNum", limitSaleNum);
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
					return resultMap;
				}
				
				limitSaleNum = limitSaleNum - buyGoodsNum; //更新限购数量
			}
		}
		
		resultMap.put("result", true);
		resultMap.put("limitSaleNum", limitSaleNum);
		return resultMap;
	}
	
	/**
	 * 查询商品限购信息
	 */
	public Map getGoodsLimitSaleInfo(String goodsSn) {
		Map<String, Object> resultMap = null;
		List<Product> productList = batchLoadProducts(goodsSn);
		Product product = productList.get(0);
		if(product != null) {
			resultMap = new HashMap<String, Object>();
			int limitSaleNum = -1;	//默认-1，表示不支持
			String limitSaleFlag = product.getLmtSaleFlg();
			if(StringUtils.isNotBlank(limitSaleFlag)) {
				if(limitSaleFlag.equals("1")) {
					Date lmtSaleStart = product.getLmtSaleStart();
					Date lmtSaleEnd = product.getLmtSaleEnd();
					Integer lmtSaleNum = product.getLmtSaleCnt();
					Date now = new Date();
					if(lmtSaleNum != null && lmtSaleNum > 0) {
						//限购数量大于0
						if(lmtSaleStart != null && lmtSaleEnd != null ) {
							//如果限购时间不为空，则判断当前时间是否在限购时间范围内
							if(now.after(lmtSaleStart) && now.before(lmtSaleEnd) ) {
								limitSaleNum = lmtSaleNum;
							}
							SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String lmtSaleStartTime = sf.format(lmtSaleStart);
							String lmtSaleEndTime = sf.format(lmtSaleEnd);
							resultMap.put("lmtSaleStart", lmtSaleStartTime);  //限购开始时间
							resultMap.put("lmtSaleEnd", lmtSaleEndTime); //限购结束时间
						} else {
							limitSaleNum = lmtSaleNum;
						}
					}
				} 
			} 
			
			resultMap.put("limitSaleNum", limitSaleNum); //限购数量
		}
		
		return resultMap;
	}
	
	/**
	 * 查询商品是否是礼品包装商品
	 * @param goodsDetail
	 * @return
	 */
	public boolean isPackagingProduct(String goodsId) {
		boolean result = false;
		try {
			Map resultMap = goodsDao.getPackagingProductList(); //查询礼品包装商品列表
			if(resultMap != null) {
				String value = (String) resultMap.get("VALUE");
				if(StringUtils.isNotBlank(value)) {
					String[] goodsList = value.split(",");
					for(String goods : goodsList) {
						if(goodsId.equals(goods.split(":")[0])) {
							result = true;
							break;
						} 
					}
				}
			}
		} catch (Exception e) {
			logger.error("商品详情：处理商品不支持单独购买状态异常，商品goodsId:"+goodsId+",错误信息："+e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 查询商品是否支持礼品包装
	 * @param goodsSn
	 * @return
	 */
	public boolean isProductSupportWrap(String goodsSn) {
		boolean supportWrapStatus = false;
		if(StringUtils.isNotBlank(goodsSn)) {
			Map supportWrapMap = productManager.getProductSupportWrap(goodsSn);
			supportWrapStatus = (Boolean) supportWrapMap.get("status");
		}
		return supportWrapStatus;
	}
	
	/**
	 * 检测是否是关注商品
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	private boolean isFavorGoods(String userId, String goodsId) {
		boolean result = false;
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(goodsId)) {
			return result;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("goodsId", goodsId);
		int favorCount = goodsDao.getFavorGoodsCount(map); //查询关注商品数量
		if(favorCount > 0) {
			result = true;
		}
		
		return result;
	}
	
	/**
	 * 获取商品尺码图片
	 * @param goodsInfoMap
	 * @param goodsSn
	 * @return
	 */
	private String getSizeUrl(Map goodsInfoMap, String goodsSn) {
		String result = null;
		
		try {
			if(goodsInfoMap != null) {
				String sizeUrl = (String) goodsInfoMap.get("sizeImgUrl");
				if(StringUtils.isNotBlank(sizeUrl) && sizeUrl.startsWith("sizeImage")) {
					sizeUrl = "http://images.xiu.com/upload/size_image/" + sizeUrl;
				} else if(StringUtils.isNotBlank(sizeUrl)) {
					Date productCreate = (Date) goodsInfoMap.get("productCreate");
					String createTime = null;
					if(productCreate != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						createTime = sdf.format(productCreate);
					}
					sizeUrl = "http://images.xiu.com/upload/goods" + createTime + "/" + goodsSn + "/" + sizeUrl.substring(sizeUrl.lastIndexOf("/") + 1);
				}
				result = sizeUrl;
			}
		} catch(Exception e) {
			logger.error("获取商品尺码图片,goodsSn："+goodsSn+",错误信息："+e.getMessage());
			return result;
		}
		
		return result;
	}
	
	/**
	 * 获取商品尺码图片
	 * @param goodsInfoMap
	 * @param goodsSn
	 * @return
	 */
	private String getBrandStoryUrl(Map goodsInfoMap, String goodsSn) {
		String result = null;
		
		try {
			if(goodsInfoMap != null) {
				String bannerImgName = (String) goodsInfoMap.get("bannerImgName");
                if(StringUtils.isNotBlank(bannerImgName)) {
                	String temp=bannerImgName.substring(0,bannerImgName.indexOf("_2_"));
					bannerImgName = GoodsConstant.brand_stort_img_url+temp+"/"+bannerImgName;
				}
				result = bannerImgName;
			}
		} catch(Exception e) {
			logger.error("获取商品品牌故事图片,goodsSn："+goodsSn+",错误信息："+e.getMessage());
			return result;
		}
		
		return result;
	}
	
	/**
	 * 查询商品详情
	 */
	public Map<String, Object> getGoodsDetail(Map<String, Object> map) {
		String appVersion = (String) map.get("appVersion");
		if(StringUtils.isBlank(appVersion)){
			return this.getGoodsDetailOld(map);
		}else if("3.7".equals(appVersion)){
			return this.getGoodsDetailV7(map);
		}else if("4.0".equals(appVersion)){
			return this.getShowGoodsDetail(map);
		}
		return null;
	}
	
	private Map<String, Object> getShowGoodsDetail(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//封装对象
		GoodsDetailsVO goodsDetail = new GoodsDetailsVO();
		
		String goodsSn = (String) map.get("goodsSn");
		String deviceType = map.get("deviceType").toString().trim(); 
		
		//调用一品多商接口获取信息
		MutilPartnerInfo mutilPartnerInfo = eIPcsOpmsManager.getMutilPartnerInfo(goodsSn);
		if(mutilPartnerInfo == null || StringUtils.isNotBlank(mutilPartnerInfo.getErrMessage())){
			logger.error("调用一品多商接口异常，goodSn：" + goodsSn + "，errMsg: " + (mutilPartnerInfo == null ? null : mutilPartnerInfo.getErrMessage()));
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			return resultMap;
		}
		//商品基础信息
		ProdBaseInfoVo prodBaseInfoVo = mutilPartnerInfo.getBaseInfo();
		//一品多商关系
		MutilPartnerRel mutilPartnerRel = mutilPartnerInfo.getRel();
		//以走秀码为维度的信息
		Map<String, SnCustomInfoVo> snMap = mutilPartnerInfo.getSnInfo();
		//以SKU为维度的信息
		Map<String, SkuCustomInfoVo> skuMap = mutilPartnerInfo.getSkuInfo();
		//主商品走秀码
		SnCustomInfoVo mainSn = snMap.get(mutilPartnerRel.getMainProductSn());
		//主SKU
		String mainSku = mutilPartnerRel.getMajorSku();
		
		String productId = String.format("%07d", prodBaseInfoVo.getInnerID());
		goodsDetail.setGoodsId(productId);
		goodsDetail.setGoodsSn(prodBaseInfoVo.getXiuSN());
		goodsDetail.setGoodsName(prodBaseInfoVo.getPrdName());
		// 商品上下架标识
		goodsDetail.setStateOnSale(prodBaseInfoVo.getOnSale());
		
		Long brandId = prodBaseInfoVo.getBrandId();
		if(brandId == null){
			try {
				brandId = getBrandIdByGoodsSn(goodsSn); //查询品牌ID
			} catch (Exception e) {
				logger.error("根据走秀码获取商品品牌ID异常", e);
			} 
		}
		if(brandId != null){
			BrandInfoVO brandInfo = goodsDao.getBrandInfoByBrandId(brandId.toString());
			
			if(brandInfo != null){
				goodsDetail.setBrandCNName(brandInfo.getCnName());
				goodsDetail.setBrandEnName(brandInfo.getEnName());
			}
		}
		
		// 管理分类是汽车
		if (GoodsConstant.BUS.equals(prodBaseInfoVo.getmCatCode())) {
			// 该商品参加了活动
			if (GoodsConstant.WCS_ACT.equals(prodBaseInfoVo.getPrdActivityPriceType())) {
				goodsDetail.setBusDisplayType(GoodsConstant.BUS_IN_ACT);
			} else {// 未参加活动
				goodsDetail.setBusDisplayType(GoodsConstant.BUS_NOT_IN_ACT);
			}
		} else {
			goodsDetail.setBusDisplayType(0);
		}
		
		//是否限购
		int limitSaleNum = getGoodsLimitSaleNum(prodBaseInfoVo);
		goodsDetail.setLimitSaleNum(limitSaleNum);
		
		Sku[] skuArr = new Sku[mutilPartnerRel.getSkuList().size()];
		List<GoodsSkuItem> goodSkuList = new ArrayList<GoodsSkuItem>();
		for (int i=0; i<mutilPartnerRel.getSkuList().size(); i++) {
			String sku = mutilPartnerRel.getSkuList().get(i);
			SkuCustomInfoVo skuCustomInfoVo = skuMap.get(sku);
			
			GoodsSkuItem goodsSkuItem = new GoodsSkuItem();
			goodsSkuItem.setColor(skuCustomInfoVo.getColor());
			goodsSkuItem.setSize(skuCustomInfoVo.getSize());
			goodsSkuItem.setSkuSn(skuCustomInfoVo.getSkuCode());
			goodsSkuItem.setStock(skuCustomInfoVo.getStore());
			goodsSkuItem.setXiuSn(skuCustomInfoVo.getProductSn());
			
			goodSkuList.add(goodsSkuItem);
			
			Sku skuObj = new Sku();
			skuObj.setSkuSN(skuCustomInfoVo.getSkuCode());
			skuArr[i] = skuObj;
		}
		//构造一个Product对象， 为了兼容以前的老方法
		Product product = new Product();
		product.setSkus(skuArr);
		product.setXiuSN(mainSn.getProductSn());
		//sku信息
		handleShowSkuData(goodSkuList, product, deviceType, goodsDetail, snMap, mainSku, mutilPartnerInfo.isMutilPartner());
		
		resultMap.put("goodsDetailInfo", goodsDetail);
		resultMap.put("result", true);
		
		return resultMap;
	}

	private Map<String, Object> getGoodsDetailV7(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//封装对象
		GoodsDetailsVO goodsDetail = new GoodsDetailsVO();
		String goodsSn = (String) map.get("goodsSn");
		try{
			//调用一品多商接口获取信息
			MutilPartnerInfo mutilPartnerInfo = eIPcsOpmsManager.getMutilPartnerInfo(goodsSn);
			if(mutilPartnerInfo == null || StringUtils.isNotBlank(mutilPartnerInfo.getErrMessage())){
				logger.error("调用一品多商接口异常，goodSn：" + goodsSn + "，errMsg: " + (mutilPartnerInfo == null ? null : mutilPartnerInfo.getErrMessage()));
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
				return resultMap;
			}
			//商品基础信息
			ProdBaseInfoVo prodBaseInfoVo = mutilPartnerInfo.getBaseInfo();
			//一品多商关系
			MutilPartnerRel mutilPartnerRel = mutilPartnerInfo.getRel();
			//以走秀码为维度的信息
			Map<String, SnCustomInfoVo> snMap = mutilPartnerInfo.getSnInfo();
			//以SKU为维度的信息
			Map<String, SkuCustomInfoVo> skuMap = mutilPartnerInfo.getSkuInfo();
			//主商品走秀码
			SnCustomInfoVo mainSn = snMap.get(mutilPartnerRel.getMainProductSn());
			//主SKU
			String mainSku = mutilPartnerRel.getMajorSku();
			//构造一个sku数组，为了兼容老方法
			Sku[] skuArr = new Sku[mutilPartnerRel.getSkuList().size()];
			List<GoodsSkuItem> goodSkuList = new ArrayList<GoodsSkuItem>();
			for (int i=0; i<mutilPartnerRel.getSkuList().size(); i++) {
				String sku = mutilPartnerRel.getSkuList().get(i);
				SkuCustomInfoVo skuCustomInfoVo = skuMap.get(sku);
				
				GoodsSkuItem goodsSkuItem = new GoodsSkuItem();
				goodsSkuItem.setColor(skuCustomInfoVo.getColor());
				goodsSkuItem.setSize(skuCustomInfoVo.getSize());
				goodsSkuItem.setSkuSn(skuCustomInfoVo.getSkuCode());
				goodsSkuItem.setStock(skuCustomInfoVo.getStore());
				goodsSkuItem.setXiuSn(skuCustomInfoVo.getProductSn());
				
				goodSkuList.add(goodsSkuItem);
				
				Sku skuObj = new Sku();
				skuObj.setSkuSN(skuCustomInfoVo.getSkuCode());
				skuArr[i] = skuObj;
			}
			//构造一个Product对象， 为了兼容以前的老方法
			Product product = new Product();
			product.setSkus(skuArr);
			product.setXiuSN(mainSn.getProductSn());
			product.setMCatCode(prodBaseInfoVo.getmCatCode());
			
			String productId = String.format("%07d", prodBaseInfoVo.getInnerID());
			goodsDetail.setGoodsId(productId);
			goodsDetail.setGoodsSn(prodBaseInfoVo.getXiuSN());
			goodsDetail.setGoodsName(prodBaseInfoVo.getPrdName());
			
			// 商品分类，用来给前端跟踪用户行为
			String categoryId = getGoodsCategoryId(goodsSn);
			goodsDetail.setCategoryId(categoryId);
			
			// 商品信息：描述、尺码、创建时间
			Map<String, Object> goodsInfoMap = goodsDao.getGoodsInfo(productId);
			String sizeUrl = getSizeUrl(goodsInfoMap, goodsSn); //尺码Url
			String description = null; //商品描述
			if(goodsInfoMap != null) {
				description = (String) goodsInfoMap.get("description");
				Date productCreate = (Date) goodsInfoMap.get("productCreate");
				product.setCreatedTime(productCreate);
			}
			
			// 商品尺码对照图
			if (StringUtils.isEmpty(sizeUrl)) {
				sizeUrl = "";
			} else {
				sizeUrl = ImageServiceConvertor.removePort(sizeUrl);
				sizeUrl = ImageServiceConvertor.addDetailSize(sizeUrl);
				sizeUrl = ImageServiceConvertor.getGoodsDetail(sizeUrl);
				description="<p><img alt=\"\" src=\""+sizeUrl+"\" /></p>"+description;
			}
			goodsDetail.setSizeUrl(sizeUrl);
			
			// 商品描述
			if(StringUtils.isNotBlank(description)) {
				this.processProDesc(description, goodsDetail);
			}
			
			// 商品图片
			String imgUrl = prodBaseInfoVo.getImgUrl();
			// 为了适应镜像环境的端口号
			if (Tools.isEnableImageReplace()) { 
				if (null != imgUrl) {
					imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
					imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
				}
			} 
			imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
			goodsDetail.setGoodsImgUrl(imgUrl);
			
			// 商品sku列表,包含sku码、sku库存数量、商品组图
			String deviceType = map.get("deviceType").toString().trim(); 
			//处理SKU
			handleSkuData(goodSkuList, product, deviceType, goodsDetail, snMap, mainSku, mutilPartnerInfo.isMutilPartner());
			
			// 商品上下架标识
			goodsDetail.setStateOnSale(prodBaseInfoVo.getOnSale());
			
			// 扩展域信息
			Map fieldExt = prodBaseInfoVo.getFieldEx();
			String supportRejected = "";
			if (null != fieldExt) {
				supportRejected = (String) fieldExt.get("rejectedFlag");
				goodsDetail.setSupportRejected(supportRejected);
				/*String supplierCode = (String) fieldExt.get("realSupplierCode");
				String supplierName = getSupplierDisplayName(supplierCode);
				if(StringUtils.isNotBlank(supplierName)) {
					supplierName = "本商品由" + supplierName + "发货并提供售后服务";
				}
				goodsDetail.setSupplierInfo(supplierName);*/
			}
			
			// 商品属性信息
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("store_id", GlobalConstants.STORE_ID);
			paraMap.put("catentry_id", productId);
			List<FlexibleItem> flexibleAttr = queryGoodsProps(paraMap,goodsSn);
			if(StringUtils.isNotBlank(supportRejected) && supportRejected.equals("0")) {
				//商品是否支持退换货放入商品属性里面
				FlexibleItem flexibleItem = new FlexibleItem();
				flexibleItem.setKey("温馨提示");
				flexibleItem.setValue("商品不支持7天无理由退换货");
				if(flexibleAttr == null) {
					flexibleAttr = new ArrayList<FlexibleItem>();
				}
				flexibleAttr.add(flexibleItem);
			}

			//供应商别名
			Map<String, Object> snExtInfo = mainSn.getSnExtInfo();
			if(snExtInfo != null && snExtInfo.get("vendorNameAlt") != null){
				FlexibleItem flexibleItem = new FlexibleItem();
				flexibleItem.setKey("服务");
				flexibleItem.setValue("本商品由" + snExtInfo.get("vendorNameAlt") + "发货，走秀网提供售后服务。");
				flexibleAttr.add(1, flexibleItem);
			}

			goodsDetail.setGoodsProperties(flexibleAttr);
			
			// 设置汽车标识及显示信息
			// 管理分类是汽车
			if (GoodsConstant.BUS.equals(prodBaseInfoVo.getmCatCode())) {
				// 该商品参加了活动
				if (GoodsConstant.WCS_ACT.equals(prodBaseInfoVo.getPrdActivityPriceType())) {
					goodsDetail.setBusDisplayType(GoodsConstant.BUS_IN_ACT);
				} else {// 未参加活动
					goodsDetail.setBusDisplayType(GoodsConstant.BUS_NOT_IN_ACT);
				}
			} else {
				goodsDetail.setBusDisplayType(0);
			}
			
			//商品评论
			handleGoodsComment(goodsDetail);
			
			//不支持单独购买
			goodsDetail.setNotSupportBuyAlone(mainSn.isGiftProduct());
			
			//是否限购
			int limitSaleNum = getGoodsLimitSaleNum(prodBaseInfoVo);
			goodsDetail.setLimitSaleNum(limitSaleNum);
			
			//是否关注商品
			String userId = (String) map.get("userId");
			boolean favorGoods = isFavorGoods(userId, productId);
			goodsDetail.setFavorGoods(favorGoods); 
			
			//3.品牌信息
			BrandInfoVO brandInfo = getBrandInfo(goodsSn, prodBaseInfoVo.getBrandId());
			goodsDetail.setBrandInfo(brandInfo);
			goodsDetail.setBrandCNName(brandInfo.getCnName());
			goodsDetail.setBrandEnName(brandInfo.getEnName());
			
			//6.比价信息
//			PriceCompareVo priceCompare = getPriceCompareInfo(goodsDetail);
//			goodsDetail.setPriceCompareInfo(priceCompare);
			
			//7.商品评论
			List<GoodsCommentVO> comentList = getGoodsCommentList(productId, brandInfo.getBrandId());
			if(comentList == null){
				comentList = new ArrayList<GoodsCommentVO>();
			}
			goodsDetail.setCommentList(comentList);
			
			//是否支持求购
			ItemSettleResultDO resultDO = mainSn.getItemSettleResultDO();
			boolean supportAskBuy = resultDO.getDiscountPrice() > 100000; //商品走秀价小于1000则不支持求购
			if(supportAskBuy) {
				String cateName = prodBaseInfoVo.getmCatName();
				if(StringUtils.isNotBlank(cateName)) {
					paraMap.put("type", cateName);
				    paraMap.put("brandName", prodBaseInfoVo.getBrandName());
				    paraMap.put("brandOtherName", prodBaseInfoVo.getBrandOtherName());
				    int result = goodsDao.getSupportAskBuy(paraMap);
				    if(result == 0) {
				    	goodsDetail.setSupportAskBuy(false);
				    }else{
				    	goodsDetail.setSupportAskBuy(true);
				    }
				} else {
					goodsDetail.setSupportAskBuy(false);
				}
			}
			resultMap.put("goodsDetailInfo", goodsDetail);
			resultMap.put("result", true);
		}catch(Exception e){
			logger.error("查询商品详情信息异常，goodsSn: " + goodsSn, e);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		
		return resultMap;
	}
	
	private void processProDesc(String description, GoodsDetailsVO goodsDetail) {
		description = description.replace("<img", "<img onerror=\"nofind(this)\""); //电脑端描述
		//设置图片宽度100%
		String phoneDescription = description.replace("<img", "<img onload=\"getPicSize(this)\""); //手机端描述
		if(StringUtils.isNotBlank(phoneDescription)) {
			//图片不存在设置不显示，占位图的宽度不修改
			phoneDescription = phoneDescription + " <script type=\"text/javascript\"> function nofind(e) { e.style.display = \"none\"; } "
					+ " function getPicSize(e) { if(e.width == 1 && e.height == 1) { e.style.display = \"none\"; } else { e.style.width = \"100%\"; } } </script>";
			//字体大小设置
			phoneDescription = phoneDescription + " <style type=\"text/css\"> body { font-size:28px; } </style>";
			goodsDetail.setGoodsDetailPhone(phoneDescription);
		}
		
		description = description + " <script type=\"text/javascript\"> function nofind(e) { e.style.display = \"none\"; } </script>";
		
		goodsDetail.setGoodsDetail(description);
	}
	//获取价格，调用一品多商接口
	public GoodsVo getGoodsPrice(String goodsSn){
		//封装对象
		GoodsVo goodsVO = new GoodsVo();
		//调用一品多商接口获取信息
		MutilPartnerInfo mutilPartnerInfo = eIPcsOpmsManager.getMutilPartnerInfo(goodsSn);
		//商品基础信息
		ProdBaseInfoVo prodBaseInfoVo = mutilPartnerInfo.getBaseInfo();
		//一品多商关系
		MutilPartnerRel mutilPartnerRel = mutilPartnerInfo.getRel();
		//以走秀码为维度的信息
		Map<String, SnCustomInfoVo> snMap = mutilPartnerInfo.getSnInfo();
		//主商品走秀码
		SnCustomInfoVo mainSn = snMap.get(mutilPartnerRel.getMainProductSn());
		String productId = String.format("%07d", prodBaseInfoVo.getInnerID());
		goodsVO.setGoodsId(productId);
		goodsVO.setGoodsSn(prodBaseInfoVo.getXiuSN());
		goodsVO.setGoodsName(prodBaseInfoVo.getPrdName());
		
		Map<String, Object> priceMap = getPriceComponentInfoAndPromotionInfo(mainSn.getItemSettleResultDO(), prodBaseInfoVo.getmCatCode());
		
		goodsVO.setPrice(((String) priceMap.get("price")));
		goodsVO.setDiscount((String) priceMap.get("zsPrice"));
		return goodsVO;
	}
	private void copyDeliveryInfo(DeliveryInfoVo deliveryInfoVo,
			DeliverInfo deliverInfo) {
		if(deliveryInfoVo == null) {return;}
		
		deliverInfo.setCountry(deliveryInfoVo.getCountry());
		deliverInfo.setFlowImgURL(deliveryInfoVo.getWlan_pictureUrl());
		deliverInfo.setDeliveryTimeInfo(deliveryInfoVo.getSendDate());
		deliverInfo.setCode(Integer.parseInt(deliveryInfoVo.getSpaceFlag()));
		deliverInfo.setCity(deliveryInfoVo.getSendAddress());
	}

	private Map<String, Object> getGoodsDetailOld(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String goodsSn = (String) map.get("goodsSn");
		
		try {
			//1.查询商品
			List<Product> productList = batchLoadProducts(goodsSn);
			if(productList == null || productList.size() == 0) {
				//如果没有找到商品
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
				return resultMap;
			}
			Product product = productList.get(0);
			String productId = String.format("%07d", product.getInnerID());
			
			//2.组装商品基本信息
			GoodsDetailsVO goodsDetail = new GoodsDetailsVO();
			goodsDetail.setGoodsId(productId);
			goodsDetail.setGoodsSn(goodsSn);
			goodsDetail.setGoodsName(product.getPrdName());
			
			// 品牌名称
			Map brandMap = topicActivityDao.getBrandNameByGoods(productId);
			if(brandMap != null) {
				String cnName = (String) brandMap.get("CN_NAME");
				String enName = (String) brandMap.get("EN_NAME");
				goodsDetail.setBrandCNName(cnName);
				goodsDetail.setBrandEnName(enName);
			}
			
			// 商品分类
			String categoryId = getGoodsCategoryId(goodsSn);
			goodsDetail.setCategoryId(categoryId);
			
			// 商品信息：描述、尺码、创建时间
			Map goodsInfoMap = goodsDao.getGoodsInfo(productId);
			String sizeUrl = getSizeUrl(goodsInfoMap, goodsSn); //尺码Url
			String description = null; //商品描述
			if(goodsInfoMap != null) {
				description = (String) goodsInfoMap.get("description");
				Date productCreate = (Date) goodsInfoMap.get("productCreate");
				product.setCreatedTime(productCreate);
			}
			
			//全球采购图和温馨提示
			BigDecimal globalFlag=(BigDecimal)goodsInfoMap.get("globalFlag");
			if(globalFlag!=null&&globalFlag.intValue()==2){//当为2时需要显示 全球采购优势图和温馨提示图
				description="<p><img alt=\"\" src=\""+GoodsConstant.REMINDER_IMG_URL+"\" /></p>"+description;
				description="<p><img alt=\"\" src=\""+GoodsConstant.GLOBAL_PURCHASE_IMG_URL+"\" /></p>"+description;
			}
			// 商品尺码对照图
			if (StringUtils.isEmpty(sizeUrl)) {
				sizeUrl = "";
			} else {
				sizeUrl = ImageServiceConvertor.removePort(sizeUrl);
				sizeUrl = ImageServiceConvertor.addDetailSize(sizeUrl);
				sizeUrl = ImageServiceConvertor.getGoodsDetail(sizeUrl);
//				<p><img alt="" src="http://images.xiu.com/upload/goods20130925/10252631/102526310001/ori1.jpg" /></p>
				description="<p><img alt=\"\" src=\""+sizeUrl+"\" /></p>"+description;
			}
			goodsDetail.setSizeUrl(sizeUrl);
			
			
			
			//品牌故事图
			String brandStoryUrl = getBrandStoryUrl(goodsInfoMap, goodsSn); //品牌故事Url
			if (StringUtils.isEmpty(brandStoryUrl)) {
				brandStoryUrl = "";
			} else {
				description=description+"<p><img alt=\"\" src=\""+brandStoryUrl+"\" /></p>";
			}
			
			// 商品描述
			if(StringUtils.isNotBlank(description)) {
				this.processProDesc(description, goodsDetail);
			}
			
			// 商品图片
			String imgUrl = product.getImgUrl();
			// 为了适应镜像环境的端口号
			if (Tools.isEnableImageReplace()) { 
				if (null != imgUrl) {
					imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
					imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
				}
			} 
			imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
			goodsDetail.setGoodsImgUrl(imgUrl);
			
			// 商品sku列表,包含sku码、sku库存数量、商品组图
			String deviceType = map.get("deviceType").toString().trim(); 
			List<GoodsSkuItem> goodSkuList = handleSkus(product.getSkus()); // 尺码颜色和库存
			handleSkuData(goodSkuList, product, deviceType, goodsDetail, null, null, false);
			
			// 商品上下架标识
			goodsDetail.setStateOnSale(product.getOnSale());
			
			// 扩展域信息
			Map fieldExt = product.getFieldEx();
			String supportRejected = "";
			if (null != fieldExt) {
				supportRejected = (String) fieldExt.get("rejectedFlag");
				goodsDetail.setSupportRejected(supportRejected);
				String supplierCode = (String) fieldExt.get("realSupplierCode");
				String supplierName = getSupplierDisplayName(supplierCode);
				if(StringUtils.isNotBlank(supplierName)) {
					supplierName = "本商品由" + supplierName + "发货并提供售后服务";
				}
				goodsDetail.setSupplierInfo(supplierName);
			}
			
			// 商品属性信息
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("store_id", GlobalConstants.STORE_ID);
			paraMap.put("catentry_id", productId);
			List<FlexibleItem> flexibleAttr = queryGoodsProps(paraMap,goodsSn);
			if(StringUtils.isNotBlank(supportRejected) && supportRejected.equals("0")) {
				//商品是否支持退换货放入商品属性里面
				FlexibleItem flexibleItem = new FlexibleItem();
				flexibleItem.setKey("温馨提示");
				flexibleItem.setValue("商品不支持7天无理由退换货");
				if(flexibleAttr == null) {
					flexibleAttr = new ArrayList<FlexibleItem>();
				}
				flexibleAttr.add(flexibleItem);
			}
			goodsDetail.setGoodsProperties(flexibleAttr);
			
			// 设置汽车标识及显示信息
			// 管理分类是汽车
			if (GoodsConstant.BUS.equals(product.getMCatCode())) {
				// 该商品参加了活动
				if (GoodsConstant.WCS_ACT.equals(product.getPrdActivityPriceType())) {
					goodsDetail.setBusDisplayType(GoodsConstant.BUS_IN_ACT);
				} else {// 未参加活动
					goodsDetail.setBusDisplayType(GoodsConstant.BUS_NOT_IN_ACT);
				}
			} else {
				goodsDetail.setBusDisplayType(0);
			}
			
			//商品评论
			handleGoodsComment(goodsDetail);
			
			//是否支持礼品包装
			boolean supportWrapStatus = isProductSupportWrap(goodsSn);
			goodsDetail.setSupportPackaging(supportWrapStatus);
			
			if(supportWrapStatus) {
				//支持礼品包装，查询包装商品ID
				Map packagingResult = getProductPackagingGoods();
				if(packagingResult != null) {
					String packagingGoodsId = (String) packagingResult.get("goodsId");
					goodsDetail.setPackagingGoodsId(packagingGoodsId);
				}
			}
			
			//不支持单独购买
			boolean notSupportBuyAlone = isPackagingProduct(goodsDetail.getGoodsId());
			goodsDetail.setNotSupportBuyAlone(notSupportBuyAlone);
			
			//是否限购
			int limitSaleNum = getGoodsLimitSaleNum(product);
			goodsDetail.setLimitSaleNum(limitSaleNum);
			
			//是否关注商品
			String userId = (String) map.get("userId");
			boolean favorGoods = isFavorGoods(userId, productId);
			goodsDetail.setFavorGoods(favorGoods); 
			
			//商品的收藏数量
//			int favorCounts = bookmarkItemDao.getGoodsFavorCounts(productId); 
//			if(favorCounts < 1000) {
//				goodsDetail.setFavorCounts(String.valueOf(favorCounts));
//			} else {
//				double hundred = Math.floor(favorCounts/100);
//				String favorCountsStr = String.valueOf(hundred);
//				if(hundred%10 == 0) {
//					goodsDetail.setFavorCounts(favorCountsStr.substring(0, 1)+"k");
//				} else {
//					goodsDetail.setFavorCounts(favorCountsStr.substring(0, 1)+"."+favorCountsStr.substring(1, 2)+"k");
//				}
//			}
			
			//3.品牌信息
			BrandInfoVO brandInfo = getBrandInfo(goodsSn, null);
			goodsDetail.setBrandInfo(brandInfo);
			
			//4.发货地信息
			paraMap.put("productId", productId);
			DeliverInfo deliverInfo = productDao.getDeliverInfoByProduct(paraMap);
			goodsDetail.setDeliverInfo(deliverInfo);
			
			//5.价格组成信息
			PriceComponentVo priceComponent = getPriceComponentInfo(product, goodsDetail);
			goodsDetail.setPriceComponentInfo(priceComponent);
			
			//6.比价信息
			PriceCompareVo priceCompare = getPriceCompareInfo(goodsDetail.getGoodsSn(), goodsDetail.getZsPrice());
			goodsDetail.setPriceCompareInfo(priceCompare);
			
			//7.商品评论
			if(brandInfo != null) {
				List<GoodsCommentVO> comentList = getGoodsCommentList(productId, brandInfo.getBrandId());
				goodsDetail.setCommentList(comentList);
			} else {
				goodsDetail.setCommentList(new ArrayList<GoodsCommentVO>());
			}
			
			//是否支持求购
			boolean supportAskBuy = goodsDetail.getSupportAskBuy(); //商品走秀价小于1000则不支持求购
			if(supportAskBuy) {
				String cateName = product.getMCatName();
				if(StringUtils.isNotBlank(cateName)) {
					paraMap.put("type", cateName);
				    paraMap.put("brandName", product.getBrandName());
				    paraMap.put("brandOtherName", product.getBrandOtherName());
				    int result = goodsDao.getSupportAskBuy(paraMap);
				    if(result == 0) {
				    	goodsDetail.setSupportAskBuy(false);
				    }
				} else {
					goodsDetail.setSupportAskBuy(false);
				}
			}
			
			resultMap.put("goodsDetailInfo", goodsDetail);
			resultMap.put("result", true);
		} catch (Exception e) {
			logger.error("查询商品详情异常，商品goodsSn："+goodsSn+"，错误信息："+e.getMessage());
		}
		
		return resultMap;
	}

	/**
	 * 查询礼品包装价格
	 */
	public String getProductPackagingPrice() {
		String result = "";
		try {
			Map resultMap = goodsDao.getPackagingProductList(); //查询礼品包装商品列表
			if(resultMap != null) {
				String value = (String) resultMap.get("VALUE");
				if(StringUtils.isNotBlank(value)) {
					String[] goodsList = value.split(",");
					String goodsId = goodsList[0].split(":")[0]; //第一个商品
					
					//根据商品ID查询商品价格信息
					String goodsSn = goodsDao.getGoodsSnByGoodsId(goodsId);
					if(StringUtils.isNotBlank(goodsSn)) {
						List<Product> productList = batchLoadProducts(goodsSn);
						Product product = productList.get(0);
						if(product != null) {
							ItemSettleResultDO resultDO = invokeSaleInterface(product);
							if(resultDO != null) {
								result = String.valueOf(new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP));
								
								String[] price = result.split("\\.");
								if (price.length > 1 && price[1].equals("00")) {
									result = price[0];
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询礼品包装价格异常,错误信息："+e.getMessage());
		}
		return result;
	}
	
	/**
	 * 查询礼品包装商品信息：商品ID、商品走秀码、sku码
	 * @return
	 */
	public Map getProductPackagingGoods() {
		Map result = null;
		try {
			Map resultMap = goodsDao.getPackagingProductList(); //查询礼品包装商品列表
			if(resultMap != null) {
				String value = (String) resultMap.get("VALUE");
				if(StringUtils.isNotBlank(value)) {
					String[] goodsList = value.split(",");
					String gooods = goodsList[0];
					String goodsId = gooods.split(":")[0]; //第一个商品
					
					//根据商品ID查询商品价格信息
					String goodsSn = goodsDao.getGoodsSnByGoodsId(goodsId);
					if(StringUtils.isNotBlank(goodsSn)) {
						result = new HashMap();
						result.put("goodsId", goodsId);
						result.put("goodsSn", goodsSn);
						result.put("skuCode", gooods.split(":")[1]);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询礼品包装商品走秀码异常,错误信息："+e.getMessage());
		}
		
		return result;
	}

	/**
	 * 查询商品
	 */
	public OrderGoodsVO getOrderGoods(String goodsSn, Map map) {
		OrderGoodsVO orderGoodsVO = null;
		try {
			GoodsDetailBo goodsDetail = loadXiuGoodsDetail(goodsSn, map);
			if(goodsDetail != null) {
				// 商品上架状态
				int iOnSale = Integer.valueOf(goodsDetail.getOnSale()).intValue();
				if (null != goodsDetail && GoodsConstant.ON_SALE == iOnSale) {
					GoodsInfo gi = goodsDetail.getGoodsInfo();
					orderGoodsVO = new OrderGoodsVO();
					orderGoodsVO.setGoodsSn(goodsSn);
					orderGoodsVO.setGoodsName(gi.getGoodsName());
					orderGoodsVO.setPrice(String.valueOf(goodsDetail.getPrice()));
					orderGoodsVO.setZsPrice(String.valueOf(goodsDetail.getZsPrice()));
					orderGoodsVO.setDiscountPrice(String.valueOf(goodsDetail.getZsPrice()));
					orderGoodsVO.setProdSettlementDO(goodsDetail.getProSettlement());
				}
			}
		} catch (Exception e) {
			logger.error("查询订单商品异常,goodsSn："+goodsSn+",错误信息："+e.getMessage());
		}
		
		return orderGoodsVO;
	}

	/**
	 * 查询商品上传身份证状态：0.必须 1.需要 2.不需要
	 */
	public int getGoodsUploadIdCardByGoodsId(String goodsId) {
		int result = 2;
		try {
			result = productDao.getGoodsUploadIdCardByGoodsId(goodsId);
		} catch (Exception e) {
			logger.error("查询商品上传身份证状态异常,goodsId："+goodsId+",错误信息："+e.getMessage());
		}
		return result;
	}

	/**
	 * 查询商品上传身份证状态：0.必须 1.需要 2.不需要
	 */
	public int getGoodsUploadIdCardByGoodsSn(String goodsSn) {
		int result = 2;
		try {
			Integer status = productDao.getGoodsUploadIdCardByGoodsSn(goodsSn);
			if(status != null) {
				result = status;
			}
		} catch (Exception e) {
			logger.error("查询商品上传身份证状态异常,goodsSn："+goodsSn+",错误信息："+e.getMessage());
		}
		return result;
	}

	/**
	 * 添加商品求购信息
	 */
	public Map<String, Object> addAskBuyInfo(Map map) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String goodsSn = (String) map.get("goodsSn");
		
		//查询商品
		List<Product> productList = batchLoadProducts(goodsSn);
		if(productList == null || productList.size() == 0) {
			//如果没有找到商品
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
			return resultMap;
		}
		
		Product product = productList.get(0);
		
		//检验是否支持求购  
	    ItemSettleResultDO resultDO = invokeSaleInterface(product);
	    //如果走秀价小于1000，则不支持求购
		if(resultDO.getDiscountPrice() < 100000) {
			//商品不支持求购
	    	resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.AskBuyGoodsNotSupport.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.AskBuyGoodsNotSupport.getErrorMsg());
			return resultMap;
		} 
		
		String catName = product.getMCatName();
		if(StringUtils.isBlank(catName)) {
			//商品不支持求购
	    	resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.AskBuyGoodsNotSupport.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.AskBuyGoodsNotSupport.getErrorMsg());
			return resultMap;
		}
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("type", catName);
	    paraMap.put("brandName", product.getBrandName());
	    paraMap.put("brandOtherName", product.getBrandOtherName());
	    Map infoMap = goodsDao.getSupportAskBuyInfo(paraMap);
	    if(infoMap == null) {
	    	//商品不支持求购
	    	resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.AskBuyGoodsNotSupport.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.AskBuyGoodsNotSupport.getErrorMsg());
			return resultMap;
	    }
	    
	    map.put("productType", catName);	//商品分类
	    map.put("brandName", infoMap.get("BRAND_NAME").toString());	//品牌名称
		
		//添加商品求购信息
		resultMap = eiCSCManager.insertProductService(map);	
		
		return resultMap;
	}
	
	public Map<String, Object> checkStock(String goodsSn, String goodsSku){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		//查询是否有库存
		Integer stock = this.queryInventoryBySku(goodsSku);
		if(stock != null && stock > 0){
			returnMap.put("isChange", false);
			returnMap.put("goodsSku", goodsSku);
		}else{//没库存则调一品多商裁决接口，裁决出新的SKU
			returnMap = eIPcsOpmsManager.getSkuRealtime(goodsSn, goodsSku);
			boolean flag = (Boolean) returnMap.get("result");
			if(!flag){
				returnMap.put("isChange", true);
				returnMap.put("goodsSku", "");
			}
		}
		returnMap.put("result", true);
		return returnMap;
	}
	
	/**
	 * 查看商品详情（少字段）
	 * @param goodsSnStr
	 * @return
	 */
	public Product getGoodsInfoSimpleByGoodSn(String goodsSnStr){
		List<Product> productList = batchLoadProducts(goodsSnStr);
		if(productList.size()>0){
			Product p=productList.get(0);
			//品牌名称
			Map brandMap = topicActivityDao.getBrandNameByGoods(p.getInnerID()+"");
			if(brandMap != null) {
				String cnName = (String) brandMap.get("CN_NAME");
				String enName = (String) brandMap.get("EN_NAME");
				if(enName!=null&&!enName.equals("")){
					p.setBrandName(enName);
				}else if(cnName!=null&&!cnName.equals("")){
					p.setBrandName(cnName);
				}
			}
			return p;
		}else{
			return null;
		}
		
	}

	@Override
	public Map<String, Boolean> isProductSupportWrapBySnList(List goodsSns) {
		Map<String, Boolean> supportWrapStatus = new HashMap<String, Boolean>();
		if(goodsSns!=null&&goodsSns.size()>0) {
			supportWrapStatus = productManager.getProductSupportWrapList(goodsSns);
		}
		return supportWrapStatus;
	}


}
