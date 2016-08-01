package com.xiu.mobile.wechat.facade.service;

import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.model.OrderBaseInfo;

/**
 * 订单状态更新消息通知服务接口 
 * <br><br>
 * 当订单状态更新时，调用此接口向微信用户发送消息通知。
 * 
 * @author wangzhenjiang
 * 
 * @since  2014年5月20日 
 */
public interface IOrderStatusNotifyService {

	/**
	 * 发送订单状态更新信息
	 * 
	 * @param openId 消息接收者OPENID
	 * @param orderBaseInfo 通知消息实体
	 * @return 返回通知结果
	 */
	NotifyResult sendNotification(String openId, OrderBaseInfo orderBaseInfo);
}
