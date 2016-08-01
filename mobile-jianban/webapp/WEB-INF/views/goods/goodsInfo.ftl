<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>【${goodsDetailVo.brandName!''} ${goodsDetailVo.goodsSn!''}】-${goodsDetailVo.goodsName!''}-走秀网官网手机站</title>
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
				
				var url = window.location.href,
				la = url.lastIndexOf('/');
				var goodsId = url.substring(la+1);
				var mURL = "http://m.xiu.com/product/"+ goodsId +".html";
							
					if (browser.versions.iPad == true) {
						win.location.href = mURL;
						return;
					}
					if (browser.versions.webKit == true || browser.versions.mobile == true || browser.versions.ios == true || browser.versions.iPhone == true || browser.versions.ucweb == true || browser.versions.ucSpecial == true) {
						win.location.href = mURL;
						return;
					}
					if (browser.versions.Symbian) {
						win.location.href = mURL;
					}
				}
			}
		})(window, browser);

		 </script>  
		 
		<!--智能手机判断结束-->	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="canonical" href="http://item.xiu.com/product/${goodsDetailVo.goodsInnerId!''}.shtml" >
		<meta name="keywords" content="${goodsDetailVo.brandName!''} ${goodsDetailVo.goodsSn!''},${goodsDetailVo.goodsName!''}"/>
		<meta name="description" content="走秀网提供${goodsDetailVo.brandName!''} ${goodsDetailVo.goodsSn!''}，价格全网最低，咨询评价${goodsDetailVo.brandName!''}好不好、怎么样，购买${goodsDetailVo.goodsName!''}，到走秀网官网手机站${goodsDetailVo.brandName!''}专区-全球时尚正品百货"/>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,minimal-ui">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/common.css" />
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/goods-details.css" />
		<link rel="stylesheet" href="http://m.xiu.com/static/css/common/base.css" />
		<script type="text/javascript" src="${ctx.getContextPath()}/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="${ctx.getContextPath()}/js/slip-min.js"></script>
		<script type="text/javascript" src="http://m.xiu.com/static/js/common/base.js"></script>
		<script type="text/javascript" src="${ctx.getContextPath()}/js/goods-details.js"></script>
	</head>
	<body>
		<!-- 页头 -->
		<#include "/common/header.ftl">
		
		<div class="header-nav">
			<a href="javascript:history.back();"><div class="return"></div>返回</a>
		</div>	
		<div class="wrap-page">
			<#if goodsDetailVo??>
		   		<#if (goodsDetailVo.imgListMax?? && goodsDetailVo.imgListMax?size > 0)>
		   			<div class="goods-pic clearfix">
						<ul class="goods-pic-ul" id="goods-pic-ul">
							<#list goodsDetailVo.imgListMax as item>
								<li><img src="${item.value}" alt="${goodsDetailVo.goodsName!''}"/></li>
					   		</#list>   
						</ul>	
						<div class="slider_status">
							<span id="slider_status_span"></span>
						</div>
					</div>
			 	<#else>
			 		<li><a href="#"></a></li>   	
				</#if>
				
				<div class="goods-title">
					<h1 class="name">${goodsDetailVo.goodsName}</h1>
					<div>
						<span class="price">¥${goodsDetailVo.zsPrice!''}</span>
						<span class="oldPrice">¥${goodsDetailVo.price!''}</span>
						<span class="discount"><b>${goodsDetailVo.discount!''}</b>折</span>
					</div>
					<p class="logistics">预计3天从美国仓库发货，12-15天送达。</p>
				</div>
	
				<div class="goods-gd">
					<div class="goods-size goods-style">
						<div class="item clearfix goods_brand">	
							<label>品牌：</label>
							<a href="http://mbrand.xiu.com/brand/goodlist/${goodsDetailVo.brandId}" title="${goodsDetailVo.brandName!''}">
								<ul>
									<li>
										${goodsDetailVo.brandName!''}
									</li>
								</ul>
							</a>
						</div>
						<div class="item clearfix goods_color">
							<label>颜色：</label>
							<ul>
								<#list goodsDetailVo.colors as color>
									<#if color_index==0>
										<li class="selected">${color}</li>
										<input type="hidden" id="colorIndex" name="colorIndex" value="${color_index}"/>
										<input type="hidden" id="color" name="color" value="${color}"/>
									<#else>
										<li>${color}</li>
									</#if>
						   		</#list>
							</ul>
							<select id="skuIndex" name="skuIndex" style="display:none;">
								<#list goodsDetailVo.styleSku as styleSku>
									<option value="${styleSku.key}">
										${styleSku.value}
									</option>
						   		</#list>
							</select>
						</div>
						<div class="item clearfix goods_size">	
							<label>尺码：</label>
							<ul>
								<#list goodsDetailVo.sizes as size>
									<#if size_index==0>
										<li class="selected">${size}</li>
										<input type="hidden" id="sizeIndex" name="sizeIndex" value="${size_index}"/>
										<input type="hidden" id="size" name="size" value="${size}"/>
									<#else>
										<li>${size}</li>
									</#if>
						   		</#list>
							</ul>
						</div>
						<div class="buy_btn">
							<div>
								<input type="hidden" id="goodsId" name="goodsId" value="${goodsDetailVo.goodsInnerId}"/>
								<input type="hidden" id="goodsSn" name="goodsSn" value="${goodsDetailVo.goodsSn}"/>
								<input type="hidden" id="activityId" name="activityId" value="${activityId!''}"/>
								<a id="buyItNow" href="javascript:void(0);" class="btn">立即购买</a>
							</div>
							<div class="fav">
								<#if favorStatus>
									<div class="fav_icon" id="favorStatus"></div>
								<#else>
									<div class="fav_icon unfav" id="favorStatus"></div>
								</#if>
								<p>收藏</p>
							</div>
						</div>
					</div>
	
						<div class="goods-desc goods-style">
							<p>基本信息</p>
							<ul>
								<#list goodsDetailVo.goodsProperties as item>
									<li><label>${item.key}：</label><span>${item.value}</span></li>
						   		</#list>
							</ul>
						</div>
					<div class="goods-com goods-style">
						<p>品牌评论</p>				
						<#if (commentInfoList?? && commentInfoList?size > 0)>
						   <#list commentInfoList as commentInfo>
						   		<div class="comment_content">
									<ul class="c_title">
										<li>${commentInfo.userNick}</li>
										<li class="grade">评分：<b>${commentInfo.commentScore}</b>分</li>
										<li>${commentInfo.commentDate?string("yyyy-MM-dd")}</li>
									</ul>
									<div class="c_content">
										<div class="left">内容：</div>
										<div class="right">${commentInfo.commentContent}</div>
									</div>
									<#if commentInfo.lastReplyContent??>
										<div class="c_content oracle">
											<div class="left">回复：</div>
											<div class="right">${commentInfo.lastReplyContent!''}</div>
										</div>
									</#if>
									<div>颜色：${commentInfo.prodColor} 尺码：${commentInfo.prodSize}</div>
									<div>
										<#if (commentInfo.commentProdAttr?? && commentInfo.commentProdAttr?size > 0)>
						  					<#list commentInfo.commentProdAttr as item>
						  						${item.key}：${item.value} &nbsp;&nbsp;
										  	</#list>
										</#if>
									</div>
									<a href="javascript:void(0);">${commentInfo.prodName}</a>
								</div>
						   </#list>
						</#if>
						<a href="http://comm.xiu.com/mobile/index/${goodsDetailVo.goodsSn}" class="more" title="走秀网 查看更多商品评论">查看所有评论>></a>
				</div>				
		 	<#else>
		 		<div class="content">
					<font color="red">暂时没有查询到记录</font>
				</div>	
			</#if>

			<!-- 页脚 -->
			<#include "/common/footer.ftl">
		</div>
	</body>
</html>