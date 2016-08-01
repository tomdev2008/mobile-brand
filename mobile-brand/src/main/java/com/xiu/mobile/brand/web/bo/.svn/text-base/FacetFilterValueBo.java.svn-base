package com.xiu.mobile.brand.web.bo;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * <p>
 * ************************************************************** 
 * @Description: 筛选属性值
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-9 下午3:57:01 
 * ***************************************************************
 * </p>
 */
public class FacetFilterValueBo implements Cloneable, Serializable {

	private static final long serialVersionUID = 6267789437435163137L;

	/**
	 * 属性值ID或品牌ID或价格筛选序号
	 */
	private String id;
	/**
	 * 属性值名称或品牌名称或价格的展示名称
	 */
	private String name;
	/**
	 * 属性值包含商品数量
	 */
	private long itemCount;
	/**
	 * 属性值页面的查询参数，例如filter=12523|12312|2132;64734;274553;56332
	 */
	private String filter;

	/**
	 * 前台展示顺序
	 */
	private int showOrder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * json转换过滤filter
	 * @return
	 */
	@JsonIgnore
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * 复杂数据请深度clone
	 */
	@Override
	public FacetFilterValueBo clone() {
		try {
			FacetFilterValueBo ret = (FacetFilterValueBo) super.clone();
			return ret;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * json转换过滤showOrder
	 * @return
	 */
	@JsonIgnore
	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

}
