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
			<h3 class="topTitle fb f14">H5列表管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/h5template/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>H5组件模板管理</dd>
				<dd class="last"><h3>H5模板列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">

		        <label>&nbsp;
			    	<a href="${rc.getContextPath()}/h5template/addUI" title="新增H5组件模板" class="btn" style="width:80px;text-align:center;height:22px;">新增模板</a>
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

					<th width="10%">ID</th>
					<th width="15%">标题</th>
					<th width="12%">图片</th>
					<th width="8%">状态</th>
					<th width="12%">创建人</th>
					<th width="15%">创建时间</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (list?? && list?size > 0)>
			<#list list as item>
			<tr>
			    <td>${item.id!''}
			    </td>
			    <td>${item.name!''}</td>
			    <td>
			     <div style="  position: relative;">
				    <img width="75px" height="28px" class="tdFocusPic" src="${item.thumbnailUrl!''}"/>
				    <img style="display:none;bottom: 0px;left: 95%;background-color: white;position: absolute;  border: 2px solid #ddd;" src="${item.thumbnailUrl!''}" width="380px"  />
				 </div>
			    </td>
			    <td>				    
			        <#if item.state?? && item.state == 0>有效
					<#elseif item.state?? && item.state==1>无效
					</#if>
				</td>
			    <td>
			    ${item.lastEditer!''}
			    </td>
			    <td>${item.lastupdateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
				<td>
					<a href="${rc.getContextPath()}/h5template/updateUI?id=${item.id!''}" title="编辑">编辑</a>
					
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<div id="deleteSubjectDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此专题？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteSubjectAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<!-- 分页文件-->
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">

   $(function(){
      $(".tdFocusPic").mouseover(function(){
        $(this).next().css("display","");
      }).mouseout(function(){
        $(this).next().css("display","none");
      });
     
   });

	

</script>
</body>
</html>