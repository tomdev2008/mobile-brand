/*	
* 我的走秀
* create 2014-4-1 leslie qin
* update 2014-5-12 
*/
var href = location.href,
	queryType = $('#ordertype').val(),
	page = $('#page').val(),
	fromPage = 'moreOrders',
	myXiu = {
		getOrderInfo : function(url,data, c){			
			Xiu.runAjax({
				url: url,
				data : data,
				sucessCallBack : function(msg){
					if(msg.result){
						if(typeof c == 'function'){
							c(msg);
						}				
					}else{
						if( msg.errorCode == 4001 ){
							Xiu.GobackUrl.ToLogin(href);
						}else if(msg.errorCode == 7005){
							Dialog.tip({txt: msg.errorMsg,autoHide : true, callBack : function(){
								location.href = './'+fromPage+'.html';
							}});							
						}else{
							Dialog.tip({txt: msg.errorCode+'--'+msg.errorMsg});								
						}
					}
				}
			})	
		},
		/*@参数说明
		* status :(等待付款，商品发货，撤销,交易完成)
		* imgUrl ： 图片地址	
		* btnCls ： 按钮class
		* btnTxt : 按钮文本		
		*/
		setHtm : function(op){
			var htm = '',
				cancel_btn = op.cancel_btn || '';				
			 htm ='<div class="order_con">'
				+'<h2>订单编号：'+op.orderNo+'</h2>'
				+'<dl class="go-orderDetail" data-lpstatus="'+op.lpStatus+'" data-orderid="'+op.orderid+'">'
				+'	<dt><a href="http://m.xiu.com/product/'+op.goodsId+'.html"><img src3="'+op.imgUrl+'"/></a></dt>'				
				+'    <dd>订单状态：'+op.status+'</dd>'
				+'    <dd>订单金额：<em class="price">￥'+op.count+'</em></dd>'
				+'    <dd class="gray">下单时间：'+op.time+'</dd>'
				+' </dl>'
				+''+op.btn+''				
				+'<em class="icons"></em></div>';
				return htm;
		}
	}	
