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
		$("#edit_address_btn").click(function() {
			WeixinJSBridge.invoke('editAddress',{
				"appId" : "${appId}",
				"scope" : "jsapi_address",
				"signType" : "sha1",
				"addrSign" : "${addrSign}",
				"timeStamp" : "${timeStamp}",
				"nonceStr" : "${nonceStr}"
				},function(res){
					alert(res.err_msg);
					//若res中所带的返回值不为空，则表示用户选择该返回值作为收货地址。否则若返回空，则表示用户取消了这一次编辑收货地址。
					document.form1.address1.value =
					res.proviceFirstStageName;
					document.form1.address2.value =
					res.addressCitySecondStageName;
					document.form1.address3.value =
					res.addressCountiesThirdStageName;
					document.form1.detail.value = res.addressDetailInfo;
					document.form1.phone.value = res.telNumber;
				}
			);
		});
	}); 
</script>
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<a class="h_logo" href="http://wap.xiu.com"></a> 
			<a class="h_home" href="http://wap.xiu.com/loginReg/toLogin.shtml" h=""></a>
		</div>
		<fieldset>
			<br>
			<legend ><span style="font-size:20px;color:red">微信  &nbsp;收货地址API接口测试</span></legend>
			<br>
			<input id="edit_address_btn" type="button" class="login_btn" value="获取收货地址" /> <br>
		</fieldset>
		<table>
			<tr>
				<td><span style="font-size:20px;color:red">收货人姓名: </span></td>
				<td><input id="userName" name="userName" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">收货人电话: </span></td>
				<td><input id="telNumber" name="telNumber" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">邮编: </span></td>
				<td><input id="addressPostalCode" name="addressPostalCode" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">国标收货地址第一级地址: </span></td>
				<td><input id="proviceFirstStageName" name="proviceFirstStageName" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">国标收货地址第二级地址: </span></td>
				<td><input id="addressCitySecondStageName" name="addressCitySecondStageName" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">国标收货地址第三级地址: </span></td>
				<td><input id="addressCountiesThirdStageName" name="addressCountiesThirdStageName" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">详细收货地址信息: </span></td>
				<td><input id="addressDetailInfo" name="addressDetailInfo" type="text" height="50px" required style="font-size:20px;"></td>
			</tr>
			<tr>
				<td><span style="font-size:20px;color:red">收货地址国家码: </span></td>
				<td><input id="nationalCode" name="nationalCode" type="text" height="50px" required style="font-size:20px;"></td>
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