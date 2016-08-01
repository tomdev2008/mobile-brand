package com.xiu.wap.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.DateUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.mobile.core.model.AppChannel;
import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAppStartManagerService;

/**
 * @description:app启动页管理Controller
 * @AUTHOR :coco.long
 * @DATE :2015-03-24
 */
@AuthRequired
@Controller
@RequestMapping("/appStartManager")
public class AppStartManagerController {

	//日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(AppStartManagerController.class);
	
	@Autowired
	private IAppStartManagerService appStartManagerService;
	
	@Value("${upload_pic_path}")
	private String upload_pic_path;
		
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String id, Long status, String appType, String useStatus, String channel, Page<?> page, Model model, HttpServletRequest request){
		List<AppStartManagerModel> appStartManagerList = new ArrayList<AppStartManagerModel>();
		Map paraMap = new HashMap();
		paraMap.put("id", id);
		paraMap.put("status", status);
		paraMap.put("appType", appType);
		paraMap.put("channel", channel);
		paraMap.put("useStatus", useStatus);
		
		try {
			appStartManagerList = appStartManagerService.getAppStartManagerList(paraMap, page);
			
			paraMap.remove("status");
			//查询发行渠道列表
			List<AppChannel> appChannelList = appStartManagerService.getAllAppChannelList(paraMap);
			
			model.addAttribute("appChannelList", appChannelList);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询app启动页列表！", e);
		}
		model.addAttribute("appStartManagerList", appStartManagerList);
		model.addAttribute("id", id);
		model.addAttribute("status", status);
		model.addAttribute("appType", appType);
		model.addAttribute("channel", channel);
		model.addAttribute("useStatus", useStatus);
		
		return "pages/appStartManager/list";
	}	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request,HttpServletResponse response, Model model) {
		Map paraMap = new HashMap();
		paraMap.put("status", 1);
		
		//查询发行渠道列表
		List<AppChannel> appChannelList = appStartManagerService.getAllAppChannelList(paraMap);
		
		model.addAttribute("appChannelList", appChannelList);
		
		return "pages/appStartManager/create";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,HttpServletResponse response, String startTimeC, AppStartManagerModel appStartManager, Model model) {
		try {
			if(!StringUtils.isEmpty(startTimeC)) {
				appStartManager.setStartTime(DateUtil.parseDate(startTimeC, "yyyy-MM-dd"));
			}
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("start_page_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/startPage" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				appStartManager.setPageUrl(picPath);// 图片上传路径
				//上传
				String picFile = upload_pic_path.trim() + picPath;
				UploadPicUtils.isUploadPathExsit(picFile);
				patch.transferTo(new File(picFile));// 上传图片
			}
			
			MultipartFile patchA = multipartRequest.getFile("start_page_f_a");
			String fileNameA = patchA.getOriginalFilename();
			String picPathA = "";
			if(null!=fileNameA && !"".equals(fileNameA)){
				fileNameA = UUID.randomUUID().toString()+ fileNameA.substring(fileNameA.lastIndexOf("."));
				picPathA = "/m/startPage" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileNameA;
				appStartManager.setPageUrlA(picPathA);// 图片上传路径
				//上传
				String picFileA = upload_pic_path.trim() + picPathA;
				UploadPicUtils.isUploadPathExsit(picFileA);
				patchA.transferTo(new File(picFileA));// 上传图片
			}
			
			//渠道设置，iphone、ipad设置为appstore
			if(appStartManager.getAppType() == 2 || appStartManager.getAppType() == 3) {
				appStartManager.setChannel("appstore");
			}
			
			//插入app启动页
			appStartManagerService.insert(appStartManager);
			
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("新增app启动页异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		
		return "redirect:/appStartManager/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("id", id);
			AppStartManagerModel appStartManager = appStartManagerService.getAppStartManager(paraMap);
			
			//查询发行渠道列表
			List<AppChannel> appChannelList = appStartManagerService.getAllAppChannelList(paraMap);
			
			model.addAttribute("appStartManager", appStartManager);
			model.addAttribute("appChannelList", appChannelList);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询app启动页异常！", e);
		}
		model.addAttribute("id", id);
		return "pages/appStartManager/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MultipartHttpServletRequest request, HttpServletResponse response, String startTimeC, AppStartManagerModel appStartManager) {
		try {
			if(!StringUtils.isEmpty(startTimeC)) {
				appStartManager.setStartTime(DateUtil.parseDate(startTimeC, "yyyy-MM-dd"));
			}
			//图片检测
			MultipartFile patch = request.getFile("start_page_f");
			
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(null!=fileName && !"".equals(fileName)){
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/startPage" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					appStartManager.setPageUrl(picPath);// 图片上传路径
					//上传
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));// 上传图片
				}
			}
			
			MultipartFile patchA = request.getFile("start_page_f_a");
			
			if(patchA != null) {
				String fileNameA = patchA.getOriginalFilename();
				if(null!=fileNameA && !"".equals(fileNameA)){
					fileNameA = UUID.randomUUID().toString() + fileNameA.substring(fileNameA.lastIndexOf("."));
					String picPathA = "/m/startPage" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileNameA;
					appStartManager.setPageUrlA(picPathA);// 图片上传路径
					//上传
					String picFileA = upload_pic_path.trim() + picPathA;
					UploadPicUtils.isUploadPathExsit(picFileA);
					patchA.transferTo(new File(picFileA));// 上传图片
				}
			}
			
			//渠道设置，iphone、ipad设置为appstore
			if(appStartManager.getAppType() == 2 || appStartManager.getAppType() == 3) {
				appStartManager.setChannel("appstore");
			}
			
			//更新App启动页
			appStartManagerService.update(appStartManager);
			response.getWriter().print("<script> alert('success!') ;if(window.opener) { window.opener.location.reload();} window.close(); </script> ");
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("修改app启动页异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;if(window.opener) { window.opener.location.reload();}  window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String delete(String id, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(id == null || id.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				Map map = new HashMap();
				map.put("id", Long.parseLong(id));
				int result = appStartManagerService.delete(map); //删除app启动页
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除app启动页成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除app启动页失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("删除app启动页异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String updatStatus(String id, String status, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(id == null || id.equals("") || status == null || status.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				Map map = new HashMap();
				map.put("id", Long.parseLong(id));
				map.put("status", Long.parseLong(status));
				
				int result = appStartManagerService.updateStatus(map); //更新app启动页状态
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("更新app启动页状态成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("更新app启动页状态失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("更新app启动页状态异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	@RequestMapping(value = "/getStartPageCount", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String getStartPageCount(String appType, String channel, String startTimeC, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(appType == null || appType.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				Map map = new HashMap();
				map.put("appType", Long.parseLong(appType));
				map.put("channel", channel);
				map.put("status", 1);
				
				if(!StringUtils.isEmpty(startTimeC)) {
					map.put("startTime", DateUtil.parseDate(startTimeC, "yyyy-MM-dd"));
				}
				
				int result = appStartManagerService.getStartPageCount(map); //更新app启动页状态
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData(result);
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("查询app启动页数量失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("查询app启动页数量异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 发行渠道列表
	 * @param name
	 * @param code
	 * @param status
	 * @param page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "appChannelList", method = RequestMethod.GET)
	public String appChannelList(String name, String code, Long status, Page<?> page, Model model, HttpServletRequest request){
		List<AppChannel> appChannelList = new ArrayList<AppChannel>();
		Map paraMap = new HashMap();
		paraMap.put("name", name);
		paraMap.put("code", code);
		paraMap.put("status", status);
		try {
			appChannelList = appStartManagerService.getAppChannelList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询发行渠道列表异常！", e);
		}
		model.addAttribute("appChannelList", appChannelList);
		model.addAttribute("name", name);
		model.addAttribute("code", code);
		model.addAttribute("status", status);
		
		return "pages/appStartManager/appChannelList";
	}
	
	@RequestMapping(value = "/createAppChannel", method = RequestMethod.GET)
	public String createAppChannel(HttpServletRequest request,HttpServletResponse response) {
		return "pages/appStartManager/createAppChannel";
	}
	
	@RequestMapping(value = "/saveAppChannel", method = RequestMethod.POST)
	public String saveAppChannel(HttpServletRequest request,HttpServletResponse response, AppChannel appChannel, Model model) {
		try {
			//插入发行渠道
			appStartManagerService.insertAppChannel(appChannel);
			
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("新增发行渠道异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		
		return "redirect:/appStartManager/appChannelList";
	}
	
	@RequestMapping(value = "/editAppChannel", method = RequestMethod.GET)
	public String editAppChannel(Long id, Model model) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("id", id);
			AppChannel appChannel = appStartManagerService.getAppChannel(paraMap);
			model.addAttribute("appChannel", appChannel);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询发行渠道异常！", e);
		}
		model.addAttribute("id", id);
		return "pages/appStartManager/editAppChannel";
	}
	
	@RequestMapping(value = "/updateAppChannel", method = RequestMethod.POST)
	public String updateAppChannel(HttpServletRequest request, HttpServletResponse response, AppChannel appChannel) {
		try {
			appStartManagerService.updateAppChannel(appChannel);
			response.getWriter().print("<script> alert('success!') ;if(window.opener) { window.opener.location.reload();} window.close(); </script> ");
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("修改发行渠道异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;if(window.opener) { window.opener.location.reload();}  window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/updateAppChannelStatus", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String updateAppChannelStatus(String id, String status, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(id == null || id.equals("") || status == null || status.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				Map map = new HashMap();
				map.put("id", Long.parseLong(id));
				map.put("status", Long.parseLong(status));
				
				int result = appStartManagerService.updateAppChannelStatus(map); //更新发行渠道状态
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("更新发行渠道状态成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("更新发行渠道状态失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("更新发行渠道状态异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
