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
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.LabelRelation;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Subject;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.utils.ImageUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.show.core.model.PictureModel;
import com.xiu.show.core.model.ShowModel;
import com.xiu.show.core.service.IShowManagerService;
import com.xiu.wap.web.service.IDataTaskManagerService;
import com.xiu.wap.web.service.ISearchService;
/**
 * 专题标签
 * @author Administrator
 *
 */
@AuthRequired
@Controller
@RequestMapping(value = "/label")
public class LabelController {	

		//日志
	    private static final XLogger LOGGER = XLoggerFactory.getXLogger(LabelController.class);
	    @Autowired
	    private ILabelService labelService;
	    
	    @Value("${upload_pic_path}")
	    private String upload_pic_path;
	    
	    @Autowired
	    private IShowManagerService showManagerService;
	    
	    @Autowired
	    private ISearchService searchService;
	    @Autowired
	    private IDataTaskManagerService dataTaskManagerService;
	    /**
	     * 查询标签列表
	     * @param page
	     * @param model
	     */
	    @SuppressWarnings("unchecked")
		@RequestMapping(value = "list", method = RequestMethod.GET)
	    public String list(String labelGroup,String labelId,String labelName,String opUser,Integer display,Page<Label> page, Model model) {
    		List<Label> labelList=new ArrayList<Label>();
    		Map<String,Object> ramp=new HashMap<String,Object>();
    		Long labelIdL=ObjectUtil.getLong(labelId, null);
    		Integer labelGroupI=ObjectUtil.getInteger(labelGroup, null);
    		ramp.put("display", display);
    		ramp.put("opUser", opUser);
    		ramp.put("labelId", labelIdL);
    		ramp.put("labelName", labelName);
    		ramp.put("labelGroup", labelGroupI);
    		page.setPageNo(page.getPageNo()==0 ? 1:page.getPageNo());
    		Map<String,Object> map=labelService.getLabelList(ramp, page);
    		labelList=(List<Label>) map.get("labelList");
    		LOGGER.info("后台查询标签列表成功："+labelList);
    		model.addAttribute("opUser", opUser);
    		model.addAttribute("labelGroup", labelGroupI);
    		model.addAttribute("labelId", labelId);
    		model.addAttribute("labelName", labelName);
    		model.addAttribute("display", display);
    		model.addAttribute("labelList", labelList);
	        return "pages/label/list";
	    }
	    
	    /**
	     * 跳转添加标签页面
	     */ 
	    @RequestMapping(value = "bfAdd", method = RequestMethod.GET)
	    public String bfAdd( Model model) {
	        return "pages/label/create";
	    }
	    
