package com.xiu.mobile.portal.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.BookmarkIitemBo;


/**
 * <p>
 * ************************************************************** 
 * @Description: 收藏商品数据层
 * @AUTHOR wangchengqun
 * @DATE 2014-5-27
 * ***************************************************************
 * </p>
 */
public interface BookmarkIitemDao 
{

	int insertIitem(BookmarkIitemBo iitemVo)throws Exception;
	

	int deleteIitem(HashMap<String, Object> valMap)throws Exception;

	List<BookmarkIitemBo> getIitemByPage(HashMap<String, Object> valMap)throws Exception;

	int getIitemCount(Long userId)throws Exception;

	List<BookmarkIitemBo> getItemByUserIdAndCatentryId(
			HashMap<String, Object> valMap)throws Exception;
	

	List getFavorGoodsListByGoods(Map map) throws Exception;
	
	List<BookmarkIitemBo>  getFavorGoodsVoListByGoods(Map map) throws Exception;

	/**
	 * 批量删除收藏的商品
	 * @param valMap
	 * @return
	 * @throws Exception
	 */
	int deleteBatchIitem(HashMap<String, Object> valMap)throws Exception;
	
	/**
	 * 查询商品的收藏数量
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	int getGoodsFavorCounts(String goodsId) throws Exception;
	
	int deleteGoodsReducedPriceInfo(String goodsId) throws Exception;
}
