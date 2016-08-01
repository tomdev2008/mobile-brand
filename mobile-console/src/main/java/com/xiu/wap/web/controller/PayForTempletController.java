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
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.PayForTemplet;
import com.xiu.mobile.core.service.IPayForTempletService;

/**
 * @CLASS :com.xiu.recommend.web.controller.PayForTempletController
 * @DESCRIPTION :
 * @AUTHOR :coco.long
 * @DATE :2015-01-14
 */
@AuthRequired
@Controller
@RequestMapping(value = "/payForTemplet")
public class PayForTempletController {
	
	//日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(PayForTempletController.class);
	
	@Autowired
	private IPayForTempletService payForTempletService;
	
	@Value("${upload_pic_path}")
	private String upload_pic_path;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String id, String title ,Long status, Page<?> page, Model model,HttpServletRequest request){
		List<PayForTemplet> templetList = new ArrayList<PayForTemplet>();
		Map paraMap = new HashMap();
		paraMap.put("id", id);
		paraMap.put("title", title);
		paraMap.put("status", status);
		try {
			templetList = payForTempletService.getPayForTempletList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询代付模板异常！", e);
		}
		model.addAttribute("templetList", templetList);
		model.addAttribute("id", id);
		model.addAttribute("title", title);
		model.addAttribute("status", status);
		
		return "pages/payForTemplet/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request,HttpServletResponse response) {
		return "pages/payForTemplet/create";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,HttpServletResponse response, PayForTemplet payForTemplet, Model model) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			//图片检测
			MultipartFile patch = multipartRequest.getFile("templet_pic_f");
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(fileName != null && !fileName.equals("")) {
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/payForTemplet" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					payForTemplet.setTempletPic(picPath); //图片上传路径
					//上传图片
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));
				}
			}
			User user=AdminAuthInfoHolder.getUserAuthInfo();
			payForTemplet.setAddedBy(user.getUsername());  //登陆用户名
			//插入代付模板数据
			payForTempletService.insert(payForTemplet);
			
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("新增代付模板异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		
		return "redirect:/payForTemplet/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("id", id);
			PayForTemplet payForTemplet = payForTempletService.getPayForTemplet(paraMap);
			model.addAttribute("payForTemplet", payForTemplet);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询代付模板异常！", e);
		}
		model.addAttribute("id", id);
		return "pages/payForTemplet/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response, PayForTemplet payForTemplet) {
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
			//图片检测
			MultipartFile patch = multipartRequest.getFile("templet_pic_f");
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(fileName != null && !fileName.equals("")) {
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/payForTemplet" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					payForTemplet.setTempletPic(picPath); //图片上传路径
					//上传图片
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));
				}
			}
			payForTempletService.update(payForTemplet);
			response.getWriter().print("<script> alert('success!') ;if(window.opener) { window.opener.location.reload();} window.close(); </script> ");
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("修改代付模板异常！", e);
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
				int result = payForTempletService.delete(map); //删除代付模板
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除代付模板成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除代付模板失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("删除代付模板异常！", e);
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
				int result = payForTempletService.updateStatus(map); //更新代付模板状态
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("更新代付模板状态成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("更新代付模板状态失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("更新代付模板状态异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
