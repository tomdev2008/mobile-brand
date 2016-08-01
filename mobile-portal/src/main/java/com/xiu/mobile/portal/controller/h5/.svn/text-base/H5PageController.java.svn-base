package com.xiu.mobile.portal.controller.h5;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.portal.controller.BaseController;
import com.xiu.mobile.portal.service.IMemcachedService;

@Controller
@RequestMapping("/h5")
public class H5PageController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(H5PageController.class);
	
	@Autowired
	public IMemcachedService memcachedService;
	
	//h5模板懒加载报错的key前缀
    private static String H5MODULE_MEMCACHEKEY = "h5Module.moduleId";

	@ResponseBody
	@RequestMapping(value="/modulehmltajax", produces = "text/html;charset=UTF-8")
	public String getModuleHtmlAjax(String moduleId,String jsoncallback){
		String html = memcachedService.getH5ModuleHtml(H5MODULE_MEMCACHEKEY + moduleId);
		logger.info("getModuleHtmlAjax.moduleId=" + moduleId + "-->" + html);
		StringBuffer sb = new StringBuffer(html);
		if(jsoncallback != null) {
			sb.insert(0, jsoncallback + "(\"");
			sb.append("\")");
		}
		return sb.toString();
	}
}
