<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>


</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">秀详情</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<div class="adminMain_wrap">
	<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/show/showPraiseList" method="GET">
	<input name="showId" value="${showId}" type="hidden"/>
	
	<div class="adminUp_wrap">

	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>秀客管理</dd>
			<dd><a href="${rc.getContextPath()}/show/showList">秀管理</a></dd>
			<dd><h3>详情菜单</h3></dd>
			<dd class="last"><h3>秀点赞</h3></dd>
		</dl>
			<#include "/pages/show/show/showInfoNav.ftl">  
	</div>
	<!--导航end-->
	
	<!-- 列表分页 -->
	
	<div class="adminContent clearfix">

		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="15%">点赞时间</th>
						<th width="15%">点赞人</th>
						<th width="70%"></th>
					</tr>
				</thead>
				<tbody>
				   <#if (praiseList?size > 0)>
				   <#list praiseList as praise>
				      <tr>					
				      
				        <td>${praise.createDate?string('yyyy-MM-dd HH:mm:ss')}</td> 
						<td>${praise.userName!''}</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="2"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
				<div class="w mt20 clearfix">

				</div>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
   </form>
</div>
</div>

</body>
<script type="text/javascript">

</script>
</html>