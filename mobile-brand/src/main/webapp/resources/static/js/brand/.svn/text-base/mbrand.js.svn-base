$(function(){
	var ListPage = {	
		brandId :$('#bid').val(),
		oldActid :'',
		scrollT : 0,
		scrollTBool : true,
		filterScroll : '',
		filter_arr : [],
		totalPage : $('#totalPage').val(),
		nextPageLoadding :  false,
		hasFilter :  false,
		init : function(){		
			var _this = this;		
			_this.categorySelect();
			_this.winScroll();
			_this.collection();
			$('#bgBox').on('touchstart',function(){
				_this.filter_setDef();
			})
		},
		//绑定滚动事件
		winScroll : function(c){	
			var _this= this;
			$(window).scroll(function(){			
				var winHeight = $(document.body).height(),
					winScrollTop = $(window).scrollTop(),					
					scrolltop = _this.scrollT;				
					_this.scrollT = winScrollTop;
					
				
				if((winScrollTop/winHeight) > 0.8 && !_this.nextPageLoadding ){			
					_this.loadNextPage();
				}			
				
				/* if(scrolltop < _this.scrollT &&  _this.scrollT > 50 && _this.scrollTBool){
					$('.ui-nav').hide();
				}else if(scrolltop > _this.scrollT){
					$('.ui-nav').show();
				}; */
				//top
				if( winScrollTop > 0){
					$(".scrollTop").show();
				}else if( winScrollTop == 0){
					$(".scrollTop").hide();
				};
				
			})
		},
		scrollToList : function(){
			var h1 = $('.banner').height(),
			       h2 = $('.story').height(),
				h = h1 + h2+45;				 
				$(window).scrollTop(h)
		},
		filter_setDef : function(){
			$('.filter-btn').removeClass('filter-btn-select');
			$('.filter-select').css({top:'-9999px'});
			$('#bgBox').hide();
		},
		categorySelect : function(){
			var _this = this;	
			$('.filter-btn').click(function(){
				var $t = $(this);
				if($(this).is('.filter-btn-select')){
					_this.filter_setDef();
				}else{				
					$(this).addClass('filter-btn-select');
					$('.filter-select').css({top:'49px'});					
					$('#bgBox').show();
				}
				
			})
			//导航
			$('.nav-list li').click(function(){
				var sort = $(this).data('sort');			
					if($(this).is('.curr') && $(this).is('.price')){						
						if($(this).is('.desc')){
							$(this).removeClass('desc');
							$(this).data('sort',1);
							sort = 1;
						}else{
							$(this).addClass('desc');
							$(this).data('sort',2);
							sort = 2;
						} 
						$('#sort').val(sort);
						_this.formSubmit();
					}else{
						$(this).addClass('curr');
						$(this).siblings('li').removeClass('curr');
						$('#sort').val(sort);
						_this.formSubmit();
						return false;
					}								
			})			

			//点击选择分类
			$('.category-itm').click(function(){
				var index = $('.filter-first-list .itm').index($(this))
				$(this).parents('.filter-first-list').hide();
				$('.filter-select .filter-sec').eq(index).show();			
			})
			//点击标题返回分类
			$('.filter-tit').click(function(){
				$(this).parents('.filter-sec').hide();
				$('#category .c-itm').removeClass('itm-select')
				$('.filter-first-list').show();
				/* _this.filterScrollDestroy();
				_this.createFilterScroll(); */
			})
			//点击分类
			$('#category .c-itm').click(function(){	
				// _this.filterScrollDestroy();
				if($(this).is('.itm-select')){
					$(this).removeClass('itm-select');					
				}else{
					$(this).siblings('li').removeClass('itm-select');
					$(this).addClass('itm-select');					
					// _this.createFilterScroll();
					
				}				
			})
			
			//点击分类分类条件
			$('.filter-third-list li').click(function(){				
				var	$t = $(this),
					catid = $t.attr('data-id'),					
					txt = $t.data('name'),						
					parent = $t.parents('.filter-sec'),
					index = $('.filter-sec').index(parent);	;
				
				$('#catid').val(catid)			
				$('.filter-third-list li').removeClass('select');
				$t.addClass('select');				
				parent.hide();
				//重新选择分类后要清除原来的筛选条件
				$('.clearIpt').val('');
				//提交数据
				_this.formSubmit();
				// 获取筛选条件
				_this.filter_setDef();
				// _this.filterScrollDestroy();
				_this.getFilterList(function(){	
					$('.filter-first-list .itm').eq(index).find('.select-itm').html(txt);
					$('.filter-first-list').show();			
				});
				
			})	
				
			//显示隐藏品牌故事
			$('.show-story').click(function(){
				if(!$(this).is('.hide-story')){
					$(this).addClass('hide-story')
					$('#story-wrap').show();
				}else{
					$(this).removeClass('hide-story')
					$('#story-wrap').hide();
				}			
			})		
		},
		formSubmit : function(){
			var _this = this;
			var data = $('#brandForm').serialize();
				_this.getGoodsList(data,function(msg){
				if(msg.goodsItems != ''){						
					$('#pageNum').val(1);
					_this.tpl(msg.goodsItems);				
					$('#currPage').val(msg.page.pageNo);
					$('#nextPage').val(msg.page.pageNo+1);
					$('#totalPage').val(msg.page.pageCount);
					_this.scrollToList();
					_this.winScroll();							
				}else{
					$('#tabsContainer').html('<p class="no-data">没有找到符合条件的商品！</p>');	
				}
				$('.no-more-data').remove();
			});
		},
		filterSelect : function(){
			var _this = this;	
			
			$('.filterList-itmArea .itm').click(function(){
				var index = $('.filter-first-list .itm').index($(this))
				$(this).parents('.filter-first-list').hide();
				$('.filter-select .filter-sec').eq(index).show();
			})
			$('.J-filter-tit').click(function(){
				$(this).parents('.filter-sec').hide();
				$('.filter-first-list').show();
				/* _this.filterScrollDestroy();
				_this.createFilterScroll(); */
			})
			//点击筛选条件			
			$('.J-filterList li').click(function(){	
				if($(this).is('.price-range')){
					return false;
				}
				var filter = $(this).data('filter'),
					txt = $(this).data('name'),
					parent = $(this).parents('.filter-sec'),
					filter_arr = [],
					index = $('.filter-sec').index(parent);				
				
					$('.filter-first-list .itm').eq(index).find('.select-itm').html(txt).attr('data-filter',filter);
					parent.hide();
					
					$('.J-filter-itm').each(function(){
						var filter = $(this).data('filter');
						if(filter!=''){
							filter_arr.push(filter);
						}						
					})					
					
					if($(this).parents('ul').is('.selectPrice')){
						$('#sPrice').val('');
						$('#ePrice').val('');						
						//点击全部
						if($(this).is('.clearPrice')){
							$('#fPrice').val('');	
						}else{
							$('#fPrice').val(filter);	
						}						
					}else{						
						$('#filter').val(filter_arr.join(';'));										
					}	
					
					_this.filter_setDef();
					$('.filter-first-list').show();	
					/*_this.getFilterList(function(){	
						$('.filter-first-list .itm').eq(index).find('.select-itm').html(txt);
						$('.filter-first-list').show();			
					});*/
					//滚动条
					/* _this.filterScrollDestroy();
					_this.createFilterScroll(); */
					//获取数据
					_this.formSubmit();
			})
			//输入价格
			$('.find-btn').click(function(){
				var sprice = $('.sprice').val(),					
					eprice = $('.eprice').val(),
					regExp = /^[1-9]+\d*$/,
					parent = $(this).parents('.filter-sec'),
					index = $('.filter-sec').index(parent);
					if(sprice != '' && eprice !=''  && regExp.test(sprice) && regExp.test(eprice)){		
					
						if(parseFloat(sprice)>parseFloat(eprice)){							
							Dialog.tip({txt:'起始价格不能大于结束价格',autoHide:true});						
						}else{
							parent.hide();
							$('.filter-first-list .itm').eq(index).find('.select-itm').html(sprice+'-'+eprice+'元');
							$('#fPrice').val('');
							$('#sPrice').val(sprice);
							$('#ePrice').val(eprice);						
							_this.filter_setDef();						
							$('.filter-first-list').show();					
							_this.formSubmit();			
						}									
					}			
			})	
			
			//价格输入框获取焦点时 设置 scrollTBool 为false 防止滚动时 导航被隐藏
			$('.price-ipt').focus(function(){
				_this.scrollTBool = false;
			}).blur(function(){
				_this.scrollTBool = true;
			})
			
		},
		createFilterScroll : function(){
			var _this = this;
			var dh = $(window).height()-124,
				sh = $('#scrollFilter').height();						
				if(sh > dh){
					$('.filter-selectInner').css('position','absolute');
					$('#scrollFilter').height(dh)
					_this.filterScroll=new IScroll("#scrollFilter",{
						hideScrollbar: true,
						fadeScrollbar : true
					});							
				}
		},
		filterScrollDestroy : function(){
			var _this = this;
			if(typeof _this.filterScroll == 'object'){
				_this.filterScroll.destroy();
				$('.filter-selectInner').css('position','static');
				$('#scrollFilter').height('auto')
			}
		},
		getFilterList : function(fn){
			var 	_this = this;
			var data = $('#brandForm').serialize();
			Xiu.runAjax({
				brandip : true,
				//url : '/brand/brandCatalogsSearchByParams?bId='+_this.brandId+'&catId='+actid,				
				url : '/brand/brandAttrsSearchByParams',
				data:data,
				sucessCallBack : function(msg){
					if(msg.attrFacets!=''){
						var first_li_htm = '',
							filter_sec_htm = '',
							catalog_li = '';
													
						//遍历分类
					/* 	first_li_htm +='<li class="itm">分类<em  class="select-itm"></em></li>';
						$.each(msg.catalogs,function(n,catalogs){	
							var thir_li = '';									
							thir_li+='<li data-id="'+catalogs.catalogId+'" data-name="查看全部">查看全部<em>('+catalogs.itemCount+')</em></li>';
							$.each(catalogs.childCatalog,function(index,childCatalog){									
								thir_li+='<li data-id="'+childCatalog.catalogId+'" data-name="'+childCatalog.catalogName+'">'+childCatalog.catalogName+'<em>('+childCatalog.itemCount+')</em></li>';
							})	
							catalog_li+='<li >'+catalogs.catalogName+'<ul class="filter-third-list">'+thir_li+'</ul></li>';						
						})
					filter_sec_htm+='<div class="filter-sec hidden">'
									+'<h2 class="filter-tit">分类</h2>'
									+'<ul  id="category"  class="filter-sec-list">'
									+''+catalog_li+''	
									+'</ul>'
									+'</div>'; */
						
						
						//遍历筛选条件
						$.each(msg.attrFacets,function(i,data){							
							var li = '',cls='',price_htm = '',all_cls='clearFilter',itm_cls='J-filter-itm';							
							if(data.facetType.toLowerCase() == 'price'){
								cls = 'selectPrice';
								all_cls = 'clearPrice';
								itm_cls = '';
								price_htm='<li class="price-range">'
									+'<p><input type="text"class="price-ipt sprice" />-<input type="text" class="price-ipt eprice" /></p>'
									+'<p><input type="button" class="buyBtn find-btn" value="立即查找"/></p>'
									+'</li>';
							}							
							first_li_htm+='<div class="itm">'+data.facetDisplay+'<em data-filter="" class="select-itm '+itm_cls+'"></em></div>';
							li+='<li class="'+all_cls+'" data-filter=""  data-name="全部">全部</li>';
							$.each(data.facetValues,function(k,d){
								li+='<li  data-filter="'+d.id+'" data-name="'+d.name+'">'+d.name+'<em>('+d.itemCount+')</em></li>';
							})	

							filter_sec_htm+='<div class="filter-sec hidden">'
											+'<h2 class="filter-tit J-filter-tit">'+data.facetDisplay+'</h2>'
											+'<ul class="filter-sec-list J-filterList '+cls+'">'
											+''+li+''	
											+''+price_htm+''
											+'</ul>'
											+'</div>';
						})						
						
						//first_li_htm = '<ul class="filter-first-list" >'+first_li_htm+'</ul>';
						$('.filterList-itmArea').html(first_li_htm)
						$('.filter-secArea').html(filter_sec_htm);	
						_this.filterSelect();
						if(typeof fn !='undefined'){
							fn();
						}
					}	
				}
			})
		},
		loadNextPage : function(){
			var _this = this,
				nextPage = Number($('#nextPage').val()),
				totalPage = Number($('#totalPage').val());		
				if(nextPage <= totalPage ){
					$('#pageNum').val(nextPage);
					var data = $('#brandForm').serialize();
						_this.getGoodsList(data,function(msg){
						if(msg.goodsItems != ''){						
							_this.tpl(msg.goodsItems,true);								
							$('#currPage').val(msg.page.pageNo);
							$('#nextPage').val(msg.page.pageNo+1);
							$('#totalPage').val(msg.page.pageCount);
							_this.winScroll();
							if(nextPage == msg.page.pageCount ){
								$('#pageList').after('<p class="no-data no-more-data">没有更多商品了</p>');	
							}
						}
						$('#pageNum').val(1);
					},true);
				}			
		},
		getGoodsList : function(data,fn,nextPage){
			var 	_this = this;
			Xiu.runAjax({
				brandip : true,
				beforeSend : function(){
					_this.nextPageLoadding = true;
					if(nextPage){
						$('.main').append('<div class="nextPageLoading"><div class="loadding"><img src="http://m.xiu.com/static/css/img/load.gif" alt="正在加载..."></div></div>');
					}
				},
				url : '/brand/brandGoodsSearchByParams',
				data : data,
				sucessCallBack : function(msg){
					if(typeof fn!= 'undefined'){
						fn(msg);
					}								
				},
				complete : function(){
					_this.nextPageLoadding = false;
					$('.nextPageLoading').remove();
				}
			})
		},
		tpl : function(gl,nextPage){
				var _this = this,				
					len = gl.length,
					htm = '';				
				for(var i = 0;  i < len; i++ ){			
					var 	sell_out = '';
						buyBtn =  '<a href="http://m.xiu.com/product/' + gl[i].id +'.html?brandId='+ _this.brandId+ '" class="buyBtn fr">立即购买</a>';
					if(gl[i].soldOut){
						sell_out='<a href="http://m.xiu.com/product/' + gl[i].id +'.html?brandId='+ _this.brandId+  '" class="shouqin"></a>';
						buyBtn = '<span class="buyBtn gry-btn fr">立即购买</span>';
					}
					
					htm +='<li><div class="picBox clearfix">'
						 +'  <a href="http://m.xiu.com/product/' + gl[i].id +'.html?brandId='+ _this.brandId +'" class="activity"><img src="http://m.xiu.com/static/css/img/default.315_420.jpg" src3="' + gl[i].imgUrl + '" onerror="nofind();" style="width:100%;" alt="'+gl[i].name+'"/></a>'
						 +'   <div class="actText clearfix">'
						 +'       <div class="line clearfix">'
						 +'           <div class="leftPart">'
						 +'               <h2 class="productName"><a href="http://m.xiu.com/product/' + gl[i].id +'.html?brandId='+ _this.brandId +'">' + gl[i].name + '</a></h2>'
						 +'               <p class="price-area clearfix">'             
						 +'                  <span class="priceNow"><i>&yen;</i> ' + gl[i].showPrice + '</span>'
						 +'                   <span class="original"><i>&yen;</i> <em class="g-price">' + gl[i].mktPrice + '</em></span>'
						 +'                  <span class="discount"><i class="fncolor"> ' + gl[i].disCount + '</i>折</span>'
						 +'              </p>'
						 +'         	</div>'
						 +'    '+buyBtn+'      '
						 +'      </div>'
						 +'	</div>'
						 +'	<div class="actTextBG"></div>'
						 +''+sell_out+''
						 +'</div></li>';
				}
				if(nextPage){
					$('#tabsContainer').append(htm);	
				}else{
					$('#tabsContainer').html(htm);	
				}
					
				lazyload({defObj : "#index"});
		},
		/*
		* 收藏品牌
		*/
		collection : function(){
			$('.favbrand-btn').click(function(){
				var $t = $(this),
					brandId = $t.data('brandid');
				if($t.is('.has-collect')){
					Xiu.runAjax({
						url:'/favorBrand/delFavorBrand.shtml?brandId='+brandId,
						sucessCallBack: function(msg){
							if(msg.result){
								Dialog.tip({'txt':'取消关注成功',autoHide: true});
								$t.removeClass('has-collect');
								//事件跟踪统计
								_gaq.push(['_trackEvent', '品牌关注', '移除关注', brandName, Number(brandId), 'true']);
							}else{
								Dialog.tip({txt:msg.errorMsg,autoHide: true});						
							}					
						}
					})					
					return false;
				}else{
					Xiu.runAjax({
						url:'/favorBrand/addFavorBrand.shtml?terminal=3&brandId='+brandId,
						sucessCallBack : function(msg){
							if(msg.result){
								Dialog.tip({'txt':'品牌关注成功',autoHide: true});
								$t.addClass('has-collect');
								//事件跟踪统计
								_gaq.push(['_trackEvent', '品牌关注', '加入关注', brandName, Number(brandId), 'true']);
							}else{
								if(msg.errorCode == 4001){								
									Xiu.GobackUrl.ToLogin(location.href);
								}else{
									Dialog.tip({'txt':msg.errorMsg,autoHide: true});
								}							
							}
						}
					})
				}
			})
			
			//检查是否收藏了该品牌
			Xiu.runAjax({
				url:'/favorBrand/hasExistsFavorBrand.shtml?brandId='+ListPage.brandId,
				sucessCallBack : function(msg){
					if(msg.result){							
						$('.favbrand-btn').addClass('has-collect');
					}
				}
			})
			
		}
	}
	ListPage.init();
	
/*
* 切换风格
*/	
if(Xiu.chkLocalStorage()){	
	var icoCls = 'one-col-ico',
		listCls = 'two-col';
		geticoCls = localStorage.getItem('pageIco'),
		getList = localStorage.getItem('pageList');
		
		if( geticoCls == 'two-col-ico' ){
			$('#pageList').removeClass(listCls);
			$('.switch-list .icons').removeClass(icoCls).addClass(geticoCls)
		}			
}	
$('.switch-list').click(function(){
	var $icons = $(this).find('.icons');
		if($icons.is('.one-col-ico')){			
			
			$icons.removeClass('one-col-ico').addClass('two-col-ico');
			$('#pageList').removeClass('two-col');
			//本地存储
			if(Xiu.chkLocalStorage()){
				
				localStorage.setItem("pageList", 'two-col');   
				localStorage.setItem("pageIco", 'one-col-ico');  
				
				localStorage.setItem("pageList", '');   
				localStorage.setItem("pageIco", 'two-col-ico');  
			}						
		}else{
			$icons.removeClass('two-col-ico').addClass('one-col-ico');
			$('#pageList').addClass('two-col');
			//本地存储
			if(Xiu.chkLocalStorage()){
				localStorage.setItem("pageList", 'two-col');   
				localStorage.setItem("pageIco", 'one-col-ico');  
				
			}	
		}
		ListPage.scrollToList();
})	
})
Array.prototype.indexOf = function (v) {
    var i = -1;
    while (this[++i]) {
        if (this[i] === v) {
            return i
        }
    }
    return -1
};
Array.prototype.remove = function (v) {
    var i = this.indexOf(v);
    if (i > -1) {
        this.splice(i, 1)
    }
    return [i, v]
};

function nofind(){

var img=event.srcElement;

img.src="http://m.xiu.com/static/css/img/default.315_420.jpg";

img.onerror=null; //控制不要一直跳动

}


	