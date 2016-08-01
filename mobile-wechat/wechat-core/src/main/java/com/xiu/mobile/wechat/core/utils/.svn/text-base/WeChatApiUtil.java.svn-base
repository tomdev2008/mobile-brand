package com.xiu.mobile.wechat.core.utils;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.model.UserInfo;

public class WeChatApiUtil {

	private static final Logger logger = LoggerFactory.getLogger(WeChatApiUtil.class);
	// token缓存时间
	public static final long overdueToken = 900;
	// 缓存token
	public static String webToken;
	public static String appToken;
	// 记录token刷新时间
	public static Date webTokenLastUpdateTime;
	public static Date appTokenLastUpdateTime;

	/**
	 * 判断票据是否过期
	 * 
	 * @return
	 */
	public static boolean isTokenOverdue(Date tokenLastUpdateTime) {
		if (tokenLastUpdateTime != null) {
			long passTime = new Date().getTime() - tokenLastUpdateTime.getTime();
			logger.info("passTime: " + passTime);
			if ((passTime / 1000) >= overdueToken) {
				logger.info("overdueToken(过期) " + (passTime / 1000) + ":" + overdueToken);
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * 根据appid,appsecret ,type 获取验证令牌
	 * 
	 * @param appID
	 * @param appSecret
	 * @param type 获取类型：web-公众号，app-开放平台
	 * @return access_token
	 * @throws IOException
	 */
	public static String getAccessToken(String appId, String appSecret, String type) throws IOException {
		return getAccessToken(type);
	}
	
	/**
	 * 通过type获取token
	 * @param type web公众平台 app 开放平台
	 * @return
	 * @throws IOException
	 */
	public static String getAccessToken(String type) throws IOException {
		String accessToken = null;
		if ("web".equals(type)) {
			accessToken = getWebAccessToken(Constants.WEB_APPID, Constants.WEB_APPSECRET);
		} else if ("app".equals(type)) {
			accessToken = getAppAccessToken(Constants.APP_APPID, Constants.APP_APPSECRET);
		}
		return accessToken;
	}

	/**
	 * 根据appid,appsecret 获取验证令牌
	 * 
	 * @param appID
	 * @param appSecret
	 * @return access_token
	 * @throws IOException
	 */
	public static synchronized String getWebAccessToken(String appId, String appSecret) throws IOException {
		// 缓存是否可用
		if (!isTokenOverdue(webTokenLastUpdateTime) && !"".equals(webToken)) {
			return webToken;
		}
		String url = String.format(Constants.GET_TOKEN_URL, appId, appSecret);
		String jsonStr = HttpUtil.requestGet(url, "utf-8");
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		// 更新获取时间
		webTokenLastUpdateTime = new Date();
		// 缓存token
		webToken = jsonObj.getString("access_token");
		return webToken;
	}

	/**
	 * 根据appid,appsecret 获取验证令牌
	 * 
	 * @param appID
	 * @param appSecret
	 * @return access_token
	 * @throws IOException
	 */
	public static synchronized String getAppAccessToken(String appId, String appSecret) throws IOException {
		// 缓存是否可用
		if (!isTokenOverdue(appTokenLastUpdateTime) && !"".equals(appToken)) {
			return appToken;
		}
		String url = String.format(Constants.GET_TOKEN_URL, appId, appSecret);
		String jsonStr = HttpUtil.requestGet(url, "utf-8");
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		// 更新获取时间
		appTokenLastUpdateTime = new Date();
		// 缓存token
		appToken = jsonObj.getString("access_token");
		return appToken;
	}

	/**
	 * 根据appid,appsecret,code 获取用户基本信息
	 * 
	 * @param appID
	 * @param appSecret
	 * @param code 授权CODE
	 * @return UserInfo
	 * @throws IOException
	 */
	public static UserInfo getUserInfo(String appId, String appSecret, String code) throws Exception {
		// 调用微信接口，获取用户基本信息
		String getUserTokenAndOpenIdUrl = String.format(Constants.GET_OPENID_URL, appId, appSecret, code);
		String result = HttpUtil.requestGet(getUserTokenAndOpenIdUrl, "UTF-8");
		JSONObject jsonObj = JSONObject.fromObject(result);
		String openId = jsonObj.containsKey("openid") ? jsonObj.getString("openid") : null;
		String accessToken = jsonObj.containsKey("access_token") ? jsonObj.getString("access_token") : null;
		String url = String.format(Constants.GET_WEIXIN_USERINFO_URL, accessToken, openId);
		result = HttpUtil.requestGet(url, "UTF-8");
		JSONObject jsonResult = JSONObject.fromObject(result);
		logger.info("获取用户信息结果：" + jsonResult);
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenId(openId);
		// 获取用户信息
		userInfo.setSubscribe(jsonResult.containsKey("subscribe") ? jsonResult.getString("subscribe") : null);
		userInfo.setNickName(jsonResult.containsKey("nickname") ? jsonResult.getString("nickname") : null);
		userInfo.setUnionId(jsonResult.containsKey("unionid") ? jsonResult.getString("unionid") : null);
		userInfo.setSubscribeTime(jsonResult.containsKey("subscribe_time") ? jsonResult.getInt("subscribe_time") : 0);
		userInfo.setSex(jsonResult.containsKey("sex") ? jsonResult.getInt("sex") : 0);
		userInfo.setLanguage(jsonResult.containsKey("language") ? jsonResult.getString("language") : null);
		userInfo.setCountry(jsonResult.containsKey("country") ? jsonResult.getString("country") : null);
		userInfo.setProvince(jsonResult.containsKey("province") ? jsonResult.getString("province") : null);
		userInfo.setCity(jsonResult.containsKey("city") ? jsonResult.getString("city") : null);
		userInfo.setHeadImgUrl(jsonResult.containsKey("headimgurl") ? jsonResult.getString("headimgurl") : null);
		userInfo.setRemark(jsonResult.containsKey("remark") ? jsonResult.getString("remark") : null);
		return userInfo;
	}

	/**
	 * 在未关注情况下获取当前用户信息
	 * 
	 * @param appId
	 * @param appSecret
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public static UserInfo getWeiXinUserInfo(String appId, String appSecret, String code) throws Exception {
		// 通过code获取token
		JSONObject jsonObj = getAccessInfoByCode(appId, appSecret, code);
		String token = "";
		try {
			token = jsonObj.getString("access_token");
		} catch (Exception e) {
			// e.printStackTrace();
			throw new Exception("获取token失效，请重新授权 appId：" + appId);
		}
		String openId = jsonObj.getString("openid");
		String url = String.format(Constants.GET_WEIXIN_USERINFO_URL, token, openId);
		String result = HttpUtil.requestGet(url, "UTF-8");
		JSONObject jsonResult = JSONObject.fromObject(result);
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenId(openId);
		userInfo.setNickName(jsonResult.containsKey("nickname") ? jsonResult.getString("nickname") : null);
		userInfo.setUnionId(jsonResult.containsKey("unionid") ? jsonResult.getString("unionid") : null);
		userInfo.setSex(jsonResult.containsKey("sex") ? jsonResult.getInt("sex") : 0);
		userInfo.setLanguage(jsonResult.containsKey("language") ? jsonResult.getString("language") : null);
		userInfo.setCountry(jsonResult.containsKey("country") ? jsonResult.getString("country") : null);
		userInfo.setProvince(jsonResult.containsKey("province") ? jsonResult.getString("province") : null);
		userInfo.setCity(jsonResult.containsKey("city") ? jsonResult.getString("city") : null);
		userInfo.setHeadImgUrl(jsonResult.containsKey("headimgurl") ? jsonResult.getString("headimgurl") : null);
		userInfo.setRemark(jsonResult.containsKey("remark") ? jsonResult.getString("remark") : null);
		return userInfo;
	}

	/**
	 * 通过code获取授权信息
	 * 
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static JSONObject getAccessInfoByCode(String appId, String appSecret, String code) throws IOException {

		String url = String.format(Constants.GET_OPENID_URL, appId, appSecret, code);
		String jsonStr = HttpUtil.requestGet(url, "utf-8");
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		return jsonObj;
	}

}