	    /**
	     * 添加标签
	     * @param request
	     * @param model
	     */
	    @RequestMapping(value = "save", method = RequestMethod.POST)
	    public String save(MultipartHttpServletRequest request,Label subjectLabel,Model model) {
	    	String msg=null;
	    	Integer status=0;
	    	User user=AdminSessionUtil.getUser(request);
	    	subjectLabel.setOperator(user.getUsername());
	    	
	    	//图片检测
			MultipartFile patch = request.getFile("label_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/label" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				subjectLabel.setImgUrl(picPath);// 图片上传路径
			}
			if(subjectLabel.getTitle()!=null){
				subjectLabel.setTitle(subjectLabel.getTitle().trim());
			}
	    	Map<String,Object> map=labelService.addSubjectLabel(subjectLabel);
	    	Boolean isSuccess=(Boolean)map.get("status");
	    	 msg=(String)map.get("msg");
			 if(isSuccess){
	    		 if(subjectLabel.getImgUrl()!=null&&!subjectLabel.getImgUrl().equals("")){
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
	    	 model.addAttribute("isSuccess", status);
	    	 model.addAttribute("msg", msg);
	    	 model.addAttribute("subjectLabel", subjectLabel);
	    	return "pages/label/create";
	    }
	    
	    
	    /**
	     * 跳转标签修改页面
	     */ 
	    @RequestMapping(value = "bfUpdate", method = RequestMethod.GET)
	    public String bfUpdate(Long labelId, Model model) {
	    	Label subjectLabel=new Label();
	    	subjectLabel=labelService.getLabelInfo(labelId);
	    	if(subjectLabel.getImgUrl()!=null&&!subjectLabel.getImgUrl().equals("")){
	    		subjectLabel.setImgUrl(ImageUtil.getShowimageUrl(subjectLabel.getImgUrl()));
	    	}

	    	model.addAttribute("subjectLabel", subjectLabel);
	        return "pages/label/update";
	    }
	    
	    /**
	     * 修改标签
	     * @param request
	     * @param model
	     */
	    @RequestMapping(value = "update", method = RequestMethod.POST)
	    public String update(MultipartHttpServletRequest request,Label subjectLabel,
	    		Model model) {
	    	String msg=null;
	    	Integer status=0;
	    	//图片检测
			MultipartFile patch = request.getFile("label_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/label" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				subjectLabel.setImgUrl(picPath);// 图片上传路径
			}
	    	
	    	User user=AdminSessionUtil.getUser(request);
	    	subjectLabel.setOperator(user.getUsername());
	    	if(subjectLabel.getTitle()!=null){
				subjectLabel.setTitle(subjectLabel.getTitle().trim());
			}
	    	Map<String,Object> map=labelService.updateLabel(subjectLabel);
	    	Boolean isSuccess=(Boolean)map.get("status");
	    	 msg=(String)map.get("msg");
	    	 if(isSuccess){
	    		 if(subjectLabel.getImgUrl()!=null&&!subjectLabel.getImgUrl().equals("")){
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
	    	 model.addAttribute("isSuccess", status);
	    	 model.addAttribute("msg", msg);
	    	 model.addAttribute("subjectLabel", subjectLabel);
	    	return "pages/label/update";
	    }
	    
	   
		
	    /**
	     * 删除标签
	     * @param labelId
	     * @param model
	     */
	    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String delete(HttpServletRequest request,Long labelId,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try{
	    		Map params =new HashMap();
	    		params.put("labelId", labelId);
	    	   	User user=AdminSessionUtil.getUser(request);
	    	   	params.put("operator", user.getUsername());
	    		int i=labelService.deleteLabel(params);
		    	if(i>0){
		    		json.setScode(JsonPackageWrapper.S_OK);
		             json.setData("删除成功！");
		    	}else{
		    		json.setScode(JsonPackageWrapper.S_ERR);
		            json.setData("删除失败！");
		    	}
	    	}catch(Exception e){
	    		json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("删除失败！：", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    /**
	     * 删除标签
	     * @param labelId
	     * @param model
	     */
	    @RequestMapping(value = "getLabelIdByName", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String delete(String labelName,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try{
	    		Long labelId=labelService.getLabelIdByName(labelName);
	    		if(labelId!=null&&labelId>0){
	    			json.setScode(JsonPackageWrapper.S_OK);
	    			json.setData(labelId);
	    		}else{
	    			json.setScode(JsonPackageWrapper.S_ERR);
	    			json.setData("该标签不存在！");
	    		}
	    	}catch(Exception e){
	    		json.setScode(JsonPackageWrapper.S_ERR);
	    		json.setData("系统发生异常！");
	    		LOGGER.error("删除失败！", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	    	return "";
	    }
	    
	    /**
	     * 查询关联标签列表
	     * @return
	     */
		@RequestMapping(value = "getRelationLabelList", method = RequestMethod.GET)
	    public String getRelationLabelList(Long labelId,String raletionLabelName,Integer type,Page<Label> page, Model model){
	    	Map ramp =new HashMap();
	    	type =ObjectUtil.getInteger(type, GlobalConstants.LABEL_TYPE_SHOWER);//默认为秀客
	    	ramp.put("type", type);
	    	ramp.put("labelId", labelId);
	    	ramp.put("raletionLabelName", raletionLabelName);
	    	page.setPageNo(page.getPageNo()==0 ? 1:page.getPageNo());
	    	Map<String, Object>  resultMap=labelService.getLabelRelationDateList(ramp, page);
	    	List<LabelRelation> labelRelationList=(List<LabelRelation>) resultMap.get("labelRelationList");
	 
	    	String labelName=(String)resultMap.get("labelName");
    		model.addAttribute("labelRelationList", labelRelationList);
    		model.addAttribute("labelName", labelName);
    		model.addAttribute("raletionLabelName", raletionLabelName);
    		model.addAttribute("type", type);
    		model.addAttribute("labelId", labelId);
	    	return "pages/label/labelRelationList";
	    }
	    
	    /**
	     * 查询标签业务数据列表
	     * @return
	     */
		@RequestMapping(value = "getLabelServiceDateList", method = RequestMethod.GET)
	    public String getLabelServiceDateList(Long labelId,Integer type,Long serviceId,String serviceName,Page<Label> page, Model model){
	    	Map ramp =new HashMap();
	    	type =ObjectUtil.getInteger(type, GlobalConstants.LABEL_TYPE_SHOWER);//默认为秀客
	    	ramp.put("type", type);
	    	ramp.put("labelId", labelId);
	    	ramp.put("serviceId", serviceId);
	    	page.setPageNo(page.getPageNo()==0 ? 1:page.getPageNo());
	    	Map<String, Object>  resultMap=labelService.getLabelServiceDateList(ramp, page);
	    	List<LabelCentre> labelCentreList=(List<LabelCentre>) resultMap.get("labelCentreList");
	    	List<Long> ids=new ArrayList<Long>();
	    	for (LabelCentre lc:labelCentreList) {
	    		ids.add(lc.getObjectId());
			}
	    	if(ids.size()>0){
		    	if(type.equals(GlobalConstants.LABEL_TYPE_SHOWER)){
			    	//查询业务特定数据(标题 图片)
			    	 List<ShowModel> shows=showManagerService.getShowsInfoByIds(ids);
				    for (LabelCentre lc:labelCentreList) {
					  for (ShowModel show:shows) {
						if(lc.getObjectId().equals(show.getId())){
							List<PictureModel>  ps=show.getPictureList();
							lc.setObjectImg(ps.get(0).getOriginalPicUrl());
							String oname=show.getContent();
								if(oname!=null&&oname.length()>20){
									lc.setObjectName(oname.substring(0,20)+"...");
								}
						}
					  }
					}
		    	}
	    	}
	    	 
	    	String labelName=(String)resultMap.get("labelName");
    		model.addAttribute("labelCentreList", labelCentreList);
    		model.addAttribute("labelName", labelName);
    		model.addAttribute("type", type);
    		model.addAttribute("labelId", labelId);
    		model.addAttribute("serviceId", serviceId);
    		model.addAttribute("serviceName", serviceName);
	    	return "pages/label/labelServiceList";
	    }
		
		
	    
	    /**
	     * 修改标签业务数据的排序
	     * @param labelObjectId
	     * @param type
	     * @param serviceOrder
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "updateLabelServiceOrder", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String updateLabelServiceOrder(Long labelObjectId,Integer type,Integer serviceOrder,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try{
	    		Map params=new HashMap();
	    		params.put("labelObjectId", labelObjectId);
	    		params.put("type", type);
	    		params.put("serviceOrder", serviceOrder);
	    		Boolean isSuccess=labelService.updateLabelServiceOrder(params);
	    		if(isSuccess){
	    			json.setScode(JsonPackageWrapper.S_OK);
	    		}else{
	    			json.setScode(JsonPackageWrapper.S_ERR);
	    			json.setData("修改失败 ！");
	    		}
	    	}catch(Exception e){
	    		json.setScode(JsonPackageWrapper.S_ERR);
	    		json.setData("系统发生异常！");
	    		LOGGER.error("删除失败！", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	    	return "";
	    }
	    
	    /**
	     * 修改标签业务数据的排序
	     * @param labelObjectId
	     * @param type
	     * @param serviceOrder
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "updateLabelRelationOrder", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String updateLabelRelationOrder(Long mainLabelId,Long relationLabelId,Integer type,Integer serviceOrder,Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try{
	    		Map params=new HashMap();
	    		params.put("mainLabelId", mainLabelId);
	    		params.put("relationLabelId", relationLabelId);
	    		params.put("type", type);
	    		params.put("orderSeq", serviceOrder);
	    		Boolean isSuccess=labelService.updateLabelRelationOrder(params);
	    		if(isSuccess){
	    			json.setScode(JsonPackageWrapper.S_OK);
	    		}else{
	    			json.setScode(JsonPackageWrapper.S_ERR);
	    			json.setData("修改失败 ！");
	    		}
	    	}catch(Exception e){
	    		json.setScode(JsonPackageWrapper.S_ERR);
	    		json.setData("系统发生异常！");
	    		LOGGER.error("删除失败！", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	    	return "";
	    }
		
	    
	    /**
	     * 触发更新数据到搜索
	     * @param labelId
	     * @param model
	     */
	    @RequestMapping(value = "syncRelation", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String syncRelation(Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try{
	    		dataTaskManagerService.syncLabelRelationDate();
	    		json.setScode(JsonPackageWrapper.S_OK);
	    		json.setData("");
	    	}catch(Exception e){
	    		json.setScode(JsonPackageWrapper.S_ERR);
	    		json.setData("系统发生异常！");
	    		LOGGER.error("删除失败！", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	    	return "";
	    }
	    
	    
	    /**
	     * 触发更新数据到搜索
	     * @param labelId
	     * @param model
	     */
	    @RequestMapping(value = "syncToSearch", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String syncToSearch(Model model) {
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try{
	    		searchService.syncDataToSearch();
    			json.setScode(JsonPackageWrapper.S_OK);
    			json.setData("");
	    	}catch(Exception e){
	    		json.setScode(JsonPackageWrapper.S_ERR);
	    		json.setData("系统发生异常！");
	    		LOGGER.error("删除失败！", e);
	    	}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	    	return "";
	    }
	    
	    
	    /**
	     * 检查标签
	     * @param labelId
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "checkLabelId", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkLabelId(Long labelId, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			try {
				labelId=ObjectUtil.getLong(labelId, null);
				if(labelId!=null){
					Label label = labelService.getLabelInfo(labelId);
						if (label != null) {
							json.setData(label);
							json.setSmsg("检查标签ID成功！");
						} else {
							json.setScode("2");
							json.setData("该标签ID不存在！");
						}
				}else{
					json.setScode("2");
					json.setData("标签ID不能为空！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("check标签失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	    
}
