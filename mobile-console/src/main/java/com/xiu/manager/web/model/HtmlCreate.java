package com.xiu.manager.web.model;

import java.io.Serializable;
import java.util.List;

public class HtmlCreate implements Serializable{
	private static final long serialVersionUID = 1L;

	private String photo;//图片
	
	private int id;
	
	private String type;//类型
	
	private String url;//链接或ID
	
	private String sign;//形状  横或竖
	
	private String figure;//区分图片位置
	
	private String number;//号码，1竖的一张图片，2竖的2张图片
	
	private List<HtmlCreate> htmlList;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<HtmlCreate> getHtmlList() {
		return htmlList;
	}

	public void setHtmlList(List<HtmlCreate> htmlList) {
		this.htmlList = htmlList;
	}
	

}
