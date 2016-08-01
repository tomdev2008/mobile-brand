package com.xiu.mobile.simple.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.simple.common.constants.ErrorCode;
import com.xiu.mobile.simple.ei.EIUUCManager;
import com.xiu.mobile.simple.service.IUserService;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;

@Service("userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private EIUUCManager eiuucManager;

	@Override
	public boolean isLogonNameExist(String logonName) {
		boolean flag = false;
		Result result = eiuucManager.isLogonNameExist(logonName);
		if (result!=null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isLogonNameExist(String logonName, Integer iPartnerChannelId) {
		boolean flag = false;
		Result result = eiuucManager.isLogonNameExist(logonName, iPartnerChannelId);
		if (result!=null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public UserBaseDTO getUserBasicInfoByUserId(Long userId) {
		return eiuucManager.getUserBasicInfoByUserId(userId);
	}

	@Override
	public Map<String, Object> modifyUserPassword(Long userId, String oldPassword,String newPassword) {
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 验证密码修改密码的相关业务逻辑处理
			Result optResult = eiuucManager.modifyUserPassword(userId, oldPassword, newPassword);
			if (optResult!=null) {
				if ("1".equals(optResult.isSuccess())) {
					result.put("result",true);
					result.put("errorCode",ErrorCode.Success.getErrorCode());
					result.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}else{
					result.put("result",false);
					result.put("errorCode",optResult.getErrorCode());
					result.put("errorMsg", optResult.getErrorMessage());
				}
			}else{
				result.put("result", false);
				result.put("errorCode", ErrorCode.SystemError.getErrorCode());
				result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			}
		} catch (Exception e) {
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", e.getMessage());
		}
		return result;
	}

	@Override
	public UserDetailDTO getUserBasicInfoByLogonName(String logonName,Integer channelId) {
		return eiuucManager.getUserBasicInfoByLogonName(logonName, channelId);
	}

	@Override
	public Map<String, Object> resetUserPassword(Long userId, String newPassword) {
		// 存储返回结果值
		Map<String, Object> result = new HashMap<String, Object>();
		Result optResult = eiuucManager.resetUserPassword(userId, newPassword);
		if (optResult!=null) {
			if ("1".equals(optResult.isSuccess())) {
				result.put("result",true);
				result.put("errorCode",ErrorCode.Success.getErrorCode());
				result.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				result.put("result",false);
				result.put("errorCode",optResult.getErrorCode());
				result.put("errorMsg", optResult.getErrorMessage());
			}
		}else{
			result.put("result", false);
			result.put("errorCode", ErrorCode.SystemError.getErrorCode());
			result.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return result;
	}

}
