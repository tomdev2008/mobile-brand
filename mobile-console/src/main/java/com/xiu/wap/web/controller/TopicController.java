package com.xiu.wap.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.xiu.common.web.utils.ExcelUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.StringUtil;
import com.xiu.common.web.utils.UploadPicUtils;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminAuthInfoHolder;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.constants.GlobalConstants;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.LabelCentre;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.Topic;
import com.xiu.mobile.core.model.TopicType;
import com.xiu.mobile.core.service.ILabelService;
import com.xiu.mobile.core.service.ITopicService;
import com.xiu.mobile.core.service.ITopicTypeService;
import com.xiu.mobile.core.utils.ObjectUtil;
import com.xiu.wap.web.service.ISearchService;

/**
 * @CLASS :com.xiu.recommend.web.controller.TopicController
 * @DESCRIPTION :
 * @AUTHOR :jack.jia@xiu.com
 * @VERSION :v1.0
 * @DATE :2014-03-18
 */
@AuthRequired
@Controller
@RequestMapping(value = "/topic")
public class TopicController {

	// 日志
	private static final XLogger LOGGER = XLoggerFactory
			.getXLogger(TopicController.class);
	@Autowired
	private ITopicService topicService;
	
	@Autowired
	private ITopicTypeService topicTypeService;

	@Value("${upload_pic_path}")
	private String upload_pic_path;
	
	@Autowired
	private ILabelService labelService;
	
	@Autowired
	private ISearchService searchService;
	
/*	@Value("${images_domain_name}")
	private String images_domain_name;*/

