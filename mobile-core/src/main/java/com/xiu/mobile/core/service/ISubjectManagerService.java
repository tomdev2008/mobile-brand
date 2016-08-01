package com.xiu.mobile.core.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.SubjectComment;
import com.xiu.mobile.core.model.SubjectLabel;


/**
 * 
* @Description: TODO(专题) 
* @author haidong.luo@xiu.com
* @date 2015年10月8日 下午2:49:22 
*
 */
public interface ISubjectManagerService {
	
	public Map<String,Object> getSubjectList(Map<Object, Object> map,Page<?> page);
	
	public Map<String,Object> save(Map<String,Object> params);
	
	public Map<String,Object> update(Map<String,Object> params);
	
	public int delete(Subject subject);
	/**
	 * 是否显示到列表中
	 * @param Subject
	 * @return
	 */
	public int updateShow(Subject Subject);

	public Subject getSubjectById(Long id);
	
	/**
	 * 检查专题时间是否重复
	 * @param params
	 * @return
	 */
	public int checkTimeByOrderSeq(Map<String,Object> params);
	
	/**
	 * 获取相同排序值的时间安排专题
	 * @return
	 */
	public String getTimesByOrderSeq(Map<String,Object> params);
	
	/**
	 * 查询专题评论列表
	 * @param map
	 * @param page
	 * @return
	 */
	public Map<String,Object> getSubjectCommentList(Map<Object, Object> map,Page<?> page);
	
	/**
	 * 删除专题评论
	 * @param commentId
	 * @param userId
	 * @param userName
	 * @param resultType
	 * @return
	 */
	public Integer deleteSubjectComment(Long commentId,Long userId,String userName,String resultType);
	
	/**
	 * 批量删除专题评论
	 * @param params
	 * @return
	 */
	public Integer deleteBatchSubjectComment(Map<String,Object> params);
	
	/**
	 * 获取专题评论详情
	 * @return
	 */
	public SubjectComment getSubjectCommentInfo(Long commentId);
	/**
	 * 获取所有标签，不分页
	 * @return
	 */
	public List<SubjectLabel> getSubjectLabel(int category);
	/**
	 * 导入专题
	 */
	public int importSubject(List<Map<Object, Object>> listMap);
	
	/**
	 * 添加评论
	 */
	public int addSubjectComment(Map<String,Object> params);
	
	/**
	 *获取适合同步到搜索数量总数
	 * @param params
	 * @return
	 */
	public Integer getSyncToSearchCount(Map<String,Object> params);

	/**
	 * 获取适合同步到搜索的数据
	 * @param syncGetParams
	 * @return
	 */
	public List<Subject> getSyncToSearchList(Map syncGetParams);
	
}
