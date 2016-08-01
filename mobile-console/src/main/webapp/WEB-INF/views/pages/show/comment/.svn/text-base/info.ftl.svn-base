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
			<dd>秀客管理</dd>
			<dd><a href="${rc.getContextPath()}/showComment/list">评论管理</a></dd>
			<dd class="last"><h3>详情菜单</h3>
			</dd>
		</dl>
	 <ul class="common_ul">
		<li><input type="button" value="评论详情" onclick="" class="userNavBtn navBtnChoose" /></li>
		<li><input type="button" value="操作日志" onclick="viewLogs()" class="userNavBtn" /></li>
	</ul>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">评论详情</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>秀ID：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${comment.id!''}"/>
					<a href="#" onclick="openShowInfo(${comment.showId!''})">${comment.showId!''}</a>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">秀状态：</th>
				<td class="tdBox">
					 <#if  comment.showFlag??&&comment.showFlag == 0>正常 
					 <#elseif  comment.showFlag??&&comment.showFlag == 1>用户删除
					 <#elseif  comment.showFlag??&&comment.showFlag == 2>前台管理员删除
					 <#elseif  comment.showFlag??&&comment.showFlag == 3>后台删除
					 </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">发布人：</th>
				<td class="tdBox">
				   <a href="#" onclick="openUserInfo(${comment.showUserId!''})">${comment.showUserName!''}</a>
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

	
	//跳转日志
	function viewLogs(){
	  location.href = "${rc.getContextPath()}/operateLog/list?modelName=comment&objectId=${comment.id!''}&datas[]=${comment.deleteFlag!''}";
	}
	
	//取消事件，返回菜单管理列表
	function returnMenuList(){
	  location.href = "${rc.getContextPath()}/showComment/list";
	}
</script>
</html>