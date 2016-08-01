<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>发现频道，一起开启探寻最具诱惑的时尚奢侈品-走秀网官网手机站</title>
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
		<meta name="keywords" content="发现商品"/>
		<meta name="description" content=""/>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,minimal-ui">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/common.css" />
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/index.css" />
		<script type="text/javascript" src="${ctx.getContextPath()}/js/zepto.min.js"></script>
	</head>
	<body>
		<!-- 页头 -->
		<#include "/common/header.ftl">

		<div class="header-nav">
			<ul>
				<li><a href="${ctx.getContextPath()}/recommend" target="_self">推荐</a></li>
				<li class="selected"><h1>发现</h1></li>
				<li><a href="${ctx.getContextPath()}/brand-goodlist" target="_self">品牌</a></li>
				<div class="goods_line"></div>
			</ul>
		</div>	
		<div class="wrap-page">
			<form action="${ctx.getContextPath()}/find" method="get">
				<div class="find-container" id="find-container">
					<#if (findGoodsList?? && findGoodsList?size > 0)>
					   <#list findGoodsList as findGoods>
					   		<div class="find clearfix">
								<a href="${ctx.getContextPath()}/product/${findGoods.goodsId}" class="pic_left">
									<img src="${ctx.getContextPath()}/images/default_162x216.png" src3="${findGoods.goodsImg}" alt="发现" onerror="this.src='${ctx.getContextPath()}/images/default_162x216.png'"/>
								</a>	
								<div class="text">
									<p>${findGoods.title}</p>
									<div class="desc">${findGoods.content}</div>
									<div class="price">
										<span> ¥${findGoods.goodsPrice} </span>
										<div class="like">
											<b>${findGoods.loveGoodsCnt}</b>
										</div>
									</div>
								</div>
							</div>	
					   </#list>
				 	<#else>
				 		<div class="content">
							<font color="red">暂时没有查询到记录</font>
						</div>	
					</#if>
					
					<!-- 分页信息 -->
					<#include "/common/pagination.ftl">
				</div>
			</form>
					
			<!-- 页脚 -->
			<#include "/common/footer.ftl">
		</div>
	</body>
	<script type="text/javascript" src="${ctx.getContextPath()}/js/lazyload.js"></script>
	<script type="text/javascript" src="${ctx.getContextPath()}/js/index.js"></script>
</html>