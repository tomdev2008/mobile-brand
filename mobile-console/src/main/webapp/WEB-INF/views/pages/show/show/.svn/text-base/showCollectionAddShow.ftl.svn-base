<!DOCTYPE html >
<html>
<head>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page.css" />
<script type="text/javascript">
	//查询
   function query(){
   	  var act_id = $("#act_id").val();
      
    //批量查询卖场时，验证卖场ID格式：只能是数字和逗号
	 var str = "";
	 if(act_id != null && act_id != "") {
	 	str = act_id.replace(/\s/g, ""); //去掉所有空格
	 	str = str.replace(/\r/g,"");	//去掉回车换行
	 	if(str.indexOf(",,") > -1) {
 			alert("不能有连续的逗号！");
 			return ;
 		}
 		var temp = str.substr(0,1);
 		if(temp == ',') {
 			alert("第一个字符不能是逗号！");
 			return;
 		}
	 	var reg = /^(\d+,?)+$/;	//验证规则
	 	var flag = reg.test(str);
	 	if(!flag) {
	 		alert("秀ID只能输入数字和英文逗号！");
	 		return false;
	 	}
	 	$("#act_id").val(str);
	 }
	 
      $("#mainForm").submit();
   }
      
   
   function selectAll1(){
		if($("#SelectAll1").attr("checked")){
	         $("input[name='checkboxId1']").attr('checked',true);
	    }else {
	       $("input[name='checkboxId1']").attr('checked',false);
	    }
	}
	
	function selectAll2(){
		if($("#SelectAll2").attr("checked")){
	         $("input[name='checkboxId2']").attr('checked',true);
	    }else {
	       $("input[name='checkboxId2']").attr('checked',false);
	    }
	}

	
	//清除卖场ID提示信息
	function clearText() {
		$("#actIdTip").text("");
	}
	
</script>
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">秀集合管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/show/showCollectionAddShow" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<input type="hidden" name="showCollectionId" value="${showCollectionVO.showCollectionId!''}">
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>秀集合</dd>
			<dd><a href="${rc.getContextPath()}/show/showCollectionList?showCollectionId=${(showCollectionVO.showCollectionId!'')?html}">秀集合管理</a></dd>
			<dd class="last"><h3>秀集合：${(showCollectionVO.name!'')?html}</h3>
			</dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 卖场信息显示框 -->
		<div style="font-weight:bold;">
			已包含<span style="color:red;">${activityCounts}个</span>秀
		</div>								
		<!-- 卖场信息显示框 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<div class="adminContent clearfix">
			<table width="100%" class="table_bg01 table_hg01">
				<thead>
					<tr>
						<th width="5%"><input type="checkbox" id="SelectAll1" name="SelectAll1" value="" onclick="selectAll1();"/>全选</th>
					<th width="10%">ID</th>
					<th width="20%">图片</th>
					<th width="20%">文字</th>
					<th width="10%">商品标识</th>
					<th width="5%">状态</th>
					<th width="10%">排序值</th>
					<th width="10%">创建时间</th>
					<th width="10%">操作</th>
					</tr>
				</thead>
				<tbody>
				   <#if (showDetailList?size > 0)>
				   <#list showDetailList as topic>
				      <tr>
				      	<td><input type="checkbox" name="checkboxId1" value="${topic.detailId!''}" /></td>
						<td>${topic.showId!''}</td>
						<td>
							<div style="position: relative;">
						 <#if topic.originalPicUrl??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${topic.originalPicUrl!''}"  /> 
						  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${topic.originalPicUrl!''}" /></div>
						   </#if>
						   </div>
						</td>
						<td>
							<#if topic.contents?? && topic.contents?length gt 10>
								${topic.contents[0..10]} 
								<#else>
								 ${topic.contents!''}
							</#if>
						</td>
						<td>${topic.prdTag!''}</td>
						<td>
							 <#if topic.status?? && topic.status == 0>
			        		<a href="javascript:void(0);" onclick="update('${topic.showCollectionId!''}','${topic.detailId!''}',1)" title="隐藏">隐藏</a>
					        </#if>
							<#if topic.status?? && topic.status  ==1><font class='red'>
									<a href="javascript:void(0);" onclick="update('${topic.showCollectionId!''}','${topic.detailId!''}',0)" title="显示">显示</a>
							</font></#if>
						</td>
						<td><input type="text" name="seq" id="seq_${topic.detailId!''}" onblur="changeSeq('${topic.detailId!''}')" value="${topic.seq!''}" ></td>
						<td>${topic.showCreateTime!''}</td>
						
						<td>
						  <a href="javascript:void(0);" title="移出秀集合" onclick="deleteActivitySet('${topic.showCollectionId!''}','${topic.detailId}')">移出秀集合</a> |
						  <a  target="_black" href="http://m.xiu.com/show/showDetail.html?showId=${topic.showId!''}">预览</a>
   					</td>    
			 	</tr>	
				   </#list>
				   <#else>
				     <tr><td colspan="14"><font color="red">暂时没有查询到记录</font></td></tr>
				   </#if>					
				</tbody>
			</table>
		<div class="w mt20 clearfix">
			<p class="fl">
				  <a id="link" class="btn" onclick="empty('${showCollectionVO.showCollectionId!''}')">批量移出秀集合</a>
			</p>
		</div>
	</div>	
