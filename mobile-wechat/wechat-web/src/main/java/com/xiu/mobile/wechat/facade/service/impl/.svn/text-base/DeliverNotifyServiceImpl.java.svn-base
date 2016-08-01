package com.xiu.mobile.wechat.facade.service.impl;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiu.mobile.wechat.core.constants.Constants;
import com.xiu.mobile.wechat.core.utils.HttpUtil;
import com.xiu.mobile.wechat.core.utils.WeChatApiUtil;
import com.xiu.mobile.wechat.facade.constants.FacadeContants;
import com.xiu.mobile.wechat.facade.model.NotifyResult;
import com.xiu.mobile.wechat.facade.service.IDeliverNotifyService;
import com.xiu.mobile.wechat.facade.utils.JSONUtil;

/**
 * 微信发货通知接口
 * 
 * <pre>
 * 为了更好地跟踪订单的情况，需要第三方在收到最终支付通知之后，调用发货通知API告知微信后台该订单的发货状态。
 * </pre>
 * 
 * <pre>
 * 请在收到支付通知发货后，一定调用发货通知接口，否则可能影响商户信誉和资金结算。
 * </pre>
 * 
 * @author wangzhenjiang
 */
@Service("deliverNotifyService")
public class DeliverNotifyServiceImpl implements IDeliverNotifyService {
	private static final Logger logger = LoggerFactory.getLogger(DeliverNotifyServiceImpl.class);

	public NotifyResult sendNotification(String openId, String transId, String outTradeNo) {
		// 先通知微信WEB支付用户
		String appId = Constants.WEB_APPID;
		String appSecret = Constants.WEB_APPSECRET;
		String appKey = Constants.WEB_PAYSIGNKEY;
		NotifyResult result = this.requestWechatAPI(appId, appSecret, appKey, openId, transId, outTradeNo);
		// 如果通知返回49003，则可能为微信APP支付用户，则用APP的相关参数再次进行通知
		// {"errcode":49003,"errmsg":"not match openid with appid"}
		if (!result.isSuccess() && "49003".equals(result.getErrorCode())) {
			appId = Constants.APP_APPID;
			appSecret = Constants.APP_APPSECRET;
			appKey = Constants.APP_PAYSIGNKEY;
			result = this.requestWechatAPI(appId, appSecret, appKey, openId, transId, outTradeNo);
		}
		return result;
	}

	private NotifyResult requestWechatAPI(String appId, String appSecret, String appKey, String openId, String transId, String outTradeNo) {

		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String deliverStatus = "1";
		String deliverMsg = "ok";
		String app_signature_string = "appid=" + appId + "&appkey=" + appKey + "&deliver_msg=" + deliverMsg + "&deliver_status="
				+ deliverStatus + "&deliver_timestamp=" + timestamp + "&openid=" + openId + "&out_trade_no=" + outTradeNo + "&transid="
				+ transId;
		String app_signature_sha = DigestUtils.shaHex(app_signature_string);

		LinkedMap params = new LinkedMap();
		params.put("appid", appId);
		params.put("openid", openId);
		params.put("transid", transId);
		params.put("out_trade_no", outTradeNo);
		params.put("deliver_timestamp", timestamp);
		params.put("deliver_status", deliverStatus);
		params.put("deliver_msg", deliverMsg);
		params.put("app_signature", app_signature_sha);
		params.put("sign_method", "sha1");

		String jsonResult = null;
		try {
			// 获取发货通知URL 、 AccessToken
			String url = String.format(FacadeContants.DELIVER_NOTIFY_URL,
					WeChatApiUtil.getAccessToken(appId, appSecret, Constants.TYPE_WEB));
			JSONObject objJson = new JSONObject();
			objJson.putAll(params);
			// 发送请求，返回结果：{"errcode":0,"errmsg":"ok", ...... }
			jsonResult = HttpUtil.requestPost(url, objJson.toString(), "utf-8");
			if (logger.isDebugEnabled()) {
				logger.debug("调用发货通知:------------");
				logger.debug("openId:{}", openId);
				logger.debug("transId:{}", transId);
				logger.debug("outTradeNo:{}", outTradeNo);
				logger.debug("result:{}", jsonResult);
			}
		} catch (IOException e) {
			logger.error("调用发货通知接口发生异常.", e);
		}
		return JSONUtil.convertToNotifyResult(jsonResult);
	}
}
