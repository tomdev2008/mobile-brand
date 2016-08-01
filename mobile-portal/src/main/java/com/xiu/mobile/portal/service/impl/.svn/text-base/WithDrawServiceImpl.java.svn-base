package com.xiu.mobile.portal.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.ei.EIPUCManager;
import com.xiu.mobile.portal.ei.EISysParamManager;
import com.xiu.mobile.portal.ei.EIUUCManager;
import com.xiu.mobile.portal.model.VAcctChangeDetailVo;
import com.xiu.mobile.portal.model.WithdrawBankVo;
import com.xiu.mobile.portal.model.WithdrawItemVo;
import com.xiu.mobile.portal.service.IWithDrawService;
import com.xiu.puc.biz.facade.dto.DrawApplyDTO;
import com.xiu.puc.biz.facade.util.Page;
import com.xiu.uuc.facade.dto.AcctChangeDTO;
import com.xiu.uuc.facade.dto.AcctChangeExtDTO;
import com.xiu.uuc.facade.dto.BankAcctDTO;
import com.xiu.uuc.facade.dto.Result;
import com.xiu.uuc.facade.dto.UserAcctDTO;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;
@Service("withDrawService")
public class WithDrawServiceImpl implements IWithDrawService{
	
	private Logger logger = Logger.getLogger(WithDrawServiceImpl.class);

	@Autowired
	private EIPUCManager eipucManager;
	@Autowired
	private EIUUCManager eiuucManager;
	@Autowired
	private EISysParamManager eiSysParamManager;
	@Override
	public boolean insertDrawApply(Long userId,String logonName,Double withDrawAmount,String bankAcctId,String phone) {
		boolean flag=false;
		DrawApplyDTO drawApplyDTO = new DrawApplyDTO();
		drawApplyDTO.setUserId(userId);
		drawApplyDTO.setUserName(logonName);
		drawApplyDTO.setOperator(logonName);
		int withdrawAmt=(int)(withDrawAmount*100);
		drawApplyDTO.setApplyAmount((long)withdrawAmt);//分为单位

		//获得银行信息
		BankAcctDTO bankAcctDTO=new BankAcctDTO();
		bankAcctDTO.setUserId(userId);
		bankAcctDTO.setBankAcctId(Long.parseLong(bankAcctId));
		BankAcctDTO bankAcct=eiuucManager.findBankAcctDetailInfo(bankAcctDTO);
		 if(null!=bankAcct){
		    // 根据省市代码取省市名称
			String provinceName=eiSysParamManager.getAddressDescByCode(bankAcct.getProvinceCode());
			String cityName=eiSysParamManager.getAddressDescByCode(bankAcct.getCityCode());
			String bankAddress = provinceName + cityName
					+ bankAcct.getBankName()+ bankAcct.getBankNameBranch();
			drawApplyDTO.setBankName(bankAddress);
			// 开户姓名
			drawApplyDTO.setSignName(bankAcct.getBankAcctName());
			drawApplyDTO.setBankAccount(bankAcct.getBankAcctNo());
			// 联系方式
			drawApplyDTO.setContactInfo(phone);
			// 单据状态 :0:未审核
			drawApplyDTO.setBillStatus(0);
			//单据状态 对应的文字描述信息[0:未审核,1:审核中,2:审核通过,3. 审核不通过,4:已返款 5:返款成功 6:返款失败 7:撤销]
			drawApplyDTO.setBillStatusDesc("未审核");
			
			AcctChangeDTO acctChangeDTO = new AcctChangeDTO();
			acctChangeDTO.setBusiType("va1102");//申请提现
			acctChangeDTO.setRltChannelId("11");
			com.xiu.puc.biz.facade.dto.Result result=eipucManager.insertDrawApply(drawApplyDTO,acctChangeDTO);
			if(null!=result&&"1".equals(result.isSuccess())){
				flag=true;
			}else{
				flag=false;
			}
		 }else{
			flag=false; 
		 }
		 return flag;
	}

