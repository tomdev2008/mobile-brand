package com.xiu.mobile.portal.controller;
import java.net.URLDecoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiu.feedback.bean.Complaint;
import com.xiu.feedback.hessianService.IComplaintHessianService;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ResponseUtil;
import com.xiu.mobile.portal.common.util.SessionUtil;
import com.xiu.mobile.portal.model.LoginResVo;
import com.xiu.mobile.portal.service.IVomitSayService;
/**
 * @author: wenxiaozhuo
 * @description:吐槽功能
 * @date: 2014-4-11
 */
@Controller
@RequestMapping("/vomitsay")
public class VomitSayController extends BaseController {
	private Logger logger = Logger.getLogger(VomitSayController.class);
	@Autowired
	private IVomitSayService vomitSayService;
	@Autowired
	private IComplaintHessianService complaintHessianService;
	
	/**
	 * 
	 * 用户吐槽接口
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/addvomitsayRemote")
	public String addVomitsay(String jsoncallback,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		String vomitRmks = request.getParameter("vomitSayVo.vomitRmks");
		String vomitContact = request.getParameter("vomitSayVo.vomitContact");
		String appVerName = request.getParameter("appVerName");//app版本名称
		String phoneBrand = request.getParameter("phoneBrand");//手机品牌
		String os = request.getParameter("os");//操作系统
		String osVer = request.getParameter("osVer");//操作系统版本
		try {
			
//			VomitSayVo vomitSayVo = new VomitSayVo();
//			vomitSayVo.setVomitContact(vomitContact);
//			vomitSayVo.setVomitRmks(vomitRmks);
//		
//			if (null != vomitSayVo && !StringUtils.isEmpty(vomitSayVo.getVomitContact()) && !StringUtils.isEmpty(vomitSayVo.getVomitRmks())) {
//				LoginResVo user = SessionUtil.getUser(request);
//				String username = null == user ? "" : URLDecoder.decode(user.getLogonName(), "utf-8");
//				vomitSayVo.setVomitUsername(username);
//				vomitSayVo.setVomitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//				vomitSayVo.setVomitDevInfo(JSON.toJSONString(getDeviceParams(request)));
//				vomitSayVo.setAppVerName(appVerName);
//				vomitSayVo.setPhoneBrand(phoneBrand);
//				vomitSayVo.setOs(os);
//				vomitSayVo.setOsVer(osVer);
//				if (vomitSayService.saveVomitsay(vomitSayVo) == 1) {
//					model.put("result", true);
//					model.put("errorCode", ErrorCode.Success.getErrorCode());
//					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
//				} else {
//					model.put("result", false);
//					model.put("errorCode", ErrorCode.VomitSayFailed.getErrorCode());
//					model.put("errorMsg", ErrorCode.VomitSayFailed.getErrorMsg());
//				}
//			} else {
//				model.put("result", false);
//				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
//				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
//			}
			//无线吐槽改成调用CSC接口
			if(!StringUtils.isEmpty(vomitContact) && !StringUtils.isEmpty(vomitRmks)) {
				//如果联系方式和吐槽内容不为空则添加吐槽信息
				Complaint complaint = new Complaint();
				complaint.setContact(vomitContact); //联系方式
				complaint.setContent(URLDecoder.decode(vomitRmks, "UTF-8"));	//吐槽内容
				complaint.setAppVersionName(appVerName);	//App版本
				complaint.setPhoneBrand(phoneBrand);	//手机品牌
				complaint.setOs(os);	//客户端系统
				complaint.setOsVersion(osVer);	//客户端系统版本
				complaint.setDeviceInfo(JSON.toJSONString(getDeviceParams(request)));	//设备信息
				complaint.setCreateTime(new Date());	//吐槽时间

				/*complaint.setDeviceNum(osVer);
				complaint.setSysName(os);
				complaint.setSysNum(appVerName);
				*/
				LoginResVo user = SessionUtil.getUser(request);
				String username = null == user ? "" : URLDecoder.decode(user.getLogonName(), "utf-8");
				complaint.setUserName(username);	//用户名
				boolean result = complaintHessianService.sendComplaint(complaint);
				if(result) {
					model.put("result", true);
					model.put("errorCode", ErrorCode.Success.getErrorCode());
					model.put("errorMsg", ErrorCode.Success.getErrorMsg());
				} else {
					model.put("result", false);
					model.put("errorCode", ErrorCode.VomitSayFailed.getErrorCode());
					model.put("errorMsg", ErrorCode.VomitSayFailed.getErrorMsg());
				}
			} else {
				model.put("result", false);
				model.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				model.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}

		} catch (Exception e) {
			model.put("result", false);
			model.put("errorCode", ErrorCode.SystemError.getErrorCode());
			model.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("用户吐槽发生异常：" + e.getMessage());
		}
		ResponseUtil.outPrintResult(response, JsonUtils.bean2jsonP(jsoncallback, model));
		return null;
	}

}
