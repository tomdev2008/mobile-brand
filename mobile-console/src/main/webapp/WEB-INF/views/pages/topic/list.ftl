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
			<h3 class="topTitle fb f14">卖场管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/topic/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>活动平台</dd>
				<dd class="last"><h3>卖场管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
			<table>
				<tr>
					<td style="vertical-align:middle;">
						卖场ID：
					</td>
					<td>
						<textarea rows="3" cols="25" name="activity_id" id="activity_id" style="position:relative;" onkeydown="clearText()">${(activity_id!'')?html}</textarea>
						<label for="activity_id" id="activityIdTip" style="position:absolute; left:90px; color:gray;"><#if activity_id?? && activity_id!=''><#else>多个卖场ID之间用英文","逗号隔开</#if></label>&nbsp;
					</td>
					<td style="vertical-align:middle;">
						<label>卖场名称：
					          <input name="name" type="text" id="name" value="${(name!'')?html}" size="12" />
					      </label>
					     <label>当日情况:
					      <input name="now_time" id="now_time" type="text"  value="<#if now_time??>${now_time}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					      </label>
					      
					      <label>开始时间:
					      <input name="start_time" id="start_time" type="text"  value="<#if start_time??>${start_time}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					      </label>
					      
					      <label>结束时间:
					      <input name="end_time" id="end_time" type="text"  value="<#if end_time??>${end_time}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					      </label>
					      
					      <label> 是否显示:
					          <select name="display" id="display">
					           <option value=""   <#if !display?? > selected="selected" </#if>  >全部</option>
					           <option value="Y"  <#if display?? && display=='Y'> selected="selected"  </#if>  >显示</option>
							   <option value="N"  <#if display?? && display=='N'> selected="selected" </#if>  >不显示</option>
					          </select> 
					     </label> 
					</td>
				</tr>
				<tr>
					<td>
						卖场类型:
					</td>
					<td>
						<label> 
				          <select name="contentType" id="contentType" style="width:70px;">
				           <option value=""   <#if !contentType?? > selected="selected" </#if>  >全部</option>
				           <option value="1"  <#if contentType?? && contentType=='1'> selected="selected"  </#if>  >促销</option>
						   <option value="2"  <#if contentType?? && contentType=='2'> selected="selected" </#if>  >H5卖场</option>
						   <option value="3"  <#if contentType?? && contentType=='3'> selected="selected" </#if>  >特卖</option>
						   <option value="4"  <#if contentType?? && contentType=='4'> selected="selected" </#if>  >卖场集</option>
				          </select> 
				          &nbsp;
					     </label>  
					     <label> 卖场分类:
					          <select name="topicTypeId" id="topicTypeId" style="width:70px;">
					           <option value="" <#if !topicTypeId?? > selected="selected" </#if>  >全部</option>
					           <#if (topicTypeList?? && topicTypeList?size > 0)>
								   <#list topicTypeList as topicType>
								   		<option value="${topicType.id}" <#if topicTypeId?? && topicTypeId!="" && topicTypeId?number==topicType.id> selected="selected" </#if>>${topicType.name}</option>
								   </#list>
							   </#if>
					          </select> 
					     </label>
					</td>
					<td>
						<label> 卖场形式：
					     	<select name="displayStyle" id="displayStyle" style="width:80px;">
					           <option value=""   <#if !displayStyle?? > selected="selected" </#if>  >全部</option>
					           <option value="1"  <#if displayStyle?? && displayStyle=='1'> selected="selected"  </#if>  >横向</option>
							   <option value="2"  <#if displayStyle?? && displayStyle=='2'> selected="selected" </#if>  >竖向</option>
					          </select> 
					          &nbsp;
					     </label>
					     <label>
					     	查询范围：
					     	<select name="queryType" id="queryType">
					     		<option value="1">顶级卖场</option>
					     		<option value="2" <#if queryType?? && queryType=='2'> selected="selected"  </#if>>全部卖场(含二级卖场)</option>
					     	</select>
					     	&nbsp;
					     </label>
					     <label>
					     	卖场状态：
					     	<select name="status" id="status">
					     		<option value="-1" >-请选择-</option>
					     		<option value="0" <#if status?? && status=='0'> selected="selected"  </#if>>未开始</option>
					     		<option value="1" <#if status?? && status=='1'> selected="selected"  </#if>>进行中</option>
					     		<option value="2" <#if status?? && status=='2'> selected="selected"  </#if>>已过期</option>
					     	</select>
					     	&nbsp;
					     </label>
					</td>
				</tr>
				<tr>
				<td>
			     	标签名称:
			     </td>
			     <td>
				<label>
			     	<input name="labelName" type="text" id="labelName" value="${(labelName!'')?html}" size="12" />
			     	&nbsp;
			     </label>
			    </td>
			     <td>
			      <label>
				     	显示卖场倒计时：
				     		<select name="isShowCountDown" id="isShowCountDown">
					     		<option value="-1" >-请选择-</option>
					     		<option value="0" <#if isShowCountDown?? && isShowCountDown==0> selected="selected"  </#if>>否</option>
					     		<option value="1" <#if isShowCountDown?? && isShowCountDown==1> selected="selected"  </#if>>是</option>
					     	</select>
				     	&nbsp;
				     </label>
					<label>
				     	结束后可被搜索：
				     		<select name="isEndCanBeSearch" id="isEndCanBeSearch">
					     		<option value="-1" >-请选择-</option>
					     		<option value="0" <#if isEndCanBeSearch?? && isEndCanBeSearch==0> selected="selected"  </#if>>否</option>
					     		<option value="1" <#if isEndCanBeSearch?? && isEndCanBeSearch==1> selected="selected"  </#if>>是</option>
					     	</select>
				     	&nbsp;
				     </label>
			    </td>
				</tr>
				<tr>
				  <td colspan="3">
				  						<label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
						 <label>
						    <a href="javascript:void(0);" title="预览" class="btn" onclick="return preview()" style="width:50px;text-align:center;height:22px;" id="query">预&nbsp;&nbsp;览</a>
						 </label>
						      &nbsp;&nbsp;&nbsp;
						 <label>
						    <a href="javascript:void(0);" title="添加H5卖场" class="btn" onclick="return create()" style="width:80px;text-align:center;height:22px;" id="query">添加H5卖场</a>
						 </label>
						 <label>
						    <a href="javascript:void(0);" title="添加卖场集" class="btn" onclick="return createSet()" style="width:80px;text-align:center;height:22px;">添加卖场集</a>
						 </label>
						  <label>
						    <a href="javascript:void(0);" title="批量导入修改" class="btn" onclick="toImport();return false;" style="width:50px;text-align:center;height:22px;" id="query">批量修改</a>
						 </label>
				  </td>
				</tr>
			</table>
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
						<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
						<th width="4%">卖场ID</th>
						<th width="12%">卖场名称</th>
						<th width="4%">卖场类型</th>
						<th width="12%">卖场分类</th>
						<th width="10%">标签</th>
						<th width="4%">是否显示</th>
						<th width="5%">排序(降序)</th>
						<th width="5%">开始时间</th>
						<th width="5%">结束时间</th>
						<th width="4%">卖场状态</th>
						<th width="4%">卖场形式</th>
						<th width="4%">卖场图</th>
						<th width="4%">商品个数</th>
						<th width="4%">是否置顶</th>
						<th width="12%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (topicList?size > 0)>
				   <#list topicList as topic>
				      <tr>
				      	<td><#if topic.contentType==4>
								<input type="checkbox" name="checkboxId" value="${topic.activity_id!''}:${topic.displayStyle!''}" disabled="true"  />
							<#else>
								<input type="checkbox" name="checkboxId" value="${topic.activity_id!''}:${topic.displayStyle!''}" />
							</#if></td>
				      	<#if topic.contentType == 2>
						  	<td> <a href="${topic.URL}" target="_BLANK">${topic.activity_id!''}</a> </td>
						<#else>
						  	<td> <a href="http://www.xiu.com/cx/${topic.activity_id!}.shtml" target="_BLANK">${topic.activity_id!''}</a> </td>
						</#if>
						<td><#if topic.contentType == 4>
								<a href="javascript:void(0);" title="${topic.name!''}" onclick="window.open('${rc.getContextPath()}/topic/enterIntoSet?set_id=${topic.activity_id}&display_style=${topic.displayStyle}')">${topic.name!''}</a>
							<#else>
								${topic.name!''}
							</#if>
						</td>
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
						<td>
						    <#if (topic.labelCentre?? && topic.labelCentre?size > 0)>
				   				<#list topic.labelCentre as label>
				   					${label.title}
				   				</#list>
				   			</#if>
					    </td>
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
						<td>
						<div style="position: relative;">
						 <#if topic.mobile_pic??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${topic.mobile_pic!''}"  /> 
						  <div class="showBigImg" style="position: absolute;right: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${topic.mobile_pic!''}" /></div>
						   </#if>
						   </div>
						</td>
						<td><#if topic.contentType==4>
								——
							<#else>
								${topic.goods_count!''}
							</#if></td>
						<td>
							<#if topic.topFlag==1>
							是
							<#elseif topic.topFlag==0>
							否
							</#if>
						</td>
						<td>
                          <a href="javascript:void(0);" onclick="edit(${topic.activity_id})" >编辑</a> |
						  <#if topic.contentType != 4>
						  	<a href="javascript:void(0);" title="移入卖场集" onclick="window.open('${rc.getContextPath()}/topic/addInSet?act_id=${topic.activity_id}&display_style=${topic.displayStyle}')">移入卖场集</a> |
						  <#else>
						  	<a href="javascript:void(0);" title="进入卖场集" onclick="window.open('${rc.getContextPath()}/topic/enterIntoSet?set_id=${topic.activity_id}&display_style=${topic.displayStyle}')">进入卖场集</a> |
						  </#if>
						  <#if topic.contentType == 2>
						  	<a href="javascript:void(0);" onclick="window.open ('${topic.URL}', '预览','center:Y');" >预览</a>
						  <#else>
						  	<a href="javascript:void(0);" onclick="window.open ('http://m.xiu.com/cx/${topic.activity_id}.html', '预览','center:Y');" >预览</a>
						  </#if>
   					</td>    
			 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="16"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
		<div class="w mt20 clearfix">
			<p class="fl">
				  <a id="link" class="btn" onclick="addBatch()">批量移入卖场集</a>
			</p>
		</div>	
	</div>
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
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
	
	function selectAll(){
		if($("#SelectAll").attr("checked")){
	        $(":checkbox[disabled!='true']").attr("checked",true);
	    }else {
	        $(":checkbox[disabled!='true']").attr("checked",false);
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
		window.open('${rc.getContextPath()}/topic/addInSet?act_id='+act_id+'&display_style='+displayStyle);
	}
	
	//清除卖场ID提示信息
	function clearText() {		 
		$("#activityIdTip").text("");
	}
		
   //查询
   function query(){
     //$("#query").removeClass("btn").addClass("quanbtn");
	 //$("#query").attr("onclick","");
	 //$("#query").text("正在查询");
	
	 var activity_id = $("#activity_id").val();
	 
	 //批量查询卖场时，验证卖场ID格式：只能是数字和逗号
	 var str = "";
	 if(activity_id != null && activity_id != "") {
	 	str = activity_id.replace(/\s/g, ""); //去掉所有空格
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
	 	$("#activity_id").val(str);
	 }
	 
	 $('#pageNo').val(1);
     $("#mainForm").submit();
   }
   
   function create(){
		location.href="${rc.getContextPath()}/topic/create";
	}
	
   //添加卖场集
   function createSet() {
   		location.href="${rc.getContextPath()}/topic/createSet";
   }
   
   //预览
   function preview() {
   		var now_time = $("#now_time").val();
   		var topicTypeId = $("#topicTypeId").val();
   		if(now_time == "") {
   			alert("请选择当日情况所在日期！");
   			return;
   		}
   		if(topicTypeId == "") {
   			topicTypeId = "1";
   		}
   		var pageUrl = "${rc.getContextPath()}/topic/previewTopicList?topicTypeId="+topicTypeId+"&now_time="+now_time;
		window.open(pageUrl);
   }
   
   function toImport(){
	location.href="${rc.getContextPath()}/topic/toimport";
   }
   
</script>
</html>