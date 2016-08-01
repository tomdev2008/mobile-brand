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
		$("#outTradeNo").val(Math.floor(new Date().getTime() / 10000));
		$("#body").val("微信支付测试");
		$("#totalFee").val("1");
		
		if (!isWechat()) {
			$(".login_btn").hide();
		}
	});
	
	// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		//公众号支付
		jQuery('#getBrandWCPayRequest').click(function(e) {
			$.ajax({
				type : 'POST',
				dataType : 'json',
				url : "<c:url value='/payment/toPay'/>",
				data : {
					"outTradeNo" : $("#outTradeNo").val(),
					"body" : $("#body").val(),
					"totalFee" : $("#totalFee").val()
				},
				cache : false,
				success : function(data) {
					pay(data);
				}
			});
		});
		WeixinJSBridge.log('yo~ ready.');
	}, false);

	
	function pay(obj) {
		WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : obj.appId, //公众号名称，由商户传入
			"timeStamp" : obj.timeStamp, //时间戳
			"nonceStr" : obj.nonceStr, //随机串
			"package" : obj.packageStr,//扩展包
			"signType" : obj.signType, //微信签名方式:1.sha1
			"paySign" : obj.paySign
		//微信签名
		}, function(res) {
			
			alert(res.err_msg);
			if (res.err_msg == "get_brand_wcpay_request:ok") {
				alert("支付成功!");
			}
			// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
			//因此微信团队建议，当收到ok返回时，向商户后台询问是否收到交易成功的通知，若收到通知，前端展示交易成功的界面；若此时未收到通知，商户后台主动调用查询订单接口，查询订单的当前状态，并反馈给前端展示相应的界面。
		});
	}

	function isWechat() {
		var ua = navigator.userAgent.toLowerCase();
		if (ua.match(/MicroMessenger/i) == "micromessenger") {
			return true;
		} else {
			return false;
		}
	}
</script>
</head>
<body>
	<div class="wrapper">
		<div class="header">
		    <a class="h_logo" href="http://wap.xiu.com"></a>
		    <a class="h_home" href="http://wap.xiu.com/loginReg/toLogin.shtml" h=""></a>
		</div>
		<div id="w_login">
			<fieldset>
				<br>
				<legend>
					<span style="font-size: 20px; color: red">微信支付测试</span>
				</legend>
				<p>
					<label for="email"><span style="font-size: 20px; color: red">订单号：</span></label> 
					<input id="outTradeNo" name="outTradeNo" type="text" required>
				</p>
				<p>
					<label for="email"><span style="font-size: 20px; color: red">商品名称：</span></label> 
					<input id="body" name="body" type="text" required>
				</p>
				<p>
					<label for="email"><span style="font-size: 20px; color: red">总费用：</span></label> 
					<input id="totalFee" name="totalFee"  type="text" required>
				</p>
				<br> 
				<a id="getBrandWCPayRequest" href="#" class="login_btn">微信支付</a>
			</fieldset>
		</div>
	</div>
	<div class="alCopyright">
		<p>
			客服电话：<a class="tel" href="tel:400-888-4499">400-888-4499</a>
		</p>
		<p>Copyright © 2013 wap.xiu.com走秀网</p>
	</div>
</body>
</html>