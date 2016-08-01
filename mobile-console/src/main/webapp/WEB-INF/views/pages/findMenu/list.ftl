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
			<h3 class="topTitle fb f14">发现频道管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/findMenu/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>配置管理</dd>
				<dd class="last"><h3>发现频道管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
					      <label>栏目ID：
					          <input type="text" name="id" id="id" size="15" value="${(id!'')}" />
					          &nbsp;&nbsp;
					      </label>
					      <label>栏目名称：
					          <input type="text" name="name" id="name" size="15" value="${(name!'')}" />
					          &nbsp;&nbsp;
					      </label>
					      <label>所属区块:
					          <select name="block" id="block">
					           <option value="" >请选择区块</option>					           
					           <option value="1" <#if block?? && block == 1>selected="selected"</#if>>区块1</option>
					           <option value="2" <#if block?? && block == 2>selected="selected"</#if>>区块2</option>
					           <option value="3" <#if block?? && block == 3>selected="selected"</#if>>区块3</option>
					           <option value="4" <#if block?? && block == 4>selected="selected"</#if>>区块4</option>
					           <option value="5" <#if block?? && block == 5>selected="selected"</#if>>区块5</option>
					           <option value="6" <#if block?? && block == 6>selected="selected"</#if>>区块6</option>
					           <option value="7" <#if block?? && block == 7>selected="selected"</#if>>区块7</option>
					           <option value="8" <#if block?? && block == 8>selected="selected"</#if>>区块8</option>
					           <option value="9" <#if block?? && block == 9>selected="selected"</#if>>区块9</option>
					           <option value="10" <#if block?? && block == 10>selected="selected"</#if>>区块10</option>
					           <option value="11" <#if block?? && block == 11>selected="selected"</#if>>区块11(首页引导)</option>
					           <option value="12" <#if block?? && block == 12>selected="selected"</#if>>区块12(App导航栏)</option>
					           <option value="13" <#if block?? && block == 13>selected="selected"</#if>>区块13(用户中心功能模块)</option>
					          </select> 
					          &nbsp;
					       </label>
					       <label>栏目类型:
					          <select name="type" id="type">
					           <option value="" >请选择类型</option>					           
					           <option value="1" <#if type?? && type == 1>selected="selected"</#if>>Native</option>
					           <option value="2" <#if type?? && type == 2>selected="selected"</#if>>H5</option>
					          </select> 
					          &nbsp;
					       </label>
					      <label>状态:
					          <select name="status" id="status">
					           <option value="" >请选择状态</option>					           
					           <option value="1" <#if status?? && status == 1>selected="selected"</#if>>启用</option>
					           <option value="0" <#if status?? && status == 0>selected="selected"</#if>>停用</option>
					          </select> 
					          &nbsp;
					       </label> 
					      					     
						 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="query();return fasle;" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						 <label>
						    <a href="javascript:void(0);" title="添加栏目" class="btn" onclick="create();return false;" style="width:50px;text-align:center;height:22px;">添加栏目</a>
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
						<th width="7%">栏目ID</th>
						<th width="10%">栏目名称</th>
						<th width="7%">所属区块</th>
						<th width="5%">排序</th>
						<th width="7%">栏目类型</th>
						<th width="28%">App URL地址</th>
						<th width="5%">版本号</th>
						<th width="7%">状态</th>
						<th width="7%">是否登录</th>
						<th width="15%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if (menuList?? && menuList?size > 0)>
				<#list menuList as menu>
				<tr>
				    <td>${menu.id!''}</td>
				    <td>${menu.name!''}</td>
				    <td>区块${menu.block!''}</td>
					<td>${menu.orderSeq!''}</td>
					<#if menu.type?? && menu.type == 1>
					<td>Native</td>
					<#elseif menu.type==2>
					<td>H5</td>
					</#if>
					<td style="word-break:break-all;">${menu.url!''}</td>
					<td>${menu.version!''}</td>
					<#if menu.status?? && menu.status == 1>
					<td>启用</td>
					<#else>
					<td>停用</td>
					</#if>
					<#if menu.isNeedLogin?? && menu.isNeedLogin == 1>
					<td>需要</td>
					<#else>
					<td>不需要</td>
					</#if>
					<td>
					   <#if menu.status?? && menu.status == 1 >
						    <a href="javascript:void(0);" onclick="changStatus('${menu.id}','0')" title="停用">停用</a>
					   <#else>
						    <a href="javascript:void(0);" onclick="changStatus('${menu.id}','1')" title="启用">启用</a> 
					   </#if>
                       <a href="javascript:void(0);" onclick="editTemplet(${menu.id})" >| 编辑 |</a>
					   <a href="javascript:void(0);" onclick="window.open ('${menu.url}', '预览','center:Y');" >预览</a>
					   <#if menu.status?? && menu.status == 1 >
					   		<a href="javascript:void(0);" onclick="updateVersion(${menu.id},${menu.version})" >| 更新</a>
					   <#else>
					   		<a href="javascript:void(0);"><span style="color:gray;">| 更新</span></a>
					   </#if>
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
 //查询
function query(){
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

function create(){
	location.href="${rc.getContextPath()}/findMenu/create";
}

function editTemplet(id) {
	var pageUrl = "${rc.getContextPath()}/findMenu/edit?id="+id;
	var title = "编辑栏目";
	var parameters = "scrollbars=yes";
	var winOption = "width=900px,height=600px,left=350px,top=150px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

function del(id,status){
	if(status != null && status == 1) {
		alert("启用中的栏目不可删除！");
		return false;
	}
	if(confirm("确实要删除该记录吗？")== true){
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/findMenu/delete?id=" + id + "&format=json",
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
		msg = "确认要停用该栏目吗？";
	} else if(status == 1) {
		msg = "确认要启用该栏目吗？";
	}
      if(window.confirm(msg)){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/findMenu/updateStatus?format=json",
				data : {"id":id, "status":status},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   //修改成后,刷新列表页
							   location.href = "${rc.getContextPath()}/findMenu/list";
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
//更新版本号
function updateVersion(id,oldVersion) {
	if(confirm("确定要更新吗？\n更新后前端会冒小红点提示。")== true){
		var version = oldVersion + 1;
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/findMenu/updateVersion?id=" + id +"&version="+version+"&oldVersion="+oldVersion+ "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
					window.location.reload();
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
}
</script>
</body>
</html>