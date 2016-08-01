<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>精选卖场商品大全，汇集最全的国内国际知名商品-走秀网官网手机站</title>
		<!--智能手机判断-->
	 <script type = "text/javascript" language = "javascript" >
			 
		function getUrlProperty(key) {
			   var url = location.search ; 
			   var theRequest = new Object();
			   if (url.indexOf("?") != -1) {
			      var str = url.substr(1);
			      strs = str.split("&");
			      for(var i = 0; i < strs.length; i ++) {
			         theRequest[strs[i].split("=")[0]]=decodeURIComponent(strs[i].split("=")[1]);
			      }
			   }
		   return theRequest[key];
		}
			 
			 
			 
			 
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
				
				var actId = getUrlProperty("activityId");
				var mUrl = "http://m.xiu.com/cx/"+actId+".html?from=wap";
					if (browser.versions.iPad == true) {
						win.location.href = mUrl;
						return;
					}
					if (browser.versions.webKit == true || browser.versions.mobile == true || browser.versions.ios == true || browser.versions.iPhone == true || browser.versions.ucweb == true || browser.versions.ucSpecial == true) {
						win.location.href = mUrl;
						return;
					}
					if (browser.versions.Symbian) {
						win.location.href = mUrl;
					}
				}
			}
		})(window, browser);

		 </script>  
		 
		<!--智能手机判断结束-->	
	
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="精选卖场商品"/>
		<meta name="description" content=""/>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,minimal-ui">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/common.css" />
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/goods-list.css" />
		<script type="text/javascript" src="${ctx.getContextPath()}/js/zepto.min.js"></script>
	</head>
	<body>
		<!-- 页头 -->
		<#include "/common/header.ftl">
		
		<div class="header-nav">
			<a href="javascript:history.back();"><div class="return"></div>返回</a>
		</div>	
		<div class="wrap-page">
			<form action="${ctx.getContextPath()}/product/good-list" method="get">
				<div class="find-container" id="find-container">
					<#if (goodsList?? && goodsList?size > 0)>
					   <#list goodsList as goods>
					   		<div class="find clearfix">
								<a href="${ctx.getContextPath()}/product/${goods.goodsId}" class="pic_left">		
									<img src="${ctx.getContextPath()}/images/default_162x216.png" src3="${goods.goodsImg}" alt="发现" onerror="this.src='${ctx.getContextPath()}/images/default_162x216.png'"/>
								</a>	
								<div class="text">
									<p>${goods.goodsName}</p>
									<div class="desc">
										<!--商品列表没有描述属性  goods.content-->
									</div>
									<div class="price">
										<span>¥${goods.zsPrice}</span>
									</div>
								</div>
							</div>
					   </#list>
				 	<#else>
				 		<div class="find clearfix">
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
	<script type="text/javascript">
		$(function(){
			lazyload({defObj : "#find-container"});
		});
	</script>
</html>