package com.xiu.show.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.show.core.common.util.ObjectUtil;
import com.xiu.show.core.model.OperateLogModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.service.IOperateLogManagerService;


/**
 * 
* @Description: TODO(操作日志) 
* @author haidong.luo@xiu.com
* @date 2015年6月12日 下午3:25:12 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/operateLog")
public class OperateLogController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(OperateLogController.class);

    @Autowired
    private IOperateLogManagerService operateLogManagerService;

	/**
	 * 
	 * @param type
	 * @param objectId
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String type,String objectId ,@RequestParam(value = "datas[]")String[] datas,String modelName,Page<?> page, Model model) {
		Map rmap = new HashMap();
		rmap.put("type", type);
		rmap.put("objectId", objectId);
		if(modelName==null||modelName.equals("")){
			modelName="comment";
		}
		rmap.put("type", ObjectUtil.getInteger(type, 2));
		rmap.put("objectId", objectId);
		try{
		 rmap=operateLogManagerService.getOperateLogList(rmap,page);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error(modelName+" 查询操作日志异常！",e);                     
		}
		List<OperateLogModel> ops=(List<OperateLogModel>)rmap.get("resultInfo");
		model.addAttribute("type", type);
		model.addAttribute("objectId", objectId);
		model.addAttribute("opLoglist", ops);
		model.addAttribute("dataObj", datas);
		return "pages/show/"+modelName+"/logs";
	}
	

	
}
