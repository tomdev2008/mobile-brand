package com.xiu.wap.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.common.lang.StringUtil;
import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.ITopicTypeService;
import com.xiu.mobile.core.utils.DateUtil;

/**
 * @description:卖场分类Controller
 * @AUTHOR :coco.long
 * @DATE :2015-03-03
 */
@AuthRequired
@Controller
@RequestMapping(value = "/topicType")
public class TopicTypeController {
	//日志
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(TopicTypeController.class);
	
	@Autowired
	private ITopicTypeService topicTypeService;
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String id, String name, Long status, Page<?> page, Model model, HttpServletRequest request){
		List<TopicType> topicTypeList = new ArrayList<TopicType>();
		Map paraMap = new HashMap();
		paraMap.put("id", id);
		paraMap.put("name", name);
		paraMap.put("status", status);
		try {
			topicTypeList = topicTypeService.getTopicTypeList(paraMap, page);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询分类异常！", e);
		}
		model.addAttribute("topicTypeList", topicTypeList);
		model.addAttribute("id", id);
		model.addAttribute("name", name);
		model.addAttribute("status", status);
		
		return "pages/topicType/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request,HttpServletResponse response) {
		return "pages/topicType/create";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,HttpServletResponse response, TopicType topicType,
			String beginTime,String endTime,
			Model model) {
		try {
			if(!StringUtil.isBlank(beginTime)){
				topicType.setBeginDate(DateUtil.parseTime(beginTime));
			}
			if(!StringUtil.isBlank(endTime)){
				topicType.setEndDate(DateUtil.parseTime(endTime));
			}
			String name=topicType.getName();
			name=name.trim();
			topicType.setName(name);
			//插入分类数据
			topicTypeService.insert(topicType);
			
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("新增分类异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		
		return "redirect:/topicType/list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, Model model) {
		try {
			Map paraMap = new HashMap();
			paraMap.put("id", id);
			TopicType topicType = topicTypeService.getTopicType(paraMap);
			model.addAttribute("topicType", topicType);
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("查询分类异常！", e);
		}
		model.addAttribute("id", id);
		return "pages/topicType/edit";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response, TopicType topicType
			,String beginTime,String endTime
			) {
		try {
			if(!StringUtil.isBlank(beginTime)){
				topicType.setBeginDate(DateUtil.parseTime(beginTime));
			}
			if(!StringUtil.isBlank(endTime)){
				topicType.setEndDate(DateUtil.parseTime(endTime));
			}
			String name=topicType.getName();
			name=name.trim();
			topicType.setName(name);
			topicTypeService.update(topicType);
			response.getWriter().print("<script> alert('success!') ;if(window.opener) { window.opener.location.reload();} window.close(); </script> ");
		} catch(Exception e) {
			e.printStackTrace();
			LOGGER.error("修改分类异常！", e);
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
				int result = topicTypeService.delete(map); //删除分类
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除分类成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除分类失败！");
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("删除分类异常！", e);
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
				
				Integer count = 0;
				//如果是启用中的分类停用，则检测该分类下是否有进行中的卖场数据
				if(status != null && status.equals("0")) {
					count = topicTypeService.queryActivityByTopicType(map);
					if(count > 0) {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("该分类下有卖场正在进行中，无法停用！");
					} 
				}
				if(count == 0) {
					int result = topicTypeService.updateStatus(map); //更新分类状态
					if(result >= 0) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("更新分类状态成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("更新分类状态失败！");
					}
				}
			}
			
		} catch(Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("更新分类状态异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
