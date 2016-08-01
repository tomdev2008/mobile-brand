package com.xiu.mobile.portal.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.core.model.PayForTemplet;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.PayTypeConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.Arithmetic4Double;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.ei.EIShortUrlManager;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.PayInfoVO;
import com.xiu.mobile.portal.model.PayReqVO;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IOrderService;

@Controller
@RequestMapping("/payForOthers")
public class PayForOthersController extends BaseController {
	private final static Logger logger = Logger.getLogger(PayForOthersController.class);
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IGoodsService goodsManager;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private EIShortUrlManager shortUrlManager;
	

	/***
	 * 通过订单号获取支付url链接
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPayForOthersURL", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPayForOthersURL(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		//点击代付按钮或复制链接按钮，返回代付页面url及数据
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try{
			// 获取订单Id
			String orderIdString = EncryptUtils.decryptOrderIdByAES(request.getParameter("orderId"), EncryptUtils.getAESKeySelf());
			/**订单Id */
			int orderId =Integer.parseInt(orderIdString);
			
			/**客户端支付平台*/
			String payMedium = request.getParameter("payMedium");
			PayReqVO payReqVO = orderService.queryOrderBaseInfo(orderId,payMedium);
			if (null != payReqVO && payReqVO.getLoginId().equals(Long.parseLong(SessionUtil.getUserId(request)))) {
				//判断订单状态，不是待支付状态则链接无效
				if("待支付".equals(payReqVO.getOrderStatus())){
					LoginResVo user = SessionUtil.getUser(request);
					String param = EncryptUtils.encryptByAES(orderIdString.concat("||").concat(user.getUserId()), EncryptUtils.getAESKeyPayForOthers());
					String payForOthersUrl = "http://m.xiu.com/payforothers/index.html?order=" + param;
//					String shortUrl=shortUrlManager.getShortLinkByThirdParty(payForOthersUrl);
//					if(shortUrl!=null){
//						payForOthersUrl=shortUrl;
//					}
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
				model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			model.put("result", false);
			model.put("errorCode", e.getExtErrCode());
			model.put("errorMsg", e.getExtMessage());
			logger.error("代付生成url发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("代付生成url发生异常：" + e.getMessage(), e);
		}
		
		logger.info("代付生成url返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}

	
	/***
	 * 通过订单号获取支付url链接
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPayForOthersURLAndTemplets", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPayForOthersURLAndTemplets(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		//点击代付按钮或复制链接按钮，返回代付页面url及数据
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		try{
			// 获取订单Id
			String orderIdString = EncryptUtils.decryptOrderIdByAES(request.getParameter("orderId"), EncryptUtils.getAESKeySelf());
			/**订单Id */
			int orderId =Integer.parseInt(orderIdString);
			
			/**客户端支付平台*/
			String payMedium = request.getParameter("payMedium");
			PayReqVO payReqVO = orderService.queryOrderBaseInfo(orderId,payMedium);
			if (null != payReqVO && payReqVO.getLoginId().equals(Long.parseLong(SessionUtil.getUserId(request)))) {
				//判断订单状态，不是待支付状态则链接无效
				if("待支付".equals(payReqVO.getOrderStatus())){
					LoginResVo user = SessionUtil.getUser(request);
					String param = EncryptUtils.encryptByAES(orderIdString.concat("||").concat(user.getUserId()), EncryptUtils.getAESKeyPayForOthers());
					String payForOthersUrl = "http://m.xiu.com/payforothers/index.html?order=" + param;
//					String shortUrl=shortUrlManager.getShortLinkByThirdParty(payForOthersUrl);
//					if(shortUrl!=null){
//						payForOthersUrl=shortUrl;
//					}
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
					model.put("payForOthersUrl", payForOthersUrl);
					logger.info("代付url："+ payForOthersUrl);
					logger.info("代付返回结果：" + JSON.toJSONString(payReqVO));
					
					Map<String,Object> paraMap = new HashMap<String,Object>(); //默认参数
					List<PayForTemplet> templetList = orderService.getPayForTempletList(paraMap);
					int count = 0;
					if(templetList != null && templetList.size() > 0) {
						count = templetList.size();
					}
					model.put("templetList", templetList);
					model.put("count", count);
					
				}else{
					model.put("result", false);
					model.put("errorCode", ErrorCode.PayForOthersInvalid.getErrorCode());
					model.put("errorMsg", ErrorCode.PayForOthersInvalid.getErrorMsg());
				}
			}else{
				model.put("result", false);
				model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
				model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
			}
		}catch(Exception e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("获取代付数据发生异常：" + e.getMessage(),e);
		}
		
		logger.info("获取代付数据返回结果：" + JSON.toJSONString(model));
		return JsonUtils.bean2jsonP(jsoncallback, model);
	}
	
	
	/***
	 * 其他方式
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getOrderInfoPayForOther", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getOrderInfoPayForOther(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		//点击代付按钮或复制链接按钮，返回代付页面url及数据
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		try{
			/**订单加密过后的Id */
			String orderEcy = request.getParameter("order");
			if (StringUtils.isBlank(orderEcy)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}
			orderEcy = EncryptUtils.decryptByAES(orderEcy, EncryptUtils.getAESKeyPayForOthers());
			String[] idsArr = orderEcy.split("\\|\\|"); // |为特殊字符 需用\\转译
			if (idsArr.length>0) {
				int orderId = Integer.parseInt(idsArr[0]);
				String userId = idsArr[1];
				/**客户端支付平台*/
				String payMedium = request.getParameter("payMedium");
				PayReqVO payReqVO = orderService.queryOrderBaseInfo(orderId,payMedium);
				if (null != payReqVO && null != payReqVO.getOrderId() && payReqVO.getLoginId().equals(Long.parseLong(userId))) {
					//判断订单状态，不是待支付就链接无效
					if("待支付".equals(payReqVO.getOrderStatus())){
						result.put("result", true);
						result.put("errorCode", ErrorCode.Success.getErrorCode());
						result.put("errorMsg", ErrorCode.Success.getErrorMsg());
						result.put("orderId", EncryptUtils.encryptOrderIdByAES(payReqVO.getOrderId(), EncryptUtils.getAESKeySelf()));
						result.put("orderNo", payReqVO.getOrderNo());
						result.put("orderAmount", Arithmetic4Double.div(payReqVO.getOrderAmount(), 100.00, 2));
						String receiveName = payReqVO.getReceiveName();
						// 第一个名称用*代替
						if (StringUtils.isNotBlank(receiveName) && receiveName.length() > 0) {
							receiveName = "*".concat(receiveName.substring(1,receiveName.length()));
						}
						result.put("receiveName", receiveName);
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						result.put("createDate", dateFormat.format(payReqVO.getCreateDate()));
					}else{
						result.put("result", false);
						result.put("errorCode", ErrorCode.PayForOthersInvalid.getErrorCode());
						result.put("errorMsg", ErrorCode.PayForOthersInvalid.getErrorMsg());
					}
				}else{
					result.put("result", false);
					result.put("errorCode", ErrorCode.PayForOthersFailed.getErrorCode());
					result.put("errorMsg", ErrorCode.PayForOthersFailed.getErrorMsg());
				}
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.PayForOthersFailed.getErrorCode());
				result.put("errorMsg", ErrorCode.PayForOthersFailed.getErrorMsg());
			}
		}catch(Exception e){
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.info("代付发生异常：" + e.getMessage(),e);
		}
		
		logger.info("代付返回结果：" + JSON.toJSONString(result));
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/***
	 * 获取找朋友代付模板
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPayForTemplets", produces = "text/html;charset=UTF-8")
	public String getPayForTemplets(HttpServletRequest request, HttpServletResponse response, String jsoncallback) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			Map<String,Object> paraMap = new HashMap<String,Object>(); //默认参数
			//查询找朋友代付模板列表
			List<PayForTemplet> templetList = orderService.getPayForTempletList(paraMap);
			
			int count = 0;
			if(templetList != null && templetList.size() > 0) {
				count = templetList.size();
			}
			
			resultMap.put("templetList", templetList);
			resultMap.put("count", count);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("获取找朋友代付模板时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

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
}
