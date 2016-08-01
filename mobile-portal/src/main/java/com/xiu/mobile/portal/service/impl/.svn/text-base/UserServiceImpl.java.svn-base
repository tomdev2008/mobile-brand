package com.xiu.mobile.portal.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.lang.StringUtil;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.dao.ShowUserDao;
import com.xiu.mobile.portal.ei.EIOrderManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.ShowUserInfoVo;
import com.xiu.mobile.portal.service.IMemcachedService;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UnionUserBindingDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;

@Service("userService")
public class UserServiceImpl implements IUserService {
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private EIUUCManager eiuucManager;
	@Autowired
	private IMemcachedService memcachedService;
	@Autowired
	private EIOrderManager eiOrderManager; 
	@Autowired
	private ShowUserDao showUserDao;

	@Override
	public boolean isLogonNameExist(String logonName) {
		boolean flag = false;
		Result result = eiuucManager.isLogonNameExist(logonName);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = Boolean.parseBoolean(result.getData().toString());
		}
		return flag;
	}
	
	@Override
	public boolean isPetNameExist(String petName) {
		boolean flag = false;
		Result result = eiuucManager.isPetNameExist(petName);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = Boolean.parseBoolean(result.getData().toString());
		}
		return flag;
	}

	@Override
	public boolean isLogonNameExist(String logonName, Integer iPartnerChannelId) {
		boolean flag = false;
		Result result = eiuucManager.isLogonNameExist(logonName, iPartnerChannelId);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = Boolean.parseBoolean(result.getData().toString());
		}
		return flag;
	}

	@Override
	public boolean isLogonNameCanRegister(String logonName) {
		boolean flag = false;
		Result result = eiuucManager.isLogonNameCanRegister(logonName);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = Boolean.parseBoolean(result.getData().toString());
		}
		return flag;
	}

	@Override
	public boolean isLogonNameCanRegister(String logonName, String type) {
		boolean flag = false;
		Result result = eiuucManager.isLogonNameCanRegister(logonName, type);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = Boolean.parseBoolean(result.getData().toString());
		}
		return flag;
	}

	@Override
	public UserBaseDTO getUserBasicInfoByUserId(Long userId) {
		return eiuucManager.getUserBasicInfoByUserId(userId);
	}
	
	@Override
	public UserDetailDTO getUserDetailDTOByUserId(Long userId) {
//		String key=XiuConstant.USER_DETAIL_CACHE_KEY+userId;
//		UserDetailDTO userDetail=memcachedService.getUserDetailCache(key);
//		if(userDetail==null){
		UserDetailDTO	userDetail=eiuucManager.getUserDetailDTOByUserId(userId);
//			Map params=new HashMap();
//			params.put("key", key);
//			params.put("value", userDetail);
//			memcachedService.addUserDetailCache(params);
//		}
		return userDetail;
	}
	
	@Override
	public String getPasswordByLogonName(String logonName){
		return eiuucManager.getPasswordByLogonName(logonName);
	}

	@Override
	public boolean modifyUserPassword(Long userId, String oldPassword,String newPassword) {
		// 存储返回结果值
		boolean flag = false;
		// 验证密码修改密码的相关业务逻辑处理
		Result result = eiuucManager.modifyUserPassword(userId, oldPassword, newPassword);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public UserDetailDTO getUserBasicInfoByLogonName(String logonName,Integer channelId) {
		return eiuucManager.getUserBasicInfoByLogonName(logonName, channelId);
	}

	@Override
	public boolean modifyUserBaseInfo(UserBaseDTO userBaseDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.modifyUserBaseInfo(userBaseDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	public boolean modifyUserBaseInfo(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.modifyUserBaseInfo(userOperateLogInfoDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean bindUser(UnionUserBindingDTO unionUserBindingDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.bindUser(unionUserBindingDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	public boolean bindUser(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.bindUser(userOperateLogInfoDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean modifyUserDetailDTO(UserDetailDTO userDetailDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.modifyUserDetailDTO(userDetailDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	public boolean modifyUserDetailDTO(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.modifyUserDetailDTO(userOperateLogInfoDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}
	
	@Override
	public boolean resetUserPassword(Long userId, String newPassword) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.resetUserPassword(userId, newPassword);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isEmptyPassword(Long userId) {
		return eiuucManager.isEmptyPassword(userId);
	}

	/**
	 * 获取秀客用户信息
	 */
	public ShowUserInfoVo getShowUserInfo(String userId) {
		ShowUserInfoVo result = showUserDao.getShowUserInfo(userId);
		return result;
	}

	/**
	 * 获取秀客管理员权限
	 */
	public boolean getManagerAuthority(Map params) {
		Integer isManager=showUserDao.getManagerAuthority(params);
		if(isManager==1){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 查询用户手机是否绑定
	 */
	public boolean isMobileBinding(String userId) {
		if(StringUtils.isNotBlank(userId)) {
			UserBaseDTO userInfo = eiuucManager.getUserBasicInfoByUserId(Long.parseLong(userId));
			if(userInfo != null) {
				if(StringUtils.isNotBlank(userInfo.getMobile()) && "1".equals(userInfo.getMobileAuthenStatus())) {
					//如果手机号不为空，且手机认证状态为1，则认为已绑定
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 修改用户认证状态
	 */
	public boolean modifyUserAuthen(ModifyUserAuthenDTO modityUserAuthenDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.modifyUserAuthen(modityUserAuthenDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
				
	}
	
	/**
	 * 修改用户认证状态，增加日志参数
	 */
	public boolean modifyUserAuthen(UserOperateLogInfoDTO userOperateLogInfoDTO) {
		// 存储返回结果值
		boolean flag = false;
		Result result = eiuucManager.modifyUserAuthen(userOperateLogInfoDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 查询用户是否有资产：1.有效订单(审核通过，且未撤销) 2.余额 3.秀
	 */
	public boolean isUserHasAsset(String userId) {
		try {
			//1.检测有效订单:已审核、交易完结、订单完结
			int orderCounts = eiOrderManager.queryValueOrderDetailCount(Long.parseLong(userId));
			if(orderCounts > 0) {
				return true;
			}
			
			//2.检测余额
			VirtualAcctItemDTO virtualAcctItemDTO = new VirtualAcctItemDTO();
			virtualAcctItemDTO.setUserId(Long.parseLong(userId));
			Result result = eiuucManager.getVirtualAccountInfo(virtualAcctItemDTO); //查询虚拟账号信息

			if (null!=result&&"1".equals(result.isSuccess())) {
				List<VirtualAcctItemDTO> virtualAcctItemList=(List<VirtualAcctItemDTO>) result.getData();
				if (virtualAcctItemList != null && virtualAcctItemList.size() > 0) {
					VirtualAcctItemDTO virtualAcct = virtualAcctItemList.get(0);
					Long totMoney = virtualAcct.getDrawTotalMoney() + virtualAcct.getNoDrawTotalMoney();
					Double totalMoney = Double.parseDouble(totMoney.toString())/100; //总金额(可提现可用金额+不可提现可用金额) 单位：分
					if(totalMoney > 0) {
						//如果金额大于0，则认为有资产
						return true;
					}
				}
			}
			
//			//3.检测秀帖，未被删除且审核通过
//			int showCount = showUserDao.getUserShowCount(userId);
//			if(showCount > 0) {
//				return true;
//			}
		} catch (NumberFormatException e) {
			logger.error("查询用户是否有资产时发生异常，用户ID："+userId+",错误信息：", e);
		} catch (Exception e) {
			logger.error("查询用户是否有资产时发生异常，用户ID："+userId+",错误信息：", e);
		}
		
		return false;
	}

	/**
	 * 删除用户认证信息
	 */
	public boolean deleteUserAuthen(Long userId, String authenType, String authenValue, String ipAddr) {
		ModifyUserAuthenDTO modifyUserAuthenDTO = new ModifyUserAuthenDTO();
		modifyUserAuthenDTO.setUserId(userId);
		modifyUserAuthenDTO.setAuthenType(authenType);
		modifyUserAuthenDTO.setAuthenValue(authenValue);
		modifyUserAuthenDTO.setIpAddr(ipAddr);
		Result result = eiuucManager.deleteUserAuthen(modifyUserAuthenDTO);
		if (result != null && "1".equals(result.getSuccess())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 更新用户昵称
	 */
	public boolean updateUserPetName(String userId, String thirdName) {
		boolean flag = false;
		if(StringUtil.isBlank(thirdName) || thirdName.startsWith("xiu_")) {
			//如果第三方昵称为空或者为xiu_开头，则不更新
			return true;
		}
		UserBaseDTO userInfo = eiuucManager.getUserBasicInfoByUserId(Long.parseLong(userId));
		if(userInfo != null) {
			String petName = userInfo.getPetName();
			if(StringUtil.isNotBlank(petName) && petName.startsWith("xiu_")) {
				//如果用户昵称不为空且以xiu_开头，更新为第三方昵称
				String newPetName = getAvailablePetName(thirdName);
				if(StringUtil.isNotBlank(newPetName)) {
					UserBaseDTO userBaseDTO = new UserBaseDTO();
					userBaseDTO.setUserId(Long.parseLong(userId));
					userBaseDTO.setPetName(newPetName);
					//修改昵称
					modifyUserBaseInfo(userBaseDTO); 
				}
			}
			flag = true;
		}
		
		return flag;
	}

	//获取可用的昵称
	private String getAvailablePetName(String petName) {
		String result = "";
		if(StringUtil.isNotBlank(petName)) {
			int count = 7; //生成五次
			String newPetName = petName;
			for(int i = 2; i < count; i++) {
				boolean petNameExits = isPetNameExist(newPetName);
				if(petNameExits) {
					newPetName = petName + Tools.getRandomCharAndNumr(i);
				} else {
					//如果昵称不存在
					result = newPetName;
					break;
				}
			}
		}
		
		return result;
	}

}
