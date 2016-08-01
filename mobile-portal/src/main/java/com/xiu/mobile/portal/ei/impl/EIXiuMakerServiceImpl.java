package com.xiu.mobile.portal.ei.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiu.common.command.result.Result;
import com.xiu.maker.dointerface.dto.MakerSpreadInfoDTO;
import com.xiu.maker.dointerface.dto.MakerUserDTO;
import com.xiu.maker.dointerface.dto.MakerUserDetailDTO;
import com.xiu.maker.dointerface.dto.QueryIncomeDetailDTO;
import com.xiu.maker.dointerface.dto.QueryUserDetailDTO;
import com.xiu.maker.dointerface.service.XiuMakerFacade;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.constants.GlobalConstants;
import com.xiu.mobile.portal.ei.EIXiuMakerService;
import com.xiu.mobile.portal.service.IUserService;
import com.xiu.uuc.facade.UserManageFacade;

@Service("xiuMakerService")
public class EIXiuMakerServiceImpl implements EIXiuMakerService {
	
	private static Logger logger = Logger.getLogger(EIXiuMakerServiceImpl.class);

	@Resource(name="xiuMakerHessianService")
	XiuMakerFacade xiuMakerFacade;
	@Autowired
	private IUserService userService;
	@Autowired
	private com.xiu.mobile.portal.service.IRegisterService IRegisterService;
	
	
	@Override
	public Map<String, Object> queryMakerResourceByUserId(Long xiuUserId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			Result result = xiuMakerFacade.queryMakerResourceByUserId(xiuUserId);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("resourceVO", result.getModels().get("resourceVO"));
				returnMap.put("dtoLst", result.getDefaultModel());
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("获取创客资源信息返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("获取创客资源信息发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> queryMakerUserInfo(Long xiuUserId) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try{
			Result result = xiuMakerFacade.queryMakerUserVO(xiuUserId);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("makerUserInfo", result.getDefaultModel());
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("获取创客信息返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("获取创客信息发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> validSpreadUrl(String serialNum) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			Result result = xiuMakerFacade.validSpreadUrl(serialNum);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("realname", result.getModels().get("realname"));
				returnMap.put("jsUrl", result.getModels().get("jsUrl"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("校验链接有效性返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("校验链接有效性发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}

	@Override
	public Map<String, Object> spreadUser(String serialNum, String channel, String mobile) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		//验证
		try{
			Result result = xiuMakerFacade.validSpreadUrl(serialNum);
			if(!result.isSuccess()){
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
				logger.info("发展用户验证链接有效性返回结果：" + returnMap);
				return returnMap;
			}
		}catch(Exception e){
			logger.error("发展用户发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			return returnMap;
		}
		//如果用户存在
		if(userService.isLogonNameExist(mobile)){
			if("0".equals(channel)){ //创客直接注册
				returnMap.put("errorCode", ErrorCode.OldUserByMaker.getErrorCode());
				returnMap.put("errorMsg", ErrorCode.OldUserByMaker.getErrorMsg());
			}else{ //会员通过二维码注册
				returnMap.put("errorCode", ErrorCode.OldUserByUser.getErrorCode());
				returnMap.put("errorMsg", ErrorCode.OldUserByUser.getErrorMsg());
			}
			returnMap.put("result", false);
			return returnMap;
		}
		//注册
		Map<String, Object> registeMap = this.buildRegisteMap(mobile);
		Long xiuUserId = IRegisterService.registerForMMKT(registeMap);
		if(xiuUserId == null){
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.RegisterAccountFailed.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.RegisterAccountFailed.getErrorMsg());
			return returnMap;
		}
		
		MakerSpreadInfoDTO makerSpreadInfoDTO = new MakerSpreadInfoDTO();
		makerSpreadInfoDTO.setSerialNum(serialNum);
		makerSpreadInfoDTO.setChannel(channel);
		makerSpreadInfoDTO.setXiuUserId(xiuUserId);
		
		try{
			Result result = xiuMakerFacade.spreadUser(makerSpreadInfoDTO, mobile);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("resourceName", result.getDefaultModel());
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("发展用户返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("发展用户发生异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> makerCenter(Long makerUserId){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			Result result = xiuMakerFacade.makerCenter(makerUserId);
			logger.info("makerCenter----query-----:"+result.getModels().get("incomeList"));
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("makerCenterInfo", result.getDefaultModel());
				returnMap.put("incomeList", result.getModels().get("incomeList"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("Maker会员中心返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("获取会员中心信息异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> querySpreadUserLst(QueryUserDetailDTO queryDTO){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			Result result = xiuMakerFacade.queryUserDetailList(queryDTO);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				
				List<MakerUserDetailDTO> makerUserDeetailDTOLst = (List<MakerUserDetailDTO>) result.getDefaultModel();
				if(makerUserDeetailDTOLst == null || makerUserDeetailDTOLst.size() <= 0){
					returnMap.put("spreadUserIncomeInfo", makerUserDeetailDTOLst);
					
					return returnMap;
				}
				//如果是按创建时间排序，则要按月份分类反给前端
				if(StringUtils.isBlank(queryDTO.getSortParameter()) || "1".equals(queryDTO.getSortParameter())){
					List<Map<String, Object>> mapLst = new ArrayList<Map<String, Object>>();
					Map<String, Object> map = null;
					for (Iterator<MakerUserDetailDTO> iterator = makerUserDeetailDTOLst.iterator(); iterator.hasNext();) {
						MakerUserDetailDTO makerUserDetailDTO = iterator.next();
						//初始化
						if(map == null){
							map = new HashMap<String, Object>();
							map.put("date", makerUserDetailDTO.getCreateTime());
							map.put("data", new ArrayList<MakerUserDetailDTO>());
						}
						//如果该对象里的创建时间与map里的不至于，则重新归类
						if(!makerUserDetailDTO.getCreateTime().equals(map.get("date").toString())){
							mapLst.add(map);
							map = new HashMap<String, Object>();
							map.put("date", makerUserDetailDTO.getCreateTime());
							
							List<MakerUserDetailDTO> dtoLst = new ArrayList<MakerUserDetailDTO>();
							dtoLst.add(makerUserDetailDTO);
							map.put("data", dtoLst);
						}else{
							List<MakerUserDetailDTO> dtoLst = (List<MakerUserDetailDTO>) map.get("data");
							dtoLst.add(makerUserDetailDTO);
							map.put("data", dtoLst);
						}
						//到最有一个时，把map放进去
						if(!iterator.hasNext()){
							mapLst.add(map);
						}
					}
					returnMap.put("spreadUserIncomeInfo", mapLst);
				}else{
					returnMap.put("spreadUserIncomeInfo", makerUserDeetailDTOLst);
				}
				
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("会员明细返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("获取会员明细异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	public Map<String, Object> querySpreadUserDetail(long makerUserId
			, long xiuUserId, String xiuIdentity, int pageNo, int pageSize){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try{
			Result result = xiuMakerFacade.queryUserDetailInfo(makerUserId, xiuUserId, xiuIdentity, pageNo, pageSize);
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("spreadUserDetail", result.getDefaultModel());
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("会员详情返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("获取会员详情异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> queryIncomeLst(Long makerUserId, String source,
			String startTime, String endTime, String sortParameter,
			String sortRule, int pageNo, int pageSize) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		QueryIncomeDetailDTO queryIncomeDetailDTO = new QueryIncomeDetailDTO();
		queryIncomeDetailDTO.setMakerUserId(makerUserId);
		queryIncomeDetailDTO.setSource(source);
		queryIncomeDetailDTO.setStartTime(startTime);
		queryIncomeDetailDTO.setEndTime(endTime);
		queryIncomeDetailDTO.setSortParameter(sortParameter);
		queryIncomeDetailDTO.setSortRule(sortRule);
		queryIncomeDetailDTO.setPageNo(pageNo);
		queryIncomeDetailDTO.setPageSize(pageSize);
		try{
			Result result = xiuMakerFacade.queryIncomeDetailList(queryIncomeDetailDTO);
			if(result.isSuccess()){
				
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
				returnMap.put("incomeDetail", result.getModels());
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
			logger.info("收益明细返回结果：" + returnMap);
		}catch(Exception e){
			logger.error("获取收益明细列表异常", e);
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> joinMaker(MakerUserDTO makerUserDTO){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try{
			Result joinRes = xiuMakerFacade.joinMaker(makerUserDTO);
			if(joinRes.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("errorCode", "0");
				returnMap.put("errorMsg", "成功");
			}else{
				returnMap.put("result", false);
				Set<String> keySet = joinRes.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", joinRes.getErrorMessages().get(key));
				}
			}
		}catch (Exception e) {
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> inviteUser(long makerUserId) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try{
			Result result = xiuMakerFacade.getOrgInviteUrl(makerUserId);
			
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("realname", result.getModels().get("realname"));
				returnMap.put("orgUrl", result.getModels().get("orgUrl"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch (Exception e) {
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		
		return returnMap;
	}
	
	public Map<String, Object> queryMakerIncomeWelfare(long makerUserId){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		try{
			Result result = xiuMakerFacade.queryMakerWelfare(makerUserId);
			
			if(result.isSuccess()){
				returnMap.put("result", true);
				returnMap.put("current", result.getModels().get("current"));
				returnMap.put("levelRule", result.getModels().get("levelRule"));
			}else{
				returnMap.put("result", false);
				Set<String> keySet = result.getErrorMessages().keySet();
				for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
					String key = iterator.next();
					returnMap.put("errorCode", key);
					returnMap.put("errorMsg", result.getErrorMessages().get(key));
				}
			}
		}catch (Exception e) {
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		
		return returnMap;
	}
	
	private Map<String, Object> buildRegisteMap(String mobile){
		Map<String, Object> registeMap = new HashMap<String, Object>();
		registeMap.put("regType", "02");
		registeMap.put("logonName", mobile);
		registeMap.put("password", "");
		registeMap.put(GlobalConstants.KEY_REMOTE_IP, "127.0.0.1");
		registeMap.put("userSource", 150);
		
		return registeMap;
	}

}
