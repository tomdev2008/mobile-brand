package com.xiu.mobile.wechat.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.model.UserInfo;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.web.constants.WebContants;
import com.xiu.mobile.wechat.web.model.BindingCfgVo;
import com.xiu.mobile.wechat.web.service.IBindingCfgService;
import com.xiu.mobile.wechat.web.service.ILoginService;
import com.xiu.mobile.wechat.web.utils.CookieUtil;
import com.xiu.mobile.wechat.web.utils.WebUtils;
import com.xiu.sso.server.dto.UserDO;

@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource(name = "loginService")
	private ILoginService loginService;

	@Resource(name = "bindingCfgService")
	private IBindingCfgService bindingCfgService;

	@RequestMapping("toLogin")
	public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response) {
		String targetUrl = (String) request.getAttribute("targetUrl");
		// 如果已登录，则直接跳转至目标页面
		if (hasLoggedIn(request)) {
			return new ModelAndView("redirect:" + targetUrl);
		}
		String redirectUrl = String.format(Constants.REDIRECT_CHECK_BIND, WebUtils.getURLEncoderVal(targetUrl, "utf-8"));
		String oauthUrl = String.format(Constants.AUTH_BASE_URL, Constants.WEB_APPID, WebUtils.getURLEncoderVal(redirectUrl, "utf-8"));
		return new ModelAndView("redirect:" + oauthUrl);
	}

	@RequestMapping("doLogin")
	public ModelAndView doLogin(HttpServletRequest request, HttpServletResponse response) {
		BindingCfgVo bindingCfgVo = (BindingCfgVo) request.getAttribute("bindingCfgVo");
		String targetUrl = request.getParameter("targetUrl");
		try {
			String clientIp = WebUtils.getRemoteIpAddr(request);
			if (StringUtils.isNotBlank(bindingCfgVo.getUserId())) {
				UserDO userDO = loginService.proxyLogin(Long.valueOf(bindingCfgVo.getUserId()), WebContants.CHANNEL_ID, clientIp);
				if (!userDO.getIsSuccess()) {
					if (logger.isErrorEnabled()) {
						logger.error("使用user_id登录失败，跳转至选择登录页面-----------------");
						logger.error("userId: {}", bindingCfgVo.getUserId());
						logger.error("errorCode: {}", userDO.getErrorCode());
						logger.error("errorMsg: {}", WebUtils.getErrorMsg(userDO.getErrorCode()));
					}
					return new ModelAndView("login/select_login", "targetUrl", targetUrl);
				}
				return addCookieAndRedirect(response, userDO, bindingCfgVo.getLogonName(), targetUrl);
			} else {
				String xiuMode = bindingCfgVo.getXiuMode();
				// 联合登陆改造，为了过渡旧数据，需要将旧数据更改为改造后的数据格式，以下代码会在以后旧数据迁移完毕后删除。
				if (WebContants.LOGIN_TYPE_BIND.equals(xiuMode)) {
					String account = bindingCfgVo.getXiuAccount();
					String password = bindingCfgVo.getXiuPassword();
					UserDO userDO = loginService.xiuLogin(account, password, WebContants.CHANNEL_ID, clientIp);
					if (!userDO.getIsSuccess()) {
						if (logger.isErrorEnabled()) {
							logger.error("使用绑定的走秀账号登录失败，跳转至选择登录页面-----------------");
							logger.error("errorCode: {}", userDO.getErrorCode());
							logger.error("errorMsg: {}", WebUtils.getErrorMsg(userDO.getErrorCode()));
							logger.error("bindingCfgVo: {}", bindingCfgVo);
							logger.error("targetUrl: {}", targetUrl);
						}
						return new ModelAndView("login/select_login", "targetUrl", targetUrl);
					}
					// 保存绑定信息
					saveBindInfo(bindingCfgVo.getOpenId(), bindingCfgVo.getUnionId(), userDO.getUserId(), account,
							WebContants.LOGIN_TYPE_BIND);
					// 写Cookie并重定向到目标页面
					return addCookieAndRedirect(response, userDO, account, targetUrl);
				} else if (WebContants.LOGIN_TYPE_UNION.equals(xiuMode)) {
					String openId = bindingCfgVo.getOpenId();
					String unionId = bindingCfgVo.getUnionId();
					String nickname = bindingCfgVo.getXiuAccount();
					UserDO userDO = loginService.unionLogin(openId, unionId, nickname, clientIp);
					if (!userDO.getIsSuccess()) {
						if (logger.isErrorEnabled()) {
							logger.error("使用绑定的微信open_id登录失败，跳转至选择登录页面-----------------");
							logger.error("errorCode: {}", userDO.getErrorCode());
							logger.error("errorMsg: {}", WebUtils.getErrorMsg(userDO.getErrorCode()));
							logger.error("bindingCfgVo: {}", bindingCfgVo);
							logger.error("targetUrl: {}", targetUrl);
						}
						return new ModelAndView("login/select_login", "targetUrl", targetUrl);
					}
					// 保存绑定信息
					saveBindInfo(openId, unionId, userDO.getUserId(), nickname, WebContants.LOGIN_TYPE_UNION);
					// 写Cookie并重定向到目标页面
					return addCookieAndRedirect(response, userDO, nickname, targetUrl);
				}
			}
		} catch (Exception e) {
			logger.error("根据绑定信息代客登录时发生异常.", e);
		}
		return new ModelAndView("login/select_login", "targetUrl", targetUrl);
	}

	@RequestMapping("toXiuLogin")
	public ModelAndView toXiuLogin(String code, String targetUrl) {
		try {
			// 返回参数
			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, code);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("openId", userInfo.getOpenId());
			model.put("unionId", userInfo.getUnionId());
			model.put("targetUrl", targetUrl);
			return new ModelAndView("login/login", model);
		} catch (Exception e) {
			logger.error("代客登录时发生异常.", e);
			return new ModelAndView("error/error");
		}
	}

	@ResponseBody
	@RequestMapping("xiuLogin")
	public Map<String, Object> xiuLogin(HttpServletRequest request, HttpServletResponse response) {
		// 返回参数
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String account = request.getParameter("xiuAccount");
			String password = request.getParameter("xiuPassword");
			UserDO userDO = loginService.xiuLogin(account, password, WebContants.CHANNEL_ID, WebUtils.getRemoteIpAddr(request));
			if (!userDO.getIsSuccess()) {
				result.put("result", false);
				result.put("errorMsg", WebUtils.getErrorMsg(userDO.getErrorCode()));
				return result;
			}
			String targetUrl = request.getParameter("targetUrl");
			String openId = request.getParameter("openId");
			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, openId);
			// 保存绑定信息
			saveBindInfo(openId, userInfo.getUnionId(), userDO.getUserId(), account, WebContants.LOGIN_TYPE_BIND);
			// 写Cookie并重定向到目标页面
			CookieUtil.getInstance().addLoginCookie(response, userDO.getTokenId(), userDO.getUserId(), account);
			result.put("result", true);
			result.put("targetUrl", targetUrl);
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorMsg", "系统异常,请重试或联系客服.");
			logger.error("绑定走秀账号时发生异常.", e);
		}
		return result;
	}

	@RequestMapping("unionLogin")
	public ModelAndView unionLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			String targetUrl = request.getParameter("targetUrl");
			if (StringUtils.isBlank(targetUrl)) {
				targetUrl = WebContants.DEFAULT_TARGET_URL;
			}
			// 如果用户取消授权 获取 没有授权,则跳转至选择登录页面
			if (Constants.CANCEL_CODE.equals(code) || StringUtils.isBlank(code)) {
				return new ModelAndView("login/select_login", "targetUrl", targetUrl);
			}

			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, code);
			String nickName = userInfo.getNickName();
			// 如果用户没用订阅服务号
			if (Constants.NOT_SUBSCRIBE.equals(userInfo.getSubscribe())) {
				// 用户没有关注时，通过授权也可以获取union_id
				nickName = StringUtils.isBlank(nickName) ? userInfo.getUnionId() + "@wechat" : nickName;
			}
			// TBD added by jack.jia
			UserDO userDO = loginService.unionLogin(userInfo.getOpenId(), userInfo.getUnionId(), nickName, WebUtils.getRemoteIpAddr(request));
			if (!userDO.getIsSuccess()) {
				if (logger.isErrorEnabled()) {
					logger.error("使用绑定的微信open_id登录失败，跳转至选择登录页面-----------------");
					logger.error("errorCode: {}", userDO.getErrorCode());
					logger.error("errorMsg: {}", WebUtils.getErrorMsg(userDO.getErrorCode()));
					logger.error("targetUrl: {}", targetUrl);
				}
				return new ModelAndView("login/select_login", "targetUrl", targetUrl);
			}
			// 保存联合登陆的信息，得保存登陆方式为1微信号接入
			saveBindInfo(userInfo.getOpenId(), userInfo.getUnionId(), userDO.getUserId(), nickName, WebContants.LOGIN_TYPE_UNION);
			// 写Cookie并重定向到目标页面
			return addCookieAndRedirect(response, userDO, nickName, targetUrl);
		} catch (Exception e) {
			logger.error("代客登录时发生异常.", e);
			return new ModelAndView("error/error");
		}
	}

	/**
	 * 登录成功后将用户信息写入Cookie并跳转至目标页面
	 * 
	 * @param response
	 * @param userDO
	 * @param targetUrl
	 * @return
	 */
	private ModelAndView addCookieAndRedirect(HttpServletResponse response, UserDO userDO, String userName, String targetUrl) {
		CookieUtil.getInstance().addLoginCookie(response, userDO.getTokenId(), userDO.getUserId(), userName);
		return new ModelAndView("redirect:" + targetUrl);
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
		return StringUtils.isNotEmpty(tokenId) && loginService.checkOnlineStatus(tokenId, WebContants.CHANNEL_ID, clientIp);
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
