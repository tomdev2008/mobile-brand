package com.xiu.show.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.xiu.common.web.utils.ImgTools;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.JsonUtils;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.common.web.view.PictureOfhead;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.service.ISubjectLabelService;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.CommentModel;
import com.xiu.show.core.model.OperateLogModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.PictureModel;
import com.xiu.show.core.model.PraiseModel;
import com.xiu.show.core.model.ReportModel;
import com.xiu.show.core.model.ShowCollectionDetailVO;
import com.xiu.show.core.model.ShowCollectionVO;
import com.xiu.show.core.model.ShowModel;
import com.xiu.show.core.model.ShowRecommendModel;
import com.xiu.show.core.model.TagModel;
import com.xiu.show.core.model.UserWhiteListModel;
import com.xiu.show.core.service.IOperateLogManagerService;
import com.xiu.show.core.service.IShowCollectionService;
import com.xiu.show.core.service.IShowManagerService;
import com.xiu.show.core.service.IShowTopicManagerService;
import com.xiu.show.web.service.IUserWhiteListService;

/**
 * 
* @Description: TODO(秀相关) 
* @author haidong.luo@xiu.com
* @date 2015年6月19日 下午4:18:15 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/show")
public class ShowController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShowController.class);

    @Autowired
    private IShowManagerService showManagerService;
    @Autowired
    private IOperateLogManagerService operateLogManagerService;
    @Autowired
    private IShowTopicManagerService showTopicManagerService;
    @Autowired
    private IUserWhiteListService userWhiteListService;
    
    @Autowired
    private ILabelService labelService; 
    
    @Value("${upload_pic_path}")
    private String upload_pic_path;
    
    @Autowired
    private IShowCollectionService showCollectionService;
    
    
    /*@Value("${images_domain_name}")
    private String IMAGES_DOMAIN_NAME;*/
    
    @Autowired
    private ISubjectLabelService subjectLabelService;
    
    
    public static String showImageURL = "http://6.xiustatic.com";
    
	/**
	 * 秀列表
	 * @param operUserName
	 * @param showId
	 * @param beginTime
	 * @param endTime
	 * @param showStatus
	 * @param recommendStatus
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "showList", method = RequestMethod.GET)
	public String showList(String brandTag,String showIds,String beginTime,String endTime,
			String itemTag,String showUserName,String showStatus,String checkStatus,String index,
			String praiseNumOrder,String commentNumOrder,String shareNumOrder,String reportedNumOrder,String goodType,
			String isRecommend,String isSelection,String topicTitle,String labelName,
			Page<?> page, Model model) {
		Map<String,Object> rmap = new HashMap<String,Object>();
		if(showIds!=null){
			showIds=showIds.replaceAll("\r\n",",");
		}
		rmap.put("brandTag", brandTag);
		rmap.put("showIds", showIds);
		rmap.put("beginTime", beginTime);
		rmap.put("endTime", endTime);
		rmap.put("itemTag", itemTag);
		rmap.put("praiseNumOrder", praiseNumOrder);
		rmap.put("commentNumOrder", commentNumOrder);
		rmap.put("shareNumOrder", shareNumOrder);
		rmap.put("reportedNumOrder", reportedNumOrder);
		rmap.put("showUserName", showUserName);
		rmap.put("isRecommend", isRecommend);
		rmap.put("isSelection", isSelection);
		rmap.put("topicTitle", topicTitle);
		rmap.put("labelName", labelName);
//		if(index!=null){//刚进入页面默认显示未审核
//			checkStatus="0";
//			showStatus="0";
//		}
		rmap.put("showStatus", showStatus);
		rmap.put("checkStatus", checkStatus);
		rmap.put("goodType", goodType);
		try{
		rmap=showManagerService.getShowList(rmap,page);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("查询秀列表异常！",e);
		}
		List<ShowModel> cs=(List<ShowModel>)rmap.get("resultInfo");
		model.addAttribute("brandTag", brandTag);
		if(showIds!=null){
			showIds=showIds.replaceAll(",","\r\n");
		}
		//查询白名单
    	List<UserWhiteListModel> whiteList=userWhiteListService.getUserWhiteList(1, 100);
		model.addAttribute("showIds", showIds);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("itemTag", itemTag);
		model.addAttribute("showUserName", showUserName);
		model.addAttribute("showStatus", showStatus);
		model.addAttribute("checkStatus", checkStatus);
		model.addAttribute("showlist", cs);
		model.addAttribute("goodType", goodType);
		model.addAttribute("praiseNumOrder", praiseNumOrder);
		model.addAttribute("commentNumOrder", commentNumOrder);
		model.addAttribute("shareNumOrder", shareNumOrder);
		model.addAttribute("reportedNumOrder", reportedNumOrder);
		model.addAttribute("isRecommend", isRecommend);
		model.addAttribute("isSelection", isSelection);
		model.addAttribute("topicTitle", topicTitle);
		model.addAttribute("labelName", labelName);
		model.addAttribute("whiteList",whiteList);
		return "pages/show/show/showList";
	}
    /**
     * 跳转添加页面
     */
	@RequestMapping(value="createUp",method=RequestMethod.GET)
	public String createUp(Model model){
		//白名单
		List<UserWhiteListModel> whiteList=userWhiteListService.getUserWhiteList(1, 100);
		//查询标签
		
		List brandList = showManagerService.getAllBrands();
		
		//查询标签
		Map<String,Object> labels=labelService.findCommonLabelList();
		model.addAttribute("newAddList", labels.get("newAddList"));
		model.addAttribute("historyList", labels.get("historyList"));
		
		model.addAttribute("whiteList",whiteList);
		model.addAttribute("brandList",brandList);
		return "pages/show/show/create";
	}
	/**
	 * 添加秀
	 * @throws ParseException 
	 */
	 @RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(HttpServletRequest request,Model model,String content,String publishTime,
			String[] labelIds,Integer orderSeq,String pictureList,String topicId) throws ParseException{
		 User user=AdminSessionUtil.getUser(request);
			Long userIds=user.getId();
			String userName=user.getUsername();	
			LOGGER.info("userName:"+userName+",userId:"+userIds+"后台添加秀");
		 Integer status=0;//操作状态
		 String msg=null;
		// json转bean
		 ShowModel showModel = JsonUtils.json2bean(pictureList, ShowModel.class);
		 if(publishTime!=null){
			 showModel.setPublishTime(publishTime);
		 }
		 showModel.setContent(content);
		 showModel.setOrderSeq(orderSeq);
		 Map<String,Object> map=showManagerService.addShow(showModel,topicId);
		 Boolean isSuccess=(Boolean)map.get("status");
		  msg=(String)map.get("msg");
		 if(isSuccess){
			 //添加标签
			 Long showId=(Long)map.get("showId");
				//如果有标签则绑定标签
					List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
					List<Long> labelId=new ArrayList<Long>();
					if(labelIds!=null&&labelIds.length>0){
						for (int j = 0; j < labelIds.length; j++) {
							LabelCentre lc=new LabelCentre();
							lc.setLabelId(ObjectUtil.getLong(labelIds[j]));
							labelCentres.add(lc);
							labelId.add(ObjectUtil.getLong(labelIds[j]));
						}
				    }	
					labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_SHOWER, showId, labelCentres);
					//添加标签关注秀
					Map<String,Object> params=new HashMap<String,Object>();
					params.put("labelCentres", labelId);
					params.put("showId", showId);
					showManagerService.addLabelShow(params);
			 status=1;
		 }
		 model.addAttribute("status", status);
		 model.addAttribute("msg", msg);
		 return "pages/show/show/create";
	}
	 /**
	  * 修改秀
	  */
	 @RequestMapping(value = "/update", method = RequestMethod.POST)
	 public String update(HttpServletRequest request,Model model,String content,Long id,String publishTime,
				String[] labelIds,Integer orderSeq,String pictureList,String topicId){
		 User user=AdminSessionUtil.getUser(request);
			Long userIds=user.getId();
			String userName=user.getUsername();	
			LOGGER.info("userName:"+userName+",userId:"+userIds+"后台修改秀");
			 Integer status=0;//操作状态
			 String msg=null;
			// json转bean
			 ShowModel showModel = JsonUtils.json2bean(pictureList, ShowModel.class);
			 if(publishTime!=null){
				 showModel.setPublishTime(publishTime);
			 }
			 showModel.setId(id);
			 showModel.setContent(content);
			 showModel.setOrderSeq(orderSeq);
			 showModel.setUserId(userIds);
			 Map<String,Object> map=showManagerService.updateShow(showModel,topicId);
			 Boolean isSuccess=(Boolean)map.get("status");
			  msg=(String)map.get("msg");
			 if(isSuccess){
				 //添加标签
				 Long showId=id;
					//如果有标签则绑定标签
						List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
						List<Long> labelId=new ArrayList<Long>();
						if(labelIds!=null&&labelIds.length>0){
							for (int j = 0; j < labelIds.length; j++) {
								LabelCentre lc=new LabelCentre();
								lc.setLabelId(ObjectUtil.getLong(labelIds[j]));
								labelCentres.add(lc);
								labelId.add(ObjectUtil.getLong(labelIds[j]));
							}
					    }	
						labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_SHOWER, showId, labelCentres);
						//添加标签关注秀
						Map<String,Object> params=new HashMap<String,Object>();
						params.put("labelCentres", labelId);
						params.put("showId", showId);
						showManagerService.addLabelShow(params);
				 status=1;
			 }
			 model.addAttribute("status", status);
			 model.addAttribute("msg", msg);
			 model.addAttribute("show", showModel);
		 return "pages/show/show/update";
	 }
	 /**
		 * 验证发布秀的数据
		 * @param showVO
		 * @return
		 */
		private Map<String, Object> validateShowData(ShowModel showVO) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<PictureModel> pictureList = showVO.getPictureList();
			if(pictureList == null || pictureList.size() == 0) {
				resultMap.put("result", false);
				resultMap.put("errorCode", "秀图片不能为空");
				return resultMap;
			}
			
			if(pictureList.size() > 5){
				resultMap.put("result", false);
				resultMap.put("errorCode", "秀图片不能超过5个");
				return resultMap;
			}
			
			for(PictureModel pictureVO : pictureList) {
				List<TagModel> tagList = pictureVO.getTagList();

				if(tagList != null && tagList.size() > 0) {
					int autoTagSize = 0;	//自动标签数量：商品标签
					int tagSize = 0;		//标签数量：普通标签和品牌标签
					
					for(TagModel tagVO : tagList) {
						Integer tagType = tagVO.getType();
						if(tagType != null && tagType == 3) {
							autoTagSize++;
						} else {
							tagSize++;
						}
					}
					
					// 标签数量判断
					if(autoTagSize > 5 || tagSize > 5) {
						resultMap.put("result", false);
						resultMap.put("errorCode", "标签不能超过5个");
						return resultMap;
					}
					
				}
			}
			resultMap.put("result", true);
			return resultMap;
		}
	/**
	 * 图片上传
	 */
	@RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
	public void uploadPicture(MultipartHttpServletRequest request,HttpServletResponse response){
		JSONObject obj = new JSONObject();
		PrintWriter out = null;
		try{
    		out = response.getWriter();
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
    		//图片检测
			MultipartFile patch = multipartRequest.getFile("file");
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/show" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
			}
			String picFile = upload_pic_path.trim() + picPath;
		//	String picFile = "F:/ten" + picPath;
			UploadPicUtils.isUploadPathExsit(picFile);
			File imageOut = new File(picFile); 
			try {
				patch.transferTo(imageOut);// 上传图片
				
				//图片缩放
		      //  PictureOfhead picOh = ImgTools.drawImage(600,600,imageOut, imageOuts);
				PictureOfhead picOh = ImgTools.drawImageAntetype(imageOut, imageOut);
		        obj.put("path",showImageURL+""+picPath);
		  //      obj.put("path","http://6.xiustatic.com/m/subject/2016/04/28/26c3eb7f-6d6a-434e-8af2-372c3f73899d.png");
				obj.put("width", picOh.getWidth());
				obj.put("height", picOh.getHeight());
				obj.put("fileName", picPath);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 out.print(obj);
    	}catch(Exception e){
    		LOGGER.error("秀添加图片上传失败："+e);
    	}
		finally{
			if(out!=null){
				out.close();
			}
		}
	}
	/**
	 * 保存裁剪图片
	 */
	@RequestMapping(value = "editPicture", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	public String editPicture(String idPicUrl,String leftWidth,String topWidth,String dragDivWidth,
			String dragDivHeight,Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		int leftWidthIn = 0;
        if(StringUtils.isNotBlank(leftWidth))
        {
        	leftWidthIn =Integer.valueOf(leftWidth);
        }
        int topWidthIn = 0;
        if(StringUtils.isNotBlank(topWidth))
        {
        	topWidthIn = Integer.valueOf(topWidth);
        }
        int dragDivWidthIn = 0;
        if(StringUtils.isNotBlank(dragDivWidth))
        {
        	dragDivWidthIn = Integer.valueOf(dragDivWidth);
        }
        int dragDivHeightIn = 0;
        if(StringUtils.isNotBlank(dragDivHeight))
        {
        	dragDivHeightIn = Integer.valueOf(dragDivHeight);
        }
        try{
        	if(idPicUrl==null || "".equals(idPicUrl))
        	{
        		json.setScode(JsonPackageWrapper.S_ERR);
        		json.setSmsg("系统发生异常,图片保存失败！");
	            model.addAttribute(Constants.JSON_MODEL__DATA, json);
	            return "0";
        	}
        	File imageIn = new File(upload_pic_path.trim() + "" + idPicUrl);
     //   	File imageIn = new File("F:/ten/m/show/2016/04/28/1b7d7510-01da-427b-89a3-4b41e4cc3c56.png");
        	 if(!imageIn.exists())
 	        {
 	        	json.setScode(JsonPackageWrapper.S_ERR);
 	            json.setSmsg("系统发生异常,图片保存失败！");
 	            model.addAttribute(Constants.JSON_MODEL__DATA, json);
 	            return "0";
 	        }
        	 // 生成名字
        	 String oldfileName=idPicUrl.substring(idPicUrl.lastIndexOf("/"));
        	 String newName=null;
        	 if(null!=oldfileName && !"".equals(oldfileName)){
        		 newName = UUID.randomUUID().toString()+ oldfileName.substring(oldfileName.lastIndexOf("."));
 			}
        	 idPicUrl = idPicUrl.replaceAll(oldfileName, newName);
 	        File imageOut = new File(upload_pic_path.trim() + "" + idPicUrl);
        //	 File imageOut = new File("F:/ten" + "/m/show/2016/04/28/1b7d7510-01da-427b-89a3-4b41e4cc3c57.png");
        	//图片裁剪
	        ImgTools.drawImage(leftWidthIn, topWidthIn, dragDivWidthIn, dragDivHeightIn, imageIn, imageOut);
	        json.setScode(JsonPackageWrapper.S_OK);
	        json.setSmsg(showImageURL+""+idPicUrl);
	      //  json.setSmsg("http://8.xiustatic.com/m/subject/2016/04/28/a336d8b3-be5a-42d9-9a18-7d44cf3dafdf.png");
	      //  json.setErrorCode("/m/subject/2016/04/28/a336d8b3-be5a-42d9-9a18-7d44cf3dafdf.png");
	        json.setErrorCode(idPicUrl);
        }catch(Exception e){
        	json.setScode(JsonPackageWrapper.S_ERR);
            json.setSmsg("系统发生异常！");
            LOGGER.error("裁剪图片失败:"+e);
        }
        model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	/**
	 * 设置、取消仅自己可见（单个）
	 */
	@RequestMapping(value="updateShowVisual", method = RequestMethod.GET,produces = "application/json", params = "format=json")
	public String updateShowVisual(HttpServletRequest request,String id,Integer isVisual,Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		User user=AdminSessionUtil.getUser(request);
		try{
			  if(null !=id){
				  int result=showManagerService.updateShowVisual(id, user.getId(), user.getUsername(), isVisual);
				  if(result>0){
					  json.setScode(JsonPackageWrapper.S_OK);
					  json.setData("设置成功！");
				  }else{
					  json.setScode(JsonPackageWrapper.S_ERR);
					  json.setData("设置操作失败！");
				  }
			  }else{
				  json.setScode(JsonPackageWrapper.S_ERR);
				  json.setData("参数不完整！");
			  }
			  
		  }catch(Exception e){
			  json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("秀设置仅自己可见失败！", e);
		  }
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	/**
	 * 批量删除秀
	 * @param request
	 * @param showIds
	 * @param resultType
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "deleteShowBatch", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String deleteShowBatch(HttpServletRequest request, String showIds,String resultType, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			User user=AdminSessionUtil.getUser(request);
			Long userId=user.getId();
			String userName=user.getUsername();
			try {
				if(null != showIds){
					Map<String,Object> params=new HashMap<String,Object>();
					params.put("showIds", showIds);
					params.put("resultType", resultType);
					params.put(ShowConstant.USER_ID, userId);
					params.put(ShowConstant.OPERATE_USER_NAME, userName);
					params.put(ShowConstant.DELETE_FLAG, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
					params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
					boolean issuccess = showManagerService.batchDeleteShowByIds(params);
					if (issuccess ) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("删除成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("删除失败！");
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("删除失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	 
	 /**
	  * 批量添加秀推荐
	  * @param request
	  * @param showIds
	  * @param beginTime
	  * @param endTime
	  * @param orderSeq
	  * @param model
	  * @return
	  */
		 @RequestMapping(value = "addShowRecommendBatch", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		 public String addShowRecommendBatch(HttpServletRequest request, String showIds,String beginTime,
		    		String channel,String recommendType,String orderSeq, Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
				User user=AdminSessionUtil.getUser(request);
				try {
					if(null != showIds&&null!=beginTime){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String endTime=DateUtil.getDateByDay(format.parse(beginTime),365);
						Map<String,Object> params=new HashMap<String,Object>();
						params.put("showIds", showIds);
						params.put("channel", channel);
						params.put("recommendType", recommendType);
						params.put(ShowConstant.USER_ID, user.getId());
						params.put("operUserName", user.getUsername());
						params.put("beginTime", beginTime);
						params.put("endTime", endTime);
						params.put("orderSeq",orderSeq);
						params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);//后台管理员操作
						boolean issuccess = showManagerService.addRecommendBatch(params);
						if (issuccess ) {
							json.setScode(JsonPackageWrapper.S_OK);
							json.setSmsg("推荐成功！");
						} else {
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setSmsg("推荐操作失败！");
						}
					}else{
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setSmsg("参数不完整！");
					}
				} catch (Exception e) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setSmsg("系统发生异常！");
					LOGGER.error("推荐失败！", e);
				}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
	
		 
		 
		 /**
		  * 批量审核
		  * @param request
		  * @param showIds
		  * @param checkFlag
		  * @param model
		  * @return
		  */
			 @RequestMapping(value = "updateShowCheckBatch", method = RequestMethod.GET, produces = "application/json", params = "format=json")
			    public String updateShowCheckBatch(HttpServletRequest request, String showIds,String status, Model model){
					JsonPackageWrapper json = new JsonPackageWrapper();
					  User user=AdminSessionUtil.getUser(request);
					   Long userId=user.getId();
					   String userName=user.getUsername();
					try {
						if(null != showIds){
							Map<String,Object> params=new HashMap<String,Object>();
							params.put("showIds", showIds);
							params.put("status", status);
							params.put(ShowConstant.USER_ID, userId);
							params.put(ShowConstant.OPERATE_USER_NAME, userName);
							params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);//后台管理员操作
							boolean issuccess = showManagerService.updateShowCheckStatusBatch(params);
							if (issuccess ) {
								json.setScode(JsonPackageWrapper.S_OK);
								json.setData("审核成功！");
							} else {
								json.setScode(JsonPackageWrapper.S_ERR);
								json.setData("审核操作失败！");
							}
						}else{
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setData("参数不完整！");
						}
					} catch (Exception e) {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("系统发生异常！");
						LOGGER.error("审核失败！", e);
					}
					model.addAttribute(Constants.JSON_MODEL__DATA, json);
					return "";
			    }
		
	   /**
		 * 秀详情
		 * @param showId
		 * @param page
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "showInfo", method = RequestMethod.GET)
		public String showInfo(String showId, Model model) {	 
			Map<String,Object> rmap = new HashMap<String,Object>();
			try{
				rmap.put("showId", showId);
				rmap=showManagerService.getShowInfo(rmap);
				}catch(Exception e){
					e.printStackTrace();
					LOGGER.error("查询秀详情异常！",e);
				}
			ShowModel showObj=(ShowModel)rmap.get("resultInfo");
			model.addAttribute("showId", showId);
			model.addAttribute("show", showObj);
			return "pages/show/show/showInfo";
		}
		/**
		 * 跳转秀详情修改页
		 */
		@RequestMapping(value = "showInfoUpdate", method = RequestMethod.GET)
		public String showInfoUpdate(String showId, Model model){
			Map<String,Object> rmap = new HashMap<String,Object>();
			try{
				rmap=showManagerService.findShowInfoByShowId(showId);
				// 查询已经添加的标签
				List<Label> label=labelService.findLabelListByObjectIdAndType(Long.parseLong(showId),  GlobalConstants.LABEL_TYPE_SHOWER);
				//品牌
				List brandList = showManagerService.getAllBrands();
				model.addAttribute("updateLabelList", label);
				// 查询常用的标签（热门和新加的）
				Map<String,Object> labels=labelService.findCommonLabelList();
				model.addAttribute("newAddList", labels.get("newAddList"));
				model.addAttribute("historyList", labels.get("historyList"));
				model.addAttribute("brandList",brandList);
			}catch(Exception e){
				LOGGER.error("查询秀基本信息异常！："+e);
			}
			ShowModel showObj=(ShowModel)rmap.get("resultInfo");
			model.addAttribute("showId", showId);
			model.addAttribute("show", showObj);
			return "pages/show/show/update";
		}
		
		/**
		 * 秀赞详情列表
		 * @param showId
		 * @param page
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "showPraiseList", method = RequestMethod.GET)
		public String showPraiseList(String showId, Page<?> page, Model model) {	 
			Map<String,Object> rmap = new HashMap<String,Object>();
			try{
				rmap.put("showId", showId);
				rmap=showManagerService.getShowPraiseInfoList(rmap,page);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("查询秀赞详情列表异常！",e);
			}
			List<PraiseModel>  praiseList=(List<PraiseModel> )rmap.get("resultInfo");
			model.addAttribute("showId", showId);
			model.addAttribute("praiseList", praiseList);
			return "pages/show/show/showInfoPraiseList";
		}
		
		/**
		 * 秀评论详情列表
		 * @param showId
		 * @param page
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "showCommentList", method = RequestMethod.GET)
		public String showCommentList(String showId, Page<?> page, Model model) {	 
			Map<String,Object> rmap = new HashMap<String,Object>();
			try{
				rmap.put("showId", showId);
				rmap=showManagerService.getShowCommentInfoList(rmap,page);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("查询秀评论列表异常！",e);
			}
			List<CommentModel> commentList=(List<CommentModel>)rmap.get("resultInfo");
			model.addAttribute("showId", showId);
			model.addAttribute("commentList", commentList);
			return "pages/show/show/showInfoCommentList";
		}
		
		/**
		 * 秀举报详情列表
		 * @param showId
		 * @param page
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "showReportList", method = RequestMethod.GET)
		public String showReportList(String showId, Page<?> page, Model model) {	 
			Map<String,Object> rmap = new HashMap<String,Object>();
			try{
				rmap.put("showId", showId);
				rmap=showManagerService.getShowReportInfoList(rmap,page);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("查询秀举报列表异常！",e);
			}
			List<ReportModel> reportList=(List<ReportModel>)rmap.get("resultInfo");
			model.addAttribute("showId", showId);
			model.addAttribute("reportList", reportList);
			return "pages/show/show/showInfoReportList";
		}
		/**
		 * 秀日志详情列表
		 * @param showId
		 * @param page
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "showLogsList", method = RequestMethod.GET)
		public String showLogsList(String showId, Page<?> page, Model model) {	 
			Map<String,Object> rmap = new HashMap<String,Object>();
			try{
				rmap.put("objectId", showId);
				rmap.put("type", ShowConstant.SHOW_OPERATE_OBJECT_SHOW);
				rmap=operateLogManagerService.getOperateLogList(rmap,page);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error(" 查询操作日志异常！",e);                     
			}
			List<OperateLogModel> ops=(List<OperateLogModel>)rmap.get("resultInfo");
			model.addAttribute("showId", showId);
			model.addAttribute("opLoglist", ops);
			return "pages/show/show/showInfoLogsList";
		}
	 
	/**
	 * 关注推荐秀列表
	 * @param operUserName
	 * @param showId
	 * @param beginTime
	 * @param endTime
	 * @param showStatus
	 * @param recommendStatus
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listRecommend", method = RequestMethod.GET)
	public String listRecommend(String operUserName,String showId,String beginTime,String endTime,String updateBeginTimeStr,String updateEndTimeStr,
			String showStatus,String recommendStatus,String recommendOrder,Page<?> page, Model model) {
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("operUserName", operUserName);
		rmap.put("showId", showId);
		rmap.put("showStatus", showStatus);
		rmap.put("beginTime", beginTime);
		rmap.put("endTime", endTime);
		rmap.put("updateBeginTime", updateBeginTimeStr);
		rmap.put("updateEndTime", updateEndTimeStr);
		rmap.put("recommendStatus", recommendStatus);
		rmap.put("recommendOrder", recommendOrder);
		rmap.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);//后台管理员操作
		try{
		rmap=showManagerService.getRecommendShowList(rmap,page);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("查询关注推荐秀异常！",e);
		}
		List<ShowRecommendModel> cs=(List<ShowRecommendModel>)rmap.get("resultInfo");
		model.addAttribute("operUserName", operUserName);
		model.addAttribute("showId", showId);
		model.addAttribute("showStatus", showStatus);
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("updateBeginTimeStr", updateBeginTimeStr);
		model.addAttribute("updateEndTimeStr", updateEndTimeStr);
		model.addAttribute("recommendStatus", recommendStatus);
		model.addAttribute("showlist", cs);
		model.addAttribute("recommendOrder", recommendOrder);
		return "pages/show/show/recommendList";
	}
	
	/**
	 * 更新进行中的推荐为开始或者暂停状态
	 * @param request
	 * @param id
	 * @param resultType
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "updateRecommendFlag", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String updateRecommendFlag(HttpServletRequest request, String showId,String recommendFlag, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			User user=AdminSessionUtil.getUser(request);
			Long userId=user.getId();
			String userName=user.getUsername();
			try {
				if(null != showId&&null!=recommendFlag){
					Map<String,Object> params=new HashMap<String,Object>();
					params.put(ShowConstant.SHOW_ID, showId);
					params.put(ShowConstant.USER_ID, userId);
					params.put("operUserId", userId);
					params.put("operUserName", userName);
					params.put("recommendFlag", recommendFlag);
					params.put(ShowConstant.SHOW_OPERATE_USER_TYPE,  ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);//管理员
					boolean issuccess = showManagerService.updateRecommendFlag(params);
					if (issuccess ) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("修改成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("修改操作失败！");
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
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
		 * 更新推荐秀的推荐时间
		 * @param request
		 * @param id
		 * @param resultType
		 * @param model
		 * @return
		 */
		 @RequestMapping(value = "updateRecommendTime", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String updateRecommendTime(HttpServletRequest request, String showIds,String beginTime,String orderSeq,
		    		String channel,Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
				User user=AdminSessionUtil.getUser(request);
				Long userId=user.getId();
				String userName=user.getUsername();				
				try {
					if(null != showIds&&null!=beginTime){
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String endTime=DateUtil.getDateByDay(format.parse(beginTime),365);
						Map<String,Object> params=new HashMap<String,Object>();
						params.put("showIds", showIds);
						params.put(ShowConstant.USER_ID, userId);
						params.put("operUserName", userName);
						params.put("beginTime", beginTime);
						params.put("endTime", endTime);
						params.put("orderSeq", orderSeq);
						params.put("channel", channel);
						params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
						boolean issuccess = showManagerService.updateRecommendTime(params);
						if (issuccess ) {
							json.setScode(JsonPackageWrapper.S_OK);
							json.setData("修改成功！");
						} else {
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setData("修改操作失败！");
						}
					}else{
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("参数不完整！");
					}
				} catch (Exception e) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("系统发生异常！");
					LOGGER.error("修改失败！", e);
				}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
		 
		 
		 @RequestMapping(value = "updateShowTopicSelection", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String updateShowTopicSelection(HttpServletRequest request, String showIds,String topicId,String flag, Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
				User user=AdminSessionUtil.getUser(request);
				Long userId=user.getId();
				String userName=user.getUsername();				
				try {
					if(null != showIds&&null!=topicId&&null!=flag){
						Map<String,Object> params=new HashMap<String,Object>();
						params.put("showIds", showIds);
						params.put("operUserId", userId);
						params.put("topicId", topicId);
						params.put("flag", flag);
						params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
						boolean issuccess = false;
						if(flag.equals("1")){//设为精选
							 params.put(ShowConstant.SHOW_OPERATE_REMARK,"后台管理员精选该话题秀");
							 issuccess = showTopicManagerService.selectionTopicShowBatch(params);
						}else{//取消精选
							 params.put(ShowConstant.SHOW_OPERATE_REMARK,"后台管理员取消精选该话题秀");
							 issuccess = showTopicManagerService.cancelSelectionTopicShowBatch(params);
						}
						if (issuccess ) {
							json.setScode(JsonPackageWrapper.S_OK);
							json.setData("修改成功！");
						} else {
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setData("修改操作失败！");
						}
					}else{
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("参数不完整！");
					}
				} catch (Exception e) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("系统发生异常！");
					LOGGER.error("修改失败！", e);
				}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
		 
		 
		 @RequestMapping(value = "checkShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		    public String checkShow(HttpServletRequest request, String showId, Model model){
				JsonPackageWrapper json = new JsonPackageWrapper();
					if(null != showId){
						Map<String,Object> params=new HashMap<String,Object>();
						params.put("showId", showId);
						boolean issuccess = false;
						try {
							 issuccess = showManagerService.checkShow(params);
							 if(issuccess){
									json.setScode(JsonPackageWrapper.S_OK);
							 }else{
									json.setScode("2");
							 }
						}catch (Exception e) {
							json.setScode(JsonPackageWrapper.S_ERR);
							json.setData("系统发生异常！");
							LOGGER.error("查询秀失败！", e);
						}
					}else{
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("参数不完整！");
					}
				model.addAttribute(Constants.JSON_MODEL__DATA, json);
				return "";
		    }
	/**
	 * 查询所以品牌标签
	 */
	@RequestMapping(value = "getAllBrands", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String getAllBrands(HttpServletRequest request, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try{
			List brandList = showManagerService.getAllBrands();
			json.setScode(JsonPackageWrapper.S_OK);
			json.setData(brandList);
		}catch(Exception e){
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("系统发生异常！");
			LOGGER.error("查询品牌标签异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/***********秀集合*******************************/
	 
	 /**
		 * 秀集合列表
		 * @param page
		 * @param model
		 * @return
		 */
		@RequestMapping(value = "showCollectionList", method = RequestMethod.GET)
		public String showCollectionList(String showCollectionId,String name,String status,
				Page<?> page, Model model) {
			Map<String,Object> rmap = new HashMap<String,Object>();
			rmap.put("showCollectionId", showCollectionId);
			rmap.put("name", name);
			rmap.put("status", status);
			try{
				rmap=showCollectionService.getShowCollectionVOListByCondition(rmap,page);
			}catch(Exception e){
				e.printStackTrace();
				LOGGER.error("查询秀列表异常！",e);
			}
			List<ShowCollectionVO> list=(List<ShowCollectionVO>)rmap.get("resultInfo");
	    	model.addAttribute("showCollectionList",list);
	    	model.addAttribute("page", page);
	    	model.addAttribute("name", name);
	    	model.addAttribute("status", status);
	    	model.addAttribute("showCollectionId", showCollectionId);
			return "pages/show/show/showCollectionList";
		}
		
		/**
	     * 跳转新增页面
	     */ 
	    @RequestMapping(value = "goAddShowCollection", method = RequestMethod.GET)
	    public String goAddShowCollection( Model model) {
	        return "pages/show/show/showCollectionAdd";
	    }
	 
	    
	    /**
	     * 添加
	     * @param request
	     * @param subject
	     * @param model
	     */
	    @RequestMapping(value = "saveShowCollection", method = RequestMethod.POST)
	    public String saveShowCollection(MultipartHttpServletRequest request,String name,String detailText,Model model) {
	    	ShowCollectionVO vo=new ShowCollectionVO();
	    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    	
	    	long id=showCollectionService.queryIdBySeq();
			/******文件上传******/
			MultipartFile patch = multipartRequest.getFile("listImgPath");
			
			String fileName = patch.getOriginalFilename();
			String listImgPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				listImgPath = "/m/show" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				vo.setListImgPath(listImgPath);
			}
			String picFile = upload_pic_path.trim() + listImgPath;
			UploadPicUtils.isUploadPathExsit(picFile);
			try {
				patch.transferTo(new File(picFile));// 上传文件
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//图片2
			MultipartFile detailPatch = multipartRequest.getFile("detailImgPath");
			
			String detailfileName = detailPatch.getOriginalFilename();
			String detailImgPath = "";
			if(null!=detailfileName && !"".equals(detailfileName)){
				detailfileName = UUID.randomUUID().toString()+ detailfileName.substring(detailfileName.lastIndexOf("."));
				detailImgPath = "/m/show" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + detailfileName;
				vo.setDetailImgPath(detailImgPath);
			}
			
			String detailpicFile = upload_pic_path.trim() + detailImgPath;
			UploadPicUtils.isUploadPathExsit(detailpicFile);
			try {
				detailPatch.transferTo(new File(detailpicFile));// 上传文件
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			/****************************/
			vo.setShowCollectionId(id);
			vo.setName(name);
			vo.setStatus(1L);
			vo.setDetailText(detailText);
			
			showCollectionService.save(vo);
	    	return showCollectionList(null, null, null, new Page(), model);
	    }
	    
	    
	    /**
	     * 进入更新
	     * @param request
	     * @param subject
	     * @param model
	     */
	    @RequestMapping(value = "goUpdateShowCollection", method = RequestMethod.GET)
	    public String goUpdateShowCollection(String id,Model model) {
	    	ShowCollectionVO vo=showCollectionService.queryShowCollectionVOById(Long.parseLong(id));
	    	model.addAttribute("showCollectionVO",vo);
	    	return "pages/show/show/showCollectionUpdate";
	    }
	    
	    /**
	     * 更新
	     * @param request
	     * @param subject
	     * @param model
	     */
	    @RequestMapping(value = "updateShowCollection", method = RequestMethod.POST)
	    public String updateShowCollection(MultipartHttpServletRequest request,String showCollectionId,String name,String detailText,Model model) {
	    	
	    	ShowCollectionVO vo=new ShowCollectionVO();
	    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	    	
			/******文件上传******/
			MultipartFile patch = multipartRequest.getFile("listImgPath");
			
			String fileName = patch.getOriginalFilename();
			String listImgPath = "";
			if(StringUtils.isNotBlank(fileName)){
				
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				listImgPath = "/m/show" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				vo.setListImgPath(listImgPath);
				
				String picFile = upload_pic_path.trim() + listImgPath;
				UploadPicUtils.isUploadPathExsit(picFile);
				try {
					patch.transferTo(new File(picFile));// 上传文件
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
			
			MultipartFile detailPatch = multipartRequest.getFile("detailImgPath");
			
			String detailfileName = detailPatch.getOriginalFilename();
			String detailImgPath = "";
			if(StringUtils.isNotBlank(detailfileName)){
				
				detailfileName = UUID.randomUUID().toString()+ detailfileName.substring(detailfileName.lastIndexOf("."));
				detailImgPath = "/m/show" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + detailfileName;
				vo.setDetailImgPath(detailImgPath);
				
				String detailpicFile = upload_pic_path.trim() + detailImgPath;
				UploadPicUtils.isUploadPathExsit(detailpicFile);
				try {
					detailPatch.transferTo(new File(detailpicFile));// 上传文件
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			vo.setDetailText(detailText);
			vo.setShowCollectionId(Long.valueOf(showCollectionId));
			vo.setName(name);
			showCollectionService.update(vo);
			
			return showCollectionList(null, null, null, new Page(), model);
	    }
	    
	    /**
	     * 显示隐藏秀集合
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "updateShowCollectionStatus", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String updateShowCollectionStatus(String  showCollectionId,String status,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		ShowCollectionVO vo=new ShowCollectionVO();
	    		vo.setShowCollectionId(Long.valueOf(showCollectionId));
	    		vo.setStatus(Long.valueOf(status));
	    		showCollectionService.update(vo);
	    		json.setScode(JsonPackageWrapper.S_OK);
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("更新秀集合失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    
	    /**
	     * 显示隐藏秀集合里面的秀
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "updateShowCollectionDetailStatus", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String updateShowCollectionDetailStatus(String  detailId,String status,String seq,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		ShowCollectionDetailVO vo=new ShowCollectionDetailVO();
	    		vo.setDetailId(Long.valueOf(detailId));
	    		vo.setStatus(StringUtils.isNotBlank(status)? Long.valueOf(status):null);
	    		vo.setSeq(StringUtils.isNotBlank(seq)? Long.valueOf(seq):null);
	    		showCollectionService.updateShowCollectionDetail(vo);
	    		json.setScode(JsonPackageWrapper.S_OK);
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("更新秀集合失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    
	    
	    
	    /**
	     * 进入添加秀集合
	     * @param request
	     * @param subject
	     * @param model
	     */
	    @RequestMapping(value = "showCollectionAddShow", method = RequestMethod.GET)
	    public String showCollectionAddShow(String showCollectionId,String  act_id,
	    		String showUserName,String labelName,Model model,Page<?> page) {
	    	ShowCollectionVO vo=showCollectionService.queryShowCollectionVOById(Long.parseLong(showCollectionId));
	    	
	    	//多个秀ID,
	    	//act_id过滤掉非数字和逗号字符
	    	Map<String,Object> map=new HashMap<String,Object>();
			if(StringUtils.isNotBlank(act_id)) {
				String regEx = "[^0-9,]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(act_id);
				act_id = m.replaceAll("").trim(); //替换字符
				if(act_id.equals(",")) {
					act_id = "";
				}
				map.put("detailIds", act_id.split(","));
			}
			map.put("showUserName", showUserName);
			map.put("labelName", labelName);
			
			Map<String,Object> result =showCollectionService.queryAllShowModel(map,page);
	    	model.addAttribute("queryShowList",(List<ShowCollectionDetailVO>)result.get("resultInfo"));
	    	
	    	model.addAttribute("showCollectionVO",vo);
	    	model.addAttribute("showDetailList", vo.getListShowCollectionDetail());
	    	model.addAttribute("activityCounts", vo.getListShowCollectionDetail().size());
	    	model.addAttribute("showUserName", showUserName);
	    	model.addAttribute("labelName", labelName);
	    	model.addAttribute("act_id", act_id);
	    	model.addAttribute("page", page);
	    	return "pages/show/show/showCollectionAddShow";
	    }
	    
	    
	    /**
	     * 删除秀集合里面的秀
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "deleteShowCollectionDetail", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String deleteShowCollectionDetail(String  detailIds,String showCollectionId,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		String [] st=detailIds.split(",");
	    		for(String detailId:st){
	    			if(StringUtils.isNotBlank(detailId)){
	    				ShowCollectionDetailVO vo=new ShowCollectionDetailVO();
			    		vo.setDetailId(StringUtils.isNotBlank(detailId)? Long.valueOf(detailId):null);
			    		vo.setShowCollectionId(StringUtils.isNotBlank(showCollectionId)? Long.valueOf(showCollectionId):null);
			    		showCollectionService.deleteShowCollectionDetail(vo);
	    			}
	    		}
	    		
	    		json.setScode(JsonPackageWrapper.S_OK);
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("更新秀集合失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    /**
	     * 添加秀集合里面的秀
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "addShowCollectionDetail", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String addShowCollectionDetail(String  showIds,String showCollectionId,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		String [] st=showIds.split(",");
		    	Map<String,Object> map=showCollectionService.addShowCollectionDetail(Long.valueOf(showCollectionId),st);
	    		Boolean status=(Boolean)map.get("status");
		    	if(status){
		    		json.setScode(JsonPackageWrapper.S_OK);
	    		}else{
	    			String msg=(String)map.get("msg");
	    			json.setScode("2");
	    			json.setSmsg(msg);
	    		}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setSmsg("系统发生异常！");
	            LOGGER.error("更新秀集合失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    /**
	     * 检查秀集合
	     * @param showCollectionId
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "checkShowCollectionId", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkShowCollectionId(Long showCollectionId, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			try {
				showCollectionId=ObjectUtil.getLong(showCollectionId, null);
				if(showCollectionId!=null){
					ShowCollectionVO showCollectionVO = showCollectionService.queryShowCollectionVOById(showCollectionId);
						if (showCollectionVO != null) {
							json.setData(showCollectionVO);
							json.setSmsg("检查秀集合ID成功！");
						} else {
							json.setScode("2");
							json.setData("该秀集合ID不存在！");
						}
				}else{
					json.setScode("2");
					json.setData("秀集合ID不能为空！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("check秀集合失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	    
	    
}