/*
* @参数说明
*  queryType ：
0 - 各订单个数；
1 -  全部订单；
2  - 待付款；
3 - 已发货；
*/	
if( typeof queryType != 'undefined' ){
	var url = '/myorder/getOrderListRemote.shtml',
	  	list_htm = '';
	switch(queryType){
		//个人中心页面
		case '0':	
			var exitUrl = '/loginReg/logoutRemote.shtml';
			myXiu.getOrderInfo(url,{queryType: queryType},function(msg){				
				//更多订单
				if(msg.allOrdersCount != 0){
					//$('#morOrders').html('<em class="num">'+msg.allOrdersCount+'</em>')
				}				
				if(msg.delivedOrdersCount != 0){
					$('#delived').html('<em class="num">'+msg.delivedOrdersCount+'</em>')
				}	
				if(msg.delayPayOrdersCount != 0){
					$('#delayPayOrder').html('<em class="num">'+msg.delayPayOrdersCount+'</em>')
				}
				
				//设置返回按钮
				$('#goBack').click(function(){
					var $t = $(this);
					Xiu.GobackUrl.get('myxiuGoBack',function(msg){
						if(msg.result){
							location.href = decodeURIComponent(msg.myxiuGoBack);							
						}
					})
					return false;
				})
				
			})			
			
			
			//获取用户信息
			Xiu.runAjax({
				url: '/loginReg/getUserInfoRmote.shtml',
				sucessCallBack : function(msg){
					if(msg.result){
						$('#username').html(msg.userName);
					}else{
						if( msg.errorCode == 4001 ){
							//Xiu.GobackUrl.ToLogin(href);
						}else{
							Dialog.tip({txt: msg.errorCode+'--'+msg.errorMsg,autoHide: true});								
						}
					}	
				}
			})
			// 退出登录
			$('#exit').click(function(){
				Xiu.runAjax({
					url: exitUrl,							
					sucessCallBack : function(msg){
						if(msg.result){
							if(Xiu.isWechatBrowser() && !Xiu.chkForbidWechat()){
								location.href =	'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbfd5bd9dd172c9a6&redirect_uri=http://weixin.xiu.com/binding/removeBinding&response_type=code&scope=snsapi_base&state=xiu#wechat_redirect';	
							}else{
								Dialog.tip({txt: '退出登录成功！',autoHide : true, time : 1000, callBack: function(){																											
									location.href = 'http://m.xiu.com';
								}});
							}
								
						}else{
							Dialog.tip({txt: msg.errorMsg,autoHide : true});	
						}	
					}
				})
			})
			
		break;
		//等待付款页面
		case '2':
			fromPage = 'payment';
			myXiu.getOrderInfo(url,{queryType: queryType},function(msg){
				if(msg.listData == '' || typeof msg.listData == 'undefined'){
					$('.main').html('<div class="order_con noorder">您没有等待付款的订单！</div>')
				}else{			
				
					$.each(msg.listData,function(index, data){
						list_htm += myXiu.setHtm({
							status : data.status,//'等待付款'
							imgUrl : data.imgUrl,						
							count : data.price,
							time :  data.when,											
							orderNo : data.orderNo,
							goodsId : data.goodsId,
							//url : data.payType
							orderid : data.orderId,
							lpStatus : data.lpStatus,
							cancel_btn : ' <span class="cancel_btn" orderid = '+data.orderId+'>撤销</span>',
							btn : '<a href="javascript:;" data-url="/order/toPay.shtml?payReqVO.orderNo='+data.orderNo+'&payReqVO.orderAmount='+data.price+'&payReqVO.orderId='+data.orderId+'&payReqVO.payMedium=web" data-paytype="'+data.payType+'" data-orderid ="'+data.orderId+'" class="ui-btn orange-btn  J-buyItNow">立即支付</a>'
						})
					})//end each					
					setList();					
				}
			})		
		break;
		//待收货页面
		case '3':	
			fromPage = 'delivery';
			myXiu.getOrderInfo(url,{queryType: queryType},function(msg){
				if(msg.listData == '' || typeof msg.listData == 'undefined'){
					$('.main').html('<div class="order_con noorder">您没有等待收货的商品！</div>')
				}else{															  
					
					$.each(msg.listData,function(index, data){
						list_htm += myXiu.setHtm({
							status : data.status,//'商品发货'
							imgUrl : data.imgUrl,						
							count : data.price,
							time :  data.when,
							goodsId : data.goodsId,
							orderNo : data.orderNo,						
							orderid : data.orderId,
							lpStatus : data.lpStatus,
							btn :'<a href="http://m.xiu.com/myxiu/order_detail.html?lpStatus='+data.lpStatus+'&orderid='+data.orderId+'&from='+fromPage+'" class="ui-btn white-btn">查看订单</a>'
						})
					})//end each					
					setList();
				}
			})		
		break;
		//全部订单页面
		case '1':	
			fromPage = 'moreOrders';
	  		myXiu.getOrderInfo(url,{queryType: queryType},function(msg){		
				if(msg.listData == '' || typeof msg.listData == 'undefined'){
						$('.main').html('<div class="order_con noorder">你还没有一个订单,<a href="http://m.xiu.com">立即去下单</a></div>')
					}else{																	  
					var  btn_txt = '',
						cancel_btn = '';					
					$.each(msg.listData,function(index, data){												 
						if( data.statusCode == 4){							
							 btn_txt = '<a href="javascript:;" data-url="/order/toPay.shtml?payReqVO.orderNo='+data.orderNo+'&payReqVO.orderAmount='+data.price+'&payReqVO.orderId='+data.orderId+'&payReqVO.payMedium=web" data-orderid="'+data.orderId+'" data-paytype="'+data.payType+'"class="ui-btn orange-btn  J-buyItNow">立即支付</a>';						
						}else{
							btn_txt = '<a href="http://m.xiu.com/myxiu/order_detail.html?lpStatus='+data.lpStatus+'&orderid='+data.orderId+'&from='+fromPage+'" class="ui-btn white-btn">查看订单</a>';
						}						
						list_htm += myXiu.setHtm({
							status : data.status,
							imgUrl : data.imgUrl,						
							count : data.price,
							time :  data.when,															
							orderNo : data.orderNo,
							goodsId : data.goodsId,
							//url : data.payType,									
							orderid : data.orderId,
							lpStatus : data.lpStatus,
							btn : btn_txt								
						})						
					})//end each					
					setList();
			}
		})
		break;
	}
	function setList(){
		$('#orderList').html(list_htm);	
		if(typeof $('#orderList')!= 'undefined'){				
			lazyload({defObj:$('#orderList')});
		}
		//点击订单跳转到订单详情
		$('.go-orderDetail').click(function(){
			var lpstatus = $(this).attr('data-lpstatus'),
				orderid = $(this).attr('data-orderid');
				location.href = 'http://m.xiu.com/myxiu/order_detail.html?lpStatus='+lpstatus+'&orderid='+orderid+'&from='+fromPage;
		})
		//设置跳转
		
		buyItNow();
	}

}//订单列表结束
//订单详情页
if( page == 'orderDetail' ){
var url = '/myorder/getOrderDetailRemote.shtml',
	lpStatus = Xiu.getUrlProperty()['lpStatus'],
	fromPage = Xiu.getUrlProperty()['from'],
	orderid = Xiu.getUrlProperty()['orderid'];
	myXiu.getOrderInfo(url,{lpStatus: lpStatus,orderId: orderid},function(msg){		
		 var mobile = msg.receiverInfoVo.mobile,
		 	btn_txt = '',
			cancel_btn = '',
			track_htm = '',
			chg_pay_btn = '',				
			pay_htm= '';	
			
			if( (typeof msg.receiverInfoVo.mobile == 'undefined') && (typeof msg.receiverInfoVo.phone == 'undefined') ){
				mobile = '';
			}else if( typeof msg.receiverInfoVo.mobile == 'undefined' ){
				mobile = msg.receiverInfoVo.phone; 
			}
			
			//待付款
			if( msg.orderBaseInfoVo.statusCode == 4){			
				btn_txt = ' <div class="btn-area-1"><a href="javascript:;" data-url="/order/toPay.shtml?payReqVO.orderNo='+msg.orderBaseInfoVo.orderNo+'&payReqVO.orderAmount='+msg.commoSummaryVoList.totalAmount+'&payReqVO.orderId='+msg.orderBaseInfoVo.orderId+'&payReqVO.payMedium=web" class="ui-btn orange-btn J-buyItNow" data-paytype="'+msg.orderBaseInfoVo.payType+'" data-orderid="'+msg.orderBaseInfoVo.orderId+'">立即支付</a></div>';  	
				//if(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == "micromessenger"){
				  chg_pay_btn = '<a id="chgPayType" orderid = '+msg.orderBaseInfoVo.orderId+' href="javascript:;">修改支付方式</a>';
				//}
				
			}
			
			//待付款、待审核状态的订单就可以进行撤销
			if( msg.orderBaseInfoVo.statusCode == 1 || msg.orderBaseInfoVo.statusCode == 4 ){
				 cancel_btn = '<span class="cancel_btn" orderid = '+msg.orderBaseInfoVo.orderId+'>撤销</span>';
			}			
			
			//已撤销的订单暂时不现实金额
			if(msg.orderBaseInfoVo.statusCode != 8){
			pay_htm +='<div class="deta_add">'
		        +'   		<p class="clo"><span></span><em>'+msg.receiverInfoVo.receiver+'</em>&nbsp;&nbsp;&nbsp;<em>'+mobile+'</em></p>'
		        +'   		<p>'+msg.receiverInfoVo.province+msg.receiverInfoVo.city+msg.receiverInfoVo.area+msg.receiverInfoVo.address+'</p>'
		        +'   </div>'
				+'    <div class="pay_fs">'
		        +'  		<dl>'
		        +'        	<dt>付款方式：在线支付</dt>'//msg.orderBaseInfoVo.paymentMethod
		        +'            <dd>商品总数量：'+msg.commoSummaryVoList.goodsAmount+'件</dd>'
		        +'            <dd>商品总金额：￥'+msg.commoSummaryVoList.totalAmount+'</dd>'
		     //   +'            <dd>配送费：¥0</dd>'
		        +'           <dd>应支付金额：<em class="price">¥'+msg.commoSummaryVoList.totalAmount+'</em></dd>'
		        +'        </dl>'
		        +'  </div>'	;
			}		
			
			var htm ='	<div class="order_con">'
            	+'<h2>订单编号：'+msg.orderBaseInfoVo.orderNo+'</h2>'
               	+' <div class="pname">'
              	+'	<p class="tit1">订单状态：'+msg.orderBaseInfoVo.status+'</p>'
              	+'     <p class="tit2">下单时间：'+msg.orderBaseInfoVo.when+'</p>'
             	+' </div>'
               	+' <dl>'
               +'	<dt><a href="http://m.xiu.com/product/'+msg.commoSummaryVoList.goodsId+'.html"><img src="'+msg.commoSummaryVoList.goodsImg+'"alt="'+msg.commoSummaryVoList.goodsName+'"></a></dt>'
				 // +'	<dt><img src="'+msg.commoSummaryVoList.goodsImg+'"></dt>'
                +' <dd class="goodsName"><h1><a href="http://m.xiu.com/product/'+msg.commoSummaryVoList.goodsId+'.html">'+msg.commoSummaryVoList.goodsName+'</a></h1></dd>'
                +'    <dd class="gray">'
                +'    	<p class="fl">颜色：<span>'+msg.commoSummaryVoList.color+'</span></p> '
                +'        <p class="fl" style="margin-left:20px;">尺码：<span>'+msg.commoSummaryVoList.size+'</span></p>'
                +'   </dd>'
                +'    <dd><em class="price">￥'+msg.commoSummaryVoList.discountPrice+'</em></dd>'
              	+' </dl>'
				+''+cancel_btn+''
           		+'</div>'
		
		      	+''+pay_htm+''
		       	+' '+btn_txt+'';	
				$('.main').html(htm);
				buyItNow();	
				cancel_order();
				//chg_payMethod();
				getStacking(msg.orderBaseInfoVo.statusCode,msg.orderBaseInfoVo.orderId,msg.commoSummaryVoList.skuCode);
	})
	
	$('#goBack').attr('href','./'+fromPage+'.html');
}//订单详情页

