package com.xiu.mobile.brand.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.brand.web.bo.DeliverBO;
import com.xiu.mobile.brand.web.bo.FacetFilterBo;
import com.xiu.mobile.brand.web.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.mobile.brand.web.bo.FacetFilterValueBo;
import com.xiu.mobile.brand.web.cache.DeliverInfoCache;
import com.xiu.mobile.brand.web.cache.XiuAttrGroupCache;
import com.xiu.mobile.brand.web.dao.model.DeliverModel;
import com.xiu.mobile.brand.web.model.AttrGroupJsonModel;
import com.xiu.mobile.brand.web.service.IFacetFilterService;
import com.xiu.mobile.brand.web.util.StaticClothSizeCache;
import com.xiu.mobile.brand.web.util.XiuSearchStringUtils;
import com.xiu.mobile.core.constants.FacetPriceRangeQueryEnum;
import com.xiu.mobile.core.constants.GoodsIndexFieldEnum;

@Service("facetFilterService")
public class FacetFilterSerivceImpl implements IFacetFilterService {

	@Autowired
	private XiuAttrGroupCache xiuAttrGroupCache;
	@Autowired
	private DeliverInfoCache deliverInfoCache;

	@Override
	public FacetFilterBo parseBrandFacetFilter(FacetField facetField) {
		if (null == facetField || facetField.getValues() == null)
			return null;
		FacetFilterBo bo = new FacetFilterBo();
		bo.setFacetFieldName(GoodsIndexFieldEnum.BRAND_ID_NAME.name());
		bo.setFacetDisplay("品牌");
		bo.setFacetType(FacetTypeEnum.BRAND);
		FacetFilterValueBo fvBo;
		String[] idNameArr;
		int firstIndex;
		for (Count count : facetField.getValues()) {
			if (StringUtils.isNotBlank(count.getName()) && count.getCount() > 0) {
				firstIndex = count.getName().indexOf("_");
				if (firstIndex > 0) {
					idNameArr = new String[2];
					idNameArr[0] = count.getName().substring(0, firstIndex);
					idNameArr[1] = count.getName().substring(firstIndex + 1, count.getName().length());
					
					if (StringUtils.isNumeric(idNameArr[0])
							&& StringUtils.isNotBlank(idNameArr[1])) {
						fvBo = new FacetFilterValueBo();
						fvBo.setId(idNameArr[0]);
						fvBo.setName(idNameArr[1]);
						fvBo.setItemCount(count.getCount());
						bo.addFacetValueBo(fvBo);
					}
				}
			}
		}
		return bo;
	}

	@Override
	public FacetFilterBo parsePriceRangeFacetFilter(
			Map<String, Integer> priceRange) {
		if (null == priceRange || priceRange.size() == 0)
			return null;
		FacetFilterBo bo = new FacetFilterBo();
		bo.setFacetFieldName(GoodsIndexFieldEnum.PRICE_FINAL.name());
		bo.setFacetDisplay("价格");
		bo.setFacetType(FacetTypeEnum.PRICE);
		FacetFilterValueBo fvBo;
		Map<String, FacetFilterValueBo> valueMap = new HashMap<String, FacetFilterValueBo>();
		
		for (Entry<String, Integer> pr : priceRange.entrySet()) {
			if (null != pr.getValue() && pr.getValue() > 0) {
				fvBo = new FacetFilterValueBo();
				fvBo.setItemCount(pr.getValue());
				valueMap.put(pr.getKey(), fvBo);
			}
		}
		
		String queryStr;
		for (FacetPriceRangeQueryEnum prq : FacetPriceRangeQueryEnum.values()) {
			queryStr = prq.getTermRangeQuery().toString();
			if (valueMap.containsKey(queryStr)) {
				fvBo = valueMap.get(queryStr);
				fvBo.setId(String.valueOf(prq.getOrder()));
				fvBo.setName(prq.getDisplay());
				bo.addFacetValueBo(fvBo);
			}
		}
		return bo;
	}
	
