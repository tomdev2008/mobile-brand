<div style="margin-top:5px;">
	<ul class="common_ul">
		<li><input type="button" value="禁止发布秀" class="userOperBtn" onclick="showBatchDiv('forbidPublishShowDiv','禁止发布秀','禁止发布秀时间：',350,200)" /></li>
		<li><input type="button" value="禁止评论" class="userOperBtn" onclick="showBatchDiv('forbidCommentDiv','禁止评论','禁止发布评论时间：',350,200)" /></li>
		<li><input type="button" value="允许发布秀" class="userOperBtn" onclick="showBatchDiv('allowPublishShowDiv','允许发布秀','确定允许发布秀吗？',350,200)" /></li>
		<li><input type="button" value="允许评论" class="userOperBtn" onclick="showBatchDiv('allowCommentDiv','允许评论','确定允许发布评论吗？',350,200)" /></li>
		<li><input type="button" value="删除所有的秀" class="userOperBtn" onclick="showBatchDiv('deleteAllShowDiv','删除所有的秀','确定删除所有秀吗？',350,200)" /></li>
		<li><input type="button" value="删除所有的评论" class="userOperBtn" onclick="showBatchDiv('deleteAllCommentDiv','删除所有的评论','确定删除所有评论吗？',350,200)" /></li>
		<li><input type="button" value="封号" class="userOperBtn" onclick="showBatchDiv('frozenDiv','封号','确定封号吗？',350,200)" style="display:none;" /></li>
		<li><input type="button" value="解封帐号" class="userOperBtn" onclick="showBatchDiv('unFrozenDiv','解封帐号','确定解除封号吗？',350,200)" style="display:none;" /></li>
	</ul>
</div>
<div id="forbidPublishShowDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			<input type="text" id="forbidPublishShowBeginTime" name="forbidPublishShowBeginTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			— <input type="text" id="forbidPublishShowEndTime" name="forbidPublishShowEndTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			<br /><br /><br />
			<input type="button" style="margin-left:180px;" class="userOperBtn" value="提交" onclick="batchForbidPublishShow()" />
		<p>
	</div>
</div>
<div id="forbidCommentDiv" class="showbox">
	<div class="centerDiv" style="margin-top:-20px;">
		<p>
			<input type="text" id="forbidCommentBeginTime" name="forbidCommentBeginTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			— <input type="text" id="forbidCommentEndTime" name="forbidCommentEndTime" maxlength="11" style="width:120px;" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd'})" />
			<br /><br /><br />
			<input type="button" style="margin-left:180px;" class="userOperBtn" value="提交" onclick="batchForbidComment()" />
		<p>
	</div>
</div>
<div id="allowPublishShowDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="batchAllowPublishShow()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="allowCommentDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="batchAllowPublishComment()" />&nbsp;&nbsp;&nbsp;&nbsp;
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
			<input type="button" class="userOperBtn" value="确定" onclick="batchDeleteAllShow()" />&nbsp;&nbsp;&nbsp;&nbsp;
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
			<input type="button" class="userOperBtn" value="确定" onclick="batchDeleteAllComment()" />&nbsp;&nbsp;&nbsp;&nbsp;
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
			<input type="button" class="userOperBtn" value="确定" onclick="batchFrozen()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
<div id="unFrozenDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="batchUnFrozen()" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>

<script type="text/javascript">
	// 显示浮层
	function showBatchDiv(id,msg,title,width,height) {
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		if(ids == "" || $.trim(ids) == ""){
			alert("请选择要"+msg+"的用户！");
			return ;
	    }
	    
		var newTitle = "<span style='color:red;'>批量</span>" + title ;
		var arg = {
			id:id,
			title:title,
			width:width,
			height:height
		}
		showPanel(arg);
	}
	
	//批量禁止发布秀	
	function batchForbidPublishShow() {
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
	    
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
	    
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchForbidPublishShow?userId="+ids +"&beginTime="+beginTime+"&endTime="+endTime+ "&format=json",
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
	
	//批量禁止评论
	function batchForbidComment() {
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
	    
	    var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
	    $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchForbidComment?userId="+ids +"&beginTime="+beginTime+"&endTime="+endTime+ "&format=json",
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
	
	//批量允许发布秀
	function batchAllowPublishShow(){
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchAllowPublishShow?userId="+ids+"&format=json",
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
	
	//批量允许发布评论
	function batchAllowPublishComment() {
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchAllowComment?userId="+ids+"&format=json",
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
	
	//批量删除所有的秀
	function batchDeleteAllShow() {
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		 var reason = $("#deleteShowReason").val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchDeleteAllShow?userId="+ids+"&reason="+reason+"&format=json",
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
	
	//批量删除所有的评论
	function batchDeleteAllComment() {
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		 var reason = $("#deleteCommentReason").val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchDeleteAllComment?userId="+ids+"&reason="+reason+"&format=json",
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
	
	//批量封号
	function batchFrozen() {
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		 var reason = $("#frozenReason").val();
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchFrozenUser?userId="+ids+"&reason="+reason+"&format=json",
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
	
	//批量解封帐号
	function batchUnFrozen(userId) {
		var ids = "";
		$(":checkbox").each(function(){
			if($(this).attr("checked")){
				ids += $(this).val() + ",";
			}
		});
		
		 $.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/userManager/batchUnFrozenUser?userId="+ids+"&format=json",
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
	
</script>