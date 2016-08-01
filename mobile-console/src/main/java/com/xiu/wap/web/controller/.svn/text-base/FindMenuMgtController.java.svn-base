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
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.FindMenuMgt;
import com.xiu.mobile.core.service.IFindMenuMgtService;

/**
 * @CLASS :com.xiu.recommend.web.controller.FindMenuMgtController
 * @DESCRIPTION :
 * @AUTHOR :coco.long
 * @DATE :2015-01-16
 */
@AuthRequired
@Controller
@RequestMapping(value = "/findMenu")
public class FindMenuMgtController {
	
	//日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(FindMenuMgtController.class);
	
	@Autowired
	private IFindMenuMgtService findMenuMgtService;
	
	@Value("${upload_pic_path}")
	private String upload_pic_path;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String id, String name, Long block, Long type, Long status, Page<?> page, Model model,HttpServletRequest request){
		List<FindMenuMgt> menuList = new ArrayList<FindMenuMgt>();
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("id", id);
		paraMap.put("name", name);
		paraMap.put("block", block);
		paraMap.put("type", type);
		paraMap.put("status", status);
		try {
			menuList = findMenuMgtService.getFindMenuMgtList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询栏目异常！", e);
		}
		model.addAttribute("menuList", menuList);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("block", block);
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		
		return "pages/findMenu/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request,HttpServletResponse response) {
		return "pages/findMenu/create";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,HttpServletResponse response, FindMenuMgt findMenuMgt,
			String appSystem[],String appSource[],String startVersion[],String lastVersion[],
			Model model) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			//图片检测
			MultipartFile patch = multipartRequest.getFile("icon_url_f");
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(fileName != null && !fileName.equals("")) {
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/findMenu" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					findMenuMgt.setIconClickUrl(picPath); //图片上传路径
					//上传图片
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));
				}
			}
			//图片检测
			 patch = multipartRequest.getFile("icon_click_url");
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(fileName != null && !fileName.equals("")) {
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/findMenu" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					findMenuMgt.setIconUrl(picPath); //图片上传路径
					//上传图片
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));
				}
			}
			//插入栏目数据
			findMenuMgtService.insert(findMenuMgt,appSystem,appSource,startVersion,lastVersion);
			
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("新增栏目异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		
		return "redirect:/findMenu/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		try {
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("id", id);
			FindMenuMgt findMenuMgt = findMenuMgtService.getFindMenuMgt(paraMap);
			model.addAttribute("findMenu", findMenuMgt);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询栏目异常！", e);
		}
		model.addAttribute("id", id);
		return "pages/findMenu/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response, FindMenuMgt findMenuMgt,
			String appSystem[],String appSource[],String startVersion[],String lastVersion[]) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			//图片检测
			MultipartFile patch = multipartRequest.getFile("icon_url_f");
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(fileName != null && !fileName.equals("")) {
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/findMenu" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					findMenuMgt.setIconUrl(picPath); //图片上传路径
					//上传图片
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));
				}
			}
			 patch = multipartRequest.getFile("icon_click_url");
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(fileName != null && !fileName.equals("")) {
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/findMenu" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					findMenuMgt.setIconClickUrl(picPath); //图片上传路径
					//上传图片
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));
				}
			}
			findMenuMgtService.update(findMenuMgt,appSystem,appSource,startVersion,lastVersion);
			response.getWriter().print("<script> alert('success!') ;if(window.opener) { window.opener.location.reload();} window.close(); </script> ");
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("修改栏目异常！", e);
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
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", Long.parseLong(id));
				int result = findMenuMgtService.delete(map); //删除栏目
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除栏目成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除栏目失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("删除栏目异常！", e);
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
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", Long.parseLong(id));
				map.put("status", Long.parseLong(status));
				int result = findMenuMgtService.updateStatus(map); //更新栏目状态
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("更新栏目状态成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("更新栏目状态失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("更新栏目状态异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	@RequestMapping(value = "/updateVersion", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String updateVersion(String id, String version, String oldVersion, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(id == null || id.equals("") || version == null || version.equals("") || oldVersion == null || oldVersion.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("id", Long.parseLong(id));
				map.put("version", Long.parseLong(version));
				map.put("oldVersion", Long.parseLong(oldVersion));
				map.put("key",Constants.XIU_MOBILE_FINDCHANNEL_VERSION);
				int result = findMenuMgtService.updateVersion(map); //更新栏目版本号
				if(result > 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("更新栏目版本号成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("更新栏目版本号失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("更新栏目版本号异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
