<html>
	<head>
		<title>${wapH5.title!''}</title>
		<meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no'>
		<meta charset='utf-8'>
		<meta content="${wapH5.title!''}" name="keywords" />
		<meta content="${wapH5.shareContent!''}" name="description" />
		<meta content="${wapH5.photo!''}" name="author" />		
		<meta name="format-detection" content="telephone=no" />
		<script>
			var shareCont={title:'${wapH5.title!''}',desc:'${wapH5.shareContent!''}',imgUr:'${wapH5.sharePhoto!''}'}
		</script>
		<script type='text/javascript' src='http://m.xiu.com/static/js/thirdparty/jquery.min.js'></script>
		<script type="text/javascript" src="http://m.xiu.com/H5/H5Demo/swiper.min.js"></script>
		<!-- 插入GA统计代码 -->
		<script type="text/javascript" src="http://m.xiu.com/static/js/common/click_xiu.js?v092a1"></script>
		<!-- GA统计代码结束 -->
		<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
		<script type='text/javascript' src='http://m.xiu.com/H5/201512/static/echo.js'></script>
		<script type='text/javascript' src='http://m.xiu.com/H5/201512/static/common.js'></script>
		<script type='text/javascript' src='http://m.xiu.com/H5/H5Demo/xiuUser2.js'></script>
		<#if jsUrls?? && jsUrls?size &gt; 0>
			<#list jsUrls as jsUrl>
				<#if jsUrl??>
				<script type='text/javascript' src="${jsUrl!''}"></script>
				</#if>
			</#list>
		</#if>
		<link rel="stylesheet" href="http://m.xiu.com/H5/H5Demo/swiper.min.css">
		<link type='text/css' rel='stylesheet' href='http://m.xiu.com/H5/H5Demo/css/H5demo.css'>
		<#if cssUrls?? && cssUrls?size &gt; 0>
			<#list cssUrls as cssUrl>
			<link type='text/css' rel='stylesheet' href="${cssUrl!''}">
			</#list>
		</#if>
	</head>
	<body>
		<div class='container' style="background-color:${wapH5.bgColor!''}">
			${moduleView!''}
		</div>	
		<div class='scrollTop' onclick='touchStart()'></div>
		<#if wapH5.isShare = 1>
		<div class='share' onclick='share()' style='display: none;'></div>
		</#if>
	</body>
</html>	