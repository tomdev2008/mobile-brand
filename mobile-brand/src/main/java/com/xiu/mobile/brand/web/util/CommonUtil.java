package com.xiu.mobile.brand.web.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.xiu.mobile.brand.web.bo.FacetFilterBo;
import com.xiu.mobile.brand.web.bo.FacetFilterBo.FacetTypeEnum;
import com.xiu.mobile.brand.web.bo.GoodsItemBo;
import com.xiu.mobile.brand.web.constants.APIVersionConstants;
import com.xiu.mobile.brand.web.constants.PriceTypeEnum;
import com.xiu.mobile.brand.web.controller.params.SearchParam;
import com.xiu.mobile.core.constants.FacetPriceRangeQueryEnum;
import com.xiu.mobile.core.constants.RequestInletEnum;
import com.xiu.mobile.core.constants.SearchSortOrderEnum;
import com.xiu.mobile.core.model.GoodsSolrModel;
import com.xiu.mobile.core.model.Page;
import com.xiu.mobile.core.param.SearchFatParam;

/**
 * 
 * <p>
 * ************************************************************** 
 * @Description: 公共方法定义 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-6-12 上午10:44:26 
 * ***************************************************************
 * </p>
 */
public class CommonUtil {
	
	/**
	 * 搜索最多字符数，1个汉字=2个字符<br>
	 * 禁止用户恶性的无限制输入参数
	 */
	public static final int MAX_SEARCH_LENGTH = 64;
	
	/**
	 * 分组过滤的最多查询个数<br>
	 * 禁止用户恶性的无限制输入filter
	 */
	private static final int MAX_FILTER_SIZE = 10;

	/**
	 * 判断是否是立即购买商品
	 * 
	 * @param onsale
	 * @param offshow
	 * @return
	 */
	public static boolean isBuyNow(int onsale, int offshow) {
		return 1 == onsale;
	}

	/**
	 * 判断是否是售罄商品
	 * 
	 * @param onsale
	 * @param offshow
	 * @return
	 */
	public static boolean isSoldOut(int onsale, int offshow) {
		return 0 == onsale && 2 == offshow;
	}

	/**
	 * 判断是否是到货通知
	 * 
	 * @param onsale
	 * @param offshow
	 * @return
	 */
	public static boolean isArrivalNotice(int onsale, int offshow) {
		return 0 == onsale && 2 != offshow;
	}

	/**
	 * 生成图片的请求路径
	 * 
	 * @param imgUrl
	 * @param width
	 * @param height
	 * @param version
	 * @return
	 */
	public static String createImgUrl(String imgUrl, int width, int height,
			String version) {
		StringBuffer sb = new StringBuffer(8);
		Random r = new Random();
		int i = r.nextInt(6);
		
		sb.append(IMAGE_ZOSHOW_URL.get(i) + "/");
		sb.append("upload").append(imgUrl)
				.append("/g1_" + width + "_" + height + ".jpg");
		if (StringUtils.isNotBlank(version)) {
			// sb.append("?").append("version").append("=").append(version);
			sb.append("?").append(version);
		}
		return sb.toString();
	}
	/**
	 * 将XiuSolrModel转化为XiuItemBo
	 * 
	 * @param highLigheKeyword
	 *            高亮关键字
	 * @param items
	 * @return
	 */
	public static List<GoodsItemBo> transformXiuItemBo(String highLigheKeyword,
			List<GoodsSolrModel> items) {
		List<GoodsItemBo> ret = new ArrayList<GoodsItemBo>();
		if (null == items) {
			return null;
		}
			
		GoodsItemBo itemBo = null;
		String name = "";

		for (GoodsSolrModel item : items) {
			itemBo = new GoodsItemBo();
			name = "";
			if (StringUtils.isNotEmpty(item.getItemNamePre())) {
				name += item.getItemNamePre();
			}
			if (StringUtils.isNotEmpty(item.getItemName())) {
				name += item.getItemName();
			}
			if (StringUtils.isNotEmpty(item.getItemNamePost())) {
				name += item.getItemNamePost();
			}
			itemBo.setId(item.getItemId());
			itemBo.setName(name);

			itemBo.setSoldOut(CommonUtil.isSoldOut(item.getOnsale(),
					item.getOffshow()));
			
			itemBo.setImgUrl(CommonUtil.createImgUrl(
					item.getImgUrl(), 315, 420, item.getImgVersion()));
			itemBo.setXiuPrice(item.getPriceXiu() != null ? item.getPriceXiu() : 0);
			itemBo.setMktPrice(item.getPriceMkt());
			
			if(item.getPriceFinal() != null && item.getPriceFinal() >= 0) {
				itemBo.setShowPrice(item.getPriceFinal());
				itemBo.setpType(PriceTypeEnum.ACTIVITY.getType());
			} else if(item.getPriceXiu() != null && item.getPriceXiu() > 0){
				itemBo.setShowPrice(item.getPriceXiu());
				itemBo.setpType(PriceTypeEnum.XIU.getType());
			} else {
				itemBo.setShowPrice(item.getPriceMkt());
				itemBo.setpType(PriceTypeEnum.MARKET.getType());
			}
			
			// 限制精度
			BigDecimal bd = new BigDecimal(item.getDiscount())
			              .setScale(1, BigDecimal.ROUND_HALF_UP)
			              .stripTrailingZeros();
			itemBo.setDisCount(bd.toPlainString());
			//添加中英文品牌
			if(StringUtils.isNotBlank(item.getBrandName())){
				itemBo.setBrandCNName(item.getBrandName());
			}else{
				itemBo.setBrandCNName("");
			}
			if(StringUtils.isNotBlank(item.getBrandNameEn())){
				itemBo.setBrandEnName(item.getBrandNameEn());
			}else{
				itemBo.setBrandEnName("");
			}
			ret.add(itemBo);
		}
		return ret;
	}
	
