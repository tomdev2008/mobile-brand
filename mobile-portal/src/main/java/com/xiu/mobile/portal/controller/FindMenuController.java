package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.model.MobileCommonData;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.service.IFindMenuService;

/**
 * 发现频道栏目
 * @author coco.long
 * @time	2015-1-19
 */
@Controller
@RequestMapping("/findMenu")
public class FindMenuController extends BaseController {

	private Logger logger = Logger.getLogger(FindMenuController.class);
	
	@Autowired
	private IFindMenuService findMenuService;
	
	@ResponseBody
	@RequestMapping(value = "/getFindMenuList", produces = "text/html;charset=UTF-8")
	public String getFindMenuList(HttpServletRequest request, String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		
		try {
			Map<String,Object> paraMap = new HashMap<String,Object>();
			//查询发现频道栏目列表
			List<FindMenuMgt> findMenuList = findMenuService.getFindMenuList(paraMap);
			
			int count = 0;
			if(findMenuList != null && findMenuList.size() > 0) {
				count = findMenuList.size();
			}
			
			//查询发现频道版本号
			paraMap.put("key", GlobalConstants.XIU_MOBILE_FINDCHANNEL_VERSION);
			MobileCommonData commonData = findMenuService.getFindChannelVersion(paraMap);
			
			resultMap.put("findMenuList", findMenuList);
			resultMap.put("count", count);
			resultMap.put("findChannelVersion", commonData.getValueNum());
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询发现频道栏目列表时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getFindMenu", produces = "text/html;charset=UTF-8")
	public String getFindMenu(HttpServletRequest request,String id, String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		
		try {
			if(StringUtils.isNotBlank(id)) { 
				Map<String,Object> paraMap = new HashMap<String,Object>();
				paraMap.put("id", Long.parseLong(id));
				
				//查询发现频道栏目
				FindMenuMgt findMenu = findMenuService.getFindMenuById(paraMap);
				
				resultMap.put("findMenu", findMenu);
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			}
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询发现频道栏目时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 导航栏图标查询
	 * @param request
	 * @param id
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAppNavMenuList", produces = "text/html;charset=UTF-8")
	public String getAppNavMenuList(HttpServletRequest request,String jsoncallback){
	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Long block=12l;//导航栏区块标识
		try {
				//查询发现频道栏目
				List<FindMenuMgt> findMenus = findMenuService.getFindMenuListByBlock(block);
				resultMap.put("appNavMenuList", findMenus);
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("导航栏图标查询发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 获取用户中心功能模块列表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserCenterFuncBlocksList", produces = "text/html;charset=UTF-8")
	public String getMyCenterFuncBlocksList(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Long block=13l;//导航栏区块标识
		try {
			//查询发现频道栏目
			List<FindMenuMgt> findMenus = findMenuService.getFindMenuListByBlock(block);
			resultMap.put("funcBlocks", findMenus);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("导航栏图标查询发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
