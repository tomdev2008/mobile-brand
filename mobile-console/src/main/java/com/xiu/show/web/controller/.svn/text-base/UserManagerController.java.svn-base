package com.xiu.show.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.CommentModel;
import com.xiu.show.core.model.OperateLogModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.PopularityRecordModel;
import com.xiu.show.core.model.ReportModel;
import com.xiu.show.core.model.ShowModel;
import com.xiu.show.core.model.ShowUserModel;
import com.xiu.show.core.service.IShowCommentManagerService;
import com.xiu.show.core.service.IShowManagerService;
import com.xiu.show.core.service.IShowUserManagerService;

/**
 * 秀用户管理
 * @author coco.long
 * @time	2015-06-13
 */
@AuthRequired
@Controller
@RequestMapping(value = "/userManager")
public class UserManagerController {

	private static final XLogger logger = XLoggerFactory.getXLogger(UserManagerController.class);
	
	@Autowired
	private IShowUserManagerService userManagerService;
	
	@Autowired
	private IShowManagerService showManagerService;
	
	@Autowired
	private IShowCommentManagerService showCommentManagerService;
	
	/**
	 * 查询秀用户列表
	 * @param userId
	 * @param petName
	 * @param sex
	 * @param publishShow
	 * @param publishComment
	 * @param status
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String userId, String petName, String sex, String publishShow, String publishComment, String status, 
			String beginTime, String endTime, String talent, String popularityOrder,String showNumOrder,String recommendShowNumOrder,
			String followNumOrder,String fansNumOrder,
			Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			String userIds = userId.replaceAll("\r\n",",");
			paraMap.put("userId", userIds.split(","));
		}
		paraMap.put("petName", petName);
		paraMap.put("sex", sex);
		paraMap.put("publishShow", publishShow);
		paraMap.put("publishComment", publishComment);
		paraMap.put("status", status);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		paraMap.put("talent", talent);
		paraMap.put("popularityOrder", popularityOrder);
		paraMap.put("showNumOrder", showNumOrder);
		paraMap.put("recommendShowNumOrder", recommendShowNumOrder);
		paraMap.put("followNumOrder", followNumOrder);
		paraMap.put("fansNumOrder", fansNumOrder);
		
		List<ShowUserModel> showUserList = null;
		try {
			// 更新秀用户的发布秀状态、发布评论状态
			Map<String, String> userMap = new HashMap<String, String>();
			userManagerService.updateShowUserPublishStatus(userMap);
			
			// 查询秀用户列表
			showUserList = userManagerService.getShowUserList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("petName", petName);
		model.addAttribute("sex", sex);
		model.addAttribute("publishShow", publishShow);
		model.addAttribute("publishComment", publishComment);
		model.addAttribute("status", status);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("talent", talent);
		model.addAttribute("showUserList", showUserList);
		model.addAttribute("popularityOrder", popularityOrder);
		model.addAttribute("showNumOrder", showNumOrder);
		model.addAttribute("recommendShowNumOrder", recommendShowNumOrder);
		model.addAttribute("followNumOrder", followNumOrder);
		model.addAttribute("fansNumOrder", fansNumOrder);
		
		
		return "pages/show/userManager/list";
	}
	
	/**
	 * 查询秀用户详情
	 * @param userId
	 * @param userPageType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userDetail", method = RequestMethod.GET)
	public String userDetail(String userId, String userPageType, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		ShowUserModel showUser = new ShowUserModel();
		try {
			// 更新秀用户的发布秀状态、发布评论状态
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("userId", userId);
			userManagerService.updateShowUserPublishStatus(userMap);
						
			 // 查询秀用户详情
			 showUser = userManagerService.getShowUserInfo(paraMap);
			 
			 Map showDeleteInfoMap = userManagerService.getUserShowDeleteInfo(paraMap);			//秀删除信息
			 Map showCheckInfoMap = userManagerService.getUserShowCheckInfo(paraMap);			//秀审核信息
			 Map commentDeleteInfoMap = userManagerService.getUserCommentDeleteInfo(paraMap);	//评论删除信息
			 Map reportInfoMap = userManagerService.getUserReportInfo(paraMap);					//举报信息

			 model.addAttribute("showDeleteInfo", showDeleteInfoMap);
			 model.addAttribute("showCheckInfo", showCheckInfoMap);
			 model.addAttribute("commentDeleteInfo", commentDeleteInfoMap);
			 model.addAttribute("reportInfo", reportInfoMap);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户信息异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("showUser", showUser);
		
		return "pages/show/userManager/userDetail";
	}
	
	/**
	 * 秀用户人气变更记录
	 * @param userId
	 * @param userPageType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserPopularity", method = RequestMethod.GET)
	public String viewUserPopularity(String userId, String userPageType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		List<PopularityRecordModel> popularityList = null;
		try {
			// 查询秀用户人气变更记录
			popularityList = userManagerService.getUserPopularityRecordList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户人气信息异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("popularityList", popularityList);
		
		return "pages/show/userManager/userPopularity";
	}
	
	/**
	 * 查看用户秀列表
	 * @param userId
	 * @param userPageType
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserShow", method = RequestMethod.GET)
	public String viewUserShow(String userId, String userPageType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		List<ShowModel> showList = null;
		try {
			// 查询用户秀列表 
			showList = userManagerService.getUserShowList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询用户秀列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("showList", showList);
		
		return "pages/show/userManager/userShow";
	}
	
	/**
	 * 查询秀用户关注列表
	 * @param userId
	 * @param userPageType
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserFollow", method = RequestMethod.GET)
	public String viewUserFollow(String userId, String userPageType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		List followList = null;
		try {
			// 查询秀用户关注列表
			followList = userManagerService.getUserFollowList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户关注列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("followList", followList);
		
		return "pages/show/userManager/userFollow";
	}
	
	/**
	 * 查询秀用户粉丝列表
	 * @param userId
	 * @param userPageType
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserFans", method = RequestMethod.GET)
	public String viewUserFans(String userId, String userPageType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		List fansList = null;
		try {
			// 查询秀用户关注列表
			fansList = userManagerService.getUserFansList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户粉丝列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("fansList", fansList);
		
		return "pages/show/userManager/userFans";
	}
	
	/**
	 * 查询秀用户评论列表
	 * @param userId
	 * @param userPageType
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserComment", method = RequestMethod.GET)
	public String viewUserComment(String userId, String userPageType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		List<CommentModel> commentList = null;
		try {
			// 查询秀用户关注列表
			commentList = userManagerService.getUserCommentList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户评论列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("commentList", commentList);
		
		return "pages/show/userManager/userComment";
	}
	
	/**
	 * 查询秀用户举报列表
	 * @param userId
	 * @param userPageType
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserReport", method = RequestMethod.GET)
	public String viewUserReport(String userId, String userPageType, String infoType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		paraMap.put("infoType", infoType);
		
		List<ReportModel> userReportedList = null;
		try {
			// 查询秀用户被举报列表
			userReportedList = userManagerService.getUserReportedList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀用户被举报列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("infoType", infoType);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("userReportedList", userReportedList);
		
		return "pages/show/userManager/userReport";
	}
	
	/**
	 * 查询用户操作信息列表
	 * @param userId
	 * @param userPageType
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "viewUserOperateLog", method = RequestMethod.GET)
	public String viewUserOperateLog(String userId, String userPageType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("userId", userId);
		
		List<OperateLogModel> operateLogList = null;
		try {
			// 查询用户操作信息列表
			operateLogList = userManagerService.getUserOperateLogList(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询用户操作信息列表异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userPageType", userPageType);
		model.addAttribute("operateLogList", operateLogList);
		
		return "pages/show/userManager/userOperateLog";
	}
	
	/**
	 * 禁止发布秀
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forbidPublishShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String forbidPublishShow(String userId, String beginTime, String endTime, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		paraMap.put("beginTime", beginTime + " 00:00:00");
		paraMap.put("endTime", endTime + " 23:59:59");
		paraMap.put("forbiddenPublish", -1);	//禁止发布秀
		
//		User user = AdminAuthInfoHolder.getUserAuthInfo();	//当前系统登录用户
		User user = AdminSessionUtil.getUser(request);
		
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || beginTime == null || beginTime.equals("") || endTime == null || endTime.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.forbidPublishShow(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("禁止发布秀成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("禁止发布秀失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("禁止发布秀异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 禁止发表评论
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forbidComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String forbidComment(String userId, String beginTime, String endTime, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		paraMap.put("beginTime", beginTime + " 00:00:00");
		paraMap.put("endTime", endTime + " 23:59:59");
		paraMap.put("forbiddenComment", -1);	//禁止发表评论
		
		User user = AdminSessionUtil.getUser(request);
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || beginTime == null || beginTime.equals("") || endTime == null || endTime.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.forbidComment(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("禁止发表评论成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("禁止发表评论失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("禁止发表评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 允许发布秀
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/allowPublishShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String allowPublishShow(String userId, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		paraMap.put("forbiddenPublish", 1);	//允许发布秀
		
		
		User user = AdminSessionUtil.getUser(request);
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.allowPublishShow(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("允许发布秀成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("允许发布秀失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("允许发布秀异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 允许发表评论
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/allowComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String allowComment(String userId, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		paraMap.put("forbiddenComment", 1);	//允许发表评论
		
		User user = AdminSessionUtil.getUser(request);
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.allowComment(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("允许发表评论成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("允许发表评论失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("允许发表评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 冻结用户
	 * @param userId
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/frozenUser", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String frozenUser(String userId, String reason, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		
	    User user = AdminSessionUtil.getUser(request);
	    paraMap.put("userId", user.getId().toString());
	    paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || reason == null || reason.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				paraMap.put(ShowConstant.SHOW_OPERATE_REMARK, reason);
				
				boolean result = userManagerService.frozenShowUser(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("封号成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("封号失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("封号异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 解封帐号
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/unFrozenUser", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String unFrozenUser(String userId,  Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		
	    User user = AdminSessionUtil.getUser(request);
	    paraMap.put("userId", user.getId().toString());
	    paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.unFrozenShowUser(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("解封帐号成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("解封帐号失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("解封帐号异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 设置达人
	 * @param userId
	 * @param talent
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/grantUserTalent", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String grantUserTalent(String userId, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		
	    User user = AdminSessionUtil.getUser(request);
	    paraMap.put("userId", user.getId().toString());
	    paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.grantUserTalent(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("设置达人成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("设置达人失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("设置达人异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 取消达人
	 * @param userId
	 * @param talent
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/revokeUserTalent", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String revokeUserTalent(String userId, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		
	    User user = AdminSessionUtil.getUser(request);
	    paraMap.put("userId", user.getId().toString());
	    paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId);
				
				boolean result = userManagerService.revokeUserTalent(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("取消达人成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("取消达人失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("取消达人异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 删除秀用户所有的秀
	 * @param userId
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteAllShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String deleteAllShow(String userId, String reason, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		paraMap.put(ShowConstant.DELETE_FLAG, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		
		User user = AdminAuthInfoHolder.getUserAuthInfo();	//当前系统登录用户
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || reason == null || reason.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_REMARK, reason);
				
				boolean result = showManagerService.deleteAllShow(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除秀用户所有的秀成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除秀用户所有的秀失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("删除秀用户所有的秀异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 删除秀用户所有的评论
	 * @param userId
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteAllComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String deleteAllComment(String userId, String reason, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("showUserId", userId);
		paraMap.put(ShowConstant.DELETE_FLAG, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		
		User user = AdminAuthInfoHolder.getUserAuthInfo();	//当前系统登录用户
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || reason == null || reason.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_REMARK, reason);
				
				boolean result = showCommentManagerService.deleteAllComment(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除秀用户所有的评论成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除秀用户所有的评论失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("删除秀用户所有的评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量禁止发布秀
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchForbidPublishShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchForbidPublishShow(String userId, String beginTime, String endTime, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		paraMap.put("beginTime", beginTime + " 00:00:00");
		paraMap.put("endTime", endTime + " 23:59:59");
		paraMap.put("forbiddenPublish", -1);	//禁止发布秀
		
		  User user=AdminSessionUtil.getUser(request);
		  paraMap.put("userId", user.getId().toString());
		  paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || beginTime == null || beginTime.equals("") || endTime == null || endTime.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId.split(","));
				
				boolean result = userManagerService.batchForbidPublishShow(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("禁止发布秀成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("禁止发布秀失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("禁止发布秀异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量禁止评论
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchForbidComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchForbidComment(String userId, String beginTime, String endTime, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		paraMap.put("beginTime", beginTime + " 00:00:00");
		paraMap.put("endTime", endTime + " 23:59:59");
		paraMap.put("forbiddenComment", -1);	//禁止发表评论
		
		  User user=AdminSessionUtil.getUser(request);
		  paraMap.put("userId", user.getId().toString());
		  paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || beginTime == null || beginTime.equals("") || endTime == null || endTime.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId.split(","));
				
				boolean result = userManagerService.batchForbidComment(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("禁止发表评论成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("禁止发表评论失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("禁止发表评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量允许发布秀
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchAllowPublishShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchAllowPublishShow(String userId, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		paraMap.put("forbiddenPublish", 1);	//允许发布秀
		
		  User user=AdminSessionUtil.getUser(request);
		  paraMap.put("userId", user.getId().toString());
		  paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId.split(","));
				
				boolean result = userManagerService.batchAllowPublishShow(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("允许发布秀成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("允许发布秀失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("允许发布秀异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量允许评论
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchAllowComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchAllowComment(String userId, Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		paraMap.put("forbiddenComment", 1);	//允许发表评论
		
		  User user=AdminSessionUtil.getUser(request);
		  paraMap.put("userId", user.getId().toString());
		  paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId.split(","));
				
				boolean result = userManagerService.batchAllowComment(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("允许发表评论成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("允许发表评论失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("允许发表评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量冻结秀用户
	 * @param userId
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchFrozenUser", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchFrozenUser(String userId, String reason, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		
		User user = AdminAuthInfoHolder.getUserAuthInfo();	//当前系统登录用户
		paraMap.put("userId", user.getId().toString());
	    paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		try {
			if(userId == null || userId.equals("") || reason == null || reason.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId.split(","));
				paraMap.put(ShowConstant.SHOW_OPERATE_REMARK, reason);
				
				boolean result = userManagerService.batchFrozenShowUser(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("封号成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("封号失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("封号异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量解冻秀用户
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchUnFrozenUser", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchUnFrozenUser(String userId,  Model model, HttpServletRequest request){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		
		   User user=AdminSessionUtil.getUser(request);
		    paraMap.put("userId", user.getId().toString());
		    paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_OBJECT, userId.split(","));
				
				boolean result = userManagerService.batchUnFrozenShowUser(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("解封帐号成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("解封帐号失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("解封帐号异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量删除所有秀
	 * @param userId
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchDeleteAllShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchDeleteAllShow(String userId, String reason, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		paraMap.put(ShowConstant.DELETE_FLAG, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		
		User user = AdminAuthInfoHolder.getUserAuthInfo();	//当前系统登录用户
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());

		try {
			if(userId == null || userId.equals("") || reason == null || reason.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_REMARK, reason);
				
				boolean result = showManagerService.batchDeleteAllShow(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除秀用户所有的秀成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除秀用户所有的秀失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("删除秀用户所有的秀异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 批量删除所有评论
	 * @param userId
	 * @param reason
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/batchDeleteAllComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String batchDeleteAllComment(String userId, String reason, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(userId)) {
			paraMap.put("showUserId", userId.split(","));
		}
		paraMap.put(ShowConstant.DELETE_FLAG, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		
		User user = AdminAuthInfoHolder.getUserAuthInfo();	//当前系统登录用户
		paraMap.put("userId", user.getId().toString());
		paraMap.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
		
		try {
			if(userId == null || userId.equals("") || reason == null || reason.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				//日志参数
				paraMap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				paraMap.put(ShowConstant.SHOW_OPERATE_REMARK, reason);
				
				boolean result = showCommentManagerService.batchDeleteAllComment(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除秀用户所有的评论成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除秀用户所有的评论失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("删除秀用户所有的评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
