// JavaScript Document
var pageNum = 1;
var containerId = '#tabsContainer';
var tabsId = '#tabs';
var ActId = '';
var wechatLoaded= false;
//var para = getPara();
ActId = getIdFromURL();
/**
 *切换添加滚动分页
 * */
var loadTabScrollHandler = function (e) {
    e.preventDefault();
    var winH = $(window).height();
    var pageH = $(document.body).height();
    var scrollT = $(window).scrollTop();
    var aa = (pageH - winH - scrollT) / winH;
    if (aa < 0.02) {
        var eq = $(".current").index();
        var text = $.trim( $("#allSortBox .select").text() );
        switch(eq){
            case 1 : loadTabNextPage(0,text,++pageNum);
                break;
            case 2 :
                if($(".current span").hasClass('other')){
                    loadTabNextPage(2,text,++pageNum);
                }else{
                    loadTabNextPage(1,text,++pageNum);
                }
                break;
            case 3 :
                if($(".current span").hasClass('other')){
                    loadTabNextPage(4,text,++pageNum);
                }else{
                    loadTabNextPage(3,text,++pageNum);
                }
                break;
        }
    };
};

var bt = {};
/**
 * 滚动条事件
 * */
var scrollHandler = function(){
   // $("#allSortBox").addClass("hidden");
   // $("#bgBox").addClass("hidden");
    //导航
    var scrollT = $(window).scrollTop();
    var st = bt.scrollT;
    bt.scrollT = scrollT;
    if(st < bt.scrollT){
        $('.listNav').addClass('hidden');
    }else if(st > bt.scrollT){
        $('.listNav').removeClass('hidden');
    };
    //top
    if( $(window).scrollTop() > 0){
        $(".scrollTop").show();
    }else if( $(window).scrollTop() == 0){
        $(".scrollTop").hide();
    };
    lazyload({defObj : "#index"});
}
$(window).bind('scroll',scrollHandler);

$(function(){
    /**
     * 页面加载
     * */
    loadSortList(0,'',1);
    loadTab(0,'',1);
	window.setTimeout(scrollTo, 0, 0, 0);
	/*
	* 切换风格
	*/	
	if(Xiu.chkLocalStorage()){	
		var icoCls = 'two-col-ico',
			listCls = 'two-col';
			geticoCls = localStorage.getItem('pageIco'),
			getList = localStorage.getItem('pageList');
			
			if( geticoCls == 'one-col-ico' ){
				$('#pageList').removeClass(listCls);
				$('.switch-list .icons').removeClass(icoCls).addClass(geticoCls)
			}			
	}	
	$('.switch-list').click(function(){
		var $icons = $(this).find('.icons');
			if($icons.is('.one-col-ico')){
				
				$icons.removeClass('one-col-ico').addClass('two-col-ico');
				$('#pageList').addClass('two-col');
				//本地存储
				if(Xiu.chkLocalStorage()){
					localStorage.setItem("pageList", 'two-col');   
					localStorage.setItem("pageIco", 'two-col-ico');  
				}				
			}else{
				$icons.removeClass('two-col-ico').addClass('one-col-ico');
				$('#pageList').removeClass('two-col');
				//本地存储
				if(Xiu.chkLocalStorage()){
					localStorage.setItem("pageList", '');   
					localStorage.setItem("pageIco", 'one-col-ico');  
				}
			}
	})
    /**
     * tab分类切换
     * */
	$("#tabs > li").click(function(e){
		e.preventDefault();
		$(window).scrollTop(0);
        var text = $.trim( $("#allSortBox .select").text() );
        if($(this).is('.jsAll')){			
			if($('#allSortBox').is('.hidden')){
				$("#allSortBox").removeClass("hidden");
         		$("#bgBox").removeClass('hidden');
			}else{
				$("#allSortBox").addClass("hidden");
          	 	$("#bgBox").addClass('hidden');
			}           
        }else{ 
			$("#allSortBox").addClass("hidden");
          	$("#bgBox").addClass('hidden');
            var tg = e.target;
            if($(this).hasClass('current')){
                $(this).children().toggleClass('other');
                if(tg.id == 'price' || tg.id == 'pSpan' || tg.id == 'pEm' ){
                    //价格;
                    if($(this).children().hasClass("other")){
                        loadTab(2,text,1);
                        pageNum = 1;
                    }else{
                        loadTab(1,text,1);
                        pageNum = 1;
                    }
                }else if(tg.id == 'Buckle' || tg.id == 'bSpan' || tg.id == 'bEm'){
                    //拆扣;
                    if($(this).children().hasClass("other")){
                        loadTab(4,text,1);
                        pageNum = 1;
                    }else{
                        loadTab(3,text,1);
                        pageNum = 1;
                    }
                };
            }else{
                if(tg.id == 'price' || tg.id == 'pSpan' || tg.id == 'pEm' ){
                    //价格;
                    if($(this).children().hasClass("other")){
                        loadTab(2,text,1);
                        pageNum = 1;
                    }else{
                        loadTab(1,text,1);
                        pageNum = 1;
                    }
                }else if(tg.id == 'Buckle' || tg.id == 'bSpan' || tg.id == 'bEm'){
                    //拆扣;
                    if($(this).children().hasClass("other")){
                        loadTab(4,text,1);
                        pageNum = 1;
                    }else{
                        loadTab(3,text,1);
                        pageNum = 1;
                    }
                };
            }
            $(this).addClass('current').siblings().removeClass("current").children().removeClass("other");
            var eq = $(".current").index();
            if(eq == 0){
                return false;
            }else if(eq == 1){
                loadTab(0,text,1);
                pageNum = 1;
            };
        }
	});
});

