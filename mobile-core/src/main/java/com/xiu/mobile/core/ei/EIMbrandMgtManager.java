package com.xiu.mobile.core.ei;

import java.util.List;

import com.xiu.mobile.core.model.GoodsMgtVo;


/** 
 * 
* @Description: TODO(调用mbrand系统) 
* @author haidong.luo@xiu.com
* @date 2015年12月18日 下午6:24:55 
*
 */
public interface EIMbrandMgtManager {
	
	/**
	 * 一品多商：卖场列表页商品调用mbrand接口过滤
	 * @param goodsSns
	 * @param goodsVoList
	 */
	public void filterGoodsByMbrand(String goodsSns, List<GoodsMgtVo> goodsVoList);
}
