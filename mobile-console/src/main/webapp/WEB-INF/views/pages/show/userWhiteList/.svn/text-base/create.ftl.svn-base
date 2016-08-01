<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">添加用户白名单菜单</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="saveMenuForm" name="saveMenuForm"   method="post" action="${rc.getContextPath()}/userWhiteList/save">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>用户白名单管理</dd>
			<dd><a href="${rc.getContextPath()}/userWhiteList/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">用户白名单管理</a></dd>
			<dd class="last"><h3>添加用户白名单</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">用户白名单</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>用户ID：</th>
				<td class="tdBox">
					<input type="text" name="userId" id="userId" onblur="getUserName()" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">用户名称：</th>
				<td class="tdBox">
				 <font id="userName"></font>
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
<input type="hidden" value="${status!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>
</body>
<script type="text/javascript">


$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("添加成功");
	   location.href="${rc.getContextPath()}/userWhiteList/list";
	 }else if(msg!=""){
	   alert(msg);
	 }
	 });
   //验证信息
    function go(){
      var userId=$("#userId").val();
      
      if(userId==""){
        alert("用户ID不能为空！");
        return false;
      }
	   
	  var isSuccess= getUserName();
	   if(!isSuccess){
	     return ;
	   }
      //提交信息 
       submit();
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#saveMenuForm").submit();   
   }
   
   	function getUserName(){
	   	var isSuccess=false;
	   	var userId=$("#userId").val();
		  $(".userName").html("");
		       $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/userWhiteList/getUserName?userId=" + userId +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				        data=data.replace(/"/gm,'');
				       if(data!=""){
			            	$("#userName").html(data);
			            	isSuccess=true;
				       }else{
				            $("#userName").html("该用户不存在");
				       }
				},
				error : function(data) {
				}
			}); 
		return isSuccess;
   }
   
</script>
</html>