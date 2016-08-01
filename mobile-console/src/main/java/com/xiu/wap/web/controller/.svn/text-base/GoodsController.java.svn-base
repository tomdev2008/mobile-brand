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
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.service.IFindGoodsService;
import com.xiu.mobile.core.service.IGoodsService;
import com.xiu.mobile.core.utils.ObjectUtil;

/**
 * 
* @Description: TODO(商品相关) 
* @author haidong.luo@xiu.com
* @date 2015年10月15日 下午3:06:29 
*
 */
@AuthRequired
@Controller
@RequestMapping(value = "/goods")
public class GoodsController {
	//日志
    private static final XLogger logger = XLoggerFactory.getXLogger(GoodsController.class);
    @Autowired
    private IGoodsService goodsService;
    
    @RequestMapping(value = "checkGoodsId", method = RequestMethod.GET, produces = "application/json", params = "format=json")
    public String checkGoodsId(Long goodsId, Model model){
		JsonPackageWrapper json = new JsonPackageWrapper();
		try {
			goodsId=ObjectUtil.getLong(goodsId, null);
			if(goodsId!=null){
					Goods goods = goodsService.getGoodsById(goodsId);
					if (goods != null) {
						json.setScode(JsonPackageWrapper.S_OK);
						String date = DateUtil.formatDate(goods.getCreateTime(), "yyyyMMdd");
						goods.setStrDate(date);
						json.setData(goods);
						json.setSmsg("检查商品ID成功！");
					} else {
						json.setScode("2");
						json.setData("该商品ID不存在！");
					}
			}else{
				json.setScode("2");
				json.setData("商品ID不能为空！");
			}
		} catch (Exception e) {
			json.setScode(JsonPackageWrapper.S_ERR);
			json.setData("系统发生异常！");
			logger.error("check商品SN失败！", e);
		}
		model.addAttribute(Constants.JSON_MODEL__DATA, json);
		return "";
    }
}
