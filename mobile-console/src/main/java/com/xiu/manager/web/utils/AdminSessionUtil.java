package com.xiu.manager.web.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.xiu.common.web.utils.CookieUtil;
import com.xiu.common.web.utils.EncryptUtils;
import com.xiu.common.web.utils.JSONObjectUtils;
import com.xiu.manager.web.model.Menu;
import com.xiu.manager.web.model.User;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;

public class AdminSessionUtil {

	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(AdminSessionUtil.class);
	private static final String COMM_USER_COOKIE = "admin_user";
//	private static final String MENU_LIST_COOKIE = "menu_list";
//	private static final String MENU_LIST_LENGHT_COOKIE = "menu_list_length";
//	private static final Integer COOKIE_MAX_LENGTH = 600;
	private static  List menuList=null;

	private static User getUser(String userString) {
		LocalOperatorsDO casUser = (LocalOperatorsDO) JSONObjectUtils.getObject(userString, LocalOperatorsDO.class);
		User user=new User();
		user.setId(Long.valueOf(casUser.getId()));
		user.setUsername(casUser.getLoginName());
		user.setEmail(casUser.getEmail());
//		User user = (User) JSONObjectUtils.getObject(userString, User.class);
		return user;
	}

	public static void setMenuList(HttpServletResponse response, List list) {
		menuList=list;
//		String value = JSONObjectUtils.toString(menuList);
//		value = EncryptUtils.encrypt(value);
//		int len=value.length();
//		System.out.println("cookie:"+value.length());
//	    CookieUtil.getInstance().addCookie(response, MENU_LIST_LENGHT_COOKIE, value.length()+"");
//	    int page=len/COOKIE_MAX_LENGTH+1;
//	    for (int i = 0; i <page; i++) {
//	    	int max=(i+1)*COOKIE_MAX_LENGTH;
//	    	if(max>len){
//	    		max=len;
//	    	}
//	    	String value_page=value.substring(i*COOKIE_MAX_LENGTH, max);
//	    	CookieUtil.getInstance().addCookie(response, MENU_LIST_COOKIE+i, value_page);
//		}
	}

	public static void setUser(HttpServletResponse response, User user) {
		String value = JSONObjectUtils.toString(user);
		value = EncryptUtils.encrypt(value);
		CookieUtil.getInstance().addCookie(response, COMM_USER_COOKIE, value);
	}
	
	public static void setUser(HttpServletResponse response, Object user) {
		String value = JSONObjectUtils.toString(user);
		value = EncryptUtils.encrypt(value);
		CookieUtil.getInstance().addCookie(response, COMM_USER_COOKIE, value);
	}

	public static List getMenu(HttpServletRequest request) {
//		List list = null;
//		try {
//			Integer menuLenght=Integer.valueOf(CookieUtil.getInstance().readCookieValue(
//					request, MENU_LIST_LENGHT_COOKIE));
//			  int page=menuLenght/COOKIE_MAX_LENGTH+1;
////			String menuString = CookieUtil.getInstance().readCookieValue(
////					request, MENU_LIST_COOKIE);
//			StringBuffer menuStringSb=new StringBuffer();
//		    for (int i = 0; i <page; i++) {
//		    	menuStringSb.append(CookieUtil.getInstance().readCookieValue(
//						request, MENU_LIST_COOKIE+i));
//			}
//		    String menuString=menuStringSb.toString();
//			if (StringUtils.isEmpty(menuString)) {
//				return null;
//			}
//			menuString = java.net.URLEncoder.encode(menuString, "utf-8");
//			menuString = EncryptUtils.decrypt(menuString);
//			list = (List) JSONObjectUtils.getCollection(menuString, Menu.class);
//
//		} catch (Exception e) {
//			LOGGER.error("", e);
//		}
		return menuList;
	}

	public static User getUser(HttpServletRequest request) {
		User user = null;
		try {
			String userString = CookieUtil.getInstance().readCookieValue(
					request, COMM_USER_COOKIE);

			if (StringUtils.isEmpty(userString)) {
				return null;
			}
			userString = java.net.URLEncoder.encode(userString, "utf-8");
			userString = EncryptUtils.decrypt(userString);
			user = getUser(userString);

		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return user;
	}
	
	/**
	 * 获取用户id
	 * @param request
	 * @return
	 */
	public static Long getUserId(HttpServletRequest request){
		Long userId=null;
		User user=getUser(request);
		if(user!=null){
			userId=(Long.valueOf(user.getId()));
		}
		return userId;
	}
	

	public static void clearUser(HttpServletResponse response) {
		CookieUtil.getInstance().deleteCookie(response, COMM_USER_COOKIE);
	}
}
