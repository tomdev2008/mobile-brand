package com.xiu.mobile.brand.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.xiu.mobile.brand.web.util.Constants;

/**
 * 
 * com.xiu.seo.web.controller.BaseController.java

 * @Description: TODO 定义了几个通用的参数<br>
 * 1. searchUrl search.xiu.com<br>
 * 2. listUrl list.xiu.com<br>
 * 3. brandUrl brand.xiu.com<br>

 * @author lvshuding   

 * @date 2014-5-13 上午11:01:10

 * @version V1.0
 */
public class BaseController {

    protected final static String ERROR = "error";
    protected final static String SUCCESS = "success";
    protected final static String LOGIN = "login";
    protected final static String INDEX = "index-search";



    /**
     * 获取列表页面的路径
     * 
     * @param req
     * @return
     */
    @ModelAttribute("listUrl")
    public String getListUrl(HttpServletRequest req) {
        return Constants.HTTP_SCHEMA + "://" + Constants.LIST_URL + "/";
    }

    /**
     * 获取品牌页面的路径
     * 
     * @param req
     * @return
     */
    @ModelAttribute("brandUrl")
    public String getBrandUrl(HttpServletRequest req) {
        return Constants.HTTP_SCHEMA + "://" + Constants.BRAND_URL + "/";
    }

    /**
     * 获取详情页url
     * 
     * @param req
     * @return
     */
    @ModelAttribute("itemUrl")
    public String getItemUrl(HttpServletRequest req) {
        return Constants.HTTP_SCHEMA + "://" + Constants.ITEM_URL + "/";
    }

}