	/**
	 * 查询卖场
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String activity_id, String name ,String display,
			String start_time,String end_time,String now_time,String status , 
			String labelName ,Integer isShowCountDown ,Integer isEndCanBeSearch ,
			Page<?> page, Model model,HttpServletRequest request) {
		List<Topic> topicList = new ArrayList<Topic>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		//查询范围
		String queryType = request.getParameter("queryType"); 
		if(queryType == null || queryType.equals("")) {
			//默认查询顶级卖场
			queryType = "1";
		}
		//卖场形式
		String displayStyle = request.getParameter("displayStyle");
		if (StringUtils.isNotBlank(displayStyle)) {
			map.put("displayStyle", Integer.parseInt(displayStyle));
		}
		//卖场形式
		String topicTypeId = request.getParameter("topicTypeId");
		if (StringUtils.isNotBlank(topicTypeId)) {
			map.put("topicTypeId", Integer.parseInt(topicTypeId));
		}
		try {
			//activity_id过滤掉非数字和逗号字符
			if(StringUtils.isNotBlank(activity_id)) {
				String regEx = "[^0-9,]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(activity_id);
				activity_id = m.replaceAll("").trim(); //替换字符
				if(activity_id.equals(",")) {
					activity_id = "";
				}
			}
			
			// 加载符合条件的所有启用的菜单
			map.put("activity_id", activity_id);
			map.put("name", name);
			map.put("display", display);
			SimpleDateFormat dateFormat_time = new SimpleDateFormat(Constants.dateFormat_time);
			if(null!=start_time  && ! "".equals(start_time.trim())){
				map.put("start_time", dateFormat_time.parse(start_time));
			}
			if(null!=end_time  && ! "".equals(end_time.trim())){
				map.put("end_time", dateFormat_time.parse(end_time));
			}
			if(null!=now_time  && ! "".equals(now_time.trim())){
				map.put("now_time", dateFormat_time.parse(now_time));
			}
			String contentType = request.getParameter("contentType");
			if (StringUtils.isNotBlank(contentType)) {
				map.put("contentType", contentType);
			}
			if (StringUtils.isNotBlank(labelName)) {
				map.put("labelName", labelName);
			}
			if (isShowCountDown!=null&&isShowCountDown!=-1) {
				map.put("isShowCountDown", isShowCountDown);
			}
			if (isEndCanBeSearch!=null&&isEndCanBeSearch!=-1) {
				map.put("isEndCanBeSearch", isEndCanBeSearch);
			}
			
			map.put("queryType", queryType);	
			map.put("status", status);	
			
			//如果activity_id包含逗号则设置批量查询参数
			if(activity_id != null && !activity_id.equals("") && activity_id.indexOf(",") > -1) {
				map.put("batchQuery", 1);
				map.put("activity_id", activity_id.split(","));
			}
			
			topicList = topicService.getTopicList(map, page);

			Date now= dateFormat_time.parse(dateFormat_time.format(new Date()));
			if (null != now_time && !"".equals(now_time.trim())) {
				now = dateFormat_time.parse(now_time);
			}
			
			for(Topic topic:topicList){
				if(topic.getEnd_time().before(now)){
					topic.setStatus_s("已过期");
				}else if (topic.getStart_time().after(now)){
					topic.setStatus_s("未开始");
				}else{
					topic.setStatus_s("进行中");
				}
			}
			
			Map paraMap = new HashMap();
			paraMap.put("status", 1);	//启用的分类
			List<TopicType> topicTypeList = topicTypeService.getAllTopicTypeList(paraMap);
			model.addAttribute("topicTypeList", topicTypeList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询卖场活动异常！", e);
		}
		model.addAttribute("activity_id", activity_id);
		model.addAttribute("name", name);
		model.addAttribute("status", status);
		model.addAttribute("display", display);
		model.addAttribute("queryType", queryType);
		model.addAttribute("displayStyle", displayStyle);
		model.addAttribute("topicTypeId", topicTypeId);
		model.addAttribute("start_time", (null==start_time||"".equals(start_time))?null: start_time  );
		model.addAttribute("end_time",(null==end_time||"".equals(end_time))?null: end_time  );
		model.addAttribute("now_time",(null==now_time||"".equals(now_time))?null: now_time  );
		model.addAttribute("contentType", request.getParameter("contentType"));
		model.addAttribute("labelName", labelName);
		model.addAttribute("isEndCanBeSearch", isEndCanBeSearch);
		model.addAttribute("isShowCountDown", isShowCountDown);
		model.addAttribute("topicList", topicList);

		return "pages/topic/list";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest request, Model model, HttpServletResponse response) {
		Map paraMap = new HashMap(); //默认参数
		List<TopicType> topicTypeList = topicTypeService.getAllTopicTypeList(paraMap);
		model.addAttribute("topicTypeList", topicTypeList);
		return "pages/topic/create";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request,HttpServletResponse response, Topic topic,Model model) {
		try {
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
			//图片检测
			MultipartFile patch = multipartRequest.getFile("mobile_pic_f");
			
			String fileName = patch.getOriginalFilename();
			String picPath = "";
			if(null!=fileName && !"".equals(fileName)){
				fileName = UUID.randomUUID().toString()+ fileName.substring(fileName.lastIndexOf("."));
				picPath = "/m/topic" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
				topic.setMobile_pic(picPath);// 图片上传路径
				//上传
				String picFile = upload_pic_path.trim() + picPath;
				UploadPicUtils.isUploadPathExsit(picFile);
				patch.transferTo(new File(picFile));// 上传图片
			} 
			
			//卖场集顶部焦点图片
			MultipartFile bannerPatch = multipartRequest.getFile("banner_pic_f");
			if(bannerPatch != null) {
				String bannerFileName = bannerPatch.getOriginalFilename();
				if(bannerFileName != null && !bannerFileName.equals("")) {
					bannerFileName = UUID.randomUUID().toString()+ bannerFileName.substring(bannerFileName.lastIndexOf("."));
					String bannerPicPath = "/m/topic" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + bannerFileName;
					topic.setBannerPic(bannerPicPath);// 图片上传路径
					//上传
					String bannerPicFile = upload_pic_path.trim() + bannerPicPath;
					UploadPicUtils.isUploadPathExsit(bannerPicFile);
					bannerPatch.transferTo(new File(bannerPicFile));// 上传图片
				} else {
					topic.setBannerPic(picPath);  //没有则默认取mobile_pic
				}
			} else {
				topic.setBannerPic(picPath);  //没有则默认取mobile_pic
			}
			
			Long activity_id = topicService.getCMSActivitySeq(); //获取卖场活动ID序列
			BigDecimal id = new BigDecimal(activity_id); 
			topic.setActivity_id(id);
			int count = topicService.insert(topic);   //1.插入卖场
			if(count > 0) {
				//如果卖场新增成功
				Map paraMap = new HashMap();
				String topicTypeRela = request.getParameter("topicTypeRela");
				paraMap.put("activity_id", activity_id);
				paraMap.put("topicTypeRela", topicTypeRela);
				
				topicService.insertTopicTypeRela(paraMap); //2.插入卖场所属分类
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("新增卖场活动异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		return "redirect:/topic/list";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(BigDecimal activity_id, Model model, Page<?> page) {
		try {
			Topic topic = topicService.getTopicByActivityId(activity_id);
			model.addAttribute("topic", topic);
			
			Map paraMap = new HashMap(); //默认参数
			paraMap.put("activity_id", activity_id);
			List<TopicType> topicTypeList = topicTypeService.getTopicTypeAndActivityList(paraMap);	//查询了卖场在分类下是否有数据
			model.addAttribute("topicTypeList", topicTypeList);
			
			if(topic != null && topic.getContentType() != 4) {
				//如果不是卖场集，则查询二级卖场所属卖场集
				HashMap map = new HashMap();
				map.put("act_id", activity_id);
				List<Topic> topicList = topicService.querySetByActivity(map, page);
				model.addAttribute("topicList", topicList);
			}
			
			//查询标签
			// 查询已经添加的标签
			List<Label> label=labelService.findLabelListByObjectIdAndType(activity_id.longValue(), GlobalConstants.LABEL_TYPE_TOPIC);
			model.addAttribute("updateLabelList", label);
			 // 查询常用的标签（热门和新加的）
			Map<String,Object> labels=labelService.findCommonLabelList();
			model.addAttribute("newAddList", labels.get("newAddList"));
			model.addAttribute("historyList", labels.get("historyList"));

			// 卖场分类 1:NORMAL 2:H5 3:特卖 4:卖场集
			if (topic!=null&&topic.getContentType()==2) {
				//HTML5
				return "pages/topic/h5Edit";
			}
			if(topic != null && topic.getContentType() == 4) {
				//卖场集
				String activityIds = topicService.queryActivityIdBySet(activity_id.intValue());
				model.addAttribute("activityIds", activityIds);
				return "pages/topic/setEdit";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询卖场活动异常！", e);
		}
		return "pages/topic/edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(MultipartHttpServletRequest request,HttpServletResponse response, Topic topic,
			String[] labelIds,Model model) {
		try {
			//图片检测
			MultipartFile patch = request.getFile("mobile_pic_f");
			
			if(patch != null) {
				String fileName = patch.getOriginalFilename();
				if(null!=fileName && !"".equals(fileName)){
					fileName = UUID.randomUUID().toString()
							+ fileName.substring(fileName.lastIndexOf("."));
					String picPath = "/m/topic"
							+ new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + fileName;
					topic.setMobile_pic(picPath);// 图片上传路径
					//上传
					String picFile = upload_pic_path.trim() + picPath;
					UploadPicUtils.isUploadPathExsit(picFile);
					patch.transferTo(new File(picFile));// 上传图片
				}
			}
			
			//卖场集顶部焦点图
			MultipartFile bannerPatch = request.getFile("banner_pic_f");
			if(bannerPatch != null) {
				String bannerFileName = bannerPatch.getOriginalFilename();
				if(null!=bannerFileName && !"".equals(bannerFileName)){
					bannerFileName = UUID.randomUUID().toString() + bannerFileName.substring(bannerFileName.lastIndexOf("."));
					String bannerPicPath = "/m/topic" + new SimpleDateFormat("/yyyy/MM/dd/").format(new Date()) + bannerFileName;
					topic.setBannerPic(bannerPicPath);// 图片上传路径
					//上传
					String bannerPicFile = upload_pic_path.trim() + bannerPicPath;
					UploadPicUtils.isUploadPathExsit(bannerPicFile);
					bannerPatch.transferTo(new File(bannerPicFile));// 上传图片
				}
			}
			
			Long topicId=topic.getActivity_id().longValue();
			int count = topicService.update(topic);
			if(count > 0) {
				//如果更新成功，则更新卖场所属分类信息
				Map paraMap = new HashMap();
				paraMap.put("activity_id",topic.getActivity_id());
				topicService.deleteTopicTypeRela(paraMap);  //删除旧的卖场所属分类
				
				String topicTypeRela = request.getParameter("topicTypeRela");
				paraMap.put("topicTypeRela", topicTypeRela); 
				topicService.insertTopicTypeRela(paraMap);	//新增卖场所属分类
				
				//如果有标签则绑定标签
					List<LabelCentre> labelCentres=new ArrayList<LabelCentre>();
					if(labelIds!=null&&labelIds.length>0){
						for (int i = 0; i < labelIds.length; i++) {
							LabelCentre lc=new LabelCentre();
							lc.setLabelId(ObjectUtil.getLong(labelIds[i]));
							labelCentres.add(lc);
						}
					}
					labelService.bindLableToSevice(GlobalConstants.LABEL_TYPE_TOPIC, topicId, labelCentres);
				//加入到搜索表 中
				searchService.addDataToSearch(GlobalConstants.LABEL_TYPE_TOPIC, topicId);
				
			}
			response.getWriter().print("<script> alert('success!') ;if(window.opener) { window.opener.location.reload();} window.close(); </script> ");
		} catch (Exception e) {
			LOGGER.error("更新卖场活动异常！", e);
			try {
				response.getWriter().print("<script> alert('failed!') ;if(window.opener) { window.opener.location.reload();}  window.close(); </script> ");
			} catch (IOException e1) {
				LOGGER.error("打印异常！", e1);
			}
		}
		return null;
	}
	
	/**
	 * 创建卖场集
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/createSet", method = RequestMethod.GET)
	public String createSet(HttpServletRequest request, Model model, HttpServletResponse response) {
		Map paraMap = new HashMap();
		paraMap.put("status", "1"); //可用的卖场分类
		List<TopicType> topicTypeList = topicTypeService.getAllTopicTypeList(paraMap);
		model.addAttribute("topicTypeList", topicTypeList);
		return "pages/topic/createSet";
	}
	
	/**
	 * 进入移入卖场集页面
	 * @param act_id	卖场ID
	 * @param display_style	  卖场形式，用来判断卖场集和二级卖场形式是否一致
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addInSet", method = RequestMethod.GET)
	public String addInSet(String act_id, Long display_style, Model model) {
		model.addAttribute("act_id", act_id);
		model.addAttribute("display_style", display_style);
		return "pages/topic/addInSet";
	}
	
	/**
	 * 进入卖场集管理界面
	 * @param set_id	卖场集ID
	 * @param act_id	查询时输入的卖场ID，批量查询时ID以逗号分隔
	 * @param display_style		卖场集形式，用来判断卖场集和二级卖场形式是否一致
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "enterIntoSet", method = RequestMethod.GET)
	public String enterIntoSet(BigDecimal set_id,String act_id, Long display_style, Model model, Page<?> page) {
		try {
			Map map = new HashMap();
			map.put("set_id", set_id);
			
			//1.查询卖场集下的二级卖场类表
			List<Topic> topicList = topicService.queryActivityBySet(map, page);
			
			SimpleDateFormat dateFormat_time = new SimpleDateFormat(Constants.dateFormat_time);
			Date now = dateFormat_time.parse(dateFormat_time.format(new Date()));
			for(Topic topic:topicList){
				if(topic.getEnd_time().before(now)){
					topic.setStatus_s("已过期");
				}else if (topic.getStart_time().after(now)){
					topic.setStatus_s("未开始");
				}else{
					topic.setStatus_s("进行中");
				}
			}
			
			//2.查询卖场集信息
			map.put("activity_id", set_id);
			Topic topicSet = topicService.queryActivity(map);
			
			
			//act_id过滤掉非数字和逗号字符
			if(StringUtils.isNotBlank(act_id)) {
				String regEx = "[^0-9,]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(act_id);
				act_id = m.replaceAll("").trim(); //替换字符
				if(act_id.equals(",")) {
					act_id = "";
				}
			}
			
			//3.查询卖场信息
			if(act_id != null && !act_id.equals("")) {
				if(act_id.indexOf(",") > -1) {
					//批量查询
					map.put("batchQuery", 1);	
					map.put("activity_id", act_id.split(","));
				} else {
					map.put("activity_id", act_id);
				}
				map.put("contentTypeNot", 4);
				List<Topic> queryTopicList = topicService.queryActivityList(map);
				if(queryTopicList != null) {
					for(Topic topic:queryTopicList){
						if(topic.getEnd_time().before(now)){
							topic.setStatus_s("已过期");
						}else if (topic.getStart_time().after(now)){
							topic.setStatus_s("未开始");
						}else{
							topic.setStatus_s("进行中");
						}
					}
				}
				model.addAttribute("queryTopicList", queryTopicList);
			}
			
			model.addAttribute("set_id",set_id);
			model.addAttribute("act_id",act_id);
			model.addAttribute("display_style",display_style);
			model.addAttribute("topicList", topicList);
			model.addAttribute("activityCounts", page.getTotalCount());
			model.addAttribute("topicSet", topicSet);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("进入卖场集管理界面异常！", e);
		}
		return "pages/topic/setManager";
	}
	
	/**
	 * 查询卖场集
	 * @param act_id	卖场ID
	 * @param set_id	卖场集ID
	 * @param display_style		卖场形式
	 * @param contentType	卖场内容： 1.促销 2.HTML5 3.特卖 4.卖场集
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/queryActivitySet", method = RequestMethod.POST)
	public String queryActivitySet(String act_id, Long set_id, Long display_style, Long contentType, Model model) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			map.put("activity_id", set_id);
			map.put("contentType", contentType); //查询卖场内容
			Topic topic = topicService.queryActivity(map);
			model.addAttribute("topic", topic);
		} catch (Exception e) {
			LOGGER.error("查询卖场集异常！", e);
		}
		
		model.addAttribute("act_id", act_id);
		model.addAttribute("set_id", set_id);
		model.addAttribute("display_style", display_style);
		return "pages/topic/addInSet";
	}
	
	/**
	 * 移入卖场集
	 * @param act_id	卖场ID，批量移入卖场集时，以逗号分隔
	 * @param set_id	卖场集ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addActivitySet", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String addActivitySet(String act_id, String set_id, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map map = new HashMap();
		map.put("act_id", act_id);
		map.put("set_id", Long.parseLong(set_id));
		User user=AdminAuthInfoHolder.getUserAuthInfo();
		map.put("userName", user.getUsername());	//登陆用户名
		try {
			if(set_id == null || set_id.equals("") || act_id == null || act_id.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				int result = topicService.addActivitySet(map);	//移入卖场集
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("删除成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("删除操作失败！");
				}
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("移入卖场集异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 移出卖场集
	 * @param set_id	卖场集ID
	 * @param act_id	卖场ID，批量删除时，以逗号分割
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteActivitySet", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String deleteActivitySet(String set_id, String act_id, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map map = new HashMap();
		map.put("set_id", Long.parseLong(set_id));
		map.put("act_id", act_id);
		try {
			if(set_id == null || set_id.equals("") || act_id == null || act_id.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				int result = topicService.deleteActivitySet(map);	//移出卖场集
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("移出卖场集成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("移出卖场集失败！");
				}
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("移出卖场集异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 查询卖场集下是否已存在二级卖场
	 * @param set_id	卖场集ID
	 * @param act_id	卖场ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/hasExistsActivitySet", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String hasExistsActivitySet(String set_id, String act_id, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map map = new HashMap();
		map.put("set_id", Long.parseLong(set_id));
		map.put("act_id", Long.parseLong(act_id));
		try {
			if(set_id == null || set_id.equals("") || act_id == null || act_id.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				int result = topicService.hasExistsActivitySet(map);	//查询卖场集下是否已存在二级卖场
				if(result > 0) {
					json.setScode("1");
					json.setData("卖场集中已存在二级卖场！");
				} else {
					json.setScode("0");
					json.setData("卖场集中不存在二级卖场！");
				}
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("查询卖场集是否存在二级卖场异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	/**
	 * 清空卖场集
	 * @param set_id	卖场集ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/emptyActivitySet", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	public String emptyActivitySet(String set_id, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		Map map = new HashMap();
		map.put("set_id", Long.parseLong(set_id));
		try {
			if(set_id == null || set_id.equals("")) {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("参数不完整！");
			} else {
				int result = topicService.emptyActivitySet(map);	//清空卖场集
				if(result >= 0) {
					json.setScode(JsonPackageWrapper.S_OK);
					json.setData("清空卖场集成功！");
				} else {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("清空卖场集失败！");
				}
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			e.printStackTrace();
			LOGGER.error("清空卖场集异常！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
	
	@RequestMapping(value = "previewTopicList", method = RequestMethod.GET)
	public String previewTopicList(String topicTypeId, String now_time, Page<?> page, Model model,HttpServletRequest request) {
		List<Topic> topicList = new ArrayList<Topic>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		String typeId = topicTypeId;
		try {
			if(StringUtils.isBlank(typeId)) {
				typeId = "1";
			} 
			map.put("topicTypeId", typeId);
			if(null!=now_time  && ! "".equals(now_time.trim())){
				SimpleDateFormat dateFormat_time = new SimpleDateFormat(Constants.dateFormat_time);
				map.put("nowTime", dateFormat_time.parse(now_time));
			}
			
			//查询要预览的卖场数据
			topicList = topicService.getPreviewTopicList(map, page);

			map.put("status", 1);	//启用的分类
			List<TopicType> topicTypeList = topicTypeService.getAllTopicTypeList(map);
			model.addAttribute("topicTypeList", topicTypeList);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询预览的卖场列表异常！", e);
		}
		model.addAttribute("topicTypeId", typeId);
		model.addAttribute("now_time", now_time);
		model.addAttribute("topicList", topicList);

		return "pages/topic/previewList";
	}
	
	
	/**
	 * 检查卖场(卖场集除外)是否存在
	 * @param request
	 * @param topicId
	 * @param model
	 * @return
	 */
	 @RequestMapping(value = "checkTopic", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	    public String checkTopic(HttpServletRequest request,  Long activityId, Model model){
			JsonPackageWrapper json = new JsonPackageWrapper();
				if(null != activityId){
					Map params=new HashMap();
					params.put("activity_id", activityId);
					boolean issuccess = false;
					Topic topic = topicService.queryActivity(params);
						 if(topic!=null&&topic.getContentType()!=4){//卖场集除外
								json.setScode(JsonPackageWrapper.S_OK);
						 }else{
								json.setScode("2");
						 }
				}else{
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("参数不完整！");
				}
			model.addAttribute(Constants.JSON_MODEL__DATA, json);
			return "";
	    }
	 
	 /**
	  * 检查卖场集是否存在
	  * @param request
	  * @param topicId
	  * @param model
	  * @return
	  */
	 @RequestMapping(value = "checkTopicSet", method = RequestMethod.GET, produces = "application/json", params = "format=json")
	 public String checkTopicSet(HttpServletRequest request,  Long activityId, Model model){
		 JsonPackageWrapper json = new JsonPackageWrapper();
		 if(null != activityId){
			 Map params=new HashMap();
			 params.put("activity_id", activityId);
			 boolean issuccess = false;
			 Topic topic = topicService.queryActivity(params);
			 if(topic!=null&&topic.getContentType()==4){//必须为卖场集
				 json.setScode(JsonPackageWrapper.S_OK);
			 }else{
				 json.setScode("2");
			 }
		 }else{
			 json.setScode(JsonPackageWrapper.S_ERR);
			 json.setData("参数不完整！");
		 }
		 model.addAttribute(Constants.JSON_MODEL__DATA, json);
		 return "";
	 }

	 
	  
	    /**
	     * 进入导入页面
	     * @return
	     */
	    @RequestMapping(value = "toimport", method = RequestMethod.GET)
	    public String toimport(){
	    	return "pages/topic/importUpdate";
	    }
	    
	    /**
	     * 下载导入模板
	     * @param request
	     * @param response
	     * @param model
	     * @return
	     */
	    @RequestMapping(value = "down", method = RequestMethod.GET)
	    public String down(HttpServletRequest request,HttpServletResponse response,Model model){
	    	String path = request.getSession().getServletContext().getRealPath("/");
	    	path += "template/topic_batch_update_template.xls";
	    	path = path.replace("\\", "/");
	    	LOGGER.debug("模板文件路径：{}", new Object[]{path});
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
				LOGGER.error("下载单品导入模板出现异常",ex);
				return null;
			}
			return null;
	    }
	    
