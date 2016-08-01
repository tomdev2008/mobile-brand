package com.xiu.mobile.wechat.facade.service;

import com.xiu.mobile.wechat.facade.model.NotifyResult;

/**
 * 微信发货通知接口
 * <pre>为了更好地跟踪订单的情况，需要第三方在收到最终支付通知之后，调用发货通知API告知微信后台该订单的发货状态。</pre>
 * <pre>请在收到支付通知发货后，一定调用发货通知接口，否则可能影响商户信誉和资金结算。</pre>
 * 
 * @author wangzhenjiang
 *
 */
public interface IDeliverNotifyService {

	/**
	 * 
	 * 发货通知
	 * 
	 * @param openId 微信OPENID
	 * @param transId 微信订单流水号
	 * @param outTradeNo 走秀订单号
	 * @return  NotifyResult 通知结果
	 */
	NotifyResult sendNotification(String openId, String transId, String outTradeNo);

}
