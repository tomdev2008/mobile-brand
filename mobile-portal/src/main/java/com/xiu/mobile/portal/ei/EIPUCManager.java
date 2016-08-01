package com.xiu.mobile.portal.ei;

import java.util.List;

import com.xiu.puc.biz.facade.dto.DrawApplyDTO;
import com.xiu.puc.biz.facade.dto.Result;
import com.xiu.uuc.facade.dto.AcctChangeDTO;


/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 外部接口-虚拟账户提现
 * @AUTHOR : chengqunwang@xiu.com
 * @DATE :2014-8-20
 *       </p>
 **************************************************************** 
 */
public interface EIPUCManager {
	
	/**
	 * 新增申请提现信息(1.新增提现申请信息 2.账目表设置冻结金额)
	 * @param drawApplyDTO
	 * @param acctChangeDTO
	 * @return
	 */
	public Result insertDrawApply(
			DrawApplyDTO drawApplyDTO,AcctChangeDTO acctChangeDTO);
	/**
	 * 取消申请提现信息（1.更新申请提现信息状态 2.账目表设置冻结金额清空）
	 * @param drawApplyDTO
	 * @param acctChangeDTO
	 * @return
	 */
	public Result deleteDrawApply(
			DrawApplyDTO drawApplyDTO,AcctChangeDTO acctChangeDTO);
	/**
	 * 查询申请提现信息列表
	 * @param drawApplyDTO
	 * @return
	 */
	public Result getDrawApplyList(DrawApplyDTO drawApplyDTO);
	/**
	 * 查询特定申请提现信息详情
	 * @param drawApplyDTO
	 * @return
	 */
	public DrawApplyDTO findDrawApply(DrawApplyDTO drawApplyDTO);
}
