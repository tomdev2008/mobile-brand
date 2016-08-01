 
package com.xiu.mobile.brand.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.mobile.brand.web.bo.ExclusiveProductBo;
import com.xiu.mobile.brand.web.controller.params.ExclusiveParam;
import com.xiu.mobile.brand.web.service.IExclusiveService;
import com.xiu.mobile.brand.web.util.Constants;
import com.xiu.mobile.brand.web.util.JsonWrapper;
import com.xiu.mobile.brand.web.util.JsonWrapper.CodeEnum;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(专属) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2015-7-22 下午4:55:54 
 * ***************************************************************
 * </p>
 */
@Controller
public class ExclusiveController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExclusiveController.class);
	@Autowired
	private IExclusiveService exclusiveService;
	/**
	 * 用户专属推荐
	 * @param sex 
	 *        性别
	 *        枚举值：{man = 男士}、{woman = 女士}
	 *        
	 * @param size 
	 *        分类及对应尺寸，格式：分类1_尺码1|尺码2;分类2_尺码1|尺码2 ...
	 *        分类枚举值：
	 *        {1001：男上装}、{1002：男下装}、{1003：男鞋}、{1004：皮带}
	 *        {2001：女上装}、{2002：女下装}、{2003：女鞋}
	 *        
	 * @param style
	 *        风格，格式：style1;style2...
	 *        枚举值： 
	 *        {101： 奢华商务}、{102： 品质休闲}、{103： 年轻潮流}
	 *        {201：服饰}、{202：鞋靴}、{203：包袋}、{204：配饰}
	 *        
	 * @param p
	 *        页码
	 * @param pSize
	 *        每页返回商品数
	 * @param v
	 *         API版本号
	 * @param callback
	 * @return
	 */
	@RequestMapping(value = "/exclusive/{sex}", produces = "text/html;charset=UTF-8")
	public @ResponseBody String exclusiveRec(
			@PathVariable String sex, @RequestParam(value="size", required = false) String size, @RequestParam(value="style",required=false) String style, 
			@RequestParam(value = "p", required=false, defaultValue = "1") Integer p, 
			@RequestParam(value = "pSize", required = false, defaultValue = "20") Integer pSize, 
			String v, String callback) {
		
		JsonWrapper wrapper = new JsonWrapper();
		try {
			if (sex == null || "".equals(sex.trim())) {
				wrapper.getHead().setCodeEnum(CodeEnum.LACK_PARAM);
				LOGGER.error("lack param when query exclusive products !");
			}else {
				ExclusiveProductBo exclusiveProductBo = exclusiveService.getExclusiveProducts(handleRequestParam(sex, size, style, p, pSize));
				wrapper.getData().put("goodsItems", exclusiveProductBo.getGoods());
				wrapper.getData().put("page", exclusiveProductBo.getPage());
				wrapper.getData().put("updateTime", exclusiveProductBo.getUpdateTime());
			}
		} catch (IllegalArgumentException e) {
			wrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("query exclusive products passed an illegal argument !",e);
		} catch (SolrServerException e) {
			wrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("query exclusive products occurs an solr query exception !",e);
		}catch (Exception e) {
			wrapper.getHead().setCodeEnum(CodeEnum.ERROR);
			LOGGER.error("query exclusive products occurs an unchecked error !",e);
		}
		return wrapper.render(callback);
	}
	private ExclusiveParam handleRequestParam(String sex, String size, String style, Integer p, Integer pSize){
		Map<String, List<String>> category_sizes = null;
		if (size != null && !"".equals(size.trim())) {
			category_sizes = new HashMap<String, List<String>>();
			String[] subSize = size.split(Constants.SPLIT_STR_SEMICOLON);
			for (int i = 0; i < subSize.length; i++) {
				String[] catSize = subSize[i].split(Constants.SPLIT_STR_UNDER_LINE);
				if (catSize !=null) {
					if (catSize.length>1 && !"0".equals(catSize[1])) {
						category_sizes.put(catSize[0], Arrays.asList(catSize[1].split("\\"+Constants.SPLIT_STR_TWO)));
					}else if(catSize.length ==1 || (catSize.length>1 && "0".equals(catSize[1]))){
						category_sizes.put(catSize[0], null );
					}
				}
			}
		}
		ExclusiveParam param = new ExclusiveParam();
		param.setSex(sex);
		param.setCategorySizes(category_sizes);
		param.setPageIndex(p);
		param.setPageSize(pSize);
		if (style != null && !"".equals(style.trim())) {
			param.setStyles(Arrays.asList(style.split(Constants.SPLIT_STR_SEMICOLON)));
		}
		return param;
	}
}
