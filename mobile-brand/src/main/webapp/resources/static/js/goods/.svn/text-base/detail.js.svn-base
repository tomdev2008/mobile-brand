/*商品详细页
* Qin fengmu 2014-3-25
* update : 2014-6-3
* 思路： 加载尺寸和颜色时记录库存状态(sizeIndex,colorIndex), 点击尺码或颜色时调用set_small_imghtm来设置小图，没库存的会变灰色，不可点,同时记录DT.param.key 来查找大图。
* 商品售罄时，默认加载imglist[0].value ,颜色尺码不可点
*/

var //goodsSn = Xiu.getUrlProperty()['goodsSn'],
    goodsId = getIdFromURL(),
	actId = Xiu.getUrlProperty()['actId'] || '';

var DT = {};
DT.param = {
	goodsSn : '',
	color : '',
	size : '',
	skuIndex : '',
	goodsSku : '',
	bigImgIndex : 0,
	sizeIndex : 0,
	colorIndex : 0,
	imgList : '',
	imgListMax :'',
	key :'',
	buyBool : true,
	goodsName : ''
};
DT.Action = {	
	//设置商品详情html结构		
	setHtm : function(msg){
		var htm = '',
			img_htm= '',
			img_arr = '',					
			skuIndex_arr = [],
			goodsSku_arr = [],			
			discount_htm = '',
			sizeUrl_htm = '',			
			btn_cls = 'orange-btn',
			sell_out_htm = '',
			goodsProperty_htm = '',
			market_price_htm = '',
			//加载颜色
			color_htm = DT.Action.loopHtm({type:'c',val :msg.goodsDetailVo.colors,styleMatrix:msg.goodsDetailVo.styleMatrix}),
			//加载尺寸
			size_htm = DT.Action.loopHtm({type:'s',val :msg.goodsDetailVo.sizes,styleMatrix:msg.goodsDetailVo.styleMatrix});		
			//商品id
			DT.param.goodsSn = msg.goodsDetailVo.goodsSn;
			DT.param.goodsName = msg.goodsDetailVo.goodsName;
		//遍历图片
		if( msg.goodsDetailVo.imgList != '' && typeof msg.goodsDetailVo.imgList != 'undefined'){	
		  	var key = msg.goodsDetailVo.imgList[0].key;
			
			if(DT.param.colorIndex != undefined){
				key = 'c'+DT.param.colorIndex+'s'+DT.param.sizeIndex;				
			}
			//console.log(key)
			img_arr = DT.Action.set_small_imghtm(msg.goodsDetailVo.imgList,key);	
			//小图
			DT.param.imgList = msg.goodsDetailVo.imgList;			
			
		}	
		if( msg.goodsDetailVo.imgListMax != '' || typeof msg.goodsDetailVo.imgListMax != 'undefined'){
			DT.param.imgListMax = msg.goodsDetailVo.imgListMax;
		}
		
		//遍历sku
		$.each(msg.goodsDetailVo.styleSku,function(s,sku){
			skuIndex_arr.push(sku.key);
			goodsSku_arr.push(sku.value);
		})	
		
		//商品折扣
		if(msg.goodsDetailVo.discount != 0 && typeof msg.goodsDetailVo.discount !='undefined'){
			discount_htm = '<span class="discount"><em>'+msg.goodsDetailVo.discount+'</em>折</span>';			
		}
		if(msg.goodsDetailVo.price != 0 && typeof msg.goodsDetailVo.price !='undefined'){
			market_price_htm = '<del class="cost">￥'+msg.goodsDetailVo.price+'</del>';			
		}
		
		
		//商品售罄
		if(msg.goodsDetailVo.stateOnsale == 0){
			sell_out_htm = '<div class="sell-out"></div>';
			btn_cls = 'grey-btn';
		}
		
		//商品属性
		if( msg.goodsDetailVo.goodsProperties !='' && typeof msg.goodsDetailVo.goodsProperties != 'undefined' ){
				var p_li = '';
				$.each(msg.goodsDetailVo.goodsProperties, function(i, p){
					p_li +='<li>'+p.key+'：'+p.value+'</li>';
				})
				
			goodsProperty_htm+='<div class="dt-box mt15">'
								+'<div class="dt-hd"><em class="icons"></em><h3>商品属性</h3></div>'
								+'<div class="dt-bd">'
								+'	<ul>'
								+'		'+p_li+''
								+'	</ul>'
								+'</div>'				
								+'</div>';	
			}
		
		//尺码表
		if( msg.goodsDetailVo.sizeUrl != '' && typeof msg.goodsDetailVo.sizeUrl != 'undefined' ){
			sizeUrl_htm = '<a href="http://m.xiu.com/goodssize/'+goodsId+'.html?actId='+actId+'" id="sizeTb" data-t='+msg.goodsDetailVo.sizeUrl+' >查看尺码表</a> ';
		}
		//设置title
		$('title').html(msg.goodsDetailVo.goodsName+'--走秀网触屏版');
		$('#detail-title').html(msg.goodsDetailVo.goodsName);
		
		//商品信息html结构	
		htm = '<!--幻灯片-->'
	    	+'<div class="goods-img">'
			+'	<div id="slider" class="goods-img-area">'
			+'		<ul id="slideimg" class="clearfix">'
			+''+(img_arr[0]|| '')+''
			+'		</ul>'
			+''+sell_out_htm+''
			+'	</div>	'		
			+'	<ul class="goods-img-index">'
			+''+(img_arr[1]||'')+''
			+'	</ul>'
			+'</div>'
			+'<!--幻灯片结束-->'
			+'<!--商品价格信息-->'
			+'<div class="goods-info">'
			+'	<div class="goods-box">'
			+'		<h1 class="g-name">'+msg.goodsDetailVo.goodsName+'</h1>'
			+'	<p class="g-price"><span class="price">￥'+msg.goodsDetailVo.zsPrice+'</span>'+market_price_htm+''+discount_htm+'</p>'
			+'	<p class="g-ps">'+msg.goodsDetailVo.tranport+'</p>'//预计<em>3</em>天从美国仓库发货，<em>12-15</em>天送
			+'	</div>'
			+'	<div class="goods-box grey-box">'
			+'		<div class="g-itm clearfix">'
			+'			<span class="fl">颜色：</span>'
			+'			<div class="J-color g-itm-r">'
			+'				<ul class="clearfix">'
			+''+color_htm+''
			+'				</ul><input type="hidden" id="colors" value=""/>'
			+'			</div>'
			+'		</div>'
			+'		<div class="g-itm clearfix">'
			+'			<span class="fl">尺码：</span>'
			+'			<div class="J-size g-itm-r">'
			+'				<ul class="clearfix">'
			+''+size_htm+''
			+'				</ul><input type="hidden" id="sizes" value=""/>'
			+''+sizeUrl_htm+''
			+'			</div>'
			+'		</div>'
			+'	</div>'
			+'</div>  '
			+'<!--商品价格信息结束--> '		
			+'		'+goodsProperty_htm+''
			+'<div class="load-goods-dt">'				
			+'	<a href="http://m.xiu.com/goodsinfo/'+goodsId+'.html?actId='+actId+'">商品详情 <em class="icons ico-arrow"></em><em class="icons ico-img"></em></a>'				
			+'</div>'
			+'<p id="t_test"></p>';
			$('#main').html(htm);
			$('.wrap').append('<div class="btn-area fix-btn"><div class="inner"><a id="buyItNow" href="javascript:;" class="ui-btn '+btn_cls+'">立即购买</a></div></div>');
			if(msg.goodsDetailVo.stateOnsale == 1){
				DT.Action.buyItNow();
				DT.Action.selectColorAndSize(skuIndex_arr,goodsSku_arr,msg.goodsDetailVo.imgList,msg.goodsDetailVo.styleMatrix);
				//设置尺寸没库存状态
				DT.Action.setSize_nostock(msg.goodsDetailVo.styleMatrix,DT.param.colorIndex);
				//设置颜色没库存状态
				DT.Action.setColor_nostock(msg.goodsDetailVo.styleMatrix,DT.param.sizeIndex);				
				$('#colors').val('c'+DT.param.colorIndex);
				$('#sizes').val('s'+DT.param.sizeIndex);
				//DT.param.skuIndex = $('#colors').val()+$('#sizes').val();
				DT.param.GoodsSku = DT.Action.getGoodsSku(DT.param.skuIndex,skuIndex_arr,goodsSku_arr);
				
				//console.log(DT.param.skuIndex+','+DT.param.GoodsSku)
			}else{
				//售罄时全部设置为没有库存状态
				$('.g-itm-r').find('li').addClass('nostock');
				//
				DT.param.buyBool = false;
			}			
			
			DT.silde();	
			DT.showBigImg(msg.goodsDetailVo.imgListMax);
			DT.Action.chkCollection(goodsId)				
			if(Xiu.isWechatBrowser())
			{
				//微信中的分享
				var dataFriend = {"img":null,"title":msg.goodsDetailVo.goodsName + "仅售" + msg.goodsDetailVo.zsPrice + "元，我挺喜欢，大家也可以看看。"};
				var dataTimeline = {"img":null,"title":msg.goodsDetailVo.goodsName + "仅售" + msg.goodsDetailVo.zsPrice + "元，我挺喜欢，大家也可以看看。"};	
				wechatShare(dataFriend, dataTimeline);
			}
	},
	//根据库存状态获取初始化尺寸和颜色下标
	getStock_status : function(styleMatrix){
	  var arr = [];		
		outerloop:
		for(var i=0; i<styleMatrix.length; i++){			
			for(var j=0; j <styleMatrix[0].length;j++){	
				if(styleMatrix[i][j]>0){
					arr.push(i);
					arr.push(j);					
					break outerloop; 			
				}	
			 } 
		}
		return arr;
	},
	/*设置尺寸为没有库存状态
	* index ： 颜色下标
	* styleMatrix[size][color]
	*/
	setSize_nostock : function(styleMatrix,index){
		for(var i=0; i<styleMatrix.length; i++){		
			if(styleMatrix[i][index] < 1){
				$('.J-size').find('li').eq(i).addClass('nostock');		
			}else{
				$('.J-size').find('li').eq(i).removeClass('nostock');	
			}			
		}
	},
	/*设置颜色为没有库存状态
	* index ： 尺码下标
	* styleMatrix[size][color]
	*/
	setColor_nostock : function(styleMatrix,index){
		for(var i=0; i<styleMatrix[0].length; i++){		
			if(styleMatrix[index][i] < 1){
				$('.J-color').find('li').eq(i).addClass('nostock');		
			}else{
				$('.J-color').find('li').eq(i).removeClass('nostock');	
			}				
		}
	},
	set_buyBtn : function(styleMatrix,colorIndex,sizeIndex){		
		if(styleMatrix[sizeIndex][colorIndex] < 1 ){
			DT.param.buyBool = false;
			$('#buyItNow').removeClass('orange-btn').addClass('grey-btn');
		}else{
			DT.param.buyBool = true;
			$('#buyItNow').removeClass('grey-btn').addClass('orange-btn');
		}
		
	},
	//循环遍历尺码跟颜色
	loopHtm : function(op){
		var htm = '', status = DT.Action.getStock_status(op.styleMatrix)[1];		
		
		if(op.type == 's'){
			//获取初始尺寸下标
			DT.param.sizeIndex = status = DT.Action.getStock_status(op.styleMatrix)[0];
			DT.param.size = op.val[status];
		}else{
			DT.param.color = op.val[status];
			//获取初始颜色下标
			DT.param.colorIndex = status ;	
		}
		
		for( var i = 0; i < op.val.length; i++ ){
			var s_cls = '',
				s_ico = '';
			if( i == status ){
				s_cls = 'class="select"';
				s_ico = '<i class="icons"></i>';				
			}
			htm += '<li '+s_cls+' data-key="'+op.type+''+i+'" data-val = '+op.val[i]+' >'+op.val[i]+''+s_ico+'</li>';	
		}
		return htm;
	},
	/*选择颜色跟尺寸
	* 	arr1 : skuKey
	* 	arr2 : skuvalue
	*/
	selectColorAndSize : function(arr1, arr2,imgList,styleMatrix){
		$('.g-itm-r').find('li').click(function(){												
			var $p = $(this).parents('.g-itm-r'),
				index = $p.find('li').index($(this));
			
			if($(this).is('.select')) return false;// || $(this).is('.nostock')
			$p.find('li').removeClass('select');
			$p.find('.icons').remove();
			$(this).addClass('select').append('<i class="icons"></i>');
			if( $p.is('.J-color') ){
				$('#colors').val($(this).attr('data-key'));
				DT.param.color = $(this).attr('data-val');	
				DT.param.colorIndex = index;
				var img_arr = DT.Action.set_small_imghtm(imgList,$('#colors').val()+$('#sizes').val());			
				
				if( img_arr[0] != undefined ){
					$('#slideimg').html(img_arr[0]);
					$('.goods-img-index').html(img_arr[1]);					
					DT.silde();
				}	
				
				//设置尺寸没库存状态
				DT.Action.setSize_nostock(styleMatrix,index);				
				DT.Action.set_buyBtn(styleMatrix,index,DT.param.sizeIndex);
				
			}else{
				$('#sizes').val($(this).attr('data-key'));
				DT.param.size = $(this).attr('data-val');
				DT.param.sizeIndex = index;
				
				//设置颜色没库存状态
				DT.Action.setColor_nostock(styleMatrix,index);				
				DT.Action.set_buyBtn(styleMatrix,DT.param.colorIndex,DT.param.sizeIndex);
			}
			DT.param.skuIndex = $('#colors').val()+$('#sizes').val();
			DT.param.GoodsSku = DT.Action.getGoodsSku(DT.param.skuIndex,arr1,arr2);
			//DT.param.key = $('#colors').val()+$('#sizes').val();		
		})
	},
	getGoodsSku : function(key,arr1, arr2){
		var index;
		for( var i = 0; i < arr1.length; i++ ){
			if( arr1[i] == key ){
				index = i;
				break;
			}
		}
		return arr2[index];
	},
	//根据key 去查找相应的小图
	set_small_imghtm : function(imgList,key){
		var	arr = [],
			img_htm='',
			img_ix_htm = '';	
		DT.param.skuIndex = key;		
		for(var i = 0 ; i < imgList.length; i++){
			if(imgList[i].key == key){			
				val = $.parseJSON(imgList[i].value)				
					for(var k = 0; k < val.length; k++){	
						if( k==0 || k == 1 ){
							img_htm +='<li><img data-src="'+val[k]+'" src="'+val[k]+'" alt="'+DT.param.goodsName+'"></li>	';	
						}else{
							img_htm +='<li><img data-src="'+val[k]+'" src="http://m.xiu.com/static/css/img/default.315_420.jpg" alt="'+DT.param.goodsName+'"/></li>	';	
						}
						
						if( k == 0){
							img_ix_htm += '<li class="curr"></li>';
						}else{
							img_ix_htm += '<li></li>';
						}
						
					}					
					arr.push(img_htm);
					arr.push(img_ix_htm);
					break;
			}
			
		}
		return arr;

	},
	//根据key 去查找相应的大图
	set_bigImg_htm : function(imgList , key){
		var big_imgArr = [];
		
		//大图	
		for(var i = 0 ; i < imgList.length; i++){
			if(imgList[i].key == key){
				var img_htm = '',
					img_ix_htm = '',
					val = $.parseJSON(imgList[i].value)	;			
					for(var k = 0; k < val.length; k++){						
											
						if( k == 0){
							img_htm +='<li><img data-src="'+val[k]+'" src="'+val[k]+'" alt="'+DT.param.goodsName+'"></li>';	
							img_ix_htm += '<li class="curr"></li>';
						}else{
							img_ix_htm += '<li></li>';
							img_htm +='<li><img data-src="'+val[k]+'" src="http://m.xiu.com/static/css/img/default.315_420.jpg" alt="'+DT.param.goodsName+'"></li>';	
						}
						
					}					
					big_imgArr.push(img_htm);	
					big_imgArr.push(img_ix_htm) 
				break;
			}	
			
		}		
		return big_imgArr;
	},
	buyItNow : function(){
		
	  		$('#buyItNow').click(function(){
				if(DT.param.buyBool){
		  			if($('#color').val() == ''){				
		  				Dialog.tip({txt: '请选择颜色', time: 1000, autoHide : true});	
		  				return false;
		  			}else if($('#size').val() == ''){					
		  				Dialog.tip({txt: '请选择尺码',time: 1000, autoHide : true});	
		  			}else{
		  				var url = 'http://m.xiu.com/order/index.html?goodsId='+goodsId+'&goodsSn='+DT.param.goodsSn+'&color='+DT.param.color+'&size='+DT.param.size+'&skuIndex='+DT.param.skuIndex+'&goodsSku='+DT.param.GoodsSku+'&actId='+actId;
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
		  				})	  				
		  			}
				}
	  		})
		
	},
	/*
	* 收藏商品
	*/
	collection : function(){
		$('.collection-btn').click(function(){
			var $t = $(this),
				goodsId = $t.data('goodsid');
			if($t.is('.has-collect')){
				Xiu.runAjax({
					url:'/favor/delFavorGoods.shtml?goodsId='+goodsId,
					sucessCallBack: function(msg){
						if(msg.result){
							Dialog.tip({'txt':'取消收藏成功',autoHide: true});
							$t.removeClass('has-collect');
						}else{
							Dialog.tip({txt:msg.errorMsg,autoHide: true});						
						}					
					}
				})		
				
				return false;
			}else{
				Xiu.runAjax({
					url:'/favor/addFavorGoods.shtml?terminal=3&goodsId='+goodsId,
					sucessCallBack : function(msg){
						if(msg.result){
							Dialog.tip({'txt':'商品收藏成功',autoHide: true});
							$t.addClass('has-collect');
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
	},
	chkCollection : function(goodsId){
	  Xiu.runAjax({
		url : '/favor/hasExistsFavorGoods?goodsId='+goodsId,
		sucessCallBack : function(msg){
			if(msg.result){
				$('.fix-btn .inner').append('<span data-goodsId="'+goodsId+'" class="collection-btn has-collect"></span>');
			}else{
				$('.fix-btn .inner').append('<span data-goodsId="'+goodsId+'" class="collection-btn"></span>');
			}
			DT.Action.collection();	
		}
	  })
	},
	translate : function(obj,value,time,has3d){
		var time=time?time:0;
		transl=has3d?"translate3d("+value+"px,0,0)":"translate("+value+"px,0)";
		obj.css({'-webkit-transform':transl,'-webkit-transition':time+'ms linear'});
	},	
	scale : function(obj,w,h,o,time){
		var time=time?time:0;
	
		obj.css({'maxHeight':h,'maxWidth':w,'opacity':o,'-webkit-transition':time+'ms linear'});
	}
	
};
DT.getGoods = function(){
 Xiu.runAjax({
		url:'/goods/loadGoodsDetailByGoodsId.shtml',
		data : {goodsId:goodsId },//'11061541'
		sucessCallBack: function(msg){
			if(msg.result){
				DT.Action.setHtm(msg);
			}else{
				Dialog.tip({txt:msg.errorMsg,autoHide: true,time: 1000,callBack: function(){
					location.href = 'http://m.xiu.com';
				}});					
			}
		}
	})
}()
//幻灯片
DT.silde = function(){
	var $imgList = $('#slideimg'),
		$li = $imgList.find('li'),
		li_len = $li.length,
		margin = 40, //li的外边距
		big_w = 225,//大图最大宽度
		big_h = 300,//大图最大高度
		small_w = 120,//小图最大宽度
		speed = 300,
		small_h = 160; //小图最大高度
	
	DT.Action.scale($imgList.find('img').first(),big_w,big_h,1, speed, true);	
	
	var smallImgSlid = slip('page',slideimg,{		
				num: li_len,
				loop: true,
				change_time : 0,
				distance : small_w+margin,
				no_follow : true,
				touchEndFun: function(){
					DT.param.bigImgIndex = this.page;
					switch(this.dir){
						case 'left':
							var index = this.page,
								$curr_img = $li.eq(index-1).find('img'),
								$next = $li.eq(index).find('img'),
								$loadImg =  $imgList.find('li').eq(index+1);						
							if(index != 0){
								//加载图片
								if(typeof  $loadImg!='undefined' ){
									var loadimg =  $loadImg.find('img'),
										dataSrc = loadimg.attr('data-src'),
										src = loadimg.attr('src');		
										
										if(dataSrc !=null && dataSrc != undefined){											
											loadimg.attr('src',dataSrc);
											loadimg.removeAttr('data-src');
										}
								}
								DT.Action.scale($next,big_w,big_h, 1,speed,  true);
								DT.Action.scale($curr_img,small_w,small_h, 0.3,speed, true);
								$('.goods-img-index li').removeClass('curr').eq(Math.abs(index)).addClass('curr');
							}
						break;
						case 'right': 
							var index = this.page,
								$curr_img = $li.eq(index+1).find('img'),
								$prev = $li.eq(index).find('img');	
							DT.Action.scale($prev,big_w,big_h, 1,speed,  true);
							DT.Action.scale($curr_img,small_w,small_h, 0.3,speed,  true);
							$('.goods-img-index li').removeClass('curr').eq(Math.abs(index)).addClass('curr');
						break;
					}
					
				},
				endFun: function(){
					
				}
		});
}

DT.showBigImg = function( imgList ){
	$('#slideimg').click(function(){			
			var big_imgArr = DT.Action.set_bigImg_htm(imgList, DT.param.skuIndex );
			var	htm =' <div id="bigSlide" class="bigSlide"> <ul id="bigImgList" class="big-imgList clearfix">'+big_imgArr[0]+'</ul><span class="close"><em class="icons"></em></span><ul class="bigImgNav clearfix">'+big_imgArr[1]+'</ul></div>';
			Dialog.show({html: htm, width: $(document).width(), left: '10%', top:'0', callBack: function(){							
				var li_len  = $('#bigImgList').find('li').length;
				$('#bigImgList').find('img').css('width',$('#bigSlide').width());			
				var bigImgSlide = slip('page',bigImgList,{		
					num: li_len,
					loop: true,
					change_time : 0,					
					no_follow : false,
					touchEndFun : function(){						
						switch(this.dir){
							case 'left':						
								if(this.page != 0){									
									loadImg(this.page);						
								}
							break;						
							case 'right':														
								loadImg(this.page);								
							break;
						}						
					},
					endFun : function(){
						$('.bigImgNav li').removeClass('curr').eq(Math.abs(this.page)).addClass('curr');	
					}
				});			
				bigImgSlide.toPage(DT.param.bigImgIndex,1);				
				loadImg(DT.param.bigImgIndex);
			}
			
		});//endDialog.show
			$('#bigSlide .close').click(function(){
				Dialog.hide();
				Dialog.stopBodyEvent('close');
			})
	})//end click
	
	function loadImg(p){
		var $loadimg = $('#bigImgList').find('img').eq(p);										
			if(typeof  $loadimg!='undefined' ){										
				var	dataSrc = $loadimg.attr('data-src'),
					src = $loadimg.attr('src');													
					if(dataSrc !=null && dataSrc != undefined){											
						$loadimg.attr('src',dataSrc);
						$loadimg.removeAttr('data-src');
					}
			}
	}
	
}//end showBigImg
// 返回上一层
DT.Action.goBack = function(){
  var url = actId == '' ? 'http://m.xiu.com' : ('http://m.xiu.com/cx/'+actId+'.html');  
	$('#goBack').attr('href',url);
}()

/*
 * 设置www的具体页面
 * **/
function setGoToWWW(){
		  addM2WWWSessionCookie();
          $('#goto_www').attr( "href", "http://item.xiu.com/product/"+ goodsId + ".shtml");
}

