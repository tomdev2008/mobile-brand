package com.xiu.mobile.portal.common.util;

import com.xiu.mobile.portal.model.GoodsVo;


public class ComparatorDiscountAsc<T> implements java.util.Comparator<T> {

	@Override
	public int compare(Object arg0, Object arg1) {
		GoodsVo goodsVo1 = (GoodsVo) arg0;
		GoodsVo goodsVo2 = (GoodsVo) arg1;
		String s1 = goodsVo1.getDiscount().trim();
		String s2 = goodsVo2.getDiscount().trim();
		Double d1 = Double.parseDouble(s1);
		Double d2 = Double.parseDouble(s2);
		return d1.compareTo(d2);
	}
}
