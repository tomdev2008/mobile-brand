package com.xiu.show.web.controller;

import java.io.OutputStream;
import java.util.ArrayList;
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
import com.xiu.common.web.utils.ExcelUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.ObjectUtil;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.ShowModel;
import com.xiu.show.core.model.ShowStatisticsModel;
import com.xiu.show.core.service.IShowStatisticsManagerService;

/**
 * 秀统计管理
 * @author coco.long
 * @time	2015-07-16
 */
@AuthRequired
@Controller
@RequestMapping(value = "/showStatistics")
public class ShowStatisticsController {

	private static final XLogger logger = XLoggerFactory.getXLogger(ShowStatisticsController.class);
	
	@Autowired
	private IShowStatisticsManagerService showStatisticsManagerService;
	
	/**
	 * 查询秀统计结果列表
	 * @param type
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String statisticsType, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("statisticsType", statisticsType);
		
		List<ShowStatisticsModel> statisticsList = null;
		try {
			if(StringUtils.isNotBlank(statisticsType)) {
				//查询秀统计结果列表
				statisticsList = showStatisticsManagerService.getShowStatisticsList(paraMap, page);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀统计结果列表异常！", e);
		}
		model.addAttribute("statisticsType", statisticsType);
		model.addAttribute("statisticsList", statisticsList);
		
		return "pages/show/showStatistics/list";
	}
	
	/**
	 * 更新统计数据
	 * @param statisticsType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateStatistics", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String updateStatistics(String statisticsType, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("statisticsType", statisticsType);
		
		try {
			if(statisticsType == null || statisticsType.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				
				boolean result = showStatisticsManagerService.updateStatistics(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("更新统计数据成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("更新统计数据失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("更新统计数据异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 删除统计数据
	 * @param statisticsType
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteStatistics", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String deleteStatistics(String statisticsType, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("statisticsType", statisticsType);
		
		try {
			if(statisticsType == null || statisticsType.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				
				boolean result = showStatisticsManagerService.deleteStatistics(paraMap);
				if(result) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除统计数据成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除统计数据失败！");
				}
			}
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			logger.error("删除统计数据异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 查询新增用户统计
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public String userAdd(String timeType, String beginTime, String endTime, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			//如果时间类型为空，默认查询过去7天
			timeType = "3";
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		List statisticsList = null;
		try {
			//查询新增用户统计
			statisticsList = showStatisticsManagerService.getUserAddStatistics(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询新增用户统计异常！", e);
		}
		model.addAttribute("timeType", timeType);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("statisticsList", statisticsList);
		
		return "pages/show/showStatistics/addUser";
	}
	
	/**
	 * 查询用户统计
	 * @param userType
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public String user(String userType, String timeType, String beginTime, String endTime, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			//如果时间类型为空，默认查询过去7天
			timeType = "3";
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		paraMap.put("userType", userType);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		List statisticsList = null;
		try {
			//查询用户统计
			statisticsList = showStatisticsManagerService.getUserStatistics(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询用户统计异常！", e);
		}
		model.addAttribute("userType", userType);
		model.addAttribute("timeType", timeType);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("statisticsList", statisticsList);
		
		return "pages/show/showStatistics/user";
	}
	
	/**
	 * 查询用户行为统计
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userBehavior", method = RequestMethod.GET)
	public String userBehavior(String userType, String timeType, String beginTime, String endTime, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			//如果时间类型为空，默认查询过去7天
			timeType = "3";
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		paraMap.put("userType", userType);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		List statisticsList = null;
		try {
			//查询用户行为统计
			statisticsList = showStatisticsManagerService.getUserBehaviorStatistics(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询用户行为统计异常！", e);
		}
		model.addAttribute("userType", userType);
		model.addAttribute("timeType", timeType);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("statisticsList", statisticsList);
		
		return "pages/show/showStatistics/userBehavior";
	}
	
	/**
	 * 查询用户秀统计
	 * @param userId
	 * @param petName
	 * @param times
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "userShow", method = RequestMethod.GET)
	public String userShow(String userId, String petName, String timeType, String showNumOrder, String beginTime, String endTime, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			timeType = "1";	//默认今天
		}
		if(StringUtils.isBlank(beginTime)) {
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		if(StringUtils.isNotBlank(userId)) {
			String userIds = userId.replaceAll("\r\n",",");
			paraMap.put("userId", userIds.split(","));
		}
		paraMap.put("petName", petName);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		paraMap.put("showNumOrder", showNumOrder);
		
		List showUserList = null;
		try {
			//查询用户秀统计
			showUserList = showStatisticsManagerService.getUserShowStatistics(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询用户秀统计异常！", e);
		}
		model.addAttribute("userId", userId);
		model.addAttribute("petName", petName);
		model.addAttribute("timeType", timeType);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("showNumOrder", showNumOrder);
		model.addAttribute("showUserList", showUserList);
		
		return "pages/show/showStatistics/userShow";
	}
	
	/**
	 * 查询秀行为统计
	 * @param showId
	 * @param userName
	 * @param timeType
	 * @param praisedOrder
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "showBehavior", method = RequestMethod.GET)
	public String showBehavior(String showId, String userName, String timeType, String praisedOrder, String beginTime, String endTime, Page<?> page, Model model, HttpServletRequest request){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			timeType = "1";	//默认今天
		}
		if(StringUtils.isBlank(beginTime)) {
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		if(StringUtils.isNotBlank(showId)) {
			String showIds = showId.replaceAll("\r\n",",");
			paraMap.put("showId", showIds.split(","));
		}
		paraMap.put("userName", userName);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		paraMap.put("praisedOrder", praisedOrder);
		
		List showList = null;
		try {
			//查询秀行为统计
			showList = showStatisticsManagerService.getShowBehaviorStatistics(paraMap, page);
			
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("查询秀行为统计异常！", e);
		}
		model.addAttribute("showId", showId);
		model.addAttribute("userName", userName);
		model.addAttribute("timeType", timeType);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("praisedOrder", praisedOrder);
		model.addAttribute("showList", showList);
		
		return "pages/show/showStatistics/showBehavior";
	}
	
	/**
	 * 新增秀客统计数据导出
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addUserExport", method = RequestMethod.GET)
	public String addUserExport(String timeType, String beginTime, String endTime, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			//如果时间类型为空，默认查询过去7天
			timeType = "3";
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String filename = "新增秀客统计数据_"+beginTime+"——"+endTime+".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型
			   
			String[] columnGroup = new String[2];
			columnGroup[0] = "日期:TIMES";
			columnGroup[1] = "新增用户数:COUNTS";
			paraMap.put("columnGroup", columnGroup);
			
			List list = showStatisticsManagerService.getUserAddExportData(paraMap);
			
			ExcelUtil.downloadStatements(list, columnGroup, os, "新增秀客统计数据", null);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("新增秀客统计数据导出异常！", e);
		}
		
		return null;
	}
	
	/**
	 * 用户统计数据导出
	 * @param userType
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "userExport", method = RequestMethod.GET)
	public String userExport(String userType, String timeType, String beginTime, String endTime, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			//如果时间类型为空，默认查询过去7天
			timeType = "3";
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		paraMap.put("userType", userType);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String filename = "秀客用户统计数据_"+beginTime+"——"+endTime+".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型
			
			String[] columnGroup = new String[9];
			columnGroup[0] = "日期:TIMESS";
			columnGroup[1] = "发秀用户:PUBLISH_SHOW_NUM";
			columnGroup[2] = "上推荐用户:RECOMMED_SHOW_NUM";
			columnGroup[3] = "话题参与用户:TOPIC_SHOW_NUM";
			columnGroup[4] = "话题精选用户:TOPIC_SHOW_SELECTION_NUM";
			columnGroup[5] = "点赞用户:PRAISE_NUM";
			columnGroup[6] = "评论用户:COMMENT_NUM";
			columnGroup[7] = "加关注用户:CONCERN_NUM";
			columnGroup[8] = "取消关注用户:CANCEL_CONCERN_NUM";
			paraMap.put("columnGroup", columnGroup);
			
			List list = showStatisticsManagerService.getUserExportData(paraMap);
			
			ExcelUtil.downloadStatements(list, columnGroup, os, "秀客用户统计数据", null);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("用户统计数据导出异常！", e);
		}
		
		return null;
	}
	
	/**
	 * 用户行为数据统计导出
	 * @param userType
	 * @param timeType
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "userBehaviorExport", method = RequestMethod.GET)
	public String userBehaviorExport(String userType, String timeType, String beginTime, String endTime, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			//如果时间类型为空，默认查询过去7天
			timeType = "3";
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		paraMap.put("userType", userType);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String filename = "秀客行为统计数据_"+beginTime+"——"+endTime+".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型
			
			String[] columnGroup = new String[10];
			columnGroup[0] = "日期:TIMESS";
			columnGroup[1] = "发秀数:PUBLISH_SHOW_NUM";
			columnGroup[2] = "上推荐秀数:RECOMMED_SHOW_NUM";
			columnGroup[3] = "话题发布秀数:TOPIC_SHOW_NUM";
			columnGroup[4] = "话题精选秀数:TOPIC_SHOW_SELECTION_NUM";
			columnGroup[5] = "点赞数:PRAISE_NUM";
			columnGroup[6] = "评论数:COMMENT_NUM";
			columnGroup[7] = "加关注数:CONCERN_NUM";
			columnGroup[8] = "取消关注数:CANCEL_CONCERN_NUM";
			columnGroup[9] = "分享数:SHARE_NUM";
			paraMap.put("columnGroup", columnGroup);
			
			List list = showStatisticsManagerService.getUserBehaviorExportData(paraMap);
			
			ExcelUtil.downloadStatements(list, columnGroup, os, "秀客行为统计数据", null);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("用户行为数据统计导出异常！", e);
		}
		return null;
	}
	
	/**
	 * 用户秀统计数据导出
	 * @param userId
	 * @param petName
	 * @param timeType
	 * @param showNumOrder
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "userShowExport", method = RequestMethod.GET)
	public String userShowExport(String userId, String petName, String timeType, String showNumOrder, String beginTime, String endTime, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			timeType = "1";	//默认今天
		}
		if(StringUtils.isBlank(beginTime)) {
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		if(StringUtils.isNotBlank(userId)) {
			String userIds = userId.replaceAll("\r\n",",");
			paraMap.put("userId", userIds.split(","));
		}
		paraMap.put("petName", petName);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		paraMap.put("showNumOrder", showNumOrder);
		
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String filename = "用户秀统计数据_"+beginTime+"——"+endTime+".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型
			
			String[] columnGroup = new String[10];
			columnGroup[0] = "用户ID:userId";
			columnGroup[1] = "头像:headPortrait";
			columnGroup[2] = "昵称:petName";
			columnGroup[3] = "发秀数:publishShowNum";
			columnGroup[4] = "上推荐秀数:recommendShowNum";
			columnGroup[5] = "话题发布秀数:topicShowNum";
			columnGroup[6] = "话题精选秀数:topicShowSelectionNum";
			columnGroup[7] = "被赞数:praisedNum";
			columnGroup[8] = "被评论数:commentedNum";
			columnGroup[9] = "秀被分享数:sharedNum";
			paraMap.put("columnGroup", columnGroup);
			
			List list = showStatisticsManagerService.getUserShowExportData(paraMap);
			
			ExcelUtil.downloadStatements(list, columnGroup, os, "用户秀统计数据", null);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("用户秀统计数据导出异常！", e);
		}
		
		return null;
	}
	
	/**
	 * 秀行为统计数据导出
	 * @param showId
	 * @param userName
	 * @param timeType
	 * @param praisedOrder
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "showBehaviorExport", method = RequestMethod.GET)
	public String showBehaviorExport(String showId, String userName, String timeType, String praisedOrder, String beginTime, String endTime, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if(StringUtils.isBlank(timeType)) {
			timeType = "1";	//默认今天
		}
		if(StringUtils.isBlank(beginTime)) {
			Map timeMap = ObjectUtil.getLastedWeekTime();
			beginTime = (String) timeMap.get("beginTime");
			endTime = (String) timeMap.get("endTime");
		}
		if(StringUtils.isNotBlank(showId)) {
			String showIds = showId.replaceAll("\r\n",",");
			paraMap.put("showId", showIds.split(","));
		}
		paraMap.put("userName", userName);
		paraMap.put("timeType", timeType);
		paraMap.put("beginTime", beginTime);
		paraMap.put("endTime", endTime);
		paraMap.put("praisedOrder", praisedOrder);
		
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String filename = "秀行为统计数据_"+beginTime+"——"+endTime+".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型
			
			String[] columnGroup = new String[6];
			columnGroup[0] = "秀ID:showId";
			columnGroup[1] = "头像:headPortrait";
			columnGroup[2] = "昵称:petName";
			columnGroup[3] = "被赞数:praisedNum";
			columnGroup[4] = "被评论数:commentedNum";
			columnGroup[5] = "被分享数:sharedNum";
			paraMap.put("columnGroup", columnGroup);
			
			List list = showStatisticsManagerService.getShowBehaviorExportData(paraMap);
			
			ExcelUtil.downloadStatements(list, columnGroup, os, "秀行为统计数据", null);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("秀行为统计数据导出异常！", e);
		}
		
		return null;
	}
	
	   /**
	    * 发现推荐管理列表
	    */
	    @RequestMapping(value="findRecommendList", method = RequestMethod.GET)
	    public String findRecommendList(String timeType,
	    		String beginTime,String endTime,Page<?> page, Model model){
	    	Map<String,Object> params=new HashMap<String,Object>();
			if(StringUtils.isBlank(timeType)) {
				//如果时间类型为空，默认查询过去7天
				timeType = "3";
				Map timeMap = ObjectUtil.getLastedWeekTime();
				beginTime = (String) timeMap.get("beginTime");
				endTime = (String) timeMap.get("endTime");
			}
	    	params.put("beginTime", beginTime);
	    	params.put("endTime", endTime);
	    	//查询列表
	    	List<ShowModel> recommendList=new ArrayList<ShowModel>();
	    	try{
	    		recommendList=showStatisticsManagerService.findRecommendList(params, page);
	    	}catch(Exception e){
	    		logger.error("查询发现推荐管理列表信息异常："+e);
	    	}
	    	model.addAttribute("statisticsList", recommendList);
	    	model.addAttribute("endTime", endTime);
	    	model.addAttribute("timeType", timeType);
	    	model.addAttribute("beginTime", beginTime);
	    	
	    	return "pages/show/showStatistics/recommendList";
	    }
	    /**
	     * 发现推荐管理列表导出
	     * @param timeType
	     * @param beginTime
	     * @param endTime
	     * @param request
	     * @param response
	     * @return
	     */
		@RequestMapping(value = "findRecommendListExport", method = RequestMethod.GET)
		public String findRecommendListExport(String timeType,
	    		String beginTime,String endTime, HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> paraMap = new HashMap<String, Object>();
			if(StringUtils.isBlank(timeType)) {
				//如果时间类型为空，默认查询过去7天
				timeType = "3";
				Map timeMap = ObjectUtil.getLastedWeekTime();
				beginTime = (String) timeMap.get("beginTime");
				endTime = (String) timeMap.get("endTime");
			}
			paraMap.put("timeType", timeType);
			paraMap.put("beginTime", beginTime);
			paraMap.put("endTime", endTime);
			
			try {
				OutputStream os = response.getOutputStream();	//取得输出流      
				response.reset();	//清空输出流      
				String filename = "发现推荐统计数据_"+beginTime+"——"+endTime+".xls";
				response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
				//设定输出文件头      
				response.setContentType("application/msexcel");	//定义输出类型
				
				String[] columnGroup = new String[8];
				columnGroup[0] = "推荐时间:recommendBeginTime";
				columnGroup[1] = "类型:recommendType";
				columnGroup[2] = "ID:id";
				columnGroup[3] = "发布人:userName";
				columnGroup[4] = "点击量:browseNum";
				columnGroup[5] = "点赞量:praisedNum";
				columnGroup[6] = "评论量:commentNum";
				columnGroup[7] = "收藏量:collectNum";
				paraMap.put("columnGroup", columnGroup);
				
				List list = showStatisticsManagerService.getFindRecommendListExportData(paraMap);
				
				ExcelUtil.downloadStatements(list, columnGroup, os, "发现推荐统计数据", null);
			} catch(Exception e) {
				e.printStackTrace();
				logger.error("发现推荐数据统计导出异常！", e);
			}
			return null;
		}
	    
