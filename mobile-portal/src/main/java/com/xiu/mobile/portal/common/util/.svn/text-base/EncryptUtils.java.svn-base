package com.xiu.mobile.portal.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class EncryptUtils {
	private final static Logger logger = Logger.getLogger(EncryptUtils.class);
	private static final String ENCODING = "UTF-8";
	private static final String MD5 = "MD5";
	private static final String AES = "AES";
	//private static String MPORTAL_MD5_KEY;
	// 自己加密用，用于orderId,addresId,bankAccountId等
	private static String MPORTAL_AES_KEY_SELF=ConfInTomcat.getValue("mportal.aes.key.self");;
	// 和app、wap共享用,用作密码加密
	private static String MPORTAL_AES_KEY_SHARE=ConfInTomcat.getValue("mportal.aes.key.share");;
	// 代支付加密key
	private static String MPORTAL_AES_KEY_PAYFOR_OTHERS = ConfInTomcat.getValue("mportal.aes.key.payforothers");
	// private static SecretKey secretKey = null;
	//private static ArrayList<Character> letterNumCharList = new ArrayList<Character>();
	//private static ArrayList<Character> numCharList = new ArrayList<Character>();
	//private static String preOrderId;
	//private static String subfixOrderId;

/*	static {
		initMportalKeys();
	}*/

	/**
	 * 计算摘要，返回32位的MD5值，加密串为SAFE_CODE
	 * 
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static byte[] getMessageDigest(String str, String encoding) {
		try {
			MessageDigest digest = MessageDigest.getInstance(MD5);
			digest.update(str.getBytes(encoding));
			return digest.digest();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取加密串异常：",e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("获取加密串异常：",e);
		}
		return null;
	}

	public static byte[] getMessageDigest(String str) {
		return getMessageDigest(str, ENCODING);
	}

	private static String byte2HexString(byte[] b) {
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();

	}

	/**
	 * md5加密
	 * 
	 * @param str
	 *            需要加密的明文
	 * @return md5 密码串
	 */
	public static String encryptByMD5(String str) {
		return byte2HexString(getMessageDigest(str));
	}

	private static byte[] hex2Byte(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}

	// 根据密钥串生成密钥对象
	public static SecretKey getSecretKey(String keyStr) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			// SecureRandom secureRandom = new
			// SecureRandom(keyStr.getBytes(ENCODING));
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(keyStr.getBytes());
			keyGenerator.init(128, secureRandom);
			return keyGenerator.generateKey();
		} catch (Exception e) {
			logger.error("根据密钥串生成密钥对象异常：",e);
			return null;
		}
	}

	/**
	 * 使用AES加密
	 * 
	 * @param str
	 *            要加密的字符串明文
	 * @param keyStr
	 *            密钥
	 * @return 加密后的字符串密文
	 */
	public static String encryptOrderIdByAES(String orderIdString, String keyStr) {
		//String newOrderId = preOrderId + orderIdString + subfixOrderId;
		// long orderId = Long.parseLong(orderIdString);
		// SecretKey secretKey = getSecretKey(keyStr);
		// return encryptByAES(str, secretKey);
		return orderIdString;
	}

	/**
	 * 使用AES加密
	 * 
	 * @param str
	 *            要加密的字符串明文
	 * @param keyStr
	 *            密钥
	 * @return 加密后的字符串密文
	 */
	public static String encryptByAES(String str, String keyStr) {
		SecretKey secretKey = getSecretKey(keyStr);
		return encryptByAES(str, secretKey);
	}

	/**
	 * 使用AES加密银行帐号 
	 * 
	 * @param str
	 *            要加密的字符串明文
	 * @param keyStr
	 *            密钥
	 * @return 加密后的字符串密文
	 */
	public static String encryptBankAcctIdByAES(String str, String keyStr) {
		//SecretKey secretKey = getSecretKey(keyStr);
		//return encryptByAES(str, secretKey);
		return str;
	}
	/**
	 * 使用AES解密
	 * 
	 * @param str
	 *            要解密的字符串密文
	 * @param keyStr
	 *            密钥
	 * @return 解密后的明文
	 */
	public static String decryptByAES(String str, String keyStr) {
		SecretKey secretKey = getSecretKey(keyStr);
		return decryptByAES(str, secretKey);
	}
	
	/**
	 * 使用AES解密
	 * 
	 * @param str
	 *            银行帐号解密
	 * @param keyStr
	 *            密钥
	 * @return 解密后的明文
	 */
	public static String decryptBankAcctIdByAES(String str, String keyStr) {
		//SecretKey secretKey = getSecretKey(keyStr);
		return str;
	}
	

	/**
	 * 使用AES解密
	 * 
	 * @param orderId
	 *            要解密的字符串密文
	 * @param keyStr
	 *            密钥
	 * @return 解密后的明文
	 */
	public static String decryptOrderIdByAES(String orderId, String keyStr) {
		// SecretKey secretKey = getSecretKey(keyStr);
		// return decryptByAES(str, secretKey);
		//int length = orderId.length();
		//String newOrderId = orderId.substring(4, length - 4);
		return orderId;

	}

	/**
	 * 使用AES加密
	 * 
	 * @param str
	 *            要加密的字符串明文
	 * @param keyStr
	 *            密钥对象
	 * @return 加密后的字符串密文
	 */
	public static String encryptByAES(String str, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] result = cipher.doFinal(str.getBytes(ENCODING));
			return byte2HexString(result);
		} catch (Exception e) {
			logger.error("AES加密异常：",e);
			return "";
		}
	}

	/**
	 * 使用AES解密
	 * 
	 * @param str
	 *            要解密的字符串密文
	 * @param secretKey
	 *            密钥对象
	 * @return 解密后的明文
	 */
	public static String decryptByAES(String str, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] result = cipher.doFinal(hex2Byte(str));
			return new String(result, ENCODING);
		} catch (Exception e) {
			logger.error("使用AES解密异常：",e);
			return "";
		}
	}

	// 初始化key
	private static void initMportalKeys() {
		// 小写字母
/*		for (char i = 65; i < 91; i++) {
			letterNumCharList.add(new Character(i));
		}
		// 大写字母
		for (char i = 97; i < 123; i++) {
			letterNumCharList.add(new Character(i));
		}
		// 数字
		for (char i = 48; i < 58; i++) {
			letterNumCharList.add(new Character(i));
		}
		// 数字
		for (char i = 49; i < 58; i++) {
			numCharList.add(new Character(i));
		}*/
		//String key = "";

		// 初始化aes mportal用
		//for (int i = 0; i < 50; i++) {
		//	key = key + letterNumCharList.get((int) (Math.random() * 62));
		//}
		//MPORTAL_AES_KEY_SELF = key;

		// 和app及wap共享的aes key
		//key = "";
		//for (int i = 0; i < 16; i++) {
		//	key = key + letterNumCharList.get((int) (Math.random() * 62));
		//}
		//MPORTAL_AES_KEY_SHARE = key;

		// 订单前缀
		//key = "";
		//for (int i = 0; i < 4; i++) {
		//	key = key + numCharList.get((int) (Math.random() * 9));
		//}
		//preOrderId = key;
		// 订单后缀
		//key = "";
		//for (int i = 0; i < 4; i++) {
		//	key = key + numCharList.get((int) (Math.random() * 9));
		//}
		//subfixOrderId = key;
	}



	public static String getAESKeySelf() {
		return MPORTAL_AES_KEY_SELF;
	}

	public static String getAESKeyShare() {
		return MPORTAL_AES_KEY_SHARE;
	}

	public static String getAESKeyPayForOthers() {
		return MPORTAL_AES_KEY_PAYFOR_OTHERS;
	}

	// 对密码进行加密，为了兼容wap，及各app的编码解码故严格
	public static String encryptPassByAES(String sSrc, String sKey) throws Exception {
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());

		String result = Base64.encodeBase64String(encrypted);// 此处使用BAES64做转码功能，同时能起到2次加密的作用。
		return result;
	}

	// 解密
	public static String decryptPassByAES(String sSrc, String sKey)
			throws Exception {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = Base64.decodeBase64(sSrc);// 先用bAES64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				logger.error("用户密码AES解密异常,密文是:" + sSrc ,e);
				logger.error("用户密码AES解密异常：",e);
				return null;
			}
		} catch (Exception ex) {
			logger.error("用户密码AES解密异常：",ex);
			return null;
		}
	}

	public static void main(String args[]) {

		// SecretKey secretKey = getSecretKey(keyStr);
		//initMportalKeys();
		String ss = "aaaaa";

		String news = encryptOrderIdByAES(ss, "ddd");
		System.out.println(news);

		news = decryptOrderIdByAES(news, "ddd");
		System.out.println(news);

	}
}
