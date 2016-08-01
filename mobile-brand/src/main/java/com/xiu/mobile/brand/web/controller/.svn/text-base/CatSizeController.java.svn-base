package com.xiu.mobile.brand.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.model.CatAndSizeModel;
import com.xiu.mobile.brand.web.service.ICatSizeService;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;
/**
 * 获取分类与尺码
 * @author rian.luo@xiu.com
 *
 * 2015-9-14
 */
@Controller
@RequestMapping("/")
public class CatSizeController extends BaseController{
	private static final Logger LOG = LoggerFactory.getLogger(CatSizeController.class);
	@Autowired
	private ICatSizeService catSizeService;
	
	@ResponseBody
	@RequestMapping(value = "/catelogsize", produces="text/html;charset=utf-8")
	public String listFirstLevelAndSize(@RequestParam(required = true) String gids,@RequestParam(required = false) String jsoncallback){
		JsonWrapper wrapper = new JsonWrapper();
		List<String> itemIdList = new ArrayList<String>();
		if(StringUtils.isNotBlank(gids)){
			String[] itemIdArray=null;
			if(gids.indexOf(",")!=-1){
				itemIdArray = gids.split(",");
				itemIdList = Arrays.asList(itemIdArray);
			}else{
				itemIdList.add(gids.trim());
			}
		}
		LOG.info("调用该方法的参数："+itemIdList);
		List<CatAndSizeModel> catSizeModels = catSizeService.listCatSize(itemIdList);
		
		if (catSizeModels ==null || catSizeModels.isEmpty()) {
			wrapper.getHead().setCodeEnum(CodeEnum.CATA_NOT_FOUND);
		}else {
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			if (catSizeModels != null) {
				for (int i = 0; i < catSizeModels.size(); i++) {
					HashMap<String, Object> catMap = new LinkedHashMap<String, Object>();
					catMap.put("goodsId", catSizeModels.get(i).getGoodsId());
					catMap.put("goodsSn", catSizeModels.get(i).getGoodsSn());
					if(StringUtils.isNotBlank(catSizeModels.get(i).getCategoryName())){
						catMap.put("categoryId", catSizeModels.get(i).getCategoryId());
						catMap.put("categoryName", catSizeModels.get(i).getCategoryName());
					}
					catMap.put("sizeList", catSizeModels.get(i).getSizeList()==null ? new HashSet<String>():catSizeModels.get(i).getSizeList());
					//catMap.put("sizeList", catSizeModels.get(i).getSizeList());
					resultList.add(catMap);
				}
			}
			wrapper.getData().put("goods", resultList);
		}
		return wrapper.render(jsoncallback);
	}
}
