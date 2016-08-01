package com.xiu.mobile.brand.web.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonFilter;

import com.xiu.mobile.brand.web.util.JsonWrapper;

/**
 * 运营分类树
 * 
 * @author Leon
 * 
 */
@JsonFilter(JsonWrapper.CATALOG_BO_FILTER)
public class CatalogBo implements Comparable<CatalogBo>, Serializable, Cloneable {

	private static final long serialVersionUID = -4387932763093391720L;
	
	/**
	 * 分类ID
	 */
	private int catalogId;
	
	/**
	 * 分类名称
	 */
	private String catalogName;

	/**
	 * 商品数量
	 */
	private long itemCount;
	
	/**
	 * 父分类ID
	 */
	private int parentCatalogId;

	/**
	 * 子分类
	 */
	private List<CatalogBo> childCatalog;

	/**
	 * 是否选中
	 */
	private boolean selected;
	
	/**
	 * 类目对应的图片
	 */
	private String img = "";
	/**
	 * 类目排序值
	 */
	private int rank;
	/**
	 * 是否显示（1：显示，2：不显示）
	 */
	private String display;
	
	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * 增加子分类
	 * 
	 * @param catalogBo
	 */
	public void addChildCatalog(CatalogBo catalogBo) {
		if (null == this.childCatalog) {
			this.childCatalog = new ArrayList<CatalogBo>();
		}
			
		this.childCatalog.add(catalogBo);
	}

	/**
	 * 清空所有的子分类
	 */
	public void cleanChildCatalog() {
		this.childCatalog = null;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public List<CatalogBo> getChildCatalog() {
		return childCatalog;
	}

	public void setChildCatalog(List<CatalogBo> childCatalog) {
		this.childCatalog = childCatalog;
	}


	@Override
	public CatalogBo clone() {
		CatalogBo cloneObj = null;
		try {
			cloneObj = (CatalogBo) super.clone();
		} catch (CloneNotSupportedException e1) {
			e1.printStackTrace();
			return this;
		}
		return cloneObj;
	}

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	public int getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		if (null != img) {
			this.img = img;
		}
	}

	/**
	 * Json转换过滤parentCatalogId
	 * @return
	 */
	@JsonIgnore
	public int getParentCatalogId() {
		return parentCatalogId;
	}

	public void setParentCatalogId(int parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public int compareTo(CatalogBo o) {
		return this.rank - o.getRank();
	}

}
