package com.xiu.mobile.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.dao.GoodsDao;
import com.xiu.mobile.portal.dao.BookmarkIitemDao;
import com.xiu.mobile.portal.dao.BookmarkIitemListDao;
import com.xiu.mobile.portal.model.BookmarkIitemListVo;
import com.xiu.mobile.portal.model.BookmarkIitemBo;
import com.xiu.mobile.portal.service.IBookmarkService;
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
	public int addBatchIitemVos(List<BookmarkIitemBo> iitemVos)
			throws Exception {		
		int result = 0;
		for (BookmarkIitemBo iitemVo:iitemVos) {
			result=result+bookmarkIitemDao.insertIitem(iitemVo)>=0?0:-1;
		}
		return result;
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
	public List<BookmarkIitemBo> getItemByUserIdAndCatentryIds(Map valMap){
		try{
			List<BookmarkIitemBo> bookmarkIitemList=bookmarkIitemDao.getFavorGoodsVoListByGoods(valMap);
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
	
	/**
	 * 批量检测商品是否收藏
	 */
	public List hasExistsFavorGoodsBatch(Map map) {
		List resultList = new ArrayList();
		try {
			String[] goodsIdArr = (String[]) map.get("goodsIdArr");
			List list = bookmarkIitemDao.getFavorGoodsListByGoods(map); //查询收藏的商品列表
			for(String goodsId : goodsIdArr) {
				//查询商品ID数组
				Map goods = new HashMap();
				goods.put("goodsId", goodsId);
				goods.put("favorGoods", false);
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i++) {
						Map goodsMap = (Map) list.get(i);
						String catentry_id = goodsMap.get("CATENTRY_ID").toString();
						if(goodsId.equals(catentry_id)) {
							goods.put("favorGoods", true);
							list.remove(i);
							break;
						}
					}
				}
				resultList.add(goods);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("批量检测商品是否收藏异常:"+e.getMessage());
			return resultList;
		}
		return resultList;
	}

	/**
	 * 批量删除收藏的商品
	 */
	public int deleteBatchBookmark(HashMap<String, Object> valMap)
			throws Exception {
		int result = 0;
		result =bookmarkIitemDao.deleteBatchIitem(valMap)>=0?0:-1;
		return result;
	}

	/**
	 * 查询商品的收藏数量
	 */
	public int getGoodsFavorCounts(String goodsId) throws Exception {
		int favorCounts = bookmarkIitemDao.getGoodsFavorCounts(goodsId); 
		return favorCounts;
	}

	/**
	 * 删除商品降价信息
	 */
	public boolean deleteGoodsReducedPriceInfo(String goodsId) throws Exception {
		bookmarkIitemDao.deleteGoodsReducedPriceInfo(goodsId);
		return true;
	}



}
