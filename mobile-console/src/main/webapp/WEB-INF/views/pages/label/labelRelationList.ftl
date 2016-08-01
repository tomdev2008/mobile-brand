<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">标签管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/label/getRelationLabelList" method="GET">
<input type="hidden"  id="type"  name="type" value="${type!''}" />
<input type="hidden" id="labelId"  name="labelId" value="${labelId!''}" />
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>专题管理</dd>
				<dd class="last"><h3>标签管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		       
		        <label>标签名称：
						${labelName!''}
		        </label><br/>
		        <label>标签名称：
						<input id="raletionLabelName" name="raletionLabelName" value="${raletionLabelName!''}"/>
		        </label>
		        <label>业务类型：
		                <select id="type" name="type">
		                    <option value="6"  <#if type==6>selected=selected </#if>>秀客</option>
		                </select>
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			 	</label>
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!--查询条件框 end-->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="20%">标签名称</th>
					<th width="5%">排序值</th>
					<th width="25%">共同关注秀数量</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (labelRelationList?? && labelRelationList?size > 0)>
			<#list labelRelationList as label>
			<tr>
			    <td>${label.relationLabelName!''}</td>
			    <td> ${label.orderSeq!''}</td>
			    <td> ${label.relationObjectNum!''}</td>
				<td>
					<a href="javascript:void(0);" onclick="updateLabel('${label.relationLabelId!''}','${label.orderSeq!''}')" title="编辑">编辑排序</a>|
					<a  target="_blank" href="http://m.xiu.com/show/tagCollection.html?labelId=${label.mainLabelId!''}" >预览</a>
					
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<div id="deleteSubjectDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此标签？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteLabelAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		
		</div>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>


<div id="updateLabelOrderDiv" class="showbox">
 <input id="updateRelationLabelId" type="hidden" />
		排序值：<input type="text" name="updateLabelOrder" id="updateLabelOrder"  maxlength="25" style="  height: 22px;width:300px;"/>	
		<div class="btnsdiv">
		<input value="修改" class="userOperBtn panelBtn" type="button" onclick="updateLabelOrderAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<script type="text/javascript">
   $(function(){
      $(".tdFocusPic").mouseover(function(){
        $(this).next().css("display","");
      }).mouseout(function(){
        $(this).next().css("display","none");
      });
   });	
   
   	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	function updateLabel(updateRelationLabelId,order){
	  		var arg = {
			id:"updateLabelOrderDiv",
			title:"标签业务数据排序修改",
			height:150,
			width:400
		}
		showPanel(arg);
		$("#updateRelationLabelId").val(updateRelationLabelId);
		$("#updateLabelOrder").val(order);
	}
		
	function updateLabelOrderAjax(){
	  var labelId=$("#labelId").val();
	  var updateLabelOrder=$("#updateLabelOrder").val();
	  var updateRelationLabelId=$("#updateRelationLabelId").val();
	  var type=$("#type").val();
	  if(updateLabelOrder==''){
	  	 alert("排序值不能为空");
	     return;
	  }
	  var params="relationLabelId="+updateRelationLabelId+"&mainLabelId="+labelId+"&type="+type+"&serviceOrder="+updateLabelOrder;
	  $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/label/updateLabelRelationOrder?format=json",
				data : params,
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {              
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("修改成功");
							   location.reload();
							}else{
							  $("#updateLabelOrder").focus();
							   alert(objs.data);
			            	}
						}
					}
						showPanelClose();
				},
				error : function(data) {
					showErrorMsg(true,data);
				}
			});
	}
	
	
</script>
</body>
</html>