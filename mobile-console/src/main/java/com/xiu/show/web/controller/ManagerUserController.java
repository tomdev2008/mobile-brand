package com.xiu.show.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.xiu.show.core.model.ManagerActionRecordModel;
import com.xiu.show.core.model.ManagerUserModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.service.IManagerUserService;

/**
 * 前台管理员管理
 * @author coco.long
 * @time	2015-06-19
 */
@AuthRequired
@Controller
@RequestMapping(value = "/managerUser")
public class ManagerUserController {

	private static final XLogger logger = XLoggerFactory.getXLogger(ManagerUserController.class);
	
	@Autowired
	private IManagerUserService managerUserService;
	
	/**
	 * 查询前台管理员列表
	 * @param userId
	 * @param account
	 * @param petName
	 * @param phone
	 * @param email
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String userId, String account, String petName, String mobile, String email, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("account", account);
		paraMap.put("petName", petName);
		paraMap.put("mobile", mobile);
		paraMap.put("email", email);
		
		List<ManagerUserModel> managerUserList = null;
		try {
			managerUserList = managerUserService.getManagerUserList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询前台管理员列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("account", account);
		model.addAttribute("petName", petName);
		model.addAttribute("mobile", mobile);
		model.addAttribute("email", email);
		model.addAttribute("managerUserList", managerUserList);
		
		return "pages/show/managerUser/list";
	}
	
	/**
	 * 修改前台管理员口令
	 * @param userId
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String updatePassword(String userId, String deviceId, String password, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("password", password);
		paraMap.put("deviceId", deviceId);
		
		try {
			if(userId == null || userId.equals("") || password == null || password.equals("") || deviceId == null || deviceId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				boolean result = managerUserService.updatePassword(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("修改前台管理员口令成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("修改前台管理员口令失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("修改前台管理员口令异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 解除授权
	 * @param userId
	 * @param deviceId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/revokeAuthority", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String revokeAuthority(String userId, String deviceId, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("deviceId", deviceId);
		
		try {
			if(userId == null || userId.equals("") || deviceId == null || deviceId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				boolean result = managerUserService.revokeAuthority(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("解除授权成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("解除授权失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("解除授权异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 修改备注
	 * @param userId
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateRemark", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String updateRemark(String userId, String deviceId, String remark, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("remark", remark);
		paraMap.put("deviceId", deviceId);
		
		try {
			if(userId == null || userId.equals("") || deviceId == null || deviceId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				boolean result = managerUserService.updateRemark(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("修改前台管理员备注成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("修改前台管理备备注失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("修改前台管理员备注异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 查询管理员动作记录列表
	 * @param userId
	 * @param account
	 * @param petName
	 * @param mobile
	 * @param email
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "recordList", method = RequestMethod.GET)
	public String recordList(String userId, String account, String petName, String mobile, String email, String beginTime, String endTime, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("account", account);
		paraMap.put("petName", petName);
		paraMap.put("mobile", mobile);
		paraMap.put("email", email);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		List<ManagerActionRecordModel> recordList = null;
		try {
			recordList = managerUserService.getActionRecordList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询管理员动作记录列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("account", account);
		model.addAttribute("petName", petName);
		model.addAttribute("mobile", mobile);
		model.addAttribute("email", email);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("recordList", recordList);
		
		return "pages/show/managerUser/recordList";
	}
	
	/**
	 * 授权管理员
	 * @param userId
	 * @param deviceId
	 * @param terminal
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/authority", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String authority(String userId, String deviceId, String terminal, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("deviceId", deviceId);
		paraMap.put("terminal", terminal);
		
		try {
			if(userId == null || userId.equals("") || deviceId == null || deviceId.equals("") || terminal == null || terminal.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				boolean result = managerUserService.authority(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("授权管理员成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("授权管理员失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("授权管理员异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