	/**
     * zoshow域名映射
     */
    public static final Map<Integer, String> IMAGE_ZOSHOW_URL = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 3725395382706332404L;
		{
    		put(0,"http://image.zoshow.com");
    		put(1,"http://image1.zoshow.com");
    		put(2,"http://image2.zoshow.com");
    		put(3,"http://image3.zoshow.com");
    		put(4,"http://image4.zoshow.com");
    		put(5,"http://image5.zoshow.com");
    	}
    };
    public static final String[] CAT_IMG_PRE = {ConfigUtil.getValue("catalog.img.url.prefix")}; 
	/**
	 * 将XiuSolrModel转化为XiuItemBo
	 * 
	 * @param items
	 * @return
	 */
	public static List<GoodsItemBo> transformXiuItemBo(List<GoodsSolrModel> items) {
		return transformXiuItemBo(null, items);
	}

	/**
	 * 创建高亮正则表达式
	 * 
	 * @param kw
	 * @return
	 */
	public static String createHighlightRegex(String kw) {
		char[] incharArr = kw.toLowerCase().toCharArray();
		StringBuffer tokSb = new StringBuffer(incharArr.length);
		int charType = 0;// 0-其他，1-字母，2-数字
		char c;
		for (int i = 0, len = incharArr.length; i < len; i++) {
			c = incharArr[i];
			if (c >= 'a' && c <= 'z') {
				if (charType != 1) {
					tokSb.append('|');
				}
				tokSb.append('[').append(c).append(Character.toUpperCase(c))
						.append(']');
				charType = 1;
			} else if (c >= '0' && c <= '9') {
				if (charType != 2) {
					tokSb.append('|');
				}
				tokSb.append(c + "");
				charType = 2;
			} else if (Character.isWhitespace(c)) {
				charType = 0;
			} else {
				tokSb.append('|').append(
						XiuSearchStringUtils.escapeRegexMetacharactor(c + ""));
				charType = 0;
			}

		}
		return tokSb.substring(1, tokSb.length());
	}

	/**
	 * 判断数据的位数是否小于给定的位数
	 * @param src 数据
	 * @param digit 位数
	 * @return
	 */
	public static boolean checkNumGtDigit(Integer src, int digit) {
		if (src == null) {
			return true;
		}
		return src.intValue() < Math.pow(10, digit);
	}
	/**
	 * 组装类目图片
	 */
	public static String wrapCatalogImg(String subUrl){
		if (subUrl == null) {
			return "";
		}
		if (subUrl.startsWith("/")) {
			subUrl = subUrl.substring(1);
		}
		return CAT_IMG_PRE[new Random().nextInt(CAT_IMG_PRE.length)]+subUrl;
	}
	
	/**
	 * 封装参数
	 * @param params
	 * @param requestPage
	 * @return
	 */
	public static SearchFatParam change(SearchParam params, RequestInletEnum requestPage) {
		SearchFatParam fatParam = new SearchFatParam();
		fatParam.setRequestInlet(requestPage);
		
		// 页码与页数
		Page page = new Page();
		if(null != params.getP() && params.getP() > 0) {
			page.setPageNo(params.getP());
		}
		
		if(null != params.getpSize() && params.getpSize() >= 0) {
			page.setPageSize(params.getpSize());
		} else {
			page.setPageSize(20);
		}
		fatParam.setPage(page);
		
		// 验证关键字
		if(StringUtils.isNotBlank(params.getKw())){
			fatParam.setKeyWord(XiuSearchStringUtils.getValidLengthTerm(params.getKw(), MAX_SEARCH_LENGTH));
		}

		// 验证运营分类
		if(null != params.getCatId() && params.getCatId().intValue() > 0){
			fatParam.setCatalogId(params.getCatId());
		}
		
		// 验证品牌ID
		if(StringUtils.isNotBlank(params.getbId())) {
			String[] brandIds = StringUtils.split(params.getbId(), "|");
			fatParam.setBrandIds(brandIds);
		}
		
		// 验证发货地ID
		// 将发货地封装
		if(StringUtils.isNotBlank(params.getdId())) {
			String[] dIds = StringUtils.split(params.getdId(), "|");
			List<String> dIdList = new ArrayList<String>();
			for(String str : dIds) {
				String[] ds = StringUtils.split(str, "_");
				for(String d : ds)
					dIdList.add(d);
			}
			fatParam.setDeliverIds(dIdList.toArray(new String[dIdList.size()]));
		}
		
		// 验证排序
		SearchSortOrderEnum sort = null;
		if(null != params.getSort()){
			sort = SearchSortOrderEnum.valueOfSortOrder(params.getSort());
		}
		
        if(null == sort 
                || SearchSortOrderEnum.VOLUME_ASC.equals(sort) 
                || SearchSortOrderEnum.VOLUME_DESC.equals(sort)
                || SearchSortOrderEnum.SCORE_AMOUNT_DESC.equals(sort) ){
            sort = SearchSortOrderEnum.AMOUNT_DESC;
        }
        fatParam.setSort(sort);
		
		// 验证翻页
		if(null != params.getP() && params.getP() > 0){
			fatParam.getPage().setPageNo(params.getP());
		}
		// 验证价格区间
		if(null != params.getsPrice() || null != params.getePrice()){		
			try{
        		if(params.getsPrice() < 0){
        			params.getsPrice();
        		}
        		fatParam.setStartPrice(params.getsPrice());
        	}catch(Exception e){
        	}
        	try{
        		if(params.getePrice()<0){
        			params.getePrice();
        		}
        		fatParam.setEndPrice(Double.valueOf(params.getePrice()));
        	}catch(Exception e){
        	}
		} else if(null != params.getfPrice()){
			// 如果没有用户自定义价格，则进行价格过滤
			// 验证价格过滤
			fatParam.setPriceRangeEnum(FacetPriceRangeQueryEnum.valueOf(params.getfPrice().intValue()));
		}
		
		if(StringUtils.isNotBlank(params.getFilter())){
			// TODO 此处限制用户恶性输入过滤条件个数
			String[] filters = StringUtils.split(params.getFilter(), ";", MAX_FILTER_SIZE * 2);
			
			//属性查询新的业务逻辑，支持多选
			List<List<String>> attrValIdList = new ArrayList<List<String>>(filters.length);
            for(String vals : filters) {
            	String[] items = StringUtils.split(vals, "|");
            	List<String> list = new ArrayList<String>(vals.length());
            	
            	for(String item : items) {
            		String fStrTrim = item.trim();
            		
            		if(XiuSearchStringUtils.isIntegerNumber(fStrTrim) 
                            && !list.contains(fStrTrim)) {
            			list.add(fStrTrim);
                    }
            	}
            	attrValIdList.add(list);
            }
            
            if(attrValIdList.size() > 0) {
            	fatParam.setAttrValIdList(attrValIdList);
            }           
		}
		
		return fatParam;
	}
	
	/**
	 * 过滤“发货地”筛选项
	 * @param apiVersion
	 *        API 版本号
	 * @param facetFilters
	 */
	public static void filterDeliverInfo(String apiVersion, final List<FacetFilterBo> facetFilters) {
		if(!APIVersionConstants.API_VERSION_2_0.equals(apiVersion)
				&& facetFilters != null) {
			for(FacetFilterBo facet : facetFilters) {
				if(FacetTypeEnum.DELIVER.equals(facet.getFacetType())) {
					facetFilters.remove(facet);
					break;
				}
			}
		}
	}
}
