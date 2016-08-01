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
			<h3 class="topTitle fb f14">焦点图管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/focusPicture/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>焦点图管理</dd>
				<dd class="last"><h3>焦点图列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
				<label>焦点图ID：
					<input name="focusId" type="text" id="focusId" value="${(focusId!'')?html}" size="12" />
				</label>
			    <label>首页显示时间：
			         <input name="startDate" type="text" id="startDate" value="<#if startDate??>${startDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
			          ---
			          <input name="endDate" type="text" id="endDate" value="<#if endDate??>${endDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</label>
		        <label>&nbsp;焦点图状态：
				          <select name="deleteFlag" id="deleteFlag" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="0" <#if deleteFlag??&&deleteFlag=='0'>selected="selected"</#if>>正常</option>
				              <option value="3" <#if deleteFlag??&&deleteFlag=='3'>selected="selected"</#if>>后台删除</option>
				              <option value="2" <#if deleteFlag??&&deleteFlag=='2'>selected="selected"</#if>>前台管理员删除</option>
				          </select> 
		        </label>
		    </div>
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		    	 <label>显示状态：
				          <select name="picStatus" id="picStatus" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="0" <#if picStatus??&&picStatus=='0'>selected="selected"</#if>>未开始</option>
				              <option value="1" <#if picStatus??&&picStatus=='1'>selected="selected"</#if>>进行中</option>
				              <option value="2" <#if picStatus??&&picStatus=='2'>selected="selected"</#if>>已结束</option>
				          </select> 
		        </label>
		        <label>&nbsp;类别：
				          <select name="linkType" id="linkType" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="2" <#if linkType??&&linkType=='2'>selected="selected"</#if>>话题</option>
				              <option value="3" <#if linkType??&&linkType=='3'>selected="selected"</#if>>秀</option>
				          </select> 
		        </label>
		        <label>创建人：
					<input name="creatorName" type="text" id="creatorName" value="${(creatorName!'')?html}" size="12" />
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="创建焦点图" class="btn" onclick="addNewfocusPicture()" style="width:80px;text-align:center;height:22px;">创建焦点图</a>
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
					<th width="7%">焦点图ID</th>
					<th width="8%">图片</th>
					<th width="5%">位置号</th>
					<th width="12%">开始时间</th>
					<th width="12%">结束时间</th>
					<th width="7%">焦点图状态</th>
					<th width="5%">显示状态</th>
					<th width="5%">点击数</th>
					<th width="12%">更新时间</th>
					<th width="6%">类别</th>
					<th width="8%">类别数据ID</th>
					<th width="12%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (focusPicturelist?? && focusPicturelist?size > 0)>
			<#list focusPicturelist as focusPicture>
			<tr>
			    <td>${focusPicture.focusId!''}</td>
			    <td>
			     <div style="  position: relative;">
			    <img width="75px" height="28px" class="tdFocusPic" src="${focusPicture.picUrl!''}"/>
			    <img style="display:none;top: 0px;left: 95%;background-color: white;position: absolute;" src="${focusPicture.picUrl!''}" width="380px" height="165px" />
			    </div>
			    </td>
			    <td>${focusPicture.position!''}</td>
			    <td>${focusPicture.beginTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${focusPicture.endTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>
				    <#if focusPicture.deleteFlag?? && focusPicture.deleteFlag == 0>正常
					<#elseif focusPicture.deleteFlag?? && focusPicture.deleteFlag==2>前台管理员删除
					<#elseif focusPicture.deleteFlag?? && focusPicture.deleteFlag==3>后台删除
					</#if>
			    </td>
			    <td>				    
			        <#if focusPicture.status?? && focusPicture.status == 0>未开始
					<#elseif focusPicture.status?? && focusPicture.status==1>进行中
					<#elseif focusPicture.status?? && focusPicture.status==2>已结束
					</#if>
				</td>
			    <td>${focusPicture.clickNum!''}</td>
			    <td>${focusPicture.updateDate?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>
			  		<#if focusPicture.linkType?? && focusPicture.linkType == 2>话题
					<#elseif focusPicture.linkType?? && focusPicture.linkType==3>秀
					</#if>  
			    </td>
			   <td>${focusPicture.linkObject!''}</td>
				<td>
					<a href="javascript:void(0);" onclick="info('${focusPicture.focusId!''}')" title="详情">详情</a> | 
					<a href="javascript:void(0);"  onclick="showUpdateLink('${focusPicture.focusId!''}','${focusPicture.linkType!''}','${focusPicture.linkObject!''}')" title="绑定">绑定</a> |
					<#if  focusPicture.deleteFlag??&&focusPicture.deleteFlag == 0>
					<a href="javascript:void(0);" onclick="deleteFocus('${focusPicture.focusId!''}')" title="删除">删除</a>
					<#else>删除
                    </#if>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="11"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>

<div id="updateLinkDiv" class="showbox">
 <input id="updateFocusId" type="hidden" />
 <input id="updateLinkType" type="hidden" />
 <input id="updateLinkObject" type="hidden" />
		<h3 id="updateShowIdTopicsTitle">绑定此焦点图点击跳转页面</h3>
			 <table>
					<tr  style="  height: 30px">
					   <td width="60px">
					   <input type="hidden" value="" name="linkObject" id="linkObject"/>
					   <input id="linkType2" type="radio" name="linkType" class="linkType" value="2"  />话题 </td> 
					   <td> 话题ID:</td>
					   <td><input id="linkObject2"  value=""/> </td>
					   <td><span id="linkObjectMsg2" class="red linkObjectMsg"></span></tr>
					<tr style="  height: 30px">
					   <td width="60px"><input  id="linkType3"  type="radio" name="linkType"  class="linkType"  value="3"  />秀</td> 
					   <td> 秀ID:</td>
					   <td><input id="linkObject3"  value=""/> 
					   <td><span id="linkObjectMsg3" class="red linkObjectMsg" ></span></tr>
					 </tr>
			    </table>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateLinkAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="deleteFocusDiv" class="showbox">
 <input id="deleteFocusId" type="hidden" />
		<h3 >确定删除此焦点图？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteFocusAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">

   $(function(){
      $(".tdFocusPic").mouseover(function(){
        $(this).next().css("display","");
      }).mouseout(function(){
        $(this).next().css("display","none");
      });
     
   });

	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	function info(id){
	  var url="${rc.getContextPath()}/focusPicture/info?focusId="+id;
	  location.href=url;
	}
	
	
	function addNewfocusPicture(){
	  var url="${rc.getContextPath()}/focusPicture/bfAdd";
	  location.href=url;
	}
	
	//修改绑定
	function showUpdateLink(focusId,LinkType,LinkObject) {
		var arg = {
			id:"updateLinkDiv",
			title:"修改绑定",
			height:200,
			width:400
		}
		showPanel(arg);
		$("#updateFocusId").val(focusId);
		$("#updateLinkType").val(LinkType);
		$("#updateLinkObject").val(LinkObject);
		$("#linkType"+LinkType).attr("checked","checked");
		$("#linkObject"+LinkType).val(LinkObject);
		 $("#linkObject2").blur(function(){
		 	checkShowTopic();
		 });
		 $("#linkObject3").blur(function(){
		    checkShow();
		 });
		 	 
		$(".linkType").change(function() { 
		   $(".linkObjectMsg").html("");
		}); 
		
	}
	
 function updateLinkAjax(){
    var focusId=    $("#updateFocusId").val();
    var LinkType = $('input[name="linkType"]:checked ').val();
	var LinkObject=	$("#linkObject"+LinkType).val();
	if(LinkType==2){
	  if(!checkShowTopic()){
	  return false;
	  }
	}
	if(LinkType==3){
	  if(!checkShow()){
	  return false;
	  }
	}
      $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/focusPicture/updateLink?focusId=" + focusId+"&linkType=" + LinkType+"&linkObject=" + LinkObject +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("更新成功");
							   location.reload();
			            	}else{
			            	    alert("更新失败");
			            	}
						}
					}
				},
				error : function(data) {
		       }
		       });
 }
	

	
	//修改绑定
	function deleteFocus(focusId) {
		var arg = {
			id:"deleteFocusDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deleteFocusId").val(focusId);
		
	}	
	
 function deleteFocusAjax(){
    var focusId=    $("#deleteFocusId").val();
      $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/focusPicture/delete?focusId=" + focusId+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("删除成功");
							   location.reload();
			            	}else{
			            	    alert("删除失败");
			            	}
						}
					}
				},
				error : function(data) {
		       }
		       });
 }
	 

