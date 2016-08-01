package com.xiu.mobile.core.constants;
/**
 * 链接类型
 * @author Administrator
 *
 */
public enum LinkType {
	
    url("url","URL"),sales("sales","卖场"),store("store","卖场集"),
    goodCommodity("goodCommodity","商品分类"),brand("brand","品牌"),
    topic("topic","话题"),show("show","秀"),good("good","商品"),subject("subject","专题");
    
    private String code;
    
    private String desc;
    
	private LinkType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static LinkType getLinkTypeByDesc(String desc){
		
		for(LinkType linkType : LinkType.values()){
			
			if(linkType.getDesc().equals(desc)){
				return linkType;
			}
			
		}
		return null;
	}
    
}
