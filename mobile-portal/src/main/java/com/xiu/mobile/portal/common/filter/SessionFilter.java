package com.xiu.mobile.portal.common.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.facade.utils.HttpUtil;
import com.xiu.mobile.portal.service.ILoginService;

/**
 * session过滤
 * @author coco.long
 * @time	2015-03-19
 */
public class SessionFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);
	//过滤的请求路径拼串
	private String filterUri = XiuConstant.MOBILE_FILTER_URI;
	
	//过滤的请求路径详细拼串
	private String filterUriDetail = XiuConstant.MOBILE_FILTER_URI_DETAIL;
	
	//登录Service
	private ILoginService loginService;
	
	//初始化
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext context = filterConfig.getServletContext();  
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);  
        loginService = (ILoginService) applicationContext.getBean("loginService");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		
		//是否过滤
		boolean doFilter = false; 
		
		//请求Uri
		String uri = servletRequest.getRequestURI();
		if(StringUtils.isNotBlank(uri) && uri.length() > 1) {
			uri = uri.substring(1, uri.length());
			if(uri.indexOf("/") > -1) {
				uri = uri.substring(0, uri.indexOf("/"));
			}
		}
		
		//1.请求路径是否在需要过滤的字符串中
		if(filterUri.indexOf(uri) > -1) {
			// 一级路径.* 表示该类下所有方法都过滤，否则查找详细路径串
			if(filterUri.indexOf(uri + ".*") > -1) {
				//全部过滤
				doFilter = true;
			} else {
				//部分过滤
				String uriDetail = servletRequest.getRequestURI();
				if(uriDetail.indexOf(".") > -1) {
					uriDetail = uriDetail.substring(1, uriDetail.indexOf("."));
				} else {
					uriDetail = uriDetail.substring(1, uriDetail.length());
				}
				uriDetail = uriDetail.replace("/", ".");
				if(filterUriDetail.indexOf(uriDetail) > -1) {
					doFilter = true;
				}
			}
		}
	//	doFilter=false;
		if(doFilter) {
			//2.需要过滤
			if(checkLogin(servletRequest)) {
				chain.doFilter(request, response);
			} else {
				Map<String, Object> model = new LinkedHashMap<String, Object>();
				model.put("result", false);
				model.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				model.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
				String jsoncallback = request.getParameter("jsoncallback");
				ResponseUtil.outPrintResult(servletResponse, JsonUtils.bean2jsonP(jsoncallback, model));
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		
	}
	
	//获取设备参数
	private Map<String, String> getDeviceParams(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String ua = request.getHeader("User-Agent");
		String deviceType = "1";// 设备类型1：iphone 2：ipad 接口为2才能调
		String deviceSn = "unknown";// 设备号
		String appVersion = "wap 1.0";// 非应用哪来应用端版本号
		String iosVersion = "unknown";
		String imei = "unknown";// 手机内部15位码，无法获取
		if (ua != null) {
			if (ua.indexOf("iPhone") > 0) {
				deviceSn = "iPhone";
				for (int v = 0; v < 10; v++) {// 取得iphone型号
					if (ua.indexOf("iPhone OS " + v + "_") >= 0) {
						iosVersion = String.valueOf(v);
						break;
					}
				}
			} else if (ua.indexOf("Android") > 0) {
				deviceSn = "Android";
			} else if (ua.indexOf("MSIE") > 0) {
				deviceSn = "winPhone";
			}
		}
		map.put("deviceType", deviceType);
		map.put("deviceSn", deviceSn);
		map.put("imei", imei);
		map.put("appVersion", appVersion);
		map.put("iosVersion", iosVersion);
		map.put("marketingFlag", "1");
		map.put("remoteIpAddr",  HttpUtil.getRemoteIpAddr(request));
		return map;
	}
	
	// 检查登陆
	private boolean checkLogin(HttpServletRequest request) {
		try{
			Map<String, Object> params = new LinkedHashMap<String, Object>();
			String tokenId = SessionUtil.getTokenId(request);
			params.put("tokenId", tokenId);
			params.putAll(getDeviceParams(request));
			// 添加IP信息，登录，获取用户状态等接口需要
			params.put(GlobalConstants.KEY_REMOTE_IP, HttpUtil.getRemoteIpAddr(request));
			// 检查登陆
			if (!StringUtils.isEmpty(tokenId) && loginService.checkOnlineStatus(params)) {
				return true;
			} else {
				//logger.error("checkLogin status:false,tokenId=" + tokenId +",userId=" + SessionUtil.getUserId(request) +",remote_ip=" +  HttpUtil.getRemoteIpAddr(request)+",uri=" + request.getRequestURI());
				return false;
			}
		} catch(EIBusinessException e){
			logger.error("checkLogin status exception,tokenId=" + SessionUtil.getTokenId(request) +",userId=" + SessionUtil.getUserId(request) +",remote_ip=" +  HttpUtil.getRemoteIpAddr(request)+",uri=" + request.getRequestURI());
			return false;
		}
	}

}
