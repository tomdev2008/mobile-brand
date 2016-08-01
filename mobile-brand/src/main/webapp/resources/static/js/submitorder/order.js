$(function(){
/*
* 页面加载时获取订单信息
* 等待完善数据 数量，颜色,尺码以及支付方式选择
*/
var url = location.href,
		actId =  Xiu.getUrlProperty()['actId'] || '',		
	 	goods_price,
		//优惠券代码
		couponCode,
		addrType;
var goodsInfo = {
		goodsSn  : Xiu.getUrlProperty()['goodsSn'],
		goodsId : Xiu.getUrlProperty()['goodsId'],
		color	 : Xiu.getUrlProperty()['color'],
		size	 : Xiu.getUrlProperty()['size'],
		skuIndex : Xiu.getUrlProperty()['skuIndex'],
		goodsSku : Xiu.getUrlProperty()['goodsSku']
	};
var gaq_addTrans = {
		orderId : '',// 订单号		
		agent :'', // 代理商信息，可选项
		total : '', // 订单总额 不包括运费
		tax :'', // 订单商品税，可选项
		fee:'', // 运费
		city:'', //市区
		Provinces :'', // 省
		country :'' // 国家
	};
var gaq_addItem = {
	orderId : '', // 订单号
	goodsSn : goodsInfo.goodsSn, // 商品唯一码（商品U9料号）
	name : '', // 商品名称
	brand : '', // 商品品牌（格式：中文/英文，例：耐克/Nike。缺省中文则为Nike，缺省英文则为耐克）
	price : '', // 商品单价（元）
	count : '' // 商品数量
};
var getOderInfo = function(){	
Xiu.runAjax({
	url:'/goods/loadGoodsAndAddressInfo.shtml',		
	data: 'goodsSn='+goodsInfo.goodsSn,
	sucessCallBack: function(msg){	
		if( msg.result ){
			var $addr_area = $('#address'),						
				$img = $('#J-img'),
				$info = $('#J-info'),
				addr_htm = '',
				img_htm = '',
				info_htm = '',
				img_url = '',					
				quantity_val = '',
				pay_htm = '',
				no_addr_cls = '',
				paymthod = 'AlipayWire';
				ua = navigator.userAgent.toLowerCase();					
			
			
			
			$.each(msg.goodsImgUrlList,function( index, data ){
				var  val = $.parseJSON(data.value);
				if( goodsInfo.skuIndex ==  data.key){
					img_url = val[0];
					return false;
				}
			})					
			//商品数量	
			for( var i = 1; i <= 10; i++ ){
				quantity_val+='	<option value="'+i+'">'+i+'</option>';
			}	
			goods_price = msg.goodsPrice;
			//收货地址
			if(msg.addressVo == '' || msg.addressVo == null){
				addr_htm ='请填写收货地址<i class="icons"></i>';
				no_addr_cls = 'no-address';
				addrType = 0;
			}else{
				addr_htm ='<ul class="addr-list">'
						+'<li>'+msg.addressVo['rcverName']+'<span class="mobile">'+msg.addressVo['mobile']+'</span></li>'							
						+'<li class="dt-addr">'+msg.addressVo['addressPrefix']+msg.addressVo['addressInfo']+'<span style="margin-left:10px;">'+msg.addressVo.postCode+'</li>'
						+'</ul><i class="icons"></i><input type="hidden" name="orderReqVO.addressId" value="'+msg.addressVo['addressId']+'"/>';	
						addrType = 1;
				//gaq统计
				gaq_addTrans.city = msg.addressVo.addressPrefix.split(' ')[1];
				gaq_addTrans.Provinces = msg.addressVo.addressPrefix.split(' ')[0];	
				gaq_addItem.name = msg.goodsName;
				gaq_addItem.price = msg.goodsPrice;
			}
			//图片
			img_htm = '<img src="'+img_url+'" alt="'+msg.goodsName+'">';
			
			//商品信息
			info_htm = '<p class="itm1">'+msg.goodsName+'</p>'								
						+'<p class="itm2">颜色：<span id="p-color">'+goodsInfo.color+'</span><input type="hidden" name="goodsSku" value="'+goodsInfo.goodsSku+'" /><span class="size">尺码：<em id="p-size">'+goodsInfo.size+'</em></span><input type="hidden" name="goodsSn" value="'+goodsInfo.goodsSn+'" /></p>'
						+'<div class="mt7 clearfix"><div class="select select-area">'
						+	'<select id="quantity" name="quantity">'+quantity_val+'</select>'
						+'</div>'								
						+'<input type="hidden" name="orderReqVO.deliverTime" value="1"/>'					
						+'<input type="hidden" name="orderReqVO.invoice" value="0"/>'
						+'<input type="hidden" name="orderReqVO.payMedium" value="web"/>'
						+'<input type="hidden" name="orderReqVO.orderSource" value="2"/>'
						+'<input type="hidden" name="couponCode" id="couponCode-ipt" value=""/>'
						+'<span class="price_ld"><img src="../static/css/img/y_loading_easy.gif"/></span>'
						+'</div>'
						+'<div class="mt5"><span class="total">总额：<em id="total">￥'+parseFloat(msg.goodsPrice)+'</em></span><span id="fee"></span></div>';	
			//支付方式显示				
			if (Xiu.isWechatBrowser() && !Xiu.chkForbidWechat()) {
				paymthod = 'WeChat';	
				pay_htm +='<li class="checked" data-val="WeChat"><span class="icons wx"></span><span class="radio"><em></em></span></li>'
					+'<li  data-val="AlipayWire"><span class="icons zfb"></span><span class="radio"><em></em></span></li>';	
									
			}else{
				pay_htm +='<li class="checked" data-val="AlipayWire"><span class="icons zfb"></span><span class="radio"><em></em></span></li>';
						//+'<li class="bankCard" data-val="card"><span class="icons card"></span><span class="radio"><em></em></span></li>';
			}
								
			//加载所有信息
			var loadInfo_htm ='	<div class="area-box product-area">'
					+'<div class="clearfix">'
					+'	<div id="J-img" class="p-left">'
					+'		'+img_htm+''
					+'	</div>'
					+'	<div id="J-info" class="p-info">'	
					+'		'+info_htm+''
					+'	</div>'
					+'</div>'							
					+'</div>'
					+'<div id="address" class="area-box address-area '+no_addr_cls+'">	'	
					+'	'+addr_htm+''
					+'</div>'
					+'<div id="use-coupons" class="area-box area-box-1"><span id="couponCodeTxt" class="fr"></span>使用优惠券<i class="icons"></i></div>'
					+'<div id="show-coupons">'					
					+'</div>'				
					+'<div class="pay-area">'
					+'	<div class="pay-tit">'
					+'		<h3 class="tit">选择支付方式</h3>'
					+'	</div>'
					+'	<ul class="pay-list">'
					+''+pay_htm+''
/*								+'		<li data-val=""><span class="icons cft"></span><span class="radio"><em></em></span></li>'
				+'		<li class="bankCard" data-val="card"><span class="icons card"></span><span class="radio"><em></em></span></li>		'		*/	
					+'	</ul>'
					+'	<input type="hidden" id="paymentMethod"  name="orderReqVO.paymentMethod" value="'+paymthod+'" >'
					+'</div>'
					+'<div class="btn-area">'
					+'	<input type="submit" class="ui-btn orange-btn" value="立即支付">'
					+'</div>';	
				$('#orderForm').html(loadInfo_htm);
			//初始化订单事件
			init();
			calc_price(1);					
		}else{						
			if( msg.errorCode == 4001 ){
				Xiu.GobackUrl.ToLogin(url);
			}else{						
				 Dialog.tip({txt: msg.errorCode+'--'+msg.errorMsg});
			}	
		}													
	}
});		

}()//end getOderInfo

//加载时事件设置
function init(){	
	var hasLoadCoupons = false;
	$('#address').click(function(){		
		var val = location.href;
		Xiu.GobackUrl.set( 'toUrl',val, function(msg){
			if( msg.result ){
				if(addrType == 0){		
					location.href = 'address.html?from=order&type=add&addressId=';
				}else{
					location.href = 'addressList.html?from=order'
				}
			}
		});							   
	})
	//改变数量		
	$('#quantity').on('change', function(){
		var $t = $(this),
			val = Number($t.val());					
			calc_price(val);						
	})		
	//提交订单
	
	$('#orderForm').on('submit', function(){
		if( addrType == 0 ){				
			 Dialog.tip({txt: '请填写收货地址',autoHide: true});
		}else{		
			Xiu.runAjax({
				url: '/order/createOrderAndPay2.shtml',
				beforeSend : function(){
					Dialog.tip({txt: '正在前往支付页面',top:'0%',height:'100%'});
				},
				data : $(this).serialize(),
				sucessCallBack: function(msg){
					if( msg.result ){
						gaq_addItem.orderId = gaq_addTrans.orderId = msg.orderId
						pageTracker_fn(gaq_addTrans,gaq_addItem)
						//createGAScript(gaq_addItem,gaq_addTrans);
						location.href = msg.payInfoVO.payInfo;
						Dialog.hide();
					}else{
						if(msg.errorCode == 7001){
							Dialog.tip({txt:'支付失败,请拨打400-888-4499选择其他支付方式',time: 10000,autoHide: true});
						}else{
						  Dialog.tip({txt: msg.errorMsg,autoHide: true});
						}						
					}
				}
			})
		}		
		return false;
	})
	//选择支付方式
	$('.pay-list li').click(function(){
		var val = $(this).attr('data-val');
	  $(this).siblings('li').removeClass('checked');
	  $(this).addClass('checked');
	  $('#paymentMethod').val(val);
	  /*	if($(this).is('.bankCard')){
			$('.bank-area').show().animate({left:0});
		}*/
	})
	
	//使用优惠券
	$('#use-coupons').click(function(){
		var fn = '';			
		if(!hasLoadCoupons){		
			Xiu.runAjax({
				url : '/coupon/list?actionType=1',
				beforeSend : function(){
					Dialog.tip({txt:'正在获取优惠券'});
				},
				sucessCallBack : function(msg){				
					var htm = '';
					Dialog.hide();
					hasLoadCoupons = true;
					if(msg.coupons != ''){								
						var li = '';	
						$.each(msg.coupons,function(index,data){
							li	+='<li class="itm" data-cardid="'+data.cardId+'">'
								+'	<div class="itm-l">'
								+'		<p><em class="fz20">'+data.cardTypeName+'券</em><em class="cl1"></em></p>'
								+'	</div>'
								+'	<div class="itm-r">'
								+'		<p>券号：'+data.cardId+'</p>'
								+'		<p>'+data.cardRuleLists+'</p>'
								+'		<p class="date">'+data.endTime+'到期</p>'
								+'	</div>'
								+'</li>';
						})
						
						htm = '<ul class="coupons-list">'+li+'</ul>';	
						fn = function(){
							$('.coupons-list .itm').click(function(){
								var $t = $(this),
									cardId = $t.data('cardid');
									if(!$t.is('.slt')){
										Xiu.runAjax({
											url : '/coupon/validateCardCoupon',
											data : {cardCode : cardId,goodsSnStr : goodsInfo.goodsSn},
											beforeSend : function(){
												Dialog.tip({txt:'正在使用优惠券'});	
											},
											sucessCallBack : function(msg){
												if(msg.result){
													couponCode = cardId;													
													Dialog.tip({txt:msg.message,autoHide: true});
													$t.siblings('li').removeClass('slt');
													$t.addClass('slt');
													$('#quantity').trigger('change');												
													uiScreen.close();
													Dialog.hide();
												}else{
													if( msg.errorCode == 4001 ){
															Dialog.tip({txt:'请重新登录',autoHide: true,callBack : function(){
																Xiu.GobackUrl.ToLogin(url);	
															}})	
													}else{
														Dialog.tip({txt:msg.message,autoHide: true});	
													}
														
												}
											}									
										})
									}else{
										couponCode = '';										
										$t.removeClass('slt');
										$('#quantity').trigger('change');												
										uiScreen.close();
										
									}									
							})
						}
					}else{
						htm = '<div class="no-coupons"><img  src="../static/css/img/no-coupons.jpg" width="84" height="84" alt="您没有优惠券"/><p>您暂时还没有优惠劵</p></div>';
						
					}
					
					uiScreen.setHtm(htm,fn);
					uiScreen.show();
				},
				complete : function(){
					Dialog.hide();
				}
			})
		}else{
			uiScreen.show();
		}							  
	})
	uiScreen.init();
}//end init
//设置返回连接	
$('#goBack').attr('href','http://m.xiu.com/product/'+goodsInfo.goodsId+'.html?goodsSn='+goodsInfo.goodsSn+'&actId='+actId);

//计算单价
function calc_price(num){	
	Xiu.runAjax({
		url: '/order/calcOrder.shtml',
		data: {'goodsSn':goodsInfo.goodsSn,'goodsSku':goodsInfo.goodsSku,'quantity':num,'couponCode':couponCode || ''},
		beforeSend : function(){
			$('.price_ld').show();
		},
		sucessCallBack : function(msg){
			if(msg.result){
				var totalAmount = !!msg.promoAmount ? (parseFloat(msg.promoAmount)+parseFloat(msg.totalAmount)) :(parseFloat(msg.totalAmount)+0);
				$('#total').html('￥'+totalAmount);						
				if( Number(msg.freight)== 0){
					$('#fee').html('(免运费)');								
				}else{
					$('#fee').html('(含'+msg.freight+'运费)')
				}				
				if(!!couponCode){
					var couponHtml ='';
						couponHtml ='<div class="area-box area-box-1">'
									+'		<p class="clearfix">使用优惠券优惠：<em class="fr">￥'+msg.promoAmount+'</em></p>'
									+'		<p style="margin-top:8px" class="clearfix">还需支付：<em class="fr totalAmount">￥'+msg.totalAmount+'</em></p>'
									+'	</div>'	;	
					$('#show-coupons').html(couponHtml).show();
					$('#couponCode-ipt').val(couponCode);
					$('#couponCodeTxt').html(couponCode)
				}else{
					$('#show-coupons').html('').hide();
					$('#couponCode-ipt').val('');
					$('#couponCodeTxt').html('')
				}
				
				//gaq统计
				gaq_addTrans.total = msg.amount;
				gaq_addTrans.fee = msg.freight;								
				gaq_addItem.count = String(num);
			}else{
				if( msg.errorCode == 4001 ){
					Dialog.tip({txt:'请重新登录',autoHide: true,callBack : function(){
						Xiu.GobackUrl.ToLogin(url);	
					}})							
				}else{						
					 Dialog.tip({txt:msg.errorMsg,autoHide: true});
				}
				
			}
		},
		complete : function(){
			$('.price_ld').hide();
		}
	})
}
var uiScreen = {
	$uiScreen : $('.ui-screen'),
	$uiScreenBd :	$('.ui-screen-bd'),
	doc_w : $(document).width(),
	
	init : function(){
		var _this = this;
		_this.$uiScreen.css({'left':_this.doc_w});
		$('.ui-screen-close').click(function(){
			_this.close();
		})
	},
	show : function(){	
		 this.$uiScreen.show().animate({left:0});	
	},
	close : function(){
		var	_this = this;		
		_this.$uiScreen.animate({left:_this.doc_w},function(){
			$(this).hide();
		});		
	},
	setHtm : function(html,fn){
		 this.$uiScreenBd.html(html);
		 if(typeof fn == 'function'){
			fn();	
		}
	},
	height : function(height){
		var _this = this;
		_this.$uiScreen.css({
			'height': height
		})
	}
}


//选择银行卡
function selectBank(){
	var $tab = $('.bank-tab').find('li'),
		$bankArea = $('.bank-area'),
		$bankList = $bankArea.find('.bank-list'),
		doc_w = $(document).width(),
		doc_h = $(window).height();
		
		$bankArea.css({'left':doc_w,'height':(doc_h+60)})
	//tab切换
	$tab.click(function(){
		var $t = $(this),
			index = $tab.index($t);
			$tab.removeClass('curr');
			$t.addClass('curr');
			$bankList.hide().eq(index).show();				
	})
	//关闭银行选择
	$('#closeBank').click(function(){
		$('.bank-area').animate({left:doc_w},function(){
			$(this).hide();
		});
	})
	//选择银行
	$('.bank-list-area li').click(function(){
		var $t = $(this);
		$('.bank-list-area li').removeClass('sel');
		$t.addClass('sel');
		$('.bank-area').animate({left:doc_w},function(){
			$(this).hide();
		});
	})
}
	//selectBank();
})//end funtion

