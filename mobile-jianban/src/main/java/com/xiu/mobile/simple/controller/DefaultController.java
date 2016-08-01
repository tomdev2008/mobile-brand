package com.xiu.mobile.simple.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.core.model.DataBrandBo;
import com.xiu.mobile.core.model.FindGoodsVo;
import com.xiu.mobile.core.service.IFindGoodsService;
import com.xiu.mobile.simple.common.constants.XiuConstant;
import com.xiu.mobile.simple.model.DataBrandVo;
import com.xiu.mobile.simple.model.Topic;
import com.xiu.mobile.simple.model.WellChosenBrandVo;
import com.xiu.mobile.simple.service.IActivityNoregularService;
import com.xiu.mobile.simple.service.IGoodsService;
import com.xiu.mobile.simple.service.IProductService;
import com.xiu.mobile.simple.service.ITopicActivityGoodsService;
import com.xiu.mobile.simple.service.IWellChosenBrandService;

/***
 * 
 * @author hejianxiong
 * @DATE 2014-08-26 下午7:39:54 
 */
@Controller
public class DefaultController {
	
	private final Logger logger = Logger.getLogger(DefaultController.class);
	
	@Autowired
	private IActivityNoregularService activityNoregularService;
	@Autowired
	private IWellChosenBrandService wellChosenBrandManager;
	@Autowired
	private IFindGoodsService findGoodsService;
	@Autowired
	private ITopicActivityGoodsService topicActivityGoodsService;
	@Autowired
	private IProductService productService;
	@Autowired
	private IGoodsService goodsManager;
	
