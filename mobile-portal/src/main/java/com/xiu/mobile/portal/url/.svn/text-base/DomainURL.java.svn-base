package com.xiu.mobile.portal.url;

import java.io.Serializable;

import com.xiu.mobile.portal.common.util.ConfInTomcat;

/***
 * 系统各域名地址
 * 
 * @author hejianxiong
 * 
 */
@SuppressWarnings("serial")
public class DomainURL implements Serializable {

	// 上传图片URL地址
	private String uploadURL = ConfInTomcat.getValue("remote.url.upload");
	private String showImgURL = ConfInTomcat.getValue("remote.url.imgurl");
	private String idPicUploadPath = ConfInTomcat.getValue("address.receiver.idpic.upload");
	
	public String getUploadURL() {
		return uploadURL;
	}
	public String getShowImgURL() {
		return showImgURL;
	}
	public String getIdPicUploadPath() {
		return idPicUploadPath;
	}
}
