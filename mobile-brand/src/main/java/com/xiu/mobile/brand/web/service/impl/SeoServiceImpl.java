/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-11 下午4:18:22 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.dao.model.XBrandModel;
import com.xiu.mobile.brand.web.service.ISeoService;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(SEO优化业务逻辑实现类) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-11 下午4:18:22 
 * *************************************************
 * </p>
 */
@Service("seoService")
public class SeoServiceImpl implements ISeoService {

	@Override
	public Map<String, String> buildBrandPageSEOInfo(XBrandModel brand) {
		Map<String, String> seoInfo = new HashMap<String, String>();
		
		boolean isHasCnName = StringUtils.isNotEmpty(brand.getBrandName());
		boolean isHasEnName = StringUtils.isNotEmpty(brand.getEnName());
		
		String showName = isHasCnName ? brand.getBrandName() : brand.getEnName();
		
		StringBuffer title = new StringBuffer("【");
		if(isHasCnName && isHasEnName) {
			title.append(brand.getEnName());
			title.append(" ");
		} 
		title.append(showName);
		title.append("】");
//		title.append(showName);
		title.append("推荐新款");
		title.append(showName);
		title.append("价格|图片-走秀网触屏版");
		
		StringBuffer keyWord = new StringBuffer();
		/*if(isHasCnName && isHasEnName) {
			keyWord.append(brand.getEnName());
			keyWord.append(" ");
		} 
		
		keyWord.append(showName);
		keyWord.append(",");*/
		keyWord.append("新款");
		keyWord.append(showName);
		keyWord.append(",");
		keyWord.append(showName);
		keyWord.append("价格");
		keyWord.append(",");
		keyWord.append(showName);
		keyWord.append("图片");
		
		StringBuffer description = new StringBuffer("走秀网");
		if(isHasCnName && isHasEnName) {
			description.append(brand.getEnName());
			description.append(" ");
		}
		
		description.append("为您提供");
		description.append(showName);
		description.append("价格、");
		description.append(showName);
		description.append("图片，购买新款");
		description.append(showName);
		description.append("，上xiu.com");
		
		if (isHasCnName && isHasEnName) {
			description.append(brand.getEnName());
			description.append(" ");
		}
		
		description.append(showName);
		description.append("专区-全球时尚正品百货！");

		seoInfo.put("title", title.toString());
		seoInfo.put("keyWord", keyWord.toString());
		seoInfo.put("description", description.toString());
		
		return seoInfo;
	}
}
