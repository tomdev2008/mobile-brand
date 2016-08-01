package com.xiu.mobile.wechat.facade.service;

import com.xiu.mobile.wechat.facade.model.MessageInfo;
import com.xiu.mobile.wechat.facade.model.NotifyResult;

/**
 * 客服接口 
 * <br><br>
 * 该接口用于客服与客户基于微信平台一对一沟通
 * 
 * @author wangzhenjiang
 * 
 * @since  2014年5月20日 
 */
public interface ICustomService {
	
	/**
	 * 发送消息
	 * 
	 * @param openId 消息接收者OPENID
	 * @param message 消息内容
	 * @return NotifyResult 返回结果
	 */
	NotifyResult sendMessage(String openId, String message);
	
	/**
	 * 发送消息
	 * 
	 * @param openId 消息接收者OPENID
	 * @param messageInfo 消息内容
	 * @return NotifyResult 返回结果
	 */
	NotifyResult sendMessage(String openId, MessageInfo messageInfo);
	
}
