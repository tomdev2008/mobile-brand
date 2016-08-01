package com.xiu.wap.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.command.result.Result;
import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.utils.ExcelUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.constants.EnumContants.DataType;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5Module;
import com.xiu.mobile.core.model.WapH5ModuleData;
import com.xiu.mobile.core.model.WapH5PageTemplate;
import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.service.IWapH5ListService;
import com.xiu.mobile.core.service.IWapH5ModuleService;
import com.xiu.mobile.core.service.IWapH5PageService;
import com.xiu.mobile.core.service.IWapH5TemplateService;
import com.xiu.mobile.core.utils.DateUtil;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.wap.web.service.ISearchService;
import com.xiu.wap.web.service.MemcachedService;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;


/**
 * wap-H5页面管理
 * @author Administrator
 *
 */
@AuthRequired
@Controller
@RequestMapping(value = "/h5page")
public class WapH5PageController {
	//日志
    private static final Logger logger = Logger.getLogger(WapH5PageController.class);
    
    @Autowired
    private IWapH5PageService wapH5PageService;
    @Autowired
    private IWapH5ModuleService wapH5ModuleService;
    @Autowired
    private IWapH5TemplateService wapH5TemplateService;
    @Autowired
    private IWapH5ListService wapH5ListService;
    @Autowired
    private MemcachedService memcachedService;
    @Autowired
	private ISearchService searchService;
    
    @Value("${upload_h5_pic_path}")
    private String upload_h5_pic_path;
    @Value("${images_compress_path}")
    private String images_compress_path;
    @Value("${upload_pic_path}")
    private String upload_pic_path;
    @Value("${h5_html_path}")
	private String h5HtmlStorePath;
    
	@Autowired
	private ILabelService labelService;
    
    //h5页面访问路径前缀
    private static String PREX_H5ACCESSURL = "http://m.xiu.com/H5";
    
    //h5模板懒加载报错的key前缀
    private static String H5MODULE_MEMCACHEKEY = "h5Module.moduleId";
    
    /**
     * 进入修改或者添加h5信息页面
     * @return
     */
    @RequestMapping(value = "createorupdatepage", method = RequestMethod.GET)
    public String createorupdatepage(Long pageId, Model model){
    	WapH5List wapH5List = null;
    	if(pageId != null && pageId > 0){
    		wapH5List = wapH5PageService.queryWapH5ListById(pageId);
    	}else{
    		wapH5List = new WapH5List();
    	}
		
//    	List<WapH5Template> allTemplateList  = wapH5TemplateService.queryActveTemplateList();
//    	List<WapH5Module> ownerModuleList = new ArrayList<WapH5Module>();
//    	if(pageId != null){
//    		ownerModuleList = wapH5ModuleService.getTargetModuleList(pageId);
//    	}

//    	List<WapH5PageTemplate> pageTemplateList = wapH5TemplateService.queryPageTemplateList();
    	model.addAttribute("wapH5", wapH5List);
//    	model.addAttribute("allTemplateList", allTemplateList);
//    	model.addAttribute("ownerModuleList", ownerModuleList);
//    	model.addAttribute("pageTemplateList", pageTemplateList);
    	
    	
    	
    	
    	
    	//显示默认访问路径前缀
		String middleUrl = DateUtil.formatDate(new Date(), "/yyyy/MM/dd/");
		model.addAttribute("prexUrl", PREX_H5ACCESSURL);
		model.addAttribute("middleUrl", middleUrl);
		
		// 查询已经添加的标签
		if(pageId != null && pageId > 0){
			List<Label> label=labelService.findLabelListByObjectIdAndType(pageId,  GlobalConstants.LABEL_TYPE_H5);
			model.addAttribute("updateLabelList", label);
		}
		 // 查询常用的标签（热门和新加的）
		Map<String,Object> labels=labelService.findCommonLabelList();
		model.addAttribute("newAddList", labels.get("newAddList"));
		model.addAttribute("historyList", labels.get("historyList"));
    	
    	return "pages/h5page/basepage";
    }
    
