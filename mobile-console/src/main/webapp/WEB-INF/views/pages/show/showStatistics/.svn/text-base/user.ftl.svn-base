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
			<h3 class="topTitle fb f14">秀客用户统计</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showStatistics/user" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客统计管理</dd>
				<dd class="last"><h3>秀客用户统计</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
            	<label>
            		用户：
            		<select name="userType" id="userType">
				        <option value="" <#if !userType??> selected="selected" </#if> >全部</option>
						<option value="1" <#if userType?? && userType=='1'> selected="selected" </#if> >真实用户</option>
						<option value="2" <#if userType?? && userType=='2'> selected="selected" </#if> >小号</option>
			        </select>
            	</label>
            	<label>
            		查询日期：
            		<select name="timeType" id="timeType" onChange="changeTime()">
				        <option value="1" <#if timeType?? && timeType=='1'> selected="selected" </#if> >今天</option>
						<option value="2" <#if timeType?? && timeType=='2'> selected="selected" </#if> >昨天</option>
						<option value="3" <#if timeType?? && timeType=='3'> selected="selected" </#if> >过去7天</option>
			        </select>
            	</label>
				<label>查询时间：
			        <input name="beginTime" id="beginTime" type="text"  value="<#if beginTime??>${beginTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					- <input name="endTime" id="endTime" type="text"  value="<#if endTime??>${endTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
			    	<a href="javascript:void(0);" title="导出" class="btn" onclick="exportData()" style="width:50px;text-align:center;height:22px;">导&nbsp;&nbsp;出</a>
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
					<th width="10%">日期</th>
					<th width="10%">发秀用户</th>
					<th width="10%">上推荐用户</th>
					<th width="10%">话题参与用户</th>
					<th width="10%">话题精选用户</th>
					<th width="10%">点赞用户</th>
					<th width="10%">评论用户</th>
					<th width="10%">加关注用户</th>
					<th width="10%">取消关注用户</th>
					<th width="10%"></th>
				</tr>
			</thead>
			<tbody>
			<#if (statisticsList?? && statisticsList?size > 0)>
			<#list statisticsList as statistics>
			<tr>
			    <td>${statistics.TIMESS!''}</td>
			    <td><#if statistics.PUBLISH_SHOW_NUM??><a href="javascript:void(0);" onclick="viewUserShowStatistics('${statistics.TIMESS!''}');" title="发秀用户">${statistics.PUBLISH_SHOW_NUM!''}</a><#else>0</#if></td>
			    <td><#if statistics.RECOMMED_SHOW_NUM??>${statistics.RECOMMED_SHOW_NUM}<#else>0</#if></td>
			    <td><#if statistics.TOPIC_SHOW_NUM??>${statistics.TOPIC_SHOW_NUM}<#else>0</#if></td>
			    <td><#if statistics.TOPIC_SHOW_SELECTION_NUM??>${statistics.TOPIC_SHOW_SELECTION_NUM}<#else>0</#if></td>
			    <td><#if statistics.PRAISE_NUM??>${statistics.PRAISE_NUM}<#else>0</#if></td>
			    <td><#if statistics.COMMENT_NUM??>${statistics.COMMENT_NUM}<#else>0</#if></td>
			    <td><#if statistics.CONCERN_NUM??>${statistics.CONCERN_NUM}<#else>0</#if></td>
			    <td><#if statistics.CANCEL_CONCERN_NUM??>${statistics.CANCEL_CONCERN_NUM}<#else>0</#if></td>
			    <td></td>
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

	//更改时间查询类型
	function changeTime() {
		var timeType = $("#timeType").val();
		if(timeType == 1) {
			var now = new Date();				//当前时间
			var year = now.getFullYear();       //年
        	var month = now.getMonth() + 1;     //月
        	var day = now.getDate();            //日
        	var today = year + "-" + month + "-" + day;
			var beginTime = today + " 00:00:00";
			var endTime = today + " 23:59:59";
			
			$("#beginTime").val(beginTime);
			$("#endTime").val(endTime);
		} else if(timeType == 2) {
			var now = new Date();				//当前时间
			now = new Date(now.getTime() - 24 * 60 * 60 * 1000);
			var year = now.getFullYear();       //年
        	var month = now.getMonth() + 1;     //月
        	var day = now.getDate();        	//日
        	var today = year + "-" + month + "-" + day;
			var beginTime = today + " 00:00:00";
			var endTime = today + " 23:59:59";
			
			$("#beginTime").val(beginTime);
			$("#endTime").val(endTime);
		} else if(timeType == 3) {
			var now = new Date();				//当前时间
			var year = now.getFullYear();       //年
        	var month = now.getMonth() + 1;     //月
        	var day = now.getDate();        	//日
        	var nextDay = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
        	var nextYear = nextDay.getFullYear();  
        	var nextMonth = nextDay.getMonth() + 1;   
        	var nextDay = nextDay.getDate();        	
			var beginTime = nextYear + "-" + nextMonth + "-" + nextDay + " 00:00:00";
			var endTime = year + "-" + month + "-" + day + " 23:59:59";
			
			$("#beginTime").val(beginTime);
			$("#endTime").val(endTime);
		}
	}
	
	//查询
	function query() {
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var start_date_val = new Date(beginTime.replace(/-/g,"/"));
	    var end_date_val = new Date(endTime.replace(/-/g,"/"));
	    if(start_date_val > end_date_val){
	        alert("结束日期要大于等于开始日期！");
            return false;
	    }
	   
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	//导出
	function exportData(){
		var userType = $("#userType").val();
		var timeType = $("#timeType").val();
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		location.href = "${rc.getContextPath()}/showStatistics/userExport?timeType="+timeType+"&userType="+userType+"&beginTime="+beginTime+"&endTime="+endTime;
	}
	
	//查询用户秀统计
	function viewUserShowStatistics(times) {
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		if(times != '总计') {
			beginTime = times + " 00:00:00";
			endTime = times + " 23:59:59";
		} 
		var pageUrl = "${rc.getContextPath()}/showStatistics/userShow?showNumOrder=1"+"&beginTime="+beginTime+"&endTime="+endTime;
		window.open(pageUrl);
	}
	
</script>
</body>
</html>