package com.xiu.mobile.simple.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiu.commerce.hessian.model.Product;
import com.xiu.commerce.hessian.model.ProductCommonParas;
import com.xiu.commerce.hessian.model.ProductSearchParas;
import com.xiu.commerce.hessian.model.Sku;
import com.xiu.common.command.result.Result;
import com.xiu.image.biz.dto.GoodsInfoDTO;
import com.xiu.image.biz.hessian.interfaces.SkuImagesPair;
import com.xiu.mobile.core.dao.DataBrandDao;
import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.simple.common.constants.ArrivalTimeMsgs;
import com.xiu.mobile.simple.common.constants.GoodsConstant;
import com.xiu.mobile.simple.common.constants.SendingPlaceMsgs;
import com.xiu.mobile.simple.common.util.ComparatorSizeAsc;
import com.xiu.mobile.simple.common.util.ImageServiceConvertor;
import com.xiu.mobile.simple.common.util.ParseProperties;
import com.xiu.mobile.simple.common.util.StringUtil;
import com.xiu.mobile.simple.common.util.Tools;
import com.xiu.mobile.simple.constants.ErrConstants;
import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.mobile.simple.dao.TopicActivityDao;
import com.xiu.mobile.simple.ei.EIChannelInventoryManager;
import com.xiu.mobile.simple.ei.EIProductManager;
import com.xiu.mobile.simple.ei.EIPromotionManager;
import com.xiu.mobile.simple.ei.EImageManager;
import com.xiu.mobile.simple.facade.utils.HttpUtil;
import com.xiu.mobile.simple.model.FlexibleItem;
import com.xiu.mobile.simple.model.GoodsDetailBo;
import com.xiu.mobile.simple.model.GoodsDetailVo;
import com.xiu.mobile.simple.model.GoodsInfo;
import com.xiu.mobile.simple.model.GoodsSkuItem;
import com.xiu.mobile.simple.model.GoodsSkuVo;
import com.xiu.mobile.simple.model.OrderGoodsVO;
import com.xiu.mobile.simple.service.IGoodsService;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleActivityResultDO;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;

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
	private DataBrandDao dataBrandDao;
	@Autowired
	private TopicActivityDao topicActivityDao;
	@Autowired
	private GoodsDao goodsDao;

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
		GoodsInfo goods = new GoodsInfo();
		goods.setGoodsSn(goodsSn);
		goods.setInnerId(String.format("%07d", product.getInnerID()));
		goods.setGoodsNumber(product.getXiuSN());

		// 品牌编码、品牌名称
		String brandCode = product.getBrandCode();
		goods.setBrandCode(brandCode);
		String brandName = product.getBrandName();
		goods.setBrandName(brandName);
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
		List<GoodsSkuItem> goodSkus = handleSkus(product.getSkus());
		List<SkuImagesPair> skuImagesPairs = goodsImages(product, 5, deviceType);
		findImgList(skuImagesPairs, goodSkus);
		
		goodsDetail.setSkuList(goodSkus);

		// 取消所有活动商品的标志
		goodsDetail.setIsActivityGoods(0);
		
		// 调用营销中心接口
		ItemSettleResultDO resultDO = invokeSaleInterface(product);
		List<ItemSettleActivityResultDO> activityList = resultDO.getActivityList();
		List<String> list = new ArrayList<String>();
		if (activityList != null && activityList.size() > 0) {
			for (ItemSettleActivityResultDO activity : activityList) {
				list.add(activity.getActivityId());
			}
		}
		goodsDetail.setActivityList(list);
		
		// 走秀价
		goodsDetail.setZsPrice(
				new BigDecimal(resultDO.getDiscountPrice() / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP));

		// 出发地的决定字段
		String supplierCode = product.getSupplierCode();
		String spaceFlag = product.getSpaceFlag();

		// 设置配货信息
		String tranport = getTransportInfo(supplierCode, spaceFlag);
		goodsDetail.setTranport(tranport);
		
		// 商品属性信息
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("store_id", GlobalConstants.STORE_ID);
		paramMap.put("catentry_id", product.getInnerID());
		List<FlexibleItem> flexibleAttr = queryGoodsProps(paramMap,goodsSn);
		goodsDetail.setFlexibleAttr(flexibleAttr);

		// 商品市场价
		// 市场价为null的处理方案，显示为“null”字符串
		if (product.getPrdListPrice() == null
				|| product.getPrdListPrice().doubleValue() == 0.0) {
			goodsDetail.setPrice(null);
		} else {
			goodsDetail.setPrice(new BigDecimal(product.getPrdListPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
		}
		
		goodsDetail.setGoodsInfo(goods);
		return goodsDetail;
	}

	@Override
	public GoodsDetailVo viewGoodsDetail(
			String goodsSn, Map<String, String> params) throws Exception {
		GoodsDetailBo gd = loadXiuGoodsDetail(goodsSn, params);
		return tranformToVo(gd);
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
	public String getRestTime(String goodsId, String goodsSn) {
		String getURL = GoodsConstant.REST_URL + "&goodsId=" + goodsId + "&goodsSn=" + goodsSn;
		String orig = HttpUtil.getContent(getURL);
		
		if (StringUtil.isExist(orig) && orig.startsWith(GoodsConstant.REST_PRE_CALLBACK)) {
			return orig.substring(GoodsConstant.REST_PRE_CALLBACK.length());
		}
		
		return null;
	}

	@Override
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
	}

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
		
		// 查询品牌信息
		DataBrandBo dataBrandBo = dataBrandDao.getBrandByCode(gi.getBrandCode());
		if (dataBrandBo!=null) {
			vo.setBrandId(dataBrandBo.getBrandId());
			String enName = dataBrandBo.getEnName();
			String cnName = dataBrandBo.getBrandName();
			String brandName = dataBrandBo.getEnName();
			if (StringUtils.isNotBlank(enName)) {
				if (StringUtils.isNotBlank(cnName) && !enName.equals(cnName)) {
					brandName = enName.concat(" ").concat(cnName);
				}
			}else{
				brandName = dataBrandBo.getBrandName();
			}
			vo.setBrandName(brandName);
		}
		// vo.setImgList(getDetailImgList(gd.getSkuList(),
		// vo.getGoodsImgUrl()));
		setStyleData(vo, gd.getSkuList());
		vo.setZsPrice(gd.getZsPrice().toString());
		String discount = null;// 折扣
		
		if (null != gd.getPrice()
				&& StringUtil.isExist(gd.getPrice().toString())) {
			BigDecimal zs = gd.getZsPrice().multiply(new BigDecimal(10));
			BigDecimal ds = zs.divide(gd.getPrice(), 1, BigDecimal.ROUND_HALF_UP);
			
			if (ds.doubleValue() < 10) {
				discount = ds.toString();
				// 去掉尾数0
				if (discount.endsWith(".0")) {
					discount = discount.substring(0, discount.length() - 2);
				}
			}
		}
		vo.setDiscount(discount);
		
		if (null != gd.getPrice()) {
			vo.setPrice(gd.getPrice().toString());
		}
		// 配送信息
		vo.setTranport(gd.getTranport());
		// 详情
		vo.setGoodsProperties(gd.getFlexibleAttr());
		vo.setDescription(gi.getDescription());
		vo.setGoodsDetail(gi.getGoodsDetail());
		// 尺码表
		vo.setSizeUrl(gi.getSizeUrl());
		vo.setStateOnsale(gd.getOnSale());
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
			// sku图片列表
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
	
	private List<SkuImagesPair> goodsImages(Product product, int length, String deviceType) {
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
			Sku[] skus = product.getSkus();
			String[] skuCodes = new String[skus.length];
			for (int i = 0; i < skus.length; i++) {
				skuCodes[i] = skus[i].getSkuSN();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

			GoodsInfoDTO goodsDTO = new GoodsInfoDTO();
			goodsDTO.setXiuCode(product.getXiuSN());
			goodsDTO.setSkuCodes(skuCodes);
			goodsDTO.setCreateTimeStr(sdf.format(product.getCreatedTime()));
			goodsDTO.setLength(length);

			List<SkuImagesPair> pairs = eImageManager.checkImageExists(goodsDTO);
			return pairs;
		} catch (Exception e) {
			logger.error("", e);
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
	private static Boolean sizeSortEnabled = new Boolean(
			ParseProperties.getPropertiesValue("size.sort.enabled"));
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
			for (Sku sku : skus) {
				GoodsSkuItem skuItem = new GoodsSkuItem();
				skuItem.setColor(sku.getColorValue());
				skuItem.setSize(sku.getSizeValue());
				skuItem.setSkuSn(sku.getSkuSN());
				
				// 添加sku的库存信息
				skuItem.setStock(queryInventoryBySku(sku.getSkuSN()));
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
			return eiChannelInventoryManager
					.inventoryQueryBySkuCodeAndChannelCode(GlobalConstants.CHANNEL_ID, skuCode);
		} catch (Exception e) {
			logger.error("查询库存出错", e);
			return -9999;
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
	 * 调用营销中心接口
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
}
