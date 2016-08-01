// 收货人
jQuery.validator.addMethod("rcverName", function(value, element) {
	var length =  value.replace (/[^\x00-\xff]/g,"rr").length;   
	return this.optional(element) || (length >= 4 && length <= 20);
}, ""); 

//收货地址
jQuery.validator.addMethod("addressInfo", function(value, element) {
	var length =  value.replace (/[^\x00-\xff]/g,"rr").length;   
	return this.optional(element) || (length >0 && length <= 70);
}, ""); 

// 邮编
jQuery.validator.addMethod("postCode", function(value, element) {
	var length = value.length;
	var rePostCode= /^[0-9]{6}$/;
	return this.optional(element) || (length == 6 && rePostCode.test(value));
}, "邮编格式错误"); 

//区号
jQuery.validator.addMethod("areaCode", function(value, element) {
	var length = value.length;
	var areaCode =/^[\d]{3,4}$/;
	return this.optional(element) || areaCode.test(value);
}, "区号格式错误");  

//固话
jQuery.validator.addMethod("phone", function(value, element) {
	var length = value.length;
	var phone =/^[\d]{7,8}$/;
	return this.optional(element) || phone.test(value);
}, "固话格式错误");  

// 增加手机号码验证
jQuery.validator.addMethod("mobile", function(value, element) {
	var length = value.length;
	var reg = /^1[3458]{1}[0-9]{9}$/;
	return this.optional(element) || (length == 11 && reg.test(value));
}, "手机号码格式错误");

//非手机格式
jQuery.validator.addMethod("noMobile", function(value, element) {
	if(value==""){
		return true;
	}
	var length = value.length;
	var reg = /^1[3458]{1}[0-9]{9}$/;
	return !(this.optional(element) || (length == 11 && reg.test(value)));
}, "不能输入手机号码");

//非Email格式
jQuery.validator.addMethod("noEmail", function(value, element) {
	if(value==""){
		return true;
	}
	return !(this.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value));
}, "不能输入邮箱");

	
// 字节长度验证
jQuery.validator.addMethod("byteRangeLength", function(value, element, param) {
	var length = value.length;
	for ( var i = 0; i < value.length; i++) {
		if (value.charCodeAt(i) > 127) {
			length++;
		}
	}
	return this.optional(element) || (length >= param[0] && length <= param[1]);
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));

//字母和数字的验证
jQuery.validator.addMethod("chrnum", function(value, element) {
    var reg = /^([a-zA-Z0-9]*)$/;
    return this.optional(element) || (reg.test(value));
}, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

//字母、数字、-和_ 的验证
jQuery.validator.addMethod("chrnum2", function(value, element) {
	var reg = /^([\w\-]*)$/;
	return this.optional(element) || (reg.test(value));
}, '只能输入字母，数字及"_"、"-"');

//中文
jQuery.validator.addMethod("chinese", function(value, element) {
	var reg = /^[\u4e00-\u9fa5]+$/;
	return this.optional(element) || (reg.test(value));
}, "只能输入中文");

//非中文
jQuery.validator.addMethod("noChinese", function(value, element) {
	var reg = /[\u4E00-\u9FA5\uF900-\uFA2D]/;
	return !(this.optional(element) || reg.test(value));
}, "不能输入中文");

//不相等
jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	return value != $(param).val();
}, "不能输入相同的值");

//验证值小数位数不能超过两位
jQuery.validator.addMethod("decimal", function(value, element) {
	var reg = /^-?\d+(\.\d{1,2})?$/;
	return this.optional(element) || (reg.test(value));
}, "请输入数字且小数位数不能超过两位.");



