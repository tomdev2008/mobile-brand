<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>

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
			<dd><a href="${rc.getContextPath()}/subject/list">专题管理</a></dd>
			<dd><a href="${rc.getContextPath()}/subject/commentlist">评论管理</a></dd>
			<dd class="last"><h3>详情菜单</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">评论详情</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">专题ID：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${comment.id!''}"/>
					<a href="#" onclick="openSubjectInfo(${comment.subjectId!''})">${comment.subjectId!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">专题名称：</th>
				<td class="tdBox">
				  ${comment.subjectName!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论内容：</th>
				<td class="tdBox">
				<textarea id="memo" name="memo" style="width:500px;height:100px;" >${(comment.content!'')?html}</textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论时间：</th>
				<td class="tdBox">
					${comment.commentDate?string('yyyy-MM-dd HH:mm:ss')} 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论人：</th>
				<td class="tdBox">
					<a href="#" onclick="openUserInfo(${comment.userId!''})"> ${comment.petName!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论状态：</th>
				<td class="tdBox">
	                 <#if  comment.deleteFlag??&&comment.deleteFlag == 0>正常 
					 <#elseif  comment.deleteFlag??&&comment.deleteFlag == 1>用户删除
					 <#elseif  comment.deleteFlag??&&comment.deleteFlag == 2>前台管理员删除
					 <#elseif  comment.deleteFlag??&&comment.deleteFlag == 3>后台删除
					 </#if>
				</td>
			</tr>
		</table>
				<div class="w mt20 clearfix">
					<p class="fl">
				     <#if  comment.deleteFlag??&&comment.deleteFlag == 0>
					<button onclick="return showComfigReport(${comment.id!''});" class="userOperBtn">删除</button> 
					</#if>
					<button onclick="return returnMenuList();" class="userOperBtn">返回</button> 
					</p>
				</div>
	</div>
</div>
</div>

<div id="comfigDeleteDiv" class="none">
<input id="comfigDeleteId" type="hidden"/>
		<h3>确定删除该评论吗？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteComment()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

</body>
<script type="text/javascript">

 function openUserInfo(userId){
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		openPannel(pageUrl);
}	
	
 function openSubjectInfo(subjectId){
   var pageUrl = "${rc.getContextPath()}/subject/bfUpdate?subjectId="+subjectId;
	 openPannel(pageUrl);
}
   //弹出框显示核实
	function showComfigReport(commentId){
	    var arg={
	     id: "comfigDeleteDiv",
	     title:"删除确认",
	     height:150,
	     width:300
	    }
	   showPanel(arg);
       $("#comfigDeleteId").val(commentId);
     }
   
   function deleteComment(){
    var id=  $("#comfigDeleteId").val();
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/subject/deleteComment?id=" + id + "&format=json",
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
	function returnMenuList(){
	  location.href = "${rc.getContextPath()}/subject/commentlist";
	}
</script>
</html>