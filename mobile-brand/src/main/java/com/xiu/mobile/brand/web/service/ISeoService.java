/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-11 下午4:17:11 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.service;

import java.util.Map;

import com.xiu.mobile.brand.web.dao.model.XBrandModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(SEO优化业务逻辑接口) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-11 下午4:17:11 
 * ***************************************************************
 * </p>
 */

public interface ISeoService {

	/**
	 * 品牌商品列表页SEO TDK优化信息
	 * @param brand
	 * @return Map<String, String>
	 *         key : title 
	 *         key : keyWord
	 *         key : description
	 */
	Map<String, String> buildBrandPageSEOInfo(XBrandModel brand);
}