	/***
	 * 推荐卖场列表
	 * @return
	 */
	@RequestMapping("/recommend")
	public String recommendList(HttpServletRequest request,HttpServletResponse response,Model model){
		// 分页  默认为1
		int pageNo = NumberUtils.toInt(request.getParameter("pageNo"), 1);
		// 数据类型ID{1：今日卖场；2：昨日卖场；3：昨日以前的所有卖场}  其余为所有时间卖场   不传默认值为4
		int dataType = NumberUtils.toInt(request.getParameter("dataType"),4);
		// 卖场类型ID{1：中性；2：男士；3：女士}  0查询全部(默认值)
		int typeId = NumberUtils.toInt(request.getParameter("typeId"), 0); 
		
		try {
			// 分页查询条件限制
			int lineMin = (pageNo - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageNo * XiuConstant.TOPIC_PAGE_SIZE + 1;
			
			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			valMap.put("dataType", dataType);
			valMap.put("typeId", typeId);

			List<Topic> topicList = activityNoregularService.queryTopicList(valMap);

			HashMap<String, Object> valMap2 = new HashMap<String, Object>();
			valMap2.put("dataType", dataType);
			valMap2.put("typeId", typeId);
			int topicTotalAmount = activityNoregularService.getTopicTotalAmount(valMap2);

			int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
			int totalPage = (topicTotalAmount % pageSize > 0) ? (topicTotalAmount / pageSize + 1) : (topicTotalAmount / pageSize);
			
			model.addAttribute("result", true);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("topicList", topicList);
		} catch (Exception e) {
			model.addAttribute("result", false);
			model.addAttribute("pageNo", 1);
			model.addAttribute("totalPage", 0);
			model.addAttribute("topicList", null);
			logger.error("获取专题数据发生异常：",e);
		}
		return "/recommend/martList";
	}

	/***
	 * 推荐商品列表
	 * @return
	 */
	@RequestMapping("/find")
	public String findList(HttpServletRequest request,HttpServletResponse response,Model model){
		// 分页参数
		int pageNo = NumberUtils.toInt(request.getParameter("pageNo"), 1);
		try {
			int lineMin = (pageNo - 1) * XiuConstant.TOPIC_PAGE_SIZE + 1;
			int lineMax = pageNo * XiuConstant.TOPIC_PAGE_SIZE + 1;
			// 分页数据
			HashMap<String, Object> valMap = new HashMap<String, Object>();
			valMap.put("lineMin", lineMin);
			valMap.put("lineMax", lineMax);
			List<FindGoodsVo> findGoodsList = findGoodsService.getFindGoodsList(valMap);
			String goodsSns="";
			if(null!= findGoodsList && findGoodsList.size()>0){			
				// 商品SN相关集合信息
				List<String> goodsSNList = new ArrayList<String>();
				for (int i = 0; i < findGoodsList.size(); i++) {
					FindGoodsVo findGoodsVo = findGoodsList.get(i);
					// 商品sn码组合
					goodsSns+= findGoodsVo.getGoodsSn()+",";
					goodsSNList.add(findGoodsVo.getGoodsSn());
				}
				
				// ===========================封装产品图片相关信息==============================/
				List<Product> products = topicActivityGoodsService.batchLoadProducts(goodsSns);
				for(FindGoodsVo findGoodsVo: findGoodsList ){
					if(null != products && products.size()>0){
				     	for(Product product:products ){
							if(findGoodsVo.getGoodsSn().equals(product.getXiuSN())){
								if (StringUtils.isNotEmpty(product.getImgUrl())) {
									// 目前图片服务器前缀
									int[] arr = { 1, 2, 3 };
									String goodsId =  findGoodsVo.getGoodsSn();
									int index = Integer.parseInt(goodsId.substring(goodsId.length()-1, goodsId.length()),16);
									String domain =  arr[index%3] + ".xiustatic.com";
									String imgUrl =  product.getImgUrl() + "/g1_162_216.jpg";
									imgUrl = imgUrl.replaceAll("images.xiu.com", domain);
									findGoodsVo.setGoodsImg(imgUrl);
									findGoodsVo.setGoodsId(product.getInnerID());
								}	
								
								// 获取商品价格
								Double price = goodsManager.getZxPrice(product);
								findGoodsVo.setGoodsPrice(price==null?0.00:price);
							}
						}
					}else{
						findGoodsVo.setGoodsImg("");
						findGoodsVo.setGoodsId(Long.parseLong("0"));
						findGoodsVo.setGoodsPrice(0.00);
					}
				}
				
				// =======================封装库存相关产品信息============================//
				// 查询产品sn对应的库存数量
				Map<String, Integer> stockMap = productService.queryInventoryByCodeList(goodsSNList);
				for (FindGoodsVo findGoodsVo : findGoodsList) {
					String goodsSn = findGoodsVo.getGoodsSn();
					if (StringUtils.isNotBlank(goodsSn) && stockMap.containsKey(goodsSn)) {
						int stock = stockMap.get(goodsSn);
						findGoodsVo.setStock(stock);
					}
				}
			
				//总数
				int count = findGoodsService.getFindGoodsListCnt();
				int pageSize = XiuConstant.TOPIC_PAGE_SIZE;
				int totalPage = (count % pageSize > 0) ? (count / pageSize + 1) : (count / pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", totalPage);
				model.addAttribute("findGoodsList", findGoodsList);
			}else{
				model.addAttribute("pageNo", 1);
				model.addAttribute("totalPage", 0);
				model.addAttribute("findGoodsList",new ArrayList<FindGoodsVo>());
			}
		}catch (Exception e) {
			model.addAttribute("pageNo", 1);
			model.addAttribute("totalPage", 0);
			model.addAttribute("findGoodsList",new ArrayList<FindGoodsVo>());
			logger.error("分页查询发现商品列表时发生异常",e);
		}

		return "/recommend/goodsList";
	}
	
	/***
	 * 推荐品牌列表
	 * @return
	 */
	@RequestMapping("/brand-goodlist")
	public String brandList(HttpServletRequest request,HttpServletResponse response,Model model){	 
		try {
			// 推荐品牌列表查询
			List<DataBrandBo> wellChosenBrandBoList = wellChosenBrandManager.getWellChosenBrandList();
			List<Object> wellChosenBrandList = new ArrayList<Object>();
			WellChosenBrandVo wcb = new WellChosenBrandVo();
			if(null!=wellChosenBrandBoList && wellChosenBrandBoList.size()>0){
				wcb = wellChosenBrandManager.getSortList(wellChosenBrandBoList);
				wellChosenBrandList = wellChosenBrandManager.getResultList(wcb);
				model.addAttribute("wellChosenBrandList", wellChosenBrandList);			 
			}else{
				model.addAttribute("wellChosenBrandList",new ArrayList<DataBrandVo>());
			}
		}catch (Exception e) {
			model.addAttribute("wellChosenBrandList",new ArrayList<DataBrandVo>());
			logger.error("分页查询精选品牌列表时发生异常",e);
		}

		return "/recommend/brandList";
	}
	
}
