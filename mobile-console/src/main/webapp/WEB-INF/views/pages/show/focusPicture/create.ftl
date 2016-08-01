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
			<h3 class="topTitle fb f14">焦点图添加</h3>
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
			<dd>焦点图管理</dd>
			<dd><a href="${rc.getContextPath()}/focusPicture/list">焦点图管理</a></dd>
			<dd class="last"><h3>焦点图添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addFocusForm" name="addFocusForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/focusPicture/add">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>位置：</th>
				<td class="tdBox">
					 	<select name="position" id="position" >
				              <option value="1" >1</option>
				              <option value="2" >2</option>
				              <option value="3" >3</option>
				              <option value="4" >4</option>
				              <option value="5" >5</option>
				              <option value="6" >6</option>
				          </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>焦点图：</th>
				<td class="tdBox">
					  <input type="file" name="focus_pic_f" id="focus_pic_f"/> 最佳尺寸：750*330的jpg类型图片
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>开始时间：</th>
				<td class="tdBox">
				<input name="beginTime" type="text" id="beginTime" value=""  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>结束时间：</th>
				<td class="tdBox">
					<input name="endTime" type="text" id="endTime" value=""  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>绑定链接：</th>
				<td class="tdBox">
				 <table>
					<tr  style="  height: 30px">
					   <td width="60px">
					   <input type="hidden" value="" name="linkObject" id="linkObject" />
					   <input type="radio" name="linkType" class="linkType" value="2"  checked="checked" />话题</td> 
					   <td> 话题ID:</td>
					   <td><input id="linkObject2"  value="" maxlength="12"/> </td>
					   <td><span id="linkObjectMsg2" class="red"></span></tr>
					   <tr style="  height: 30px">
					   <td width="60px"><input type="radio" name="linkType"  class="linkType"  value="3" />秀</td> 
					   <td> 秀ID:</td>
					   <td><input id="linkObject3"  value="" maxlength="12"/> 
					   <td><span id="linkObjectMsg3" class="red" ></span></tr>
					 </tr>
			    </table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">备注：</th>
				<td class="tdBox">
                      <textarea id="remark" name="remark"></textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
                    <p class="fl">
						<input type="button" onclick="add();" class="userOperBtn" value="确认提交"></input> 
					</p>
				</td>
			</tr>
		</table>
				
	</div>
</form>
</div>
</div>
<input type="hidden" value="${status!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>

</body>
<script type="text/javascript">

$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("更新成功");
	   location.href="${rc.getContextPath()}/focusPicture/list";
	 }else if(msg!=""){
	   alert(msg);
	 }
	 
	$(".linkType").change(function() { 
	   $("#linkObjectTopicMsg").html("");
	   $("#linkObjectShowMsg").html("");
	}); 
	 
	 $("#linkObject2").blur(function(){
	 	checkShowTopic();
	 });
	 $("#linkObject3").blur(function(){
	    checkShow();
	 });
});

function checkShowTopic(){
  var isSuccess=false;
  var linkType = $('input[name="linkType"]:checked ').val();
		if(linkType==2){
		    var val=$("#linkObject2").val();
		    if(val!=''){
		       $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/showTopic/checkShowTopic?topicId=" + val +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "2")
							{
						       $("#linkObjectMsg2").html("话题不存在");
			            	}else{
			            		isSuccess=true;
			            	   $("#linkObjectMsg2").html("");
			            	}
						}
					}
				},
				error : function(data) {
				}
			}); 
		    }else{
		       $("#linkObjectMsg2").html("请输入话题ID");
		    }
	    }
   return isSuccess;
}

function checkShow(){
  var isSuccess=false;
   var linkType = $('input[name="linkType"]:checked ').val();
		if(linkType==3){
		    var val=$("#linkObject3").val();
		    if(val!=''){
		       $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/show/checkShow?showId=" + val +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "2")
							{
						       $("#linkObjectMsg3").html("秀不存在");
			            	}else{
			            	   isSuccess=true;
			            	   $("#linkObjectMsg3").html("");
			            	}
						}
					}
				},
				error : function(data) {
				}
			}); 
		    }else{
		       $("#linkObjectMsg3").html("请输入话题ID");
		    }
	    }
	    return isSuccess;
}


	function add(){
	    var linkType = $('input[name="linkType"]:checked ').val();
	    var linkObject =$("#linkObject"+linkType).val();
	    var beginTime=$("#beginTime").val();
	    var endTime=$("#endTime").val();
	    var pic=$("#focus_pic_f").val();
	    $("#linkObject").val(linkObject);
	    if(beginTime==""||endTime==""||linkObject==""||pic==""){
	      alert("请输入完整数据");
	      return ;
	    }
	    	   
	   	if(pic.toLowerCase().indexOf(".jpg")<0){
	   		alert("请上传.jpg类型的图片！");
	   		return false;
	    }
	    
	    if(linkType==2){
	      if(!checkShowTopic()){
	        return false;
	      }
	    }
	    if(linkType==3){
	      if(!checkShow()){
	        return false;
	      }
	    }
	     if(!compareDate(beginTime,endTime)){
	       alert("开始时间不能大于结束时间");
	      return ;
	   }
	   	var remark=$("#remark").val();
	    if(remark.length>1000){
	    	alert("备注长度不能大于1000个字符");
	       return ;
	    }
	    
	    $("#linkObject").val(linkObject);
	   $("#addFocusForm").submit();
	}
</script>
</html>