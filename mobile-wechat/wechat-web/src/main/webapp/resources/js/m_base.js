/** 添加Session期限的m2www的cookie功能 */
function addM2WWWSessionCookie(){
	document.cookie = "m2www=1;path=/;domain=xiu.com;"
};

var Xiu = {
	//全局请求
	runAjax : function(op){	
		var settings = {
			url:op.url,
			type:'post',						
			data:op.data,
			async: op.async || true,
			dataType:'json',
			//dataType:'jsonp',
			//jsonp: 'jsoncallback', 
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
		settings = $.extend(settings, op || {});
		$.ajax(settings);
	}	
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
				$('.layer-bg').on('touchstart',function(){
					 event.preventDefault();		
				})
			}else{
				$('#dialog').show();
			}
			var doc_w = $(document).width(),
				inner_w = $('#dialogInner').width(),
				left =0;
			$('#dialogInner').html(op.html || '').css({
				left: left || '0%',
				top : op.top || '15%',
				width : op.width || 'auto',
				height : op.height || 'auto'
			})
			//回调函数
			if( typeof op.callBack == 'function' ){
				op.callBack();
			}				
			if(op.tip){
				$('.layer-bg').css('background','rgba(0,0,0,0)');
			}else{
				$('.layer-bg').css('background','rgba(0,0,0,0.6)');
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
		        if (e.target.className === className) {  						//如果点上的是遮罩层的话，执行遮罩层消失的动作，然后在全局（注意是全局，因为绑在了body上），阻止默认事件。
					e.preventDefault();		
		        }
			}, false);
		}
		
	}
	return dialog;
}()