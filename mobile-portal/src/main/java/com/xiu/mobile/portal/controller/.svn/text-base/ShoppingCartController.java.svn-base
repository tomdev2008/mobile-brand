package com.xiu.mobile.portal.controller;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.CardCouponConstant;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.model.CPSCookieVo;
import com.xiu.mobile.portal.common.util.Arithmetic4Double;
import com.xiu.mobile.portal.common.util.CPSCookieUtils;
import com.xiu.mobile.portal.common.util.ConfigUtil;
import com.xiu.mobile.portal.common.util.CookieUtil;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.HttpClientUtil;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.model.AddressListQueryInParam;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.BookmarkIitemBo;
import com.xiu.mobile.portal.model.BookmarkIitemListVo;
import com.xiu.mobile.portal.model.CalculateOrderBo;
import com.xiu.mobile.portal.model.FlexibleItem;
import com.xiu.mobile.portal.model.GoodsDetailBo;
import com.xiu.mobile.portal.model.GoodsDetailVo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.MktActInfoVo;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.model.OrderReqVO;
import com.xiu.mobile.portal.model.OrderResVO;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.ShoppingCart;
import com.xiu.mobile.portal.model.ShoppingCartGoodsVo;
import com.xiu.mobile.portal.model.UserPayPlatform;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IBookmarkService;
import com.xiu.mobile.portal.service.ICPSService;
import com.xiu.mobile.portal.service.ICouponService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IOrderService;
import com.xiu.mobile.portal.service.IProductService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.mobile.portal.service.IShoppingCartService;
import com.xiu.mobile.portal.service.ISysParamService;
import com.xiu.mobile.portal.service.IUserPayPlatformService;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 用户购物车
 * @AUTHOR : hejianxiong
 * @DATE :2014年10月24日 上午11:01:29
 * </p>
 ****************************************************************
 */
