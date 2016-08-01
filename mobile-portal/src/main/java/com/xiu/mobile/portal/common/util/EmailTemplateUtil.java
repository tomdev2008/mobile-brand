package com.xiu.mobile.portal.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailTemplateUtil {
	
	static String datefomate = "yyyy-MM-dd";
	
	public static String logoUrl = "http://images.xiu.com/edm/zt_edm_140228/logo1.jpg";
	
	public static String getDay() {
		SimpleDateFormat sf = new SimpleDateFormat(datefomate); 
		Date now = new Date();
		String day = sf.format(now);
		return day;
	}
	
	/**
	 * 获取找回密码邮件
	 * @param validateCode
	 * @return
	 */
	public static String getFindPasswordBody(String validateCode) {
		String day = getDay(); //日期
		String body = "<html><head><title><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />"
				+ "<style type=\"text/css\"> body { font-family: arial, sans-serif; } </style></title></head>"
				+ "<body><div ><div style=\"margin-left: 2%; margin-right: 2%; padding-top: 30px; padding-left: 30px;\">"
				+ "<img src=\"" + logoUrl + "\" alt=\"走秀logo\" /></div>"
				+ "<div><table cellpadding=\"0\" cellspacing=\"0\" style=\"width:96%; background: #ffffff; border: 1px solid #CCCCCC; margin: 2%;\">"
				+ "<tr><td style=\"font-weight: bold; font-size: 14px; padding-top: 50px; padding-left: 30px;\">亲爱的用户：</td></tr>"
				+ "<tr><td>&nbsp;</td><tr><tr><td style=\"font-size: 14px; padding-top: 20px; padding-left: 30px;\">"
				+ "<p style=\"color:#333333\">您好！感谢您使用走秀服务，您正在进行邮箱验证，本次请求的验证码为：</p>"
				+ "<p><span style=\"font-weight: bold; font-size: 18px; color: #ff9900\">" + validateCode + "</span>"
				+ "&nbsp;<span style=\"color:#979797\">(为了保障您帐号的安全性，请在1小时内完成验证。)<span></p></td>"
				+ "</tr><tr><td style=\"font-size: 14px;color:#333333; padding-top: 70px; padding-left: 30px; padding-bottom: 30px;\"><p>走秀账号团队</p>"
				+ "<p>" + day + "</p></td></tr></table></div></div></body></html>";
		
		return body;
	}
	
	/**
	 * 获取绑定邮箱邮件
	 * @param validateCode
	 * @return
	 */
	public static String getBindingEmailBody(String validateCode) {
		String day = getDay(); //日期
		String body = "<html><head><title><meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />"
				+ "<style type=\"text/css\"> body { font-family: arial, sans-serif; } </style></title></head>"
				+ "<body><div ><div style=\"margin-left: 2%; margin-right: 2%; padding-top: 30px; padding-left: 30px;\">"
				+ "<img src=\"" + logoUrl + "\" alt=\"走秀logo\" /></div>"
				+ "<div><table cellpadding=\"0\" cellspacing=\"0\" style=\"width:96%; background: #ffffff; border: 1px solid #CCCCCC; margin: 2%;\">"
				+ "<tr><td style=\"font-weight: bold; font-size: 14px; padding-top: 50px; padding-left: 30px;\">亲爱的用户：</td></tr>"
				+ "<tr><td>&nbsp;</td><tr><tr><td style=\"font-size: 14px; padding-top: 20px; padding-left: 30px;\">"
				+ "<p style=\"color:#333333\">您好！感谢您使用走秀服务，您正在进行邮箱验证，本次请求的验证码为：</p>"
				+ "<p><span style=\"font-weight: bold; font-size: 18px; color: #ff9900\">" + validateCode + "</span>"
				+ "&nbsp;<span style=\"color:#979797\">(为了保障您帐号的安全性，请在1小时内完成验证。)<span></p></td>"
				+ "</tr><tr><td style=\"font-size: 14px;color:#333333; padding-top: 70px; padding-left: 30px; padding-bottom: 30px;\"><p>走秀账号团队</p>"
				+ "<p>" + day + "</p></td></tr></table></div></div></body></html>";
		
		return body;
	}
}
