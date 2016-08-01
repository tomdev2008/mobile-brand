package com.xiu.mobile.simple.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {

	/***
	 * 错误信息页面
	 * @return
	 */
	@RequestMapping("/error")
	public String error(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/error/error";
	}
	
	/***
	 * 403错误
	 * @return
	 */
	@RequestMapping("/403")
	public String error403(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/error/403";
	}
	
	/***
	 * 404错误
	 * @return
	 */
	@RequestMapping("/404")
	public String error404(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/error/404";
	}
	
	/***
	 * 500错误
	 * @return
	 */
	@RequestMapping("/500")
	public String error500(Model model,HttpServletRequest request,HttpServletResponse response){
		return "/error/500";
	}
	
}
