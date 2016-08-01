package com.xiu.mobile.portal.ei.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.ei.EIShowerManager;
import com.xiu.show.remote.service.ShowerManagerApiServive;

/** 
 * 
* @Description: TODO(秀客管理员权限) 
* @author haidong.luo@xiu.com
* @date 2015年11月18日 下午4:19:13 
*
 */
@Service("eishowerManager")
public class EIShowerManagerImpl implements EIShowerManager {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(EIShowerManagerImpl.class);
	
	@Autowired
	private ShowerManagerApiServive showerManagerApiFacade;

	@Override
	public Map<String, Object> updateShowerForbidComment(Map params) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		 Long userId=(Long)params.get("userId");
		 Result result= showerManagerApiFacade.updateShowerForbidComment(userId);
		 if(result!=null){
			  if(result.isSuccess()){
				  resultMap.put(XiuConstant.RESULT_STATUS, ErrorCode.Success.getErrorCode());
				  resultMap.put(XiuConstant.STATUS_INFO, ErrorCode.Success.getErrorMsg());
			  }else{
				  resultMap.put(XiuConstant.RESULT_STATUS, ErrorCode.SystemError.getErrorCode());
				  resultMap.put(XiuConstant.STATUS_INFO,  result.getError());
			  }
		 }else{
			 resultMap.put(XiuConstant.RESULT_STATUS, ErrorCode.SystemError.getErrorCode());
			 resultMap.put(XiuConstant.STATUS_INFO,  ErrorCode.SystemError.getErrorMsg());
		 }
		 return resultMap;
	}

	public Boolean checkShowerCommentAuthority(Map params) {Map<String, Object> resultMap=new HashMap<String, Object>();
		 Long userId=(Long)params.get("userId");
		 Boolean isComment=true;
		 Result result= showerManagerApiFacade.checkShowerCommentAuthority(userId);
		 if(result!=null){
			  if(result.isSuccess()){
				  Integer status=(Integer)result.getModels().get("status");
				  if(status!=1){
					  isComment=false;
				  }
				  resultMap.put(XiuConstant.RESULT_STATUS, ErrorCode.Success.getErrorCode());
				  resultMap.put(XiuConstant.STATUS_INFO, ErrorCode.Success.getErrorMsg());
			  }else{
				  isComment=false;
				  resultMap.put(XiuConstant.RESULT_STATUS, ErrorCode.SystemError.getErrorCode());
				  resultMap.put(XiuConstant.STATUS_INFO,  result.getError());
			  }
		 }else{
			 resultMap.put(XiuConstant.RESULT_STATUS, ErrorCode.SystemError.getErrorCode());
			 resultMap.put(XiuConstant.STATUS_INFO,  ErrorCode.SystemError.getErrorMsg());
		 }
		 return isComment;
	 }
}
