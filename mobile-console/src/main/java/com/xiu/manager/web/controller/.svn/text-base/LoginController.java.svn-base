package com.xiu.manager.web.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.common.command.result.Result;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.EncryptUtils;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.RSAUtil;
import com.xiu.manager.web.biz.MenuService;
import com.xiu.manager.web.biz.UserService;
import com.xiu.manager.web.model.Menu;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.usermanager.provider.UserManagerService;
import com.xiu.usermanager.provider.bean.LocalOperatorsDO;
import com.xiu.usermanager.provider.bean.PageDO;
import com.xiu.usermanager.provider.bean.SystemDO;

/**
 * @CLASS :com.xiu.recommend.web.controller.LoginController
 * @DESCRIPTION :
 * @AUTHOR :haijun.chen@xiu.com
 * @VERSION :v1.0
 * @DATE :2013-05-29 上午10:07:08
 */
@Controller
@RequestMapping(value = { "/login", "/", "" })
public class LoginController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;
    
	@Resource(name="userManagerService")
	private UserManagerService userManagerService;
    /**
     * 登录页面加载
     * 
     * @Title: index
     * @Description:
     * @param deliveryAddress
     * @return String
     * @throws
     */
    @RequestMapping(value = { "index", "/", "" }, method = RequestMethod.GET)
    public String index(Model model) {
        return "pages/login/index";
    }

//    /**
//     * 用户登录检验
//     * 
//     * @param loginName
//     * @param password
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "check", method = RequestMethod.POST, produces = "application/json", params = "format=json")
//    public String check(HttpServletRequest request, HttpServletResponse response, String loginName, String password,
//            Model model) {
//        JsonPackageWrapper json = new JsonPackageWrapper();
//        Map<Object, Object> uMap = new HashMap<Object, Object>();
//        List<Menu> menulist = new ArrayList<Menu>();
//        try {
//        	String md5Password = EncryptUtils.getMD5(password);
//            uMap = userService.check_loginUser(loginName, md5Password);
//            User user = (User) uMap.get("user");// 获得用户对象
//            if (user != null) {
//         
//                menulist = menuService.getMenuListByUser(user);// 加载当前用户拥有的权限菜单
//                userService.updateUserVersion(user.getId());
//                user = userService.getUser(user.getId());
//                AdminSessionUtil.setUser(response, user);
//                AdminSessionUtil.setMenuList(response, menulist);
//
//            }
//            json.setScode(uMap.get("sCode").toString());
//            json.setData(uMap.get("data"));
//        } catch (Exception e) {
//            json.setScode(JsonPackageWrapper.S_ERR);
//            json.setSmsg("系统发生异常！");
//            LOGGER.error("用户登录请求失败！：", e);
//        }
//        model.addAttribute(Constants.JSON_MODEL__DATA, json);
//        return "";
//    }
    
	  /**
	  * 用户登录检验
	  * 
	  * @param loginName
	  * @param password
	  * @param model
	  * @return
	  */
	 @RequestMapping(value = "check", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	 public String check(HttpServletRequest request, HttpServletResponse response, String loginName, String password,
	         Model model) {
//	 	password = restoreRSAPwd(password);//
	     JsonPackageWrapper json = new JsonPackageWrapper();
	     List<Menu> menulist = new ArrayList<Menu>();
//	     Map<PageDO, List<PageDO>> msalesPageMap = null;//key一级菜单，value为二级菜单
//	     List<PageDO> msalesPageList =null;//二级菜单
//	     List<Map<PageDO, List<PageDO>>> pageList = new ArrayList<Map<PageDO,List<PageDO>>>();//菜单目录
	     String loginCode="2";
	     String loginDesc="登录失败";
	     try {
	     	Result result = userManagerService.authenticate(loginName, password);
				if(!result.isSuccess()){
					loginCode="2";
					loginDesc=result.getResultCode();
				}else{
					LocalOperatorsDO opt = (LocalOperatorsDO) result.getDefaultModel();
					SystemDO msales = userManagerService.getSystemByName("madmin");
//					SystemDO msales = userManagerService.getSystemByName("maker");
					if(msales != null){
						List<PageDO> msalesPages  = userManagerService.getPagesBySystem(
								msales.getId(), opt.getId());
						if(msalesPages != null && msalesPages.size()>0){
							for(int i=0;i<msalesPages.size();i++){
								if(msalesPages.get(i).getParentId()==0){//判断是否是一级菜单, 父ID为0为一级菜单
									Menu firstMemu=new Menu();
									firstMemu.setMenuName(msalesPages.get(i).getPageName());
									firstMemu.setMenuUrl(msalesPages.get(i).getPageUrl());
//									msalesPageList = new ArrayList<PageDO>();
//									msalesPageMap = new HashMap<PageDO, List<PageDO>>();
									List<Menu> secondMenuList=new ArrayList<Menu>();
									for(int j=0;j<msalesPages.size();j++){
										if(msalesPages.get(j).getParentId().intValue() == msalesPages.get(i).getPageId().intValue()){
											Menu secondMenu=new Menu();
											secondMenu.setMenuName(msalesPages.get(j).getPageName());
											secondMenu.setMenuUrl(msalesPages.get(j).getPageUrl());
											secondMenuList.add(secondMenu);
//											msalesPageList.add(msalesPages.get(j));//分别把一级菜单对应的二级菜单存储到集合list
										}
									}
									firstMemu.setMlist(secondMenuList);
									menulist.add(firstMemu);
//									msalesPageMap.put(msalesPages.get(i), msalesPageList);
//									pageList.add(msalesPageMap);
								}
							}
							
						}
		             AdminSessionUtil.setUser(response, opt);
		             AdminSessionUtil.setMenuList(response, menulist);
		             List menu=  AdminSessionUtil.getMenu(request);
					loginCode="0";
					loginDesc="提示:登录成功！";
					}
				}
	         json.setScode(loginCode);
	         json.setData(loginDesc);
	     } catch (Exception e) {
	         json.setScode(JsonPackageWrapper.S_ERR);
	         json.setSmsg("系统发生异常！");
	         LOGGER.error("用户登录请求失败！：", e);
	     }
	     model.addAttribute(Constants.JSON_MODEL__DATA, json);
	     return "";
	 }
	 

		/**
		 * 对页面进行JS加密的密码进行解密
		 * 
		 * @param password JS加密的密码
		 * 
		 * @return originalPwd 用户请求的密码原文
		 */
		private String restoreRSAPwd(String password){

			String originalPwd = null;
			
			StringBuffer sb = new StringBuffer();
			
//			byte[] en_result = new BigInteger(password, 16).toByteArray();
			byte[] en_result =password.getBytes();
			byte[] de_result = null;
			try {
				de_result = RSAUtil.decrypt(RSAUtil.getKeyPair().getPrivate(), en_result);
				
				sb.append(new String(de_result));
				password = sb.reverse().toString();
				
				originalPwd = URLDecoder.decode(password, "UTF-8");
			} catch (Exception e) {
				LOGGER.error("密码还原失败!",e);
			}
			return originalPwd;
		}
    
}
