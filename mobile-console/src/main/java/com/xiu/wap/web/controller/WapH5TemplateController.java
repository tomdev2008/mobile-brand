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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.WapH5List;
import com.xiu.mobile.core.model.WapH5Template;
import com.xiu.mobile.core.service.IWapH5TemplateService;


/**
 * wap-H5模板管理
 * @author Administrator
 *
 */
@AuthRequired
@Controller
@RequestMapping(value = "/h5template")
public class WapH5TemplateController {
	//日志
    private static final Logger logger = Logger.getLogger(WapH5TemplateController.class);
    
    @Autowired
    private IWapH5TemplateService wapH5TemplateService;
    @Value("${upload_pic_path}")
    private String upload_pic_path;
    
    /**
     * 跳转到添加页面--页面加载
     * @return
     */
    @RequestMapping(value = "addUI", method = RequestMethod.GET)
    public String addUI(Model model){
    	model.addAttribute("template", new WapH5Template());
    	return "pages/h5template/create";
    }
    
    /**
     * 保存或更新模板
     * @return
     */
    @RequestMapping(value = "addOrUpdate", method = RequestMethod.POST)
    public String addOrUpdate(MultipartHttpServletRequest request, WapH5Template template, Model model){
    	User user = AdminSessionUtil.getUser(request);
    	template.setLastEditer(user.getUsername());
    	
    	//图片检测
    	MultipartFile patch = request.getFile("thumbnail");
    	String fileName = patch.getOriginalFilename();
    	String picPatch = "";
    	if(StringUtils.isNotBlank(fileName)){
    		fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
    		picPatch = "/m/h5template" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
    		template.setThumbnailUrl(picPatch);//图片上传路径
    	}
    	wapH5TemplateService.saveOrUpdateH5Template(template);
    	
    	if(StringUtils.isNotBlank(picPatch)){
	    	String picFile = upload_pic_path.trim() + picPatch;
			UploadPicUtils.isUploadPathExsit(picFile);
			try {
				patch.transferTo(new File(picFile));//上传图片
			} catch (IOException e) {
				logger.error("上传模板缩略图异常", e);
			}
    	}
    	//正常跳转到模板列表页
    	Page<?> page = new Page();
    	return list(page,model);
    }
    
    /**
     * 跳转到修改页面--页面跳转
     * @return
     */
    @RequestMapping(value = "updateUI", method = RequestMethod.GET)
    public String updateUI(long id, Model model){
    	WapH5Template template = wapH5TemplateService.queryWapH5TemplateById(id);
    	model.addAttribute("template", template);
    	
    	return "pages/h5template/create";
    }
    
    /**
     * 获取list
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Page<?> page, Model model){
    	
    	List<WapH5Template> list=new ArrayList<WapH5Template>();
    	Map<String,Object> map=new HashMap<String,Object>();
    	page.setPageNo(page.getPageNo()==0 ? 1 : page.getPageNo());
    	Map<String,Object> result=wapH5TemplateService.queryWapH5TemplateListDesc(map, page);
    	list=(List<WapH5Template>)result.get("resultInfo");
    	model.addAttribute("list", list);
    	model.addAttribute("page",page);
    	logger.info("H5列表："+list);
    	return "pages/h5template/list";
    }

    
}
