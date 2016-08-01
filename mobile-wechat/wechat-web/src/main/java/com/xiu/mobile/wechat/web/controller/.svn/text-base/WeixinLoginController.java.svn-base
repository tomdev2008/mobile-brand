package com.xiu.mobile.wechat.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.core.model.UserInfo;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.web.constants.WebContants;
import com.xiu.mobile.wechat.web.model.BindingCfgVo;
import com.xiu.mobile.wechat.web.service.IBindingCfgService;
import com.xiu.mobile.wechat.web.service.ILoginService;
import com.xiu.mobile.wechat.web.utils.CookieUtil;
import com.xiu.mobile.wechat.web.utils.WebUtils;
import com.xiu.sso.server.dto.UserDO;
import com.xiu.mobile.wechat.core.constants.Constants;

/**
 * 
 * @author Tomman.qiu
 *
 */
@Controller
@RequestMapping("/weixinlogin")
public class WeixinLoginController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinLoginController.class);

	@Resource(name = "loginService")
	private ILoginService loginService;

	@Resource(name = "bindingCfgService")
	private IBindingCfgService bindingCfgService;

	@RequestMapping("toLogin")
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String targetUrl = (String) request.getParameter("targetUrl");
		// 如果已登录，则直接跳转至目标页面
		if (hasLoggedIn(request)) {
			logger.info("login redirect .......");
			return new ModelAndView("redirect:" + targetUrl);
		}
		if(targetUrl == null)
		{
			targetUrl="";
		}
		logger.info("no login redirect to weixin Oauth");
		//未登录，引导到授权页面(暂时写死 后续调用配置文件)
		//targetUrl="http://testxiu.ngrok.com/wechat-web/redirect/to?targetUrl=http://testxiu.ngrok.com/wechat-web/weixinlogin/userInfo?targetUrl="+targetUrl;
		//String redirectUrl = URLEncoder.encode("http://testxiu.ngrok.com/wechat-web/weixinlogin/userInfo?targetUrl="+targetUrl, "utf-8");
		//String redirectUrl = URLEncoder.encode(targetUrl, "utf-8");
		//String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx012e48a49732696a&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_userinfo&state=stop&connect_redirect=1#wechat_redirect";
		
		String redirectUrl = String.format(Constants.REDIRECT_USER_INFO, WebUtils.getURLEncoderVal(targetUrl, "utf-8"));
		String oauthUrl = String.format(Constants.AUTH_USERINFO_URL, Constants.WEB_APPID, WebUtils.getURLEncoderVal(redirectUrl, "utf-8"));
		return new ModelAndView("redirect:" + oauthUrl);
	}
	
	@RequestMapping("userInfo")
	public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response){
		
		String code = request.getParameter("code");
		String targetUrl = request.getParameter("targetUrl");
		String appId = Constants.WEB_APPID;
		String appSecret = Constants.WEB_APPSECRET;
		//回调为空，跳转到默认页面
		if (StringUtils.isBlank(targetUrl)) {
			targetUrl = WebContants.DEFAULT_TARGET_URL;
			logger.info("no targetUrl,redirect to default(http://m.xiu.com) .......");
		}
		// 如果用户取消授权 获取 没有授权,则跳转至选择登录页面
		if (Constants.CANCEL_CODE.equals(code) || StringUtils.isBlank(code)) {
			logger.info("Cancel auth to select_login");
			return new ModelAndView("login/select_login", "targetUrl", targetUrl);
		}
		logger.info("get userInfo by weixin auth begin appId:"+appId+"code:"+code);
		UserInfo userInfo=null;
		try {
			userInfo = WeChatApiUtil.getWeiXinUserInfo(appId, appSecret, code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取用户token失败 ："+e.getMessage());
			logger.error("重定向到 ："+targetUrl);
			return new ModelAndView("login/select_login", "targetUrl", targetUrl);
		}
		logger.info("get userInfo by weixin auth success:"+userInfo.getUnionId());
		String openId = userInfo.getOpenId();
		String clientIp = WebUtils.getRemoteIpAddr(request);
		String unionId = userInfo.getUnionId();
		logger.info("get userInfo unionId:"+unionId);
		UserDO userDo = loginService.unionLogin(openId,unionId, userInfo.getNickName(), clientIp);
		if (!userDo.getIsSuccess()) {
			if (logger.isErrorEnabled()) {
				logger.error("使用绑定的微信open_id登录失败，跳转至选择登录页面-----------------");
				logger.error("errorCode: {}", userDo.getErrorCode());
				logger.error("errorMsg: {}", WebUtils.getErrorMsg(userDo.getErrorCode()));
				logger.error("targetUrl: {}", targetUrl);
			}
			return new ModelAndView("login/select_login", "targetUrl", targetUrl);
		}
		//保存联合登陆的信息，得保存登陆方式为1微信号接入
		saveBindInfo(openId, userInfo.getUnionId(), userDo.getUserId(), userInfo.getNickName(), WebContants.LOGIN_TYPE_UNION);		
		logger.info("saveBindInfo success unionId:"+userInfo.getUnionId());
		//添加登录信息到cookie
		addCookieLogin(response,userDo,userInfo);
		logger.info("addCookieLogin success unionId:"+userInfo.getUnionId());
		String userId = userDo.getUserId();
		String loginName = userDo.getLoginName();
		String petName = userDo.getPetName();
		String nickName = userInfo.getNickName();
		String headImgUrl= userInfo.getHeadImgUrl();
		ModelAndView mv=new ModelAndView("redirect:"+targetUrl);
		mv.addObject("userId", userId);
		mv.addObject("loginName", loginName);
		mv.addObject("petName", petName);
		mv.addObject("nickName", nickName);
		mv.addObject("unionId", unionId);
		mv.addObject("headImgUrl", headImgUrl);
		return mv;
	}
	
	/**
	 * 检查是否已登录
	 * 
	 * @param request
	 * @return 登录结果：true-已登录;false-未登录、登录状态已失效
	 */
	private boolean hasLoggedIn(HttpServletRequest request) {
		String tokenId = CookieUtil.getInstance().getTokenId(request);
		String clientIp = WebUtils.getRemoteIpAddr(request);
	    String isOauthActivity = CookieUtil.getInstance().readCookieValue(request, ".xiu.com", CookieUtil.getInstance().AOUTH_ACTIVITY);
		return StringUtils.isNotEmpty(tokenId) && StringUtils.isNotEmpty(isOauthActivity)&&loginService.checkOnlineStatus(tokenId, WebContants.CHANNEL_ID, clientIp);
	}
	
	/**
	 * 添加微信登陆信息到cookie
	 * @param response
	 * @param userDO
	 * @param userInfo
	 */
	private void addCookieLogin(HttpServletResponse response,UserDO userDO,UserInfo userInfo)
	{
		CookieUtil.getInstance().addWeixinLoginCookie(response, userDO.getTokenId(), userDO.getUserId(), userDO.getPetName(),
				userInfo.getNickName(),userInfo.getHeadImgUrl(),userInfo.getUnionId());
	}
	
	/**
	 * 保存绑定信息
	 * 
	 * @param xiuMode
	 * @param userDO
	 * @param bindingCfgVo
	 * @return 保存结果
	 */
	private void saveBindInfo(String openId, String unionId, String userId, String logonName, String xiuMode) {
		try {
			// 保存联合登陆的信息
			BindingCfgVo bindingCfgVo = new BindingCfgVo();
			bindingCfgVo.setOpenId(openId);
			bindingCfgVo.setUserId(userId);
			bindingCfgVo.setLogonName(logonName);
			bindingCfgVo.setXiuMode(xiuMode);
			bindingCfgVo.setCreateDate(new Date());
			bindingCfgVo.setUnionId(unionId);
			int result = bindingCfgService.saveOrUpdateBindingCfg(bindingCfgVo);
			if (logger.isDebugEnabled()) {
				logger.debug("更新绑定信息-----BEGIN-----");
				logger.debug(result > 0 ? "success" : "failed");
				logger.debug("bindingCfgVo: {}", bindingCfgVo);
				logger.debug("更新绑定信息-----END-----");
			}
		} catch (Exception e) {
			logger.error("保存绑定信息时发生异常.", e);
		}
	}
}
