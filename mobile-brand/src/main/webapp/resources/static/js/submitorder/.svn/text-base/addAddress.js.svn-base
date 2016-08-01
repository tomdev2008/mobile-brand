/*添加收货地址 leslie qin 2014-3-18*/
$(function(){
var	patterns =	{
		mobile: function(text){
			return	/^0?(13|15|18|14)[0-9]{9}$/.test(text);
		}
	},
 backUrl = '',
 url = location.href,
 submitUrl = '/address/saveAddressRemote.shtml'
 addressId = Xiu.getUrlProperty()['addressId'],
 type = Xiu.getUrlProperty()['type'],
 from = Xiu.getUrlProperty()['from'];

//修改收货地址
if(type == 'edit' && addressId != '' ){
	submitUrl = '/address/updateAddressRemote.shtml';
	$('#addressId').val(addressId);
	Xiu.runAjax({
			url : '/address/getAddressDetailRemote.shtml',
			data:{addressId : addressId},
			sucessCallBack : function( msg){																								
				if( msg.result ){			
					var mobile = '',
						province_htm = '',
						city_htm = '',
						region_htm = '';
					if(msg.addresses.addressDetail['mobile']!= '' && msg.addresses.addressDetail['mobile'] != undefined ){
						mobile = msg.addresses.addressDetail['mobile'];
					}
					//获取省
					$.each(msg.addresses.list_province, function(i,p){
						var selected = '';					
						if( msg.addresses.addressDetail.provinceCode == p.paramCode ){
							selected = 'selected=selected';
						}
						province_htm +='<option '+selected+' value='+p.paramCode+'>'+p.paramDesc+'</option>'									
					})
					//获取市
					//console.log(msg.addresses.list_city );
					$.each(msg.addresses.list_region, function(i,p){
						var selected = '';			
						if( msg.addresses.addressDetail.regionCode == p.paramCode ){
							selected = 'selected=selected';
						}
						region_htm +='<option '+selected+' value='+p.paramCode+'>'+p.paramDesc+'</option>'									
					})
					//获取区				
					if( typeof msg.addresses.list_city == 'undefined'  ){
						city_htm = '<option value="">请选择</option>';
					}else{
							
						$.each(msg.addresses.list_city, function(i,p){
							var selected = '';			
							if( msg.addresses.addressDetail.cityCode == p.paramCode ){
								selected = 'selected=selected';
							}
							city_htm +='<option '+selected+' value='+p.paramCode+'>'+p.paramDesc+'</option>'									
						})
						
					}
					
					$('#rcverName').val(msg.addresses.addressDetail['rcverName']);
					$('#mobile').val(mobile);
					$('#province').html(province_htm);
					$('#region').html(region_htm);
					$('#city').html(city_htm);
					
					$('#postCode').val( msg.addresses.addressDetail['postCode'])
					$('#det-addr-ipt').val(msg.addresses.addressDetail['addressInfo']);
				}else{
					if( msg.errorCode == 4001 ){
						Xiu.GobackUrl.ToLogin(url);
					}else{						
						Dialog.tip({txt: msg.errorCode+'--'+msg.errorMsg,autoHide: true});	
					}
				}																										
			}
	})
}else{
	//如果市添加地址，则先获取省份
	Xiu.runAjax({
		url : '/address/initAddAddressRemote.shtml',
		data:{addressId : addressId},
		sucessCallBack : function( msg){	
			if( msg.result ){	
				var province_htm = '<option value="" >请选择</option>';				
				$.each(msg.provinces, function(i,p){			
					province_htm +='<option  value='+p.paramCode+'>'+p.paramDesc+'</option>'	;								
				})	
				$('#province').html(province_htm);
			}else{
				Dialog.tip({txt: msg.errorCode+'--'+msg.errorMsg,autoHide: true});	
			}		
		}		
	})
}

//提交地址	
$('#editAddrForm').on('submit', function(){		
	var data = $(this).serialize(),
		$rq = $(this).find('.rq'),
		rq_len = $rq.length,
		k = 0;				
		if( type == 'edit' ){
			data = data+'&addressVo.addressId='+addressId;	
		}	
		for(var i = 0 ; i < rq_len; i++){
				var sibling_text = $rq.eq(i).parents('.add-itm').find('label').html(),
				sibling_text = sibling_text.substring(0,sibling_text.length-1),
				val = $rq.eq(i).val(),
				name = $rq.eq(i).attr('name'),
				tagName = $rq.eq(i).attr('tagName').toLowerCase();
				
				if( val == '' || (tagName == 'select' && val == '请选择') ){					
					Dialog.tip({txt: '请填写'+sibling_text,time: 1000, autoHide : true});	
					break;
					return false;
				}else{
					if( name == 'addressVo.mobile' && !patterns.mobile(val) ){						
						Dialog.tip({txt: '请输入正确的手机号码',time: 1000, autoHide : true});	
						break;
						return false;
					}
					k++;
				}			
			}
		if( k == rq_len ){		
			Xiu.runAjax({
				url : submitUrl,
				data:data,
				sucessCallBack : function( msg){	
					if( msg.result ){	
						var text = '成功添加新地址';
						if( type == 'edit' ){
							text = '地址更新成功';
						}
						//获取跳转url
						if(from == 'order'){
							Dialog.tip({txt:text,time: 1000, autoHide : true, callBack : function(){
								Xiu.GobackUrl.get('toUrl',function(msg){
									if( msg.result ){		
										location.href = decodeURIComponent(msg.toUrl);		
									}
								});
							}})							
						}else{
							Dialog.tip({txt:text,time: 1000, autoHide : true, callBack : function(){
								location.href = 'http://m.xiu.com/order/addressList.html?from=myxiu';
							}})										
						}										
					}else{							
						Dialog.tip({txt: msg.errorCode+'--'+msg.resultMsg,autoHide: true});	
					}		
				}		
			})		
		}	
		return false;
})

//省市联动
$('#province').on('change', function(){
	var parentCode = $(this).val();
	Xiu.runAjax({
		url : '/address/getAddressParamRemote.shtml',
		data:{parentCode:parentCode,paramType:2},
		sucessCallBack : function( msg){	
				var regionHtml = '<option>请选择</option>';
			$.each(msg.prcVos, function(i,p){
				regionHtml +='<option  value='+p.paramCode+'>'+p.paramDesc+'</option>';			
			})
			$('#region').html(regionHtml);
			$('#city').html('<option>请选择</option>');	
		}		
	})						 
})
$('#region').on('change', function(){
	var parentCode = $(this).val();
	Xiu.runAjax({
		url : '/address/getAddressParamRemote.shtml',
		data:{parentCode:parentCode,paramType:3},
		sucessCallBack : function( msg){	
			var cityHtml = '<option>请选择</option>';
			$.each(msg.prcVos, function(i,p){
				cityHtml +='<option  value='+p.paramCode+'>'+p.paramDesc+'</option>';			
			})
			$('#city').html(cityHtml);		
		}		
	})							 
})

//删除地址	
var del_address = function(){
		$('#J-del-address').on('click', function(){			
			if(addressId!=''){
				var	del_bool = window.confirm('确认要撤销该地址？');
				if(del_bool){		
					Xiu.runAjax({
						url : '/address/delAddressRemote.shtml',
						data:{addressId:addressId},
						sucessCallBack : function( msg){	
							if( msg.result ){						
								Dialog.tip({txt:'删除地址成功',time: 800, autoHide : true, callBack : function(){
									  //获取跳转url	
									Xiu.GobackUrl.get('toUrl',function(msg){
										if( msg.result ){		
											location.href = decodeURIComponent(msg.toUrl);		
										}
									});	
								}});						
								
							}else{
								Dialog.tip({txt: msg.errorCode+'--'+msg.resultMsg,autoHide: true});						
							}	
						}		
					})
				}
			}			
		})			
	}()	
	if( from != 'order'  ){	
		$('#goBack').attr('href','http://m.xiu.com/order/addressList.html?from=myxiu');
	}
})//end $function
