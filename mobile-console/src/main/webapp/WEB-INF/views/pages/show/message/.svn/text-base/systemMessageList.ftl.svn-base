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
			<h3 class="topTitle fb f14">系统消息管理</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="mainForm" name="mainForm" action="${rc.getContextPath()}/messageManager/list" method="GET">
<div class="adminMain_wrap">
	<!-- 查询条件框 -->
	<div class="adminUp_wrap">
		<!-- 导航 -->
		<dl class="adminPath clearfix">
				<dt>您现在的位置：</dt>
				<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
				<dd>秀客后台管理</dd>
				<dd class="last"><h3>系统消息管理</h3></dd>
		</dl>
		<!-- 导航 end -->
		
		<!-- 查询条件 -->
		<fieldset class="clearfix adminSearch">
            <div class="wapbt" style="border-bottom:0px;" align="left">
				<table>
					<tr>
						<td>
							<table>
				        	  	<tr><td valign="top">消息ID：</td>
				        	       <td><textarea rows="3" cols="25" name="messageId" id="messageId">${(messageId!'')?html}</textarea></td>
							    </tr>
						    </table> 
						</td>
						<td style="padding-left:10px;">
							<table>
								<tr>
									<td>发布时间：
										<input name="beginTime" id="beginTime" type="text"  value="<#if beginTime??>${beginTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
					      				- <input name="endTime" id="endTime" type="text"  value="<#if endTime??>${endTime}</#if>" maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
								        <label>&nbsp;消息状态：
								        	<select name="messageStatus" id="messageStatus">
								            	<option value="" >全部</option>
								            	<option value="0" <#if messageStatus??&&messageStatus=='0'>selected="selected"</#if>>正常</option>
								            	<option value="1" <#if messageStatus??&&messageStatus=='1'>selected="selected"</#if>>用户删除</option>
								            	<option value="2" <#if messageStatus??&&messageStatus=='2'>selected="selected"</#if>>前台管理员删除</option>
								            	<option value="3" <#if messageStatus??&&messageStatus=='3'>selected="selected"</#if>>后台删除</option>
								        	</select>
								        </label>
								        <label>&nbsp;
									    	<a href="javascript:void(0);" title="查询" class="btn" onclick="return submitSeachForm()" style="width:50px;text-align:center;height:22px;" id="query">查&nbsp;&nbsp;询</a>
									    	<a href="javascript:void(0);" title="发布系统消息" class="btn" onclick="addSystemMsg()" style="width:80px;text-align:center;height:22px;">发布系统消息</a>
									 	</label>
									</td>
									<tr>
										<td>
											<label>发布人：&nbsp;
									        	<input name="sendName" type="text" id="sendName" value="${(sendName!'')?html}" style="width:120px;" />
									        </label>
										</td>
									</tr>
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
		<table width="100%" class="table_bg01 table_hg01">
			<thead>
				<tr>
					<th width="6%">消息ID</th>
					<th width="12%">发布人</th>
					<th width="12%">发送时间</th>
					<th width="6%">接收人数</th>
					<th width="6%">消息状态</th>
					<th width="24%">标题</th>
					<th width="6%">绑定链接</th>
					<th width="13%">绑定链接ID</th>
					<th width="17%">操作</th>
				</tr>
			</thead>
			<tbody>
			<#if (messageList?? && messageList?size > 0)>
			<#list messageList as message>
			<tr>
			    <td>${message.id!''}</td>
			    <td>${message.senderName!''}</td>
			    <td>${message.sendTime?string('yyyy-MM-dd HH:mm:ss')}</td>
			    <td>${message.receiverNum!''}</td>
			    <#if message.deleteFlag?? && message.deleteFlag == 0>
					<td>正常</td>
				<#elseif message.deleteFlag?? && message.deleteFlag == 1>
					<td>用户删除</td>
				<#elseif message.deleteFlag?? && message.deleteFlag == 2>
					<td>前台管理员删除</td>
				<#elseif message.deleteFlag?? && message.deleteFlag == 3>
					<td>后台删除</td>
				<#else>
					<td></td>
				</#if>
			    <td>${message.title!''}</td>
			    <#if message.linkType?? && message.linkType == 1>
					<td>Url地址</td>
				<#elseif message.linkType?? && message.linkType == 2>
					<td>话题</td>
				<#elseif message.linkType?? && message.linkType == 3>
					<td>秀</td>
				<#else>
					<td>无</td>
				</#if>
			    <#if message.linkType?? && message.linkType == 1>
					<td><a href="${message.linkObject!''}" target="_blank">${message.linkObject!''}</a></td>
				<#elseif message.linkType?? && message.linkType == 2>
					<td><a href="javascript:void(0);" onclick="viewTopic('${message.linkObject!''}');" title="话题ID">${message.linkObject!''}</a></td>
				<#elseif message.linkType?? && message.linkType == 3>
					<td><a href="javascript:void(0);" onclick="viewShow('${message.linkObject!''}');" title="秀ID">${message.linkObject!''}</a></td>
				<#else>
					<td></td>
				</#if>
				<td>
					<a href="javascript:void(0);" onclick="viewMessage(${message.id!''})" title="详情">详情</a> 
					<#if message.deleteFlag?? && message.deleteFlag == 0> | <a href="javascript:void(0);" onclick="showDeleteSysMsgDiv('showDeleteSysMsgDiv${message_index}')" title="删除">删除</a></#if>
					<div id="showDeleteSysMsgDiv${message_index}" class="showbox">
						<div class="centerDiv">
							<p>
								<input type="button" class="userOperBtn" value="确定" onclick="deleteSysMsg(${message.id!''})" />&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="button" class="userOperBtn" value="取消" onclick="showPanelClose()" />
							</p>
						</div>
					</div>
				</td>    
		    </#list>
		    <#else>
		    <tr><td colspan="9"><font color="red">暂时没有查询到记录</font></td></tr>
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
	
	//发布系统消息
	function addSystemMsg() {
		var pageUrl = "${rc.getContextPath()}/messageManager/create";
		location.href = pageUrl;
	}
	
	//秀详情
	function viewShow(showId) {
		var pageUrl = "${rc.getContextPath()}/show/showInfo?showId="+showId;
		window.open(pageUrl);
	}
	
	//话题详情
	function viewTopic(topicId) {
		var pageUrl = "${rc.getContextPath()}/showTopic/info?topicId="+topicId;
		window.open(pageUrl);
	}
	
	//系统消息详情
	function viewMessage(messageId) {
		var pageUrl = "${rc.getContextPath()}/messageManager/info?messageId="+messageId;
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
		var pageUrl = "${rc.getContextPath()}/messageManager/list";
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
	
</script>
</body>
</html>