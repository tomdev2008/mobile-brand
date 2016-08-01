<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">

</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">评论详情</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>秀客管理</dd>
			<dd><a href="${rc.getContextPath()}/commentReport/list">评论举报列表</a></dd>
			<dd class="last"><h3>详情</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/showComment/list" method="GET">
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">评论详情</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">秀ID：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${commentId!''}"/>
					<a href="#" onclick="openShowInfo(${showId!''})">${showId!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">秀状态：</th>
				<td class="tdBox">
					 <#if  showStatus??&&showStatus == 0>正常 
					 <#elseif  showStatus??&&showStatus == 1>用户删除
					 <#elseif  showStatus??&&showStatus == 2>前台管理员删除
					 <#elseif  showStatus??&&showStatus == 3>后台删除
					 </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">发布人：</th>
				<td class="tdBox">
				 <a href="#" onclick="openUserInfo(${showUserId!''})">  ${showUserName!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论内容：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${commentId!''}"/>
					${commentContent!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论人：</th>
				<td class="tdBox">
						<a href="#" onclick="openUserInfo(${commentUserId!''})">${commentUser!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论状态：</th>
				<td class="tdBox">
				  	<#if  commentStatus??&&commentStatus == 0>正常 
					 <#elseif  commentStatus??&&commentStatus == 1>用户删除
					 <#elseif  commentStatus??&&commentStatus == 2>前台管理员删除
					 <#elseif  commentStatus??&&commentStatus == 3>后台删除
					 </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">举报列表：</th>
				<td class="tdBox">
				<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="30%">举报时间</th>
						<th width="40%">举报原因</th>
						<th width="30%">举报人</th>
					</tr>
				</thead>
				<tbody>
				  <#if (reportlist?size > 0)>
				   <#list reportlist as report>
				      <tr>
						<td>${report.reportTime?string('yyyy-MM-dd HH:mm:ss')}</td>
						<td>${report.reportReason!''}</td>
						<td>${report.userName!''}</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="7"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>						
				</tbody>
			</table>
   <#include "/common/page.ftl">  
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">处理状态：</th>
				<td class="tdBox">
						 <#if  reportStatus??&&reportStatus == 0>未处理
						 <#elseif  reportStatus??&&reportStatus == 1>已核实
						 <#elseif  reportStatus??&&reportStatus == 2>已忽略
						 </#if>
				</td>
			</tr>
		</table>  
		<div style="padding-top:10px">
		 <#if reportStatus??&&reportStatus==0>
					<input  onclick="return ignoreReport(${commentId!''});" class="userOperBtn" type="button" value="忽略"/>
					</#if>
					 <#if reportStatus??&&reportStatus!=1>
					    <input onclick="return showComfigReport(${commentId!''});" class="userOperBtn" type="button"  value="核实"/>
				     </#if>
					<input onclick="return returnList();" class="userOperBtn" type="button"  value="返回"/>
		</div>
	</div>
	 <!-- 分页文件-->
   </form>
</div>
</div>
<div id="comfigReportDiv" class="none">
<input id="comfingcommentId" type="hidden"/>
		<h3>该评论为不合格内容，将删除该评论吗？</h3>
		<div>删除原因:
		<select id="resultTypeSelect">
		   <option value="1">营销广告</option>
		   <option value="2">淫秽色情</option>
		   <option value="3">虚假信息</option>
		   <option value="4">政治敏感</option>
		   <option value="5">其他</option>
		</select>
		</div>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="comfigReport()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
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
	       
   function ignoreReport(commentId){
		if(confirm("确实要忽略该举报吗？")== true){
			 updateStutas(commentId,2,"");  
		}
	}
	
	
		
	//弹出框显示核实
	function showComfigReport(commentId){
	    var arg={
	     id: "comfigReportDiv",
	     title:"核实举报",
	     height:200,
	     width:300
	    }
	   showPanel(arg);
       $("#comfingcommentId").val(commentId);
  }
    
   function comfigReport(){
     var commentId=$("#comfingcommentId").val();
     var resultTypeSelect=$("#resultTypeSelect").val();
	 updateStutas(commentId,1,resultTypeSelect);  
  }
  
  
  function updateStutas(commentId,status,resultTypeSelect){
	   $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/commentReport/updateStatus?commentIds=" + commentId+"&status="+status+"&resultType="+resultTypeSelect + "&format=json",
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
  

	
	//取消事件，返回菜单管理列表
	function returnList(){
	  location.href = "${rc.getContextPath()}/commentReport/list?html}";
	}
</script>
</html>