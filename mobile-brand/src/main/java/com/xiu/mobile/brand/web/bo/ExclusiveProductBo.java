package com.xiu.mobile.brand.web.bo;

import java.util.List;

import com.xiu.mobile.brand.web.util.Page;

public class ExclusiveProductBo {
	private List<GoodsItemBo> goods;
	private Page page;
	private String updateTime;
	public List<GoodsItemBo> getGoods() {
		return goods;
	}
	public void setGoods(List<GoodsItemBo> goods) {
		this.goods = goods;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
