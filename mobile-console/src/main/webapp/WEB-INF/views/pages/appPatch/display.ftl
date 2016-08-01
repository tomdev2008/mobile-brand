<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>

<style>
	
.btns_2 {
    border: 1px solid #babac0;
    line-height: 48px;
    padding:5px;
}
#labelName{
	height: 30px;
}
.addBtn{
	display:none;
	float: left
}
.insertBtn{
	display:block;
	float: left
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
			<h3 class="topTitle fb f14">专题添加</h3>
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
			<dd><a href="${rc.getContextPath()}/subject/list">热更新管理</a></dd>
			<dd class="last"><h3>热更新添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addSubjectForm" name="addSubjectForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/appPatch/save">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="3" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>

			<tr>
				<th class="thList" scope="row"><font class="red">*</font>热更新标题：</th>
				<td class="tdBox">
					  <input type="text"  name="name" id="name" value="${appPatchVO.name!''}" disabled="disabled"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">指定APP版本：</th>
				<td class="tdBox">
					  <input type="radio"  name="type" id="title" value="appstore" <#if appPatchVO.type=='appstore'> checked="checked"</#if>/>appstore : <input type="text" name="version" <#if appPatchVO.type=='appstore'>  value="${appPatchVO.version!''}" </#if> disabled="disabled"/>
					  <input type="radio"  name="type" id="title" value="appPro"  <#if appPatchVO.type=='appPro'> checked="checked"</#if>/> appPro:     <input type="text" name="version" <#if appPatchVO.type=='appPro'>  value="${appPatchVO.version!''}"</#if> disabled="disabled"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>是否强制升级：</th>
				<td class="tdBox">
	 					<input type="radio" name="needUpdate" class="displayStytle" value="0" <#if appPatchVO.needUpdate==0>   checked="checked" </#if>/>否
	 					<input type="radio" name="needUpdate" class="displayStytle" value="1" <#if appPatchVO.needUpdate==1>   checked="checked" </#if>   />是</td> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>是否启用：</th>
				<td class="tdBox">
	 					<input type="radio" name="status" class="displayStytle" value="0"    <#if appPatchVO.status==0>   checked="checked" </#if> />停用
	 					<input type="radio" name="status" class="displayStytle" value="1"   <#if appPatchVO.status==1>   checked="checked" </#if> />启用</td> 
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>更新文件：</th>
				<td class="tdBox">
					  <input type="text" name="out_pic" id="out_pic" value="${appPatchVO.path!''}" disabled="disabled" style="width:500px;" /> 
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>热更新说明：</th>
				<td class="tdBox">
					<textarea name="remark" rows="3" cols="40">${appPatchVO.remark!''}</textarea>
				</td>
			</tr>
			
		</table>
	</div>
</form>

</script>
</html>