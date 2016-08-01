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
				<h3 class="topTitle fb f14">组件模板添加</h3>
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
				<dd><a href="${rc.getContextPath()}/h5template/list">组件模板管理</a></dd>
				<dd class="last"><h3>组件模板添加</h3>
				</dd>
			</dl>
		</div>
		<!--导航end-->
		<form id="addTemplateForm" name="addTemplateForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/h5template/addOrUpdate">
			<input type="hidden" name="id" value="${template.id!''}"></input>
			<!--菜单内容-->
			<div class="adminContent clearfix">
				<table width="100%" class="table_bg05">
					<tr>
						<th colspan="3" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
					</tr>
		
					<tr>
						<th class="thList" scope="row"><font class="red">*</font>模板名称：</th>
						<td class="tdBox">
							  <input  name="name" id="name" value="${template.name!''}" style="width:300px;" /> 
						</td>
					</tr>
					<tr>
						<th class="thList" scope="row">模板内容：</th>
						<td class="tdBox">
							  <textarea name="template" id="template" style="width:70%;height:400px;">${template.template!''}</textarea>
						</td>
					</tr>
					<tr>
						<th class="thList" scope="row">数据规范：</th>
						<td class="tdBox">
							  <textarea name="dataTemplate" id="dataTemplate" style="width:70%;height:100px;">${template.dataTemplate!''}</textarea>
						</td>
					</tr>
					<tr>
						<th class="thList" scope="row"><font class="red">*</font>模板缩略图：</th>
						<td class="tdBox">
							  <input type="file" name="thumbnail" id="thumbnail"/> 
						</td>
					</tr>
					<tr>
						<th class="thList" scope="row">js链接：</th>
						<td class="tdBox">
							  <input name="jsUrl" id="jsUrl" value="${template.jsUrl!''}" style="width:70%;height:50px;"></input>
						</td>
					</tr>
					
					<tr>
						<th class="thList" scope="row">css链接：</th>
						<td class="tdBox">
							  <input name="cssUrl" id="cssUrl" value="${template.cssUrl!''}" style="width:70%;height:50px;"></input>
						</td>
					</tr>
					
					<tr>
						<th class="thList" scope="row">状态：</th>
						<td class="tdBox">
							  <select id="state" name="state">
								 <option value="0" <#if template.state = 0>selected</#if> >-启用-</option>
					             <option value="1" <#if template.state = 1>selected</#if> >-停用- </option>
							  </select>
						</td>
					</tr>
					
					<tr>
						<th class="thList" scope="row">模板处理器：</th>
						<td class="tdBox">
							  <select id="templateHandler" name="templateHandler">
							     <option value="" >--请选择--</option>
								 <option value="oneOnoneHandler" <#if template.templateHandler?? && template.templateHandler = 'oneOnoneHandler'>selected</#if> >数据一对一处理器</option>
								 <option value="storeDataTypeHandler" <#if template.templateHandler?? && template.templateHandler = 'storeDataTypeHandler'>selected</#if> >卖场特例处理器</option>
							  </select>
						</td>
					</tr>
					
					<tr>
						<th class="thList" scope="row">模板排序权重：</th>
						<td class="tdBox">
							<input type="text" maxlength="3" onchange="if(/\D/.test(this.value)){alert('只能输入数字');this.value=0;}" name="templateWeight" value="${template.templateWeight!''}" style="width:50px;"/>
							<span>*只可输入0~999之间的数字，数字越大，模板排序越靠前。</span>
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
	</div>
</div>
</body>
</html>