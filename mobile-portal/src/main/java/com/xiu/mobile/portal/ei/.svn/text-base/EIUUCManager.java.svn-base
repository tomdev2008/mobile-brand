package com.xiu.mobile.portal.ei;

import java.util.List;
import java.util.Map;

import com.xiu.uuc.facade.dto.AcctChangeExtDTO;
import com.xiu.uuc.facade.dto.BankAcctDTO;
import com.xiu.uuc.facade.dto.DeviceInfoDTO;
import com.xiu.uuc.facade.dto.ModifyUserAuthenDTO;
import com.xiu.uuc.facade.dto.RegisterUserDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UnionInfoDTO;
import com.xiu.uuc.facade.dto.UnionUserBindingDTO;
import com.xiu.uuc.facade.dto.UserAcctDTO;
import com.xiu.uuc.facade.dto.UserBaseDTO;
import com.xiu.uuc.facade.dto.UserDetailDTO;
import com.xiu.uuc.facade.dto.UserOperateLogInfoDTO;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;


/** 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 
 * @AUTHOR : mike@xiu.com 
 * @DATE :2014-5-15 下午12:12:59
 * </p>
 **************************************************************** 
 */
public interface EIUUCManager {

	/**
	 * 判断登录名是否存在
	 * @param logonName
	 * @return
	 */
	Result isLogonNameExist(String logonName);
	
	/**
	 * 判断登录昵称名是否存在
	 * @param petName
	 * @return
	 */
	Result isPetNameExist(String petName);
	
	/**
	 * 判断登录名是否存在（联合登录）
	 * @param logonName
	 * @param userSource
	 * @return
	 */
	Result isLogonNameExist(String logonName, Integer iPartnerChannelId);
	
	/***
	 * 判断登陆名是否能注册
	 * @param logonName
	 * @return
	 */
	public Result isLogonNameCanRegister(String logonName);
	
	/***
	 * 判断登陆名是否能注册
	 * @param logonName
	 * @param type(1: 邮箱, 2:手机, 3:呢称)
	 * @return
	 */
	public Result isLogonNameCanRegister(String logonName,String type);
	
	/**
	 * 注册用户
	 * @param partnerId
	 * @param logonName
	 * @param clientIp
	 * @param iPartnerChannelId
	 * @param userSource
	 * @return
	 */
	Result registerUser(RegisterUserDTO registerUserDTO);
	
	/**
	 * 取得用户基本信息
	 * @return
	 */
	UserBaseDTO getUserBasicInfoByUserId(Long userId);
	
	/**
	 * 取得用户详细信息
	 * @return
	 */
	UserDetailDTO getUserDetailDTOByUserId(Long userId);
	
	/**
	 * 通过userId,channelId获取联合用户信息
	 * @return
	 */
	UnionInfoDTO getUnionInfoDTOByUserId(Long userId,Integer channelId);
	
	/**
	 * 通过unionInfoDTO条件信息获取联合用户信息
	 * @return
	 */
	List<UnionInfoDTO> getUnionInfoByCondition(UnionInfoDTO unionInfoDTO);
	
	/**
	 * 通过userId获取联合用户信息
	 * @return
	 */
	List<UnionInfoDTO> getUnionInfoDTOListByUserId(Long userId);
	
	/***
	 * 获取用户名密码信息
	 * @param logonName
	 * @return
	 */
	public String getPasswordByLogonName(String logonName);
	
	/***
	 * 更改用户密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public Result modifyUserPassword(Long userId, String oldPassword, String newPassword);
	
	/***
	 * 更改用户信息
	 * @param userBaseDTO
	 * @return
	 */
	public Result modifyUserBaseInfo(UserBaseDTO userBaseDTO);
	
	/**
	 * 更新用户信息，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public Result modifyUserBaseInfo(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/***
	 * 联合登录用户绑定走秀用户
	 * @param unionUserBindingDTO
	 * @return
	 */
	public Result bindUser(UnionUserBindingDTO unionUserBindingDTO);
	
	/**
	 * 联合登录用户绑定走秀用户，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public Result bindUser(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/***
	 * 联合登录用户绑定走秀用户
	 * @param unionUserBindingDTO
	 * @return
	 */
	public boolean unBindUser(UnionUserBindingDTO unionUserBindingDTO);
	
