package com.xiu.common.web.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :
 * @AUTHOR : xiu@xiu.com
 * @DATE :2012-4-3 下午12:24:47
 *       </p>
 **************************************************************** 
 */
public final class CookieUtil {
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserSessionUtil.class);

    private static final String COOKIE_IS_SECURE = "cookie.isSecure";
    private static final String COOKIE_MAX_AGE = "cookie.maxAge";
    private static final String COOKIE_PATH = "cookie.path";
    private static final String COOKIE_DOMAIN = "cookie.domain";

    static CookieUtil util = new CookieUtil();

    public static CookieUtil getInstance() {
        return util;
    }

    private Properties props = new Properties();

    private CookieUtil() {
        InputStream is = CookieUtil.class.getResourceAsStream("/conf/application.properties");
        if (is != null) {
            try {
                props.load(is);
            } catch (IOException e) {
                LOGGER.error("load application properties faild");
            }
        }
    }

    /**
     * 获取cookie的值，包括从合并后的cookie中取值
     * 
     * @Title: readCookieValue
     * @Description: TODO
     * @param request
     * @param key
     * @return
     * @return String
     * @throws
     */
    public String readCookieValue(HttpServletRequest request, String key) {
    	String domain = props.getProperty("cookie.domain");
        if (domain == null) {
            domain = ".xiu.com";
        }
        
        return getCookieValue(request, domain, key);
    }

    /**
     * 获取Cookie的值
     */
    public String getCookieValue(HttpServletRequest request, String domain, String name) {
        Cookie cookie = null;
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i] == null) {
                    continue;
                }
                if (StringUtils.equals(name, cookies[i].getName())) {
                    cookie = cookies[i];
                    break;
                }
            }
        }
        if (cookie == null) {
            return null;
        }
        String value = cookie.getValue();
//        try {
//            return java.net.URLDecoder.decode(value, "utf-8");
//        } catch (Exception e) {
//        }
        return value;
    }

    /**
     * 
     * @Title: addCookie
     * @Description: 增加cookie
     * @param response
     * @param key
     * @param value
     * @return void
     * @throws
     */
    public void addCookie(HttpServletResponse response, String key, String value) {
        String maxAgeStr = props.getProperty(COOKIE_MAX_AGE);
        int maxAge = Integer.parseInt(maxAgeStr == null ? "-1" : maxAgeStr);
        addCookie(response,key,value,maxAge);
        
    }
    
    public void addCookie(HttpServletResponse response, String key, String value,int maxAge) {
        String domain = props.getProperty(COOKIE_DOMAIN,".xiu.com" );
        
        String path = props.getProperty(COOKIE_PATH,"/");
        
        Boolean isSecure = Boolean.valueOf(props.getProperty(COOKIE_IS_SECURE));
        
        addCookie(response,key,value,domain,path,maxAge,isSecure);
    }
    
    public void addCookie(HttpServletResponse response, String key, String value,String domain ,String path,int maxAge,Boolean isSecure) {

        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(isSecure);
        response.addCookie(cookie);
    }

    public void deleteCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);

        String domain = props.getProperty(COOKIE_DOMAIN);
        String path = props.getProperty(COOKIE_PATH);

        cookie.setDomain(domain == null ? ".xiu.com" : domain);
        cookie.setPath(path == null ? "/" : path);
        cookie.setMaxAge(0);
        
        response.addCookie(cookie);
    }
}
