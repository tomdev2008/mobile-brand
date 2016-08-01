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
			<h3 class="topTitle fb f14">公告位管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/noticePlace/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>WAP管理</dd>
				<dd class="last"><h3>公告位管理</h3></dd>
		</dl>
		<!-- 导航 end -->
	   <!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
				 <label>
				    <a href="javascript:void(0);" title="添加公告位" class="btn" onclick="return addNoticePlace()" style="width:50px;text-align:center;height:22px;float:right;" id="query">添&nbsp;&nbsp;加</a>
				 </label>
				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="20%">公告位名称</th>
						<!--<th width="25%">公告位备注</th>-->
						<th width="25%">操作人</th>
						<th width="25%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (advertisementPlacelist?size > 0)>
				   <#list advertisementPlacelist as  advertisementPlace>
				      <tr>
				        <td>${advertisementPlace.name!''}</td>
						<!--<td>${advertisementPlace.memo!''}</td>-->
						<td>${advertisementPlace.create_by!''}</td>
						<td>
						  <a href="javascript:void(0);" onclick="toedit('${(advertisementPlace.id)!''}');" title="编辑">编辑</a>|
						  <a href="javascript:void(0);" onclick="toNoticePage(${(advertisementPlace.id)!''},'${(advertisementPlace.name)!''}');" title="编辑">详细</a>
						  <a href="javascript:void(0);" onclick="deleteNoticePlace(${(advertisementPlace.id)!''});" title="删除">删除</a>
						</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="4"><font color="red">暂时没有查询到记录</font></td></tr>
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

   //添加公告位
   function addNoticePlace(){
      location.href="${rc.getContextPath()}/noticePlace/create";
   }   
   
   //编辑公告位
   function toedit(id){
    //if(window.confirm('您确定要修改该公告位吗?')){
      location.href="${rc.getContextPath()}/noticePlace/toedit?id="+id;
    //}
   }
   //跳转到公告列表页面
   function toNoticePage(advPlaceId,advPlaceName){
   	location.href="${rc.getContextPath()}/notice/list?advPlaceId="+advPlaceId+"&advPlaceName="+encodeURI(encodeURI(advPlaceName));
   }
    //删除公告位
   function deleteNoticePlace(id){
      if(window.confirm('您确定要删除该公告位吗?')){
			$.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/noticePlace/delete?format=json",
					data : {"id":id},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //修改成后,刷新列表页
								   location.href = "${rc.getContextPath()}/noticePlace/list";
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