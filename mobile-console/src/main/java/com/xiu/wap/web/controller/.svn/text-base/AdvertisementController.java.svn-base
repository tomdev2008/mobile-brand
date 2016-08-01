package com.xiu.wap.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.model.Advertisement;
import com.xiu.mobile.core.model.AdvertisementFrame;
import com.xiu.mobile.core.model.AdvertisementPlace;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAdvertisementFrameService;
import com.xiu.mobile.core.service.IAdvertisementPlaceService;
import com.xiu.mobile.core.service.IAdvertisementService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.show.core.common.constants.ShowConstant;
/**
 * 
* @Description: TODO(广告) 
* @author haidong.luo@xiu.com
* @date 2015年9月9日 下午5:02:02 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/adv")
public class AdvertisementController {	

		//日志
	    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AdvertisementController.class);
	    
	    @Autowired
	    private IAdvertisementService advertisementService;
	    @Autowired
	    private IAdvertisementFrameService advertisementFrameService;
	    @Autowired
	    private IAdvertisementPlaceService advertisementPlaceService;
	    
	    @Value("${upload_pic_path}")
	    private String upload_pic_path;
	    
	    @Value("${images_domain_name}")
	    private String images_domain_name;
	    
	    /**
	     * 广告管理--查询广告
	     * @param advPlaceId
	     * @param advPlaceName
	     * @param page
	     * @param model
	     */
	    @RequestMapping(value = "list", method = RequestMethod.GET)
	    public String list(String id,String startDate,String endDate,
				String picStatus,String deleteFlag,String linkType,String creatorName,
				Long advPlaceId,String advPlaceName,String status,Page<?> page, Model model) {
	    	List<Advertisement> advertisementlist=new ArrayList<Advertisement>();
	    	Map<Object,Object> rmap=new HashMap<Object, Object>(); 
	    	rmap.put("advPlaceId", advPlaceId);
			rmap.put("id", id);
			rmap.put("startDate", startDate);
			rmap.put("endDate", endDate);
			rmap.put("status", picStatus);
			rmap.put("deleteFlag", deleteFlag);
			rmap.put("linkType", linkType); 
			rmap.put("creatorName", creatorName);
			rmap.put("status", status);
	    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
	    	
    		Map result =advertisementService.getAdvertisementList(rmap,page);
    		advertisementlist=(List<Advertisement>)result.get("resultInfo");
    		
	    	Advertisement advertisement=new Advertisement(); 
	    	advertisement.setAdvPlaceId(advPlaceId);
	    	model.addAttribute("advertisement",advertisement);
	    	
			model.addAttribute("id", id);
			model.addAttribute("status", status);
			model.addAttribute("advPlaceId", advPlaceId);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("picStatus", picStatus);
			model.addAttribute("deleteFlag", deleteFlag);
			model.addAttribute("linkType", linkType);
			model.addAttribute("creatorName", creatorName);
	    	model.addAttribute("advertisementlist",advertisementlist);
	    	
	    	List<AdvertisementPlace> advPlaces=advertisementPlaceService.getAdvertisementPlaceListAll(null);
	    	model.addAttribute("advPlaces",advPlaces);
	    	
	        return "pages/advertisement/list";
	    }
	    
	    /**
	     * 跳转广告添加页面--界面加载
	     */ 
	    @RequestMapping(value = "bfAdd", method = RequestMethod.GET)
	    public String bfAdd( Model model) {
	    	List<AdvertisementPlace> advertisementPlaces=advertisementPlaceService.getAdvertisementPlaceListAll(null);
	    	 List<AdvertisementFrame> frames=advertisementFrameService.getAdvertisementFrameListAll();
	    	 model.addAttribute("advPlaces",advertisementPlaces);
	    	 model.addAttribute("advFrames",frames);
	        return "pages/advertisement/create";
	    }
	    
	    /**
	     * 添加广告
	     * @param request
	     * @param advertisement
	     * @param model
	     */
	    @RequestMapping(value = "save", method = RequestMethod.POST)
	    public String save(MultipartHttpServletRequest request,String title,Long advFrameId,String beginTime,String endTime,
	    		Integer linkType,String linkObject, String xiuAppUrl,String remark,Model model) {
	    	Advertisement advertisement=new Advertisement();
	    	advertisement.setTitle(title);
	    	advertisement.setAdvFrameId(advFrameId);
	    	advertisement.setStartTime(DateUtil.parseTime(beginTime));
	    	advertisement.setEndTime(DateUtil.parseTime(endTime));
	    	advertisement.setLinkType(linkType);
	    	if(linkObject!=null){
	    		linkObject=linkObject.replace(" ", ""); 
	    		advertisement.setLinkObject(linkObject.trim());
	    	}
	    	if(xiuAppUrl!=null){
	    		xiuAppUrl=xiuAppUrl.replace(" ", ""); 
	    	}
	    	advertisement.setXiuAppUrl(xiuAppUrl);
	    	advertisement.setRemark(remark);
	    	Map params=new HashMap();
	    	Integer status=0;//操作状态
	    	String msg=null;
			User user=AdminSessionUtil.getUser(request);
			Long userId=user.getId(); 
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("adv_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/adv" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				advertisement.setPicPath(picPath);// 图片上传路径
			}
			 advertisement.setCreateBy(userId);
			 params.put("model", advertisement);
		     params.put(ShowConstant.USER_ID, userId);
//			 params.put(ShowConstant.OPERATE_USER_NAME, userName);
			 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			 Map result=advertisementService.save(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					try {
						patch.transferTo(new File(picFile));// 上传图片
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				 status=1;
			 }else{
		    	 List<AdvertisementFrame> frames=advertisementFrameService.getAdvertisementFrameListAll();
		    	 model.addAttribute("advFrames",frames);
			 }
//		    advertisement=advertisementService.getAdvertisementById(advertisement.getId());
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("advertisement", advertisement);
	    	return "pages/advertisement/create";
	    }
	   
	    /**
	     * 广告管理--跳转广告编辑页面
	     * @param id
	     * @param advPlaceName
	     * @param model
	     */
	    @RequestMapping(value = "toedit", method = RequestMethod.GET)
	    public String toEdit(Long id ,Model model) {
	    	Advertisement advertisement=null;
	    	try {
	    		advertisement=advertisementService.getAdvertisementById(id);
		    	List<AdvertisementPlace> advertisementPlaces=advertisementPlaceService.getAdvertisementPlaceListAll(null);
		    	 List<AdvertisementFrame> frames=advertisementFrameService.getAdvertisementFrameListAll();
		    	 model.addAttribute("advPlaces",advertisementPlaces);
		    	 model.addAttribute("advFrames",frames);
			} catch (Exception e) {
				LOGGER.error("根据广告ID查询广告异常！",e);
			}
    		model.addAttribute("advertisement",advertisement);
	        return "pages/advertisement/info";
	    }
	    /**
	     * 广告管理--编辑广告
	     * @param Advertisement
	     * @param model
	     */
	    @RequestMapping(value = "edit", method = RequestMethod.POST)
	    public String edit(MultipartHttpServletRequest request,Long id,String title,Long advFrameId,String beginTime,String endTime,
	    		Integer linkType,String linkObject,String xiuAppUrl,String remark,Model model) {
	    	Map params=new HashMap();
	    	Integer status=0;//操作状态
	    	String msg=null;
			 User user=AdminAuthInfoHolder.getUserAuthInfo();
			Long userId=user.getId(); 
			String userName=user.getUsername(); 
	    	Advertisement advertisement=new Advertisement();
			 
			 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
				//图片检测
				MultipartFile patch = multipartRequest.getFile("adv_pic_f");
				
				String fileName = patch.getOriginalFilename();
				String picPath = "";
				if(null!=fileName && !"".equals(fileName)){
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					picPath = "/m/focuspicture" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					advertisement.setPicPath(picPath);// 图片上传路径
				}
			 advertisement.setId(id);
			 advertisement.setTitle(title);
			 advertisement.setAdvFrameId(advFrameId);
			 if(xiuAppUrl!=null){
				 xiuAppUrl=xiuAppUrl.replace(" ", ""); 
			 }
		     advertisement.setXiuAppUrl(xiuAppUrl);
			 if(beginTime!=null&&!beginTime.equals("")){
				 advertisement.setStartTime(DateUtil.parseDate(beginTime,Constants.DATE_FORMAT_STYLE));
			 }
			 if(endTime!=null&&!endTime.equals("")){
				 advertisement.setEndTime(DateUtil.parseDate(endTime,Constants.DATE_FORMAT_STYLE));
			 }
			 advertisement.setLinkType(linkType);
			 if(linkObject!=null){
				 linkObject=linkObject.replace(" ", ""); 
			 }
			 advertisement.setLinkObject(linkObject);
			 advertisement.setLastUpdateBy(userId);
			 advertisement.setRemark(remark);
			 params.put("model", advertisement);
			 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			 Map result=advertisementService.update(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
				 if(null!=fileName && !"".equals(fileName)){
					//上传
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					try {
						patch.transferTo(new File(picFile));// 上传图片
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				 }
				 status=1;
			 }
			advertisement=advertisementService.getAdvertisementById(id);
	    	 List<AdvertisementFrame> frames=advertisementFrameService.getAdvertisementFrameListAll();
	    	 model.addAttribute("advFrames",frames);
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("advertisement", advertisement);
	        return "pages/advertisement/info";
	    }
	    
	    /**
	     * 广告管理--删除广告(deleted: 0 未删除 , 1 已删除 )
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String delete(Long id,Model model) {
			 User user=AdminAuthInfoHolder.getUserAuthInfo();
			Long userId=user.getId(); 
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		Advertisement advertisement=new Advertisement();
	    		advertisement.setId(id);
	    		advertisement.setLastUpdateBy(userId);
	    		advertisement.setDeleteFlag(ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
	            if(advertisementService.delete(advertisement)==0){
	    			 json.setScode(JsonPackageWrapper.S_OK);
	                 json.setData("删除广告成功！");
	    		 }else{
	    			 json.setScode(JsonPackageWrapper.S_ERR);
	                 json.setData("删除广告失败！");
	    		 }
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("删除广告失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    
	    @RequestMapping(value = "advPreview", method = RequestMethod.GET)
	    public String advPreview(String hrefUrl,String picPath,Model model) {
	    	 model.addAttribute("hrefUrl",hrefUrl);
	    	 model.addAttribute("picPath",images_domain_name+picPath);
	         return "pages/advertisement/advPreview";
	    }
	    
	    /**
	     * 更新绑定
	     * @param request
	     * @param focusId
	     * @param linkType
	     * @param linkObject
	     * @param model
	     * @return
	     */
		 @RequestMapping(value = "updateLink", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String updateLink(HttpServletRequest request,Long id,
	    		Integer linkType,String linkObject,String xiuAppUrl,Model model) {
		    	Map params=new HashMap();
			 User user=AdminAuthInfoHolder.getUserAuthInfo();
			Long userId=user.getId(); 
			String userName=user.getUsername(); 
			 JsonPackageWrapper json = new JsonPackageWrapper();
			 Advertisement adv =new Advertisement();
			 adv.setId(id);
			 adv.setLinkType(linkType);
			 if(linkObject!=null){
				 linkObject=linkObject.replace(" ", ""); 
			 }
			 adv.setLinkObject(linkObject);
			 if(xiuAppUrl!=null){
				 xiuAppUrl=xiuAppUrl.replace(" ", ""); 
			 }
			 adv.setXiuAppUrl(xiuAppUrl);
			 adv.setLastUpdateBy(userId);
			 params.put("model", adv);
			 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			 Map result=advertisementService.update(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			 if(isSuccess){
				 json.setScode(JsonPackageWrapper.S_OK);
				 json.setData("更新成功！");
			 }else{
				 json.setScode(JsonPackageWrapper.S_ERR);
				 json.setData("修改成功！"); 
			 }
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	    
		    
		    /**
		     * 获取广告帧下安排的时间
		     * @param advFrameId
		     * @param advId
		     * @param model
		     * @return
		     */
		    @RequestMapping(value = "getTimesByAdvFrameId", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String getTimesByAdvFrameId(Long advFrameId,Long advId,Model model) {
		    	JsonPackageWrapper json = new JsonPackageWrapper();
		    	String timesStr="";
		    	try {
		    		Map params=new HashMap();
		    		params.put("advId", advId);
		    		params.put("advFrameId", advFrameId);
		    		 timesStr=advertisementService.getTimesByAdvFrameId(params);
				} catch (Exception e) {
					json.setScode(JsonPackageWrapper.S_ERR);
		            json.setData("系统发生异常！");
		            LOGGER.error("删除广告失败！：", e);
				}
		    	model.addAttribute(Constants.JSON_MODEL__DATA, timesStr);
		        return "";
		    }
	    
}
