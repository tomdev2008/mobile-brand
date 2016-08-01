package com.xiu.mobile.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.portal.model.BrandTopicVo;
import com.xiu.mobile.portal.model.FavorBrandBo;

public interface IFavorBrandService {

	/**
	 * 添加收藏品牌
	 * @param favorBrand
	 * @return
	 * @throws Exception 
	 */
	int addFavorBrand(FavorBrandBo favorBrand) throws Exception;
	/**
	 * 删除某收藏品牌
	 * @param valMap
	 * @return
	 * @throws Exception 
	 */
	int delFavorBrand(HashMap<String, Object> valMap) throws Exception;
	/**
	 * 获得用户的某个收藏品牌
	 * @param valMap
	 * @return
	 * @throws Exception 
	 */
	List<FavorBrandBo> getBrandByUserIdAndBrandId(HashMap<String, Object> valMap) throws Exception;
	/**
	 * 获得品牌详细信息
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	List<DataBrandBo> getBrandList(Map<String, Object> params) throws Exception;
	/**
	 * 分页查询收藏品牌
	 * @param valMap
	 * @return
	 * @throws Exception 
	 */
	List<FavorBrandBo> getFavorBrand(HashMap<String, Object> valMap) throws Exception;
	/**
	 * 分页查询收藏品牌的总数
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	int getFavorBrandCount(Long userId) throws Exception;
	/**
	 * 关联品牌收藏的精选卖场
	 * @param valMap
	 * @return
	 */
	List<BrandTopicVo> queryBrandTopicList(HashMap<String, Object> valMap)throws Exception;
	/**
	 * 关联品牌收藏的精选卖场总数
	 * @param brandId
	 * @return
	 */
	int queryBrandTopicListCount(Long brandId)throws Exception;
	/**
	 * 关联品牌收藏的精选卖场总数和卖场Id
	 * @param brandId
	 * @return
	 */
	String queryActivityCount(Long brandId)throws Exception;
	
	/**
	 * 批量删除某收藏品牌
	 * @param valMap
	 * @return
	 * @throws Exception 
	 */
	int delBatchFavorBrand(HashMap<String, Object> valMap) throws Exception;
	
}
