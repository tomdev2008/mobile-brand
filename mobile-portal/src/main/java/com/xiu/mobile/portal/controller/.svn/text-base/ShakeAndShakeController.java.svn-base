package com.xiu.mobile.portal.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiu.common.command.result.Result;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.portal.common.util.AppVersionUtils;
import com.xiu.mobile.portal.model.AppVo;
import com.xiu.mobile.sales.dointerface.vo.PageView;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.mobile.core.exception.EIBusinessException;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.ShakeWinDesc;
import com.xiu.mobile.portal.model.ShakeWinInfo;
import com.xiu.mobile.portal.service.ILoginService;
import com.xiu.mobile.portal.service.IShakeAndShakeService;

/**
 * 
 * @ClassName
 * @Description 
 * @author chenlinyu
 * @date 2014年12月3日 下午3:50:01
 */
@Controller
@RequestMapping("/shakeAndShake")
public class ShakeAndShakeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ShakeAndShakeController.class);

	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IShakeAndShakeService shakeAndShakeService;
	
	/**
	 * 点击摇一摇页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="goShakeAndShake",produces = "text/html;charset=UTF-8")
	public String getShakeAndShakeInfo(String jsoncallback,HttpServletRequest request,HttpServletResponse response){
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String remainShakeTime=null;
		List<ShakeWinDesc> list=new ArrayList<ShakeWinDesc>();
		
		try {
			String userId=SessionUtil.getUserId(request);
			String userName=SessionUtil.getUserName(request);
			
			// 获取用还剩摇奖次数和用户中奖结果
			Map<String, Object> map =shakeAndShakeService.getUserCanShakeTime(userId,userName);

			
			model.put("result", true);	
			model.put("errorCode", ErrorCode.Success.getErrorCode());
			model.put("errorMsg", ErrorCode.Success.getErrorMsg());	
			model.put("remainShakeTime", map.get("leftNum")); // 用户剩余摇奖次数
			model.put("otherWinList", map.get("userRockReward")); // 用户获奖结果信息
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		logger.info("获取摇一摇信息返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));		
		return null;
		
	}
	
	/**
	 * 用手机进行摇一摇
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="mobileShake",produces = "text/html;charset=UTF-8")
	public String mobileShake(String jsoncallback,HttpServletRequest request,HttpServletResponse response){
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		// 存储返回结果值
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		Result result = null;
		List<ShakeWinDesc> list=new ArrayList<ShakeWinDesc>();
		
		try {
			String userId=SessionUtil.getUserId(request);
			String userName=SessionUtil.getUserName(request);
			//String remainShakeTime=shakeAndShakeService.getUserCanShakeTime(userId,userName);
			try {

				AppVo appVo = AppVersionUtils.parseAppVo(request);
				// 用户可以进行摇奖
				result = shakeAndShakeService.mobileShake(userId, userName);
				if(result.isSuccess()){

					model.put("result", true);
					model.put("remainShakeTime", result.getModels().get("remainShakeTime"));
					model.put("shakeWinInfo", dealRockWinDescForVersions(appVo, result.getModels().get("shakeWinInfo")));
					model.put("otherWinList", result.getModels().get("otherWinList"));

				}else{
					model.put("result", false);
					model.put("remainShakeTime", result.getModels().get("remainShakeTime"));
					if("252".equals(result.getResultCode())){
						model.put("errorCode", ErrorCode.ShakeNoTime.getErrorCode());
						model.put("errorMsg", ErrorCode.ShakeNoTime.getErrorMsg());
					}else{
						model.put("errorCode", ErrorCode.ShakeAndShakeFailed.getErrorCode());
						model.put("errorMsg", getFailedMessage());
					}
				}
			} catch (EIBusinessException e) {
				model.put("result", false);
				model.put("errorCode", ErrorCode.ShakeAndShakeFailed.getErrorCode());
				model.put("errorMsg", getFailedMessage());
				e.printStackTrace();
			}	
			
			/*if("0".equals(remainShakeTime)){
				model.put("remainShakeTime", remainShakeTime); // 用户剩余摇奖次数		
			}else{
				//String remainShakeTime1=shakeAndShakeService.getUserCanShakeTime(userId,userName);
				model.put("remainShakeTime", remainShakeTime1); // 用户剩余摇奖次数	
			}*/
		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}
		
		logger.info("用户进行摇一摇后信息返回结果：" + JSON.toJSONString(model));
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
		return null;
	}

	/**
	 * 摇一摇获取用户的中奖信息
	 * @param jsoncallback
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value="getWinInfo",produces = "text/html;charset=UTF-8")
	public String getWinInfo(String jsoncallback,HttpServletRequest request,HttpServletResponse response){
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");

		Map<String, Object> model = new LinkedHashMap<String, Object>();

		String userId=SessionUtil.getUserId(request);

		try {
			PageView<ShakeWinDesc> winDescList = shakeAndShakeService.queryShakeWinList(
					Long.parseLong(userId), currentPage, pageSize);

			model.put("result", true);
			model.put("pageIndex", winDescList.getCurrentPage());
			model.put("pageSize", winDescList.getPageSize());
			model.put("total", winDescList.getTotalRecord());
			model.put("winRecords",winDescList.getRecords());

		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}


		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
		return null;
	}

	/**
	 * 摇一摇获取另一次摇一摇机会
	 * @param jsoncallback
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value="getAnotherChance",produces = "text/html;charset=UTF-8")
	public String getAnotherChance(String jsoncallback,HttpServletRequest request,HttpServletResponse response){

		Map<String, Object> model = new LinkedHashMap<String, Object>();

		String userId=SessionUtil.getUserId(request);
		String userName=SessionUtil.getUserName(request);
		try {

			Result result = shakeAndShakeService.getAnotherRockChance(Long.parseLong(userId), userName);

			if(result.isSuccess()){

				model.put("result", true);
				model.put("remainShakeTime", result.getModels().get("remainShakeTime"));
			}else {
				model.put("result", false);
				model.put("errorCode", result.getResultCode());
				model.put("errorMsg", result.getErrorMessages());
			}

		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
		}


		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback,model));
		return null;
	}
	
	// 随机获取摇不中的信息提示
	public String getFailedMessage(){
		String[] str =new String[]{"木有中奖，洗洗手再来吧！","姿势不对，再来一次！",
				"表灰心，再来一次嘛！","摇一摇，是一种生活态度，是一份坚持。"};
		int k=str.length;

		Random random = new Random();
		int i = random.nextInt(k)+0;
		String mes=str[i];
		return mes;
		
	}

	private Object dealRockWinDescForVersions(AppVo appVo, Object winDesc){

		if(appVo == null || appVo.getAppVersion() == null || "3.8.0".compareTo(appVo.getAppVersion()) > 0){
			Map<String, Object> winInfo = (Map<String, Object>)winDesc;

			if("1".equals(winInfo.get("cardType"))){
				winInfo.put("cardType", "积分");
				winInfo.put("validityDate", "5年内有效");
			}else{
				winInfo.put("cardType", "优惠券");
				winInfo.put("cardDesc", winInfo.get("cardDesc") + "元");
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 30);
				winInfo.put("validityDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(cal.getTime()) + "前有效");
			}

			return winInfo;
		}

		return winDesc;
	}
}
