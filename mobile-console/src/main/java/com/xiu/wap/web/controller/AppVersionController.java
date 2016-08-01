/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午10:12:13 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.wap.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import jxl.common.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.ConfigUtil;
import com.xiu.common.web.utils.DateUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.mobile.core.model.AppVersionModel;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAppVersionService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(App版本管理) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-27 上午10:12:13 
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/version")
public class AppVersionController {
	private static final Logger LOG = Logger.getLogger(AppVersionController.class);
	
	@Autowired
	private IAppVersionService appVersionService;
	
	@RequestMapping(value = "list")
	public String list(String versionNo, String content, String sDate, String eDate, 
			Integer status, Page<?> page, Model model) {
		List<AppVersionModel> versionList = appVersionService
				.getAppVersionListByParam(versionNo, content, sDate, eDate, status, page);
		
		int totalCount = appVersionService
				.getVersionCountByParam(versionNo, content, sDate, eDate, status);
		page.setTotalCount(totalCount);
		model.addAttribute("versionList", versionList);
		model.addAttribute("versionNo", versionNo);
		model.addAttribute("content", content);
		model.addAttribute("sDate", sDate);
		model.addAttribute("eDate", eDate);
		model.addAttribute("status", status);
		return "pages/version/list";
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createPage() {
		return "pages/version/create";
	}
	
	/**
	 * 添加
	 * @param appVersion
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String saveVersion(MultipartHttpServletRequest request, HttpServletResponse response, 
			AppVersionModel appVersion, String pubTimeC, Model model) {
		
		if(!StringUtils.isEmpty(pubTimeC)) {
			appVersion.setPubTime(DateUtil.parseDate(pubTimeC, "yyyy-MM-dd"));
		}
		
		try {
			MultipartFile patch = request.getFile("dataFile"); // 获取文件流
			String fileName = patch.getOriginalFilename(); // 文件名
			if(StringUtils.isNotEmpty(fileName)) {	
				//如果上传文件不为空，则根据上传文件生成url
				String appUrl = this.uploadApp(request, appVersion.getVersionNo());
				appVersion.setUrl(appUrl);
			} 
			
			if (appVersionService.saveOrUpdateAppVersion(appVersion)) {
				response.getWriter().print("<script>location.href = '/version/list/';</script>");
            } else {
            	response.getWriter().print("<script> $('#error_c').text('添加版本失败！'); </script> ");
            }
		} catch (Exception e) {
			String errorMsg = "";
			if(appVersion != null) {
				errorMsg = appVersion.toString();
			}
			LOG.error("版本更新信息失败！错误信息："+e.getMessage()+"，参数信息："+errorMsg, e);
			
			try {
				response.getWriter().print("<script> $('#error_c').text('添加版本异常！'); </script> ");
			} catch (IOException e1) {
			}
		}
		return null;
	}
	
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String editPage(Long id, Model model) {
		AppVersionModel version = appVersionService.getAppVersionModelById(id);
		model.addAttribute("version", version);
		return "pages/version/edit";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateVersion(MultipartHttpServletRequest request, HttpServletResponse response, 
			AppVersionModel appVersion, String pubTimeC) {
		if(!StringUtils.isEmpty(pubTimeC)) {
			appVersion.setPubTime(DateUtil.parseDate(pubTimeC, "yyyy-MM-dd"));
		}
		
		try {
			MultipartFile patch = request.getFile("dataFile"); // 获取文件流
			String fileName = patch.getOriginalFilename(); // 文件名
			if(StringUtils.isNotEmpty(fileName)) {	
				//如果上传文件不为空，则根据上传文件生成url
				String appUrl = this.uploadApp(request, appVersion.getVersionNo());
				appVersion.setUrl(appUrl);
			}
			
			if (appVersionService.saveOrUpdateAppVersion(appVersion)) {
				response.getWriter().print("<script> alert('success!') ;window.opener.location.reload(); self.close();</script> ");
            } else {
            	response.getWriter().print("<script> alert('failed!') ;window.opener.location.reload(); self.close(); </script> ");
            }
		} catch (Exception e) {
			LOG.error("版本更新信息失败！", e);
			
			try {
				response.getWriter().print("<script> alert('failed!') ;window.opener.location.reload(); self.close();</script> ");
			} catch (IOException e1) {
			}
		}
		return null;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String delete(Long id, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		
		try {
			if(null != id) {
				if(appVersionService.deleteAppVersion(id)) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除操作失败！");
				}
			} else {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			LOG.error("删除版本更新信息失败！", e);
		}
		
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	@RequestMapping(value = "updateStatus", produces = "application/json", params = "format=json")
	public String updateStatus(Long id, String status, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		
		try {
			if(null != id || StringUtils.isNotEmpty(status)) {
				if(appVersionService.updateStatus(id, status)) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("操作成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("操作失败！");
				}
			} else {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			LOG.error("修改版本信息状态异常！", e);
		}
		
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";	
	}
	
	/**
	 * 处理app文件上传
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	private String uploadApp(MultipartHttpServletRequest request, Long versionNo) throws IllegalStateException, IOException {
		
		// 获取文件流
		MultipartFile patch = request.getFile("dataFile");
		
		// 文件名
		String fileName = patch.getOriginalFilename();
		
		if(StringUtils.isNotEmpty(fileName)) {
			String rootPath = ConfigUtil.getValue("upload.app.path");
			long uploadTime = System.currentTimeMillis();
			String relativePath = versionNo + "/" + uploadTime + fileName;
			File appFile = new File(rootPath + relativePath);
			
			if(!appFile.getParentFile().exists()) {
				appFile.getParentFile().mkdirs();
			}
			
			patch.transferTo(appFile);
			String appUrl = ConfigUtil.getValue("app.domain") + relativePath;
			return appUrl;
		} else {
			return null;
		}
	}

}
