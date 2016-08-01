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
			<h3 class="topTitle fb f14">角色管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/role/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>系统管理</dd>
				<dd class="last"><h3>角色管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>角色名称：
					          <input name="role_name" type="text" id="role_name" value="${role_name!''}" size="20" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      <label> 角色状态:
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
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="return selectRole()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						      &nbsp;&nbsp;&nbsp;
						 <label>
						    <a href="javascript:void(0);" title="添加" class="btn" onclick="return addRole()" style="width:50px;text-align:center;height:22px;" id="query">添&nbsp;&nbsp;加</a>
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
						<th width="8%">角色ID</th>
						<th width="32%">角色名称</th>
						<th width="35%">角色描述</th>
						<th width="10%">角色状态</th>
						<th width="15%">执行操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (rolelist?size > 0)>
				   <#list rolelist as role>
				      <tr>
				        <td>${role.id!''}</td>
						<td>${role.roleName!''}</td>
						<td>${role.roleDesc!''}</td>
						<td>
						 <#if role.getStatus()? exists && role.getStatus() ==1 >
						  启用
						 <#else>
						   停用 
						 </#if>
						</td>
						<td>
						  <a href="javascript:void(0);" onclick="editRole('${role.id}');" title="编辑">编辑</a>|
						 <#if role.getStatus()? exists && role.getStatus() ==1 >
						    <a href="javascript:void(0);" onclick="endRole('${role.id}','0')" title="停用">停用</a>
						 <#else>
						    <a href="javascript:void(0);" onclick="startRole('${role.id}','1')" title="启用">启用</a> 
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
   //查询角色
   function selectRole(){
     $("#mainForm").submit();
   }
   
   //添加角色
   function addRole(){
      location.href="${rc.getContextPath()}/role/create?role_name="+$("#role_name").val()+"&status="+$("select").find("option:selected").val();
   }
   
   //停用
   function endRole(id,status){
     if(window.confirm('您确定要停用该角色吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/role/updatestatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/role/list";
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
   function startRole(id,status){
      if(window.confirm('您确定要启用该角色吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/role/updatestatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/role/list";
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
   
   //编辑角色
   function editRole(id){
    if(window.confirm('您确定要修改该角色吗?')){
      location.href="${rc.getContextPath()}/role/edit?id="+id+"&role_name="+$("#role_name").val()+"&status="+$("select").find("option:selected").val();
    }
   }
</script>
</html>