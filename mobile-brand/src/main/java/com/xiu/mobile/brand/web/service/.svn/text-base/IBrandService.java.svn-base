package com.xiu.mobile.brand.web.service;


import java.util.List;

import com.xiu.mobile.brand.web.dao.model.XBrandModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 品牌业务逻辑接口
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-4 下午2:01:39 
 * ***************************************************************
 * </p>
 */

public interface IBrandService {
	
	/**
	 * 通过品牌ID获取品牌信息
	 * @param brandId
	 * @return
	 */
	XBrandModel getBrandInfoById(long brandId);
	
	/**
	 * 通过品牌名称获取有上架商品的名牌信息
	 * @param name
	 * @return
	 */
	XBrandModel getBrandHasGoodsByName(String name);
	
	public List<XBrandModel> getBrandInfo();
	
}
