<div>
	<ul class="common_ul">
		<#if showUser.publishShowFlag?? && showUser.publishShowFlag == 1>
			<li><input type="button" value="禁止发布秀" class="userOperBtn" onclick="showDiv('forbidPublishShowDiv','禁止发布秀时间：',350,200)" /></li>
		<#elseif showUser.publishShowFlag?? && showUser.publishShowFlag == 0>
			<li><input type="button" value="禁止发布秀" class="userOperBtn" onclick="showDiv('forbidPublishShowDiv','禁止发布秀时间：',350,200)" /></li>
			<li><input type="button" value="允许发布秀" class="userOperBtn" onclick="showDiv('allowPublishShowDiv','确定允许发布秀吗？',350,200)" /></li>
		<#else>
			<li><input type="button" value="允许发布秀" class="userOperBtn" onclick="showDiv('allowPublishShowDiv','确定允许发布秀吗？',350,200)" /></li>
		</#if>
		<li><input type="button" value="禁止评论" class="userOperBtn" onclick="showDiv('forbidCommentDiv','禁止发布评论时间：',350,200)"
				<#if showUser.publishCommentFlag?? && showUser.publishCommentFlag == 1><#else> style="display:none;" </#if> /></li>
		<li><input type="button" value="允许评论" class="userOperBtn" onclick="showDiv('allowCommentDiv','确定允许发布评论吗？',350,200)"
				<#if showUser.publishCommentFlag?? && showUser.publishCommentFlag == 1> style="display:none;" <#else></#if> /></li>
		<li><input type="button" value="删除所有的秀" class="userOperBtn" onclick="showDiv('deleteAllShowDiv','确定删除所有秀吗？',350,200)" /></li>
		<li><input type="button" value="删除所有的评论" class="userOperBtn" onclick="showDiv('deleteAllCommentDiv','确定删除所有评论吗？',350,200)" /></li>
		<#if showUser.talentFlag?? && showUser.talentFlag == 0>
			<li><input type="button" value="设置达人" class="userOperBtn" onclick="showDiv('grantTalentDiv','确定设置达人吗？',350,200)" /></li>
		<#else>
			<li><input type="button" value="取消达人" class="userOperBtn" onclick="showDiv('revokeTalentDiv','确定取消达人吗？',350,200)" /></li>
		</#if>
		<li><input type="button" value="封号" class="userOperBtn" onclick="showDiv('frozenDiv','确定封号吗？',350,200)"
				<#if showUser.status?? && showUser.status == 1><#else> style="display:none;" </#if> /></li>
		<li><input type="button" value="解封帐号" class="userOperBtn" onclick="showDiv('unFrozenDiv','确定解除封号吗？',350,200)"
				<#if showUser.status?? && showUser.status == 1> style="display:none;" <#else> style="display:none;" </#if> /></li>
		<li>&nbsp;&nbsp;<input type="button" value="返回" class="userOperBtn" onclick="returnToList()" /></li>
	</ul>
</div>
<div id="zhezhao"></div>
<div id="forbidPublishShowDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			<input type="text" id="forbidPublishShowBeginTime" name="forbidPublishShowBeginTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			— <input type="text" id="forbidPublishShowEndTime" name="forbidPublishShowEndTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			<br /><br /><br />
			<input type="button" style="margin-left:180px;" class="userOperBtn" value="提交" onclick="forbidPublishShow()" />
		<p>
	</div>
</div>
<div id="forbidCommentDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			<input type="text" id="forbidCommentBeginTime" name="forbidCommentBeginTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			— <input type="text" id="forbidCommentEndTime" name="forbidCommentEndTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			<br /><br /><br />
			<input type="button" style="margin-left:180px;" class="userOperBtn" value="提交" onclick="forbidComment()" />
		<p>
	</div>
</div>
<div id="allowPublishShowDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="allowPublishShow()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="allowCommentDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="allowPublishComment()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="deleteAllShowDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			删除原因：
			<select name="deleteShowReason" id="deleteShowReason" style="width:140px;">
		        <option value="营销广告">营销广告</option>
				<option value="淫秽色情">淫秽色情</option>
				<option value="虚假信息">虚假信息</option>
				<option value="政治敏感">政治敏感</option>
				<option value="其他">其他</option>
	        </select>
	        <br /><br /><br />
			<input type="button" class="userOperBtn" value="确定" onclick="deleteAllShow()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="deleteAllCommentDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			删除原因：
			<select name="deleteCommentReason" id="deleteCommentReason" style="width:140px;">
		        <option value="营销广告">营销广告</option>
				<option value="淫秽色情">淫秽色情</option>
				<option value="虚假信息">虚假信息</option>
				<option value="政治敏感">政治敏感</option>
				<option value="其他">其他</option>
	        </select>
	        <br /><br /><br />
			<input type="button" class="userOperBtn" value="确定" onclick="deleteAllComment()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="grantTalentDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="grantTalent()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="revokeTalentDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="revokeTalent()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="frozenDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			封号原因：
			<select name="frozenReason" id="frozenReason" style="width:140px;">
		        <option value="营销广告">营销广告</option>
				<option value="淫秽色情">淫秽色情</option>
				<option value="虚假信息">虚假信息</option>
				<option value="政治敏感">政治敏感</option>
				<option value="其他">其他</option>
	        </select>
	        <br /><br /><br />
			<input type="button" class="userOperBtn" value="确定" onclick="frozen()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="unFrozenDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="unFrozen()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<script type="text/javascript">
	<!-- 浮层显示Start -->
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
	<!-- 浮层显示End -->
	
	var userId = ${showUser.userId!''};
	
	// 刷新页面
	function refresh(currDiv){
		showPanelClose();
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		location.href = pageUrl;
	}
	
	//禁止发布秀
	function forbidPublishShow() {
		var beginTime = $("#forbidPublishShowBeginTime").val();
		var endTime = $("#forbidPublishShowEndTime").val();
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
				    	refresh('forbidPublishShowDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//禁止评论
	function forbidComment() {
		var beginTime = $("#forbidCommentBeginTime").val();
		var endTime = $("#forbidCommentEndTime").val();
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
				    	refresh('forbidCommentDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//允许发布秀
	function allowPublishShow(){
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/allowPublishShow?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('allowPublishShowDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//允许发布评论
	function allowPublishComment() {
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/allowComment?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('allowCommentDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//删除该用户所有的秀
	function deleteAllShow() {
		 var reason = $("#deleteShowReason").val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/deleteAllShow?userId="+userId+"&reason="+reason+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('deleteAllShowDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//删除该用户所有的评论 
	function deleteAllComment() {
		 var reason = $("#deleteCommentReason").val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/deleteAllComment?userId="+userId+"&reason="+reason+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('deleteAllCommentDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//设置达人
	function grantTalent(){
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/grantUserTalent?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('grantTalentDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//取消达人
	function revokeTalent(){
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/revokeUserTalent?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('revokeTalentDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//封号
	function frozen() {
		 var reason = $("#frozenReason").val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/frozenUser?userId="+userId+"&reason="+reason+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('frozenDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//解封帐号
	function unFrozen() {
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/unFrozenUser?userId="+userId+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	refresh('unFrozenDiv');
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	// 返回 秀用户列表页
	function returnToList() {
		var pageUrl = "${rc.getContextPath()}/userManager/list";
		location.href = pageUrl;
	}
</script>