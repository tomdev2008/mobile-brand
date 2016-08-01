<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<style>
	.table_bg05.thsubject{
		font-weight: bold;
	}
	.tdsubject{
		padding: 5px 6px;
	}
	.tdSelect{
		width:260px;
		margin-left: -117px;
	}
</style>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">专题管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/appPatch/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>热更新管理</dd>
				<dd class="last"><h3>热更新列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		        <label>热更新标题：
					<input name="name" type="text" id="name" value="${name!''}" size="12" />
		        </label>
		        <label>APP版本：
					<select name="type" id="type" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="appstore" <#if type??&&type=='appstore'>selected="selected"</#if>>appstore</option>
				              <option value="appPro" <#if type??&&type=='appPro'>selected="selected"</#if>>appPro</option>
				     </select> 
		        </label>
		        <label>状态：
						 <select name="status" id="status" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="0" <#if status??&&status=='0'>selected="selected"</#if>>停用</option>
				              <option value="1" <#if status??&&status=='1'>selected="selected"</#if>>启用</option>
				          </select> 
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="新增" class="btn" onclick="addNewsubject()" style="width:80px;text-align:center;height:22px;">新增</a>
			 	</label>
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="10%">js版本</th>
					<th width="15%">发布时间</th>
					<th width="10%">APP版本</th>
					<th width="10%">版本编号</th>
					<th width="15%">热更新标题</th>
					<th width="15%">是否强制升级</th>
					<th width="15%">状态</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (appPatchList?? && appPatchList?size > 0)>
			<#list appPatchList as subject>
			<tr>
				<td>${subject.id!''}</td>
			    <td>${subject.createTime!''}</td>
			    <td>${subject.type!''}</td>
			    <td>${subject.version!''}</td>
			    <td>${subject.name!''}</td>
			    <td>				    
			        <#if subject.needUpdate?? && subject.needUpdate == 0>否</#if>
					<#if subject.needUpdate?? && subject.needUpdate==1><font class='red'>是</font></#if>
					
				</td>
				<td>				    
			        <#if subject.status?? && subject.status == 0>停用</#if>
					<#if subject.status?? && subject.status==1><font class='red'>启用</font></#if>
					
				</td>
				<td>
					<#if subject.status?? && subject.status==1>
						<a href="javascript:void(0);" onclick="updateAppPatch('${subject.id!''}',0)" title="停用">停用</a>
					</#if>
					<#if subject.status?? && subject.status==0>
						<font class='red'><a href="javascript:void(0);" onclick="updateAppPatch('${subject.id!''}',1)" >启用</a></font>
					</#if>
					|
					<a href="javascript:void(0);" onclick="appPatchDetail('${subject.id!''}')" title="预览">详情</a>
				</td>   
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">
	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	//增加
	function addNewsubject(){
	  var url="${rc.getContextPath()}/appPatch/goAdd";
	  location.href=url;
	}
	function appPatchDetail(id){
		var url="${rc.getContextPath()}/appPatch/display?id="+id;
	  location.href=url;
	}
	
	//修改
	function updateAppPatch(id,status){
	 	$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/appPatch/queryTypeOfAppPatchStatus?id=" + id+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   if(confirm("该版本已存在启用的更新文件，是否禁用并使用现在的版本？"))
 								{
 									 var url="${rc.getContextPath()}/appPatch/update?id="+id+"&status="+status;
	 								 location.href=url;
 								}
			            	}else{
			            	    	var url="${rc.getContextPath()}/appPatch/update?id="+id+"&status="+status;
	 							 	location.href=url;
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		       });
	}
</script>
</body>
</html>