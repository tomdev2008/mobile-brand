<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
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
			<h3 class="topTitle fb f14">编辑菜单</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="savePageForm" name="savePageForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/appStartManager/save">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>配置管理</dd>
			<dd><a href="${rc.getContextPath()}/appStartManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">App启动页管理</a></dd>
			<dd class="last"><h3>添加启动页</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加启动页</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>App类型：</th>
				<td class="tdBox">
				<input type="radio" name="appType" id="type_01" value="1" checked="true" onclick="changeView(1);"/><label>Android</label>
                <input type="radio" name="appType" id="type_02" value="2" onclick="changeView(2);"/><label>IPhone</label>
                <input type="radio" name="appType" id="type_03" value="3" onclick="changeView(3);"/><label>IPad</label>
				</td>
			</tr>
			<tr id="channel_tr">
				<th class="thList" scope="row"><span class="red">*</span>发行渠道：</th>
				<td class="tdBox">
					<input type="radio" name="channel" id="type_0" value="" checked="true"/><label>全部</label>
					<#if (appChannelList?? && appChannelList?size > 0)>
					   <#list appChannelList as appChannel>
					   		<input type="radio" name="channel" id="type_${appChannel.id}" value="${appChannel.code}" />
					   		<label>${appChannel.name}</label>		 		
					   </#list>
				    </#if>
				</td>
			</tr>		
			<tr>
				<th class="thList" scope="row"><span class="red">*</span><span id="pageName">启动页</span>：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td><input name="start_page_f" id ="start_page_f" type="file" class="inp24 w400" /> </td>
							<td style="padding-left:25px;">
								<span id="descSpan" style="color:gray">注：android图片尺寸：720x1280px</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr id="page_tr" style="display:none;">
				<th class="thList" scope="row"><span class="red">*</span>4英寸屏启动页：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td><input name="start_page_f_a" id ="start_page_f_a" type="file" class="inp24 w400" /> </td>
							<td style="padding-left:25px;">
								<span style="color:gray">注：iphone4英寸图片尺寸：640x1136px</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>生效时间：</th>
				<td class="tdBox">
					<input name="startTimeC" id="startTimeC" type="text" style="width:180px;" readonly onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
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
	function changeView(type) {
		if(type == 1) {
			$("#pageName").text("启动页");
			$("#page_tr").hide();
			$("#start_page_f_a").val("");
			$("#channel_tr").show();
			$("#descSpan").text("注：android图片尺寸：720x1280px");
		} else if (type == 2) {
			$("#page_tr").show();
			$("#pageName").text("3.5英寸屏启动页");
			$("#channel_tr").hide();
			$("#descSpan").text("注：iphone3.5英寸图片尺寸：640x960px");
		} else if (type == 3) {
			$("#pageName").text("启动页");
			$("#page_tr").hide();
			$("#start_page_f_a").val("");
			$("#channel_tr").hide();
			$("#descSpan").text("注：ipad图片尺寸：2048x1536px");
		}
	}	
   //验证信息
    function go(){
      var startPage = $("#start_page_f").val();
      var startTimeC = $("#startTimeC").val();
      
      if(startPage=="") {
        if($("#type_02").attr("checked")) {
        	alert("请选择3.5英寸启动页！");
        } else {
	      	alert("请选择启动页！");
        }
      	return;
      }
      if($("#type_02").attr("checked")) {
      	var startPageA = $("#start_page_f_a").val();
      	if(startPageA == "") {
      		alert("请选择4英寸启动页！");
      		return;
      	}
      }
      
      if(startTimeC == "") {
      	alert("请选择生效时间！");
      	return;
      }
      
      var appType = $("input:radio[name=appType]:checked").val();
      var channel = $("input:radio[name=channel]:checked").val();
      
      if(channel == "") {
      	channel = "all";
      }
      
      $.ajax({
		type : "GET",
		url : "${rc.getContextPath()}/appStartManager/getStartPageCount?format=json",
		data : {"appType":appType, "channel":channel, "startTimeC":startTimeC},
        dataType: "text",
		success : function(data, textStatus) {
		   if (isNaN(data)) {
				var objs =  $.parseJSON(data);
				if (objs != null) {
					if(objs.scode == "0")
					{
					   var count = objs.data;
					   if(count > 0) {
					   		alert("该时间已有启动页，请勿重复添加。");
					   		return;
					   } else {
						   	//提交信息 
	       					submit();
					   }
					   
					} else if(objs.scode == "-1") {
						alert(objs.data);
					}
				}
			}
		},
		error : function(data) {
			showErrorMsg(true,data);
		}
	});
	
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#savePageForm").submit();   
   }
</script>
</html>