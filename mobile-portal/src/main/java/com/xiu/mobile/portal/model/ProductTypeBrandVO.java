package com.xiu.mobile.portal.model;

import java.util.List;

/**
 * 产品分类品牌
 * @author coco.long
 * @time	2015-08-26
 */
public class ProductTypeBrandVO {

	//产品分类
	private String productType;
	//品牌列表
	private List<String> brandList;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<String> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<String> brandList) {
		this.brandList = brandList;
	}
	
}
