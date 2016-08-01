package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.SubjectVo;

public interface SubjectDao {
	
	/**
	 * 获取首页专题列表
	 * @param params
	 * @return
	 */
	public List<SubjectVo> getIndexSubjectList(Map<String,Object> params);
	
	/**
	 * 获取专题详情
	 * @param params
	 * @return
	 */
	public SubjectVo getSubjectInfo(Map<String,Object> params);
	
	/**
	 * 检查专题是否存在
	 * @param params
	 * @return
	 */
	public Integer checkSubjectById(Map<String,Object> params);
	/**
	 * 大小专题列表
	 * @param params
	 * @return
	 */
	public List<SubjectVo> getBigOrSmallSubjectListIndex(Map<String,Object> params);
	
	/**
	 * 查询总的条数
	 */
	public int getSubjectListCount(Map<String,Object> params);
	
	
	public int getUserSubjectListCount(Map<String,Object> params);
	
	public List<SubjectVo> getUserSubjectListList(Map<String,Object> params);
	
}
