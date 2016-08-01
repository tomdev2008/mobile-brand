<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">版本更新管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/version/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>活动平台</dd>
				<dd class="last"><h3>版本更新管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>版本号：
					          <input name="versionNo" type="text" id="versionNo" size="10" value="${(versionNo!'')}" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      <label>更新内容：
					          <input name="content" type="text" id="content" size="15" value="${(content!'')}" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      
					      <label>发布时间:
					      <input name="sDate" id="sDate" type="text" value="${(sDate!'')}" maxlength="11" style="width:80px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'eDate\')}'})" />
					      </label>
					      <label>——
					      <input name="eDate" id="eDate" type="text" value="${(eDate!'')}" maxlength="11" style="width:80px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sDate\')}'})" />
                              &nbsp;&nbsp;&nbsp;
					      </label>
					      
					      <label> 状态:
					          <select name="status" id="status">
					           <option value="-1" >请选择状态</option>					           
					           <option value="1" <#if status?? && status == 1>selected="selected"</#if>>已启用</option>
					           <option value="0" <#if status?? && status == 0>selected="selected"</#if>>被禁用</option>
					          </select> 
					          &nbsp;&nbsp;&nbsp; 
					       </label> 
					      					     
						 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="query();return fasle;" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						 
						 <label>
						    <a href="javascript:void(0);" title="发布新版本" class="btn" onclick="create();return false;" style="width:60px;text-align:center;height:22px;">发布新版本</a>
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
						<th width="8%">版本名称</th>
						<th width="8%">发布时间</th>
						<th width="22%">更新内容</th>
						<th width="8%">客户端类型</th>
						<th width="6%">版本号</th>
						<th width="20%">链接地址</th>
						<th width="6%">状态</th>
						<th width="7%">强制更新</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if (versionList?size > 0)>
				<#list versionList as version>
				<tr>
				    <td>${version.name!''}</td>
				    <td><#if version.pubTime??>${version.pubTime?string('yyyy-MM-dd')}</#if></td>
					<td>
					<!-- <textarea style="overflow-y:visible;" disabled="disabled"> -->
					${version.content!''}
					<!-- </textarea> -->
					</td>
					<#if version.type?? && version.type == 1>
						<td>Android</td>
					<#elseif version.type==2>
						<td>IPhone</td>
					<#elseif version.type==3>
						<td>iPad</td>
					</#if>	
					<td><span style="font-size: 16px;font-weight:bold;">${version.versionNo!''}</span></td>
					<td>${version.url!''}</td>
					<#if version.status?? && version.status == 1>
					<td>启用</td>
					<#else>
					<td>禁用</td>
					</#if>
					<#if version.forcedUpdate?? && version.forcedUpdate == 1>
					<td>是</td>
					<#else>
					<td>否</td>
					</#if>
					<td>
					   <#if version.status?? && version.status == 1 >
						    <a href="javascript:void(0);" onclick="changStatus('${version.id}','0')" title="停用">停用</a>
					   <#else>
						    <a href="javascript:void(0);" onclick="changStatus('${version.id}','1')" title="启用">启用</a> 
					   </#if>
                       <a href="javascript:void(0);" onclick="editVersion(${version.id})" >编辑</a>|
					   <a href="javascript:;" onclick="del(${version.id});return false;">删除</a></td>
   					</td>    
			    </#list>
			    <#else>
			    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
			    </#if>
			   </tbody>
			</table>
	</div>	
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">
 //查询
function query(){
	$('#pageNo').val(1);
	
	var versionNo = $("#versionNo").val();
	
	if(versionNo == null || undefined == versionNo || isNaN(versionNo)) {
       alert("不合法的版本号，版本号为数字！");
       return;
    }
   
    $("#mainForm").submit();
}

function create(){
	location.href="${rc.getContextPath()}/version/create";
}

function edit(id){
	location.href="${rc.getContextPath()}/findgoods/toedit?id=" + id;
}

function editVersion(id) {
	var pageUrl = "${rc.getContextPath()}/version/edit?id="+id;
	var title = "编辑版本更新信息";
	var parameters = "scrollbars=yes";
	var winOption = "width=600px,height=650px,left=450px,top=150px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

function del(id){
	if(confirm("确实要删除该记录吗？")== true){
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/version/delete?id=" + id + "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0") {
							window.location.reload();
						}else{
						    alert(objs.data);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
}

//启用
function changStatus(id,status){
      if(window.confirm('您确定要修改版本启用状态吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/version/updateStatus?format=json",
					data : {"id":id, "status":status},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/version/list";
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
</script>
</body>
</html>