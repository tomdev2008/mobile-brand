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
			<h3 class="topTitle fb f14">话题管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showTopic/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>话题管理</dd>
				<dd class="last"><h3>话题列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
				<label>话题标题：
					<input name="title" type="text" id="title" value="${(title!'')?html}" size="12" />
				</label>
			    <label>发布时间：
			         <input name="startDate" type="text" id="startDate" value="<#if startDate??>${startDate}</#if>"  maxlength="11" style="width:120px;"  ontopic="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
			          ---
			          <input name="endDate" type="text" id="endDate" value="<#if endDate??>${endDate}</#if>"  maxlength="11" style="width:120px;"  ontopic="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</label>
		        <label>发布人：
					<input name="publishUserName" type="text" id="publishUserName" value="${(publishUserName!'')?html}" size="12" />
		        </label>

			</div>
            <div class="wapbt" style="border-bottom:0px;" align="left">
				 <label>显示状态：
				          <select name="status" id="status" style="width:117px;">
				           <option value="" >全部</option>
				              <option value="0" <#if status??&&status==0>selected="selected"</#if>>未开始</option>
				              <option value="1" <#if status??&&status==1>selected="selected"</#if>>进行中</option>
				              <option value="2" <#if status??&&status==2>selected="selected"</#if>>已结束</option>
				          </select> 
		        </label>
				 <label>话题状态：
				          <select name="deleteFlag" id="deleteFlag" style="width:117px;">
				           <option value="" >全部</option>
				              <option value="0" <#if deleteFlag??&&deleteFlag=='0'>selected="selected"</#if>>正常</option>
				              <option value="3" <#if deleteFlag??&&deleteFlag=='3'>selected="selected"</#if>>后台删除</option>
				              <option value="2" <#if deleteFlag??&&deleteFlag=='2'>selected="selected"</#if>>前台管理员删除</option>
				          </select> 
		        </label>
				 <label>是否热门：
				          <select name="isHot" id="isHot" style="width:117px;">
				           <option value="" >全部</option>
				              <option value="0" <#if isHot??&&isHot=='0'>selected="selected"</#if>>普通</option>
				              <option value="1" <#if isHot??&&isHot=='1'>selected="selected"</#if>>热门</option>
				          </select> 
		        </label>

		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="创建话题" class="btn" onclick="addNewtopic()" style="width:80px;text-align:center;height:22px;">创建话题</a>
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
					<th width="4%">话题ID</th>
					<th width="8%">话题标题</th>
					<th width="6%">话题图片</th>
					<th width="5%">参与人数</th>
					<th width="5%">参与秀数</th>
					<th width="5%">精选人数</th>
					<th width="5%">精选秀数</th>
					<th width="5%">点赞数</th>
					<th width="4%">评论数</th>
					<th width="4%">分享数</th>
					<th width="6%">发布时间</th>
					<th width="6%">开始时间</th>
					<th width="6%">结束时间</th>
					<th width="5%">发布人</th>
					<th width="5%">显示状态</th>
					<th width="6%">话题状态</th>
					<th width="6%">是否热门</th>
					<th width="12%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (topicList?? && topicList?size > 0)>
			<#list topicList as topic>
			<tr>
			    <td>${topic.topicId!''}</td>
			    <td>${topic.title!''}</td>
			    <td>
				     <div style="  position: relative;">
					    <img width="75px" height="28px" class="tdtopicPic" src="${topic.imageUrl!''}"/>
					    <img style="display:none;top: 0px;left: 95%;background-color: white;position: absolute;  border: 2px solid #ddd;" src="${topic.imageUrl!''}" width="380px"  />
				    </div>
			    </td>
			    <td>${topic.userNum!''}</td>
			    <td>${topic.showNum!''}</td>
			    <td>${topic.selectionUserNum!''}</td>
			    <td>${topic.selectionShowNum!''}</td>
			    <td>${topic.praisedNum!''}</td>
			    <td>${topic.commentNum!''}</td>
			    <td>${topic.shareNum!''}</td>
			    <td>${topic.publishTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${topic.beginTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${topic.endTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			   <td>${topic.publishUserName!''}</td>
			    <td>
				    <#if topic.status?? && topic.status == 0>未开始
					<#elseif topic.status?? && topic.status==1>进行中
					<#elseif topic.status?? && topic.status==2>已结束
					</#if>
			    </td>
			    <td>
				    <#if topic.deleteFlag?? && topic.deleteFlag == 0>正常
					<#elseif topic.deleteFlag?? && topic.deleteFlag==2>前台管理员删除
					<#elseif topic.deleteFlag?? && topic.deleteFlag==3>后台删除
					</#if>
			    </td>
			    <td>
				    <#if topic.isHot?? && topic.isHot == 0>普通
					<#elseif topic.isHot?? && topic.isHot==1><font class='red'>热门</font>
					</#if>
			    </td>
				<td>
					<a href="javascript:void(0);" onclick="info('${topic.topicId!''}')" title="详情">详情</a> | 
					<#if  topic.deleteFlag??&&topic.deleteFlag == 0>
					  <#if  topic.isHot??&&topic.isHot == 0>
					  <a href="javascript:void(0);" onclick="updateTopicHot('${topic.topicId!''}','1')" title="设为热门">设为热门</a>|
					  <#else>
					  <a href="javascript:void(0);" onclick="updateTopicHot('${topic.topicId!''}','0')" title="取消热门">取消热门</a>|
					  </#if>
					<a href="javascript:void(0);" onclick="deletetopic('${topic.topicId!''}')" title="删除">删除</a>
					<#else>
					<#if  topic.isHot??&&topic.isHot == 0>
					         设为热门|
					  <#else>
					        取消热门|
					  </#if>
					删除
					</#if>

				</td>    
		    </#list>
		    
		    <#else>
		    <tr><td colspan="16"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>


<div id="deletetopicDiv" class="showbox">
 <input id="deletetopicId" type="hidden" />
		<h3 >确定删除此话题？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deletetopicAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="updatetopicHotDiv" class="showbox">
 <input id="updatetopicHotId" type="hidden" />
 <input id="updatetopicisHot" type="hidden" />
		<h3 >确定把该话题<font id="hotStr"></font>？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateTopicHotAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">

   $(function(){
      $(".tdtopicPic").mouseover(function(){
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
	  var url="${rc.getContextPath()}/showTopic/info?topicId="+id;
	  location.href=url;
	}
	
	
	function addNewtopic(){
	  var url="${rc.getContextPath()}/showTopic/bfAdd";
	  location.href=url;
	}
	
	//修改绑定
	function deletetopic(topicId) {
		var arg = {
			id:"deletetopicDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deletetopicId").val(topicId);
	}	
	
 function deletetopicAjax(){
    var topicId=    $("#deletetopicId").val();
      $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/showTopic/delete?topicId=" + topicId+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert(objs.smsg);
							   location.reload();
			            	}else{
			            	    alert(objs.smsg);
			            	}
						}
					}
				},
				error : function(data) {
		       }
		       });
 }
	 

function updateTopicHot(topicId,isHot){
		var arg = {
			id:"updatetopicHotDiv",
			title:"确定修改热门",
			height:150,
			width:300
		}
		showPanel(arg);
		if(isHot==0){
			$("#hotStr").html("由热门改为普通");
		}else{
			$("#hotStr").html("由普通改为热门");
		}
		$("#updatetopicisHot").val(isHot);
		$("#updatetopicHotId").val(topicId);

}

	
 function updateTopicHotAjax(){
    var isHot=    $("#updatetopicisHot").val();
    var topicId=    $("#updatetopicHotId").val();
      $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/showTopic/updateTopicHot?topicId=" + topicId+"&isHot="+isHot+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert(objs.smsg);
							   location.reload();
			            	}else{
			            	   alert("修改失败");
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