function checkShowTopic(){
  var isSuccess=false;
  var linkType = $('input[name="linkType"]:checked ').val();
		if(linkType==2){
		    var val=$("#linkObject2").val();
		    if(val!=''){
		       $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/showTopic/checkShowTopic?topicId=" + val +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "2")
							{
						       $("#linkObjectMsg2").html("话题不存在");
			            	}else{
			            		isSuccess=true;
			            	   $("#linkObjectMsg2").html("");
			            	}
						}
					}
				},
				error : function(data) {
				}
			}); 
		    }else{
		       $("#linkObjectMsg2").html("请输入话题ID");
		    }
	    }
   return isSuccess;
}

function checkShow(){
  var isSuccess=false;
   var linkType = $('input[name="linkType"]:checked ').val();
		if(linkType==3){
		    var val=$("#linkObject3").val();
		    if(val!=''){
		       $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/show/checkShow?showId=" + val +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "2")
							{
						       $("#linkObjectMsg3").html("秀不存在");
			            	}else{
			            	   isSuccess=true;
			            	   $("#linkObjectMsg3").html("");
			            	}
						}
					}
				},
				error : function(data) {
				}
			}); 
		    }else{
		       $("#linkObjectMsg3").html("请输入话题ID");
		    }
	    }
	    return isSuccess;
}
	
	
</script>
</body>
</html>