function createGAScript(gaq_addItem,gaq_addTrans){	
	$.each(gaq_addTrans,function(i,d){
		ga_addTrans_arr.push(d);
	})	
	$.each(gaq_addItem,function(k,n){
		ga_addItem_arr.push(n);
	})
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.async = true;
	script.src = '../static/js/common/chama_common.js';
	var  scripts=$('script'),
		s = $('script').eq(scripts.length-1);		
	s.after(script);
}

//解决js 浮点运算的bug
function accMul(arg1, arg2) {

	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();

	try { m += s1.split(".")[1].length } catch (e) { }

	try { m += s2.split(".")[1].length } catch (e) { }

	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)

}

// 给Number类型增加一个mul方法，调用起来更加方便。

Number.prototype.mul = function(arg) {

        return accMul(arg, this);

    }

function accAdd(arg1, arg2) {

	var r1, r2, m, c;

	try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }

	try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }

	c = Math.abs(r1 - r2);
	m = Math.pow(10, Math.max(r1, r2))
	if (c > 0) {
		var cm = Math.pow(10, c);
		if (r1 > r2) {
			arg1 = Number(arg1.toString().replace(".", ""));
			arg2 = Number(arg2.toString().replace(".", "")) * cm;
		}
		else {
			arg1 = Number(arg1.toString().replace(".", "")) * cm;
			arg2 = Number(arg2.toString().replace(".", ""));
		}
	}
	else {
		arg1 = Number(arg1.toString().replace(".", ""));
		arg2 = Number(arg2.toString().replace(".", ""));
	}
	return (arg1 + arg2) / m

}

//给Number类型增加一个add方法，调用起来更加方便。

 Number.prototype.add = function(arg) {

        return accAdd(arg, this);

    }