	    /**
	     * 发现推荐管理列表
	     */
	    @RequestMapping(value="collectionShowList", method = RequestMethod.GET)
	    public String collectionShowList(Long colletionId,String showIds,String createBy,
	    		Page<?> page, Model model){
	    	Map<String,Object> params=new HashMap<String,Object>();
	    	params.put("colletionId", colletionId);
	    	params.put("showIds", showIds);
	    	params.put("createBy", createBy);
	    	//查询列表
	    	List<ShowModel> recommendList=new ArrayList<ShowModel>();
	    	try{
	    		recommendList=showStatisticsManagerService.findCollectionShowList(params, page);
	    	}catch(Exception e){
	    		logger.error("查询发现推荐管理列表信息异常："+e);
	    	}
	    	model.addAttribute("statisticsList", recommendList);
	    	model.addAttribute("showIds", showIds);
	    	model.addAttribute("colletionId", colletionId);
	    	model.addAttribute("createBy", createBy);
	    	
	    	return "pages/show/showStatistics/collectionShow";
	    }
	    
	    
	 
		
		/**
		 * 发现推荐管理列表导出
		 * @param timeType
		 * @param beginTime
		 * @param endTime
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "findCollectionShowListExport", method = RequestMethod.GET)
		public String findCollectionShowListExport(Long colletionId,String showIds,String createBy,
				HttpServletRequest request, HttpServletResponse response){
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("colletionId", colletionId);
			paraMap.put("showIds", showIds);
			paraMap.put("createBy", createBy);
			
			try {
				OutputStream os = response.getOutputStream();	//取得输出流      
				response.reset();	//清空输出流      
				String filename = "秀集合的秀数据_秀集合ID_"+colletionId+".xls";
				response.setHeader("Content-disposition", "attachment; filename="+ new String(filename.getBytes("GB2312"),"ISO8859-1"));
				//设定输出文件头      
				response.setContentType("application/msexcel");	//定义输出类型
				
				String[] columnGroup = new String[6];
				columnGroup[0] = "ID:id";
				columnGroup[1] = "发布人:userName";
				columnGroup[2] = "点击量:browseNum";
				columnGroup[3] = "点赞量:praisedNum";
				columnGroup[4] = "评论量:commentNum";
				columnGroup[5] = "收藏量:collectNum";
				paraMap.put("columnGroup", columnGroup);
				
				List list = showStatisticsManagerService.getCollectionShowListExportData(paraMap);
				
				ExcelUtil.downloadStatements(list, columnGroup, os, "秀集合统计数据", null);
			} catch(Exception e) {
				e.printStackTrace();
				logger.error("秀集合统计导出异常！", e);
			}
			return null;
		}
		
	    
}
