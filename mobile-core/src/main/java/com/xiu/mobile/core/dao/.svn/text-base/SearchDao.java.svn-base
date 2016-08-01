package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.ServiceSearchDateBo;

/**
 * 标签
 * @author Administrator
 *
 */
public interface SearchDao {
//	/**
//	 *  批量添加业务数据到搜索中
//	 * @param params
//	 * @return
//	 */
//	public Integer addBatchDataToSearch(Map params);
	
	/**
	 * 添加业务数据到搜索临时表
	 * @param labelServiceDateBo
	 * @return
	 */
	public int addSearchDate(ServiceSearchDateBo labelServiceDateBo);
	/**
	 * 添加业务数据到搜索临时表
	 * @param labelServiceDateBo
	 * @return
	 */
	public int addBatchSearchDate(List<ServiceSearchDateBo> labelServiceDateBo);
	/**
	 * 查询业务数据搜索临时表
	 * @param getParams
	 * @return
	 */
	public ServiceSearchDateBo getSearchDate(Map getParams);
	
	/**
	 * 更新业务数据到搜索临时表
	 * @param labelServiceDateBo
	 */
	public Integer updateSearchDate(ServiceSearchDateBo labelServiceDateBo);
	
	/**
	 * 更新业务数据到搜索临时表
	 * @param labelServiceDateBo
	 */
	public Integer updateBacthSearchDate(List<ServiceSearchDateBo> labelServiceDateBo);

	/**
	 * 删除过期的搜索数据
	 * @param params
	 */
	public void deleteOverTimeSearchDate(Map params);
}