/**
 * 获取URL参数
 * */
var getPara = function(){
	var obj = {},
		str = window.location.search.replace("?","");

	var arr = str.split("&");
	if(arr.length > 0){
		for(var i = 0,l=arr.length;i<l;i++){
			try{
				var t = arr[i].split("=");
				obj[t[0]] = t[1];
			}catch(e){}
		}
	}
	return obj;
};

/*
* 加裁分类列表
* **/
function loadSortList(or,text,pageNum){
    var order = or;
    var text = text?text:'全部';
    var pageNum = pageNum?pageNum:1;
    var link = "" ;
    link = "/cx/getTopicGoodsList.shtml?queryAVo.activityId="+ ActId +"&queryAVo.subType=" + text + "&queryAVo.order=" + order + "&queryAVo.pageNum="+pageNum;
    Xiu.runAjax({
        url:link,
        beforeSend:function(){
            $('#loadding').html("<div class='loadBox'>"+
                "<span class='iconLoad disV'></span><span class='disV'>努力加载中</span>"+
                "</div>");
        },
        sucessCallBack :function(msg){
            var result = msg.result;
            if(result){
                var categorys = msg.categorys;
                var html = "<li><span class='select'>全部</span></li>";
				
				if( msg.categorys != undefined ){
					for(var i = 0; i<msg.categorys.length; i++){
						if(msg.categorys[i].category != undefined && msg.categorys[i].category != ''){
							 html += "<li><span>"+ msg.categorys[i].category +"</span></li>";
						}
					}
				}
               
                $("#allSortBox").html(html);
                $("#allSortBox li span").bind("click",function(e){
                    e.preventDefault();
                    e.stopPropagation();
                    $(this).addClass('select').parent()
                        .siblings().children('span').removeClass('select');
                    var text=$.trim($(this).text());
                    loadTab(0,text,1);
                    pageNum = 1;
                    $("#tabs .recommend").parent().addClass("current").siblings().removeClass("current");
                    $("#allSortBox").addClass('hidden');
                    $("#bgBox").addClass("hidden");
                });
                var h = $(window).height();
                $("#bgBox").height(h);
				$('#bgBox').on('touchstart', function(){
					 $("#allSortBox").addClass("hidden");
                     $("#bgBox").addClass("hidden");
				})
                /*$(document).bind("click", function (e) {
                    var target = e.target;
                    if(target.id != 'categorys' && target.id != 'allPro'){
                        $("#allSortBox").addClass("hidden");
                        $("#bgBox").addClass("hidden");
                    }
                });*/
            }
        },
        complete:function(){
            $('.loadBox').remove();
            $(window).unbind('scroll');
            $(window).bind('scroll',scrollHandler);
            $(window).bind('scroll',loadTabScrollHandler);
        }
    });
};
/*
 * 加裁切换类商品
 * **/
function loadTab(or,text,pageNum){
    $(containerId).show('fast');
	var order = or;
    var text = text?text:'全部';
    var pageNum = pageNum?pageNum:1;
	var link = "" ;
    link = "/cx/getTopicGoodsList.shtml?queryAVo.activityId="+ ActId +"&queryAVo.subType=" + text + "&queryAVo.order=" + order + "&queryAVo.pageNum="+pageNum;
	if(pageNum==1){
		$(containerId).html("");
	};
    Xiu.runAjax({
        url:link,
        beforeSend:function(){
			$('#loadding').html("<div class='loadBox'>"+
                   "<span class='iconLoad disV'></span><span class='disV'>努力加载中</span>"+
                   "</div>");
		},
		sucessCallBack :function(msg){
            var result = msg.result;
            if(result){
                var goodsList = msg.goodsList;
                var activityId = msg.activityId;
                var activityName = msg.activityName;
                $('#activeName').text(activityName);
                $('#keywords').attr("content",activityName);
                $('title').text(activityName + "-走秀网触屏版");
				//微信分享接口调用
				if( Xiu.isWechatBrowser() && !wechatLoaded)
				{
					var dataFriend =   {"img":null,"title": activityName + "专题卖场，超低折扣，限时限量，快来看看！"};
					var dataTimeline = {"img":null,"title":"走秀网" + activityName + "专题卖场，超低折扣，限时限量，快来看看！"};
					wechatShare(dataFriend,dataTimeline);
					wechatLoaded = true;
				}
				if(goodsList.length == 0 || goodsList == ''){
						$(window).unbind('scroll');
                        $(window).bind('scroll',scrollHandler);
						if(msg.pageTotal == 0){
							Dialog.tip({txt:'卖场商品为空',autoHide: true,callBack: function(){
								location.href = 'http://m.xiu.com';
							}})	
						}
				}else{
					 inserData(goodsList,activityId,activityName);
				}
            }else{
				if(msg.pageTotal == 0){
					Dialog.tip({txt:msg.errorMsg,autoHide: true,callBack: function(){
						location.href = 'http://m.xiu.com';
					}})	
				}
			}
        },
		complete:function(){
			$('.loadBox').remove();
            $(window).unbind('scroll');
            $(window).bind('scroll',scrollHandler);
            $(window).bind('scroll',loadTabScrollHandler);
		}
    });
};

