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
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.ReportModel;
import com.xiu.show.core.service.IShowReportManagerService;

/**
 * 
* @Description: TODO(秀举报) 
* @author haidong.luo@xiu.com
* @date 2015年6月15日 下午6:13:43 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/showReport")
public class ShowReportController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShowCommentController.class);

    @Autowired
    private IShowReportManagerService showReportManagerService;

	/**
	 * 举报列表
	 * @param content
	 * @param showId
	 * @param startDate
	 * @param endDate
	 * @param commentName
	 * @param commentStatus
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String reportedUserName,String showId,String reportStartDate,String reportEndDate,
			String handleStartTime,String handleEndTime,String handleStatus,
			String handleUserName,String reportedNumOrder,String index,Page<?> page, Model model) {	 
		Map rmap = new HashMap();
		if(index!=null){//刚进入页面默认显示未处理
			handleStatus="0";
		}
		rmap.put("reportedUserName", reportedUserName);
		rmap.put("showId", showId);
		rmap.put("reportStartDate", reportStartDate);
		rmap.put("reportEndDate", reportEndDate);
		rmap.put("handleStartTime", handleStartTime);
		rmap.put("handleEndTime", handleEndTime);
		rmap.put("handleStatus", handleStatus);
		rmap.put("handleUserName", handleUserName);
		rmap.put("reportedNumOrder", reportedNumOrder);
		try{
		rmap=showReportManagerService.getShowReportList(rmap,page);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("查询秀举报异常！",e);
		}
		List<ReportModel> cs=(List<ReportModel>)rmap.get("resultInfo");
		model.addAttribute("showId", showId);
		model.addAttribute("reportedUserName", reportedUserName);
		model.addAttribute("reportStartDate", reportStartDate);
		model.addAttribute("reportEndDate", reportEndDate);
		model.addAttribute("handleStartTime", handleStartTime);
		model.addAttribute("handleEndTime", handleEndTime);
		model.addAttribute("handleStatus", handleStatus);
		model.addAttribute("handleUserName", handleUserName);
		model.addAttribute("reportedNumOrder", reportedNumOrder);
		
		model.addAttribute("reportlist", cs);
		return "pages/show/showReport/list";
	}
	
	/**
	 * 举报详情(含举报列表)
	 * @param showId
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(String showId,Page<?> page, Model model) {	 
		Map rmap = new HashMap();
		rmap.put("showId", ObjectUtil.getLong(showId, null));
		try{
			rmap=showReportManagerService.getShowReportInfoList(rmap,page);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("查询秀举报详情异常！",e);
			}
		List<ReportModel> cs=(List<ReportModel>)rmap.get("resultInfo");
		model.addAttribute("showId", showId);
		if(cs!=null&&cs.size()>0){
 			ReportModel report =cs.get(0);
 			model.addAttribute("reportedUserId", report.getReportedUserId());
			model.addAttribute("showStatus", report.getObjectStatus());
			model.addAttribute("showUser", report.getObjectUserName());
			model.addAttribute("reportStatus", report.getHandleStatus());
		}
		model.addAttribute("reportlist", cs);
		return "pages/show/showReport/info";
	}
	
	

	/**
	 * 确认举报前的页面
	 * @param showId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bfConfigReport", method = RequestMethod.GET)
	public String bfConfigReport(String showId, Model model) {	 
		model.addAttribute("showId", showId);
		return "pages/show/showReport/config";
	}
	
	/**
	 * 修改举报状态
	 * @param request
	 * @param showId
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "updateStatus", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String updateStatus(HttpServletRequest request, String showIds, String status,String resultType,Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			Map rmap = new HashMap();
			Boolean isSuccess=false;
			rmap.put("objectIds", showIds);
			rmap.put("handleStatus", ObjectUtil.getInteger(status, null));
			User user=AdminSessionUtil.getUser(request);
			rmap.put("handleUserId", user.getId());
			rmap.put("handleUserName", user.getUsername());
			rmap.put("resultType", resultType);
			rmap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			try {
				if(null != showIds||null != status){
					isSuccess=showReportManagerService.updateShowReportStatus(rmap);
					if (isSuccess ) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("修改成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("修改失败！");
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("修改失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	
	
}
