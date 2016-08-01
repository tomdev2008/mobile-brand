package com.xiu.mobile.portal.service;

import java.util.Map;

import com.xiu.mobile.portal.model.MessageCode;


/**
 * 验证码service层接口
 * @author wenxiaozhuo
 * @date 2014-4-24
 */
public interface IMessageCodeService {

	/**
	 * 保存验证码信息
	 * @param messageCode 验证码
	 * @return
	 */
	public int saveMessageCode(MessageCode messageCode);
	
	/**
	 * 获取短信验证码（如果限定时间内有未过时的则获取旧的，无则生成）
	 * @param msgPhone
	 * @return
	 */
	public String getMessageCodeByPhone(String msgPhone);
	
	public MessageCode findMessageCodeByPhone(String msgPhone);
	
	public int removeMessageCodeByPhone(String msgPhone);

	public int updateMessageCode(MessageCode messageCode);
	
	//获取短信验证码限制状态，1小时内同一台设备同一种业务只能发送五次验证码，一个手机号所有业务只能发送10次验证码
	boolean getSMSLimitStatus(Map map);
	
	//检测邮箱验证码是否存在
	public boolean isEmailVerifyCodeExists(Map map);
	
	//获取邮箱验证码，如果邮箱验证码在有效期内，则返回，没有则生成
	public String getEmailVerifyCode(Map map);
	
	//修改邮箱验证码
	public boolean updateEmailVerifyCode(Map map);
	
	//新增邮箱验证码
	public boolean addEmailVerifyCode(Map map);
	
}
