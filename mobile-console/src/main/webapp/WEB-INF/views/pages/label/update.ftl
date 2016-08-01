<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>


</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">修改标签</h3>
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
			<dd>专题管理</dd>
			<dd><a href="${rc.getContextPath()}/showTopic/list">标签管理</a></dd>
			<dd class="last"><h3>修改标签</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addFocusForm" name="addFocusForm" method="post" enctype="multipart/form-data"  action="${rc.getContextPath()}/label/update">
	<!--菜单内容-->
	<div class="adminContent clearfix">
	<input type="hidden" name="labelId" value="${subjectLabel.labelId!''}"/>
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>标题：</th>
				<td class="tdBox">
				  <input id="title" name="title" value="${subjectLabel.title!''}" style="width:200px;"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标签图片:</th>
				<td class="tdBox">
				  <#if !subjectLabel.imgUrl??>
				           暂无上传
				    <#else> <img src="${subjectLabel.imgUrl}" width="50" height="20"/>
				  </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标签图片：</th>
				<td class="tdBox">
					  <input type="file" name="label_pic_f" id="label_pic_f"/>jpg类型图片
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row">排序：</th>
				<td class="tdBox">
				  <input id="orderSeq" name="orderSeq" value="${subjectLabel.orderSeq!100}" style="width:200px;"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标签分类：</th>
				<td class="tdBox">
                <select id="labelGroup" name="labelGroup">
				   <option value="" >无</option>
				   	 <option value="1" <#if subjectLabel.labelGroup?exists&&subjectLabel.labelGroup==1>selected="selected"</#if>>热词</option>
				   <option value="3" <#if subjectLabel.labelGroup?exists&&subjectLabel.labelGroup==3>selected="selected"</#if>>同款</option>
				   <option value="5" <#if subjectLabel.labelGroup?exists&&subjectLabel.labelGroup==5>selected="selected"</#if>> 品牌</option>
				   <option value="20" <#if subjectLabel.labelGroup?exists&&subjectLabel.labelGroup==20>selected="selected"</#if>>大V</option>
				   <option value="21" <#if subjectLabel.labelGroup?exists&&subjectLabel.labelGroup==21>selected="selected"</#if>>元素</option>
				   <option value="22" <#if subjectLabel.labelGroup?exists&&subjectLabel.labelGroup==22>selected="selected"</#if>>品类</option>
				   
				</select>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
				                     显示: <input type="radio" name="status"  value="1"  <#if subjectLabel.status==1>checked="checked"</#if>/>
				                     隐藏: <input type="radio" name="status"  value="0" <#if subjectLabel.status==0>checked="checked"</#if>  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
					<p class="fl">
					<input type="button" onclick="update();" class="userOperBtn" value="确认修改"></input> 
					</p>				
			    </td>
			</tr>
		</table>
				
	</div>
</form>
</div>
</div>
<input type="hidden" value="${isSuccess!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>
</body>
<script type="text/javascript">

//var ue = UE.getEditor('container');
$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("修改成功");
	   location.href="${rc.getContextPath()}/label/list";
	 }else if(msg!=""){
	   alert(msg);
	 }
});

	function update(){
	    var title=$("#title").val();
	    var orderSeq=$("#orderSeq").val();
	    if(title==""){
	      alert("请输入完整数据");
	      return ;
	    }
	    if(orderSeq==""){
	    	$("#orderSeq").val(100);
	    }
	    if(confirm("确定修改此标签吗")){
	    	$("#addFocusForm").submit();
	    }
	}
</script>
</html>