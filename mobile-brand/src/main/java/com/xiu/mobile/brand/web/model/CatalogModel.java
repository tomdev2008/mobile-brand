package com.xiu.mobile.brand.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * com.xiu.search.core.catalog.CatalogModel.java
 * 
 * @Description: TODO 存入缓存的运营分类树
 * 
 * @author lvshuding
 * 
 * @date 2013-7-1 下午12:10:22
 * 
 * @version V1.0
 */
public class CatalogModel implements Comparable<CatalogModel>, Serializable {

	private static final long serialVersionUID = 3555603321626424543L;

	/**
	 * 分类ID
	 */
	private int catalogId;
	
	/**
	 * 分类名称
	 */
	private String catalogName;
	
	/**
	 * 类目图片
	 */
	private String img;
	/**
	 * 父分类ID
	 */
	private int parentCatalogId;

	/**
	 * 子分类ID
	 */
	private List<Integer> childIdList;

	/**
	 * 排序顺序
	 */
	private int sortNum;

	private int mktType;
	
	private long itemCount;
	/**
	 * 显示状态
	 */
	private String display;

	public CatalogModel(int catalogId, String catalogName, int parentId,
			int sortNum, long itemCount, int mktType, String display, String img) {
		this.catalogId = catalogId;
		this.catalogName = catalogName;
		this.parentCatalogId = parentId;
		this.sortNum = sortNum;
		this.itemCount = itemCount;
		this.mktType = mktType;
		this.display = display;
		this.img = img;
	}

	/**
	 * 增加子分类
	 * 
	 * @param catalogBo
	 */
	public void addChildId(Integer childId) {
		if (null == this.childIdList) {
			this.childIdList = new ArrayList<Integer>();
		}
			
		this.childIdList.add(childId);
	}
	public void setChildIdList(List<Integer> idList){
		this.childIdList = idList;
	}
	/**
	 * @return the catalogId
	 */
	public int getCatalogId() {
		return catalogId;
	}

	/**
	 * @return the catalogName
	 */
	public String getCatalogName() {
		return catalogName;
	}

	/**
	 * @return the parentCatalogId
	 */
	public int getParentCatalogId() {
		return parentCatalogId;
	}

	/**
	 * @return the childCatalog
	 */
	public List<Integer> getChildIdList() {
		return childIdList;
	}

	/**
	 * @return the sortNum
	 */
	public int getSortNum() {
		return sortNum;
	}

	/**
	 * @return the mktType
	 */
	public int getMktType() {
		return mktType;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display
	 *            the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public void setParentCatalogId(int parentCatalogId) {
		this.parentCatalogId = parentCatalogId;
	}

	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}

	public void setMktType(int mktType) {
		this.mktType = mktType;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	@Override
	public int compareTo(CatalogModel o) {
		return this.sortNum - o.getSortNum();
	}
	
}
