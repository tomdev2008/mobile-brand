package com.xiu.mobile.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.ImageUtil;
import com.xiu.mobile.portal.dao.SubjectCommentDao;
import com.xiu.mobile.portal.dao.SubjectDao;
import com.xiu.mobile.portal.model.SubjectCommentVO;
import com.xiu.mobile.portal.service.ISubjectCommentService;

@Service("subjectCommentService")
public class SubjectCommentServiceImpl implements ISubjectCommentService{

	
	@Autowired
	private SubjectCommentDao subjectCommentDao;
	
	@Autowired
	private SubjectDao subjectDao;
	

	
	/**
	 * 查询专题的评论列表
	 */
	public Map<String,Object> getSubjectCommentList(Map<String,Object> map) {
		Map<String,Object> results=new HashMap<String,Object>();
		Boolean resultStatus=false;

		//1.获取总数
		Integer count= subjectCommentDao.getSubjectCommentCount(map);
		results.put(XiuConstant.TOTAL_COUNT, count);
		Page page = new Page();
		Integer pageSize=ObjectUtil.getInteger(map.get("pageSize"),null);
		if(pageSize!=null){
			page.setPageSize(pageSize);//分页大小
		}
		page.setTotalCount(count);
		results.put(XiuConstant.TOTAL_PAGE, page.getTotalPages());
		List<SubjectCommentVO> commentList = null;
		//获取评论
		if(count>0){
			Integer pageNum = ObjectUtil.getInteger(map.get("pageNum"),1);//当前查询页码
			page.setPageNo(pageNum);
			map.put("lineMin", page.getFirstRecord());
			map.put("lineMax", page.getEndRecord());
			commentList = subjectCommentDao.getSubjectCommentList(map);
			if(results != null && commentList.size() > 0) {
				for(SubjectCommentVO commentVO : commentList) {
					// 处理头像地址
					String headPortrait = commentVO.getHeadPortrait();
					commentVO.setHeadPortrait(ImageUtil.getHeadPortrait(headPortrait));
				}
			}
		}
		resultStatus=true;
		results.put("subjectCommentList", commentList);
		results.put(XiuConstant.RESULT_STATUS, resultStatus);
		results.put(XiuConstant.STATUS_INFO, ErrorCode.Success);
		return results;
	}


	/**
	 * 增加专题评论
	 */
	public Map<String,Object> addSubjectComment(Map<String,Object> params) {
			Boolean resultStatus=false;
			Long subjectId=(Long)params.get(XiuConstant.SUBJECT_ID);
			Long userId=(Long)params.get(XiuConstant.USER_ID);
			//查询专题是否存在
			Map<String,Object> checkSubjectMap=new HashMap<String,Object>();
			checkSubjectMap.put("subjectId", subjectId);
			Integer checkSubject=subjectDao.checkSubjectById(checkSubjectMap);
			if(checkSubject==null||checkSubject==0){
				//专题不存在
				params.put(XiuConstant.RESULT_STATUS, resultStatus);
				params.put(XiuConstant.STATUS_INFO, ErrorCode.SubjectNotExists);
				return params;
			}
			Long  commentId=subjectCommentDao.getSubjectCommentSqeuence();//评论id
			
			//增加评论操作
			params.put("deleteFlag", 0);
			params.put(XiuConstant.ID, commentId);
			subjectCommentDao.addSubjectComment(params);
			
			//成功
			resultStatus=true;
			params.put(XiuConstant.RESULT_STATUS, resultStatus);
			params.put(XiuConstant.STATUS_INFO, ErrorCode.Success);
			return params;
		}


	public Map<String,Object> deleteSubjectComment(Map<String,Object> params) {
        Boolean resultStatus=false;
		
        params.put(XiuConstant.ID, params.get(XiuConstant.COMMENT_API_ID));
		Long commentUserId = subjectCommentDao.getCommentUserIdById(params); //发表评论的用户ID
		if(commentUserId==null){
			//删除的评论不存在
			params.put(XiuConstant.RESULT_STATUS, resultStatus);
			params.put(XiuConstant.STATUS_INFO, ErrorCode.SubjectCommentNotExists);
			return params;
		}
		
		int num=subjectCommentDao.delSubjectComment(params);
		if(num>0){
			resultStatus=true;
		}else{
			//删除的评论不存在
			params.put(XiuConstant.RESULT_STATUS, resultStatus);
			params.put(XiuConstant.STATUS_INFO, ErrorCode.SubjectCommentNotExists);
			return params;
		}
		params.put(XiuConstant.RESULT_STATUS, resultStatus);
		params.put(XiuConstant.STATUS_INFO, ErrorCode.Success);
		return params;
	}


	@Override
	public Map getSubjectsCommentList(Map map) {
		Map results=new HashMap();
		Boolean resultStatus=false;

		//1.获取总数
		Integer count= subjectCommentDao.getSubjectsCommentCount(map);
		results.put(XiuConstant.TOTAL_COUNT, count);
		Page page = new Page();
		Integer pageSize=ObjectUtil.getInteger(map.get("pageSize"),null);
		if(pageSize!=null){
			page.setPageSize(pageSize);//分页大小
		}
		page.setTotalCount(count);
		results.put(XiuConstant.TOTAL_PAGE, page.getTotalPages());
		List<SubjectCommentVO> commentList = null;
		//获取评论
		if(count>0){
			Integer pageNum = ObjectUtil.getInteger(map.get("pageNum"),1);//当前查询页码
			page.setPageNo(pageNum);
			map.put("lineMin", page.getFirstRecord());
			map.put("lineMax", page.getEndRecord());
			commentList = subjectCommentDao.getSubjectsCommentList(map);
			if(results != null && commentList.size() > 0) {
				for(SubjectCommentVO commentVO : commentList) {
					// 处理头像地址
					String headPortrait = commentVO.getHeadPortrait();
					commentVO.setHeadPortrait(ImageUtil.getHeadPortrait(headPortrait));
				}
			}
		}
		resultStatus=true;
		results.put("subjectCommentList", commentList);
		results.put(XiuConstant.RESULT_STATUS, resultStatus);
		results.put(XiuConstant.STATUS_INFO, ErrorCode.Success);
		return results;
	}


	/**
	 * 删除用户所有专题评论
	 * @param params
	 * @return
	 */
	public Map deleteSubjectCommentByUserId(Map params) {
        Boolean resultStatus=false;
		
		int num=subjectCommentDao.delSubjectCommentByUserId(params);
		if(num>0){
			resultStatus=true;
		}
		params.put(XiuConstant.RESULT_STATUS, resultStatus);
		params.put(XiuConstant.STATUS_INFO, ErrorCode.Success);
		return params;
	}


	public Map deleteBatchSubjectComment(Map params) {
        Boolean resultStatus=false;
		
		int num=subjectCommentDao.delBatchSubjectComment(params);
		if(num>0){
			resultStatus=true;
		}
		params.put(XiuConstant.RESULT_STATUS, resultStatus);
		params.put(XiuConstant.STATUS_INFO, ErrorCode.Success);
		return params;
	}

	
}
