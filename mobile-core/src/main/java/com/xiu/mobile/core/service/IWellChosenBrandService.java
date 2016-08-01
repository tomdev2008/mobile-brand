package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WellChosenBrandBo;
import com.xiu.mobile.core.model.WellChosenBrandVo;

public interface IWellChosenBrandService {

	/**
	 * 添加精选品牌
	 * @param wellChosenBrand
	 * @return
	 */
	int addWellChosenBrand(WellChosenBrandBo wellChosenBrand)throws Exception;

	/**
	 * 修改某精选品牌
	 * @param brandId
	 * @param orderSequence
	 * @param bannerPic
	 * @return
	 */
	int updateWellChosenBrandByBrandId(Long id, Long orderSequence,String bannerPic)throws Exception;

	/**
	 * 删除某精选品牌
	 * @param brandId
	 * @return
	 */
	int deleteWellChosenBrandByBrandId(Long id)throws Exception;
	
	/**
	 * 加载修改的某精选品牌
	 * @param brandId
	 * @return
	 */
	WellChosenBrandVo getWellChosenBrandVoList(Long id)throws Exception;

	/**
	 * 按条件分页查询精选品牌
	 * @param map
	 * @param page
	 * @return
	 */
	List<WellChosenBrandVo> getWellChosenBrandList(Map<Object, Object> map,
			Page<?> page)throws Exception;

	/**
	 * 按品牌名称查询详细信息
	 * @param brandName
	 * @return
	 */
	List<DataBrandBo> getBrandList(String brandName)throws Exception;

	/**
	 * 导入精选品牌
	 * @param data
	 * @param id
	 * @return
	 */
	int importWellChosenBrand(Map<String, Map<Object, Object>> data, Long id)throws Exception;

	/**
	 * 检查BrandCode是否存在 
	 * @param bcList
	 * @return
	 */
	List<String> checkBrandCode(List<String> bcList)throws Exception;
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteWellChosenBrandByIds(List<Long> ids)throws Exception;
}
