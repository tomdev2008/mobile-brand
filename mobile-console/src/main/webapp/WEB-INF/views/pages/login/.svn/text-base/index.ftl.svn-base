<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js" defer="defer"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css"  >
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_login.css"  >
<title>走秀网后台管理系统</title>
</head>
<body>
<form id="loginForm" name="loginForm">
<table width="1003" align="center" cellpadding="0" cellspacing="0"
	class="table">
	<tr>
		<td height="550" valign="top">
		<table width="300px" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="340" colspan="2">&nbsp;</td>
			</tr>
			<tr>
				<td width="94" height="36">&nbsp;</td>
				<td valign="top">
					<input type="text" name="loginName" value="" id="input_loginName" class="input w130"/>
				</td>
			</tr>
			<tr>
				<td width="94" height="36">&nbsp;</td>
				<td valign="top">
					<input type="password" name="password" id="input_password" class="input w130"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<table width="250" border="0" cellpadding="0" cellspacing="0"
					class="mt15">
					<tr>
						<td width="35"></td>
						<td width="135">
							<div id="errorMessage" style="color:red">
							</div>
						</td>
						<td height="30">
						    <a href="javascript:void(0);" title="登录" onclick="return checkInput()"><img src="${rc.getContextPath()}/resources/manager/images/btn_login.gif"></img></a>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="text-align: center; font-size: 14px; font-weight: bold;">XIU2.0客服服务中心</td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
  //界面加载 指针定位到帐号输入框
     $(document).ready(function(){
         $("#input_loginName").focus();
     });
     
    //用户登录验证
	function checkInput() {
	   var loginName = $("#input_loginName").val();
	   var password = $("#input_password").val();
		if ("" == loginName) {
			$("#errorMessage").text("提示:帐号不能为空！");
			return false;
		}
		if ("" == password) {
			$("#errorMessage").text("提示:密码不能为空！");
			return false;
		}
		checkInfo();
        return true;
	}

    //回车事件
	document.onkeydown=function(event){
		var event = window.event?window.event:event;
        if(event.keyCode==13)     //判断回车按钮事件
        {
        	event.returnValue=false;
            event.cancel = true;
        	if(checkInput() == true){
        	   checkInfo();
        	}
        }
	};
	
	//用Ajax验证登录信息
	function checkInfo(){
	    var loginName = $("#input_loginName").val();
	    var password = $("#input_password").val();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/login/check?format=json",
					data : {"loginName":loginName, "password":password},
					async: false,
	                dataType: "text",
	                timeout : 60000,
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								    //登录成功后，跳转到主菜单界面
								    location.href = "${rc.getContextPath()}/menu/index";
								}else{
								    if(objs.scode == "1")
								    {
								      $("#input_loginName").val("");
	                                  $("#input_password").val("");
	                                  $("#input_loginName").focus();
								    }else{
	                                  $("#input_password").val("");
	                                  $("#input_password").focus();
								    }
								    $("#errorMessage").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
</script>
</html>