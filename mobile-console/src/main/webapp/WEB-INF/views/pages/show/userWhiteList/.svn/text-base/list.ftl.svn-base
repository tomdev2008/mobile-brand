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
			<h3 class="topTitle fb f14">用户白名单管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/userWhiteList/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>用户白名单管理</dd>
				<dd class="last"><h3>用户白名单列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		        <label>用户ID：
					<input name="userId" type="text" id="userId" value="${userId!''}" size="12" />
		        </label>
		        <label>状态：
						 <select name="status" id="status" style="width:155px;">
				           <option value="-1" >全部</option>
				              <option value="1" <#if status??&&status=='1'>selected="selected"</#if>>正常</option>
				              <option value="0" <#if status??&&status=='0'>selected="selected"</#if>>停用</option>
				          </select> 
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="创建白名单" class="btn" onclick="addNewitem()" style="width:80px;text-align:center;height:22px;">创建白名单</a>
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
					<th width="10%">用户ID</th>
					<th width="50%">用户名称</th>
					<th width="10%">创建时间</th>
					<th width="10%">状态</th>
					<th width="10%">创建人</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (dateList?? && dateList?size > 0)>
			<#list dateList as item>
			<tr>
			    <td>${item.userId!''}
			    </td>
			    <td>${item.userName!''}</td>
			    <td>${item.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>				    
			        <#if item.status?? && item.status == 0>停用
					<#elseif item.status?? && item.status==1>正常
					</#if>
				</td>
			    <td>${item.createBy!''}</td>
				<td>
					<a href="javascript:void(0);" onclick="updateitem('${item.id!''}')" title="编辑">编辑</a>|
					<a href="javascript:void(0);" onclick="deleteitem('${item.id!''}')" title="删除">删除</a>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<div id="deleteitemDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此专题？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteitemAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
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
	
	//增加专题
	function addNewitem(){
	  var url="${rc.getContextPath()}/userWhiteList/bfAdd";
	  location.href=url;
	}
	
	
 function deleteitemAjax(){
    var id=    $("#deleteid").val();
      $.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/item/delete?id=" + id+"&format=json",
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
	 
	
</script>
</body>
</html>