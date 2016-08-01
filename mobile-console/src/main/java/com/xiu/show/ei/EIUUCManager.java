package com.xiu.show.ei;

import java.util.Map;

import com.xiu.uuc.facade.dto.UserDetailDTO;

/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : UUC接口管理类
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午12:12:59
 * </p>
 **************************************************************** 
 */
public interface EIUUCManager {
	
	
	/**
	 * 通过userId获取联合用户信息
	 * @return
	 */
	Map<String,UserDetailDTO> getFriendInfoDTOList(Map params);
	
}
