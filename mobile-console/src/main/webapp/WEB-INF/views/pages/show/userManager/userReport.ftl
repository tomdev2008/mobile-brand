<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">用户详情</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/userManager/viewUserReport" method="GET">
	<input type="hidden" name="userId" value= "${userId!''}" />
	<input type="hidden" name="userPageType" value= "${userPageType!''}" />
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>秀客后台管理</dd>
			<dd><a href="${rc.getContextPath()}/userManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">秀用户管理</a></dd>
			<dd class="last"><h3>用户详情</h3></dd>
		</dl>
		<#include "/pages/show/userManager/userCommonNav.ftl">  
	</div>
	<!--导航end-->
	
	<!-- 查询条件 -->
	<div>
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
            	<span style="font-weight:bold;">被举报类型</span>：
		        <input name="infoType" type="radio" value="1" <#if infoType?? && infoType == "1">checked="checked"</#if> onclick="query(${userId!''},'1')"  />秀&nbsp;
				<input name="infoType" type="radio" value="2" <#if infoType?? && infoType == "2">checked="checked"</#if> onclick="query(${userId!''},'2')"  />评论
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="15%">被举报类型</th>
					<th width="15%">举报时间</th>
					<th width="15%">举报原因</th>
					<th width="15%">举报人</th>
					<#if infoType?? && infoType == "1">
						<th width="15%">所属秀ID</th>
					<#elseif infoType?? && infoType=="2">
						<th width="15%">所属评论</th>
					</#if>
					<th width="25%"></th>
				</tr>
			</thead>
			<tbody>
			<#if (userReportedList?? && userReportedList?size > 0)>
			<#list userReportedList as userReport>
			<tr>
				<td><#if userReport.infoType?? && userReport.infoType == 1>秀
					<#elseif userReport.infoType?? && userReport.infoType==2>评论
					</#if>
				</td>
			    <td><#if userReport.reportTime??>${userReport.reportTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			    <td>${userReport.reportReason!''}</td>
			    <td><a href="javascript:void(0);" onclick="viewShowUser('${userReport.userId!''}');" title="${userReport.userName!''}">${userReport.userName!''}</a></td>
			    <#if infoType?? && infoType == "1">
					<td><a href="javascript:void(0);" onclick="viewShow('${userReport.objectId!''}');" title="秀ID">${userReport.objectId!''}</a></td>
				<#elseif infoType?? && infoType=="2">
					<td>${userReport.objectContent!''}</td>
				</#if>
			<tr>
		    </#list>
		    <#else>
		    <tr><td colspan="5"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
	</div>
	<#include "/common/page.ftl">  
	</form>
</div>
</body>
<script type="text/javascript">
    function query(userId,infoType) {
		//document.getElementById("mainForm").submit();
    	var pageUrl = "${rc.getContextPath()}/userManager/viewUserReport?userId="+userId+"&infoType="+infoType+"&userPageType=report";
		location.href = pageUrl;
    }
    
    //详情
	function viewShowUser(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		window.open(pageUrl);
	}
	
	//秀详情
	function viewShow(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
		window.open(pageUrl);
	}
</script>
</html>