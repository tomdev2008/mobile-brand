<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
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
			<h3 class="topTitle fb f14">菜单管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showComment/list?page.pageNo=1" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客管理</dd>
				<dd class="last"><h3>评论管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
                <div class="wapbt" style="border-bottom:0px;" align="left">
				      <label>评论内容：
				          <input name="content" type="text" id="content" value="<#if content??>${content}</#if>" size="20"  />
				          &nbsp;
				      </label>
				      <label>秀ID：
				          <input name="showId" type="text" id="showId" value="<#if showId??>${showId}</#if>" size="20"  />
				          &nbsp;
				      </label>
				      <label>评论时间：
				         <input name="startDate" type="text" id="startDate" value="<#if startDate??>${startDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				          ---
				          <input name="endDate" type="text" id="endDate" value="<#if endDate??>${endDate}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				      </label>
				</div>
                <div class="wapbt" style="border-bottom:0px;" align="left">
				      <label>评 论 人：
				          <input name="commentName" type="text" id="commentName" value="<#if commentName??>${commentName}</#if>" size="20"  />
				          &nbsp;
				      </label>
				      <label> 评论状态:
				          <select name="commentStatus" id="commentStatus" style="width:155px;">
				           <option value="" >全部</option>
				              <option value="0" <#if commentStatus??&&commentStatus=='0'>selected="selected"</#if>>正常</option>
				              <option value="1" <#if commentStatus??&&commentStatus=='1'>selected="selected"</#if>>用户删除</option>
				              <option value="2" <#if commentStatus??&&commentStatus=='2'>selected="selected"</#if>>前台管理员删除</option>
				              <option value="3" <#if commentStatus??&&commentStatus=='3'>selected="selected"</#if>>后台删除</option>
				          </select> 
				          &nbsp; 
				       </label>     
					 <label>
					    <a href="javascript:void(0);" title="查询" class="btn" onclick="return submitSeachForm()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
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
						<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
						<th width="26%">评论内容</th>
						<th width="9%">所属秀</th>
						<th width="10%">秀状态</th>
						<th width="11%">评论时间</th>
						<th width="12%">评论人</th>
						<th width="10%">评论状态</th>
						<th width="18%">执行操作</th>
					</tr>
				</thead>
				<tbody>
				 	<#if (commentlist?? && commentlist?size > 0)> 
				     <#list commentlist as menu>
				      <tr>
				        <td>
				        <#if menu.deleteFlag??&&menu.deleteFlag==0>
				        <input type="checkbox" name="checkboxId" value="${menu.id!''}" />
				        <#else><input type="checkbox" disabled="true" value="" name="checkboxId">
				        </#if>
				        </td> 
						<td>${menu.content!''}</td>
						<td><a href="#" onclick="openShowInfo(${menu.showId!''})">${menu.showId!''}</a></td>
						<td>						  
						<#if  menu.showFlag??&&menu.showFlag == 0>正常 
						 <#elseif  menu.showFlag??&&menu.showFlag == 1>用户删除
						 <#elseif  menu.showFlag??&&menu.showFlag == 2>前台管理员删除
						 <#elseif  menu.showFlag??&&menu.showFlag == 3>后台删除
						 </#if>
					 </td>
						<td>${menu.commentDate?string('yyyy-MM-dd HH:mm:ss')} </td>
						<td><a href="#" onclick="openUserInfo(${menu.userId!''})">${menu.petName!''}</a></td>
						<td>
						  <#if  menu.deleteFlag??&&menu.deleteFlag == 0>正常 
					 <#elseif  menu.deleteFlag??&&menu.deleteFlag == 1>用户删除
					 <#elseif  menu.deleteFlag??&&menu.deleteFlag == 2>前台管理员删除
					 <#elseif  menu.deleteFlag??&&menu.deleteFlag == 3>后台删除
					 </#if>
						</td>
						<td>
						  <a href="javascript:void(0);" onclick="viewComment('${menu.id}');" title="详情">详情</a>
						   <#if menu.deleteFlag??&&menu.deleteFlag==0>
						  |<a href="javascript:void(0);" onclick="showComfigReport('${menu.id}','1')" title="删除">删除</a> 
						   | <a href="javascript:void(0);" onclick="addComment('${menu.showId!''}','${menu.userId!''}')" title="回复">回复</a>
						   <#elseif menu.deleteFlag??&&menu.deleteFlag!=0>
						    |删除
						  </#if>
						  |<#if menu.isVisual??&&menu.isVisual==1>
						  	<a href="javascript:void(0);" onclick="showVisual('${menu.id}',0)" title="取消仅自己可见">取消仅自己可见</a> 
						  	<#else>
						  	<a href="javascript:void(0);" onclick="showVisual('${menu.id}',1)" title="仅自己可见">仅自己可见</a>
						  </#if>
						</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="8"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
			<#if page.totalCount gt 0 >
				<div class="wmt20 clearfix">
					<p class="fl">
						  <a id="link" onclick="deleteBatch()"><img id="imageId" src="${rc.getContextPath()}/resources/manager/images/button_del22.gif"></a>
						  <input id="linkVisual" type="button" onclick="updateAllVisual()" value="仅自己可见"/>
					</p>
				</div>
			</#if>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
