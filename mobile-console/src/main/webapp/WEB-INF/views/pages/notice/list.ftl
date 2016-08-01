<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
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
			<h3 class="topTitle fb f14">公告管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/notice/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>WAP管理</dd>
				<dd class="last"><h3>公告管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
						 <label>
						    <a href="javascript:void(0);" title="添加公告" class="btn" onclick="return addAdvertisement(${advertisement.adv_place_id},'${advertisement.advPlaceName}')" style="width:50px;text-align:center;height:22px;float:right;" id="query">添&nbsp;&nbsp;加</a>
						 </label>
				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->
    <input type="hidden" name="advPlaceId" value="${(advertisement.adv_place_id)!''}" />
    <input type="hidden" name="advPlaceName" value="${(advertisement.advPlaceName)!''}" />
	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="10%">公告位名称</th>
						<th width="10%">公告内容</th>
						<th width="10%">开始时间</th>
						<th width="10%">结束时间</th>
						<th width="10%">更新时间</th>
						<th width="10%">操作人</th>
						<th width="10%">是否显示</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (advertisementlist?size > 0)>
				   <#list advertisementlist as advertisement>
				      <tr>
				      	<#if advertisement_index == 0>
				        <td rowspan="${advertisementlist?size}">${advertisement.advPlaceName!''}</td>
				        </#if>
						<td>${advertisement.memo!''}</td>
						<td>${advertisement.start_time?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${advertisement.end_time?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td><#if advertisement.last_update_dt??>${advertisement.last_update_dt?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
						<td>${advertisement.create_by!''}</td>
						<td><#if advertisement.effective?? && advertisement.effective==1>是<#else>否</#if></td>
						<td>
						  <a href="javascript:void(0);" onclick="toedit(${(advertisement.id)!''},'${(advertisement.advPlaceName)!''}');" title="编辑">编辑</a>|
						  <a href="javascript:void(0);" onclick="deleteAdvertisement(${(advertisement.id)!''},${(advertisement.adv_place_id)!''},'${(advertisement.advPlaceName)!''}');" title="删除">删除</a>
						</td>
					  </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="8"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
	</div>	
</div>
 <!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
</body>
<script type="text/javascript">
    
   
   //添加公告
   function addAdvertisement(advPlaceId,advPlaceName){
      location.href="${rc.getContextPath()}/notice/create?advPlaceId="+advPlaceId+"&advPlaceName="+encodeURI(encodeURI(advPlaceName));
   }
   //编辑公告
   function toedit(id,advPlaceName){
    //if(window.confirm('您确定要修改该公告吗?')){
      location.href="${rc.getContextPath()}/notice/toedit?id="+id+"&advPlaceName="+encodeURI(encodeURI(advPlaceName));
    //}
   }
    //删除公告
   function deleteAdvertisement(id,advPlaceId,advPlaceName){
      if(window.confirm('您确定要删除该公告吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/notice/delete?format=json",
					data : {"id":id},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/notice/list?advPlaceId="+advPlaceId+"&advPlaceName="+encodeURI(encodeURI(advPlaceName));
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
</html>