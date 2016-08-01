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
			<h3 class="topTitle fb f14">朋友代付模板管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/payForTemplet/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>配置管理</dd>
				<dd class="last"><h3>朋友代付模板管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>模板ID：
					          <input type="text" name="id" id="id" size="15" value="${(id!'')}" />
					          &nbsp;&nbsp;&nbsp;
					      </label>
					      <label>模板标题：
					          <input type="text" name="title" id="title" size="15" value="${(title!'')}" />
					          &nbsp;&nbsp;&nbsp;
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
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="query();return fasle;" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						 
						 <label>
						    <a href="javascript:void(0);" title="添加模板" class="btn" onclick="create();return false;" style="width:60px;text-align:center;height:22px;">添加模板</a>
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
						<th width="8%">模板ID</th>
						<th width="15%">模板标题</th>
						<th width="15%">模板主图</th>
						<th width="8%">排序(降序)</th>
						<th width="8%">模板背景色</th>
						<th width="8%">状态</th>
						<th width="8%">添加人</th>
						<th width="12%">添加时间</th>
						<th width="18%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if (templetList?? && templetList?size > 0)>
				<#list templetList as templet>
				<tr>
				    <td>${templet.id!''}</td>
				    <td>${templet.title!''}</td>
				    <td>
						<div style="position: relative;">
						  <#if templet.templetPic?? && templet.templetPic != ''>
							  <img class="smallImg" style="width:40px;height:40px;" src="http://6.xiustatic.com${templet.templetPic!''}"  /> 
							  <div class="showBigImg" style="position: absolute;left: 110px;top: -100px; display:none;"><img style="width:300px;height:300px;" src="http://6.xiustatic.com${templet.templetPic!''}" /></div>
						  </#if>
					   </div>
					</td>
					<td>${templet.orderSeq!''}</td>
					<td><input type="text" style="width:19px; background-color:${templet.backgroundColor!''};" readonly="true" /></td>
					<#if templet.status?? && templet.status == 1>
					<td>启用</td>
					<#else>
					<td>停用</td>
					</#if>
					<td>${templet.addedBy!''}</td>
					<td><#if templet.createDate??>${templet.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
					<td>
					   <#if templet.status?? && templet.status == 1 >
						    <a href="javascript:void(0);" onclick="changStatus('${templet.id}','0')" title="停用">停用</a>
					   <#else>
						    <a href="javascript:void(0);" onclick="changStatus('${templet.id}','1')" title="启用">启用</a> 
					   </#if>
                       <a href="javascript:void(0);" onclick="editTemplet(${templet.id})" >编辑</a>|
					   <a href="javascript:void(0);" onclick="del(${templet.id},${templet.status});return false;">删除</a></td>
   					</td>    
			    </#list>
			    <#else>
			    <tr><td colspan="9"><font color="red">暂时没有查询到记录</font></td></tr>
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
// 显示大图
$('.smallImg').hover(function(){
	$(this).siblings('.showBigImg').show();
},function(){
	$(this).siblings('.showBigImg').hide();
})
	
 //查询
function query(){
	var id = $("#id").val();
	if(id != null && id != "") {
		var reg = /^[0-9]+$/;	//验证规则
      	var flag = reg.test(id);
      	if(!flag) {
	 		alert("模板ID只能输入数字！");
	 		return false;
	 	}
	}
	$('#pageNo').val(1);
    $("#mainForm").submit();
}

function create(){
	location.href="${rc.getContextPath()}/payForTemplet/create";
}

function editTemplet(id) {
	var pageUrl = "${rc.getContextPath()}/payForTemplet/edit?id="+id;
	var title = "编辑朋友代付模板";
	var parameters = "scrollbars=yes";
	var winOption = "width=650px,height=600px,left=450px,top=150px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

function del(id,status){
	if(status != null && status == 1) {
		alert("启用中的模板不可删除！");
		return false;
	}
	if(confirm("确实要删除该记录吗？")== true){
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/payForTemplet/delete?id=" + id + "&format=json",
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
		msg = "确认要停用该模板吗？";
	} else if(status == 1) {
		msg = "确认要启用该模板吗？";
	}
      if(window.confirm(msg)){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/payForTemplet/updateStatus?format=json",
				data : {"id":id, "status":status},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   //修改成后,刷新列表页
							   location.href = "${rc.getContextPath()}/payForTemplet/list";
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