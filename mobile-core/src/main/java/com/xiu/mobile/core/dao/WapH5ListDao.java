package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.Topic;
import com.xiu.mobile.core.model.WapH5List;

/**
 * wap-H5列表
 * @author Administrator
 *
 */
public interface WapH5ListDao {
	//分页查询H5列表信息
	public List<WapH5List> getH5list(Map<String,Object> para);
	//统计总数
	public Integer getH5ListCount(Map<String,Object> para);
	
	public Integer addH5list(WapH5List h5list);
	//查询ID
	public Long findWapH5Id();
	//根据ID查询列表信息
	public WapH5List getWapH5ListById(Long id);
	//修改列表信息
	public int updateWapH5List(WapH5List wapH5List);
	//删除列表信息
	public int deleteWapH5List(@Param(value="ids")List<Long> ids);
	//查询组件化数据
	public List<Long> findWapH5ListTypeThree(@Param(value="ids")List<Long> ids);
	//检查列表标题是否有重复
	public int findWapH5ListTitil(String title);
	//查询H5url是否存在
	public int findH5Url(String url);
	/**
	 * 获取同步到搜索的个数
	 * @param params
	 * @return
	 */
	public Integer getSyncToSearchCount(Map params);
	
	/**
	 * 获取同步到搜索的
	 * @param params
	 * @return
	 */
	public List<WapH5List> getSyncToSearchList(Map params);
}
