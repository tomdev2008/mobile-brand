<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
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
			<h3 class="topTitle fb f14">编辑菜单</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="saveMenuForm" name="saveMenuForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/messageManager/info">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>走秀后台管理</dd>
			<dd><a href="${rc.getContextPath()}/messageManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">系统消息管理</a></dd>
			<dd class="last"><h3>系统消息</h3>
			</dd>
		</dl>
		<div>
			<ul class="common_ul">
				<li><input type="button" value="消息详情" onclick="viewMessage(${messageId!''})"
						<#if pageType?? && pageType == 'message'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
				<li><input type="button" value="操作日志" onclick="viewMessageOperateLog(${messageId!''})"
						<#if pageType?? && pageType == 'operatelog'> class="userNavBtn navBtnChoose" <#else> class="userNavBtn" </#if> /></li>
			</ul>
		</div>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">系统消息</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">系统消息ID：</th>
				<td class="tdBox">${message.id!''}</td>
			</tr>
			<tr>
				<th class="thList" scope="row">创建时间：</th>
				<td class="tdBox">${message.createDate?string('yyyy-MM-dd HH:mm:ss')}</td>
			</tr>
			<tr>
				<th class="thList" scope="row">创建人：</th>
				<td class="tdBox">${message.creatorUserName!''}</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>接收用户：</th>
				<td class="tdBox">
					<table>
		        	  	<tr>
		        	  		<td valign="top">
		        	  			<input type="radio" name="receiveType" id="type_01" value="1" <#if message.receiverType == 1>checked="true"</#if> disabled="disabled" /><label>All</label>&nbsp;
								<input type="radio" name="receiveType" id="type_02" value="2" <#if message.receiverType == 2>checked="true"</#if> disabled="disabled" /><label>人气值 > <input name="receiver" id="popularity" type="text" style="width:50px;" <#if message.receiverType == 2>value="${message.receiverObject!''}"</#if> readonly /></label>&nbsp;
								<input type="radio" name="receiveType" id="type_03" value="3" <#if message.receiverType == 3>checked="true"</#if> disabled="disabled" /><label>用户ID&nbsp;</label>
							</td>
		        	    	<td><textarea rows="3" cols="25" name="receiver" id="userId" readonly><#if message.receiverType == 3>${message.receiverObject!''}</#if></textarea></td>
					    </tr>
				    </table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>标题：</th>
				<td class="tdBox"> <input type="text" name="title" id="title" maxlength="100" value="${message.title!''}" /></td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>内容：</th>
				<td class="tdBox"><textarea id="content" name="content" rows="5" cols="60">${message.content!''}</textarea></td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>发送时间：</th>
				<td class="tdBox">
					<input name="sendTime" id="sendTime" type="text" maxlength="11" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${message.sendTime?string('yyyy-MM-dd HH:mm:ss')}" style="font-weight:bold;" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>绑定链接：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_03" value="1" <#if message.linkType?? && message.linkType == 1>checked="true"</#if> disabled="disabled" /><label>Url</label></td>
							<td style="padding-top:5px;">&nbsp;Url地址:</td>
							<td style="padding-top:5px;"><input type="text" id="urlObject" name="linkObject" readonly <#if message.linkType?? && message.linkType == 1> value="${message.linkObject!''}" </#if> /></td>
						</tr>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_01" value="2" <#if message.linkType?? && message.linkType == 2>checked="true"</#if> disabled="disabled" /><label>话题</label></td>
							<td style="padding-top:5px;">&nbsp;话题ID:</td>
							<td style="padding-top:5px;"><input type="text" id="topicObject" name="linkObject" readonly <#if message.linkType?? && message.linkType == 2> value="${message.linkObject!''}" </#if> /></td>
						</tr>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_02" value="3" <#if message.linkType?? && message.linkType == 3>checked="true"</#if> disabled="disabled" /><label>秀</label></td>
							<td style="padding-top:5px;">&nbsp;秀ID:</td>
							<td style="padding-top:5px;"><input type="text" id="showObject" name="linkObject" readonly <#if message.linkType?? && message.linkType == 3> value="${message.linkObject!''}" </#if> /></td>
						</tr>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_04" value="" <#if message.linkType??><#else>checked="true"</#if> disabled="disabled" /><label>无</label></td>
							<td style="padding-top:5px;"></td>
							<td style="padding-top:5px;"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">备注：</th>
				<td class="tdBox"><textarea id="remark" name="remark" rows="3" cols="60">${message.remark!''}</textarea></td>
			</tr>
			<tr>
				<th class="thList" scope="row">消息状态：</th>
				<td class="tdBox">
					<#if message.deleteFlag?? && message.deleteFlag == 0>
						正常
					<#elseif message.deleteFlag?? && message.deleteFlag == 1>
						<span style="color:red; ">用户删除</span>
					<#elseif message.deleteFlag?? && message.deleteFlag == 2>
						<span style="color:red; ">前台管理员删除</span>
					<#elseif message.deleteFlag?? && message.deleteFlag == 3>
						<span style="color:red; ">后台删除</span>
					<#else>
					</#if>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<div>
						<ul class="common_ul">
							<li><input type="button" value="删除消息" class="userOperBtn" <#if message.deleteFlag?? && message.deleteFlag == 0> onclick="showDeleteSysMsgDiv('deleteSysMsgDiv')" <#else> style="display:none;"</#if> /></li>
							<li><input type="button" value="返回" class="userOperBtn" onclick="returnToList()" /></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="zhezhao"></div>
<div id="deleteSysMsgDiv" class="showbox">
	<div class="centerDiv">
		<p>
			<input type="button" class="userOperBtn" value="确定" onclick="deleteSysMsg(${messageId!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
		</p>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
	
	var messageId = ${messageId!''};
	
	//系统消息详情
	function viewMessage(messageId) {
		var pageUrl = "${rc.getContextPath()}/messageManager/info?messageId="+messageId;
		location.href = pageUrl;
	}
	
	//系统消息操作日志
	function viewMessageOperateLog(messageId) {
		var pageUrl = "${rc.getContextPath()}/messageManager/operateInfo?messageId="+messageId;
		location.href = pageUrl;
	}
	
	//显示删除系统消息浮层
	function showDeleteSysMsgDiv(div) {
		var arg = {
			id:div,
			title:"确定要删除此系统消息吗？",
			height:200,
			width:300
		}
		showPanel(arg);
	}
	
	// 刷新页面
	function refresh(){
		showPanelClose();
		var pageUrl = "${rc.getContextPath()}/messageManager/info?messageId="+messageId;
		location.href = pageUrl;
	}
	
	//删除系统消息
	function deleteSysMsg(messageId) {
		$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/messageManager/deleteSysMsg?format=json",
			data : {"id":messageId},
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
						   var data = objs.data;
						   if(data.result) {
						   		refresh();
						   } else {
						   		alert(data.errorMsg);
						   }
						} else if(objs.scode == "-1") {
							alert(objs.data);
						}
					}
				}
			},
			error : function(data) {
				showErrorMsg(true,data);
			}
		});	
	}
	
	//返回系统消息列表
	function returnToList() {
		var pageUrl = "${rc.getContextPath()}/messageManager/list";
		location.href = pageUrl;
	}
    
</script>
</html>