package com.xiu.mobile.portal.controller;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.BaseException;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.core.utils.XiuUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.OrderQueryTypeConstant;
import com.xiu.mobile.portal.common.constants.PayTypeConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.model.CPSCookieVo;
import com.xiu.mobile.portal.common.util.CPSCookieUtils;
import com.xiu.mobile.portal.common.util.CookieUtil;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.HttpClientUtil;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.AddressOutParam;
import com.xiu.mobile.portal.model.CalculateOrderBo;
import com.xiu.mobile.portal.model.CancelOrderVO;
import com.xiu.mobile.portal.model.CheckRepeatedRespVo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.OrderGoodsVO;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.OrderReqVO;
import com.xiu.mobile.portal.model.OrderResVO;
import com.xiu.mobile.portal.model.PayInfoVO;
import com.xiu.mobile.portal.model.PayMethodInParam;
import com.xiu.mobile.portal.model.PayReqVO;
import com.xiu.mobile.portal.model.QueryUserAddressDetailInParam;
import com.xiu.mobile.portal.model.UserPayPlatform;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.ICPSService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IOrderService;
import com.xiu.mobile.portal.service.IProductService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.mobile.portal.service.ISysParamService;
import com.xiu.mobile.portal.service.IUserPayPlatformService;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 订单处理
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年5月14日 上午11:01:29
 * </p>
 ****************************************************************
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	private Logger logger = Logger.getLogger(OrderController.class);
	
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ISysParamService sysParamService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IUserPayPlatformService userPayInfoService;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private IProductService productServiceImpl;
	@Autowired
	private ICPSService cpSService;
	
	
	private Integer page = 1;
	private Integer pageSize = Integer.MAX_VALUE;
	
	/**
	 * 计算订单
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "calcOrder")
	public String calcOrder(HttpServletRequest request,HttpServletResponse response,
			String jsoncallback)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			String tokenId = SessionUtil.getTokenId(request);
			/**商品编码*/
			String goodsSn = request.getParameter("goodsSn");
			/**商品SKU(尺寸、颜色)*/
			String goodsSku = request.getParameter("goodsSku");
			/**购买数量*/
			int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
			/**优惠券号*/
			String couponCode=request.getParameter("couponCode");
			/**是否使用虚拟账户中可用余额 */
			String isVirtualPay=request.getParameter("isVirtualPay");
			if(null==isVirtualPay||"".equals(isVirtualPay)){
				isVirtualPay="0";//0不使用虚拟账户支付,1使用
			}
			/**是否使用礼品包装服务*/
			String useProductPackaging = request.getParameter("useProductPackaging");
			if(StringUtils.isEmpty(useProductPackaging) || !useProductPackaging.equals("1")) {
				useProductPackaging = "0";
			}
			if(useProductPackaging.equals("1")) {
				//如果使用了礼品包装，商品购买数量改为1
				quantity = 1;
			}
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			
			List<OrderGoodsVO> orderGoodsList = goodsManager.getOrderGoodsList(goodsSn, goodsSku, quantity, getDeviceParams(request));

			if (null != orderGoodsList && orderGoodsList.size() > 0) {
				String userId = SessionUtil.getUserId(request);
				//判断商品是否限购
				for(OrderGoodsVO goodsVO : orderGoodsList) {
					Map<String,Object> limitMap = new HashMap<String,Object>();
					limitMap.put("goodsSn", goodsVO.getGoodsSn());
					limitMap.put("quantity", quantity);
					Map limitResultMap = orderService.getOrderLimitSaleInfo(limitMap); //查询限购信息
					boolean limitResult = (Boolean) limitResultMap.get("result");
					if(limitResult) {
						//如果没限购
						int limitSaleNum = (Integer) limitResultMap.get("limitSaleNum");
						model.put("limitSaleNum", limitSaleNum);
					} else {
						model.put("result", limitResult);
						model.put("errorCode", limitResultMap.get("errorCode"));
						model.put("errorMsg", limitResultMap.get("errorMsg"));
						ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
						return null;
					}
				}
				
				if(useProductPackaging.equals("1")) {
					//如果使用了礼品包装服务，则添加礼品包装商品
					OrderGoodsVO orderGoods = orderService.getOrderPackagingGoods();
					if(orderGoods != null) {
						orderGoodsList.add(orderGoods);
					}
				}
				
				
				
				// 计算订单
				OrderReqVO calcOrderReqVO = new OrderReqVO();
				calcOrderReqVO.setTokenId(tokenId);
				calcOrderReqVO.setGoodsList(orderGoodsList); // 购物车商品列表
				SessionUtil.setDeviceInfo(request, calcOrderReqVO);
				calcOrderReqVO.setCouponNumber(couponCode);//优惠券号
				calcOrderReqVO.setUserId(loginResVo.getUserId());//用户Id
				calcOrderReqVO.setIsVirtualPay(isVirtualPay);//是否使用虚拟账户中可用余额
				calcOrderReqVO.setDeviceParams(getDeviceParams(request));
				calcOrderReqVO.setUseProductPackaging(useProductPackaging);
				calcOrderReqVO.setGoodsSn(goodsSn);
				CalculateOrderBo calcOrder = orderService.calcOrder(calcOrderReqVO);
				
				//优惠券活动Id
				List<String> couponActIds=calcOrder.getActivityList();
				//是否能使用优惠券
				boolean canUseCoupon=orderService.canUserCoupon(goodsSn);
				Double goodsMktPrice=0.00;
				for(OrderGoodsVO orderGoods:calcOrder.getGoodsList()){
					if(goodsSn.equals(orderGoods.getGoodsSn())){
						//订单参加促销活动后商品的购买价
						goodsMktPrice=Double.parseDouble(orderGoods.getDiscountPrice())/100.0;
						break;
					}
				}
				
				//查询发票数据
//				List<OrderInvoiceVO> invoiceList=orderService.getInvoiceTypeList(null);
//				model.put("invoiceList",invoiceList);
				
				//身份证上传强制标识
				int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(goodsSn); 
				model.put("goodAreaType",uploadIdCard);
				
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				//订单参加促销活动后商品的购买价
				model.put("goodsMktPrice", XiuUtil.getPriceDouble2Str(goodsMktPrice));
				model.put("mktActInfoList", calcOrder.getMktActInfoList());
//					model.put("mktGiftList", calcOrder.getMktGiftList());
				// 查询用户支付记录信息
				UserPayPlatform userPayInfo = userPayInfoService.get(loginResVo.getUserId());
				if (userPayInfo != null) {
					model.put("payPlatform", userPayInfo.getPayPlatform());
				}else{
					model.put("payPlatform", "");
				}
				//计算订单金额
				model.put("amount", getPrice(calcOrder.getAmount()));
				model.put("goodsAmount", getPrice(calcOrder.getGoodsAmount()));
				model.put("freight", getPrice(calcOrder.getFreight()));
				model.put("promoAmount", getPrice(calcOrder.getPromoAmount()));
				model.put("vtotalAmount",getPrice(calcOrder.getVtotalAmount()));
				model.put("vpayAmount",getPrice(calcOrder.getVpayAmount()));
				model.put("totalAmount",getPrice(calcOrder.getTotalAmount()));
				model.put("leftAmount",getPrice(calcOrder.getLeftAmount()));
				model.put("packagingAmount",getPrice(calcOrder.getPackagingAmount()));
				model.put("supportPackaging",calcOrder.getSupportPackaging());
				model.put("packagingPrice",calcOrder.getPackagingPrice());
				model.put("salesActIds", couponActIds);
				model.put("canUseCoupon", canUseCoupon);
				
				//是否满足订单多笔支付
				model.put("orderPayConfig", orderService.getOrderPayConfig(
						Double.parseDouble(calcOrder.getTotalAmount())));
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
				model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("计算订单时发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (BaseException e) {
			model.put("result", false);
			model.put("errorCode", e.getErrCode());
			model.put("errorMsg", e.getMessage());
			logger.error("计算订单时发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("计算订单时发生异常：" + e.getMessage(), e);
		}

		logger.info("计算订单返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	/**
	 * 创建订单（新）
	 */
	@RequestMapping(value = "createOrder")
	public String createOrder(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		logger.info("用户创建订单参数params="+request.getQueryString());
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String tokenId = SessionUtil.getTokenId(request);
		
		/**商品编码*/
		String goodsSn = request.getParameter("goodsSn");
		/**商品SKU(尺寸、颜色)*/
		String goodsSku = request.getParameter("goodsSku");
//		/**发票抬头*/
//		String invoiceHeading =ObjectUtil.getString(request.getParameter("orderReqVO.invoiceHeading"),"无");
//		/**发票类型*/
//		String invoice = request.getParameter("orderReqVO.invoice");
		/**购买数量*/
		int quantity = NumberUtils.toInt(request.getParameter("quantity"), 1);
		if (quantity < 1) {
			quantity = 1;
		}
		/**优惠券号*/
		String couponCode=request.getParameter("couponCode");
		/**活动编号*/
		String activeId=request.getParameter("activeId");
		/**订单金额*/
//		String orderAmount=request.getParameter("orderAmount");
		/**客户端支付媒介*/
//		String payMedium = request.getParameter("payMedium");
		/**收货地址Id*/
		String addressId=request.getParameter("orderReqVO.addressId");
		/**分享用户编号*/
/*		String cpsFromId=CookieUtil.getInstance().readCookieValue(request, "m-cps-from-id");
		if(null==cpsFromId||"".equals(cpsFromId)){
			cpsFromId="";
		}
*/
		/**是否多次支付 */
		String isMutilPay = "1".equals(request.getParameter("isMutilPay")) ? "1" : "0";
		/**多笔支付 本次支付金额*/
		String reqAmount = request.getParameter("reqAmount");
		/**本次支付方式*/
		String payPlatform = request.getParameter("payPlatform");
		
		/**是否使用礼品包装服务*/
		String useProductPackaging = request.getParameter("useProductPackaging");
		if(StringUtils.isEmpty(useProductPackaging) || !useProductPackaging.equals("1")) {
			useProductPackaging = "0";
		}
		if(useProductPackaging.equals("1")) {
			//如果使用了礼品包装，商品购买数量改为1
			quantity = 1;
		}
		
		String cps="";
		//取cookie获取cps
		String xiukcps =CookieUtil.getInstance().readCookieValue(request, "kcpsxiu");
		String cpscode =CookieUtil.getInstance().readCookieValue(request, "cpscode");
		if(null!= xiukcps){
			cps = cpSService.queryCookiesInfo(xiukcps,  cpscode);
		}
		
		if(null==activeId||"".equals(activeId)){
			activeId="";
		}
		/**是否使用虚拟账户中可用余额 */
		String isVirtualPay=request.getParameter("isVirtualPay");
		if(null==isVirtualPay||"".equals(isVirtualPay)){
			isVirtualPay="0";//0不使用虚拟账户支付,1使用
		}
		// 从cookie中获得userId
		LoginResVo loginResVo = SessionUtil.getUser(request);
		
		try {
//			OrderListInParam orderListInParam = getOrderListInParamRemote(request,tokenId,
//					OrderQueryTypeConstant.ALL_COUNT, this.page, this.pageSize);
			//检验是否为重复订单
//			CheckRepeatedRespVo repOrder=CheckRepeateOrder(tokenId,orderListInParam,addressId,goodsSku,quantity,orderAmount);
//			if(repOrder.isRepeated()){
//				logger.info("此订单为重复订单，直接跳转到支付");
//				StringBuilder sbParams = new StringBuilder();
//				sbParams.append("&orderId=" + repOrder.getOrderId());
//				sbParams.append("&payPlatform=" + repOrder.getPayType());
//				sbParams.append("&payMedium=" + (StringUtils.isEmpty(payMedium) ? "web" : payMedium));
//				
//				logger.info("检查重复订单最后跳转路径参数值: "+sbParams.toString());
//				String forward = "forward:/order/payOrder.shtml?" + sbParams.toString();
//				return forward;
//			}else{
				//反之，创建订单
				List<OrderGoodsVO> orderGoodsList = null;
				try {
					orderGoodsList = goodsManager.getOrderGoodsList(goodsSn, goodsSku, quantity, getDeviceParams(request));
				} catch (EIBusinessException e) {
					model.put("result", false);
					model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
					model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
					ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
					logger.error("创建订单 并支付EIBusinessException"+e.getMessage(),e);
					return null;
				}
				
				if(orderGoodsList.size() == 0) {
					model.put("result", false);
					model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
					model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
					ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
					return null;
				}
				
				String userId = SessionUtil.getUserId(request);
				//判断商品的限购数量
				for(OrderGoodsVO orderGoodsVO : orderGoodsList) {
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
						ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
						return null;
					}
				}
				
				if(useProductPackaging.equals("1")) {
					//如果使用了礼品包装服务，则添加礼品包装商品
					OrderGoodsVO orderGoods = orderService.getOrderPackagingGoods();
					if(orderGoods != null) {
						for(OrderGoodsVO goods : orderGoodsList) {
							goods.setGiftPackType("2"); //非礼品包装商品
						}
						orderGoods.setGiftPackType("1"); //礼品包装类型
						orderGoodsList.add(orderGoods);
					}
				}
				
				// 计算订单
				OrderReqVO calcOrderReqVO = new OrderReqVO();
				calcOrderReqVO.setTokenId(tokenId);
				calcOrderReqVO.setGoodsList(orderGoodsList); // 购物车商品列表
				SessionUtil.setDeviceInfo(request, calcOrderReqVO);
				if(null!=couponCode&&!"".equals(couponCode)&&!"null".equals(couponCode)){
					calcOrderReqVO.setCouponNumber(couponCode);
				}
				calcOrderReqVO.setUserId(loginResVo.getUserId());//用户Id
				calcOrderReqVO.setIsVirtualPay(isVirtualPay);//是否使用虚拟账户中可用余额
				calcOrderReqVO.setUseProductPackaging(useProductPackaging); //是否使用礼品包装服务
				calcOrderReqVO.setGoodsSn(goodsSn);
				CalculateOrderBo calcOrder = orderService.calcOrder(calcOrderReqVO);
				
				OrderReqVO orderReqVO = new OrderReqVO();
				orderReqVO.setDeliverTime(request.getParameter("orderReqVO.deliverTime"));
				orderReqVO.setPaymentMethod(StringUtils.isEmpty(payPlatform) ? "AlipayWire" : payPlatform);//默认值支付宝网页版
				orderReqVO.setGoodsFrom(request.getParameter("orderReqVO.goodsFrom"));//
//				orderReqVO.setInvoice(invoice);
//				orderReqVO.setInvoiceHeading(invoiceHeading);
				
				orderReqVO.setIsMutilPay(isMutilPay);
				orderReqVO.setReqAmount(reqAmount);
				
				// addressId解密(AES)
				String aesKey = EncryptUtils.getAESKeySelf();
			    addressId=EncryptUtils.decryptByAES(addressId, aesKey);
				orderReqVO.setAddressId(addressId);
				
//				orderReqVO.setInvoice(request.getParameter("orderReqVO.invoice"));
				orderReqVO.setOrderSource(request.getParameter("orderReqVO.orderSource"));
				orderReqVO.setMessage(request.getParameter("orderReqVo.orderMsg"));
				
				orderReqVO.setTokenId(tokenId);
				
				orderReqVO.setGoodsList(priceFormat(orderGoodsList, calcOrder));
				orderReqVO.setUseProductPackaging(useProductPackaging); //是否包装服务  
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
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg",ErrorCode.Success.getErrorMsg());
					
					// orderId加密(AES)
					Long orderId =  Long.parseLong(EncryptUtils.encryptOrderIdByAES(String.valueOf(objOrderResVO.getOrderId()), aesKey));
					model.put("orderId",orderId);
					model.put("orderNo",objOrderResVO.getOrderNo());
					
					//查询商品对身份证上传的限制状态（0强制 1需要但不强制 2不需要）
					int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(goodsSn); 
					
					if(uploadIdCard == 0 || uploadIdCard == 1){
					 //判断用户收货地址中是否上传了身份证
					 QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
				 	 addressDetailInParam.setAddressId(addressId);
					 SessionUtil.setDeviceInfo(request, addressDetailInParam);
				 	 AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam); //查询收货地址信息
					
					 Long identityId = addressOutParam.getIdentityId();
					 if(identityId != null && identityId.longValue() != 0 ){
					 	//如果地址的身份信息ID不为空
					 	IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
					 	if(identityInfoDTO != null) {
//					 		if(identityInfoDTO.getIdNumber()!=null){//如果有身份证，则不需录入
//					 			uploadIdCard=2;
//					 		}
					 		
					 		// 审核状态 reviewState{0 - 待审核, 1 - 审核通过, 2 - 审核不通过}
							Integer reviewState = identityInfoDTO.getReviewState();
							//  审核通过和待审核状态的身份证，则显示支付成功页面  否则提示需要上传身份证
							if(reviewState == 2) {
								model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?orderId=" + objOrderResVO.getOrderId()+"&addressId="+addressId);
								model.put("uploadIdCardStatus", false);
							} else {
								model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_URL + "?orderId=" + objOrderResVO.getOrderId());
								model.put("uploadIdCardStatus", true);
							}
					  	} else {
					  		//若未上传身份证信息或上传的身份证审核不通过时，则显示支付成功页面2
							model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?orderId=" + objOrderResVO.getOrderId()+"&addressId="+addressId);
							model.put("uploadIdCardStatus", false);
					  	}
					 } else {
						//若未上传身份证信息或上传的身份证审核不通过时，则显示支付成功页面2
						model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?orderId=" + objOrderResVO.getOrderId()+"&addressId="+addressId);
						model.put("uploadIdCardStatus", false);
					 }
						
				 	}else{
						//若不是全球速递商品，就不用上传身份证
						model.put("paySuccessUrl", XiuConstant.PAY_SUCCESS_CALLBACK_URL+ "?orderId=" + objOrderResVO.getOrderId());
						model.put("uploadIdCardStatus", true);
					}
					
					if(Double.parseDouble(calcOrder.getLeftAmount())==0){
						model.put("paySuccessStatus", true);
					}else{
						model.put("paySuccessStatus", false);
						model.put("paySuccessUrl","");
					}
//					model.put("goodAreaType",uploadIdCard);//设置身份证上传限制类型
				} catch (EIBusinessException e) {
					model.put("result", false);
					model.put("errorCode",e.getExtErrCode());//ErrorCode.CreateOrderFailed.getErrorCode()
					model.put("errorMsg",e.getExtMessage());//ErrorCode.CreateOrderFailed.getErrorMsg()
					logger.error("生成订单时发生异常EIBusinessException：" + e.getExtErrCode()+e.getExtMessage(),e);
				}
//			}
			
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("获取订单商品列表时发生异常:" + e.getMessageWithSupportCode());
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取订单商品列表时发生异常：" + e.getMessage());
		}
		logger.info("创建订单返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
		return null;
	}
	
	
	/**
	 * 创建完订单后支付（新）
	 */
	@RequestMapping(value = "payOrder")
	public String payOrder(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		PayReqVO payReqVO = new PayReqVO();
		try {
			/**客户端支付平台*/
			String payMedium = request.getParameter("payMedium");
			/**订单Id */
			String oId =request.getParameter("orderId");
			String paymentMethod = request.getParameter("payPlatform");
			//多笔支付，用户输入的本次支付金额
			String reqAmount = request.getParameter("reqAmount");
			//客户所在ip
			String ordIp = HttpUtil.getRemoteIpAddr(request);
			//加密的paymentDate(applePay)
			String payMentInfo = request.getParameter("payMentInfo");
			
			logger.info("创建完订单后支付(新)参数：payMedium="
					+payMedium+"&orderId="+oId+"&payPlatform="+paymentMethod+"&reqAmount="+reqAmount);	
			
			if(PayTypeConstant.ALIPAYWIRE.equals(paymentMethod)
					|| PayTypeConstant.ALIPAY_WIRE_APP.equals(paymentMethod)
					|| PayTypeConstant.WECHAT.equals(paymentMethod)
					||PayTypeConstant.CHINAPAY_MOBILE_APP.equals(paymentMethod)
					||PayTypeConstant.CHINAPAY_MOBILE_WAP.equals(paymentMethod)
					||PayTypeConstant.WANLITONG_WAP.equals(paymentMethod)
					||PayTypeConstant.WECHAT_PRO.equals(paymentMethod)
					||PayTypeConstant.PAYEASE_APPLEPAY.equals(paymentMethod)
					){
			
			// orderId解密(AES)
			String aesKey = EncryptUtils.getAESKeySelf();
			oId = EncryptUtils.decryptOrderIdByAES(oId, aesKey);
			Integer orderId = Integer.valueOf(oId);
			
				// 现根据orderId查出订单信息
				payReqVO = orderService.queryOrderBaseInfo(orderId, payMedium);
				payReqVO.setReqAmount(reqAmount);
				if (null != payReqVO && null != payReqVO.getOrderId() && !"".equals(payReqVO.getOrderId())) {
					String encryOrderId = EncryptUtils.encryptOrderIdByAES(payReqVO.getOrderId(), EncryptUtils.getAESKeySelf());
					boolean uploadIdCardStatus = true;
					String callbackUrl = request.getParameter("callBackUrl");
					if(StringUtil.isBlank(callbackUrl)) {
						callbackUrl = XiuConstant.PAY_SUCCESS_CALLBACK_URL + "?orderId=" + encryOrderId;
					}
					//查询商品上传身份证状态 
					int uploadIdCard = goodsManager.getGoodsUploadIdCardByGoodsSn(payReqVO.getGoodsSn()); 
					// 只要一件商品包含 0:普通商品 ,1:直发, 2:全球速递, 3:香港速递  
					if (uploadIdCard == 0 || uploadIdCard == 1) {
						if (null != payReqVO.getAddressId() && !("").equals(payReqVO.getAddressId().toString())) {
							String addressId = payReqVO.getAddressId().toString();
							String encryAddressId = EncryptUtils.encryptByAES(addressId, EncryptUtils.getAESKeySelf());
							// 判断用户收货地址中是否上传了身份证
							QueryUserAddressDetailInParam addressDetailInParam = new QueryUserAddressDetailInParam();
						 	addressDetailInParam.setAddressId(addressId);
							SessionUtil.setDeviceInfo(request, addressDetailInParam);
						 	AddressOutParam addressOutParam = addressService.getAddress(addressDetailInParam); //查询收货地址信息
							
							Long identityId = addressOutParam.getIdentityId();
							if(identityId != null && identityId.longValue() != 0 ){
								//如果地址的身份信息ID不为空
							 	IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoById(identityId);
							 	if(identityInfoDTO != null) {
							 		if(identityInfoDTO.getIdNumber()!=null&&!identityInfoDTO.getIdNumber().equals("")){//如果有身份证，则为不用录入
							 			uploadIdCard=2;
							 		}
							 		// 审核状态 reviewState{0 - 待审核, 1 - 审核通过, 2 - 审核不通过}
									Integer reviewState = identityInfoDTO.getReviewState();
									//  审核通过和待审核状态的身份证，则显示支付成功页面  否则提示需要上传身份证
									if(reviewState == 2) {
										if(StringUtil.isBlank(callbackUrl)) {
											callbackUrl = XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?addressId=" + encryAddressId;
										}
										uploadIdCardStatus = false;
									} else {
										if(StringUtil.isBlank(callbackUrl)) {
											callbackUrl = XiuConstant.PAY_SUCCESS_CALLBACK_URL + "?orderId=" + encryOrderId;
										}
										uploadIdCardStatus = true;
									}
							  	} else {
							  		if(StringUtil.isBlank(callbackUrl)) {
							  			//若未上传身份证信息或上传的身份证审核不通过时，则显示支付成功页面2
							  			callbackUrl = XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?addressId=" + encryAddressId;
							  		}
									uploadIdCardStatus = false;
							    }
							} else {
								if(StringUtil.isBlank(callbackUrl)) {
									//若未上传身份证信息或上传的身份证审核不通过时，则显示支付成功页面2
									callbackUrl = XiuConstant.PAY_SUCCESS_CALLBACK_UPLOAD_IDCARD_URL + "?addressId=" + encryAddressId;
								}
								uploadIdCardStatus = false;
							}
							 
						}
					}
					
					payReqVO.setPayMedium(payMedium);
					payReqVO.setPayType(paymentMethod);
					
					String tokenId = SessionUtil.getTokenId(request);
					payReqVO.setTokenId(tokenId);
					payReqVO.setPayMedium(StringUtils.isEmpty(payReqVO.getPayMedium()) ? "web" : payReqVO.getPayMedium());
					payReqVO.setCallbackUrl(callbackUrl);
					if(PayTypeConstant.WANLITONG_WAP.equals(paymentMethod)) {
						String memberID = SessionUtil.getCookie(request, "wltMemberId"); //万里通用户ID
						String memberToken = SessionUtil.getCookie(request, "wltTokenId"); //万里通用户token
//						//测试数据
//						if(StringUtil.isBlank(memberID)) {
//							memberID = "010000164130524";
//						}
//						if(StringUtil.isBlank(memberToken)) {
//							memberToken = "98d353eb35bd1a8b3cec42bebfb8011a";
//						}
						payReqVO.setMemberID(memberID);
						payReqVO.setMemberToken(memberToken);
						logger.info("万里通支付参数：memberID="+memberID+",memberToken="+memberToken);
					}
					// 获取设备信息 并重新赋值userId为订单userId
					SessionUtil.setDeviceInfo(request, payReqVO);
					payReqVO.setUserId(payReqVO.getLoginId().toString());
					payReqVO.setOrdIp(ordIp);
					payReqVO.setPayMentInfo(payMentInfo);
					// 发起支付
					PayInfoVO payInfoVO = orderService.pay(payReqVO);
					if (null != payInfoVO) {
						//记录用户支付方式
						userPayInfoService.recordInfo(SessionUtil.getUserId(request), paymentMethod);
						model.clear();
						model.put("result", true);
						model.put("errorCode", ErrorCode.Success.getErrorCode());
						model.put("errorMsg", ErrorCode.Success.getErrorMsg());							
						model.put("payInfoVO", payInfoVO);
						// orderId加密(AES)
						oId = EncryptUtils.encryptOrderIdByAES(orderId.toString(), aesKey);
						String addressId = EncryptUtils.encryptByAES(payReqVO.getAddressId().toString(), aesKey);
						model.put("orderId", oId);
						model.put("addressId", addressId);
						model.put("orderNo", payReqVO.getOrderNo());
						model.put("uploadIdCardStatus", uploadIdCardStatus);
						model.put("uploadIdCardType",uploadIdCard);//是否需要录入身份证 0需要 1建议 2不用
						logger.info("发起支付成功url：" +URLDecoder.decode(URLDecoder.decode(payInfoVO.getPayInfo(), "utf-8"), "utf-8"));
						logger.info("发起支付成功：" + JSON.toJSONString(payInfoVO));
					} else {
						model.put("result", false);
						model.put("errorCode", ErrorCode.PayFailed.getErrorCode());
						model.put("errorMsg", ErrorCode.PayFailed.getErrorMsg());
					}
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
					model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.PayFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.PayFailed.getErrorMsg());
			logger.error("发起支付时发生异常EIBusinessException：" + e.getMessage(),e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("发起支付发生异常：" + e.getMessage(),e);
		}
		logger.info("支付返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	
	/***
	 * 代支付 支付他人订单
	 * update 20150427：转移到PayForOthersController中处理
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/payOrderForOthers")
	public String payOrderForOthers(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		PayReqVO payReqVO = new PayReqVO();
		try {
				
			/**客户端支付平台*/
			String payMedium = request.getParameter("payMedium");
			/**订单Id */
			String oId =request.getParameter("orderId");
			String paymentMethod = request.getParameter("payPlatform");
			
			if(PayTypeConstant.ALIPAYWIRE.equals(paymentMethod)
					|| PayTypeConstant.ALIPAY_WIRE_APP.equals(paymentMethod)
					|| PayTypeConstant.WECHAT.equals(paymentMethod)
					||PayTypeConstant.CHINAPAY_MOBILE_APP.equals(paymentMethod)
					||PayTypeConstant.CHINAPAY_MOBILE_WAP.equals(paymentMethod)
					||PayTypeConstant.WANLITONG_WAP.equals(paymentMethod)){
			
				// orderId解密(AES)
				String aesKey = EncryptUtils.getAESKeySelf();
				oId = EncryptUtils.decryptOrderIdByAES(oId, aesKey);
				Integer orderId = Integer.valueOf(oId);

				// 现根据orderId查出订单信息
				payReqVO = orderService.queryOrderBaseInfo(orderId, payMedium);
				if (null != payReqVO && null != payReqVO.getOrderId() && !"".equals(payReqVO.getOrderId())) {
					payReqVO.setPayMedium(payMedium);
					payReqVO.setPayType(paymentMethod);
					
					String tokenId = SessionUtil.getTokenId(request);
					payReqVO.setTokenId(tokenId);
					payReqVO.setPayMedium(StringUtils.isEmpty(payReqVO.getPayMedium()) ? "web" : payReqVO.getPayMedium());
					String callbackUrl = XiuConstant.PAY_SUCCESS_CALLBACK_FOR_OTHER_URL;
					
					payReqVO.setCallbackUrl(callbackUrl);
					// 获取设备信息 并重新赋值userId为订单userId
					SessionUtil.setDeviceInfo(request, payReqVO);
					payReqVO.setUserId(payReqVO.getLoginId().toString());
					// 发起支付
					PayInfoVO payInfoVO = orderService.pay(payReqVO);
					if (null != payInfoVO) {
						model.clear();
						model.put("result", true);
						model.put("errorCode", ErrorCode.Success.getErrorCode());
						model.put("errorMsg", ErrorCode.Success.getErrorMsg());							
						model.put("payInfoVO", payInfoVO);
						logger.info("代支付成功url：" +URLDecoder.decode(URLDecoder.decode(payInfoVO.getPayInfo(), "utf-8"), "utf-8"));
						logger.info("代支付成功：" + JSON.toJSONString(payInfoVO));
					} else {
						model.put("result", false);
						model.put("errorCode", ErrorCode.PayFailed.getErrorCode());
						model.put("errorMsg", ErrorCode.PayFailed.getErrorMsg());
					}
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
					model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.UnsupportPayMethod.getErrorCode());
				model.put("errorMsg", ErrorCode.UnsupportPayMethod.getErrorMsg());
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.PayFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.PayFailed.getErrorMsg());
			logger.error("发起支付时发生异常EIBusinessException：" + e.getMessage(),e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("发起支付发生异常：" + e.getMessage(),e);
		}
		logger.info("支付返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 代付（新）
	 * 
	 */
	@RequestMapping(value="payForOthers")
	public String payForOthers(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		//点击代付按钮或复制链接按钮，返回代付页面url及数据
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		PayReqVO payReqVO=new PayReqVO();
		try{
			/**订单Id */
			int orderId =Integer.parseInt(request.getParameter("orderId"));
			/**客户端支付平台*/
			String payMedium = request.getParameter("payMedium");
			payReqVO = orderService.queryOrderBaseInfo(orderId,payMedium);
			if(null!=payReqVO&&null!=payReqVO.getOrderId()&&!"".equals(payReqVO.getOrderId())){
				//判断订单状态，不是待支付就链接无效
				if("待支付".equals(payReqVO.getOrderStatus())){
					String param="buyerName="+payReqVO.getLogonName()+"&orderAmount="+payReqVO.getOrderAmount()+"&orderNo="+payReqVO.getOrderNo();
					//加密
					param =  URLEncoder.encode(URLEncoder.encode(param, "UTF-8"), "UTF-8");
					String payForOthersUrl="http://m.xiu.com/payForOthers.html?"+param;
					model.clear();
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("payForOthersUrl", payForOthersUrl);
					logger.info("代付成功url："+ payForOthersUrl);
					logger.info("代付成功：" + JSON.toJSONString(payReqVO));
				}else{
					model.put("result", false);
					model.put("errorCode", ErrorCode.PayForOthersInvalid.getErrorCode());
					model.put("errorMsg", ErrorCode.PayForOthersInvalid.getErrorMsg());
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.PayForOthersFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.PayForOthersFailed.getErrorMsg());
			}
		}catch(Exception e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("代付发生异常：" + e.getMessage(),e);
		}
		logger.info("代付返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 创建订单 并支付
	 * 
	 */
	@RequestMapping(value = "createOrderAndPay")
	public String createOrderAndPay(String jsoncallback,HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String tokenId = SessionUtil.getTokenId(request);
		
		/**商品编码*/
		String goodsSn = request.getParameter("goodsSn");
		/**商品SKU(尺寸、颜色)*/
		String goodsSku = request.getParameter("goodsSku");
		/**购买数量*/
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		/**客户端支付平台*/
		String payMedium = request.getParameter("payMedium");
		/**优惠券号*/
		String couponCode=request.getParameter("couponCode");
		/**活动编号*/
		String activeId=request.getParameter("activeId");
		/**分享用户编号*/
		String cpsFromId=request.getParameter("cpsFromId");
		if(null==activeId||"".equals(activeId)||"null".equals(activeId)){
			activeId="";
		}
		if(null==cpsFromId||"".equals(cpsFromId)||"null".equals(cpsFromId)){
			cpsFromId="";
		}
		try {
			List<OrderGoodsVO> orderGoodsList = null;
			try {
				orderGoodsList = goodsManager.getOrderGoodsList(goodsSn, goodsSku, quantity, getDeviceParams(request));
			} catch (EIBusinessException e) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
				model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
				ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
				logger.error("创建订单 并支付EIBusinessException"+e.getMessage(),e);
				return null;
			}
			
			if(orderGoodsList.size() == 0) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.StockShortage.getErrorCode());
				model.put("errorMsg", ErrorCode.StockShortage.getErrorMsg());
				ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
				return null;
			}
			
			String paymentMethod = request.getParameter("orderReqVO.paymentMethod");
			if(PayTypeConstant.ALIPAYWIRE.equals(paymentMethod)
					|| PayTypeConstant.ALIPAY_WIRE_APP.equals(paymentMethod)
					|| PayTypeConstant.WECHAT.equals(paymentMethod)
					||PayTypeConstant.CHINAPAY_MOBILE_APP.equals(paymentMethod)
					||PayTypeConstant.CHINAPAY_MOBILE_WAP.equals(paymentMethod)){
				
				// 计算订单
				OrderReqVO calcOrderReqVO = new OrderReqVO();
				calcOrderReqVO.setTokenId(tokenId);
				calcOrderReqVO.setGoodsList(orderGoodsList); // 购物车商品列表
				SessionUtil.setDeviceInfo(request, calcOrderReqVO);
				if(null!=couponCode&&!"".equals(couponCode)&&!"null".equals(couponCode)){
					calcOrderReqVO.setCouponNumber(couponCode);
				}
				CalculateOrderBo calcOrder = orderService.calcOrder(calcOrderReqVO);
				
				OrderReqVO orderReqVO = new OrderReqVO();
				orderReqVO.setDeliverTime(request.getParameter("orderReqVO.deliverTime"));
				orderReqVO.setPaymentMethod(request.getParameter("orderReqVO.paymentMethod"));
				// addressId解密(AES)
				String addressId=request.getParameter("orderReqVO.addressId");
				String aesKey=EncryptUtils.getAESKeySelf();
				addressId=EncryptUtils.decryptByAES(addressId, aesKey);
				orderReqVO.setAddressId(addressId);			
				
//				orderReqVO.setInvoice(request.getParameter("orderReqVO.invoice"));
				orderReqVO.setOrderSource(request.getParameter("orderReqVO.orderSource"));
				
				orderReqVO.setTokenId(tokenId);
				orderReqVO.setGoodsList(priceFormat(orderGoodsList, calcOrder));
				if(null!=couponCode&&!"".equals(couponCode)&&!"null".equals(couponCode)){
					orderReqVO.setCouponNumber(couponCode);//优惠券关联订单
				}
				
				SessionUtil.setDeviceInfo(request, orderReqVO);
				// 处理cps或media信息
				// 如果是android或者ios，则不进行CPS处理 update 2015-05-19
				String orderSource = request.getParameter("orderReqVO.orderSource");
				if(!Strings.isNullOrEmpty(orderSource) && ("3".equals(orderSource) || "4".equals(orderSource) || "5".equals(orderSource)) ) {
					cpsFromId = "";
				} else {
					handleCPSAndMeida(request, orderReqVO);
				}
				
				// 生成订单
				OrderResVO objOrderResVO = null;
				try {
					objOrderResVO = orderService.createOrder(orderReqVO,activeId,cpsFromId);
					StringBuilder sbParams = new StringBuilder();
					sbParams.append("payReqVO.orderNo=" + objOrderResVO.getOrderNo());
					sbParams.append("&payReqVO.orderAmount=" + objOrderResVO.getOrderAmount());		
					// orderId加密(AES)
					String orderId=EncryptUtils.encryptOrderIdByAES(String.valueOf(objOrderResVO.getOrderId()), aesKey);
					
					sbParams.append("&payReqVO.orderId=" + orderId);
					sbParams.append("&payReqVO.payType=" + objOrderResVO.getPayType());
					sbParams.append("&payReqVO.payMedium=" + (StringUtils.isEmpty(payMedium) ? "web" : payMedium));
					if(null != jsoncallback){
						sbParams.append("&jsoncallback=");
						sbParams.append(jsoncallback);
					}
					String forward = "forward:/order/toPay.shtml?" + sbParams.toString();
					return forward;	
				} catch (EIBusinessException e) {
					model.put("result", false);
					model.put("errorCode",e.getExtErrCode());//ErrorCode.CreateOrderFailed.getErrorCode()
					model.put("errorMsg",e.getExtMessage());//ErrorCode.CreateOrderFailed.getErrorMsg()
					logger.error("生成订单时发生异常EIBusinessException：" + e.getExtErrCode()+e.getExtMessage(),e);
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.UnsupportPayMethod.getErrorCode());
				model.put("errorMsg", ErrorCode.UnsupportPayMethod.getErrorMsg());
			}
		}  catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("创建订单 并支付发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("创建订单 并支付发生异常：" + e.getMessage(),e);
		}
		logger.info("创建订单并支付返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
		return null;
	}

	/**
	 * 
	 * 更新支付方式
	 */
	@ResponseBody
	@RequestMapping(value = "updatePayMethod")
	public String updatePayMethod(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		
		String orderId = request.getParameter("payMethodIn.orderId");
		String payType = request.getParameter("payMethodIn.payType");
		
		try {
			
			PayMethodInParam payMethodIn = new PayMethodInParam();
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			orderId=EncryptUtils.decryptOrderIdByAES(orderId, aesKey);
			
			payMethodIn.setOrderId(orderId);
			payMethodIn.setPayType(payType);
			
			String tokenId = SessionUtil.getTokenId(request);
			SessionUtil.setDeviceInfo(request, payMethodIn);
			payMethodIn.setTokenId(tokenId);
			payMethodIn.setLogonName(SessionUtil.getUserName(request));
			boolean result = orderService.updatePayMethod(payMethodIn);
			if (result) {
				model.put("result", result);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				model.put("result", result);
				model.put("errorCode", ErrorCode.UpdatePayMethodFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.UpdatePayMethodFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("修改支付方式时发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("修改支付方式时发生异常：" + e.getMessage(), e);
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 支付
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "toPay")
	public String toPay(HttpServletRequest request,HttpServletResponse response)  {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		
		String orderNo = request.getParameter("payReqVO.orderNo");
		String orderId = request.getParameter("payReqVO.orderId");
		String payType = request.getParameter("payReqVO.payType");
		String payMedium = request.getParameter("payReqVO.payMedium");
		String jsoncallback = request.getParameter("jsoncallback");
		String memberID = SessionUtil.getCookie(request, "MemberID"); //万里通用户ID
		String memberToken = SessionUtil.getCookie(request, "MemberToken"); //万里通用户token
		
		try {
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			orderId=EncryptUtils.decryptOrderIdByAES(orderId, aesKey);
			
			PayReqVO payReqVO0 = orderService.queryOrderBaseInfo(Integer.parseInt(orderId),payMedium);
			PayReqVO payReqVO= new PayReqVO();
			payReqVO.setOrderNo(orderNo);
			payReqVO.setOrderAmount (payReqVO0.getOrderAmount()) ;
			payReqVO.setOrderId(orderId);
			payReqVO.setPayType(payType);
			payReqVO.setPayMedium(payMedium);
			payReqVO.setMemberID(memberID);
			payReqVO.setMemberToken(memberToken);
			
			String tokenId = SessionUtil.getTokenId(request);
			payReqVO.setTokenId(tokenId);
			payReqVO.setPayMedium(StringUtils.isEmpty(payReqVO.getPayMedium()) ? "web" : payReqVO.getPayMedium());
			payReqVO.setCallbackUrl(XiuConstant.PAY_SUCCESS_CALLBACK_URL + "?orderId=" + payReqVO.getOrderId());
			SessionUtil.setDeviceInfo(request, payReqVO);
			// 发起支付
			PayInfoVO payInfoVO = orderService.pay(payReqVO);
			if (null != payInfoVO) {
				model.clear();
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				model.put("payInfoVO", payInfoVO);
				
				// orderId加密(AES)
				//String aesKey=EncryptUtils.getAESKeySelf();
				orderId=EncryptUtils.encryptOrderIdByAES(orderId, aesKey);
				model.put("orderId", orderId);					
				model.put("orderNo", orderNo);
				logger.info("支付成功url：" +URLDecoder.decode(URLDecoder.decode(payInfoVO.getPayInfo(), "utf-8"), "utf-8"));
				logger.info("发起支付成功：" + JSON.toJSONString(payInfoVO));
	
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.PayFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.PayFailed.getErrorMsg());
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.PayFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.PayFailed.getErrorMsg());
			logger.error("发起支付时发生异常EIBusinessException：" + e.getMessage(),e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("发起支付发生异常：" + e.getMessage(),e);
		}
		logger.info("支付返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	/**
	 * 撤销订单
	 */
	@ResponseBody
	@RequestMapping(value = "cancelOrder")
	public String cancelOrder(HttpServletRequest request,HttpServletResponse response,String jsoncallback)  {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try {
			
			CancelOrderVO cancelVo = new CancelOrderVO();
			String orderId = request.getParameter("cancelVo.orderId");
			Assert.notNull(orderId,"撤销订单，订单orderId不能为空.");
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			orderId=EncryptUtils.decryptOrderIdByAES(orderId, aesKey);
			
			cancelVo.setOrderId(Integer.parseInt(orderId));
			
			// 组装取消订单参数
			cancelVo.setUserId(new Long(SessionUtil.getUserId(request)));
			cancelVo.setUserName(SessionUtil.getUserName(request));
			cancelVo.setIp(HttpUtil.getRemoteIpAddr(request));
			cancelVo.setReason("default");
			if (orderService.cancelOrder(cancelVo)) {
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.CancelOrderFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.CancelOrderFailed.getErrorMsg());
			logger.error("撤销订单时发生异常EIBusinessException：" + e.getMessage(),e);
		}catch (Exception e) {
			model.clear();
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("撤销订单时发生异常：" + e.getMessage(),e);
		}
		logger.info("撤单返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 创建或者支付订单
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "createOrderAndPay2")
	public String createOrderAndPay2(String jsoncallback,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		CheckRepeatedRespVo checRespVo=null;
		
		/**商品编码*/
		String goodsSn = request.getParameter("goodsSn");
		/**商品SKU(尺寸、颜色)*/
		String goodsSku = request.getParameter("goodsSku");
		/**购买数量*/
		int quantity = Integer.valueOf(request.getParameter("quantity"));
		/**客户端支付平台*/
		String payMedium = request.getParameter("payMedium");
		/**支付方式**/
		String payMethod = request.getParameter("orderReqVO.paymentMethod");
		/**送货时间**/
		String deliverTime = request.getParameter("orderReqVO.deliverTime");
		/**收货地址id**/
		String addressId = request.getParameter("orderReqVO.addressId");
		/**发票选择**/
//		String invoice = request.getParameter("orderReqVO.invoice");
		/**订单来源**/
		String orderSourced = request.getParameter("orderReqVO.orderSource");
		/**优惠券号*/
		String couponCode=request.getParameter("couponCode");
		/**活动编号*/
		String activeId=request.getParameter("activeId");
		/**获得分享的用户id*/
		String cpsFromId=CookieUtil.getInstance().readCookieValue(request, "m-cps-from-id");
		/**订单金额*/
		String orderAmount=request.getParameter("orderAmount");
		try{
			String tokenId = SessionUtil.getTokenId(request);
			
			// 查询待付款的订单进行检验是否重复订单
			OrderListInParam orderListInParam = getOrderListInParamRemote(request,tokenId,OrderQueryTypeConstant.DELAY_PAY_COUNT, this.page, this.pageSize);
			
			// addressId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			addressId=EncryptUtils.decryptByAES(addressId, aesKey);
			// 检验是否重复订单
			checRespVo = orderService.checkIsRepeatedOrder(orderListInParam, goodsSku, quantity, addressId, orderAmount);
			if(checRespVo.isRepeated()){
				logger.info("此订单为重复订单，直接跳转到支付");
				StringBuilder sbParams = new StringBuilder();
				sbParams.append("payReqVO.orderNo=" + checRespVo.getOrderNo());
				sbParams.append("&payReqVO.orderAmount=" + checRespVo.getOrderAmount());
				// orderId加密(AES)
				String orderId=EncryptUtils.encryptOrderIdByAES(checRespVo.getOrderId(), aesKey);
				
				sbParams.append("&payReqVO.orderId=" + orderId);
				sbParams.append("&payReqVO.payType=" + checRespVo.getPayType());
				sbParams.append("&payReqVO.payMedium=" + (StringUtils.isEmpty(payMedium) ? "web" : payMedium));
				
				logger.info("检查重复订单最后跳转路径参数值: "+sbParams.toString());
				String forward = "forward:/order/toPay.shtml?" + sbParams.toString();
				return forward;
			}else{
				logger.info("此订单为非重复订单，创建新订单");
				StringBuilder sbParams = new StringBuilder();
				sbParams.append("goodsSn=" + goodsSn);
				sbParams.append("&goodsSku=" + goodsSku);
				sbParams.append("&quantity=" + quantity);
				sbParams.append("&couponCode=" + couponCode);
				sbParams.append("&activeId="+activeId);
				sbParams.append("&cpsFromId="+cpsFromId);
				sbParams.append("&orderReqVO.deliverTime=" + deliverTime);
				sbParams.append("&orderReqVO.paymentMethod=" + payMethod);
				// addressId加密(AES)
				addressId=EncryptUtils.encryptByAES(addressId, aesKey);
				
				sbParams.append("&orderReqVO.addressId=" + addressId);
//				sbParams.append("&orderReqVO.invoice=" + invoice);
				sbParams.append("&orderReqVO.orderSource=" + orderSourced);
				sbParams.append("&payMedium=" + (StringUtils.isEmpty(payMedium) ? "web" : payMedium));
				logger.info("检查重复订单最后跳转路径参数值: "+sbParams.toString());
				String forward = "forward:/order/createOrderAndPay.shtml?" + sbParams.toString();
				return forward;
			}
		}catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检查当前是否为重复提交订单时发生异常EIBusinessException errorCode=" + e.getErrCode() + "errorMsg=" + e.getMessageWithSupportCode(),e);
		}catch(Exception e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("检查当前是否为重复提交订单时发生异常：" + e.getMessage(),e);
		}
		logger.info("检查当前是否为重复提交订单返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response,JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	private OrderListInParam getOrderListInParamRemote(HttpServletRequest request,String tokenId,
			String queryType, int page, int pageSize) {
		OrderListInParam orderListInParam = new OrderListInParam();

		orderListInParam.setTokenId(tokenId);
		// 转换为查询全部订单（在全部订单中过滤各类订单）
		orderListInParam.setQueryType(queryType);
		orderListInParam.setPage(page);
		orderListInParam.setPageSize(pageSize);
		SessionUtil.setDeviceInfo(request, orderListInParam);

		return orderListInParam;
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
			Logger logger = Logger.getLogger(OrderController.class);
			logger.error("mediaProcess() ==> " + ex.getMessage(),ex);
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

	/**
	 * 更新已发货订单为已完结状态
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "updateTradeToEnd")
	public String updateTradeToEnd(String jsoncallback,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		 
		/**订单Id*/
		String orderId = request.getParameter("orderId");
		String userId = SessionUtil.getUserId(request);
		try{
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			orderId=EncryptUtils.decryptOrderIdByAES(orderId, aesKey);
			
			boolean resultFlag=orderService.updateTradeEndStatus(orderId, userId);
			if(resultFlag){
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.UpdateTradeEndStatusFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.UpdateTradeEndStatusFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("更新已发货订单为已完结状态时发生异常:" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("更新已发货订单为已完结状态时发生异常：" + e.getMessage(), e);
		}
		logger.info("更新已发货订单为已完结状态返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response,JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
	
	/**
	 * 根据订单ID删除订单
	 * 只允许删除订单状态为 已撤销、审核未通过、交易完成、订单完结的订单，不会对订单进行物理删除，而是把订单表标识为已删除。
	 * @param jsoncallback
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteOrder")
	public String deleteOrder(String jsoncallback,HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		 
		/**订单Id*/
		String orderId = request.getParameter("orderId");
		String userId = SessionUtil.getUserId(request);
		try{
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			orderId=EncryptUtils.decryptOrderIdByAES(orderId, aesKey);
			
			//删除订单
			boolean resultFlag=orderService.deleteOrder(orderId, userId);
			if(resultFlag){
				model.put("result", true);
				model.put("errorCode", ErrorCode.Success.getErrorCode());
				model.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.DeleteOrderFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.DeleteOrderFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("删除订单时发生异常:" + e.getMessageWithSupportCode(), e);
		} catch(Exception e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除订单时发生异常：" + e.getMessage(),e);
		}
		logger.info("删除订单返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response,JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}
//	
//	/**
//	 * 获取发票
//	 * @param jsoncallback
//	 * @param request
//	 * @param response
//	 * @throws Exception
//	 */
//	@RequestMapping(value="getOrderInvoiceType")
//	public void getOrderInvoiceType(String jsoncallback,HttpServletRequest request,HttpServletResponse response) throws Exception {
//		// 存储返回结果值
//		Map<String, Object> model = new LinkedHashMap<String, Object>();
//		List<OrderInvoiceDO>
//	
//		
//		ResponseUtil.outPrintResult(response,JsonUtils.bean2jsonP(jsoncallback, model));
//	}
//	
	
	/**
	 * 处理后缀为.00 和.0的价格
	 * @param price
	 * @return
	 */
	public String getPrice(String price) {
		if(!StringUtils.isEmpty(price)) {
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
