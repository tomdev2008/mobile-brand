package com.xiu.mobile.wechat.core.constants;

import java.util.Collections;
import java.util.List;

/**
 * 
 * 分页使用.
 * 
 */
public class Page<T> {

	// 默认分页条数.
	public static final int DEFAULT_PAGESIZE = 15;

	// 分页参数
	protected int pageNo = 1;
	protected int pageSize = DEFAULT_PAGESIZE;
	protected int totalCount = 0;
	private int totalPages = 0;
	// 可以直接点击的页面.
	protected int morePage = 3;
	protected int preMorePage = 1;
	private int showMinPage = 1;
	private int showMaxPage = morePage;
	// 排序使用.
	// protected List<Map<String, String>> orderBy = new ArrayList<Map<String, String>>();
	// 返回结果
	protected List<T> result = Collections.emptyList();

	private boolean isHasNext = false;
	private boolean isHasPre = false;
	private int firstRecord;
	private int endRecord;

	public Page() {
		super();
	}

	/**
	 * 设置总记录数. 设置记录数时同时计算总页数.
	 */
	public void setTotalCount(final int totalCount) {
		this.totalCount = totalCount;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getShowMinPage() {
		if (this.getTotalPages() <= morePage) {
			showMinPage = 1;
		} else if (this.getPageNo() + preMorePage <= morePage) {
			showMinPage = 1;
		} else {
			showMinPage = this.getShowMaxPage() - morePage + 1;
		}
		return this.showMinPage;
	}

	public int getShowMaxPage() {
		if (this.getTotalPages() <= morePage) {
			this.showMaxPage = this.getTotalPages();
		} else if (this.getPageNo() >= this.getTotalPages()) {
			this.showMaxPage = this.getTotalPages();
		} else if (this.getPageNo() + this.preMorePage >= this.getTotalPages()) {
			this.showMaxPage = this.getTotalPages();
		} else if (this.getPageNo() + this.preMorePage <= this.morePage) {
			this.showMaxPage = this.morePage;
		} else {
			this.showMaxPage = this.getPageNo() + this.preMorePage;
		}
		return this.showMaxPage;
	}

	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1) {
			this.pageNo = 1;
		} else if (this.pageNo >= this.totalPages) {
			this.pageNo = pageNo;
		}
	}

	/**
	 * 获得每页的记录数量,默认为20.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
	 */
	public int getFirstRecord() {
		return ((pageNo - 1) * pageSize) + 1;
	}

	/**
	 * 根据pageNo和PageSize计算当前最后一条记录在结果集中的位置，序号从0开始.
	 * 
	 * @return
	 */
	public long getEndRecord() {
		long end = getFirstRecord() + pageSize;
		return end;
	}

	/**
	 * 取得页内的记录列表.
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * 取得总记录数,默认值为0.
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 根据pageSize与totalCount计算总页数,默认值为0.
	 */
	public int getTotalPages() {
		if (totalCount <= 0)
			this.totalPages = 0;

		int pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount++;
		}
		this.totalPages = pageCount;
		return totalPages;
	}

	public long getLastPage() {
		return getTotalPages();
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (getIsHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean getIsHasNext() {
		// if (getTotalPages() <= morePage) {
		// return false;
		// }
		this.isHasNext = pageNo + 1 <= getTotalPages();
		return this.isHasNext;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean getIsHasPre() {
		// if (getTotalPages() <= morePage) {
		// return false;
		// }
		this.isHasPre = (pageNo - 1 >= 1);
		return this.isHasPre;
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (getIsHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalCount=" + totalCount + ", totalPages=" + totalPages
				+ ", morePage=" + morePage + ", preMorePage=" + preMorePage + ", showMinPage=" + showMinPage + ", showMaxPage="
				+ showMaxPage + ", result=" + result + ", isHasNext=" + isHasNext + ", isHasPre=" + isHasPre + ", firstRecord="
				+ firstRecord + ", endRecord=" + endRecord + "]";
	}

}
