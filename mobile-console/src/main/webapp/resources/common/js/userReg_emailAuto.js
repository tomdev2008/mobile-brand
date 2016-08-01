﻿var suffixArr=new Array("@163.com", "@qq.com", "@126.com", "@sina.com", "@hotmail.com", "@gmail.com", "@sohu.com", "@yahoo.cn",  "@139.com");
	(function(){
		$.fn.autoComplete=function(arg){
			var opt={
				subBox:"#e_tip",/*提示框ID*/
				subOp:"li",/*操作对象*/
				id:"#emailAddr",/*文本框ID*/
				suffixArr:suffixArr,//数组对象
				hoverClass:"on"/*选中效果*/
			};
		  
		   var option=$.extend(opt,arg);
		   var _oWidth=$(option.id).outerWidth();
		   var _cur=-1;
			
		   $(option.id).keyup(function(e){
						if(e.keyCode==40 || e.keyCode==38){
							return false;
						}
						var _that=$(this);
						if(_that.val()!=""){
								if(e.keyCode!=38 && e.keyCode!=40 && e.keyCode!=13 && e.keyCode!=27){
									var _inputVal=_that.val();
									$.fn.autoComplete.tipFun(_inputVal,_that);
								}
						}else{
							$(option.subBox).hide();	
						}
				 });
		   
		   //tipFun函数
		   $.fn.autoComplete.tipFun=function(_v,o){
			   //var _that=o;
			   $(option.subBox).css({"width":_oWidth-2,"display":"block"});
			   var str="<ul>";
		       //str += '<li id="e_type">请选择邮箱类型:</li><li><a class="cur_val" href="javascript:void(0)" >' + _v + "</a></li>"; // 不要了
			   var e=_v.indexOf("@");
			   if(e==-1){
						  $.each(option.suffixArr,function(s,m){
						  str+='<li><a href="javascript:void(0)"  id="e'+s+'">' + _v + m + "</a></li>";							
				      });
				}else {
						 var _sh=_v.substring(0,e);
						 var _se=_v.substring(e);
						 $.each(option.suffixArr, function (s,m) {
							if(m.indexOf(_se)!=-1){
									//alert(_sh+m);
									str += '<li><a href="javascript:void(0)" id="e'+s+'" >' + _sh + m + "</a></li>"	;
							}
					});
				}
				str+="</ul>";
				$(option.subBox).html(str); 
			    
				 /*绑定hover事件*/
					$(option.subBox).find(option.subOp).hover(function(){
							var _that=$(this);
							_that.addClass(option.hoverClass);					   
						},function(){
							var _that=$(this);
							_that.removeClass(option.hoverClass);			
						});
					/*绑定click事件*/
					$(option.subBox).find(option.subOp).each(function(){
						$(this).click(function(e){
							//alert(typeof($(e.target)));
							//alert($(e.target).val());
							if($(e.target).attr("id")!="e_type"){
								 
								$(option.id).val($(e.target).text());
								$(option.id).blur();
								
								$(option.subBox).hide();
								e.stopPropagation();
							}
						});											  
					});
			};
			/*点击页面其他地方关闭提示层*/
			$(document).bind("click",function(e){
				 	$(option.subBox).hide();
			});
			
			/*itemFun*/
			$.fn.autoComplete.itemFun=function(){
				var _tempArr=$(option.subBox).find(option.subOp);
				var _size=_tempArr.size();
				//alert(_cur); 
					for(var i=0;i<_size;i++){
						_tempArr.eq(i).removeClass(option.hoverClass);
					}
					if(_size>=0){
						if(_cur>_size-1){
							_cur=0;	
						}
						if(_cur<0){
							_cur=_size-1;	
						}
						_tempArr.eq(_cur).addClass(option.hoverClass);
					}else{
						_cur=0;	
					}
			};
			
			$(document).keydown(function(e){
					switch(e.keyCode){
						  case 40://下键
							   _cur++;
							   $.fn.autoComplete.itemFun();
							   break;
						  case 38://上键
							   _cur--;
							   $.fn.autoComplete.itemFun();
							   break;
						  default:
							   break;
					}
			});
		
		/*文本框keydown*/
		$(option.id).keydown(function(e){
			var _temp=$(option.subBox).find(option.subOp);
			if(e.keyCode==13){
				$(this).val(_temp.eq(_cur).text());
				$(option.subBox).hide();
				e.stopPropagation();
				_cur=0;
			}							  
		});
		return this;	
	};		  
})(jQuery);
	/*执行*/
	$("#emailAddr").autoComplete();