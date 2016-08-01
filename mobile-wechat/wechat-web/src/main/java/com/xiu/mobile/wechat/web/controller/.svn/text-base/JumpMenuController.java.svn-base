package com.xiu.mobile.wechat.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.web.model.JumpMenuVo;
import com.xiu.mobile.wechat.web.service.IJumpMenuService;

@Controller
@RequestMapping("/menu")
public class JumpMenuController {

	private static final Logger logger = LoggerFactory.getLogger(JumpMenuController.class);

	@Resource
	IJumpMenuService jumpMenuService;

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<JumpMenuVo> resultList = jumpMenuService.getAllJumpMenus();
		model.put("resultList", resultList);
		return new ModelAndView("/menu/viewJumpMenu", model);
	}

	@RequestMapping(value = "/add")
	public ModelAndView add(HttpServletRequest request) {
		return new ModelAndView("/menu/jumpMenu");
	}

	@ResponseBody
	@RequestMapping(value = "/addJumpMenu")
	public Map<String, Object> addJumpMenu(JumpMenuVo jumpMenuVo) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			jumpMenuService.addJumpMenu(jumpMenuVo);
			model.put("result", "success");
		} catch (Exception e) {
			logger.error("添加菜单发生异常。", e);
			model.put("result", "failed");
		}
		return model;
	}

}
