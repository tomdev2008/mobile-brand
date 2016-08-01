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
			<h3 class="topTitle fb f14">标签管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/label/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd class="last"><h3>标签管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
		    <div class="wapbt" style="border-bottom:0px;" align="left">
		        
		        <label>标签分类：
                 <select id="labelGroup" name="labelGroup">
				   <option value="" >无</option>
				   <option value="1" <#if labelGroup?exists&&labelGroup==1>selected="selected"</#if>>热词</option>
				   <option value="3" <#if labelGroup?exists&&labelGroup==3>selected="selected"</#if>>同款</option>
				   <option value="5" <#if labelGroup?exists&&labelGroup==5>selected="selected"</#if>> 品牌</option>
				   <option value="20" <#if labelGroup?exists&&labelGroup==20>selected="selected"</#if>>大V</option>
				   <option value="21" <#if labelGroup?exists&&labelGroup==21>selected="selected"</#if>>元素</option>
				   <option value="22" <#if labelGroup?exists&&labelGroup==22>selected="selected"</#if>>品类</option>
		</select>
		        </label>
		        <label>标签ID：
						 <input id="labelId" name="labelId" value="${labelId!''}"/>
		        </label>
		        <label>标签名称：
						 <input id="labelName" name="labelName" value="${labelName!''}"/>
		        </label>
		        <label>操作人：
						 <input id="opUser" name="opUser" value="${opUser!''}"/>
		        </label>
		        <label>显示：
						 <select name="display" id="status" style="width:155px;">
				           <option value="" >全部</option>
				              <option value=1 <#if display??&&display==1>selected="selected"</#if>>显示</option>
				              <option value=2 <#if display??&&display==2>selected="selected"</#if>>隐藏</option>
				          </select> 
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			    	<a href="javascript:void(0);" title="创建标签" class="btn" onclick="addLabel()" style="width:80px;text-align:center;height:22px;">创建标签</a>
			    	<a href="javascript:void(0);" title="同步标签关联数据" class="btn" onclick="syncSearch()" style="width:80px;text-align:center;height:22px;">同步搜索数据</a>
			    	<a href="javascript:void(0);" title="同步标签关联数据" class="btn" onclick="syncLabel()" style="width:80px;text-align:center;height:22px;">同步关联数据</a>
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
					<th width="5%">标签ID</th>
					<th width="20%">标题</th>
					<th width="10%">标签组</th>
					<th width="8%">状态</th>
					<th width="5%">排序</th>
					<th width="8%">标签图片</th>
					<th width="15%">创建时间</th>
					<th width="5%">创建人</th>
					<th width="5%">粉丝数</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (labelList?? && labelList?size > 0)>
			<#list labelList as label>
			<tr>
			    <td>${label.labelId!''}
			    </td>
			    <td>${label.title!''}</td>
			    <td>
			    <#if !label.labelGroup??>
			                       无
			    </#if>
			    
			    <#if label.labelGroup?? && label.labelGroup == 1>热词
					<#elseif label.labelGroup?? && label.labelGroup==3>同款
					<#elseif label.labelGroup?? && label.labelGroup==5>品牌
					<#elseif label.labelGroup?? && label.labelGroup==20>大V
					<#elseif label.labelGroup?? && label.labelGroup==21>元素
					<#elseif label.labelGroup?? && label.labelGroup==22>品类
				</#if>
			    </td>
			    <td>
			    	<#if label.status?? && label.status == 1>显示
					<#elseif label.status?? && label.status==0>隐藏
					</#if>
			    </td>
			    <td>
			    ${label.orderSeq!''}
			    </td>
			    <td>
			    	<div style="position: relative;">
						 <#if label.imgUrl??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${label.imgUrl!''}"  /> 
						  <div class="showBigImg" style="position: absolute;right: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${label.imgUrl!''}" /></div>
						   </#if>
					 </div>
			    </td>
			    <td>${label.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${label.operator!''}</td>
			    <td>${label.labelFans!''}</td>
				<td>
					<a href="javascript:void(0);" onclick="updateLabel('${label.labelId!''}')" title="编辑">编辑</a>|
					<a href="javascript:void(0);" onclick="deleteLabel('${label.labelId!''}')" title="删除">删除</a>|
					<a href="javascript:void(0);" onclick="labelRelationLabelDate('${label.labelId!''}')" title="编辑">关联标签(${label.relationLabelNum!''})</a>|
					<a href="javascript:void(0);" onclick="labelServiceDate('${label.labelId!''}')" title="编辑">关联业务数据(${label.relationServiceNum!''})</a>|
					<a  target="_blank" href="http://m.xiu.com/show/tagCollection.html?labelId=${label.labelId!''}" >预览</a>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<div id="deleteSubjectDiv" class="showbox">
 <input id="deleteid" type="hidden" />
		<h3 >确定删除此标签？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteLabelAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">

	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	});
	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	//增加专题
	function addLabel(){
	  var url="${rc.getContextPath()}/label/bfAdd";
	  location.href=url;
	}
	
	//增加专题
	function syncLabel(){
      $.ajax({
				type : "POST",
				async: true,
				url : "${rc.getContextPath()}/label/syncRelation?format=json",
	            dataType: "text",
				success : function(data, textStatus) {
		
				},
				error : function(data) {
		       }
		       });
		       alert("已经触发，数据正在同步中");
	}
	
	
	//增加专题
	function syncSearch(){
      $.ajax({
				type : "POST",
				async: true,
				url : "${rc.getContextPath()}/label/syncToSearch?format=json",
	            dataType: "text",
				success : function(data, textStatus) {
		
				},
				error : function(data) {
		       }
		       });
		       alert("已经触发，数据正在同步中");
	}
	
	//修改专题
	function updateLabel(labelId){
	  var url="${rc.getContextPath()}/label/bfUpdate?labelId="+labelId;
	  location.href=url;
	}
	
	//标签关联标签
	function labelRelationLabelDate(labelId){
	  var url="${rc.getContextPath()}/label/getRelationLabelList?labelId="+labelId;
	  location.href=url;
	}
	//标签关联业务
	function labelServiceDate(labelId){
	  var url="${rc.getContextPath()}/label/getLabelServiceDateList?labelId="+labelId;
	  location.href=url;
	}
	
	//删除绑定
	function deleteLabel(labelId) {
		var arg = {
			id:"deleteSubjectDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deleteid").val(labelId);
	}	
 function deleteLabelAjax(){
    var id=    $("#deleteid").val();
      $.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/label/delete?labelId=" + id+"&format=json",
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