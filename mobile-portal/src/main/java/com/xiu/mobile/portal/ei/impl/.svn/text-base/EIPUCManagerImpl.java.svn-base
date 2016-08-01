package com.xiu.mobile.portal.ei.impl;

import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.core.exception.ExceptionFactory;
import com.xiu.mobile.portal.constants.ErrConstants;
import com.xiu.mobile.portal.ei.EIPUCManager;
import com.xiu.puc.biz.facade.DrawApplyFacade;
import com.xiu.puc.biz.facade.dto.DrawApplyDTO;
import com.xiu.puc.biz.facade.dto.Result;
import com.xiu.uuc.facade.dto.AcctChangeDTO;
/**
 *************************************************************** 
 * <p>
 * 
 * @DESCRIPTION : 外部接口-虚拟账户提现uuc puc
 * @AUTHOR : chengqunwang@xiu.com
 * @DATE :2014-8-20
 *       </p>
 **************************************************************** 
 */
@Service("eipucManager")
public class EIPUCManagerImpl implements EIPUCManager{
	private static final XLogger logger = XLoggerFactory.getXLogger(EIPUCManagerImpl.class);
	
	@Autowired
	private DrawApplyFacade drawApplyFacade;
	
	@Override
	public Result insertDrawApply(DrawApplyDTO drawApplyDTO,
			AcctChangeDTO acctChangeDTO) {
		Result result = null;
		try {
			result = drawApplyFacade.insertDrawApply(drawApplyDTO,acctChangeDTO);
		} catch (Exception e) {
			logger.error("调用PUC接口新增申请提现信息(1.新增提现申请信息 2.账目表设置冻结金额)异常exception:",e);
			logger.error("{}.drawApplyFacade 新增申请提现信息异常 | message={}",
					new Object[]{drawApplyFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_PUC_ADD_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			logger.error("{}.drawApplyFacade  调用PUC接口新增申请提现信息失败 | errCode={} | errMessage={}",
					new Object[]{drawApplyFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PUC_ADD_FAILED_ERR, result.getErrorCode(),"调用PUC接口新增申请提现信息失败");
		}
		return result;
	}

	@Override
	public Result deleteDrawApply(DrawApplyDTO drawApplyDTO,
			AcctChangeDTO acctChangeDTO) {
		Result result = null;
		try {
			result = drawApplyFacade.deleteDrawApply(drawApplyDTO,acctChangeDTO);
		} catch (Exception e) {
			logger.error("调用PUC接口取消申请提现信息（1.更新申请提现信息状态 2.账目表设置冻结金额清空）异常exception:",e);
			logger.error("{}.drawApplyFacade 取消申请提现信息异常 | message={}",
					new Object[]{drawApplyFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_PUC_DEL_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			logger.error("{}.drawApplyFacade  调用PUC接口取消申请提现信息失败 | errCode={} | errMessage={}",
					new Object[]{drawApplyFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PUC_DEL_FAILED_ERR, result.getErrorCode(),"调用PUC接口取消申请提现信息失败");
		}
		return result;
	}

	@Override
	public Result getDrawApplyList(DrawApplyDTO drawApplyDTO) {
		Result result = null;
		try {
			result = drawApplyFacade.getDrawApplyList(drawApplyDTO);
		} catch (Exception e) {
			logger.error("调用PUC接口查询申请提现信息列表异常exception:",e);
			logger.error("{}.drawApplyFacade 查询申请提现信息列表异常 | message={}",
					new Object[]{drawApplyFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_PUC_BIZ_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			logger.error("{}.drawApplyFacade  调用PUC接口查询申请提现信息列表失败 | errCode={} | errMessage={}",
					new Object[]{drawApplyFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PUC_BIZ_FAILED_ERR, result.getErrorCode(),"调用PUC接口查询申请提现信息列表失败");
		}
		return result;
	}

	@Override
	public DrawApplyDTO findDrawApply(DrawApplyDTO drawApplyDTO) {
		Result result = null;
		try {
			result = drawApplyFacade.findDrawApply(drawApplyDTO);
		} catch (Exception e) {
			logger.error("调用PUC接口查询特定申请提现信息详情异常exception:",e);
			logger.error("{}.drawApplyFacade 查询特定申请提现信息详情异常 | message={}",
					new Object[]{drawApplyFacade.getClass(),e.getMessage()});
			throw ExceptionFactory.buildEIRuntimeException(ErrConstants.EIErrorCode.EI_PUC_BIZ_INFO_ERR, e);
		}
		if("0".equals(result.isSuccess())){
			logger.error("{}.drawApplyFacade  调用PUC接口查询特定申请提现信息详情失败 | errCode={} | errMessage={}",
					new Object[]{drawApplyFacade.getClass(),result.getErrorCode(),result.getErrorMessage()});
			throw ExceptionFactory.buildEIBusinessException(ErrConstants.EIErrorCode.EI_PUC_BIZ_INFO_FAILED_ERR, result.getErrorCode(),"调用PUC接口查询特定申请提现信息详情失败");
		}
		DrawApplyDTO drawApply=(DrawApplyDTO)result.getData();
		return drawApply;
	}


	
}
