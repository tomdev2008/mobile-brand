package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Label;

/**
 * 专题标签
 * @author Administrator
 *
 */
public interface ILabelService {
	/**
	 * 分页查询标签列表
	 */
	public Map<String,Object> getLabelList(Map<String,Object> params,Page<Label> page);
	/**
	 * 添加标签
	 */
	public Map<String,Object> addSubjectLabel(Label label);
	/**
	 * 根据ID查询标签信息
	 */
	public Label getLabelInfo(long labelId);
	/**
	 * 修改标签
	 */
	public Map<String,Object> updateLabel(Label label);
	/**
	 * 删除标签
	 */
	public int deleteLabel(Map params);
	/**
	 * 根据标签名称查询标签信息
	 */
	public long findLabelIdByTitle(Map<String,Object> params);
	
	/**
	 * 绑定标签
	 * 其中params中含有以下参数
	 *  type（标签类型(3:卖场 4:专题  5:H5)） 必填 String 类型
	 *  objectId 业务类型的ID
	 *  labelId 标签id  必填  String 类型
	 *  labelOrder 标签维度排序 非必填 String 类型
	 *  objectOrder 对象维度排序 非必填 String 类型
	 * @return 1成功 0失败 2参数不完整 3业务数据不存在
	 */
	public Integer bindLableToSevice(Integer type,Long objectId,List<LabelCentre> lables);
	
	/**
	 * 根据业务id和类型查询标签
	 */
	public List<Label> findLabelListByObjectIdAndType(Long objectId,Integer type);

	/**
	 * 查询常用标签
	 */
	public Map<String,Object> findCommonLabelList();
	/**
	 * 根据标签名称获取标签ID
	 * @param labelName
	 * @return
	 */
	public Long getLabelIdByName(String labelName);
	
	/**
	 * 按对象id集合和分类批量查询标签
	 */
	public Map<Long,List<LabelCentre>> findLabelsByObjectIds(List<Long> objectIds,Integer type);
	
	/**
	 * 根据名称集合获取ID
	 * @param typeNamesMap
	 * @return
	 */
	public List<Long> getLabelIdByNames(Map typeNamesMap);
	
	/**
	 * 获取标签业务数据列表
	 * @param ramp
	 * @param page
	 * @return
	 */
	public  Map<String, Object>  getLabelServiceDateList(Map ramp, Page<Label> page);
	
	/**
	 * 获取标签关联标签数据列表
	 * @param ramp
	 * @param page
	 * @return
	 */
	public  Map<String, Object>  getLabelRelationDateList(Map ramp, Page<Label> page);
	
	/**
	 * 更新标签业务数据排序
	 * @param params
	 * @return
	 */
	public Boolean updateLabelServiceOrder(Map params);
	
	/**
	 * 定时刷标签关联数据
	 * @param params
	 */
	public void syncLabelRelationDate(Map params);
	
	/**
	 * 更新标签关联标签数据
	 * @param params
	 * @return
	 */
	public Boolean updateLabelRelationOrder(Map params);
	
}
