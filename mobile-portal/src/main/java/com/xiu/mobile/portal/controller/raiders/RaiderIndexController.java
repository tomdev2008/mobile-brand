package com.xiu.mobile.portal.controller.raiders;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.EncryptUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.common.util.UnicodeUtils;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIRaidersManager;
import com.xiu.mobile.portal.model.AddressDataListVo;
import com.xiu.mobile.portal.model.AddressUpdateOutParam;
import com.xiu.mobile.portal.model.AddressUpdateVo;
import com.xiu.mobile.portal.model.AddressVo;
import com.xiu.mobile.portal.model.QueryAddressParamInParam;
import com.xiu.mobile.portal.model.raiders.RaidersUserInfo;
import com.xiu.mobile.portal.service.IAddressService;
import com.xiu.mobile.portal.service.IWithDrawService;
import com.xiu.mobile.portal.service.impl.AddressUpdateVoConvertor;
import com.xiu.uuc.facade.dto.VirtualAcctItemDTO;

/**
 * 零钱夺宝首页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/raiderIndex")
public class RaiderIndexController extends BaseController{
	private Logger logger=Logger.getLogger(RaiderIndexController.class);
	
	@Autowired
	private EIRaidersManager raidersManager;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private AddressUpdateVoConvertor addressUpdateVoConvertor;
	@Autowired
	private IWithDrawService withDrawServiceImpl;
	/**
	 * 最近中奖用户
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value="/getLastTenLotteryUsers",produces="text/html;charset=UTF-8")
	public String getLastTenLotteryUsers(HttpServletRequest request,String jsoncallback,
			Integer number){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(number==null){ //如果不传要查询的个数，则默认为10个
			number=10;
		}
		try{
			Map<String,Object> result=raidersManager.getLastTenLotteryUsers(number);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("零钱夺宝最近中奖纪录："+result);
				resultMap.put("lotteryList", result.get("lotteryList"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询零钱夺宝最近中间计量异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	
	/**
	 * 马上开奖
	 */
	@ResponseBody
    @RequestMapping(value="/findSoonLottery",produces="text/html;charset=UTF-8")
	public String findSoonLottery(HttpServletRequest request,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String number1=request.getParameter("number1");//马上开奖个数
		if(number1==null){
			number1="3"; 
		}
		try{
			Map<String,Object> result=raidersManager.findSoonLottery(number1);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("零钱夺宝马上开奖纪录："+result);
				resultMap.put("soonLotteryList", result.get("soonLotteryList"));
				resultMap.put("time", result.get("time"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询零钱夺宝马上开奖异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 全部活动
	 * 根据isHot来区分是推荐还是全部
	 */
	@ResponseBody
	@RequestMapping(value="/getHotGood",produces="text/html;charset=UTF-8")
	public String getHotGood(HttpServletRequest request,String jsoncallback,String isHot,
			String page,String pageSize,Integer orderSeq,Integer sort){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(sort==null){// 1升序，2降序。默认为升序
			sort=1; 
		}
		if(orderSeq==null){ //排序1人气，2最新，3进度，4总需人数，默认为1人气
			orderSeq=1;
		}
		if(page==null){
			page="1";
		}
		if(pageSize==null){
			pageSize="20";
		}
		try{
			Map<String,Object> result=raidersManager.getHotGood(isHot,page,pageSize,orderSeq,sort);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("零钱夺宝全部活动纪录："+result);
				resultMap.put("raidersList", result.get("raidersList"));
				resultMap.put("totalCount", result.get("totalCount"));
				resultMap.put("totalPage", result.get("totalPage"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询零钱夺宝全部活动异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 	我的夺宝记录
	 */
	@ResponseBody
	@RequestMapping(value="/getMyRaidersIndex",produces="text/html;charset=UTF-8")
	public String getMyRaidersIndex(HttpServletRequest request,String jsoncallback,
			String page,String pageSize,String isLottery){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String userIdStr=SessionUtil.getUserId(request);//用户ID
	//	String userIdStr="2012465815";
		Long userId=Long.valueOf(userIdStr);
		if(page==null){
			page="1";
		}
		if(pageSize==null){
			pageSize="20";
		}
		Integer lottery=0;
		if(isLottery==null){
			lottery=1;
		}else{
			lottery=Integer.valueOf(isLottery);
		}
		try{
			double totalMoney=0;
			VirtualAcctItemDTO virtualAcct=withDrawServiceImpl.getVirtualAccountInfo(userId);
			if(null!=virtualAcct){
				Long totMoney=virtualAcct.getDrawTotalMoney()+virtualAcct.getNoDrawTotalMoney();
				totalMoney=Double.parseDouble(totMoney.toString())/100;// 总金额(可提现可用金额+不可提现可用金额) 单位：分
			}
			Map<String,Object> result=raidersManager.getMyRaidersIndex(page, pageSize,userId,lottery);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("我的夺宝纪录："+result);
				RaidersUserInfo info=(RaidersUserInfo)result.get("userInfo");
				info.setTotalMoney(totalMoney);
				resultMap.put("raidersList", result.get("raidersList"));
				resultMap.put("totalCount", result.get("totalCount"));
				resultMap.put("totalPage", result.get("totalPage"));
				resultMap.put("userInfo", info);
				resultMap.put("time", result.get("time"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询我的夺宝记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 我的夺宝（他人夺宝）购买详情
	 */
	@ResponseBody
	@RequestMapping(value="/getRaidersBuyList",produces="text/html;charset=UTF-8")
	public String getRaidersBuyList(HttpServletRequest request,String jsoncallback,
			String raiderId,String userId,String isOther){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(raiderId)){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		Long raiderIds=Long.valueOf(raiderId);
		Long userIds=Long.valueOf(userId);
		try{
			Map<String,Object> result=raidersManager.getRaidersBuyList(raiderIds, userIds,isOther);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				resultMap.put("raidersList", result.get("raidersList"));
				resultMap.put("total", result.get("total"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询购买详情异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 他人夺宝记录
	 */
	@ResponseBody
	@RequestMapping(value="/findOtheRaidersIndex",produces="text/html;charset=UTF-8")
	public String findOtheRaidersIndex(HttpServletRequest request,String jsoncallback,
			String page,String pageSize,String isLottery,String userId,String userName,
			String userPicUrl){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(StringUtils.isBlank(userId) || StringUtils.isBlank(userName) || StringUtils.isBlank(userPicUrl)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		if(page==null){
			page="1";
		}
		if(pageSize==null){
			pageSize="20";
		}
		Integer lottery=0;
		if(StringUtils.isNotBlank(isLottery)){
			lottery=Integer.valueOf(isLottery);
		}
		try{
			Map<String,Object> result=raidersManager.findOtheRaidersIndex(page, pageSize, userId, lottery);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("他人的夺宝纪录："+result);
				resultMap.put("raidersList", result.get("raidersList"));
				resultMap.put("totalCount", result.get("totalCount"));
				resultMap.put("totalPage", result.get("totalPage"));
				resultMap.put("userId",userId);
				resultMap.put("userName", userName);
				resultMap.put("userPicUrl", userPicUrl);
				resultMap.put("time", result.get("time"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询他人的夺宝记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 根据participateId查询领取用户记录
	 */
	@ResponseBody
	@RequestMapping(value="/findRaiderSupperdList",produces="text/html;charset=UTF-8")
	public String findRaiderSupperdList(HttpServletRequest request,String jsoncallback,
			String participateId){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(participateId==null){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		try{
			Map<String,Object> result=raidersManager.findRaiderSupperdList(participateId);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("领取用户纪录："+result);
				resultMap.put("receiveList", result.get("receiveList"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("根据participateId查询领取用户记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 添加收货地址ID
	 */
	@ResponseBody
	@RequestMapping(value="/addRaiderLotteryAdd",produces="text/html;charset=UTF-8")
	public String addRaiderLotteryAdd(HttpServletRequest request,String addId,
			String raiderId,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String userId=SessionUtil.getUserId(request);//用户ID
	//	String userId="2012465815";
		//addId解密(AES)
		String aseKey=EncryptUtils.getAESKeySelf();
		addId=EncryptUtils.decryptByAES(addId, aseKey);
		if(StringUtils.isBlank(addId) || StringUtils.isBlank(raiderId)){
			resultMap=getResultMap(resultMap,false,ErrorCode.MissingParams);//缺少参数
			return JsonUtils.bean2jsonP(jsoncallback, resultMap);
		}
		String addressInfo=null;
		String realName=null;
		String addressPrefix=null;
		String mobile=null;
		//查询收货地址详情
		QueryAddressParamInParam queryAddressParam = new QueryAddressParamInParam();
		String tokenId = SessionUtil.getTokenId(request);
		queryAddressParam.setAddressId(addId);
		queryAddressParam.setTokenId(tokenId);
		try{
			AddressUpdateOutParam addressUpdateOut = addressService.getUpdateAddressNew(queryAddressParam);
			if (addressUpdateOut.isSuccess()) {
				AddressUpdateVo addressUpdateVo = addressUpdateVoConvertor.converter2AddressUpdateVo(addressUpdateOut);
				AddressDataListVo addressDataList = addressUpdateVo.getAddressDataList();
				AddressVo decodeUnicode = this.decodeAddress(addressDataList.getAddressDetail());
				addressInfo=decodeUnicode.getAddressInfo();
				realName=decodeUnicode.getRcverName();
				addressPrefix=decodeUnicode.getAddressPrefix();
				mobile=decodeUnicode.getMobile();
			}
			Map<String,Object> result=raidersManager.addRaiderLotteryAdd(raiderId, userId, addId,addressInfo,realName,addressPrefix,mobile);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("添加收货地址ID："+result);
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("添加收货地址ID信息异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 往期记录
	 * ，默认查询所有活动的记录
	 * 如果要查询购买活动的记录，则需要传活动ID
	 */
	@ResponseBody
	@RequestMapping(value="findPastRaider",produces="text/html;charset=UTF-8")
	public String findPastRaider(HttpServletRequest request,Long raiderId,String page,
			String pageSize,String jsoncallback){
		Map<String,Object> resultMap=new HashMap<String,Object>();
		if(page==null){
			page="1";
		}
		if(pageSize==null){
			pageSize="20";
		}
		try{
			Map<String,Object> result=raidersManager.findPastRaider(raiderId, page, pageSize);
			Boolean isSuccess=(Boolean)result.get("status");
			if(isSuccess){
				logger.info("零钱夺宝往期记录："+result);
				resultMap.put("lotteryList", result.get("lotteryList"));
				resultMap.put("totalCount", result.get("totalCount"));
				resultMap.put("totalPage", result.get("totalPage"));
				resultMap.put("result", true);
				resultMap.put("errorCode", ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.Success.getErrorMsg());
			}else{
				resultMap.put("result", false);
				resultMap.put("errorCode",result.get("errorCode") );
				resultMap.put("errorMsg",result.get("errorMsg"));
			}
		}catch(Exception e){
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询零钱夺宝往期记录异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
	/**
	 * 转换 Unicode 字符串 将收货人姓名和街道信息
	 * 
	 * @Title: decodeUnicode
	 * @param addressVo
	 * @author: yong
	 * @date: 2013-5-15上午09:47:28
	 */
	private AddressVo decodeAddress(AddressVo addressVo) {

		String rName = addressVo.getRcverName();
		rName = UnicodeUtils.decodeUnicode_try(rName);
		addressVo.setRcverName(rName);

		String addressInfo = addressVo.getAddressInfo();
		addressInfo = UnicodeUtils.decodeUnicode_try(addressInfo);
		addressVo.setAddressInfo(addressInfo);
		
		if(StringUtils.isBlank(addressVo.getMobile())) {
			//如果手机号为空，则改为空串
			addressVo.setMobile("");
		}
		
		return addressVo;
	}
}
