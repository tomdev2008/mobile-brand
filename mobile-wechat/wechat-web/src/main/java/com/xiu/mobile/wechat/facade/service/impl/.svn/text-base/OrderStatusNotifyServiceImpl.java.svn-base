package com.xiu.mobile.wechat.facade.service.impl;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.HttpUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.constants.FacadeContants;
import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.model.OrderBaseInfo;
import com.xiu.mobile.wechat.facade.service.IOrderStatusNotifyService;
import com.xiu.mobile.wechat.facade.utils.JSONUtil;

/**
 * 订单状态更新消息通知服务接口 <br>
 * <br>
 * 当订单状态更新时，调用此接口向微信用户发送消息通知。
 * 
 * @author wangzhenjiang
 * 
 * @since 2014年5月20日
 */
@Service("orderStatusNotifyService")
public class OrderStatusNotifyServiceImpl implements IOrderStatusNotifyService {
	private static final Logger logger = LoggerFactory.getLogger(OrderStatusNotifyServiceImpl.class);

	@Override
	public NotifyResult sendNotification(String openId, OrderBaseInfo info) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("touser", openId);
		params.put("template_id", FacadeContants.ORDER_STATUS_TEMPLATE_ID);
		params.put("url", FacadeContants.ORDER_STATUS_HREF.replace("ORDERID", info.getOrderId()));
		// 消息栏上边框颜色
		params.put("topcolor", FacadeContants.TEMPLATE_BORDER_COLOR);
		// 组装通知信息
		Map<String, String> msgData = new LinkedHashMap<String, String>();
		// String title = "您的订单【" + info.getOrderSn() + "】有一条新的物流信息，点击查看物流详情。";
		String title = info.getRemark();
		msgData.put("title", JSONUtil.assembleTemplateAttr(title));
		msgData.put("OrderSn", JSONUtil.assembleTemplateAttr(info.getOrderSn()));
		// 由于TMS中无法查询订单状态,订单状态目前按照物流信息判断
		String orderStatus = "已发货";
		if (StringUtils.contains(title, "正在准备发货")) {
			orderStatus = "备货中";
		} else if (StringUtils.contains(title, "您的商品已经配送完成")) {
			orderStatus = "订单完结";
		}
		msgData.put("OrderStatus", JSONUtil.assembleTemplateAttr(orderStatus));
		StringBuilder sb = new StringBuilder();
		sb.append("商品名称：");
		sb.append(info.getOrderProductName());
		// sb.append("\n");
		// sb.append("物流信息：");
		// sb.append(info.getRemark());
		msgData.put("remark", JSONUtil.assembleTemplateAttr(sb.toString()));

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
				logger.debug("调用订单状态更新通知接口:------------");
				logger.debug("openId:{}", openId);
				logger.debug("params:{}", paramsJson.toString());
				logger.debug("result:{}", result);
			}
		} catch (IOException e) {
			logger.error("调用订单状态更新通知接口时发生异常.", e);
		}
		return JSONUtil.convertToNotifyResult(result);
	}

}
