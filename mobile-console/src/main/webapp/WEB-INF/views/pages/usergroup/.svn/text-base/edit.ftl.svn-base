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
			<h3 class="topTitle fb f14">编辑用户组</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="editUserGroupForm" name="editUserGroupForm">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>系统管理</dd>
			<dd><a href="${rc.getContextPath()}/usergroup/list?usergroup_name=${(usergroup_name!'')?html}&status=${(status!'')?html}">用户组管理</a></dd>
			<dd class="last"><h3>编辑用户组</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑用户组</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>用户组名称：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${usergroup.id!''}"/>
				    <input type="hidden" name="user_usergroup" id="user_usergroup"/>
				     <input type="hidden" name="olduserGroupName" value="${usergroup.userGroupName!''}"/>
					<input type="text" name="userGroupName" id="userGroupName" value="${usergroup.userGroupName!''}" style="width:300px;"/>
					<span id="userGroupName_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">用户组描述：</th>
				<td class="tdBox">
					<textarea id="userGroupDesc" name="userGroupDesc" style="width:300px;height:100px;">${(usergroup.userGroupDesc!'')?html}</textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
					<#if usergroup?? && usergroup.status == 1>
				     <input type="radio" name="status" id="status_01" value="1" checked="true"/><label>启用</label>
				   <#else>
				     <input type="radio" name="status" id="status_01" value="1" /><label>启用</label> 
				   </#if>
				   <#if usergroup?? && usergroup.status == 0>
				     <input type="radio" name="status" id="status_02" value="0" checked="true"/><label>停用</label>
				   <#else>
				     <input type="radio" name="status" id="status_02" value="0"/><label>停用</label>
				   </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">分配用户：</th>
				<td class="tdBox">
					  <#if userlist??>
                            <#list userlist as user>
                               <input type="checkbox" value="${user.id!''}"
                                <#if uuglist??>
                                <#list uuglist as uug>
                                 <#if user.id == uug.userId>
                                  checked="true"
                                 </#if>
                                </#list>
                               </#if>
                               /><label>&nbsp;${user.username}&nbsp;</label>
                            </#list>
                      </#if>      
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkUserGroup();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnUserGroupList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
    function checkUserGroup(){
      var userGroupName=$("#userGroupName").val();
      if(userGroupName==""){
        $("#userGroupName").focus();
        $("#userGroupName_c").text("用户组名称不能为空！");
        return false;
      }else{
        $("#userGroupName_c").text("");
      }
      var user_usergroup="";//权限
      $("input[type='checkbox']:checkbox:checked").each(function(){ 
           if(user_usergroup==""){
              user_usergroup+=$(this).val();
           }else{
             user_usergroup+=","+$(this).val(); 
           }
           
        }) 
      //将对用户赋予的权限给隐藏域，以便提交表单  
      $("#user_usergroup").val(user_usergroup); 
      //提交信息  
      checkInfo();
    }
    
    //用Ajax提交信息
	function checkInfo(){
	    var params=$("#editUserGroupForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/usergroup/edit?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/usergroup/list";
								}else{
								  $("#userGroupName").focus();
								  $("#userGroupName_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
	
	//取消事件，返回用户组管理列表
	function returnUserGroupList(){
	  location.href = "${rc.getContextPath()}/usergroup/list?usergroup_name=${(usergroup_name!'')?html}&status=${(status!'')?html}";
	}
</script>
</html>