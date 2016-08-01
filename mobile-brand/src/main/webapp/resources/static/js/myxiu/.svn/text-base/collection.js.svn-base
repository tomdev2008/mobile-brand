/*	
* 我的收藏
* create 2014-5-28/Qin fengmu
* update 2014-6-5
*/
$.extend($.fn,{
	tab : function(callBack){
		//for(var i = 0; i < this.length; i++){			
		 return this.find('.ui-tab-hd li').click(function(){
				var parent = $(this).parents('.ui-tab'),
					index = parent.find('.ui-tab-hd li').index($(this)),
					tab_bd = parent.find('.ui-tab-bd');
				if($(this).is('.curr')){
					return false;
				}else{		
					$(this).siblings('li').removeClass('curr');
					$(this).addClass('curr');
					tab_bd.hide().eq(index).show();
					
					Collection.setBtnDefault();
				}
				Collection.setType(index);
				if(Collection.getType() == 1 && Collection.firstLoadBrand){
					Collection.getFavGoodsList(1,1);
					Collection.firstLoadBrand = false;
				}
			})	
		//}		
	},
	toggle : function(cls, callBack1, callBack2){
		return	this.click(function(){			
			if(!$(this).is('.'+cls)){
				 $(this).addClass(cls);
				 if(typeof callBack1 == 'function'){
						callBack1();
				}
			}else{
				$(this).removeClass(cls);
				 if(typeof callBack2 == 'function'){
						callBack2();
				}
			}			
					
		})		
	},
	fadeOut : function(t,c){		
		return this.animate({'opacity':0},t||500,function(){
					$(this).hide();
					if(typeof c == 'function'){
						c();
					}
				})
	}
})
$('.ui-tab').tab();

