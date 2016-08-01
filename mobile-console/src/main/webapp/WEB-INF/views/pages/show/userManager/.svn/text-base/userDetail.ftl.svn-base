<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
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
			<h3 class="topTitle fb f14">用户详情</h3>
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
			<dd>秀客后台管理</dd>
			<dd><a href="${rc.getContextPath()}/userManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">秀用户管理</a></dd>
			<dd class="last"><h3>用户详情</h3></dd>
		</dl>
		<#include "/pages/show/userManager/userCommonNav.ftl">  
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th class="thList" scope="row">用户ID：</th>
				<td class="tdBox">${showUser.userId!''}</td>
			</tr>
			<tr>
				<th class="thList" scope="row">昵称：</th>
				<td class="tdBox">${showUser.petName!''}</td>
			</tr>
			<tr>
				<th class="thList" scope="row">头像：</th>
				<td class="tdBox">
					<div style="position: relative;">
						<#if showUser.headPortrait??>
							<img class="smallImg" style="width:40px;height:40px;" src="http://6.xiustatic.com/user_headphoto${showUser.headPortrait!''}"  /> 
							<div class="showBigImg" style="position: absolute; left: 65px;top: -40px; display:none;">
								<img style="width:120px;height:120px;" src="http://6.xiustatic.com/user_headphoto${showUser.headPortrait!''}" />
							</div>
						</#if>
					</div>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">性别：</th>
				<td class="tdBox">
					<#if showUser.sex?? && showUser.sex == 'sex_01'>男
					<#elseif showUser.sex?? && showUser.sex=='sex_02'> 女
					</#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">人气：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.popularity!''}</span></td>
			</tr>
			<tr>
				<th class="thList" scope="row">发布秀数：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.showNum!''}</span> 
								  （未审核:<#if showCheckInfo.notCheck??>${showCheckInfo.notCheck!''}<#else>0</#if>,
								  审核通过:<#if showCheckInfo.checkPass??>${showCheckInfo.checkPass!''}<#else>0</#if>,
								  审核不通过:<#if showCheckInfo.checkNotPass??>${showCheckInfo.checkNotPass!''}<#else>0</#if>;&nbsp;
								  用户删除:<#if showDeleteInfo.userDelete??>${showDeleteInfo.userDelete!''}<#else>0</#if>,
								  前台管理员删除:<#if showDeleteInfo.managerDelete??>${showDeleteInfo.managerDelete!''}<#else>0</#if>,
								  后台删除:<#if showDeleteInfo.systemManagerDelete??>${showDeleteInfo.systemManagerDelete!''}<#else>0</#if>）
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">被推荐秀数：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.recommendShowNum!''}</span></td>
			</tr>
			<tr>
				<th class="thList" scope="row">关注数：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.followNum!''}</span></td>
			</tr>
			<tr>
				<th class="thList" scope="row">粉丝数：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.fansNum!''}</span></td>
			</tr>
			<tr>
				<th class="thList" scope="row">发表评论数：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.commentNum!''}</span>
								  （用户删除：<#if commentDeleteInfo.userDelete??>${commentDeleteInfo.userDelete!''}<#else>0</#if>；
								  前台管理员删除： <#if commentDeleteInfo.managerDelete??>${commentDeleteInfo.managerDelete!''}<#else>0</#if>；
								  后台删除：<#if commentDeleteInfo.systemManagerDelete??>${commentDeleteInfo.systemManagerDelete!''}<#else>0</#if>）
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">被举报数：</th>
				<td class="tdBox"><span style="color:blue;">${showUser.praisedNum!''}</span>
								  （秀被举报：<#if reportInfo.showReport??>${reportInfo.showReport!''}<#else>0</#if>；
								  评论被举报：<#if reportInfo.commentReport??>${reportInfo.commentReport!''}<#else>0</#if>）
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">发布秀权限：</th>
				<td class="tdBox">
					<input type="radio" value="1" <#if showUser.publishShowFlag?? && showUser.publishShowFlag == 1>checked="checked"</#if> disabled="disabled" />允许(自动审核通过)&nbsp;
					<input type="radio" value="0" <#if showUser.publishShowFlag?? && showUser.publishShowFlag == 0>checked="checked"</#if> disabled="disabled" />允许(不自动审核通过)&nbsp;
					<input type="radio" value="-1" <#if showUser.publishShowFlag?? && showUser.publishShowFlag == -1>checked="checked"</#if> disabled="disabled" />禁止&nbsp;
					<input name="publishBeginTime" id="publishBeginTime" type="text" disabled="disabled" value="<#if showUser.publishBeginTime??>${showUser.publishBeginTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					- <input name="publishEndTime" id="publishEndTime" type="text" disabled="disabled" value="<#if showUser.publishEndTime??>${showUser.publishEndTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">评论权限：</th>
				<td class="tdBox">
					<input type="radio" value="1" <#if showUser.publishCommentFlag?? && showUser.publishCommentFlag == 1>checked="checked"</#if> disabled="disabled" />允许&nbsp;
					<input type="radio" value="-1" <#if showUser.publishCommentFlag?? && showUser.publishCommentFlag == -1>checked="checked"</#if> disabled="disabled" />禁止&nbsp;
					<input name="commentBeginTime" id="commentBeginTime" type="text" disabled="disabled" value="<#if showUser.commentBeginTime??>${showUser.commentBeginTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					- <input name="commentEndTime" id="commentEndTime" type="text" disabled="disabled" value="<#if showUser.commentEndTime??>${showUser.commentEndTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">是否已封号：</th>
				<td class="tdBox">
					<input type="radio" value="2" <#if showUser.status?? && showUser.status == 2>checked="checked"</#if> disabled="disabled" />是&nbsp;&nbsp;
					<input type="radio" value="1" <#if showUser.status?? && showUser.status == 1>checked="checked"</#if> disabled="disabled" />否&nbsp;
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">是否达人：</th>
				<td class="tdBox">
					<input type="radio" value="1" <#if showUser.talentFlag?? && showUser.talentFlag == 1>checked="checked"</#if> disabled="disabled" />是&nbsp;&nbsp;
					<input type="radio" value="0" <#if showUser.talentFlag?? && showUser.talentFlag == 0>checked="checked"</#if> disabled="disabled" />否&nbsp;
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<#if showUser.status?? && showUser.status == 1>
						<#include "/pages/show/userManager/userCommonOperate.ftl">
					<#else>
						<ul class="common_ul">
							<li><input type="button" value="返回" class="userOperBtn" onclick="returnToUserList()" /></li>
						</ul>
					</#if> 
				</td>
			</tr>
		</table>
	</div>
</div>
</div>
</body>
<script type="text/javascript">
	// 显示大图
	$('.smallImg').hover(function(){
		$(this).siblings('.showBigImg').show();
	},function(){
		$(this).siblings('.showBigImg').hide();
	})
	
    // 返回 秀用户列表页
	function returnToUserList() {
		var pageUrl = "${rc.getContextPath()}/userManager/list";
		location.href = pageUrl;
	}
</script>
</html>