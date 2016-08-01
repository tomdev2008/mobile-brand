package com.xiu.mobile.core.service;

import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.SubjectLabel;

/**
 * 专题标签
 * @author Administrator
 *
 */
public interface ISubjectLabelService {
	/**
	 * 分页查询标签列表
	 */
	public Map<String,Object> getLabelList(Map<String,Object> params,Page<SubjectLabel> page);
	/**
	 * 添加标签
	 */
	public Map<String,Object> addSubjectLabel(SubjectLabel label);
	/**
	 * 根据ID查询标签信息
	 */
	public SubjectLabel getLabelInfo(long labelId);
	/**
	 * 修改标签
	 */
	public Map<String,Object> updateLabel(SubjectLabel label);
	/**
	 * 删除标签
	 */
	public int deleteLabel(long labelId);
	/**
	 * 根据标签名称查询标签信息
	 */
	public long findLabelIdByTitle(Map<String,Object> params);
	

}
