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
			<h3 class="topTitle fb f14">话题添加</h3>
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
			<dd>话题管理</dd>
			<dd><a href="${rc.getContextPath()}/showTopic/list">话题管理</a></dd>
			<dd class="last"><h3>话题添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addFocusForm" name="addFocusForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/showTopic/add">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>话题标题：</th>
				<td class="tdBox">
				  <input id="title" name="title" value=""/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>话题内容：</th>
				<td class="tdBox">
				     <textarea id="content"  name="content" style=" width: 350px; height: 150px;"></textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>话题图片：</th>
				<td class="tdBox">
					  <input type="file" name="topic_pic_f" id="topic_pic_f"/> 格式：比例为16:9 的jpg类型图片，宽度在640px以上
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
				<th class="thList" scope="row">是否热门：</th>
				<td class="tdBox">
                     普通: <input type="radio" name="isHot"  value="0"  checked="checked" />
                     热门: <input type="radio" name="isHot"  value="1"   />
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

    <!-- 配置文件
    <script type="text/javascript" src="${rc.getContextPath()}/ueditor/ueditor.config.js"></script>
           编辑器源码文件 
    <script type="text/javascript" src="${rc.getContextPath()}/ueditor/ueditor.all.js"></script>
     -->
</body>
<script type="text/javascript">

//var ue = UE.getEditor('container');
$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("添加成功");
	   location.href="${rc.getContextPath()}/showTopic/list";
	 }else if(msg!=""){
	   alert(msg);
	 }
});

	function add(){
	    var beginTime=$("#beginTime").val();
	    var endTime=$("#endTime").val();
	    var pic=$("#topic_pic_f").val();
	    var title=$("#title").val();
	    var content=$("#content").val();
	    if(beginTime==""||endTime==""||pic==""||title==""||content==""){
	      alert("请输入完整数据");
	      return ;
	    }
	    	   
	   	if(pic.toLowerCase().indexOf(".jpg")<0){
	   		alert("请上传.jpg类型的图片！");
	   		return false;
	    }
	    
	   if(!compareDate(beginTime,endTime)){
	       alert("开始时间不能大于结束时间");
	       return ;
	   }
	    if(title.length>30){
	    	alert("标题长度不能大于30个字符");
	       return ;
	    }
	    if(content.length>1000){
	    	alert("内容长度不能大于1000个字符");
	       return ;
	    }
	    var remark=$("#remark").val();
	    if(remark.length>300){
	    	alert("备注长度不能大于300个字符");
	       return ;
	    }

	   
	   $("#addFocusForm").submit();
	}
</script>
</html>