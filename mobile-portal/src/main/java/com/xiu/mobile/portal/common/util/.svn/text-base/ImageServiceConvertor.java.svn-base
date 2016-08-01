/**  
 * @Project: xiu
 * @Title: ImageServiceConvertor.java
 * @Package org.lazicats.xiu.service.image
 * @Description: TODO
 * @author: yong
 * @date 2013-5-10 上午11:20:44
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.common.constants.GoodsConstant;

/**
 * 图片地址转换
 * 
 * @ClassName: ImageServiceConvertor
 * @Description: TODO
 * @author: yong
 * @date 2013-5-10 上午11:20:44
 * 
 */
@Service
public class ImageServiceConvertor {

    private static String domain_REGEX = "(?i)^(http){0,1}(s){0,1}(://){0,1}(www.){0,1}([^/]*?/)";
    private static String random="RANDOM";

    @Resource(name = "imagepropertyMap")
    private static Map<String, String> imageMap;

    private static Map<String, String> imagepropertyMap = null;
    private static List<String> listImage = null;

    @PostConstruct
    public void init() {
        imagepropertyMap = this.imageMap;
        mapToList();
    }

    /**
     * 得到原URL域名
     * 
     * @Title: getUrlDomain
     * @Description: TODO
     * @param httpUrl
     * @return
     * @author: yong
     * @date: 2013-5-10下午05:19:35
     */
    private static String getUrlDomain(String httpUrl) {
        if (null == httpUrl) {
            return null;
        }
        String domainStr = null;
        Pattern p = Pattern.compile(domain_REGEX);
        Matcher matcher = p.matcher(httpUrl);
        if (matcher.find()) {
            domainStr = matcher.group(5);
            domainStr = domainStr.replace("/", "");
        }
        return domainStr;
    }

    /**
     * 根据原域名获得新域名
     * 
     * @Title: imageConvert
     * @Description: TODO
     * @param key
     * @return
     * @author: yong
     * @date: 2013-5-10下午05:20:55
     */
    private static String imageNewDomain(String key) {
        if (null == key) {
            return null;
        }
        if (null == imagepropertyMap || imagepropertyMap.isEmpty()) {
            return key;
        }

        String value = imagepropertyMap.get(key);
        if (null == value || "".equals(value.trim())) {
            return key;
        } else {
            return value;
        }
    }
    
    /**
     * MAP 转 成   LIST
     * @Title: mapToList 
     * @Description: TODO
     * @return  
     * @author: yong
     * @date: 2013-5-24下午05:02:21
     */
    public static List<String> mapToList(){
    	
    	if(null != listImage && !listImage.isEmpty()){
    		return listImage;
    	}
	    if (null != imagepropertyMap && !imagepropertyMap.isEmpty()) {
	    	List<String> newlistImage = new ArrayList<String> ();
	    	Set<String> keySet = imagepropertyMap.keySet();
	    	
	    	Iterator<String> iterator = keySet.iterator();
	    	 while(iterator.hasNext()){
	    		 String key = iterator.next();
	    		 String domainStr = imagepropertyMap.get(key);
	    		 if(!random.equals(domainStr)){
	    			 newlistImage.add(domainStr);
	    		 }
	    	}
	    	 listImage = newlistImage;
	    }
    	return listImage;
    }
    
    
    private static String randomImageDomain(){
    	
    	List<String> imageList = mapToList();
    	if (null == imageList || imageList.isEmpty()) {
            return null;
        }
    	
    	int size = imageList.size();
        Random random = new Random();
        int nextInt = random.nextInt(size);
        if(nextInt > size || nextInt < 0 ){
        	nextInt = 0;
        }
    	return imageList.get(nextInt);
    }

    /**
     * 替换图片服务器域名
     * 
     * @Title: replaceImageDomain
     * @Description: TODO
     * @param httpUrl
     * @return
     * @author: yong
     * @date: 2013-5-10下午05:19:35
     */
    public static String replaceImageDomain(String httpUrl) {
        if (null == httpUrl) {
            return null;
        }
        String domainStr = getUrlDomain(httpUrl);

        String imageDomain = imageNewDomain(domainStr.trim());
        
        if(random.equals(imageDomain)){
        	imageDomain = randomImageDomain();
        	if(null == imageDomain){
        		imageDomain = domainStr.trim();
        	}
        }
        return httpUrl.replaceAll(domain_REGEX, "$1$2$3$4" + imageDomain + "/");
    }
    /**
     * 去掉端口号
     * @param httpUrl
     * @return
     */
    public static String removePort(String httpUrl){
    	if (StringUtil.isEmpty(httpUrl)) {
            return null;
        }
    	String orig = httpUrl.trim();
    	String dom = getUrlDomain(orig);
    	dom = dom.split(":")[0];
    	return httpUrl.replaceAll(domain_REGEX, "$1$2$3$4" + dom + "/");
    }
    /**
     * 商品详情图片替换
     * @param httpUrl
     * @return
     */
    public static String getGoodsDetail(String httpUrl){
    	if (StringUtil.isEmpty(httpUrl)) {
            return null;
        }
//    	String dom = ConfigUtil.getValue("goods_detail_image");
    	String dom = GoodsConstant.GOODS_IMG_REP;
    	return httpUrl.replaceAll(domain_REGEX, "$1$2$3$4" + dom + "/");
    }
    /**
     * 为详情页尺码表增加规格参数
     * @param httpurl
     * @return
     */
    public static String addDetailSize(String httpurl){
    	if (StringUtil.isEmpty((httpurl))) {
            return null;
        }
    	if (httpurl.startsWith(GoodsConstant.GOODS_IMG_START) && !httpurl.endsWith("/")) {
    		String[] arr = httpurl.split("/");
    		String filename = arr[arr.length - 1];
    		if (filename.indexOf(".")<0) {
				return null;
			}
    		String urlNoname = httpurl.substring(0, httpurl.length()-filename.length());
    		String[] farr = filename.split("\\.");
    		String ext = "."+farr[farr.length - 1];
    		String oriName = filename.substring(0,filename.length()-ext.length());
    		String[] narr = oriName.split("_");
    		if (narr.length >= 3) {
    			String wid = narr[narr.length - 2], height = narr[narr.length - 1];
    			if (!(StringUtil.isNumber(wid) || "*".equals(wid))
    					|| !(StringUtil.isNumber(height) || "*".equals(height))) {
    				//不是规格
    				String aname = oriName + ext;
    				return urlNoname + aname;
    			}
    		}else{
    			//没有规格
    			String aname = oriName + ext;
    			return urlNoname + aname;
    		}
		}
    	return httpurl;
    }
    public static void main(String[] args) {
		String imageUrl = "http://images.xiu.com:6080/upload/goods20110927/10015287/100152870001/g1.jpg";
//		System.out.println(removePort(imageUrl));
		System.out.println(addDetailSize(imageUrl));
	}
}
