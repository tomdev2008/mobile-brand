package com.xiu.mobile.portal.model;

import java.io.Serializable;

import com.xiu.mobile.core.model.BaseObject;

/**
 * 
* @Description: TODO(秀口令数据) 
* @author haidong.luo@xiu.com
* @date 2016年2月23日 上午11:59:52 
*
 */
public class XiuWordDataVo  extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String  title; //名称
	private String url;//广告图片url
	private Integer type;
	private String imgUrl;
	private Double price; //广告时间
	private String brandName;//标签ID
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	
	
	
}
