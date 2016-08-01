/*
* @Author : Qin fengmu
* @date   : 2014-6-6 
*/
/*cx 促销卖场， discover 发现， brand 品牌*/
$(function(){		   
var homePage = {
	cxUrl : '/cx/getTopics.shtml?queryTopicsVo.dataType=4',	
	discoverUrl : '/findGoods/getFindGoodsList.shtml',
	brandUrl : '/wellChosenBrand/getwellChosenBrandList.shtml',
	isLoadding : true,
	firstLoadCxPage : true,
	firstLoadDiscoverPage : true,
	firstLoadBrandPage : true,
	nextPageLoadding : false,
	letterScroll : '',
	cxTotalPage : 0,
	discoverTotalPage : 0,
	cxPageNum : 1,
	discoverPageNum : 1,
	scrollT : 0,
	init : function(){
		//默认加载推荐列表第一页数据
		this.setPageType(0);
		this.loadDataList({type:0,pageNum: 1});
		this.navTab();		
	},	
	//绑定滚动事件
	winScroll : function(c){	
		var $thisObj = this;
		$(window).scroll(function(){			
			var winHeight = $(document.body).height(),
				winScrollTop = $(window).scrollTop(),
				pageType = parseInt($thisObj.getPageType()),
				scrolltop = $thisObj.scrollT,
				pageNum;
				$thisObj.scrollT = winScrollTop;
		//	console.log(winHeight+','+winScrollTop+','+winScrollTop/winHeight);
			if(pageType == 0){
				pageNum = $thisObj.cxPageNum;
			}else if(pageType == 1){
				pageNum = $thisObj.discoverPageNum;
			}
			
			if((winScrollTop/winHeight) > 0.8 && !$thisObj.nextPageLoadding ){			
				if(pageType == 0){
					$thisObj.loadCxNextPage(pageNum);
				}else if( pageType == 1){
					$thisObj.loadDisNextPage(pageNum);	
				}else{
					//console.log(pageType)
				}					
			}			
			
			if(scrolltop < $thisObj.scrollT &&  $thisObj.scrollT > 50 ){
				$('.indexNav').hide();
			}else if(scrolltop > $thisObj.scrollT){
				$('.indexNav').show();
			};
			//top
			if( winScrollTop > 0){
				$(".scrollTop").show();
			}else if( winScrollTop == 0){
				$(".scrollTop").hide();
			};
			
		})
	},
	loadDataList : function(op){
		var $thisObj = this;			
		switch(op.type){
			case 0 : 
				Xiu.runAjax({
		       		url:$thisObj.cxUrl+ "&queryTopicsVo.pageNum="+op.pageNum,
					beforeSend: function(){
						$thisObj.isLoadding = true;
					},
					sucessCallBack :function(msg){	        
				        if(msg.result && msg.topicList != ''){
							if($thisObj.firstLoadCxPage){				
								$thisObj.firstLoadCxPage = false;
								$thisObj.isLoadding = false;
								//插入html
								$('#cxPage').html($thisObj.setTpl(msg.topicList,0));
								//绑定滚动事件
								$thisObj.winScroll();								
								//图片懒加载
								lazyload({defObj : "#index"});
								//获取促销总分页
								$thisObj.cxTotalPage = msg.totalPage;
							}
						}else{
							if( msg.topicList == ''){
								Dialog.tip({txt:'推荐列表为空',autoHide: true});
							}else{
								Dialog.tip({txt:msg.errorMsg,autoHide: true});
							}
							
						}
			        },
					complete : function(){
						$thisObj.isLoadding = false;
					}
			    });
				
			break;
			case 1 :				
				Xiu.runAjax({
		       		url:$thisObj.discoverUrl+'?pageNum='+op.pageNum,	
					beforeSend: function(){
						$thisObj.isLoadding = true;
					},
					sucessCallBack :function(msg){	        
				        if(msg.result && msg.findGoodsList != ''){
							if($thisObj.firstLoadDiscoverPage){				
								$thisObj.firstLoadDiscoverPage = false;
								$thisObj.isLoadding = false;
								//插入html
								$('#discoverPage').html($thisObj.setTpl(msg.findGoodsList,1));
								//绑定滚动事件
								$thisObj.winScroll();								
								//图片懒加载
								lazyload({defObj : "#index"});
								//获取发现总分页
								$thisObj.discoverTotalPage = msg.totalPage;
								//绑定点喜欢
								$thisObj.addFavor();
							}
						}else{
							if( msg.findGoodsList == ''){
								Dialog.tip({txt:'发现列表为空',autoHide: true});
							}else{
								Dialog.tip({txt:msg.errorMsg,autoHide: true});
							}
						}
			        },
					complete : function(){
						$thisObj.isLoadding = false;
					}
			    });
			break;
			case 2 :
				
				Xiu.runAjax({
		       		url:$thisObj.brandUrl,
					beforeSend: function(){
						$thisObj.isLoadding = true;
					},
					sucessCallBack :function(msg){	        
				        if(msg.result && msg.wellChosenBrandList != ''){
							if($thisObj.firstLoadBrandPage){				
								$thisObj.firstLoadBrandPage = false;								
								//插入html
								$('#brandwrap').html($thisObj.setTpl(msg.wellChosenBrandList,2));
								$thisObj.createBrandScroll();
								$thisObj.letterBar();
							}
						}else{
							if( msg.wellChosenBrandList == ''){
								Dialog.tip({txt:'品牌列表为空',autoHide: true});
							}else{
								Dialog.tip({txt:msg.errorMsg,autoHide: true});
							}
						}
			        },
					complete : function(){
						$thisObj.isLoadding = false;
					}
			    });
			break;
		
		}			
	},
	//设置模板
	setTpl : function(dataList,pageType){
		var	tpl = '',
			$thisObj = this;
			
		switch(pageType){
			case 0 : 			
			$.each(dataList,function(index,data){
				tpl +="<div class='picBox'>"
					+"<a href='http://m.xiu.com/cx/" + data.activity_id +".html ' class='activity'>"
					+"	<img src='http://m.xiu.com/static/css/img/proImg.jpg' src3="+ data.mobile_pic + " width='100%' onerror='this.style.opacity=0;' alt='"+data.name+"' />"
					+"</a>"
					+"<div class='actText clearfix'>" 
					+"	<h2 class='line'>"
					+"		<span class='actName textOverflow'> " + data.name + "</span><span class='actTime textOverflow'>" + $thisObj.setCxDate(data.start_time) + " - " + $thisObj.setCxDate(data.end_time)  +"</span>"
					+"	</h2>"
					+"</div>"
					+"	<div class='actTextBG'>"
					+"	</div>"
					+"</div>";
			})		
			break;
			case 1 : 
				$.each(dataList,function(index,data){
					var  isfav = data.isLove == "Y" ? 'has-fav' :''
					tpl+='<li class="itm clearfix">'
						+'<div class="l">'
						+'	<a href="http://m.xiu.com/product/'+data.goodsId+'.html"><img src3="'+data.goodsImg+'" src="../static/css/img/default.315_420.jpg"  alt="'+data.title+'"></a>'
						+'</div>'
						+'<div class="r">'
						+'	<h2 class="g-name"><a href="http://m.xiu.com/product/'+data.goodsId+'.html">'+data.title+'</a></h2>'
						+'	<p class="g-des">'+data.content+'</p>'
						+'	<p><span class="g-price">￥'+data.goodsPrice+'</span></p>'
						+'	<span data-goodssn="'+data.goodsSn+'" data-num="'+data.loveGoodsCnt+'" class="fav-btn '+isfav+'"><em>'+data.loveGoodsCnt+'</em>人喜欢</span>'
						+'</div>'
						+'</li>';
				})
				tpl = '<div class="discoverArea"><ul class="discoverList">'+tpl+'</ul></div>';
			break;
			case 2 : 
				$.each(dataList,function(index,data){					
					if(data.list!=''){
						var li='',
							letter_name = data.name.toLowerCase();
						$.each(data.list, function(i,d){
							li+='<li>'
								+'<a href="http://mbrand.xiu.com/brand/goodlist/'+d.brandId+'">	<img src="'+d.brandImg+'" alt="'+d.brandName+'"></a>'
								+'	<h2 class="brand-name">'+d.brandName+'</h2>'
								+'</li>';
						});
						if(letter_name == '1-9'){
							letter_name = 'num';
						}
						tpl+='<div id="letter-'+letter_name+'" class="brand-itm">'
							+'	<p  class="letter-index">'+data.name+'</p>'
							+'	<div class="brand-area">		'		
							+'		<ul class="brand-list clearfix">'
							+''+li+''			
							+'		</ul>'
							+'	</div>'
							+'</div>';
					}					
				})	
				tpl = '<ul class="brandList">'+tpl+'</ul>';
			break;	
			
		}
		return tpl;
	},
	//加载促销下一页
	loadCxNextPage : function(pageNum){
		var $thisObj = this;
		$thisObj.nextPageLoadding = true;				
		pageNum++;							
		// 如果页数大于总页数则不在加载数据
		if( pageNum <= $thisObj.cxTotalPage ){
			$thisObj.cxPageNum = pageNum;			
			Xiu.runAjax({
				url : $thisObj.cxUrl+ "&queryTopicsVo.pageNum=" +pageNum,
				async : false,
				beforeSend : function(){
					$thisObj.isLoadding = true;
					$('.main').append('<div class="nextPageLoading"><div class="loadding"><img src="../static/css/img/load.gif" alt="正在加载..."></div></div>');
				},
				sucessCallBack : function( msg ){
					if(msg.result){
						$('#cxPage').append($thisObj.setTpl(msg.topicList,0));
						$thisObj.nextPageLoadding = false;							
						lazyload({defObj : "#index"});
					}
					
				},
				complete : function(){
					$thisObj.isLoadding = false;
					$('.nextPageLoading').remove();
				}
			})
		}
	},
	//加载发现下一页
	loadDisNextPage : function(pageNum){
		var $thisObj = this;
		$thisObj.nextPageLoadding = true;				
		pageNum++;							
		// 如果页数大于总页数则不在加载数据
		if( pageNum <= $thisObj.discoverTotalPage ){
			$thisObj.discoverTotalPage = pageNum;
			Xiu.runAjax({
				url : $thisObj.discoverUrl+'&pageNum='+pageNum,
				async : false,
				beforeSend : function(){
					$thisObj.isLoadding = true;
					$('.main').append('<div class="nextPageLoading"><div class="loadding"><img src="../static/css/img/load.gif" alt="正在加载..."></div></div>');
				},
				sucessCallBack : function( msg ){
					if(msg.result){
						$('#discoverPage').append($thisObj.setTpl(msg.findGoodsList,1));
						$thisObj.nextPageLoadding = false;						
						lazyload({defObj : "#index"});
						$('.fav-btn').unbind('click');
						//绑定点喜欢
						$thisObj.addFavor();
					}					
				},
				complete : function(){
					$thisObj.isLoadding = false;
					$('.nextPageLoading').remove();
				}
			})
		}
	},
	//导航标签切换
	navTab : function(){
		var $thisObj = this,
			startX,startY,endX,endY;
		$('#tabs li').click(function(){					
			if(!$thisObj.isLoadding){
				var index = $('#tabs li').index($(this));	
				$(this).siblings('li').removeClass('current');
				$(this).addClass('current');
				$('.main .tab-bd').hide().eq(index).show();
				$thisObj.setPageType(index);
				
				//第一次点击 加载发现和品牌内容。
				if(index == 1){
					if($thisObj.firstLoadDiscoverPage){
						$thisObj.loadDataList({type:index,pageNum: 1});
					}
				}else if(index == 2){
					if($thisObj.firstLoadBrandPage){
						$thisObj.loadDataList({type:index,pageNum: 1});						
					}
				}else{
					//console.log($thisObj.firstLoadCxPage)
				}
			}			
		})

	},
	addFavor : function(){
		$('.fav-btn').click(function(){
			var $t = $(this),
				goodsSn = $t.data('goodssn'),
				num = Number($t.data('num'));
			if($t.is('.has-fav')){ 
				Xiu.runAjax({
					url :'/findGoods/delLovedTheGoods.shtml?terminal=3&goodsSn='+goodsSn,
					sucessCallBack : function(msg){
						if(msg.result){
							$t.find('em').html(num-1);
							$t.data('num',num-1);
							$t.removeClass('has-fav ico-scale');
						}else{
							if(msg.errorCode == 4001){
								Xiu.GobackUrl.ToLogin(location.href);
							}else{
								Dialog.tip({txt:msg.errorMsg,autoHide: true});
							}
						}
					}
				})
			
			
			}else{
				
				Xiu.runAjax({
					url :'/findGoods/addLovedTheGoods.shtml?terminal=3&goodsSn='+goodsSn,
					sucessCallBack : function(msg){
						if(msg.result){
							$t.find('em').html(num+1);
							$t.data('num',num+1);
							$t.addClass('has-fav ico-scale');
						}else{
							if(msg.errorCode == 4001){
								Xiu.GobackUrl.ToLogin(location.href);
							}else{
								Dialog.tip({txt:msg.errorMsg,autoHide: true});
							}
						}
					}
				})			
			}
			
		})
	},
	createBrandScroll : function(){
		var _this = this;
		var dh = $(window).height()-99,
			sh = $('#brandwrap').height();	
			
			$('#brandwrap').height(dh)
			_this.letterScroll = new IScroll("#brandwrap",{
				hideScrollbar: true,
				fadeScrollbar : true
			});				
			
	},	
	letterBar : function(){
		var _this = this;
		$('.ui-letter').on('touchstart',function(e){
			e.preventDefault();
			var touch = event.targetTouches[0],
				pageY = touch.pageY;
				$(this).addClass('bg');	
				letterScroll(pageY)
		})
		$('.ui-letter').on('touchend',function(e){
			$(this).removeClass('bg');
			$('.show-letter').hide();
		})
		$('.ui-letter').on('touchmove',function(e){
			e.preventDefault();
			e.preventDefault();
			var touch = event.targetTouches[0],
				pageY = touch.pageY;
				letterScroll(pageY)
		})		
		function showletter(letter){	
			var objstr = 'letter-'+letter;
			if($('#'+objstr).length>0){				
				_this.letterScroll.scrollToElement(document.getElementById(objstr),0);
			}
			if(letter == 'num'){
				$('.show-letter').html('#').show();	
			}else{
				$('.show-letter').html(letter.toUpperCase()).show();		
			}
			
		}
		
		function letterScroll(pos){
			if(pos >=103 && pos <= 107){
				showletter('a');
			}else if(pos >107 && pos <= 121){
				showletter('b');
			}else if(pos >121 && pos <= 135){
				showletter('c');
			}else if(pos >135 && pos <= 149){
				showletter('d');
			}else if(pos >149 && pos <= 163){
				showletter('e');
			}else if(pos >163 && pos <= 177){
				showletter('f');
			}else if(pos >177 && pos <= 191){
				showletter('g');
			}else if(pos >191 && pos <= 205){
				showletter('h');
			}else if(pos >205 && pos <= 219){
				showletter('i');
			}else if(pos >219 && pos <= 233){
				showletter('j');
			}else if(pos >233 && pos <= 247){
				showletter('k');
			}else if(pos >247 && pos <= 261){
				showletter('l');
			}else if(pos >261 && pos <= 275){
				showletter('m');
			}else if(pos >275 && pos <= 289){
				showletter('n');
			}else if(pos >289 && pos <= 303){
				showletter('o');
			}else if(pos >303 && pos <= 317){
				showletter('q');
			}else if(pos >317 && pos <= 331){
				showletter('r');
			}else if(pos >331 && pos <= 345){
				showletter('s');
			}else if(pos >345 && pos <= 359){
				showletter('t');
			}else if(pos >359 && pos <= 373){
				showletter('u');
			}else if(pos >373 && pos <= 387){
				showletter('v');
			}else if(pos >387 && pos <= 401){
				showletter('w');
			}else if(pos >401 && pos <= 415){
				showletter('x');
			}else if(pos >415 && pos <= 429){
				showletter('y');
			}else if(pos >429 && pos <= 443){
				showletter('z');
			}else if(pos >443 && pos <= 458){
				showletter('num');
			}
		}
	},
	setCxDate : function(newDate){
		var date = new Date(newDate);	
		return (date.getMonth()+1)+ "月" + date.getDate() +"日";
	},
	setPageType : function(val){
		$('#pageType').val(val);
	},
	getPageType : function(){
		return $('#pageType').val();
	}
	
};
homePage.init();	
if(Xiu.isWechatBrowser()){
	//微信分享
	var dataFriend = {"img":null,"title":"走秀网今日精选-最实惠的促销商品女装、男装、鞋靴、配饰、运动、化妆品、包包、奢侈品、手表等上千款精品,只卖让你疯狂的价格"};
	var dataTimeline = {"img":null,"title":"走秀网今日精选-最实惠的促销商品女装、男装、鞋靴、配饰、运动、化妆品、包包、奢侈品、手表等上千款精品,只卖让你疯狂的价格"};
	wechatShare(dataFriend,dataTimeline);
}
//显示下载app
var showAppDownLoadTip = function(){
var userAgentInfo = navigator.userAgent,
	track_dl = '',
	android_dl_url = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xiu.app&g_f=994054',
	ios_dl_url ='https://itunes.apple.com/cn/app/zou-xiu-wang/id512915857?mt=8' ;

if(userAgentInfo.indexOf('Android') > 0 ){	
	$('.dl-btn').attr('href',android_dl_url);
	track_dl='Android下载';
	showAppDl();
}else if(userAgentInfo.indexOf('iPhone') > 0){		
	$('.dl-btn').attr('href',ios_dl_url);	
	track_dl='iPhone下载'
	showAppDl();
}	
function showAppDl(){	
	var closeAppDl = sessionStorage.getItem('closeAppDl') || false,
		hideAppDl =  localStorage.getItem('hideAppDl') || false;			
	if(!closeAppDl && !hideAppDl){
		$('.down-app').show();			
	}		
}	

$('.down-app-close').click(function(){	
	if(window.sessionStorage){
		sessionStorage.setItem('closeAppDl',true);
	}
	$('.down-app').hide();								   
})

$('.dl-btn').click(function(){
	if(Xiu.chkLocalStorage()){
		localStorage.setItem('hideAppDl',true);
	}
	//事件跟踪统计
	_gaq.push(['_trackEvent', 'app下载', '点击下载', track_dl, '', 'true']);
	$('.down-app').hide();
})

}()
})	



/*
 * 设置www的具体页面
 * **/
function setGoToWWW(){
            addM2WWWSessionCookie();
            $('#goto_www').attr( "href", "http://www.xiu.com");
}
	