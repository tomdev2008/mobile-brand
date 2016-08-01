var ip = 'http://mportal.xiu.com:8083',
	bip = '',
	wxLoginUrl = 'http://weixin.xiu.com/binding/union_login?redirectUrl=';
	/**
	 * 获取URL中最后/后.前的id
	 * */
	function getIdFromURL(){
		var url = window.location.href,
			la = url.lastIndexOf('/'),
			ld = url.lastIndexOf('.');
		var id = url.substring(la+1,ld);
		return id;
	};
	/**
	 * add Session期限的m2www的cookie功能
	 * */
	function addM2WWWSessionCookie(){
		document.cookie = "m2www=1;path=/;domain=xiu.com;"
	};

var Xiu = {
		//全局请求
	  	runAjax : function(op){				  	
			var domain = op.brandip ? bip : ip;				
			var settings = {
					url:domain+op.url,
					type:'get',						
					data:op.data,
					async: op.async || true,
					dataType:'jsonp',
					jsonp: 'jsoncallback', 
					timeout : 60000,
					error : function(xrh,ts){
						if( ts == 'error'){						 
						//	Dialog.tip({txt: '请求失败!'});
						}else if( ts == 'timeout'){
							Dialog.tip({txt: '请求超时!'});
						}
					},
					success: function(msg){												
						op.sucessCallBack(msg);
					}
				}
				op.url = domain+op.url;
			settings = $.extend(settings, op || {});
			$.ajax(settings);
		},
		Cookie : {			
			get: function(cookieName) {
                var doc_cookie = document.cookie, name = doc_cookie.indexOf(cookieName + "="), cookie;
                if (name !== -1) {
                    name += cookieName.length + 1;
                    cookie = doc_cookie.indexOf(";", name);
                    return unescape(doc_cookie.substring(name, (cookie === -1 ? doc_cookie.length : cookie)))
                }
                return
            },
			set: function(cookieName, cookie, time) {
				
                var domain = (document.domain.toLowerCase().indexOf("xiu.com") !== -1 ? "xiu" : "xiu"), date;
                if (time === 0) {					
                    document.cookie = cookieName + "=" + escape(cookie) + ";path=/;domain=." + domain + ".com"
                } else {					
                    date = new Date();
                    date.setTime(date.getTime() + (time * 24 * 3600 * 1000));
                    document.cookie = cookieName + "=" + escape(cookie) + ";expires=" + date.toGMTString() + ";path=/;domain=." + domain + ".com"
                }
            },
			del: function(name) {				
                document.cookie = name + "=; expires=Mon, 26 Jul 1997 05:00:00 GMT; "
            }
	
		},
		//获取url参数
		getUrlProperty : function() {
			   var url = location.search; 
			   var theRequest = new Object();
			   if (url.indexOf("?") != -1) {
			      var str = url.substr(1);
			      strs = str.split("&");
			      for(var i = 0; i < strs.length; i ++) {
			         theRequest[strs[i].split("=")[0]]=decodeURIComponent(strs[i].split("=")[1]);
			      }
			   }
		   return theRequest;
		},
		//
		GobackUrl : {
		  	set : function(uKey, uVal, callBack){
				Xiu.runAjax({
					url : '/loginReg/setRedirectUrl.shtml',					
					data : {uKey : uKey,uValue :encodeURIComponent(uVal)},
					sucessCallBack : function(msg){
						callBack(msg);
					}
				})
			},
			get : function(uKey, callBack){
					Xiu.runAjax({
						url : '/loginReg/getRedirectUrl.shtml',	
						async : false,
						data : {uKey : uKey},
						sucessCallBack : function(msg){
							callBack(msg);
						}
					})
				},
			ToLogin : function(url){				
				if(Xiu.isWechatBrowser()){
					location.href = wxLoginUrl+encodeURIComponent(url);
				}else{
					Xiu.GobackUrl.set('toUrl',url,function(msg){
						if( msg.result ){
							location.href = 'http://m.xiu.com/login/login.html';
						}					
					})		
				}
					
			}
		},	
		chkLocalStorage : function(){
			return !!window.localStorage;
		},
		isWechatBrowser : function(){
			var ua = navigator.userAgent.toLowerCase(),
				bool = false;	
			if (ua.match(/MicroMessenger/i) == "micromessenger") {
				bool = true;	
				
			}
			return bool;
		},
		chkForbidWechat : function(){
			return Xiu.Cookie.get('forbid_wechat')=='y';
		},	
		initForbidWechatCookie : function(){
			var params = Xiu.getUrlProperty();	
			if (params['forbid_wechat'] == "y") {
				Xiu.Cookie.set('forbid_wechat','y',0);		
			}
		}
		
	};
	
