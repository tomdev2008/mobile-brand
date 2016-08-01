<div>
	<ul class="common_ul">
		<li><input type="button" value="基本信息" onclick="viewUser(${userId!''})"
				<#if userPageType?? && userPageType == 'user'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="人气" onclick="viewUserPopularity(${userId!''})"
				<#if userPageType?? && userPageType == 'popularity'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="秀" onclick="viewUserShow(${userId!''})"
				<#if userPageType?? && userPageType == 'show'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="关注" onclick="viewUserFollow(${userId!''})" 
				<#if userPageType?? && userPageType == 'follow'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="粉丝" onclick="viewUserFans(${userId!''})"
				<#if userPageType?? && userPageType == 'fans'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="评论" onclick="viewUserComment(${userId!''})"
				<#if userPageType?? && userPageType == 'comment'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="举报" onclick="viewUserReport(${userId!''})"
				<#if userPageType?? && userPageType == 'report'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
		<li><input type="button" value="操作日志" onclick="viewUserOperateLog(${userId!''})"
				<#if userPageType?? && userPageType == 'operatelog'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
	</ul>
</div>

<script type="text/javascript">
	//秀用户基本信息
	function viewUser(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/userDetail?userId="+userId+"&userPageType=user";
		location.href = pageUrl;
	}
	
	//人气
	function viewUserPopularity(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserPopularity?userId="+userId+"&userPageType=popularity";
		location.href = pageUrl;
	}
	
	//秀
	function viewUserShow(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserShow?userId="+userId+"&userPageType=show";
		location.href = pageUrl;
	}
	
	//关注 
	function viewUserFollow(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserFollow?userId="+userId+"&userPageType=follow";
		location.href = pageUrl;
	}
	
	//粉丝
	function viewUserFans(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserFans?userId="+userId+"&userPageType=fans";
		location.href = pageUrl;
	}
	
	//评论
	function viewUserComment(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserComment?userId="+userId+"&userPageType=comment";
		location.href = pageUrl;
	}
	
	//举报 
	function viewUserReport(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserReport?userId="+userId+"&infoType=1"+"&userPageType=report";
		location.href = pageUrl;
	}
	
	//操作日志
	function viewUserOperateLog(userId) {
		var pageUrl = "${rc.getContextPath()}/userManager/viewUserOperateLog?userId="+userId+"&userPageType=operatelog";
		location.href = pageUrl;
	}
</script>