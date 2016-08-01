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
			<h3 class="topTitle fb f14">版本更新管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="editForm" name="editForm" action="${rc.getContextPath()}/version/update" enctype="multipart/form-data" method="post">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd><a href="${rc.getContextPath()}/version/list">版本更新管理</a></dd>
			<dd class="last"><h3>编辑版本更新信息</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">修改版本更新信息</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>版本名称：</th>
				<td class="tdBox">
					<input type="text" name="name"  value="${version.name!''}" style="width:100px;"/>
					<input type="hidden" id="id" name="id" value="${version.id}"/>
					<span id="name" style="color: red">对外版本名称为：X.Y.Z，总共为3位</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>发布时间：</th>
				<td class="tdBox">
					<input type="text" name="pubTimeC" id="pubTimeC" value="${version.pubTime?string('yyyy-MM-dd')}" style="width:100px;" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'pubTimeC\')}'})"/>
					<span id="pubTime_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>App类型：</th>
				<td class="tdBox">
				<input type="radio" name="type" id="type_01" value="1" 
				<#if version.type?? && version.type == 1>checked="true"</#if> onclick="changeView(1);"/><label>Android</label>
				<input type="radio" name="type" id="type_02" value="2" 
				<#if version.type?? && version.type == 2>checked="true"</#if> onclick="changeView(2);"/><label>IPhone</label>
				<input type="radio" name="type" id="type_03" value="3" 
				<#if version.type?? && version.type == 3>checked="true"</#if> onclick="changeView(3);"/><label>iPad</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>状态：</th>
				<td class="tdBox">
				<input type="radio" name="status" id="status_01" value="1" <#if version.status?? && version.status == 1>checked="true"</#if>/><label>启用</label>
                <input type="radio" name="status" id="status_02" value="0" <#if version.status?? && version.status == 0>checked="true"</#if>/><label>停用</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>是否强制更新：</th>
				<td class="tdBox">
				<input type="radio" name="forcedUpdate" id="forcedUpdate_01" value="1" <#if version.forcedUpdate?? && version.forcedUpdate == 1>checked="true"</#if> onclick="changeForcedUpdate(1)" /><label>是</label>
                <input type="radio" name="forcedUpdate" id="forcedUpdate_02" value="0" <#if version.forcedUpdate?? && version.forcedUpdate == 0>checked="true"</#if> onclick="changeForcedUpdate(2)" /><label>否</label>
				</td>
			</tr>
			<tr id="forcedUpdate_tr" <#if version.forcedUpdate?? && version.forcedUpdate == 0>style="display:none"</#if> >
				<th class="thList" scope="row"><span class="red">*</span>强制更新版本号：</th>
				<td class="tdBox">低于或等于 <input type="text" id="forcedBeforeVerno" name="forcedBeforeVerno" style="width:100px;" value="${version.forcedBeforeVerno!''}" /> 则强制更新
				</td>
			</tr>
			
			<tr id="dataFile_tr" <#if version.type?? && version.type == 1><#else>style="display:none"</#if>>
				<th class="thList" scope="row"><span class="red">*</span>安卓APK文件：</th>
				<td class="tdBox">
					<input name="dataFile" id ="dataFile" type="file"  class="inp24 w200" onchange="checkApk(this);"  /> 
				</td>
			</tr>
			<tr id="url_tr" >
				<th class="thList" scope="row"><span class="red">*</span>已设置链接地址：</th>
				<td class="tdBox">
					<input type="text" name="url"  value="${version.url!''}" style="width:300px;"/>
					<span id="url" style="color: red"></span>
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>版本号：</th>
				<td class="tdBox">
					<input type="text" name="versionNo"  value="${version.versionNo!''}" style="width:100px;"/>
					<span id="versionNo" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>更新内容：</th>
				<td class="tdBox">
					<textarea id="content" name="content" style="width:300px;height:80px;">${version.content!''}</textarea>&nbsp;4/48字数
					<span id="content" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return update();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="self.close();return false;"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
function changeView(type) {
    if(type == 1) { // Android
       $("#dataFile_tr").show();
    } 
    else if(type == 2) { //iPhone
       $("#dataFile_tr").hide();
    } else if(type == 3) { //iPad
       $("#dataFile_tr").hide();
    }
}

function changeForcedUpdate(type) {
	if(type == 1) {
		$("#forcedUpdate_tr").show();
	} else if(type == 2) {
		$("#forcedUpdate_tr").hide();
		$("#forcedBeforeVerno").val("");
	}
}

function checkApk(file){
	var subfix = file.value.substring(file.value.lastIndexOf(".") + 1);    
	if(subfix != "apk"){
		alert("必须是apk文件!");
		file.outerHTML= file.outerHTML.replace(/(value=\").+\"/i,"$1\"");
		return false;
	}
	return true;
}

//用Ajax提交信息
function update(){
   
   var name = $("input[name='name']").val();
   var pubTimeC = $("input[name='pubTimeC']").val();
   var url = $("input[name='url']").val();
   var versionNo = $("input[name='versionNo']").val();
   var forcedBeforeVerno = $("#forcedBeforeVerno").val();
   
   if(name == null || undefined == name || name == '') {
      alert("版本名称不能为空！");
      return;
   }
   
   if(pubTimeC == null || undefined == pubTimeC || pubTimeC == '') {
      alert("发布时间不能为空！");
      return;
   }
   
   if(versionNo == null || undefined == versionNo || isNaN(versionNo)) {
      alert("版本号为版本名称去掉 . 的数字！");
      return;
   }
   
   //如果强制更新，则需要输入最低版本号
   if($("#forcedUpdate_01").attr("checked")) {
      if(forcedBeforeVerno == null || undefined == forcedBeforeVerno || forcedBeforeVerno == '' || isNaN(forcedBeforeVerno)) {
        alert("强制更新版本号不能为空，且必须为数字！");
        return ;
      }
      if(parseInt(versionNo) < parseInt(forcedBeforeVerno)) {
        alert("强制更新版本号 不能大于 版本号！");
        return ;
      }
   } 
   
   $("#editForm").submit();
}
</script>
</html>