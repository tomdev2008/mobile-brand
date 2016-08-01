package com.xiu.mobile.brand.web.bo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.xiu.mobile.brand.web.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.mobile.brand.web.util.Page;
import com.xiu.mobile.brand.web.util.StaticClothSizeCache;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(搜索结果模型) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-9-9 下午5:48:41 
 * ***************************************************************
 * </p>
 */

public class SearchBo {

	/**
	 * 搜索商品结果
	 */
	private List<GoodsItemBo> goodsItems;
	
	/**
	 * 运营分类树
	 */
	private List<CatalogBo> catalogs;
	
	/**
	 * 属性过滤条件
	 */
	private List<FacetFilterBo> attrFacets;
	
	/**
	 * 分页信息
	 */
	private Page page;
	
	/**
	 * 增加属性筛选
	 * @param facetFilterBo
	 */
	public void addAttrFacetBoList(FacetFilterBo facetFilterBo) {
		if (attrFacets == null) {
			attrFacets = new ArrayList<FacetFilterBo>();
		}
		
		if(facetFilterBo != null) {
			attrFacets.add(facetFilterBo);
		}
	}
	
	public FacetFilterBo removeAttrFacetBoList(String facetName) {
		if (attrFacets == null) {
			return null;
		}
			
		for (int i = 0, len = attrFacets.size(); i < len; i++) {
			if (facetName.equals(attrFacets.get(i).getFacetFieldName())) {
				return attrFacets.remove(i);
			}
		}
		return null;
	}

	/**
	 * @return the goodsItems
	 */
	public List<GoodsItemBo> getGoodsItems() {
		return goodsItems;
	}

	/**
	 * @param goodsItems the goodsItems to set
	 */
	public void setGoodsItems(List<GoodsItemBo> goodsItems) {
		this.goodsItems = goodsItems;
	}

	/**
	 * @return the catalogs
	 */
	public List<CatalogBo> getCatalogs() {
		return catalogs;
	}

	/**
	 * @param catalogs the catalogs to set
	 */
	public void setCatalogs(List<CatalogBo> catalogs) {
		this.catalogs = catalogs;
	}

	/**
	 * @return the attrFacets
	 */
	public List<FacetFilterBo> getAttrFacets() {
		return attrFacets;
	}

	/**
	 * @param attrFacets the attrFacets to set
	 */
	public void setAttrFacets(List<FacetFilterBo> attrFacets) {
		this.attrFacets = attrFacets;
	}

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}
	/**
	 * 筛选项排序，【品牌，价格，尺码，颜色，发货地】
	 */
	public void sortAttrFacets(){
		if (attrFacets == null || attrFacets.isEmpty()) {
			return;
		} 
		List<FacetFilterBo> tempFacets = new ArrayList<FacetFilterBo>(Arrays.asList(new FacetFilterBo[]{null,null,null,null,null}));
		Iterator<FacetFilterBo> iterator = attrFacets.iterator();
		for (;iterator.hasNext();) {
			FacetFilterBo bo = iterator.next();
			if (FacetTypeEnum.BRAND.equals(bo.getFacetType())) {
					Collections.sort(bo.getFacetValues(), new  Comparator<FacetFilterValueBo>() {
						 Collator clt=Collator.getInstance(java.util.Locale.CHINA); 
						@Override
						public int compare(FacetFilterValueBo o1, FacetFilterValueBo o2) {
							 String o1FirstLetter = com.xiu.mobile.core.utils.XiuSearchStringUtils.getFirstLetter(o1.getName()).toUpperCase().substring(0,1);
							 String o2FirstLetter = com.xiu.mobile.core.utils.XiuSearchStringUtils.getFirstLetter(o2.getName()).toUpperCase().substring(0,1);
								int rst = o1FirstLetter.compareToIgnoreCase( o2FirstLetter );
								if(rst!=0){
									return rst;
								}
								return (int)Math.signum((o2.getItemCount() - o1.getItemCount()));
						}
					});
				tempFacets.set(0, bo);
				iterator.remove();
			}else if (FacetTypeEnum.PRICE.equals(bo.getFacetType())) {
				tempFacets.set(1, bo);
				iterator.remove();
			}else if (FacetTypeEnum.ATTR.equals(bo.getFacetType())) {
				if ("尺码".equals(bo.getFacetDisplay())) {
					Collections.sort(bo.getFacetValues(), new  Comparator<FacetFilterValueBo>() {
						@Override
						public int compare(FacetFilterValueBo o1, FacetFilterValueBo o2) {
							double pos1 = StaticClothSizeCache.getInstance().getSize(o1.getName().trim());
							double pos2 = StaticClothSizeCache.getInstance().getSize(o2.getName().trim());
							if ( pos1 > pos2 )
								return 1;
							else
								return -1;
						}
					});
					tempFacets.set(2, bo);
					iterator.remove();
				}else if ("颜色".equals(bo.getFacetDisplay())) {
					tempFacets.set(3, bo);
					iterator.remove();
				}
			}else if(FacetTypeEnum.DELIVER.equals(bo.getFacetType())){
				tempFacets.set(4, bo);
				iterator.remove();
			}
		}
		tempFacets.addAll(attrFacets);
		tempFacets.removeAll(Arrays.asList(new FacetFilterBo[]{null}));
		this.attrFacets = tempFacets;
	}
}