    /**
     * 修改或者添加H5页面
     * @return
     */
    @RequestMapping(value = "modifypage", method = RequestMethod.POST)
    public String modifypage(MultipartHttpServletRequest request, WapH5List wapH5List,String[] labelIds, Model model){
    	User user = AdminSessionUtil.getUser(request);
    	wapH5List.setOperator(user.getUsername());
    	//如果是保存页面，则组装下访问url
    	if(StringUtils.isNotBlank(wapH5List.getH5Url()) && wapH5List.getId() == 0){
    		wapH5List.setH5Url(PREX_H5ACCESSURL + wapH5List.getH5Url() + ".html");
    	}
    	
    	int existsCount = wapH5ListService.findH5Url(wapH5List.getH5Url());
    	if(existsCount > 0 && wapH5List.getId() == 0){
        	model.addAttribute("msg", "该URL链接已经存在 ，请更换");
        	return createorupdatepage(wapH5List.getId(), model);
    	}
    	
    	//图片检测
    	MultipartFile listpatch = request.getFile("listPic");//列表图
    	MultipartFile sharepatch = request.getFile("sharePic");//分享图
    	int limitPicSize = 1048576; //图片限制1M
    	String uploadUrl = upload_pic_path.trim();
    	String picUrl = "/m/h5page" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
    	//上传缩略图
    	Result uploadResult = UploadPicUtils.uploadPic(listpatch, limitPicSize, uploadUrl, picUrl);
    	if(!uploadResult.isSuccess()
    			&& uploadResult.getError() != null){
    		model.addAttribute("msg", uploadResult.getError());
        	return createorupdatepage(wapH5List.getId(), model);
    	}
    	Map<String, String> map = (Map<String, String>) uploadResult.getDefaultModel();
//    	String uploadFileName = "";
//    	String filePath = "";
//    	String savePath = "";
    	if(null != map && StringUtils.isNotBlank(map.get("picPatch"))){
    		wapH5List.setPhoto(map.get("picPatch"));
//    		uploadFileName = map.get("fileName");
//        	filePath = uploadUrl + picUrl + uploadFileName;
//        	savePath = images_compress_path.trim() + picUrl + uploadFileName;
//        	UploadPicUtils.copyFile(filePath, savePath);
    	}
    	
    	//上传分享图
    	uploadResult = UploadPicUtils.uploadPic(sharepatch, limitPicSize, uploadUrl, picUrl);
    	if(!uploadResult.isSuccess()
    			&& uploadResult.getError() != null){
    		model.addAttribute("msg", uploadResult.getError());
        	return createorupdatepage(wapH5List.getId(), model);
    	}
    	map = (Map<String, String>) uploadResult.getDefaultModel();
    	if(map != null && StringUtils.isNotBlank(map.get("picPatch"))){
    		wapH5List.setSharePhoto(map.get("picPatch"));
//    		uploadFileName = map.get("fileName");
//        	filePath = uploadUrl + picUrl + uploadFileName;
//        	savePath = images_compress_path.trim() + picUrl + uploadFileName;
//        	UploadPicUtils.copyFile(filePath, savePath);
    	}
    	
    	//分享
    	String shareCheck = request.getParameter("shareCheck");
    	wapH5List.setIsShare(0);
    	if("on".equals(shareCheck)){
    		wapH5List.setIsShare(1);
    	}
    	wapH5List.setType(3); //组件化H5
//    	Long h5id=wapH5List.getId();
//    	if(h5id==0){
//    		h5id=wapH5ListService.findWapH5Id();
//        	wapH5List.setId(h5id);
//    	}
    	Long h5Id=wapH5PageService.saveOrUpadteWapH5List(wapH5List);
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
				
    	return "redirect:/h5list/list";
    	
    }
    
    /**
     * 编辑h5组件
     * @return
     */
    @RequestMapping("editpage")
    public String editH5Module(Long pageId, Model model){
    	logger.info("editH5Module-->pageId=" + pageId);
    	WapH5List wapH5List = null;
    	if(pageId != null && pageId > 0){
    		wapH5List = wapH5PageService.queryWapH5ListById(pageId);
    	}else{
    		wapH5List = new WapH5List();
    	}
    	List<WapH5Template> allTemplateList  = wapH5TemplateService.queryActveTemplateList();
    	List<WapH5Module> ownerModuleList = new ArrayList<WapH5Module>();
    	if(pageId != null){
    		ownerModuleList = wapH5ModuleService.getTargetModuleList(pageId);
    	}

    	List<WapH5PageTemplate> pageTemplateList = wapH5TemplateService.queryPageTemplateList();
    	model.addAttribute("wapH5", wapH5List);
    	model.addAttribute("allTemplateList", allTemplateList);
    	model.addAttribute("ownerModuleList", ownerModuleList);
    	model.addAttribute("pageTemplateList", pageTemplateList);
    	
    	return "pages/h5page/page";
    }
    
