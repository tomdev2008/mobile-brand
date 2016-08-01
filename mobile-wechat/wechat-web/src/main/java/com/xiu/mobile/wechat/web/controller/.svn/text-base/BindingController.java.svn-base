package com.xiu.mobile.wechat.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.model.UserInfo;
import com.xiu.mobile.wechat.core.utils.ConfigUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.web.constants.WebContants;
import com.xiu.mobile.wechat.web.model.BindingCfgVo;
import com.xiu.mobile.wechat.web.service.IBindingCfgService;

/**
 * 账户绑定 Controller
 * 
 * @author wangzhenjiang
 * 
 */
@Controller
@RequestMapping("/binding")
public class BindingController {
	private static final Logger logger = LoggerFactory.getLogger(BindingController.class);

	@Resource
	private IBindingCfgService bindingCfgService;

	/**
	 * 先检查用户绑定状态，再检查用户登录状态
	 * 
	 * @param request
	 * @return String
	 */

	@RequestMapping(value = "/checkLogin")
	public ModelAndView checkLogin(String targetUrlKey) {
		String targetUrl = ConfigUtil.INSTANCE.getString(targetUrlKey);
		if (StringUtils.isEmpty(targetUrl)) {
			targetUrl = WebContants.DEFAULT_TARGET_URL;
		}
		return new ModelAndView("forward:/login/toLogin", "targetUrl", targetUrl);
	}

	/***
	 * 跳转到联合登陆页面
	 * 
	 * 该请求在m.xiu.com上跳转使用，请勿随便修改
	 * 
	 * @param redirectUrl跳转地址
	 * @return ModelAndView
	 */

	@RequestMapping(value = "/union_login")
	public ModelAndView unionLogin(String redirectUrl) {
		logger.info("微信联合登陆获取跳转URL: " + redirectUrl);
		return new ModelAndView("forward:/login/toLogin", "targetUrl", redirectUrl);
	}

	/**
	 * 先检查用户绑定状态，再检查用户登录状态
	 * 
	 * @param request
	 * @return String
	 */

	@RequestMapping(value = "/checkBind")
	public ModelAndView checkBind(String code, String targetUrl) {
		// 返回参数
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("targetUrl", targetUrl);
		try {
			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, code);
			// 检查绑定情况
			BindingCfgVo bindingCfgVo = bindingCfgService.getBindingCfgByUnionId(userInfo.getUnionId());
			// 如果有绑定记录，则跳转到自动登录页面
			if (null != bindingCfgVo) {
				model.put("bindingCfgVo", bindingCfgVo);
				return new ModelAndView("forward:/login/doLogin", model);
			}
			return new ModelAndView("login/select_login", model);
		} catch (Exception e) {
			logger.error("解除绑定时发生异常.", e);
			return new ModelAndView("error/error");
		}
	}

	/**
	 * 解除绑定
	 * 
	 * 该请求在m.xiu.com上退出登陆，解除绑定使用，请勿随便修改
	 * 
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/removeBinding")
	public ModelAndView removeBinding(String code) {
		try {
			// 调用微信接口，获取用户基本信息
			UserInfo userInfo = WeChatApiUtil.getUserInfo(Constants.WEB_APPID, Constants.WEB_APPSECRET, code);
			int iResult = bindingCfgService.deleteBindingCfgByUnionId(userInfo.getUnionId());
			if (logger.isDebugEnabled()) {
				logger.debug("解除绑定结果:------------");
				logger.debug("openId:{}", userInfo.getOpenId());
				logger.debug("unionId:{}", userInfo.getUnionId());
				logger.debug("iResult(成删除的记录数):{}", iResult);
			}
		} catch (Exception e) {
			logger.error("解除绑定时发生异常.", e);
		}
		return new ModelAndView("redirect:" + WebContants.DEFAULT_TARGET_URL);
	}

	/**
	 * 查询重复的绑定数据
	 * 
	 * @param request
	 * @return String
	 */
	@ResponseBody
//	@RequestMapping(value = "/queryRepeatingData")
	public Map<String, Object> queryRepeatingData(String userName, boolean delete, int count) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if ("zoshow".equals(userName)) {
				List<BindingCfgVo> lst = bindingCfgService.queryRepeatingData(count);
				result.put("list", lst);
				result.put("size", lst.size());
				if (delete && count > 0) {
					List<Long> ids = new ArrayList<Long>();
					for (BindingCfgVo bindingCfgVo : lst) {
						ids.add(bindingCfgVo.getId());
					}
					int iCount = bindingCfgService.deleteBindingCfgList(ids);
					if (logger.isDebugEnabled()) {
						logger.debug("查询重复数据：---------BEGIN------------");
						logger.debug("重复数据：" + lst.size() + "条");
						logger.debug("成功删除：" + iCount + "条");
						logger.debug("查询重复数据：----------END-------------");
					}
				}
			}
		} catch (Exception e) {
			logger.error("查询重复数据时发生异常.", e);
		}
		return result;
	}
}
