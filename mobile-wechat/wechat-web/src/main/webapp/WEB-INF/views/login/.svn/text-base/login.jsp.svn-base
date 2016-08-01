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
<script type="text/javascript" src="../resources/js/m_base.js?v=2014091802" /></script>
<script type="text/javascript" src="../resources/js/m_validate.js?v=2014091802" /></script>
<script type="text/javascript">$(function(){$('#loginForm').xiuValidator();})</script>
</head>
<body>
<div class="wrap login">	
	<header class="header">	
		<div class="l-nav">
			<section><em class="icons"></em><a href="javascript:history.go(-1);"></a></section>			
			<section>绑定微信</section>	
			<section></section>
		</div>		
	</header>
	<section class="innerContent">	
			<form action="<%=Constants.BASE_DOMAIN%>/login/xiuLogin" id="loginForm">
				<fieldset>
					<div class="form-area">							
						<div class="f-field">
							<div class="f-ipt-area">
								<input type="text" class="f-ipt" id="username" name="xiuAccount" placeholder="请输入邮箱/手机号"/>
							</div>	
							<button type="button">
								<span></span>
							</button>						
						</div>
						<div class="f-field f-field-bdt">
							<div class="f-ipt-area">
								<input type="password" class="f-ipt" id="pwd" name="xiuPassword" placeholder="请输入密码"/>								
							</div>
							<button type="button">
								<span></span>
							</button>							
						</div>
					</div>
					<div class="f-field f-btn-area" >
						<input type="hidden" name="openId"  value="${openId}">	
						<input type="hidden" name="unionId" value="${unionId}">	
						<input type="hidden" name="targetUrl" value="${targetUrl}">	
						<input type="submit" id="J-submit" class="f-orange-btn" value="登&nbsp;录&nbsp;并&nbsp;绑&nbsp;定"/>					
					</div>
				</fieldset>
			</form>	
			<div class="f-tip clearfix">
				<section ><a  href="http://m.xiu.com/login/register1.html">免费注册</a></section>
				<section></section>
				<section class="tr"><a href="http://m.xiu.com/myxiu/retrieve_password.html">忘记密码</a></section>	
			</div>
			<!-- 
			<p class="f-grey">其他登录方式</p>
			<div class="wb-login">
				<ul class="clearfix">
					<li> <a class="icons zfb" href="http://union.xiu.com/alipay/redirect.action?type=login&url=http://m.xiu.com/login/success.html" onClick="_gaq.push(['_trackEvent',  '联合登录', '支付宝登录']);"></a> </li>
					<li><a href="http://union.xiu.com/sina/redirect.action?url=http://m.xiu.com/login/success.html" class="icons sina" onClick="_gaq.push(['_trackEvent',  '联合登录', '微博登录']);"></a></li>
					<li><a href="http://union.xiu.com/tencent/redirect.action?url=http://m.xiu.com/login/success.html" class="icons qq" onClick="_gaq.push(['_trackEvent',  '联合登录', 'QQ登录']);"></a></li>
				</ul>				
			</div>
			-->
			<footer class="footer">
				<p class="footer-link">
					<span>触屏版</span>
					<em>|</em>
					<a href="http://www.xiu.com"  onclick="addM2WWWSessionCookie();">电脑版</a>
				</p>
				<p class="footer-c">© 2014 走秀网</p>
			</footer>
			
	</section>
	<div class="login-tip"></div>	
</div>
</body>
</html>