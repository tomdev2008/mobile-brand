/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
 
package com.xiu.show.ei.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.web.contants.ErrConstants;
import com.xiu.show.core.exception.ExceptionFactory;
import com.xiu.show.ei.EIUUCManager;
import com.xiu.uuc.facade.UserManageFacade;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserDetailDTO;

/** 
 * <p>
 * ************************************************************** 
 * @Description: 用户中心
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-5-22 下午3:34:50 
 * ***************************************************************
 * </p>
 */
@Service("eiuucManager")
public class EIUUCManagerImpl implements EIUUCManager {
	private static final XLogger LOGGER = XLoggerFactory.getXLogger(EIUUCManagerImpl.class);
	
	@Autowired
	private UserManageFacade userManageFacade;


	public Map<String,UserDetailDTO> getFriendInfoDTOList(Map params) {
		Result result = null;
		String type=(String)params.get("type");
		List<String> mobiles=(List<String>)params.get("values");
		try {
			if(type.equals("1")){
				result = userManageFacade.getUserListByMobileList(mobiles);
			}else if(type.equals("2")){
				result = userManageFacade.getUserListByPartnerIdList(mobiles);
			}
		} catch (Exception e) {
			LOGGER.error("调用UUC接口获取获取好友信息异常exception:",e);
			LOGGER.error("{}.getUnionInfoByUserId获取联合好友列表信息异常 | message={}",
					new Object[]{userManageFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, e);
		}
		if(result==null){
			return new HashMap<String,UserDetailDTO>();
		}else{
			if(result.isSuccess()==null||"0".equals(result.isSuccess())){
				LOGGER.error("{}.getUnionInfoByUserId 调用UUC接口获取获取好友列表信息失败 | errCode={} | errMessage={}",
						new Object[]{userManageFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
				throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_UUC_BIZ_ERR, result.getErrorCode(),"调用UUC接口获取获取联合用户列表信息失败");
			}
			
			if (result.getData() != null) {
				// 数据转换
				Map<String,UserDetailDTO> unionInfoDTOList = (Map<String,UserDetailDTO>) result.getData();
				return unionInfoDTOList;
			}else{
				return new HashMap<String,UserDetailDTO>();
			}
		}
	}
	
	
}
