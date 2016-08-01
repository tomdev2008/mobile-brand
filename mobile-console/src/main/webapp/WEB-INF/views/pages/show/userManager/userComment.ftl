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

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/userManager/viewUserComment" method="GET">
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
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="15%">评论时间</th>
					<th width="15%">评论内容</th>
					<th width="15%">评论状态</th>
					<th width="15%">是否被举报</th>
					<th width="15%">所属秀ID</th>
					<th width="25%"></th>
				</tr>
			</thead>
			<tbody>
			<#if (commentList?? && commentList?size > 0)>
			<#list commentList as comment>
			<tr>
			    <td><#if comment.commentDate??>${comment.commentDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			    <td>${comment.content!''}</td>
			    <td><#if comment.deleteFlag?? && comment.deleteFlag == 0>正常
					<#elseif comment.deleteFlag?? && comment.deleteFlag==1>用户删除
					<#elseif comment.deleteFlag?? && comment.deleteFlag==2>前台管理员删除
					<#elseif comment.deleteFlag?? && comment.deleteFlag==3>后台删除
					</#if>
			    </td>
			    <td><#if comment.reportedFlag?? && comment.reportedFlag == 0>否
					<#elseif comment.reportedFlag?? && comment.reportedFlag==1>是
					</#if>
				</td>
				<td><a href="javascript:void(0);" onclick="viewShow('${comment.showId!''}');" title="秀ID">${comment.showId!''}</a></td>
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
	//秀详情
	function viewShow(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
		window.open(pageUrl);
	}
</script>
</html>