	    @RequestMapping(value = "import_excel", method = RequestMethod.POST)
	    public String import_excel(MultipartHttpServletRequest request,Model model){
	    	try{
	    		// 获取Excel文件流
				MultipartFile patch = request.getFile("dataFile");
				
				// 原始数据
				List<Map<Object, Object>> listMap = ExcelUtil.importStatements(patch.getInputStream());
				
				// 数据验证
				boolean flag = checkExcelData(listMap, model);
				if(!flag){
					// 返回页面，提示验证结果
					return "pages/topic/importUpdate";
				}
				
				// 去掉Excel表格中重复的卖场id
				Map<String, Map<Object,Object>> data = removeRepetition(listMap);
				
				// 调用service接口导入
				User user = AdminSessionUtil.getUser(request);
				int count = topicService.batchUpdateImportTopics(data, user.getId());
				LOGGER.debug("{}.import_excel 导入商品完成,共导入{}条",
						new Object[]{FindGoodsImportController.class,count});
	    	}catch(Exception e){
	    		LOGGER.error("导入修改卖场异常", e);
	    		model.addAttribute("error", "导入修改卖场异常");
	    		// 返回页面，提示验证结果
				return "pages/topic/importUpdate";
	    	}
	    	return "redirect:/topic/list";
	    }
	    
