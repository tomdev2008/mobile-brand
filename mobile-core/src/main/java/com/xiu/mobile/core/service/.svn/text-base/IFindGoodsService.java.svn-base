package com.xiu.mobile.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.FindGoods;
import com.xiu.mobile.core.model.FindGoodsBo;
import com.xiu.mobile.core.model.FindGoodsVo;
import com.xiu.mobile.core.model.LoveGoodsBo;
import com.xiu.mobile.core.model.Page;

public interface IFindGoodsService {
	
	/**
	 * 判断商品是否添加
	 * @param goodsSn
	 * @return
	 */
	boolean isFindGoodsExist(String goodsSn);
	
	/**
	 * 通过走秀码获取发现商品信息
	 * @param goodsSn
	 * @return
	 */
	FindGoods getFindGoodsBySn(String goodsSn);

	/**
	 * 分页获得发现商品集合
	 * @param valMap
	 * @return
	 */
	List<FindGoodsVo> getFindGoodsList(HashMap<String, Object> valMap)throws Exception;

	/**
	 * 获得发现商品集合总数
	 * @return
	 */
	int getFindGoodsListCnt()throws Exception;
	
	/**
	 * 查询登陆用户是否赞了发现商品
	 * @param valMap
	 * @return
	 */

	List<LoveGoodsBo> getLoveGoodsList(HashMap<String, Object> valMap)throws Exception;
	/**
	 * 某商品用户收藏总数
	 * @param goodsSn
	 * @return
	 */
	int getLovedCountofGoods(String goodsSn)throws Exception;

	/**
	 * 添加喜爱商品
	 * @param loveGoods
	 * @return
	 * @throws Exception
	 */
	int addLovedTheGoods(LoveGoodsBo loveGoods)throws Exception;
	
	/**
	 * 搜索发现商品
	 * @param findGoodsBo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<FindGoodsBo> searchFindGoodsList(FindGoodsBo findGoodsBo,Page<?> page) throws Exception;

	/**
	 * 用户取消点赞发现商品
	 * @param valMap
	 * @return
	 */
	int delLovedTheGoods(HashMap<String, Object> valMap)throws Exception;
	
	/**
	 * 添加发现商品
	 * @param findGoods
	 * @return
	 */
	int addFindGoods(FindGoods findGoods);
	
	/**
	 * 修改发现商品
	 * @param findGoods
	 * @return
	 */
	int updateFindGoods(FindGoods findGoods);
	
	/**
	 * 删除发现商品
	 * @param id
	 * @return
	 */
	int deleteFindGoods(long id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDelete(List<Long> ids);
	
	/**
	 * 获取发现商品信息
	 * @param id
	 * @return
	 */
	FindGoods getFindGoods(long id);

	/**
	 * 搜索发现商品
	 * @param findGoodsBo
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<FindGoods> searchFindGoodsList(FindGoods findGoods,Page<?> page);
	
	List<FindGoods> findAll();
	
	/**
	 * 导入单品发现商品
	 * @param date
	 * @return
	 */
	int importGoods(Map<String, Map<Object,Object>> data,Long createUserId);
	
	/**
	 * 筛选商品中心中存在的goodsSn
	 * @param goodsSnList
	 * @return
	 */
	List<String> checkGoodsSn(List<String> goodsSnList);

}
