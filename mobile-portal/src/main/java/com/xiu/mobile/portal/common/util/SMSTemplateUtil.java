package com.xiu.mobile.portal.common.util;

public class SMSTemplateUtil {

	/***
	 * 获取注册短信内容
	 * @param validateCode
	 * @return
	 */
	public static String getRegisterBody(String validateCode) {
		String smsBody = " 验证码为" + validateCode + "（此为新用户注册验证码，小心被偷瞄），"
				+ "走秀网欢迎您，祝您在走秀购得舒心、秀得愉快，希望您在时尚达人的道路上越走越远哦  ~ ";
		return smsBody;
	}
	
	/***
	 * 获取忘记密码短信内容
	 * @param validateCode
	 * @return
	 */
	public static String getForgotPasswordBody(String validateCode){
		String smsBody = " 验证码为" + validateCode + "（此为重置密码验证码，如非本人操作，请忽略），请在页面中输入以完成验证   ";
		return smsBody;
	}
	
	
	/***
	 * 获取更换手机验证码
	 * @param validateCode
	 * @return
	 */
	public static String getChangePhoneBody(String validateCode){
		String smsBody = " 验证码为" + validateCode + "（此为账户安全验证码，如非本人操作，请忽略），请在页面中输入以完成验证 ";
		return smsBody;
	}

	/**
	 * 获取通过手机号登录验证码
	 * @param validateCode
	 * @return
	 */
	public static String getLoginBody(String validateCode) {
		String smsBody = " 验证码为" + validateCode + "（此为通过手机号登录验证码，如非本人操作，请忽略），请在页面中输入以完成验证 ";
		return smsBody;
	}
}
