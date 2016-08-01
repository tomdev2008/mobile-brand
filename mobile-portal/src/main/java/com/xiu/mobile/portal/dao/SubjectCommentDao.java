package com.xiu.mobile.portal.dao;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.SubjectCommentVO;

/**
 * 
* @Description: TODO(专题评论) 
* @author haidong.luo@xiu.com
* @date 2015年10月7日 上午11:15:41 
*
 */
public interface SubjectCommentDao {
	
	/**
	 * 添加专题评论记录
	 * @param params
	 */
	public Integer addSubjectComment(Map<String, Object> params);
	/**
	 * 删除专题评论记录
	 * @param params
	 */
	public int delSubjectComment(Map<String, Object> params);
	
	/**
	 * 通过ID和userId获取用户的评论数目
	 * @param params
	 * @return
	 */
	public Integer getSubjectCommentCountById(Map<String, Object> params);
	/**
	 * 获取用户评论的专题ID，通过评论ID
	 * @param params
	 * @return
	 */
	public Long getCommentSubjectIdByIdAndUserId(Map<String, Object> params);
	
	/**
	 * 根据评论ID查询发表评论的用户ID
	 * @param params
	 * @return
	 */
	public Long getCommentUserIdById(Map<String, Object> params);
	
	/**
	 * 获取用户在该专题下的评论数
	 * @param params
	 * @return
	 */
	public Integer getCountBySubjectIDAndUserId(Map<String, Object> params);
	
	/**
	 * 查询专题评论列表
	 * @param map
	 * @return
	 */
	public List<SubjectCommentVO> getSubjectCommentList(Map map);
	
	/**
	 * 查询所有专题评论列表
	 * @param map
	 * @return
	 */
	public List<SubjectCommentVO> getSubjectsCommentList(Map map);
	
	/**
	 * 查询专题评论数量
	 * @param map
	 * @return
	 */
	public int getSubjectCommentCount(Map map);
	/**
	 * 查询所有专题评论数量
	 * @param map
	 * @return
	 */
	public int getSubjectsCommentCount(Map map);
	
	/**
	 * 查询评论信息
	 * @param map
	 * @return
	 */
	public SubjectCommentVO getCommentInfo(Map map);
	
	/**
	 * 获取评论id
	 * @return
	 */
	public Long getSubjectCommentSqeuence();
	
	/**
	 * 删除用户的所有专题评论记录
	 * @param params
	 */
	public int delSubjectCommentByUserId(Map<String, Object> params);
	
	/**
	 * 批量删除用户的专题评论记录
	 * @param params
	 */
	public int delBatchSubjectComment(Map<String, Object> params);
	
}
