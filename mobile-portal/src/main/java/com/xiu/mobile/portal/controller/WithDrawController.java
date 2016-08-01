package com.xiu.mobile.portal.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.model.WithdrawBankVo;
import com.xiu.mobile.portal.model.WithdrawItemVo;
import com.xiu.mobile.portal.service.IWithDrawService;
import com.xiu.uuc.facade.dto.BankAcctDTO;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;

/**
 * <p>
 * ************************************************************** 
 * @Description: 虚拟账户提现控制器
 * @AUTHOR wangchengqun@xiu.com
 * @DATE 2014-8-20
 * ***************************************************************
 * </p>
 */
@Controller
@RequestMapping("/withDraw")
public class WithDrawController extends BaseController{
private final Logger logger = Logger.getLogger(WithDrawController.class);
	
	@Autowired
	private IWithDrawService withDrawServiceImpl;

	//查询用户提现信息列表接口
	@ResponseBody
	@RequestMapping(value = "/getWithDrawList", produces = "text/html;charset=UTF-8")
	public String getWithDrawList(HttpServletRequest request,String jsoncallback,String currentPage){
		int currPage = 1;
		try {
			currPage = Integer.parseInt(currentPage);
		} catch (NumberFormatException e1) {
			logger.info("询用户提现信息列表时页码参数错误，故使用默认第一页：" + e1.getMessage(),e1);
		}
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int pageSize =XiuConstant.TOPIC_PAGE_SIZE;
			resultMap=withDrawServiceImpl.getDrawApplyList(userId,currPage,pageSize);
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户提现信息列表时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户提现信息列表时发生异常：" + e.getMessage(), e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	//查询用户某条提现信息详情接口
	@ResponseBody
	@RequestMapping(value = "/getWithDrawInfo", produces = "text/html;charset=UTF-8")
	public String getWithDrawInfo(HttpServletRequest request,Long drawId,String jsoncallback){
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			WithdrawItemVo withDraw=withDrawServiceImpl.findDrawApply(userId,drawId);
			if(null!=withDraw){
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("withDraw", withDraw);
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GetWithDrawInfoFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GetWithDrawInfoFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户某条提现信息详情时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户某条提现信息详情时发生异常：" + e.getMessage(), e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	//取消提现接口
	@ResponseBody
	@RequestMapping(value = "/delWithDrawInfo", produces = "text/html;charset=UTF-8")
	public String delWithDrawInfo(HttpServletRequest request,Long drawId,String jsoncallback){
			// 存储返回结果值
			Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
			try {
				// 从cookie中获得userId
				LoginResVo loginResVo = SessionUtil.getUser(request);
				Long userId = Long.parseLong(loginResVo.getUserId());
				WithdrawItemVo withDraw=withDrawServiceImpl.findDrawApply(userId,drawId);
				if(0==withDraw.getBillStatus()){
					boolean result=withDrawServiceImpl.deleteDrawApply(userId,drawId);
					if(result){
						resultMap.put("result", true);
						resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
					}else{
						resultMap.put("result", false);
						resultMap.put("errorCode", ErrorCode.DelWithDrawInfoFailed.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.DelWithDrawInfoFailed.getErrorMsg());
					}
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.DelWithDrawInfoError.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.DelWithDrawInfoError.getErrorMsg());
				}
			} catch (EIBusinessException e) {
				resultMap.put("result", false);
				resultMap.put("errorCode", e.getExtErrCode());
				resultMap.put("errorMsg", e.getExtMessage());
				logger.error("取消提现时发生异常：" + e.getMessageWithSupportCode(), e);
			} catch (Exception e) {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
				logger.error("取消提现时发生异常：" + e.getMessage(),e);
			}
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	//添加申请提现接口
	@ResponseBody
	@RequestMapping(value = "/addWithDrawInfo", produces = "text/html;charset=UTF-8")
	public String addWithDrawInfo(HttpServletRequest request,String withDrawAmount,String bankAcctId,String mobile,String jsoncallback){
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			String logonName=loginResVo.getLogonName();//登录名
			//校验用户是否可进行提现申请 校验内容包括：当前用户账户是否被冻结,申请提现金额是否小于可提现金额
			boolean checkResult=withDrawServiceImpl.checkVirtualAccount(Double.parseDouble(withDrawAmount),userId);
			if(checkResult){
				// bankAcctId解密(AES)
				String aesKey=EncryptUtils.getAESKeySelf();
				bankAcctId=EncryptUtils.decryptBankAcctIdByAES(bankAcctId, aesKey);
				
				boolean result=withDrawServiceImpl.insertDrawApply(userId,logonName,Double.parseDouble(withDrawAmount),bankAcctId,mobile);
				if(result){
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.AddDrawApplyInfoFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.AddDrawApplyInfoFailed.getErrorMsg());
				}
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.AddDrawApplyInfoError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.AddDrawApplyInfoError.getErrorMsg());
			
			}
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("取消提现时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加申请提现时发生异常：" + e.getMessage(), e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	//查询用户虚拟账户可提现,不可提现,冻结金额详情接口
	@ResponseBody
	@RequestMapping(value = "/getWithDrawAmountInfo", produces = "text/html;charset=UTF-8")
	public String getWithDrawAmountInfo(HttpServletRequest request,String jsoncallback){
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			VirtualAcctItemDTO virtualAcct=withDrawServiceImpl.getVirtualAccountInfo(userId);
				  
			if(null!=virtualAcct){
				Double unableDrawMoney=Double.parseDouble(virtualAcct.getUnableDrawMoney().toString())/100;	// 可提现可用金额（可提现总金额-可提现冻结金额） 单位：分
				Double unableNoDrawMoney=Double.parseDouble(virtualAcct.getUnableNoDrawMoney().toString())/100;// 不可提现可用金额（不可提现总金额-不可提现冻结金额） 单位：分
				Long freezeMoney=virtualAcct.getNoDrawFreezeMoney()+virtualAcct.getDrawFreezeMoney();
				Double freezeTotalMoney=Double.parseDouble(freezeMoney.toString())/100;//冻结金额
				Long totMoney=virtualAcct.getDrawTotalMoney()+virtualAcct.getNoDrawTotalMoney();
				Double totalMoney=Double.parseDouble(totMoney.toString())/100;// 总金额(可提现可用金额+不可提现可用金额) 单位：分
			
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("unableDrawMoney", unableDrawMoney);
				resultMap.put("unableNoDrawMoney", unableNoDrawMoney);
				resultMap.put("freezeTotalMoney", freezeTotalMoney);
				resultMap.put("totalMoney", totalMoney);
			}else{
				resultMap.put("result",false);
				resultMap.put("errorCode",ErrorCode.GetWithDrawAmountInfoFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GetWithDrawAmountInfoFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户虚拟账户可提现,不可提现,冻结金额详情时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户虚拟账户可提现,不可提现,冻结金额详情时发生异常：" + e.getMessage(), e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		
	}
	//查询用户提现银行账户接口
	@ResponseBody
	@RequestMapping(value = "/getWithDrawBankList", produces = "text/html;charset=UTF-8")
	public String getWithDrawBankList(HttpServletRequest request,String jsoncallback){
		
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			List<WithdrawBankVo> bankList=withDrawServiceImpl.getBankAcctList(userId);
			if(null!=bankList&&bankList.size()>0){
				// bankAcctId加密(AES)
				for(WithdrawBankVo bankVo:bankList){
					String aesKey=EncryptUtils.getAESKeySelf();
					bankVo.setBankAcctId(EncryptUtils.encryptBankAcctIdByAES(bankVo.getBankAcctId(),aesKey));
				}
				
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("withDrawBankList", bankList);
			}else{
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("withDrawBankList", new ArrayList<BankAcctDTO>());
			}
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户提现银行账户时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户提现银行账户时发生异常：" + e.getMessage(),e);
		}
		
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	//查询用户银行账户信息详情
	@ResponseBody
	@RequestMapping(value = "/getWithDrawBankInfo", produces = "text/html;charset=UTF-8")
	public String getWithDrawBankInfo(HttpServletRequest request,String jsoncallback){
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			String bAcId=request.getParameter("bankAcctId");//银行账户Id
			// bankAcctId解密(AES)
			String aesKey=EncryptUtils.getAESKeySelf();
			bAcId=EncryptUtils.decryptBankAcctIdByAES(bAcId, aesKey);
			Long bankAcctId=Long.valueOf(bAcId);   
			
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			WithdrawBankVo bankAcct=withDrawServiceImpl.findBankAcctDetailInfo(userId,bankAcctId);
			if(null!=bankAcct){
				// bankAcctId加密(AES)
				bAcId=EncryptUtils.encryptBankAcctIdByAES(bankAcctId.toString(), aesKey);
				bankAcct.setBankAcctId(bAcId);
				
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				resultMap.put("withDrawBank", bankAcct);
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.GetWithDrawBankInfoFailed.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.GetWithDrawBankInfoFailed.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户提现银行账户时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户银行账户信息详情时发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	// 添加和修改用户银行账户接口
	@ResponseBody
	@RequestMapping(value = "/addOrUpdateWithDrawBankInfo", produces = "text/html;charset=UTF-8")
	public String addOrUpdateWithDrawBankInfo(HttpServletRequest request, String jsoncallback) {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			String bankAcctId = request.getParameter("bankAcctId");// 银行账户Id
			String bankAcctNo = request.getParameter("bankAcctNo");// 客户银行卡号
			String bankAcctName = request.getParameter("bankAcctName");// 开户人姓名
			String bankName = request.getParameter("bankName");// 开户银行名称
			String provinceCode = request.getParameter("provinceCode");// 省代码
			String cityCode = request.getParameter("cityCode");// 市代码
			String mobile = request.getParameter("mobile");// 手机号码
			String bankNameBranch = request.getParameter("bankNameBranch");// 分行名称
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());

			BankAcctDTO bankAcctDTO = new BankAcctDTO();
			bankAcctDTO.setBankAcctNo(bankAcctNo);
			bankAcctDTO.setBankAcctName(bankAcctName);
			bankAcctDTO.setBankName(bankName);
			bankAcctDTO.setProvinceCode(provinceCode);
			bankAcctDTO.setCityCode(cityCode);
			bankAcctDTO.setMobile(mobile);
			bankAcctDTO.setBankNameBranch(bankNameBranch);
			bankAcctDTO.setUserId(userId);
			bankAcctDTO.setCreateChannelId(11);

			String aesKey = EncryptUtils.getAESKeySelf();
			if (null == bankAcctId || "".equals(bankAcctId)) {
				// add
				resultMap = withDrawServiceImpl.addBankAcctInfo(bankAcctDTO);
				// bankAcctId加密(AES)
				String bAcId = resultMap.get("bankAcctId").toString();
				bAcId = EncryptUtils.encryptBankAcctIdByAES(bAcId, aesKey);
				resultMap.put("bankAcctId", bAcId);

			} else {
				// update
				// bankAcctId解密(AES)
				bankAcctId = EncryptUtils.decryptBankAcctIdByAES(bankAcctId, aesKey);

				bankAcctDTO.setBankAcctId(Long.parseLong(bankAcctId));
				resultMap = withDrawServiceImpl.updateBankAcctInfo(bankAcctDTO);
			}

		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("添加和修改用户银行账户时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加和修改用户银行账户时发生异常：" + e.getMessage(), e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	// 删除用户银行账户接口
	@ResponseBody
	@RequestMapping(value = "/delWithDrawBankInfo", produces = "text/html;charset=UTF-8")
	public String delWithDrawBankInfo(HttpServletRequest request, String jsoncallback) {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			String bAcId = request.getParameter("bankAcctId");
			// bankAcctId解密(AES)
			String aesKey = EncryptUtils.getAESKeySelf();
			bAcId = EncryptUtils.decryptBankAcctIdByAES(bAcId, aesKey);
			Long bankAcctId = Long.valueOf(bAcId);

			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			WithdrawBankVo bankAcct = withDrawServiceImpl.findBankAcctDetailInfo(userId, bankAcctId);
			if (null != bankAcct && bAcId.equals(bankAcct.getBankAcctId())) {
				boolean result = withDrawServiceImpl.deleteBankAcctInfo(userId, bankAcctId);
				if (result) {
					resultMap.put("result", true);
					resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
				} else {
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.DelWithDrawBankInfoFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.DelWithDrawBankInfoFailed.getErrorMsg());
				}
			} else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.DelWithDrawBankInfoError.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.DelWithDrawBankInfoError.getErrorMsg());
			}
		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("删除用户银行账户发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("删除用户银行账户发生异常：" + e.getMessage(), e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	// 查询用户虚拟账户变更明细列表接口
	@ResponseBody
	@RequestMapping(value = "/getAccountChangeList", produces = "text/html;charset=UTF-8")
	public String getAccountChangeList(HttpServletRequest request, String jsoncallback, String currentPage, String startDate, String endDate, String type) {
		int currPage = 1;
		try {
			currPage = Integer.parseInt(currentPage);
		} catch (NumberFormatException e1) {
			logger.info("查询用户虚拟账户变更明细列表时页码参数错误，故使用默认第一页：" + e1.getMessage(), e1);
		}
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
			resultMap = withDrawServiceImpl.getAccountChangeRecord(userId, currPage, pageSize, startDate, endDate, type);

		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户虚拟账户变更明细信息列表时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户虚拟账户变更明细信息列表时发生异常：" + e.getMessage(), e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}

	// 查询用户虚拟账户变更明细详情接口
	@ResponseBody
	@RequestMapping(value = "/getAccountChangeInfo", produces = "text/html;charset=UTF-8")
	public String getAccountChangeInfo(HttpServletRequest request, String jsoncallback, String accountChangeId) {
		// 存储返回结果值
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		try {
			// 从cookie中获得userId
			LoginResVo loginResVo = SessionUtil.getUser(request);
			Long userId = Long.parseLong(loginResVo.getUserId());
			resultMap = withDrawServiceImpl.getAccountChangeInfo(userId, accountChangeId);

		} catch (EIBusinessException e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", e.getExtErrCode());
			resultMap.put("errorMsg", e.getExtMessage());
			logger.error("查询用户虚拟账户变更明细详情时发生异常：" + e.getMessageWithSupportCode(), e);
		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询用户虚拟账户变更明细详情时发生异常：" + e.getMessage(), e);
		}

		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
