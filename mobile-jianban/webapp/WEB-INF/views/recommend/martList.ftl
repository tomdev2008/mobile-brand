<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>【走秀网官网手机站-每日促销】-国内时尚奢侈品购物网站</title>
		
		
					
			<!--智能手机判断-->
			 <script type = "text/javascript" language = "javascript" >
			Function.prototype.bind = function (bindObj, args) {
			var _self = this;
			return function () {
				return _self.apply(bindObj, [].concat(args))
			}
		};
		function $(id) {
			return "string" == typeof id ? document.getElementById(id) : id;
		};

		var browser = {
			versions : function () {
				var u = navigator.userAgent,
				app = navigator.appVersion;
				return {
					trident : u.indexOf('Trident') > -1,
					presto : u.indexOf('Presto') > -1,
					webKit : u.indexOf('AppleWebKit') > -1,
					gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
					mobile : !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/),
					ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
					android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
					iPhone : u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1,
					iPad : u.indexOf('iPad') > -1,
					webApp : u.indexOf('Safari') == -1,
					QQbrw : u.indexOf('MQQBrowser') > -1,
					ucLowEnd : u.indexOf('UCWEB7.') > -1,
					ucSpecial : u.indexOf('rv:1.2.3.4') > -1,
					ucweb : function () {
						try {
							return parseFloat(u.match(/ucweb\d+\.\d+/gi).toString().match(/\d+\.\d+/).toString()) >= 8.2
						} catch (e) {
							if (u.indexOf('UC') > -1) {
								return true;
							} else {
								return false;
							}
						}
					}
					(),
					Symbian : u.indexOf('Symbian') > -1,
					ucSB : u.indexOf('Firefox/1.') > -1
				};
			}
			()
		}
		var _gaq = _gaq || [];
		(function (win, browser, undefined) {
			var rf = document.referrer;
			if (rf === "" || rf.toLocaleLowerCase().indexOf(".xiu.com") === -1) {
				if (screen == undefined || screen.width < 810) {
				
				var mRUL = "http://m.xiu.com/?from=wap";
				
					if (browser.versions.iPad == true) {
						win.location.href = mRUL;
						return;
					}
					if (browser.versions.webKit == true || browser.versions.mobile == true || browser.versions.ios == true || browser.versions.iPhone == true || browser.versions.ucweb == true || browser.versions.ucSpecial == true) {
						win.location.href = mRUL;
						return;
					}
					if (browser.versions.Symbian) {
						win.location.href = mRUL;
					}
				}
			}
		})(window, browser);

		 </script>  
		 
		<!--智能手机判断结束-->	
		
		
		
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="canonical" href="http://www.xiu.com" >
		<meta name="keywords" content="走秀网,走秀网官网,奢侈品,奢侈品购物网站"  />
		<meta name="description" content="走秀网官网每日促销频道，时尚新势力 周五大不同-主场，为您提供最实惠的促销商品，女装、男装、鞋靴、包袋、腕表、饰品、妆品、家居、婴童、礼物等上千款时尚奢侈品，网购时尚就到XIU.COM-国内时尚奢侈品购物网站。" />
		<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,minimal-ui"/>
		<meta name="apple-mobile-web-app-capable" content="yes"/>
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/common.css" />
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/index.css" />
		<script type="text/javascript" src="${ctx.getContextPath()}/js/zepto.min.js"></script>
	</head>
	<body>
		<!-- 页头 -->
		<#include "/common/header.ftl">
		
		<div class="header-nav">
			<ul>
				<li class="selected"><h1>推荐</h1></li>
				<li><a href="${ctx.getContextPath()}/find">发现</a></li>
				<li><a href="${ctx.getContextPath()}/brand-goodlist">品牌</a></li>
				<div class="s_line"></div>
			</ul>
		</div>	
		<div class="wrap-page">
			<form action="${ctx.getContextPath()}/recommend" method="get">
				<!-- 卖场start-->	
				<div id="goods-container">
					<#if (topicList?? && topicList?size > 0)>
					   <#list topicList as topic>
					   		<div class="goods_info">
								<a href="${ctx.getContextPath()}/product/good-list" target="_self">
									<img src3="${topic.mobile_pic!''}" src="${ctx.getContextPath()}/images/default_720x450.png" alt="${topic.name!''}" onerror="this.src='${ctx.getContextPath()}/images/default_720x450.png'"></img>
									<ul class="text">
										<li class="title">${topic.name!''}</li>
										<li class="date">
											${topic.start_time?string("M")}月${topic.start_time?string("dd")}日
											-
											${topic.end_time?string("M")}月${topic.end_time?string("dd")}日
										</li>
									</ul>
								</a>
							</div>			   		
					   </#list>
				 	<#else>
				 		<div class="goods_info">
							<font color="red">暂时没有查询到记录</font>
						</div>	
					</#if>
				
					<!-- 分页信息 -->
					<#include "/common/pagination.ftl">
				</div>
				<!-- 卖场end -->
			</form>
			<!-- 页脚 -->
			<#include "/common/footer.ftl">
		</div>
	</body>
	<script type="text/javascript" src="${ctx.getContextPath()}/js/lazyload.js"></script>
	<script type="text/javascript" src="${ctx.getContextPath()}/js/index.js"></script>
	
</html>