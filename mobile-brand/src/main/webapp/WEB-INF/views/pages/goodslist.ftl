<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta name="baidu-site-verification" content="" />
<meta http-equiv="pragma" content="no-cache" />
<title>${seoInfo.title}</title>
<meta content="${seoInfo.keyWord}" name="keywords" id="keywords">
<meta content="${seoInfo.description}" name="description">
<link rel="stylesheet" href="http://m.xiu.com/static/css/common/base.css" />
<link rel="stylesheet" href="http://m.xiu.com/static/css/brand/brand.css?v1202a" />
<script type="text/javascript" src="http://m.xiu.com/static/js/thirdparty/zepto.min.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/click_xiu.js"></script>
</head>
<body>
<div class="wrap" id="index">
<!--
	   <div id="topPromptDlg" class="down-app open-app">
		  	<div class="down-app-inner clearfix">
		  		<div class="app-img">
		  		
		  		</div>
		  		<a id="handleRightNow" data-app="" href="javascript:;" class="dl-btn">立即打开</a>
		  	</div>
		  	<a href="javascript:;" class="down-app-close"></a>
	  </div>
-->
	<!--头部开始-->
	<#if channel ?exists>
		<#else>
		<header class="header headerOther channel">
			<div class="l-nav">
				<section><em class="icons"></em><a href="javascript:pageBack();"></a></section>
				<section><h1 class="header-title" id="activeName">${brandInfo.mainName}</h1></section>
				<section> 			
				 <em class="icons loginPanel"></em>
				<a href="javascript:;" id="loginPanel"></a>
		        <!--用户信息 我的订单 查物流-->
	                <div class="triangle-up hidden"></div>
			        <ul class="loginStatus hidden">
			            
			        </ul>
				</section>
			</div> 
		</header>
	</#if>
    <!--导航开始-->
    <nav class="ui-nav" style="">
		<div class="nav-bar clearfix">				
			<ul class="nav-list clearfix">
	    		<#if sortId == 8>
	    		<li style="width: 0px" class=""></li>
			    <li data-sort="11" ><a href="javascript:;">推荐</a></li>
	    		<li class="ico price" data-sort="1"  ><a href="javascript:;">价格</a></li>
	    		<li class="curr" data-sort="8"><a href="javascript:;">新上架</a></li>
			 	<#else>
			 	<li style="width: 0px" class=""></li>
			    <li class="curr" data-sort="11" ><a href="javascript:;">推荐</a></li>
	    		<li class="ico price" data-sort="1"  ><a href="javascript:;">价格</a></li>
	    		<li data-sort="8"><a href="javascript:;">新上架</a></li>
			 	</#if>
			<li><div class="filter"><div class="filter-btn"><em class="icons allPro"></em>筛选</div></div></li>
	    	</ul>
	  	</div>
	</nav>
	<div class="hideNav"></div>
    <!--主要内容-->
    <section class="main">
		<#if brandInfo.bannerPic??>
		<div class="banner">
			<img src="${brandInfo.bannerPic}" alt="${brandInfo.mainName}" />
		</div>
		</#if>
		<div class="story" >
			<div class="story-hd"><h6 class="tit"><span class="storyOn"><#if brandInfo.story??>品牌故事</#if></span>
			   <span data-brandid="${brandInfo.brandId}" class="icons favbrand-btn" >收藏品牌</span>
			   <#if brandInfo.story??><span class="show-story"></span></#if></h6>
			</div>
			<#if brandInfo.story??>
			<div class="story-bd" id="story-wrap">
				<p class="story-wrap-inner">${brandInfo.story}</p>
			</div>
			</#if>			
		</div>
		<ul id="pageList" class="page-list two-col">
			<div class="container_con">
				<ul class="condition clearfix" id="condition"></ul>
			</div>
	    	<div class="activityList01 clearfix" id="tabsContainer">
	    	 <#if goodsItems?? && goodsItems?size gt 0>
	    	    <#list goodsItems as goods>
				<li>
					<div class="picBox clearfix">
					<#if channel ?exists>
                         <a href="xiuApp://xiu.app.goodsdetail/openwith?id=${goods.id}" class="activity">
                   <#else>
                         <a href="http://m.xiu.com/product/${goods.id}.html" class="activity">
                   </#if>
					     <img src="${goods.imgUrl}" onerror="javascript:this.src='http://m.xiu.com/static/css/img/default.315_420.jpg';" style="width:100%;" alt="${goods.name}"></a>   
					     <div class="actText clearfix">    
					         <div class="line clearfix"> 
						     <div class="leftPart"> 
						        <h2 class="productName">
				                     <a href="http://m.xiu.com/product/${goods.id}.html">
				                            <#if goods.brandEnName ?exists>
				                                 <p>${goods.brandEnName}</p>
				                           <#else>
				                                 <p>${goods.brandCNName}</p>
				                           </#if>
				                                <p style="text-overflow:ellipsis;white-space: nowrap;overflow: hidden;">${goods.name}</p>
				                     </a>
                                 </h2>
								<p class="price-area clearfix">
									<span class="priceNow"><i>¥</i>${goods.showPrice}</span>
									 <#if goods.showPrice?number lt goods.mktPrice?number>
									 <span class="original"><i>¥</i> <em class="g-price">${goods.mktPrice}</em></span>
									 <span class="discount"><i class="fncolor">${goods.disCount}</i>折</span>	
									 </#if>
								</p>
							</div>
							<#if goods.soldOut == false>
							<a href="http://m.xiu.com/product/${goods.id}.html" class="buyBtn fr">立即购买</a>
							<#else>
							<span class="buyBtn gry-btn fr">立即购买</span>
							</#if>
							</div>
						 </div>
						 <div class="actTextBG"></div>
						 <#if goods.soldOut == true>
						 <a href="http://m.xiu.com/product/${goods.id}.html" class="shouqin"></a>
						 </#if>
					</div>
				</li>
				</#list>
			<#else>
				<p class="no-data">没有找到符合条件的商品！</p>
			</#if>
	        </div>			
		</ul>
	</section>
	<input type="hidden" id="currPage" value="${page.pageNo}"/>
	<input type="hidden" id="nextPage" value="${page.pageNo+1}" />
	<input type="hidden" id="totalPage" value="${page.pageCount}" />
	<form class="hidden" action="" id="brandForm">
		<input type="hidden" id="bid" name="bId" value="${brandId}" />
		<!-- <input type="hidden" id="catid" name="catId" value="" />
		<input type="hidden" id="pageNum" name="p" value="1" /> -->
		<input type="hidden" id="pSize" name="pSize" value="${page.pageSize}" />
