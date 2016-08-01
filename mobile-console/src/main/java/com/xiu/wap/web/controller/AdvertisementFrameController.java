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
import com.xiu.mobile.core.model.AdvertisementFrame;
import com.xiu.mobile.core.model.AdvertisementPlace;
import com.xiu.mobile.core.service.IAdvertisementFrameService;
import com.xiu.mobile.core.service.IAdvertisementPlaceService;
import com.xiu.show.core.common.constants.ShowConstant;
/**
 * @author: gaoyuan
 * @description:管理
 * @date: 2013-11-15
 */
@AuthRequired
@Controller
@RequestMapping(value = "/advFrame")
public class AdvertisementFrameController {

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdvertisementFrameController.class);
    
    @Autowired
    private IAdvertisementFrameService advertisementFrameService;
    
    
    @Autowired
    private IAdvertisementPlaceService advertisementPlaceService;
    
    /**
     * 广告帧查询
     * @param advPlaceId
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Long advPlaceId, Model model) {
    	List<AdvertisementFrame> advFramelist=new ArrayList<AdvertisementFrame>();
    	Map<Object,Object> map=new HashMap<Object, Object>(); 
    	map.put("advPlaceId", advPlaceId); //id
    	try {
    		advFramelist=advertisementFrameService.getAdvertisementFrameList(map);
    		AdvertisementPlace advPlace=advertisementPlaceService.getAdvertisementPlaceById(advPlaceId);
        	model.addAttribute("advPlace",advPlace);

		} catch (Exception e) {
			LOGGER.error("查询异常！",e);
		}
    	
    	model.addAttribute("advFramelist",advFramelist);
        return "pages/advertisementPlace/advFramelist";
    }
    
    
    /**
     * 添加
     * @param advertisementFrame
     * @param model
     */
    @RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String save(AdvertisementFrame advertisementFrame,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		 User user=AdminAuthInfoHolder.getUserAuthInfo();
    		 advertisementFrame.setCreate_by(user.getId());
    		
    		 int result=advertisementFrameService.save(advertisementFrame);//保存数据
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("保存成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("该广告帧名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("保存失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
            LOGGER.error("添加失败！", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
   
    /**
     * 管理--编辑
     * @param advertisementFrame
     * @param model
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String edit(AdvertisementFrame advertisementFrame,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		User user=AdminAuthInfoHolder.getUserAuthInfo();
    		advertisementFrame.setLast_update_by(user.getId());
    		 int result=advertisementFrameService.update(advertisementFrame);
    		 if(result==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
    			 json.setData("修改成功！");
    		 }else if(result==1){
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("名称已经存在！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
    			 json.setData("修改失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setData("系统发生异常！");
            LOGGER.error("修改失败！", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }
    
    /**
     * 管理--编辑
     * @param advertisementFrame
     * @param model
     */
    @RequestMapping(value = "editOrder", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String editOrder(Long id,Long beId,Integer type,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		User user=AdminAuthInfoHolder.getUserAuthInfo();
    		Map params=new HashMap();
    		params.put("id", id);
    		params.put("beId", beId);
    		params.put("type", type);
    		int result=advertisementFrameService.updateOrder(params);
    		if(result==0){
    			json.setScode(JsonPackageWrapper.S_OK);
    			json.setData("修改成功！");
    		}else{
    			json.setScode(JsonPackageWrapper.S_ERR);
    			json.setData("修改失败！");
    		}
    	} catch (Exception e) {
    		json.setScode(JsonPackageWrapper.S_ERR);
    		json.setData("系统发生异常！");
    		LOGGER.error("修改失败！", e);
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
    
    /**
     * 管理--删除(deleted: 0 未删除 , 1 已删除 )
     * @param id
     * @param model
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String delete(Long id,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		AdvertisementFrame advertisementFrame=new AdvertisementFrame();
    		advertisementFrame.setId(id);
    		advertisementFrame.setDeleteFlag(ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
            if(advertisementFrameService.delete(advertisementFrame)==0){
    			 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("删除成功！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("删除失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setData("系统发生异常！");
            LOGGER.error("删除失败！", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
    }

}
