package com.xiu.mobile.simple.excpetion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/***
 * 异常配置注解 统一处理
 * 
 * @author Administrator
 * 
 */
@Component
public class HandlerExceptionResolverImpl implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("errorMessage", ex.getMessage());
		modelAndView.setViewName("common/error");
		return modelAndView;
	}

}
