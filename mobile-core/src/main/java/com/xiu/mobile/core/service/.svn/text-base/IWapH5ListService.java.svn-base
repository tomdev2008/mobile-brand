package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WapH5List;

/**
 * wap-H5列表
 * @author Administrator
 *
 */
public interface IWapH5ListService {
	/**
	 * 获取H5列表信息
	 */
	public Map<String,Object> getH5list(Map<String, Object> params, Page<?> page);
	
	public int addH5list(WapH5List h5list);
	public Long findWapH5Id();
	
	public WapH5List getWapH5ListById(Long id);
	//修改列表信息
	public int updateWapH5List(WapH5List wapH5List);
	
	public int deleteWapH5List(List<Long> ids);
	
	//接口分享查询列表信息
	public Map<String,Object> getWapH5List(Map<String,Object> params);
	
	/**
	 * 查询H5中的url
	 */
	public int findH5Url(String url);

	
	/**
	 *获取适合同步到搜索数量总数
	 * @param params
	 * @return
	 */
	public Integer getSyncToSearchCount(Map syncGetParams);

	/**
	 * 获取适合同步到搜索的数据
	 * @param syncGetParams
	 * @return
	 */
	public List<WapH5List> getSyncToSearchList(Map syncGetParams);
	

}
