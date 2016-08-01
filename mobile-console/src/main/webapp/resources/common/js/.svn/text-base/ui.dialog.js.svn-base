/*++++++++++++++++++++++++
+ 
+ FileName : ui.dialog.js
+ xiu.com 
+ Anson.chen@xiu.com
+ v1.0
+ 2012-4-25
+ 

+ 含：$.dialog / $.xiupop/ $.closeDialog / $.closepop;
easyPop


例：
easyPOP(title,content,bcallback,callback)
easyPOP('标题','内容','确定按钮回调','打开后回调')
///----------------
$.dialog({
	 title:'放入购物车成功',//标题，可以不写
	 time:0,//自动关闭时间，单位秒
	 width:'300px',//宽度
	 url:'http://www.xiu.com',//iframe url
	 height:'300px',//高度
	 content:'购物袋<em>10</em>件商品，合计<b  id="wndCartPrice">100</b>',//内容,支持html,也支持jqery,比如显示id的内容,$('#id')
	 skin:'',//皮肤样式
	 hasBg:false,//是否有背影
	 drag: false,//是否可拖动标题
	 center:false,//是否整屏幕居中,设置之后pageX,pageY将无效
	 scrollClose:false,//居中时滚动是否关闭
	 contentCss:'',//内容区域可添加样式
	 left:offset.left-6,//left坐标
	 top:offset.top-3,//top坐标
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

*/

$.closepop = function() {
			var doc = document,
				w = window,
				bx = $('#xiuPopBox');
			if (bx.data('remove')) clearTimeout(bx.data('remove'));
			if ($('body').data('showXiuPop')) clearTimeout($('body').data('showXiuPop'));
			if (doc.getElementById('xiuPopCopy')) {
				bx.data('xiuPopCon').replaceAll('#xiuPopCopy')
			}
			if (doc.getElementById('xiuPopBoxBG')) $('#xiuPopBoxBG').hide().remove();
			if (doc.getElementById('xiuPopBox')) bx.hide().remove();
			$(w).unbind('scroll.xiupop').unbind("resize.xiupop");
			$(doc).unbind("keydown.xiupop");
		};
//提示层
function xiuTips(content,x,y,time){return showTipDiv(content,x,y,time)}function hideTipDiv(){if($('#xiuTipBox').data('remove'))clearTimeout($('#xiuTipBox').data('remove'));if($('body').data('showTip'))clearTimeout($('body').data('showTip'));if(document.getElementById('xiuTipBox'))$('#xiuTipBox').remove()}function showTipDiv(content,x,y,time){hideTipDiv();content=content?content:'';x=x?parseInt(x):0;y=y?parseInt(y):0;time=time?time*1000:3000;var html='';html+='<div class="popupDiv" id="xiuTipBox" style="position:absolute; display:none; left:'+x+'px; top:'+y+'px"><div class="M_infoTop"></div><div class="M_infocontents">';html+=content;html+='</div></div>';$('body').append(html);var xiuTipBoxDiv=$('#xiuTipBox');xiuTipBoxDiv.hover(function(){if($(this).data('remove'))clearTimeout($(this).data('remove'))},function(){$(this).data('remove',setTimeout(function(){hideTipDiv()},time))});$('body').data('showTip',setTimeout(function(){
    if($.fn.bgIframe){xiuTipBoxDiv.bgIframe();}
    xiuTipBoxDiv.show().data('remove',setTimeout(function(){hideTipDiv()},time))},100))}
