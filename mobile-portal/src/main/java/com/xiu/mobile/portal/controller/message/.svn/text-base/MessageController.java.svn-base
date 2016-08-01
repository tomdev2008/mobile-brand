package com.xiu.mobile.portal.controller.message;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.maker.dointerface.dto.MakerUserDTO;
import com.xiu.maker.dointerface.dto.QueryUserDetailDTO;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIMessageCenterManager;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

	private static Logger logger = Logger.getLogger(MessageController.class);
	
	@Autowired
	EIMessageCenterManager eIMessageCenterManager;
	
	/**
	 * 查询该用户下未读消息数
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/unreadqty", produces = "text/html;charset=UTF-8")
	public String unreadqty(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("获取用户未读消息数，前端提交参数：params=" + request.getQueryString());
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		Map<String, Object> returnMap = eIMessageCenterManager.queryUnReadMsgCount(Integer.parseInt(xiuUserId));
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 查询消息中心分类列表信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/center", produces = "text/html;charset=UTF-8")
	public String center(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("查询消息中心分类列表信息：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		Map<String, Object> returnMap = eIMessageCenterManager.queryMsgCenterInfo(Integer.parseInt(xiuUserId));
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 查询某个分类下的消息列表
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/msgCategorylist", produces = "text/html;charset=UTF-8")
	public String msgCategorylist(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("查询某个分类下的消息列表：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		String categoryId=request.getParameter("categoryId");
		String page = request.getParameter("page");
		String pageSize=request.getParameter("pageSize");
		if(StringUtils.isBlank(xiuUserId) || StringUtils.isBlank(categoryId) ||
				StringUtils.isBlank(page) || StringUtils.isBlank(pageSize)){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		returnMap = eIMessageCenterManager.queryMsgListByCategory(Integer.parseInt(xiuUserId),Integer.parseInt(categoryId)
				,Integer.parseInt(page),Integer.parseInt(pageSize));
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 查询某条消息详情
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/detail", produces = "text/html;charset=UTF-8")
	public String detail(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("查询某条消息详情，前端提交参数：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		String categoryId=request.getParameter("categoryId");
		if(StringUtils.isBlank(request.getParameter("msgId")) ||
				StringUtils.isBlank(xiuUserId)|| StringUtils.isBlank(categoryId) ){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		returnMap = eIMessageCenterManager.queryMsgDetail(Integer.parseInt(xiuUserId),Integer.parseInt(categoryId),Integer.parseInt(request.getParameter("msgId")));
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 更新消息已读：
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/read", produces = "text/html;charset=UTF-8")
	public String read(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("更新消息为已读，前端提交参数：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		String categoryId=request.getParameter("categoryId");
		String msgId=request.getParameter("msgId");
		if(StringUtils.isBlank(xiuUserId)|| StringUtils.isBlank(categoryId) ){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		Integer returnMsgId=StringUtils.isNotBlank(msgId) ? Integer.parseInt(msgId) : null ;
		returnMap = eIMessageCenterManager.readMsg(Integer.parseInt(xiuUserId),Integer.parseInt(categoryId),returnMsgId);
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
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
	 * 查询用户所有分类消息明细状况
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryMsgCategory", produces = "text/html;charset=UTF-8")
	public String queryMsgCategory(HttpServletRequest request, String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("查询某个分类下的消息列表：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId =SessionUtil.getUserId(request);
		if(StringUtils.isBlank(xiuUserId) ){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		returnMap = eIMessageCenterManager.queryMsgCategory(Integer.parseInt(xiuUserId));
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 更新用户分类接收状态
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateMsgCategoryStatus", produces = "text/html;charset=UTF-8")
	public String updateMsgCategoryStatus(HttpServletRequest request, String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("查询某个分类下的消息列表：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId =SessionUtil.getUserId(request);
		String categoryId=request.getParameter("categoryId");
		String status=request.getParameter("status");
		if(StringUtils.isBlank(xiuUserId) || StringUtils.isBlank(categoryId) || StringUtils.isBlank(status)){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		returnMap = eIMessageCenterManager.updateMsgCategoryStatus(Integer.parseInt(xiuUserId),Integer.parseInt(categoryId),Integer.parseInt(status));
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 清空用户下的所有消息
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/cleanUserMsg", produces = "text/html;charset=UTF-8")
	public String cleanUserMsg(HttpServletRequest request, String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId =SessionUtil.getUserId(request);
		logger.info("清空用户的消息列表 ,xiuUserId=" +xiuUserId);
	
		returnMap = eIMessageCenterManager.cleanUserMsg(Integer.parseInt(xiuUserId));

		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
}
