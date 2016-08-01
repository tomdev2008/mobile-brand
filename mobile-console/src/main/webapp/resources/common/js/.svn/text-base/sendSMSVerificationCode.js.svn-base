/*+++++++++++++++++
+ 发送短信
+ mobileNum 手机号
+ type 验证码类型
+ obj 发送按钮对象，必须是jquery对象
+ 依赖jquery.js, xiupop.js
+  返回对象returnObj
if(returnObj.data == true  && returnObj.scode == "0"){
    xiuTips("短信已发送，请注意接收！", offset.left, offset.top-45, 3);
}else{
   xiuTips(returnObj.smsg, offset.left, offset.top-60, 3);
}
如果returnObj是null,则发生异常
++++++++++++++++++*/
function sendVerificationCode(mobileNum, type){
	var returnObj = null;
    $.ajax({
          type: "POST",
          async:false,
          url: xiuPortal.constants.XIU_PORTAL_CONTEXTPATH + "/sms/create?format=json",
          data: "phoneNum="+mobileNum+"&type="+type,
          dataType : "text",
          success : function(data, textStatus) {
        	  if (isNaN(data)) {
        		  var objs =  $.parseJSON(data);
        		  if (objs != null) {
        			  returnObj = objs; 
        		  }
        	  }
          },
          error: function(){}
      });
    return returnObj;
}

/**
 * 检查手机是否重复并发送随机验证码
 * @param mobileNum
 * @param type
 * @returns
 */
function checkMobileAndSendCode(mobileNum, type){
	var returnObj = null;
    $.ajax({
          type: "POST",
          async:false,
          url: xiuPortal.constants.XIU_PORTAL_CONTEXTPATH + "/sms/change/send?format=json",
          data: "phoneNum="+mobileNum+"&type="+type,
          dataType : "text",
          success : function(data, textStatus) {
        	  if (isNaN(data)) {
        		  var objs =  $.parseJSON(data);
        		  if (objs != null) {
        			  returnObj = objs; 
        		  }
        	  }
          },
          error: function(){}
      });
    return returnObj;
}