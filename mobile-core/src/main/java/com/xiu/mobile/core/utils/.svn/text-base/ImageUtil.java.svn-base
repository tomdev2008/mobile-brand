package com.xiu.mobile.core.utils;

import org.apache.commons.lang.StringUtils;

public class ImageUtil {
	
	public static String headPortraitPrefixURL = "http://6.xiustatic.com/user_headphoto";
//	public static String showImageURL = "http://6.xiustatic.com";
	private static String[] IMG_DOMAIN = {"http://6.xiustatic.com/", "http://7.xiustatic.com/", "http://8.xiustatic.com/"};
	private static String[] GOODS_IMG_DOMAIN = {"http://image1.zoshow.com/", "http://image2.zoshow.com/", "http://image3.zoshow.com/"};
	
	public static String showImageAdd="/www/target/docdir/images";
	
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
	
	/**
	 * 获取秀图片地址
	 * @param imageUrl
	 * @return
	 */
	public static String getShowimageUrl(String imageUrl){
		return getShowimageUrl(imageUrl, "other");
	}
	
	/**
	 * 获取秀图片地址
	 * @param imageUrl
	 * @return
	 */
	public static String getShowimageUrl(String imageUrl, String type){
		if(StringUtils.isNotBlank(imageUrl)
				&& !imageUrl.contains("http://")){
			int index = Math.abs(imageUrl.hashCode() % 3);
			
			String imgDomain = IMG_DOMAIN[index];
			//商品的域名
			if("goods".equals(type)){
				imgDomain = GOODS_IMG_DOMAIN[index];
			}
			
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				imageUrl = imgDomain + imageUrl.substring(1); 
			} else {
				imageUrl = imgDomain + imageUrl;
			}
		}
		return imageUrl;
	}
	
	/**
	 * 获取秀图片地址
	 * @param imageUrl
	 * @return
	 */
//	public static String getShowimageUrl(String imageUrl) {
//		String result = null;
//		
//		if(StringUtils.isNotBlank(imageUrl)) {
//			String firstChr = imageUrl.substring(0, 1);
//			if(firstChr.equals("/")) {
//				result = showImageURL + imageUrl; 
//			} else {
//				result = showImageURL + "/" +imageUrl;
//			}
//		} 
//		
//		return result;
//	}
	//获取文件夹地址
	public static String getShowImageAdd(String imageUrl){
		String result = null;
		
		if(StringUtils.isNotBlank(imageUrl)) {
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				result = showImageAdd + imageUrl; 
			} else {
				result = showImageAdd + "/" +imageUrl;
			}
		} 
		return result;
	}
}
