package com.xiu.mobile.wechat.core.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.xiu.mobile.wechat.core.utils.http.MySSLConnectionSocketFactory;

/**
 * HTTP 工具类
 * 
 * @author wangzhenjiang
 * 
 */
public class HttpUtil {

	private static HttpClient httpClient;

	static {
		httpClient = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getInstance()).build();
	}

	/**
	 * GET方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param encode
	 *            编码
	 * @return 响应结果为json字符串
	 * @throws IOException
	 */
	public static String requestGet(String url, String encode) throws IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = httpClient.execute(httpGet);
		return EntityUtils.toString(response.getEntity(), encode);

	}

	/**
	 * POST方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param encode
	 *            编码
	 * @return 响应结果为json字符串
	 * @throws IOException
	 */
	public static String requestPost(String url, String encode) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		return EntityUtils.toString(httpResponse.getEntity(), encode);
	}

	/**
	 * POST方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param jsonParams
	 *            需要提交的数据（json格式）
	 * @param encode
	 *            编码
	 * @return 响应结果为json字符串
	 * @throws IOException
	 */
	public static String requestPost(String url, String jsonParams, String encode) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(jsonParams, encode));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		return EntityUtils.toString(httpResponse.getEntity(), encode);
	}

	/**
	 * POST方式提交数据
	 * 
	 * @param url
	 *            待请求的URL
	 * @param HttpEntity
	 *            An entity that can be sent or received with an HTTP message
	 * @return 响应结果为json字符串
	 * @throws IOException
	 */
	public static String requestPost(String url, HttpEntity entity) throws IOException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(entity);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		return EntityUtils.toString(httpResponse.getEntity(), ContentType.get(entity).getCharset());
	}
}
