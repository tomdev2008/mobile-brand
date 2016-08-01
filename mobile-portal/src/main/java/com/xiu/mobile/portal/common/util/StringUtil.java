package com.xiu.mobile.portal.common.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

import com.xiu.mobile.portal.facade.utils.DateUtil;

public class StringUtil {

	/**
	 * 字符串替换
	 * @param str 原字符串
	 * @param replace 被替换的字符
	 * @param replaceStr 替换字符
	 * @return
	 */
	public static String replace(String str, String replace, String replaceStr) {
		if (str != null) {
			int i = str.indexOf(replace);
			while (i != -1) {
				str = str.substring(0, i) + replaceStr + str.substring(i + replace.length());
				i = str.indexOf(replace, i + replaceStr.length());
			}
		}
		return str;
	}

	/**
	 * 把数据转换为html格式： " " -> nbsp; \r\n -> br
	 * @param s
	 * @return
	 */
	public static String htmlType(String s) {
		if (s == null || s.toLowerCase().equals("null")) {
			return "";
		}
		s = replace(s, " ", "&nbsp;");
		s = replace(s, "\r\n", "<br>");
		return s;
	}

	/**
	 * 判断该字符串是否为中文
	 * @param str  String 输入字符
	 * @return boolean 返回true,中文字符
	 */
	public static boolean IsChinese(String str) {
		if (str.matches("[\u4e00-\u9fa5]+")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isGB(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isBig5(String str) {
		if (null == str) {
			return false;
		}
		if (str.trim().equals("")) {
			return false;
		}
		byte[] bytes = str.getBytes();
		if (bytes.length < 2) {
			return false;
		}
		byte aa = (byte) 0xB0;
		byte bb = (byte) 0xF7;
		byte cc = (byte) 0xA1;
		byte dd = (byte) 0xFE;
		if (bytes[0] >= aa && bytes[0] <= bb) {
			if (bytes[1] < cc || bytes[1] > dd) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * 将输入字符串转化为utf8编码,并返回该编码的字符串 
	 */
	public static String convertToUTF8(String in) {
		String s = null;
		byte temp[];
		try {
			temp = in.getBytes("utf8");
			s = new String(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * @param c  需要判断的字符
	 * @return  返回true,Ascill字符
	 */
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * @param s 需要得到长度的字符串
	 * @return i得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null) {
			return 0;
		}
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	/**
	 * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	 * @param  origin 原始字符串
	 * @param len 截取长度(一个汉字长度按2算的)
	 * @param c 后缀
	 * @return 返回的字符串
	 */
	public static String subString(String origin, int len, String c) {
		if (origin == null || origin.equals("") || len < 1) {
			return "";
		}
		if (length(origin) <= len) {
			return origin;
		}
		byte[] strByte = new byte[len];
		if (len > length(origin)) {
			return origin + c;
		}
		try {
			System.arraycopy(origin.getBytes("GBK"), 0, strByte, 0, len);
			int count = 0;
			for (int i = 0; i < len; i++) {
				int value = (int) strByte[i];
				if (value < 0) {
					count++;
				}
			}
			if (count % 2 != 0) {
				len = (len == 1) ? ++len : --len;
			}
			return new String(strByte, 0, len, "GBK") + c;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断字符串是否存在
	 * @param str
	 * @return
	 */
	public static boolean isExist(String str) {
		if (str != null && str.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isValidDate(String dateStr) {
		boolean isValid = false;
		if (dateStr == null || dateStr.length() <= 0) {
			return false;
		}
		String pattern = DateUtil.DEFAULT_PATTERN;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			String date = sdf.format(sdf.parse(dateStr));
			if (date.equalsIgnoreCase(dateStr)) {
				isValid = true;
			}
		} catch (Exception e) {
			isValid = false;
		}
		return isValid;
	}

	/**
	 * 验证是否是数字
	 */
	public static boolean isNumber(String str) {
		return str.matches("^\\d+(\\,\\d+)*$");
	}

	/**
	 * UTF-8转码 ISO-8859-1
	 * @param str 字符串
	 * @return
	 */
	public static String ecodeStr(String str) {
		try {
			str = new String(str.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return str;
	}

	/**
	 * ISO-8859-1 转码 UTF-8
	 * @param str字符串
	 * @return
	 */
	public static String decodeStr(String str) {
		try {
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return str;
	}

	/**
	 * 判断数组是否为空的
	 * @param array 数组对象
	 * @return 数组为空或者大小为0，返回true
	 */
	public static boolean isEmpty(Object[] array) {
		if (null == array || array.length == 0)
			return true;
		return false;
	}

	/**
	 * 把数组转换为字符串
	 * @param array 数组
	 * @param joinChar 分割字符串    如果 joinChar 为空，默认为','
	 * @return 
	 *   如果array是空的，返回null;  <br/>
	 *   如果array里面元素是空的，返回null;  <br/>
	 *   如果array里面包含分隔符，返回null;  <br/>
	 */
	public static String joinStr(String[] array, String joinChar) {
		if (isEmpty(array))
			return null;
		if (joinChar == null)
			joinChar = ",";

		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (isEmpty(array[i]))
				return null;
			if (array[i].indexOf(joinChar) != -1)
				return null;
			strB.append(array[i]);
			if (i < array.length - 1)
				strB.append(joinChar);
		}
		return strB.toString();
	}

	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (null == str || str.length() < 1 || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/********************************************************
	  功    能：将金额改写成符合要求的小数点位数，只去掉多余的小数点位数，不扩展位数；
	  入口参数：param amount 输入的金额, param length 指定的小数位长度
	  出口参数：
	  返    回：return 添加转意符后的字符串
	  整 理 人：
	  编写日期：20051220
	  修改备注：
	  ********************************************************/
	public static String double2str(double amount, int length) {
		String strAmt = Double.toString(amount);

		int pos = strAmt.indexOf('.');

		if (pos != -1 && strAmt.length() > length + pos + 1)
			strAmt = strAmt.substring(0, pos + length + 1);

		return strAmt;
	}

	/********************************************************
	  功    能：根据chr分割字符串，因为String类自带的split不支持以"|"为分割符；
	  入口参数：param str 将要被分割的串,param chr 分割符号
	  出口参数：
	  返    回：return String[] 分割后的字符串数组,不包含最后一个|后面的字符串
	  整 理 人：
	  编写日期：20051220
	  修改备注：
	  ********************************************************/
	public static String[] doSplit(String str, char chr) {
		int iCount = 0;
		char chrTmp = ' ';
		// 计算分割出多少割字符串
		for (int i = 0; i < str.length(); i++) {
			chrTmp = str.charAt(i);
			if (chrTmp == chr) {
				iCount++;
			}
		}
		String[] strArray = new String[iCount];
		for (int i = 0; i < iCount; i++) {
			int iPos = str.indexOf(chr);
			if (iPos == 0) {
				strArray[i] = "";
			} else {
				strArray[i] = str.substring(0, iPos);
			}
			str = str.substring(iPos + 1); // 从iPos+1到结束,str长度逐步缩小
		}
		return strArray;
	}

}
