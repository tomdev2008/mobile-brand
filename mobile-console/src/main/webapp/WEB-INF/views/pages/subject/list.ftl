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
			<h3 class="topTitle fb f14">专题管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/subject/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>专题管理</dd>
				<dd class="last"><h3>专题列表</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		        <label>专题ID：
					<input name="id" type="text" id="id" value="${id!''}" size="12" />
		        </label>
		        <label>标题：
					<input name="title" type="text" id="title" value="${title!''}" size="12" />
		        </label>
		        <label>创建人：
					<input name="creatorName" type="text" id="creatorName" value="${creatorName!''}" size="12" />
		        </label>
		        <label>状态：
						 <select name="status" id="status" style="width:155px;">
				           <option value="-1" >全部</option>
				              <option value="0" <#if status??&&status=='0'>selected="selected"</#if>>未开始</option>
				              <option value="1" <#if status??&&status=='1'>selected="selected"</#if>>进行中</option>
				              <option value="2" <#if status??&&status=='2'>selected="selected"</#if>>已结束</option>
				          </select> 
		        </label>
		        <label>标签名称：
					<input name="labelName" type="text" id="labelName" value="${labelName!''}" size="12" />
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="创建专题" class="btn" onclick="addNewsubject()" style="width:80px;text-align:center;height:22px;">创建专题</a>
			    	<a href="javascript:void(0);" title="专题评论" class="btn" onclick="getSubjectCommentList()" style="width:80px;text-align:center;height:22px;">专题评论</a>
			    	<a href="javascript:void(0);" title="标签管理" class="btn" onclick="getLabelList()" style="width:80px;text-align:center;height:22px;">标签管理</a>
			    	<a href="http://m.xiu.com/H5/H5list/h5ZT/H5ZT.html" target="_blank" title="预览列表" class="btn" style="width:80px;text-align:center;height:22px;">预览列表</a>
			    	<a href="javascript:void(0);" title="导入专题" class="btn" onclick="toImport();return false;" style="width:80px;text-align:center;height:22px;" id="query">导入专题</a>
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
					<th width="3%">专题ID</th>
					<th width="5%">名称</th>
					<th width="5%">标题</th>
					<th width="8%">图片</th>
					<th width="6%">类型</th>
					<th width="4%">排序值</th>
					<th width="4%">评论数</th>
					<th width="5%">标签</th>
					<th width="7%">创建时间</th>
					<th width="7%">更新时间</th>
					<th width="7%">开始时间</th>
					<th width="7%">结束时间</th>
					<th width="4%">状态</th>
					<th width="4%">创建人</th>
					<th width="6%">操作</th>
					<th width="5%">显示到列表</th>
					<th width="5%">点击数</th>
					<th width="5%">收藏数</th>
					<th width="5%">分享数</th>
				</tr>
			</thead>
			<tbody>
			<#if (subjectList?? && subjectList?size > 0)>
			<#list subjectList as subject>
			<tr>
			    <td>${subject.subjectId!''}
			    </td>
			    <td>${subject.name!''}</td>
			    <td>${subject.title!''}</td>
			    <td>
			     <div style="  position: relative;">
			    <img width="75px" height="28px" class="tdFocusPic" src="${subject.outPic!''}"/>
			    <img style="display:none;bottom: 0px;left: 95%;background-color: white;position: absolute;  border: 2px solid #ddd;" src="${subject.outPic!''}" width="380px"  />
			    </div>
			    </td>
			    <td>
			    <#if subject.displayStytle?? && subject.displayStytle==1>竖专题(大)
			    <#elseif subject.displayStytle?? && subject.displayStytle==2>横专题(小)
			    </#if>
			    </td>
			    <td>
			    ${subject.orderSeq!''}
			    </td>
			    <td>
			    ${subject.commentNum!''}
			    </td>
			    <td>
			    <#if (subject.labelCentre?? && subject.labelCentre?size > 0)>
	   				<#list subject.labelCentre as label>
	   					${label.title}
	   				</#list>
	   			</#if>
			    </td>
			    <td>${subject.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${subject.updateTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${subject.startTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${subject.endTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>				    
			        <#if subject.status?? && subject.status == 0>未开始
					<#elseif subject.status?? && subject.status==1><font class='red'>进行中</font>
					<#elseif subject.status?? && subject.status==2><font class='gray'>已结束</font>
					</#if>
				</td>
			    <td>${subject.creatorName!''}</td>
				<td>
					<a href="javascript:void(0);" onclick="updateSubject('${subject.subjectId!''}')" title="编辑">编辑</a>|
					<a href="javascript:void(0);" onclick="deletesubject('${subject.subjectId!''}')" title="删除">删除</a>|
					<a href="http://m.xiu.com/subject/subjectDetail.html?subjectId=${subject.subjectId!''}" target="_blank" title="预览">预览</a>
					<#if subject.status?? && subject.status==1>
					<a href="javascript:void(0);" onclick="addComment('${subject.subjectId!''}')" title="评论">评论</a>
					</#if>
				</td>   
				<td>
					<#if subject.isShow?? && subject.isShow==1>
						<a href="javascript:void(0);" onclick="isShow('${subject.subjectId!''}',2)" title="否">不显示</a>
					<#elseif subject.isShow?? && subject.isShow==2>
						<a href="javascript:void(0);" onclick="isShow('${subject.subjectId!''}',1)" title="是">显示</a>
					<#else>
						<a href="javascript:void(0);" onclick="isShow('${subject.subjectId!''}',1)" title="是">显示</a>|
						<a href="javascript:void(0);" onclick="isShow('${subject.subjectId!''}',2)" title="否">不显示</a>
					</#if>
				</td>
				<td>${subject.clickCount!''}</td>
				<td>${subject.saveCount!''}</td>
				<td>${subject.shareCount!''}</td>  
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<div id="deleteSubjectDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此专题？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteSubjectAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<div id="addComment" class="showbox">
	<div class="centerDiv" >
	<input type="hidden" id="subjectId"/>
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
	function addNewsubject(){
	  var url="${rc.getContextPath()}/subject/bfAdd";
	  location.href=url;
	}
	
	//修改专题
	function updateSubject(subjectId){
	  var url="${rc.getContextPath()}/subject/bfUpdate?subjectId="+subjectId;
	  location.href=url;
	}
	
	//修改专题
	function getSubjectCommentList(){
	  var url="${rc.getContextPath()}/subject/commentlist";
	  location.href=url;
	}
	//标签管理
	function getLabelList(){
		var url="${rc.getContextPath()}/label/list";
		location.href=url;
	}
	//删除绑定
	function deletesubject(id) {
		var arg = {
			id:"deleteSubjectDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deleteid").val(id);
		
	}	
	
 function deleteSubjectAjax(){
    var id=    $("#deleteid").val();
      $.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/subject/delete?subjectId=" + id+"&format=json",
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
 function toImport(){
	location.href="${rc.getContextPath()}/subject/toimport";
}
//添加评论
	function addComment(obj) {
		var div="addComment";
		var arg = {
			id:div,
			title:"添加评论",
			height:455,
			width:500
		}
		showPanel(arg);
		$("#subjectId").val(obj);
	}
		//评论确定
	function addSubjectComment(){
		var userId=$(".tdSelect").val();
		var content=$(".commentConect").val();
		var subjectId=$("#subjectId").val();
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
			url : "${rc.getContextPath()}/subject/addComment?subjectId="+subjectId+"&userId="+userId+"&content="+content+"&format=json",
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
 //是否显示
function isShow(id,show){
      $.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/subject/isShow?subjectId=" + id+"&isShow="+show+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("修改成功");
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