	@Override
	public FacetFilterBo parseDeliverFacetFilter(FacetField facetField) {
		if (null == facetField || facetField.getValues() == null)
			return null;
		FacetFilterBo bo = new FacetFilterBo();
		bo.setFacetFieldName(GoodsIndexFieldEnum.SPACE_FLAG.name());
		bo.setFacetDisplay("发货地");
		bo.setFacetType(FacetTypeEnum.DELIVER);
		
		Map<String, Set<DeliverBO>> map = new HashMap<String, Set<DeliverBO>>();
		for(Count count : facetField.getValues()) {
			String code = count.getName();
			DeliverModel space = deliverInfoCache.getDeliverModel(Integer.valueOf(code));
			
			if(space == null) {
				continue;
			}
			
			if(!map.containsKey(space.getName())) {
				Set<DeliverBO> spaceBOs = new HashSet<DeliverBO>();
				spaceBOs.add(new DeliverBO(space, count.getCount()));
				map.put(space.getName(), spaceBOs);
			} else {
				Set<DeliverBO> spaceBOs = map.get(space.getName());
				if(spaceBOs != null) {
					spaceBOs.add(new DeliverBO(space, count.getCount()));
				} else {
					spaceBOs = new HashSet<DeliverBO>();
					spaceBOs.add(new DeliverBO(space, count.getCount()));
					map.put(space.getName(), spaceBOs);
				}
			}
		}
		
		Set<Entry<String, Set<DeliverBO>>> entrySet = map.entrySet();
		for(Entry<String, Set<DeliverBO>> entry : entrySet) {
			String name = entry.getKey();
			Set<DeliverBO> value = entry.getValue();
			
			if(value == null || value.size() == 0) {
				continue;
			}
			
			FacetFilterValueBo facet = new FacetFilterValueBo();
			String id = "";
			long count = 0;
			for(DeliverBO spaceBO : entry.getValue()) {
				id = id + spaceBO.getSpace().getCode() + "_";
				count += spaceBO.getCount();
			}
			id = id.substring(0, id.length() - 1);
			
			facet.setName(name);
			facet.setId(id);
			facet.setItemCount(count);
			bo.addFacetValueBo(facet);
		}
		return bo;
	}

	@Override
	public FacetFilterBo formatSelectFacetFilter(FacetTypeEnum type,
			long valueId) {
		FacetFilterBo ret = new FacetFilterBo();
		if (FacetTypeEnum.PRICE.equals(type)) {
			FacetPriceRangeQueryEnum priceEnum = FacetPriceRangeQueryEnum.valueOf((int) valueId);
			if (null == priceEnum) {
				return null;
			}
				
			ret.setFacetType(type);
			ret.setFacetDisplay("价格");
			ret.setFacetFieldName(GoodsIndexFieldEnum.PRICE_FINAL.fieldName());
			FacetFilterValueBo vBo = new FacetFilterValueBo();
			vBo.setId(String.valueOf(valueId));
			vBo.setName(priceEnum.getDisplay());
			ret.addFacetValueBo(vBo);
		}
		return ret;
	}

