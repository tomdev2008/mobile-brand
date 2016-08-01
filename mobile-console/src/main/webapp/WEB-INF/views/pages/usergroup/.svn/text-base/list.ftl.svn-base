<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">用户组管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/usergroup/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>系统管理</dd>
				<dd class="last"><h3>用户组管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>用户组名称：
					          <input name="usergroup_name" type="text" id="usergroup_name" value="${usergroup_name!''}" size="20" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      <label> 用户组状态:
					          <select name="status" id="status">
					           <option value="">--请选择--</option>
					           <#if status?? && status ==1>
					              <option value="1" selected="selected">启用</option>
					           <#else>
					              <option value="1">启用</option>
					           </#if>
					           <#if status?? && status ==0>
					              <option value="0" selected="selected">停用</option>
					           <#else>
					              <option value="0">停用</option>
					           </#if>
					          </select> 
					          &nbsp;&nbsp;&nbsp; 
					       </label>     
						 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="return selectUserGroup()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						      &nbsp;&nbsp;&nbsp;
						 <label>
						    <a href="javascript:void(0);" title="添加" class="btn" onclick="return addUserGroup()" style="width:50px;text-align:center;height:22px;" id="query">添&nbsp;&nbsp;加</a>
						 </label>
				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="8%">用户组ID</th>
						<th width="20%">用户组名称</th>
						<th width="47%">用户组描述</th>
						<th width="10%">用户组状态</th>
						<th width="15%">执行操作</th>
					</tr>
				</thead>
				<tbody>
				    <#if (usergrouplist?size > 0)>
				   <#list usergrouplist as usergroup>
				      <tr>
				        <td>${usergroup.id!''}</td>
						<td>${usergroup.userGroupName!''}</td>
						<td>${usergroup.userGroupDesc!''}</td>
						<td>
						 <#if usergroup.getStatus()? exists && usergroup.getStatus() ==1 >
						  启用
						 <#else>
						   停用 
						 </#if>
						</td>
						<td>
						  <a href="javascript:void(0);" onclick="editUserGroup('${usergroup.id}');" title="编辑">编辑</a>|
						 <#if usergroup.getStatus()? exists && usergroup.getStatus() ==1 >
						    <a href="javascript:void(0);" onclick="endUserGroup('${usergroup.id}','0')" title="停用">停用</a>
						 <#else>
						    <a href="javascript:void(0);" onclick="startUserGroup('${usergroup.id}','1')" title="启用">启用</a>
						 </#if>
						</td>
					 </tr>	
				   </#list>
				    <#else>
				     <tr><td colspan="5"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>				
				</tbody>
			</table>
	</div>	
</div>
   <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>
</div>
</body>
<script type="text/javascript">
   //查询用户组
   function selectUserGroup(){
     $("#mainForm").submit();
   }
   
   //添加用户组
   function addUserGroup(){
      location.href="${rc.getContextPath()}/usergroup/create?usergroup_name="+$("#usergroup_name").val()+"&status="+$("select").find("option:selected").val();
   }
   
   //停用
   function endUserGroup(id,status){
     if(window.confirm('您确定要停用该用户组吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/usergroup/updatestatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/usergroup/list";
								}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	 }
   }
   
   //启用
   function startUserGroup(id,status){
      if(window.confirm('您确定要启用该用户组吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/usergroup/updatestatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/usergroup/list";
								}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	  }
   }
   
   //编辑用户组
   function editUserGroup(id){
    if(window.confirm('您确定要修改该用户组吗?')){
      location.href="${rc.getContextPath()}/usergroup/edit?id="+id+"&usergroup_name="+$("#usergroup_name").val()+"&status="+$("select").find("option:selected").val();
    }
   }
</script>
</html>