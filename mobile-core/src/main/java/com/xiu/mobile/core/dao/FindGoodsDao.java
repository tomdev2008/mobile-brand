package com.xiu.mobile.core.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.FindGoods;
import com.xiu.mobile.core.model.FindGoodsBo;
import com.xiu.mobile.core.model.FindGoodsVo;


/**
 * <p>
 * ************************************************************** 
 * @Description: 发现商品数据层
 * @AUTHOR wangchengqun
 * @DATE 2014-6-4
 * ***************************************************************
 * </p>
 */
public interface FindGoodsDao {
	
	/**
	 * 添加发现商品
	 * @param findGoods
	 * @return
	 */
	int addFindGoods(FindGoods findGoods);
	//获取ID
	long findGoodsId();
	
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
	int batchDelete(@Param(value="ids")List<Long> ids);
	
	/**
	 * 获取发现商品信息
	 * @param id
	 * @return
	 */
	FindGoods getFindGoods(long id);
	
	/**
	 * 根据goodsSn获得发现商品信息
	 * @param goodsSn
	 * @return
	 */
	FindGoods getFindGoodsBySn(String goodsSn);

	/**
	 * 搜索发现商品数量
	 * @param findGoodsBo
	 * @return
	 * @throws Exception
	 */
	int searchFindGoodsCount(FindGoods findGoods) throws Exception;
	
	/**
	 * 搜索发现商品数据列表
	 * @param findGoodsBo
	 * @return
	 * @throws Exception
	 */
	List<FindGoods> searchFindGoodsList(FindGoods findGoodsBo) throws Exception;
	
	List<FindGoods> findAll();
	
	/**
	 * 导入时，把表中还有效的记录改为被替换
	 * @param goodsSn
	 * @return
	 */
	void upateReplace(@Param(value="goodsSn")List<String> goodsSn);
	
	List<FindGoodsVo> getFindGoodsList(HashMap<String, Object> valMap)throws Exception;

	int getFindGoodsListCount()throws Exception;
	
	/**
	 * 判断goodsSn的商品是否存在
	 * @param goodsSn
	 * @return
	 */
	Integer isFindGoodsExist(String goodsSn);

}
