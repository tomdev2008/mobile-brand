package com.xiu.mobile.brand.web.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.brand.web.dao.model.XSalesCatalogCond;

public interface SalesCatalogCondDao {
	/**
	 * 根据ID查询运营分类对象
	 * @param catGroupId
	 * @return
	 */
    XSalesCatalogCond selectByPrimaryKey(Long catGroupId);
    /**
     * 查询x_sales_catalog_cond中的field2字段
     * field2字段为json模式
     * @param catGroupId
     * @return
     */
    String selectByPrimaryKeyForJson(Long catGroupId);
    
    Map<Long, String> selectByPrimaryKeysForJson(List<Long> catGroupIds);
    /**
     * 取得全部的属性项			William.zhang	20130705
     * @return
     */
    List<XSalesCatalogCond> selectAllForAttrGroup();
    /**
     * 取得全部的属性项 的值		William.zhang	20130705
     * @return
     */
    List<XSalesCatalogCond> selectAllForAttrValue();
}