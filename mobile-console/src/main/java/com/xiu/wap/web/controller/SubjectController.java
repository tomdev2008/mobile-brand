package com.xiu.wap.web.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.xiu.common.web.utils.ExcelUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.StringUtil;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.model.Goods;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.model.SubjectComment;
import com.xiu.mobile.core.service.IGoodsService;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.service.ISubjectManagerService;
import com.xiu.mobile.core.utils.ImageUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.UserWhiteListModel;
import com.xiu.show.core.service.ISensitiveWordService;
import com.xiu.show.core.service.IShowUserWhiteListManagerService;
import com.xiu.show.web.service.IUserWhiteListService;
import com.xiu.wap.web.service.ISearchService;
/**
 * 
* @Description: TODO(专题) 
* @author haidong.luo@xiu.com
* @date 2015年10月8日 下午2:40:44 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/subject")
public class SubjectController {	

		//日志
	    private static final XLogger LOGGER = XLoggerFactory.getXLogger(SubjectController.class);
	    
	    @Autowired
	    private ISubjectManagerService subjectManagerService;
	    
	    @Autowired
	    private IGoodsService goodsService;
	    
	    @Autowired
	    private ILabelService subjectLabelService;
	    
	    @Autowired
	    private IUserWhiteListService userWhiteListService;
	    
	    @Autowired
	    private IShowUserWhiteListManagerService showUserWhiteListManagerService;
	    
	    @Autowired
	    private ISensitiveWordService sensitiveWordService;
	    
	    @Autowired
        private ILabelService labelService;
	  
	    @Autowired
		private ISearchService searchService;
	    
	    @Value("${upload_pic_path}")
	    private String upload_pic_path;
	    
	    @Value("${images_domain_name}")
	    private String images_domain_name;
	    
	    /**
	     * 专题管理--查询专题
	     * @param advPlaceId
	     * @param advPlaceName
	     * @param page
	     * @param model
	     */
	    @RequestMapping(value = "list", method = RequestMethod.GET)
	    public String list(String id,String startDate,String endDate,String status,
	    		String title,String creatorName,Long labelId,String labelName,
	    		Page<?> page, Model model) {
	    	List<Subject> subjectList=new ArrayList<Subject>();
	    	Map<Object,Object> rmap=new HashMap<Object, Object>(); 
			rmap.put("id", id);
			rmap.put("startDate", startDate);
			rmap.put("endDate", endDate);
			rmap.put("creatorName", creatorName);
			rmap.put("title", title);
			rmap.put("status", status);
			rmap.put("labelId", labelId);
			rmap.put("labelName", labelName);
	    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
	    	
    		Map<String,Object> result =subjectManagerService.getSubjectList(rmap,page);
    		subjectList=(List<Subject>)result.get("resultInfo");
	    	//查询白名单
	    	List<UserWhiteListModel> whiteList=userWhiteListService.getUserWhiteList(1, 100);
			model.addAttribute("labelName", labelName);
			model.addAttribute("id", id);
			model.addAttribute("status", status);
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("title", title);
			model.addAttribute("labelId", labelId);
			model.addAttribute("creatorName", creatorName);
	    	model.addAttribute("subjectList",subjectList);
	    	model.addAttribute("whiteList",whiteList);
	    	
	        return "pages/subject/list";
	    }
	    
	    /**
	     * 跳转专题添加页面--界面加载
	     */ 
	    @RequestMapping(value = "bfAdd", method = RequestMethod.GET)
	    public String bfAdd( Model model) {
//	    	//查询标签
//	    	int category=1;//专题
//	    	List<Label> listLabel=subjectManagerService.getSubjectLabel(category);
//	    	model.addAttribute("listLabel", listLabel);
			 // 查询常用的标签（热门和新加的）
			Map<String,Object> labels=labelService.findCommonLabelList();
			model.addAttribute("newAddList", labels.get("newAddList"));
			model.addAttribute("historyList", labels.get("historyList"));
	    	
	        return "pages/subject/create";
	    }
	    
	    /**
	     * 添加专题
	     * @param request
	     * @param subject
	     * @param model
	     */
	    @RequestMapping(value = "save", method = RequestMethod.POST)
	    public String save(MultipartHttpServletRequest request,String name,String title,String displayStytle,
	    		String beginTime,String endTime,String orderSeq,String subjectItem[],String subjectItemType[],
	    		String urlType[],String subjectUrl[],String[] labelIds,Model model) {
	    	Integer status=0;//操作状态
	    	Subject subject=new Subject();
	    	name=ObjectUtil.getString(name, "").trim();
	    	subject.setName(name);
	    	title=ObjectUtil.getString(title, "").trim();
	    	subject.setTitle(title);
	    	subject.setDisplayStytle(ObjectUtil.getInteger(displayStytle, 1));
	    	if(StringUtil.isEmpty(orderSeq)){
	    		orderSeq="100";
	    	}
	    	subject.setOrderSeq(ObjectUtil.getInteger(orderSeq, 1));
	    	subject.setStartTime(DateUtil.parseTime(beginTime)); 
	    	subject.setEndTime(DateUtil.parseTime(endTime));
//	    	subject.setLabelId(labelId);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("out_pic");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/subject" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				subject.setOutPic(picPath);// 图片上传路径
			}
			Map<String,Object> params=new HashMap<String,Object>();
	    	String msg=null;
			User user=AdminSessionUtil.getUser(request);
			subject.setCreatorName(user.getUsername());
			params.put("model", subject);
			params.put("subjectItem", subjectItem);
			params.put("subjectItemType", subjectItemType);
			params.put("urlType", urlType);
			params.put("subjectUrl", subjectUrl);
			 Map<String,Object> result=subjectManagerService.save(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
				 Long subjectId	=(Long)result.get("subjectId");
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					try {
						patch.transferTo(new File(picFile));// 上传图片
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					//如果有标签则绑定标签
					if(labelIds!=null&&labelIds.length>0){
						List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
						for (int j = 0; j < labelIds.length; j++) {
							LabelCentre lc=new LabelCentre();
							lc.setLabelId(ObjectUtil.getLong(labelIds[j]));
							labelCentres.add(lc);
						}
						labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_SUBJECT, subjectId, labelCentres);
					}	
					
					//加入到搜索表 中
					searchService.addDataToSearch(GlobalConstants.LABEL_TYPE_SUBJECT, subjectId);
				 status=1;
			 }
	    	
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("subject", subject);
	    	return "pages/subject/create";
	    }
	    
	    
	    /**
	     * 跳转专题修改页面--界面加载
	     */ 
	    @RequestMapping(value = "bfUpdate", method = RequestMethod.GET)
	    public String bfUpdate(Long subjectId, Model model) {
	    	Subject subject=subjectManagerService.getSubjectById(subjectId);
//	    	int category=1;//单品
//	    	List<Label> listLabel=subjectManagerService.getSubjectLabel(category);
			//查询标签
			// 查询已经添加的标签
			List<Label> label=labelService.findLabelListByObjectIdAndType(subjectId,  GlobalConstants.LABEL_TYPE_SUBJECT);
			model.addAttribute("updateLabelList", label);
			 // 查询常用的标签（热门和新加的）
			Map<String,Object> labels=labelService.findCommonLabelList();
			model.addAttribute("newAddList", labels.get("newAddList"));
			model.addAttribute("historyList", labels.get("historyList"));
	    	
			model.addAttribute("subject", subject);
//			model.addAttribute("listLabel", listLabel);
//			model.addAttribute("subjectLabel", subject.getLabelCentre());
	        return "pages/subject/update";
	    }
	    
	    /**
	     * 修改专题
	     * @param request
	     * @param subject
	     * @param model
	     */
	    @RequestMapping(value = "update", method = RequestMethod.POST)
	    public String update(MultipartHttpServletRequest request,Long subjectId,String name,String title,String displayStytle,
	    		String beginTime,String endTime,String orderSeq,String subjectItem[],String subjectItemType[],String[] labelIds,
	    		String urlType[],String subjectUrl[],Model model) {
	    	Integer status=0;//操作状态
	    	Subject subject=new Subject();
	    	subject.setSubjectId(subjectId);
	    	name=ObjectUtil.getString(name, "").trim();
	    	subject.setName(name);
	    	title=ObjectUtil.getString(title, "").trim();
	    	subject.setTitle(title);
	    	subject.setDisplayStytle(ObjectUtil.getInteger(displayStytle, null));
	    	subject.setOrderSeq(ObjectUtil.getInteger(orderSeq, null));
	    	subject.setStartTime(DateUtil.parseTime(beginTime));
	    	subject.setEndTime(DateUtil.parseTime(endTime));
//	    	subject.setLabelId(labelId);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("out_pic");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			boolean isChangeImg=false;
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/subject" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				subject.setOutPic(picPath);// 图片上传路径
				isChangeImg=true;
			}
			Map<String,Object> params=new HashMap<String,Object>();
	    	String msg=null;
			User user=AdminSessionUtil.getUser(request);
			subject.setCreatorName(user.getUsername());
			params.put("model", subject);
			params.put("subjectItem", subjectItem);
			params.put("subjectItemType", subjectItemType);
			params.put("urlType", urlType);
			params.put("subjectUrl", subjectUrl);
			 Map<String,Object> result=subjectManagerService.update(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
				 status=1;
				if(isChangeImg){
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
				
				//如果有标签则绑定标签
					List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
					if(labelIds!=null&&labelIds.length>0){
						for (int j = 0; j < labelIds.length; j++) {
							LabelCentre lc=new LabelCentre();
							lc.setLabelId(ObjectUtil.getLong(labelIds[j]));
							labelCentres.add(lc);
						}
				    }	
					labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_SUBJECT, subjectId, labelCentres);
				
				//加入到搜索表 中
				searchService.addDataToSearch(GlobalConstants.LABEL_TYPE_SUBJECT, subjectId);
				 subject=subjectManagerService.getSubjectById(subjectId);
				model.addAttribute("subject", subject);
			 }
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("subject", subject);
	    	return "pages/subject/update";
	    }
	    
	    /**
	     * 添加专题详情图片
	     * @param request
	     * @param subject
	     * @param model
	     */
		@RequestMapping(value = "/saveImg", method = RequestMethod.POST)
	    public void saveImg(MultipartHttpServletRequest request,HttpServletResponse response) {
			JSONObject obj = new JSONObject();
			PrintWriter out = null;
	    	Integer status=0;//操作状态
	    	try {
					out = response.getWriter();
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
				//图片检测
				MultipartFile patch = multipartRequest.getFile("item_pic");
				
				String fileName = patch.getOriginalFilename();
				String picPath = "";
				if(null!=fileName && !"".equals(fileName)){
					fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
					picPath = "/m/subjectItem" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
	//				subject.setOutPic(picPath);// 图片上传路径
				}
				String picFile = upload_pic_path.trim() + picPath;
				UploadPicUtils.isUploadPathExsit(picFile);
				try {
					patch.transferTo(new File(picFile));// 上传图片
					obj.put("fileName",ImageUtil.getShowimageUrl(picPath));
					obj.put("status", status);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				 status=1;
				 out.print(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if(out!=null){
					out.close();
				}
			}
	    }
		
		
		/**
		 * 根据走专题码获取专题
		 * @param goodsSn
		 * @param model
		 * @return
		 */
	    @RequestMapping(value = "checkGoodsSn", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkGoodsSn(String goodsSn, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			try {
				if(!StringUtil.isEmpty(goodsSn)){
						Goods goods = goodsService.getGoodsBySn(goodsSn);
						if (goods != null) {
							json.setScode(JsonPackageWrapper.S_OK);
							String date = DateUtil.formatDate(goods.getCreateTime(), "yyyyMMdd");
							goods.setStrDate(date);
							json.setData(goods);
							json.setSmsg("检查走秀码成功！");
						} else {
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setData("该走秀码不存在！");
						}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("走秀码不能为空！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("check走秀SN失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
		
	    /**
	     * 专题管理--删除专题(deleted: 0 未删除 , 1 已删除 )
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String delete(Long subjectId,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		Subject subject=new Subject();
	    		subject.setSubjectId(subjectId);
	            if(subjectManagerService.delete(subject)>0){
	    			 json.setScode(JsonPackageWrapper.S_OK);
	                 json.setData("删除专题成功！");
	    		 }else{
	    			 json.setScode(JsonPackageWrapper.S_ERR);
	                 json.setData("删除专题失败！");
	    		 }
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("删除专题失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    
	    /**
	     * 检查专题
	     * @param subjectId
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "checkSubjectId", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkSubjectId(Long subjectId, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			try {
				subjectId=ObjectUtil.getLong(subjectId, null);
				if(subjectId!=null){
					   Subject subject = subjectManagerService.getSubjectById(subjectId);
						if (subject != null) {
							json.setData(subject);
							json.setSmsg("检查专题ID成功！");
						} else {
							json.setScode("2");
							json.setData("该专题ID不存在！");
						}
				}else{
					json.setScode("2");
					json.setData("专题ID不能为空！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("check专题失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	    
	    
	    /**
	     * 获取该排序值下已经安排的还没过期的时间
	     * @param orderSeq
	     * @param displayStytle
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "getTimesByOrderSeq", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String getTimesByOrderSeq(Integer subjectId,Integer orderSeq,Integer displayStytle,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	String timesStr="";
	    	try {
	    		Map<String,Object> params=new HashMap<String,Object>();
	    		params.put("subjectId", subjectId);
	    		params.put("orderSeq", orderSeq);
	    		params.put("displayStytle", displayStytle);
	    		 timesStr=subjectManagerService.getTimesByOrderSeq(params);
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("删除广告失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, timesStr);
	        return "";
	    }
	    /**
	     * 检查排序值时间上有没重复
	     * @param orderSeq
	     * @param displayStytle
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "checkTimesByOrderSeq", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkTimesByOrderSeq(Integer subjectId,Integer orderSeq,Integer displayStytle,String beginTime,String endTime,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	Integer status=0;
	    	try {
	    		Map<String,Object> params=new HashMap<String,Object>();
	    		params.put("subjectId", subjectId);
	    		params.put("orderSeq", orderSeq);
				if(beginTime!=null&&!beginTime.equals("")){
					params.put("beginTime",DateUtil.parseTime(beginTime));
				}
				if(endTime!=null&&!endTime.equals("")){
					params.put("endTime",DateUtil.parseTime(endTime));
				}
	    		params.put("displayStytle", displayStytle);
	    		status=subjectManagerService.checkTimeByOrderSeq(params);
	    	} catch (Exception e) {
	    		json.setScode(JsonPackageWrapper.S_ERR);
	    		json.setData("系统发生异常！");
	    		LOGGER.error("删除广告失败！：", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, status);
	    	return "";
	    }
	    
	    /**
	     * 专题管理--查询专题评论
	     * @param advPlaceId
	     * @param advPlaceName
	     * @param page
	     * @param model
	     */
	    @RequestMapping(value = "commentlist", method = RequestMethod.GET)
	    public String commentlist(String content,String subjectId,String startDate,String endDate,
				String commentName,String commentStatus,Page<?> page, Model model) {
	    	List<SubjectComment> subjectCommentList=new ArrayList<SubjectComment>();
	    	Map rmap = new HashMap();
			rmap.put("content", content);
			rmap.put("subjectId", subjectId);
			if((startDate==null||startDate.equals(""))&&(endDate==null||endDate.equals(""))){
				startDate=DateUtil.getTimeByDays(new Date(), -30);
				endDate=DateUtil.getNowTime();
			}
			rmap.put("startDate", startDate);
			rmap.put("endDate", endDate);
			rmap.put("content", content);
			rmap.put("commentName", commentName);
			rmap.put("commentStatus", commentStatus);
	    	
    		Map result =subjectManagerService.getSubjectCommentList(rmap,page);
    		subjectCommentList=(List<SubjectComment>)result.get("resultInfo");
    		//查询白名单
	    	List<UserWhiteListModel> whiteList=userWhiteListService.getUserWhiteList(1, 100);
    		model.addAttribute("content", content);
    		model.addAttribute("subjectId", subjectId);
    		model.addAttribute("commentName", commentName);
    		model.addAttribute("startDate", startDate);
    		model.addAttribute("endDate", endDate);
    		model.addAttribute("commentStatus", commentStatus);
    		model.addAttribute("subjectCommentList", subjectCommentList);
    		model.addAttribute("whiteList",whiteList);
	        return "pages/subject/commentList";
	    }
	    
	    
	    /**
		 * 删除评论
		 * @param id
		 * @param model
		 * @return
		 */
		 @RequestMapping(value = "deleteComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String deleteComment(HttpServletRequest request, Long id,String resultType, Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
				User user=AdminSessionUtil.getUser(request);
				try {
					if(null != id){
						int result = subjectManagerService.deleteSubjectComment(id,user.getId(),user.getUsername(),resultType);
						if (result > 0) {
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
				} catch (Exception e) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("系统发生异常！");
					LOGGER.error("删除评论失败！", e);
				}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
		    
		     /**
		      * 批量删除评论
		      * @param ids
		      * @param model
		      * @return
		      */
		    @RequestMapping(value = "deleteBatchComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String deleteBatchComment(HttpServletRequest request, String ids,Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
				try {
					if(null != ids){
						LOGGER.debug("即将被删的商品ids是:{}",new Object[]{ids});
						String[] strIds = ids.split(",");
						List<Long> idsL = new ArrayList<Long>();
						if(strIds != null){
							for(String s : strIds){
								if(!StringUtil.isEmpty(s))
									idsL.add(Long.parseLong(s));
							}
						}
						Map<String,Object> params=new HashMap<String,Object>();
						params.put("ids", idsL);
						User user=AdminSessionUtil.getUser(request);
						params.put(ShowConstant.USER_ID, user.getId());
						params.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
						params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
						
						int result = subjectManagerService.deleteBatchSubjectComment(params);
						if (result > 0) {
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
				} catch (Exception e) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("系统发生异常！");
					LOGGER.error("批量删除评论失败！", e);
				}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
	    
	    

			/**
			 * 评论详情
			 * @param commentId
			 * @param model
			 * @return
			 */
			@RequestMapping(value = "commentInfo", method = RequestMethod.GET)
			public String info(String commentId,Model model) {
				Long commentIdl=Long.valueOf(commentId);
				SubjectComment commentModel=null;
				try{
					commentModel=subjectManagerService.getSubjectCommentInfo(commentIdl);
				}catch(Exception e){
					e.printStackTrace();
					LOGGER.error("查询专题评论异常！",e);
				}
				model.addAttribute("comment", commentModel);
				return "pages/subject/commentInfo";
			}
	 /**
	  * 后台列表是否显示
	  */
	@RequestMapping(value = "isShow", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	public String isShow(Model model,Long subjectId,Integer isShow){
		JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
    		Subject subject=new Subject();
    		subject.setSubjectId(subjectId);
    		subject.setIsShow(isShow);
            if(subjectManagerService.updateShow(subject)>0){
    			 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("修改成功！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("修改失败！");
    		 }
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
            json.setData("系统发生异常！");
            LOGGER.error("修改失败！：", e);
		}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
        return "";
	}
	/**
	 * 跳转到导入专题页面
	 */
	@RequestMapping(value = "toimport", method = RequestMethod.GET)
    public String toimport(){
    	return "pages/subject/import";
    }
	 /**
	  * 导入专题  
	  */
	 @RequestMapping(value = "import_excel", method = RequestMethod.POST)
	    public String import_excel(MultipartHttpServletRequest request,Model model){
	    	try{
	    		// 获取Excel文件流
				MultipartFile patch = request.getFile("dataFile");
				
				// 原始数据
				List<Map<Object, Object>> listMap = ExcelUtil.importStatements(patch.getInputStream());
				
				// 数据验证
				boolean flag = checkExcelData(listMap, model);
				if(!flag){
					// 返回页面，提示验证结果
					return "pages/subject/import";
				}
				//开始导入商品
				int count=subjectManagerService.importSubject(listMap);
				if(count>0){
					LOGGER.info("导入专题信息成功");
				}
	    	}catch(Exception e){
	    		LOGGER.error("导入专题异常", e);
	    	}
	    	return "redirect:/subject/list";
	    }
	 //文件下载
	 @RequestMapping(value = "down", method = RequestMethod.GET)
    public String down(HttpServletRequest request,HttpServletResponse response,Model model){
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "template/subject-template.xls";
    	path = path.replace("\\", "/");
    	LOGGER.debug("模板文件路径：{}", new Object[]{path});
    	try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。

			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setHeader("Content-Type", "application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes(), "utf-8"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = response.getOutputStream();
			
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			LOGGER.error("下载专题导入模板出现异常",ex);
			return null;
		}
		return null;
    }
 /**
     * Excel数据校验
     * @param listMap
     * @param model
     * @return
     */
	private boolean checkExcelData(List<Map<Object, Object>> listMap, Model model) {
		boolean flag = true;
		Date startDate = null;
		Date endDate = null;
		if (listMap != null && !listMap.isEmpty()) {
			//名称
			Map<Object, Object> map = listMap.get(0);
			String name=(String)map.get(2);
			if(StringUtil.isEmpty(name)){
				flag=false;
				model.addAttribute("error","名称不能为空");
				return flag;
			}
			//类型
			Map<Object, Object> map2 = listMap.get(2);
			String displayStytle=(String)map2.get(2);
			if(StringUtil.isEmpty(displayStytle)){
				flag=false;
				model.addAttribute("error", "类型不能为空");
				return flag;
			}else{
				if(!displayStytle.equals("竖专题(大)") && !displayStytle.equals("横专题(小)")){
					flag=false;
					model.addAttribute("error", "填写的类型不对，请填竖专题(大) 或 横专题(小)");
					return flag;
				}
			}
			//专题图片
			Map<Object, Object> map4 = listMap.get(4);
			String out_pic=(String)map4.get(2);
			if(StringUtil.isEmpty(out_pic)){
				flag=false;
				model.addAttribute("error", "专题图片不能为空");
				return flag;
			}
			//开始时间
			Map<Object, Object> map5 = listMap.get(5);
			String beginTime=(String)map5.get(2);
			if(StringUtil.isEmpty(beginTime)){
				flag=false;
				model.addAttribute("error", "开始时间不能为空");
				return flag;
			}else{
				try{
					startDate=DateUtil.parseTime(beginTime);
				}catch(Exception e){
					flag=false;
					LOGGER.error("开始时间转换为日期是异常");
					model.addAttribute("error", "开始时间格式不正确");
					return flag;
				}
			}
			//结束时间
			Map<Object, Object> map6 = listMap.get(6);
			String endTime=(String)map6.get(2);
			if(StringUtil.isEmpty(endTime)){
				flag=false;
				model.addAttribute("error", "结束时间不能为空");
				return flag;
			}else{
				try{
					endDate=DateUtil.parseTime(endTime);
				}catch(Exception e){
					flag=false;
					LOGGER.error("结束时间转换为时间格式异常");
					model.addAttribute("error", "结束时间格式不正确");
					return flag;
				}
			}
			//标签
			Map<Object, Object> map7 = listMap.get(7);
			String labelName=(String)map7.get(2);
			if(StringUtil.isEmpty(labelName)){
				flag=false;
				model.addAttribute("error", "标签不能为空");
				return flag;
			}else{
				String[] labelIdsArr=labelName.split(",");
				for(int j=0;j<labelIdsArr.length;j++){
					Map<String,Object> params=new HashMap<String,Object>();
					params.put("title", labelIdsArr[j]);
					params.put("category", 1);
					long rabelId=subjectLabelService.findLabelIdByTitle(params);
					if(rabelId==0L){
						flag=false;
						model.addAttribute("error", "该"+labelIdsArr[j]+" 标签不存在");
						return flag;
					}
				}
			}
			if(listMap.size()<9){
				flag=false;
				model.addAttribute("error", "内容不能为空");
				return flag;
			}
			for (int i = 9; i < listMap.size(); i++) {
				Map<Object, Object> maps = listMap.get(i);
				String item=(String)maps.get(1);
				if(item.equals("图片")){
					String goodPath=(String)maps.get(2);
					if(StringUtil.isNotEmpty(goodPath)){
						 String path=ImageUtil.getShowImageAdd(goodPath);
						 File file = new File(path);
						 BufferedImage buff=null;
						 try {
							buff = ImageIO.read(file);
						} catch (IOException e) {
							model.addAttribute("error", "该"+goodPath+"图片不存在");
							LOGGER.error("该图片不存在:"+e);
						} 
					}
				}
				if(item.equals("商品")){
					String goodsSn=(String)maps.get(2);
					if(StringUtil.isNotEmpty(goodsSn)){
						Goods goods = goodsService.getGoodsBySn(goodsSn);
						if (goods == null) {
							flag=false;
							model.addAttribute("error", "该"+goodsSn+"走秀码不存在");
							return flag;
						}
					}
				}
			}
		}else{
			flag=false;
			model.addAttribute("error", "模板数据为空，请填写数据后重试！");
		}
		return flag;
	}
	/**
	 * 添加评论
	 */
	@RequestMapping(value = "addComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String addComment(HttpServletRequest request,Model model,Long subjectId,Long userId,
			String commentedUserId){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try{
			request.setCharacterEncoding("UTF-8");
			String comment=request.getParameter("content");
			String content=new String(comment.getBytes("ISO-8859-1"),"UTF-8");
			//判断评论内容的长度
			if(!content.matches(".{1,1000}")){
				 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setSmsg("评论长度限制在1-1000字符以内!");
			}
			//判断是否存在敏感词语
			boolean sensitiveFlag = sensitiveWordService.isSensitiveExists(content);
			if(sensitiveFlag) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setSmsg("您输入的内容包含不良信息!");
			}
			//添加评论
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("userId", userId);
			params.put("content", content);
			params.put("subjectId", subjectId);
			if(StringUtils.isNotBlank(commentedUserId)){
				params.put("commentedUserId", Long.parseLong(commentedUserId));
			}
			int i=subjectManagerService.addSubjectComment(params);
			if(i>0){
				json.setScode(JsonPackageWrapper.S_OK);
				json.setSmsg("评论成功");
			}else{
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setSmsg("评论失败");
			}
		}catch(Exception e){
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("系统发生异常！");
			LOGGER.error("添加评论异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
}
