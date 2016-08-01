<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"> 
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	<meta name="baidu-site-verification" content="" />
	<title>${catalog.catalogName}【品牌|价格|图片|2014新款】-走秀网官网${firstCatalogName}频道手机站</title>
	<meta content="${catalog.catalogName},${catalog.catalogName}品牌,${catalog.catalogName}价格,${catalog.catalogName}图片,2014新款${catalog.catalogName}" name="keywords">
	<meta content="走秀网官网国内最专业的时尚商城，为您提供${catalog.catalogName}商品，查看${catalog.catalogName}品牌、价格、图片及2014新款${catalog.catalogName}，网购${firstCatalogName}${catalog.catalogName}，上走秀网手机站${firstCatalogName}频道，正品保证，货到付款" name="description">
	<link rel="stylesheet" href="http://m.xiu.com/static/css/common/base.css" />
	<link rel="stylesheet" href="http://m.xiu.com/static/css/brand/brand.css?v1202a" />
	<link rel="stylesheet" href="http://m.xiu.com/static/css/search/search_listBySort.css" />
	<script type="text/javascript" src="http://m.xiu.com/static/js/thirdparty/zepto.min.js"></script>
	<script type="text/javascript" src="http://m.xiu.com/static/js/common/click_xiu.js"></script>
	</head>
	<body>
	     <div id="topPromptDlg" class="down-app">
		  	<div class="down-app-inner clearfix">
		  		<div class="app-img">
		  		
		  		</div>
		  		<a id="handleRightNow" href="javascript:;" class="dl-btn">立即下载</a>
		  	</div>
		  	<a href="javascript:;" class="down-app-close"></a>
	  </div>
		<#if page??>
			<input type="hidden" id="pageCount" value="${page.pageCount}"/>
		</#if>
		<#if channel ?exists>
	     <#else>
		<header class="header channel">	
			<div class="l-nav">
				<section><em class="icons"></em><a href="javascript:pageBack();"></a></section>	
				<section class="goodsName">${catalog.catalogName}<input type="hidden" id="catId" value="${catalog.catalogId}"/></section>
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
		<nav class="ui-nav" style="">
		<div class="nav-bar clearfix">
			<ul class="nav-list clearfix">
  				<li style="width: 0px" class=""></li>
			    <li class="curr" data-sort="11"><a href="javascript:;">推荐</a></li>
	    		<li class="ico price" data-sort="1"><a href="javascript:;">价格</a></li>
	    		<li data-sort="8"><a href="javascript:;">新上架</a></li>
			<li><div class="filter"><div class="filter-btn"><em class="icons allPro"></em>筛选</div></div></li>
	    	</ul>
	  	</div>
		</nav>
    <div class="hideNav"></div>
	<section class="content">
		<ul id="pageList" class="page-list two-col">
			<div class="container_con">
				<ul class="condition clearfix" id="condition"></ul>
			</div>
	    	<div class="activityList01 clearfix" id="tabsContainer">
	    		<#if goodsItems??>
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
									       <span class="priceNow"><i class='rmb1'></i>${goods.showPrice}</span>
									       <#if goods.showPrice?number lt goods.mktPrice?number>
									       <span class="original"><i class='rmb2'></i> <em class="g-price">${goods.mktPrice}</em></span>
									       <span class="discount"><i class="fncolor">${goods.disCount}</i>折</span>	
									       </#if>
								        </p>
									</div>
									<a href="http://m.xiu.com/product/${goods.id}.html" class="buyBtn fr">立即购买</a>
									</div>
								 </div>
								 <div class="actTextBG"></div>
							</div>
						</li>
					</#list>
				</#if>
				
	        </div>			
		</ul>

	</section>
	<div class="filter_condition">
		<header class="header">	
			<div class="l-nav">
				<section><em class="icons"></em><a  id="closeFilter" href="javascript:;"></a></section>	
				<section class="goodsName">筛选</section>
				<section class="btn"><span>重置</span><span class="submit">确定</span></section>	
			</div>	
			
		</header>
		<section class="filter_list">
			<#if attrFacets ??>
						<#list attrFacets as attrFacet>
							<div type="${attrFacet.facetType}" class="filter_attr_list">
								<p class="attr_name"><span>${attrFacet.facetDisplay}</span><b></b></p>
								<ul class="param_list"  data-cat="${attrFacet.facetDisplay}">
									<li data-id="" data-name="全部"><span>全部</span></li>
									<#if attrFacet.facetValues??>
										<#list attrFacet.facetValues as facetValue>
											<li data-id="${facetValue.id}" data-name="${facetValue.name}"><span>${facetValue.name}</span><em>(${facetValue.itemCount})</em></li>
										</#list>
									</#if>									
								</ul>
							</div>
						</#list>
					</#if> 
		</section>
	</div>
	<#if channel ?exists>
	 <#else>
		<footer class="footer">
			<p class="footer-link"><span>触屏版</span><em>|</em>
				<a href="http://www.xiu.com"  onclick="addM2WWWSessionCookie();">电脑版</a>
			</p>
			<p class="footer-c">© 2014 走秀网</p>
		</footer>
	</#if>
	<a href="javascript:scroll(0,0)" class="scrollTop"></a>
	<div class="fixloading">
		<img src="http://m.xiu.com/static/css/img/load.gif">
</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://xres.xiu.com/static/xclk-jsapi.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/base.js?v0901a"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/thirdparty/slip-min.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/common/chama_common.js"></script>
<script type="text/javascript" src="http://m.xiu.com/static/js/search/search_listBySort.js?v1202a"></script>
<script type="text/javascript">
//微信分享接口调用
if(Xiu.isWechatBrowser()){	
	//微信分享
	var dataFriend = {"img":null,"title":'${catalog.catalogName}【品牌|价格|图片|2014新款】',"desc":'走秀网官网国内最专业的时尚商城，为您提供${catalog.catalogName}商品，查看${catalog.catalogName}品牌、价格、图片及2014新款${catalog.catalogName}，网购${firstCatalogName}${catalog.catalogName}，上走秀网手机站${firstCatalogName}频道，正品保证，货到付款','mainURL':location.href};
	wechatShare(dataFriend);
}
</script>
</body>	
</html>
