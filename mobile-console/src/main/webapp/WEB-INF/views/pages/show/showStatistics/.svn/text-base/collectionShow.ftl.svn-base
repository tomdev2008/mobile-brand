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
			<h3 class="topTitle fb f14">秀集合秀数据统计</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showStatistics/collectionShowList" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客统计管理</dd>
				<dd class="last"><h3>秀集合秀数据统计</h3></dd>
		</dl>
		<!-- 导航 end -->
		<input type="hidden" value="${colletionId!''}" id="colletionId" name="colletionId"/>
				 <div style="  float: left;width:auto;">
		        	<table>
		        	  <tr><td valign="top">秀ID：</td>
		        	       <td><textarea  name="showIds" id="showIds" style="height: 25px;" ><#if showIds??>${showIds}</#if></textarea>&nbsp;</td>
					    </tr>
					   </table> 
		        </div>
		         <div class="wapbt showsearchBar" style="border-bottom:0px;" align="left">
			      <label>发布人：
			          <input name="createBy" type="text" id="createBy" value="<#if createBy??>${createBy}</#if>" size="10"  />
			          &nbsp;
			      </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
			    	<a href="javascript:void(0);" title="导出" class="btn" onclick="exportData()" style="width:50px;text-align:center;height:22px;">导&nbsp;&nbsp;出</a>
			 	</label>
			      </div>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="10%">秀ID</th>
					<th width="10%">发布人</th>
					<th width="10%">点击量</th>
					<th width="10%">点赞量</th>
					<th width="10%">评论量</th>
					<th width="10%">收藏量</th>
					<th width="10%">预览</th>
				</tr>
			</thead>
			<tbody>
			<#if (statisticsList?? && statisticsList?size > 0)>
			<#list statisticsList as statistics>
			<tr>
			    <td><#if statistics.id??>${statistics.id}<#else></#if></td>
			    <td><#if statistics.userName??>${statistics.userName}<#else></#if></td>
			    <td><#if statistics.browseNum??>${statistics.browseNum}<#else></#if></td>
			    <td><#if statistics.praisedNum??>${statistics.praisedNum}<#else></#if></td>
			    <td><#if statistics.commentNum??>${statistics.commentNum}<#else></#if></td>
			    <td><#if statistics.collectNum??>${statistics.collectNum}<#else></#if></td>
			    <td>
			         <a href="http://m.xiu.com/show/showDetail.html?showId=${statistics.id}" target="_blank">预览</a>
			    </td>
			</tr>
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
	
	//导出
	function exportData(){
		var colletionId = $("#colletionId").val();
		var showIds = $("#showIds").val();
		var createBy = $("#createBy").val();
		location.href = "${rc.getContextPath()}/showStatistics/findCollectionShowListExport?colletionId="+colletionId+"&showIds="+showIds+"&createBy="+createBy;
	}
	
</script>
</body>
</html>