package com.xiu.show.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.xiu.common.web.utils.DateUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.FocusPictureModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.service.IFocusPictureManagerService;

/**
 * 
* @Description: TODO(焦点图) 
* @author haidong.luo@xiu.com
* @date 2015年6月12日 下午3:25:24 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/focusPicture")
public class FocusPictureController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(FocusPictureController.class);

    @Autowired
    private IFocusPictureManagerService focusPictureManagerService;


	@Value("${upload_pic_path}")
	private String upload_pic_path;
    
	/**
	 * 焦点图列表
	 * @param content
	 * @param showId
	 * @param startDate
	 * @param endDate
	 * @param commentName
	 * @param commentStatus
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String focusId,String startDate,String endDate,
			String picStatus,String deleteFlag,String linkType,String creatorName,
			Page<?> page, Model model) {
		Map rmap = new HashMap();
		rmap.put("focusId", focusId);
		rmap.put("startDate", startDate);
		rmap.put("endDate", endDate);
		rmap.put("status", picStatus);
		rmap.put("deleteFlag", deleteFlag);
		rmap.put("linkType", linkType); 
		rmap.put("creatorName", creatorName);
		rmap=focusPictureManagerService.getFocusPictureList(rmap,page);
		List<FocusPictureModel> fs=(List<FocusPictureModel>)rmap.get("resultInfo");
		model.addAttribute("focusId", focusId);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("picStatus", picStatus);
		model.addAttribute("deleteFlag", deleteFlag);
		model.addAttribute("linkType", linkType);
		model.addAttribute("creatorName", creatorName);
		model.addAttribute("focusPicturelist", fs);
		
		
		return "pages/show/focusPicture/list";
	}
	
	/**
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "bfAdd", method = RequestMethod.GET)
	public String bfAdd(Model model) {
		return "pages/show/focusPicture/create";
	}
	
   /**
     * 更新焦点图
     * @param request
     * @param focusPicture
     * @param model
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(MultipartHttpServletRequest request,Integer position,String beginTime,String endTime,
    		Integer linkType,String linkObject,String remark,Model model) {
    	Map params=new HashMap();
    	Integer status=0;//操作状态
    	String msg=null;
		User user=AdminSessionUtil.getUser(request);
		Long userId=user.getId(); 
		String userName=user.getUsername(); 
		
		FocusPictureModel focusPicture =new FocusPictureModel();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		//图片检测
		MultipartFile patch = multipartRequest.getFile("focus_pic_f");
		
		String fileName = patch.getOriginalFilename();
		String picPath = "";
		if(null!=fileName && !"".equals(fileName)){
			fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
			picPath = "/m/focuspicture" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
			focusPicture.setPicUrl(picPath);// 图片上传路径
		}
		
		
		 focusPicture.setPosition(position);
		 if(beginTime!=null&&!beginTime.equals("")){
			 focusPicture.setBeginTime(DateUtil.parseDate(beginTime,Constants.DATE_FORMAT_STYLE));
		 } 
		 if(endTime!=null&&!endTime.equals("")){
			 focusPicture.setEndTime(DateUtil.parseDate(endTime,Constants.DATE_FORMAT_STYLE));
		 }
		 focusPicture.setCreatorId(userId);
		 focusPicture.setCreatorName(userName);
		 focusPicture.setLinkType(linkType);
		 focusPicture.setLinkObject(linkObject);
		 focusPicture.setRemark(remark);
//		 focusPicture.setPicUrl("");
		 params.put("model", focusPicture);
	     params.put(ShowConstant.USER_ID, userId);
		 params.put(ShowConstant.OPERATE_USER_NAME, userName);
		 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		 Map result=focusPictureManagerService.addFocusPicture(params);//保存数据
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
		 }
		 FocusPictureModel focusPictureModel=focusPictureManagerService.getFocusPictureInfo(focusPicture.getFocusId());
		model.addAttribute("status", status);
    	model.addAttribute("msg", msg);
		model.addAttribute("focusPicture", focusPictureModel);
    	return "pages/show/focusPicture/create";
    }
	
	

	/**
	 * 焦点图详情
	 * @param commentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(String focusId,Model model) {
		Long focusIdl=Long.valueOf(focusId);
		FocusPictureModel focusPictureModel=null;
		focusPictureModel=focusPictureManagerService.getFocusPictureInfo(focusIdl);
		model.addAttribute("focusPicture", focusPictureModel);
		return "pages/show/focusPicture/info";
	}
	
    /**
     * 更新焦点图
     * @param request
     * @param focusPicture
     * @param model
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(MultipartHttpServletRequest request,Long focusId,Integer position,String beginTime,String endTime,
    		Integer linkType,String linkObject,String remark,Model model) {
    	Map params=new HashMap();
    	Integer status=0;//操作状态
    	String msg=null;
		 User user=AdminAuthInfoHolder.getUserAuthInfo();
		Long userId=user.getId(); 
		String userName=user.getUsername(); 
		 FocusPictureModel focusPicture =new FocusPictureModel();
		 
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("focus_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/focuspicture" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				focusPicture.setPicUrl(picPath);// 图片上传路径
			}
		 
		 focusPicture.setFocusId(focusId);
		 focusPicture.setPosition(position);
		 if(beginTime!=null&&!beginTime.equals("")){
			 focusPicture.setBeginTime(DateUtil.parseDate(beginTime,Constants.DATE_FORMAT_STYLE));
		 }
		 if(endTime!=null&&!endTime.equals("")){
			 focusPicture.setEndTime(DateUtil.parseDate(endTime,Constants.DATE_FORMAT_STYLE));
		 }
		 focusPicture.setLinkType(linkType);
		 focusPicture.setLinkObject(linkObject);
		 focusPicture.setRemark(remark);
		 params.put("model", focusPicture);
	     params.put(ShowConstant.USER_ID, userId);
		 params.put(ShowConstant.OPERATE_USER_NAME, userName);
		 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		 Map result=focusPictureManagerService.updateFocusPicture(params);//保存数据
		 Boolean isSuccess=(Boolean)result.get("status");
		  msg=(String)result.get("msg");
		 if(isSuccess){
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
			 status=1;
		 }
		FocusPictureModel focusPictureModel=focusPictureManagerService.getFocusPictureInfo(focusPicture.getFocusId());
		model.addAttribute("status", status);
    	model.addAttribute("msg", msg);
		model.addAttribute("focusPicture", focusPictureModel);
    	return "pages/show/focusPicture/info";
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
    public String updateLink(HttpServletRequest request,Long focusId,
    		Integer linkType,String linkObject,Model model) {
	    	Map params=new HashMap();
		 User user=AdminAuthInfoHolder.getUserAuthInfo();
		Long userId=user.getId(); 
		String userName=user.getUsername(); 
		 JsonPackageWrapper json = new JsonPackageWrapper();
		 FocusPictureModel focusPicture =new FocusPictureModel();
		 focusPicture.setFocusId(focusId);
		 focusPicture.setLinkType(linkType);
		 focusPicture.setLinkObject(linkObject);
		 params.put("model", focusPicture);
	     params.put(ShowConstant.USER_ID, userId);
		 params.put(ShowConstant.OPERATE_USER_NAME, userName);
		 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
		 Map result=focusPictureManagerService.updateFocusPicture(params);//保存数据
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
	 * 删除焦点图
	 * @param id
	 * @param model
	 * @return 
	 */
	 @RequestMapping(value = "delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String delete(HttpServletRequest request, Long focusId,String resultType, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			User user=AdminSessionUtil.getUser(request);
				if(null != focusId){ 
					Map params=new HashMap();
					params.put("focusId", focusId);
					params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
					Map result = focusPictureManagerService.deleteFocusPicture(params);
					 Boolean isSuccess=(Boolean)result.get("status");
					if (isSuccess) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("删除成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("删除操作失败！");
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
//	    
//	     /**
//	      * 批量删除焦点图
//	      * @param ids
//	      * @param model
//	      * @return
//	      */
//	    @RequestMapping(value = "deleteBatch", method = RequestMethod.GET, produces = "application/json", params = "format=json")
//	    public String deleteBatch(HttpServletRequest request, String ids,Model model){
//			JsonPackageWrapper json = new JsonPackageWrapper();
//			try {
//				if(null != ids){
//					LOGGER.debug("即将被删的商品ids是:{}",new Object[]{ids});
//					String[] strIds = ids.split(",");
//					List<Long> idsL = new ArrayList<Long>();
//					if(strIds != null){
//						for(String s : strIds){
//							if(!StringUtil.isEmpty(s))
//								idsL.add(Long.parseLong(s));
//						}
//					}
//					Map params=new HashMap();
//					params.put("ids", idsL);
//					User user=AdminSessionUtil.getUser(request);
//					params.put(ShowConstant.USER_ID, user.getId());
//					params.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
//					params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
//					
//					int result = showCommentService.deleteBatchComment(params);
//					if (result > 0) {
//						json.setScode(JsonPackageWrapper.S_OK);
//						json.setData("删除成功！");
//					} else {
//						json.setScode(JsonPackageWrapper.S_ERR);
//						json.setData("删除操作失败！");
//					}
//				}else{
//					json.setScode(JsonPackageWrapper.S_ERR);
//					json.setData("参数不完整！");
//				}
//			} catch (Exception e) {
//				json.setScode(JsonPackageWrapper.S_ERR);
//				json.setData("系统发生异常！");
//				LOGGER.error("批量删除焦点图失败！", e);
//			}
//			model.addAttribute(Constants.JSON_MODEL__DATA, json);
//			return "";
//	    }
	
}
