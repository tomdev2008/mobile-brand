$(function(){

	//主页的方法
	window.s_xiu = {

		//初始化方法
		init: function(){
		//	this.tabChange();	
			lazyload({defObj : "#goods-container"});
			lazyload({defObj : "#find-container"});
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

		},

		//添加或取消喜欢
		editLike: function(obj){
			//debugger;
			if($(obj).hasClass("like")){
				$(obj).removeClass("like");
				$(obj).addClass("dislike");
				$("b", obj).html(Number($("b", obj).text())-1);
			}else{
				$(obj).removeClass("dislike");
				$(obj).addClass("like");
				$("b", obj).html(Number($("b", obj).text())+1);				
			}
		}

	};	

	s_xiu.init();

});