	/**
	 * 解析属性筛选结果 支持多选 William.zhang 20130508<br>
	 * 索引分组查询结果的解析<br>
	 * 将名称通过attrIndexFieldNameMap进行初始化
	 * 
	 * @return
	 */
	@Override
	public FacetFilterBo parseAttrFacetFilter(FacetField facetField,
			List<List<String>> attrValIdList,
			Map<String, AttrGroupJsonModel> attrIdNameMap) {
		if (null == facetField || facetField.getValues() == null
				|| attrIdNameMap == null
				|| !attrIdNameMap.containsKey(facetField.getName())
				) {
			return null;
		}

		FacetFilterBo bo = new FacetFilterBo();
		bo.setFacetFieldName(facetField.getName());
		AttrGroupJsonModel attrGroupJsonModel = attrIdNameMap.get(facetField.getName());
		
		if (null == attrGroupJsonModel) {
			return null;
		}
			
		// 必须小于四个字
		String name = attrGroupJsonModel.getName();
		if (StringUtils.isNotBlank(name)) {
			if (name.length() > 4)
				name = name.substring(0, 4);
			bo.setFacetDisplay(name);
		}
		
		bo.setFacetType(FacetTypeEnum.ATTR);
		FacetFilterValueBo fvBo;
		String[] idNameArr;
		int firstIndex;
		String idStr;
		int indexof;
		List<String> attrGroupList = Arrays.asList(attrGroupJsonModel.getAttrGroupValueIds());
		Set<String> allAttrValIdSet = new HashSet<String>();
		
		for (Count count : facetField.getValues()) {
			if (StringUtils.isNotBlank(count.getName()) && count.getCount() > 0) {
				firstIndex = count.getName().indexOf("_");
				if (firstIndex > 0) {
					idNameArr = new String[2];
					idNameArr[0] = count.getName().substring(0, firstIndex);
					idNameArr[1] = count.getName().substring(
							firstIndex + 1, count.getName().length());
					if (XiuSearchStringUtils.isIntegerNumber(idNameArr[0])
							&& StringUtils.isNotBlank(idNameArr[1])) {

						// 根据isAll字段，判断页面输出类容 
						indexof = attrGroupList.indexOf(idNameArr[0]);
						fvBo = new FacetFilterValueBo();
						fvBo.setId(idNameArr[0]);
						fvBo.setName(idNameArr[1]);
						fvBo.setItemCount(count.getCount());
						fvBo.setShowOrder(indexof);
						allAttrValIdSet.add(fvBo.getId().toString());
						
						if (attrGroupJsonModel.isAll()) {
							bo.addFacetValueBo(fvBo);
						} else {
							if (indexof >= 0) {
								bo.addFacetValueBo(fvBo);
							}
						}
					}
				}
			}
		}
		if (bo.getFacetValues() == null || bo.getFacetValues().size() == 0) {
			return null;
		}
			
		List<List<String>> unselectAttrVals;
		StringBuilder sb;
		
		for (FacetFilterValueBo o : bo.getFacetValues()) {
			unselectAttrVals = null;
			if (attrValIdList != null && attrValIdList.size() > 0) {
				unselectAttrVals = new ArrayList<List<String>>();
				loop2: for (List<String> list : attrValIdList) {
					for (String str : list) {
						if (allAttrValIdSet.contains(str))
							continue loop2;
					}
					unselectAttrVals.add(list);
				}
			}
			
			if (CollectionUtils.isEmpty(unselectAttrVals)) {
				o.setFilter(String.valueOf(o.getId()));
			} else {
				sb = new StringBuilder();
				
				for (List<String> list : unselectAttrVals) {
					if (CollectionUtils.isNotEmpty(list)) {
						if (sb.length() > 0)
							sb.append(";");
						sb.append(StringUtils.join(list, "|"));
					}
				}
				if (sb.length() > 0)
					sb.append(";");
				sb.append(String.valueOf(o.getId()));
				o.setFilter(sb.toString());
			}
		}

		// 获取attrId
		name = facetField.getName();
		indexof = name.indexOf('_');
		if (indexof <= 0) {
			return null;
		}
		idStr = name.substring(indexof + 1, name.length());
		if (StringUtils.isNumeric(idStr)) {
			bo.setFacetId(Long.valueOf(idStr));
		}
		if("规范尺码".equals(bo.getFacetDisplay().trim()) || "尺码".equals(bo.getFacetDisplay().trim())){ 
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
		}else if(bo.getFacetValues() != null && bo.getFacetValues().size()>1){
			Collections.sort(bo.getFacetValues(), new  Comparator<FacetFilterValueBo>() {
				@Override
				public int compare(FacetFilterValueBo o1, FacetFilterValueBo o2) {
					int tmpVal = o2.getShowOrder()-o1.getShowOrder();
					if(tmpVal!=0){
						return (int)Math.signum(tmpVal);
					}
					return (int)Math.signum((o2.getItemCount() - o1.getItemCount()));
				}
			});
		}
		/*Collections.sort(bo.getFacetValues(),
				new Comparator<FacetFilterValueBo>() {
					@Override
					public int compare(FacetFilterValueBo o1,
							FacetFilterValueBo o2) {
						return o2.getShowOrder() - o1.getShowOrder();
					}
				});*/

		// 属性值展示排序 
		/*if (bo.getFacetValues() != null && bo.getFacetValues().size() >= 1) {
			for (FacetFilterValueBo ff : bo.getFacetValues()) {
				Integer orderBy = xiuAttrGroupCache.selectAttrValueForId(Long.valueOf(ff.getId()));
				
				// 如果取得的排序顺序为空，则将该条属性放在最后
				if (orderBy == null) {
					ff.setShowOrder(Integer.MIN_VALUE);
				} else {
					ff.setShowOrder(orderBy);
				}

			}

			Collections.sort(bo.getFacetValues(),
					new Comparator<FacetFilterValueBo>() {
						public int compare(FacetFilterValueBo a,
								FacetFilterValueBo b) {
							int one = a.getShowOrder();
							int two = b.getShowOrder();
							return one > two ? -1 : 1;
						}
					});
		}*/
		
		// 规范颜色名称展示不正确，现在需要重新设置一下 
		if ("规范尺码".equals(bo.getFacetDisplay().trim())) {
			bo.setFacetDisplay("尺码");
			bo.setShowOrder(Integer.MAX_VALUE-1);
		}
		if ("规范颜色".equals(bo.getFacetDisplay().trim())) {
			bo.setFacetDisplay("颜色");
			bo.setShowOrder(Integer.MAX_VALUE-2);
		}
		
		return bo;
	}

}
