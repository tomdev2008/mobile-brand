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

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/messageManager/operateInfo" method="GET">
	<input type="hidden" name="userId" value= "${userId!''}" />
	<input type="hidden" name="userPageType" value= "${userPageType!''}" />
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>走秀后台管理</dd>
			<dd><a href="${rc.getContextPath()}/messageManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">系统消息管理</a></dd>
			<dd class="last"><h3>系统消息</h3>
			</dd>
		</dl>
		<div>
			<ul class="common_ul">
				<li><input type="button" value="消息详情" onclick="viewMessage(${messageId!''})"
						<#if pageType?? && pageType == 'message'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
				<li><input type="button" value="操作日志" onclick="viewMessageOperateLog(${messageId!''})"
						<#if pageType?? && pageType == 'operatelog'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
			</ul>  
		</div>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="15%">操作时间</th>
					<th width="25%">操作描述</th>
					<th width="15%">操作人</th>
					<th width="50%"></th>
				</tr>
			</thead>
			<tbody>
			<#if (operateLogList?? && operateLogList?size > 0)>
			<#list operateLogList as operateLog>
			<tr>
			    <td><#if operateLog.createDate??>${operateLog.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			    <td>${operateLog.operDesc!''}<#if operateLog.remark??>，${operateLog.remark!''}</#if></td>
				<td>${operateLog.userName!''}</td>
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
	//系统消息详情
	function viewMessage(messageId) {
		var pageUrl = "${rc.getContextPath()}/messageManager/info?messageId="+messageId;
		location.href = pageUrl;
	}
	
	//系统消息操作日志
	function viewMessageOperateLog(messageId) {
		var pageUrl = "${rc.getContextPath()}/messageManager/operateInfo?messageId="+messageId;
		location.href = pageUrl;
	}
</script>
</html>