package com.xiu.wap.web.controller;

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
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.mobile.core.model.AdvertisementPlace;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAdvertisementPlaceService;
import com.xiu.show.core.common.constants.ShowConstant;
/**
 * @author: gaoyuan
 * @description:广告位管理
 * @date: 2013-11-15
 */
@AuthRequired
@Controller
@RequestMapping(value = "/advertisementPlace")
public class AdvertisementPlaceController {
	

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdvertisementPlaceController.class);
    
    @Autowired
    private IAdvertisementPlaceService advertisementPlaceService;
    
    /**
     * 广告位管理--查询广告位
     * @param page
     * @param model
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Page<?> page, Model model) {
    	List<AdvertisementPlace> advPlacelist=new ArrayList<AdvertisementPlace>();
    	Map<Object,Object> map=new HashMap<Object, Object>(); 
    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
    	try {
    		advPlacelist=advertisementPlaceService.getAdvertisementPlaceList(map,page);
    	
		} catch (Exception e) {
			LOGGER.error("查询广告位异常！",e);
		}
    	model.addAttribute("advPlacelist",advPlacelist);
        return "pages/advertisementPlace/list";
    }
    
    /**
     * 跳转广告位添加页面--界面加载
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create() {
        return "pages/advertisementPlace/create";
    }
    
    /**
     * 添加广告位
     * @param advertisementPlace
     * @param model
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String save(AdvertisementPlace advertisementPlace,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 User user=AdminAuthInfoHolder.getUserAuthInfo();
    		 advertisementPlace.setCreate_by(user.getId());
    		
    		 int result=advertisementPlaceService.save(advertisementPlace);//保存数据
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("广告位保存成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("广告位code或者名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("广告位保存失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
            LOGGER.error("添加广告位失败！", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
   
    /**
     * 广告位管理--跳转广告位编辑页面
     * @param id
     * @param model
     */
    @RequestMapping(value = "toedit", method = RequestMethod.GET)
    public String toEdit(Long id,Model model) {
    	AdvertisementPlace advertisementPlace=null;
    	try {
    		advertisementPlace=advertisementPlaceService.getAdvertisementPlaceById(id);
		} catch (Exception e) {
			LOGGER.error("根据广告位ID查询广告位异常！",e);
		}
		model.addAttribute("advertisementPlace",advertisementPlace);
        return "pages/advertisementPlace/edit";
    }
    /**
     * 广告位管理--编辑广告位
     * @param advertisementPlace
     * @param model
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String edit(AdvertisementPlace advertisementPlace,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		User user=AdminAuthInfoHolder.getUserAuthInfo();
    		advertisementPlace.setLast_update_by(user.getId());
    		 int result=advertisementPlaceService.update(advertisementPlace);
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("广告位修改成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("广告位名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("广告位修改失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setData("系统发生异常！");
            LOGGER.error("广告位修改失败！", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 广告位管理--删除广告位(deleted: 0 未删除 , 1 已删除 )
     * @param id
     * @param model
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String delete(Long id,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		AdvertisementPlace advertisementPlace=new AdvertisementPlace();
    		advertisementPlace.setId(id);
    		advertisementPlace.setDeleted(ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
            if(advertisementPlaceService.delete(advertisementPlace)==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("删除广告位成功！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("删除广告位失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setData("系统发生异常！");
            LOGGER.error("删除广告位失败！", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }

}
