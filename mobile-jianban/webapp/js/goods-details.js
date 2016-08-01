$(function(){

	var details = {

		init: function(){
			//绑定选择颜色
			$(".goods_color li").each(function(index, dom){
				$(dom).bind("click", function(){
					$("li", this.parentNode).each(function(){
						if($(this).hasClass("selected"))$(this).removeClass("selected");
					});
					$(this).addClass("selected");
					// 赋值尺寸  下标索引
					$("#colorIndex").val(index);
					$("#color").val($(this).text());
					var skuIndex = "c"+$("#colorIndex").val()+"s"+$("#sizeIndex").val();
					$("#skuIndex").val(skuIndex);
				});
			});
			
			//绑定选择尺码
			$(".goods_size li").each(function(index, dom){
				$(dom).bind("click", function(){
					$("li", this.parentNode).each(function(){
						if($(this).hasClass("selected"))$(this).removeClass("selected");
					});
					$(this).addClass("selected");
					// 赋值颜色  下标索引
					$("#sizeIndex").val(index);
					$("#size").val($(this).text());
					var skuIndex = "c"+$("#colorIndex").val()+"s"+$("#sizeIndex").val();
					$("#skuIndex").val(skuIndex);
				});
			});

			//绑定收藏
			$("#favorStatus").bind("click", function(){
				//检查是否登录
				Xiu.runAjax({
				  url :'/loginReg/checkLogin.shtml',			  
				  sucessCallBack : function(msg){
					  	// 如果已登录 则实行收藏或取消收藏操作
						if(msg.result){
							if($("#favorStatus").hasClass("unfav")){
								// 收藏商品
								Xiu.runAjax({
									  url :'/favor/addFavorGoods.shtml?terminal=3&goodsId='+$("#goodsId").val(),			  
									  sucessCallBack : function(msg){
											if( msg.result){
												$("#favorStatus").removeClass("unfav");
												Dialog.tip({txt:'收藏成功'});
											}else{
												Dialog.tip({txt:msg.errorMsg});
											}
										},
										complete : function(){
											setTimeout(function () { 
												 Dialog.hide();
											}, 3000);	
										}
								});
							}else{
								// 取消收藏
								Xiu.runAjax({
									  url :'/favor/delFavorGoods.shtml?terminal=3&goodsId='+$("#goodsId").val(),			  
									  sucessCallBack : function(msg){
											if( msg.result){
												$("#favorStatus").addClass("unfav");
												Dialog.tip({txt:'取消收藏成功'});
											}else{
												Dialog.tip({txt:msg.errorMsg});
											}
										},
										complete : function(){
											 setTimeout(function () { 
												 Dialog.hide();
											 }, 2000);	
										}
								});
							}
						}else{
							Xiu.GobackUrl.ToLogin(location.href);
						}
					},
					complete : function(){
						Dialog.hide();
					}
				});
			});
		}
	};
	
	details.init();
	
	$('#buyItNow').click(function(){
			if($('#color').val() == ''){				
				Dialog.tip({txt: '请选择颜色', time: 1000, autoHide : true});	
				return false;
			}else if($('#size').val() == ''){					
				Dialog.tip({txt: '请选择尺码',time: 1000, autoHide : true});	
			}else{
				var goodsId = $("#goodsId").val();
				var goodsSn = $("#goodsSn").val();
				var color = $("#color").val();
				var size = $("#size").val();
				var skuIndex = $("#skuIndex").val();
				var goodsSku = $("#skuIndex").find("option:selected").text();  
				var activityId = $("#activityId").val();
				var url = 'http://m.xiu.com/order/index.html?goodsId='+goodsId+'&goodsSn='+goodsSn+'&color='+color+'&size='+size+'&skuIndex='+skuIndex+'&goodsSku='+goodsSku+'&actId='+activityId;
				//检查是否登录
				Xiu.runAjax({
				  url :'/loginReg/checkLogin.shtml',
				  beforeSend : function(){
				  	Dialog.tip({txt:'正在提交...'});
				  },				  
				  sucessCallBack : function(msg){
						if( msg.result ){
							location.href = url;
						}else{
							Xiu.GobackUrl.ToLogin(url);
						}
					},
					complete : function(){
						Dialog.hide();
					}
				});	  				
			}
	});
});


document.addEventListener('DOMContentLoaded', function(){
			
	var picUl = $("#goods-pic-ul").get(0),
	slider_status_span = $("#slider_status_span").get(0);	
	//debugger;
	function changeScreenEndFun() {
	    slider_status_span.style['webkitTransform'] = 'translateX('+this.page * 225/3+'px) translateZ(0)';    
	}
	slip('page', picUl, {
		//change_time:5000,
		num:3,
		endFun:changeScreenEndFun,
		infinite: true
	}, false);

});
