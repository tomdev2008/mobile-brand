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
			<h3 class="topTitle fb f14">编辑用户</h3>
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
			<dd>系统管理</dd>
			<dd><a href="${rc.getContextPath()}/user/list?user_name=${(user_name!'')?html}&status=${(status!'')?html}">用户管理</a></dd>
			<dd class="last"><h3>编辑用户</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑用户</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>用户名称：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${user.id!''}"/>
				    <input type="hidden" name="user_role" id="user_role"/>
				     <input type="hidden" name="olduserName" value="${user.username!''}"/>
					<input type="text" name="username" id="username" value="${user.username!''}" style="width:300px;"/>
					<span id="userName_c" style="color: red"></span>
				</td>
			</tr>
			<!--
			<tr>
				<th class="thList" scope="row">用户密码：</th>
				<td class="tdBox">
					<input type="text" name="password" id="password" value="${user.password}" style="width:300px;"/>
				</td>
			</tr>-->
			<tr>
				<th class="thList" scope="row">用户邮箱：</th>
				<td class="tdBox">
					<input type="text" name="email" id="email" value="${user.email!''}" style="width:300px;"/>
					<span id="email_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">用户电话：</th>
				<td class="tdBox">
					<input type="text" name="phoneNumber" id="phoneNumber" maxlength="11" value="${user.phoneNumber!''}" style="width:300px;"/>
					<span id="phoneNumber_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
                    <#if user?? && user.status == 1>
				     <input type="radio" name="status" id="status_01" value="1" checked="true"/><label>启用</label>
				   <#else>
				     <input type="radio" name="status" id="status_01" value="1" /><label>启用</label> 
				   </#if>
				   <#if user?? && user.status == 0>
				     <input type="radio" name="status" id="status_02" value="0" checked="true"/><label>停用</label>
				   <#else>
				     <input type="radio" name="status" id="status_02" value="0"/><label>停用</label>
				   </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">所属角色：</th>
				<td class="tdBox">
					  <#if rolelist??>
                            <#list rolelist as role>
                               <input type="checkbox" value="${role.id!''}"
                               <#if userrolelist??>
                                 <#list userrolelist as userrole>
                                    <#if userrole.roleId==role.id>
                                      checked="true"
                                    </#if>
                                 </#list>
                               </#if>
                               /><label>&nbsp;${role.roleName}&nbsp;</label>
                            </#list>
                      </#if>      
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
      var userName=$("#username").val();
      var email=$("#email").val();
      var phoneNumber=$("#phoneNumber").val();
      if(userName==""){
        $("#userName_c").text("用户名称不能为空！");
        return false;
      }else{
        $("#userName_c").text("");
      }
      if(email!=""){
         if (/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email) == false) {
              $("#email_c").text("邮箱格式不正确，请重新填写");
              return false;
          }else{
            $("#email_c").text("");
          }
      }
      
      if(phoneNumber!=""){
          if(isNaN(phoneNumber)){
            $("#phoneNumber_c").text("电话号码只能是数字!");
            return false;
          }else{
            $("#phoneNumber_c").text("");
          }
      }
      var user_role="";//权限
      $("input[type='checkbox']:checkbox:checked").each(function(){ 
           if(user_role==""){
              user_role+=$(this).val();
           }else{
             user_role+=","+$(this).val(); 
           }
           
        }) 
      //将对用户赋予的权限给隐藏域，以便提交表单  
      $("#user_role").val(user_role); 
      //提交信息  
      checkInfo();
    }
    
    //用Ajax提交信息
	function checkInfo(){
	    var params=$("#addUserForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/user/edit?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/user/list";
								}else{
								  $("#username").focus();
								  $("#userName_c").text(objs.data);
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
	  location.href = "${rc.getContextPath()}/user/list?user_name=${(user_name!'')?html}&status=${(status!'')?html}";
	}
</script>
</html>