</form>
</div>


<div id="comfigDeleteDiv" class="none">
<input id="comfigDeleteId" type="hidden"/>
		<h3>确定删除该评论吗？</h3>
		<div>删除原因：
		<select id="resultTypeSelect">
		   <option value="1">营销广告</option>
		   <option value="2">淫秽色情</option>
		   <option value="3">虚假信息</option>
		   <option value="4">政治敏感</option>
		   <option value="5">其他</option>
		</select>
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteComment()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<div id="deleteVisualDiv" class="showbox">
 <input id="commentid" type="hidden" />
 <input id="isVisual" type="hidden" />
		<h3 class="is_visual_title">确定设置为仅自己可见吗？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="updateVisualAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<div id="addComment" class="showbox">
	<div class="centerDiv" >
	<input type="hidden" id="showId"/>
	<input type="hidden" id="commentUserId"/>
		<table width="100%" class="table_bg05">
			<tr>
				<th class="thsubject">评论用户ID</td>
				<td class="tdsubject">
					<select class="tdSelect" name="">
						<option value="">--请选择--</option>
						<#if (whiteList?? && whiteList?size > 0)>
						<#list whiteList as item>
						<option value="${item.userId!''}">${item.userId!''},${item.userName!''}</option>
						</#list>
						</#if>
					</select>
				</td>
			</tr>
			<tr>
				<th class="thsubject">评论内容</td>
				<td class="tdsubject"><textarea name="" class="commentConect" style="width:370px;height:210px;"></textarea></td>
			</tr>
			<tr>
				<th class="thsubject"></td>
				<td class="tdsubject">
					<input type="button" class="userOperBtn operBtn" value="确定" onclick="addSubjectComment()" />
					<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()"/>
				</td>
			</tr>
		</table>
	</div>
</div>

</body>
<script type="text/javascript">

 function openUserInfo(userId){
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		openPannel(pageUrl);
}	
 function openShowInfo(showId){
   var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
	  openPannel(pageUrl);
}
function viewComment(commentId) {
	var pageUrl = "${rc.getContextPath()}/showComment/info?commentId="+commentId;
    openUrl(pageUrl);
}
//仅自己可见弹出框显示核实
function showVisual(commentId,visaul){
	var arg={
	     id: "deleteVisualDiv",
	     title:"仅自己可见确认",
	     height:200,
	     width:300
	    }
	   showPanel(arg);
	   if(visaul==0){
	   	$(".is_visual_title").html("确定取消仅自己可见吗");
	   }
       $("#commentid").val(commentId);
       $("#isVisual").val(visaul);
}

function updateVisualAjax(){
	var commectId=$("#commentid").val();
	var isVisual=$("#isVisual").val();
	$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showComment/updateVisual?id=" + commectId +"&isVisual="+isVisual+ "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
							alert("设置成功");
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
function updateAllVisual(){
	var ids = "";
	$(":checkbox").each(function(){
		if($(this).attr("checked")){
			ids += $(this).val() + ",";
		}
	});
	if(ids != ""){
		if(confirm("确实设置为仅自己可见吗？")== true){
		    $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/showComment/updateBatch?ids=" + ids + "&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
								alert("设置成功");
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
	}else{
		if($.trim(ids) == ""){
			alert("请选择要删除的评论");
		}
	}
}
//弹出框显示核实
function showComfigReport(commentId){
    var arg={
     id: "comfigDeleteDiv",
     title:"删除确认",
     height:200,
     width:300
    }
   showPanel(arg);
   $("#comfigDeleteId").val(commentId);
 }
   
   function deleteComment(){
    var id=  $("#comfigDeleteId").val();
    var resultType=  $("#resultTypeSelect").val();
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showComment/delete?id=" + id +"&resultType="+resultType+ "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
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

function deleteBatch(){
	var ids = "";
	$(":checkbox").each(function(){
		if($(this).attr("checked")){
			ids += $(this).val() + ",";
		}
	});
	if(ids != ""){
		if(confirm("确实要删除吗？")== true){
		    $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/showComment/deleteBatch?ids=" + ids + "&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
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
	}else{
		if($.trim(ids) == ""){
			alert("请选择要删除的评论");
		}
	}
}
 //添加评论
	function addComment(obj,userId) {
		var div="addComment";
		var arg = {
			id:div,
			title:"回复评论",
			height:455,
			width:500
		}
		showPanel(arg);
		$("#showId").val(obj);
		$("#commentUserId").val(userId);
	} 
	function addSubjectComment(){
		var userId=$(".tdSelect").val();
		var content=$(".commentConect").val();
		var showId=$("#showId").val();
		var commentedUserId=$("#commentUserId").val();
		if(userId=='' || userId==null){
			alert("请选择评论人ID");
			return ;
		}
		if(content=='' || content==null){
			alert("请填写评论内容");
			return ;
		}
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/showComment/addComment?showId="+showId+"&userId="+userId+"&content="+content+"&commentedUserId="+commentedUserId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs = $.parseJSON(data);
					alert(objs.smsg);
				    if(objs.scode == "0") {
				    	closeDiv();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	// 关闭浮层
	function closeDiv() {
		showPanelClose();
	}  
</script>
</html>