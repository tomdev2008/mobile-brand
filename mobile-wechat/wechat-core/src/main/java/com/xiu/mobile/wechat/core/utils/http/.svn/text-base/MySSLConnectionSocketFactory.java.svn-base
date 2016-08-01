package com.xiu.mobile.wechat.core.utils.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

/**
 * 
 * 不使用安全证书访问
 * 
 * @author wangzhenjiang
 *
 */
public class MySSLConnectionSocketFactory extends SSLConnectionSocketFactory {

	private static MySSLConnectionSocketFactory mySSLConnectionSocketFactory = null;

	static {
		mySSLConnectionSocketFactory = new MySSLConnectionSocketFactory(createSContext());
	}

	public MySSLConnectionSocketFactory(SSLContext sslContext) {
		super(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}

	private static SSLContext createSContext() {
		SSLContext sslContext = null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[] { new TrustAnyTrustManager() }, null);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return sslContext;
	}

	public static MySSLConnectionSocketFactory getInstance() {
		if (mySSLConnectionSocketFactory != null) {
			return mySSLConnectionSocketFactory;
		} else {
			return mySSLConnectionSocketFactory = new MySSLConnectionSocketFactory(createSContext());
		}
	}
}
