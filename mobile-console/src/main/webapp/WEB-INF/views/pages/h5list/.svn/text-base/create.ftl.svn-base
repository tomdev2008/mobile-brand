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
			<h3 class="topTitle fb f14">H5列表添加</h3>
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
			<dd class="last"><h3>H5列表添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addH5Form" name="addH5Form" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/h5list/add">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">添加方式：</th>
				<td class="tdBox">
                     <input type="radio" name="type" class="addType"   value="1"  checked="checked"/>输入链接
                     <input type="radio" name="type" class="addType"   value="2"/>模板导入
                     <input type="radio" name="type" class="addType"    value="3"/>组件添加
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>标题：</th>
				<td class="tdBox">
				  <input id="title" name="title" value="" style="width:200px;"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>图片：</th>
				<td class="tdBox">
					  <input type="file" name="photo" id="topic_pic_f"/> 格式：750*330 的jpg类型图片
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
                     <input type="radio" name="status"  value="1"  checked="checked" />显示
                     <input type="radio" name="status"  value="2"   />隐藏
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
                            />右下角增加分享按钮&nbsp;&nbsp;
                        </td>
             </tr>
           <tr>
                        <th class="thList" scope="row">分享标题：</th>
                        <td class="tdBox">
                          <input name="shareTitle" style="width:200px;" value=""/>
                        </td>
             </tr>
           <tr>
                        <th class="thList" scope="row">分享内容：</th>
                        <td class="tdBox">
                          <input name="shareContent" style="width:200px;" value=""/>
                        </td>
             </tr>
             <tr>
                        <th class="thList" scope="row">分享图片：</th>
                        <td class="tdBox">
                            <input type="file" name="sharePic" id="topic_pic_f" value="" />
                        </td>
               </tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>URL链接：</th>
				<td class="tdBox">
				<input id="middleUrl" value="${middleUrl!''}" type="hidden"/>
				<font class="otherUrlText" style="display:none"> http://m.xiu.com/H5</font> 
				<input id="h5Url" name="h5Url" value="" style="width:200px;"/>
				<font  class="otherUrlText" style="display:none">.html</font>
				</td>
			</tr>
			</table>
			<div id="model2" style="display:none">
			<table width="100%" class="table_bg05">
			<tr>
				<th class="thList" scope="row">内容组件：</th>
				<td class="tdBox">
                     <input type="radio" name="style"  value="1"  checked="checked"/>组件1
                     <input type="radio" name="style"  value="2"/>组件2
                     <input type="radio" name="style"  value="3"/>组件3
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">上传：</th>
				<td class="tdBox">
					<input name="download" id ="download" type="button"  value="下载模板" onclick="downloadTemplate();"  /> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">选择文件：</th>
				<td class="tdBox">
					<input name="dataFile" id ="dataFile" type="file" class="inp24 w400" onchange="checkExcel(this);"  /> 
				</td>
			</tr>
			</table>
			</div> 
			<div id="model3" style="display:none">
				<table width="100%" class="table_bg05">
				<tr>
					<th class="thList" scope="row">背景色：</th>
					<td class="tdBox">
	                   	<input id="bgColor" name="bgColor" value="" style="width:100px;" />
					</td>
				</tr>
				</table>
			</div> 
		</table>
		<table width="100%" class="table_bg05">
				<tr>
					<th class="thList" scope="row"></th>
					<td class="tdBox">
						<p class="fl">
						<input type="button" onclick="add();" class="userOperBtn" value="确认提交"></input> 
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
	   alert("添加成功");
	   location.href="${rc.getContextPath()}/h5list/list";
	 }else if(msg!=""){
	 	alert(msg);
	 }
	 
	 
	 $(".addType").click(
	   function(){
	          var value=$(this).val();
		      if(value==1){
		         $("#model2").css("display","none");
		         $("#model3").css("display","none");
		         $(".otherUrlText").css("display","none");
		         $("#h5Url").val("");
		      }else if(value==2){
		         $("#model2").css("display","");
		         $("#model3").css("display","none");
		         $(".otherUrlText").css("display","");
		         $("#h5Url").val($("#middleUrl").val());
		      }else if(value==3){
		         $("#model2").css("display","none");
		         $("#model3").css("display","");
		         $(".otherUrlText").css("display","");
		         $("#h5Url").val($("#middleUrl").val());
		      }
		  }
	 );
	 
});

	function add(){
		var title=$("#title").val();
	    var pic=$("#topic_pic_f").val();
	    var h5Url=$("#h5Url").val();
	     var status=$("input[name='status']:checked").val();//是否显示，如果值为2，则不需要上传图片
	    if(title==""){
	    	alert("必须输入标题");
	    	return ;
	    }
	    if(status==1){
	    	if(pic==""){
		    	alert("必须上传一张图片");
		    	return ;
		    }
	    	if(pic.toLowerCase().indexOf(".jpg")<0){
		   		alert("请上传.jpg类型的图片！");
		   		return false;
		    }
	    }
	    if(h5Url==""){
	    	alert("必须绑定一个链接");
	    	return ;
	    }
	   	
	    if(confirm("确定发布此H5吗")){
	    	$("#addH5Form").submit();
	    }
	}
</script>
</html>