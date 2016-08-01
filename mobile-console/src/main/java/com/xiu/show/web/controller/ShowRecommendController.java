package com.xiu.show.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.ShowCollectionVO;
import com.xiu.show.core.model.ShowModel;
import com.xiu.show.core.service.IShowRecommendService;


/**
 * 
* @Description: TODO(推荐秀)  
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/showRecommend")
public class ShowRecommendController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShowRecommendController.class);
    @Autowired
    private IShowRecommendService showRecommendService;
    
   /**
    * 发现推荐管理列表
    */
    @RequestMapping(value="findRecommendList", method = RequestMethod.GET)
    public String findRecommendList(String showId,String showTitle,String recommendType,
    		String beginTime,String endTime,String status,Page<?> page, Model model){
    	Map<String,Object> params=new HashMap<String,Object>();
    	params.put("showId", showId);
    	params.put("showTitle", showTitle);
    	params.put("recommendType", recommendType);
    	params.put("beginTime", beginTime);
    	params.put("endTime", endTime);
    	params.put("status", status);
    	//查询列表
    	List<ShowModel> recommendList=new ArrayList<ShowModel>();
    	try{
    		recommendList=showRecommendService.findRecommendList(params, page);
    	}catch(Exception e){
    		LOGGER.error("查询发现推荐管理列表信息异常："+e);
    	}
    	model.addAttribute("recommendList", recommendList);
    	model.addAttribute("showId", showId);
    	model.addAttribute("showTitle", showTitle);
    	model.addAttribute("recommendType", recommendType);
    	model.addAttribute("status", status);
    	model.addAttribute("endTime", endTime);
    	model.addAttribute("beginTime", beginTime);
    	
    	return "pages/show/recommend/recommendList";
    }
    /**
     * 修改推荐显示状态
     */
    @RequestMapping(value = "updateDisplay", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String updateDisplay(HttpServletRequest request,String showId,String status,Model model){
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try{
    		if(StringUtils.isBlank(showId) && StringUtils.isBlank(status)){
        		json.setScode(JsonPackageWrapper.S_ERR);
    			json.setSmsg("参数不能为空！");
        	}else{
        		Map<String,Object> params=new HashMap<String,Object>();
        		params.put("showId", showId);
        		params.put("status", status);
        		int i=showRecommendService.updateDisplay(params);
        		if(i>0){
        			json.setScode(JsonPackageWrapper.S_OK);
        		}else{
        			json.setScode(JsonPackageWrapper.S_ERR);
        			json.setSmsg("修改状态失败！");
        		}
        	}
    	}catch(Exception e){
    		json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("修改状态异常！");
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
    /**
     * 修改排序值
     */
    @RequestMapping(value = "updateOrderSeq", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String updateOrderSeq(HttpServletRequest request,String showId,String orderSeq,Model model){
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try{
    		if(StringUtils.isBlank(showId) && StringUtils.isBlank(orderSeq)){
    			json.setScode(JsonPackageWrapper.S_ERR);
    			json.setSmsg("参数不能为空！");
    		}else{
    			Map<String,Object> params=new HashMap<String,Object>();
    			params.put("showId", showId);
    			params.put("orderSeq", orderSeq);
    			int i=showRecommendService.updateOrderSeq(params);
    			if(i>0){
    				json.setScode(JsonPackageWrapper.S_OK);
    			}else{
    				json.setScode(JsonPackageWrapper.S_ERR);
        			json.setSmsg("修改排序值失败！");
    			}
    		}
    	}catch(Exception e){
    		json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("修改排序值异常！");
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
    /**
     * 批量删除推荐秀
     */
    @RequestMapping(value = "deleteRecommendShow", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String deleteRecommendShow(HttpServletRequest request, String ids, Model model){
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try{
    		if(StringUtils.isBlank(ids)){
    			json.setScode(JsonPackageWrapper.S_ERR);
    			json.setSmsg("参数不能为空！");
    		}else{
    			Map<String,Object> params=new HashMap<String,Object>();
    			params.put("ids", ids);
    			params.put("deleteFlag", 3);//后台删除
    			int i=showRecommendService.deleteRecommendShow(params);
    			if(i>0){
    				json.setScode(JsonPackageWrapper.S_OK);
    			}else{
    				json.setScode(JsonPackageWrapper.S_ERR);
        			json.setSmsg("批量删除秀推荐失败！");
    			}
    		}
    	}catch(Exception e){
    		json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("批量删除秀推荐异常！");
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
    /**
     * 根据秀集合ID查询集合是否存在
     */
    @RequestMapping(value = "findShowCollectionById", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String findShowCollectionById(HttpServletRequest request, String showId, Model model){
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try{
    		if(StringUtils.isBlank(showId)){
    			json.setScode(JsonPackageWrapper.S_ERR);
    			json.setSmsg("秀集合ID不能为空！");
    		}else{
    			Map<String,Object> params=new HashMap<String,Object>();
    			params.put("showId", showId);
    			ShowCollectionVO collection=showRecommendService.findShowCollectionById(params);
    			if(collection==null){
    				json.setScode(JsonPackageWrapper.S_ERR);
        			json.setSmsg("秀集合不存在");
    			}else{
    				json.setScode(JsonPackageWrapper.S_OK);
    				json.setData(collection);
    			}
    		}
    	}catch(Exception e){
    		json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("查询信息异常！");
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
    /**
     * 秀添加至秀集合
     */
    @RequestMapping(value = "addShowCollection", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String addShowCollection(HttpServletRequest request, String ids,String collectionId, Model model){
    	JsonPackageWrapper json = new JsonPackageWrapper();
    	try{
    		if(StringUtils.isBlank(ids) && StringUtils.isBlank(collectionId)){
    			json.setScode(JsonPackageWrapper.S_ERR);
    			json.setSmsg("参数不能为空！");
    		}else{
    			Map<String,Object> params=new HashMap<String,Object>();
    			params.put("ids", ids);
    			params.put("collectionId", collectionId);
    			Map<String,Object> map=showRecommendService.addShowCollection(params);
    			Boolean stauts=(Boolean)map.get("status");
    			if(stauts){
    				json.setScode(JsonPackageWrapper.S_OK);
    				json.setSmsg("添加成功");
    			}else{
    				json.setScode("2");
    				String msg=(String)map.get("msg");
    				json.setSmsg(msg);
    			}
    		}
    	}catch(Exception e){
    		json.setScode(JsonPackageWrapper.S_ERR);
			json.setSmsg("查询信息异常！");
    	}
    	model.addAttribute(Constants.JSON_MODEL__DATA, json);
    	return "";
    }
}