//修改支付方式
function chg_payMethod(){
	
}

//获取物流信息
function getStacking(statusCode,orderId,skuCode){
	if(statusCode!=8){
		var htm = '';
		Xiu.runAjax({
			url :'/myorder/getCarryInfoRemote.shtml?orderId='+orderId+'&skuCode='+skuCode,//'/myorder/getCarryInfoRemote.shtml?orderId=4043838&skuCode=110617300001',
			async : false,		
			sucessCallBack : function(info){				
				if(info.result && info.carryInfos != ''){
					var track_htm = '';					
					for( var i = info.carryInfos.length-1 ; i >=0; i-- ){				
						track_htm +='<li><p>'+info.carryInfos[i]['detail']+'</p><p class="date">'+info.carryInfos[i]['carryTime']+'</p><span class="s-ico"></span></li>';
					}
				htm+='<div class="pay_fs  tracking">'
					+'	<div class="track-area">'
				 	+'  	<h6>订单跟踪</h6>'
					+'		<ul class="track-list">'	
					+''+track_htm+''
					+' 		</ul>'
					+'	</div>'
					+'</div>';					
					$('.pay_fs').after(htm);
				}
			}
		})			
	}	
}//end 	getStacking

function buyItNow(){
	//待付款订单立即购买，撤销订单的重新购买
	
	var payMethod_htm = '';		

	if (Xiu.isWechatBrowser() && !Xiu.chkForbidWechat()) {
		payMethod_htm +='<li  data-val="WeChat"><span class="icons wx"></span><span class="radio"><em></em></span></li>'
			+'<li  data-val="AlipayWire"><span class="icons zfb"></span><span class="radio"><em></em></span></li>';	
							
	}else{
		payMethod_htm +='<li  data-val="AlipayWire"><span class="icons zfb"></span><span class="radio"><em></em></span></li>';
				//+'<li class="bankCard" data-val="card"><span class="icons card"></span><span class="radio"><em></em></span></li>';
	}	
	html ='	<div class="paylist-area">'
			+'	<div class="pay-hd"><h6>选择支付方式</h6><span class="closeLayer">关闭</span></div>'
			+'	<ul class="pay-list">'
			+''+payMethod_htm+''
			+'	</ul>'
			+'</div>';
	$('.J-buyItNow').click(function(){
		var $buyBtn = $(this),
			orderId = $buyBtn.data('orderid'),
			payType = $buyBtn.data('paytype'),
			url = $buyBtn.data('url');			
		Dialog.show({html:html,top:'0%',height:'100%',callBack: function(){
			var $pay_area = $('.paylist-area'),
				$payLi = $('.pay-list li');														  
			
			//默认选中原来的支付方式
			/*$payLi.each(function(){
				var data = $(this).data('val');					
				if(data == payType){
					$(this).addClass('checked');
				}				
			})*/			
			$pay_area.show().animate({'bottom':0});			
			$payLi.click(function(){
				var $t = $(this)
					val = $t.data('val');
					url = url+'&payReqVO.payType='+val;					
					if(val == payType){										
						gotoPay(url);							
						return false;
					}else{
						$t.siblings('li').removeClass('checked');
				 		 $t.addClass('checked'); 						
						 Xiu.runAjax({
							url :'/order/updatePayMethod.shtml?payMethodIn.orderId='+orderId+'&payMethodIn.payType='+val,
							async : false,	
							sucessCallBack : function(msg){
								if(msg.result){									
									gotoPay(url);
								}else{
									if(val == payType){	
										gotoPay(url);	
									}else{
										Dialog.tip({txt:msg.errorMsg,autoHide: true});
									}
									
								}
								 
							}
						})	
					}	 		
			})
			
			$('.closeLayer').click(function(){
				$pay_area.show().animate({'bottom':'-165px'},function(){
					Dialog.hide();
				});
				
			})
			
		}})
	})
	
	function gotoPay(url, callBack){
		Xiu.runAjax({
			url : url ,
			beforeSend : function(){
				Dialog.tip({txt: '正在前往支付页面',top:'0%',height:'100%'});
			},
			sucessCallBack : function(msg){
				if(msg.result){
					if(typeof callBack == 'function'){
						callBack();
					}
					location.href = msg.payInfoVO.payInfo;
					Dialog.hide();
				}else{
					Dialog.tip({txt:msg.errorMsg,autoHide: true})
				}
			}
		})
	}
}//end buyItNow

//取消订单
function cancel_order(){
	$('.cancel_btn').click(function(){
		var orderId = $(this).attr('orderid'),
			del_bool = window.confirm('确认要撤销该订单？');
		//return false;
		if(del_bool){
			
			Xiu.runAjax({
				url : '/order/cancelOrder.shtml?cancelVo.orderId='+orderId ,		
				sucessCallBack : function(msg){
					if(msg.result){				
						Dialog.tip({txt:'订单撤销成功',autoHide: true,callBack : function(){
							location.href = 'http://m.xiu.com/myxiu/'+fromPage+'.html';
						}})
					}else{
						Dialog.tip({txt:msg.errorMsg,autoHide: true,callBack : function(){
							
						}})
					}
				}
			})	
		}
	})
}//end cancel_order


//地址管理
$('#J-go-addressList').click(function(){
	var val = location.href;
	Xiu.GobackUrl.set( 'toUrl',val, function(msg){
		if( msg.result ){			
			location.href = 'http://m.xiu.com/order/addressList.html?from=myxiu';			
		}
	});
})
