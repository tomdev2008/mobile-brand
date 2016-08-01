/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-16 下午7:21:26 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.dao;

import java.util.List;

import com.xiu.mobile.brand.web.dao.model.XBrandModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(品牌信息数据持久层) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-16 下午7:21:26 
 * ***************************************************************
 * </p>
 */

public interface BrandDao {
	
	/**
	 * 通过ID获取品牌信息
	 * @param brandId
	 * @return
	 */
	XBrandModel getBrandInfoById(long brandId);

	/**
	 * 获取所有品牌信息
	 * @return
	 */
	List<XBrandModel> getAllBrandInfo();
	
	/**
	 * @Description: 加载所有品牌数据（show_flag=1 and 品牌下挂的有商品）
	 * @return    
	 * @return List<XDataBrand>
	 */
	List<XBrandModel> selectAllByShowFlag1AndHasGoods();
	
	/**
	 * 所有xiu_goods品牌
	 * @return
	 */
	List<XBrandModel> getBrandIDs();
}
