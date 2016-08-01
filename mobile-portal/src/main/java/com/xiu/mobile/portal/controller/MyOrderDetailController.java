package com.xiu.mobile.portal.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.OrderQueryTypeConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.model.CommoSummaryVo;
import com.xiu.mobile.portal.model.OrderBaseInfoVo;
import com.xiu.mobile.portal.model.OrderDetailInParam;
import com.xiu.mobile.portal.model.OrderDetailOutParam;
import com.xiu.mobile.portal.model.OrderListInParam;
import com.xiu.mobile.portal.model.ReceiverInfoVo;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IGoodsService;
import com.xiu.mobile.portal.service.IOrderDetailService;
import com.xiu.mobile.portal.service.IReceiverIdService;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.uuc.facade.dto.IdentityInfoDTO;

@Controller
@RequestMapping("/myorder")
public class MyOrderDetailController extends BaseController {
	
	private Logger logger = Logger.getLogger(MyOrderDetailController.class);
	private Integer page = 1;
	private Integer pageSize = Integer.MAX_VALUE;
	@Autowired
	private IOrderDetailService orderDetailService;
	@Autowired
	private IReceiverIdService receiverIdService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IGoodsService goodsService;
	
	private OrderDetailInParam getOrderDetailInParam(HttpServletRequest request,String tokenId, Integer orderId) {
		OrderDetailInParam orderDetailInParam = new OrderDetailInParam();
		orderDetailInParam.setTokenId(tokenId);
		orderDetailInParam.setOrderId(orderId);
		SessionUtil.setDeviceInfo(request, orderDetailInParam);
		return orderDetailInParam;
	}
	
