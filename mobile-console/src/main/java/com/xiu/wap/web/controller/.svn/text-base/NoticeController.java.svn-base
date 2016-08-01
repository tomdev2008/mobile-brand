//package com.xiu.wap.web.controller;
//
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.ext.XLogger;
//import org.slf4j.ext.XLoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.xiu.common.web.annotation.AuthRequired;
//import com.xiu.common.web.contants.Constants;
//import com.xiu.common.web.utils.JsonPackageWrapper;
//import com.xiu.manager.web.model.User;
//import com.xiu.manager.web.utils.AdminAuthInfoHolder;
//import com.xiu.mobile.core.model.Advertisement;
//import com.xiu.mobile.core.model.Page;
//import com.xiu.mobile.core.service.IAdvertisementService;
///**
// * @author: gaoyuan
// * @description:公告管理
// * @date: 2013-11-16
// */
//@AuthRequired
//@Controller
//@RequestMapping(value = "/notice")
//public class NoticeController {	
//
//		//日志
//	    private static final XLogger LOGGER = XLoggerFactory.getXLogger(NoticeController.class);
//	    
//	    @Autowired
//	    private IAdvertisementService advertisementService;
//	    
//	    
//	    /**
//	     * 公告管理--查询公告
//	     * @param advPlaceId
//	     * @param advPlaceName
//	     * @param page
//	     * @param model
//	     */
//	    @RequestMapping(value = "list", method = RequestMethod.GET)
//	    public String list(Long advPlaceId,String advPlaceName,Page<?> page, Model model) {
//	    	List<Advertisement> advertisementlist=new ArrayList<Advertisement>();
//	    	Map<Object,Object> map=new HashMap<Object, Object>(); 
//	    	map.put("adv_place_id", advPlaceId);
//	    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
//	    	try {
//	    		advertisementlist=advertisementService.getAdvertisementList(map,page);
//		    	Advertisement advertisement=new Advertisement(); 
//		    	advertisement.setAdv_place_id(advPlaceId);
//		    	advertisement.setAdvPlaceName(URLDecoder.decode(advPlaceName, "UTF-8"));
//		    	model.addAttribute("advertisement",advertisement);
//			} catch (Exception e) {
//				LOGGER.error("查询公告异常！",e);
//			}
//	    	model.addAttribute("advertisementlist",advertisementlist);
//	        return "pages/notice/list";
//	    }
//	    
//	    /**
//	     * 跳转公告添加页面--界面加载
//	     * @param advPlaceId
//	     * @param advPlaceName
//	     * @param model
//	     */
//	    @RequestMapping(value = "create", method = RequestMethod.GET)
//	    public String create(Long advPlaceId,String advPlaceName, Model model) {
//	    	Advertisement advertisement=new Advertisement();
//	    	try{  	
//	    		advertisement.setAdv_place_id(advPlaceId);
//	    		advertisement.setAdvPlaceName(URLDecoder.decode(advPlaceName, "UTF-8"));
//	    	}catch(Exception e){
//	    		LOGGER.error(" 跳转公告添加页面异常！",e);
//	    	}
//	    	model.addAttribute("advertisement",advertisement);
//	        return "pages/notice/create";
//	    }
//	    
//	    /**
//	     * 添加公告
//	     * @param advertisement
//	     * @param model
//	     */
//	    @RequestMapping(value = "save" , method = RequestMethod.POST, produces = "application/json", params = "format=json")
//	    public String save(Advertisement advertisement,Model model) {
//	    	JsonPackageWrapper json = new JsonPackageWrapper();
//	    	try {
//	    		 User user=AdminAuthInfoHolder.getUserAuthInfo();
//	    		 advertisement.setCreate_by(user.getId());
//	    		 advertisement.setStart_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(advertisement.getStartTime()));
//	    		 advertisement.setEnd_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(advertisement.getEndTime()));
//	    		 if(advertisement.getEffect()!=null&&!"".equals(advertisement.getEffect())){
//	    			 advertisement.setEffective(1);//显示
//	    		 }else{
//	    			 advertisement.setEffective(0); //不显示
//	    		 }
//	    		 int result=advertisementService.save(advertisement);//保存数据
//	    		 
//	    		 if(result==0){
//	    			 json.setScode(JsonPackageWrapper.S_OK);
//	    			 json.setData("公告保存成功！");
//	    		 }else if(result==1){
//	    			 json.setScode(JsonPackageWrapper.S_ERR);
//	                 json.setData("在同一广告位,同一排期内不能存在相同的公告,请修改排期！");
//	    		 }else{
//	    			 json.setScode(JsonPackageWrapper.S_ERR);
//	                 json.setData("公告保存失败！");
//	    		 }
//			} catch (Exception e) {
//				 json.setScode(JsonPackageWrapper.S_ERR);
//                 json.setData("系统发生异常！");
//	             LOGGER.error("公告保存失败！", e);
//			}
//	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
//	    	return "";
//	    }
//	   
//	    /**
//	     * 公告管理--跳转公告编辑页面
//	     * @param id
//	     * @param advPlaceName
//	     * @param model
//	     */
//	    @RequestMapping(value = "toedit", method = RequestMethod.GET)
//	    public String toEdit(Long id,String advPlaceName ,Model model) {
//	    	Advertisement advertisement=null;
//	    	try {
//	    		advertisement=advertisementService.getAdvertisementById(id);
//	    		advertisement.setAdvPlaceName(URLDecoder.decode(advPlaceName, "UTF-8"));
//			} catch (Exception e) {
//				LOGGER.error("根据公告ID查询公告异常！",e);
//			}
//	    	model.addAttribute("advertisement",advertisement);
//	        return "pages/notice/edit";
//	    }
//	    /**
//	     * 公告管理--编辑公告
//	     * @param Advertisement
//	     * @param model
//	     */
//	    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
//	    public String edit(Advertisement advertisement,Model model) {
//	    	JsonPackageWrapper json = new JsonPackageWrapper();
//	    	try {
//	    		 User user=AdminAuthInfoHolder.getUserAuthInfo();
//	    		 advertisement.setLast_update_by(user.getId());
//	    		 advertisement.setStart_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(advertisement.getStartTime()));
//	    		 advertisement.setEnd_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(advertisement.getEndTime()));
//	    		 if(advertisement.getEffect()!=null){
//	    			 advertisement.setEffective(1); //1:表示显示
//	    		 }else{
//	    			 advertisement.setEffective(0); //0:表示不显示
//	    		 }
//	    		 int result=advertisementService.update(advertisement);
//	    		 if(result==0){
//	    			 json.setScode(JsonPackageWrapper.S_OK);
//	    			 json.setData("公告修改成功！");
//	    		 }else if(result==1){
//	    			 json.setScode(JsonPackageWrapper.S_ERR);
//	                 json.setData("在同一广告位,同一排期内不能存在相同的公告,请修改排期！");
//	    		 }else{
//	    			 json.setScode(JsonPackageWrapper.S_ERR);
//	                 json.setData("公告修改失败！");
//	    		 }
//			} catch (Exception e) {
//				 json.setScode(JsonPackageWrapper.S_OK);
//                 json.setData("系统发生异常！");
//	             LOGGER.error("公告修改失败！", e);
//			}
//	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
//	        return "";
//	    }
//	    
//	    /**
//	     * 公告管理--删除公告(deleted: 0 未删除 , 1 已删除 )
//	     * @param id
//	     * @param model
//	     */
//	    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
//	    public String delete(Long id,Model model) {
//	    	JsonPackageWrapper json = new JsonPackageWrapper();
//	    	try {
//	    		Advertisement advertisement=new Advertisement();
//	    		advertisement.setId(id);
//	            if(advertisementService.delete(advertisement)==0){
//	    			 json.setScode(JsonPackageWrapper.S_OK);
//	                 json.setData("删除公告成功！");
//	    		 }else{
//	    			 json.setScode(JsonPackageWrapper.S_ERR);
//	                 json.setData("删除公告失败！");
//	    		 }
//			} catch (Exception e) {
//				json.setScode(JsonPackageWrapper.S_ERR);
//	            json.setData("系统发生异常！");
//	            LOGGER.error("删除公告失败！：", e);
//			}
//	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
//	        return "";
//	    }
//}