	@Override
	public boolean deleteDrawApply(Long userId,Long drawId) {
		DrawApplyDTO drawApplyDTO=new DrawApplyDTO();
		drawApplyDTO.setDrawId(drawId);
		drawApplyDTO.setBillStatus(7);
		
		AcctChangeDTO acctChangeDTO=new AcctChangeDTO();
		acctChangeDTO.setRltId(drawId);
		acctChangeDTO.setBusiType("va1108");//取消申请提现
		acctChangeDTO.setRltChannelId("11");// 获取渠道id，11表示官网，12表示秀团
		com.xiu.puc.biz.facade.dto.Result result=eipucManager.deleteDrawApply(drawApplyDTO,acctChangeDTO);
		if(null!=result&&"1".equals(result.isSuccess())){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Map<String, Object> getDrawApplyList(Long userId,int currentPage,int pageSize)throws Exception{
		//DateFormatUtils.format(bizOrder.getGmtCreate(), "yyyy-MM-dd HH:mm:ss")
		List<WithdrawItemVo> withdrawList=new ArrayList<WithdrawItemVo>();
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		DrawApplyDTO drawApplyDTO=new DrawApplyDTO();
		drawApplyDTO.setUserId(userId);
		drawApplyDTO.setCurrentPage(currentPage);
		drawApplyDTO.setPageSize(pageSize);
		com.xiu.puc.biz.facade.dto.Result result=eipucManager.getDrawApplyList(drawApplyDTO);
		if(null!=result&&"1".equals(result.isSuccess())){
			List<DrawApplyDTO> drawApplyList=(List<DrawApplyDTO>)result.getData();
			Page page=result.getPage();
			if(null!=drawApplyList&&drawApplyList.size()>0){
				for(DrawApplyDTO drawApply:drawApplyList){
					WithdrawItemVo withdraw=new WithdrawItemVo();
					withdraw.setApplyAmount(Double.parseDouble(drawApply.getApplyAmount().toString())/100);
					withdraw.setBankAccount(drawApply.getBankAccount());
					withdraw.setBankName(drawApply.getBankName());
					withdraw.setBillStatus(drawApply.getBillStatus());
					withdraw.setBillStatusDesc(drawApply.getBillStatusDesc());
					withdraw.setContactInfo(drawApply.getContactInfo());
					withdraw.setDrawId(drawApply.getDrawId());
					withdraw.setGmtCreate(DateFormatUtils.format(drawApply.getGmtCreate(),"yyyy-MM-dd HH:mm:ss"));
					withdraw.setSignName(drawApply.getSignName());
					withdraw.setUserId(drawApply.getUserId());
					withdrawList.add(withdraw);
				}
				resultMap.put("result", true);
				resultMap.put("totalPage", page.getTotalPage());
				resultMap.put("withDrawList", withdrawList);
				}else{
					resultMap.put("result", true);
					resultMap.put("totalPage",0);
					resultMap.put("withDrawList", new ArrayList<WithdrawItemVo>());
				}
		}else{
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GetDrawApplyListFailed.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GetDrawApplyListFailed.getErrorMsg());
		}
		//判断账户是否冻结，可提现金额大于0
		boolean checkResult=checkVirtualAccount(null,userId);
		resultMap.put("checkResult", checkResult);
		return resultMap;
	}
	@Override
	public boolean checkVirtualAccount(Double withDrawAmount,Long userId)throws Exception{
		UserAcctDTO userAcctDTO = new UserAcctDTO();
		userAcctDTO.setUserId(userId);
		// 校验当前用户账户是否被冻结 01 表示账户正常 02 表示账户冻结
		String isFreeze = eiuucManager.checkIsFreezeUserAcct(userAcctDTO);
		boolean result=false;
		if(null!=isFreeze){
			if("02".equals(isFreeze)){
				result=false;
			}else{ 
				// 判断申请提现金额是否小于可提现金额（账户余额=可提现金额-可提现冻结金额）
				VirtualAcctItemDTO virtualAcctItemDTO=this.getVirtualAccountInfo(userId);
				Double unableMoney=Double.parseDouble(virtualAcctItemDTO.getUnableDrawMoney().toString())/100;
				if(null!=withDrawAmount&&withDrawAmount>0){
					
					if (unableMoney>0&&withDrawAmount >unableMoney ) {
						result=false;
					}else if (unableMoney>0&&withDrawAmount <=unableMoney ){
						result=true;
					}else{
						result=false;
					}
				}else{
					if (unableMoney>0){
						result=true;
					}else{
						result=false;
					}
				}
				
			}
			
		}else {
			result=false;
		} 
		return result;
	}

	@Override
	public WithdrawItemVo findDrawApply(Long userId,Long drawId)throws Exception {
		DrawApplyDTO drawApplyDTO=new DrawApplyDTO();
		drawApplyDTO.setUserId(userId);
		drawApplyDTO.setDrawId(drawId);
		DrawApplyDTO drawApply=eipucManager.findDrawApply(drawApplyDTO);
		if(userId.equals(drawApply.getUserId())){
			WithdrawItemVo withdraw=new WithdrawItemVo();
			withdraw.setApplyAmount(Double.parseDouble(drawApply.getApplyAmount().toString())/100);
			withdraw.setBankAccount(drawApply.getBankAccount());
			withdraw.setBankName(drawApply.getBankName());
			withdraw.setBillStatus(drawApply.getBillStatus());
			withdraw.setBillStatusDesc(drawApply.getBillStatusDesc());
			withdraw.setContactInfo(drawApply.getContactInfo());
			withdraw.setDrawId(drawApply.getDrawId());
			withdraw.setGmtCreate(DateFormatUtils.format(drawApply.getGmtCreate(),"yyyy-MM-dd HH:mm:ss"));
			withdraw.setSignName(drawApply.getSignName());
			withdraw.setUserId(drawApply.getUserId());
			return withdraw;
		}else{
			return	new WithdrawItemVo();
		}
		
	}

	@Override
	public List<WithdrawBankVo> getBankAcctList(Long userId) throws Exception{
		List<WithdrawBankVo> withdrawBankList=new ArrayList<WithdrawBankVo>();
		BankAcctDTO bankAcctDTO=new BankAcctDTO();
		bankAcctDTO.setUserId(userId);
		List<BankAcctDTO> bankAcctList=eiuucManager.getBankAcctListInfo(bankAcctDTO);
		if(null!=bankAcctList&&bankAcctList.size()>0){
			for(BankAcctDTO bankAcct:bankAcctList){
				WithdrawBankVo bank=new WithdrawBankVo();
				bank.setBankAcctId(bankAcct.getBankAcctId().toString());
				bank.setBankAcctName(bankAcct.getBankAcctName());
				bank.setBankAcctNo(bankAcct.getBankAcctNo());
				bank.setBankName(bankAcct.getBankName());
				bank.setBankNameBranch(bankAcct.getBankNameBranch());
				bank.setCityCode(bankAcct.getCityCode());
				bank.setMobile(bankAcct.getMobile());
				bank.setProvinceCode(bankAcct.getProvinceCode());
				String provinceName=eiSysParamManager.getAddressDescByCode(bankAcct.getProvinceCode());
				bank.setProvinceName(provinceName);
				String cityName=eiSysParamManager.getAddressDescByCode(bankAcct.getCityCode());
				bank.setCityName(cityName);
				bank.setUserId(bankAcct.getUserId());
				withdrawBankList.add(bank);
			}
			
		}
		return withdrawBankList;
	}

	@Override
	public Map<String, Object> addBankAcctInfo(BankAcctDTO bankAcctDTO) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Result result=eiuucManager.addBankAcctInfo(bankAcctDTO);
		if(null!=result&&"1".equals(result.isSuccess())){
			Long bankId=(Long)result.getData();
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			resultMap.put("bankAcctId", bankId);
		}else{
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.AddDrawApplyBankFailed.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.AddDrawApplyBankFailed.getErrorMsg());
		
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> updateBankAcctInfo(BankAcctDTO bankAcctDTO) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		Result result=eiuucManager.updateBankAcctInfo(bankAcctDTO);
		if(null!=result&&"1".equals(result.isSuccess())&&(Integer)result.getData()>0){
			resultMap.put("result", true);
			resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
		}else{
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.UpdateDrawApplyBankFailed.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.UpdateDrawApplyBankFailed.getErrorMsg());
		
		}
		return resultMap;
	}

	@Override
	public boolean deleteBankAcctInfo(Long userId,Long bankAcctId) {
		BankAcctDTO bankAcctDTO=new BankAcctDTO();
		bankAcctDTO.setUserId(userId);
		bankAcctDTO.setBankAcctId(bankAcctId);
		Result result=eiuucManager.deleteBankAcctInfo(bankAcctDTO);
		if(null!=result&&"1".equals(result.isSuccess())&&(Integer)result.getData()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public WithdrawBankVo findBankAcctDetailInfo(Long userId,Long bankAcctId)throws Exception {
		BankAcctDTO bankAcctDTO=new BankAcctDTO();
		WithdrawBankVo bank=new WithdrawBankVo();
		bankAcctDTO.setUserId(userId);
		bankAcctDTO.setBankAcctId(bankAcctId);
		BankAcctDTO bankAcct=eiuucManager.findBankAcctDetailInfo(bankAcctDTO);
		if(null!=bankAcct){
			bank.setBankAcctId(bankAcct.getBankAcctId().toString());
			bank.setBankAcctName(bankAcct.getBankAcctName());
			bank.setBankAcctNo(bankAcct.getBankAcctNo());
			bank.setBankName(bankAcct.getBankName());
			bank.setBankNameBranch(bankAcct.getBankNameBranch());
			bank.setCityCode(bankAcct.getCityCode());
			bank.setMobile(bankAcct.getMobile());
			bank.setProvinceCode(bankAcct.getProvinceCode());
			String provinceName=eiSysParamManager.getAddressDescByCode(bankAcct.getProvinceCode());
			bank.setProvinceName(provinceName);
			String cityName=eiSysParamManager.getAddressDescByCode(bankAcct.getCityCode());
			bank.setCityName(cityName);
			bank.setUserId(bankAcct.getUserId());
			
		}
		return bank;
	}

	@Override
	public VirtualAcctItemDTO getVirtualAccountInfo(Long userId)
			throws Exception {
		VirtualAcctItemDTO virtualAcctItemDTO = new VirtualAcctItemDTO();
		VirtualAcctItemDTO virtualAcctItem= null;
		virtualAcctItemDTO.setUserId(userId);
		Result result = eiuucManager.getVirtualAccountInfo(virtualAcctItemDTO);

		if (null!=result&&"1".equals(result.isSuccess())) {
			List<VirtualAcctItemDTO> virtualAcctItemList=(List<VirtualAcctItemDTO>) result.getData();
			if (virtualAcctItemList != null&& virtualAcctItemList.size() > 0) {
				virtualAcctItem = virtualAcctItemList.get(0);
			}

		}
		return virtualAcctItem;
	}
	@Override
	public Map<String, Object> getAccountChangeRecord(Long userId,int currentPage,int pageSize,String startDate,String endDate,String type)throws Exception{
	Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
	List<VAcctChangeDetailVo>  acctChangeList=new ArrayList<VAcctChangeDetailVo>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	AcctChangeExtDTO acctChangeDTO = new AcctChangeExtDTO();
	acctChangeDTO.setUserId(userId);
	acctChangeDTO.setCurrentPage(currentPage);
	acctChangeDTO.setPageSize(pageSize);
	
	if(null!=startDate&&!"".equals(startDate)){
		acctChangeDTO.setValidTime(sdf.parse(startDate+" 00:00:00"));
	}
	if(null!=endDate&&!"".equals(endDate)){
		acctChangeDTO.setExpireTime(sdf.parse(endDate+" 23:59:59"));
	}
	if(null!=type&&!"".equals(type)&&!"0".equals(type)){
		// 账目进出类型 01.进账 02出帐 03冻结 04解冻
		acctChangeDTO.setIoType("0"+type);
	}
	  List<String> acctItemTypeList = new ArrayList<String>();
      acctItemTypeList.add("01");
      acctItemTypeList.add("02");
      acctChangeDTO.setAcctTypeCodeList(acctItemTypeList);

	Result result = eiuucManager.getVirtualChangeList(acctChangeDTO);
	if(null!=result&&"1".equals(result.isSuccess())){
		List<AcctChangeExtDTO> acctList=(List<AcctChangeExtDTO>)result.getData();
		com.xiu.uuc.facade.util.Page page=result.getPage();//包含所有账目类型的数据
		if(null!=acctList&&acctList.size()>0){
			for(AcctChangeExtDTO acctChange:acctList){
				//账目类型 01 可提现 02不可提现 03积分(虚拟账户不显示积分)
//				if("01".equals(acctChange.getAcctTypeCode())||"02".equals(acctChange.getAcctTypeCode())){
					VAcctChangeDetailVo acct=new VAcctChangeDetailVo();
					acct.setAccountChangeId(acctChange.getAccountChangeId());
					acct.setAcctId(acctChange.getAcctId());
					acct.setAcctItemId(acctChange.getAcctItemId());
					acct.setAcctTypeCode(acctChange.getAcctTypeCode());
					acct.setAcctTypeCodeDesc(acctChange.getAcctTypeCodeDesc());
					acct.setBusiType(acctChange.getBusiType());
					acct.setBusiTypeDesc(acctChange.getBusiTypeDesc());
					acct.setCreateTime(DateFormatUtils.format(acctChange.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
					acct.setIoType(acctChange.getIoType());// 01.进账 02出帐 
					acct.setIoAmount(Double.parseDouble(acctChange.getIoAmount().toString())/100);
					acct.setLastIoAmount(Double.parseDouble(acctChange.getLastIoAmount().toString())/100);
					acct.setUserId(userId);
					//关联信息
					String rltCode="";
					if(null!=acctChange.getRltCode()&&!"0".equals(acctChange.getRltCode())){
						rltCode=acctChange.getRltCode();
					}else{
						rltCode=acctChange.getRltId();
					}
					if(null!=rltCode){
						if("va1101".equals(acctChange.getBusiType())||"va1110".equals(acctChange.getBusiType())||"va1106".equals(acctChange.getBusiType())){
							acct.setRltCode("订单号"+rltCode);
						}else if("va1107".equals(acctChange.getBusiType())){
							acct.setRltCode("退换货单号"+rltCode);
						}else if("va1111".equals(acctChange.getBusiType())){
							acct.setRltCode("充值卡号"+acctChange.getRltId());
						}else if(!"0".equals(rltCode)){
							acct.setRltCode(rltCode);
						}else{
							acct.setRltCode("无");
						}
					}
						
					acctChangeList.add(acct);
//				}
				
			}
			resultMap.put("result", true);
			resultMap.put("totalPage",page.getTotalPage());
			resultMap.put("acctChangeList", acctChangeList);
			}else{
				resultMap.put("result", true);
				resultMap.put("totalPage",0);
				resultMap.put("acctChangeList", new ArrayList<VAcctChangeDetailVo>());
			}
	}else{
		resultMap.put("result", false);
		resultMap.put("errorCode", ErrorCode.GetAccountChangeListFailed.getErrorCode());
		resultMap.put("errorMsg", ErrorCode.GetAccountChangeListFailed.getErrorMsg());
	}
	 
	return resultMap;
	}

	@Override
	public Map<String, Object> getAccountChangeInfo(Long userId,String accountChangeId)throws Exception {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		VAcctChangeDetailVo acct=new VAcctChangeDetailVo();
		AcctChangeExtDTO acctChangeDTO = new AcctChangeExtDTO();
		acctChangeDTO.setAccountChangeId(Long.parseLong(accountChangeId));
		acctChangeDTO.setUserId(userId);
		Result result = eiuucManager.getVirtualChangeList(acctChangeDTO);
		if(null!=result&&"1".equals(result.isSuccess())){
			List<AcctChangeExtDTO> acctList=(List<AcctChangeExtDTO>)result.getData();
			if(null!=acctList&&acctList.size()>0){
					AcctChangeExtDTO acctChange =acctList.get(0);
					acct.setAccountChangeId(acctChange.getAccountChangeId());
					acct.setAcctId(acctChange.getAcctId());
					acct.setAcctItemId(acctChange.getAcctItemId());
					acct.setAcctTypeCode(acctChange.getAcctTypeCode());
					acct.setAcctTypeCodeDesc(acctChange.getAcctTypeCodeDesc());
					acct.setBusiType(acctChange.getBusiType());
					acct.setBusiTypeDesc(acctChange.getBusiTypeDesc());
					acct.setCreateTime(DateFormatUtils.format(acctChange.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
					acct.setIoType(acctChange.getIoType());// 01.进账 02出帐 
					acct.setIoAmount(Double.parseDouble(acctChange.getIoAmount().toString())/100);
					acct.setLastIoAmount(Double.parseDouble(acctChange.getLastIoAmount().toString())/100);
					acct.setUserId(userId);
					//关联信息
					String rltCode="";
					if(null!=acctChange.getRltCode()&&!"0".equals(acctChange.getRltCode())){
						rltCode=acctChange.getRltCode();
					}else{
						rltCode=acctChange.getRltId();
					}
					if(null!=rltCode){
						if("va1101".equals(acctChange.getBusiType())||"va1110".equals(acctChange.getBusiType())||"va1106".equals(acctChange.getBusiType())){
							acct.setRltCode("订单号"+rltCode);
						}else if("va1107".equals(acctChange.getBusiType())){
							acct.setRltCode("退换货单号"+rltCode);
						}else if("va1111".equals(acctChange.getBusiType())){
							acct.setRltCode("充值卡号"+acctChange.getRltId());
						}else if(!"0".equals(rltCode)){
							acct.setRltCode(rltCode);
						}else{
							acct.setRltCode("无");
						}
					}
					resultMap.put("result", true);
					resultMap.put("acctChangeInfo", acct);	
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.GetAccountChangeInfoFailed.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.GetAccountChangeInfoFailed.getErrorMsg());
				}
		}else{
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.GetAccountChangeInfoFailed.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.GetAccountChangeInfoFailed.getErrorMsg());
		}
	 
	return resultMap;
	}

}
