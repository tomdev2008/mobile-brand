<div>
	 <ul class="common_ul">
		<li class="showInfoNav"><input type="button" value="基本信息" onclick="viewInfo('${showId!''}')" class="userNavBtn" /></li>
		<li class="showPraiseListNav"><input type="button" value="赞" onclick="viewPraise('${showId!''}')" class="userNavBtn" /></li>
		<li class="showCommentListNav"><input type="button" value="评论" onclick="viewComment('${showId!''}')" class="userNavBtn" /></li>
		<li class="showReportListNav"><input type="button" value="举报" onclick="viewReport('${showId!''}')" class="userNavBtn" /></li>
		<li class="showLogsListNav"><input type="button" value="操作日志" onclick="viewLogs('${showId!''}')" class="userNavBtn" /></li>
	</ul>
</div>

<script type="text/javascript">
	//秀用户基本信息
	function viewInfo(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
		location.href = pageUrl;
	}
	
	//人气
	function viewComment(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showCommentList?showId="+showId;
		location.href = pageUrl;
	}
	
	//秀点赞
	function viewPraise(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showPraiseList?showId="+showId;
		location.href = pageUrl;
	}
	
	//秀举报
	function viewReport(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showReportList?showId="+showId;
		location.href = pageUrl;
	}
	
	//操作日志
	function viewLogs(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showLogsList?showId="+showId;
		location.href = pageUrl;
	}
	
	
	//导航栏选中   	
   $(function(){ 
       var thishref=location.href;
       var sign=thishref.substring(thishref.indexOf('/show/')+6,thishref.indexOf('?'));
           $("."+sign+"Nav").addClass("navBtnChoose");
	}); 
   	
	
</script>