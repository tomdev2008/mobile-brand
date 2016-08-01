/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-6 下午2:06:40 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.mobile.brand.web.dao;

import java.util.List;

import com.xiu.mobile.brand.web.dao.model.XiuCatalogDBModel;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 运营分类数据持久层 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-6 下午2:06:40 
 * ***************************************************************
 * </p>
 */

public interface CatalogDao {
	
	/**
     * 从商品中心数据库查询运营分类列表(只查询diaplay=1)
     * @return
     */
	List<XiuCatalogDBModel> selectAllLevel3CatalogListByDB() ;
    
    /**
     * 从商品中心数据库查询所有运营分类列表(查询diaplay=1、0)
     * @return
     */
    List<XiuCatalogDBModel> selectAllLevel3CatalogListByDB2() ;

}
