package com.xiu.mobile.core.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MD5对数据进行签名
 * 
 * @author root
 * 
 */
public class MD5SignUtils {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	// 用于数据签名
	public static String signPriKey = null;

	// 商户ID,支付的时候使用
	public static String merchantId = null;

	// 支付密钥
	public static String payPriKey = null;

	// 支付请求路径
	public static String payRequestUrl = "http://pay.xiu.com/payment/proxyPay.action";

	public static String proxyRefundURL = null;

	public static String getProxyrefundurl() {
		return proxyRefundURL;
	}

	public static String getSignPriKey() {
		return MD5SignUtils.signPriKey;
	}

	public static String getMerchantId() {
		return merchantId;
	}

	public static String getPayPriKey() {
		return payPriKey;
	}

	public static String getPayRequestUrl() {
		return payRequestUrl;
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(Object... values) {

		StringBuffer buffer = new StringBuffer();

		for (Object value : values) {
			buffer.append(value);
		}

		return MD5Encode(buffer.toString());
	}

	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

	public static void main(String[] args) {

		String signPriKey = "f80b5b624f8a1d23d0e1c58bd7c3eb9d";
		String code = MD5Encode(signPriKey);
		System.out.println(code);

	}

	public static String getNumber6FromMath() {
		Long random6Number = Math.round(Math.random() * 1000000);
		while (random6Number < 100000) {
			random6Number = Math.round(Math.random() * 1000000);
		}
		return String.valueOf(random6Number);

	}

	public static String getNumber6FromRandom() {
		Random r = new Random();
		int random6Number = r.nextInt(1000000);
		while (random6Number < 100000) {
			random6Number = r.nextInt(1000000);
		}
		return String.valueOf(random6Number);
	}

}