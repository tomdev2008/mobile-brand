package com.xiu.mobile.core.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.SubjectComment;
import com.xiu.mobile.core.model.SubjectItem;


/**
 * 
* @Description: TODO(专题) 
* @author haidong.luo@xiu.com
* @date 2015年10月8日 下午3:01:23 
*
 */
public interface SubjectManagerDao {
	
	public List<Subject> getSubjectList(Map<Object, Object> map);
	
	public Integer getSubjectTotalCount(Map<Object, Object> map);
	
	public int save(Subject Subject);
	
	public int update(Subject Subject);
	
	public int delete(Subject subject);
	/**
	 * 修改是否显示到列表中
	 * @param id
	 * @return
	 */
	public int updateShow(Subject Subject);
	
	public Subject getSubjectById(Long id);
	
	public int saveItem(SubjectItem item);
	
	public Long getSubjectId();
	
	public int deleteItem(Long subjectId);
	
	/**
	 * 检查专题时间是否重复
	 * @param params
	 * @return
	 */
	public int checkTimeByOrderSeq(Map params);
	
	/**
	 * 获取相同排序值的时间安排专题
	 * @return
	 */
	public List<Subject> getTimesByOrderSeq(Map params);
	
	/**
	 * 获取专题评论列表
	 * @param map
	 * @return
	 */
	public List<SubjectComment> getSubjectCommentList(Map<Object, Object> map);
	
	/**
	 * 获取专题评论数量
	 * @param map
	 * @return
	 */
	public Integer getSubjectCommentCount(Map<Object, Object> map);
	
	/**
	 * 删除专题评论
	 * @param commentId
	 * @return
	 */
	public Integer deleteSubjectComment(Long commentId);
	
	/**
	 * 批量删除专题评论
	 * @param params
	 * @return
	 */
	public Integer deleteBatchSubjectComments(Map params);
	
	/**
	 * 
	 * @param commentId
	 * @return
	 */
	public SubjectComment getSubjectCommentById(Long commentId);
	/**
	 * 添加评论
	 */
	public int addSubjectComment(Map<String,Object>params);

	/**
	 * 查询同步到搜索的数据总数
	 * @param params
	 * @return
	 */
	public Integer getSyncToSearchCount(Map<String, Object> params);

	/**
	 * 查询同步到搜索的数据
	 * @param params
	 * @return
	 */
	public List<Subject> getSyncToSearchList(Map params);
	
}
