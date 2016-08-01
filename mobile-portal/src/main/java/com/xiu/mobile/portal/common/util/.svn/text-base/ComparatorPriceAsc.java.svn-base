package com.xiu.mobile.portal.common.util;

import com.xiu.mobile.portal.model.GoodsVo;

public class ComparatorPriceAsc<T> implements java.util.Comparator<T> {

	@Override
	public int compare(Object arg0, Object arg1) {
		GoodsVo goodsVo1 = (GoodsVo) arg0;
		GoodsVo goodsVo2 = (GoodsVo) arg1;
		Double d1 = Double.parseDouble(goodsVo1.getZsPrice());
		Double d2 = Double.parseDouble(goodsVo2.getZsPrice());
		return (d1).compareTo(d2);
	}

}