	/**
	 * 联合登录用户绑定走秀用户，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public boolean unBindUser(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/***
	 * 判断当前用户是否是空密码
	 * @param userId
	 * @return
	 */
	public boolean isEmptyPassword(Long userId);
	
	/***
	 * 更改用户信息
	 * @param userDetailDTO
	 * @return
	 */
	public Result modifyUserDetailDTO(UserDetailDTO userDetailDTO);
	
	/**
	 * 更改用户信息，增加日志记录
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public Result modifyUserDetailDTO(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/**
	 * 通过用户名、channelId取得用户基本信息
	 * @return
	 */
	UserDetailDTO getUserBasicInfoByLogonName(String logonName,Integer channelId);
	
	/***
	 * 重置用户密码
	 * @param userId
	 * @param newPassword
	 * @return
	 */
	public Result resetUserPassword(Long userId,String newPassword);
	/**
	 * 查询用户已有的提现账号列表 
	 * @param bankAcctDTO
	 * @return
	 */
	public List<BankAcctDTO> getBankAcctListInfo(BankAcctDTO bankAcctDTO);
	/**
	 * 新增用户提现账号信息
	 * @param bankAcctDTO
	 * @return
	 */
	public Result addBankAcctInfo(BankAcctDTO bankAcctDTO);
	/**
	 * 编辑用户提现帐号信息
	 * @param bankAcctDTO
	 * @return
	 */
	public Result updateBankAcctInfo(BankAcctDTO bankAcctDTO);
	/**
	 * 删除用户提现帐号信息
	 * @param bankAcctDTO
	 * @return
	 */
	public Result deleteBankAcctInfo(BankAcctDTO bankAcctDTO);
	/**
	 * 查询特定提现银行账号详情
	 * @param bankAcctDTO
	 * @return
	 */
	public BankAcctDTO findBankAcctDetailInfo(BankAcctDTO bankAcctDTO);

	/**
	 * 查询虚拟账户详细信息
	 * @param virtualAcctItemDTO
	 * @return
	 */
	public Result getVirtualAccountInfo(VirtualAcctItemDTO virtualAcctItemDTO);

	/**
	 * 校验当前用户账户是否被冻结
	 * @param userAcctDTO
	 * @return
	 */
	
	public String checkIsFreezeUserAcct(UserAcctDTO userAcctDTO);

	/**
	 * 获得虚拟账户的变更明细
	 * @param acctChangeDTO
	 * @return
	 */
	public Result getVirtualChangeList(AcctChangeExtDTO acctChangeDTO);
	
	/**
	 * 修改用户认证状态
	 * @param modityUserAuthenDTO
	 * @return
	 */
	public Result modifyUserAuthen(ModifyUserAuthenDTO modityUserAuthenDTO);
	
	/**
	 * 修改用户认证状态，增加日志参数
	 * @param userOperateLogInfoDTO
	 * @return
	 */
	public Result modifyUserAuthen(UserOperateLogInfoDTO userOperateLogInfoDTO);
	
	/**
	 * 绑定百度推送节点信息
	 * @param modityUserAuthenDTO
	 * @return
	 */
	public Result bindXiuUserToBaiduPushDevice(DeviceInfoDTO  deviceInfoDTO );
	/**
	 * 为user设置默认第三方信息
	 * @param userId
	 * @param channelId
	 * @param clientIp
	 * @return
	 */
	Result setDefaultUserInfoFromTrd(long userId,int trdPtySource,String partnerId, String picURL,String trdName);

	/**
	 * 删除用户认证信息
	 * @param userId
	 * @return
	 */
	Result deleteUserAuthen(ModifyUserAuthenDTO modifyUserAuthenDTO);
	/**
	 * 查询用户总积分
	 */
	Map<String,Object> getVirtualIntegralInfo(Long userId);
	/**
	 * 积分明细
	 */
	Map<String,Object> findIntegralInfoList(Long userId,int currPage,int pageSize);
	/**
	 * 虚拟账户积分充值（积分增加）
	 */
	Map<String,Object> addVirtualAccountIntegral(Long userId,Long rltId,Long integral,String busiType);
	/**
	 * 虚拟账户积分支付（积分减少）
	 */
	Map<String,Object> decVirtualAccountIntegral(Long userId,Long rltId,Long integral,String busiType);
	
	public void insertOrUpdateAppInfo(String deviceId, String deviceOs,String deviceVersion,String source,String userId,String version,String idfa);
	
}