@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController extends BaseController {

	private Logger logger = Logger.getLogger(ShoppingCartController.class);
	private final static int OVERLIMIT = 9999; // 购物车商品上限
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private IProductService productServiceImpl;
	@Autowired
	private IUserPayPlatformService userPayInfoService;
	@Autowired
	private ICouponService couponService;
	@Autowired
	private IShoppingCartService shoppingCartService;
	@Autowired
	private ICPSService cpSService;
	@Autowired
	private IBookmarkService bookmarkService;
	
	/**
	 * 获取购物车详情
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/getCartDetails", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getShoppingCartGoodsList(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		logger.info("查看购物车-进入方法getCartDetails start 当前系统时间："+dateFormat.format(new Date()));
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		List<ShoppingCart> shoppingCartGoodsList = new ArrayList<ShoppingCart>();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		boolean loginStatus = true;
		try {
			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				// 已登录就查询用户购物车信息
				// 从cookie中获得userId
				LoginResVo loginResVo = SessionUtil.getUser(request);
				Long userId = Long.parseLong(loginResVo.getUserId());
				HashMap<String, Object> valMap = new HashMap<String, Object>();
				valMap.put("userId", userId);
				Date startDate = new Date();
				logger.info("查看购物车-登陆用户获取用户购物车列表数据start 当前系统时间："+dateFormat.format(new Date()));
				shoppingCartGoodsList = shoppingCartService.getGoodsList(valMap);
				logger.info("查看购物车-登陆用户获取用户购物车列表数据end  当前系统时间："+dateFormat.format(new Date()));
				logger.info("查看购物车-获取登陆用户获取用户购物车列表数据用时："+ ((new Date()).getTime() - startDate.getTime()));
			} else {
				loginStatus = false;
				// 未登录就取wap/app传过来的购物车信息 如果不传递则取cookie里的商品信息
				// 商品信息的json串 
				String goods = request.getParameter("goods");
				if (StringUtils.isEmpty(goods)) {
					goods = SessionUtil.getShoppingCartGoodsList(request);
				}
				logger.info("获取传递商品列表参数为："+goods);	
				// 如果不传递
				if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
					model.put("result", true);
					model.put("shoppingCartGoodsList", new ArrayList<ShoppingCart>());
					model.put("totalAmount", 0);
					model.put("promoAmount", 0);
					model.put("totalQuantity", 0);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, model);
				}
				// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
				if ((!goods.startsWith("[")) && (!goods.endsWith("]"))) {
					model.put("result", false);
					model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
					model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, model);
				}
				// 转换成商品列表信息
				JSONArray jsonArray = JSONArray.fromObject(goods);
				shoppingCartGoodsList = (List<ShoppingCart>) JSONArray.toCollection(jsonArray, ShoppingCart.class);
			}
			if (null != shoppingCartGoodsList && shoppingCartGoodsList.size() > 0) {
				sortMap = sortShoppingCartGoods(shoppingCartGoodsList, tokenId, request);
				// 如果用户未登陆 则需要把更改后的购物车数量总数重新写入cookie
				if (!loginStatus) {
					// 重新写购物车cookie  
					SessionUtil.addShoppingCartGoods(response, net.sf.json.JSONArray.fromObject(shoppingCartGoodsList).toString());
				}
				
				List<ShoppingCartGoodsVo> goodsList = (List<ShoppingCartGoodsVo>) sortMap.get("goodsList");
				logger.info("查看购物车--处理礼品包装和限购 start 当前系统时间："+dateFormat.format(new Date()));
				handleProductSupportAndLimitSale(goodsList); //处理礼品包装和限购
				logger.info("查看购物车--处理礼品包装和限购  end 当前系统时间："+dateFormat.format(new Date()));
				model.put("result", true);
				model.put("shoppingCartGoodsList", goodsList);
				model.put("totalAmount", sortMap.get("totalAmt"));
				model.put("promoAmount", sortMap.get("promoAmount"));
				model.put("totalQuantity", sortMap.get("totalQunt"));
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.NoShoppingCartGoods.getErrorCode());
				model.put("errorMsg", ErrorCode.NoShoppingCartGoods.getErrorMsg());
			}
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查看购物车-查询购物车商品信息列表时发生异常：" + e.getMessage(), e);
		}
		String resultStr= JsonUtils.beanjsonP(jsoncallback, model);// beanjsonP2(当集合的key和value都一样的时候显示不了重复数据)
		logger.info("查看购物车--退出方法getCartDetails end 当前系统时间："+dateFormat.format(new Date()));
		return resultStr;
	}
	
	/**
	 * 订单确认
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/goToBuy", produces = "text/html;charset=UTF-8")
	public @ResponseBody String goToBuy(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		List<ShoppingCart> shoppingCartGoodsList = new ArrayList<ShoppingCart>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			// 检查用户登陆状态
			if (!checkLogin(request)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}

			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));

			// 商品信息的json串
			String goods = request.getParameter("goods");
			// 未登录就取wap/app传过来的购物车信息 如果不传递则取cookie里的商品信息
			if (StringUtils.isEmpty(goods)) {
				goods = SessionUtil.getShoppingCartGoodsList(request);
			}
			logger.info("获取传递商品列表参数为："+goods);	
			// 如果不传递
			if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!goods.startsWith("[")) && (!goods.endsWith("]"))) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}

			JSONArray jsonArray = JSONArray.fromObject(goods);
			shoppingCartGoodsList = (List<ShoppingCart>) JSONArray.toCollection(jsonArray, ShoppingCart.class);

			if (null != shoppingCartGoodsList && shoppingCartGoodsList.size() > 0) {
				// 检验产品相关数据信息
				result = confirmGoodsList(shoppingCartGoodsList, tokenId, request);
				boolean uploadIdCardStatus = true;
				if (Boolean.parseBoolean(result.get("result").toString())) {
					// 查询用户支付记录信息
					List<ShoppingCartGoodsVo> goodsList = (List<ShoppingCartGoodsVo>) result.get("goodsList");
					UserPayPlatform userPayInfo = userPayInfoService.get(SessionUtil.getUserId(request));
					if (userPayInfo != null) {
						result.put("payPlatform", userPayInfo.getPayPlatform());
					}else{
						result.put("payPlatform", "");
					}
					
					//查询是否需要上传身份证
					boolean checkUploadIdCard = false;
					Integer goodAreaType = 2;//商品所在地区 0保税区 1海外 2国内
					if(goodsList != null && goodsList.size() > 0) {
						for(ShoppingCartGoodsVo goodsVO : goodsList) {
							//查询商品上传身份证状态
							int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(goodsVO.getGoodsSn()); 
							if(goodAreaType>uploadIdCard){
								goodAreaType=uploadIdCard;
							}
							if(uploadIdCard == 0 || uploadIdCard == 1) {
								checkUploadIdCard = true;
//								break;
							}
						}
					}
					
					result.put("goodAreaType",goodAreaType);//商品所在地区 0保税区 1海外 2国内
					
					//查询发票数据
//					List<OrderInvoiceVO> invoiceList=orderService.getInvoiceTypeList(null);
//					result.put("invoiceList",invoiceList);
					
					// 获取默认收货地址
					AddressListQueryInParam addressListQuery = new AddressListQueryInParam();
					addressListQuery.setTokenId(tokenId);
					SessionUtil.setDeviceInfo(request, addressListQuery);
					AddressVo addressVo = addressService.getMasterAddress(addressListQuery);
					// 加载身份认证信息
					if (addressVo != null) {
						boolean flag = false;
						Long identityId = addressVo.getIdentityId();
						if(identityId != null && identityId.longValue() != 0 ){
							//如果地址的身份信息ID不为空
							IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
							if(identityInfoDTO != null) {
//								if(identityInfoDTO.getReviewState() != null && identityInfoDTO.getReviewState().equals(1)) {
//									flag = true;
//								}
								if(identityInfoDTO.getIdNumber()!=null&&!identityInfoDTO.getIdNumber().equals("")){
									flag = true;
								}
								addressVo.setIdCard(identityInfoDTO.getIdNumber());
								
								if (checkUploadIdCard) {
									// 审核状态 reviewState{0 - 待审核, 1 - 审核通过, 2 - 审核不通过}
									Integer reviewState = identityInfoDTO.getReviewState();
									//  审核通过和待审核状态的身份证，则显示支付成功页面  否则提示需要上传身份证
									if(reviewState == 2) {
										uploadIdCardStatus = false;
									} else {
										uploadIdCardStatus = true;
									}
								}
							} else {
								if (checkUploadIdCard) {
									uploadIdCardStatus = false;
								}
							}
						} else {
							if (checkUploadIdCard) {
								uploadIdCardStatus = false;
							}
						}
						
						
//						boolean flag = false;
//						LoginResVo user = SessionUtil.getUser(request);
//						UserIdentityDTO userIdentityDTO = receiverIdService.getUserIdentity(user.getUserId(), addressVo.getAddressId());
//						if (userIdentityDTO != null) {
//							// 是否审核0:未审核 1:审核通过 2:审核不通过
//							Long isReview = userIdentityDTO.getIsReview();
//							if (isReview != null && (isReview == 1)) {
//								flag = true;
//							}
//						}
						addressVo.setIdAuthorized(flag);
						// addressId加密(AES)
						String aesKey=EncryptUtils.getAESKeySelf();
						String addressId=EncryptUtils.encryptByAES(addressVo.getAddressId(), aesKey);
						addressVo.setAddressId(addressId);
						
						// 设置默认收货地址
						result.put("addressVo", addressVo);
					}
//					else{
//						result.put("addressVo", "{}");
//					}
				}else{
					// 不做处理 直接用confirmGoodsList响应的结果
				}
				result.put("uploadIdCardStatus", uploadIdCardStatus);			
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.NoShoppingCartGoods.getErrorCode());
				result.put("errorMsg", ErrorCode.NoShoppingCartGoods.getErrorMsg());
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("订单确认发生异常：" + e.getMessage(), e);
		}
		logger.info("订单确认返回结果：" + JSON.toJSONString(result));
		return JsonUtils.beanjsonP(jsoncallback, result);// beanjsonP2(当集合的key和value都一样的时候显示不了重复数据)
	}
	
	
	/**
	 * 订单确认价格重新计算
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/calOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String calOrder(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		List<ShoppingCart> shoppingCartGoodsList = new ArrayList<ShoppingCart>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			// 检查用户登陆状态
			if (!checkLogin(request)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}

			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));

	
			
			// 商品信息的json串
			String goods = request.getParameter("goods");
			logger.info("获取传递商品列表参数为："+goods);	
			// 如果不传递
			if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!goods.startsWith("[")) && (!goods.endsWith("]"))) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				result.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			// 转换成商品列表信息
			JSONArray jsonArray = JSONArray.fromObject(goods);
			shoppingCartGoodsList = (List<ShoppingCart>) JSONArray.toCollection(jsonArray, ShoppingCart.class);
			if (null != shoppingCartGoodsList && shoppingCartGoodsList.size() > 0) {
				// 检验产品相关数据信息
				result = confirmGoodsList(shoppingCartGoodsList, tokenId, request);
				
				boolean uploadIdCardStatus = true;
				if (Boolean.parseBoolean(result.get("result").toString())) {
					// 查询用户支付记录信息
					List<ShoppingCartGoodsVo> goodsList = (List<ShoppingCartGoodsVo>) result.get("goodsList");
//					Integer goodAreaType = 2;//商品所在地区 0保税区 1海外 2国内
					if(goodsList != null && goodsList.size() > 0) {
						for(ShoppingCartGoodsVo goodsVO : goodsList) {
							//查询商品上传身份证状态
							int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(goodsVO.getGoodsSn()); 
//							if(goodAreaType>uploadIdCard){
//								goodAreaType=uploadIdCard;
//							}
						}
					}
					
//					result.put("goodAreaType",goodAreaType);
				}
				
				// 查询用户支付记录信息
				UserPayPlatform userPayInfo = userPayInfoService.get(SessionUtil.getUserId(request));
				if (userPayInfo != null) {
					result.put("payPlatform", userPayInfo.getPayPlatform());
				}else{
					result.put("payPlatform", "");
				}
			} else {
				result.put("result", false);
				result.put("errorCode", ErrorCode.NoShoppingCartGoods.getErrorCode());
				result.put("errorMsg", ErrorCode.NoShoppingCartGoods.getErrorMsg());
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("订单确认重新计算价格发生异常：" + e.getMessage(), e);
		}
		logger.info("订单确认重新计算价格返回结果：" + JSON.toJSONString(result));
		return JsonUtils.beanjsonP(jsoncallback, result);// beanjsonP2(当集合的key和value都一样的时候显示不了重复数据)
	}
	
	private HashMap<String, Object> sortShoppingCartGoods(List<ShoppingCart> shoppingCartGoodsList,String tokenId,HttpServletRequest request) throws Exception{
		HashMap<String, Object> valMap = new HashMap<String, Object>();
		List<ShoppingCartGoodsVo> goodsList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> checkedList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> unCheckedList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> mktGoodsList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> noMktGoodsList = new ArrayList<ShoppingCartGoodsVo>();
		String goodSns=new String();
//		boolean markStatus = false; // 促销标识
		int totalQunt = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		// 获取商品列表信息
		String goodsSnStr = goodsSnListToString(shoppingCartGoodsList);
		Date startDate = new Date();
		logger.info("查看购物车-调用商品中心获取商品列表信息start 当前系统时间："+dateFormat.format(new Date()));
		List<GoodsDetailBo> goodsDetailBoList = goodsManager.getShoppingCartGoodsDetailBoList(goodsSnStr, getDeviceParams(request));
		List<GoodsDetailVo> goodsDetailVoList = goodsManager.getGoodsDetailList(goodsDetailBoList);
		logger.info("查看购物车-调用商品中心获取商品列表信息end  当前系统时间："+dateFormat.format(new Date()));
		logger.info("查看购物车-调用商品中心获取商品列表信息用时："+ ((new Date()).getTime() - startDate.getTime()));
		Map<String, GoodsDetailVo> goodsDetailVoMap = new HashMap<String, GoodsDetailVo>();
		for (GoodsDetailVo goodsDetailVo : goodsDetailVoList) {
			goodsDetailVoMap.put(goodsDetailVo.getGoodsSn(), goodsDetailVo);
		}
		// 查询商品信息
		for (ShoppingCart shopGoods : shoppingCartGoodsList) {
			ShoppingCartGoodsVo goods = new ShoppingCartGoodsVo();
			GoodsDetailVo objGoodsDetailVo = goodsDetailVoMap.get(shopGoods.getGoodsSn());
			if (objGoodsDetailVo == null) {
				continue;
			}
			goods.setGoodsSn(objGoodsDetailVo.getGoodsSn());
			goods.setGoodsImgUrl(objGoodsDetailVo.getGoodsImgUrl()+"/"+shopGoods.getGoodsSku() + "/g1_180_240.jpg");
			goods.setBrandId(objGoodsDetailVo.getBrandId());
			goods.setBrandCode(objGoodsDetailVo.getBrandCode());
			goods.setGoodsName(objGoodsDetailVo.getGoodsName());
			goods.setStateOnsale(objGoodsDetailVo.getStateOnsale());
			goods.setGoodsSku(shopGoods.getGoodsSku());
			goods.setQuantity(shopGoods.getQuantity());
			goods.setShoppingCartId(shopGoods.getId());
			goods.setChecked(shopGoods.getChecked());
			String goodsSource=shopGoods.getGoodsSource();;
			if(goodsSource!=null&&goodsSource.indexOf("\"")>=0){
				goodsSource=goodsSource.replaceAll("\"", "");
			}
			goods.setGoodsSource(goodsSource);
			goods.setGoodsId(objGoodsDetailVo.getGoodsInnerId());
			goods.setShoppingCartId(shopGoods.getId());
//			goods.setActivityItemList(objGoodsDetailVo.getActivityItemList());
			//品牌名称
			goods.setBrandCNName(objGoodsDetailVo.getBrandCNName());
			goods.setBrandEnName(objGoodsDetailVo.getBrandEnName());
			goods.setReferrerPageId(shopGoods.getReferrerPageId());
			// 颜色尺码库存
			List<FlexibleItem> skuList = objGoodsDetailVo.getStyleSku();
			List<String> sizeList = objGoodsDetailVo.getSizes();
			List<String> colorList = objGoodsDetailVo.getColors();
			int[][] styleMatrix = objGoodsDetailVo.getStyleMatrix();
			for (int i = 0; i < skuList.size(); i++) {
				if (shopGoods.getGoodsSku().equals(skuList.get(i).getValue())) {
					String sku = skuList.get(i).getKey();// "key": "c0s2"
					int c = Integer.parseInt(sku.substring(sku.indexOf("c")+1, sku.indexOf("s")));
					int s = Integer.parseInt(sku.substring(sku.indexOf("s")+1));
					String color = colorList.get(c);
					String size = sizeList.get(s);
					int inventory = styleMatrix[s][c];
					goods.setColor(color);
					goods.setSize(size);
					goods.setInventory(inventory);
					break;
				}
			}
			Integer inventory=  goods.getInventory();
			if (goods.getChecked().equals("Y") && goods.getQuantity() >0 && objGoodsDetailVo.getStateOnsale().equals("1") && inventory!=null&&inventory > 0) {
				// 如果购买量大于库存，就修改购买量为库存量；库存为0则为售罄商品
				if (inventory < goods.getQuantity()) {
					goods.setQuantity(inventory);
					// 当购物车中商品库存不足时，购物车图标显示数量与购物车中真实数量也需要保持一致
					String id = shopGoods.getId();
					if (id !=null && !id.equals("")) {
						shopGoods.setQuantity(inventory);
						shoppingCartService.updateGoods(shopGoods);
					}else{
						shopGoods.setQuantity(inventory);
					}
					
				}
				
				checkedList.add(goods);
				totalQunt += goods.getQuantity();
			} else {
				// 0:下架,1:上架
				// 下架,售罄商品处理
				goods.setChecked("N");
				unCheckedList.add(goods);
			}
			goods.setPurchasePrice(Double.parseDouble(objGoodsDetailVo.getZsPrice()));
			goods.setPrice(Double.parseDouble(objGoodsDetailVo.getPrice()));
			goods.setAmount(XiuUtil.getPriceDouble2Str(Arithmetic4Double.multi(objGoodsDetailVo.getZsPrice(), shopGoods.getQuantity())));
			// 原有列表
			goodsList.add(goods);
			if(!goodSns.equals("")){
				goodSns=goodSns+",";
			}
			goodSns=goodSns+goods.getGoodsSn();
			
			//限购
			goods.setLimitSaleNum(objGoodsDetailVo.getLimitSaleNum());
			
			//是否支持礼品包装
			goods.setSupportPackaging(objGoodsDetailVo.getSupportPackaging());
			
		}
		
		// 是否能使用优惠券
		if(!goodSns.equals("")){
			logger.info("查看购物车-查看是否能使用优惠券start 当前系统时间："+dateFormat.format(new Date())+",goodSns:"+goodSns);
			Map<String,Boolean>  canUseCouponMap = orderService.canUserCouponBatchs(goodSns);
			logger.info("查看购物车-查看是否能使用优惠券end 当前系统时间："+dateFormat.format(new Date()));
			for (ShoppingCartGoodsVo shop:goodsList) {
				Boolean isUserCoupons=canUseCouponMap.get(shop.getGoodsSn());
				if(isUserCoupons==null){
					isUserCoupons=false;
				}
				shop.setCanUseCoupon(isUserCoupons);
			}
		}
		
		
		if (null != checkedList && checkedList.size() > 0) {
			// 构造订单商品信息
			List<OrderGoodsVO> orderGoodsList = goodsManager.getOrderGoodsList(checkedList, goodsDetailBoList);

			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				OrderReqVO calcOrderReqVO = new OrderReqVO();
				calcOrderReqVO.setTokenId(tokenId);
				calcOrderReqVO.setGoodsList(orderGoodsList);
				SessionUtil.setDeviceInfo(request, calcOrderReqVO);
				calcOrderReqVO.setDeviceParams(getDeviceParams(request));

				Date calStartDate = new Date();
//				CalculateOrderBo calcOrder = new CalculateOrderBo();
//				CalculateOrderBo calcOrder = orderService.calcShoppingCartGoods(calcOrderReqVO);
//				List<MktActInfoVo> mktList = calcOrder.getMktActInfoList();
//				if (mktList != null && mktList.size() > 0) {
//					markStatus = true;
//				}
				Double amount=0D;
				for (ShoppingCartGoodsVo scgoods : checkedList) {
					amount=amount+Arithmetic4Double.multi(scgoods.getPurchasePrice(), scgoods.getQuantity());
				}
                String amountStr=XiuUtil.getPriceDouble2Str(amount);
				logger.info("计算购物车商品总金额：" + amountStr);
				valMap.put("totalAmt", amountStr);
				valMap.put("promoAmount", "0");
			}
		} else {
			valMap.put("totalAmt", "0");
			valMap.put("promoAmount", "0");
		}
		// 如果有促销活动  则按促销重新排序  否则按原顺序排序
//		if (markStatus) {
//			// 排序，有促销活动在前，没促销在后，按创建时间逆序
//			goodsList = new ArrayList<ShoppingCartGoodsVo>();
//			goodsList.addAll(mktGoodsList);
//			goodsList.addAll(noMktGoodsList);
//			goodsList.addAll(unCheckedList);
//		}
		valMap.put("goodsList", goodsList);
		valMap.put("totalQunt", totalQunt);
		return valMap;
	}
	
	/***
	 * 订单确认页面信息
	 * @param shoppingCartGoodsList
	 * @param tokenId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private HashMap<String, Object> confirmGoodsList(List<ShoppingCart> shoppingCartGoodsList,String tokenId,HttpServletRequest request) throws Exception{
		HashMap<String, Object> valMap = new HashMap<String, Object>();
		List<ShoppingCartGoodsVo> goodsList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> onSaleGoodsList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> mktGoodsList = new ArrayList<ShoppingCartGoodsVo>();
		List<ShoppingCartGoodsVo> noMktGoodsList = new ArrayList<ShoppingCartGoodsVo>();
		String lowStocks = "";
		int totalNumber = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获取商品列表信息
		String goodsSnStr = goodsSnListToString(shoppingCartGoodsList);
		Date startDate = new Date();
		logger.info("调用商品中心获取商品列表信息start 当前系统时间："+dateFormat.format(new Date()));
		List<GoodsDetailBo> goodsDetailBoList = goodsManager.getShoppingCartGoodsDetailBoList(goodsSnStr, getDeviceParams(request));
		List<GoodsDetailVo> goodsDetailVoList = goodsManager.getGoodsDetailList(goodsDetailBoList);
		logger.info("调用商品中心获取商品列表信息end  当前系统时间："+dateFormat.format(new Date()));
		logger.info("调用商品中心获取商品列表信息用时："+ ((new Date()).getTime() - startDate.getTime())/1000);
		Map<String, GoodsDetailVo> goodsDetailVoMap = new HashMap<String, GoodsDetailVo>();
		for (GoodsDetailVo goodsDetailVo : goodsDetailVoList) {
			goodsDetailVoMap.put(goodsDetailVo.getGoodsSn(), goodsDetailVo);
		}
		// 查询商品信息
		for (ShoppingCart shopGoods : shoppingCartGoodsList) {
			ShoppingCartGoodsVo goods = new ShoppingCartGoodsVo();
			GoodsDetailVo objGoodsDetailVo = goodsDetailVoMap.get(shopGoods.getGoodsSn());
			if (objGoodsDetailVo == null) {
				continue;
			}
			goods.setGoodsSn(objGoodsDetailVo.getGoodsSn());
			goods.setGoodsImgUrl(objGoodsDetailVo.getGoodsImgUrl()+"/"+shopGoods.getGoodsSku() + "/g1_180_240.jpg");
			goods.setBrandId(objGoodsDetailVo.getBrandId());
			goods.setBrandCode(objGoodsDetailVo.getBrandCode());
			goods.setGoodsName(objGoodsDetailVo.getGoodsName());
			goods.setBrandEnName(objGoodsDetailVo.getBrandEnName());
			goods.setBrandCNName(objGoodsDetailVo.getBrandCNName());
			goods.setStateOnsale(objGoodsDetailVo.getStateOnsale());
			goods.setGoodsSku(shopGoods.getGoodsSku());
			goods.setQuantity(shopGoods.getQuantity());
			goods.setGoodsSource(shopGoods.getGoodsSource());
			goods.setGoodsId(objGoodsDetailVo.getGoodsInnerId());
			// 颜色尺码库存
			List<FlexibleItem> skuList = objGoodsDetailVo.getStyleSku();
			List<String> sizeList = objGoodsDetailVo.getSizes();
			List<String> colorList = objGoodsDetailVo.getColors();
			int[][] styleMatrix = objGoodsDetailVo.getStyleMatrix();
			for (int i = 0; i < skuList.size(); i++) {
				if (shopGoods.getGoodsSku().equals(skuList.get(i).getValue())) {
					String sku = skuList.get(i).getKey();// "key": "c0s2"
					int c = Integer.parseInt(sku.substring(sku.indexOf("c")+1, sku.indexOf("s")));
					int s = Integer.parseInt(sku.substring(sku.indexOf("s")+1));
					String color = colorList.get(c);
					String size = sizeList.get(s);
					int inventory = styleMatrix[s][c];
					goods.setColor(color);
					goods.setSize(size);
					goods.setInventory(inventory);
					break;
				}
			}
			// 是否能使用优惠券
			boolean canUseCoupon = orderService.canUserCoupon(objGoodsDetailVo.getGoodsSn());
			goods.setCanUseCoupon(canUseCoupon);
			if (goods.getStateOnsale().equals("1") && goods.getInventory() > 0 && goods.getInventory() >= goods.getQuantity()) {
				onSaleGoodsList.add(goods);
				totalNumber = totalNumber + shopGoods.getQuantity();
			}else{
				if (goods.getStateOnsale().equals("0")) {
					lowStocks = lowStocks.concat(goods.getGoodsName()).concat("已下架").concat(",");
				}else {
					lowStocks = lowStocks.concat(goods.getGoodsName()).concat("库存不足").concat(",");
				}
			}
		}
		// 如果包含库存
		if (org.apache.commons.lang.StringUtils.isNotBlank(lowStocks)) {
			valMap.put("result", false);
			valMap.put("errorCode", ErrorCode.StockShortage.getErrorCode());
			valMap.put("errorMsg", lowStocks);
			return valMap;
		}
		
		if (null != onSaleGoodsList && onSaleGoodsList.size() > 0) {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			// 构造订单商品信息
			List<OrderGoodsVO> orderGoodsList = goodsManager.getOrderGoodsList(onSaleGoodsList, goodsDetailBoList);
			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				OrderReqVO calcOrderReqVO = new OrderReqVO();
				/** 优惠券号 */
				String couponCode = request.getParameter("couponCode");
				/** 是否使用虚拟账户中可用余额 */
				String isVirtualPay = request.getParameter("isVirtualPay");
				if (null == isVirtualPay || "".equals(isVirtualPay)) {
					isVirtualPay = "0";// 0不使用虚拟账户支付,1使用
				}
				calcOrderReqVO.setTokenId(tokenId);
				calcOrderReqVO.setGoodsList(orderGoodsList);
				SessionUtil.setDeviceInfo(request, calcOrderReqVO);
				calcOrderReqVO.setDeviceParams(getDeviceParams(request));
				calcOrderReqVO.setCouponNumber(couponCode);// 优惠券号
				calcOrderReqVO.setUserId(loginResVo.getUserId());// 用户Id
				calcOrderReqVO.setIsVirtualPay(isVirtualPay);// 是否使用虚拟账户中可用余额
				Date calStartDate = new Date();
				logger.info("计算购物车商品价格和促销信息start 当前系统时间："+dateFormat.format(new Date()));
				CalculateOrderBo calcOrder = orderService.calcOrder(calcOrderReqVO);
				logger.info("计算购物车商品价格和促销信息end  当前系统时间："+dateFormat.format(new Date()));
				logger.info("获取选择商品列表数据信息用时："+ ((new Date()).getTime() - calStartDate.getTime())/1000);
				List<MktActInfoVo> mktList = calcOrder.getMktActInfoList();
				for (ShoppingCartGoodsVo scgoods : onSaleGoodsList) {
					for (OrderGoodsVO orderGoods : calcOrder.getGoodsList()) {
						if (scgoods.getGoodsSn().equals(orderGoods.getGoodsSn())) {
							double price = Arithmetic4Double.div(orderGoods.getZsPrice(), 100, 2);
							scgoods.setPrice(price);
							double purchasePrice = Arithmetic4Double.div(orderGoods.getDiscountPrice(), 100, 2);
							scgoods.setPurchasePrice(purchasePrice);
							scgoods.setAmount(XiuUtil.getPriceDouble2Str(Arithmetic4Double.multi(purchasePrice, scgoods.getQuantity())));
							break;
						}
					}
					List<MktActInfoVo> goodsMkt = new ArrayList<MktActInfoVo>();
					for (MktActInfoVo mkt : mktList) {
						Object[] goodsSns = mkt.getCombinationId();
						if (goodsSns != null && goodsSns.length > 0) {
							for (int i = 0; i < goodsSns.length; i++) {
								HashMap<String, String> valMap11 = (HashMap<String, String>) goodsSns[i];
								logger.info("valMap11.get(goods_id):" + valMap11.get("goods_id") + "    scgoods.getGoodsSn():" + scgoods.getGoodsSn());
								if (scgoods.getGoodsSn().equals(valMap11.get("goods_id"))) {
									goodsMkt.add(mkt);
								}
							}
						}
					}

					scgoods.setActivityItemList(goodsMkt);
					if (null != scgoods.getActivityItemList() && scgoods.getActivityItemList().size() > 0) {
						mktGoodsList.add(scgoods);
					} else {
						noMktGoodsList.add(scgoods);
					}
				}

				// 是否能使用优惠券
				boolean canUseCoupon = orderService.canUserCoupon(goodsSnStr);
				logger.info("计算购物车商品总金额：" + calcOrder.getAmount());
				valMap.put("totalPrice", calcOrder.getAmount());
				// 计算订单金额
				valMap.put("amount", calcOrder.getAmount());
				valMap.put("goodsAmount", calcOrder.getGoodsAmount());
				valMap.put("freight", calcOrder.getFreight());
				valMap.put("promoAmount", calcOrder.getPromoAmount());
				valMap.put("vtotalAmount", calcOrder.getVtotalAmount());
				valMap.put("vpayAmount", calcOrder.getVpayAmount());
				valMap.put("totalAmount", calcOrder.getTotalAmount());
				valMap.put("leftAmount", calcOrder.getLeftAmount());
				valMap.put("canUseCoupon", canUseCoupon);
				
				valMap.put("orderPayConfig", orderService.getOrderPayConfig(
						Double.parseDouble(calcOrder.getTotalAmount())));
			}
			// 排序，有促销活动在前，没促销在后，按创建时间逆序
			goodsList.addAll(mktGoodsList);
			goodsList.addAll(noMktGoodsList);
			
			valMap.put("result", true);
			valMap.put("errorCode", ErrorCode.Success.getErrorCode());
			valMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			valMap.put("goodsList", goodsList);
			valMap.put("totalNumber", totalNumber);
			return valMap;
		} else {
			valMap.put("result", false);
			valMap.put("errorCode", ErrorCode.GetGoodsListFailed.getErrorCode());
			valMap.put("errorMsg", ErrorCode.GetGoodsListFailed.getErrorMsg());
			return valMap;
		}
	}
	/**
	 * 计算购物车选中商品的价格及促销信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "calcCartDetail", produces = "text/html;charset=UTF-8")
	public String calcShoppingCartGoods(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		List<ShoppingCart> shoppingCartGoodsList=new ArrayList<ShoppingCart>();
		HashMap<String, Object> sortMap = new HashMap<String, Object>();
		try {
			String tokenId = SessionUtil.getTokenId(request);
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));
			
			 //添加多选以及修改数量（全部）
			//过滤选中的商品，查询促销信息，未选中的就不参与促销查询
			// 商品信息的json串  
			String goods = request.getParameter("goods");
			logger.info("获取传递商品列表参数为："+goods);	
			if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.beanjsonP(jsoncallback, model);
			}
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!goods.startsWith("[")) && (!goods.endsWith("]"))) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			// 转换成商品列表信息
			JSONArray jsonArray = JSONArray.fromObject(goods);
			
			shoppingCartGoodsList = (List<ShoppingCart>) JSONArray.toCollection(jsonArray, ShoppingCart.class);
			if (null != shoppingCartGoodsList && shoppingCartGoodsList.size() > 0) {
				sortMap=sortShoppingCartGoods(shoppingCartGoodsList,tokenId,request);
				if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
					//如果登陆就修改数据库中对应的值
					for(ShoppingCart shopCart:shoppingCartGoodsList){
						if (null != shopCart.getChanged() && "Y".equals(shopCart.getChanged()) && shopCart.getQuantity() > 0) {
							ShoppingCart sc = new ShoppingCart();
							sc.setId(shopCart.getId());
							sc.setChecked(shopCart.getChecked());
							sc.setQuantity(shopCart.getQuantity());
							shoppingCartService.updateShoppingCartGoods(sc);
						}
					}
				}else{
					// 重新写购物车cookie
					SessionUtil.addShoppingCartGoods(response, goods);
				}
				
				List<ShoppingCartGoodsVo> goodsList = (List<ShoppingCartGoodsVo>) sortMap.get("goodsList");
				handleProductSupportAndLimitSale(goodsList); //处理礼品包装和限购
				
				model.put("result", true);
				model.put("shoppingCartGoodsList",goodsList);
				model.put("totalAmount", sortMap.get("totalAmt"));
				model.put("promoAmount", sortMap.get("promoAmount"));
				model.put("totalQuantity", sortMap.get("totalQunt"));
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.NoShoppingCartGoods.getErrorCode());
				model.put("errorMsg", ErrorCode.NoShoppingCartGoods.getErrorMsg());
			}	
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("计算购物车某商品的价格及促销时发生异常：" + e.getMessage());
		}

		logger.info("计算购物车某商品的价格及促销返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	/**
	 * 获取购物车商品总数
	 */
	@RequestMapping(value = "/getGoodsTotal", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getGoodsTotal(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			// 如果用户未登陆
			if (!checkLogin(request)) {
				// 未登录就取wap/app传过来的购物车信息 如果不传递则取cookie里的商品信息
				// 商品信息的json串
				String goods = request.getParameter("goods");
				if (StringUtils.isEmpty(goods)) {
					goods = SessionUtil.getShoppingCartGoodsList(request);
				}
				if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("total", 0);
					return JsonUtils.bean2jsonP(jsoncallback, model);
				}
				// 转换成商品列表信息
				JSONArray jsonArray = JSONArray.fromObject(goods);
				int total = 0;
				for (int i = 0; i < jsonArray.size(); i++) {
					net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
					/** 购买数量 */
					int quantity = jsonObject.getInt("quantity");
					total = total + quantity;
				}
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("total", total);
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			LoginResVo user = SessionUtil.getUser(request);
			Map<String, String> params = new HashMap<String, String>();
			params.put("userId", user.getUserId());
			// 移除购物车商品
			int total = shoppingCartService.getGoodsTotal(params);
			// 移除购物车商品
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			model.put("total", total);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取购物车商品总数异常：" + e.getMessage(), e);
		}
		logger.info("获取购物车商品总数返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	
	/**
	 * 加入购物车
	 */
	@RequestMapping(value = "/addGoods", produces = "text/html;charset=UTF-8")
	public @ResponseBody String addGoods(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			String goodsSn = request.getParameter("goodsSn");
			String goodsSku = request.getParameter("goodsSku");
			int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
			String goodsSource = request.getParameter("goodsSource");
			String referrePageId = request.getParameter("referrerPageId");
			goodsSource = org.apache.commons.lang.StringUtils.isBlank(goodsSource)?"UC0000":goodsSource;
			String checked = request.getParameter("checked");
			checked = org.apache.commons.lang.StringUtils.isBlank(checked) ? "Y" : checked;

			// 验证参数
			if (StringUtils.isEmpty(goodsSn) || StringUtils.isEmpty(goodsSku)) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			
			// 查询goodsSku库存量
			int inventory = goodsManager.queryInventoryBySku(goodsSku);
			
			if(inventory < 0) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
				model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 如果用户未登陆 则添加到cookie
			if (!checkLogin(request)) {
				// 获取商品原来数量
				int number = SessionUtil.getShoppingCartGoodsQuantityBySku(request, goodsSku);
				number = quantity + number;
				// 如果超过上限数量
				if (number > OVERLIMIT) {
					model.put("result", false);
					model.put("errorCode", ErrorCode.OverLimitNumber.getErrorCode());
					model.put("errorMsg", ErrorCode.OverLimitNumber.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, model);
				}
				int limitSaleNum = goodsManager.getGoodsLimitSaleNum(goodsSn); //限购数量
				// 如果购买数量大于库存
				if (number > inventory) {
					if(limitSaleNum != -1 && inventory == limitSaleNum) {
						//如果商品限购，且限购数量和库存数量一样，返回限购提示
						model.put("result", false);
						model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
						model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, model);
					}
					model.put("result", false);
					model.put("errorCode", ErrorCode.CanNotContinueBuy.getErrorCode());
					model.put("errorMsg", ErrorCode.CanNotContinueBuy.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, model);
				}
				// 验证商品是否限购
				if(limitSaleNum != -1 && limitSaleNum < number) {
					//是限购商品，且购买数量超过限购数量
					model.put("result", false);
					model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
					model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
					return JsonUtils.beanjsonP(jsoncallback, model);
				}
				if(limitSaleNum != -1) {
					//如果是限购商品，则查询购物车里的商品是否有同走秀码不同sku的商品，且合计数量超过限购数量
					int goodsCount = SessionUtil.getShoppingCartGoodsQuantityByGoodsSn(request, goodsSn);
					if(limitSaleNum <= goodsCount) {
						model.put("result", false);
						model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
						model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, model);
					}
				}
				
				net.sf.json.JSONObject goodsJson = new net.sf.json.JSONObject();
				goodsJson.put("goodsSn", goodsSn);
				goodsJson.put("goodsSku", goodsSku);
				goodsJson.put("quantity", quantity);
				goodsJson.put("checked", checked);
				goodsJson.put("goodsSource", goodsSource);
				goodsJson.put("referrePageId", referrePageId);
				boolean status = SessionUtil.addShoppingCartGoods(request, response, goodsJson);
				if (status) {
					// 加入购物车成功
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.SystemError.getErrorCode());
					model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				}
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 查询当前sku是否已经存在 存在则在数量上相加 否则新增
			LoginResVo user = SessionUtil.getUser(request);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", user.getUserId());
			params.put("goodsSku", goodsSku);
			List<ShoppingCart> shoppingCartList = shoppingCartService.getGoodsList(params);
			if (shoppingCartList != null && shoppingCartList.size() > 0) {
				ShoppingCart shoppingCart = shoppingCartList.get(0);
				quantity = shoppingCart.getQuantity() + quantity;
				// 如果数量大于上限 则重置为上限
				// 如果超过上限数量
				if (quantity > OVERLIMIT) {
					model.put("result", false);
					model.put("errorCode", ErrorCode.OverLimitNumber.getErrorCode());
					model.put("errorMsg", ErrorCode.OverLimitNumber.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, model);
				}
				
				// 查询商品是否限购
				Map<String,Object> limitMap = new HashMap<String,Object>();
				limitMap.put("goodsSn", shoppingCart.getGoodsSn());
				limitMap.put("quantity", quantity);
				
				Map limitResultMap = goodsManager.getOrderLimitSaleInfo(limitMap); //查询限购信息
				
				boolean limitResult = (Boolean) limitResultMap.get("result");
				if(limitResult) {
					int limitSaleNum = (Integer) limitResultMap.get("limitSaleNum");
					// 如果购买数量大于库存
					if (quantity > inventory) {
						if(limitSaleNum != -1 && inventory == limitSaleNum) {
							//如果商品限购，且限购数量和库存数量一样，返回限购提示
							model.put("result", false);
							model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
							model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback, model);
						}
						model.put("result", false);
						model.put("errorCode", ErrorCode.CanNotContinueBuy.getErrorCode());
						model.put("errorMsg", ErrorCode.CanNotContinueBuy.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, model);
					}
					
					if(limitSaleNum != -1) {
						//如果商品限购，则查询购物车里的商品是否有同走秀码不同sku的商品，且合计数量超过限购数量
						params.put("goodsSn", goodsSn);
						int goodsCount = shoppingCartService.getGoodsCountByGoodsSn(params);
						if(limitSaleNum <= goodsCount) {
							model.put("result", false);
							model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
							model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback, model);
						}
					}
				} else {
					model.put("result", limitResult);
					model.put("errorCode", limitResultMap.get("errorCode"));
					model.put("errorMsg", limitResultMap.get("errorMsg"));
					return JsonUtils.beanjsonP(jsoncallback, model);
				}
				
				shoppingCart.setQuantity(quantity);
				shoppingCart.setCreateDate(new Date());
				shoppingCart.setChecked(checked);
				shoppingCart.setGoodsSource(goodsSource);
				shoppingCartService.updateGoods(shoppingCart);
			} else {
				ShoppingCart shoppingCart = new ShoppingCart();
				shoppingCart.setCreateDate(new Date());
				shoppingCart.setGoodsSku(goodsSku);
				shoppingCart.setGoodsSn(goodsSn);
				shoppingCart.setPlatform(1);
				// 如果数量大于上限
				if (quantity > OVERLIMIT) {
					quantity = OVERLIMIT;
				}
				
				// 验证商品是否限购
				Map<String,Object> limitMap = new HashMap<String,Object>();
				limitMap.put("goodsSn", goodsSn);
				limitMap.put("quantity", quantity);
				
				Map limitResultMap = goodsManager.getOrderLimitSaleInfo(limitMap); //查询限购信息
				
				boolean limitResult = (Boolean) limitResultMap.get("result");
				int limitSaleNum = (Integer) limitResultMap.get("limitSaleNum");
				if(!limitResult) {
					//如果限购
					if(limitSaleNum != -1 && inventory == limitSaleNum) {
						//如果商品限购，且限购数量和库存数量一样，返回限购提示
						model.put("result", false);
						model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
						model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, model);
					}
					
					model.put("result", limitResult);
					model.put("errorCode", limitResultMap.get("errorCode"));
					model.put("errorMsg", limitResultMap.get("errorMsg"));
					return JsonUtils.beanjsonP(jsoncallback, model);
				} else {
					if(limitSaleNum != -1) {
						//如果商品限购，则查询购物车里的商品是否有同走秀码不同sku的商品，且合计数量超过限购数量
						params.put("goodsSn", goodsSn);
						int goodsCount = shoppingCartService.getGoodsCountByGoodsSn(params);
						if(limitSaleNum <= goodsCount) {
							model.put("result", false);
							model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
							model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback, model);
						}
					}
				}
				
				shoppingCart.setQuantity(quantity);
				shoppingCart.setChecked(checked);
				shoppingCart.setGoodsSource(goodsSource);
				shoppingCart.setReferrerPageId(referrePageId);
				shoppingCart.setUserId(Long.parseLong(user.getUserId()));
				// 添加商品到购物车
				shoppingCartService.addGoods(shoppingCart);
			}
			// 加入购物车成功
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品加入购物车异常：" + e.getMessage(), e);
		}
		logger.info("商品加入购物车返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	
	/***
	 * 同步购物车商品
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/synGoods", produces = "text/html;charset=UTF-8")
	public @ResponseBody String synGoods(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			// 检查用户登陆状态
			if (!checkLogin(request)) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 未登录就取wap/app传过来的购物车信息 如果不传递则取cookie里的商品信息
			// 商品信息的json串
			String goods = request.getParameter("goods");
			if (StringUtils.isEmpty(goods)) {
				goods = SessionUtil.getShoppingCartGoodsList(request);
			}
			logger.info("获取传递商品列表参数为："+goods);	
			// 如果不传递
			if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!goods.startsWith("[")) && (!goods.endsWith("]"))) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			// 转换成商品列表信息
			JSONArray jsonArray = JSONArray.fromObject(goods);
			for (int i = 0; i < jsonArray.size(); i++) {
				net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
				/** 商品编码 */
				String goodsSn = jsonObject.getString("goodsSn");
				/** 商品SKU(尺寸、颜色) */
				String goodsSku = jsonObject.getString("goodsSku");
				/** 购买数量 */
				int quantity = jsonObject.getInt("quantity");
				/*** 是否选中 ***/
				String checked = "Y";
				if (jsonObject.containsKey("checked")) {
					checked = jsonObject.getString("checked");
				}
				String goodsSource = "UC0000";
				if (jsonObject.containsKey("goodsSource")) {
					goodsSource = jsonObject.getString("goodsSource");
				}
				/** 商品加入购物车来源pageId */
				String referrerPageId = "";
				if (jsonObject.containsKey("referrerPageId")) {
					referrerPageId = jsonObject.getString("referrerPageId");
				}
				// 查询goodsSku库存量
				int inventory = goodsManager.queryInventoryBySku(goodsSku);
				// 调用远程查询库存或许有-9999 则判定为0
				inventory = inventory >= 0 ? inventory : 0;
				// 查询当前sku是否已经存在 存在则在数量上相加 否则新增
				LoginResVo user = SessionUtil.getUser(request);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", user.getUserId());
				params.put("goodsSku", goodsSku);
				List<ShoppingCart> shoppingCartList = shoppingCartService.getGoodsList(params);
				if (shoppingCartList != null && shoppingCartList.size() > 0) {
					ShoppingCart shoppingCart = shoppingCartList.get(0);
					quantity = shoppingCart.getQuantity() + quantity;
					// 如果数量大于上限
					if (quantity > OVERLIMIT) {
						quantity = OVERLIMIT;
					}
					// 如果数量大于库存 则重置为库存数
					if (inventory > 0 && quantity > inventory) {
						quantity = inventory;
					}
					
					// 判断限购数量，如果购买商品数量大于限购数量
					int limitSaleNum = goodsManager.getGoodsLimitSaleNum(shoppingCart.getGoodsSn());
					if(limitSaleNum != -1 && limitSaleNum < quantity) {
						quantity = limitSaleNum;
					}
					shoppingCart.setQuantity(quantity);
					shoppingCart.setChecked(checked);
					shoppingCart.setGoodsSource(goodsSource);
					shoppingCartService.updateGoods(shoppingCart);
				} else {
					ShoppingCart shoppingCart = new ShoppingCart();
					shoppingCart.setCreateDate(new Date());
					shoppingCart.setGoodsSku(goodsSku);
					shoppingCart.setGoodsSn(goodsSn);
					shoppingCart.setPlatform(1);
					// 如果数量大于上限
					if (quantity > OVERLIMIT) {
						quantity = OVERLIMIT;
					}
					// 如果数量大于库存 则重置为库存数
					if (inventory > 0 && quantity > inventory) {
						quantity = inventory;
					}
					shoppingCart.setQuantity(quantity);
					shoppingCart.setChecked(checked);
					shoppingCart.setGoodsSource(goodsSource);
					shoppingCart.setReferrerPageId(referrerPageId);
					shoppingCart.setUserId(Long.parseLong(user.getUserId()));
					// 添加商品到购物车
					shoppingCartService.addGoods(shoppingCart);
				}
			}
			// 清空cookie里购物车的数据
			SessionUtil.delShoppingCartGoods(response);
			// 加入购物车成功
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("同步购物车商品异常：" + e.getMessage(), e);
		}
		logger.info("同步购物车商品返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	
	/**
	 * 移除购物车商品
	 */
	@RequestMapping(value = "/delGoods", produces = "text/html;charset=UTF-8")
	public @ResponseBody String delGoods(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			String goodsSku = request.getParameter("goodsSkus");
			// 验证参数
			if (StringUtils.isEmpty(goodsSku)) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 如果用户未登陆
			if (!checkLogin(request)) {
				boolean status = SessionUtil.delShoppingCartGoods(request, response, goodsSku);
				if (status) {
					// 加入购物车成功
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.SystemError.getErrorCode());
					model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				}
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 登陆用户删除购物车里商品信息
			Map<String, Object> params = new HashMap<String, Object>();
			String[] skuArr = goodsSku.split(",");
			List<String> skuList = Arrays.asList(skuArr);
			LoginResVo user = SessionUtil.getUser(request);
			params.put("userId", user.getUserId());
			params.put("skuList", skuList);
			// 移除购物车商品
			shoppingCartService.delGoodsBySku(params);

			// 移除购物车商品
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品移除购物车异常：" + e.getMessage(), e);
		}
		logger.info("移除购物车商品返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	
	/**
	 * 移除购物车商品
	 */
	@RequestMapping(value = "/delAndFavosGoods", produces = "text/html;charset=UTF-8")
	public @ResponseBody String delAndFavosGoods(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			String goodsIdsStr = request.getParameter("goodsIds");
			String goodsSku = request.getParameter("goodsSkus");
			String terminal = request.getParameter("terminal");
			// 验证参数
			if (StringUtils.isEmpty(goodsSku)||StringUtils.isEmpty(goodsIdsStr)) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 如果用户未登陆
			if (!checkLogin(request)) {
				boolean status = SessionUtil.delShoppingCartGoods(request, response, goodsSku);
				if (status) {
					// 加入购物车成功
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.SystemError.getErrorCode());
					model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				}
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}

			// 登陆用户删除购物车里商品信息
			Map<String, Object> params = new HashMap<String, Object>();
			String[] skuArr = goodsSku.split(",");
			List<String> skuList = Arrays.asList(skuArr);
			LoginResVo user = SessionUtil.getUser(request);
			params.put("userId", user.getUserId());
			params.put("skuList", skuList);
			// 移除购物车商品
			shoppingCartService.delGoodsBySku(params);

			//移入收藏
			//
			String[] goodIdArr=goodsIdsStr.split(",");
			Long ter=Long.parseLong("3");
			//查询该用户是否有收藏夹
			//从cookie中获得userId
			LoginResVo loginResVo=SessionUtil.getUser(request);
			Long userId=Long.parseLong(loginResVo.getUserId());
			if(null==terminal||"".equals(terminal)){
				ter=Long.parseLong("3");
			}else{
				ter=Long.parseLong(terminal);
			}
			
			//批量查询已经收藏的商品
			Map<String,Object> findItemMap=new HashMap<String,Object>();
			findItemMap.put("userId",  userId);
			findItemMap.put("goodsIdArr", goodIdArr);
			List<BookmarkIitemBo> bookMarkList=bookmarkService.getItemByUserIdAndCatentryIds(findItemMap);
			List<String> addBookMarks=new ArrayList<String>();
			for (int i = 0; i < goodIdArr.length; i++) {
				Boolean isExist=false;
				for (int j = 0; j < bookMarkList.size(); j++) {
					if(goodIdArr[i].equals(bookMarkList.get(j).getCatentryId()+"")){
						isExist=true;
						break;
					}
				}
				if(!isExist){
					addBookMarks.add(goodIdArr[i]);
				}
			}
			//
			if(addBookMarks.size()>0){
				List<BookmarkIitemListVo> itemListVoList=bookmarkService.getItemListByUserId(userId);
				if(null!=itemListVoList){
					Long itemListId=itemListVoList.get(0).getIitemlistId();
						//存在就获得收藏夹Id，添加收藏的商品信息，修改收藏夹更新时间
					//是否收藏了此商品
					List<BookmarkIitemBo> bookmarkIitemBoList=new ArrayList<BookmarkIitemBo>();
					for (int i = 0; i < addBookMarks.size(); i++) {
						BookmarkIitemBo iitemVo=new BookmarkIitemBo();
						iitemVo.setCatentryId(Long.parseLong(addBookMarks.get(i)));
						iitemVo.setUserId(userId);
						iitemVo.setStoreentId(Integer.parseInt(ConfigUtil.getValue("goods.storeId")));
						iitemVo.setLastupdate(new Date());
						iitemVo.setField2(getGoodsPrice(Long.parseLong(addBookMarks.get(i))));
						iitemVo.setTerminalType(ter);
						iitemVo.setIitemlistId(itemListId);
						bookmarkIitemBoList.add(iitemVo);
					}
					int flag = bookmarkService.addBatchIitemVos(bookmarkIitemBoList);
					if (0 == flag) {
						BookmarkIitemListVo iitemList=new BookmarkIitemListVo();
						iitemList.setIitemlistId(itemListId);
						iitemList.setUserId(userId);
						iitemList.setLastupdate(new Date());
						int res=bookmarkService.updateIitemListByUserIdAndItemListId(iitemList);
					
					} 
				}else{
					//不存在就为用户新建收藏夹，获得收藏夹Id，添加收藏商品信息
					BookmarkIitemListVo iitemList=new BookmarkIitemListVo();
					iitemList.setUserId(userId);
					iitemList.setLastupdate(new Date());
					iitemList.setDescription("Favor");
					int flag = bookmarkService.addIitemListVo(iitemList);
					if (0 == flag) {
						Long newItemListId=bookmarkService.getItemListByUserId(userId).get(0).getIitemlistId();
						//是否收藏了此商品
						List<BookmarkIitemBo> bookmarkIitemBoList=new ArrayList<BookmarkIitemBo>();
						for (int i = 0; i < addBookMarks.size(); i++) {
							BookmarkIitemBo iitemVo=new BookmarkIitemBo();
							iitemVo.setCatentryId(Long.parseLong(addBookMarks.get(i)));
							iitemVo.setUserId(userId);
							iitemVo.setStoreentId(Integer.parseInt(ConfigUtil.getValue("goods.storeId")));
							iitemVo.setLastupdate(new Date());
							iitemVo.setField2(getGoodsPrice(Long.parseLong(addBookMarks.get(i))));
							iitemVo.setTerminalType(ter);
							iitemVo.setIitemlistId(newItemListId);
							bookmarkIitemBoList.add(iitemVo);
						}
						int res = bookmarkService.addBatchIitemVos(bookmarkIitemBoList);
						
					} 
				}
			}
			// 移除购物车商品
			model.put("result", true);
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("商品移除购物车异常：" + e.getMessage(), e);
		}
		logger.info("移除购物车商品返回结果：" + JSON.toJSONString(model));
		return JsonUtils.beanjsonP(jsoncallback, model);
	}
	
	/**
	 * 检查是否已收藏了商品
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	private boolean hasExistsFavorGoodsUG(Long userId,Long goodsId)throws Exception{
		HashMap<String, Object> valMap=new HashMap<String, Object>();
		valMap.put("userId", userId);
		valMap.put("catentryId", goodsId);
		List<BookmarkIitemBo> itemList=bookmarkService.getItemByUserIdAndCatentryId(valMap);
		if(null!=itemList&&itemList.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 获得商品最终售价
	 * 
	 */
	private Double getGoodsPrice(Long goodsId)throws Exception {
		Double price=0.00;
		String goodsSn=bookmarkService.getGoodsSnByCatentryId(goodsId);
		if(StringUtils.isEmpty(goodsSn)){
			price=goodsManager.getZxPrice(goodsSn) ;
			return price;
		}else{
			logger.info("获得商品最终售价时异常"+goodsId);
			return null;
		} 
	}

	
	
	/**
	 * 创建订单
	 */
	@RequestMapping(value = "/createOrder", produces = "text/html;charset=UTF-8")
	public @ResponseBody String createOrder(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值  
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String tokenId = SessionUtil.getTokenId(request);	
		
		try {
			// 如果用户未登陆
			if (!checkLogin(request)) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback,model);
			}

			// 商品信息的json串
			String goods = request.getParameter("goods");
			logger.info("获取传递商品列表参数为："+goods);	
			if (StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback,model);
			}
			
			// 如果参数不为空 或者不是已[开头 已]结尾则认为不是一个合格的json参数
			if ((!goods.startsWith("[")) && (!goods.endsWith("]"))) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				model.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, model);
			}
			
			List<OrderGoodsVO> goodsList = new ArrayList<OrderGoodsVO>();
			Set<String> activeIdList = new HashSet<String>();
			Set<String> goodsSnList = new HashSet<String>();
			try {
				// 转换成商品列表信息
				JSONArray jsonArray = JSONArray.fromObject(goods);
				for (int i = 0; i < jsonArray.size(); i++) {
					net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
					/**商品编码*/
					String goodsSn = jsonObject.getString("goodsSn");
					goodsSnList.add(goodsSn);
					/**商品SKU(尺寸、颜色)*/
					String goodsSku = jsonObject.getString("goodsSku");
					/**购买数量*/
					int quantity = jsonObject.getInt("quantity");
					/**活动Id**/
					String activeId = jsonObject.getString("activeId");
					/**商品来源**/
					String goodsSource = "UC0000";
					if (jsonObject.containsKey("goodsSource")) {
						goodsSource = jsonObject.getString("goodsSource");
					}
					
					// 获取计算商品库存数据信息
					List<OrderGoodsVO> goodsVOList = goodsManager.getOrderGoodsList(goodsSn, goodsSku, quantity, getDeviceParams(request));
					if (goodsVOList.size()>0) {
						// 添加商品来源
						for (OrderGoodsVO orderGoodsVO : goodsVOList) {
							orderGoodsVO.setGoodsSource(goodsSource);
						}
						// 分割活动Id
						if (org.apache.commons.lang.StringUtils.isNotBlank(activeId)) {
							String[] activeArr = activeId.split(",");
							for (int j = 0; j < activeArr.length; j++) {
								activeIdList.add(activeArr[j]);
							}
						}
						goodsList.addAll(goodsVOList);
					}else{
						model.put("result", false);
						model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
						model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback,model);
					}
				}
			} catch (EIBusinessException e) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
				model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
				ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
				logger.error("创建订单 并支付EIBusinessException"+e.getMessage(),e);
				return JsonUtils.bean2jsonP(jsoncallback,model);
			}
			
			//判断商品的限购数量
			String userId = SessionUtil.getUserId(request);
			Map<String, Integer> limitGoodsMap = new HashMap<String, Integer>();
			for(OrderGoodsVO orderGoodsVO : goodsList) {
				Map<String,Object> limitMap = new HashMap<String,Object>();
				limitMap.put("userId", userId);
				limitMap.put("goodsSn", orderGoodsVO.getGoodsSn());
				limitMap.put("quantity", orderGoodsVO.getGoodsQuantity());
				Map limitResultMap = orderService.getOrderLimitSaleInfo(limitMap); //查询限购信息
				boolean limitResult = (Boolean) limitResultMap.get("result");
				if(!limitResult) {
					//如果限购
					model.put("result", limitResult);
					model.put("errorCode", limitResultMap.get("errorCode"));
					model.put("errorMsg", limitResultMap.get("errorMsg"));
					return JsonUtils.bean2jsonP(jsoncallback,model);
				}
				int limitSaleNum = (Integer) limitResultMap.get("limitSaleNum");
				if(limitSaleNum != -1) {
					//如果商品限购，则查询商品列表是否有同走秀码不同sku的商品，且合计数量超过限购数量
					if(limitGoodsMap.containsKey(orderGoodsVO.getGoodsSn())) {
						int goodsCount = limitGoodsMap.get(orderGoodsVO.getGoodsSn());
						if(limitSaleNum <= goodsCount) {
							model.put("result", false);
							model.put("errorCode", ErrorCode.OverGoodsLimitSaleNum.getErrorCode());
							model.put("errorMsg", ErrorCode.OverGoodsLimitSaleNum.getErrorMsg());
							return JsonUtils.bean2jsonP(jsoncallback,model);
						} else {
							goodsCount = goodsCount + 1;
							limitGoodsMap.put(orderGoodsVO.getGoodsSn(), goodsCount); //更新商品数量
						}
					} else {
						limitGoodsMap.put(orderGoodsVO.getGoodsSn(), 1);
					}
				}
			}
			
			// 计算订单
			OrderReqVO calcOrderReqVO = new OrderReqVO();
			calcOrderReqVO.setTokenId(tokenId);
			calcOrderReqVO.setGoodsList(goodsList); // 购物车商品列表
			SessionUtil.setDeviceInfo(request, calcOrderReqVO);
			/**优惠券号*/
			String couponCode=request.getParameter("couponCode");
			if (org.apache.commons.lang.StringUtils.isNotBlank(couponCode)) {
				calcOrderReqVO.setCouponNumber(couponCode);
			}
			
			/**活动编号*/
			String activeId = "";
			if (activeIdList != null && activeIdList.size() > 0) {
				activeId = listToString(activeIdList);
			}
			/**收货地址Id*/
			String addressId=request.getParameter("orderReqVO.addressId");
			// addressId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			addressId=EncryptUtils.decryptByAES(addressId, aesKey);
			
			/**分享用户编号*/