</div>
 
   <br />
	<!-- 添加二级卖场 -->
	<div class="adminUp_wrap">
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
        	<table>
        		<tr>
        			<td style="vertical-align:middle;">
        				<label>秀ID：</label>
        			</td>
        			<td>
        				<textarea rows="3" cols="25" name="act_id" id="act_id" style="position:relative;" onkeydown="clearText()">${(act_id!'')?html}</textarea>
        				<label for="act_id" id="actIdTip" style="position:absolute; left:85px; color:gray;"><#if act_id?? && act_id!=''><#else>多个秀ID之间用英文","逗号隔开</#if></label>&nbsp;
        			</td>
        			<td>
        				<label>发布人：</label><input name="showUserName" id="showUserName" value="${showUserName!''}" size="13"/>
        			</td>
        			<td>
        				<label>标签：</label></label><input name="labelName" id="labelName" value="${labelName!''}" size="13"/>
        			<td>
        				<label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
						 </label>
        			</td>
        		</tr>
        	</table>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="5%"><input type="checkbox" id="SelectAll2" name="SelectAll2" value="" onclick="selectAll2();"/>全选</th>
					<th width="10%">ID</th>
					<th width="15%">图片</th>
					<th width="15%">文字</th>
					<th width="10%">商品标签</th>
					<th width="10%">点击数</th>
					<th width="20%">创建时间</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
			    <#if (queryShowList?? && queryShowList?size > 0)>
				<#list queryShowList as topic>
			      <tr>
					<td><input type="checkbox" name="checkboxId2" value="${topic.showId!''}" /></td>
					<td>${topic.showId!''}</td>
						<td>
							<div style="position: relative;">
						 <#if topic.originalPicUrl??>    <img class="smallImg" style="width:50px;height:22px;" src="http://6.xiustatic.com${topic.originalPicUrl!''}"  /> 
						  <div class="showBigImg" style="position: absolute;left: 65px;top: -100px; display:none;"><img style="width:480px;height:219px;" src="http://6.xiustatic.com${topic.originalPicUrl!''}" /></div>
						   </#if>
						   </div>
						</td>
						<td><#if topic.contents?? && topic.contents?length gt 10>
								${topic.contents[0..10]} 
								<#else>
								 ${topic.contents!''}
							</#if>
						</td>
						<td>${topic.prdTag!''}</td>
						<td>${topic.browseNum!''}</td>
						<td>${topic.showCreateTime!''}</td>
						<td>
						  <a href="javascript:void(0);" title="移入秀集合" onclick="addActivitySet('${showCollectionVO.showCollectionId!''}','${topic.showId}')">移入秀集合</a> |
						  <a  target="_black" href="http://m.xiu.com/show/showDetail.html?showId=${topic.showId!''}">预览</a>
						</td>    
		 	</tr>	
		 		</#list>
			   <#else>
			     <tr><td colspan="15"><font color="red">暂时没有查询到记录</font></td></tr>
			   </#if>					
			</tbody>
		</table>
		<!-- 分页文件-->
  		 <#include "/common/page.ftl">  
		<div class="w mt20 clearfix">
			<p class="fl">
				  <a id="link" class="btn" onclick="addBatch('${showCollectionVO.showCollectionId!''}')">批量移入秀集合</a>
			</p>
		</div>
	</div>
