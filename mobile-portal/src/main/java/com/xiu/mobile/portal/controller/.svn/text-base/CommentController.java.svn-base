package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.ei.EIShowerManager;
import com.xiu.mobile.portal.service.ISubjectCommentService;

/**
 * 
* @Description: TODO(评论管理) 
* @author haidong.luo@xiu.com
* @date 2015年11月9日 上午11:33:44 
*
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {
	private Logger logger = Logger.getLogger(CommentController.class);

  @Autowired
  private ISubjectCommentService subjectCommentService;
  @Autowired
  private EIShowerManager eishowerManager;
  
	/**
	 * 获取专题评论列表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getCommentList",produces="text/html;charset=UTF-8")
	public String getSubjectCommentList(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String typeStr=request.getParameter("type");//评论类型 1专题
		String pageNumStr=request.getParameter("pageNum");
		Integer pageNum=ObjectUtil.getInteger(pageNumStr, 1);
		//获取评论
		Map params=new HashMap();
		params.put(XiuConstant.PAGE_SIZE, XiuConstant.PAGE_SIZE_20);
		params.put(XiuConstant.PAGE_NUM, pageNum);
		Map result=subjectCommentService.getSubjectsCommentList(params);

		resultMap.put(XiuConstant.TOTAL_COUNT, result.get(XiuConstant.TOTAL_COUNT));//总数
		resultMap.put(XiuConstant.TOTAL_PAGE, result.get(XiuConstant.TOTAL_PAGE));//总页数
		resultMap.put("commentList", result.get("subjectCommentList"));//数据内容
		ErrorCode resultCode =(ErrorCode)result.get(XiuConstant.STATUS_INFO);
		Boolean result_tatus =(Boolean)result.get(XiuConstant.RESULT_STATUS);
		resultMap=getResultMap(resultMap,result_tatus,resultCode);
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);

	}
	/**
	 * 批量删除评论
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteBatchComment",produces="text/html;charset=UTF-8")
	public String deleteBatchComment(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userIdStr=SessionUtil.getUserId(request);//用户ID
		String typeStr=request.getParameter("type");//评论类型 1专题
		String ids=request.getParameter("ids");//被删除评论ids

		if(StringUtil.isBlank(userIdStr)||StringUtil.isBlank(typeStr)||StringUtil.isBlank(ids)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		Boolean result_tatus =false;
		ErrorCode resultCode =null;
		String[] strIds = ids.split(",");
		List<Long> idsL = new ArrayList<Long>();
		if(strIds != null){
			for(String s : strIds){
				if(!StringUtil.isEmpty(s))
					idsL.add(Long.parseLong(s));
			}
		}
		//删除评论
		Map params=new HashMap();
		if(typeStr.equals("1")){
			params.put("ids", strIds);
			Map result=subjectCommentService.deleteBatchSubjectComment(params);
			resultCode =(ErrorCode)result.get(XiuConstant.STATUS_INFO);
			result_tatus =(Boolean)result.get(XiuConstant.RESULT_STATUS);
		}
		resultMap=getResultMap(resultMap,result_tatus,resultCode);
    	return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	/**
	 * 按类删除所有评论列表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAllComment",produces="text/html;charset=UTF-8")
	public String deleteAllComment(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userIdStr=SessionUtil.getUserId(request);//用户ID
		String typeStr=request.getParameter("type");//评论类型 1专题
		String beDelUserIdStr=request.getParameter("userId");//被删除评论的用户id

		if(StringUtil.isBlank(userIdStr)||StringUtil.isBlank(typeStr)||StringUtil.isBlank(beDelUserIdStr)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		Boolean result_tatus =false;
		ErrorCode resultCode =null;
		//删除评论
		Map params=new HashMap();
		if(typeStr.equals("1")){
			params.put(XiuConstant.USER_ID, ObjectUtil.getLong(beDelUserIdStr));
			Map result=subjectCommentService.deleteSubjectCommentByUserId(params);
			resultCode =(ErrorCode)result.get(XiuConstant.STATUS_INFO);
			result_tatus =(Boolean)result.get(XiuConstant.RESULT_STATUS);
		}
		resultMap=getResultMap(resultMap,result_tatus,resultCode);
    	return JsonUtils.bean2jsonP(jsoncallback, resultMap);

	}
	
	
	/**
	 * 禁止评论
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/forbitComment",produces="text/html;charset=UTF-8")
	public String forbitComment(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userIdStr=request.getParameter("userId");
		String typeStr=request.getParameter("type");//评论类型 1专题
		
		if(userIdStr==null){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
	    Boolean isSucess=false;
		try {
			if(typeStr.equals("1")){
				Map paraMap = new HashMap();
				paraMap.put("userId", ObjectUtil.getLong(userIdStr, null));
				Map result=eishowerManager.updateShowerForbidComment(paraMap);
				Integer updateCode=(Integer)result.get(XiuConstant.RESULT_STATUS);
				if(updateCode==0){
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", result.get(XiuConstant.RESULT_STATUS));
					resultMap.put("errorMsg", result.get(XiuConstant.STATUS_INFO));
				}
				
			}
		} catch(Exception e) {
			resultMap=getErroResultMap(resultMap);
			logger.error("禁止评论发生异常:" + e.getMessage());
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		
	}
	
	
	/**
	 * 删除所有且禁止评论
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteAllAndForbitComment",produces="text/html;charset=UTF-8")
	public String deleteAndForbitComment(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userIdStr=SessionUtil.getUserId(request);//用户ID
		String typeStr=request.getParameter("type");//评论类型 1专题
		String beDelUserIdStr=request.getParameter("userId");//被删除评论的用户id

		if(StringUtil.isBlank(userIdStr)||StringUtil.isBlank(typeStr)||StringUtil.isBlank(beDelUserIdStr)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		Boolean result_tatus =false;
		ErrorCode resultCode =null;
		//删除评论
		Map params=new HashMap();
		if(typeStr.equals("1")){
			//1。删除评论
			params.put(XiuConstant.USER_ID, ObjectUtil.getLong(beDelUserIdStr));
			Map result=subjectCommentService.deleteSubjectCommentByUserId(params);
			//2.禁止评论
			Map paraMap = new HashMap();
			paraMap.put("userId", ObjectUtil.getLong(beDelUserIdStr, null));
			 result=eishowerManager.updateShowerForbidComment(paraMap);
			
			 Integer updateCode=(Integer)result.get(XiuConstant.RESULT_STATUS);
				if(updateCode==0){
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", result.get(XiuConstant.RESULT_STATUS));
					resultMap.put("errorMsg", result.get(XiuConstant.STATUS_INFO));
				}
		}
//		resultMap=getResultMap(resultMap,result_tatus,resultCode);
    	return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		
	}
	
    
	
	
}