/*			String cpsFromId=CookieUtil.getInstance().readCookieValue(request, "m-cps-from-id");
			if(null==cpsFromId||"".equals(cpsFromId)){
				cpsFromId="";
			}*/
			
			String cps="";
			//取cookie获取cps
			String xiukcps =CookieUtil.getInstance().readCookieValue(request, "kcpsxiu");
			String cpscode =CookieUtil.getInstance().readCookieValue(request, "cpscode");
			if(null!= xiukcps){
				cps = cpSService.queryCookiesInfo(xiukcps,  cpscode);
			}
			/**是否使用虚拟账户中可用余额 */
			String isVirtualPay=request.getParameter("isVirtualPay");
			if(null==isVirtualPay||"".equals(isVirtualPay)){
				isVirtualPay="0";//0不使用虚拟账户支付,1使用
			}
			
			/**是否多次支付 */
			String isMutilPay = "1".equals(request.getParameter("isMutilPay")) ? "1" : "0";
			/**多笔支付 本次支付金额*/
			String reqAmount = request.getParameter("reqAmount");
			/**本次支付方式*/
			String payPlatform = request.getParameter("payPlatform");
			
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			calcOrderReqVO.setUserId(loginResVo.getUserId());//用户Id
			calcOrderReqVO.setIsVirtualPay(isVirtualPay);//是否使用虚拟账户中可用余额
			CalculateOrderBo calcOrder = orderService.calcOrder(calcOrderReqVO);
			
			//发票类型
