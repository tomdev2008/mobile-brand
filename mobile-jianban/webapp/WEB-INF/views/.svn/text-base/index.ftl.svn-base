<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no,minimal-ui">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<title>简版首页--走秀网</title>
		<link rel="stylesheet" href="${ctx.getContextPath()}/css/common.css" />
		<script type="text/javascript" src="http://m.xiu.com/static/js/thirdparty/zepto.min.js"></script>
		<style type="text/css">
			.wrap-page > div{display:none;}
			.wrap-page div:nth-child(1){display:block;}
			.wrap-page .goods_info:first-child{border:0;}
			.goods_info{padding-top:10px;background: #f0f0f0;border-top:1px solid #e1e1e1;}
			.goods_info img{width:100%;height:auto;}
			.goods_info .text{display:box;display:-webkit-box;display:-moz-box;padding: 10px 3%;background:#fff;} 
			.goods_info .text .title{box-flex:1;-webkit-box-flex:1;-moz-box-flex:1;text-align: left;color:#454545;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
			.goods_info .text .date{width:8em;color:#A1A1A1;text-align: right;}

			.find-container{background:#F0F0F0;padding-top:10px;}
			.find{background: #fff;border-top:1px solid #E1E1E1;padding:10px 4%;}
			.find:first-child{border-color: #DADADA;}
			.find img{width:102px;height:136px;}
			.find .pic_left{float:left;width:35%;}
			.find .text{margin-left:35%;}
			.find .text p{font-weight: bold;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
			.find .text .desc{margin:10px 0; color:#A1A1A1;}
			.find .text .price{color:#E97616;height:30px;position: relative;}
			.find .text .price > span{text-align: left;margin-top: 5px;display: inline-block;}
			.find .text .price .like, .find .text .price .dislike{border:1px solid #E97616;float:right;padding:5px;border-radius: 5px;}
			.find .text .price .dislike{border-color:#D8D8D8;color:#7C7C7C;}
			.find .like:before, .find .dislike:before{content: "";position: absolute;top:7px;background: url("css/images/bg.png") no-repeat;background-position: 0px -22px;width: 20px;height: 16px;background-size: 144px;}
			.find  b{margin-left: 25px;}
			.find .dislike:before{background-position: -18px -22px;}

			.brand{color:#464646;}
			.brand h1:first-child{border-top:0;}
			.brand h1{background:#f0f0f0;border-width:1px 0px;border-style:solid;border-color:#d8d8d8;line-height: 1.5em;padding:0 4%;}
			.brand li{line-height: 2.5em;padding:0 4%;}
			.brand li:active{background:#e97614;color:#fff;}

		</style>
	</head>
	<body>
		<div class="header">
			<a class="logo" href="#" target="_self"></a>
			<ul class="pull-right">
				<li>登入</li>
				<li class="split">|</li>
				<li>注册</li>
			</ul>
		</div>
		<div class="header-nav">
				<ul>
					<li class="selected">推荐</li>
					<li>发现</li>
					<li >品牌</li>
					<div class="s_line"></div>
				</ul>
			</div>	
		<div class="wrap-page">
			<!--tab1 推荐 begin-->	
			<div >
				<#if (topicList?? && topicList?size > 0)>
				   <#list topicList as topic>
				   		<div class="goods_info">
							<a href="#" target="_self">
								<img src="${topic.mobile_pic!''}" alt="${topic.name!''}"></img>
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
				<div class="paging">
					<ul class="clearfix">
						<li class="disPrevious"><span>上一页</span></li>
						<li class="currpage"><input type="text" />/<span>100</span></li>
						<li class="next"><span>下一页</span></li>				
					</ul>
					<div class="go">跳转</div>
				</div>
			</div>
			<!--tab1 推荐 end-->

			<!--tab2 推荐 begin-->	
			<div class="find-container">
				<div class="find clearfix">
					<a href="#" class="pic_left">
						<img src="${ctx.getContextPath()}/css/images/find-pic.png" alt="发现"/>
					</a>	
					<div class="text">
						<p>2014春季女长袖针织时尚T恤季女长</p>
						<div class="desc">春季女长袖针织时尚T恤季女长</div>
						<div class="price">
							<span>¥200323.00</span>
							<div class="like">
								<b>550</b>
							</div>
						</div>
					</div>
				</div>

				<div class="find clearfix">
					<a href="#" class="pic_left">
						<img src="${ctx.getContextPath()}/css/images/find-pic.png" alt="发现"/>
					</a>	
					<div class="text">
						<p>2014春季女长袖针织时尚T恤季女长</p>
						<div class="desc">春季女长袖针织时尚T恤季女春季女长袖针织时尚T春季女长袖针织时尚T春季女长袖针织时尚T春季女长袖针织时尚T长</div>
						<div class="price">
							<span>¥200323.00</span>
							<div class="dislike">
								<b>550</b>
							</div>
						</div>
					</div>
				</div>
				<div class="find clearfix">
					<a href="#" class="pic_left">
						<img src="${ctx.getContextPath()}/css/images/find-pic.png" alt="发现"/>
					</a>	
					<div class="text">
						<p>2014春季女长袖针织时尚T恤季女长</p>
						<div class="desc">春季女长袖针织时尚T恤季女长春季女长袖针织时尚T春季女长袖针织时尚T春季女长袖针织时尚T春季女长袖针织时尚T春季女长袖针织时尚T</div>
						<div class="price">
							<span>¥200323.00</span>
							<div class="dislike">
								<b>550</b>
							</div>
						</div>
					</div>
				</div>
				<div class="paging">
					<ul class="clearfix">
						<li class="previous"><span>上一页</span></li>
						<li class="currpage"><input type="text" />/<span>100</span></li>
						<li class="disNext"><span>下一页</span></li>				
					</ul>
					<div class="go">跳转</div>
				</div>
			</div>
			<!--tab2 推荐 end-->

			<!--tab3 推荐 begin-->	
			<div >
				<ul class="brand">
					<h1>A</h1>
						<li><a href="#">爱琪美(Academie)</a></li>
						<li><a href="#">彭玛之源(Acqua Di Parma)</a></li>
						<li><a href="#">阿迪达斯(adidas)</a></li>
						<li><a href="#">adidas by Stella McCartney</a></li>
						<li><a href="#">Aeropostale</a></li>
						<li><a href="#">伊索(Aesop)</a></li>
					<h1>B</h1>
						<li><a href="#">B Philip</a></li>
						<li><a href="#">Babakul</a></li>
						<li><a href="#">巴兰缇妮(BALLANTYNE)</a></li>
						<li><a href="#">班德里诺(BANDOLINO)</a></li>
					<h1>C</h1>
						<li><a href="#">Calvin Klein Jeans</a></li>
						<li><a href="#">Cameleon</a></li>
						<li><a href="#">凯伊黛(CARITA)</a></li>
				</ul>
			</div>
			<!--tab3 推荐 end-->
					
			<section class="footer">
				<ul>
					<li class="link"><a href="javascript:;" class="on">简版</a></li>
					<li class="split">|</li>
					<li class="link"><a href="http://m.xiu.com" target="_self">触屏版</a></li>
					<li class="split">|</li>
					<li class="link"><a href="http://www.xiu.com" target="_self">电脑版</a></li>
				</ul>	
			</section>
	
		</div>
		
	</body>
	<script type="text/javascript">
		$(function(){

			//主页的方法
			var main = {

				//初始化方法
				init: function(){
					this.tabChange();	
				},

				//Tab切换的方法
				tabChange: function(){
					var tabs = $(".header-nav li");
					var contents = $(".wrap-page > div");
					$.each(tabs, function(index, dom){
						$(dom).bind("click", function(){
							 var curr_index = $(this).index();
							$(".s_line").css("left", (curr_index*33+5)+"%");
							$.each(tabs, function(i, d){
								if($(d).hasClass("selected")){
									$(d).removeClass("selected");	
								}	
							});

							$.each(contents, function(index, dom){
								$(dom).hide();
							});
							$(contents[curr_index]).show();
							$(this).addClass("selected");

						});	

					});

				}

			};	

			main.init();

		});
	</script>

</html>