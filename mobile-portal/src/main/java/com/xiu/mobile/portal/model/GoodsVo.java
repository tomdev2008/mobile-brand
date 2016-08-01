/**  
 * @Project: xiu
 * @Title: GoodsVo.java
 * @Package org.lazicats.xiu.model.topicActivity.vo
 * @Description: TODO
 * @author: chengyuanhuan
 * @date 2013-5-3 下午2:22:30
 * @Copyright: BroadenGate Software Services Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.xiu.mobile.portal.model;

import java.io.Serializable;

public class GoodsVo implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO
	 */ 
	
	private static final long serialVersionUID = 1L;
	private String goodsId; //商品ID
	private String goodsSn	;//商品Sn
	private String goodsName	;//商品名称
	private int  isActivityGoods	;//是否活动商品 0:普通商品 1：活动商品
	private String  activityPrice	;//活动价格
	private String zsPrice	;//走秀价
	private String price	;//市场价格
	private String discount	;//价格折扣
	private String goodsImg	;//商品图片
	private String brandEnName; //品牌英文名
	private String brandCNName; //品牌中文名
	
	private int stateOnsale;//上下架状态  0-未上架、1-已上架
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public int getIsActivityGoods() {
		return isActivityGoods;
	}
	public void setIsActivityGoods(int isActivityGoods) {
		this.isActivityGoods = isActivityGoods;
	}
	public String getActivityPrice() {
		return activityPrice;
	}
	public void setActivityPrice(String activityPrice) {
		this.activityPrice = activityPrice;
	}
	public String getZsPrice() {
		return zsPrice;
	}
	public void setZsPrice(String zsPrice) {
		this.zsPrice = zsPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getGoodsImg() {
		return goodsImg;
	}
	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}
	public int getStateOnsale() {
		return stateOnsale;
	}
	public void setStateOnsale(int stateOnsale) {
		this.stateOnsale = stateOnsale;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getBrandEnName() {
		return brandEnName;
	}
	public void setBrandEnName(String brandEnName) {
		this.brandEnName = brandEnName;
	}
	public String getBrandCNName() {
		return brandCNName;
	}
	public void setBrandCNName(String brandCNName) {
		this.brandCNName = brandCNName;
	}

}
