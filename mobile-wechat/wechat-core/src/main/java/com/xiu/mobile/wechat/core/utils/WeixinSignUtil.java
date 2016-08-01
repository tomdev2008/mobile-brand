package com.xiu.mobile.wechat.core.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.xiu.mobile.wechat.core.constants.Constants;


public class WeixinSignUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeixinSignUtil.class);

	public static Date  getSignTime;
	//临时票据过期时间
	public static final long overdueTime=7000;
	public static String jsapi_ticket="";
	
	/**
	 * 判断js临时票据是否过期
	 * @return
	 */
	public static boolean isOverdue()
	{
		if(getSignTime !=null)
		{
			long passTime = new Date().getTime()-getSignTime.getTime();
			logger.info("passTime: "+passTime);
			if((passTime/1000) >=overdueTime)
			{
				logger.info("overdue(过期) "+(passTime/1000)+":"+overdueTime);
				return true;
			}
		}
		else
		{
			return true;
		}
		return false;
	}
	
	public static Map<String, String> signByUrl(String url) throws IOException {
		//缓存是否可用
		if(!isOverdue() && !"".equals(jsapi_ticket))
		{
			return sign(jsapi_ticket,url);
		}
		
		//获取token
		String appId = Constants.WEB_APPID;
		String appSecret = Constants.WEB_APPSECRET;
		String ticketUrl = Constants.GET_WEIXIN_TICKET_URL;
		String token = WeChatApiUtil.getAccessToken(appId,appSecret,"web");
		//获取js票据
		String ticketUrlFromat = String.format(ticketUrl, token);
		String result = HttpUtil.requestGet(ticketUrlFromat, "UTF-8");
		if(result!=null && !"".equals(result.trim())){
			logger.info("result:"+result);
			JSONObject jsonResult = JSONObject.fromObject(result);
			jsapi_ticket=jsonResult.getString("ticket");
			getSignTime = new Date();
			return sign(jsapi_ticket,url);
		}
		return new HashMap<String,String>();
	
	}
	
	
	/**
	 * 获取js签名接口
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        //String nonce_str = "82693e11-b9bc-448e-892f-f5289f46cd0f";
        //String timestamp = "1419835025";
        String string1;
        String signature = "";

        // //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        //System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        //ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appId",Constants.WEB_APPID);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

	private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

	private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
	public static void main(String[] args) {
		try {
			WeixinSignUtil.signByUrl("m.xiu.com");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
