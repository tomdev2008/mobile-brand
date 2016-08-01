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
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>


</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">添加广告帧</h3>
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
			<dd>WAP管理</dd>
			<dd  class="last">广告帧管理</dd>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">广告帧管理</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>广告位名称：</th>
				<td class="tdBox">
					${advPlace.name}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>广告帧：</th>
				<td class="tdBox">
				 <div style="">
				    <ul  style="width: 580px;margin-bottom: 10px;">
				   <#if (advFramelist?size > 0)>
				   <#list advFramelist as  advFrame>
				       <li id="li_${advFrame.id}" advFrameId="${advFrame.id}" style="height: 35px;border-bottom: 1px #ddd dashed;  line-height: 35px;">
					        <span id="name_${advFrame.id}" style="  display: block;float: left;width: 300px;">${advFrame.name}</span>
					        <span style="display: block;float:right;">
					        <input type="button" class="userOperBtn" value="上移" onclick="updateOrderUp(this)" />
					        <input type="button" class="userOperBtn" value="下移" onclick="updateOrderDown(this)" />
					        <input type="button" class="userOperBtn" value="修改" onclick="updateAdvFrame('${advFrame.id}');"/>
					        <input type="button" class="userOperBtn" value="删除" onclick="deleteAdvFrame('${advFrame.id}')"/>
					        </span>
				       </li>
				   </#list>
				     <#else>该广告位还未添加广告帧
				   </#if>
				    </ul>
				    <form id="addAdvFrameForm" name="addAdvFrameForm">
					    <input type="hidden" name="advPlaceId" id="advPlaceId" value="${advPlace.id}" style="width:300px;"/>	
					    <div>
	                	<input type="text" name="name" id="name" value=""  maxlength="25" style="  height: 22px;width:352px;"/>		<input value="新增广告帧"  class="userOperBtn" onclick="checkAdvertisementPlace()" type="button" />	 
	                	</div>
                	</form>
				 </div>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
				    <input value="返回" class="userOperBtn panelBtn" type="button"  onclick="returnAdvertisementPlaceList()"/> 
				</td>
			</tr>
		</table>
	</div>
</div>
</div>


<div id="deleteAdvFrameDiv" class="showbox">
 <input id="deleteAdvFrameId" type="hidden" />
		<h3 >确定删除此广告帧？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deleteAdvFrameAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<div id="updateAdvFrameDiv" class="showbox">
 <input id="updateAdvFrameId" type="hidden" />
		广告帧名称：<input type="text" name="updateName" id="updateName" value=""  maxlength="25" style="  height: 22px;width:300px;"/>	
		<div class="btnsdiv">
		<input value="修改" class="userOperBtn panelBtn" type="button" onclick="updateAdvFrameAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

</body>
<script type="text/javascript">

    //验证信息
    function checkAdvertisementPlace(){
      var name=$("#name").val();
      if(name==""){
        alert("广告帧名称不能为空！");
        return false;
      }
      //提交信息  
      checkInfo();
    }
    
    //用Ajax提交信息
	function checkInfo(){
	    var params=$("#addAdvFrameForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/advFrame/save?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.reload();
								}else{
								  $("#name").focus();
								   alert(objs.data);
								  //$("#name_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
	
	function updateOrderUp(obj){
	  var li=$(obj).parents("li");
	  var chooseLi=li[0];
	  var id=$(chooseLi).attr("advFrameId");
	  var prevli=$(li[0]).prev();
	  var beid=$(prevli).attr("advFrameId");
	  if(beid==""||beid==undefined){
	     alert("已经是第一位");
	     return;
	  }
	  
	  updateOrderAjax(id,beid,0);
	}
	
	
	function updateOrderDown(obj){
	  var li=$(obj).parents("li");
	  var chooseLi=li[0];
	  var id=$(chooseLi).attr("advFrameId");
	  var nextli=$(li[0]).next();
	  var beid=$(nextli).attr("advFrameId");
	  if(beid==""||beid==undefined){
	     alert("已经是最后一位");
	     return;
	  }
	  updateOrderAjax(id,beid,1);
	}
	
	function updateOrderAjax(id,beid,type){
	 var params="id="+id+"&beId="+beid+"&type="+type;
	   $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/advFrame/editOrder?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   	  var chooseLi=$("#li_"+id);
								   	  var beli=$("#li_"+beid);
								   	  if(type==0){
								       $(beli).before(chooseLi); 
								   	  }else{
								   	    $(beli).after(chooseLi); 
								   	  }
								      
								}else{
								  $("#name").focus();
								   alert(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	
	}
	
		
	//删除
	function deleteAdvFrame(id) {
		var arg = {
			id:"deleteAdvFrameDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deleteAdvFrameId").val(id);
		
	}	
	
	
	function deleteAdvFrameAjax(){
	  var id=$("#deleteAdvFrameId").val();
	  var params="id="+id;
	  $.ajax({
				type : "POST",
				url : "${rc.getContextPath()}/advFrame/delete?format=json",
				data : params,
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {              
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   	  var chooseLi=$("#li_"+id);
							      $(chooseLi).remove();
							}else{
							  $("#name").focus();
							   alert(objs.data);
			            	}
						}
					}
						showPanelClose();
				},
				error : function(data) {
					showErrorMsg(true,data);
				}
			});
	}
		
	//删除
	function updateAdvFrame(id) {
		var arg = {
			id:"updateAdvFrameDiv",
			title:"广告帧修改",
			height:150,
			width:400
		}
		showPanel(arg);
		var name=$("#name_"+id).html();
		$("#updateName").val(name);
		$("#updateAdvFrameId").val(id);
		
	}	
	
	
	function updateAdvFrameAjax(){
	  var id=$("#updateAdvFrameId").val();
	  var name=$("#updateName").val();
	  if(name==''){
	  	 alert("广告帧名称不能为空");
	     return;
	  }
	  var params="id="+id+"&name="+name;
	  $.ajax({
				type : "POST",
				url : "${rc.getContextPath()}/advFrame/edit?format=json",
				data : params,
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {              
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("修改成功");
							   $("#name_"+id).html(name);
							}else{
							  $("#name").focus();
							   alert(objs.data);
			            	}
						}
					}
						showPanelClose();
				},
				error : function(data) {
					showErrorMsg(true,data);
				}
			});
	}
	
	
	//取消事件，返回广告位管理列表
	function returnAdvertisementPlaceList(){
	  location.href = "${rc.getContextPath()}/advertisementPlace/list";
	}
</script>
</html>