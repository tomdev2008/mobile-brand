package com.xiu.mobile.portal.controller.cps;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIMobileCpsManager;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.uuc.facade.dto.UserBaseDTO;

@Controller
@RequestMapping("/giftcps")
public class MobileCpsController extends BaseController  {

	private static Logger logger = Logger.getLogger(MobileCpsController.class);
	
	@Autowired
	private EIMobileCpsManager cpsManager;
	@Autowired
	private IUserService userService;
	
	/**
	 * 礼包cps个人中心
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/personCenter", produces = "text/html;charset=UTF-8")
	public String personCenter(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		String xiuUserId = SessionUtil.getUserId(request);
		logger.info("礼包cps个人中心，提交参数：xiuUserId: " + xiuUserId + ", " + request.getQueryString());
		
		String userPhoneUrl = request.getParameter("userPhoneUrl");
		
		Map<String, Object> resultMap = cpsManager.queryPersonCpsCenterInfo(Long.valueOf(xiuUserId), userPhoneUrl);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 分享礼包cps回调
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/shareCallback", produces = "text/html;charset=UTF-8")
	public String shareCallback(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Long xiuUserId = Long.valueOf(SessionUtil.getUserId(request));
		
		UserBaseDTO userBaseDTO = userService.getUserBasicInfoByUserId(xiuUserId);
		String mobile = null;
		if(userBaseDTO != null){
			mobile = userBaseDTO.getMobile();
		}
		logger.info("分享礼包回调，提交参数：" + xiuUserId + "，mobile：" + mobile);
		
		Map<String, Object> resultMap = cpsManager.shareCallback(Long.valueOf(xiuUserId), mobile);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 返现明细
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryRebateDetail", produces = "text/html;charset=UTF-8")
	public String queryRebateDetail(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		String xiuUserId = SessionUtil.getUserId(request);
		logger.info("获取返现明细，提交参数：xiuUserId: " + xiuUserId + ", " + request.getQueryString());
		
		String status = request.getParameter("status");
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		Map<String, Object> resultMap = cpsManager.queryRebateDetail(Long.valueOf(xiuUserId), status, page, pageSize);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 已领取礼包明细
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryGiftbagDetail", produces = "text/html;charset=UTF-8")
	public String queryGiftbagDetail(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		String xiuUserId = SessionUtil.getUserId(request);
		logger.info("获取已领取礼包明细，提交参数：xiuUserId: " + xiuUserId + ", " + request.getQueryString());
		
		String status = request.getParameter("status");
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		
		Map<String, Object> resultMap = cpsManager.queryGiftbagDetail(Long.valueOf(xiuUserId), status, page, pageSize);
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
