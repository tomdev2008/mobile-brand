<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.xiu.mobile.wechat.core.constants.Constants"%>
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
<link rel="stylesheet" href="../resources/css/m_base.css?v=2014091802" />
<link rel="stylesheet" href="../resources/css/m_login.css?v=2014091802" />
<script type="text/javascript" src="../resources/js/zepto.min.js" /></script>
<script type="text/javascript">
	$(function(){
		 $("#union_login_btn").click(function(){
			 unionLogin();
		 });
		 $('#bind_login_btn').click(function(){
			 bindLogin();
		});
	});
	function unionLogin(){
		var targetUrl ="${targetUrl}";
		var base_domain = "<%=Constants.BASE_DOMAIN%>";
		var redirect_uri = base_domain+"/login/unionLogin?targetUrl="+encodeURIComponent(targetUrl);
		window.location.href ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbfd5bd9dd172c9a6&redirect_uri="+encodeURIComponent(redirect_uri)+"&response_type=code&scope=snsapi_userinfo&state=xiu#wechat_redirect";
	}
	function bindLogin(){
		var targetUrl ="${targetUrl}";
		var base_domain = "<%=Constants.BASE_DOMAIN%>";
		var redirect_uri = base_domain + "/login/toXiuLogin?targetUrl="+encodeURIComponent(targetUrl);
		window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbfd5bd9dd172c9a6&redirect_uri="+encodeURIComponent(redirect_uri)+"&response_type=code&scope=snsapi_base&state=xiu#wechat_redirect"; 
	}
</script>
</head>
<body>
<div class="wrap login">	
	<header class="header">	
		<div class="l-nav">
			<section><em class="icons"></em><a href="javascript:history.go(-1);"></a></section>			
			<section>登录至走秀</section>	
			<section></section>
		</div>		
	</header>
	<section class="innerContent">	
			<form action="" id="loginForm">
				<fieldset>
					<aside class="aside-title">亲爱的微信用户：</aside>
					<div class="f-field f-btn-area" >
    					<aside class="aside-text">如果您是走秀网的新用户建议您:</aside>
						<input id="union_login_btn" type="button" class="f-wx-btn" value="微信号直接登录" />
					</div>
					<div class="f-field f-btn-area" >
    					<aside class="aside-text">如果您已经有走秀帐号:</aside>
						<input id="bind_login_btn" type="button" class="f-orange-btn" value="绑定走秀账号" />
					</div>
				</fieldset>
			</form>	
			<p class="f-grey">其他登录方式</p>
			<div class="wb-login">
				<ul class="clearfix">
					<li> <a class="icons zfb" href="http://union.xiu.com/alipay/redirect.action?type=login&url=http://m.xiu.com/login/success.html" onClick="_gaq.push(['_trackEvent',  '联合登录', '支付宝登录']);"></a> </li>
					<li><a href="http://union.xiu.com/sina/redirect.action?url=http://m.xiu.com/login/success.html" class="icons sina" onClick="_gaq.push(['_trackEvent',  '联合登录', '微博登录']);"></a></li>
					<li><a href="http://union.xiu.com/tencent/redirect.action?url=http://m.xiu.com/login/success.html" class="icons qq" onClick="_gaq.push(['_trackEvent',  '联合登录', 'QQ登录']);"></a></li>
				</ul>				
			</div>
			<footer class="footer">
				<p class="footer-link">
					<span>触屏版</span><em>|</em><a href="http://www.xiu.com"  onclick="addM2WWWSessionCookie();">电脑版</a>
				</p>
				<p class="footer-c">© 2014 走秀网</p>
			</footer>
	</section>
	<div class="login-tip"></div>	
</div>
</body>
</html>