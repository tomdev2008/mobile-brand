<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
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
			<h3 class="topTitle fb f14">卖场分类管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/topicType/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>配置管理</dd>
				<dd class="last"><h3>卖场分类管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
				      <label>分类ID：
				          <input type="text" name="id" id="id" size="15" value="${(id!'')}" />
				          &nbsp;&nbsp;
				      </label>
				      <label>分类名称：
				          <input type="text" name="name" id="name" size="15" value="${(name!'')}" />
				          &nbsp;&nbsp;
				      </label>
				      <label>状态:
				          <select name="status" id="status">
				           <option value="" >请选择状态</option>					           
				           <option value="1" <#if status?? && status == 1>selected="selected"</#if>>启用</option>
				           <option value="0" <#if status?? && status == 0>selected="selected"</#if>>停用</option>
				          </select> 
				          &nbsp;&nbsp;&nbsp;
				       </label> 
				      					     
					 <label>
					    <a title="查询" class="btn" onclick="queryType()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
					 </label>
					 <label>
					    <a title="添加分类" class="btn" onclick="createType()" style="width:50px;text-align:center;height:22px;">添加分类</a>
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
						<th width="15%">分类ID</th>
						<th width="15%">分类名称</th>
						<th width="15%">排序(降序)</th>
						<th width="15%">状态</th>
						<th width="15%">添加时间</th>
						<th width="25%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if (topicTypeList?? && topicTypeList?size > 0)>
				<#list topicTypeList as topicType>
				<tr>
				    <td>${topicType.id!''}</td>
				    <td>${topicType.name!''}</td>
					<td>${topicType.orderSeq!''}</td>
					<#if topicType.status?? && topicType.status == 1>
					<td>启用</td>
					<#else>
					<td>停用</td>
					</#if>
					<td><#if topicType.createDate??>${topicType.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
					<td>
					   <#if topicType.status?? && topicType.status == 1 >
						    <a href="javascript:void(0);" onclick="changStatus('${topicType.id}','0')" title="停用">停用</a>
					   <#else>
						    <a href="javascript:void(0);" onclick="changStatus('${topicType.id}','1')" title="启用">启用</a> 
					   </#if>
                       <a href="javascript:void(0);" onclick="editType(${topicType.id})" > | 编辑</a>
   					</td>    
			    </#list>
			    <#else>
			    <tr><td colspan="6"><font color="red">暂时没有查询到记录</font></td></tr>
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
function queryType(){
	var id = $("#id").val();
	if(id != null && id != "") {
		var reg = /^[0-9]+$/;	//验证规则
      	var flag = reg.test(id);
      	if(!flag) {
	 		alert("栏目ID只能输入数字！");
	 		return false;
	 	}
	}
	$('#pageNo').val(1);
    $("#mainForm").submit();
}

function createType(){
	location.href="${rc.getContextPath()}/topicType/create";
}

function editType(id) {
	var pageUrl = "${rc.getContextPath()}/topicType/edit?id="+id;
	var title = "编辑卖场分类";
	var parameters = "scrollbars=yes";
	var winOption = "width=800px,height=500px,left=350px,top=150px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

function deleteType(id,status){
	if(status != null && status == 1) {
		alert("启用中的分类不可删除！");
		return false;
	}
	if(confirm("确实要删除该分类吗？")== true){
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/topicType/delete?id=" + id + "&format=json",
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
	var msg = "";
	if(status == 0){
		msg = "确认要停用该分类吗？";
	} else if(status == 1) {
		msg = "确认要启用该分类吗？";
	}
      if(window.confirm(msg)){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/topicType/updateStatus?format=json",
				data : {"id":id, "status":status},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   //修改成后,刷新列表页
							   location.href = "${rc.getContextPath()}/topicType/list";
							} else if(objs.scode == "-1") {
								alert(objs.data);
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