//			String invoice = request.getParameter("orderReqVO.invoice");
			//发票抬头
//			String invoiceHeading =ObjectUtil.getString( request.getParameter("orderReqVO.invoiceHeading"),"无");
			OrderReqVO orderReqVO = new OrderReqVO();
			orderReqVO.setDeliverTime(request.getParameter("orderReqVO.deliverTime"));
			orderReqVO.setPaymentMethod(StringUtils.isEmpty(payPlatform) ? "AlipayWire" : payPlatform);//默认值支付宝网页版
			orderReqVO.setGoodsFrom(request.getParameter("orderReqVO.goodsFrom"));//
			orderReqVO.setAddressId(addressId);
//			orderReqVO.setInvoice(invoice);
//			orderReqVO.setInvoiceHeading(invoiceHeading);
			orderReqVO.setOrderSource(request.getParameter("orderReqVO.orderSource"));
			orderReqVO.setMessage(request.getParameter("orderReqVo.orderMsg"));
			
			orderReqVO.setTokenId(tokenId);
			orderReqVO.setGoodsList(priceFormat(goodsList, calcOrder));
			
			orderReqVO.setIsMutilPay(isMutilPay);
			orderReqVO.setReqAmount(reqAmount);
			
			if(null!=couponCode&&!"".equals(couponCode)&&!"null".equals(couponCode)){
				orderReqVO.setCouponNumber(couponCode);//优惠券关联订单
			}
			 orderReqVO.setUserId(loginResVo.getUserId());
			//使用虚拟账户金额
			if(null!=isVirtualPay&&"1".equals(isVirtualPay)){
				 orderReqVO.setVtotalAmount(calcOrder.getVtotalAmount());
				 orderReqVO.setLeftAmount(calcOrder.getLeftAmount());
				 orderReqVO.setIsVirtualPay(isVirtualPay);
			}
			SessionUtil.setDeviceInfo(request, orderReqVO);
			// 处理cps或media信息
			// 如果是android或者ios，则不进行CPS处理 update 2015-05-19
			String orderSource = request.getParameter("orderReqVO.orderSource");
			if(!Strings.isNullOrEmpty(orderSource) && ("3".equals(orderSource) || "4".equals(orderSource) || "5".equals(orderSource)) ) {
				cps = "";
			} else {
				handleCPSAndMeida(request, orderReqVO);
			}
			
			// 生成订单
			OrderResVO objOrderResVO = null;
			try {
				objOrderResVO = orderService.createOrder(orderReqVO,activeId,cps);
				// orderId加密(AES)
				String orderId=EncryptUtils.encryptOrderIdByAES(String.valueOf(objOrderResVO.getOrderId()), aesKey);		
				
				// 清理购物车中已购买的商品
				deleteHaveBuyGoods(goods, request, response);
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg",ErrorCode.Success.getErrorMsg());
				model.put("orderId", orderId);
				model.put("orderNo", objOrderResVO.getOrderNo());
				
				//查询商品上传身份证状态
				boolean checkUploadIdCard = false;
//				Integer checkUploadIdType= 2; //默认是不需要返回
				for(String goodsSn : goodsSnList) {
					int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(goodsSn);
//					if(uploadIdCard<checkUploadIdType){
//						checkUploadIdType=uploadIdCard;
//					}
					if(uploadIdCard == 0 || uploadIdCard == 1) {
						checkUploadIdCard = true;
						break;
					}
				}
				
				if(checkUploadIdCard){
					 //判断用户收货地址中是否上传了身份证
					QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
					addressDetailInParam.setAddressId(addressId);
					SessionUtil.setDeviceInfo(request, addressDetailInParam);
					AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam); //查询收货地址信息
					
					//addressId加密(AES)
					addressId=EncryptUtils.encryptByAES(addressId, aesKey);
					
					Long identityId = addressOutParam.getIdentityId();
					if(identityId != null && identityId.longValue() != 0 ){
						//如果地址的身份信息ID不为空
						IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
//						if(identityInfoDTO.getIdNumber()!=null){
//							checkUploadIdType=2;
//						}
						if(identityInfoDTO != null) {
							// 审核状态 reviewState{0 - 待审核, 1 - 审核通过, 2 - 审核不通过}
							Integer reviewState = identityInfoDTO.getReviewState();
							//  审核通过和待审核状态的身份证，则显示支付成功页面  否则提示需要上传身份证
							if(reviewState == 2) {
								model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?orderId=" + orderId+"&addressId="+addressId);
								model.put("uploadIdCardStatus", false);
							} else {
								model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_URL + "?orderId=" + orderId);
								model.put("uploadIdCardStatus", true);
							}
						} else {
							model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?orderId=" + orderId+"&addressId="+addressId);
							model.put("uploadIdCardStatus", false);
						}
					} else {
						model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?orderId=" + orderId+"&addressId="+addressId);
						model.put("uploadIdCardStatus", false);
					}
					
				 }else{
					//若不是全球速递商品，就不用上传身份证
					model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_URL+ "?orderId=" + orderId);
					model.put("uploadIdCardStatus", true);
				}
				
				// 根据商品信息是否为全球速递信息
				if(Double.parseDouble(calcOrder.getLeftAmount())==0){
					model.put("paySuccessStatus", true);
				}else{
					model.put("paySuccessStatus", false);
					model.put("paySuccessUrl","");
				}
