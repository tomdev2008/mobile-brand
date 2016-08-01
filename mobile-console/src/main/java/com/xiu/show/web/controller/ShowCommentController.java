package com.xiu.show.web.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.xiu.common.web.utils.DateUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.StringUtil;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.show.core.common.constants.ShowConstant;
import com.xiu.show.core.model.CommentModel;
import com.xiu.show.core.model.Page;
import com.xiu.show.core.model.UserWhiteListModel;
import com.xiu.show.core.service.ISensitiveWordService;
import com.xiu.show.core.service.IShowCommentManagerService;
import com.xiu.show.web.service.IUserWhiteListService;

/**
 * 
* @Description: TODO(评论) 
* @author haidong.luo@xiu.com
* @date 2015年6月12日 下午3:25:24 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/showComment")
public class ShowCommentController {

    // 日志
    private static final XLogger LOGGER = XLoggerFactory.getXLogger(ShowCommentController.class);

    @Autowired
    private IShowCommentManagerService showCommentService;
    @Autowired
    private ISensitiveWordService sensitiveWordService;
    @Autowired
    private IUserWhiteListService userWhiteListService;

	/**
	 * 评论列表
	 * @param content
	 * @param showId
	 * @param startDate
	 * @param endDate
	 * @param commentName
	 * @param commentStatus
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String content,String showId,String startDate,String endDate,
			String commentName,String commentStatus,Page<?> page, Model model) {
		Map<String,Object> rmap = new HashMap<String,Object>();
		rmap.put("content", content);
		rmap.put("showId", showId);
		if((startDate==null||startDate.equals(""))&&(endDate==null||endDate.equals(""))){
			startDate=DateUtil.getTimeByDays(new Date(), -30);
			endDate=DateUtil.getNowTime();
		}
		rmap.put("startDate", startDate);
		rmap.put("endDate", endDate);
		rmap.put("content", content);
		rmap.put("showId", showId);
		rmap.put("commentName", commentName);
		rmap.put("commentStatus", commentStatus);
		try{
		rmap=showCommentService.getCommentList(rmap,page);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("查询秀评论异常！",e);
		}
		List<CommentModel> cs=(List<CommentModel>)rmap.get("resultInfo");
		//查询白名单
    	List<UserWhiteListModel> whiteList=userWhiteListService.getUserWhiteList(1, 100);
		model.addAttribute("content", content);
		model.addAttribute("showId", showId);
		model.addAttribute("commentName", commentName);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("commentStatus", commentStatus);
		model.addAttribute("commentlist", cs);
		model.addAttribute("whiteList",whiteList);
		return "pages/show/comment/list";
	}
	

	/**
	 * 评论详情
	 * @param commentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.GET)
	public String info(String commentId,Model model) {
		Long commentIdl=Long.valueOf(commentId);
		CommentModel commentModel=null;
		try{
			commentModel=showCommentService.getCommentInfo(commentIdl);
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.error("查询秀评论异常！",e);
		}
		model.addAttribute("comment", commentModel);
		return "pages/show/comment/info";
	}
	
	/**
	 * 删除评论
	 * @param id
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String delete(HttpServletRequest request, Long id,String resultType, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			User user=AdminSessionUtil.getUser(request);
			try {
				if(null != id){
					int result = showCommentService.deleteComment(id,user.getId(),user.getUsername(),resultType);
					if (result > 0) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("删除成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("删除操作失败！");
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("删除评论失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	    
	     /**
	      * 批量删除评论
	      * @param ids
	      * @param model
	      * @return
	      */
	    @RequestMapping(value = "deleteBatch", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String deleteBatch(HttpServletRequest request, String ids,Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
			try {
				if(null != ids){
					LOGGER.debug("即将被删的商品ids是:{}",new Object[]{ids});
					String[] strIds = ids.split(",");
					List<Long> idsL = new ArrayList<Long>();
					if(strIds != null){
						for(String s : strIds){
							if(!StringUtil.isEmpty(s))
								idsL.add(Long.parseLong(s));
						}
					}
					Map<String,Object> params=new HashMap<String,Object>();
					params.put("ids", idsL);
					User user=AdminSessionUtil.getUser(request);
					params.put(ShowConstant.USER_ID, user.getId());
					params.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
					params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
					
					int result = showCommentService.deleteBatchComment(params);
					if (result > 0) {
						json.setScode(JsonPackageWrapper.S_OK);
						json.setData("删除成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("删除操作失败！");
					}
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			} catch (Exception e) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("系统发生异常！");
				LOGGER.error("批量删除评论失败！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	/**
	 * 设置、取消仅自己可见（单个）
	 */
	  @RequestMapping(value="updateVisual", method = RequestMethod.GET,produces = "application/json", params = "format=json")
	  public String updateVisual(HttpServletRequest request,Long id,Integer isVisual,Model model){
		  JsonPackageWrapper json = new JsonPackageWrapper();
		  User user=AdminSessionUtil.getUser(request);
		  try{
			  if(null !=id){
				  int result=showCommentService.updateVisual(id, user.getId(), user.getUsername(), isVisual);
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
				LOGGER.error("设置仅自己可见失败！", e);
		  }
		  model.addAttribute(Constants.JSON_MODEL__DATA, json);
		  return "";
	  }
	  /**
	   * 设置为仅自己可见（多个）
	   */
	  @RequestMapping(value="updateBatch",method = RequestMethod.GET, produces = "application/json", params = "format=json")
	  public String updateBatch(HttpServletRequest request, String ids,Model model){
		  JsonPackageWrapper json = new JsonPackageWrapper();
		  if(null != ids){
			  LOGGER.debug("即将被设置为仅自己可见的商品ids是:{}",new Object[]{ids});
			  String[] strIds = ids.split(",");
			  List<Long> idsL = new ArrayList<Long>();
				if(strIds != null){
					for(String s : strIds){
						if(!StringUtil.isEmpty(s))
							idsL.add(Long.parseLong(s));
					}
				}
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("ids", idsL);
				User user=AdminSessionUtil.getUser(request);
				params.put(ShowConstant.USER_ID, user.getId());
				params.put(ShowConstant.OPERATE_USER_NAME, user.getUsername());
				params.put(ShowConstant.SHOW_OPERATE_USER_TYPE, ShowConstant.SHOW_OPERATE_USER_SYSTEM_MANAGER);
				params.put("isVisual", 1);
				int result=showCommentService.updateBatch(params);
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
		  model.addAttribute(Constants.JSON_MODEL__DATA, json);
		  return "";
	  }
	    /**
		 * 添加评论
		 */
		@RequestMapping(value = "addComment", method = RequestMethod.GET, produces = "application/json", params = "format=json")
		public String addComment(HttpServletRequest request,Model model,Long showId,Long userId,
				String commentedUserId){
			JsonPackageWrapper json = new JsonPackageWrapper();
			try{
				request.setCharacterEncoding("UTF-8");
				String comment=request.getParameter("content");
				String content=new String(comment.getBytes("ISO-8859-1"),"UTF-8");
				//判断评论内容的长度
				if(!content.matches(".{1,1000}")){
					 json.setScode(JsonPackageWrapper.S_ERR);
	                 json.setSmsg("评论长度限制在1-1000字符以内!");
				}
				//判断是否存在敏感词语
				boolean sensitiveFlag = sensitiveWordService.isSensitiveExists(content);
				if(sensitiveFlag) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setSmsg("您输入的内容包含不良信息!");
				}
				//添加评论
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("userId", userId);
				params.put("content", content);
				params.put("showId", showId);
				if(StringUtils.isNotBlank(commentedUserId)){
					params.put("commentedUserId", Long.parseLong(commentedUserId));
				}
				Map<String,Object> resultMap=showCommentService.addShowComment(params);
				Boolean status=(Boolean)resultMap.get("status");
				if(status){
					json.setScode(JsonPackageWrapper.S_OK);
					json.setSmsg("评论成功");
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setSmsg("评论失败");
				}
			}catch(Exception e){
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setSmsg("系统发生异常！");
				LOGGER.error("添加评论异常！", e);
			}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
		}
}
