package com.xiu.mobile.portal.model;

import java.io.Serializable;

/**
 * 商品尺码信息VO
 * @author coco.long
 * @time	2015-08-24
 */
public class GoodsSizeVO implements Serializable {

	private static final long serialVersionUID = 8256283503710934324L;
	//尺码名称
	private String sizeName;
	//商品数量
	private int goodsNum;
	
	public String getSizeName() {
		return sizeName;
	}
	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	
}