//				model.put("goodAreaType", checkUploadIdType);
			} catch (EIBusinessException e) {
				model.put("result", false);
				model.put("errorCode",e.getExtErrCode());//ErrorCode.CreateOrderFailed.getErrorCode()
				model.put("errorMsg",e.getExtMessage());//ErrorCode.CreateOrderFailed.getErrorMsg()
				logger.error("生成订单时发生异常EIBusinessException：" + e.getExtErrCode()+e.getExtMessage(),e);
			}	
		}catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取订单商品列表时发生异常：" + e.getMessage(),e);
		}
		logger.info("创建订单返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback,model);
	}
	
	
	/***
	 * 验证优惠券信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateCardCoupon" , produces = "text/html;charset=UTF-8")
	public String validateCardCoupon(HttpServletRequest request,HttpServletResponse response,String jsoncallback) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if(!checkLogin( request)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				result.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 优惠券号、走秀码、商品数量
			String cardCode = request.getParameter("cardCode");
			// 商品信息的json串
			String goods = request.getParameter("goods");
			if (StringUtils.isEmpty(cardCode) || StringUtils.isEmpty(goods) || goods.equals("") || goods.equals("[]")) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			// 转换成商品列表信息
			List<OrderGoodsVO> goodsVOList = new ArrayList<OrderGoodsVO>();
			JSONArray jsonArray = JSONArray.fromObject(goods);
			for (int i = 0; i < jsonArray.size(); i++) {
				net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
				/**商品SKU(尺寸、颜色)*/
				String goodsSn = jsonObject.getString("goodsSn");
				/**购买数量*/
				int quantity = jsonObject.getInt("quantity");
				OrderGoodsVO orderGoodsVO = new OrderGoodsVO();
				orderGoodsVO.setGoodsSn(goodsSn);
				orderGoodsVO.setGoodsQuantity(quantity);
				goodsVOList.add(orderGoodsVO);
			}
			String channelId = GlobalConstants.CHANNEL_ID;
			Map<String, Object> validateResult = couponService.validateCardCoupon(cardCode, Long.parseLong(user.getUserId()), channelId, goodsVOList);
			// 如果验证成功
			if (Boolean.parseBoolean(validateResult.get("result").toString())) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				Map<String, Object> cardCouponStatusMap = CardCouponConstant.getCardCouponStatusList();
				result.put("message", cardCouponStatusMap.get(validateResult.get("status")));
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		logger.info("验证优惠券返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/**
	 * 计算应付总额 商品总额+运费
	 * @param amount
	 * @param freight
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String calculateAmount(String amount, String freight) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format((Double.valueOf(amount) + Double.valueOf(freight)));
	}
	
	/**
	 * 转换计算后的价格 <功能详细描述>
	 * @param goodsList
	 * @param calculateOrderResVO
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public List<OrderGoodsVO> priceFormat(List<OrderGoodsVO> goodsList, CalculateOrderBo calculateOrderResVO) {
		// 计算后价格转换
		for (OrderGoodsVO orderGoods : calculateOrderResVO.getGoodsList()) {
			for (OrderGoodsVO goods : goodsList) {
				if (goods.getGoodsSn().equals(orderGoods.getGoodsSn())) {
					DecimalFormat df = new DecimalFormat("0.00");
					double zsPrice = (Double.valueOf(orderGoods.getZsPrice()) / 100);
					double discountPrice = (Double.valueOf(orderGoods.getDiscountPrice()) / 100);
					String zprice = df.format(zsPrice);
					String dprice = df.format(discountPrice);
					goods.setZsPrice(zprice);
					goods.setDiscountPrice(dprice);
				}

			}
		}
		return goodsList;
	}
	
	/**
	 * CPS或媒体引入的用户
	 */
	private void handleCPSAndMeida(HttpServletRequest request, OrderReqVO orderReqVO) {
		CPSCookieVo cpsVo = CPSCookieUtils.getInstance().readCookieValueToCPS(request);
		String cpsType = cpsVo.getCps_type();
		if (!Strings.isNullOrEmpty(cpsType)) {
			int cpsTypeInt = Integer.parseInt(cpsType);
			String fromId = null;
			String cpsId = null;
			// 走媒体下单;
			if ("1".equals(cpsType)) {
				fromId = cpsVo.getFromid();// CookieUtil.readCookieValue(RequestUtil.getHttpServletRequest(this.getCommandContext()), OrderCookieConstants.COOKIE_NAME_FROMID);
				if (!Strings.isNullOrEmpty(fromId)) {
					mediaProcess(request,fromId, cpsTypeInt, orderReqVO);
				}
			} else if ("0".equals(cpsType)) {
				// CPS
				cpsId = cpsVo.getCps();// CookieUtil.readCookieValue(RequestUtil.getHttpServletRequest(this.getCommandContext()), OrderCookieConstants.COOKIE_NAME_CPS);
				if (!Strings.isNullOrEmpty(cpsId)) {
					cpsProcess(request,cpsId, cpsTypeInt, orderReqVO);
				}
			}
		}
	}

	/**
	 * 下单的时候调用接口: http://media.xiu.com/zs_media_from.php?act=query&user_id=123
	 * 返回: {"media_id":123,"ad_pos_id":123,"media_name":"name"}
	 * 
	 * 返回值： media_id ：媒体ID 也就是form_id的前四位<br/>
	 * ad_pos_id：广告位ID 也就是form_id的后两位<br/>
	 * media_name: 媒体名称
	 * 
	 * 媒体处理.
	 * 
	 * @param fromId
	 */
	private void mediaProcess(HttpServletRequest request,String fromId, int cpsType, OrderReqVO orderReqVO) {
		try {
			String userId = SessionUtil.getUserId(request);
			String content = HttpClientUtil.sendHttpClientMsg(XiuConstant.MEDIA_URL + userId);
			orderReqVO.setCpsType(cpsType);
			
			if (!Strings.isNullOrEmpty(content)) {
				JSONObject obj = JSONObject.parseObject(content);
				String mediaId = obj.getString("media_id");
				String resourceMediaId = fromId.length() > 4 ? fromId.substring(0, 4) : fromId;
				if (mediaId.equals(resourceMediaId)) {
					orderReqVO.setMediaId(fromId);
					orderReqVO.setAdPosId(obj.getString("ad_pos_id"));
					orderReqVO.setMediaName(obj.getString("media_name"));
				}
			} else {
				orderReqVO.setMediaId(fromId);
			}
		} catch (Exception ex) {
			Logger logger = Logger.getLogger(ShoppingCartController.class);
			logger.error("mediaProcess() ==> " + ex.getMessage());
		}
	}

	/**
	 * cps处理
	 * @param cps
	 */
	private void cpsProcess(HttpServletRequest request,String cpsId, int cpsType, OrderReqVO orderReqVO) {
		CPSCookieVo cpsVo = CPSCookieUtils.getInstance().readCookieValueToCPS(request);
		orderReqVO.setCpsId(cpsId);
		orderReqVO.setCpsType(cpsType);
		orderReqVO.setA_id(cpsVo.getA_id());
		orderReqVO.setU_id(cpsVo.getU_id());
		orderReqVO.setW_id(cpsVo.getW_id());
		orderReqVO.setBid(cpsVo.getBid());
		orderReqVO.setUid(cpsVo.getUid());
		orderReqVO.setAbid(cpsVo.getAbid());
		orderReqVO.setCid(cpsVo.getCid());
	}
	
	/***
	 * List转换成String 采用List默认会有[]及字符直接有空格
	 * @param stringList
	 * @return
	 */
	public static String goodsSnListToString(List<ShoppingCart> shoppingCartList) {
		Set<String> setList = new HashSet<String>(); 
		for (ShoppingCart shoppingCart : shoppingCartList) {
			setList.add(shoppingCart.getGoodsSn());
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : setList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}
	
	/***
	 * set转换成String 采用Set默认会有[]及字符直接有空格
	 * @param stringList
	 * @return
	 */
	public static String listToString(Set<String> stringSet) {
		if (stringSet == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringSet) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}
	
	/**
	 * 创建订单成功后  清理购物车中已购买的商品
	 */
	public void deleteHaveBuyGoods(String goods,HttpServletRequest request,HttpServletResponse response)  {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getUserId());
		// 获取当前用户下购物车里商品
		List<ShoppingCart> shoppingCartList = shoppingCartService.getGoodsList(params);
		Map<String, ShoppingCart> cartMap = new HashMap<String, ShoppingCart>();
		for (ShoppingCart shoppingCart : shoppingCartList) {
			cartMap.put(shoppingCart.getGoodsSku(), shoppingCart);
		}
		// 转换成商品列表信息
		JSONArray jsonArray = JSONArray.fromObject(goods);
		for (int i = 0; i < jsonArray.size(); i++) {
			net.sf.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
			/**商品SKU(尺寸、颜色)*/
			String goodsSku = jsonObject.getString("goodsSku");
			/**购买数量*/
			int quantity = jsonObject.getInt("quantity");
			if (cartMap.containsKey(goodsSku)) {
				ShoppingCart shoppingCart = cartMap.get(goodsSku);
				// 如果现在存储的购物车商品商量大于刚刚购买商品数量 则减少购物车商品数量  否则删除购物车中此商品信息
				if (shoppingCart.getQuantity()>quantity) {
					shoppingCart.setQuantity(shoppingCart.getQuantity()-quantity);
					shoppingCartService.updateGoods(shoppingCart);
				}else{
					shoppingCartService.delGoodsById(shoppingCart.getId());
				}
			}
		}
		
		// 设置为选中
		params.put("checked", "Y");
		// 选中购物车所有商品
		shoppingCartService.updateCheckedAll(params);
		
	}
	
	/**
	 * 处理商品是否支持礼品包装和是否限购
	 * @param goodsList
	 */
	public void handleProductSupportAndLimitSale(List<ShoppingCartGoodsVo> goodsList) {
		if(goodsList != null && goodsList.size() > 0) {
			//增加是否支持礼品包装和是否限购
			String packagingPrice = ""; //包装价格
			String packagingGoodsId = ""; //礼品包装商品ID
			List<String> goodsSns=new ArrayList<String>();
			for (ShoppingCartGoodsVo sgood:goodsList) {
				goodsSns.add(sgood.getGoodsSn());
			}
			//批量获取商品是否支持礼品包装
		    Map<String,Boolean> isSupportPackagingMap = goodsManager.isProductSupportWrapBySnList(goodsSns);
		    
			for(ShoppingCartGoodsVo goods : goodsList) {
				boolean supportPackaging = false;
				if(isSupportPackagingMap.get(goods.getGoodsSn())!=null){
					supportPackaging=isSupportPackagingMap.get(goods.getGoodsSn());
				}
				//礼品包装
//				supportPackaging = goods.isSupportPackaging();
				goods.setSupportPackaging(supportPackaging);
				if(supportPackaging) {
					//如果支持礼品包装
					if(StringUtils.isEmpty(packagingPrice)) {
						//查询包装价格
						packagingPrice = goodsManager.getProductPackagingPrice();
					}
					goods.setPackagingPrice(packagingPrice);
					
					//设置礼品包装商品ID
					if(StringUtils.isEmpty(packagingGoodsId)) {
						Map packagingGoodsMap = goodsManager.getProductPackagingGoods();
						packagingGoodsId = (String) packagingGoodsMap.get("goodsId");
					}
					goods.setPackagingGoodsId(packagingGoodsId);
				}
				
				//限购
//				int limitSaleNum = goodsManager.getGoodsLimitSaleNum(goodsSn);
//				goods.setLimitSaleNum(limitSaleNum);
			}
		}
	}
	
}
