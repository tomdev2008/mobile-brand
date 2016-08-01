<%@page import="com.xiu.mobile.wechat.core.model.UserInfo"%>
<%@page import="com.xiu.mobile.wechat.core.utils.WeChatApiUtil"%>
<%@page import="com.xiu.mobile.wechat.web.utils.WebUtils"%>
<%@page import="com.xiu.mobile.wechat.core.constants.Constants"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>走秀网</title>
<script type="text/javascript">
function getCookie(name){
	var strCookie = document.cookie;
	var arrCookie=strCookie.split("; ");
	var value;
	for(var i=0;i<arrCookie.length;i++){
		var arr=arrCookie[i].split("=");
		if(name==arr[0]){
			value=arr[1];
			break;
		}
	}
	value = decodeURIComponent(value);
	return value;
}
var nickName =  getCookie('xiu.login.nickName');
var tokenId =  getCookie('xiu.login.tokenId');
var userId =  getCookie('xiu.login.userId');
var petName =  getCookie('xiu.login.petName');
var headImgUrl =  getCookie('xiu.login.headImgUrl');
var unionId =  getCookie('xiu.login.unionId');
var htmlString="nickName:"+nickName+"<br>"+
		"tokenId:"+tokenId+"<br>"+
		"userId:"+userId+"<br>"+
		"petName:"+petName+"<br>"+
		"headImgUrl:"+headImgUrl+"<br>"+
		"unionId:"+unionId+"<br>";
document.getElementById("insert").innerHTML = htmlString;  
</script>
</head>
<body>
<div id="insert"></div>
</body>
</html>