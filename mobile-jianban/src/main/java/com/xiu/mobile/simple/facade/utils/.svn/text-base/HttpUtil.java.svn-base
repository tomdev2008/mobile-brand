package com.xiu.mobile.simple.facade.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import com.xiu.mobile.simple.excpetion.DoHttpRequestFailException;
import com.xiu.mobile.simple.excpetion.ParseResponseFailException;

/**
 * HTTP工具类，发送POST请求
 * 
 * @author 江天明
 *
 */
public class HttpUtil {

	/** 日志 **/
	private Logger logger = Logger.getLogger(this.getClass());

	private static final int RESPONSE_SUCCESS = 200;

	private static final int CONNECTION_TIMEOUT = 60000;

	private static final int SOCKET_TIMEOUT = 60000;

	private static final String CHAR_CODING = "UTF-8";

	private HttpPost post;

	/**
	 * 发送HTTP POST请求
	 */
	public String sendPost(String url, Map<String, String> postParams) throws DoHttpRequestFailException,
			ParseResponseFailException, IOException {

		try {
			initPost(url, postParams);
		} catch (UnsupportedEncodingException exc) {
			logger.error("HttpUtil --- 设置HTTP参数时异常", exc);
			throw new DoHttpRequestFailException("设置HTTP参数时异常");
		}

		HttpResponse response = null;
		try {
			response = doPost();
		} catch (IOException exc) {
			logger.error("HttpUtil --- 发送HTTP请求时异常", exc);
			throw new DoHttpRequestFailException("发送HTTP请求时异常");
		}

		if (!isResponseStatusSuccessful(response)) {
			abortPost();
			return "";
		}

		return parseHttpResponse(response);

	}

	private void initPost(String url, Map<String, String> postParams) throws UnsupportedEncodingException {
		List<NameValuePair> params = preparePostParams(postParams);
		post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(params, CHAR_CODING));
	}

	private List<NameValuePair> preparePostParams(Map<String, String> params) {
		List<NameValuePair> qParams = new ArrayList<NameValuePair>();

		Iterator<Entry<String, String>> keys = params.entrySet().iterator();// entrySet()方法效率更高

		while (keys.hasNext()) {
			Entry<String, String> e = keys.next();
			qParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
		}

		return qParams;
	}

	private HttpResponse doPost() throws IOException {
		HttpClient httpClient = getHttpClient();

		HttpResponse response = httpClient.execute(post);
		return response;
	}

	private HttpClient getHttpClient() {
		HttpClient httpClient = new DefaultHttpClient();
		setHttpClientParameters(httpClient);
		return httpClient;
	}

	private void setHttpClientParameters(HttpClient hc) {
		HttpParams params = hc.getParams();
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);// 连接超时
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SOCKET_TIMEOUT);// Socket超时

	}

	private boolean isResponseStatusSuccessful(HttpResponse response) {
		int responseStatus = response.getStatusLine().getStatusCode();

		return RESPONSE_SUCCESS == responseStatus;
	}

	/**
	 * 解析HTTP响应信息，返回响应文本
	 */
	private String parseHttpResponse(HttpResponse response) throws ParseResponseFailException, IOException {
		HttpEntity entity = response.getEntity();
		checkEntity(entity);
		InputStream is = getInputStreamFromEntity(entity);
		return readContentFromInputStream(is);
	}

	private void checkEntity(HttpEntity entity) throws ParseResponseFailException {
		if (null == entity) {
			abortPost();
			logger.error("HttpUtil --- HttpEntity为null");
			throw new ParseResponseFailException("服务器接口返回异常");
		}
	}

	private InputStream getInputStreamFromEntity(HttpEntity entity) throws ParseResponseFailException {
		try {
			BufferedHttpEntity wrappedEntity = new BufferedHttpEntity(entity);
			return wrappedEntity.getContent();
		} catch (IOException exc) {
			abortPost();
			logger.error("HttpUtil --- 解析HTTP响应时异常", exc);
			throw new ParseResponseFailException("解析HTTP响应时异常");
		}
	}

	private String readContentFromInputStream(InputStream is) throws ParseResponseFailException, IOException {
		byte[] read = new byte[1024];
		byte[] all = new byte[0];
		int num;

		try {
			while ((num = is.read(read)) > 0) {
				byte[] temp = new byte[all.length + num];
				System.arraycopy(all, 0, temp, 0, all.length);
				System.arraycopy(read, 0, temp, all.length, num);
				all = temp;
			}
			return new String(all, CHAR_CODING);
		} catch (IOException exc) {
			logger.error("HttpUtil --- 解析HTTP响应时异常", exc);
			throw new ParseResponseFailException("解析HTTP响应时异常");
		} finally {
			abortPost();
			if (null != is) {
				is.close();
			}
		}
	}

	private void abortPost() {
		post.abort();
	}

	public static Map<String, String> getXiuParams() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceType", "2");
		map.put("deviceSn", "010101");
		map.put("imei", "1");
		map.put("appVersion", "6");
		map.put("iosVersion", "4");
		map.put("marketingFlag", "1");
		return map;
	}

	public static String getContent(String getURL) {
		HttpURLConnection connection = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader reader = null;
		try {
			URL getUrl = new URL(getURL);
			// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
			// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
			connection = (HttpURLConnection) getUrl.openConnection();
			// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
			// 服务器
			connection.connect();
			// 取得输入流，并使用Reader读取
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
			String lines;
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 断开连接
			connection.disconnect();
		}
		return sb.toString();
	}	
	// 返回远程访问者的IP地址
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Cluster-Client-Ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	

	/*	public static void main(String[] args) {
			getContent("http://portal.xiu.com/business/GoodsDetailMainCmd?langId=-7&storeId=10154&catalogId=10101&jsoncallback=jQuery17208626945361022063_1368600435253&rand=&goodsId=0593278&goodsSn=10014497&sellType=2&_=1368600435577");
		}
	*/}