	// 供移动端接口使用
	private OrderListInParam getOrderListInParam(HttpServletRequest request,String tokenId, String queryType, int page, int pageSize) {
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
	 * 
	 * 订单详情接口
	 *  
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderDetailRemote")
	public String getOrderDetail(HttpServletRequest request,HttpServletResponse response,String jsoncallback,
			String lpStatus,Integer page,Integer pageSize) {
		/**订单Id */
		String oId =request.getParameter("orderId");
		String orderDetailIdStr =request.getParameter("orderDetailId");
		
		Assert.notNull(oId,"订单orderId不能为空.");
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		if(null != page){
			this.page = page;
		}
		if(null != pageSize){
			this.pageSize = pageSize;
		}
		try {
			// 检查登录
			String tokenId = SessionUtil.getTokenId(request);
			// orderId解密(AES）
			String aesKey=EncryptUtils.getAESKeySelf();
		    oId=EncryptUtils.decryptOrderIdByAES(oId,aesKey);
			int orderId =Integer.valueOf(oId);
			
			// 组装查询参数
			OrderDetailInParam params = getOrderDetailInParam(request,tokenId, orderId);
			// 获取订单详情接口输出接口
			OrderDetailOutParam orderDetailOutParam = orderDetailService.getOrderDetailOutParam(params);
			if (orderDetailOutParam.isSuccess()) {
				//判断当前用户查询的订单是否是自己的订单{多此一举，上面本来就拿userID去查的订单}
//				OrderListInParam orderListInParam = getOrderListInParam(request,tokenId, OrderQueryTypeConstant.ALL_COUNT, this.page, this.pageSize);
//				boolean flag=orderDetailService.checkOrderId(orderListInParam, orderId);
//				if (flag) {
					Assert.notNull(lpStatus,"订单物流状态lpStatus不能为空.");
					orderDetailOutParam.setLpStatus(lpStatus);// 从网页传入
					// 获取订单信息
					OrderBaseInfoVo orderBaseInfoVo = orderDetailService.getOrderBaseInfo(orderDetailOutParam);
					
					SimpleDateFormat formatDate = new SimpleDateFormat(GlobalConstants.DATE_FROMAT);
					Date targetDate, orderDate;
					targetDate = formatDate.parse(GlobalConstants.DATE_2013);
					orderDate = formatDate.parse(orderBaseInfoVo.getWhen());
					if (orderDate.before(targetDate)) {
						orderBaseInfoVo.setForbidComment(1);
					}
					
					// orderId加密(AES)
					String oId1=EncryptUtils.encryptOrderIdByAES(orderBaseInfoVo.getOrderId(), aesKey);
					orderBaseInfoVo.setOrderId(oId1);
					
					model.put("orderBaseInfoVo", orderBaseInfoVo);
					List<CommoSummaryVo> commoSummaryVoList = orderDetailService.getCommoSummaryList(orderDetailOutParam);
					boolean uploadIdCardStatus = true; //身份证状态返回值
					boolean checkUploadIdCard = false; //检测身份证状态标志
					if (null != commoSummaryVoList && commoSummaryVoList.size() > 0) {
						for (CommoSummaryVo commoSummaryVo : commoSummaryVoList) {
							//查询商品上传身份证状态
							int uploadIdCard = goodsService.getGoodsUploadIdCardByGoodsSn(commoSummaryVo.getGoodsSn()); 
							if(uploadIdCard == 0 || uploadIdCard == 1) {
								checkUploadIdCard = true;
								break;
							}
						}
					}
					
					// 商品的上传身份证状态为必须和需要
					if (checkUploadIdCard) {
						if (null != orderDetailOutParam.getAddressId() && !("").equals(orderDetailOutParam.getAddressId().toString())) {
							// 判断用户收货地址中是否上传了身份证
							String userId = SessionUtil.getUserId(request);
							IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoByUserIdAndName(Long.parseLong(userId), orderDetailOutParam.getReceiver());
							if(identityInfoDTO != null) {
								Integer reviewState = identityInfoDTO.getReviewState();
								//审核通过和待审核状态的身份证，则显示支付成功页面1
								if(reviewState == 2) {
									uploadIdCardStatus = false;
								} else {
									uploadIdCardStatus = true;
								}
							} else {
								uploadIdCardStatus = false;
							}
						}
					}
					CommoSummaryVo vo = new CommoSummaryVo();
					if(commoSummaryVoList != null && commoSummaryVoList.size() > 0) {
						Integer orderDetailId=ObjectUtil.getInteger(orderDetailIdStr, null);
						if(orderDetailId!=null){
							for (CommoSummaryVo svo:commoSummaryVoList) {
								if(svo.getOrderDetailId()==orderDetailId){
									vo=svo;
								}
							}
						}else{
							vo=commoSummaryVoList.get(0);
						}
						
						
						String price[] = vo.getDiscountPrice().split("\\.");
						if (price.length > 0 && price[0].equals("00")) {
							vo.setDiscountPrice(price[0]);
						}
					}
					
					ReceiverInfoVo receiverInfoVo = orderDetailService.getReceiverInfo(orderDetailOutParam);
					//如果addressId不为空则进行加密
					String addressId = receiverInfoVo.getAddressId();
					if(StringUtils.isNotBlank(addressId)) {
						// addressId加密(AES)
						String keyStr = EncryptUtils.getAESKeySelf();
						receiverInfoVo.setAddressId(EncryptUtils.encryptByAES(addressId, keyStr));
					}else{
						// 如果没有地址信息 则直接表明不需要上传身份证
						uploadIdCardStatus = true;
					}
					
					model.put("result", true);
					model.put("uploadIdCardStatus", uploadIdCardStatus);
					model.put("receiverInfoVo", receiverInfoVo);
					model.put("commoSummaryVoList", vo);
//				}else{
//					model.put("result", false);
//					model.put("errorCode", ErrorCode.OrderNotBelongToUser.getErrorCode());
//					model.put("errorMsg", ErrorCode.OrderNotBelongToUser.getErrorMsg());
//				}
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
			logger.error("获取订单详情时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

	/***
	 * 订单详情信息接口  展示多个商品信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @param orderId
	 * @param lpStatus
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderDetailInfoRemote")
	public String getOrderDetailInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback,
			String lpStatus,Integer page,Integer pageSize) {
		String oId=request.getParameter("orderId");
		
		Assert.notNull(oId,"订单orderId不能为空.");
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		if(null != page){
			this.page = page;
		}
		if(null != pageSize){
			this.pageSize = pageSize;
		}
		try {
			// 检查登录
			String tokenId = SessionUtil.getTokenId(request);
			// orderId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			Integer orderId=Integer.valueOf(EncryptUtils.decryptOrderIdByAES(oId, aesKey));
			
			// 组装查询参数
			OrderDetailInParam params = getOrderDetailInParam(request,tokenId, orderId);
			// 获取订单详情接口输出接口
			OrderDetailOutParam orderDetailOutParam = orderDetailService.getOrderDetailOutParam(params);
			if (orderDetailOutParam.isSuccess()) {
				//判断当前用户查询的订单是否是自己的订单 {多此一举，上面本来就拿userID去查的订单}
//				OrderListInParam orderListInParam = getOrderListInParam(request,tokenId, OrderQueryTypeConstant.ALL_COUNT, this.page, this.pageSize);
//				boolean flag=orderDetailService.checkOrderId(orderListInParam, orderId);
//				if (flag) {
					if(StringUtil.isNotBlank(lpStatus)) {
						orderDetailOutParam.setLpStatus(lpStatus);// 从网页传入
					} else {
						String orderLpStatus = orderDetailOutParam.getLpStatus();
						if(StringUtil.isNotBlank(orderLpStatus)) {
							orderDetailOutParam.setLpStatus(orderLpStatus);
						} else {
							Assert.notNull(lpStatus,"订单物流状态lpStatus不能为空.");
						}
					}
					// 获取订单信息
					OrderBaseInfoVo orderBaseInfoVo = orderDetailService.getOrderBaseInfo(orderDetailOutParam);
					
					SimpleDateFormat formatDate = new SimpleDateFormat(GlobalConstants.DATE_FROMAT);
					Date targetDate, orderDate;
					targetDate = formatDate.parse(GlobalConstants.DATE_2013);
					orderDate = formatDate.parse(orderBaseInfoVo.getWhen());
					if (orderDate.before(targetDate)) {
						orderBaseInfoVo.setForbidComment(1);
					}
					
					// orderId加密(AES)
					orderBaseInfoVo.setOrderId(EncryptUtils.encryptOrderIdByAES(orderBaseInfoVo.getOrderId(), aesKey));
					
					model.put("orderBaseInfoVo", orderBaseInfoVo);
					// 获取订单商品列表信息
					List<CommoSummaryVo> commoSummaryVoList = orderDetailService.getCommoSummaryList(orderDetailOutParam);
					boolean uploadIdCardStatus = true; //身份证状态返回值
					Integer goodAreaType = 2; //商品所在地区 0保税区 1海外 2国内 默认是国内
					boolean checkUploadIdCard = false; //检测身份证状态标志
					if (null != commoSummaryVoList && commoSummaryVoList.size() > 0) {
						for (CommoSummaryVo commoSummaryVo : commoSummaryVoList) {
							//查询商品上传身份证状态
							int uploadIdCard = goodsService.getGoodsUploadIdCardByGoodsSn(commoSummaryVo.getGoodsSn()); 
							if(uploadIdCard<goodAreaType){
								goodAreaType=uploadIdCard;
							}
							if(uploadIdCard == 0 || uploadIdCard == 1) {
								checkUploadIdCard = true;
//								break;
							}
						}
					}
					Boolean idAuthorized=false;
					// 商品的上传身份证状态为必须和需要
					if (checkUploadIdCard) {
						if (null != orderDetailOutParam.getAddressId() && !("").equals(orderDetailOutParam.getAddressId().toString())) {
							// 判断用户收货地址中是否上传了身份证
							String userId = SessionUtil.getUserId(request);
							IdentityInfoDTO identityInfoDTO = receiverIdService.queryIdentityInfoByUserIdAndName(Long.parseLong(userId), orderDetailOutParam.getReceiver());
							if(identityInfoDTO != null) {
						 		if(identityInfoDTO.getIdNumber()!=null&&!identityInfoDTO.getIdNumber().equals("")){//如果有身份证则为已经身份认证
						 			idAuthorized=true;
						 		}
								
								// 审核状态 reviewState{0 - 待审核, 1 - 审核通过, 2 - 审核不通过}
								Integer reviewState = identityInfoDTO.getReviewState();
								//  审核通过和待审核状态的身份证，则显示支付成功页面  否则提示需要上传身份证
								if(reviewState == 2) {
									uploadIdCardStatus = false;
								} else {
									uploadIdCardStatus = true;
								}
							} else {
								uploadIdCardStatus = false;
							}
						}
					}
					ReceiverInfoVo receiverInfoVo = orderDetailService.getReceiverInfo(orderDetailOutParam);
					//如果addressId不为空，则进行加密
					String addressId = receiverInfoVo.getAddressId();
					if(StringUtils.isNotBlank(addressId)) {
						// addressId加密(AES)
						String keyStr = EncryptUtils.getAESKeySelf();
						receiverInfoVo.setAddressId(EncryptUtils.encryptByAES(addressId, keyStr));
					}else{
						// 如果没有地址信息 则直接表明不需要上传身份证
						uploadIdCardStatus = true;
					}
					receiverInfoVo.setIdAuthorized(idAuthorized);
					model.put("result", true);
					model.put("receiverInfoVo", receiverInfoVo);
					model.put("uploadIdCardStatus", uploadIdCardStatus);
					model.put("goodAreaType", goodAreaType);
					model.put("commoSummaryVoList", commoSummaryVoList);
//				}else{
//					model.put("result", false);
//					model.put("errorCode", ErrorCode.OrderNotBelongToUser.getErrorCode());
//					model.put("errorMsg", ErrorCode.OrderNotBelongToUser.getErrorMsg());
//				}
			}
		} catch(EIBusinessException e){
			model.put("result", false);
			model.put("errorCode", ErrorCode.GetOrderDataFailed.getErrorCode());
			model.put("errorMsg", ErrorCode.GetOrderDataFailed.getErrorMsg());
			logger.error("获取订单详情时发生异常:" + e.getMessageWithSupportCode(),e);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

}
