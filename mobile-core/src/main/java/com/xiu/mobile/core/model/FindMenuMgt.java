package com.xiu.mobile.core.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 发现频道栏目
 * @author coco.long
 *
 */
public class FindMenuMgt extends BaseSupportVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//栏目ID
	private Long id;
	
	//栏目ID
	private Long findMenuId;
	
	//栏目名称
	private String name;
	
	//栏目字体颜色
	private String fontColor;
	
	//栏目图标
	private String iconUrl;
	
	//广告语
	private String slogan;
	
	//所属区块
	private Long block;
	
	//排序
	private Long orderSeq;
	
	//栏目类型：1.Native 2.H5
	private Long type;
	
	//跳转URL地址
	private String url;

	//状态：1.启用 0.停用
	private Long status;
	
	//创建时间
	private Date createDate;
	
	//Wap url
	private String wapUrl;
	
	//版本号
	private Long version;
	
	//支持版本  0全部 1指定版本
	private Integer supportVersion;
	
	private Integer animation;
	
	private String iconClickUrl;//图标点击显示图片Url
	
	private String bgColor;//背景颜色
	
	private Integer isNeedLogin;//是否需要登录 0不需要 1需要
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public Long getBlock() {
		return block;
	}

	public void setBlock(Long block) {
		this.block = block;
	}

	public Long getOrderSeq() {
		return orderSeq;
	}

	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getWapUrl() {
		return wapUrl;
	}

	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	public Integer getAnimation() {
		return animation;
	}

	public void setAnimation(Integer animation) {
		this.animation = animation;
	}

	public Long getFindMenuId() {
		return findMenuId;
	}

	public void setFindMenuId(Long findMenuId) {
		this.findMenuId = findMenuId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getIconClickUrl() {
		return iconClickUrl;
	}

	public void setIconClickUrl(String iconClickUrl) {
		this.iconClickUrl = iconClickUrl;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public Integer getIsNeedLogin() {
		return isNeedLogin;
	}

	public void setIsNeedLogin(Integer isNeedLogin) {
		this.isNeedLogin = isNeedLogin;
	}

	public Integer getSupportVersion() {
		return supportVersion;
	}

	public void setSupportVersion(Integer supportVersion) {
		this.supportVersion = supportVersion;
	}
	
	
}
