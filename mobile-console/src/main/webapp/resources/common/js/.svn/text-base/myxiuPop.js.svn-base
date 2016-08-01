/*++++++++++++++++++++++++
+ 
+ FileName : xiupop.js
+ xiu.com 公用弹出层 ,xiuTips 
+ Anson.chen@xiu.com
+ v0.8
+ 2011-9-07
+ 

例：

显示弹出窗口
///----------------
$.xiupop({
	 title:'放入购物车成功',//标题，可以不写
	 time:0,//自动关闭时间，单位秒
	 width:'300px',//宽度
	 height:'300px',//高度
	 content:'购物袋<em>10</em>件商品，合计<b  id="wndCartPrice">100</b>',//内容,支持html,也支持jqery,比如显示id的内容,$('#id')
	 skin:'',//皮肤样式
	 hasBg:false,//是否有背影
	 center:false,//是否整屏幕居中,设置之后pageX,pageY将无效
	 scrollClose:true,//居中时滚动是否关闭
	 contentCss:'',//内容区域可添加样式
	 pageX:offset.left-6,//left坐标,数字
	 pageY:offset.top-3,//top坐标，数字
	 button:{ //按钮,可以不写，注意格式
		 
		'查看购物袋': function(t) {//t代表按钮本身
			
			//jumpToShoppingBag(data.orderId,url['parm'].storeId,url['parm'].catalogId);
			},
		'关闭': function(t) {
			$.closepop();//关闭弹出层
			}
	
	 },
	 callback:false//回调
	 })

///----------------
显示气泡提示:
	showTipDiv('内容',20,10,2)//提示内容,left,top,自动关闭时间(单位：秒,默认:3秒); 
关闭气泡提示:
	hideTipDiv()

///----------------

显示多内容提示:

$(obj).XTip({
			width:250 //提示框的宽度可省略，默认为250
			})
关闭多内容提示:(hover自动关闭)
$('#XT').remove()

++++++++++++++++++++++++*/
(function($){$.closepop=function(){if($('#xiuPopBox').data('remove'))clearTimeout($('#xiuPopBox').data('remove'));if($('body').data('showXiuPop'))clearTimeout($('body').data('showXiuPop'));if(document.getElementById('xiuPopCopy')){$('#xiuPopBox').data('xiuPopCon').replaceAll('#xiuPopCopy')}if(document.getElementById('xiuPopBoxBG'))$('#xiuPopBoxBG').remove();if(document.getElementById('xiuPopBox'))$('#xiuPopBox').remove();$(window).unbind('scroll')};$.xiupop=function(settings){var self=this;self.close=function(){$.closepop()};var options=$.extend({time:0,pageX:0,pageY:0,title:false,skin:'',hasBg:false,contentCss:'',center:false,width:false,height:false,content:'',scrollClose:false,callback:false,button:false},settings),html=[],i=0,width=options.width?'width:'+options.width+';':'',height=options.height?'height:'+options.height+';':'';self.close();html[i++]='<div id="xiuPopBox" class="xiuPop default '+options.skin+'" style="position:absolute; z-index:9999px; display:none;'+width+height+' left:'+options.pageX+'px; top:'+options.pageY+'px">';if(options.title)html[i++]='<h1><a href="#" title="关闭" id="closeXiuPop">关闭</a>'+options.title+'</h1>';html[i++]='<div class="content '+options.contentCss+'" id="xiuPopContent">'+options.content+'</div>';if(options.button){html[i++]='<div class="buttonZone" id="xiuPopBoxBtn">';html[i++]='</div>'}html[i++]='</div>';if(options.hasBg){var Elementheight=document.documentElement.scrollHeight;html[i++]='<div class="xiuPopBG" id="xiuPopBoxBG" style="height:'+Elementheight+'px"></div>'}html=html.join('');$('body').append(html);if(typeof(options.content)=='object'){options.content.parent().append('<span id="xiuPopCopy"><!--  xiuPopCopy By Anson--></span>');$('#xiuPopContent').empty().append(options.content);$('#xiuPopBox').data('xiuPopCon',options.content)}$('#closeXiuPop').click(function(){self.close();return false});$.each(options.button,function(id,func){$('<button>'+id+'</button>').unbind('click').bind('click',function(e){func(this)}).appendTo(document.getElementById('xiuPopBoxBtn'))});if(options.callback){callback=options.callback||function(){};callback()}if(options.center){var E=$('#xiuPopBox'),elWidth=E.width(),elHeight=E.height(),winWidth=$(window).width(),winHeight=$(window).height(),bdTop=$(window).scrollTop();E.css({"top":(winHeight/2)-(elHeight/2)+bdTop,"left":(winWidth/2)-(elWidth/2)});$('body').data('sTop',bdTop);if(options.scrollClose){$(window).bind('scroll',function(){if(Math.abs($('body').data('sTop')-$(window).scrollTop())>100)$.closepop()})}}$('body').data('showXiuPop',setTimeout(function(){$('#xiuPopBox').show();if(options.time!=0){$('#xiuPopBox').data('remove',setTimeout(function(){self.close()},options.time*1000))}},100));$(document).unbind("keydown").bind("keydown",function(event){if(event.keyCode==27){$.closepop();return false}})}})(jQuery);
//提示层
function xiuTips(content,x,y,time){return showTipDiv(content,x,y,time)}function hideTipDiv(){if($('#xiuTipBox').data('remove'))clearTimeout($('#xiuTipBox').data('remove'));if($('body').data('showTip'))clearTimeout($('body').data('showTip'));if(document.getElementById('xiuTipBox'))$('#xiuTipBox').remove()}function showTipDiv(content,x,y,time){hideTipDiv();content=content?content:'';x=x?parseInt(x):0;y=y?parseInt(y):0;time=time?time*1000:3000;var html='';html+='<div class="popupDiv" id="xiuTipBox" style="position:absolute; display:none; left:'+x+'px; top:'+y+'px"><div class="M_infoTop"></div><div class="M_infocontents">';html+=content;html+='</div></div>';$('body').append(html);var xiuTipBoxDiv=$('#xiuTipBox');xiuTipBoxDiv.hover(function(){if($(this).data('remove'))clearTimeout($(this).data('remove'))},function(){$(this).data('remove',setTimeout(function(){hideTipDiv()},time))});$('body').data('showTip',setTimeout(function(){
	if($.fn.bgIframe){xiuTipBoxDiv.bgIframe();}
	xiuTipBoxDiv.show().data('remove',setTimeout(function(){hideTipDiv()},time))},100))}


