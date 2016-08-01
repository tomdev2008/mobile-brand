package com.xiu.wap.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.xiu.common.web.contants.LinkType;
import com.xiu.common.web.utils.ExcelUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.StringUtil;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.HtmlCreate;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.service.IWapH5ListService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.wap.web.service.ISearchService;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import com.xiu.common.command.result.Result;
/**
 * wap-H5列表
 * @author Administrator
 *
 */
@AuthRequired
@Controller
@RequestMapping(value = "/h5list")
public class WapH5ListController {
	//日志
    private static final XLogger logger = XLoggerFactory.getXLogger(WapH5ListController.class);
    
    @Autowired
    private IWapH5ListService wapH5ListService;
    
	@Autowired
	private ILabelService labelService;
	  
    @Autowired
	private ISearchService searchService;
    
    @Value("${upload_pic_path}")
    private String upload_pic_path;
    
    @Value("${h5_html_path}")
	private String h5_html_path;
    @Value("${h5_template_upload_path}")
    private String h5_template_upload_path;
    private static String[] IMG_DOMAIN = {"http://6.xiustatic.com/H5", "http://7.xiustatic.com/H5", "http://8.xiustatic.com/H5"};
    
    private static String h5_template="http://m.xiu.com/H5";
    /**
     * 获取h5列表信息
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Integer status,String title,String creatorName,
    		String labelName,String h5Url,
    		Page<?> page, Model model) throws UnsupportedEncodingException{
    	List<WapH5List> listH5=new ArrayList<WapH5List>();
    	Map<String,Object> map=new HashMap<String,Object>();
    	map.put("labelName", labelName);
    	map.put("status", status);
    	map.put("creatorName", creatorName);
    	if(StringUtils.isNotBlank(title)){
    		title=title.trim();
    	}
    	map.put("title", title);
    	if(StringUtils.isNotBlank(h5Url)){
    		h5Url=h5Url.trim();
    	}
    	map.put("h5Url", h5Url);
    	page.setPageNo(page.getPageNo()==0 ? 1 : page.getPageNo());
    	Map<String,Object> result=wapH5ListService.getH5list(map, page);
    	listH5=(List<WapH5List>)result.get("resultInfo");
    	model.addAttribute("labelName",labelName);
    	model.addAttribute("status",status);
    	model.addAttribute("title", title);
    	model.addAttribute("h5Url", h5Url);
    	model.addAttribute("creatorName", creatorName);
    	model.addAttribute("h5List", listH5);
    	logger.info("H5列表："+listH5);
    	return "pages/h5list/list";
    }
    /**
     * 跳转到添加页面--页面加载
     * @return
     */
    @RequestMapping(value = "bfAdd", method = RequestMethod.GET)
    public String bfAdd(Model model){
		 // 查询常用的标签（热门和新加的）
		Map<String,Object> labels=labelService.findCommonLabelList();
		model.addAttribute("newAddList", labels.get("newAddList"));
		model.addAttribute("historyList", labels.get("historyList"));
    	model.addAttribute("middleUrl", new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()));
    	return "pages/h5list/create";
    }
    /**
     * 跳转新增H5
     */
    @RequestMapping(value = "bfCreate", method = RequestMethod.GET)
    public String bfCreate(Model model){
		 // 查询常用的标签（热门和新加的）
		Map<String,Object> labels=labelService.findCommonLabelList();
		model.addAttribute("newAddList", labels.get("newAddList"));
		model.addAttribute("historyList", labels.get("historyList"));
    	model.addAttribute("url", new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()));
    	return "pages/h5list/addHtml";
    }
    /**
     * 跳转到修改页面--页面跳转
     * @return
     */
    @RequestMapping(value = "bfUpdate", method = RequestMethod.GET)
    public String bfUpdate(Long id,Model model){
    	WapH5List h5list=wapH5ListService.getWapH5ListById(id);
    	model.addAttribute("h5list", h5list);
    	
		//查询标签
		// 查询已经添加的标签
		List<Label> label=labelService.findLabelListByObjectIdAndType(id.longValue(),  GlobalConstants.LABEL_TYPE_H5);
		model.addAttribute("updateLabelList", label);
		 // 查询常用的标签（热门和新加的）
		Map<String,Object> labels=labelService.findCommonLabelList();
		model.addAttribute("newAddList", labels.get("newAddList"));
		model.addAttribute("historyList", labels.get("historyList"));
    	return "pages/h5list/update";
    }
    @RequestMapping(value = "bfNewUpdate", method = RequestMethod.GET)
    public String bfNewUpdate(Long id,Model model){
    	WapH5List h5list=wapH5ListService.getWapH5ListById(id);
    	model.addAttribute("h5list", h5list);
    	
		//查询标签
		// 查询已经添加的标签
		List<Label> label=labelService.findLabelListByObjectIdAndType(id.longValue(), GlobalConstants.LABEL_TYPE_H5);
		model.addAttribute("updateLabelList", label);
		 // 查询常用的标签（热门和新加的）
		Map<String,Object> labels=labelService.findCommonLabelList();
		model.addAttribute("newAddList", labels.get("newAddList"));
		model.addAttribute("historyList", labels.get("historyList"));
    	return "pages/h5list/updateHtml";
    }
    /**
     * 修改信息
     * @return
     */
    @RequestMapping(value = "updateNew", method = RequestMethod.POST)
    public String updateNew(MultipartHttpServletRequest request,Long id,Integer status,String title,String[] labelIds,
    		String h5Url,String operator,Integer type,Integer style,String shareTitle,String shareContent,Model model){
    	Integer isSuccess=0;  //修改状态
    	WapH5List h5list=new WapH5List();
    	h5list.setId(id);
    	h5list.setTitle(title);
    	h5list.setStatus(status);
    	h5list.setH5Url(h5Url);
    	h5list.setStyle(style);
    	h5list.setType(type);
    	h5list.setShareTitle(shareTitle);
    	h5list.setShareContent(shareContent);
    	//组件化处理
    	if(type==2){
    		try{
    			// 获取Excel文件流
            	MultipartFile patchExcel = request.getFile("dataFile");
            	Long in=patchExcel.getSize();
            	if(in != 0L){
            		boolean isTemplate=false;
            		// 原始数据
                	List<Map<Object, Object>> listMap = ExcelUtil.importStatements(patchExcel.getInputStream());
                	//将excel先保存下来
    	        	String excelName=patchExcel.getOriginalFilename();//文件名称
    	        	if(null!=excelName && !"".equals(excelName)){
    	    			excelName=id+excelName.substring(excelName.lastIndexOf("."));
    	    			String excelPath=h5_template_upload_path.trim()+"/template/"+excelName;
    	    			UploadPicUtils.isUploadPathExsit(excelPath);
    	    			try {
    	    				patchExcel.transferTo(new File(excelPath)); //excel图片
    	    			} catch (IllegalStateException e) {
    	    				e.printStackTrace();
    	    			} catch (IOException e) {
    	    				e.printStackTrace();
    	    			}
    	    		}
                	String templateFilePath = request.getSession().getServletContext().getRealPath("/");
    	        	templateFilePath += "templateHTML";
    	        	templateFilePath = templateFilePath.replace("\\", "/");
    	        	String templateFileName=null;
    	        	if(style==1){
    	        		templateFileName="templateHtml.ftl";
    	        	}else if(style==2){
    	        		templateFileName="templateHtml2.ftl";
    	        	}else{
    	        		templateFileName="templateHtml3.ftl";
    	        	}
    	        	String[] co=h5Url.split("/");
    	        	String htmlFileName=co[co.length-1];
    	        	String filePath=h5Url.split("/"+htmlFileName)[0];
    	        	String htmlFilePath=h5_html_path.trim()+filePath.split(h5_template)[1];
    	        	isTemplate=geneHtmlFile(templateFilePath,templateFileName,listMap,htmlFilePath,htmlFileName,style);
    	        	if(!isTemplate){//如果模板生成失败则返回
    		    		model.addAttribute("isSuccess", isSuccess);
    		        	model.addAttribute("msg", "模板生成失败");
    		        	model.addAttribute("h5list", h5list);
    		    		return "pages/h5list/updateHtml";
    		    	}
            	}
    		}catch(Exception e){
    			logger.info("导入模板异常："+e);
    		}
    	}
    	MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request;
    	//图片检测
    	MultipartFile patch=multipartRequest.getFile("photo");
    	String fileName=patch.getOriginalFilename();
    	String picPatch="";
    	boolean isChangeImg=false;
    	if(null!=fileName && !"".equals(fileName)){
    		fileName= UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
    		picPatch="/m/h5list"+new SimpleDateFormat("/yyyy/MM/dd/").format(new Date())+fileName;
    		h5list.setPhoto(picPatch);//图片上传路径
    		isChangeImg=true;
    	}
    	User user=AdminSessionUtil.getUser(request);
    	h5list.setOperator(user.getUsername());
    	MultipartFile sharepatch = request.getFile("sharePic");//分享图
    	if(sharepatch!=null){
	    	int limitPicSize = 1048576; //图片限制1M
	    	//上传分享图
	    	String uploadUrl = upload_pic_path.trim();
	    	String picUrl = "/m/h5page" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
	    	Result uploadResult = UploadPicUtils.uploadPic(sharepatch, limitPicSize, uploadUrl, picUrl);

	    	Map<String, String> map = (Map<String, String>) uploadResult.getDefaultModel();
	    	map = (Map<String, String>) uploadResult.getDefaultModel();
	    	if(map != null && StringUtils.isNotBlank(map.get("picPatch"))){
	    		h5list.setSharePhoto(map.get("picPatch"));
	    	}
    	}
    	
    	int i=wapH5ListService.updateWapH5List(h5list);
    	if(i==1){
    		isSuccess=1;
    		if(isChangeImg){
    			String picFile = upload_pic_path.trim() + picPatch;
    			UploadPicUtils.isUploadPathExsit(picFile);
    			try {
					patch.transferTo(new File(picFile));//上传图片
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
    		}
    	}
    	
    	Long h5Id=id;
    	//如果有标签则绑定标签
			List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
			if(labelIds!=null&&labelIds.length>0){
				for (int j = 0; j < labelIds.length; j++) {
					LabelCentre lc=new LabelCentre();
					lc.setLabelId(ObjectUtil.getLong(labelIds[j]));
					labelCentres.add(lc);
				}
			}
			labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_H5, h5Id, labelCentres);
		//加入到搜索表 中
		searchService.addDataToSearch(GlobalConstants.LABEL_TYPE_H5, h5Id);
		
    	model.addAttribute("isSuccess", isSuccess);
    	model.addAttribute("msg", "");
    	model.addAttribute("h5list", h5list);
    	if(type==2){
    		return "pages/h5list/updateHtml";
    	}
    	return "pages/h5list/update";
    }
    @RequestMapping(value = "deleteAll", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String deleteAll(HttpServletRequest request, String ids, Model model){
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try{
    		if(null != ids){
    			logger.debug("即将被删的H5ids是:{}",new Object[]{ids});
    			String[] strIds = ids.split(",");
				List<Long> idsL = new ArrayList<Long>();
				if(strIds != null){
					for(String s : strIds){
						if(!StringUtil.isEmpty(s))
							idsL.add(Long.parseLong(s));
					}
				}
				int i=wapH5ListService.deleteWapH5List(idsL);
				if(i>0){
		       		 json.setScode(JsonPackageWrapper.S_OK);
		             json.setData("删除成功！");
			    }else{
		       		json.setScode(JsonPackageWrapper.S_ERR);
		            json.setData("删除失败！");
		       	}
    		}else{
    			json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
    		}
    	}catch(Exception e){
    		json.setScode(JsonPackageWrapper.S_ERR);
            json.setData("系统发生异常！");
            logger.error("删除失败！：", e);
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
    /**
     * 下载模板
     */
    @RequestMapping(value = "down", method = RequestMethod.GET)
    public String down(HttpServletRequest request,HttpServletResponse response,Model model,String style){
    	String path = request.getSession().getServletContext().getRealPath("/");
    	if(style.equals("1")){
    		path += "template/h5-style1.xls";
    	}else if(style.equals("2")){
    		path += "template/h5-style2.xls";
    	}else{
    		path += "template/h5-style3.xls";
    	}
    	path = path.replace("\\", "/");
    	logger.debug("模板文件路径：{}", new Object[]{path});
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
			logger.error("下载H5组件导入模板出现异常",ex);
			return null;
		}
    	return null;
    }
    /**
     * 下载修改模板
     */
    @RequestMapping(value = "downUpdate", method = RequestMethod.GET)
    public String downUpdate(HttpServletRequest request,HttpServletResponse response,Model model,Long id){
    	String path=h5_template_upload_path.trim()+"/template/"+id+".xls";
    	path = path.replace("\\", "/");
    	logger.debug("模板文件路径：{}", new Object[]{path});
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
			logger.error("下载H5组件导入模板出现异常",ex);
			return null;
		}
    	return null;
    }
    /**
     * 添加列表
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(MultipartHttpServletRequest request,String title,String operator,String[] labelIds,
    		Integer status,String h5Url,Integer style,Integer type,String shareTitle,String shareContent,String bgColor,
    		Model model){
    	WapH5List h5list=new WapH5List();
    	Integer isSuccess=0;  //操作状态
    	h5list.setTitle(title);
    	h5list.setStatus(status);
    	h5list.setType(type);
    	h5list.setStyle(style);
    	h5list.setShareTitle(shareTitle);
    	h5list.setShareContent(shareContent);
    	h5list.setBgColor(bgColor);
    	Long id=wapH5ListService.findWapH5Id();
    	h5list.setId(id);
    	
    	//分享
    	String shareCheck = request.getParameter("shareCheck");
    	h5list.setIsShare(0);
    	if("on".equals(shareCheck)){
    		h5list.setIsShare(1);
    	}
    	
    	if(type==2){
	    	//毫秒
	    	boolean isTemplate=false;
	    	//模板导入
	    	try{
	    		String url=h5_template+h5Url+".html";
	    		//查询数据库比对url是否一样
	    		int i=wapH5ListService.findH5Url(url);
	    		if(i>0){
	    			model.addAttribute("isSuccess", isSuccess);
		        	model.addAttribute("msg", "该URL已经存在，请更换");
		        	model.addAttribute("h5list", h5list);
		    		return "pages/h5list/addHtml";
	    		}
	    		h5list.setH5Url(url);
	    		String[] co=h5Url.split("/");
	    		String ccName=co[co.length-1];
	    		// 获取Excel文件流
	        	MultipartFile patchExcel = request.getFile("dataFile");
	        	// 原始数据
	        	List<Map<Object, Object>> listMap = ExcelUtil.importStatements(patchExcel.getInputStream());
	        	//将excel先保存下来
	        	String excelName=patchExcel.getOriginalFilename();//文件名称
	        	if(null!=excelName && !"".equals(excelName)){
	    			excelName=id+excelName.substring(excelName.lastIndexOf("."));
	    			String excelPath=h5_template_upload_path.trim()+"/template/"+excelName;
	    		//	String excelPath="F:/ten/"+excelName;
	    			UploadPicUtils.isUploadPathExsit(excelPath);
	    			try {
	    				patchExcel.transferTo(new File(excelPath)); //excel图片
	    			} catch (IllegalStateException e) {
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
	        	String templateFilePath = request.getSession().getServletContext().getRealPath("/");
	        	templateFilePath += "templateHTML";
	        	templateFilePath = templateFilePath.replace("\\", "/");
	        	String templateFileName=null;
	        	if(style==1){
	        		templateFileName="templateHtml.ftl";
	        	}else if(style==2){
	        		templateFileName="templateHtml2.ftl";
	        	}else{
	        		templateFileName="templateHtml3.ftl";
	        	}
	        	String htmlFilePath=h5_html_path.trim()+h5Url.split("/"+ccName)[0];
	        	String htmlFileName=ccName+".html";
	        	isTemplate=geneHtmlFile(templateFilePath,templateFileName,listMap,htmlFilePath,htmlFileName,style);
	    	}catch(Exception e){
	    		logger.error("导入组件模板异常", e);
	    	}
	    	if(!isTemplate){//如果模板生成失败则返回
	    		model.addAttribute("isSuccess", isSuccess);
	        	model.addAttribute("msg", "模板生成失败");
	        	model.addAttribute("h5list", h5list);
	    		return "pages/h5list/addHtml";
	    	}
    	}else{
    		h5list.setH5Url(h5Url);
    	}
    	MultipartHttpServletRequest multipartRequest=(MultipartHttpServletRequest)request;
    	//图片检测
    	MultipartFile patch=multipartRequest.getFile("photo");
    	//检查是否有上传图片
    	Long picIn=patch.getSize();
    	String picPatch="";
    	if(picIn!=0L){
    		String fileName=patch.getOriginalFilename();
        	if(null!=fileName && !"".equals(fileName)){
        		fileName= UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
        		picPatch="/m/h5list"+new SimpleDateFormat("/yyyy/MM/dd/").format(new Date())+fileName;
        		h5list.setPhoto(picPatch);//图片上传路径
        	}
    	}
    	User user=AdminSessionUtil.getUser(request);
    	h5list.setOperator(user.getUsername());
     	MultipartFile sharepatch = request.getFile("sharePic");//分享图
    	if(sharepatch!=null){
	    	int limitPicSize = 1048576; //图片限制1M
	    	//上传分享图
	    	String uploadUrl = upload_pic_path.trim();
	    	String picUrl = "/m/h5page" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
	    	Result uploadResult = UploadPicUtils.uploadPic(sharepatch, limitPicSize, uploadUrl, picUrl);

	    	Map<String, String> map = (Map<String, String>) uploadResult.getDefaultModel();
	    	map = (Map<String, String>) uploadResult.getDefaultModel();
	    	if(map != null && StringUtils.isNotBlank(map.get("picPatch"))){
	    		h5list.setSharePhoto(map.get("picPatch"));
	    	}
    	}
    	
    	int i=wapH5ListService.addH5list(h5list);
    	if(i>0 && !picPatch.equals("")){
    		String picFile = upload_pic_path.trim() + picPatch;
    		UploadPicUtils.isUploadPathExsit(picFile);
    		try {
				patch.transferTo(new File(picFile)); //上传图片
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	
		//如果有标签则绑定标签
    	Long h5Id=id;
		if(labelIds!=null&&labelIds.length>0){
			List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
			for (int j = 0; j < labelIds.length; j++) {
				LabelCentre lc=new LabelCentre();
				lc.setLabelId(ObjectUtil.getLong(labelIds[j]));
				labelCentres.add(lc);
			}
			labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_H5, h5Id, labelCentres);
		}
		
		//加入到搜索表 中
		searchService.addDataToSearch(GlobalConstants.LABEL_TYPE_H5, h5Id);
    	
    	isSuccess=1;
    	model.addAttribute("isSuccess", isSuccess);
    	model.addAttribute("msg", "");
    	model.addAttribute("h5list", h5list);
    	if(type==2){
    		return "pages/h5list/addHtml";
    	}
    	return "pages/h5list/create";
    }
    /**
     * 生成html
     * @param templateFilePath(模板路径)
     * @param templateFileName（模板名称）
     * @param data（excle数据）
     * @param htmlFilePath（保存html路径）
     * @param htmlFileName（html名称）
     * @param style（组件类型 1组件1  2组件2）
     * @return
     */
    public boolean geneHtmlFile(String templateFilePath,String templateFileName,List<Map<Object,Object>> data
    		,String htmlFilePath,String htmlFileName,Integer style){
    	boolean isSuccess=false;
    	try{
    		Configuration configuration = new Configuration();
       	 	configuration.setDirectoryForTemplateLoading(new File(templateFilePath));
       	 	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
       	 	configuration.setDefaultEncoding("UTF-8");//文字编码
       //获取或创建一个模版
       	 Template template = configuration.getTemplate(templateFileName);
       	 Map<String,Object> map=new HashMap<String,Object>();
       	Map<String,Object> mapPara=new HashMap<String,Object>();
       	 if(style==1){
       		mapPara=getTemplateDate(data);//excle里面的数据 
       		List<HtmlCreate> listHtml=(List<HtmlCreate>)mapPara.get("listHtml");
           	List<HtmlCreate> listH5=(List<HtmlCreate>)mapPara.get("listH5");
           	String sharaTitle=(String)mapPara.get("sharaTitle");
        	String sharaContent=(String)mapPara.get("sharaContent");
        	String sharaUrl=(String)mapPara.get("sharaUrl");
        	String isShara=(String)mapPara.get("isShara");
        	String isTop=(String)mapPara.get("isTop");//是否返回顶部
           	map.put("listHtml", listHtml);
           	map.put("listH5", listH5);
           	map.put("sharaTitle", sharaTitle);
           	map.put("sharaContent", sharaContent);
           	map.put("sharaUrl", sharaUrl);
           	map.put("isShara", isShara);
           	map.put("isTop", isTop);
       	 }else if(style==2){
       		mapPara=getTemplateDate2(data);
       		List<List<HtmlCreate>> mapList=(List<List<HtmlCreate>>)mapPara.get("mapList");
       		String sharaTitle=(String)mapPara.get("sharaTitle");
        	String sharaContent=(String)mapPara.get("sharaContent");
        	String sharaUrl=(String)mapPara.get("sharaUrl");
        	String isShara=(String)mapPara.get("isShara");
        	String isTop=(String)mapPara.get("isTop");//是否返回顶部
        	map.put("mapList", mapList);
           	map.put("sharaTitle", sharaTitle);
           	map.put("sharaContent", sharaContent);
           	map.put("sharaUrl", sharaUrl);
           	map.put("isShara", isShara);
           	map.put("isTop", isTop);
       	 }else{
       		mapPara=getTemplateDate3(data);
       		List<List<HtmlCreate>> mapList=(List<List<HtmlCreate>>)mapPara.get("mapList");
       		String sharaTitle=(String)mapPara.get("sharaTitle");
        	String sharaContent=(String)mapPara.get("sharaContent");
        	String sharaUrl=(String)mapPara.get("sharaUrl");
        	String isShara=(String)mapPara.get("isShara");
        	String isTop=(String)mapPara.get("isTop");//是否返回顶部
        	map.put("mapList", mapList);
           	map.put("sharaTitle", sharaTitle);
           	map.put("sharaContent", sharaContent);
           	map.put("sharaUrl", sharaUrl); 
           	map.put("isShara", isShara);
           	map.put("isTop", isTop);
       	 }
       	creatDirs(htmlFilePath);//判断是否存在件夹，不存在则创建
       	File afile = new File(htmlFilePath + "/" + htmlFileName);
       //	File afile = new File("F:/ten" + "/" + htmlFileName);
       	Writer out = new BufferedWriter(new OutputStreamWriter(    
                new FileOutputStream(afile))); 
       	template.process(map, out); //创建Html
       	out.flush();    
        out.close(); 
        logger.info("创建组件模板成功");
        isSuccess=true;
    	}catch(Exception e){
    		logger.error("创建组件模板异常:",e);
    		e.printStackTrace();
    	}
    	return isSuccess;
    }
    /**
     * 创建文件夹
     * @param path
     * @return
     */
    private boolean creatDirs(String path) {    
        File aFile = new File(path);    
        if (!aFile.exists()) {    
            return aFile.mkdirs();    
        } else {    
            return true;    
        }    
    }  
    /**
     * 读取excel内容
     * 模板1
     */
    public Map<String,Object> getTemplateDate(List<Map<Object,Object>> data){
    	Map<String,Object> map=new HashMap<String,Object>();
    	List<HtmlCreate> listHtml=new ArrayList<HtmlCreate>();//链接A
    	List<HtmlCreate> listH5=new ArrayList<HtmlCreate>();//链接B
    	String sharaTitle=null;
    	String sharaContent=null;
    	String sharaUrl=null;
    	String isShara=null;//是否分享
    	String isTop=null;
    	for(Map<Object,Object> maps : data){
    		HtmlCreate h5=new HtmlCreate();
    		String photo=(String)maps.get(1);
    		if(photo!=null && photo!=""){
    			h5.setPhoto(getShowimageUrl(photo));//图片
    			h5.setType(LinkType.getLinkTypeByDesc((String)maps.get(2)).getCode());//类型
        		h5.setUrl((String)maps.get(3));//链接
        		listHtml.add(h5);
    		}
    		HtmlCreate h2=new HtmlCreate();
    		String photo1=(String)maps.get(4);
    		if(photo1!=null && photo1!=""){
    			h2.setPhoto(getShowimageUrl(photo1));
        		h2.setType(LinkType.getLinkTypeByDesc((String)maps.get(5)).getCode());
        		h2.setUrl((String)maps.get(6));
        		listH5.add(h2);
    		}
    		String title=(String)maps.get(7);
    		if(StringUtils.isNotBlank(title)){
    			sharaTitle=title;
    		}
    		String content=(String)maps.get(8);
    		if(StringUtils.isNotBlank(content)){
    			sharaContent=content;
    		}
    		String url=(String)maps.get(9);
    		if(StringUtils.isNotBlank(url)){
    			sharaUrl=getShowimageUrl(url);
    		}
    		String shara=(String)maps.get(10);
    		if(StringUtils.isNotBlank(shara)){
    			isShara=shara;
    		}
    		String top=(String)maps.get(11);
    		if(StringUtils.isNotBlank(top)){
    			isTop=top;
    		}
    	}
    	map.put("listHtml", listHtml);
    	map.put("listH5", listH5);
    	map.put("sharaTitle", sharaTitle);
    	map.put("sharaContent", sharaContent);
    	map.put("sharaUrl", sharaUrl);
    	map.put("isShara", isShara);
    	map.put("isTop", isTop);
    	return map;
    }
    /**
     * 模板2
     */
    public Map<String,Object> getTemplateDate2(List<Map<Object,Object>> data){
    	Map<String,Object> map=new HashMap<String,Object>();
    	List<List<HtmlCreate>> param=new ArrayList<List<HtmlCreate>>();
    	String sharaTitle=null;
    	String sharaContent=null;
    	String sharaUrl=null;
    	String isShara=null;//是否分享
    	String isTop=null;
    	for(Map<Object,Object> maps : data){
    		List<HtmlCreate> list=new ArrayList<HtmlCreate>();
    		HtmlCreate h5=new HtmlCreate();
    		String photo=(String)maps.get(1);
    		if(photo!=null && photo!=""){
    			h5.setPhoto(getShowimageUrl(photo));
    			h5.setType(LinkType.getLinkTypeByDesc((String)maps.get(2)).getCode());
    			h5.setUrl((String)maps.get(3));
    			list.add(h5);
    		}
    		HtmlCreate h2=new HtmlCreate();
    		String photo2=(String)maps.get(4);
    		if(photo2!=null && photo2!=""){
    			h2.setPhoto(getShowimageUrl(photo2));
    			h2.setType(LinkType.getLinkTypeByDesc((String)maps.get(5)).getCode());
    			h2.setUrl((String)maps.get(6));
    			list.add(h2);
    		}
    		HtmlCreate h3=new HtmlCreate();
    		String photo3=(String)maps.get(7);
    		if(photo3!=null && photo3!=""){
    			h3.setPhoto(getShowimageUrl(photo3));
    			h3.setType(LinkType.getLinkTypeByDesc((String)maps.get(8)).getCode());
    			h3.setUrl((String)maps.get(9));
    			list.add(h3);
    		}
    		String title=(String)maps.get(10);
    		if(StringUtils.isBlank(title)){
    			sharaTitle=title;
    		}
    		String content=(String)maps.get(11);
    		if(StringUtils.isBlank(content)){
    			sharaContent=content;
    		}
    		String url=(String)maps.get(12);
    		if(StringUtils.isBlank(url)){
    			sharaUrl=getShowimageUrl(url);
    		}
    		String shara=(String)maps.get(13);
    		if(StringUtils.isBlank(shara)){
    			isShara=shara;
    		}
    		String top=(String)maps.get(14);
    		if(StringUtils.isBlank(top)){
    			isTop=top;
    		}
    		param.add(list);
    	}
    	map.put("mapList", param);
    	map.put("sharaTitle", sharaTitle);
    	map.put("sharaContent", sharaContent);
    	map.put("sharaUrl", sharaUrl);
    	map.put("isShara", isShara);
    	map.put("isTop", isTop);
    	return map;
    }
    /**
     * 模板3
     */
    public Map<String,Object> getTemplateDate3(List<Map<Object,Object>> data){
    	Map<String,Object> map=new HashMap<String,Object>();
    	List<List<HtmlCreate>> param=new ArrayList<List<HtmlCreate>>();
    	String sharaTitle=null;
    	String sharaContent=null;
    	String sharaUrl=null;
    	String isShara=null;//是否分享
    	String isTop=null;
    	List<HtmlCreate> listHtml=new ArrayList<HtmlCreate>();//
    	List<HtmlCreate> listLeft=new ArrayList<HtmlCreate>();//左边图片集合
    	List<HtmlCreate> listRight=new ArrayList<HtmlCreate>();//右边图片集合
    	List<HtmlCreate> listCentre=new ArrayList<HtmlCreate>();//中间图片集合
    	HtmlCreate left=new HtmlCreate();//左边图片对象
    	HtmlCreate right=new HtmlCreate();//右边图片对象
    	HtmlCreate centre=new HtmlCreate();//中间图片对象
    	int c=0; //竖 2图片
    	int s=0;//横 3图片
    	for(Map<Object,Object> maps : data){
    		List<HtmlCreate> list=new ArrayList<HtmlCreate>();
    		String num=(String)maps.get(1);//横，竖
    		String sign=(String)maps.get(2);//1 2 3
    		HtmlCreate h5=new HtmlCreate();
    		String photo1=(String)maps.get(3);
    		if(num.equals("竖")&&(sign.equals("21")||sign.equals("22"))){
    			c+=1;
    			if(s>0){
    				left.setHtmlList(listLeft);
    				right.setHtmlList(listRight);
    				centre.setHtmlList(listCentre);
    				listHtml.add(left);
    				listHtml.add(right);
    				listHtml.add(centre);
    				param.add(listHtml);
    				listHtml=new ArrayList<HtmlCreate>();//去除之前数据
    				listLeft=new ArrayList<HtmlCreate>();//去除之前数据
    				listRight=new ArrayList<HtmlCreate>();//去除之前数据
    				listCentre=new ArrayList<HtmlCreate>();
    				left=new HtmlCreate();//去除之前数据
    				right=new HtmlCreate();//去除之前数据
    				centre=new HtmlCreate();
    			}
    			s=0;
    		}else if(num.equals("竖")&&(sign.equals("31")||sign.equals("32")||sign.equals("33"))){
    			s+=1;
    			if(c>0){
    				left.setHtmlList(listLeft);
    				right.setHtmlList(listRight);
    				listHtml.add(left);
    				listHtml.add(right);
    				param.add(listHtml);
    				listHtml=new ArrayList<HtmlCreate>();//去除之前数据
    				listLeft=new ArrayList<HtmlCreate>();//去除之前数据
    				listRight=new ArrayList<HtmlCreate>();//去除之前数据
    				left=new HtmlCreate();//去除之前数据
    				right=new HtmlCreate();//去除之前数据
    			}
    			c=0;
    		}else{
    			if(s>0){
    				left.setHtmlList(listLeft);
    				right.setHtmlList(listRight);
    				centre.setHtmlList(listCentre);
    				listHtml.add(left);
    				listHtml.add(right);
    				listHtml.add(centre);
    				param.add(listHtml);
    				listHtml=new ArrayList<HtmlCreate>();//去除之前数据
    				listLeft=new ArrayList<HtmlCreate>();//去除之前数据
    				listRight=new ArrayList<HtmlCreate>();//去除之前数据
    				listCentre=new ArrayList<HtmlCreate>();
    				left=new HtmlCreate();//去除之前数据
    				right=new HtmlCreate();//去除之前数据
    				centre=new HtmlCreate();
    			}
    			if(c>0){
    				left.setHtmlList(listLeft);
    				right.setHtmlList(listRight);
    				listHtml.add(left);
    				listHtml.add(right);
    				param.add(listHtml);
    				listHtml=new ArrayList<HtmlCreate>();//去除之前数据
    				listLeft=new ArrayList<HtmlCreate>();//去除之前数据
    				listRight=new ArrayList<HtmlCreate>();//去除之前数据
    				left=new HtmlCreate();//去除之前数据
    				right=new HtmlCreate();//去除之前数据
    			}
    			s=0;
    			c=0;
    		}
    		//A 左边
    		if(c==1 || s==1){
    			left.setPhoto(getShowimageUrl(photo1));
    			left.setType(LinkType.getLinkTypeByDesc((String)maps.get(4)).getCode());
    			left.setUrl((String)maps.get(5));
    			left.setFigure("A");
    			left.setNumber(sign);
    		}else if(c>1 || s>1){
    			if(photo1!=null && photo1!=""){
	    			h5.setPhoto(getShowimageUrl(photo1));
	    			h5.setType(LinkType.getLinkTypeByDesc((String)maps.get(4)).getCode());
	    			h5.setUrl((String)maps.get(5));
	    			h5.setFigure("A");
	    			h5.setNumber(sign);
	    			listLeft.add(h5);
    			}
    		}else{
    			if(photo1!=null && photo1!=""){
    				h5.setPhoto(getShowimageUrl(photo1));
        			h5.setType(LinkType.getLinkTypeByDesc((String)maps.get(4)).getCode());
        			h5.setUrl((String)maps.get(5));
        			h5.setFigure("A");
        			h5.setNumber(sign);
        			list.add(h5);
    			}
    		}
    		//B 右边
    		HtmlCreate h2=new HtmlCreate();
    		String photo2=(String)maps.get(6);
    		if(c==1 || s==1){
    			right.setPhoto(getShowimageUrl(photo2));
    			right.setType(LinkType.getLinkTypeByDesc((String)maps.get(7)).getCode());
    			right.setUrl((String)maps.get(8));
    			right.setFigure("B");
    			right.setNumber(sign);
    		}else if(c>1 || s>1){
    			if(photo2!=null && photo2!=""){
    				h2.setPhoto(getShowimageUrl(photo2));
        			h2.setType(LinkType.getLinkTypeByDesc((String)maps.get(7)).getCode());
        			h2.setUrl((String)maps.get(8));
        			h2.setFigure("B");
        			h2.setNumber(sign);
        			listRight.add(h2);
    			}
    		}else{
    			if(photo2!=null && photo2!=""){
    				h2.setPhoto(getShowimageUrl(photo2));
        			h2.setType(LinkType.getLinkTypeByDesc((String)maps.get(7)).getCode());
        			h2.setUrl((String)maps.get(8));
        			h2.setFigure("B");
        			h2.setNumber(sign);
        			list.add(h2);
    			}
    		}
    		//C图
    		HtmlCreate h3=new HtmlCreate();
    		String photo3=(String)maps.get(9);
    		if(s==1){
    			centre.setPhoto(getShowimageUrl(photo3));
    			centre.setType(LinkType.getLinkTypeByDesc((String)maps.get(10)).getCode());
    			centre.setUrl((String)maps.get(11));
    			centre.setFigure("C");
    			centre.setNumber(sign);
    		}else if(s>1){
    			if(photo3!=null && photo3!=""){
    				h3.setPhoto(getShowimageUrl(photo3));
        			h3.setType(LinkType.getLinkTypeByDesc((String)maps.get(10)).getCode());
        			h3.setUrl((String)maps.get(11));
        			h3.setFigure("C");
        			h3.setNumber(sign);
        			listCentre.add(h3);
    			}
    		}else{
    			if(photo3!=null && photo3!=""){
        			h3.setPhoto(getShowimageUrl(photo3));
        			h3.setType(LinkType.getLinkTypeByDesc((String)maps.get(10)).getCode());
        			h3.setUrl((String)maps.get(11));
        			h3.setFigure("C");
        			h3.setNumber(sign);
        			list.add(h3);
        		}
    		}
    		String title=(String)maps.get(12);
    		if(StringUtils.isNotBlank(title)){
    			sharaTitle=title;
    		}
    		String content=(String)maps.get(13);
    		if(StringUtils.isNotBlank(content)){
    			sharaContent=content;
    		}
    		String url=(String)maps.get(14);
    		if(StringUtils.isNotBlank(url)){
    			sharaUrl=getShowimageUrl(url);
    		}
    		String shara=(String)maps.get(15);
    		if(StringUtils.isNotBlank(shara)){
    			isShara=shara;
    		}
    		String top=(String)maps.get(16);
    		if(StringUtils.isNotBlank(top)){
    			isTop=top;
    		}
    		if(c==0 && s==0){
    			param.add(list);
    		}
    	}
    	if(c>0){
    		left.setHtmlList(listLeft);
			right.setHtmlList(listRight);
			listHtml.add(left);
			listHtml.add(right);
			param.add(listHtml);
    	}
    	if(s>0){
    		left.setHtmlList(listLeft);
			right.setHtmlList(listRight);
			centre.setHtmlList(listCentre);
			listHtml.add(left);
			listHtml.add(right);
			listHtml.add(centre);
			param.add(listHtml);
    	}
    	map.put("mapList", param);
    	map.put("sharaTitle", sharaTitle);
    	map.put("sharaContent", sharaContent);
    	map.put("sharaUrl", sharaUrl);
    	map.put("isShara", isShara);
    	map.put("isTop", isTop);
    	return map;
    } 
    /**
     *图片拼接 
     */
    public String getShowimageUrl(String imageUrl) {
		String result = null;
		if(StringUtils.isNotBlank(imageUrl)) {
			int index = Math.abs(imageUrl.hashCode() % 3);
			String imgDomain = IMG_DOMAIN[index];
			String firstChr = imageUrl.substring(0, 1);
			if(firstChr.equals("/")) {
				result = imgDomain + imageUrl; 
			} else {
				result = imgDomain + "/" +imageUrl;
			}
		} 
		
		return result;
	}
}
