package com.xiu.mobile.portal.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.constants.GlobalConstants;
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
		if("PAY_VIRTUAL".equals(payType)){
			return "虚拟账户支付";
		}
		if("WANLITONG_WAP".equals(payType)){
			return "万里通WAP支付";
		}
		if("WeChat_Pro".equalsIgnoreCase(payType)){
			return "微信支付";
		}
		if("PAYEASE_APPLEPAY".equalsIgnoreCase(payType)){
			return "applePay支付";
		}
		
		return OrderEnum.PayTypeEnum.getValueByKey(payType);
	}
	
	/**
	 * 按类型获取app跳转url
	 * @param type
	 * @return
	 */
	public static String getXiuAppUrlByType(Integer type){
		String[] urls=XiuConstant.XIU_APP_URL;
		int[] urlTypes=XiuConstant.XIU_APP_URL_TYPE;
		for (int i = 0; i < urlTypes.length; i++) {
			if(type==urlTypes[i]){
				return urls[i];
			}
		}
		return "";
	}
	
	
	/**
	 * 获取秀的样式显示数字
	 * @param num
	 * @return
	 */
	
	static Map<String,String> levelMap=new HashMap<String,String>(); 
	static{
		levelMap.put("00", "LV0");
		levelMap.put("01", "LV0");
		levelMap.put("02", "LV1");
		levelMap.put("03", "LV2");
		levelMap.put("04", "LV3");
		levelMap.put("05", "LV4");
		levelMap.put("06", "LV5");
		levelMap.put("07", "LV6");
	}
	/**
	 * 获取用户对应秀客等级
	 * @param uucLevel
	 * @return
	 */
	public static String getUserLevel(String uucLevel){
		String level=levelMap.get(uucLevel);
		if(level==null){
			level=levelMap.get("02");
		}
		return level;
	}
	
	/**
	 * 获取随机密码
	 * @return
	 */
	public static String getRandomPassword() {
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
		    int index = rand.nextInt(i);
		    int tmp = array[index];
		    array[index] = array[i - 1];
		    array[i - 1] = tmp;
		}
		int result = 0;
		for(int i = 0; i < 6; i++) {
			result = result * 10 + array[i];
		}
		
		String value = String.valueOf(result);
		if(value.length() == 5) {
			value = value + String.valueOf(rand.nextInt(10));
		}
		
		return value;
	}
	
	/**
	 * 获取随机的数字或字母
	 * @param length
	 * @return
	 */
	public static String getRandomCharAndNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) { // 字符串
				// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
				str += (char) (97 + random.nextInt(26));// 取得小写字母
			} else { // 数字
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
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
