package com.xiu.show.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.TopicModel;
import com.xiu.show.core.service.IShowTopicManagerService;

/**
 * 
* @Description: TODO(话题) 
* @author haidong.luo@xiu.com
* @date 2015年6月12日 下午3:25:24 
*
 */
@AuthRequired 
@Controller
@RequestMapping(value = "/showTopic")
public class ShowTopicController { 
 
    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShowTopicController.class);

    @Autowired
    private IShowTopicManagerService showTopicManagerService;

	@Value("${upload_pic_path}")
	private String upload_pic_path;
	  
		/**
		 * 话题列表
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
		public String list(String topicIds,Integer status,String title,String startDate,String endDate,
				String deleteFlag,String isHot,String publishUserName,
				Page<?> page, Model model) {
			Map<String,Object> rmap = new HashMap<String,Object>();
			rmap.put("status", status);
			rmap.put("topicIds", topicIds);
			rmap.put("title", title);
			rmap.put("startDate", startDate);
			rmap.put("endDate", endDate);
			rmap.put("deleteFlag", deleteFlag);
			rmap.put("isHot", isHot);
			rmap.put("publishUserName", publishUserName);
			rmap=showTopicManagerService.getTopicList(rmap,page);
			List<TopicModel> fs=(List<TopicModel>)rmap.get("resultInfo");
			model.addAttribute("status", status);
			model.addAttribute("topicIds", topicIds);
			model.addAttribute("title", title);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("deleteFlag", deleteFlag);
			model.addAttribute("isHot", isHot);
			model.addAttribute("publishUserName", publishUserName);
			model.addAttribute("topicList", fs);
			
			return "pages/show/showTopic/list";
		}
    
    
	/**
	 * 检查话题是否存在
	 * @param request
	 * @param topicId
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "checkShowTopic", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkShowTopic(HttpServletRequest request, String topicId, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
				if(null != topicId){
					Map params=new HashMap();
					params.put("topicId", topicId);
					boolean issuccess = false;
						 issuccess = showTopicManagerService.checkShowTopic(params);
						 if(issuccess){
								json.setScode(JsonPackageWrapper.S_OK);
						 }else{
								json.setScode("2");
						 }
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
		/**
		 * 跳转到添加页面
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "bfAdd", method = RequestMethod.GET)
		public String bfAdd(Model model) {
			return "pages/show/showTopic/create";
		}  
		
		
		  /**
	     * 添加话题
	     * @param request
	     * @param topicPicture
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "add", method = RequestMethod.POST)
	    public String add(MultipartHttpServletRequest request,String title,String beginTime,String endTime,
	    		String content,String remark,String isHot,Model model) {
	    	Map params=new HashMap();
	    	Integer status=0;//操作状态
	    	String msg=null; 
			User user=AdminSessionUtil.getUser(request);
			Long userId=user.getId(); 
			String userName=user.getUsername();  
			
			TopicModel topicModel =new TopicModel();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("topic_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/showtopic" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				topicModel.setImageUrl(picPath);// 图片上传路径
			}
			
			 if(beginTime!=null&&!beginTime.equals("")){
				 topicModel.setBeginTime(DateUtil.parseDate(beginTime,Constants.DATE_FORMAT_STYLE));
			 } 
			 if(endTime!=null&&!endTime.equals("")){
				 topicModel.setEndTime(DateUtil.parseDate(endTime,Constants.DATE_FORMAT_STYLE));
			 }
			 topicModel.setPublishUserId(userId);
			 topicModel.setPublishUserName(userName);
			 topicModel.setTitle(title);
			 topicModel.setContent(content);
			 topicModel.setRemark(remark);
			 topicModel.setIsHot(Integer.parseInt(isHot));
			 params.put("model", topicModel);
		     params.put(ShowConstant.USER_ID, userId);
			 params.put(ShowConstant.OPERATE_USER_NAME, userName);
			 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			 Map result=showTopicManagerService.addTopic(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
				    //上传话题图片 
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					try {
						patch.transferTo(new File(picFile));// 上传图片
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
//					//处理话题内容中的图片存放位置(uediter上传到项目里，需把图片移到关联图片目录里)
//					String projertDir=request.getSession().getServletContext().getRealPath("/");
//					String ueUploadDirFile="ueditor\\jsp\\upload\\image";
//					String  ueUploadDir=projertDir+ueUploadDirFile;
//					copyFolder(ueUploadDir,upload_pic_path.trim()+ "/m/"+ueUploadDirFile);
				 status=1;
			 }
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
	    	return "pages/show/showTopic/create";
	    }
	    
		  /**
	     * 修改话题
	     * @param request
	     * @param topicPicture
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "update", method = RequestMethod.POST)
	    public String update(MultipartHttpServletRequest request,Long topicId,String title,String beginTime,String endTime,
	    		String content,Integer isHot,String remark,Model model) {
	    	Map params=new HashMap();
	    	Integer status=0;//操作状态
	    	String msg=null;
			User user=AdminSessionUtil.getUser(request);
			Long userId=user.getId(); 
			String userName=user.getUsername(); 
			
			TopicModel topicModel =new TopicModel();
			topicModel.setTopicId(topicId);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("topic_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			Boolean isUploadImg=false;
			if(null!=fileName && !"".equals(fileName)){
				isUploadImg = true;
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/showtopic" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				topicModel.setImageUrl(picPath);// 图片上传路径
			}
			
			 if(beginTime!=null&&!beginTime.equals("")){
				 topicModel.setBeginTime(DateUtil.parseDate(beginTime,Constants.DATE_FORMAT_STYLE));
			 } 
			 if(endTime!=null&&!endTime.equals("")){
				 topicModel.setEndTime(DateUtil.parseDate(endTime,Constants.DATE_FORMAT_STYLE));
			 }
			 topicModel.setPublishUserId(userId);
			 topicModel.setPublishUserName(userName);
			 topicModel.setTitle(title);
			 topicModel.setContent(content);
			 topicModel.setRemark(remark);
			 topicModel.setIsHot(ObjectUtil.getInteger(isHot,null));
			 params.put("model", topicModel);
		     params.put(ShowConstant.USER_ID, userId);
			 params.put(ShowConstant.OPERATE_USER_NAME, userName);
			 params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
			 Map result=showTopicManagerService.updateTopic(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
					 status=1; 
					 if(isUploadImg){  
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
			 }
			 //处理话题内容中的图片存放位置(uediter上传到项目里，需把图片移到关联图片目录里)
//			 String projertDir=request.getSession().getServletContext().getRealPath("/");
//			 String ueUploadDirFile="ueditor\\jsp\\upload\\image";
//			 String  ueUploadDir=projertDir+ueUploadDirFile;
//			 copyFolder(ueUploadDir,upload_pic_path.trim()+ "/m/"+ueUploadDirFile);
			TopicModel topicModelNew=showTopicManagerService.getTopicInfo(topicModel.getTopicId());
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("topic", topicModelNew);
	    	return "pages/show/showTopic/info";
	    }
	
		/**
		 * 话题详情
		 * @param topicId
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "info", method = RequestMethod.GET)
		public String info(String topicId,Model model) {
			Long topicIdl=Long.valueOf(topicId);
			TopicModel topicModel=null;
			topicModel=showTopicManagerService.getTopicInfo(topicIdl);
			model.addAttribute("topic", topicModel);
			return "pages/show/showTopic/info";
		}
		
		/**
		 * 删除话题
		 * @param id
		 * @param model
		 * @return 
		 */
		 @RequestMapping(value = "delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String delete(HttpServletRequest request, Long topicId,String resultType, Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
				User user=AdminSessionUtil.getUser(request);
				Long userId=user.getId(); 
				String userName=user.getUsername(); 
					if(null != topicId){ 
						Map params=new HashMap();
						params.put("topicId", topicId);
					     params.put(ShowConstant.USER_ID, userId);
						 params.put(ShowConstant.OPERATE_USER_NAME, userName);
						params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
						Map result = showTopicManagerService.deleteTopic(params);
						 Boolean isSuccess=(Boolean)result.get("status");
						if (isSuccess) {
							json.setScode(JsonPackageWrapper.S_OK);
							json.setSmsg("删除成功！");
						} else {
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setSmsg("删除操作失败！");
						}
					}else{
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setSmsg("参数不完整！");
					}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
		
		 
			/**
			 * 修改话题热门标识
			 * @param request
			 * @param topicId
			 * @param isHot
			 * @param model
			 * @return
			 */
			 @RequestMapping(value = "updateTopicHot", method = RequestMethod.GET, produces = "application/json", params = "format=json")
			    public String updateTopicHot(HttpServletRequest request, Long topicId,Integer isHot, Model model){
					JsonPackageWrapper json = new JsonPackageWrapper();
					User user=AdminSessionUtil.getUser(request);
					Long userId=user.getId(); 
					String userName=user.getUsername(); 
						if(null != topicId&&isHot!=null){ 
							Map<String,Object> params=new HashMap<String,Object>();
							params.put("topicId", topicId);
							params.put("isHot", isHot);
						    params.put(ShowConstant.USER_ID, userId);
							params.put(ShowConstant.OPERATE_USER_NAME, userName);
							params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
							Map result = showTopicManagerService.updateTopicIsHot(params);
							 Boolean isSuccess=(Boolean)result.get("status");
							if (isSuccess) {
								json.setScode(JsonPackageWrapper.S_OK);
								json.setSmsg("修改成功！");
							} else {
								json.setScode(JsonPackageWrapper.S_ERR);
								json.setSmsg("修改操作失败！");
							}
						}else{
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setSmsg("参数不完整！");
						}
					model.addAttribute(Constants.JSON_MODEL__DATA, json);
					return "";
			    }
		 
	   
	   
	   /** 
	   * 复制整个文件夹内容 
	   * @param oldPath String 原文件路径 如：c:/fqf 
	   * @param newPath String 复制后路径 如：f:/fqf/ff 
	   * @return boolean 
	   */ 
	   public void copyFolder(String oldPath, String newPath) { 
		   try { 
			   (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
			   File a=new File(oldPath); 
			   String[] file=a.list(); 
			   File temp=null; 
			   if(file!=null){ 
				   for (int i = 0; i < file.length; i++) { 
					   if(oldPath.endsWith(File.separator)){ 
						   temp=new File(oldPath+file[i]); 
					   } 
					   else{ 
						   temp=new File(oldPath+File.separator+file[i]); 
					   } 
				
					   if(temp.isFile()){ 
						   FileInputStream input = new FileInputStream(temp); 
						   FileOutputStream output = new FileOutputStream(newPath + "/" + 
						   (temp.getName()).toString()); 
						   byte[] b = new byte[1024 * 5]; 
						   int len; 
						   while ( (len = input.read(b)) != -1) { 
							   output.write(b, 0, len); 
						   } 
						   output.flush(); 
						   output.close(); 
						   input.close(); 
					   } 
					   if(temp.isDirectory()){//如果是子文件夹 
						   copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]); 
					   } 
				   } 
			   }
		   } 
		   catch (Exception e) { 
		   System.out.println("复制整个文件夹内容操作出错"); 
		   e.printStackTrace(); 
	
		   } 

	   }
		 
}