    /**
     * 添加组件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addcomponents", method = RequestMethod.POST)
    public JsonPackageWrapper addcomponents(HttpServletRequest request, 
    		Long pageId, Long templateId, Integer index, boolean backward){
    	User user = AdminSessionUtil.getUser(request);
    	WapH5Module module = new WapH5Module();
    	module.setPageId(pageId);
    	module.setTemplateId(templateId);
    	module.setLastEditer(user.getUsername());
    	Integer zIndex = (index == null)? 1 : index; //在某个位置插入组件
    	module.setRowIndex(zIndex);
    	//前五个组件默认不懒加载，其他组件默认为懒加载，1位懒加载。
    	if(zIndex > 3){
    		module.setIsLazyLoad("1");
    	}else{
    		module.setIsLazyLoad("0");
    	}
    	
    	wapH5ModuleService.addModule(module, backward);
    	
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		
//		List<WapH5Module> ownerModuleList = wapH5ModuleService.getTargetModuleList(pageId);
		WapH5Module ownerModule = wapH5ModuleService.getTargetModuleByRowIndex(pageId,zIndex);
		wrapper.setScode(JsonPackageWrapper.S_OK);
		wrapper.setData(ownerModule);
		
		return wrapper;

    }
    
    /**
     * 删除组件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deletecomponents", method = RequestMethod.POST)
    public JsonPackageWrapper deletecomponents(Long moduleId,int pageId,int rowIndex,Model model){
    	WapH5Module module = new WapH5Module();
    	module.setId(moduleId);
    	module.setPageId(pageId);
    	module.setRowIndex(rowIndex);
    	wapH5ModuleService.deletecomponents(module);
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		
		List<WapH5Module> ownerModuleList = wapH5ModuleService.getTargetModuleList(pageId);
		wrapper.setScode(JsonPackageWrapper.S_OK);
		wrapper.setData(ownerModuleList);
		
		return wrapper;
    }
    
    /**
     * 移动组件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "movecomponents", method = RequestMethod.POST)
    public JsonPackageWrapper movecomponents(Long firstModuleId , int secondToIndex ,Long secondModuleId ,int pageId, int firstToIndex, Model model){
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("firstModuleId", firstModuleId);
    	paramMap.put("secondToIndex", secondToIndex);
    	paramMap.put("secondModuleId", secondModuleId);
    	paramMap.put("firstToIndex", firstToIndex);
    	wapH5ModuleService.exChangeModuleRowIndex(paramMap);
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		
		List<WapH5Module> ownerModuleList = wapH5ModuleService.getTargetModuleList(pageId);
		wrapper.setScode(JsonPackageWrapper.S_OK);
		wrapper.setData(ownerModuleList);
		
		return wrapper;
    }
    
    /**
     * 更新组件是否懒加载
     * @param moduleId
     * @param isLazyLoad
     */
    @RequestMapping("updatelazyload")
    public String updateModuleLazyLoad(Long moduleId, String isLazyLoad){
    	wapH5ModuleService.updateModuleLazyLoad(moduleId, isLazyLoad);
    	return null;
    }
    