</form>
</div>

</body>
<script type="text/javascript">
	// 显示大图
	
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})
	
	
	//修改秀
	function update(showCollectionId,detailId,status){
	 	$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/updateShowCollectionDetailStatus?detailId=" + detailId+"&status="+status+"&format=json&seq=",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+showCollectionId+"&act_id=";
	 						   location.href=url;
			            	}else{
			            	    alert(objs.data);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		    });
	}
	
	//修改排序
	function changeSeq(detailId){
		var seq=$("#seq_"+detailId).val();
		$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/updateShowCollectionDetailStatus?detailId=" + detailId+"&seq="+seq+"&format=json&status=",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+showCollectionId+"&act_id=";
	 						   location.href=url;
			            	}else{
			            	    alert(objs.data);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		    });
	}
	
	//删除秀关联
	function deleteActivitySet(showCollectionId,detailId){
		$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/deleteShowCollectionDetail?detailIds=" + detailId+"&format=json&showCollectionId="+showCollectionId,
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+showCollectionId+"&act_id=";
	 						   location.href=url;
			            	}else{
			            	    alert(objs.data);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		    });
	}
	
	//预览
	function detail(showId){
		location.href="http://m.xiu.com/show/showDetail.html?showId="+showId;
	}
	
	//批量移出秀关联
	function empty(showCollectionId){
		var count=0;
		var detailIds="";
		if(document.getElementsByName("checkboxId1")){
			var checkboxs = document.getElementsByName("checkboxId1");
			var check = true;
			
			if(typeof(checkboxs.length) == "undefined"){
				if(checkboxs.checked == true) count = 1;
			}else{
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked == true){
					 	count = 1;
					 	var arr = checkboxs[i].value;
					 	detailIds+=arr+"," ;
					}		
				}
			}
		}
		if(count==0){
			alert('请选取要移入卖场集的卖场!');
			return false;
		}
	
		$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/deleteShowCollectionDetail?showCollectionId=" + showCollectionId+"&format=json&detailIds="+detailIds,
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+showCollectionId+"&act_id=";
	 						   location.href=url;
			            	}else{
			            	    alert(objs.data);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		    });
	}
	
	//移入秀关联
	function addActivitySet(showCollectionId,showId){
		$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/addShowCollectionDetail?showIds=" + showId+"&format=json&showCollectionId="+showCollectionId,
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+showCollectionId+"&act_id=";
	 						   location.href=url;
			            	}else if(objs.scode == "2"){
			            	    alert("该"+objs.smsg+"秀已经添加过");
			            	}else{
			            		alert(objs.smsg);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		    });
		}
	//批量移入秀关联
	
	function addBatch(showCollectionId){
		var count=0;
		var detailIds="";
		if(document.getElementsByName("checkboxId2")){
			var checkboxs = document.getElementsByName("checkboxId2");
			var check = true;
			
			if(typeof(checkboxs.length) == "undefined"){
				if(checkboxs.checked == true) count = 1;
			}else{
				for(var i=0;i<checkboxs.length;i++){
					if(checkboxs[i].checked == true){
					 	count = 1;
					 	var arr = checkboxs[i].value;
					 	detailIds+=arr+"," ;
					}		
				}
			}
		}
		if(count==0){
			alert('请选取要移入秀集合的秀!');
			return false;
		}
	
		$.ajax({
				type : "POST",
				async: false,
				url : "${rc.getContextPath()}/show/addShowCollectionDetail?showCollectionId=" + showCollectionId+"&format=json&showIds="+detailIds,
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   var url="${rc.getContextPath()}/show/showCollectionAddShow?showCollectionId="+showCollectionId+"&act_id=";
	 						   location.href=url;
			            	}else if(objs.scode == "2"){
			            	    alert("该"+objs.smsg+"秀已经添加过");
			            	}else{
			            		alert(objs.smsg);
			            	}
						}
					}
				},
				error : function(data) {
		       	}
		    });
	}
</script>
</html>