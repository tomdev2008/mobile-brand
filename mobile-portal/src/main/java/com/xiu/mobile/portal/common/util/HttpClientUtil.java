package com.xiu.mobile.portal.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientUtil {

	/**
	 * @param url
	 * @param charset
	 *            设置字符集
	 * @return
	 */
	public static String sendHttpClientMsg(String url) {
		String content = "";
		// 核心应用类
		HttpClient httpClient = new DefaultHttpClient();
		// HTTP请求
		HttpUriRequest request = new HttpGet(url);
		// 打印请求信息
		System.out.println(request.getRequestLine());
		try {
			HttpParams params=new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 6000);
			HttpConnectionParams.setSoTimeout(params, 60000);
			request.setParams(params);
			
			// 发送请求，返回响应
			HttpResponse response = httpClient.execute(request);
			// 打印响应信息
			content=inputStreamConvertString(response.getEntity().getContent(),"UTF-8");
		} catch (ClientProtocolException e) {
			// 协议错误
			e.printStackTrace();
		} catch (IOException e) {
			// 网络异常
			e.printStackTrace();

		}
		return content;
	}
	public static String inputStreamConvertString(InputStream response, String charset) {
		String ret = "";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(response, charset));

			String tempbf = "";
			StringBuffer html = new StringBuffer(100);
			while ((tempbf = br.readLine()) != null) {
				html.append(tempbf + "\n");
			}
			ret = new String(html.toString());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
