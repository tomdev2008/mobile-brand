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
			<h3 class="topTitle fb f14">评论详情</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<div class="adminMain_wrap">
 <div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>话题管理</dd>
			<dd><a href="${rc.getContextPath()}/showTopic/list">话题管理</a></dd>
			<dd class="last"><h3>话题修改</h3>
			</dd>
		</dl>
	<ul class="common_ul">
		<li><input type="button" value="话题详情" onclick="viewTopic()" class="userNavBtn " /></li>
		<li><input type="button" value="操作日志" onclick="viewLogs()" class="userNavBtn navBtnChoose" /></li>
	</ul>
	</div>
	<!--导航end-->
	
	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="15%">操作时间</th>
						<th width="15%">操作描述</th>
						<th width="15%">操作人</th>
						<th width="55%"></th>
					</tr>
				</thead>
				<tbody>
				   <#if (opLoglist?size > 0)>
				   <#list opLoglist as oplog>
				      <tr>
				        <td>${oplog.createDate?string('yyyy-MM-dd HH:mm:ss')}</td> 
						<td>${oplog.operDesc!''}</td>
						<td>${oplog.userName!''}</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="3"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</div>
</div>


</body>
<script type="text/javascript">
	
	function viewTopic() {
		var pageUrl = "${rc.getContextPath()}/showTopic/info?topicId="+${objectId!''};
		location.href = pageUrl;
	}
	
	
	//取消事件，返回菜单管理列表
	function returnMenuList(){
	  location.href = "${rc.getContextPath()}/showTopic/list";
	}
	
	
	
</script>
</html>