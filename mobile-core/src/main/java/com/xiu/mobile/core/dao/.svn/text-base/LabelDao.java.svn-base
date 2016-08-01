package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.LabelRelation;
import com.xiu.mobile.core.model.ServiceSearchDateBo;
import com.xiu.mobile.core.model.SubjectLabel;

/**
 * 标签
 * @author Administrator
 *
 */
public interface LabelDao {
	/**
	 * 查询所有标签信息，不分页
	 */
	public List<Label> getSubjectLabel(int category);
	/**
	 * 
	 */
	public List<Label> getSubjectLabelSecond(int category);
	/**
	 * 分页查询标签列表
	 */
	public List<Label> getLabelList(Map<String,Object> params);
	/**
	 * 标签总数
	 * @param params
	 * @return
	 */
	public int getLabelCount(Map<String,Object> params);
	/**
	 * 添加标签
	 */
	public int addSubjectLabel(Label label);
	
	/**
	 * 根据ID查询标签信息
	 */
	public Label getLabelInfo(long labelId);
	/**
	 * 修改标签信息
	 */
	public int updaetLabel(Label label);
	/**
	 *删除标签
	 */
	public int deleteLabel(Map params);
	
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
	public Label findLabelIdByTitle(Map<String,Object> params);
	
	
	/**
	 * 批量绑定业务标签
	 */
	public int addLabelObjectList(List<LabelCentre> labelCentres);
	
	/**
	 * 添加标签绑定业务数据到搜索临时表
	 * @param labelServiceDateBo
	 * @return
	 */
	public int addLabelServiceDate(ServiceSearchDateBo labelServiceDateBo);
	
	/**
	 * 查询新添加的前15个标签
	 * @return
	 */
	public List<Label> findNewAddLabel();
	
	/**
	 * 查询历史最近使用的前15个标签
	 * @return
	 */
	public List<Label> findHistoryLabel();
	
	/**
	 * 根据标签名称获取标签ID
	 * @param labelName
	 * @return
	 */
	public Long getLabelIdByName(String labelName);
	
	/**
	 * 根据业务id和类型查询标签
	 * @param params
	 * @return
	 */
	public List<Label> findLabelListByObjectIdAndType(Map params);
	/**
	 * 查询绑定的业务数据搜索临时表
	 * @param getParams
	 * @return
	 */
	public ServiceSearchDateBo getLabelServiceDate(Map getParams);
	
	/**
	 * 更新标签绑定业务数据到搜索临时表
	 * @param labelServiceDateBo
	 */
	public Integer updateLabelServiceDate(ServiceSearchDateBo labelServiceDateBo);
	/**
	 * 删除关联关系
	 * @param delParams
	 */
	public void deleteBatchLabelObject(Map delParams);
	
	/**
	 * 根据对象ID和分类批量查询标签
	 * @param params
	 * @return
	 */
	public List<LabelCentre> findLabelsByObjectIds(Map params);
	
	/**
	 * 根据标签名称查询标签ID
	 * @param typeNamesMap
	 * @return
	 */
	public List<Long> getLabelIdByNames(Map typeNamesMap);
	
	/**
	 * 获取标签业务数据列表
	 * @param params
	 * @return
	 */
	public List<LabelCentre> getLabelServiceDateList(Map params);
	
	/**
	 * 获取标签业务数据总数
	 * @param params
	 * @return
	 */
	public int getLabelServiceDateCount(Map params);
	
	/**
	 * 跟新标签业务数据排序
	 * @param params
	 * @return
	 */
	public Integer updateLabelServiceOrder(Map params);
	
	/**
	 * 获取标签关联标签数据总数
	 * @param params
	 * @return
	 */
	public int getLabelRelationDateCount(Map params);
	
	/**
	 * 获取标签关联标签数据
	 * @param params
	 * @return
	 */
	public List<LabelRelation> getLabelRelationDateList(Map params);
	
	/**
	 * 修改标签关联标签排序
	 * @param params
	 * @return
	 */
	public Integer updateLabelRelationOrder(Map params);
	
	/**
	 * 删除未改动的关联标签数据
	 */
	public void deleteLabelRelationDate();
	
	/**
	 * 分类型添加标签管理数据
	 * @param params
	 */
	public void addLabelRelationDateByType(Map params);
	
	
	/**
	 * 按分类查询关联标签的个数
	 * @param params
	 * @return
	 */
	public Integer findLabelRelationDateContByType(Map params);
	
	/**
	 * 删除标签对应的业务关联数据
	 * @param labelId
	 */
	public void deleteLabelObjectByLabelId(Long labelId);
	/**
	 * 删除重复的数据
	 */
	public void deleteBatchRepeatedLabelObject(Map params);

}
