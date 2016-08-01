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
			<h3 class="topTitle fb f14">秀集合添加</h3>
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
			<dd><a href="${rc.getContextPath()}/subject/list">秀集合管理</a></dd>
			<dd class="last"><h3>秀集合添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addSubjectForm" name="addSubjectForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/show/saveShowCollection">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="3" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>

			<tr>
				<th class="thList" scope="row"><font class="red">*</font>秀集合标题：</th>
				<td class="tdBox">
					  <input type="text"  name="name" id="name" maxlength="100"  style="width:500px;"/> 
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>秀集合列表图：</th>
				<td class="tdBox">
					  <input type="file" name="listImgPath" id="listImgPath"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>秀集合详情图：</th>
				<td class="tdBox">
					  <input type="file" name="detailImgPath" id="detailImgPath"/> 
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>文本：</th>
				<td class="tdBox">
					<textarea name="detailText" rows="12" cols="100" maxlength="1000"></textarea>
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
                    <p class="fl">
						<input type="submit" class="userOperBtn" value="确认提交"></input> 
					</p>
				</td>
			</tr>
		</table>
	</div>
</form>

</script>
</html>