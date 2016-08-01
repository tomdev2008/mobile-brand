package com.xiu.wap.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiu.common.web.annotation.AuthRequired;
import com.xiu.common.web.contants.Constants;
import com.xiu.common.web.utils.DateUtil;
import com.xiu.common.web.utils.JsonPackageWrapper;
import com.xiu.common.web.utils.StringUtil;
import com.xiu.manager.web.model.User;
import com.xiu.manager.web.utils.AdminSessionUtil;
import com.xiu.mobile.core.model.FindGoods;
import com.xiu.mobile.core.model.Goods;
import com.xiu.mobile.core.model.Label;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.model.SubjectLabel;
import com.xiu.mobile.core.service.IFindGoodsService;
import com.xiu.mobile.core.service.IGoodsService;
import com.xiu.mobile.core.service.ISubjectManagerService;

/**
 * 
 *************************************************************** 
 * <p>
 * @DESCRIPTION : 单品发现
 * @AUTHOR : chenghong.ding@xiu.com 
 * @DATE :2014年6月9日 下午2:17:43
 * </p>
 ****************************************************************
 */
@AuthRequired
@Controller
@RequestMapping(value = "/findgoods")
public class FindGoodsController {
	//日志
    private static final XLogger logger = XLoggerFactory.getXLogger(FindGoodsController.class);
    @Autowired
    private IFindGoodsService findGoodsService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISubjectManagerService subjectManagerService;
    
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(String goodsSn, String title, String sDate, String eDate,Long labelId,
			@RequestParam(value = "status", required = false, defaultValue = "-1") int status,
			Page<?> page, Model model) {
		page.setPageNo(page.getPageNo() == 0 ? 1 : page.getPageNo());
		FindGoods findGoods = new FindGoods();
		
		if (!StringUtil.isEmpty(goodsSn)) {
			findGoods.setGoodsSn(goodsSn);
			model.addAttribute("goodsSn", goodsSn);
		}
		
		if (!StringUtil.isEmpty(title)) {
			findGoods.setTitle(title);
			model.addAttribute("title", title);
		}
		
		if (!StringUtil.isEmpty(sDate)) {
			findGoods.setStartDate(DateUtil.parseDate(sDate + " 00:00", "yyyy-MM-dd HH:mm"));
			model.addAttribute("sDate", sDate);
		}
		
		if (!StringUtil.isEmpty(eDate)) {
			findGoods.setEndDate(DateUtil.parseDate(eDate + " 23:59", "yyyy-MM-dd HH:mm"));
			model.addAttribute("eDate", eDate);
		}
		
		if (status != -1) {
			findGoods.setStatus(status);
		}
		
		model.addAttribute("status", status);
		if(labelId!=null){
			findGoods.setLabelId(String.valueOf(labelId));
			model.addAttribute("labelId", labelId);
		}

		try {
			List<FindGoods> findGoodsList = findGoodsService.searchFindGoodsList(findGoods, page);
			
			if (null != findGoodsList) {
				// 设置状态
				Date now = Calendar.getInstance().getTime();
				
				for (FindGoods goods : findGoodsList) {
					if (goods.getStartDate().after(now)) {
						goods.setStatus(0); // 未开始
					} else if (goods.getEndDate().after(now)) {
						goods.setStatus(1); // 进行中
					} else {
						goods.setStatus(2);
					}
				}
			}
			model.addAttribute("findGoodsList", findGoodsList);
		} catch (Exception e) {
			logger.error("获取单品发现列表异常", e);
		}
		int category=2;//单品
    	List<SubjectLabel> listLabel=subjectManagerService.getSubjectLabel(category);
    	model.addAttribute("listLabel", listLabel);
		return "pages/findgoods/list";
	}
    
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String create(FindGoods findGoods,Page<?> page,Model model){
    	//查询标签
    	int category=2;//单品
    	List<SubjectLabel> listLabel=subjectManagerService.getSubjectLabel(category);
    	model.addAttribute("listLabel", listLabel);
    	return "pages/findgoods/create";
    }
    