$.xiupop = function(settings) {
	
		var self = this;
		var doc = document;
		var de = doc.documentElement;
		var w = window;
		self.close = function() {
			$.closepop()
		};
		
		var options = $.extend({
			time: 0,
			left: '0px',
			top: '0px',
			url:'',
			title: false,
			skin: '',
			hasBg: false,
			drag: false,
			contentCss: '',
			center: false,
			width: false,
			height: false,
			content: '',
			callback: false,
			closeText:'\u5173\u95ed',
			button: false
		},
		settings),
		//html = [],i = 0,
		box,h1,a,text,btn,
		fragment = doc.createDocumentFragment(),
		fixedIe = !$.browser.msie || $.browser.version > 6,
		width = options.width ? 'width:' + (options.width+'').replace('px','') + 'px;': '',
		height = options.height ? 'height:' + (options.height+'').replace('px','') + 'px;': '',
		left ='left:' + (options.left+'').replace('px','') + 'px;',
		top = 'top:' + (options.top+'').replace('px','') + 'px;';
		self.close();
		callback = options.callback || function() {};	
		
		$(doc).bind("keydown.xiupop",function(event) {
			if (event.keyCode == 27) {
				self.close();
				return false
			}
		});
		//外盒
		box = doc.createElement('div');
		box.id = 'xiuPopBox';
		box.className = 'xiuPop default shadow ' + options.skin;
		box.style.cssText = 'position:absolute; z-index:9999px; display:none;' + width + left + top;
		
		//标题
		if(options.title){
			h1 = doc.createElement('h1');
			h1.id = 'xiuPopBoxh1';
			a = doc.createElement('a');
			a.title = options.closeText;
			a.id = 'closeXiuPop';
			text = doc.createTextNode(options.closeText);
			a.appendChild(text);
			h1.appendChild(a);
			h1.innerHTML += options.title;
			box.appendChild(h1);
		}
		//iframe
		if(options.url){
			var iframe = document.createElement("iframe");
				iframe.src =options.url;
				iframe.setAttribute("frameborder", "0",0);
				iframe.style.cssText = width + height;
			if(iframe.attachEvent){
				iframe.attachEvent("onload",function(){
						callback()
				});
			}else{
				iframe.onload = function(){
					callback()
				};
			}
			box.appendChild(iframe);
		}else{
			//内容
			con  = doc.createElement('div');
			con.id = 'xiuPopContent'
			con.className = 'content ' + options.contentCss;
			con.innerHTML =  options.content;
			con.style.cssText = height;
			box.appendChild(con);
		}
		//按键
		if (options.button) {
			
			btn= doc.createElement('div');
			btn.id = 'xiuPopBoxBtn'
			btn.className = 'buttonZone';
			box.appendChild(btn);
		}
		fragment.appendChild(box);
		
		//背景
		if (options.hasBg) {
			bg= doc.createElement('div');
			bg.id = 'xiuPopBoxBG'
			bg.className = 'xiuPopBG';
			Elementheight = doc.documentElement.scrollHeight;
			//wh = $(w).height();
			//bh = Elementheight > wh ? Elementheight : wh;
			bg.style.cssText = 'height:' + Elementheight + 'px';
			if($.fn.bgIframe){$(bg).bgIframe();}
			fragment.appendChild(bg);
			
		}
				
		doc.body.appendChild(fragment);	
		
		//内容为JQ对象时
		if (typeof(options.content) == 'object') {
			options.content.parent().append('<span id="xiuPopCopy"><!--  xiuPopCopy By Anson--></span>');
			$('#xiuPopContent').empty().append(options.content);
			$('#xiuPopBox').data('xiuPopCon', options.content)
		}
		
		//关闭
		$('#closeXiuPop').click(function() {
			self.close();
			return false
		});
		
		//按键事件
		if(options.button){
			$.each(options.button,
			function(id, func) {
				$('<button>' + id + '</button>').unbind('click.xiupop').bind('click.xiupop',
				function(e) {
					func(this)
				}).appendTo(doc.getElementById('xiuPopBoxBtn'))
			});
		}
		
		
		if (options.center) {
			function ct(){
				var E = $(box),
					elWidth = E.width(),
					elHeight = E.height(),
					winWidth = $(w).width(),
					winHeight = $(w).height(),
					bdTop = fixedIe ? 0 :  $(w).scrollTop();
					pos = fixedIe ? "fixed": "absolute";
				$('body')
				.data('sTop', bdTop)
				.data('winHeight', winHeight/2)
				.data('elHeight', elHeight/2);
				E.css({
					"position":pos,
					"top": (winHeight / 2) - (elHeight / 2) + bdTop,
					"left": (winWidth / 2) - (elWidth / 2)
				});
				
				if(pos=='absolute'){
					
				$(w).unbind('scroll.xiupop').bind('scroll.xiupop',function(){
						box.style.top = doc.documentElement.scrollTop+ (winHeight/2) - (elHeight/2);
					})
				
				}
/*		if (options.scrollClose) {
					$(w).bind('scroll',
					function() {
						if (Math.abs($('body').data('sTop') - $(w).scrollTop()) > 100) $.closepop()
					})
				}*/
			}
			var E = $(box),
				elWidth = E.width(),
				elHeight = E.height(),
				winWidth = $(w).width(),
				winHeight = $(w).height(),
				bdTop = fixedIe ? 0 :  $(w).scrollTop();
				pos = fixedIe ? "fixed": "absolute";
				ct();
			
			$(w).bind('resize.xiupop',function() {
					E.hide();
					ct();
					E.show();
			})
			
		}
		if(options.drag && options.title){
			require('ui.dnr')($);$(box).dragAndResize({handle: {'#xiuPopBoxh1':'m'}});
		}
		try {
			document.execCommand("BackgroundImageCache", false, true);
		} catch (e) {}
		$('body').data('showXiuPop', setTimeout(function() {
				if (!options.hasBg){
					if($.fn.bgIframe){$('#xiuPopBox').bgIframe();}
				}
				$('#xiuPopBox').show();
				
				if(!options.url){
					callback()
				}
			if (options.time != 0) {
				$('#xiuPopBox').data('remove', setTimeout(function() {
					self.close()
				},
				options.time * 1000))
			}
		},
		100));
		
	return self;
	}
$.dialog = $.xiupop;
$.closeDialog = $.closepop;
function easyPOP(title,content,bcallback,callback){
	var btncallback = bcallback || function() {};
	var tcallback = callback || function() {};
	$.xiupop({
			time: 0,
			title:title,
			hasBg: true,
			center: true,
			skin:'white',
			content: '<div style="width:100%; text-align:center">'+content+'</div>',
			button:{
			'确定': function(t) {
				btncallback();
				$.closepop();
				}		
		 	},
	 		callback:tcallback
					 
			})
	
	} 
