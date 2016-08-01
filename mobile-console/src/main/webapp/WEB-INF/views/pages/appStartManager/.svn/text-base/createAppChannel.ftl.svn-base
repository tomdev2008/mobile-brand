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
			<h3 class="topTitle fb f14">编辑菜单</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="saveMenuForm" name="saveMenuForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/appStartManager/saveAppChannel">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>配置管理</dd>
			<dd><a href="${rc.getContextPath()}/appStartManager/appChannelList?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">渠道管理</a></dd>
			<dd class="last"><h3>添加发行渠道</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加发行渠道</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>渠道名称：</th>
				<td class="tdBox">
					<input type="text" name="name" id="name" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>渠道编码：</th>
				<td class="tdBox">
					<input type="text" name="code" id="code" maxlength="100"/>
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
					<input type="radio" name="status" id="statusType_01" value="1" checked="true" /><label>启用</label>
					<input type="radio" name="status" id="statusType_02" value="0" /><label>停用</label>
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
	
   //验证信息
    function go(){
      var name=$("#name").val();
      var code=$("#code").val();
      var seq = $("#orderSeq").val();
      
      if(name==""){
        alert("渠道名称不能为空！");
        return false;
      }
      
      if(code==""){
        alert("渠道编码不能为空！");
        return false;
      }
      
      if(seq==""){
        alert("序号不能为空！");
        return false;
      }
	   
      //提交信息 
       submit();
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#saveMenuForm").submit();   
   }
</script>
</html>