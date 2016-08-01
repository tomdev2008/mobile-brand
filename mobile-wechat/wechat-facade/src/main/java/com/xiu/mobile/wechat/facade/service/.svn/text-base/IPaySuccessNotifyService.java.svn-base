package com.xiu.mobile.wechat.facade.service;

import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.model.OrderBaseInfo;

/**
 * 支付成功消息通知服务接口 
 * <br><br>
 * 当用户支付成功时，调用此接口向微信用户发送消息通知。
 * 
 * @author wangzhenjiang
 * 
 * @since  2014年5月20日 
 */
public interface IPaySuccessNotifyService {
	/**
	 * 发送支付成功消息通知
	 * 
	 * @param openId 接收信息者微信openId
	 * @param orderBaseInfo 订单基本信息
	 * @return NotifyResult 返回通知结果
	 */
	NotifyResult sendNotification(String openId, OrderBaseInfo orderBaseInfo);
}
