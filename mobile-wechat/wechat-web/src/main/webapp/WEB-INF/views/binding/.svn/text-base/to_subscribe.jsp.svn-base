<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta name="baidu-site-verification" content="" />
<title>走秀网-登录</title>
<meta content="走秀网" name="keywords">
<meta content="" name="description">
<link rel="stylesheet" href="../resources/css/m_base.css?v=2014091801" />
<link rel="stylesheet" href="../resources/css/m_login.css?v=2014091801" />
<script type="text/javascript" src="../resources/js/zepto.min.js" /></script>
<script type="text/javascript" src="../resources/js/m_base.js?v=2014091801" /></script>
<script>
	// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
		$('#subscribe_btn').click(function(){
			addWxContact('gh_50071c0d98af');
		});
	}, false);
	
	function addWxContact(username) {
		if (typeof WeixinJSBridge == 'undefined'){
			return false;
		}
		//一键关注需要在支付授权目录下发起请求，80端口，测试目录需要添加白名单。
		//该功能不在支付中心职责下，暂不添加。
		WeixinJSBridge.invoke('addContact', {
			webtype : '1',
			username : username
		}, function(d) {
			alert(d.err_msg);
			// 返回d.err_msg取值，d还有一个属性是err_desc
			// add_contact:cancel 用户取消
			// add_contact:fail　关注失败
			// add_contact:ok 关注成功
			// add_contact:added 已经关注
			WeixinJSBridge.log(d.err_msg);
		});
	}
	
</script>
</head>
<body>
<div class="wrap login">	
	<header class="header">	
		<div class="l-nav">
			<section><em class="icons"></em><a href="javascript:history.go(-1);"></a></section>			
			<section>o(︶︿︶)o</section>	
			<section></section>
		</div>		
	</header>
	<section class="innerContent">	
			<form action="" id="loginForm">
				<fieldset>
				<aside class="aside-title">尊敬的用户：</aside>
    			<aside class="aside-text" style="font-size:1.2em">很抱歉，由于您尚未关注走秀网服务号，无法为您提供更多便利服务，请搜索并关注 <font style="font-weight:bold;">走秀网商城</font>。</aside>
    			<!-- 
    			<input id="subscribe_btn" type="button" class="login_wx" value="关注走秀商城" /> 
    			-->
			</fieldset>
			</form>	
			<div class="f-tip clearfix">
				<section ></section>
				<section></section>
				<section class="tr"><a href="http://m.xiu.com">返回首页</a></section>	
			</div>
			<footer class="footer">
				<p class="footer-c">© 2014 走秀网</p>
			</footer>
			
	</section>
	<div class="login-tip"></div>	
</div>
</body>
</html>