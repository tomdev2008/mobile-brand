package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.GoodsVo;
import com.xiu.sales.common.itemsettle.dataobject.ItemSettleResultDO;


/** 
 * 
* @Description: TODO(调用mbrand系统) 
* @author haidong.luo@xiu.com
* @date 2015年12月18日 下午6:24:55 
*
 */
public interface EIMbrandManager {

	/**
	 * 查询商品价格
	 * @param logonName
	 * @return
	 */
	public List<ItemSettleResultDO> queryGoodsPrice(String goodSns);
	
	/**
	 * 查询商品价格
	 * Map  
	 *    goodSn      走秀码
	 *    finalPrice  最终价
	 */
	public List<Map> queryGoodsPriceList(String goodSns);
	
	/**
	 * 一品多商：卖场列表页商品调用mbrand接口过滤
	 * @param goodsSns
	 * @param goodsVoList
	 */
	public void filterGoodsByMbrand(String goodsSns, List<GoodsVo> goodsVoList);
}
