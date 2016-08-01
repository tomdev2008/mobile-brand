package com.xiu.manager.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xiu.manager.web.biz.UserGroupService;
import com.xiu.manager.web.biz.UserService;
import com.xiu.manager.web.biz.UserUserGroupService;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.model.UserGroup;
import com.xiu.manager.web.model.UserUserGroup;
import com.xiu.mobile.core.model.Page;
/**
 * @CLASS :com.xiu.recommend.web.controller.UserGroupController
 * @DESCRIPTION :
 * @AUTHOR :haijun.chen@xiu.com
 * @VERSION :v1.0
 * @DATE :2013-06-06 上午10:07:08
 */
@AuthRequired
@Controller
@RequestMapping(value = "/usergroup")
public class UserGroupController {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserGroupController.class);
    
    @Autowired
    private UserGroupService userGroupService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserUserGroupService userUserGroupService;
    
    /**
     * 用户组管理--查询用户用户组
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String usergroup_name,Long status,Page<?> page, Model model) {
    	Map<Object,Object> map=new HashMap<Object, Object>(); 
    	List<UserGroup> usergrouplist=new ArrayList<UserGroup>();
    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
    	try {
    		map.put("usergroup_name", usergroup_name);
    		map.put("status", status);
    		usergrouplist=userGroupService.getUserGroupListAll(map,page);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询用户组异常！",e);
		}
    	model.addAttribute("usergroup_name",usergroup_name);
        model.addAttribute("status",status);
        model.addAttribute("usergrouplist",usergrouplist);
    	return "pages/usergroup/list";
    }
    
    /**
     * 添加用户组--界面加载
     * @param menu_name
     * @param menu_status
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(String usergroup_name,Long status,Model model) {
    	List<User> userlist=new ArrayList<User>();
    	try {
    		userlist=userService.getUserList();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询用户异常！",e);
		}
    	model.addAttribute("userlist",userlist);
    	model.addAttribute("usergroup_name",usergroup_name);
    	model.addAttribute("status",status);
        return "pages/usergroup/create";
    }
    
    /**
     * 用户组管理--添加用户组--保存
     * @param menu
     * @param model
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String save(UserGroup usergroup,String user_usergroup,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 int result=userGroupService.save(usergroup, user_usergroup);
             if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("用户组保存成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户组名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户组保存失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("用户添加用户组失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 用户组管理--编辑用户组
     * @param menu_name
     * @param menu_status
     * @param model
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Long id,String usergroup_name,Long status,Model model) {
    	UserGroup usergroup=new UserGroup();
    	List<User> userlist=new ArrayList<User>();
    	List<UserUserGroup> uuglist=new ArrayList<UserUserGroup>();
    	try {
    		usergroup=userGroupService.getUserGroup(id);
    		userlist=userService.getUserList();
    		uuglist=userUserGroupService.getUserUserGroupbyuserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询用户组异常！",e);
		}
    	model.addAttribute("usergroup_name",usergroup_name);
    	model.addAttribute("status",status);
    	model.addAttribute("usergroup",usergroup);
    	model.addAttribute("userlist",userlist);
    	model.addAttribute("uuglist", uuglist);
        return "pages/usergroup/edit";
    }
    
    /**
     * 用户组管理--编辑用户组--修改保存
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String edit(UserGroup usergroup,String user_usergroup,String olduserGroupName,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 int result=userGroupService.update(usergroup, user_usergroup, olduserGroupName);
             if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("用户组修改成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户组名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户组修改失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("用户修改用户组失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 用户组管理--修改用户组的状态(status: 1 启用 , 0 停用 )
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "updatestatus", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String updatestatus(Long id,Long status,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	Map<Object,Object> map=new HashMap<Object, Object>();
    	try {
    		 map.put("id", id);
    		 map.put("status", status);
             if(userGroupService.updateUserGroupStatus(map)==0){
            	 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("修改用户组状态成功！"); 
             }else{
            	 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("修改用户组状态失败！"); 
             }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("修改用户组状态失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
}
