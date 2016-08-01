var patterns = {
	email: function(text){
		return /^(?:[a-z0-9]+[_\-+.]?)*[a-z0-9]+@(?:([a-z0-9]+-?)*[a-z0-9]+.)+([a-z]{2,})+$/i.test(text);
	},
	mobile: function(text){
		return	/^0?(13|15|18|14)[0-9]{9}$/.test(text);
	},

	// 座机：仅中国座机支持；区号可有 3、4位数并且以 0 开头；电话号不以 0 开头，最 8 位数，最少 7 位数
	//  但 400/800 除头开外，适应电话，电话本身是 7 位数
	// 0755-29819991 | 0755 29819991 | 400-6927972 | 4006927927 | 800...
	tel: function(text){
	  return /^(?:(?:0\d{2,3}[- ]?[1-9]\d{6,7})|(?:[48]00[- ]?[1-9]\d{6}))$/.test(text);
	},
	password: function(text){
	  return this.text(text);
	},
	pwdFormat : function( text ){
		return /^.*([\W_a-zA-z0-9-])+.*$/i.test(text);
	}
}

// 设置提示文本	
var validate_Text = {
	userName: {
		onFocus: '输入邮箱/手机号',
		succeed: '',
		isEmpty: '请输入手机号码',
		isEmpty_log:'请填写登录邮箱或手机号码',
		error:{
			tel_notexist_err: '您输入的手机号码不存在',
			email_notexist_err: '您输入的邮箱地址不存在',
			Format_err: '请输入正确的手机号码',			
			log_err:'密码与用户名不匹配，请您核对后再次输入！',
			reg_err :'该号码已注册，请登录'
		}
	},
	pwd: {
		onFocus: '<span>6-20位字符，可使用字母、数字或符号组合，不建议使用纯数字，纯字母，纯符号</span>',
		succeed: '',
		log_isEmpty: '请输入密码',
		isEmpty: '请输入6-16位密码！',
		error:{				
			Format_err: '密码只能由英文、数字及标点符号组成',
			len_err:'密码长度只能在6-16位字符之间'
		}
	},
	confirm_pwd: {
		onFocus: '请再次输入密码',
		succeed: '',
		isEmpty: '请再次输入密码',
		error:{
			not_same_err: '两次输入密码不一致',
			Format_err: '密码只能由英文、数字及标点符号组成',
			len_err:'密码长度只能在6-16位字符之间'
		}
	},
	msg_code : {
		onFocus: '请输入短信验证码',
		succeed: '',
		isEmpty: '请输入6位短信验证码',
		error:{				
			not_same_err :'您输入的验证码有误，请输入正确的验证码'
		}	
	},	
	verify : {
		onFocus: '请输入验证码',
		succeed: '',
		isEmpty: '请输入6位短信验证码',
		error:{				
			not_same_err :'您输入的验证码有误，请输入正确的验证码'
		}	
	}
}
//验证格式
var validate_Format = {
	isEmpty: function( str ){
		return ($.trim(str) == "" || typeof str != "string");
	},
	range : function( str, min, max ){
		return (str.length >= min && str.length <= max );
	},
	is_pwd : function( text ){
		return patterns.pwdFormat( text );
	},
	is_confirm_pwd : function( text1, text2 ){
		return (text1 == text2)
	},
	is_email : function( text ){
		return patterns.email( text );
	},
	is_mobile : function( text ){
		return patterns.mobile( text );
	},
	is_tel : function( text ){
		return patterns.tel( text );
	}
}
var validateSettings = {
	bindClear : true,
	tip: {
		timer : '',
		style:'err',
		set: function( option ){					
			clearTimeout(validateSettings.tip.timer);
			option.ele.html(option.txt).show();
			validateSettings.tip.timer = setTimeout(function(){option.ele.hide();},2000)}
		}		
	}

var validate_callBack = {
		blur:function(){},
		focus: function(){},
		keyup: function($t){
			var $parent = $t.parents('.f-field');
			if( $t.val() != '' ){
				$parent.find('button').show();	
			}else{
				$parent.find('button').hide();	
			}		
			if(validateSettings.bindClear){
				$('.f-field').find('button').click(function(){
					$(this).parents('.f-field').find('input').val('');
					$(this).hide();
				})
				validateSettings.bindClear = false;				
			}
		},
		form_submit: function($form){	
				var $userName = $('#username');
				var $pwd = $('#pwd');
				var username = $userName.val();
				var pwd = $pwd.val();
				if( $.trim(username) == ''){
					validateSettings.tip.set({
						ele:$('.login-tip'),
						txt:validate_Text.userName.isEmpty_log,
						hideCls:'none'
					});
					$userName.focus();
					return false;
				}else if($.trim(pwd) == ''){
					validateSettings.tip.set({
						ele:$('.login-tip'),
						txt:validate_Text.pwd.log_isEmpty,
						hideCls:'none'
					});
					$pwd.focus();
					return false;
				}else{
					Xiu.runAjax({
						url:$form.attr("action"),
						data: $form.serialize(),
						sucessCallBack : function(callback){
							if(callback.result){
								Dialog.tip({txt: '登录成功！',autoHide:true, time: 800,callBack: function(){
									location.href = decodeURIComponent(callback.targetUrl);
								}})	 	
							}else{
								validateSettings.tip.set({
									ele:$('.login-tip'),
									txt:callback.errorMsg,
									hideCls:'none'
								})	
							}	
						}
					})									
				}				
			return false;
		}
	}

//@vt 主函数
$.fn.xiuValidator = function(options){	
	var $form = $(this);
	$form.find('input[type="password"], input[type="text"]').focus(function(){
		validate_callBack.focus($(this),$(this).attr('name'),$form );
	}).blur(function(){
		validate_callBack.blur($(this),$(this).attr('name'),$form );
	}).keyup(function(){
			validate_callBack.keyup($(this),$(this).attr('name'),$form );
	});
	$form.on('submit', function(){					
		return validate_callBack.form_submit( $(this) );
	});	
}	