Xiu.initForbidWechatCookie();
checkShareFrom();
	
/**
* 微信中分享接口重载
*/
	function wechatShare(dataFriend, dataTimeline) {
    // If no data is defined, just fallback to wechat's default behaviour.
    if (!dataFriend || !dataTimeline) {
        return;
    }
    var titleFriend = dataFriend.title;
    var titleTimeline = dataTimeline.title;
	var desc = dataFriend.desc;
	var url = document.location.href;
	var mainURL = dataFriend.mainURL || 'http://m.xiu.com' ;
    
    function getDefaultImage() {
        var images = document.getElementsByTagName("img");
        if (images && images.length > 0) {
            for (var i = 0; i < images.length; i++) {
                var image = images[i];
                if (image.width > 140 && image.height > 130 ) {
                	return image.src;
                }
            }
        }
        return null;
    }
	//分享给朋友
    function shareFriend() {
        // Delay image loading to the time when DOM is ready. 
        var img = dataFriend.img || getDefaultImage();
        WeixinJSBridge.invoke("sendAppMessage", {
            "img_url": img,
            "link": url,
            "desc": desc,
            "title": titleFriend
        }, function(res) {
        });
    }
	//分享到朋友圈
    function shareTimeline() {
        var img = dataTimeline.img || getDefaultImage();
        WeixinJSBridge.invoke("shareTimeline", {
            "img_url": img,
            "link": url, 
            "desc": '走秀网',
            "title": titleTimeline
        }, function(res) {
        });
    }
	
	//同步到微博
	
	function shareWeibo(){
		WeixinJSBridge.on("menu:share:weibo", function() {
			WeixinJSBridge.invoke("shareWeibo", {
				"content": titleFriend,
				"url": mainURL 
			}, function(res) {
				WeixinJSBridge.log(res.err_msg);
			});
		});	
	}
	

    
    document.addEventListener("WeixinJSBridgeReady", function onBridgeReady() {
        WeixinJSBridge.on("menu:share:appmessage", function(argv) {
            shareFriend();
        });

        WeixinJSBridge.on("menu:share:timeline", function(argv) {
            shareTimeline();
        });
		shareWeibo();
    }, false);
};

