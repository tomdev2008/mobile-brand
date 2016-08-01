package com.xiu.mobile.simple.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.simple.dao.BookmarkIitemDao;
import com.xiu.mobile.simple.dao.BookmarkIitemListDao;
import com.xiu.mobile.simple.model.BookmarkIitemBo;
import com.xiu.mobile.simple.model.BookmarkIitemListVo;
import com.xiu.mobile.simple.service.IBookmarkService;
/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION :收藏商品业务逻辑层
 * @AUTHOR : wangchengqun
 * @DATE :2014-5-27 
 *       </p>
 **************************************************************** 
 */
@Service("bookmarkService")
public class BookmarkServiceImpl implements IBookmarkService{

	private static Logger logger = Logger.getLogger(BookmarkServiceImpl.class);
	@Autowired
	private BookmarkIitemDao bookmarkIitemDao;
	@Autowired
	private BookmarkIitemListDao bookmarkIitemListDao;
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public List<BookmarkIitemListVo> getItemListByUserId(Long userId)
			throws Exception {
		List<BookmarkIitemListVo> bookmarkIitemList=bookmarkIitemListDao.getItemListByUserId(userId);
		if(null!=bookmarkIitemList&&bookmarkIitemList.size()>0){
			return bookmarkIitemList;
		}else{
			logger.info("找不到此用户的收藏夹:" + userId);
			return null;
		}
		
	}

	@Override
	public int addIitemVo(BookmarkIitemBo iitemVo) throws Exception{
		int result = 0;
		result =bookmarkIitemDao.insertIitem(iitemVo)>=0?0:-1;
		return result;
	}

	@Override
	public int updateIitemListByUserIdAndItemListId(
			BookmarkIitemListVo iitemList)throws Exception {
		int result = 0;
		result =bookmarkIitemListDao.updateIitemListByUserIdAndItemListId(iitemList)>=0?0:-1;
		return result;
	}

	@Override
	public int addIitemListVo(BookmarkIitemListVo iitemList) throws Exception{
		int result = 0;
		result =bookmarkIitemListDao.insertIitemList(iitemList)>=0?0:-1;
		return result;
	}

	@Override
	public int deleteBookmark(HashMap<String, Object> valMap)throws Exception {
		int result = 0;
		result =bookmarkIitemDao.deleteIitem(valMap)>=0?0:-1;
		return result;
	}
 
	@Override
	public List<BookmarkIitemBo> getBookmark(HashMap<String, Object> valMap)
			throws Exception {
		List<BookmarkIitemBo> bookmarkIitemList=bookmarkIitemDao.getIitemByPage(valMap);
		return bookmarkIitemList;
	}

	@Override
	public int getBookmarkCount(Long userId) throws Exception {
		return  bookmarkIitemDao.getIitemCount(userId);
	}

	@Override
	public List<BookmarkIitemBo> getItemByUserIdAndCatentryId(HashMap<String, Object> valMap){
		try{
		List<BookmarkIitemBo> bookmarkIitemList=bookmarkIitemDao.getItemByUserIdAndCatentryId(valMap);
		return bookmarkIitemList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getGoodsSnByCatentryId(Long catentryId){
		try{	
		String goodsSn=goodsDao.getGoodsSnByGoodsId(catentryId.toString());
		return goodsSn;
	}catch(Exception e){
		e.printStackTrace();
		logger.info("获得商品走秀码异常:"+e.getMessage());
		return null;
	}
	}

}
