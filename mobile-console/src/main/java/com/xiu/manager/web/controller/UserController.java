package com.xiu.manager.web.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import com.xiu.common.web.utils.EncryptUtils;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.manager.web.biz.RoleService;
import com.xiu.manager.web.biz.UserRoleService;
import com.xiu.manager.web.biz.UserService;
import com.xiu.manager.web.model.Role;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.model.UserRole;
import com.xiu.mobile.core.model.Page;
/**
 * @CLASS :com.xiu.recommend.web.controller.UserController
 * @DESCRIPTION :
 * @AUTHOR :haijun.chen@xiu.com
 * @VERSION :v1.0
 * @DATE :2013-06-05 上午10:07:08
 */
@AuthRequired
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(UserController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    /**
     * 用户管理--查询用户
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String user_name,Long status,Page<?> page, Model model) {
    	List<User> userlist=new ArrayList<User>();
    	Map<Object,Object> map=new HashMap<Object, Object>(); 
    	 page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
    	try {
    		map.put("user_name", user_name);
    		map.put("status", status);
    		userlist=userService.getUserListAll(map,page);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询用户异常！",e);
		}
    	model.addAttribute("userlist",userlist);
    	model.addAttribute("user_name",user_name);
    	model.addAttribute("status",status);
        return "pages/user/list";
    }
    
    /**
     * 添加用户--界面加载
     * @param menu_name
     * @param menu_status
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(String user_name,Long status,Model model) {
    	List<Role> rolelist=new ArrayList<Role>();
    	try {
    		rolelist=roleService.getRoleList();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询角色异常！",e);
		}
    	model.addAttribute("user_name",user_name);
    	model.addAttribute("status",status);
    	model.addAttribute("rolelist",rolelist);
        return "pages/user/create";
    }
    
    /**
     * 角色管理--添加角色--保存
     * @param menu
     * @param model
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String save(User user,String user_role,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 int result=userService.save(user, user_role);//保存数据
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("用户保存成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户保存失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("添加用户失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 用户管理--编辑用户
     * @param menu_name
     * @param menu_status
     * @param model
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Long id,String user_name,Long status,Model model) {
    	List<Role> rolelist=new ArrayList<Role>();
    	List<UserRole> userrolelist=new ArrayList<UserRole>();
    	User user=new User();
    	try {
    		rolelist=roleService.getRoleList();
    		user=userService.getUser(id);
    		userrolelist=userRoleService.getUserRolebyuserId(id);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询角色异常！",e);
		}
    	model.addAttribute("user_name",user_name);
    	model.addAttribute("status",status);
    	model.addAttribute("rolelist",rolelist);
    	model.addAttribute("user",user);
    	model.addAttribute("userrolelist",userrolelist);
        return "pages/user/edit";
    }
    
    /**
     * 用户管理--编辑角色--修改保存
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String edit(User user,String olduserName,String user_role,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 int result=userService.update(user, user_role, olduserName);
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("用户修改成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("用户修改失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("用户修改角色失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 修改用户密码--界面加载
     * @param id
     * @param user_name
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "editpwd", method = RequestMethod.GET)
    public String editpwd(Long id,String user_name,Long status,String param,Model model) {
    	User user=new User();
    	try {
    		user=userService.getUser(id);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询用户异常！",e);
		}
    	model.addAttribute("user_name",user_name);
    	model.addAttribute("status",status);
    	model.addAttribute("user",user);
    	model.addAttribute("param", param);
        return "pages/user/editpwd";
    }
    
    /**
     * 修改用户密码--密码保存
     */
    @RequestMapping(value = "editpwd", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String editpwd(User user,String newpassword,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		String password = EncryptUtils.getMD5(user.getPassword()); //密码加密
    		user.setPassword(password);
    		String newPasswordMd5 = EncryptUtils.getMD5(newpassword);
    		int result=userService.updatepwd(user,newPasswordMd5);
    		 if(result==0){
	   			 json.setScode(JsonPackageWrapper.S_OK);
	   			 json.setData("修改密码成功！");
	   		 }else if(result==1){
	   			 json.setScode(JsonPackageWrapper.S_ERR);
	   			 json.setData("您输入的旧密码不正确！！");
	   		 }else{
	   			 json.setScode(JsonPackageWrapper.S_ERR);
	   			 json.setData("修改密码失败！");
	   		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("用户修改密码异常！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 用户管理--修改用户的状态(status: 1 启用 , 0 停用 )
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
             if(userService.updateUserStatus(map)==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("修改用户状态成功！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("修改用户状态失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("修改用户状态失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 重置密码
     * 重置后密码为：Www.xiu.coM
     */
    @RequestMapping(value = "resetpwd", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String resetpwd(String id, Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		String defaultPassword = EncryptUtils.getMD5("Www.xiu.coM");	//默认密码
    		Map map = new HashMap();
    		map.put("id", id);
    		map.put("defaultPassword", defaultPassword);
    		int result=userService.resetpwd(map);
    		 if(result > 0){
	   			 json.setScode(JsonPackageWrapper.S_OK);
	   			 json.setData("重置密码成功！");
	   		 }else{
	   			 json.setScode(JsonPackageWrapper.S_ERR);
	   			 json.setData("重置密码失败！");
	   		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("重置密码异常！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
}
