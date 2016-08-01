package com.xiu.mobile.wechat.web.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.HttpUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.service.IWechatCommonService;


public class WeixinTemplateUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeixinTemplateUtil.class);

	/**
	 * 发送模板消息
	 * @param token
	 * @param openId
	 * @param title
	 * @param activityName
	 * @param activityTime
	 * @param remark
	 * @param url
	 * @return 返回null时正常
	 * @throws IOException
	 */
	public static String sendInfo(String token,String openId,String title,String activityName,String activityTime,String remark,String url,String templateId) throws IOException {
        //获取token
		String tokenInfo;
		if(token == null)
		{
			tokenInfo = getAccessToken();
		}
		else
		{
			tokenInfo = token;
		}
		if(!isSubscribe(tokenInfo,openId))
		{
			return "发送失败，未关注！";
		}
		if(templateId==null || "".equals(templateId))
		{
			templateId="vHFgmAzwjDHG4mbsmcONsnzP5VtI2HU9e6sA_25Si5U";
		}
        //获取接口地址
        String apiUrl =String.format(Constants.SEND_WEIXIN_TEMPLATEMSG_URL,tokenInfo);
        //生成参数串
        //String paramInfo="{\"touser\":\"oKr0st6ji65_XbCu4T3QYbDWuExo\",\"template_id\":\"vHFgmAzwjDHG4mbsmcONsnzP5VtI2HU9e6sA_25Si5U\","
        //		+ "\"url\":\"http://weixin.qq.com/download\",\"topcolor\":\"#FF0000\","
        //		+ "\"data\":{\"first\":{\"value\":\"年终奖加奖通知\",\"color\":\"#173177\"},\"keyword1\":{\"value\":\"年终奖\",\"color\":\"#173177\"},\"keyword2\":{\"value\":\"2015-02-09\",\"color\":\"#173177\"},\"remark\":{\"value\":\"恭喜！你获得xx位好友加奖，现在已经累计加奖了xx元！凑齐100元就可以领取走秀年终奖，继续加油点击详情转入到活动页面。\",\"color\":\"#173177\"}}}";
        
        String paramInfo="{\"touser\":\""+openId+"\",\"template_id\":\""+templateId+"\","
        		+ "\"url\":\""+url+"\",\"topcolor\":\"#FF0000\","
        		+ "\"data\":{\"first\":{\"value\":\""+title+"\",\"color\":\"#173177\"},"
        				+ "\"keyword1\":{\"value\":\""+activityName+"\",\"color\":\"#173177\"},"
        				+ "\"keyword2\":{\"value\":\""+activityTime+"\",\"color\":\"#173177\"},"
        			    + "\"remark\":{\"value\":\""+remark+"\",\"color\":\"#173177\"}}}";
        String jsonStr= HttpUtil.requestPost(apiUrl, paramInfo, "utf-8");
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		logger.info("send Template result: "+jsonObj.toString());
		System.out.println("send Template result: "+jsonObj.toString());
		if(!("ok".equals(jsonObj.getString("errmsg"))))
		{
			return jsonObj.getString("errmsg");
		}
        return null;
    }
	
	/**
	 * 发送模板消息
	 * @param openId
	 * @param title
	 * @param activityName
	 * @param activityTime
	 * @param remark
	 * @param url
	 * @return 返回null时正常
	 * @throws IOException
	 */
	public static String sendInfo(String openId,String title,String activityName,String activityTime,String remark,String url,String templateId) throws IOException {
		return  sendInfo(null,openId,title,activityName,activityTime,remark,url,templateId);
	}
	
	/**
	 * 获取服务器token
	 * @return
	 * @throws IOException
	 */
	private static String getAccessToken() throws IOException {
		//HessianProxyFactory factory = new HessianProxyFactory();
		//String url = Constants.GET_WEIXIN_REMOTETOKEN_URL;
		//IWechatCommonService service = (IWechatCommonService) factory.create(IWechatCommonService.class, url);
		//String result = service.getAccessToken("wxbfd5bd9dd172c9a6", "cd931ec0bbcf6df2db93e88e202461f4", "web");
		return WeChatApiUtil.getAccessToken(Constants.WEB_APPID, Constants.WEB_APPSECRET, "web");
	}

	/**
	 * 判断是否关注
	 * @param token
	 * @param openId
	 * @return
	 * @throws IOException 
	 */
	private static boolean isSubscribe(String token,String openId) throws IOException
	{
		String url = String.format(Constants.GET_USERINFO_URL, token, openId);
		String result = HttpUtil.requestGet(url, "UTF-8");
		JSONObject jsonResult = JSONObject.fromObject(result);
		logger.info("获取用户信息结果：" + jsonResult);
		String subscribe = jsonResult.containsKey("subscribe") ? jsonResult.getString("subscribe") : null;
		if("1".equals(subscribe))
		{
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		try {
			String result = WeixinTemplateUtil.sendInfo("oKr0st6ji65_XbCu4T3QYbDWuExo","年终奖加奖通知","年终奖","2015-02-09","恭喜！你获得xx位好友加奖，现在已经累计加奖了xx元！凑齐100元就可以领取走秀年终奖，继续加油点击详情转入到活动页面。","www.baidu.com",null);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
