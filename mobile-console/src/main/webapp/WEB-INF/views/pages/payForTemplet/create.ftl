<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery.colorpicker.js"></script>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">编辑菜单</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="saveTempletForm" name="saveTempletForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/payForTemplet/save">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>配置管理</dd>
			<dd><a href="${rc.getContextPath()}/payForTemplet/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">朋友代付模板管理</a></dd>
			<dd class="last"><h3>添加朋友代付模板</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加朋友代付模板</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>模板标题：</th>
				<td class="tdBox">
					<input type="text" name="title" id="title" maxlength="100"/>
					&nbsp;&nbsp;
					<span style="color:gray">注：控制在30个字符（15个汉字）以内</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">模板背景色：</th>
				<td class="tdBox" style="vertical-align:middle;">
					<input type="text" name="backgroundColor" id="backgroundColor" value="#EA7515" maxlength="7" />
					<input type="text" id="colorBlock" style="width:19px; background-color:#EA7515;" readonly="true" />
					<img src="${rc.getContextPath()}/resources/common/images/colorpicker.png" id="backgroundColorChoose" style="cursor:pointer; padding-bottom:4px;"/>
					&nbsp;&nbsp;
					<span style="color:gray">注：尽量不要用白色背景</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>模板主图：</th>
				<td class="tdBox">
					<input type="file" name="templet_pic_f" id="templet_pic_f"/>
					&nbsp;&nbsp;
					<span style="color:gray">注：图片尺寸：197x197px</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">排序（降序）：</th>
				<td class="tdBox">
	            	<input type="text" name="orderSeq" id="orderSeq" value="0" maxlength="4"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
					<input type="radio" name="status" id="statusType_01" value="1" /><label>启用</label>
					<input type="radio" name="status" id="statusType_02" value="0" checked="true" /><label>停用</label>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return go();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="javascript:history.go(-1);" class="ml10"> <img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
    
    $("#backgroundColorChoose").colorpicker({
	    fillcolor:true,
	    target:$("#backgroundColor"),
	    success:function(o,color) {
	    	$("#colorBlock").css("background-color",color);
	    },
	    reset:function(o) {
	    	$("#colorBlock").css("background-color","white");
	    }
	});
    
   //验证信息
    function go(){
      var title=$("#title").val();
      var pic=$("#templet_pic_f").val();
      var seq = $("#orderSeq").val();
      var colorText = $("#backgroundColor").val();
      
      if(title==""){
        alert("模板标题不能为空！");
        return false;
      }
      
      if(colorText != "") {
      	var firstColorText = colorText.substr(0,1);
      	if(firstColorText != '#') {
      		alert("请选择正确的颜色代码！");
      		return false;
      	}
      	var reg = /^#[A-Za-z0-9]+$/;	//验证规则
      	var flag = reg.test(colorText);
      	if(!flag) {
	 		alert("请选择正确的颜色代码！");
	 		return false;
	 	}
	 	if(colorText.length == 4 || colorText.length == 7) {
	 	} else {
	 		alert("请选择正确的颜色代码");
	 		return false;
	 	}
      }
      
       if(seq != null && seq != "") {
       		if(isNaN(seq)){
				alert("请输入数字!!");
				return false;
			}
       }
	   
	   if(pic!=""){
		   if(pic.toLowerCase().indexOf(".jpg")<0&&pic.toLowerCase().indexOf(".gif")<0&&pic.toLowerCase().indexOf(".png")<0){
		     alert("请上传.jpg || .gif || .png类型的图片！");
		   	 return false;
		   }
	   } else {
	   	 alert("请选择模板图片！");
	   	 return false;
	   }
	   
      //提交信息 
       submit();
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#saveTempletForm").submit();   
   }
</script>
</html>