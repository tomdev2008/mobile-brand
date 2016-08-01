package com.xiu.mobile.simple.common.util;

import java.util.Map;

import com.xiu.mobile.simple.constants.GlobalConstants;
import com.xiu.tc.common.statusenum.OrderEnum;

public class Tools {
	
	public static void addPageInfoToParamMap(Map<String, Object> paramMap, Map<String, Object> input) {
		int pageNum = GlobalConstants.DEFAULT_PAGE_NUM;
		try {
			pageNum = Integer.parseInt(input.get(GlobalConstants.PAGE_NUM).toString().trim());
			if (pageNum < 1) {
				pageNum = GlobalConstants.DEFAULT_PAGE_NUM;
			}
		} catch (Exception e) {

		}

		int pageSize = GlobalConstants.DEFAULT_PAGE_SIZE;
		try {
			pageSize = Integer.parseInt(input.get(GlobalConstants.PAGE_SIZE).toString().trim());
			if (pageSize < 1) {
				pageSize = GlobalConstants.DEFAULT_PAGE_SIZE;
			}
		} catch (Exception e) {

		}

		int startPos = (pageNum - 1) * pageSize;
		int endPos = pageNum * pageSize;

		paramMap.put("startPos", startPos);
		paramMap.put("endPos", endPos);
	}
	
	public static void addPageInfoToParamMap2(Map<String, Object> paramMap, Map<String, Object> input) {
		int pageNum = GlobalConstants.DEFAULT_PAGE_NUM;
		try {
			pageNum = Integer.parseInt(input.get(GlobalConstants.PAGE_NUM).toString().trim());
			if (pageNum < 1) {
				pageNum = GlobalConstants.DEFAULT_PAGE_NUM;
			}
		} catch (Exception e) {

		}

		int pageSize = GlobalConstants.DEFAULT_PAGE_SIZE_2;
		try {
			pageSize = Integer.parseInt(input.get(GlobalConstants.PAGE_SIZE).toString().trim());
			if (pageSize < 1) {
				pageSize = GlobalConstants.DEFAULT_PAGE_SIZE_2;
			}
		} catch (Exception e) {

		}

		int startPos = (pageNum - 1) * pageSize;
		int endPos = pageNum * pageSize;

		paramMap.put("startPos", startPos);
		paramMap.put("endPos", endPos);
	}
	
	/**
	 * 根据商品中心的URL组装成TC下订单时需要的url格式
	 * @param originalImgUrl
	 * @return
	 */
	public static String assembleImgUrlForTc(String imgUrl) {
		if (imgUrl.endsWith("jpg")) {
			return imgUrl;
		}
		
		if (imgUrl.endsWith("/")) {
			imgUrl += "g1_80_80.jpg";
		} else {
			imgUrl += "/g1_80_80.jpg";
		}
		
		return imgUrl;
	}
	
	/**
	 * 根据TC返回的商品URL组装app客户端的URL
	 * @param url
	 * @return
	 */
	public static String assembleImgUrlForApp(String url) {		
		url = url.replace("//upload", "/upload");
		
		if (url.endsWith("jpg")) {
			return url.substring(0, url.lastIndexOf("/"));
		}
		
		if (url.endsWith("/")) {
			return url.substring(0, url.length()-1);
		}
		
		return url;
	}
	
	
	public static String getGoodsSnFromSku(String sku) {
		if (sku == null || sku.length() < 8) {
			return null;
		}
		
		return sku.substring(0, 8);
	}
	
	private static boolean enableImageReplace = new Boolean(ParseProperties.getPropertiesValue("enable.setting.image.replace"));

	public static boolean isEnableImageReplace() {
		return enableImageReplace;
	}

	/**
	 * 根据支付类型返回支付大类
	 * @param payType
	 * @return
	 */
	public static String getPayTypeCat(String payType) {
		if ("COD".equals(payType)) {
			return "PAY_COD";
		} else {
			return "PAY_ONLINE";
		}
	}
	
	/**
	 * 根据mac和appId 计算 md5
	 * @param mac		设备的mac地址
	 * @param appId
	 * @return
	 */
	public static String getMd5ByUdidAndAppId(String mac, String appId) {
		if (mac == null || mac.trim().isEmpty() ||
				appId == null || appId.trim().isEmpty()) {
			return null;
		}
		
		mac = mac.trim();
		appId = appId.trim();
		
		if (mac.contains(":")) {
			// 验证mac地址
			if (! mac.matches("^[0-9A-Fa-f]{2}(:[0-9A-Fa-f]{2}){5}$")) {
				return null;
			}
			
		} else {
			if (mac.length() != 12) {
				return null;
			}
			StringBuilder macTmp = new StringBuilder(17);
			for (int i = 0; i < mac.length(); i++) {
				if (i > 0 && i % 2 == 0) {
					macTmp.append(":");
				}
				macTmp.append(mac.charAt(i));				
			}
			
			mac = macTmp.toString();
		}
		
		mac = mac.toUpperCase();
		
		String str = mac + appId;

		return EncryptUtils.encryptByMD5(str);
	}
	
	/**
	 * 根据支付类型代码返回支付类型描述信息
	 * 
	 * @param payType		支付类型
	 * @return 支付类型描述信息
	 */
	public static String getPayTypeDesc(String payType) {
		if (payType == null || payType.trim().isEmpty()) 
			return "在线支付";
		
		/*
		 * AlipayWire是后加的wap支付方式，OrderEnum.PayTypeEnum 
		 * 中不存在该类型，所以写死在这里 
		 */
		if ("AlipayWire".equals(payType)) {
			return "支付宝WAP移动";
		}
		if ("ALIPAY_WIRE_APP".equals(payType)) {
			return "支付宝APP移动";
		}
		
		if("wechat".equalsIgnoreCase(payType)){
			return "微信支付";
		}
		if("CHINAPAY_MOBILE_APP".equals(payType)){
			return "银联APP支付";
		}
		if("CHINAPAY_MOBILE_WAP".equals(payType)){
			return "银联WAP支付";
		}
		
		return OrderEnum.PayTypeEnum.getValueByKey(payType);
	}
	
	
	public static void main(String[] args) {
//		System.out.println(Tools.assembleImgUrlForApp("http://images.xiu.com/upload/goods20110823/11000071/110000710001"));
//		System.out.println(Tools.assembleImgUrlForApp("http://images.xiu.com/upload/goods20110823/11000071/110000710001/g1_80_80.jpg"));
		
//		System.out.println(Tools.getGoodsSnFromSku("110000090001"));
//		System.out.println(Tools.getPayTypeDesc("COD"));
//		System.out.println(Tools.getPayTypeDesc("99BILL"));
//		System.out.println(Tools.getPayTypeDesc(null));
//		System.out.println(Tools.getPayTypeDesc("  "));
	}
}
