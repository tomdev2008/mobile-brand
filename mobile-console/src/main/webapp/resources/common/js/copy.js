function copyContent(copyCon){
	var flashvars = {
		content: encodeURIComponent(copyCon),
		//uri: '../resources/common/images/flash_copy_btn.png'
		uri: '../resources/portal/img/copy.png'
	};
		 
	var params = {
		wmode: "transparent",
		allowScriptAccess: "always"
	};
	
//	swfobject.embedSWF("../resources/portal/flv/clipboard.swf", "forLoadSwf", "52", "25", "9.0.0", null, flashvars, params);
	swfobject.embedSWF("../resources/portal/flv/clipboard.swf", "forLoadSwf", "83", "27", "9.0.0", null, flashvars, params);
}

/*
var flashvars = {
	content: encodeURIComponent(copyCon),
	uri: '../image/flash_copy_btn.png'
};
 
var params = {
	wmode: "transparent",
	allowScriptAccess: "always"
};
 
swfobject.embedSWF("../js/clipboard.swf", "forLoadSwf", "52", "25", "9.0.0", null, flashvars, params);
*/
function copySuccess(){
	//flash回调
	alert("复制成功！");
}