    /**
     * 导出单个组件excel
     * @return
     */
    @RequestMapping(value = "exportcomponents", method = RequestMethod.GET)
    public String exportcomponents(HttpServletResponse response, Long moduleId, Model model){
    	if(moduleId == null){
    		return "error/500";
    	}
    	//查询excel标题头
    	Map<String, Object> resultMap = wapH5ModuleService.queryModuleTemplateByModuleId(moduleId);
    	//查询该组件是否已经导入过数据
//    	List<Map<String, Object>> listData = wapH5ModuleService.queryModuleDataByModuleId(moduleId);
    	List<WapH5ModuleData> listData = wapH5ModuleService.queryModuleDataByModuleId(moduleId);
    	
    	String sheetName = "";
    	String[] excelHeader = null;
    	if(resultMap != null && resultMap.size() > 0){
    		sheetName = (String) resultMap.get("name");
    		excelHeader = (String[]) resultMap.get("excelHeader");
    	}
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String fileName = sheetName + ".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型

			ExcelUtil.downloadStatements(listData, excelHeader, os, sheetName, DataType.getDataTypeNameCnList());
		} catch(Exception e) {
			logger.error("下载单个组件模板异常！", e);
		}
    	return null;
    }
    
    /**
     * 导入单个组件excel
     * @return
     */
    @RequestMapping(value = "importcomponents", method = RequestMethod.POST)
    public void importcomponents(MultipartHttpServletRequest request, 
    		HttpServletResponse response, Model model){
    	
    	String strModuleId = request.getParameter("moduleId");
    	Long moduleId = null;
    	if(StringUtils.isNotBlank(strModuleId)){
    		moduleId = Long.valueOf(strModuleId);
    	}
    	if(moduleId == null){
    		logger.error("moduleId参数不能为空");
    		outPrintData(response, "moduleId参数不能为空");
    		return;
    	}
    	MultipartFile patchExcel = request.getFile("dataUpload_" + moduleId);
    	
//    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	List<Map<Object, Object>> listMap = null;
    	try {
    		listMap = ExcelUtil.importStatements(patchExcel.getInputStream());
		} catch (IOException e) {
			logger.error("解析excel数据异常", e);
			outPrintData(response, "解析excel数据异常");
    		return;
		}
		if(listMap == null || listMap.size() <= 0){
			outPrintData(response, "导入失败");
			return;
		}
//		List<Map<Object, Object>> listMap = null;
//		for (Iterator<String> iterator = resultMap.keySet().iterator(); iterator.hasNext();) {
//			String sheetName = iterator.next();
//			if(sheetName.contains("_")){
//				String excelModuleId = sheetName.substring(sheetName.indexOf("_")+1);
//				if(!strModuleId.equals(excelModuleId)){
//					outPrintData(response, "该excel已失效，请重新下载模板导入");
//		    		return;
//				}
//			}else{
//				outPrintData(response, "该excel已失效，请重新下载模板导入");
//	    		return;
//			}
//			listMap = (List<Map<Object, Object>>) resultMap.get(sheetName);
//			break;
//		}
    	
		String importResult = "导入失败";
		try{
			importResult = wapH5ModuleService.importModuleData(moduleId, listMap);
		}catch(Exception e){
			logger.error("导入单个组件数据异常", e);
		}
		outPrintData(response, importResult);
    }
    
    /**
     * 导出所有组件excel
     * @return
     */
    @RequestMapping(value = "exportallcomponents", method = RequestMethod.GET)
    public String exportallcomponents(HttpServletResponse response, Long pageId, Model model){
    	if(pageId == null){
    		return "error/500";
    	}
    	WapH5List wapH5List = wapH5PageService.queryWapH5ListById(pageId);
    	//查询该页面导出excel的标题头
    	Map<String, Object> resultMap = wapH5PageService.queryModuleTemplateByPageId(pageId);
    	//查询该页面所有组件已导入的数据
    	List<WapH5ModuleData> listData = wapH5PageService.queryAllModuleDataByPageId(pageId);
    	
    	String[] excelHeader = null;
    	if(resultMap != null && resultMap.size() > 0){
    		excelHeader = (String[]) resultMap.get("excelHeader");
    	}
		try {
			OutputStream os = response.getOutputStream();	//取得输出流      
			response.reset();	//清空输出流      
			String fileName = wapH5List.getTitle() + ".xls";
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));
			//设定输出文件头      
			response.setContentType("application/msexcel");	//定义输出类型
			   
			ExcelUtil.downloadStatements(listData, excelHeader, os, wapH5List.getTitle(), DataType.getDataTypeNameCnList());
		} catch(Exception e) {
			logger.error("下载页面所有组件模板异常！", e);
		}
    	return null;
    }
    
    /**
     * 导入所有组件excel
     * @return
     */
    @RequestMapping(value = "importallcomponents", method = RequestMethod.POST)
    public void importallcomponents(MultipartHttpServletRequest request,
    		HttpServletResponse response, Model model){
    	String strPageId = request.getParameter("pageId");
    	Long pageId = null;
    	if(StringUtils.isNotBlank(strPageId)){
    		pageId = Long.valueOf(strPageId);
    	}
    	if(pageId == null){
    		logger.error("pageId参数不能为空");
    		outPrintData(response, "pageId参数不能为空");
    		return;
    	}
    	MultipartFile patchExcel = request.getFile("allDataUpload");
    	// 原始数据
    	List<Map<Object, Object>> dataList = null;
    	try {
    		dataList = ExcelUtil.importStatements(patchExcel.getInputStream());
		} catch (IOException e) {
			logger.error("解析excel数据异常", e);
			outPrintData(response, "解析excel数据异常");
    		return;
		}
		if(dataList == null || dataList.size() <= 0){
			outPrintData(response, "导入失败");
			return;
		}
//		Map<Long, Object> copyMap = new HashMap<Long, Object>();
//		for (Iterator<String> iterator = resultMap.keySet().iterator(); iterator.hasNext();) {
//			String key = iterator.next();
//			if(key.contains("_")){
//				Long moduleId = Long.parseLong(key.substring(key.indexOf("_")+1));
//				copyMap.put(moduleId, resultMap.get(key));
//			}
//		}
		String importResult = "";
		try{
			importResult = wapH5PageService.importAllModuleData(pageId, dataList);
		}catch(Exception e){
			logger.error("导入所有组件数据异常", e);
			importResult = "系统异常";
		}
		outPrintData(response, StringUtils.isBlank(importResult) ? "导入成功" : importResult);
		return;
    }
    
    @RequestMapping(value = "editOnline", method = RequestMethod.GET)
    public void editOnline(Long moduleId, HttpSession session, HttpServletResponse response){
    	//查询excel标题头
    	Map<String, Object> resultMap = wapH5ModuleService.queryModuleTemplateByModuleId(moduleId);
    	//查询该组件是否已经导入过数据
    	List<WapH5ModuleData> listData = wapH5ModuleService.queryModuleDataByModuleId(moduleId);
    	//获取所有数据类型
    	Map<String, String> dataTypes = DataType.getNameCnMap();
    	
    	String[] tableHeader = (String[]) resultMap.get("excelHeader");
    	for (int i = 0; i < tableHeader.length; i++) {
    		tableHeader[i] = tableHeader[i].substring(0, tableHeader[i].indexOf(":"));
		}
    	for (Iterator<WapH5ModuleData> iterator = listData.iterator(); iterator.hasNext();) {
			WapH5ModuleData wapH5ModuleData = iterator.next();
			if(StringUtils.isNotBlank(wapH5ModuleData.getGroupIndex())){
				wapH5ModuleData.setGroupIndex(wapH5ModuleData.getGroupIndex()
						.substring(0, wapH5ModuleData.getGroupIndex().indexOf("_")));
				break;
			}
		}
    	
    	String moduleEditHtml = "";
    	try{
	    	String moduleEditFilePath = session.getServletContext().getRealPath("/WEB-INF/views/pages/h5page/");
	    	Configuration configuration = new Configuration();
	   	 	configuration.setDirectoryForTemplateLoading(new File(moduleEditFilePath));
	   	 	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
	   	 	configuration.setDefaultEncoding("UTF-8");//文字编码
	   	 	//获取或创建一个模版
		   	Template template = configuration.getTemplate("moduleedit.ftl");
		   	
		   	Map<String, Object> dataMap = new HashMap<String, Object>();
		   	dataMap.put("tableHeader", tableHeader);
		   	dataMap.put("dataList", listData);
		   	dataMap.put("dataTypes", dataTypes);
		   	dataMap.put("moduleId", moduleId);
		   	
		   	StringWriter writer = new StringWriter();
		   	template.process(dataMap, writer);
		   	moduleEditHtml = writer.toString();
    	}catch(Exception e){
    		logger.error("", e);
    	}
    	outPrintData(response, moduleEditHtml);
    }
    
    @RequestMapping(value = "saveModuleEdit", method = RequestMethod.POST)
    public void saveModuleEdit(Long moduleId, 
    		MultipartHttpServletRequest request, HttpServletResponse response){
    	String[] positions = request.getParameterValues("position");
    	String[] titles = request.getParameterValues("title");
    	String[] viceTitles = request.getParameterValues("viceTitle");
    	String[] dataTypes = request.getParameterValues("dataType");
    	String[] links = request.getParameterValues("link");
    	List<MultipartFile> patchs = request.getFiles("moduleImgData");
    	String[] imgs = request.getParameterValues("img");
    	
    	List<WapH5ModuleData> dataList = new ArrayList<WapH5ModuleData>();
    	for (int i = 0; i < positions.length; i++) {
    		WapH5ModuleData wapH5ModuleData = new WapH5ModuleData();
    		wapH5ModuleData.setPosition(positions[i]);
    		wapH5ModuleData.setTitle(titles[i]);
    		wapH5ModuleData.setViceTitle(viceTitles[i]);
    		wapH5ModuleData.setDataType(dataTypes[i]);
    		wapH5ModuleData.setLink(links[i]);
    		wapH5ModuleData.setImg(imgs[i]);
    		wapH5ModuleData.setExcelRow(i+1);
    		wapH5ModuleData.setModuleId(moduleId);
    		
			//图片检测
	    	MultipartFile patch = patchs.get(i);
	    	String picPatch = "/m/h5page" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
	    	Result result = UploadPicUtils.uploadPic(patch, null, upload_pic_path.trim(), picPatch);
	    	Map<String, String> map = (Map<String, String>) result.getDefaultModel();
//	    	String uploadFileName = "";
//	    	String filePath = "";
//	    	String savePath = "";
	    	if(null != map && StringUtils.isNotBlank(map.get("picPatch"))){
	    		wapH5ModuleData.setImg(map.get("picPatch"));
//	    		uploadFileName = map.get("fileName");
//	        	filePath = upload_pic_path.trim() + picPatch + uploadFileName;
//	        	savePath = images_compress_path.trim() + picPatch + uploadFileName;
//	        	UploadPicUtils.copyFile(filePath, savePath);
	    	}
    		dataList.add(wapH5ModuleData);
		}
    	String importResult = "保存失败";
		try{
			importResult = wapH5ModuleService.importModuleData(moduleId, dataList);
			importResult = "导入成功".equals(importResult) ? "保存成功" : importResult;
		}catch(Exception e){
			logger.error("导入单个组件数据异常", e);
		}
    	outPrintData(response, importResult);
    }
    
    /**
     * 预览单个组件
     * @return
     */
    @RequestMapping(value = "previewmodule", method = RequestMethod.GET)
    public String previewmodule(Long moduleId, Model model, HttpSession session, HttpServletResponse response){
    	Map<String, Object> dataMap = wapH5ModuleService.assembleModuleVeiw(moduleId);
    	String template = (String) dataMap.get("template");
    	Object data = dataMap.get("dataList");
    	if(data == null){
    		outPrintData(response, "该组件还未导入数据，请先导入数据再预览");
        	return null;
    	}
    	
    	StringTemplateLoader stringLoader = new StringTemplateLoader();
    	stringLoader.putTemplate("moduleViewTemplate", template);
    	
    	Configuration cfg = new Configuration();
    	cfg.setTemplateLoader(stringLoader);
    	
    	String moduleViewModel = "";
    	Map<String, Object> paramMap = new HashMap<String, Object>();    
    	try {  
            Template moduleViewTemplate = cfg.getTemplate("moduleViewTemplate","utf-8");  
            paramMap.put("list", data);
            paramMap.put("moduleId", dataMap.get("moduleId"));
            paramMap.put("isLazyLoad", "0"); //预览都默认不懒加载
              
            StringWriter writer = new StringWriter();
        	moduleViewTemplate.process(paramMap, writer);
        	moduleViewModel = writer.toString();
    	}catch (Exception e) {  
    		logger.error("组装单个组件数据异常", e);
    		outPrintData(response, "组装预览数据异常");
    		return null;
    	}
    	//查询页面信息
    	WapH5List wapH5List = wapH5ModuleService.queryPageInfoByModuleId(moduleId);
    	
    	String viewHtml = "";
    	try{
	    	String moduleEditFilePath = session.getServletContext().getRealPath("/WEB-INF/views/pages/h5module/");
	    	Configuration configuration = new Configuration();
	   	 	configuration.setDirectoryForTemplateLoading(new File(moduleEditFilePath));
	   	 	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
	   	 	configuration.setDefaultEncoding("UTF-8");//文字编码
	   	 	//获取或创建一个模版
	   	 	Template moduleViewTemplate = configuration.getTemplate("view.ftl");
		   	
	   	 	String jsUrl = (String) dataMap.get("jsUrl");
	   	 	String cssUrl = (String) dataMap.get("cssUrl");
	   	 	
	   	 	List<String> jsUrls = new ArrayList<String>();
	   	 	List<String> cssUrls = new ArrayList<String>();
	   	 	
	   	 	jsUrls.add(jsUrl);
	   	 	cssUrls.add(cssUrl);
	   	 	
	   	 	paramMap.clear();
	   	 	paramMap.put("wapH5", wapH5List);
	   	 	paramMap.put("moduleView", moduleViewModel);
	   	 	paramMap.put("jsUrls", jsUrls);
	   		paramMap.put("cssUrls", cssUrls);
		   	
		   	StringWriter writer = new StringWriter();
		   	moduleViewTemplate.process(paramMap, writer);
		   	viewHtml = writer.toString();
    	}catch(Exception e){
    		logger.error("", e);
    	}
    	outPrintData(response, viewHtml);
    	return null;
    }
    
    /**
     * 预览单个组件for ajax
     * @return
     */
    @RequestMapping(value = "previewmoduleforajax", method = RequestMethod.GET)
    public String previewmoduleforajax(Long moduleId, Model model, HttpServletResponse response){
    	Map<String, Object> dataMap = wapH5ModuleService.assembleModuleVeiw(moduleId);
    	String template = (String) dataMap.get("template");
    	Object data = dataMap.get("dataList");
    	if(data == null){
    		outPrintData(response, "");
        	return null;
    	}
    	
    	StringTemplateLoader stringLoader = new StringTemplateLoader();
    	stringLoader.putTemplate("moduleViewTemplate", template);
    	
    	Configuration cfg = new Configuration();
    	cfg.setTemplateLoader(stringLoader);
    	
    	String moduleViewModel = "";
    	try {  
            Template moduleViewTemplate = cfg.getTemplate("moduleViewTemplate","utf-8");  
            Map<String, Object> paramMap = new HashMap<String, Object>();    
            paramMap.put("list", data);
            paramMap.put("moduleId", dataMap.get("moduleId"));
            paramMap.put("isLazyLoad", "0"); //预览都默认不懒加载
              
            StringWriter writer = new StringWriter();
        	moduleViewTemplate.process(paramMap, writer);
        	moduleViewModel = writer.toString();
    	}catch (Exception e) {  
    		logger.error("组装单个组件数据异常", e);
    		outPrintData(response, "");
    		return null;
    	}
    	//查询页面信息
    	StringBuilder viewHtml = new StringBuilder("");	
    	viewHtml.append("<div class='container forview").append(dataMap.get("index")).append("'>");
    	viewHtml.append(moduleViewModel);
    	viewHtml.append("</div>");  	
    	outPrintData(response, viewHtml.toString());	
    	return null;
    }

    /**
     * 预览h5页面
     * @return
     */
    @RequestMapping(value = "previewpage", method = RequestMethod.GET)
    public String previewpage(Long pageId, Model model, HttpSession session, HttpServletResponse response){
    	String viewHtml = this.getPageHtml(pageId, session, "pre");
    	
    	if(StringUtils.isBlank(viewHtml)){
    		viewHtml = "组装页面预览数据异常";
    	}
    	
    	outPrintData(response, viewHtml);
    	return null;
    }
    
    /**
     * 发布h5页面
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "publishpage", method = RequestMethod.GET)
    public String publishpage(Long pageId, Model model, HttpSession session){
    	WapH5List wapH5List = wapH5PageService.queryWapH5ListById(pageId);
    	if(wapH5List == null || StringUtils.isBlank(wapH5List.getH5Url())){
    		logger.error("找不到该页面或页面没设置访问路径，pageId: " + pageId);
    		return "0";
    	}
		String h5Url = wapH5List.getH5Url();
		h5Url = h5Url.replace(PREX_H5ACCESSURL, "");
		String h5Path = h5HtmlStorePath + h5Url;
		
		File h5File = new File(h5Path);
		
		if(h5File.exists()){
			h5File.delete();
		}
		if(!h5File.getParentFile().exists()){
			h5File.getParentFile().mkdirs();
		}
		//组装h5页面html
		String viewHtml = this.getPageHtml(pageId, session, "pub");
		if(StringUtils.isBlank(viewHtml)){
			return "0";
		}
		try {
			FileWriter fw = new FileWriter(h5File, true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(viewHtml);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			logger.error("写入页面html到文件异常", e);
			return "0";
		}
    	return "1";
    }
    
    public static void main(String[] args) {
		String a = "<div class='model4' moduleid='488' lazyload='0'><div class='oneImg'><div class='sec'><img src='http://8.xiustatic.com/m/h5page/2016/04/18/9be6cd0e-09ba-448c-af52-c4a2f3fa2e9a.jpg'><div class='page100' data-type='' data-id=''></div></div> </div>  <div class='oneImgBor'><div class='sec'><img src='http://7.xiustatic.com/m/h5page/2016/04/19/891166c0-15d8-4bce-8318-99174ccc1a33.jpg'><div class='page100' data-type='subject' data-id='331'></div></div>   </div>  <div class='oneImgBor'><div class='sec'><img src='http://7.xiustatic.com/m/h5page/2016/04/19/4d8d55b4-6714-4cc9-89f9-01c0e742b78d.jpg'><div class='page100' data-type='subject' data-id='331'></div></div>   </div></div>";
		System.out.println(a.indexOf(">")+1);
		System.out.println(a.lastIndexOf("<"));
		a = a.substring(a.indexOf(">")+1, a.lastIndexOf("<"));
		System.out.println(a);
	}
    
    private String getPageHtml(Long pageId, HttpSession session, String type){
    	WapH5List wapH5List = wapH5ListService.getWapH5ListById(pageId);
    	
    	StringBuilder moduleHtml = new StringBuilder();
    	Set<String> jsUrlSet = null;
    	Set<String> cssUrlSet = null;
    	try{
	    	Map<String, Object> dataMap = wapH5PageService.assemblePageVeiw(pageId);
	    	if(dataMap != null){
	    		jsUrlSet = (Set<String>) dataMap.get("jsUrlSet");
	    		cssUrlSet = (Set<String>) dataMap.get("cssUrlSet");
	    		
	    		List<String> templates = (List<String>) dataMap.get("templates");
	    		List<String> isLazyLoads = (List<String>) dataMap.get("isLazyLoads");
	    		List<Long> moduleIds = (List<Long>) dataMap.get("moduleIds");
	    		List<List<List<WapH5ModuleData>>> pageData = (List<List<List<WapH5ModuleData>>>) dataMap.get("pageData");
	    		StringTemplateLoader stringLoader = new StringTemplateLoader();
	    		Configuration cfg = new Configuration();
	    		for (int i=0; i<templates.size(); i++) {
	    			String template = templates.get(i);
	    			String isLazyLoad = isLazyLoads.get(i);
					List<List<WapH5ModuleData>> moduleData = pageData.get(i);
					stringLoader.putTemplate("moduleViewTemplate", template);
					cfg.setTemplateLoader(stringLoader);
		            Template moduleViewTemplate = cfg.getTemplate("moduleViewTemplate","utf-8");  
		            Map<String, Object> paramMap = new HashMap<String, Object>();    
		            paramMap.put("list", moduleData);
		            paramMap.put("isLazyLoad", isLazyLoad);
		            paramMap.put("moduleId", moduleIds.get(i));
		              
		            StringWriter writer = new StringWriter();
					moduleViewTemplate.process(paramMap, writer);
					
	    			if("1".equals(isLazyLoad)){
	    				if("pub".equals(type)){
	    					moduleHtml.append(writer.toString());
	    				}
	    				paramMap.put("isLazyLoad", "0");
	    				writer = new StringWriter();
	    				moduleViewTemplate.process(paramMap, writer);
	    				if("pub".equals(type)){
	    					String cacheHtml = writer.toString().replaceAll("\\r\\n", "").replaceAll("\\t", "").replaceAll("\"", "\'");
	    					cacheHtml = cacheHtml.substring(cacheHtml.indexOf(">")+1, cacheHtml.lastIndexOf("<"));
	    					memcachedService.setCache(H5MODULE_MEMCACHEKEY + moduleIds.get(i), cacheHtml);
	    				}else{
	    					moduleHtml.append(writer.toString());
	    				}
	    			}else{
	    				moduleHtml.append(writer.toString());
	    			}
					
				}
	    	}
    	}catch(Exception e){
    		logger.error("组装页面组件数据异常", e);
    		return null;
    	}
    	
    	String viewHtml = "";
    	try{
	    	String moduleEditFilePath = session.getServletContext().getRealPath("/WEB-INF/views/pages/h5module/");
	    	Configuration configuration = new Configuration();
	   	 	configuration.setDirectoryForTemplateLoading(new File(moduleEditFilePath));
	   	 	configuration.setObjectWrapper(new DefaultObjectWrapper()); 
	   	 	configuration.setDefaultEncoding("UTF-8");//文字编码
	   	 	//获取或创建一个模版
	   	 	Template moduleViewTemplate = configuration.getTemplate("view.ftl");
		   	
	   	 	Map<String, Object> paramMap = new HashMap<String, Object>();
	   	 	paramMap.put("wapH5", wapH5List);
	   	 	paramMap.put("moduleView", moduleHtml.toString());
	   	 	paramMap.put("jsUrls", jsUrlSet);
	   		paramMap.put("cssUrls", cssUrlSet);
		   	
		   	StringWriter writer = new StringWriter();
		   	moduleViewTemplate.process(paramMap, writer);
		   	viewHtml = writer.toString();
    	}catch(Exception e){
    		logger.error("", e);
    	}
    	
    	return viewHtml.toString();
    }
    
    
    
    /**
     * 保存或者修改页面模板
     */
    @ResponseBody
    @RequestMapping(value = "checkpagetemplatename", method = RequestMethod.POST)
    public JsonPackageWrapper checkpagetemplatename(HttpServletRequest request, 
    		String name){
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		Boolean  isExist =  wapH5TemplateService.queryPageTemplateNameExist(name);
		wrapper.setData(isExist);
		wrapper.setScode(JsonPackageWrapper.S_OK);
		return wrapper;
    }
    
    /**
     * 保存或者修改页面模板
     */
    @ResponseBody
    @RequestMapping(value = "savepagetemplate", method = RequestMethod.POST)
    public JsonPackageWrapper savepagetemplate(HttpServletRequest request, 
    		Long id, String name, String templateIds){
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		WapH5PageTemplate template = new WapH5PageTemplate();
		template.setName(name);
		template.setTemplateIds(templateIds);
		//do update  or do insert
		if(id!=null){ 
			template.setId(id);
			wapH5TemplateService.updatePageTemplate(template);
		}else{
			wapH5TemplateService.insertPageTemplate(template);
		}
		List<WapH5PageTemplate> temp = wapH5TemplateService.queryPageTemplateList();
		wrapper.setData(temp);
		wrapper.setScode(JsonPackageWrapper.S_OK);

		
		return wrapper;
    }
    
    
    /**
     * 修改页面模板的名称
     */
    @ResponseBody
    @RequestMapping(value = "changepagetemplatename", method = RequestMethod.POST)
    public JsonPackageWrapper changepagetemplatename(HttpServletRequest request, 
    		Long id, String name){
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		WapH5PageTemplate template = new WapH5PageTemplate();
		template.setId(id);
		template.setName(name);
		wapH5TemplateService.updatePageTemplate(template);
		List<WapH5PageTemplate> temp = wapH5TemplateService.queryPageTemplateList();
		wrapper.setData(temp);
		wrapper.setScode(JsonPackageWrapper.S_OK);

		
		return wrapper;
    }
    
    
    /**
     * 修改页面模板的名称
     */
    @ResponseBody
    @RequestMapping(value = "getmodulehasdata", method = RequestMethod.GET)
    public JsonPackageWrapper getmodulehasdata(HttpServletRequest request, 
    		Long pageId){
    	JsonPackageWrapper wrapper = new JsonPackageWrapper();
		wrapper.setScode(JsonPackageWrapper.S_ERR);
		List<WapH5Module> temp = wapH5ModuleService.getModuleDataCount(pageId);
		Map<Long,Long> resultMap = new HashMap<Long,Long>();
		if(temp!=null && temp.size()!=0 ){
			for (WapH5Module item : temp) {
				resultMap.put(item.getId(), item.getDataCount());
			 }
			wrapper.setData(resultMap);
		}
		wrapper.setScode(JsonPackageWrapper.S_OK);

		
		return wrapper;
    }
    
    /**
     * 页面应用某个页面模板
     * @param id
     * @param model
     * @return
     */
    
    @RequestMapping(value = "applypagetemplate", method = RequestMethod.GET)
    public String applypagetemplate(HttpServletRequest request, @RequestParam("pageId")Long pageId, @RequestParam("templateIds")String templateIds){
    	logger.info("WapH5PageController-->applypagetemplate---------------begin------");
    	logger.info("WapH5PageController.applypagetemplate-->templateIds:" + templateIds);
    	try {
			User user = AdminSessionUtil.getUser(request);
			logger.info("WapH5PageController.applypagetemplate-->templateIds:" + user);
			if(templateIds!=null && templateIds.length() >0){
				String [] ids  = templateIds.split(",");
				for (int i =0 ,len = ids.length;i<len;i++) {  
					WapH5Module module = new WapH5Module();
			    	module.setPageId(pageId);
			    	module.setTemplateId(Long.valueOf(ids[i]));
			    	module.setLastEditer(user.getUsername());
			    	module.setRowIndex(i+1);
			    	module.setIsLazyLoad("1");
			    	logger.info("WapH5PageController.applypagetemplate-->templateIds:" + module);
			    	wapH5ModuleService.addModule(module, false);
				} 
			}
		} catch (Exception e) {
			logger.info("WapH5PageController-->applypagetemplate", e);
		}
    	logger.info("WapH5PageController-->applypagetemplate---------------end------");
    	return "redirect:/h5page/editpage?pageId="+pageId;
    }
    
    private void outPrintData(HttpServletResponse response, String data){
    	response.setContentType("text/html;charset=gb2312");
    	PrintWriter out = null;
    	try{
	    	out = response.getWriter();
			out.print(data);
			out.flush();
    	}catch(Exception e){
    		logger.error("输出数据异常", e);
    	}finally{
    		if(out != null){
    			out.close();
    		}
    	}
    }
    
}
