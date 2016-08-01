<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0">

<!-- 开启对web app程序的支持 -->
<meta name="apple-mobile-web-app-capable" content="yes">
<!-- 全屏模式浏览 -->
<meta name="apple-touch-fullscreen" content="yes">
<!-- 改变Safari状态栏的外观 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<title>走秀网</title>
<link rel="stylesheet" href="<c:url value='/resources/css/base.css' />" />
<link rel="stylesheet" href="<c:url value='/resources/css/login.css' />" />
<script type="text/javascript" src="<c:url value='/resources/js/zepto.min.js' />" /></script>
<script type="text/javascript">
	$(function(){
		$("#qrcode_btn").click(function(){
			QRCode();
		});
		
	});
	
	function QRCode() {
		var params = encodeURIComponent($("#content").val());
		var img = "<c:url value='/testapi/QRCodeEncoder'/>?content="+params;
		$("#qrcode").attr("src", img);
	}
</script>
</head>
<body>
	<div id="w_login" class="wrapper">
		<div class="header">
			<a class="h_logo" href="http://wap.xiu.com"></a> 
			<a class="h_home" href="http://wap.xiu.com/loginReg/toLogin.shtml" h=""></a>
		</div>
		<div align="center">
			<img id="qrcode" alt="点击按钮二维码即将显示在这里" src="<c:url value='/testapi/QRCodeEncoder'/>">
		</div>
		<fieldset>
			<p>
				<label  for="email"><span style="font-size:20px;color:red">填写二维码内容:</span></label> 
				<input id="content" type="text" height="50px" placeholder="这是一个二维码生成测试！" required style="font-size:20px;">
			</p>
			<input id="qrcode_btn" type="button" class="login_btn" value="二维码生成" /> <br> 
		</fieldset>
	</div>
	<div class="alCopyright">
		<p>
			客服电话：<a class="tel" href="tel:400-888-4499">400-888-4499</a>
		</p>
		<p>Copyright © 2013 wap.xiu.com走秀网</p>
	</div>
</body>
</html>