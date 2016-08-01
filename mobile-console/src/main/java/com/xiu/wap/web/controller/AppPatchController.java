package com.xiu.wap.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.RSAEncrypt;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.mobile.core.model.AppPatchVO;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IAppPatchService;

/**
 * 
* @Description: TODO(app热部署) 
* @author jesse.chen
* @date 2016年5月4日 下午2:40:44 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/appPatch")
public class AppPatchController {

	//日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(AppPatchController.class);
    
    @Autowired
    private IAppPatchService appPatchService;
    
    @Value("${upload.app.jsPatch.path}")
    private String UPLOAD_APP_JSPATCH_PATH;
    
    @Value("${app.jsPatch.domain}")
    private String APP_JSPATCH_DOMAIN;
    
    /**
     * 热部署列表
     * @param advPlaceId
     * @param advPlaceName
     * @param page
     * @param model
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(String name,String type,String status,Page<?> page, Model model) {
    	List<AppPatchVO> appPatchList=new ArrayList<AppPatchVO>();
    	Map<Object,Object> rmap=new HashMap<Object, Object>(); 
		rmap.put("name", name);
		rmap.put("status", status);
		rmap.put("type", type);
    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
    	
		Map<String,Object> result =appPatchService.getAppPatchVOListByCondition(rmap,page);
		appPatchList=(List<AppPatchVO>)result.get("resultInfo");
    	model.addAttribute("appPatchList",appPatchList);
    	model.addAttribute("page", page);
    	model.addAttribute("name", name);
    	model.addAttribute("status", status);
    	model.addAttribute("type", type);
        return "pages/appPatch/list";
    }
    
    /**
     * 跳转新增页面
     */ 
    @RequestMapping(value = "goAdd", method = RequestMethod.GET)
    public String goAdd( Model model) {
        return "pages/appPatch/add";
    }
    
    /**
     * 更新
     * @param request
     * @param subject
     * @param model
     */
    @RequestMapping(value = "display", method = RequestMethod.GET)
    public String display(String id,Model model) {
    	AppPatchVO vo=appPatchService.queryAppPatchVOById(Long.parseLong(id));
    	model.addAttribute("appPatchVO",vo);
    	return "pages/appPatch/display";
    }
    
    /**
     * 添加
     * @param request
     * @param subject
     * @param model
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(MultipartHttpServletRequest request,String name,String type,String version,String needUpdate,
    		String status,String remark,Model model) {
    	AppPatchVO vo=new AppPatchVO();
    	
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
    	
    	
		/******文件上传******/
    	
    	/**
    	 * 源文件上传
    	 */
		MultipartFile patch = multipartRequest.getFile("out_pic");
		int id=appPatchService.queryIdBySeq();
		
		String fileName = patch.getOriginalFilename();
		String picPath = "";
		if(null!=fileName && !"".equals(fileName)){
			fileName =fileName.substring(fileName.lastIndexOf("."));
			picPath = UPLOAD_APP_JSPATCH_PATH + id+fileName;
		}
		
		UploadPicUtils.isUploadPathExsit(picPath);
		try {
			patch.transferTo(new File(picPath));// 上传文件
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String readPath=APP_JSPATCH_DOMAIN+id+fileName;
		try {
			//插入MD5验签
			vo.setMd5Encode(MD5toRsaResource(patch));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vo.setId((long)id);
		vo.setType(type);
		vo.setName(name);
		//访问路径
		vo.setPath(readPath);
		vo.setNeedUpdate(Long.parseLong(needUpdate));
		vo.setStatus(Long.parseLong(status));
		vo.setVersion(StringUtils.isNotBlank(version.split(",")[0]) ? version.split(",")[0]:version.split(",")[1] );
		vo.setRemark(remark);
		
		appPatchService.save(vo);
    	return list(null, null, null, new Page(), model);
    }
    
    /**
     * 更新
     * @param request
     * @param subject
     * @param model
     */
    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(String id,String status,Model model) {
    	AppPatchVO vo=new AppPatchVO();
    	vo.setId(Long.parseLong(id));
    	vo.setStatus(Long.parseLong(status));
    	appPatchService.update(vo);
    	
    	return list(null, null, null, new Page() , model);
    }
    /**
     * 
     * @param id
     * @param model
     */
    @RequestMapping(value = "queryTypeOfAppPatchStatus", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String queryTypeOfAppPatchStatus(String  id,Model model) {
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try {
            if(appPatchService.checkStatusAppPatchVO(Long.parseLong(id))){
    			 json.setScode(JsonPackageWrapper.S_OK);
                 json.setData("存在已！");
    		 }else{
    			 json.setScode(JsonPackageWrapper.S_ERR);
                 json.setData("不存在！");
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
     * 文件进行MD5加密和RSA加密
     * @return
     */
    public String MD5toRsaResource(MultipartFile patch) throws Exception {
         
        /* FileInputStream  fis =new FileInputStream(patch); 
         FileChannel  fileChannel = fis.getChannel();  
         MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, patch.length());
         */
         MessageDigest md = MessageDigest.getInstance("MD5");  
         md.update(patch.getBytes());  
         String md5 = RSAEncrypt.byteArrayToString(md.digest());  
         md5=md5.replaceAll(" ", "");
         RSAEncrypt rsaEncrypt=new RSAEncrypt();
         //加载秘钥
         rsaEncrypt.loadPublicKey(RSAEncrypt.DEFAULT_PUBLIC_KEY);
         rsaEncrypt.loadPrivateKey(RSAEncrypt.DEFAULT_PRIVATE_KEY);  
         String cipher = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), md5.getBytes());  
         LOGGER.info("IOS热更新文件加密结果："+cipher);
		return cipher;
    }
    
}
