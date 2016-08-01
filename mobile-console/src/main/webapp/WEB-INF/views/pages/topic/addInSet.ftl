<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript">
	
	//查询
    function query(){
      var set_id = $("#set_id").val();
      if(set_id == null || set_id == "") {
      	alert("请输入卖场ID！");
      	return ;
      }
      $("#mainForm").submit();
    }
	   
	function add(activity_id,displayStyle){
		var display_style = $("#display_style").val();
		if(display_style != displayStyle) {
			alert("卖场形式必须一致！");
			return;
		}
		if(window.confirm('确定移入卖场集吗?')){
			var act_id = $("#act_id").val();
			var set_id = activity_id;
			if(act_id.indexOf(",") > -1) {
				//批量移入卖场集
				$.ajax({
					type : "GET",
					url : "${rc.getContextPath()}/topic/addActivitySet?format=json",
					data : {"set_id":set_id, "act_id":act_id},
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   alert("移入卖场集成功！");
								}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
				});
			} else {
				$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/topic/hasExistsActivitySet?format=json",
				data : {"set_id":set_id, "act_id":act_id},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "1")
							{
								//卖场集中已存在二级卖场
							   alert(objs.data);
							} else if(objs.scode == "0") {
								//不存在则添加
								$.ajax({
									type : "GET",
									url : "${rc.getContextPath()}/topic/addActivitySet?format=json",
									data : {"set_id":set_id, "act_id":act_id},
					                dataType: "text",
									success : function(data, textStatus) {
									   if (isNaN(data)) {
											var objs =  $.parseJSON(data);
											if (objs != null) {
												if(objs.scode == "0")
												{
												   alert("移入卖场集成功！");
												}
											}
										}
									},
									error : function(data) {
										showErrorMsg(true,data);
									}
								});
							}
						}
					}
				},
				error : function(data) {
					showErrorMsg(true,data);
				}
			});
			}
			
		}
	}
   
</script>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">移入卖场集</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/topic/queryActivitySet" method="POST">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>活动平台</dd>
			<dd><a href="${rc.getContextPath()}/topic/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">卖场管理</a></dd>
			<dd class="last"><h3>移入卖场集</h3>
			</dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
			     <label>卖场ID：
			         <input type="hidden" id="act_id" name="act_id" value="${(act_id!'')}" />
			         <input type="hidden" id="display_style" name="display_style" value="${(display_style!'')}" />
			         <input type="hidden" id="contentType" name="contentType" value="4" />
			         <input type="text" id="set_id" name="set_id" size="20" value="${(set_id!'')}" />
			         &nbsp;&nbsp;&nbsp;
			     </label>
				 <label>
				    <a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
				 </label>
				 <label>
				 	<span id="remark" style="color:gray;">注：只能查询卖场集。</span>
				 	<#if display_style ??>
				 	<span style="color:gray;">只能移入<#if display_style == 1>
									横向
									<#elseif display_style == 2>
									竖向
									</#if>卖场集。</span>
					</#if>
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
						<th width="10%">卖场ID</th>
						<th width="25%">卖场名称</th>
						<th width="10%">卖场内容</th>
						<th width="25%">卖场集下二级卖场形式</th>
						<th width="10%">开始时间</th>
						<th width="10%">结束时间</th>
						<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
			     <#if (topic ?? )>
			      <tr>
					<td>${topic.activity_id!''}</td>
					<td>${topic.name!''}</td>
					<td>
					<#if topic.contentType==1>
					促销
					<#elseif topic.contentType==2>
					HTML5卖场
					<#elseif topic.contentType==3>
					特卖
					<#elseif topic.contentType==4>
					卖场集
					</#if>
					<td>
					<#if topic.displayStyle==1>
					横向
					<#elseif topic.displayStyle==2>
					竖向
					</#if>
					<td>${topic.start_time?string('yyyy-MM-dd HH:mm:ss')}</td>
					<td>${topic.end_time?string('yyyy-MM-dd HH:mm:ss')}</td>
					<td><a href="javascript:void(0);" title="确认移入卖场集" onclick="return add(${topic.activity_id},${topic.displayStyle})">确认移入</a></td>    
				 </tr>	
				   <#else>
				     <tr><td colspan="7"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
				<tr>
				<td class="tdBox" colspan="7" style="text-align:center;">
					<a href="javascript:void(0)" title="关闭" onclick="window.close()" class="ml10"> <img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
			</table>
	</div>	
</body>
</html>