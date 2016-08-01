<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<style>
	.topicDiv {
		width:380px; 
		height:200px; 
	}
	
	.placeP {
		width:100%;
		height:160px;
	}
	
	.wrap {
		width:380px; 
		height:40px; 
		background: rgba(0,0,0,0.7); 
		font-family:"微软雅黑", Arial, Helvetica, sans-serif;
		line-height:40px;
		color:white;
		display:-moz-box;
	    display:-webkit-box;
	    display:box;
	}
	
	.textOverflow {
		overflow:hidden;
		text-overflow:ellipsis;
		white-space:nowrap;
	}
	
	.spanLeft {
		-moz-box-flex:2;
	    -webkit-box-flex:2;
	    box-flex:2;
	}
	
	.spanRight {
		-moz-box-flex:1;
	    -webkit-box-flex:1;
	    box-flex:1;
	    text-align:right;
	}
</style>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">预览卖场列表</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/topic/previewTopicList" method="GET">
	<input type="hidden" id="now_time" name="now_time" value="<#if now_time??>${now_time}</#if>" />
	<input type="hidden" id="topicTypeId" name="topicTypeId" value="${topicTypeId!''}" />
	<div id="topicTypeDiv" style="width:100%; text-align:center;">
		<#if topicTypeList?? && (topicTypeList?size > 0)>
			<#list topicTypeList as topicType>
				<input type="button" id="btn_${topicType.id!''}" class="btn" name="topicTypeButton" value="${topicType.name!''}" 
					onclick="updateTopic(${topicType.id!''})" <#if topicType.id?string==topicTypeId>style="border:1px solid DeepSkyBlue;"</#if> />
			</#list>
		</#if>
	</div>
	<table style="margin:0 auto;">
		<tbody>
			<#if topicList?? && (topicList?size > 0)>
				<#list topicList as topic>
					<tr>
						<td>
							<div class="topicDiv" style="background:url('http://6.xiustatic.com${topic.mobile_pic!''}') no-repeat; background-size:380px 200px; ">
								<a <#if topic.contentType == 2> href="${topic.URL!''}" 
								<#else> href="http://www.xiu.com/cx/${topic.activity_id!}.shtml"
								</#if> target="_BLANK">
								<p class="placeP"></p>
								<div class="wrap textOverflow">
									<section class="spanLeft">&nbsp;&nbsp;&nbsp;${topic.name!''}</section>
								    <section class="spanRight">${topic.dateInfo!''}&nbsp;&nbsp;&nbsp;</section>
								</div>
								</a>
							</div>
						</td>
					</tr>
				</#list>
			</#if>
		</tbody>
	</table>
	<!-- 分页文件-->
    <#include "/common/page.ftl">  
</form>

</div>
</body>
<script type="text/javascript">
	function updateTopic(topicTypeId) {
		$("input:button[name=topicTypeButton]").css("border","");
		$("#btn_"+topicTypeId).css("border","1px solid DeepSkyBlue");
		var now_time = $("#now_time").val();
		location.href="${rc.getContextPath()}/topic/previewTopicList?topicTypeId="+topicTypeId+"&now_time="+now_time;
	}
</script>
</html>