<!-- 		<input type="hidden" class="clearIpt" id="filter" name="filter" value="" />
		<input type="hidden" class="clearIpt clearPrice" id="fPrice" name="fPrice" value="" />
		<input type="hidden" class="clearIpt clearPrice" id="sPrice" name="sPrice" value="" />
		<input type="hidden" class="clearIpt clearPrice" id="ePrice" name="ePrice" value="" /> -->
		<input type="hidden" id="sort"  name="sort" value="${sortId}" />
	</form>

	<div class="filter_condition">
		<header class="header">	
			<div class="l-nav">
				<section><em class="icons"></em><a  id="closeFilter" href="javascript:;"></a></section>	
				<section class="goodsName">筛选</section>
				<section class="btn"><span>重置</span><span class="submit">确定</span></section>	
			</div>	
			
		</header>
		<section class="filter_list">
			<#if catalogs?? && catalogs?size gt 0>
				<h1 class="selected">分类<cite></cite></h1>
					<#list catalogs as catalog>
					<div class="filter_cat_list">
						<p class="cat_name"><span>${catalog.catalogName}</span><b></b></p>
							<ul class="param_list">
								<li data-id="${catalog.catalogId}"data-name="全部">全部<em>(${catalog.itemCount})</em></li>
								<#if catalog.childCatalog?? && catalog.childCatalog?size gt 0>
								<#list catalog.childCatalog as childCatalog>
								<li data-id="${childCatalog.catalogId}"data-name="${childCatalog.catalogName}">${childCatalog.catalogName}<em>(${childCatalog.itemCount})</em></li>
								</#list>
								</#if>
							</ul>
						</div>
					</#list>
				</#if>
		</section>
		<section class="attr_list">
		</section>
	</div>

    <!--页面底部-->
	<section class="innerContent">
	<#if channel ?exists>
		<#else>
		<footer class="footer">
			<p class="footer-link"><span>触屏版</span><em>|</em><a id="goto_www" href="#" onClick="setGoToWWW();">电脑版</a></p>
			<p class="footer-c">© 2014 走秀网</p>
		</footer>
	</#if>
	</section>
    <a href="#" class="scrollTop"></a>
</div>
<div class="fixloading">
		<img src="http://m.xiu.com/static/css/img/load.gif">
</div>
<div id="bgBox" class="hidden"></div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/lazyload.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/base.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/thirdparty/slip-min.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/dlApp.js?v1023a"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/thirdparty/iScroll-min.js"></script>
<script type="text/javascript">
	var brandName = '${brandInfo.mainName}';
</script>
<script type="text/javascript">
//微信分享接口调用
if(Xiu.isWechatBrowser()){	
	//微信分享
	var dataFriend = {"img":null,"title":'${brandInfo.mainName},全球新品同步销售！',"desc":'${brandInfo.mainName},时尚新先、欧美同价、正品保障！','mainURL':location.href};
	wechatShare(dataFriend);
}
</script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/chama_common.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/brand/mbrand.js?v1202a"></script>
</body>
</html>
