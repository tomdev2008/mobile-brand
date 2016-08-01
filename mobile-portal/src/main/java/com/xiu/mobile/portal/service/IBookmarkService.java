package com.xiu.mobile.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.BookmarkIitemListVo;
import com.xiu.mobile.portal.model.BookmarkIitemBo;


public interface IBookmarkService {

	/**
	 * 查询用户收藏夹
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<BookmarkIitemListVo> getItemListByUserId(Long userId)throws Exception;
	/**
	 * 新增收藏的商品
	 * @param iitemVo
	 * @return
	 * @throws Exception
	 */
	int addIitemVo(BookmarkIitemBo iitemVo)throws Exception;
	/**
	 * 批量新增收藏的商品
	 * @param iitemVo
	 * @return
	 * @throws Exception
	 */
	int addBatchIitemVos(List<BookmarkIitemBo> iitemVos)throws Exception;
	/**
	 * 修改收藏夹
	 * @param iitemList
	 * @return
	 * @throws Exception
	 */
	int updateIitemListByUserIdAndItemListId(BookmarkIitemListVo iitemList)throws Exception;
	/**
	 * 新增收藏夹
	 * @param iitemList
	 * @return
	 * @throws Exception
	 */
	int addIitemListVo(BookmarkIitemListVo iitemList)throws Exception;
	/**
	 * 删除收藏的商品
	 * @param valMap
	 * @return
	 * @throws Exception
	 */
	int deleteBookmark(HashMap<String, Object> valMap)throws Exception;
	/**
	 * 分页查询收藏的商品
	 * @param valMap
	 * @return
	 * @throws Exception
	 */
	List<BookmarkIitemBo> getBookmark(HashMap<String, Object> valMap)throws Exception;
	/**
	 * 查询收藏的商品总数
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	int getBookmarkCount(Long userId)throws Exception;
	/**
	 * 检查用户是否收藏了某商品
	 * @param userId
	 * @param catentryId
	 * @return
	 */
	List<BookmarkIitemBo> getItemByUserIdAndCatentryId(HashMap<String, Object> valMap)throws Exception;
	/**
	 * 批量查询用户是否收藏了某商品
	 * @param userId
	 * @param catentryId
	 * @return
	 */
	List<BookmarkIitemBo> getItemByUserIdAndCatentryIds(Map valMap)throws Exception;
	/**
	 * 根据商品Id获得走秀码
	 * @param catentryId
	 * @return 
	 * @throws Exception
	 */
	String getGoodsSnByCatentryId(Long catentryId)throws Exception;

	/**
	 * 批量检测商品是否收藏
	 * @param map
	 * @return
	 */
	List hasExistsFavorGoodsBatch(Map map);
	/*
	 * 批量删除收藏的商品
	 * @param valMap
	 * @return
	 * @throws Exception
	 */
	int deleteBatchBookmark(HashMap<String, Object> valMap)throws Exception;
	
	/**
	 * 查询商品的收藏数量
	 * @param map
	 * @return
	 */
	int getGoodsFavorCounts(String goodsId) throws Exception;
	
	/**
	 * 删除商品降价信息
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	boolean deleteGoodsReducedPriceInfo(String goodsId) throws Exception;
}
