package com.xiu.mobile.portal.service;

/***
 * 邮件管理
 * @author hejianxiong
 *
 */
public interface IEmailService {

	/**
	 * 发送邮件
	 * @param emailAddr 地址
	 * @param subject   标题
	 * @param body  内容
	 * @param creator 创建者
	 * @param senderName 发送者
	 * @return
	 */
	public boolean sendEmail(String emailAddr, String subject, String body, String creator, String senderName);
}