//弹出层
var Dialog = function(){
	var	bool = false,
		tip_bool = false,
		tiptimer,
		dialog_htm = '<div id="dialog" class="layer">'
				+'<div class="layer-bg"></div>'
				+	'<div id="dialogInner" class="layer-main">'					
				+	'</div>'
				+'</div>';	
	
	
	var dialog = {
		show : function(op){			
			if( !bool ){
				$('body').append(dialog_htm);	
				$('#dialog .layer-bg').click(function(e){	
					dialog.hide();	
					Dialog.stopBodyEvent('layer-bg');
				})
				bool = true;
			}else{
				$('#dialog').show();
			}
			var doc_w = $(document).width(),
				inner_w = $('#dialogInner').width(),
				left =0; //(doc_w - inner_w)/2;
				//alert(doc_w)
			$('#dialogInner').html(op.html || '').css({
				left: left || '0%',
				top : op.top || '15%',
				width : op.width || 'auto',
				height: op.height || 'auto'
			})
			
			//回调函数
			if( typeof op.callBack == 'function' ){
				op.callBack();
			}				
			
			if(op.tip){
				$('.layer-bg').css('background','rgba(0,0,0,0)');
			}else{
				$('.layer-bg').css('background','rgba(0,0,0,0.85)');
			}
		},
		tip : function( op ){
			var tip_htm ='	<div class="tip-loadding">'			
					+'		<span class="txt">'+op.txt+'</span>'							
					+'	</div>';					
			Dialog.show({
				html : tip_htm,
				tip : true,
				top: op.top || '45%',
				height : op.height || 'auto'
			})	
			clearTimeout(tiptimer);
			if( op.autoHide ){
			tiptimer =	setTimeout(function(){
					Dialog.hide();
					if( typeof op.callBack == 'function' ){
						op.callBack();
					}		
				},op.time || 2000);
			}
		},
		hide : function(){
			$('#dialog').hide();
		},
		
		stopBodyEvent : function(className){			
			document.body.addEventListener('touchstart', function(e) {		
		        if (e.target.className === className) {  //如果点上的是遮罩层的话，执行遮罩层消失的动作，然后在全局（注意是全局，因为绑在了body上），阻止默认事件。
										
					e.preventDefault();		
		        }
		
		    }, false);
		}
		
	}
	return dialog;
}()
Xiu.checkedLogin = function(){			  
			           // +' <li class="clearfix"><a href="../login/login.html"><p class="fl"><i class="icons wuLiu"></i><span class="disV">查物流</span></p><span class="fr fcNum">(<i>3</i>)</span></a></li>'			  
	function chgLoginPanel(){	
		if( $('.loginStatus').length > 0 ){		
			Xiu.runAjax({
				url: '/loginReg/getUserInfoRmote.shtml',
				//async: false,
				sucessCallBack : function(msg){	
					var loginStatus = false;	
					var log_htm = ' <li class="J-seturl"><a  href="'+(Xiu.isWechatBrowser()?(wxLoginUrl+'http://m.xiu.com'):'http://m.xiu.com/login/login.html')+'"><i class="icons user"></i><span class="disV">我的走秀</span></a></li>'
			            +' <li class="J-seturl"><a href="'+(Xiu.isWechatBrowser()?(wxLoginUrl+'http://m.xiu.com/myxiu/moreOrders.html'):'http://m.xiu.com/login/login.html')+'"><i class="icons order"></i><span class="disV">我的订单</span></a></li>'
						 +'  <li class="J-seturl"><a href="'+(Xiu.isWechatBrowser()?(wxLoginUrl+'http://m.xiu.com/myxiu/myCollection.html'):'http://m.xiu.com/login/login.html')+'"><i class="icons favor"></i><span class="disV">我的收藏</span</a></li>'	    
			            +' <li class="clr"><a href="http://m.xiu.com/myxiu/feedback.html"><i class="icons tuChao"></i><span class="disV">我要吐槽</span></a></li>';
					if(msg.result){					
						log_htm = ' <li class="J-seturl J-login"><a href="http://m.xiu.com/myxiu/index.html"><i class="icons user"></i><span class="disV">'+msg.userName+'</span></a></li>'
						          +'  <li class="J-seturl"><a href="http://m.xiu.com/myxiu/moreOrders.html"><i class="icons order"></i><span class="disV">我的订单</span</a></li>'	     
								   +'  <li class="J-seturl"><a href="http://m.xiu.com/myxiu/myCollection.html"><i class="icons favor"></i><span class="disV">我的收藏</span</a></li>'	     
						          +' <li class="clr"><a href="http://m.xiu.com/myxiu/feedback.html"><i class="icons tuChao"></i><span class="disV">我要吐槽</span></a></li>';
						loginStatus = true;	
					}				
					$('.loginStatus').html(log_htm).removeClass('hidden');
					setTourl(loginStatus);
				}
			})		
		}
	}
	$('#loginPanel').click(function(){
		var $panel = $('.loginStatus');
		if($panel.is('.hidden')){
			//$panel.removeClass('hidden');
			chgLoginPanel();
			$('body').append('<div class="login-layer"></div>');
			tapLayer();
		}else{
			$panel.addClass('hidden');	
			$('.login-layer').remove();
		}
		
		//单独处理列表页
		if(typeof $('#bgBox')!= 'undefined'){
			$("#allSortBox").addClass("hidden");
        	$("#bgBox").addClass("hidden");
		}		 
	})
	function tapLayer(){	
		$('.login-layer').on('touchstart',function(){
			var $panel = $('.loginStatus');
			if(!$panel.is('.hidden')){
					$panel.addClass('hidden');	
					$('.login-layer').remove();
			}
			Dialog.stopBodyEvent('login-layer');
		})
	}
	
	function setTourl(loginStatus){
		$('.J-seturl a').click(function(){
			//登录后url为首页、列表页、详情页，未登录为个人中心
			var url = loginStatus ? location.href : 'http://m.xiu.com',
				$t = $(this);
			//没登陆		
			if(!loginStatus){			
				Xiu.GobackUrl.set('toUrl',location.href,function(msg){window.location.href = $t.attr('href');});				
			}else{
			
				Xiu.GobackUrl.set('myxiuGoBack',url,function(msg){
					if(msg.result){
						var $panel = $('.loginStatus');
						if(!$panel.is('.hidden')){
								$panel.addClass('hidden');	
								$('.login-layer').remove();
						}
						location.href = $t.attr('href');
					}				
				})
		
			}		
			return false;
		})
	}
	
}()
//返回
function history_go(){
	var history_len = window.history.length;	
	if(history_len > 0){
		history.go(-1);
	}else{
		location.href = 'http://m.xiu.com';	
	}
	
}

//由谁分享
function checkShareFrom(){
	var params = Xiu.getUrlProperty();	
	var userId=params['m_cps_from_id'];
	var client=params['m_cps_from_client'];

	if (userId != undefined) {		
		Xiu.runAjax({
			url:'http://mcps.xiu.com:8888/visit/'+client+'/'+userId,
			brandip : true				
		})
	}		
	
}



