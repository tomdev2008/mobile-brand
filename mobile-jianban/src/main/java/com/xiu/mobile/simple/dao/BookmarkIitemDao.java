package com.xiu.mobile.simple.dao;

import java.util.HashMap;
import java.util.List;

import com.xiu.mobile.simple.model.BookmarkIitemBo;


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

}