var Collection =  {
	firstLoadBrand : true,
	init : function(){
		Collection.getFavGoodsList(1,0);
		
		$('#J-del-collection').click(function(){
			var type = Collection.getType();						  
			if($(this).is('.act')){
				$('#goodsFav'+type).find('.del-icons').animate({'marginLeft':-50},300);	
				$(this).removeClass('act');
				$('#goodsFavBd'+type).find('.loadMore').show();
			}else{
				$('#goodsFav'+type).find('.del-icons').animate({'marginLeft':0},300);	
				$(this).addClass('act');
				$('#goodsFavBd'+type).find('.loadMore').hide();
			}
		})
	},
	setBtnDefault : function(){
		if($('#J-del-collection').is('.act')){
			$('#J-del-collection').trigger('click');
		}
	},
	delCollection : function(){			
		$('.del-icons').click(function(){									  
			var	del_bool = window.confirm('确认删除该条收藏？'),
				$t = $(this),
				id = $t.data('id'),
				type = Collection.getType(),
				url = type == 0 ? '/favor/delFavorGoods.shtml?goodsId='  : '/favorBrand/delFavorBrand.shtml.shtml?brandId=';
			if(del_bool){	
				Xiu.runAjax({
					url:url+id,					
					sucessCallBack: function(msg){
						if(msg.result){
							$t.parents('li').fadeOut(500,function(){
								var $p_li =	$t.parents('li');					
								
								if($p_li.siblings('li').length == 0){
									var $p = $p_li.parents('ul');
									if($('#goodsFavBd'+type).find('.loadMore').length > 0){
										$('#goodsFavBd'+type).find('.loadMore').remove();
										Collection.getFavGoodsList(1,type);
										Collection.setBtnDefault();
									}else{
										if($p.is('#goodsFav0')){
											$p.html('<li class="no-collection"><p>您还没有收藏任何商品，<a href="http://m.xiu.com">去首页看看吧！</a></p></li>');
										}else{
											$p.html('<li class="no-collection"><p>您还没有收藏任何品牌，<a href="http://m.xiu.com">去首页看看吧！</a></p></li>');
										}			
									}																
								}else{
									//先做隐藏处理
									$('#goodsFavBd'+type).find('.loadMore').hide();
								}
								$p_li.remove();
							});
						}else{
							Dialog.tip({txt:msg.errorMsg,autoHide: true});						
						}					
					}
				})						
			}					  
		})
	},
	setGoodsListHtm : function(goodsList,type,loadmore){
		var htm = '';	
		switch(type){
			case 0: 			
				if(goodsList == ''){
					htm = loadmore ? '' : '<li class="no-collection"><p>您还没有收藏任何商品，<a href="http://m.xiu.com">去首页看看吧！</a></p></li>';
				}else{
					$.each(goodsList,function(index,data){
						htm +='<li class="clearfix">'
							+'			<div data-id="'+data.goodsId+'" class="del-icons"></div>'
							+'			<div class="goods clearfix">'
							+'				<div class="gd-left">'
							+'					<a href="http://m.xiu.com/product/'+data.goodsId+'.html"><img src="'+data.goodsImgUrl+'" width="66" height="88" alt="'+data.goodsName+'"></a>'
							+'				</div>'
							+'				<div class="gd-right">'
							+'					<h3 class="gd-name"><a href="http://m.xiu.com/product/'+data.goodsId+'.html">'+data.goodsName+'</a></h3>'
							+'					<p class="gd-price">￥'+data.goodsPrice+'</p>'
							+'				</div>'
							+'			</div>'
							+'		</li>';
					
					});
					
				}
				break;
			case 1:
				if(goodsList == ''){
					htm = '<li class="no-collection"><p>您还没有收藏任何品牌，<a href="http://m.xiu.com">去首页看看吧！</a></p></li>';
				}else{
					$.each(goodsList,function(index,data){
						htm +='<li class="clearfix">'
							+'	<div data-id="'+data.brandId+'" class="del-icons"></div>'
							+'	<div class="goods clearfix">'
							+'		<div class="gd-left">'
							+'			<img src="'+data.brandImg+'" style="width:115px; height:49px"  alt="'+data.brandName+'">'
							+'			<h2 class="brands-name">'+data.brandName+'</h2>'
							+'		</div>'
							+'		<div class="gd-right">'
							+'			<ul class="rt-list">'
							+'				<li><a href="">新品上架</a></li>'
							+'				<li><a href="">精选卖场</a></li>'
							+'			</ul>'
							+'		</div>'
							+'	</div>'
							+'</li>';		
					
					});					
				}
			break;
		
		}
			
		return htm;
	},
	loadMoreGds : function(){
		$('.loadMore').click(function(){
			var $t = $(this),
				pageNum = $t.data('pagenum'),
				type = parseInt(Collection.getType());
				$('.del-icons').unbind('click');
				Collection.getFavGoodsList(pageNum,type,$t,true);
		})
	},
	/* 
	* @pageNum 页码	
	* @type 0代表商品操作  1代表品牌操作
	*/
	getFavGoodsList : function(pageNum,type,obj,loadmore){
		var url = type == 0 ? '/favor/getFavorGoodsList.shtml' : '/favorBrand/getFavorBrandsList.shtml';
		Xiu.runAjax({
			url:url,
			data: {'pageNum' : pageNum},
			sucessCallBack : function(msg){
				var list = type == 0 ? msg.favorGoodsList : msg.favorBrandList;
				if(msg.result){		
					if(pageNum == 1){						
						$('#goodsFav'+type).html(Collection.setGoodsListHtm(list,type,loadmore));						
						if( msg.totalPage > 1 ){
							$('#goodsFavBd'+type).append('<div data-pagenum="2" class="loadMore">+显示更多</div>');
							Collection.loadMoreGds();
						}						
					}else{						
						$('#goodsFav'+type).append(Collection.setGoodsListHtm(list,type,loadmore));	
						if(pageNum <  msg.totalPage){
							obj.attr('data-pageum',pageNum+1);
						}else{
							obj.remove();
						}
					}
					
					if(list != ''){						
					  Collection.delCollection();
					}					
				}else{
					if(msg.errorCode == 4001){
							Dialog.tip({txt:'请重新登录！',autoHide: true,callBack: function(){
								location.href ='http://m.xiu.com/login/login.html';
							}});
						}else{
							Dialog.tip({txt:msg.errorMsg,autoHide: true});
						}
				}
			}
		})
	},
	getType : function(){
		return $('#collectionType').val();
	},
	setType : function(type){
		$('#collectionType').val(type);
	}
};
Collection.init();
