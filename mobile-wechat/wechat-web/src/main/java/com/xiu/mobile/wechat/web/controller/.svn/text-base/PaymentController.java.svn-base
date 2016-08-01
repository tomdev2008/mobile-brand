package com.xiu.mobile.wechat.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.wechat.web.utils.MessageUtil;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	private static final String APPID = "wxbfd5bd9dd172c9a6";
	private static final String APPSECRET = "cd931ec0bbcf6df2db93e88e202461f4";
	private static final String APPKEY = "JAFMNGSUqytlVnd5QfC6sfYiX84ggPKfE0NH5UQgfAQrckfkQDE69mCPfVnLwmhqhjo8u1CYPJaMkbSq5ghro9xTkEV7frNNZq0fF1tLYpOARVKaqiS7FFLfAVA8D80o";
	private static final String PARTNER = "1217962501";
	private static final String PARTNERKEY = "bec543dc05ae588174fdba3ccba60ef6";

	/**
	 * 跳转到支付demo页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/payment_demo")
	public String toPaymentDemo(HttpServletRequest request, Model model) {
		return "/payment/wxm-pay-api-demo";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/notify_pay")
	public String notifyPay(HttpServletRequest request, HttpServletResponse response) {
		Map params = request.getParameterMap();
		logger.info("params :" + params.toString());
		Set<Map.Entry<String, Object>> set = params.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			logger.info(entry.getKey() + "--->" + entry.getValue());
		}
		Map<String, String> parMap = null;
		try {
			parMap = MessageUtil.parseXml(request.getInputStream());

			logger.info("params :" + parMap.toString());
			Set<Map.Entry<String, String>> parSet = parMap.entrySet();
			for (Iterator<Map.Entry<String, String>> it = parSet.iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
				logger.info("参数：" + entry.getKey() + "--->" + entry.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (parMap != null && parMap.size() > 0) {
				out.print("success");
			} else {
				out.print("fail");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return null;
	}

	/**
	 * 组装支付参数
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toPay")
	public Map<String, Object> toPay(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String appId = this.getAppId();
			String timeStamp = this.getTimeStamp();
			String nonceStr = this.getNonceStr();
			String packageStr = this.getPackage(request);
			String signType = this.getSignType();
			String paySign = this.getPaySign(timeStamp, nonceStr, packageStr);

			model.put("appId", appId);
			model.put("timeStamp", timeStamp);
			model.put("nonceStr", nonceStr);
			model.put("packageStr", packageStr);
			model.put("signType", signType);
			model.put("paySign", paySign);
		} catch (Exception e) {
			logger.error("组装支付参数发生异常：" + e.getMessage());
		}
		return model;
	}

	private String getPaySign(String timeStamp, String nonceStr, String packageStr) {
		String app_id = this.getAppId();
		String app_key = this.getAppKey();
		String nonce_str = nonceStr;
		String package_string = packageStr;
		String time_stamp = timeStamp;
		// 第一步，对所有需要传入的参数加上appkey作一次key＝value字典序的排序
		String keyvaluestring = "appid=" + app_id + "&appkey=" + app_key + "&noncestr=" + nonce_str + "&package=" + package_string
				+ "&timestamp=" + time_stamp;
		String sign = DigestUtils.shaHex(keyvaluestring);
		logger.info("sign >>>:" + sign);
		return sign;
	}

	private String getAppKey() {
		return APPKEY;
	}

	private String getSignType() {
		return "SHA1";
	}

	private String getPackage(HttpServletRequest request) throws UnsupportedEncodingException {
		String banktype = "WX";
		String body = request.getParameter("body");// 商品名称信息，这里由测试网页填入。
		String fee_type = "1";// 费用类型，这里1为默认的人民币
		String input_charset = "GBK";// 字符集，这里将统一使用GBK
		String notify_url = "http://weixin.xiu.com/payment/notify_pay";// 支付成功后将通知该地址
		String out_trade_no = request.getParameter("outTradeNo");// 订单号，商户需要保证该字段对于本商户的唯一性
		String partner = getPartnerId();// 测试商户号
		String spbill_create_ip = this.getRemortIP(request);// 用户浏览器的ip，这个需要在前端获取。这里使用127.0.0.1测试值
		logger.info("---------> Custom Ip: " + spbill_create_ip + " <-----------");
		String total_fee = request.getParameter("totalFee");// 总金额。
		String partnerKey = getPartnerKey();// 这个值和以上其他值不一样是：签名需要它，而最后组成的传输字符串不能含有它。这个key是需要商户好好保存的。

		// 首先第一步：对原串进行签名，注意这里不要对任何字段进行编码。这里是将参数按照key=value进行字典排序后组成下面的字符串,在这个字符串最后拼接上key=XXXX。由于这里的字段固定，因此只需要按照这个顺序进行排序即可。
		String signString = "bank_type=" + banktype + "&body=" + body + "&fee_type=" + fee_type + "&input_charset=" + input_charset
				+ "&notify_url=" + notify_url + "&out_trade_no=" + out_trade_no + "&partner=" + partner + "&spbill_create_ip="
				+ spbill_create_ip + "&total_fee=" + total_fee + "&key=" + partnerKey;

		String md5SignValue = DigestUtils.md5Hex(signString).toUpperCase();
		// 然后第二步，对每个参数进行url转码，如果您的程序是用js，那么需要使用encodeURIComponent函数进行编码。

		banktype = URLEncoder.encode(banktype, "UTF-8");
		body = URLEncoder.encode(body, "UTF-8");
		fee_type = URLEncoder.encode(fee_type, "UTF-8");
		input_charset = URLEncoder.encode(input_charset, "UTF-8");
		notify_url = URLEncoder.encode(notify_url, "UTF-8");
		out_trade_no = URLEncoder.encode(out_trade_no, "UTF-8");
		partner = URLEncoder.encode(partner, "UTF-8");
		spbill_create_ip = URLEncoder.encode(spbill_create_ip, "UTF-8");
		total_fee = URLEncoder.encode(total_fee, "UTF-8");

		// 然后进行最后一步，这里按照key＝value除了sign外进行字典序排序后组成下列的字符串,最后再串接sign=value
		String completeString = "bank_type=" + banktype + "&body=" + body + "&fee_type=" + fee_type + "&input_charset=" + input_charset
				+ "&notify_url=" + notify_url + "&out_trade_no=" + out_trade_no + "&partner=" + partner + "&spbill_create_ip="
				+ spbill_create_ip + "&total_fee=" + total_fee;
		completeString = completeString + "&sign=" + md5SignValue;
		logger.info("package >>> : " + completeString);
		return completeString;
	}

	private String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	private String getPartnerKey() {
		return PARTNERKEY;
	}

	private String getPartnerId() {
		return PARTNER;
	}

	private String getNonceStr() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String nonceStr = "";
		Random r = new Random();
		for (int i = 0; i < 32; i++) {
			nonceStr += chars.charAt(r.nextInt(chars.length()));
		}
		return nonceStr;
	}

	private String getTimeStamp() {
		return String.valueOf(new Date().getTime());
	}

	private String getAppId() {
		return APPID;
	}
}
