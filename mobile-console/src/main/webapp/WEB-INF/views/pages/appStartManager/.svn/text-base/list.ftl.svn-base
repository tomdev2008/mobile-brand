<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">App启动页管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/appStartManager/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>配置管理</dd>
				<dd class="last"><h3>App启动页管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
				      <label>App类型：
				          <select name="appType" id="appType">
				           <option value=""   <#if !appType?? > selected="selected" </#if>  >请选择</option>
				           <option value="1"  <#if appType?? && appType=="1"> selected="selected" </#if>  >Android</option>
						   <option value="2"  <#if appType?? && appType=="2"> selected="selected" </#if>  >IPhone</option>
						   <option value="3"  <#if appType?? && appType=="3"> selected="selected" </#if>  >IPad</option>
				          </select> 
			         	 &nbsp;
				     </label>
				     <label>渠道：
				          <select name="channel" id="channel">
				          <option value=""  <#if channel?? && channel==""> selected="selected" </#if>  >请选择</option>
				           <option value="all"  <#if channel?? && channel=="all"> selected="selected" </#if>  >全部</option>
				           <#if (appChannelList?? && appChannelList?size > 0)>
							   <#list appChannelList as appChannel>
							   		<option value="${appChannel.code}" <#if channel?? && channel=="${appChannel.code}"> selected="selected" </#if> >${appChannel.name}</option>	
							   </#list>
						   </#if>
				         </select> 
			         	 &nbsp;
				     </label>
				     <label>生效状态：
				          <select name="useStatus" id="useStatus">
				           <option value=""   <#if !useStatus?? > selected="selected" </#if>  >请选择</option>
				           <option value="2"  <#if useStatus?? && useStatus=="2"> selected="selected" </#if>  >未生效</option>
						   <option value="3"  <#if useStatus?? && useStatus=="3"> selected="selected" </#if>  >生效中</option>
						   <option value="1"  <#if useStatus?? && useStatus=="1"> selected="selected" </#if>  >已失效</option>
				          </select> 
			         	 &nbsp;
				     </label>
				     
				     <label>启用状态：
				          <select name="status" id="status">
				           <option value="" >请选择</option>					           
				           <option value="1" <#if status?? && status == 1>selected="selected"</#if>>启用</option>
				           <option value="0" <#if status?? && status == 0>selected="selected"</#if>>停用</option>
				          </select> 
				          &nbsp;
				     </label> 
				     <label>
					    <a title="查询" class="btn" onclick="query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
					 </label>
					 <label>
					    <a title="添加启动页" class="btn" onclick="createPage()" style="width:60px;text-align:center;height:22px;">添加启动页</a>
					 </label>
					 <label>
					    <a title="渠道管理" class="btn" onclick="enterChannel()" style="width:50px;text-align:center;height:22px;">渠道管理</a>
					 </label>
				</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="10%">启动页ID</th>
						<th width="10%">App类型</th>
						<th width="10%">发行渠道</th>
						<th width="16%">启动页缩略图</th>
						<th width="8%">生效状态</th>
						<th width="8%">生效时间</th>
						<th width="8%">启用状态</th>
						<th width="12%">添加时间</th>
						<th width="18%">操作</th>
					</tr>
				</thead>
				<tbody>
				<#if (appStartManagerList?? && appStartManagerList?size > 0)>
				<#list appStartManagerList as appStartManager>
				<tr>
					<td>${(appStartManager.id)!''}</td>
				    <#if appStartManager.appType?? && appStartManager.appType == 1>
						<td>Android</td>
					<#elseif appStartManager.appType==2>
						<td>IPhone</td>
					<#elseif appStartManager.appType==3>
						<td>IPad</td>
					</#if>
					<#if appStartManager.channel?? && appStartManager.channel == "appstore">
						<td>AppStore</td>
					<#elseif appStartManager.channel?? && appStartManager.channel != "">
						<td>
							<#if (appChannelList?? && appChannelList?size > 0)>
							   <#list appChannelList as appChannel>
							   		<#if appStartManager.channel?? && appStartManager.channel=="${appChannel.code}">${appChannel.name}</#if>
							   </#list>
							</#if>
						</td>
					<#else>
						<td>全部</td>
					</#if>
					<td>
						<div style="position: relative;">
						  <#if appStartManager.pageUrl?? && appStartManager.pageUrl != ''>
							  <img class="smallImg" style="width:40px;height:40px;" src="http://6.xiustatic.com${appStartManager.pageUrl!''}"  /> 
							  <div class="showBigImg" style="position: absolute;left: 170px;top: -100px; display:none;"><img style="width:300px;height:300px;" src="http://6.xiustatic.com${appStartManager.pageUrl!''}" /></div>
						  </#if>
						  <#if appStartManager.pageUrlA?? && appStartManager.pageUrlA != ''>
							  <img class="smallImgA" style="width:40px;height:40px;" src="http://6.xiustatic.com${appStartManager.pageUrlA!''}"  /> 
							  <div class="showBigImgA" style="position: absolute;left: 220px;top: -100px; display:none;"><img style="width:300px;height:300px;" src="http://6.xiustatic.com${appStartManager.pageUrlA!''}" /></div>
						  </#if>
					   </div>
					</td>
					<#if appStartManager.useStatus?? && appStartManager.useStatus == "2">
						<td>未生效</td>
					<#elseif appStartManager.useStatus=="3">
						<td><span style="color:red;">生效中</span></td>
					<#elseif appStartManager.useStatus=="1">
						<td>已失效</td>
					</#if>
					<td><#if appStartManager.startTime??>${appStartManager.startTime?string('yyyy-MM-dd')}</#if></td>
					<#if appStartManager.status?? && appStartManager.status == 1>
						<td>启用</td>
					<#else>
						<td>停用</td>
					</#if>
					<td><#if appStartManager.createDate??>${appStartManager.createDate?string('yyyy-MM-dd hh:mm:ss')}</#if></td>
					<td>
					   <#if appStartManager.status?? && appStartManager.status == 1 >
						    <a href="javascript:void(0);" onclick="changStatus(${appStartManager.id},0,${appStartManager.appType},'${appStartManager.channel!''}','${appStartManager.startTime?string('yyyy-MM-dd')}')" title="停用">停用</a>
					   <#else>
						    <a href="javascript:void(0);" onclick="changStatus(${appStartManager.id},1,${appStartManager.appType},'${appStartManager.channel!''}','${appStartManager.startTime?string('yyyy-MM-dd')}')" title="启用">启用</a> 
					   </#if>
                       <a href="javascript:void(0);" onclick="editPage(${appStartManager.id})" > | 编辑</a>
   					</td>    
			    </#list>
			    <#else>
			    <tr><td colspan="9"><font color="red">暂时没有查询到记录</font></td></tr>
			    </#if>
			   </tbody>
			</table>
	</div>	
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">
// 显示大图
$('.smallImg').hover(function(){
	$(this).siblings('.showBigImg').show();
},function(){
	$(this).siblings('.showBigImg').hide();
})

