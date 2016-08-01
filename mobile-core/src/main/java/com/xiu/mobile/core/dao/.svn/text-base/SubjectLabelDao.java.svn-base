package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.SubjectLabel;

/**
 * 专题标签
 * @author Administrator
 *
 */
public interface SubjectLabelDao {
	/**
	 * 查询所有标签信息，不分页
	 */
	public List<SubjectLabel> getSubjectLabel(int category);
	/**
	 * 
	 */
	public List<SubjectLabel> getSubjectLabelSecond(int category);
	/**
	 * 分页查询标签列表
	 */
	public List<SubjectLabel> getLabelList(Map<String,Object> params);
	/**
	 * 标签总数
	 * @param params
	 * @return
	 */
	public int getLabelCount(Map<String,Object> params);
	/**
	 * 添加标签
	 */
	public int addSubjectLabel(SubjectLabel label);
	
	/**
	 * 根据ID查询标签信息
	 */
	public SubjectLabel getLabelInfo(long labelId);
	/**
	 * 修改标签信息
	 */
	public int updaetLabel(SubjectLabel label);
	/**
	 *删除标签
	 */
	public int deleteLabel(long labelId);
	
	/**
	 * 批量查询专题/单品对应的标签
	 */
	public List<LabelCentre> findLabelCentreList(Map<String,Object> params);
	/**
	 * 根据专题/单品ID查询标签信息
	 */
	public List<LabelCentre> findLabelBySubjectId(Map<String,Object> params);
	/**
	 * 删除标签（中间表）
	 */
	public int deleteLabelCentreList(Map<String,Object> params);
	/**
	 * 批量插入专题/单品标签(中间表)
	 */
	public int addLabelCentreList(Map<String,Object> list);
	/**
	 * 根据标签名称查询标签信息
	 */
	public SubjectLabel findLabelIdByTitle(Map<String,Object> params);

}
