package com.xiu.wap.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.SysParamsMgtVo;
import com.xiu.mobile.core.service.ISysParamsService;
/**
 * 
* @Description: TODO(系统配置管理) 
* @author haidong.luo@xiu.com
* @date 2016年6月21日 下午4:00:57 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/systemParams")
public class SystemParamsController {	

		//日志
	    private static final XLogger LOGGER = XLoggerFactory.getXLogger(SystemParamsController.class);
	    
        @Autowired
	    private ISysParamsService sysParamService;
	    
	    /**
	     * 
	     * @param paramsName
	     * @param paramsValue
	     * @param page
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "list", method = RequestMethod.GET)
	    public String list(String paramKey,String paramValue,
				Page<?> page, Model model) {
	    	List<SysParamsMgtVo> sysParamsMgtVolist=new ArrayList<SysParamsMgtVo>();
	    	Map<Object,Object> rmap=new HashMap<Object, Object>(); 
	    	rmap.put("paramKey", paramKey);
			rmap.put("paramValue", paramValue);
	    	page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
	    	
    		Map result =sysParamService.getSysParamsList(rmap,page);
    		sysParamsMgtVolist=(List<SysParamsMgtVo>)result.get("resultInfo");
    		
	    	SysParamsMgtVo SysParamsMgtVo=new SysParamsMgtVo(); 
	    	model.addAttribute("sysParamsMgtVolist",sysParamsMgtVolist);
	    	model.addAttribute("paramKey",paramKey);
	    	model.addAttribute("paramValue",paramValue);
	    	
	        return "pages/sysParams/list";
	    }
	    
	    /**
	     * 跳转系统参数添加页面--界面加载
	     */ 
	    @RequestMapping(value = "bfAdd", method = RequestMethod.GET)
	    public String bfAdd( Model model) {
	        return "pages/sysParams/create";
	    }
	    
	    /**
	     * 添加系统参数
	     * @param request
	     * @param SysParamsMgtVo
	     * @param model
	     */
	    @RequestMapping(value = "save", method = RequestMethod.POST)
	    public String save(HttpServletRequest request,String paramKey,String paramValue,String paramDesc,Model model) {
	    	Map params=new HashMap();
	    	Integer status=0;
			 User user=AdminAuthInfoHolder.getUserAuthInfo();
	    	SysParamsMgtVo sysParamsMgtVo=new SysParamsMgtVo();
	    	sysParamsMgtVo.setParamKey(paramKey);
	    	sysParamsMgtVo.setParamValue(paramValue);
	    	sysParamsMgtVo.setParamDesc(paramDesc);
	    	sysParamsMgtVo.setUpdateBy(user.getUsername());
	    	params.put("model", sysParamsMgtVo);
	    	params =sysParamService.save(params);
	    	Boolean isSuccess=(Boolean)params.get("status");
	    	if(isSuccess){
	    		status=1;
	    	}
			String  msg=(String)params.get("msg");
	    	sysParamsMgtVo=sysParamService.getSysParamsById(sysParamsMgtVo.getId());
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("sysParams", sysParamsMgtVo);
	    	return "pages/sysParams/create";
	    }
	   
	    /**
	     * 系统参数管理--跳转系统参数编辑页面
	     * @param id
	     * @param advPlaceName
	     * @param model
	     */
	    @RequestMapping(value = "toedit", method = RequestMethod.GET)
	    public String toEdit(Long id ,Model model) {
	    	SysParamsMgtVo SysParamsMgtVo=null;
	    	try {
	    		SysParamsMgtVo=sysParamService.getSysParamsById(id);
			} catch (Exception e) {
				LOGGER.error("根据系统参数ID查询系统参数异常！",e);
			}
    		model.addAttribute("sysParams",SysParamsMgtVo);
	        return "pages/sysParams/info";
	    }
	    /**
	     * 系统参数管理--编辑系统参数
	     * @param SysParamsMgtVo
	     * @param model
	     */
	    @RequestMapping(value = "edit", method = RequestMethod.POST)
	    public String edit(HttpServletRequest request,Long id,String paramKey,String paramValue,String paramDesc,Model model) {
	    	Map params=new HashMap();
	    	Integer status=0;//操作状态
	    	String msg=null;
			 User user=AdminAuthInfoHolder.getUserAuthInfo();
			String userName=user.getUsername(); 
			SysParamsMgtVo sys=new SysParamsMgtVo();
			 
			 sys.setId(id);
			 sys.setParamDesc(paramDesc);
			 sys.setParamKey(paramKey);
			 sys.setParamValue(paramValue);
			 sys.setUpdateBy(userName);
			 params.put("model", sys);
			 Map result=sysParamService.update(params);//保存数据
			 Boolean isSuccess=(Boolean)result.get("status");
			  msg=(String)result.get("msg");
			 if(isSuccess){
				 status=1;
			 }
			sys=sysParamService.getSysParamsById(id);
			model.addAttribute("status", status);
	    	model.addAttribute("msg", msg);
			model.addAttribute("sysParams", sys);
	        return "pages/sysParams/info";
	    }
	    
	    /**
	     * 系统参数管理--删除系统参数(deleted: 0 未删除 , 1 已删除 )
	     * @param id
	     * @param model
	     */
	    @RequestMapping(value = "delete", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	    public String delete(Long id,Model model) {
			 User user=AdminAuthInfoHolder.getUserAuthInfo();
			Long userId=user.getId(); 
	    	JsonPackageWrapper json = new JsonPackageWrapper();
	    	try {
	    		SysParamsMgtVo SysParamsMgtVo=new SysParamsMgtVo();
	    		SysParamsMgtVo.setId(id);
	    		SysParamsMgtVo.setUpdateBy(user.getUsername());
	            if(sysParamService.delete(SysParamsMgtVo)==0){
	    			 json.setScode(JsonPackageWrapper.S_OK);
	                 json.setData("删除系统参数成功！");
	    		 }else{
	    			 json.setScode(JsonPackageWrapper.S_ERR);
	                 json.setData("删除系统参数失败！");
	    		 }
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
	            json.setData("系统发生异常！");
	            LOGGER.error("删除系统参数失败！：", e);
			}
	    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
	        return "";
	    }
	    
	    
	    
}