$('.smallImgA').hover(function(){
	$(this).siblings('.showBigImgA').show();
},function(){
	$(this).siblings('.showBigImgA').hide();
})

 //查询
function query(){
	$('#pageNo').val(1);
    $("#mainForm").submit();
}

function createPage(){
	location.href="${rc.getContextPath()}/appStartManager/create";
}

function enterChannel() {
	location.href="${rc.getContextPath()}/appStartManager/appChannelList";
}

function editPage(id) {
	var pageUrl = "${rc.getContextPath()}/appStartManager/edit?id="+id;
	var title = "编辑卖场启动页";
	var parameters = "scrollbars=yes";
	var winOption = "width=880px,height=600px,left=350px,top=150px;";
	if(navigator.userAgent.indexOf("Chrome") > 0) {
		//如果是chrome浏览器
		window.open(pageUrl, title, winOption);
	} else {
		window.open(pageUrl, title, parameters);
	}
}

function deletePage(id,status){
	if(status != null && status == 1) {
		alert("启用中的启动页不可删除！");
		return false;
	}
	if(confirm("确实要删除该启动页吗？")== true){
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/appStartManager/delete?id=" + id + "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0") {
							window.location.reload();
						}else{
						    alert(objs.data);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
}

//启用
function changStatus(id,status,appType,channel,startTimeC){
	var msg = "";
	if(status == 0){
		msg = "确认要停用该启动页吗？";
	} else if(status == 1) {
		msg = "确认要启用该启动页吗？";
	}
	$.ajax({
		type : "GET",
		url : "${rc.getContextPath()}/appStartManager/getStartPageCount?format=json",
		data : {"appType":appType},
        dataType: "text",
		success : function(data, textStatus) {
		   if (isNaN(data)) {
				var objs =  $.parseJSON(data);
				if (objs != null) {
					if(objs.scode == "0")
					{
					   var count = objs.data;
					   if(status == 0 && count <= 1) {
					   		msg = msg+"\n停用后该APP类型将没有启用中的启动页";
					   }
					   var channelPara = channel;
					   if(channel == '') {
					   	 channelPara = "all";
					   }
					   if(window.confirm(msg)){
					   		$.ajax({
								type : "GET",
								url : "${rc.getContextPath()}/appStartManager/getStartPageCount?format=json",
								data : {"appType":appType, "channel":channelPara, "startTimeC":startTimeC},
						        dataType: "text",
								success : function(data, textStatus) {
								   if (isNaN(data)) {
										var objs =  $.parseJSON(data);
										if (objs != null) {
											if(objs.scode == "0")
											{
											   var count = objs.data;
											   if(status ==1 && count > 0) {
											   		alert("该生效时间("+startTimeC+")已有启动页，请修改生效时间后再修改启用状态。");
											   		return;
											   } else {
												   	//更新状态 
							       					$.ajax({
														type : "GET",
														url : "${rc.getContextPath()}/appStartManager/updateStatus?format=json",
														data : {"id":id, "status":status},
										                dataType: "text",
														success : function(data, textStatus) {
														   if (isNaN(data)) {
																var objs =  $.parseJSON(data);
																if (objs != null) {
																	if(objs.scode == "0")
																	{
																	   //修改成后,刷新列表页
																	   var appType = $("#appType").val();
																	   var channel = $("#channel").val();
																	   var useStatus = $("#useStatus").val();
																	   var status = $("#status").val();
																	   location.href = "${rc.getContextPath()}/appStartManager/list?appType="+appType+"&channel="+channel+"&useStatus="+useStatus+"&status="+status;
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
</script>
</body>
</html>