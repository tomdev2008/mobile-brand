/**  
 * @Project: xiu
 * @Title: UnicodeUtils.java
 * @Package org.lazicats.xiu.common.utils
 * @Description: TODO
 * @author: yong
 * @date 2013-5-15 上午01:15:52
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.simple.common.util;

/**
 * Unicode帮助类
 * 
 * @ClassName: UnicodeUtils
 * @Description: TODO
 * @author: yong
 * @date 2013-5-15 上午08:39:26
 * 
 */
public class UnicodeUtils {

	/**
	 * 字符串转成Unicode码
	 * 
	 * @Title: encodeUnicode
	 * @Description: TODO
	 * @param gbString
	 * @return
	 * @author: yong
	 * @date: 2013-5-15上午08:35:58
	 */
	public static String encodeUnicode(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			buffer.append("\\u" + hexB);
		}
		return buffer.substring(0);
	}

	/**
	 * unicode 码转换成相应字符串 有异常原字符返回
	 * 
	 * @Title: decodeUnicode_try
	 * @Description: TODO
	 * @param unicodeStr
	 * @return
	 * @author: yong
	 * @date: 2013-5-15上午08:35:05
	 */
	public static String decodeUnicode_try(String unicodeStr) {
		String backStr = unicodeStr;
		if (null == unicodeStr) {
			return backStr;
		}
		try {
			backStr = decodeUnicode(unicodeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return backStr;
	}

	/**
	 * unicode 码转换成相应字符串
	 * 
	 * @Title: decodeUnicode
	 * @Description: TODO
	 * @param theString
	 * @return
	 * @author: yong
	 * @date: 2013-5-15上午08:34:20
	 */
	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}

		}
		return outBuffer.toString();

	}

	public static void main(String[] args) {
		String encoding = encodeUnicode("<=t功能介绍:将unicode字符串转为汉字 输入参数:源unicode字符串 输出参数: 转换后的字符串");
		System.out.println(encoding);
		String decoeing2 = decodeUnicode(encoding);
		System.out.println(decoeing2);
		String decoeing = decodeUnicode("\\u52af能介绍:\\u003c\\u529f\\u0064\\u0065将unicode字符串转为汉字 输入参数: 源unicode字符串 输出参数:转换后的字符串");
		System.out.println(decoeing);
	}

}
