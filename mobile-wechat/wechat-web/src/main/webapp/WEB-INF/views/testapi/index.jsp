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
	$(function() {
		$("#binding_btn_1").click(function() {
			window.location.href = "<c:url value='/testapi/orderQueryIndex'/>";
		});
		$("#binding_btn_2").click(function() {
			window.location.href = "<c:url value='/testapi/QRCodeIndex'/>";
		});
		$("#binding_btn_3").click(function() {
			window.location.href = "<c:url value='/testapi/editAddressIndex'/>";
		});
		$("#binding_btn_4").click(function() {
			window.location.href = "<c:url value='/testapi/deliverNotifyIndex'/>";
		});
		$("#binding_btn_5").click(function() {
			window.location.href = "<c:url value='/testapi/paymentIndex'/>";
		});
		$("#binding_btn_6").click(function() {
			window.location.href = "<c:url value='/testapi/updateFeedbackIndex'/>";
		});
		
		$("#binding_btn_7").click(function() {
			window.location.href = "<c:url value='/testapi/crossDomainTestIndex'/>";
		});
	});
</script>
</head>
<body>
	<div id="w_login" class="wrapper">
		<div class="header">
			<a class="h_logo" href="http://wap.xiu.com"></a> 
			<a class="h_home" href="http://wap.xiu.com/loginReg/toLogin.shtml" h=""></a>
		</div>
			<fieldset>
				<input id="binding_btn_1" type="button" class="login_btn" value="订单查询" /> <br> 
				<input id="binding_btn_2" type="button" class="login_btn" value="二维码生成" /> <br> 
				<input id="binding_btn_3" type="button" class="login_btn" value="收货地址" /> <br>
				<input id="binding_btn_4" type="button" class="login_btn" value="发货通知" /> <br>
				<input id="binding_btn_5" type="button" class="login_btn" value="支付测试" /> <br>
				<input id="binding_btn_6" type="button" class="login_btn" value="更新维权状态" /> <br>
				
				<input id="binding_btn_7" type="button" class="login_btn" value="测试跨域调用" /> <br>
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