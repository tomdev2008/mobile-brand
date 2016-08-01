package com.xiu.mobile.core.dao;

import java.util.HashMap;
import java.util.List;

import com.xiu.mobile.core.model.LoveGoodsBo;

/**
 * <p>
 * ************************************************************** 
 * @Description: 喜爱商品数据层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-4
 * ***************************************************************
 * </p>
 */
public interface LoveGoodsDao {

	List<LoveGoodsBo> getLoveGoodsList(HashMap<String, Object> valMap)throws Exception;
	int getLoveGoodsListCount(String goodsSn)throws Exception;
	int addLovedTheGoods(LoveGoodsBo loveGoods)throws Exception;
	int delLovedTheGoods(HashMap<String, Object> valMap)throws Exception;

}
