package com.xiu.mobile.portal.controller.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.filter.HttpThreadTool;
import com.xiu.mobile.portal.common.filter.SpreadThread;
import com.xiu.mobile.portal.common.filter.SupportHttpThread;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import com.xiu.mobile.sales.dointerface.vo.WeiXinActivityResponse;
import com.xiu.mobile.sales.dointerface.vo.WeiXinSpreadRewardResult;
import com.xiu.uuc.facade.UserManageFacade;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;

/**
 * 微信营销活动控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/wechatacty")
public class WechatPromoActyController extends BaseController {

	private final static Logger logger = Logger.getLogger(WechatPromoActyController.class);
	
	@Autowired
	EIMobileSalesManager manager;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserManageFacade userManageFacade;
	
	/**
	 * 进入活动页
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/accessWeiXinActivityPage", produces = "text/html;charset=UTF-8")
	public String accessWeiXinActivityPage(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("进入活动首页前端提交参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String spreadUnionId = request.getParameter("spreadUnionId"); // 微信传播者
		String code = request.getParameter("code"); // 防止篡改签名
		String nickName = request.getParameter("nickName"); // 微信用户微信昵称
		if(StringUtils.isNotBlank(nickName)){
			try {
				nickName = URLDecoder.decode(nickName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error("解密nickname异常，nickname: " + request.getParameter("nickName"), e1);
			}
		}
		String userPhoneUrl = request.getParameter("userPhoneUrl"); // 微信用户图片地址
		boolean checkLogin = true; //默认校验登陆
		if(StringUtils.isNotBlank(request.getParameter("checkLogin"))){
			checkLogin = Boolean.parseBoolean(request.getParameter("checkLogin")); //是否校验登陆
		}
		Long xiuUserId = null;
		String xiuUserName = null;
		// 检查登录
		if(checkLogin){
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			xiuUserId = Long.parseLong(SessionUtil.getUserId(request)); //走秀用户id
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
			if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
				result.put("result", false);
				result.put("errorCode", "-1");
				result.put("errorMsg", "根据user_id获取不到用户信息");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		}
		String extstring1 = request.getParameter("extstring1"); //拓展字段，根据不同活动表示不同意义
		String extstring2 = request.getParameter("extstring2"); //拓展字段，根据不同活动表示不同意义
		Double extnumber1 = null;
		if(StringUtils.isNotBlank(request.getParameter("extnumber1"))){
			extnumber1 = Double.parseDouble(request.getParameter("extnumber1")); //拓展字段，根据不同活动表示不同意义
		}try{
			WeiXinActivityResponse weiXinActivityResponse = manager.getWeiXinActivitySupportInfo(activityId,
					unionId, spreadUnionId, code, xiuUserId, xiuUserName, nickName, userPhoneUrl, checkLogin, extstring1, extstring2, extnumber1);
			result.put("result", true);
			result.put("errorCode", "0");
			result.put("errorMsg", "成功");
			result.put("weiXinActivityResponse", weiXinActivityResponse);
			
			logger.info("获取活动信息返回结果：" + weiXinActivityResponse);
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取活动信息返回结果发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "系统异常");
			logger.error("获取活动信息返回结果发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	/**
	 * 下单发红包发现金进入活动页
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/accessWeiXinActivityPageNew", produces = "text/html;charset=UTF-8")
	public String accessWeiXinActivityPageNew(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("进入活动首页前端提交参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String spreadUnionId = request.getParameter("spreadUnionId"); // 微信传播者
		String code = request.getParameter("code"); // 防止篡改签名
		boolean checkLogin = true; //默认校验登陆
		if(StringUtils.isNotBlank(request.getParameter("checkLogin"))){
			checkLogin = Boolean.parseBoolean(request.getParameter("checkLogin")); //是否校验登陆
		}
		Long xiuUserId = null;
		String xiuUserName = null;
		// 检查登录
		if(checkLogin){
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			xiuUserId = Long.parseLong(SessionUtil.getUserId(request)); //走秀用户id
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
			if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
				result.put("result", false);
				result.put("errorCode", "-1");
				result.put("errorMsg", "根据user_id获取不到用户信息");
				return JsonUtils.bean2jsonP(jsoncallback, result);
			}
			xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		}
			Map<String,Object> requestMap = manager.getOrderRedpacketParentInfo(activityId,
					unionId, spreadUnionId, code, xiuUserId, xiuUserName, checkLogin);
		return JsonUtils.bean2jsonP(jsoncallback, requestMap);
	}
	/**
	 * 打赏
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/supportSendReward", produces = "text/html;charset=UTF-8")
	public String supportSendReward(HttpServletRequest request, HttpServletResponse response, String jsoncallback){
		logger.info("打赏用户时前端提交参数：params="+request.getQueryString());
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String code = request.getParameter("code"); // 防止篡改签名
		String supportUnionId = request.getParameter("supportUnionId"); // 微信支持者UnionId
		String supportSay = request.getParameter("supportSay"); //支持者对传播者打赏是发表的说说
		String supportPhoneUrl = request.getParameter("supportPhoneUrl"); // 支持者微信图像Url
		String spreadUnionId = request.getParameter("spreadUnionId"); // 微信传播者UnionId
		
		String nickName = request.getParameter("nickName"); // 微信传播者微信昵称
		if(StringUtils.isNotBlank(nickName)){
			try {
				nickName = URLDecoder.decode(nickName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error("解密nickname异常，nickname: " + request.getParameter("nickName"), e1);
			}
		}
		
		boolean checkLogin = true; //默认校验登陆
		if(StringUtils.isNotBlank(request.getParameter("checkLogin"))){
			checkLogin = Boolean.parseBoolean(request.getParameter("checkLogin")); //是否校验登陆
		}
		String telephone = request.getParameter("telephone"); // 用户手机号
		String extstring2 = request.getParameter("extstring2"); //拓展字段，根据不同活动表示不同意义
		Double extnumber1 = null;
		if(StringUtils.isNotBlank(request.getParameter("extnumber1"))){
			extnumber1 = Double.parseDouble(request.getParameter("extnumber1")); //拓展字段，根据不同活动表示不同意义
		}

		String xiuUserId = null;
		String xiuUserName = null;
		// 检查登录
		if(checkLogin){
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			xiuUserId = SessionUtil.getUserId(request); //走秀用户id
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(xiuUserId));
			if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", false);
				resultMap.put("errorCode", "-1");
				resultMap.put("errorMsg", "根据user_id获取不到用户信息");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		}
		// 打赏微信传播者
		Map<String, Object> resultMap = manager.supportSendReward(activityId, unionId, code, 
				nickName, supportUnionId, supportSay, supportPhoneUrl, xiuUserName, xiuUserId, spreadUnionId, checkLogin, extstring2, extnumber1, telephone);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 下单发红包发现金打赏
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/supportSendRewardNew", produces = "text/html;charset=UTF-8")
	public String supportSendRewardNew(HttpServletRequest request, HttpServletResponse response, String jsoncallback){
		logger.info("打赏用户时前端提交参数：params="+request.getQueryString());
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String code = request.getParameter("code"); // 防止篡改签名
		String supportUnionId = request.getParameter("supportUnionId"); // 微信支持者UnionId
		String supportSay = request.getParameter("supportSay"); //支持者对传播者打赏是发表的说说
		String supportPhoneUrl = request.getParameter("supportPhoneUrl"); // 支持者微信图像Url
		String spreadUnionId = request.getParameter("spreadUnionId"); // 微信传播者UnionId
		
		String nickName = request.getParameter("nickName"); // 微信传播者微信昵称
		if(StringUtils.isNotBlank(nickName)){
			try {
				nickName = URLDecoder.decode(nickName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error("解密nickname异常，nickname: " + request.getParameter("nickName"), e1);
			}
		}
		
		boolean checkLogin = true; //默认校验登陆
		if(StringUtils.isNotBlank(request.getParameter("checkLogin"))){
			checkLogin = Boolean.parseBoolean(request.getParameter("checkLogin")); //是否校验登陆
		}
		String telephone = request.getParameter("telephone"); // 用户手机号
		String extstring2 = request.getParameter("extstring2"); //拓展字段，根据不同活动表示不同意义
		Double extnumber1 = null;
		if(StringUtils.isNotBlank(request.getParameter("extnumber1"))){
			extnumber1 = Double.parseDouble(request.getParameter("extnumber1")); //拓展字段，根据不同活动表示不同意义
		}

		String xiuUserId = null;
		String xiuUserName = null;
		// 检查登录
		if(checkLogin){
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			xiuUserId = SessionUtil.getUserId(request); //走秀用户id
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(xiuUserId));
			if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", false);
				resultMap.put("errorCode", "-1");
				resultMap.put("errorMsg", "根据user_id获取不到用户信息");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		}
		SupportHttpThread httpThread=new SupportHttpThread(telephone,manager);
		HttpThreadTool.getInstance().execute(httpThread);
		// 打赏微信传播者
		Map<String, Object> resultMap = manager.supportSendRewardNew(activityId, unionId, code, 
				nickName, supportUnionId, supportSay, supportPhoneUrl, xiuUserName, xiuUserId, spreadUnionId, checkLogin, extstring2, extnumber1, telephone);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 活动排行榜
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/rank", produces = "text/html;charset=UTF-8")
	public String rank(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("前端提交参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID

		try{
			PageView<WeiXinSpreadRewardResult> pageView = manager.queryWeiXinSpreadRewardResult(activityId, null, 1, 8);
			
			result.put("result", true);
			result.put("errorCode", ErrorCode.Success.getErrorCode());
			result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			result.put("pageView", pageView);			
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("获取活动排行榜异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "系统异常");
			logger.error("获取活动排行榜异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	} 
	
	/**
	 * 微信扫码获取优惠礼包
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getGiftByScanCode", produces = "text/html;charset=UTF-8")
	public String getGiftByScanCode(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("前端提交参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// 获取传递相关参数
		String activityId = request.getParameter("activityId"); // 微信活动ID
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String spreadUnionId = request.getParameter("spreadUnionId"); // 微信传播者
		String code = request.getParameter("code"); // 防止篡改签名
		String nickName = request.getParameter("nickName"); // 微信用户微信昵称
		String userPhoneUrl = request.getParameter("userPhoneUrl"); // 微信用户图片地址
		

		if(StringUtils.isBlank(activityId) 
				|| StringUtils.isBlank(unionId) || StringUtils.isBlank(code)){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "参数不能为空，请检查传递的参数");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		// 检查登录
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		Long xiuUserId = Long.parseLong(SessionUtil.getUserId(request)); //走秀用户id
		UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
		if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "根据user_id获取不到用户信息");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		String xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		
		try{
			WeiXinActivityResponse weiXinActivityResponse = manager.scanCodeWeiXinActivityPage(activityId,
					unionId, spreadUnionId, code, xiuUserId, xiuUserName, nickName, userPhoneUrl);
			result.put("result", true);
			result.put("errorCode", "0");
			result.put("errorMsg", "成功");
			result.put("weiXinActivityResponse", weiXinActivityResponse);
			
			logger.info("返回结果：" + weiXinActivityResponse);
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("扫码获取优惠礼包信息发生异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "系统异常");
			logger.error("扫码获取优惠礼包信息发生异常：", e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 微信核销接口
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/consumeWechatCard", produces = "text/html;charset=UTF-8")
	public String consumeWechatCard(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("微信核销前端提交参数：params="+request.getQueryString());
		
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		// 获取传递相关参数
		String unionId = request.getParameter("unionId"); // 微信用户unionId
		String encryptCode = request.getParameter("encryptCode");
		String cardId = request.getParameter("cardId");
		String signature = request.getParameter("signature");
		
		// 检查登录
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		
		logger.info("用户[" + unionId + "]成功登陆！");
		
		Long xiuUserId = Long.parseLong(SessionUtil.getUserId(request)); //走秀用户id
		UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
		
		logger.info("用户[" + unionId + "]取用户信息！+ " + userBaseDTO);
		if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "根据user_id获取不到用户信息");
			return JsonUtils.bean2jsonP(jsoncallback, result);
		}
		String xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		//Long xiuUserId=1012736510l;
		//String xiuUserName="xiu_1012736510773";
		try{
			Result consumeResult = manager.consumeWechatCard(xiuUserId, xiuUserName, unionId, encryptCode, cardId, signature);
			result.put("result", true);
			result.put("errorCode", consumeResult.getResultCode());
			result.put("errorMsg", consumeResult.getErrorMessages());
			result.put("consumeResult", consumeResult);
			logger.info("核销返回结果：" + consumeResult);
			
		}catch (EIBusinessException e) {
			result.put("result", false);
			result.put("errorCode", e.getExtErrCode());
			result.put("errorMsg", e.getExtMessage());
			logger.error("卡券核销异常：", e);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", "-1");
			result.put("errorMsg", "系统异常");
			logger.error("卡券核销异常：", e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 设备送券状态
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/customRewardStatus", produces = "text/html;charset=UTF-8")
	public String customRewardStatus(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("获取设备送券状态提交参数：" + request.getQueryString());
		
		String deviceId = request.getParameter("deviceId"); //设备ID
		String xiuUserId = SessionUtil.getUserId(request); //用户ID
		
		Map<String, Object> resultMap = manager.customRewardStatus(xiuUserId, deviceId);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 设备专享送券
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userCustomReward", produces = "text/html;charset=UTF-8")
	public String userCustomReward(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("设备专享送券提交参数：" + request.getQueryString());
		
		String deviceId = request.getParameter("deviceId"); //设备ID
		String operType = request.getParameter("operType"); //是否送券 1：送券，2：放弃
		String platform = request.getParameter("platform"); //平台 ios、ipad-app、android
		
		Long xiuUserId = null;
		String xiuUserName = null;
		if("1".equals(operType)){
			//校验登陆
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			xiuUserId = Long.parseLong(SessionUtil.getUserId(request));
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
			xiuUserName = userBaseDTO.getLogonName(); //走秀用户名
		}
		
		Map<String, Object> resultMap = manager.userCustomReward(xiuUserId, xiuUserName, deviceId, operType, platform);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 获取分享红包链接接口
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/spreadSendReward", produces = "text/html;charset=UTF-8")
	public String spreadSendReward(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("获取分享红包链接接口提交参数：" + request.getQueryString());
		
		String activityCode = request.getParameter("activityCode"); //活动CODE
		String spreadUnionId = request.getParameter("spreadUnionId"); //订单Code
		String xiuUserId = request.getParameter("xiuUserId"); //走秀用户ID
		String xiuNickName = request.getParameter("xiuUserName"); //走秀用户昵称
		String userPhoneUrl = request.getParameter("userPhoneUrl"); //走秀头像
		String checkLogin = request.getParameter("checkLogin"); //是否要校验登陆，是：true, 否：false
		String code = request.getParameter("code"); //防止篡改签名
		
		if(StringUtils.isNotBlank(xiuNickName)){
			try {
				xiuNickName = URLDecoder.decode(xiuNickName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error("解密xiuNickName异常，xiuNickName: " + xiuNickName, e1);
			}
		}
		
		Long xiuUserIdLong = null;
		if(StringUtils.isNotBlank(xiuUserId)){
			xiuUserIdLong = Long.parseLong(xiuUserId);
		}
		boolean check = false;
		if(StringUtils.isNotBlank(checkLogin)){
			check = Boolean.parseBoolean(checkLogin);
		}
		
		Map<String, Object> resultMap = manager.spreadSendReward(activityCode, 
				spreadUnionId, xiuUserIdLong, xiuNickName, userPhoneUrl, code, check);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 下单发红包发现金获取分享链接
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/spreadSendRewardNew", produces = "text/html;charset=UTF-8")
	public String spreadSendRewardNew(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		
		logger.info("获取分享红包链接接口提交参数：" + request.getQueryString());
		
		String activityCode = request.getParameter("activityCode"); //活动CODE
		String spreadUnionId = request.getParameter("spreadUnionId"); //订单Code
		String xiuUserId = request.getParameter("xiuUserId"); //走秀用户ID
		String xiuNickName = request.getParameter("xiuUserName"); //走秀用户昵称
		String userPhoneUrl = request.getParameter("userPhoneUrl"); //走秀头像
		String checkLogin = request.getParameter("checkLogin"); //是否要校验登陆，是：true, 否：false
		String code = request.getParameter("code"); //防止篡改签名
		String mobile=request.getParameter("mobile"); //下单成功的手机号码
		String deviceId=request.getParameter("deviceId"); //下单设备号
		
		if(StringUtils.isNotBlank(xiuNickName)){
			try {
				xiuNickName = URLDecoder.decode(xiuNickName, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				logger.error("解密xiuNickName异常，xiuNickName: " + xiuNickName, e1);
			}
		}
		
		Long xiuUserIdLong = null;
		if(StringUtils.isNotBlank(xiuUserId)){
			xiuUserIdLong = Long.parseLong(xiuUserId);
		}
		boolean check = false;
		if(StringUtils.isNotBlank(checkLogin)){
			check = Boolean.parseBoolean(checkLogin);
		}
		
		Map<String, Object> resultMap = manager.spreadSendRewardNew(activityCode, 
				spreadUnionId, xiuUserIdLong, xiuNickName, userPhoneUrl, code, check,mobile,deviceId);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 检验下单发红包是不是黑名单
	 */
	@ResponseBody
	@RequestMapping(value="/spreadIsBlacklist", produces = "text/html;charset=UTF-8")
	public String spreadIsBlacklist(HttpServletRequest request,
			HttpServletResponse response, String jsoncallback){
		logger.info("检验下单发红包者是不是黑名单接口提交参数：" + request.getQueryString());
		Map<String, Object> resultMap=new HashMap<String, Object>();
		String userId=request.getParameter("userId");  //用户Id
		String mobile=request.getParameter("mobile");//用户下单手机号码
		String deviceId=request.getParameter("deviceId");//下单成功的设备号
		String orderId=request.getParameter("orderId"); //订单号
		Long xiuUserId = null;
		if(StringUtils.isNotBlank(userId)){
			xiuUserId = Long.parseLong(userId);
		}else{
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		SpreadThread httpThread=new SpreadThread(mobile,xiuUserId,deviceId,manager);
		HttpThreadTool.getInstance().execute(httpThread);
		 resultMap=manager.spreadIsBlacklist(xiuUserId,mobile,deviceId,orderId);
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 注册用户
	 * @param logonName
	 * @return
	 */
	private String registerUser(String logonName){
		RegisterUserDTO user = new RegisterUserDTO();
		user.setRegisterType("02");
		user.setLogonName(logonName);
		user.setPassword(null); //密码设置为空
		user.setChannelId(Integer.parseInt(GlobalConstants.CHANNEL_ID));
		user.setCustType("01");
		user.setRegisterIp("127.0.0.1");
		user.setIsNeedActivate("N"); // 注册时直接激活用户
		user.setUserSource(150); //注册来源：wap
		
		String userId = null;
		try{
			com.xiu.uuc.facade.dto.Result result = this.userManageFacade.registerUser(user);
			if(result != null && result.getData() != null){
				userId = result.getData().toString();
			}
		}catch(Exception e){
			logger.error("调用户中心注册用户时发生异常，logonName: " + logonName);
			logger.error(e);
		}
		return userId;
	}
	
	/**
	 * 校验登陆
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	private String isLogin(HttpServletRequest request, String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if(!checkLogin(request)){
				returnMap.put("result", false);
				returnMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				returnMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (Exception e) {
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		if(returnMap.size() > 0){
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		return null;
	}
	/**
	 * web端扫描二维码进入下单发红包分享页面
	 */
	@ResponseBody
	@RequestMapping(value="/spreadSendRewardByWeb", produces = "text/html;charset=UTF-8")
	public String spreadSendRewardByWeb(HttpServletRequest request,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		logger.info("web端扫描二维码进入下单发红包分享页面接口参数："+request.getQueryString());
		String orderId=request.getParameter("orderId");
		if(StringUtils.isBlank(orderId)){
			resultMap=getResultMap(resultMap, false, ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		Long orderIdstr=Long.parseLong(orderId);
		Map<String,Object> map=manager.getOrderRedPacketNum(orderIdstr);
		Boolean isSuccess=(Boolean)map.get("result");
		if(isSuccess){
			resultMap.put("total", map.get("total"));
		}else{
			resultMap.put("result", false);
			resultMap.put("errorCode", map.get("errorCode"));
			resultMap.put("errorMsg", map.get("errorMsg"));
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		String url="http://m.xiu.com/about/dlapp.html?targetUrl=xiuApp://xiu.app.orderDetail/openwith?id="+orderIdstr;
		resultMap.put("URL", url);
		resultMap.put("result", true);
		resultMap.put("errorCode", "0");
		resultMap.put("errorMsg", "成功");
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