	    /**
	     * 去掉重复的goodsSn
	     * @return
	     */
	    private Map<String,Map<Object,Object>> removeRepetition(List<Map<Object,Object>> data){
	    	Map<String,Map<Object,Object>> result = new HashMap<String, Map<Object,Object>>();
	    	if(null != data && !data.isEmpty()){
	    		
	    		// 转化---把goodsSn当key,map当value放入新的map中
	    		Map<String,Map<Object,Object>> allData = new HashMap<String, Map<Object,Object>>();
				int i = 0;
	    		for(Map<Object,Object> map : data){
	    			String goodsSn = (String)map.get(0);  // excel中第一列为goodsSn
					if(StringUtils.isBlank(goodsSn)){//HTML5 卖场
						result.put("HTML5" + i++, map);
						continue;
					}
	    			allData.put(goodsSn, map);
	    		}
	    		
	    		// 用set去除重复的goodsSn
	    		Set<Map<Object,Object>> dataPool = new HashSet<Map<Object,Object>>();
	    		for(String key : allData.keySet()){
	    			dataPool.add(allData.get(key));
	    		}
	    		
	    		// 把set中的结果放入result中，然后返回
	    		Iterator<Map<Object,Object>> it = dataPool.iterator();
	    		while (it.hasNext()){
	    			Map<Object,Object> m = it.next();
	    			result.put((String)m.get(0),m);
	    		}
	    	}
	    	return result;
	    }
	    
