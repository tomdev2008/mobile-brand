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
			<h3 class="topTitle fb f14">秀用户管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/userManager/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客后台管理</dd>
				<dd class="last"><h3>秀用户管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
	            <table>
					<tr>
						<td>
							<table>
				        	  	<tr><td valign="top">用户ID：</td>
				        	       <td><textarea rows="3" cols="25" name="userId" id="userId">${(userId!'')?html}</textarea></td>
							    </tr>
						   </table> 
						</td>
						<td>
							<table>
								<tr>
									<td>
										<label>&nbsp;昵称：
								        	<input name="petName" type="text" id="petName" value="${(petName!'')?html}" size="24" />
								        </label>
								        &nbsp;加入秀客时间：
										<input name="beginTime" id="beginTime" type="text"  value="<#if beginTime??>${beginTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					      				- <input name="endTime" id="endTime" type="text"  value="<#if endTime??>${endTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					      				<label>&nbsp;是否达人：
									        <select name="talent" id="talent">
										        <option value="" <#if !talent??> selected="selected" </#if> >全部</option>
										        <option value="1" <#if talent?? && talent=='1'> selected="selected" </#if> >是</option>
												<option value="0" <#if talent?? && talent=='0'> selected="selected" </#if> >否</option>
									        </select> 
								        </label>
									</td>
								</tr>
								<tr>
									<td>
										<label>&nbsp;性别：
									        <select name="sex" id="sex">
										        <option value="" <#if !sex??> selected="selected" </#if> >全部</option>
										        <option value="sex_01" <#if sex?? && sex=='sex_01'> selected="selected" </#if> >男</option>
												<option value="sex_02" <#if sex?? && sex=='sex_02'> selected="selected" </#if> >女</option>
									        </select> 
								        </label>
								        <label>&nbsp;是否已封号：
									        <select name="status" id="status">
										        <option value="" <#if !status??> selected="selected" </#if> >全部</option>
										        <option value="2" <#if status?? && status=='2'> selected="selected" </#if> >是</option>
												<option value="1" <#if status?? && status=='1'> selected="selected" </#if> >否</option>
										    </select> 
									    </label>
								        <label>&nbsp;评论权限：
									        <select name="publishComment" id="publishComment">
										        <option value="" <#if !publishComment??> selected="selected" </#if> >全部</option>
										        <option value="1"  <#if publishComment?? && publishComment=='1'> selected="selected" </#if> >允许</option>
												<option value="-1" <#if publishComment?? && publishComment=='-1'> selected="selected" </#if> >禁止</option>
									        </select> 
								        </label>
								        <label>&nbsp;发布秀权限：
									        <select name="publishShow" id="publishShow">
										        <option value="" <#if !publishShow??> selected="selected" </#if> >全部</option>
										        <option value="1"  <#if publishShow?? && publishShow=='1'> selected="selected" </#if> >允许 | 自动审核通过</option>
												<option value="0"  <#if publishShow?? && publishShow=='0'> selected="selected" </#if> >允许 | 不自动审核通过</option>
												<option value="-1" <#if publishShow?? && publishShow=='-1'> selected="selected" </#if> >禁止</option>
									        </select> 
								        </label>
					      				<label>&nbsp;
									    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return submitSeachForm()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
									 	</label>
									</td>
								</tr>
							</table>
						</td>
					<tr>
				</table>
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<input type="hidden" name="popularityOrder" class="orderInput" id="popularityOrder"  value="${popularityOrder!''}" />
		<input type="hidden" name="showNumOrder" class="orderInput" id="showNumOrder"  value="${showNumOrder!''}" />
		<input type="hidden" name="recommendShowNumOrder" class="orderInput" id="recommendShowNumOrder"  value="${recommendShowNumOrder!''}" />
		<input type="hidden" name="followNumOrder" class="orderInput" id="followNumOrder"  value="${followNumOrder!''}" />
		<input type="hidden" name="fansNumOrder" class="orderInput" id="fansNumOrder"  value="${fansNumOrder!''}" />
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="4%"><input type="checkbox" id="SelectAll" name="SelectAll" value="" onclick="selectAll();"/>全选</th>
					<th width="5%">用户ID</th>
					<th width="6%">加入秀客时间</th>
					<th width="4%">头像</th>
					<th width="10%">昵称</th>
					<th width="3%">性别</th>
					<th width="4%">人气 <span class="" id="popularityOrderBut"></span></th>
					<th width="6%">发布秀数 <span class="" id="showNumOrderBut"></span></th>
					<th width="7%">被推荐秀数 <span class="" id="recommendShowNumOrderBut"></span></th>
					<th width="5%">关注数 <span class="" id="followNumOrderBut"></span></th>
					<th width="5%">粉丝数 <span class="" id="fansNumOrderBut"></span></th>
					<th width="7%">发布秀权限</th>
					<th width="4%">评论权限</th>
					<th width="5%">是否已封号</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (showUserList?? && showUserList?size > 0)>
			<#list showUserList as showUser>
			<tr>
				<td>
			        <input type="checkbox" name="checkboxId" value="${showUser.userId!''}" <#if showUser.status?? && showUser.status == 2> disabled="disabled" </#if> />
			    </td>
			    <td><a href="javascript:void(0);" onclick="viewUser('${showUser.userId}');" title="用户ID">${showUser.userId!''}</a></td>
			    <td><#if showUser.createDate??>${showUser.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
			    <td>
			    	<div style="position: relative;">
						<#if showUser.headPortrait??>
							<img class="smallImg" style="width:40px;height:40px;" src="http://6.xiustatic.com/user_headphoto${showUser.headPortrait!''}"  /> 
							<div class="showBigImg" style="position: absolute; left: 65px;top: -40px; display:none;">
								<img style="width:120px;height:120px;" src="http://6.xiustatic.com/user_headphoto${showUser.headPortrait!''}" />
							</div>
						</#if>
					</div>
			    </td>
			    <td style="word-break:break-all;">${showUser.petName!''}</td>
			    <#if showUser.sex?? && showUser.sex == 'sex_01'>
					<td>男</td>
				<#elseif showUser.sex?? && showUser.sex=='sex_02'>
					<td>女</td>
				<#else>
					<td></td>
				</#if>
			    <td>${showUser.popularity!''}</td>
			    <td>${showUser.showNum!''}</td>
			    <td>${showUser.recommendShowNum!''}</td>
			    <td>${showUser.followNum!''}</td>
			    <td>${showUser.fansNum!''}</td>
			    <#if showUser.publishShowFlag?? && showUser.publishShowFlag == 1>
					<td>允许 |<br />自动审核通过</td>
				<#elseif showUser.publishShowFlag?? && showUser.publishShowFlag==0>
					<td>允许 |<br />不自动审核通过</td>
				<#elseif showUser.publishShowFlag?? && showUser.publishShowFlag==-1>
					<td>禁止</td>
				<#else>
					<td></td>
				</#if>
				<#if showUser.publishCommentFlag?? && showUser.publishCommentFlag == 1>
					<td>允许</td>
				<#elseif showUser.publishCommentFlag?? && showUser.publishCommentFlag==-1>
					<td>禁止</td>
				<#else>
					<td></td>
				</#if>
				<#if showUser.status?? && showUser.status == 1>
					<td>否</td>
				<#elseif showUser.status?? && showUser.status==2>
					<td>是</td>
				<#else>
					<td></td>
				</#if>
				<td>
					<a href="javascript:void(0);" onclick="viewUser('${showUser.userId}');" title="详情">详情</a> 
					<#if showUser.status?? && showUser.status == 1> |
						<#if showUser.publishShowFlag?? && showUser.publishShowFlag == 1>
							<a href="javascript:void(0);" onclick="showDiv('forbidPublishShowDiv${showUser_index}','禁止发布秀时间：',350,200)" title="禁止发布秀">禁止发布秀</a> | 
						<#elseif showUser.publishShowFlag?? && showUser.publishShowFlag == 0>
							<a href="javascript:void(0);" onclick="showDiv('forbidPublishShowDiv${showUser_index}','禁止发布秀时间：',350,200)" title="禁止发布秀">禁止发布秀</a> |
							<a href="javascript:void(0);" onclick="showDiv('allowPublishShowDiv${showUser_index}','确定允许发布秀吗？',350,200)" title="允许发布秀">允许发布秀</a> |
						<#else>
							<a href="javascript:void(0);" onclick="showDiv('allowPublishShowDiv${showUser_index}','确定允许发布秀吗？',350,200)" title="允许发布秀">允许发布秀</a> | 
						</#if>
						<#if showUser.publishCommentFlag?? && showUser.publishCommentFlag == 1>
							<a href="javascript:void(0);" onclick="showDiv('forbidCommentDiv${showUser_index}','禁止发布评论时间：',350,200)" title="禁止评论">禁止评论</a> |
						<#else>
							<a href="javascript:void(0);" onclick="showDiv('allowCommentDiv${showUser_index}','确定允许发布评论吗？',350,200)" title="允许评论">允许评论</a> |
						</#if>
						<a href="javascript:void(0);" title="删除所有秀" <#if showUser.hasShowFlag?? && showUser.hasShowFlag == 1> 
							onclick="showDiv('deleteAllShowDiv${showUser_index}','确定删除所有秀吗？',350,200)" <#else> style="color:gray;"</#if> >删除所有秀</a> |
						<a href="javascript:void(0);" title="删除所有评论" <#if showUser.hasCommentFlag?? && showUser.hasCommentFlag == 1>
							onclick="showDiv('deleteAllCommentDiv${showUser_index}','确定删除所有评论吗？',350,200)" <#else> style="color:gray;" </#if> >删除所有评论</a> |
						<#if showUser.talentFlag?? && showUser.talentFlag == 0>
							<a href="javascript:void(0);" onclick="showDiv('grantTalentDiv${showUser_index}','确定设置达人吗？',350,200)" style="display:none;" title="设置达人">设置达人 | </a>
						<#else>
							<a href="javascript:void(0);" onclick="showDiv('revokeTalentDiv${showUser_index}','确定取消达人吗？',350,200)" style="display:none;" title="取消达人">取消达人 | </a>
						</#if>
						<#if showUser.status?? && showUser.status == 1>
							<a href="javascript:void(0);" onclick="showDiv('frozenDiv${showUser_index}','确定封号吗？',350,200)" title="封号">封号</a>
						<#else>
							<a href="javascript:void(0);" onclick="showDiv('unFrozenDiv${showUser_index}','确定解除封号吗？',350,200)" title="解除封号" style="display:none;">解除封号</a>
						</#if>
					</#if> 
					
					<div id="forbidPublishShowDiv${showUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-20px;">
							<p>
								<input type="text" id="forbidPublishShowBeginTime${showUser_index}" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
								— <input type="text" id="forbidPublishShowEndTime${showUser_index}" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
								<br /><br /><br />
								<input type="button" style="margin-left:180px;" class="userOperBtn operBtn" value="提交" onclick="forbidPublishShow(${showUser.userId!''},${showUser_index})" />
							<p>
						</div>
					</div>
					<div id="forbidCommentDiv${showUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-20px;">
							<p>
								<input type="text" id="forbidCommentBeginTime${showUser_index}" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
								— <input type="text" id="forbidCommentEndTime${showUser_index}" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
								<br /><br /><br />
								<input type="button" style="margin-left:180px;" class="userOperBtn operBtn" value="提交" onclick="forbidComment(${showUser.userId!''},${showUser_index})" />
							<p>
						</div>
					</div>
					<div id="allowPublishShowDiv${showUser_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="allowPublishShow(${showUser.userId!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="allowCommentDiv${showUser_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="allowPublishComment(${showUser.userId!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="deleteAllShowDiv${showUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-20px;">
							<p>
								删除原因：
								<select id="deleteShowReason${showUser_index}" style="width:140px;">
							        <option value="营销广告">营销广告</option>
									<option value="淫秽色情">淫秽色情</option>
									<option value="虚假信息">虚假信息</option>
									<option value="政治敏感">政治敏感</option>
									<option value="其他">其他</option>
						        </select>
						        <br /><br /><br />
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="deleteAllShow(${showUser.userId!''},${showUser_index})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="deleteAllCommentDiv${showUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-20px;">
							<p>
								删除原因：
								<select id="deleteCommentReason${showUser_index}" style="width:140px;">
							        <option value="营销广告">营销广告</option>
									<option value="淫秽色情">淫秽色情</option>
									<option value="虚假信息">虚假信息</option>
									<option value="政治敏感">政治敏感</option>
									<option value="其他">其他</option>
						        </select>
						        <br /><br /><br />
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="deleteAllComment(${showUser.userId!''},${showUser_index})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="grantTalentDiv${showUser_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="grantTalent(${showUser.userId!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="revokeTalentDiv${showUser_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="revokeTalent(${showUser.userId!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="frozenDiv${showUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-20px;">
							<p>
								封号原因：
								<select id="frozenReason${showUser_index}" style="width:140px;">
							        <option value="营销广告">营销广告</option>
									<option value="淫秽色情">淫秽色情</option>
									<option value="虚假信息">虚假信息</option>
									<option value="政治敏感">政治敏感</option>
									<option value="其他">其他</option>
						        </select>
						        <br /><br /><br />
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="frozen(${showUser.userId!''},${showUser_index})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
					<div id="unFrozenDiv${showUser_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="unFrozen(${showUser.userId!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="15"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<!-- 批量操作文件 -->
<#include "/pages/show/userManager/listBatchOperate.ftl">
<!-- 分页文件-->
<#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">
   
	// 显示大图
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})

   $(function(){ 
           initOrderImg("popularityOrderBut","popularityOrder");
           initOrderImg("showNumOrderBut","showNumOrder");
           initOrderImg("recommendShowNumOrderBut","recommendShowNumOrder");
           initOrderImg("followNumOrderBut","followNumOrder");
           initOrderImg("fansNumOrderBut","fansNumOrder");
	}); 
   	

	//清除用户ID提示信息
	function clearText() {		 
		$("#userIdTip").text("");
	}
	
	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	//详情
	function viewUser(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		//window.open(pageUrl);
		location.href = pageUrl;
	}
	
	// 显示浮层
	function showDiv(id,title,width,height) {
		var arg = {
			id:id,
			title:title,
			width:width,
			height:height
		}
		showPanel(arg);
	}
	
	// 刷新页面
	function refresh(){
		showPanelClose();
		var userId = $("#userId").val();
		var petName = $("#petName").val();
		var sex = $("#sex").val();
		var publishShow = $("#publishShow").val();
		var publishComment = $("#publishComment").val();
		var status = $("#status").val();
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var talent = $("#talent").val();
		var params = "userId="+userId+"&petName="+petName+"&sex="+sex+"&publishShow="+publishShow+"&publishComment="+publishComment
					+"&status="+status+"&beginTime="+beginTime+"&endTime="+endTime+"&talent="+talent;
		var pageUrl = "${rc.getContextPath()}/userManager/list?"+params;
		location.href = pageUrl;
	}
	
	<!-- 业务方法 Begin -->
	//禁止发布秀
	function forbidPublishShow(userId,index) {
		var beginTime = $("#forbidPublishShowBeginTime"+index).val();
		var endTime = $("#forbidPublishShowEndTime"+index).val();
		if(beginTime == null || beginTime == '') {
			alert("禁止发布秀的开始时间不能为空！");
			return ;
		}
		if(endTime == null || endTime == '') {
			alert("禁止发布秀的结束时间不能为空！");
			return ;
		}
		
		var beginTime_val = new Date(beginTime.replace(/-/g,"/"));
	    var endTime_val = new Date(endTime.replace(/-/g,"/"));
	    if(beginTime_val > endTime_val){
	        alert("结束时间要大于等于开始时间！");
            return false;
	    }
	    var currendTime_val = new Date();
	    if(currendTime_val >= new Date(endTime_val.getTime() + 1 * 24 * 60 * 60 * 1000)){
	         alert("结束时间要大于当前时间！");
             return false;
	    } 
		
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/forbidPublishShow?userId="+userId +"&beginTime="+beginTime+"&endTime="+endTime+ "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//禁止评论
	function forbidComment(userId,index) {
		var beginTime = $("#forbidCommentBeginTime"+index).val();
		var endTime = $("#forbidCommentEndTime"+index).val();
		if(beginTime == null || beginTime == '') {
			alert("禁止发表评论的开始时间不能为空！");
			return ;
		}
		if(endTime == null || endTime == '') {
			alert("禁止发表评论的结束时间不能为空！");
			return ;
		}
		
		var beginTime_val = new Date(beginTime.replace(/-/g,"/"));
	    var endTime_val = new Date(endTime.replace(/-/g,"/"));
	    if(beginTime_val > endTime_val){
	        alert("结束时间要大于等于开始时间！");
            return false;
	    }
	    var currendTime_val = new Date();
	    if(currendTime_val >= new Date(endTime_val.getTime() + 1 * 24 * 60 * 60 * 1000)){
	         alert("结束时间要大于当前时间！");
             return false;
	    } 
		
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/forbidComment?userId="+userId +"&beginTime="+beginTime+"&endTime="+endTime+ "&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//允许发布秀
	function allowPublishShow(userId){
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/allowPublishShow?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//允许发布评论
	function allowPublishComment(userId) {
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/allowComment?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//删除该用户所有的秀
	function deleteAllShow(userId,index) {
		 var reason = $("#deleteShowReason"+index).val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/deleteAllShow?userId="+userId+"&reason="+reason+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//删除该用户所有的评论 
	function deleteAllComment(userId,index) {
		 var reason = $("#deleteCommentReason"+index).val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/deleteAllComment?userId="+userId+"&reason="+reason+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//设置达人
	function grantTalent(userId){
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/grantUserTalent?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//取消达人
	function revokeTalent(userId){
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/revokeUserTalent?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//封号
	function frozen(userId,index) {
		 var reason = $("#frozenReason"+index).val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/frozenUser?userId="+userId+"&reason="+reason+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//解封帐号
	function unFrozen(userId) {
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/unFrozenUser?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	<!-- 业务方法 End -->
	
</script>
</body>
</html>