package com.xiu.show.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.ShowUserModel;
import com.xiu.show.core.model.UserWhiteListModel;
import com.xiu.show.core.service.IShowUserManagerService;
import com.xiu.show.core.service.IShowUserWhiteListManagerService;

/**
 * 
* @Description: TODO(用户白名单) 
* @author haidong.luo@xiu.com
* @date 2015年12月22日 下午12:18:39 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/userWhiteList")
public class UserWhiteListController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShowCommentController.class);

    @Autowired
    private IShowUserWhiteListManagerService showUserWhiteListManagerService;
    @Autowired
    private IShowUserManagerService showUserManagerService;

	/**
	 * 查询白名单
	 * @param userId
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String userId,Page<?> page, Model model) {	 
		Map rmap = new HashMap();
	
		rmap.put("userId", userId);
		try{
		rmap=showUserWhiteListManagerService.getUserWhiteList(rmap,page);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("查询秀举报异常！",e);
		}
		List<UserWhiteListModel> cs=(List<UserWhiteListModel>)rmap.get("model");
		model.addAttribute("userId", userId);
		model.addAttribute("dateList", cs);
		return "pages/show/userWhiteList/list";
	}
	
	@RequestMapping(value="bfAdd",method=RequestMethod.GET)
	public String bfAdd(Model model){
		return "pages/show/userWhiteList/create";
	}
	
	
	/**
	 * 增加白名单
	 * @param request
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	    public String save(HttpServletRequest request, String userId, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
	    	Integer status=0;//操作状态
			Map rmap = new HashMap();
			User user=AdminSessionUtil.getUser(request);
			rmap.put("handleUserId", user.getId());
			rmap.put("handleUserName", user.getUsername());
			rmap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			try {
				if(null != userId||null != userId){
					rmap.put("userId", ObjectUtil.getLong(userId));
					rmap=showUserWhiteListManagerService.addUserWhiteList(rmap);
					Boolean isSuccess=(Boolean)rmap.get("status");
					if (isSuccess ) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("修改成功！");
						status=1;
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData(rmap.get("msg"));
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("修改失败！", e);
			}
			model.addAttribute("status", status);
	    	model.addAttribute("msg", json.getData());
	    	return "pages/show/userWhiteList/create";
	    }
	 
	    
	    /**
	     * 获取用户名称
	     * @param advFrameId
	     * @param advId
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "getUserName", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String getUserName(Long userId,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	String name ="";
	    	try {
	    		Map params=new HashMap();
	    		params.put("userId", userId);
	    		ShowUserModel timesStr=showUserManagerService.getShowUserInfo(params);
	    		if(timesStr!=null){
	    			name=timesStr.getPetName();
	    		}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, name);
	        return "";
	    }
	
}
