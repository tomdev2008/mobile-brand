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
		$("#openid").val("oKr0st86VMPwWMdi10CJLEXkdskI");
		$("#feedbackId").val("1217962501201401148124199999");
		
		$('#query_btn').click(function(){
			updatFeedback();
		}); 
	});
	
	function updatFeedback(){
		$.ajax({
			type : 'POST',
			dataType:'json',
			url :"<c:url value='/testapi/updateFeedback'/>",
			data : {
				"openid":$("#openid").val(),
				"feedbackId":$("#feedbackId").val()
			},
			cache : false,
			success : function(data) {
				$("#result").html(data.result);
			}
		}); 
	}
	
	
</script>
</head>
<body>
	<div id="w_login" class="wrapper">
		<div class="header">
			<a class="h_logo" href="http://wap.xiu.com"></a> 
			<a class="h_home" href="http://wap.xiu.com/loginReg/toLogin.shtml" h=""></a>
		</div>
		<fieldset>
			<br>
			<legend ><span style="font-size:20px;color:red">微信API接口测试 ==> 更新维权状态</span></legend>
			<p>
				<label for="email"><span style="font-size:20px;color:red">投诉单号：</span></label> 
				<input id="feedbackId" name="feedbackId" type="text" height="50px" required style="font-size:20px;">
			</p>
			<p>
				<label for="email"><span style="font-size:20px;color:red">投诉者微信openId：</span></label> 
				<input id="openid" name="openid" type="text" height="50px" required style="font-size:20px;">
			</p>
			<br>
			<input id="query_btn" type="button" class="login_btn" value="更新状态" /> <br>
		</fieldset>
		<table>
			<tr>
				<td><span style="font-size:20px;color:red">请求结果 : </span></td>
				<td><textarea id="result" rows="5" cols="120"  style="font-size:20px;text-align:left;"></textarea></td>
			</tr>
			
		</table>
	</div>
	<div class="alCopyright">
		<p>
			客服电话：<a class="tel" href="tel:400-888-4499">400-888-4499</a>
		</p>
		<p>Copyright © 2013 wap.xiu.com走秀网</p>
	</div>
</body>
</html>