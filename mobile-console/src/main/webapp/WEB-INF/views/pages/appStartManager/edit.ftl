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

<form id="saveMenuForm" name="saveMenuForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/appStartManager/update">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>配置管理</dd>
			<dd><a href="${rc.getContextPath()}/appStartManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">App启动页管理</a></dd>
			<dd class="last"><h3>编辑启动页</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑启动页</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>启动页ID：</th>
				<td class="tdBox">
					<input type="text" name="id" id="id" value="${appStartManager.id!''}" readonly="true" style="background-color:#f5f5f5;" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>App类型：</th>
				<td class="tdBox">
				<input type="hidden" id="appType" name="appType" value = "${(appStartManager.appType)!''}" />
				<input type="radio" id="type_01" value="1" disabled="disabled" <#if appStartManager.appType?? && appStartManager.appType ==1> checked="true" </#if>/><label>Android</label>
                <input type="radio" id="type_02" value="2" disabled="disabled" <#if appStartManager.appType?? && appStartManager.appType ==2> checked="true" </#if>/><label>IPhone</label>
                <input type="radio" id="type_03" value="3" disabled="disabled" <#if appStartManager.appType?? && appStartManager.appType ==3> checked="true" </#if>/><label>IPad</label>
				</td>
			</tr>
			<tr id="channel_tr" <#if appStartManager.appType?? && appStartManager.appType == 1><#else>style="display:none"</#if>>
				<th class="thList" scope="row"><span class="red">*</span>发行渠道：</th>
				<td class="tdBox">
			         <input type="radio" name="channel" id="type_0" value="" checked="true" /><label>全部</label>
			         <#if (appChannelList?? && appChannelList?size > 0)>
					   <#list appChannelList as appChannel>
					   		<input type="radio" name="channel" id="type_${appChannel.id}" value="${appChannel.code}" <#if appStartManager.channel?? && appStartManager.channel=="${appChannel.code}"> checked="true" </#if> /><label>${appChannel.name}</label>	 		
					   </#list>
				    </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span><span id="pageName"><#if appStartManager.appType?? && appStartManager.appType == 2>已设3.5英寸屏启动页地址<#else>已设启动页地址</#if></span>：</th>
				<td class="tdBox">
					<label style="width:300px;"> ${(appStartManager.pageUrl)!''} </label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">启动页：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td><input name="start_page_f" id ="start_page_f" type="file" class="inp24 w400" /> </td>
							<td style="padding-left:25px;">
								<span id="descSpan" style="color:gray">
									<#if appStartManager.appType?? && appStartManager.appType == 1>
										注：android图片尺寸：720x1280px
									<#elseif appStartManager.appType?? && appStartManager.appType == 2>
										注：iphone3.5英寸图片尺寸：640x960px
									<#elseif appStartManager.appType?? && appStartManager.appType == 3>
										注：ipad图片尺寸：2048x1536px
									</#if>
								</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr <#if appStartManager.appType?? && appStartManager.appType == 2><#else>style="display:none"</#if>>
				<th class="thList" scope="row"><span class="red">*</span>已设4英寸屏启动页地址：</th>
				<td class="tdBox">
					<label style="width:300px;"> ${(appStartManager.pageUrlA)!''} </label>
				</td>
			</tr>
			<tr <#if appStartManager.appType?? && appStartManager.appType == 2><#else>style="display:none"</#if>>
				<th class="thList" scope="row">4英寸屏启动页：</th>
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
					<input name="startTimeC" id="startTimeC" type="text" <#if appStartManager.startTime??> value="${appStartManager.startTime?string('yyyy-MM-dd')}" </#if> style="width:180px;" readonly onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
					<input type="radio" name="status" id="statusType_01" value="1" <#if appStartManager.status?? && appStartManager.status ==1> checked="true" </#if> /><label>启用</label>
					<input type="radio" name="status" id="statusType_02" value="0" <#if appStartManager.status?? && appStartManager.status ==0> checked="true" </#if> /><label>停用</label>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return go();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="window.close();" class="ml10"> <img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
      var startTimeC = $("#startTimeC").val();
      
      if(startTimeC == "") {
      	alert("请选择开始时间！");
      	return;
      }
      
      var appType = $("#appType").val();
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
					   		alert("该时间已有启动页，请重新选择生效时间。");
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
	 $("#saveMenuForm").submit();   
   }
</script>
</html>