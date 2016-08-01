package com.xiu.mobile.portal.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.model.AppDownloadModel;
import com.xiu.mobile.core.model.AppPatchVO;
import com.xiu.mobile.core.model.AppStartManagerModel;
import com.xiu.mobile.core.model.AppVersionModel;
import com.xiu.mobile.core.service.IAppVersionService;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.AppVersionUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.ei.EIMobileSalesManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.AdvertisementVo;
import com.xiu.mobile.portal.model.AppVo;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.service.IAdvService;
import com.xiu.mobile.portal.service.IAppPatchService;
import com.xiu.mobile.portal.service.IAppService;

/**
 * 
 * @author wangchengqun
 * @date 20140701
 * 
 */
@Controller
@RequestMapping("/app")
public class AppController extends BaseController {
	private Logger logger = Logger.getLogger(AppController.class);
	
	@Autowired
	private IAppPatchService appPatchServiceImpl;
	
	@Autowired
	private IAppVersionService appVersionService;
	@Autowired
	private EIMobileSalesManager eiMobileSalesManager;
	@Autowired
	private IAppService appService;
	@Autowired
	private IAdvService advService;
	@Autowired
	private EIUUCManager eiuucManager;

	/**
	 * 判断是否需要更新App的版本
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNewestAppVersion", produces = "text/html;charset=UTF-8")
	public String getNewestAppVersion(HttpServletRequest request,
			String jsoncallback, String type, String versionNo) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			if (null != type && !"".equals(type) && null != versionNo && !"".equals(versionNo)) {
				AppVersionModel app = appVersionService.getNewestAppVersion(Integer.parseInt(type));
				if (null != app) {
					Long verNo = Long.parseLong(versionNo);
					if (verNo >= app.getVersionNo()) {
						resultMap.put("result", false);
						resultMap.put("errorCode",ErrorCode.NewestAppVersion.getErrorCode());
						resultMap.put("errorMsg",ErrorCode.NewestAppVersion.getErrorMsg());
						logger.info("已经是最新版或者未查出改版本");
					} else {
						resultMap.put("result", true);
						resultMap.put("errorCode",ErrorCode.Success.getErrorCode());
						resultMap.put("errorMsg",ErrorCode.Success.getErrorMsg());
						//这里添加代码：比较当前版本与强制更新的最低版本从而决定用户是否必须更新
						//app.setForcedUpdate(forcedUpdate);
						Integer forcedUpdate = app.getForcedUpdate();
						if(forcedUpdate != null && forcedUpdate.equals(1)){
							//如果当前版本设置了强制更新，则进行版本比较
							Integer forcedBeforeVerno = app.getForcedBeforeVerno();	//最低版本号
							if(forcedBeforeVerno != null && forcedBeforeVerno >= Integer.parseInt(versionNo)) {
								//如果最低版本号不为空，且大于传递过来的版本号，则设置强制更新
								app.setForcedUpdate(1);
							} else {
								app.setForcedUpdate(0);
							}
						}
						
						resultMap.put("appVersion", app);
					}
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode",ErrorCode.NewestAppVersion.getErrorCode());
					resultMap.put("errorMsg",ErrorCode.NewestAppVersion.getErrorMsg());
					logger.error("没有查询到最新版本的App and type is " + type);
				}

			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询最新版本的App时发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);

	}

	/**
	 * 记录第一次打开
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reportFirstOpenApp", produces = "text/html;charset=UTF-8")
	public String reportFirstOpenApp(HttpServletRequest request,String jsoncallback, String deviceId, String deviceOS) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			String source = request.getParameter("source"); // 应用来源市场
			String version = request.getParameter("version"); // 当前版本
			String idfa = request.getParameter("idfa"); // IOS设备编号
			boolean result = eiMobileSalesManager.recordFirstOpen(deviceId, deviceOS, source, version,idfa);
			
			AppVo appVo = AppVersionUtils.parseAppVo(request);
			logger.info("reportFirstOpen的AppVo，"+appVo);
			String userId = SessionUtil.getUserId(request);
			if(appVo!=null){
				String appSummary=appVo.getAppSummary().replace(" ", "");
				eiuucManager.insertOrUpdateAppInfo(deviceId, appVo.getAppType(), appSummary, source, userId, version, idfa);
			}
			
			resultMap.put("result", result);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("recordFirstOpen时发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 奖励第一次登录
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rewardFirstLogin", produces = "text/html;charset=UTF-8")
	public String rewardFirstLogin(HttpServletRequest request,
			String jsoncallback, String deviceId, String deviceOS) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		LoginResVo user = SessionUtil.getUser(request);
		try {
			String source = request.getParameter("source"); // 应用来源市场
			String version = request.getParameter("version"); // 当前版本
			String idfa = request.getParameter("idfa"); // IOS设备编号
			boolean result = eiMobileSalesManager.awardFirstLogin(deviceId, deviceOS, source, version, user.getUserId(), user.getLogonName(),idfa);
			resultMap.put("result", result);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("awardFirstLogin时发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 下载APP
	 * 
	 * @param request
	 * @param jsoncallback
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadXiuApp", produces = "text/html;charset=UTF-8")
	public void downloadXiuApp(HttpServletRequest request, String jsoncallback,
			String deviceId, String deviceOS, HttpServletResponse response) {
		try {
			String userAgent = request.getHeader("user-agent");
			String refer =  request.getHeader("referer"); 
			String queryString = request.getQueryString();
			String fromchannel = request.getParameter("fromchannel"); // 从哪里点进来下载的
			//android 1；iphone 2；ipad 3；默认取1
			int appType = 1;
			
			AppDownloadModel model = new AppDownloadModel();
			userAgent = StringUtils.isBlank(userAgent) ? "" : userAgent; // 设置一个参数
			if (userAgent.toLowerCase().indexOf("iphone") > -1|| null!=queryString && queryString.indexOf("downloadIphone")>-1) {
				appType = 2;
			} else if(userAgent.toLowerCase().indexOf("ipad") > -1 || null!=queryString && queryString.indexOf("downloadIpad")>-1) {
				appType = 3;
			}
			else if (userAgent.toLowerCase().indexOf("android") > -1 || null!=queryString && queryString.indexOf("downloadAndroid")>-1) {
				appType = 1;
			}
			AppVersionModel app = appVersionService.getNewestAppVersion(appType);
			
			model.setAppType(appType);
			model.setRefer(refer);
			model.setVersionName(app.getName());
			model.setVersionNo(app.getVersionNo());
			model.setQueryString(queryString);
			appVersionService.insertDownloadRecord(model);
			// 有渠道参数的判断
			if ("framework20150625".equals(fromchannel)) {
				if (userAgent.toLowerCase().indexOf("micromessenger") > -1) {
					response.sendRedirect("http://a.app.qq.com/o/simple.jsp?pkgname=com.xiu.app&g_f=994054");
					return;
				} else
				if (appType == 2) {// iphone
					response.sendRedirect("https://itunes.apple.com/app/apple-store/id512915857?pt=698418&ct=framework20150625&mt=8");
					return;
				}
				else if (appType == 3) {// ipad
					response.sendRedirect("https://itunes.apple.com/app/apple-store/id474436918?pt=698418&ct=framework20150625ipad&mt=8");
					return;
				} else if (appType == 1) {// android
					response.sendRedirect("http://app.xiu.com/xiu_andriod.apk");
					return;
				}
			}
			//无渠道参数的微信判断
			else if (userAgent.toLowerCase().indexOf("micromessenger") > -1 && appType == 3) {
				response.sendRedirect("http://mp.weixin.qq.com/mp/redirect?url=" + app.getUrl());
				return;
			} else if (userAgent.toLowerCase().indexOf("micromessenger") > -1) {
				response.sendRedirect("http://a.app.qq.com/o/simple.jsp?pkgname=com.xiu.app&g_f=994054");
				return;
			} else {
				response.sendRedirect(app.getUrl());
			}
		} catch (Exception e) {
			logger.error("downloadXiuApp时发生异常：" + e.getMessage(),e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getLatelyAppStartPage", produces = "text/html;charset=UTF-8")
	public String getLatelyAppStartPage(HttpServletRequest request, String appType, String channel, String sizeType, String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		
		try {
			if(StringUtils.isNotBlank(appType)) { 
				Map paraMap = new LinkedHashMap();
				paraMap.put("appType", Long.parseLong(appType));
				if(appType.equals("2")) {
					paraMap.put("sizeType", sizeType);
				}
				if(appType.equals("2") || appType.equals("3")) {
					paraMap.put("channel", "appstore");
				} else {
					paraMap.put("channel", StringUtils.lowerCase(channel));
				} 
				
				//查询最近的App启动页
				AppStartManagerModel appStartPage = appService.getLatelyAppStartPage(paraMap);
				
				if(appStartPage == null && appType.equals("1") && channel != null && !channel.equals("")) {
					//如果启动页查询为空，且渠道参数不为空，则查询渠道为全部的启动页
					paraMap.remove("channel");
					appStartPage = appService.getLatelyAppStartPage(paraMap);
				}
				
				//查询闪屏广告
				if(appType.equals("1")) {
					paraMap.put("code", "AndroidSplash");
				} else if(appType.equals("2")) {
					paraMap.put("code", "IphoneSplash");
				} else if(appType.equals("3")) {
					paraMap.put("code", "IpadSplash");
				}
				AdvertisementVo splashAdv = advService.getAdvByType(paraMap);
				if(splashAdv != null) {
					splashAdv.setAdvTime(4);
					resultMap.put("splashAdv", splashAdv);
				}
				
				//查询弹窗广告
				paraMap.put("code", "PopupInHome");
				AdvertisementVo cpmAdv = advService.getAdvByType(paraMap);
				if(cpmAdv != null) {
					cpmAdv.setAdvTime(3);
					resultMap.put("popupAdv", cpmAdv);
				}
				
				resultMap.put("appStartPage", appStartPage);
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.ParamsError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.ParamsError.getErrorMsg());
			}
			
		} catch(Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询最近的App启动页时发生异常：" + e.getMessage());
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
	/**
	 * IOS热更新
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getNewPatchIOS", produces = "text/html;charset=UTF-8")
	public String getNewPatchIOS(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		logger.info("getNewPatchIOS,User-Agent:"+request.getHeader("User-Agent"));
		logger.info("getNewPatchIOS,device_info:"+request.getHeader("device_info"));
		AppVo appVo = AppVersionUtils.parseAppVo(request);
		/*appVo=new AppVo();
		appVo.setAppSource("appstore");
		appVo.setAppVersion("3.9.5");*/
		try {
			AppPatchVO vo=appPatchServiceImpl.getAppPatchVOByCondition(appVo);
			logger.info("AppVo,getNewPatchIOS查询结果："+vo);
			resultMap.put("appPatchVO", vo);
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		}catch(Exception e){
			logger.info("IOS热更新异常",e);
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询最近的IOS热更新时发生异常：" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
