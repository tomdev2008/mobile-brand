package com.xiu.mobile.portal.controller.raiders;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIRaiderGoodManager;
import com.xiu.mobile.portal.ei.EIRaidersManager;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.uuc.facade.dto.UserBaseDTO;

/**
 * 夺宝商品详情
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/raiderGood")
public class RaiderGoodController extends BaseController{
	private Logger logger=Logger.getLogger(RaiderGoodController.class);
	
	@Autowired
	private EIRaiderGoodManager raiderGoodManager;
	
	@Autowired
	private EIRaidersManager raidersManager;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 商品详情
	 */
	@ResponseBody
	@RequestMapping(value="/getRaiderGoodInfo",produces="text/html;charset=UTF-8")
	public String getRaiderGoodInfo(HttpServletRequest request,String jsoncallback,
			String raiderId){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		Long raiderIdL=ObjectUtil.getLong(raiderId,null);
		String userId = SessionUtil.getUserId(request);

		if(raiderIdL==null){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("raiderId", raiderIdL);
			params.put("userId", userId);
			Map<String,Object> result=raidersManager.getRaiderGoodInfo(params);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("查询夺宝详情："+result);
				resultMap.put("raidersGood", result.get("model"));
				resultMap.put("time", result.get("time"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询夺宝详情异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 他们都在玩
	 */
	@ResponseBody
	@RequestMapping(value="/findParticipateUser",produces="text/html;charset=UTF-8")
	public String findParticipateUser(HttpServletRequest request,String jsoncallback,
			String raiderId){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isBlank(raiderId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", "101");
			resultMap.put("errorMsg", "参数不能为空");
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raiderGoodManager.findParticipateUser(raiderId);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("他们都在玩纪录："+result);
				resultMap.put("richUser", result.get("richUser"));
				resultMap.put("sofaUser", result.get("sofaUser"));
				resultMap.put("lastUser", result.get("lastUser"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询他们都在玩纪录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 本场活动参与的用户记录
	 */
	@ResponseBody 
	@RequestMapping(value="/findParticipateByRaiders",produces="text/html;charset=UTF-8")
	public String findParticipateByRaiders(HttpServletRequest request,String raiderId,
			String page,String pageSize,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isBlank(raiderId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", "101");
			resultMap.put("errorMsg", "参数不能为空");
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		if(StringUtils.isBlank(page)){
			page="1";
		}
		if(StringUtils.isBlank(pageSize)){
			pageSize="20";
		}
		try{
			Map<String,Object> result=raiderGoodManager.findParticipateByRaiders(raiderId, page, pageSize);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("场活动参与的用户记录："+result);
				resultMap.put("userList", result.get("userList"));
				resultMap.put("totalPage", result.get("totalPage"));
				resultMap.put("totalCount", result.get("totalCount"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询场活动参与的用户记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 夺宝分享
	 */
	@ResponseBody 
	@RequestMapping(value="/spreadSendRaiders",produces="text/html;charset=UTF-8")
	public String spreadSendRaiders(HttpServletRequest request,String raiderId,String participateId,
			String checkLogin,Integer personCount,String jsoncallback,String content){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		logger.info("分享内容："+content);
		String xiuUserId=SessionUtil.getUserId(request);
	//	String xiuUserId="2012465815";
		if(StringUtils.isBlank(xiuUserId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", 10080);
			resultMap.put("errorMsg", "请登录后分享");
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		//判断传入的参数是否为空
		if(StringUtils.isBlank(raiderId) || StringUtils.isBlank(participateId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		boolean check = false;
		if(StringUtils.isNotBlank(checkLogin)){
			check = Boolean.parseBoolean(checkLogin);
		}
		// 检查登录
		if(check){
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			Long userId = Long.parseLong(SessionUtil.getUserId(request)); //走秀用户id
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(userId);
			if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
				resultMap.put("result", false);
				resultMap.put("errorCode", "-1");
				resultMap.put("errorMsg", "根据user_id获取不到用户信息");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
		}
		try{
			Map<String,Object> result=raiderGoodManager.spreadSendRaiders(raiderId, participateId, check, xiuUserId, personCount,content);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("分享记录："+result);
				resultMap.put("url", result.get("url"));
				resultMap.put("packetsReceiveVo", result.get("packetsReceiveVo"));
				resultMap.put("personCount", result.get("personCount"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("分享记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 获取分享夺宝详情
	 */
	@ResponseBody 
	@RequestMapping(value="/accessRaidersSharaIndex",produces="text/html;charset=UTF-8")
	public String accessRaidersSharaIndex(HttpServletRequest request,String raiderId,
			String participateId, String code, String checkLogin,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//判断传入的参数是否为空
		if(StringUtils.isBlank(raiderId) || StringUtils.isBlank(participateId) || 
				StringUtils.isBlank(code)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raiderGoodManager.accessRaidersSharaIndex(raiderId, participateId, code);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("分享详情："+result);
				resultMap.put("shareRaiders", result.get("shareRaiders"));
				resultMap.put("packetChildren", result.get("packetChildren"));
				resultMap.put("time", result.get("time"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("分享详情异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 领取夺宝号码
	 */
	@ResponseBody 
	@RequestMapping(value="/supportSendRaiders",produces="text/html;charset=UTF-8")
	public String supportSendRaiders(HttpServletRequest request,String raiderId,String participateId,
			String code,String mobile,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//判断传入数据是否为空
		if(StringUtils.isBlank(raiderId) || StringUtils.isBlank(participateId) ||
				StringUtils.isBlank(code) || StringUtils.isBlank(mobile)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		boolean check = false; //默认校验登陆
		if(StringUtils.isNotBlank(request.getParameter("checkLogin"))){
			check = Boolean.parseBoolean(request.getParameter("checkLogin")); //是否校验登陆
		}
		String ip=HttpUtil.getRemoteIpAddr(request);//获得IP
		String ipAdr="广东深圳";
		String xiuUserId = null;
		// 检查登录
		if(check){
			String msg = isLogin(request, jsoncallback);
			if(StringUtils.isNotBlank(msg)){
				return msg;
			}
			xiuUserId = SessionUtil.getUserId(request); //走秀用户id
			UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(Long.parseLong(xiuUserId));
			if(userBaseDTO == null || StringUtils.isBlank(userBaseDTO.getLogonName())){
				resultMap.put("result", false);
				resultMap.put("errorCode", "-1");
				resultMap.put("errorMsg", "根据user_id获取不到用户信息");
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
		}
		try{
			Map<String,Object> result=raiderGoodManager.supportSendRaiders(raiderId, participateId, code, mobile,ip,ipAdr);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("分享记录："+result);
				resultMap.put("raiderList", result.get("raiderList"));
				resultMap.put("packetChildren", result.get("packetChildren"));
				resultMap.put("count", result.get("count"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("分享记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 活动详情计算规则
	 */
	@ResponseBody 
	@RequestMapping(value="/getLotteryRule",produces="text/html;charset=UTF-8")
	public String getLotteryRule(HttpServletRequest request,String raiderId,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		//判断传入数据是否为空
		if(StringUtils.isBlank(raiderId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raiderGoodManager.getLotteryRule(raiderId);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("计算规则："+result);
				resultMap.put("lotteryIndex", result.get("lotteryIndex"));
				resultMap.put("raiderList", result.get("raiderList"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("活动详情计算规则异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 根据订单ID查询参与记录信息
	 * @param request
	 * @param orderId
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getParticipateByOrderId",produces="text/html;charset=UTF-8")
	public String getParticipateByOrderId(HttpServletRequest request,String orderId,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isBlank(orderId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raiderGoodManager.getParticipateByOrderId(orderId);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("计算规则："+result);
				resultMap.put("participateVO", result.get("participateVO"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
			
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("根据OrderId查询参与记录信息异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
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
	 * 根据participateId查询购买次数
	 * @param request
	 * @param participateId
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findSharaNum",produces="text/html;charset=UTF-8")
	public String findSharaNum(HttpServletRequest request,String participateId,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isBlank(participateId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raiderGoodManager.findSharaNum(participateId);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("购买个数："+result);
				resultMap.put("participateNumber", result.get("participateNumber"));
				resultMap.put("statusType", result.get("statusType"));
				resultMap.put("refund", result.get("refund"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("根据participateId查询购买次数异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
