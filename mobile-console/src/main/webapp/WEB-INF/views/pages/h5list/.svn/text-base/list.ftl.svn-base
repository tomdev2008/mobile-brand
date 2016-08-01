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
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">H5列表管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/h5list/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>H5列表管理</dd>
				<dd class="last"><h3>H5列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		        <label>显示：
						 <select name="status" id="status" style="width:155px;">
				           <option value="" >全部</option>
				              <option value=1 <#if status??&&status==1>selected="selected"</#if>>显示</option>
				              <option value=2 <#if status??&&status==2>selected="selected"</#if>>隐藏</option>
				          </select> 
		        </label>
		        <label>标题：
					<input name="title" type="text" id="title" value="${title!''}" size="12" />
		        </label>
		        <label>创建人：
					<input name="creatorName" type="text" id="creatorName" value="${creatorName!''}" size="12" />
		        </label>
		        <label>标签名称：
					<input name="labelName" type="text" id="labelName" value="${labelName!''}" size="12" />
		        </label>
		        <label>URL链接：
					<input name="h5Url" type="text" id="h5Url" value="${h5Url!''}" size="12" />
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="添加H5" class="btn" onclick="addNewH5list()" style="width:80px;text-align:center;height:22px;">添加H5</a>
			    	<a href="javascript:void(0);" title="新增H5" class="btn" onclick="createH5list()" style="width:80px;text-align:center;height:22px;">新增H5</a>
			    	<a href="/h5page/createorupdatepage" title="新增H5组件" class="btn" style="width:80px;text-align:center;height:22px;">新增H5组件</a>
			    	<a href="http://m.xiu.com/fopageApp/H5list.html" title="预览列表" class="btn" target="_blank" style="width:80px;text-align:center;height:22px;">预览列表</a>
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
					<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
					<th width="10%">ID</th>
					<th width="15%">标题</th>
					<th width="15%">标签</th>
					<th width="12%">图片</th>
					<th width="8%">状态</th>
					<th width="12%">创建人</th>
					<th width="15%">创建时间</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (h5List?? && h5List?size > 0)>
			<#list h5List as list>
			<tr>
			    <td><input type="checkbox" name="ids" value="${list.id!''}"/></td>
			    <td>${list.id!''}
			    </td>
			    <td>${list.title!''}</td>
			   	<td>
				    <#if (list.labelCentre?? && list.labelCentre?size > 0)>
		   				<#list list.labelCentre as label>
		   					${label.title}
		   				</#list>
		   			</#if>
			    </td>
			    <td>
			     <div style="  position: relative;">
				    <img width="75px" height="28px" class="tdFocusPic" src="${list.photo!''}"/>
				    <img style="display:none;bottom: 0px;left: 95%;background-color: white;position: absolute;  border: 2px solid #ddd;" src="${list.photo!''}" width="380px"  />
				 </div>
			    </td>
			    <td>				    
			        <#if list.status?? && list.status == 1>显示
					<#elseif list.status?? && list.status==2>隐藏
					</#if>
				</td>
			    <td>
			    ${list.operator!''}
			    </td>
			    <td>${list.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
				<td>
					<a href="javascript:void(0);" onclick="updateList('${list.id!''}','${list.type!''}')" title="编辑"><#if list.type?? && list.type == 3>编辑H5
					<#else>编辑
					</#if></a>|
					<a href="${list.h5Url!''}" title="预览" target="_black">预览</a>|
					<#if list.type?? && list.type == 3>
						<a href="/h5page/editpage?pageId=${list.id!''}" title="修改H5组件">修改H5组件</a>
					</#if>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
		<#if page.totalCount gt 0 >
			<div class="w mt20 clearfix">
				<p class="fl">
					  <a id="link" onclick="deleteAll()"><img id="imageId" src="${rc.getContextPath()}/resources/manager/images/button_del22.gif"></a>
				</p>
			</div>
		</#if>
</div>
<div id="deleteSubjectDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此专题？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteSubjectAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
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
	
	//增加
	function addNewH5list(){
	  var url="${rc.getContextPath()}/h5list/bfAdd";
	  location.href=url;
	}
	//新增H5
	function createH5list(){
	  var url="${rc.getContextPath()}/h5list/bfCreate";
	  location.href=url;
	}
	//修改
	function updateList(subjectId,type){
	  var url;
	  if(type==2){
	  	url="${rc.getContextPath()}/h5list/bfNewUpdate?id="+subjectId;
	  }else if(type == 1){
	  	url="${rc.getContextPath()}/h5list/bfUpdate?id="+subjectId;
	  }else if(type == 3){
	  	url = "${rc.getContextPath()}/h5page/createorupdatepage?pageId="+subjectId;
	  }
	  location.href=url;
	}
	
 //删除多个
 function deleteAll(){
	var ids = "";
	$(":checkbox").each(function(){
		if($(this).attr("checked")){
			ids += $(this).val() + ",";
		}
	});
	if(ids != ""){
		if(confirm("确定要删除吗？")== true){
		    $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/h5list/deleteAll?ids=" + ids + "&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
								alert("删除成功");
								location.href="${rc.getContextPath()}/h5list/list";
							}else{
							  alert("删除失败");
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
			alert("请选择要删除的H5列表");
		}
	}
}
</script>
</body>
</html>