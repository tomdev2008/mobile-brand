package com.xiu.manager.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.xiu.manager.web.biz.MenuService;
import com.xiu.manager.web.biz.RoleMenuService;
import com.xiu.manager.web.biz.RoleService;
import com.xiu.manager.web.model.Menu;
import com.xiu.manager.web.model.Role;
import com.xiu.manager.web.model.RoleMenu;
import com.xiu.mobile.core.model.Page;
/**
 * @CLASS :com.xiu.recommend.web.controller.RoleController
 * @DESCRIPTION :
 * @AUTHOR :haijun.chen@xiu.com
 * @VERSION :v1.0
 * @DATE :2013-06-05 上午10:07:08
 */
@AuthRequired
@Controller
@RequestMapping(value = "/role")
public class RoleController {
	
	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(RoleController.class);
   
   @Autowired
   private RoleService roleService;
   
   @Autowired
   private MenuService menuService;
   
   @Autowired
   private RoleMenuService roleMenuService;
    
    /**
     * 角色管理--查询角色
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String role_name,Long status,Page<?> page, Model model) {
    	List<Role> rolelist=new ArrayList<Role>();
       	Map<Object,Object> map=new HashMap<Object, Object>(); 
        page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
       	try {
       		map.put("role_name", role_name);
       		map.put("status", status);
       		rolelist=roleService.getRoleListAll(map,page);
    		} catch (Exception e) {
    			e.printStackTrace();
    			LOGGER.error("查询角色异常！",e);
    		}
       	model.addAttribute("rolelist",rolelist);
       	model.addAttribute("role_name",role_name);
       	model.addAttribute("status",status);
           return "pages/role/list";
    }
   
    /**
     * 添加角色--界面加载
     * @param menu_name
     * @param menu_status
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(String role_name,Long status,Model model) {
    	List<Menu> menulist=new ArrayList<Menu>();
    	try {
    		menulist=menuService.getMenuList();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询菜单异常！",e);
		}
    	model.addAttribute("menulist",menulist);
    	model.addAttribute("role_name",role_name);
    	model.addAttribute("status",status);
        return "pages/role/create";
    }
    
    /**
     * 角色管理--添加角色--保存
     * @param menu
     * @param model
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String save(Role role,String role_menu,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 role.setCreateDt(new Date());
    		 int result=roleService.save(role,role_menu);//保存数据
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("角色保存成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("角色名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("角色保存失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("用户添加菜单失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 修改角色--界面加载
     * @param role_name
     * @param status
     * @param model
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Long id,String role_name,Long status,Model model) {
    	List<Menu> menulist=new ArrayList<Menu>();
    	List<RoleMenu> rolemenulist=new ArrayList<RoleMenu>();
    	Role role=new Role();
    	try {
    		role=roleService.getRolebyId(id);
    		menulist=menuService.getMenuList();
    		rolemenulist=roleMenuService.getRoleMenubyroleId(id);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询菜单异常！",e);
		}
    	model.addAttribute("role",role);
    	model.addAttribute("menulist",menulist);
    	model.addAttribute("rolemenulist",rolemenulist);
    	model.addAttribute("role_name",role_name);
    	model.addAttribute("status",status);
        return "pages/role/edit";
    }
    
    /**
     * 修改角色--保存
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String edit(Role role,String oldroleName,String role_menu,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 int result=roleService.update(role, role_menu, oldroleName);//保存数据
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("角色修改成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("角色名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("修改角色失败！");
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
     * 角色管理--修改角色的状态(status: 1 启用 , 0 停用 )
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
    		 if(roleService.updateRoleStatus(map)==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("修改角色状态成功！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("修改角色状态失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("修改角色状态失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
}
