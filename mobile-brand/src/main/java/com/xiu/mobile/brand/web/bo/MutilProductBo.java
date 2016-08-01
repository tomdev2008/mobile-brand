package com.xiu.mobile.brand.web.bo;

import java.io.Serializable;
/**
 * 提供APP卖场通过SN查询一品多商信息
 * @author rian.luo@xiu.com
 *
 * 2016-1-29
 */
public class MutilProductBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5698323434196133345L;

    //商品id
	private String goodsId;
	//走秀码
	private String goodsSn;
	//商品名称
	private String goodName;
	//走秀价
	private Double xiuPrice;
	//市场价
	private Double mkPrice;
	//最终价
	private Double finalPrice;
	//折扣
	private Double disCount;
	//图片地址
	private String img;
	//英文品牌名
	private String brandNameEn;
	//中文品牌名
	private String brandNameCn;
	//上架下状态（1：上架，0：下架）
	private int onSale;
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsSn() {
		return goodsSn;
	}
	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public Double getXiuPrice() {
		return xiuPrice;
	}
	public void setXiuPrice(Double xiuPrice) {
		this.xiuPrice = xiuPrice;
	}
	public Double getMkPrice() {
		return mkPrice;
	}
	public void setMkPrice(Double mkPrice) {
		this.mkPrice = mkPrice;
	}
	public Double getDisCount() {
		return disCount;
	}
	public void setDisCount(Double disCount) {
		this.disCount = disCount;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getBrandNameEn() {
		return brandNameEn;
	}
	public void setBrandNameEn(String brandNameEn) {
		this.brandNameEn = brandNameEn;
	}
	public String getBrandNameCn() {
		return brandNameCn;
	}
	public void setBrandNameCn(String brandNameCn) {
		this.brandNameCn = brandNameCn;
	}
	public int getOnSale() {
		return onSale;
	}
	public void setOnSale(int onSale) {
		this.onSale = onSale;
	}
	public Double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}
	@Override
	public String toString() {
		return "MutilProductBo [goodsId=" + goodsId + ", goodsSn=" + goodsSn
				+ ", goodName=" + goodName + ", xiuPrice=" + xiuPrice
				+ ", mkPrice=" + mkPrice + ", finalPrice=" + finalPrice
				+ ", disCount=" + disCount + ", img=" + img + ", brandNameEn="
				+ brandNameEn + ", brandNameCn=" + brandNameCn + ", onSale="
				+ onSale + "]";
	}
}