	    /**
	     * Excel数据校验
	     * @param listMap
	     * @param model
	     * @return
	     */
		private boolean checkExcelData(List<Map<Object, Object>> listMap, Model model) {
			boolean flag = true;
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date startDate = null;
			Date endDate = null;
			if (listMap != null && !listMap.isEmpty()) {
				List<String> activityIdList = new ArrayList<String>();
				for (int i = 0; i < listMap.size(); i++) {
					Map<Object, Object> map = listMap.get(i);
					// 卖场ID
					String activityId = (String) map.get(0);
					// 卖场名称
					String name = (String) map.get(1);
					// 标题
					String title = (String) map.get(2);
					//卖场类型
					String contentType = (String)map.get(3);
					// 分类-旧
					String typeOld = (String) map.get(4);
					// 分类
					String type = (String) map.get(5);
					// 排序
					String order = (String) map.get(6);
					// 开始时间
					String beginTime = (String) map.get(7);
					// 结束时间
					String endTime = (String) map.get(8);
					// 广告图片地址
					String adImgUrl = (String) map.get(9);
					// 卖场顶部焦点图
					String topImg = (String) map.get(10);
					// 卖场形式
					String displayStyle = (String) map.get(11);
					// 卖场是否显示
					String isDisplay = (String) map.get(12);
					//Html5卖场URL
					String topicUrl = (String) map.get(13);
					//是否显示倒计时
					String isShowCuntDownStr = (String) map.get(14);
					//结束后是否被搜索到
					String isEndBeSearchStr = (String) map.get(15);
					//标签名称串
					String labelsStr = (String) map.get(16);

					if(StringUtils.isNotBlank(contentType)){
						map.put(3,"HTML5".equalsIgnoreCase(contentType) ? 2 : 1);
					}

					// 卖场ID
					if (!"HTML5".equals(contentType) && StringUtil.isEmpty(activityId)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行非HTML5卖场ID不能为空。");
						break;
					} else {
						if (activityId.length() > 10) {
							flag = false;
							model.addAttribute("error", "第" + (i + 1) + "行卖场ID长度超过10个字符，请重填");
							break;
						}
					}
					// 卖场名称
					if (StringUtil.isEmpty(name)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行卖场名称不能为空。");
						break;
					} else {
						if (name.length() < 4) {
							flag = false;
							model.addAttribute("error", "第" + (i + 1) + "行卖场名称长度不能少于4个字，请重填");
							break;
						}
						if (name.length() > 500) {
							map.put(1, name.substring(0, 500));
						}
					}



					// 分类-旧
					if (StringUtil.isEmpty(typeOld)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行卖场分类（旧）不能为空。");
						break;
					} else {

						if (typeOld.length() > 50) {
							typeOld=typeOld.substring(0, 50);
						}
						
						Long topicTypeId=null;
						if(typeOld.equals("中性")){
							topicTypeId=1l;
						}
						else if(typeOld.equals("男士")){
							topicTypeId=2l;
						}else if(typeOld.equals("女士")){
							topicTypeId=3l;
						}
						if(topicTypeId==null){
							flag = false;
							model.addAttribute("error", "第" + (i + 1) + "行卖场分类（旧）取值只能为 中性、男士、女士。");
							break;
						}
						map.put(4, topicTypeId);
					}
					// 分类
					if (StringUtil.isEmpty(type)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行卖场分类不能为空。");
						break;
					} else {
						String[] strIds = type.split(",");
						List<String> namesL = new ArrayList<String>();
						if(strIds != null){
							for(String s : strIds){
								if(!StringUtil.isEmpty(s))
									namesL.add(s);
							}
						}
						Map typeNames=new HashMap();
						typeNames.put("names", namesL);
						List<Long> ids=topicTypeService.getTopicTypeIdByNames(typeNames);
						if(ids!=null&&ids.size()!=namesL.size()){
							flag = false;
							model.addAttribute("error", "第" + (i + 1) + "行卖场分类录入有误，请录入启用的卖场分类，多个则用英文逗号隔开。");
							break;
						}
						map.put(5, ids);
					}

					if(order.length()>5){
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行排序值不能大于5位数。");
						break;
					}
					
					
					//开始时间
					if (!StringUtil.isEmpty(beginTime)) {
						try {
							startDate = sf.parse(beginTime);
							map.put(7,startDate);
						} catch (ParseException e) {
							flag = false;
							LOGGER.error("开始时间转换为日期时异常！", e);
							model.addAttribute("error", "第" + (i + 1) + "行开始时间格式不正确，请重填");
							break;
						}
					} else {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行开始时间不能为空，请重填");
						break;
					}

					//结束时间
					if (!StringUtil.isEmpty(endTime)) {
						try {
							endDate = sf.parse(endTime);
						} catch (ParseException e) {
							flag = false;
							LOGGER.error("结束时间转换为日期时异常！", e);
							model.addAttribute("error", "第" + (i + 1) + "行结束时间格式不正确，请重填");
							break;
						}
					} 
					map.put(8,endDate);
				     String imgUrlReg="http://.+\\.com/m/topic/[0-9]{4}/[0-9]{2}/[0-9]{2}/.+\\..{2,6}";					// 广告图片地址
					if (StringUtil.isEmpty(adImgUrl)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行广告图片地址不能为空。");
						break;
					}else if(!adImgUrl.matches(imgUrlReg)){
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行广告图片地址格式不正确。");
						break;
					}else{
						adImgUrl=adImgUrl.substring(adImgUrl.indexOf(".com")+4);
						map.put(9, adImgUrl);
					}
					
					// 卖场顶部焦点图
					if (StringUtil.isEmpty(topImg)) {
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行卖场顶部焦点图不能为空。");
						break;
					} else if(!topImg.matches(imgUrlReg)){
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行卖场顶部焦点图格式不正确。");
						break;
					}else{
						topImg=topImg.substring(topImg.indexOf(".com")+4);
						map.put(10, topImg);
					}
					
					//卖场形式
					Integer displayStyleInt=null;
					if(displayStyle!=null){
						if(displayStyle.equals("横向")){
							displayStyleInt=1;
						}else if(displayStyle.equals("竖向")){
							displayStyleInt=2;
						}
					}
					map.put(11, displayStyleInt);
					
					// 卖场是否显示
					
					String  isDisplaySign=null;
					if(isDisplay!=null){
						if(isDisplay.equals("显示")){
							isDisplaySign="Y";
						}else if(isDisplay.equals("不显示")){
							isDisplaySign="N";
						}
					}
					map.put(12, isDisplaySign);

					if("HTML5".equalsIgnoreCase(contentType) & StringUtils.isBlank(topicUrl)){
						flag = false;
						model.addAttribute("error", "第" + (i + 1) + "行HTML5卖场URL地址不能为空，请重填");
						break;
					}
					//是否显示倒计时
					Integer isShowCuntDown=1;//默认显示
					if(isShowCuntDownStr!=null&&isShowCuntDownStr.equals("否")){
						isShowCuntDown=0;
					}
					map.put(14, isShowCuntDown);
					
					//结束后是否被搜索到
					Integer isEndBeSearch=0;//默认不被搜索到
					if(isEndBeSearchStr!=null&&isEndBeSearchStr.equals("是")){
						isEndBeSearch=1;
					}
					map.put(15, isEndBeSearch);

					//标签名称串
					List<Long> labelIds=new ArrayList<Long>();
					if(labelsStr!=null&&!labelsStr.equals("")){
						String[] labelsStrArr = labelsStr.split(",");
						List<String> labelNamesL = new ArrayList<String>();
						if(labelsStrArr != null){
							for(String s : labelsStrArr){
								if(!StringUtil.isEmpty(s))
									labelNamesL.add(s);
							}
						}
						Map typeNamesMap=new HashMap();
						typeNamesMap.put("labelNames", labelNamesL);
						 labelIds=labelService.getLabelIdByNames(typeNamesMap);
						if(labelIds!=null&&labelIds.size()!=labelNamesL.size()){
							flag = false;
							model.addAttribute("error", "第" + (i + 1) + "行标签录入有误，请录入正确的标签名称，多个则用英文逗号隔开。");
							break;
						}
					}
					map.put(16, labelIds);
					
					// goodsSn加入snList中
					if(StringUtils.isNotBlank(activityId)){
						activityIdList.add(activityId);
					}

				}

				if (flag) {
					if (!activityIdList.isEmpty()) {
						// 筛选不存在的goodsSn
						Map findMap=new HashMap();
						findMap.put("activityIds", activityIdList);
						List<String> topics = topicService.findExistTopicActivityId(findMap);
						
						if (topics != null && !topics.isEmpty()) {
							activityIdList.removeAll(topics);
						}

						if (!activityIdList.isEmpty()) {
							flag = false;
							model.addAttribute("error", "以下卖场活动ID不存在:" + activityIdList.toString());
						} 
//						else {
//							String existGoodsSn = "";
//							for(String goodsSn : topics) {
//								if(topicService.(goodsSn)) {
//									existGoodsSn = existGoodsSn + " 走秀码：" + goodsSn;
//								}
//							}
//							
//							if(!"".equals(existGoodsSn)) {
//								flag = false;
//								model.addAttribute("error", "以下商品已导入，" + existGoodsSn);
//							}
//						}
					}
				}
			} else {
				flag = false;
				model.addAttribute("error", "模板数据为空，请填写数据后重试！");
			}
			return flag;
		}
	 
	 
	 
}
