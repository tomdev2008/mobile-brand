package com.xiu.mobile.wechat.facade.service;

/**
 * 微信公众号基础服务接口
 * 
 * @author wangzhenjiang 20150206
 */
public interface IWechatCommonService {

	/**
	 * 根据类型，调用微信服务器获取相应的access_token
	 * <p>
	 * 获取授权的URL为:https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential
	 * </p>
	 * 
	 * @param appId
	 * @param appSecret
	 * @param type 获取类型：web - 公众号, app - 开放平台
	 * @return access_token
	 */
	@Deprecated
	String getAccessToken(String appId, String appSecret, String type);
	
	/**
	 * 根据类型，调用微信服务器获取相应的access_token
	 * @param type "web"返回公众平台token "app"返回开放平台
	 * @return
	 */
	String getAccessToken(String type);
}
