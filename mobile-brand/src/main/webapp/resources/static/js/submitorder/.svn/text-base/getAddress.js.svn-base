$(function(){
/*
* 页面加载时获取地址信息	
*/
//获取地址列表
var	from = Xiu.getUrlProperty()['from'],
	url = location.href;	
var getAddress = function(){	
	Xiu.runAjax({
		url:'/address/getAddressListRemote.shtml',					
		sucessCallBack: function(msg){	
			if( msg.result ){
				var $addr_area = $('#address'),							
					addr_htm = '';
				if(msg.adressList == '' || msg.adressList == null){
					addr_htm ='<a href="http://m.xiu.com/order/address.html?from=myxiu&type=add&addressId=">请填写收货地址</a>';
				}else{
					$.each(msg.adressList,function(index, data){
						var selected = '',
							ico = '',
							tel = data.mobile;
						  if( data.mobile == 'undefined' ){
								tel = data.phone; 
							}else if( data.mobile = 'undefined' && data.phone == 'undefined' ){
								tel = '';
							}
						if(data.isMaster == 'Y'){
							selected = 'selected';
							ico= '<i class="icons selected-arrow"></i>' ;
						}
						addr_htm +='<div class="area-box address-area '+selected+'">'					
								+'<ul class="addr-list">'
								+'<li>'+data.rcverName+'<span class="mobile">'+tel+'</span></li>'								
								+'<li class="dt-addr">'+data.addressPrefix+data.addressInfo+'<span style="margin-left:10px;">'+data.postCode+'</span></li>'
								+'</ul><span class="icons"></span><a class="edit" href="javascript:;" ></a><input type="hidden" class="addressId" value="'+data.addressId+'" />'+ico+'</div>';
							
					})
					
				}					
				$addr_area.html(addr_htm);
				init();
			}else{
				if( msg.errorCode == 4001 ){
					Xiu.GobackUrl.ToLogin(url);
				}else{
					Dialog.tip({txt: msg.errorCode+'--'+msg.errorMsg});					
				}						
			}													
		}
   });		
	if( from != 'order'  ){	
		$('#goBack').attr('href','http://m.xiu.com/myxiu/index.html');
	}
}()	
	
function init(){
	//点击编辑地址
	$('.edit, .addNewAddr').click(function(e){	
		var addrId = $(this).siblings('.addressId').val(),
			url = location.href,
			type = 'edit';	
		//如果从订单进入列表点击添加
		if( ($(this).is('.addNewAddr') && from == 'order') || ($(this).is('.addNewAddr') && from == 'myxiu')  ){
			addrId = '';
			type = 'add';
		}		
		location.href = 'address.html?from='+from+'&type='+type+'&addressId='+addrId;
		return false;		
	})		
	//点击选择地址
	$('.address-area .addr-list').click(function(){
			if( $(this).is('.selected') ){
				return false;
			}else{				
				$('.address-area').removeClass('selected');
				$(this).parents('.address-area').addClass('selected').append('<i class="icons selected-arrow"></i>');
				var addressId = $(this).parents('.address-area').find('.addressId').val();
				Xiu.runAjax({
					url : '/address/setMasterAddressRemote.shtml',
					data : {addressId : addressId},
					sucessCallBack : function(msg){
						if(msg.result){
							//如果从我的走秀进入则不跳转
							if(from != 'myxiu'){
								//获取跳转url									
								Xiu.GobackUrl.get('toUrl',function(msg){
									if( msg.result ){		
										location.href  = decodeURIComponent(msg['toUrl']);			
									}
								});	
							}else{							
								Dialog.tip({txt: '成功设置默认地址',time: 800,autoHide: true});	
							}								
						}
					}
				})
				
									
			}
	})	
}	
})