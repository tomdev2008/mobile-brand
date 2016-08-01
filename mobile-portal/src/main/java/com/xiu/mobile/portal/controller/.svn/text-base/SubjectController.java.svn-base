package com.xiu.mobile.portal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.ei.EIShowerManager;
import com.xiu.mobile.portal.service.ISensitiveWordService;
import com.xiu.mobile.portal.service.ISubjectApiService;
import com.xiu.mobile.portal.service.ISubjectCommentService;

/**
* @Description: TODO( 单品推荐接口) 
* @author haidong.luo@xiu.com
* @date 2015年9月29日 上午10:04:52 
*
 */
@Controller
@RequestMapping("/subject")
public class SubjectController extends BaseController {
	private Logger logger = Logger.getLogger(SubjectController.class);

  @Autowired
  private ISubjectApiService subjectApiService;
  @Autowired
  private ISubjectCommentService subjectCommentService;
  @Autowired
  private ISensitiveWordService sensitiveWordService;
  @Autowired
  private EIShowerManager eishowerManager;
  
  
  
  	/**
  	 * 获取用户收藏的专题列表接口
  	 * @param request
  	 * @param jsoncallback
  	 * @param subjectId
  	 * @return
  	 */
	@ResponseBody
	@RequestMapping(value = "/getUserCollectSubjectList", produces = "text/html;charset=UTF-8")
	public String getUserCollectSubjectList(HttpServletRequest request,String jsoncallback,
			String page,String pageSize){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		//获取专题数据
    	if(null == page || "".equals(page)){
    		page="1";
    	}
    	if(null == pageSize || "".equals(pageSize)){
    		pageSize="5";
    	}

    	String xiuUserId=SessionUtil.getUserId(request);
		if(StringUtils.isBlank(xiuUserId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", 10080);
			resultMap.put("errorMsg", "请登录");
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
    	Map<String,Object> subjectParams=new HashMap<String,Object>();
    	try{
    		subjectParams.put(XiuConstant.USER_ID, xiuUserId);
    		subjectParams.put(XiuConstant.PAGE_NUM, page);
    		subjectParams.put(XiuConstant.PAGE_SIZE, pageSize);
    		Map<String,Object> result=subjectApiService.getUserCollectSubjectList(subjectParams);
        	Boolean isSuccess=(Boolean)result.get("status");
    		if(isSuccess){
    			logger.info("查询专题列表成功："+result);
    			resultMap.put("result", true);
    			resultMap.put(XiuConstant.TOTAL_COUNT, result.get("totalCount"));
    			resultMap.put(XiuConstant.TOTAL_PAGE, result.get("totalPage"));
    			resultMap.put("subjectList", result.get("listSubject"));
    			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
    		}else{
    			resultMap.put("result", false);
    			resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
    		}
    	}catch(Exception e){
    		resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询大小专题列表信息异常：");
			logger.error(e);
    	}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	
  
	@ResponseBody
	@RequestMapping(value = "/getSubjectInfo", produces = "text/html;charset=UTF-8")
	public String getSubjecInfo(HttpServletRequest request,String jsoncallback, 
			String subjectId){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		if(ObjectUtil.getLong(subjectId, null)==null){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SubjectNotExists.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SubjectNotExists.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try {
			params.put("subjectId", subjectId);
			String xiuUserId=SessionUtil.getUserId(request);
			if(StringUtils.isNotBlank(xiuUserId)){
				params.put("xiuUserId", xiuUserId);
			}
			
			Map<String,Object> returnMap=subjectApiService.getSubjectInfo(params);
			Boolean isSuccess=(Boolean)returnMap.get("status");
			if(isSuccess){
				resultMap.put("subjectVO", returnMap.get("model"));
				resultMap.put("subjectCommentData", returnMap.get("subjectCommentData"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",returnMap.get("errorCode"));
				resultMap.put("errorMsg",returnMap.get("errorMsg"));
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("专题详情查询发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	

	/**
	 * 添加评论
	 * 必填参数：subjectId（专题id）,content(评论内容)
	 * 非必填参数  toUserId（被评论用户id）
	 * @param request
	 * @param jsoncallback
	 * @param subjectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSubjectComment", produces = "text/html;charset=UTF-8")
	public String addSubjectComment(HttpServletRequest request,String jsoncallback){
			Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
			String subjectIdStr=request.getParameter("subjectId");//获取专题ID
			String userIdStr=SessionUtil.getUserId(request);//用户ID
			Long userId=ObjectUtil.getLong(userIdStr, null);
			String toUserIdStr=request.getParameter(XiuConstant.COMMENTED_API_TO_USER_INFO);//被评论的用户id
			String comment=request.getParameter(XiuConstant.COMMENT_INFO);//获取评论内容
			if(StringUtils.isBlank(subjectIdStr)||userId==null||StringUtils.isBlank(comment)){
				resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			try {
				comment=URLDecoder.decode(comment, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			if(!comment.matches(".{1,1000}")){
				resultMap=getResultMap(resultMap,false,ErrorCode.SubjectCommentLenghFormat);//长度限制
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}

			//检查是否有权限评论
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("userId", userId);
			Boolean isComment=eishowerManager.checkShowerCommentAuthority(params);
			if(!isComment){
				resultMap=getResultMap(resultMap,false,ErrorCode.SubjectCommentNotAuthority);//无权限评论
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			
			//检测评论内容是否存在敏感词 add by coco.long
			boolean sensitiveFlag = sensitiveWordService.isSensitiveExists(comment);
			if(sensitiveFlag) {
				resultMap=getResultMap(resultMap,false,ErrorCode.SubjectCommentSensitiveWordExists);//存在敏感内容
				return JsonUtils.bean2jsonP(jsoncallback, resultMap);
			}
			
			
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put(XiuConstant.SUBJECT_ID,  Long.parseLong(subjectIdStr));
			paraMap.put(XiuConstant.USER_ID, userId);
			paraMap.put(XiuConstant.USER_NAME, 	SessionUtil.getUserName(request));
			
							
			if(StringUtils.isNotBlank(toUserIdStr)){
				paraMap.put(XiuConstant.COMMENTED_USER_INFO,  Long.parseLong(toUserIdStr));
			}
			try {
				paraMap.put(XiuConstant.COMMENT_INFO, comment);	
				paraMap  = subjectCommentService.addSubjectComment(paraMap);
				ErrorCode resultCode =(ErrorCode)paraMap.get(XiuConstant.STATUS_INFO);
				Boolean result_tatus =(Boolean)paraMap.get(XiuConstant.RESULT_STATUS);
				resultMap=getResultMap(resultMap,result_tatus,resultCode);
			} catch(Exception e) {
				resultMap=getErroResultMap(resultMap);
				logger.error("用户评论发生异常:" + e.getMessage());
			}
			
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
	
	/**
	 * 获取专题评论列表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getSubjectCommentList",produces="text/html;charset=UTF-8")
	public String getSubjectCommentList(HttpServletRequest request,String jsoncallback){
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		String pageNumStr=request.getParameter("pageNum");
		Integer pageNum=ObjectUtil.getInteger(pageNumStr, 1);
		String subjectIdStr=request.getParameter("subjectId");//获取专题ID
		if(StringUtils.isBlank(subjectIdStr)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		//获取评论
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(XiuConstant.PAGE_SIZE, 10);
		params.put(XiuConstant.PAGE_NUM, pageNum);
		params.put(XiuConstant.SUBJECT_ID, subjectIdStr);
		Map<String,Object> result=subjectCommentService.getSubjectCommentList(params);

		resultMap.put(XiuConstant.TOTAL_COUNT, result.get(XiuConstant.TOTAL_COUNT));//总数
		resultMap.put(XiuConstant.TOTAL_PAGE, result.get(XiuConstant.TOTAL_PAGE));//总页数
		resultMap.put("subjectCommentList", result.get("subjectCommentList"));//数据内容
		ErrorCode resultCode =(ErrorCode)result.get(XiuConstant.STATUS_INFO);
		Boolean result_tatus =(Boolean)result.get(XiuConstant.RESULT_STATUS);
		resultMap=getResultMap(resultMap,result_tatus,resultCode);
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);

	}
	
	/**
	 * 删除评论
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="/deleteSubjectComment",produces="text/html;charset=UTF-8")
    public String deleteSubjectComment(HttpServletRequest request,String jsoncallback){
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	
		String subjectIdStr=request.getParameter("subjectId");//获取专题ID
		String commentIdStr=request.getParameter("subjectCommentId");//获取专题评论ID
		String userIdStr=SessionUtil.getUserId(request);//用户ID

		if(StringUtils.isBlank(subjectIdStr)||StringUtils.isBlank(commentIdStr)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		//删除评论
		Map<String,Object> params=new HashMap<String,Object>();
		params.put(XiuConstant.SUBJECT_ID, subjectIdStr);
		params.put(XiuConstant.COMMENT_API_ID, commentIdStr);
		params.put(XiuConstant.USER_ID, ObjectUtil.getLong(userIdStr));
		Map<String,Object> result=subjectCommentService.deleteSubjectComment(params);
		ErrorCode resultCode =(ErrorCode)result.get(XiuConstant.STATUS_INFO);
		Boolean result_tatus =(Boolean)result.get(XiuConstant.RESULT_STATUS);
		resultMap=getResultMap(resultMap,result_tatus,resultCode);
    	return JsonUtils.bean2jsonP(jsoncallback, resultMap);
    }
    
	/**
	 * 获取专题列表
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="/getSubjectList",produces="text/html;charset=UTF-8")
	public String getSubjectList(HttpServletRequest request,String jsoncallback,
			String page,String pageSize,String tagId){
    	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    	//displayStyle 1为小(横)，2为大（竖）
    	String diaplayStytle=request.getParameter("diaplayStytle");//获取展示大小专题的标志
    	if(diaplayStytle==null || "".equals(diaplayStytle)){
    		diaplayStytle="2";
    	}
    	logger.info("根据 diaplayStytle="+diaplayStytle+"查询大小专题列表");
    	//获取专题数据
    	if(null == page || "".equals(page)){
    		page="1";
    	}
    	if(null == pageSize || "".equals(pageSize)){
    		pageSize="5";
    	}
    	Map<String,Object> subjectParams=new HashMap<String,Object>();
    	subjectParams.put("diaplayStytle", diaplayStytle);
    	subjectParams.put("tagId", tagId);
    	try{
    		subjectParams.put(XiuConstant.PAGE_NUM, page);
    		subjectParams.put(XiuConstant.PAGE_SIZE, pageSize);
    		Map<String,Object> result=subjectApiService.getBigOrSmallSubjectListIndex(subjectParams);
        	Boolean isSuccess=(Boolean)result.get("status");
    		if(isSuccess){
    			logger.info("查询专题列表成功："+result);
    			resultMap.put("result", true);
    			resultMap.put(XiuConstant.TOTAL_COUNT, result.get("totalCount"));
    			resultMap.put(XiuConstant.TOTAL_PAGE, result.get("totalPage"));
    			if(tagId!=null){
    				resultMap.put("tagId", tagId);
    			}
    			resultMap.put("subjectList", result.get("listSubjiect"));
    			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
    		}else{
    			resultMap.put("result", false);
    			resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
    		}
    	}catch(Exception e){
    		resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询大小专题列表信息异常：diaplayStytle="+diaplayStytle);
			logger.error(e);
    	}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
}