	@RequestMapping(value = "save", method = RequestMethod.POST, produces = "application/json", params = "format=json")
	public String save(FindGoods findGoods, HttpServletRequest request, Model model) {
		JsonPackageWrapper json = new JsonPackageWrapper();
		
		try {
			if (!StringUtil.isEmpty(findGoods.getsDate())) {
				findGoods.setStartDate(DateUtil.parseDate(findGoods.getsDate(), "yyyy-MM-dd HH:mm"));
			}
			
			if (!StringUtil.isEmpty(findGoods.geteDate())) {
				findGoods.setEndDate(DateUtil.parseDate(findGoods.geteDate(), "yyyy-MM-dd HH:mm"));
			}
			
			if (null == findGoods.getOrderSequence()) {
				findGoods.setOrderSequence(0l);
			}
			
			User user = AdminSessionUtil.getUser(request);
			findGoods.setCreateBy(user.getId());
			findGoods.setCreateDate(Calendar.getInstance().getTime());

			// 查询商品信息
			Goods goods = goodsService.getGoodsBySn(findGoods.getGoodsSn());
			String date = DateUtil.formatDate(goods.getCreateTime(), "yyyyMMdd");
			String imageUrl = "upload/goods" + date + "/" + goods.getGoodsSn()
					+ "/" + goods.getMainSku() + "/g1_162_216.jpg";

			findGoods.setGoodsImage(imageUrl);
			findGoods.setGoodsName(goods.getGoodsName());

			int result = findGoodsService.addFindGoods(findGoods);// 保存数据
			
			if (result > 0) {
				json.setScode(JsonPackageWrapper.S_OK);
				json.setData("单品商品保存成功！");
			} else {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("单品商品保存失败！");
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			logger.error("添加单品商品失败！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
	}
    
    @RequestMapping(value = "checkGoodsSn", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String checkGoodsSn(String goodsSn, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(!StringUtil.isEmpty(goodsSn)){
				if(findGoodsService.isFindGoodsExist(goodsSn)) {
					json.setScode(JsonPackageWrapper.S_ERR);
					json.setData("商品已存在，走秀码：" + goodsSn);
				} else {
					Goods goods = goodsService.getGoodsBySn(goodsSn);
					if (goods != null) {
						json.setScode(JsonPackageWrapper.S_OK);
						String date = DateUtil.formatDate(goods.getCreateTime(), "yyyyMMdd");
						goods.setStrDate(date);
						json.setData(goods);
						json.setSmsg("检查走秀码成功！");
					} else {
						json.setScode(JsonPackageWrapper.S_ERR);
						json.setData("该走秀码不存在！");
					}
				}
			}else{
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("走秀码不能为空！");
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			logger.error("check商品SN失败！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
    }
    
    @RequestMapping(value = "delete", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String delete(Long id,Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(null != id){
				int result = findGoodsService.deleteFindGoods(id);
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
			logger.error("删除发现商品失败！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
    }
    
    @RequestMapping(value = "deleteAll", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String deleteAll(String ids,Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(null != ids){
				logger.debug("即将被删的商品ids是:{}",new Object[]{ids});
				String[] strIds = ids.split(",");
				List<Long> idsL = new ArrayList<Long>();
				if(strIds != null){
					for(String s : strIds){
						if(!StringUtil.isEmpty(s))
							idsL.add(Long.parseLong(s));
					}
				}
				int result = findGoodsService.batchDelete(idsL);
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
			logger.error("删除发现商品失败！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
    }
    
    @RequestMapping(value = "toedit", method = RequestMethod.GET)
    public String toEdit(Long id,Model model){
    	FindGoods findGoods = findGoodsService.getFindGoods(id);
    	//查询所有标签
    	int category=2;//单品
    	List<SubjectLabel> listLabel=subjectManagerService.getSubjectLabel(category);
    	model.addAttribute("goods", findGoods);
    	model.addAttribute("listLabel", listLabel);
    	model.addAttribute("subjectLabel", findGoods.getLabelCentre());
    	return "pages/findgoods/edit";
    }
    
    @RequestMapping(value = "edit", method = RequestMethod.POST, produces = "application/json", params = "format=json")
    public String edit(FindGoods findGoods,HttpServletRequest request,Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			if(!StringUtil.isEmpty(findGoods.getsDate())){
				findGoods.setStartDate(DateUtil.parseDate(findGoods.getsDate(), "yyyy-MM-dd HH:mm"));
			}
			if(!StringUtil.isEmpty(findGoods.geteDate())){
				findGoods.setEndDate(DateUtil.parseDate(findGoods.geteDate(), "yyyy-MM-dd HH:mm"));
			}
			int result = findGoodsService.updateFindGoods(findGoods);// 保存数据
			if (result > 0) {
				json.setScode(JsonPackageWrapper.S_OK);
				json.setData("单品商品保存成功！");
			} else {
				json.setScode(JsonPackageWrapper.S_ERR);
				json.setData("单品商品保存失败！");
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			logger.error("添加单品商品失败！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
    }
}