//XTip 多内容提示
function getAbsoluteLeft(obj){o=obj;oLeft=o.offsetLeft;while(o.offsetParent!=null){oParent=o.offsetParent;oLeft+=oParent.offsetLeft;o=oParent}return oLeft}function getAbsoluteTop(obj){o=obj;oTop=o.offsetTop;while(o.offsetParent!=null){oParent=o.offsetParent;oTop+=oParent.offsetTop;o=oParent}return oTop}

(function($) {
	jQuery.fn.XTip = function(settings) {
		var selft = this;
		var options = $.extend({
			width: 250
		},
		settings);
		var de = document.documentElement,
		w = self.innerWidth || (de && de.clientWidth) || document.body.clientWidth,
		width = options.width;
		selft.hover(function() {
			var title = this.getAttribute('tit') || '&nbsp;',
			content = this.getAttribute('con'),
			hasArea = w - getAbsoluteLeft(this),
			clickElementy = getAbsoluteTop(this) - 3;
			if (hasArea > ((width * 1) + 75)) {
				$("body").append("<div id='XT' class='shadow_XTR' style='width:" + width * 1 + "px'><div id='XT_arrow_left'></div><div id='XT_close_left'>" + title + "</div><div id='XT_copy'><div class='XT_loader'><div></div></div>");
				var arrowOffset = this.offsetWidth + 11;
				var clickElementx = getAbsoluteLeft(this) + arrowOffset
			} else {
				$("body").append("<div id='XT' class='shadow_XTL' style='width:" + width * 1 + "px'><div id='XT_arrow_right' style='left:" + ((width * 1) + 1) + "px'></div><div id='XT_close_right'>" + title + "</div><div id='XT_copy'><div class='XT_loader'><div></div></div>");
				var clickElementx = getAbsoluteLeft(this) - ((width * 1) + 15)
			}
			$('#XT').css({
				left: clickElementx + "px",
				top: clickElementy + "px"
			}).show(); (document.getElementById(content)) ? $('#XT_copy').html($('#' + content).html()) : $('#XT_copy').html(content)
		},
		function() {
			$('#XT').remove()
		})
	}
})(jQuery);