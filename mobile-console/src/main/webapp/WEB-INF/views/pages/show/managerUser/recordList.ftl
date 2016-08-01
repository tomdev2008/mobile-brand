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
			<h3 class="topTitle fb f14">前台管理员管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/managerUser/recordList" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客后台管理</dd>
				<dd><a href="${rc.getContextPath()}/managerUser/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">前台管理员管理</a></dd>
				<dd class="last"><h3>授权新前台管理员</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
				<label>用户ID：
					<input name="userId" type="text" id="userId" value="${(userId!'')?html}" size="12" />
				</label>
				<label>&nbsp;帐号：
		        	<input name="account" type="text" id="account" value="${(account!'')?html}" size="12" />
		        </label>
		        <label>&nbsp;昵称：
		        	<input name="petName" type="text" id="petName" value="${(petName!'')?html}" size="12" />
		        </label>
		        <label>&nbsp;手机：
		        	<input name="mobile" type="text" id="mobile" value="${(mobile!'')?html}" size="12" />
		        </label>
		        <label>&nbsp;邮箱：
		        	<input name="email" type="text" id="email" value="${(email!'')?html}" size="12" />
		        </label>
		        <label>&nbsp;
			    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return query()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>&nbsp;
			 	</label>
			 	<label>
			    	<a href="javascript:void(0);" title="返回" class="btn" onclick="returnList()" style="width:50px;text-align:center;height:22px;" id="query">返&nbsp;&nbsp;回</a>&nbsp;
			 	</label>
			 	<br />
			 	<label>请求时间：
		        	<input name="beginTime" id="beginTime" type="text"  value="<#if beginTime??>${beginTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					- <input name="endTime" id="endTime" type="text"  value="<#if endTime??>${endTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		        </label>
			</div>
		</fieldset>
		<!-- 查询条件 end -->
	</div>
	<!-- 查询条件框 end -->

	<!-- 列表分页 -->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="5%">ID</th>
					<th width="7%">用户ID</th>
					<th width="11%">昵称</th>
					<th width="11%">帐号</th>
					<th width="8%">手机</th>
					<th width="11%">邮箱</th>
					<th width="6%">设备类型</th>
					<th width="22%">设备ID</th>
					<th width="12%">请求时间</th>
					<th width="7%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (recordList?? && recordList?size > 0)>
			<#list recordList as record>
			<tr>
			    <td>${record.id!''}</td>
			    <td>${record.userId!''}</td>
			    <td>${record.petName!''}</td>
			    <td>${record.account!''}</td>
			    <td>${record.mobile!''}</td>
			    <td>${record.email!''}</td>
			    <#if record.terminal?? && record.terminal == 3>
					<td>wap</td>
				<#elseif record.terminal?? && record.terminal==4>
					<td>android</td>
				<#elseif record.terminal?? && record.terminal==5>
					<td>iphone</td>
				<#elseif record.terminal?? && record.terminal==6>
					<td>ipad</td>
				<#else>
					<td></td>
				</#if>
			    <td>${record.deviceId!''}</td>
			    <td><#if record.createDate??>${record.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
				<td>
					<#if record.flag?? && record.flag == 0>
						<a href="javascript:void(0);" onclick="showAuthority('showAuthorityDiv${record_index}')" title="授权">授权</a>
					</#if>
					<div id="showAuthorityDiv${record_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="authority(${record.userId!''},'${record.deviceId!''}',${record.terminal!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()"/>
							</p>
						</div>
					</div>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="10"><font color="red">暂时没有查询到记录</font></td></tr>
		    </#if>
		   </tbody>
		</table>
</div>
<!-- 分页文件-->
  <#include "/common/page.ftl">  
</form>
</div>
<script type="text/javascript">
	//查询
	function query() {
	    $('#pageNo').val(1);
     	$("#mainForm").submit();
	}
	
	//返回
	function returnList() {
		var pageUrl = "${rc.getContextPath()}/managerUser/list";
		location.href = pageUrl;
	}
	
	// 刷新页面
	function refresh(){
		showPanelClose();
		var pageUrl = "${rc.getContextPath()}/managerUser/recordList";
		location.href = pageUrl;
	}
	
	//显示授权浮层
	function showAuthority(div) {
		var arg = {
			id:div,
			title:"确定要授权该用户为前台管理员吗？",
			height:200,
			width:300
		}
		showPanel(arg);
	}
	
	//修改口令
	function authority(userId,deviceId,terminal) {
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/managerUser/authority?userId="+userId+"&deviceId="+deviceId+"&terminal="+terminal+"&format=json",
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
</body>
</html>