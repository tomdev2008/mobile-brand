package com.xiu.show.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.MessageSystemModel;
import com.xiu.show.core.model.OperateLogModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.service.IMessageManagerService;

/**
 * 消息管理
 * @author coco.long 
 * @time	2015-08-17
 */
@AuthRequired
@Controller
@RequestMapping(value = "/messageManager")
public class MessageManagerController {

	private static final XLogger logger = XLoggerFactory.getXLogger(MessageManagerController.class);
	
	@Autowired
	private IMessageManagerService messageManagerService;
	
	/**
	 * 查询系统消息列表
	 * @param messageId
	 * @param beginTime
	 * @param endTime
	 * @param sendName
	 * @param messageStatus
	 * @param readFlag
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String messageId, String beginTime, String endTime, String sendName, String messageStatus, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(messageId)) {
			String messageIds = messageId.replaceAll("\r\n",",");
			paraMap.put("messageId", messageIds.split(","));
		}
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		paraMap.put("sendName", sendName);
		paraMap.put("messageStatus", messageStatus);
		
		try {
			
			//查询系统消息列表
			List<MessageSystemModel> messageList = messageManagerService.getSystemMessageList(paraMap, page);
			
			model.addAttribute("messageList", messageList);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询系统消息列表异常！", e);
		}
		
		model.addAttribute("messageId", messageId);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("sendName", sendName);
		model.addAttribute("messageStatus", messageStatus);
		return "pages/show/message/systemMessageList";
	}
	
	/**
	 * 添加系统消息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request,HttpServletResponse response) {
		return "pages/show/message/addSystemMsg";
	}
	
	/**
	 * 发布系统消息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		String receiveType = request.getParameter("receiveType");
		String receiver = request.getParameter("receiver");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String sendTime = request.getParameter("sendTime");
		String linkType = request.getParameter("linkType");
		String linkObject = request.getParameter("linkObject");
		String remark = request.getParameter("remark");
		
		if(StringUtils.isNotBlank(receiveType) && "2".equals(receiveType)) {
			paraMap.put("popularity", receiver);
		} else if("3".equals(receiveType)){
			String userIds = receiver.replaceAll("\r\n",",");
			paraMap.put("receiveUserId", userIds.split(","));
		}
		paraMap.put("receiver", receiver);
		paraMap.put("receiveType", receiveType);
		paraMap.put("title", title);
		paraMap.put("content", content);
		paraMap.put("sendTime", sendTime);
		paraMap.put("linkType", linkType);
		paraMap.put("linkObject", linkObject);
		paraMap.put("remark", remark);
		
		User user = AdminSessionUtil.getUser(request);
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			//发布系统消息
			Map<String, Object> result = messageManagerService.addSystemMessage(paraMap);
			
			boolean flag = (Boolean) result.get("result");
			
			if(flag) {
				response.getWriter().print("<script> alert('发布系统消息成功!'); window.close(); </script> ");
			} else {
				String errorMsg = (String) result.get("errorMsg");
				response.getWriter().print("<script> alert('发布系统消息失败!错误原因："+errorMsg+"'); </script> ");
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("发布系统消息异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				logger.error("打印异常！", e1);
			}
		}
		
		return "redirect:/messageManager/list";
	}
	
	/**
	 * 检测话题是否有效
	 * @param topicId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkTopicEffectivity", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String checkTopicEffectivity(String topicId, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		
		if(topicId == null || topicId.equals("") ) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("参数不完整！");
		}
		
		try {
			//检测话题是否有效
			Map result = messageManagerService.checkTopicEffectivity(topicId);
			
			json.setScode(JsonPackageWrapper.S_OK);
			json.setData(result);
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("检测话题是否有效异常！", e);
		}
		
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 检测秀是否有效
	 * @param showId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkShowEffectivity", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String checkShowEffectivity(String showId, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		
		if(showId == null || showId.equals("") ) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("参数不完整！");
		}
		
		try {
			//检测话题是否有效
			Map result = messageManagerService.checkShowEffectivity(showId);
			
			json.setScode(JsonPackageWrapper.S_OK);
			json.setData(result);
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("检测秀是否有效异常！", e);
		}
		
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 查询系统消息
	 * @param messageId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(String messageId, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		try {
			paraMap.put("id", messageId);
			
			//查询系统消息
			MessageSystemModel message = messageManagerService.getSystemMessage(paraMap);
			
			model.addAttribute("messageId", messageId);
			model.addAttribute("message", message);
			model.addAttribute("pageType", "message");	//页面类型字段
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询系统消息异常！", e);
		}
		
		return "pages/show/message/systemMessageInfo";
	}
	
	/**
	 * 查询系统消息操作日志
	 * @param messageId
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "operateInfo", method = RequestMethod.GET)
	public String operateInfo(String messageId, Model model, Page<?> page, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		List<OperateLogModel> operateLogList = null;
		try {
			paraMap.put("id", messageId);
			
			//查询系统消息操作日志
			operateLogList = messageManagerService.getSysMsgOperateLogList(paraMap, page);
			
			model.addAttribute("messageId", messageId);
			model.addAttribute("operateLogList", operateLogList);
			model.addAttribute("pageType", "operatelog");	//页面类型字段
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询系统消息操作日志异常！", e);
		}
		
		return "pages/show/message/systemMessageLog";
	}
	
	/**
	 * 删除系统消息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteSysMsg", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String deleteSysMsg(String id, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		
		if(id == null || id.equals("") ) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("参数不完整！");
		}
		
		try {
			Map map = new HashMap();
			map.put("id", id);
			map.put("deleteFlag", ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			
			User user = AdminSessionUtil.getUser(request);
			map.put("userId", user.getId().toString());
			map.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
			
			//删除系统消息
			Map result = messageManagerService.deleteSysMsg(map);
			
			json.setScode(JsonPackageWrapper.S_OK);
			json.setData(result);
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("删除系统消息异常！", e);
		}
		
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
