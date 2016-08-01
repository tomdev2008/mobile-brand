<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">修改密码</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="addUserForm" name="addUserForm">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<#if "${param!''}" != "top">
			    <dd>系统管理</dd>
				<dd><a href="${rc.getContextPath()}/user/list?user_name=${(user_name!'')?html}&status=${(status!'')?html}">用户管理</a></dd>
			</#if>
			<dd><h3>用户:${user.username!''}</h3>
			<dd class="last"><h3>修改密码</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row">
				   <span class="cBlue f14 fb pl20">修改密码</span>
				   <span id="user_c" class="f14 fb pl20" style="color: red"></span>
				</th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>用户名称：</th>
				<td class="tdBox">
				     <input type="hidden" name="id" value="${user.id!''}"/>
				     <input type="hidden" name="param" id="param" value="${param!''}"/>
					 <input type="text" name="username" id="username" value="${user.username!''}" style="width:300px;" readonly="true"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>我的旧密码：</th>
				<td class="tdBox">
					<input type="password" name="password" id="password" value="" style="width:300px;"/>
					<span id="password_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>我的新密码：</th>
				<td class="tdBox">
					<input type="password" name="newpassword" id="newpassword" value="" style="width:300px;"/>
					<span id="newpassword_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>确认新密码：</th>
				<td class="tdBox">
					<input type="password" name="_newpassword" id="_newpassword" value="" style="width:300px;"/>
					<span id="_newpassword_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkUser();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnUserList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">

    //验证信息
    function checkUser(){
      var password=$("#password").val();
      var newpassword=$("#newpassword").val();
      var _newpassword=$("#_newpassword").val();
      if(password==""){
        $("#password_c").text("我的旧密码不能为空！");
        $("#password").focus();
        return false;
      }else{
        $("#password_c").text("");
      }
      
      if(newpassword==""){
        $("#newpassword_c").text("我的新密码不能为空！");
        $("#newpassword").focus();
        return false;
      }else{
        $("#newpassword_c").text("");
      }
      
      if(_newpassword==""){
        $("#_newpassword_c").text("确认新密码不能为空！");
        $("#_newpassword").focus();
        return false;
      }else{
        $("#_newpassword_c").text("");
      }
      
      if(newpassword!=_newpassword){
        $("#_newpassword_c").text("确认新密码和您输入的新密码不一致！");
        $("#_newpassword").focus();
        return false;
      }else{
        $("#_newpassword_c").text("");
      }
      
      //提交信息  
      checkInfo();
    }
    
    //用Ajax提交信息
	function checkInfo(){
	    var params=$("#addUserForm").serialize();
	    var param=$("#param").val();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/user/editpwd?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								  if(param=='top'){
								    //保存成后,刷新列表页
								    location.href = "${rc.getContextPath()}/menu/menu_main"; 
								  }else{
								     //保存成后,刷新列表页
								    location.href = "${rc.getContextPath()}/user/list";
								  }
								}else if(objs.scode == "1"){
								  $("#password").focus();
								  $("#password_c").text(objs.data);
				            	}else{
				            	   $("#user_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
	
	//取消事件，返回用户管理列表
	function returnUserList(){
	  var param=$("#param").val();
	  if(param=='top'){
	     location.href = "${rc.getContextPath()}/menu/menu_main";
	  }else{
	     location.href = "${rc.getContextPath()}/user/list?user_name=${(user_name!'')?html}&status=${(status!'')?html}";
	  }
	}
</script>
</html>