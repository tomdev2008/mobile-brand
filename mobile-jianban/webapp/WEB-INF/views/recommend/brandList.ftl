<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>精选品牌大全，汇集最全的国内国际知名品牌-走秀网官网手机站</title>

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
		<meta name="keywords" content="精选品牌大全"/>
		<meta name="description" content=""/>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,minimal-ui">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/common.css" />
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/index.css" />
		<script type="text/javascript" src="${ctx.getContextPath()}/js/index.js"></script>
		<script type="text/javascript" src="${ctx.getContextPath()}/js/zepto.min.js"></script>
	</head>
	<body>
		<!-- 页头 -->
		<#include "/common/header.ftl">
	
		<div class="header-nav">
			<ul>
				<li ><a href="${ctx.getContextPath()}/recommend">推荐</a></li>
				<li><a href="${ctx.getContextPath()}/find">发现</a></li>
				<li class="selected"><h1>品牌</h1></li>
				<div class="brand_line"></div>
			</ul>
		</div>	
		<div class="wrap-page">
			<!-- 品牌推荐 begin -->	
			<div >
				<ul class="brand">
					<#if (wellChosenBrandList?? && wellChosenBrandList?size > 0)>
					   <#list wellChosenBrandList as wellChosenBrand>
					   		<#if (wellChosenBrand.list?? && wellChosenBrand.list?size > 0)>
						   		<ul>${wellChosenBrand.name}</ul>	
						   		<#list wellChosenBrand.list as brand>
						   			<li><a href="http://mbrand.xiu.com/brand/goodlist/${brand.brandId}" title="${brand.enName!''}${brand.brandName!''}">${brand.brandName!''}</a></li>   		
						   		</#list>   		
						 	<#else>
						 		<li><a href="#"></a></li>   	
							</#if>		
					   </#list>
				 	<#else>
				 		<div class="goods_info">
							<font color="red">暂时没有查询到记录</font>
						</div>	
					</#if>
				</ul>
			</div>
			<!-- 品牌推荐 end-->
					
			<!-- 页脚 -->
			<#include "/common/footer.ftl">
		</div>
	</body>
</html>