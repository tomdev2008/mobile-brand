package com.xiu.mobile.portal.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.xiu.mobile.core.model.BaseSupportVersion;
import com.xiu.mobile.core.model.FindSupportVersion;
import com.xiu.mobile.portal.model.AppVo;

/**
 * Created by yulong on 16/1/28.
 */
public class AppVersionUtils {


    //zou xiu/3.6.1 (iPhone; iOS 9.2; Scale/3.00)
    private static final String APP_VERSION_HEADER_KEY = "User-Agent";
    //xiu/appstore;IPHONE 6 Plus;deviceid;idfa;IOS 
    private static final String APP_BERSION_HEADER_KEY_IOS="device_info";
    
    private static final String APP_BERSION_HEADER_KEY_INFO="device-info";

    private static final Pattern PATTERN = Pattern.compile("xiu/([0-9]+(\\.[0-9]+)?\\.[0-9]+) ?\\((\\w+); ?(\\w+ ?[0-9]+\\.[0-9]+)");

    private static final Pattern APPSOURCE_PATHER=Pattern.compile("xiu/([\\S| ]+);([\\S| ]+);([\\S| ]+);([\\S| ]+)?");//(\\w+ \\w+)
    /**
     * 解析APP的版本
     * @param request
     * @return
     */
    public static AppVo parseAppVo(HttpServletRequest request){

        String info = request.getHeader(APP_VERSION_HEADER_KEY);
        String device_info=request.getHeader(APP_BERSION_HEADER_KEY_IOS);
        if(device_info==null){
        	device_info=request.getHeader(APP_BERSION_HEADER_KEY_INFO);
        }
        if(StringUtils.isBlank(info)){
            return null;
        }

        AppVo appVo = new AppVo();

        Matcher matcher = PATTERN.matcher(info);
        if(matcher.find()){
            appVo.setAppVersion(matcher.group(1));
            appVo.setAppType(matcher.group(3));
            appVo.setAppSummary(matcher.group(4));
        }else {
            return null;
        }
        if(StringUtils.isBlank(device_info)){
        	return appVo;
        }
        Matcher device=APPSOURCE_PATHER.matcher(device_info);
        if(device.find()){
        	appVo.setAppSource(device.group(1));
        	appVo.setPhoneModel(device.group(2));
        	appVo.setDeviceId(device.group(3));
        }
        return appVo;
    }
    //判断是否有限制版本
    public static void filterSupportVersionItems(List<? extends BaseSupportVersion> items, AppVo appVo){
    	if(CollectionUtils.isEmpty(items)){
    		return ;
    	}
    	
    	for(Iterator<? extends BaseSupportVersion> iter = items.iterator();iter.hasNext();){
    		
    		BaseSupportVersion supportVersion = iter.next();
    		
    		if(supportVersion.getIsShow()  != 0){
    			
    			boolean flag = false;
    			
    			for(FindSupportVersion findSupportVersion : supportVersion.getListVersion()){
    				flag = checkVersionSupported(findSupportVersion, appVo);
    				
    				if(flag){
    					break;
    				}
    			}
    			//如果没有限制则移除
    			if(!flag){
    				iter.remove();
    			}
    		}
    	}
    }
    /**
     * 判断指定版本
     * @param findSupportVersion
     * @param appVo
     */
    private static boolean checkVersionSupported(FindSupportVersion findSupportVersion, AppVo appVo){
    	if(appVo==null){
    		appVo=new AppVo();
    	}
    	if(appVo.getAppType()==null){
    		return true;
    	}
    	//如果不为空或不是当前系统
    	if(StringUtils.isNotBlank(findSupportVersion.getAppSystem())
    			&& !findSupportVersion.getAppSystem().equalsIgnoreCase(appVo.getAppType())){
    		return false;
    	}
    	//如果不为空并且不是当前appSource
    	if(StringUtils.isNotBlank(findSupportVersion.getAppSource()) 
    			&& !findSupportVersion.getAppSource().equalsIgnoreCase(appVo.getAppSource())){
    		return false;
    	}
    	String appVersion=appVo.getAppVersion();
    	String start=findSupportVersion.getStartVersion();
		String last=findSupportVersion.getLastVersion();
    	if(StringUtils.isNotBlank(start) && start.compareTo(appVersion)>0){
    		return false;
    	}
    	if(StringUtils.isNotBlank(last) && last.compareTo(appVersion)<=0){
    		return false;
    	}
		return true;
    }
    

}
