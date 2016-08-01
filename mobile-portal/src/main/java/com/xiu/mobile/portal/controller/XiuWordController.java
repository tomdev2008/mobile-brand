package com.xiu.mobile.portal.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiu.commerce.hessian.model.Product;
import com.xiu.mobile.portal.common.constants.ErrorCode;
import com.xiu.mobile.portal.common.constants.GoodsConstant;
import com.xiu.mobile.portal.common.constants.XiuConstant;
import com.xiu.mobile.portal.common.json.JsonUtils;
import com.xiu.mobile.portal.common.util.ImageServiceConvertor;
import com.xiu.mobile.portal.common.util.Tools;
import com.xiu.mobile.portal.model.Topic;
import com.xiu.mobile.portal.model.XiuWordDataVo;
import com.xiu.mobile.portal.service.IActivityNoregularService;
import com.xiu.mobile.portal.service.IGoodsService;

/**
 * 
* @Description: TODO(秀口令) 
* @author haidong.luo@xiu.com
* @date 2016年2月23日 上午11:53:21 
*
 */
@Controller
@RequestMapping("/xiuWord")
public class XiuWordController extends BaseController {
	private Logger logger = Logger.getLogger(XiuWordController.class);

	@Autowired
	private IGoodsService goodsService;
	@Autowired
	private IActivityNoregularService activityNoregularService;

	/**
	 * 查询秀口令数据
	 * @param request
	 * @param jsoncallback
	 * @param xiuWord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getXiuWordDataList", produces = "text/html;charset=UTF-8")
	public String getAdvList(HttpServletRequest request,
			String jsoncallback, String xiuWord ) {
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		XiuWordDataVo xiuWordData=new XiuWordDataVo();
		try {
			if (null != xiuWord && !"".equals(xiuWord)) {
				
				if(xiuWord.indexOf("product")>0){//商品情况
					String goodsId=xiuWord.substring(xiuWord.indexOf("product/")+8,xiuWord.lastIndexOf("."));
					String goodsSn = goodsService.getGoodsSnByGoodsId(goodsId);
					if(goodsSn!=null&&!goodsSn.equals("")){
						Product p=goodsService.getGoodsInfoSimpleByGoodSn(goodsSn);
						xiuWordData.setBrandName(p.getBrandName());
						String imgUrl = p.getImgUrl();
						// 为了适应镜像环境的端口号
						if (Tools.isEnableImageReplace()) { 
							if (null != imgUrl) {
								imgUrl = imgUrl.replace("pic.xiu.com", "pic.xiu.com:6080");
								imgUrl = imgUrl.replace("images.xiu.com", "images.xiu.com:6080");
							}
						} 
						imgUrl = ImageServiceConvertor.getGoodsDetail(imgUrl);
						imgUrl=imgUrl+"/g1_"+GoodsConstant.SLIDE_IMG_MAX;
						xiuWordData.setImgUrl(imgUrl);
						Double goodsPrice = p.getPrdOfferPrice() * 100;
						xiuWordData.setPrice(goodsPrice);
						xiuWordData.setTitle(p.getPrdName());
						xiuWordData.setUrl("xiuApp://xiu.app.goodsdetail/openwith?id="+goodsId);
						xiuWordData.setType(1);
					}else{
						resultMap.put("result", false);
						resultMap.put("errorCode", ErrorCode.GoodsNotFound.getErrorCode());
						resultMap.put("errorMsg", ErrorCode.GoodsNotFound.getErrorMsg());
						return JsonUtils.bean2jsonP(jsoncallback, resultMap);
					}
				}else if(xiuWord.indexOf("/cx/")>0){//卖场情况
					String topicId=xiuWord.substring(xiuWord.indexOf("/cx/")+4,xiuWord.lastIndexOf("."));
					Topic topic=activityNoregularService.getTopicByActId(topicId);
					String picPath=topic.getMobile_pic();
					xiuWordData.setImgUrl(picPath);
					xiuWordData.setTitle(topic.getName());
					xiuWordData.setUrl("xiuApp://xiu.app.topicgoodslist/openwith?id="+topicId);
					xiuWordData.setType(2);
				}else{
					resultMap.put("result", false);
					resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
					resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
					return JsonUtils.bean2jsonP(jsoncallback, resultMap);
				}
				
				resultMap.put("xiuWordData", xiuWordData);
				resultMap.put("result", true);
				resultMap.put("errorCode",ErrorCode.Success.getErrorCode());
				resultMap.put("errorMsg",ErrorCode.Success.getErrorMsg());
			} 
			//参数不对
			else {
				resultMap.put("result", false);
				resultMap.put("errorCode", ErrorCode.MissingParams.getErrorCode());
				resultMap.put("errorMsg", ErrorCode.MissingParams.getErrorMsg());
			}

		} catch (Exception e) {
			resultMap.put("result", false);
			resultMap.put("errorCode", ErrorCode.SystemError.getErrorCode());
			resultMap.put("errorMsg", ErrorCode.SystemError.getErrorMsg());
			logger.error("查询秀口令发生异常：" + e.getMessage(),e);
		}
		return JsonUtils.bean2jsonP(jsoncallback, resultMap);
	}
}
