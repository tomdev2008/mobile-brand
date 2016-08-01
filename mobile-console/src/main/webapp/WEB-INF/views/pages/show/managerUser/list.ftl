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

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/managerUser/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客后台管理</dd>
				<dd class="last"><h3>前台管理员管理</h3></dd>
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
			    	<a href="javascript:void(0);" title="授权新管理员" class="btn" onclick="addNewManagerUser()" style="width:80px;text-align:center;height:22px;">授权新管理员</a>
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
					<th width="6%">用户ID</th>
					<th width="10%">昵称</th>
					<th width="10%">帐号</th>
					<th width="7%">手机</th>
					<th width="10%">邮箱</th>
					<th width="5%">设备类型</th>
					<th width="10%">设备ID</th>
					<th width="5%">口令</th>
					<th width="10%">备注</th>
					<th width="11%">添加时间</th>
					<th width="16%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (managerUserList?? && managerUserList?size > 0)>
			<#list managerUserList as managerUser>
			<tr>
			    <td>${managerUser.userId!''}</td>
			    <td>${managerUser.petName!''}</td>
			    <td>${managerUser.account!''}</td>
			    <td>${managerUser.mobile!''}</td>
			    <td>${managerUser.email!''}</td>
			    <#if managerUser.terminal?? && managerUser.terminal == 3>
					<td>wap</td>
				<#elseif managerUser.terminal?? && managerUser.terminal==4>
					<td>android</td>
				<#elseif managerUser.terminal?? && managerUser.terminal==5>
					<td>iphone</td>
				<#elseif managerUser.terminal?? && managerUser.terminal==6>
					<td>ipad</td>
				<#else>
					<td></td>
				</#if>
			    <td>${managerUser.deviceId!''}</td>
			    <td>******</td>
			    <td>${managerUser.remark!''}</td>
			    <td><#if managerUser.createDate??>${managerUser.createDate?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
				<td>
					<a href="javascript:void(0);" onclick="showUpdatePassword('updatePasswordDiv${managerUser_index}')" title="修改口令">修改口令</a> | 
					<a href="javascript:void(0);" onclick="showRevokeAuthority('revokeAuthorityDiv${managerUser_index}')" title="解除授权">解除授权</a> |
					<a href="javascript:void(0);" onclick="showUpdateRemark('updateRemarkDiv${managerUser_index}')" title="修改备注">修改备注</a>
					<div id="updatePasswordDiv${managerUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-40px;">
							<p>
								输入新口令：<input type="password" id="password${managerUser_index}" name="password${managerUser_index}" /><br /><br />
								确认新口令：<input type="password" id="passwordConfirm${managerUser_index}" name="passwordConfirm${managerUser_index}" /><br /><br />
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="updatePassword(${managerUser.userId!''},'${managerUser.deviceId!''}',${managerUser_index})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()"/>
							</p>
						</div>
					</div>
					<div id="revokeAuthorityDiv${managerUser_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="revokeAuthority(${managerUser.userId!''},'${managerUser.deviceId!''}')" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()"/>
							</p>
						</div>
					</div>
					<div id="updateRemarkDiv${managerUser_index}" class="showbox">
						<div class="centerDiv" style="margin-top:-50px;">
							<p>
								<textarea id="remark${managerUser_index}" name="remark${managerUser_index}" rows="5" cols="20">${managerUser.remark!''}</textarea><br />
								<input type="button" class="userOperBtn operBtn" value="确定" onclick="updateRemark(${managerUser.userId!''},'${managerUser.deviceId!''}',${managerUser_index})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn operBtn" value="取消" onclick="showPanelClose()"/>
							</p>
						</div>
					</div>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="11"><font color="red">暂时没有查询到记录</font></td></tr>
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
	
	//授权新管理员
	function addNewManagerUser() {
		var pageUrl = "${rc.getContextPath()}/managerUser/recordList";
		location.href = pageUrl;
	}
	
	// 关闭浮层
	function closeDiv() {
		showPanelClose();
	}
	
	// 刷新页面
	function refresh(){
		showPanelClose();
		var userId = $("#userId").val();
		var account = $("#account").val();
		var petName = $("#petName").val();
		var mobile = $("#mobile").val();
		var email = $("#email").val();
		var pageUrl = "${rc.getContextPath()}/managerUser/list?userId="+userId+"&account="+account+"&petName="+petName+"&mobile="+mobile+"&email="+email;
		location.href = pageUrl;
	}
	
	//显示修改口令浮层
	function showUpdatePassword(div) {
		var arg = {
			id:div,
			title:"修改口令",
			height:200,
			width:300
		}
		showPanel(arg);
	}
	
	//显示移除授权浮层
	function showRevokeAuthority(div) {
		var arg = {
			id:div,
			title:"解除授权",
			height:200,
			width:300
		}
		showPanel(arg);
	}
	
	//显示修改备注浮层
	function showUpdateRemark(div) {
		var arg = {
			id:div,
			title:"修改备注",
			height:200,
			width:300
		}
		showPanel(arg);
	}

	//修改口令
	function updatePassword(userId,deviceId,index) {
		var password = $("#password"+index).val();
		var passwordConfirm = $("#passwordConfirm"+index).val();
		
		if(password == null || password == '' || passwordConfirm == null || passwordConfirm == '') {
			alert("新密码不能为空！");
			return ;
		}
		
		if(password != passwordConfirm) {
			alert("密码必须一致！");
			return ;
		}
		
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/managerUser/updatePassword?userId="+userId+"&deviceId="+deviceId+"&password="+password+"&format=json",
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
				    alert(objs.data);
				    if(objs.scode == 0) {
				    	closeDiv();
				    }
				}
			},
			error : function(data) {
				//showErrorMsg(true,data);
			}
		});
	}
	
	//解除授权
	function revokeAuthority(userId,deviceId) {
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/managerUser/revokeAuthority?userId="+userId+"&deviceId="+deviceId+"&format=json",
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
	
	//修改备注
	function updateRemark(userId,deviceId,index) {
		var remark = $("#remark"+index).val();
		
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/managerUser/updateRemark?userId="+userId+"&deviceId="+deviceId+"&remark="+remark+"&format=json",
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