<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">秀统计管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showStatistics/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客后台管理</dd>
				<dd class="last"><h3>秀统计管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
				<label>统计类型：
			        <select name="statisticsType" id="statisticsType">
				        <option value="1001" <#if statisticsType?? && statisticsType=='1001'> selected="selected" </#if> >24小时内品牌新增秀数</option>
						<option value="1002" <#if statisticsType?? && statisticsType=='1002'> selected="selected" </#if> >24小时内秀被赞数</option>
						<option value="1003" <#if statisticsType?? && statisticsType=='1003'> selected="selected" </#if> >24小时内用户被赞数</option>
			        </select> 
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
			    	<a href="javascript:void(0);" title="更新" class="btn" onclick="updateStatistics()" style="width:50px;text-align:center;height:22px;">更&nbsp;&nbsp;新</a>
			    	<a href="javascript:void(0);" title="删除" class="btn" onclick="deleteStatistics()" style="width:50px;text-align:center;height:22px;">删&nbsp;&nbsp;除</a>
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
					<th width="10%">ID</th>
					<th width="10%">数据类型</th>
					<th width="10%">统计类型</th>
					<#if statisticsType?? && statisticsType == '1001'>
						<th width="10%">品牌ID</th>
						<th width="10%">品牌名称</th>
					<#elseif statisticsType?? && statisticsType=='1002'>
						<th width="10%">秀ID</th>
						<th width="10%">发布秀用户昵称</th>
					<#elseif statisticsType?? && statisticsType=='1003'>
						<th width="10%">秀客ID</th>
						<th width="10%">秀客昵称</th>
					<#else>
						<th width="10%">对象ID</th>
						<th width="10%">对象名称</th>
					</#if>
					<th width="10%">统计结果</th>
					<th width="10%">统计时间</th>
				</tr>
			</thead>
			<tbody>
			<#if (statisticsList?? && statisticsList?size > 0)>
			<#list statisticsList as statistics>
			<tr>
			    <td>${statistics.id!''}</td>
			    <td>${statistics.typeName!''}</td>
			    <td>${statistics.serviceTypeName!''}</td>
			    <#if statisticsType?? && statisticsType == '1001'>
					<td>${statistics.objectId!''}</td>
				<#elseif statisticsType?? && statisticsType=='1002'>
					<td><a href="javascript:void(0);" onclick="viewShow('${statistics.objectId!''}');" title="秀ID">${statistics.objectId!''}</a></td>
				<#elseif statisticsType?? && statisticsType=='1003'>
					<td><a href="javascript:void(0);" onclick="viewShowUser('${statistics.objectId!''}');" title="秀客ID">${statistics.objectId!''}</a></td>
				<#else>
					<td>${statistics.objectId!''}</td>
				</#if>
			    <td>${statistics.objectName!''}</td>
			    <td>${statistics.value!''}</td>
			    <td><#if statistics.createDate??>${statistics.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			</tr>
		    </#list>
		    <#else>
		    <tr><td colspan="7"><font color="red">暂时没有查询到记录</font></td></tr>
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
	
	//查看秀用户
	function viewShowUser(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		window.open(pageUrl);
	}
	
	//查看秀详情
	function viewShow(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
		window.open(pageUrl);
	}
	
	//更新统计数据
	function updateStatistics() {
		var statisticsType = $("#statisticsType").val();
		if(window.confirm('确定要更新统计数据吗？')){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/showStatistics/updateStatistics?format=json",
				data : {"statisticsType":statisticsType},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   //刷新列表页
							   location.href = "${rc.getContextPath()}/showStatistics/list?statisticsType="+statisticsType;
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
	
	//删除统计数据
	function deleteStatistics() {
		var statisticsType = $("#statisticsType").val();
		if(window.confirm('确定要删除统计数据吗？')){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/showStatistics/deleteStatistics?format=json",
				data : {"statisticsType":statisticsType},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   //刷新列表页
							   location.href = "${rc.getContextPath()}/showStatistics/list?statisticsType="+statisticsType;
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