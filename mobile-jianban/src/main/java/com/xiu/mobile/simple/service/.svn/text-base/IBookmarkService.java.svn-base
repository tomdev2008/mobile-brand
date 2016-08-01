package com.xiu.mobile.simple.service;

import java.util.HashMap;
import java.util.List;

import com.xiu.mobile.simple.model.BookmarkIitemBo;
import com.xiu.mobile.simple.model.BookmarkIitemListVo;


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
	 * 根据商品Id获得走秀码
	 * @param catentryId
	 * @return
	 * @throws Exception
	 */
	String getGoodsSnByCatentryId(Long catentryId)throws Exception;

}