/*
 * 设置www的具体页面
 * **/
function setGoToWWW(){
            addM2WWWSessionCookie();
            $('#goto_www').attr( "href", "http://www.xiu.com/cx/"+ ActId + ".shtml");
};

/**
 *切换滚动分页
 * */
function loadTabNextPage(or,text,pageNum){
    var order = or;
    var text = text?text:'全部';
    var pageNum = pageNum?pageNum:1;
    var link = "" ;
    link = "/cx/getTopicGoodsList.shtml?queryAVo.activityId="+ ActId +"&queryAVo.subType=" + text + "&queryAVo.order=" + order + "&queryAVo.pageNum="+pageNum;	
    Xiu.runAjax({
        url:link,
        beforeSend:function(){
            $(containerId).append("<div class='loadBox'>"+
                "<span class='iconLoad disV'></span><span class='disV'>努力加载中</span>"+
                "</div>");
        },
        sucessCallBack :function(msg){
            var result = msg.result;
            if(result){				
				if( msg.goodsList.length == 0 || msg.goodsList == null ){
					   $(window).unbind('scroll');
                       $(window).bind('scroll',scrollHandler);
					   if(msg.pageTotal == 0){
							Dialog.tip({txt:'卖场商品为空',autoHide: true,callBack: function(){
								location.href = 'http://m.xiu.com';
							}})	
						}
				}else{
					inserData(msg.goodsList,msg.activityId);
				}			
            }else{
				if(msg.pageTotal == 0){
					Dialog.tip({txt:msg.errorMsg,autoHide: true,callBack: function(){
						location.href = 'http://m.xiu.com';
					}})	
				}
			}           
        },
        complete:function(){
            $('.loadBox').remove();
        }
    });
}
/*
 * 插入数据html
 * **/
function inserData(goodsList,activityId,activityName){
    var ActId = activityId;
    var actName = activityName;
	for(i in goodsList){
		var gl = goodsList[i];
        if(gl){          
			$(containerId).append(setHtm(gl));
        }
	}
	function setHtm(gl){
		var htm = '',
			sell_out = '';
			buyBtn =  '<a href="http://m.xiu.com/product/' + gl.goodsId +'.html?actId='+ activityId+ '" class="buyBtn fr">立即购买</a>';
		if(gl.stateOnsale == 0){
			sell_out='<a href="http://m.xiu.com/product/' + gl.goodsId +'.html?actId='+ activityId+  '" class="shouqin"></a>';
			buyBtn = '<span class="buyBtn gry-btn fr">立即购买</span>';
		}
		
		htm ='<li><div class="picBox clearfix">'
             +'  <a href="http://m.xiu.com/product/' + gl.goodsId +'.html?actId='+ activityId +'" class="activity"><img src="http://m.xiu.com/static/css/img/default.315_420.jpg" src3="' + gl.goodsImg + '" onerror="this.src=http://m.xiu.com/static/css/img/default.315_420.jpg" style="width:100%;" alt="'+gl.goodsName+'"/></a>'
             +'   <div class="actText clearfix">'
             +'       <div class="line clearfix">'
             +'           <div class="leftPart">'
             +'               <h2 class="productName"><a href="http://m.xiu.com/product/' + gl.goodsId +'.html?actId='+ activityId +'">' + gl.goodsName + '</a></h2>'
             +'               <p class="clearfix">'             
             +'                  <span class="priceNow"><i>&yen;</i> ' + gl.zsPrice + '</span>'
             +'                   <span class="original"><i>&yen;</i> <em class="g-price">' + gl.price + '</em></span>'
			 +'                  <span class="discount"><i class="fncolor"> ' + gl.discount + '</i>折</span>'
             +'              </p>'
             +'         	</div>'
             +'    '+buyBtn+'      '
             +'      </div>'
			 +'	</div>'
             +'	<div class="actTextBG"></div>'
			 +''+sell_out+''
			 +'</div></li>';
			 return htm;
	}
    lazyload({defObj : "#index"});
}