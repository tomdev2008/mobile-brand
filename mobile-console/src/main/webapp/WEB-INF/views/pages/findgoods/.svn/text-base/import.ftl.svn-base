<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">导入商品</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="saveFindGoods" name="saveFindGoods" action="${rc.getContextPath()}/findgoods/import_excel" method="post" enctype="multipart/form-data">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>单品发现</dd>
			<dd class="last"><h3>导入商品</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">导入商品</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">下载模板：</th>
				<td class="tdBox">
					<input name="download" id ="download" type="button"  value="下载模板" onclick="downloadTemplate();"  /> 
					（导入时请遵循模板数据格式）
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>选择文件：</th>
				<td class="tdBox">
					<input name="dataFile" id ="dataFile" type="file" class="inp24 w400" onchange="checkExcel(this);"  /> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">提示信息：</th>
				<td class="tdBox">
					<textarea id="content" name="content" readonly="readonly" style="width:400px;height:300px;color:red;">${(error!'')?html}</textarea>
					<span id="content_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkFileNullAndSubmit();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnGoodsList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
					<span id="error_c" style="color: red"></span>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
	function checkExcel(file){
		var subfix = file.value.substring(file.value.lastIndexOf(".")+1);    
		if(subfix!="xls"){
			alert("必须是xls文件!");
			file.outerHTML= file.outerHTML.replace(/(value=\").+\"/i,"$1\"");
			return false;
		}
		return true;
    }
      
     function checkFileNullAndSubmit(){
	     var objFile=document.getElementById("dataFile");
	     if(!objFile.value){
		     alert("文件不能为空!");
		     return;
	     }
	     var flag = checkExcel(objFile);
	     if(flag){
			document.getElementById("saveFindGoods").submit();
	     }
     }
	
	function downloadTemplate(){
		location.href = "${rc.getContextPath()}/findgoods/down";
	}
	
	//取消事件，返回单品发现管理列表
	function returnGoodsList(){
	  location.href = "${rc.getContextPath()}/findgoods/list";
	}
</script>
</html>