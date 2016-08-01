<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css">
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
			<dd><h3>详情菜单</h3></dd>
			<dd class="last"><h3>操作日志</h3></dd>
		</dl>
		<ul class="common_ul">
			<li><input type="button" value="评论详情" onclick="viewComment()" class="userNavBtn" /></li>
			<li><input type="button" value="操作日志"  class="userNavBtn navBtnChoose" /></li>
		</ul>
	</div>
	<!--导航end-->
	
	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="15%">操作时间</th>
						<th width="15%">操作描述</th>
						<th width="15%">操作人</th>
						<th width="55%"></th>
					</tr>
				</thead>
				<tbody>
				   <#if (opLoglist?size > 0)>
				   <#list opLoglist as oplog>
				      <tr>
				        <td>${oplog.createDate?string('yyyy-MM-dd HH:mm:ss')}</td> 
						<td>${oplog.operDesc!''}</td>
						<td>${oplog.userName!''}</td>
					 </tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="3"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
				<div class="w mt20 clearfix">
					<p class="fl">
					<#if  dataObj??&&dataObj[0] == '0'>
					<button onclick="return showComfigReport(${objectId!''});" class="userOperBtn">删除</button> 
					</#if>
					<button onclick="return returnMenuList();" class="userOperBtn">返回</button> 					</p>
				</div>
	</div>	
</div>
 <!-- 分页文件-->
   <#include "/common/page.ftl">  
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
	
	
	function viewComment(commentId) {
		var pageUrl = "${rc.getContextPath()}/showComment/info?commentId="+${objectId!''};
		location.href = pageUrl;
	}
	
	
	//取消事件，返回菜单管理列表
	function returnMenuList(){
	  location.href = "${rc.getContextPath()}/showComment/list";
	}
	
	
	
</script>
</html>