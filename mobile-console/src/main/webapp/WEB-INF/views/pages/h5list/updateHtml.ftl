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


</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">H5列表修改</h3>
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
			<dd>话题管理</dd>
			<dd><a href="${rc.getContextPath()}/showTopic/list">H5列表管理</a></dd>
			<dd class="last"><h3>H5列表修改</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addH5Form" name="addH5Form" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/h5list/updateNew">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
				<input type="hidden" name="type" value="2"/>
				<input type="hidden" name="id" value="${h5list.id!''}"/>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>H5列表标题：</th>
				<td class="tdBox">
				  <input id="title" name="title" value="${h5list.title!''}" style="width:200px;"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">H5列表图：</th>
				<td class="tdBox">
					  <img src="${h5list.photo!''}" width="300px"/>
					  <input type="file" name="photo" id="topic_pic_f"/> 格式：750*330 的jpg类型图片
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>URL链接：</th>
				<td class="tdBox">
				 ${h5list.h5Url!''}<input id="h5Url" name="h5Url" value="${h5list.h5Url!''}" type="hidden"/> 不可修改
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">内容组件：</th>
				<td class="tdBox">
                    <input type="radio" name="style"  value="1"  <#if h5list.style==1>checked="checked"</#if>/> 组件1
                     <input type="radio" name="style"  value="2" <#if h5list.style==2>checked="checked"</#if>/> 组件2
                     <input type="radio" name="style"  value="3" <#if h5list.style==3>checked="checked"</#if>/> 组件3
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
                     <input type="radio" name="status"  value="1"  <#if h5list.status==1>checked="checked"</#if>/> 显示到H5列表
                     <input type="radio" name="status"  value="2" <#if h5list.status==2>checked="checked"</#if>  /> 不显示到H5列表
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">上传：</th>
				<td class="tdBox">
					<input name="download" id ="download" type="button"  value="下载模板" onclick="downloadTemplate(${h5list.id!''});"  /> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">选择文件：</th>
				<td class="tdBox">
					<input name="dataFile" id ="dataFile" type="file" class="inp24 w400" onchange="checkExcel(this);"  /> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标签：</th>
				<td class="tdBox">
					<#include "/common/label.ftl">  
				</td>
			</tr>
			 <tr>
                        <th class="thList" scope="row">功能：</th>
                        <td class="tdBox">
                            <input type="checkbox" name="shareCheck"
                            <#if h5list.isShare=1>checked</#if> />右下角增加分享按钮
                        </td>
             </tr>
			 <tr>
                        <th class="thList" scope="row">分享标题：</th>
                        <td class="tdBox">
                           <input name="shareTitle" style="width:200px;" value="${h5list.shareTitle!''}"/>
                        </td>
             </tr>
			 <tr>
                        <th class="thList" scope="row">分享内容：</th>
                        <td class="tdBox">
                           <input name="shareContent" style="width:200px;" value="${h5list.shareContent!''}"/>
                        </td>
             </tr>
			<tr>
                <th class="thList" scope="row">分享图片：</th>
                    <td class="tdBox">
                    	<#if h5list.sharePhoto??>
                    	<img src="${h5list.sharePhoto}" width="300px">
                    	</#if>
                        <input type="file" name="sharePic" id="topic_pic_f" value="${h5list.sharePhoto!''}" />
                 </td>
            </tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
					<p class="fl">
					<input type="button" onclick="update();" class="userOperBtn" value="确认提交"></input> 
					</p>				
			    </td>
			</tr>
		</table>
				
	</div>
</form>
</div>
</div>
<input type="hidden" value="${isSuccess!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>
</body>
<script type="text/javascript">

$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("修改成功");
	   location.href="${rc.getContextPath()}/h5list/list";
	 }else if(msg!=""){
	 	alert(msg);
	 }
});

	function update(){
		var pic=$("#topic_pic_f").val();
	    var title=$("#title").val();
	    var h5Url=$("#h5Url").val();
	    if(title==""){
	    	alert("必须输入标题");
	    	return ;
	    }
	    if(h5Url==""){
	    	alert("必须绑定一个链接");
	    	return ;
	    }
	    if(pic!=''){
		    if(pic.toLowerCase().indexOf(".jpg")<0){
		   		alert("请上传.jpg类型的图片！");
		   		return false;
		    }
	    }
	   	if(confirm("确定修改此H5吗")){
	   		$("#addH5Form").submit();
	   	}
	}
	//下载模板
	function downloadTemplate(id){
		location.href = "${rc.getContextPath()}/h5list/downUpdate?id="+id;
	}
	function checkExcel(file){
		var subfix = file.value.substring(file.value.lastIndexOf(".")+1);    
		if(subfix!="xls"){
			alert("必须是xls文件!");
			file.outerHTML= file.outerHTML.replace(/(value=\").+\"/i,"$1\"");
			return false;
		}
		return true;
    }
</script>
</html>