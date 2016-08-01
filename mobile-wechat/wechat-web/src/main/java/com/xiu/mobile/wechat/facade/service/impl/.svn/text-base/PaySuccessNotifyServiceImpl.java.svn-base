package com.xiu.mobile.wechat.facade.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.HttpUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.constants.FacadeContants;
import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.model.OrderBaseInfo;
import com.xiu.mobile.wechat.facade.service.IPaySuccessNotifyService;
import com.xiu.mobile.wechat.facade.utils.JSONUtil;

/**
 * 支付成功消息通知服务接口 <br>
 * <br>
 * 当用户支付成功时，调用此接口向微信用户发送消息通知。
 * 
 * @author wangzhenjiang
 * 
 * @since 2014年5月20日
 */
@Service("paySuccessNotifyService")
public class PaySuccessNotifyServiceImpl implements IPaySuccessNotifyService {
	private static final Logger logger = LoggerFactory.getLogger(PaySuccessNotifyServiceImpl.class);

	@Override
	public NotifyResult sendNotification(String openId, OrderBaseInfo orderBaseInfo) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("touser", openId);
		params.put("template_id", FacadeContants.PAY_SUCCESS_TEMPLATE_ID);
		params.put("url", FacadeContants.ORDER_STATUS_HREF.replace("ORDERID", orderBaseInfo.getOrderId()));
		// 消息栏上边框颜色
		params.put("topcolor", FacadeContants.TEMPLATE_BORDER_COLOR);
		// 组装通知信息
		Map<String, String> msgData = new LinkedHashMap<String, String>();
		String title = "订单编号：" + orderBaseInfo.getOrderSn();
		msgData.put("title", JSONUtil.assembleTemplateAttr(title));
		msgData.put("orderMoneySum", JSONUtil.assembleTemplateAttr("￥" + orderBaseInfo.getOrderMoneySum()));
		msgData.put("orderProductName", JSONUtil.assembleTemplateAttr(orderBaseInfo.getOrderProductName()));
		String remark = "\n" + "订单已经支付成功，我们会尽快为您安排发货。祝您购物愉快！";
		msgData.put("Remark", JSONUtil.assembleTemplateAttr(remark));

		params.put("data", msgData.toString());

		String result = "";
		try {
			// 获得ACCESS_TOKEN
			String access = WeChatApiUtil.getAccessToken(Constants.WEB_APPID, Constants.WEB_APPSECRET,Constants.TYPE_WEB);
			// 获得请求路径
			String url = FacadeContants.TEMPLATE_MSG_URL.replace("ACCESS_TOKEN", access);
			JSONObject paramsJson = new JSONObject();
			paramsJson.putAll(params);
			// 发送模板消息， 请求返回结果 {"errcode":0,"errmsg":"ok","msgid":200241615}
			result = HttpUtil.requestPost(url, paramsJson.toString(), "utf-8");

			if (logger.isDebugEnabled()) {
				logger.debug("调用支付成功通知接口:------------");
				logger.debug("openId:{}", openId);
				logger.debug("params:{}", paramsJson.toString());
				logger.debug("result:{}", result);
			}
		} catch (IOException e) {
			logger.error("调用支付成功通知接口时发生异常.", e);
		}
		return JSONUtil.convertToNotifyResult(result);
	}
}
