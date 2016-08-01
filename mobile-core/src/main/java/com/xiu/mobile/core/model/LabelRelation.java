package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
* @Description: TODO(标签关联标签数据) 
* @author haidong.luo@xiu.com
* @date 2016年5月18日 下午6:20:09 
*
 */
public class LabelRelation implements Serializable{
	

	private static final long serialVersionUID = 5147309131340945675L;

	private Long id; //主键
	
	private Long mainLabelId;//主标签ID
	
	private Long relationLabelId;//关联标签的Id
	
	private Integer orderSeq;//排序
	
	private Integer category;//分类
	
	private Integer relationObjectNum;//关联相同业务数据的个数
	
	private String relationLabelName;//关联标签名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainLabelId() {
		return mainLabelId;
	}

	public void setMainLabelId(Long mainLabelId) {
		this.mainLabelId = mainLabelId;
	}

	public Long getRelationLabelId() {
		return relationLabelId;
	}

	public void setRelationLabelId(Long relationLabelId) {
		this.relationLabelId = relationLabelId;
	}

	public Integer getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getRelationObjectNum() {
		return relationObjectNum;
	}

	public void setRelationObjectNum(Integer relationObjectNum) {
		this.relationObjectNum = relationObjectNum;
	}

	public String getRelationLabelName() {
		return relationLabelName;
	}

	public void setRelationLabelName(String relationLabelName) {
		this.relationLabelName = relationLabelName;
	}
	
	
}
