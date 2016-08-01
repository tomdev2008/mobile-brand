package com.xiu.mobile.portal.controller.maker;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.maker.dointerface.dto.MakerUserDTO;
import com.xiu.maker.dointerface.dto.QueryUserDetailDTO;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.ei.EIXiuMakerService;

@Controller
@RequestMapping("/maker")
public class MakerUserController extends BaseController {

	private static Logger logger = Logger.getLogger(MakerUserController.class);
	
	@Autowired
	EIXiuMakerService eiXiuMakerService;
	
	/**
	 * 获取创客下的有效资源
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryMakerResByUser", produces = "text/html;charset=UTF-8")
	public String queryMakerResByUser(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("获取创客下的有效资源，前端提交参数：params=" + request.getQueryString());
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		Map<String, Object> returnMap = eiXiuMakerService.queryMakerResourceByUserId(Long.parseLong(xiuUserId));
		
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 获取创客信息
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryMakerUserInfo", produces = "text/html;charset=UTF-8")
	public String queryMakerUserInfo(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("获取创客信息，前端提交参数：params=" + request.getQueryString());
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = SessionUtil.getUserId(request);
		Map<String, Object> returnMap = eiXiuMakerService.queryMakerUserInfo(Long.parseLong(xiuUserId));
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 校验链接有效性
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/validSpreadUrl", produces = "text/html;charset=UTF-8")
	public String validSpreadUrl(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("校验链接有效性，前端提交参数：params=" + request.getQueryString());
		String serialNum = request.getParameter("serialNum");//资源序列号
		if(StringUtils.isBlank(serialNum)){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		returnMap = eiXiuMakerService.validSpreadUrl(serialNum);
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 发展用户
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/spreadUser", produces = "text/html;charset=UTF-8")
	public String spreadUser(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		logger.info("发展用户，前端提交参数：params=" + request.getQueryString());
		
		String serialNum = request.getParameter("serialNum"); //资源序列号
		String channel = request.getParameter("channel"); //链接推广方式
		String mobile = request.getParameter("mobile"); //用户注册手机号
		
		if(StringUtils.isBlank(channel) 
				|| StringUtils.isBlank(mobile)
				|| StringUtils.isBlank(serialNum)){
			returnMap.put("result", false);
			returnMap.put("errorCode", "-1");
			returnMap.put("errorMsg", "提交参数不全");
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		returnMap = eiXiuMakerService.spreadUser(serialNum, channel, mobile);
			
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 创客中心
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/makerCenter", produces = "text/html;charset=UTF-8")
	public String makerCenter(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String makerUserId = SessionUtil.getUserId(request);
		Map<String, Object> returnMap = eiXiuMakerService.makerCenter(Long.parseLong(makerUserId));
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 会员明细列表
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querySpreadUserLst", produces = "text/html;charset=UTF-8")
	public String querySpreadUserLst(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("查询会员明细列表，前端提交参数：" + request.getQueryString());
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		
		String status = request.getParameter("status"); //会员状态：0：未激活，1：未下单，2：无效
		String createTimeStart = request.getParameter("createTimeStart"); //发展会员时间
		String createTimeEnd = request.getParameter("createTimeEnd"); //发展会员时间
		String sortParameter = request.getParameter("sortParameter"); //排序字段  1：激活时间，2：贡献收益 
		String sortRule = request.getParameter("sortRule"); //sortRule：排序顺序（1：升序，2：降序）
		String pageNo = request.getParameter("pageNo"); // 页码
		String pageSize = request.getParameter("pageSize"); // pageSize：每页显示数量
		String xiuIdentity = request.getParameter("xiuIdentity"); // 明细类型，1：会员，2：代理
		
		String makerUserId = SessionUtil.getUserId(request);
		
		QueryUserDetailDTO queryDTO = new QueryUserDetailDTO();
		queryDTO.setMakerUserId(Long.parseLong(makerUserId));
		queryDTO.setStatus(status);
		queryDTO.setCreateTimeStart(createTimeStart);
		if(StringUtils.isNotBlank(createTimeEnd)){
			queryDTO.setCreateTimeEnd(createTimeEnd + " 23:59:59");
		}
		queryDTO.setSortParameter(sortParameter);
		queryDTO.setSortRule(sortRule);
		queryDTO.setPageNo(StringUtils.isBlank(pageNo) ? 1 : Integer.parseInt(pageNo));
		queryDTO.setPageSize(StringUtils.isBlank(pageNo) ? 10 : Integer.parseInt(pageSize)); //默认10条
		queryDTO.setXiuIdentity(xiuIdentity);
		
		Map<String, Object> returnMap = eiXiuMakerService.querySpreadUserLst(queryDTO);
		
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 会员详情
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/querySpreadUserDetail", produces = "text/html;charset=UTF-8")
	public String querySpreadUserDetail(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("查询会员详情，前端提交参数：" + request.getQueryString());
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		String xiuUserId = request.getParameter("xiuUserId"); //会员ID
		int pageNo =  StringUtils.isBlank(request.getParameter("pageNo")) ? 1 : Integer.parseInt(request.getParameter("pageNo")); //页码
		int pageSize =  StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : Integer.parseInt(request.getParameter("pageSize")); //每页显示数量
		
		Long makerUserId = Long.parseLong(SessionUtil.getUserId(request));
		//默认查会员
		String xiuIdentity = "1";
		
		Map<String, Object> returnMap = eiXiuMakerService.querySpreadUserDetail(makerUserId, Long.parseLong(xiuUserId), xiuIdentity, pageNo, pageSize);
		
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 收益明细
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryIncomeLst", produces = "text/html;charset=UTF-8")
	public String queryIncomeLst(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("查询收益明细列表，前端提交参数：" + request.getQueryString());
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
		Long makerUserId = Long.parseLong(SessionUtil.getUserId(request));
		
		String sortParameter = request.getParameter("sortParameter"); //排序字段  1：金额，2：入账时间
		String sortRule = request.getParameter("sortRule"); //sortRule：排序顺序（1：升序，2：降序）
		String source = request.getParameter("source"); //奖励类型  1：会员激活，2：会员消费，4：综合奖励
		String startTime = request.getParameter("startTime"); //收益创建开始时间
		String endTime = request.getParameter("endTime"); //收益创建结束时间
		int pageNo =  StringUtils.isBlank(request.getParameter("pageNo")) ? 1 : Integer.parseInt(request.getParameter("pageNo")); //页码
		int pageSize =  StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : Integer.parseInt(request.getParameter("pageSize")); //每页显示数量
		
		if(StringUtils.isNotBlank(endTime)){
			endTime += " 23:59:59";
		}
		
		Map<String, Object> returnMap = eiXiuMakerService.queryIncomeLst(makerUserId, source, startTime, endTime, sortParameter, sortRule, pageNo, pageSize);
	
		return JsonUtils.bean2jsonP(jsoncallback, returnMap);
	}
	
	/**
	 * 注册创客
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/joinMaker", produces = "text/html;charset=UTF-8")
	public String joinMaker(HttpServletRequest request,HttpServletResponse response,String jsoncallback){
		
		logger.info("申请成为创客，前端提交参数：" + request.getQueryString());
		
		String makerUserName = request.getParameter("makerUserName"); // 创客姓名
		String sex = request.getParameter("sex"); // 性别 0：女，1：男
		String mobile = request.getParameter("mobile"); //手机号码
		String job = request.getParameter("job"); //行业
		String recommended = request.getParameter("recommended"); //推荐人
		String recommendedUserId = request.getParameter("recommendedUserId");//推荐人UserId
		String sign = request.getParameter("sign");//签名
		
		
		MakerUserDTO makerUserDTO = new MakerUserDTO();
		makerUserDTO.setRealname(makerUserName);
		makerUserDTO.setSex(sex);
		makerUserDTO.setMobile(mobile);
		makerUserDTO.setJob(job);
		if(StringUtils.isNotBlank(recommendedUserId)){
			makerUserDTO.setRecommendedUserId(Long.parseLong(recommendedUserId));
		}
		makerUserDTO.setSign(sign);
		makerUserDTO.setRecommended(recommended);
		
		Map<String, Object> returnMap = eiXiuMakerService.joinMaker(makerUserDTO);
		
		return JsonUtils.bean2jsonP(jsoncallback, returnMap); 
	}

	@ResponseBody
	@RequestMapping(value="/inviteUser", produces = "text/html;charset=UTF-8")
	public String inviteUser(HttpServletRequest request,HttpServletResponse response,
			String jsoncallback){
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
	
		Map<String,Object> result =  eiXiuMakerService.inviteUser(Long.parseLong(
				SessionUtil.getUserId(request)));
		
		logger.info("邀请创客返回结果：" + result);
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 是否是创客
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/isMaker", produces = "text/html;charset=UTF-8")
	public String isMaker(HttpServletRequest request,HttpServletResponse response,
			String jsoncallback){
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
	
		Map<String,Object> result =  eiXiuMakerService.queryMakerUserInfo(Long.parseLong(
				SessionUtil.getUserId(request)));
		
		logger.info("是否创客返回结果：" + result);
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 创客福利
	 * @param request
	 * @param response
	 * @param jsoncallback
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/welfare", produces = "text/html;charset=UTF-8")
	public String welfare(HttpServletRequest request,HttpServletResponse response,
			String jsoncallback){
		
		String msg = isLogin(request, jsoncallback);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
	
		Map<String,Object> result =  eiXiuMakerService.queryMakerIncomeWelfare(Long.parseLong(
				SessionUtil.getUserId(request)));
		
		logger.info("创客福利返回结果：" + result);
		
		return JsonUtils.bean2jsonP(jsoncallback, result);
	}
	
	/**
	 * 校验登陆
	 * @param request
	 * @param jsoncallback
	 * @return
	 */
	private String isLogin(HttpServletRequest request, String jsoncallback){
		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			if(!checkLogin(request)){
				returnMap.put("result", false);
				returnMap.put("errorCode", ErrorCode.SessionTimeOut.getErrorCode());
				returnMap.put("errorMsg", ErrorCode.SessionTimeOut.getErrorMsg());
			}
		} catch (Exception e) {
			returnMap.put("result", false);
			returnMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			returnMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		if(returnMap.size() > 0){
			return JsonUtils.bean2jsonP(jsoncallback, returnMap);
		}
		return null;
	}
	
}
