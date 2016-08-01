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
			<h3 class="topTitle fb f14">菜单管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/menu/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>系统管理</dd>
				<dd class="last"><h3>菜单管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>菜单名称：
					          <input name="menu_name" type="text" id="menu_name" value="${(menu_name!'')?html}" size="20" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      <label> 菜单状态:
					          <select name="menu_status" id="menu_status">
					           <option value="">--请选择--</option>
					           <#if menu_status?? && menu_status ==1>
					              <option value="1" selected="selected">启用</option>
					           <#else>
					              <option value="1">启用</option>
					           </#if>
					           <#if menu_status?? && menu_status ==0>
					              <option value="0" selected="selected">停用</option>
					           <#else>
					              <option value="0">停用</option>
					           </#if>
					          </select> 
					          &nbsp;&nbsp;&nbsp; 
					       </label>     
						 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="return selectMenu()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						      &nbsp;&nbsp;&nbsp;
						 <label>
						    <a href="javascript:void(0);" title="添加" class="btn" onclick="return addMenu()" style="width:50px;text-align:center;height:22px;" id="query">添&nbsp;&nbsp;加</a>
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
						<th width="5%">菜单ID</th>
						<th width="20%">菜单名称</th>
						<th width="15%">菜单URL</th>
						<th width="10%">父级菜单</th>
						<th width="25%">备注信息</th>
						<th width="10%">菜单状态</th>
						<th width="15%">执行操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (menulist?size > 0)>
				   <#list menulist as menu>
				      <tr>
				        <td>${menu.id!''}</td>
						<td>${menu.menuName!''}</td>
						<td>${menu.menuUrl!''}</td>
						<td>${menu.parentName!''}</td>
						<td>${menu.memo!''}</td>
						<td>
						 <#if menu.getStatus()? exists && menu.getStatus() ==1 >
						  启用
						 <#else>
						   停用 
						 </#if>
						</td>
						<td>
						  <a href="javascript:void(0);" onclick="editMenu('${menu.id}');" title="编辑">编辑</a>|
						 <#if menu.getStatus()? exists && menu.getStatus() ==1 >
						    <a href="javascript:void(0);" onclick="endMenu('${menu.id}','0')" title="停用">停用</a>
						 <#else>
						    <a href="javascript:void(0);" onclick="startMenu('${menu.id}','1')" title="启用">启用</a> 
						 </#if>
						 
						</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="7"><font color="red">暂时没有查询到记录</font></td></tr>
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
   //查询菜单
   function selectMenu(){
     $("#query").removeClass("btn").addClass("quanbtn");
	 $("#query").attr("onclick","");
	 $("#query").text("正在查询");
     $("#mainForm").submit();
   }
   
   //添加菜单
   function addMenu(){
      location.href="${rc.getContextPath()}/menu/create?menu_name="+$("#menu_name").val()+"&menu_status="+$("select").find("option:selected").val();
   }
   
   //停用
   function endMenu(id,status){
     if(window.confirm('您确定要停用该菜单吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/menu/updatestatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/menu/list";
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
   function startMenu(id,status){
      if(window.confirm('您确定要启用该菜单吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/menu/updatestatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/menu/list";
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
   
   //编辑菜单
   function editMenu(id){
    if(window.confirm('您确定要修改该菜单吗?')){
      location.href="${rc.getContextPath()}/menu/edit?id="+id+"&menu_name="+$("#menu_name").val()+"&menu_status="+$("select").find("option:selected").val();
    }
   }
</script>
</html>