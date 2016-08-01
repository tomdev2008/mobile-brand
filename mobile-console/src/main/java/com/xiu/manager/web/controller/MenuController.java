package com.xiu.manager.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.xiu.manager.web.biz.MenuService;
import com.xiu.manager.web.model.Menu;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.model.Page;

/**
 * @CLASS :com.xiu.recommend.web.controller.MenuController
 * @DESCRIPTION :
 * @AUTHOR :haijun.chen@xiu.com
 * @VERSION :v1.0
 * @DATE :2013-05-29 上午10:07:08
 */
@AuthRequired
@Controller
@RequestMapping(value = "/menu")
public class MenuController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(MenuController.class);
    @Autowired
    private MenuService menuService;

    /**
     * 首页--左边菜单--内容
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_left_bot", method = RequestMethod.GET)
    public String menu_left_bot(HttpServletRequest request, Model model) {
        // 从session中获取菜单权限信息
        // List<Menu> menulist=(List<Menu>) request.getSession().getAttribute("menulist");
        List menulist = AdminSessionUtil.getMenu(request);
        if (menulist == null) {
            User user = AdminSessionUtil.getUser(request);
            menulist = menuService.getMenuListByUser(user);// 加载当前用户拥有的权限菜单
        }
        model.addAttribute("menulist", menulist);
        return "pages/menu/menu_left_bot";
    }

//    /**
//     * 菜单管理--查询菜单
//     * 
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "list", method = RequestMethod.GET)
//    public String list(String menu_name, Long menu_status, Page<?> page, Model model) {
//        List<Menu> menulist = new ArrayList<Menu>();
//        Map<Object, Object> map = new HashMap<Object, Object>();
//        page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
//        try {
//            // 加载符合条件的所有启用的菜单
//            map.put("menu_name", menu_name);
//            map.put("status", menu_status);
//            menulist = menuService.getMenuListAll(map, page);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("查询菜单异常！", e);
//        }
//        model.addAttribute("menulist", menulist);
//        model.addAttribute("menu_name", menu_name);
//        model.addAttribute("menu_status", menu_status);
//        return "pages/menu/list";
//    }

    /**
     * 添加菜单--界面加载
     * 
     * @param menu_name
     * @param menu_status
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(String menu_name, Long menu_status, Model model) {
        List<Menu> menulist = new ArrayList<Menu>();
        try {
            menulist = menuService.getOneMenuList();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("查询菜单异常！", e);
        }
        model.addAttribute("menu_name", menu_name);
        model.addAttribute("menu_status", menu_status);
        model.addAttribute("menulist", menulist);
        return "pages/menu/create";
    }


    /**
     * 登录成功后，跳转到主页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // String value=SpringResourceLocator.getConfiguration().getString("sms.identifying_code_expired_minute");
        return "pages/menu/index";
    }

    /**
     * 首页--主界面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_main", method = RequestMethod.GET)
    public String menu_main(Model model) {
        return "pages/menu/menu_main";
    }

    /**
     * 首页--主界面页头
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_top", method = RequestMethod.GET)
    public String menu_top(Model model) {
        // 从Session中获取用户信息
        User user = AdminAuthInfoHolder.getUserAuthInfo();
        ;
        model.addAttribute("user", user);
        return "pages/menu/menu_top";
    }

    /**
     * 首页--左边菜单
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_left", method = RequestMethod.GET)
    public String menu_left(Model model) {
        return "pages/menu/menu_left";
    }

    /**
     * 首页--左边菜单--页头
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_left_top", method = RequestMethod.GET)
    public String menu_left_top(Model model) {
        return "pages/menu/menu_left_top";
    }

    /**
     * 首页--左边菜单--收起（隐藏）
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_center", method = RequestMethod.GET)
    public String menu_center(Model model) {
        return "pages/menu/menu_center";
    }

    /**
     * 首页--右侧公共页脚
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "menu_bottom", method = RequestMethod.GET)
    public String menu_bottom(Model model) {
        return "pages/menu/menu_bottom";
    }
}
