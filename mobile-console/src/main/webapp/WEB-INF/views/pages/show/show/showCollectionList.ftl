<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<style>
	.table_bg05.thsubject{
		font-weight: bold;
	}
	.tdsubject{
		padding: 5px 6px;
	}
	.tdSelect{
		width:260px;
		margin-left: -117px;
	}
</style>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">秀集合管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/show/showCollectionList" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀集合管理</dd>
				<dd class="last"><h3>秀集合列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		        <label>秀集合ID：
					<input name="showCollectionId" type="text" id="name" value="${showCollectionId!''}" size="12" />
		        </label>
		         <label>秀集合名称：
					<input name="name" type="text" id="name" value="${name!''}" size="12" />
		        </label>
		        <label>状态：
						 <select name="status" id="status" style="width:155px;">
				           <option value="" >全部</option>
				          	  <option value="1" <#if status??&&status=='1'>selected="selected"</#if>>显示</option>
				              <option value="0" <#if status??&&status=='0'>selected="selected"</#if>>隐藏</option>
				          </select> 
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="新增" class="btn" onclick="addNewsubject()" style="width:80px;text-align:center;height:22px;">新增</a>
			 	</label>
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="10%">ID</th>
					<th width="15%">秀集合名称</th>
					<th width="10%">秀集合图</th>
					<th width="10%">状态</th>
					<th width="15%">点击数</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (showCollectionList?? && showCollectionList?size > 0)>
			<#list showCollectionList as subject>
			<tr>
				<td>${subject.showCollectionId!''}</td>
			    <td>${subject.name!''}</td>
			    <td>
			    <div style="position: relative;">
						 <#if subject.listImgPath??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${subject.listImgPath!''}"  /> 
						  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${subject.listImgPath!''}" /></div>
						   </#if>
			    </td>
			    <td>				    
			        <#if subject.status?? && subject.status == 0>
			        		<a href="javascript:void(0);" onclick="update('${subject.showCollectionId!''}',1)" title="隐藏">隐藏</a>
			        </#if>
					<#if subject.status?? && subject.status  ==1><font class='red'>
							<a href="javascript:void(0);" onclick="update('${subject.showCollectionId!''}',0)" title="显示">显示</a>
					</font></#if>
				</td>
			    <td>${subject.clickCount!''}</td>
				<td>
					<a href="javascript:void(0);" onclick="goUpdate('${subject.showCollectionId!''}')" title="编辑">编辑</a>
					|
					<a href="javascript:void(0);" onclick="goAddShowColletion('${subject.showCollectionId!''}')" title="编辑">添加秀</a>
					<#if subject.isRecommend?? && subject.isRecommend == 0>
					|
					<a href="javascript:void(0);" onclick="advShowColletion('${subject.showCollectionId!''}')" title="编辑">推荐到发现页</a>
					</#if>
					|
					<a target="_black" href="http://m.xiu.com/show/showCollection.html?showColletionId=${subject.showCollectionId!''}"  title="编辑">预览</a>
					
				</td>   
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>

<div id="updateBatchShowDiv" class="none">
<input id="updateShowIds" type="hidden"/>
<input id="recommendChannel" type="hidden"/>
		<h3>推荐时间：</h3>
		<div>
          <input name="updateBatchBeginTime" type="text" id="updateBatchBeginTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
		</div>
		<br />
		<h3>排序：</h3>
		<input type="text" name="orderSeq" id="orderSeq" value="100" maxlength="20" style="width:120px;" />
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="addShowCecommendBatch()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<script type="text/javascript">
// 显示大图
	
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})

	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	//增加
	function addNewsubject(){
	  var url="${rc.getContextPath()}/show/goAddShowCollection";
	  location.href=url;
	}
	function goUpdate(id){
		var url="${rc.getContextPath()}/show/goUpdateShowCollection?id="+id;
	  location.href=url;
	}
	
	function goAddShowColletion(id){
		var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+id+"&act_id=";
	 	 location.href=url;
	}
	//推荐
	function advShowColletion(showCollectionId){
	var arg={
	     id: "updateBatchShowDiv",
	     title:"推荐至发现",
	     height:200,
	     width:400
	    }
	   showPanel(arg);
	   $("#updateShowIds").val(showCollectionId);
	}
	
	function addShowCecommendBatch(){
	  var showIds=$("#updateShowIds").val();
	  var channel=2;
	  var updateBatchBeginTime=$("#updateBatchBeginTime").val();
	  var orderSeq=$("#orderSeq").val();
	  if(updateBatchBeginTime==null||updateBatchBeginTime==""){
	    alert("请选择时间");
	    return ;
	  }
	  if(orderSeq==null||orderSeq==""){
	    alert("请选择排序号");
	    return ;
	  }
	  addShowCecommendAjax(showIds,channel,updateBatchBeginTime,orderSeq);
	}
	
	
	function addShowCecommendAjax(showIds,channel,beginTime,orderSeq){
	      $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/show/addShowRecommendBatch?showIds="+showIds+"&channel=" + channel+"&beginTime=" + beginTime+"&recommendType=2&orderSeq="+orderSeq + "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
							window.location.reload();
						}else{
						  alert(objs.smsg);
		            	}
					}
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	
	//修改
	function update(showCollectionId,status){
	 	$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/updateShowCollectionStatus?showCollectionId=" + showCollectionId+"&status="+status+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   $("#mainForm").submit();
			            	}else{
			            	    alert(objs.data);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		       });
	}
</script>
</body>
</html>