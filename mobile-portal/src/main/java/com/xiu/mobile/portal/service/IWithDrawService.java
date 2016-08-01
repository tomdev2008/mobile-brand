package com.xiu.mobile.portal.service;

import java.util.List;
import java.util.Map;

import com.xiu.mobile.portal.model.WithdrawBankVo;
import com.xiu.mobile.portal.model.WithdrawItemVo;
import com.xiu.puc.biz.facade.dto.DrawApplyDTO;
import com.xiu.uuc.facade.dto.AcctChangeDTO;
import com.xiu.uuc.facade.dto.BankAcctDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;

/**
 * <p>
 * ************************************************************** 
 * @Description: 虚拟账户提现业务逻辑
 * @AUTHOR wangchengqun@xiu.com
 * @DATE 2014-8-20
 * ***************************************************************
 * </p>
 */

public interface IWithDrawService {
	
	/**
	 * 新增申请提现信息(1.新增提现申请信息 2.账目表设置冻结金额)
	 * @param userId
	 * @param logonName
	 * @param withDrawAmount
	 * @param bankAcctId
	 * @param phone
	 * @return
	 */
	public boolean insertDrawApply(Long userId,String logonName,Double withDrawAmount,String bankAcctId,String phone);
	/**
	 * 取消申请提现信息（1.更新申请提现信息状态 2.账目表设置冻结金额清空）
	 * @param userId 用户Id
	 * @param drawId 申请提现Id
	 * @return
	 */
	public boolean deleteDrawApply(Long userId,Long drawId);
	/**
	 * 查询申请提现信息列表
	 * @param userId 用户Id
	 * @param currentPage 当前页码
	 * @param pageSize 每页条数
	 * @return
	 */
	public Map<String, Object> getDrawApplyList(Long userId,int currentPage,int pageSize)throws Exception;
	/**
	 * 查询特定申请提现信息详情
	 * @param userId 用户Id
	 * @param drawId 申请提现Id
	 * @return
	 */
	public WithdrawItemVo findDrawApply(Long userId,Long drawId)throws Exception;
	
	/**
	 * 查询用户已有的提现账号列表 
	 * @param bankAcctDTO
	 * @return
	 */
	public List<WithdrawBankVo> getBankAcctList(Long userId)throws Exception;
	/**
	 * 新增用户提现账号信息
	 * @param bankAcctDTO
	 * @return
	 */
	public Map<String, Object> addBankAcctInfo(BankAcctDTO bankAcctDTO);
	/**
	 * 编辑用户提现帐号信息
	 * @param bankAcctDTO
	 * @return
	 */
	public Map<String, Object> updateBankAcctInfo(BankAcctDTO bankAcctDTO);
	/**
	 * 删除用户提现帐号信息
	 * @param bankAcctDTO
	 * @return
	 */
	public boolean deleteBankAcctInfo(Long userId,Long bankAcctId);
	/**
	 * 查询特定提现银行账号详情
	 * @param bankAcctDTO
	 * @return
	 */
	public WithdrawBankVo findBankAcctDetailInfo(Long userId,Long bankAcctId)throws Exception;
	/**
	 * 1.虚拟帐户基本信息（账目）包括：账户总计 可提现 不可提现 冻结 2.积分信息查询:总积分,积分冻结
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public VirtualAcctItemDTO getVirtualAccountInfo(Long userId)throws Exception;
	/**
	 * 校验用户是否可进行提现申请 校验内容包括：当前用户账户是否被冻结,申请提现金额是否小于可提现金额
	 * @param withDrawAmount
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean checkVirtualAccount(Double withDrawAmount,Long userId)throws Exception;

	/**
	 * 获得用户虚拟账户的变更明细
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getAccountChangeRecord(Long userId,int currentPage,int pageSize,String startDate,String endDate,String type)throws Exception;
	
	/**
	 * 获得用户虚拟账户的某条变更明细详情
	 * @param userId
	 * @param accountChangeId
	 * @return
	 */
	public Map<String, Object> getAccountChangeInfo(Long userId,
			String accountChangeId)throws Exception;

}
