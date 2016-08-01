<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css" />
<script type="text/javascript">
	//查询
   function query(){
   	  var act_id = $("#act_id").val();
	 
      if(act_id == null || act_id == "") {
      	alert("请输入卖场ID！");
      	return ;
      }
      
    //批量查询卖场时，验证卖场ID格式：只能是数字和逗号
	 var str = "";
	 if(act_id != null && act_id != "") {
	 	str = act_id.replace(/\s/g, ""); //去掉所有空格
	 	str = str.replace(/\r/g,"");	//去掉回车换行
	 	if(str.indexOf(",,") > -1) {
 			alert("不能有连续的逗号！");
 			return ;
 		}
 		var temp = str.substr(0,1);
 		if(temp == ',') {
 			alert("第一个字符不能是逗号！");
 			return;
 		}
	 	var reg = /^(\d+,?)+$/;	//验证规则
	 	var flag = reg.test(str);
	 	if(!flag) {
	 		alert("卖场ID只能输入数字和英文逗号！");
	 		return false;
	 	}
	 	$("#act_id").val(str);
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
			var act_id = activity_id;
			var set_id = $("#set_id").val();
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
												   var display_style = $("#display_style").val();
							   					   location.href = "${rc.getContextPath()}/topic/enterIntoSet?set_id="+set_id+"&display_style="+display_style;
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
   
   //移出卖场集
   function deleteActivitySet(act_id) {
   	 var set_id = $("#set_id").val();
   	 if(window.confirm('确定移出本卖场集吗?')){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/topic/deleteActivitySet?format=json",
				data : {"set_id":set_id, "act_id":act_id},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("移出卖场集成功！");
							   //修改成后,刷新列表页
							   var display_style = $("#display_style").val();
							   location.href = "${rc.getContextPath()}/topic/enterIntoSet?set_id="+set_id+"&display_style="+display_style;
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
   
   //清空卖场集
   function empty() {
   	var set_id = $("#set_id").val();
   	if(window.confirm('确认清空本卖场集吗?')) {
   		$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/topic/emptyActivitySet?format=json",
				data : {"set_id":set_id},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("清空卖场集成功！");
							   //修改成后,刷新列表页
							   var display_style = $("#display_style").val();
							   location.href = "${rc.getContextPath()}/topic/enterIntoSet?set_id="+set_id+"&display_style="+display_style;
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
   
   function selectAll(){
		if($("#SelectAll").attr("checked")){
	        $(":checkbox").attr("checked",true);
	    }else {
	        $(":checkbox").attr("checked",false);
	    }
	}
	
	function addBatch() {
		var count=0;
		var ids="";
		var displayStyle = '0';
		if(document.getElementsByName("checkboxId")){
			var checkboxs = document.getElementsByName("checkboxId");
			var check = true;
			
			if(typeof(checkboxs.length) == "undefined"){
				if(checkboxs.checked == true) count = 1;
			}else{
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked == true){
					 	count = 1;
					 	var arr = checkboxs[i].value.split(":");
					 	//判断卖场形式是否一致
					 	if(displayStyle == '0') {
					 		displayStyle = arr[1];
					 	} else {
					 		if(displayStyle != arr[1]) {
					 			alert("批量移入卖场集的卖场形式必须一致！");
					 			return ;
					 		}
					 	}
					 	ids+=arr[0]+"," ;
					}		
				}
			}
		}
		if(count==0){
			alert('请选取要移入卖场集的卖场!');
			return false;
		}
		
		var act_id = ids.substring(0,ids.length - 1);
		var set_id = $("#set_id").val();
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
						   alert("批量移入卖场集成功！");
						   var display_style = $("#display_style").val();
	   					   location.href = "${rc.getContextPath()}/topic/enterIntoSet?set_id="+set_id+"&display_style="+display_style;
						}
					}
				}
			},
			error : function(data) {
				showErrorMsg(true,data);
			}
		});
	}
	
	function edit(activity_id) {
		var pageUrl = "${rc.getContextPath()}/topic/edit?activity_id="+activity_id;
		var title = "编辑卖场";
		var parameters = "scrollbars=yes";
		var winOption = "width=800px,height=700px,left=300px,top=90px;";
		if(navigator.userAgent.indexOf("Chrome") > 0) {
			//如果是chrome浏览器
			window.open(pageUrl, title, winOption);
		} else {
			window.open(pageUrl, title, parameters);
		}
	}
	
	//清除卖场ID提示信息
	function clearText() {
		$("#actIdTip").text("");
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
			<h3 class="topTitle fb f14">卖场集管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/topic/enterIntoSet" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>活动平台</dd>
			<dd><a href="${rc.getContextPath()}/topic/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">卖场管理</a></dd>
			<dd class="last"><h3>卖场集：${(topicSet.name!'')?html}</h3>
			</dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 卖场信息显示框 -->
		<div style="font-weight:bold;">
		已包含<span style="color:red;">${activityCounts}个</span>二级卖场（<#if topicSet.displayStyle==1>
									横向
									<#elseif topicSet.displayStyle==2>
									竖向
									</#if>）
		</div>								
		<!-- 卖场信息显示框 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="4%">卖场ID</th>
						<th width="12%">卖场名称</th>
						<th width="4%">卖场类型</th>
						<th width="10%">卖场分类</th>
						<th width="4%">是否显示</th>
						<th width="5%">排序(降序)</th>
						<th width="5%">开始时间</th>
						<th width="5%">结束时间</th>
						<th width="5%">卖场状态</th>
						<th width="5%">卖场形式</th>
						<th width="5%">商品列数</th>
						<th width="3%">卖场图</th>
						<th width="4%">商品个数</th>
				
						<th width="12%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (topicList?size > 0)>
				   <#list topicList as topic>
				      <tr>
						<td>${topic.activity_id!''}</td>
						<td>${topic.name!''}</td>
						<td>
							<#if topic.contentType==1>
							促销
							<#elseif topic.contentType==2>
							HTML5
							<#elseif topic.contentType==3>
							特卖
							<#elseif topic.contentType==4>
							卖场集
							</#if>
						</td>
						<td>${topic.topicType!''}</td>
						<td>${topic.display!''}</td>
						<td>${topic.order_seq!''}</td>
						<td>${topic.start_time?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${topic.end_time?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td><font color="red"> ${topic.status_s!''}</font></td>
						<td>
							<#if topic.displayStyle==1>
							横向
							<#elseif topic.displayStyle==2>
							竖向
							</#if>
						</td>
						<td><#if topic.contentType==4>
								——
							<#else>
								${topic.display_col_num!''}列
							</#if></td>
						<td>
						<div style="position: relative;">
						 <#if topic.mobile_pic??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${topic.mobile_pic!''}"  /> 
						  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${topic.mobile_pic!''}" /></div>
						   </#if>
						   </div>
						</td>
						<td><#if topic.contentType==4>
								——
							<#else>
								${topic.goods_count!''}
							</#if></td>
						<td>
                          <a href="javascript:void(0);" onclick="edit(${topic.activity_id})">编辑</a> |
						  <a href="javascript:void(0);" title="移出卖场集" onclick="deleteActivitySet(${topic.activity_id})">移出卖场集</a> |
						  <#if topic.contentType == 2>
						  	<a href="javascript:void(0);" onclick="window.open ('${topic.URL}', '预览','center:Y');" >预览</a>
						  <#else>
						  	<a href="javascript:void(0);" onclick="window.open ('http://m.xiu.com/cx/${topic.activity_id}.html', '预览','center:Y');" >预览</a>
						  </#if>
   					</td>    
			 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="14"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
		<div class="w mt20 clearfix">
			<p class="fl">
				  <a id="link" class="btn" onclick="empty()">清空卖场集</a>
			</p>
		</div>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
   <br />
	<!-- 添加二级卖场 -->
	<div class="adminUp_wrap">
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
        	<table>
        		<tr>
        			<td style="vertical-align:middle;">
        				<label>卖场ID：
					         <input type="hidden" id="set_id" name="set_id" value="${(set_id!'')}" />
					         <input type="hidden" id="display_style" name="display_style" value="${(display_style!'')}" />
					     </label>
        			</td>
        			<td>
        				<textarea rows="3" cols="25" name="act_id" id="act_id" style="position:relative;" onkeydown="clearText()">${(act_id!'')?html}</textarea>
        				<label for="act_id" id="actIdTip" style="position:absolute; left:85px; color:gray;"><#if act_id?? && act_id!=''><#else>多个卖场ID之间用英文","逗号隔开</#if></label>&nbsp;
        			</td>
        			<td>
        				<label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						 <label>
						 	<span style="font-weight:bold;">增加二级卖场：</span>
							<span>只能添加<#if topicSet.displayStyle==1>
											横向
											<#elseif topicSet.displayStyle==2>
											竖向
											</#if>卖场</span>
						 </label>
        			</td>
        		</tr>
        	</table>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
					<th width="4%">卖场ID</th>
					<th width="12%">卖场名称</th>
					<th width="4%">卖场类型</th>
					<th width="10%">卖场分类</th>
					<th width="4%">是否显示</th>
					<th width="5%">排序(降序)</th>
					<th width="5%">开始时间</th>
					<th width="5%">结束时间</th>
					<th width="5%">卖场状态</th>
					<th width="5%">卖场形式</th>
					<th width="5%">商品列数</th>
					<th width="3%">卖场图</th>
					<th width="4%">商品个数</th>
			
					<th width="12%">操作</th>
				</tr>
			</thead>
			<tbody>
			    <#if (queryTopicList?? && queryTopicList?size > 0)>
				<#list queryTopicList as topic>
			      <tr>
					<td><input type="checkbox" name="checkboxId" value="${topic.activity_id!''}:${topic.displayStyle!''}" /></td>
					<td>${topic.activity_id!''}</td>
					<td>${topic.name!''}</td>
					<td>
						<#if topic.contentType==1>
						促销
						<#elseif topic.contentType==2>
						HTML5
						<#elseif topic.contentType==3>
						特卖
						<#elseif topic.contentType==4>
						卖场集
						</#if>
					</td>
					<td>${topic.topicType!''}</td>
					<td>${topic.display!''}</td>
					<td>${topic.order_seq!''}</td>
					<td>${topic.start_time?string('yyyy-MM-dd HH:mm:ss')}</td>
					<td>${topic.end_time?string('yyyy-MM-dd HH:mm:ss')}</td>
					<td><font color="red"> ${topic.status_s!''}</font></td>
					<td>
						<#if topic.displayStyle==1>
						横向
						<#elseif topic.displayStyle==2>
						竖向
						</#if>
					</td>
					<td><#if topic.contentType==4>
							——
						<#else>
							${topic.display_col_num!''}列
						</#if></td>
					<td>
					<div style="position: relative;">
					 <#if topic.mobile_pic??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${topic.mobile_pic!''}"  /> 
					  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${topic.mobile_pic!''}" /></div>
					   </#if>
					   </div>
					</td>
					<td><#if topic.contentType==4>
							——
						<#else>
							${topic.goods_count!''}
						</#if></td>
					<td>
                      <a href="javascript:void(0);" title="移入本卖场集" onclick="return add(${topic.activity_id},${topic.displayStyle})">移入本卖场集</a>
					  <#if topic.contentType == 2>
					  	<a href="javascript:void(0);" onclick="window.open ('${topic.URL}', '预览','center:Y');" >预览</a>
					  <#else>
					  	<a href="javascript:void(0);" onclick="window.open ('http://m.xiu.com/cx/${topic.activity_id}.html', '预览','center:Y');" >预览</a>
					  </#if>
				</td>    
		 	</tr>	
		 		</#list>
			   <#else>
			     <tr><td colspan="15"><font color="red">暂时没有查询到记录</font></td></tr>
			   </#if>					
			</tbody>
		</table>
		<div class="w mt20 clearfix">
			<p class="fl">
				  <a id="link" class="btn" onclick="addBatch()">批量移入卖场集</a>
			</p>
		</div>
	</div>
</form>
</div>

</body>
<script type="text/javascript">
	// 显示大图
	
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})
</script>
</html>