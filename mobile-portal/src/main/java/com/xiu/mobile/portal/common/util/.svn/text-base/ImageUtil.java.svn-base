package com.xiu.mobile.portal.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.xiu.mobile.portal.common.constants.XiuConstant;

public class ImageUtil {
	
	public static String headPortraitPrefixURL = "http://6.xiustatic.com/user_headphoto";
	
	public static String askBuyPrefixURL = "http://6.xiustatic.com/purchase_imgs";
	
	public static String brandBannerPrefixURL = "http://images.xiustatic.com";
	
	public static String AdvPrefixURL = "http://6.xiustatic.com";
	
	private static Pattern IMAGE_CREATEDATE_PATTERN =  Pattern.compile("goods([0-9]+)");
	
	public static String getImageConvertUrl(String imageUrl) {
		String result = null;
			
		if(StringUtils.isNotBlank(imageUrl)) {
			int index = imageUrl.lastIndexOf(".");
			String lastStr = imageUrl.substring(index-1, index);
			int lastNum = 0;
			if(lastStr.matches("[1-9]+")) {
				lastNum = Integer.parseInt(lastStr,16);
			}
			String path = XiuConstant.MOBILE_IMAGE[lastNum%3]+ imageUrl; //图片地址拼串
			result = ImageServiceConvertor.replaceImageDomain(path); 
		} 
		
		return result;
	}
	
	/**
	 * 从图片路径解析商品创建时间
	 * @param ImageUrl
	 * @return
	 */
	public static String parseGoodsCreateDateFromImageUrl(String imageUrl){
		
		Matcher m = IMAGE_CREATEDATE_PATTERN.matcher(imageUrl);
		if(m.find()){
			return m.group(1);
		}else{
			return null;
		}
	}
	
	public static String getHeadPortrait(String imageUrl) {
		String result = null;
		
		if(StringUtils.isNotBlank(imageUrl)) {
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				result = headPortraitPrefixURL + imageUrl; 
			} else {
				result = headPortraitPrefixURL + "/" +imageUrl;
			}
		} 
		
		return result;
	}
	
	public static String getAsyBuyImageConvertUrl(String imageUrl) {
		String result = null;
		
		if(StringUtils.isNotBlank(imageUrl)) {
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				result = askBuyPrefixURL + imageUrl; 
			} else {
				result = askBuyPrefixURL + "/" +imageUrl;
			}
		} 
		
		return result;
	}
	
	public static String getBrandLogo(String imageUrl) {
		String result = null;
		
		if(StringUtils.isNotBlank(imageUrl)) {
			int index = imageUrl.lastIndexOf(".");
			String lastStr = imageUrl.substring(index-1, index);
			int lastNum = 0;
			if(lastStr.matches("[1-9]+")) {
				lastNum = Integer.parseInt(lastStr,16);
			}
			String path = XiuConstant.MOBILE_IMAGE[lastNum%3] + "/UploadFiles/xiu/brand" + imageUrl; //图片地址拼串
			result = ImageServiceConvertor.replaceImageDomain(path); 
		} 
		
		return result;
	}
	
	public static String getBrandBanner(String imageUrl) {
		String result = null;
		
		if(StringUtils.isNotBlank(imageUrl)) {
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				result = brandBannerPrefixURL + imageUrl; 
			} else {
				result = brandBannerPrefixURL + "/" +imageUrl;
			}
		} 
		
		return result;
	}
	
	/**
	 * 获取广告图片
	 * @param imageUrl
	 * @return
	 */
	public static String getAdvImageUrl(String imageUrl) {
		String result = null;
		
		if(StringUtils.isNotBlank(imageUrl)) {
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				result = AdvPrefixURL + imageUrl; 
			} else {
				result = AdvPrefixURL + "/" +imageUrl;
			}
		} 
		
		return result;
	}
	
}
