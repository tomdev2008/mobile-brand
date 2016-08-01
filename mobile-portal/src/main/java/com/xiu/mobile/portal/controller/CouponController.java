/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-17 下午5:46:07 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.CardCouponConstant;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.CouponUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.model.CouponBo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.service.ICouponService;
import com.xiu.mobile.portal.service.ILoginService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(优惠券) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-17 下午5:46:07 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController{
	
	private static final Logger LOG = Logger.getLogger(CouponController.class);
	
	@Autowired
	private ILoginService loginService;
	@Autowired
	private ICouponService couponService;
	
	/**
	 * 激活优惠券
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "active", produces = "text/html;charset=UTF-8")
	public String activeCoupon(String cardCode, String activeCode, String jsoncallback, HttpServletRequest request) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			Map resultMap = couponService.activeCouponOrCardCode(cardCode, activeCode, user.getUserId(), user.getLogonName());
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
			if(resultMap != null) {
				boolean isSuccess = (Boolean) resultMap.get("isSuccess");
				if(!isSuccess) {
					//如果不成功
					String resultCode = resultMap.get("resultCode").toString();
					String message = CouponUtil.ACTIVE_RESULT.get(resultCode); 	//错误信息
					if(message == null || message.equals("")) {
						//如果根据错误code没有找到可以转义的错误信息，则取原来的错误信息
						Object msgObj = resultMap.get("errorMessages");
						if(msgObj != null && !msgObj.toString().equals("")) {
							Map msgMap = (Map) msgObj;
							message = (String) msgMap.get(resultCode);
						}else {
							message = "";   
						}
					}
					
					result.put("result", false);
					result.put("errorCode", ErrorCode.SystemError.getErrorCode());
					result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
					result.put("message", message);
					
					LOG.info("User" + user.getUserId() + "Active Coupon cardCode=" + cardCode + ",activeCode=" + activeCode
							+ ",fail.errorCode=" + resultCode + ",msg=" + message);
				}
			}
			
//			String msgKey = couponService.activeCoupon(cardCode,activeCode, user.getUserId(), user.getLogonName());
//			result.put("result", true);
//			result.put("errorCode", ErrorCode.Success.getErrorCode());
//			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
//			
//			// 不成功返回msgKey
//			if(!"11".equals(msgKey)) { 
//				result.put("result", false);
//				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
//				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
//				result.put("message", CouponUtil.ACTIVE_RESULT.get(msgKey));
//				LOG.info("User" + user.getUserId() + "Active Coupon cardCode=" + cardCode 
//						+ ",activeCode=" + activeCode + "fail.MsgKey=" + msgKey + ",msg=" + CouponUtil.ACTIVE_RESULT.get(msgKey));
//			}
		} catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			LOG.error("激活优惠卡、券异常", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			LOG.error("激活优惠卡、券异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 
	 * @param actionType
	 * @param terminal 展示类型 0全平台 1 手机 2 电脑  默认0全平台
	 * @param orderField 排序 0激活时间 1到期时间 2失效时间 默认0激活时间
	 * @param orderType 排序类型 0倒叙 1正序 默认0倒叙
	 * @param page
	 * @param jsoncallback
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "list" , produces = "text/html;charset=UTF-8")
	public String couponList(String actionType,String terminal,String orderField ,String orderType,String isCanUse, Page<?> page, String jsoncallback, HttpServletRequest request) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 优惠券分类属性 0(全部) 1(可使用) 2(已使用) 3(已过期)
			int type = NumberUtils.toInt(actionType, 1);
			// 查询优惠券列表   分页信息查询  因不需要分页 暂把每页记录数设置大一点
			page.setPageNo(NumberUtils.toInt(request.getParameter("pageNo"),1));
			page.setPageSize(NumberUtils.toInt(request.getParameter("pageSize"),100));
			Integer terminalInt=ObjectUtil.getInteger(terminal, 0);
			Integer orderFieldInt=ObjectUtil.getInteger(orderField, 0);
			Integer orderTypeInt=ObjectUtil.getInteger(orderType, 0);
			//是否只返回当前能够使用的优惠券
			int isCanUseInt = ObjectUtil.getInteger(isCanUse, 0);
			
			CouponBo couponBo = couponService.findCouponBoByParam(user.getUserId(), type, page,terminalInt,orderFieldInt,orderTypeInt,isCanUseInt);
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("coupons", couponBo.getCoupons());
			result.put("totalCount", couponBo.getPage().getTotalCount());
			result.put("totalPages", couponBo.getPage().getTotalPages());
			result.put("pageNo", couponBo.getPage().getPageNo());
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			LOG.error("查询优惠券列表异常:", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	
	/***
	 * 验证优惠券信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateCardCoupon" , produces = "text/html;charset=UTF-8")
	public String validateCardCoupon(HttpServletRequest request,HttpServletResponse response,String jsoncallback) {
		LoginResVo user = SessionUtil.getUser(request);
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 优惠券号、走秀码、商品数量
			String cardCode = request.getParameter("cardCode");
			String goodsSnStr = request.getParameter("goodsSnStr");
			int number = NumberUtils.toInt(request.getParameter("number"), 1);
			if (StringUtils.isBlank(cardCode) || StringUtils.isBlank(goodsSnStr)) {
				result.put("result", false);
				result.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				result.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			
			String channelId = GlobalConstants.CHANNEL_ID;
			Map<String, Object> validateResult = couponService.validateCardCoupon(cardCode,Long.parseLong(user.getUserId()),goodsSnStr,channelId,number);
			// 如果验证成功
			if (Boolean.parseBoolean(validateResult.get("result").toString())) {
				result.put("result", true);
				result.put("errorCode", ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
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
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
}
