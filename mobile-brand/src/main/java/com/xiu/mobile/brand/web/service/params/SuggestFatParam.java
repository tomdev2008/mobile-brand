package com.xiu.mobile.brand.web.service.params;

import java.net.URLDecoder;

import com.xiu.mobile.brand.web.controller.params.SuggestParam;
import com.xiu.solr.lexicon.client.common.MktTypeEnum;

/** 
 * <p>
 * ************************************************************** 
 * @Description: TODO(关键字联想格式化参数) 
 * @AUTHOR zhenqiang.li@xiu.com
 * @DATE 2014-10-15 下午4:58:07 
 * ***************************************************************
 * </p>
 */

public class SuggestFatParam {

	/**
	 * 搜索关键字
	 */
	private String keyword;
	/**
	 * 最大的返回数量
	 */
	private int maxRows;
	/**
	 * suggest类型
	 */
	private String type;
	
	/**
	 * 市场类型
	 */
	private MktTypeEnum mktType;
	
	public SuggestFatParam() {
		super();
	}

	public SuggestFatParam(SuggestParam params) {
		try {
			this.setKeyword(URLDecoder.decode(params.getQ(), "UTF-8"));
		} catch (Exception e) {
			this.setKeyword("");
		}
		
		if(params.getLimit() > 0) {
			this.setMaxRows(params.getLimit());
		}
			
		this.setType(params.getType());
		
		if(null != params.getMkt()){
			try {
				this.setMktType(MktTypeEnum.valueOf(params.getMkt().toUpperCase()));
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the maxRows
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * @param maxRows the maxRows to set
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the mktType
	 */
	public MktTypeEnum getMktType() {
		return mktType;
	}

	/**
	 * @param mktType the mktType to set
	 */
	public void setMktType(MktTypeEnum mktType) {
		this.mktType = mktType;
	}
}
