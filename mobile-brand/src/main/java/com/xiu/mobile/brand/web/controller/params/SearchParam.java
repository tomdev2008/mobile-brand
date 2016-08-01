package com.xiu.mobile.brand.web.controller.params;

import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 搜索接口用户传入参数对象
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 上午10:19:01 
 * ***************************************************************
 * </p>
 */

public class SearchParam {
	
	/**
	 * 搜索关键字
	 */
	private String kw;
	/**
	 * 品牌ID，多个用|号隔开
	 */
	private String bId;
	/**
	 * 运营分类
	 */
	private Integer catId;
	/**
	 * 价格过滤
	 */
	private Integer fPrice;
	
	/**
	 * 发货地ID
	 */
	private String dId;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 输入开始价格
	 */
	private Double sPrice;
	/**
	 * 输入结束价格
	 */
	private Double ePrice;
	/**
	 * 过滤条件, 格式-6208|-6189;-5524|-5529
	 */
	private String filter;
	/**
	 * 页数
	 */
	private Integer p;
	/**
	 * 每一页数据数量
	 */
	private Integer pSize;
	
	/**
	 * API版本号
	 */
	private String v;
	
	/**
	 * @return the bId
	 */
	public String getbId() {
		return bId;
	}
	/**
	 * @param bId the bId to set
	 */
	public void setbId(String bId) {
		this.bId = bId;
	}
	
	/**
	 * @return the catId
	 */
	public Integer getCatId() {
		return catId;
	}
	/**
	 * @param catId the catId to set
	 */
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	/**
	 * @return the fPrice
	 */
	public Integer getfPrice() {
		return fPrice;
	}
	/**
	 * @param fPrice the fPrice to set
	 */
	public void setfPrice(Integer fPrice) {
		this.fPrice = fPrice;
	}
	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	/**
	 * @return the p
	 */
	public Integer getP() {
		return p;
	}
	/**
	 * @param p the p to set
	 */
	public void setP(Integer p) {
		this.p = p;
	}
	/**
	 * @return the pSize
	 */
	public Integer getpSize() {
		return pSize;
	}
	/**
	 * @param pSize the pSize to set
	 */
	public void setpSize(Integer pSize) {
		this.pSize = pSize;
	}
	/**
	 * @return the sPrice
	 */
	public Double getsPrice() {
		return sPrice;
	}
	/**
	 * @param sPrice the sPrice to set
	 */
	public void setsPrice(Double sPrice) {
		this.sPrice = sPrice;
	}
	/**
	 * @return the ePrice
	 */
	public Double getePrice() {
		return ePrice;
	}
	/**
	 * @param ePrice the ePrice to set
	 */
	public void setePrice(Double ePrice) {
		this.ePrice = ePrice;
	}
	/**
	 * @return the kw
	 */
	public String getKw() {
		return kw;
	}
	/**
	 * @param kw the kw to set
	 */
	public void setKw(String kw) {
		this.kw = kw;
	}
	/**
	 * @return the dId
	 */
	public String getdId() {
		return dId;
	}
	/**
	 * @param dId the dId to set
	 */
	public void setdId(String dId) {
		this.dId = dId;
	}
	
	/**
	 * @return the v
	 */
	public String getV() {
		return v;
	}
	/**
	 * @param v the v to set
	 */
	public void setV(String v) {
		this.v = v;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
