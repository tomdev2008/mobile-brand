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
			<dd class="last"><h3>话题修改</h3>
			</dd>
		</dl>
	<ul class="common_ul">
		<li><input type="button" value="话题详情" onclick="" class="userNavBtn navBtnChoose" /></li>
		<li><input type="button" value="操作日志" onclick="viewLogs()" class="userNavBtn" /></li>
	</ul>
	</div>
	<!--导航end-->
<form id="updateFocusForm" name="updateFocusForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/showTopic/update">
	<!--菜单内容-->
	<div class="adminContent clearfix">
	 <input id="topicId" name="topicId" value="${topic.topicId!''}" type="hidden"/>
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">话题ID：</th>
				<td class="tdBox">
				  ${topic.topicId!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">发布时间：</th>
				<td class="tdBox">
				 ${topic.publishTime?string('yyyy-MM-dd HH:mm:ss')}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">发布人：</th>
				<td class="tdBox">
				   ${topic.publishUserName!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>话题标题：</th>
				<td class="tdBox">
				  <input id="title" name="title" value="${topic.title!''}" /> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>话题内容：</th>
				<td class="tdBox">
					 <textarea id="content"  name="content" style=" width: 350px; height: 150px;">${topic.content!''}</textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>话题图片：</th>
				<td class="tdBox">
				      <img src=" ${topic.imageUrl!''}" width="250px" /> <br/>
					  <input type="file" name="topic_pic_f" id="topic_pic_f"/> 格式：比例为16:9 的jpg类型图片，宽度在640px以上
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>开始时间：</th>
				<td class="tdBox">
				<input name="beginTime" type="text" id="beginTime" value="${topic.beginTime?string('yyyy-MM-dd HH:mm:ss')}"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>结束时间：</th>
				<td class="tdBox">
					<input name="endTime" type="text" id="endTime" value="${topic.endTime?string('yyyy-MM-dd HH:mm:ss')}"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">显示状态：</th>
				<td class="tdBox">
				<#if topic.status??&&topic.status==0>未开始
				<#elseif topic.status??&&topic.status==1>进行中
				<#elseif topic.status??&&topic.status==2>已过期
				</#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">话题状态：</th>
				<td class="tdBox">
					<#if topic.deleteFlag??&&topic.deleteFlag==0>正常
					<#elseif topic.deleteFlag??&&topic.deleteFlag==3>后台删除
					<#elseif topic.deleteFlag??&&topic.deleteFlag==2>前台管理员删除
					</#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">是否热门：</th>
				<td class="tdBox">
				                     普通: <input type="radio" name="isHot" value="0" <#if topic.isHot??&&topic.isHot == 0> checked="checked" </#if> />
				                     热门: <input type="radio" name="isHot" value="1" <#if topic.isHot??&&topic.isHot == 1> checked="checked" </#if> />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">参与人数：</th>
				<td class="tdBox">
                      ${topic.userNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">参与秀数：</th>
				<td class="tdBox">
                      ${topic.showNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">精选人数：</th>
				<td class="tdBox">
                      ${topic.selectionUserNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">精选秀数：</th>
				<td class="tdBox">
                      ${topic.selectionShowNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">点赞数：</th>
				<td class="tdBox">
                      ${topic.praisedNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论数：</th>
				<td class="tdBox">
                      ${topic.commentNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">分享数：</th>
				<td class="tdBox">
                      ${topic.shareNum!''}
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">备注：</th>
				<td class="tdBox">
                      <textarea id="remark" name="remark">${topic.remark!''}</textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
					<p class="fl">
				    <#if topic.deleteFlag??&&topic.deleteFlag == 0>
					<input type="button" onclick="update();" class="userOperBtn" value="确认修改"></input> 
					<input type="button" onclick="deletetopic('${topic.topicId!''}');" class="userOperBtn" value="删除"></input> 
					</#if>
					<input type="button" onclick="returnMenuList();" class="userOperBtn" value="返回"></input> 
					</p>
				</td>
			</tr>
		</table>
			
				
	</div>
</form>
</div>
</div>


<div id="deletetopicDiv" class="showbox">
 <input id="deletetopicId" type="hidden" />
		<h3 >确定删除此话题？</h3>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" type="button" onclick="deletetopicAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
</div>

<input type="hidden" value="${status!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>

    <!-- 配置文件 -->
    <!-- <script type="text/javascript" src="${rc.getContextPath()}/ueditor/ueditor.config.js"></script> -->
    <!-- 编辑器源码文件 -->
   <!-- <script type="text/javascript" src="${rc.getContextPath()}/ueditor/ueditor.all.js"></script> -->
</body>
<script type="text/javascript">

//	var ue = UE.getEditor('container');
	
//	ue.ready(function(){
//	    var content=$("#content").val();
//	    ue.setContent(content);    
//	})
	$(function(){
		 var status=$("#updateStatus").val();
		   var msg=$("#msg").val();
		 if(status==1){
		   alert("更新成功");
		   location.href="${rc.getContextPath()}/showTopic/list";
		 }else if(msg!=""){
		   alert(msg);
		 }
	});

	function update(){
	    var beginTime=$("#beginTime").val();
	    var endTime=$("#endTime").val();
	    var pic=$("#topic_pic_f").val();
	    var content=$("#content").val();
	    var title=$("#title").val();
	    if(title==""||beginTime==""||endTime==""||content==""){
	      alert("请输入完整数据");
	      return ;
	    }
	    	   
	   	if(pic!=""&&pic.toLowerCase().indexOf(".jpg")<0){
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
	   $("#updateFocusForm").submit();
	}
	
		//修改绑定
	function deletetopic(topicId) {
		var arg = {
			id:"deletetopicDiv",
			title:"删除确定",
			height:150,
			width:300
		}
		showPanel(arg);
		$("#deletetopicId").val(topicId);
	}	
	
	 function deletetopicAjax(){
	    var topicId=    $("#deletetopicId").val();
	      $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/showTopic/delete?topicId=" + topicId+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert(objs.smsg);
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
	 	
	//跳转日志
	function viewLogs(){
	  location.href = "${rc.getContextPath()}/operateLog/list?modelName=showTopic&type=5&objectId=${topic.topicId!''}&datas[]=";
	}
	 	
	//取消事件，返回菜单管理列表
	function returnMenuList(){
	  location.href = "${rc.getContextPath()}/showTopic/list";
	}
	
</script>
</html>