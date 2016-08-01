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

<form id="saveMenuForm" name="saveMenuForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/messageManager/save">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>走秀后台管理</dd>
			<dd><a href="${rc.getContextPath()}/messageManager/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">系统消息管理</a></dd>
			<dd class="last"><h3>发布系统消息</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">发布系统消息</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>接收用户：</th>
				<td class="tdBox">
					<table>
		        	  	<tr>
		        	  		<td valign="top">
		        	  			<input type="radio" name="receiveType" id="type_01" value="1" checked="true" onclick="changeReceiverUser(1);"/><label>All</label>&nbsp;
								<input type="radio" name="receiveType" id="type_02" value="2" onclick="changeReceiverUser(2);" /><label>人气值 > <input name="receiver" id="popularity" type="text" style="width:50px;" disabled="disabled" /></label>&nbsp;
								<input type="radio" name="receiveType" id="type_03" value="3" onclick="changeReceiverUser(3);" /><label>用户ID&nbsp;</label>
							</td>
		        	    	<td><textarea rows="3" cols="25" name="receiver" id="userId" disabled="disabled"></textarea></td>
					    </tr>
				    </table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>标题：</th>
				<td class="tdBox"> <input type="text" name="title" id="title" maxlength="100"/></td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>内容：</th>
				<td class="tdBox"><textarea id="content" name="content" rows="5" cols="60"></textarea></td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>发送时间：</th>
				<td class="tdBox">
					<input type="radio" name="send_time" id="type_12" value="1" checked="true" onclick="changeSendTime(1);" /><label><input name="sendTime" id="sendTime" type="text" maxlength="11" onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></label>
					<input type="radio" name="send_time" id="type_11" value="2" onclick="changeSendTime(2);" /><label>立即</label>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>绑定链接：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_03" value="1" onclick="changeLinkType(1);"/><label>Url</label></td>
							<td style="padding-top:5px;">&nbsp;Url地址:</td>
							<td style="padding-top:5px;"><input type="text" id="urlObject" name="linkObject" disabled="disabled" /></td>
						</tr>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_01" value="2"  onclick="changeLinkType(2);"/><label>话题</label></td>
							<td style="padding-top:5px;">&nbsp;话题ID:</td>
							<td style="padding-top:5px;"><input type="text" id="topicObject" name="linkObject" disabled="disabled" /></td>
						</tr>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_02" value="3" onclick="changeLinkType(3);"/><label>秀</label></td>
							<td style="padding-top:5px;">&nbsp;秀ID:</td>
							<td style="padding-top:5px;"><input type="text" id="showObject" name="linkObject" disabled="disabled" /></td>
						</tr>
						<tr>
							<td style="width:40px; padding-top:5px;"><input type="radio" name="linkType" id="type_04" value="" checked="true" onclick="changeLinkType(4);"/><label>无</label></td>
							<td style="padding-top:5px;"></td>
							<td style="padding-top:5px;"></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">备注：</th>
				<td class="tdBox"><textarea id="remark" name="remark" rows="3" cols="60"></textarea></td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return go();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="javascript:history.go(-1);" class="ml10"> <img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
   //更改接收用户类型
   function changeReceiverUser(type) {
   		if(type == 1) {
   			//所有
			$("#popularity").attr("disabled",true);
			$("#userId").attr("disabled",true);
			$("#popularity").val("");
			$("#userId").val("");
		} else if (type == 2) {
			//人气值
			$("#popularity").attr("disabled",false);
			$("#userId").attr("disabled",true);
			$("#userId").val("");
		} else if (type == 3) {
			//用户ID
			$("#popularity").attr("disabled",true);
			$("#userId").attr("disabled",false);
			$("#popularity").val("");
		} 
   }
   
   //更新发送时间类型
   function changeSendTime(type) {
   		if(type == 1) {
   			//指定时间
			$("#sendTime").attr("disabled",false);
		} else if (type == 2) {
			//立即
			$("#sendTime").attr("disabled",true);
			$("#sendTime").val("");
		}
   }
   
   //更改绑定链接类型
   function changeLinkType(type) {
   		if(type == 1) {
   			//url
			$("#topicObject").attr("disabled",true);
			$("#showObject").attr("disabled",true);
			$("#urlObject").attr("disabled",false);
			$("#topicObject").val("");
			$("#showObject").val("");
		} else if (type == 2) {
			//话题
			$("#topicObject").attr("disabled",false);
			$("#showObject").attr("disabled",true);
			$("#urlObject").attr("disabled",true);
			$("#showObject").val("");
			$("#urlObject").val("");
		} else if (type == 3) {
			//秀
			$("#topicObject").attr("disabled",true);
			$("#showObject").attr("disabled",false);
			$("#urlObject").attr("disabled",true);
			$("#topicObject").val("");
			$("#urlObject").val("");
		} else if(type == 4) {
			//无
			$("#topicObject").attr("disabled",true);
			$("#showObject").attr("disabled",true);
			$("#urlObject").attr("disabled",true);
			$("#topicObject").val("");
			$("#showObject").val("");
			$("#urlObject").val("");
		}
   }
	
   //验证信息
    function go(){
      var title = $("#title").val();
      var content = $("#content").val();
      
      if(title == ""){
        alert("标题不能为空！");
        return false;
      }
      
      if(content == ""){
        alert("内容不能为空！");
        return false;
      }
      
      var reg = /^(\d+)+$/;	//验证规则
      
      var receiveType = $("input[name=receiveType]:checked").val();
      if(receiveType == 2) {
      	var popularity = $("#popularity").val();
      	if(popularity == "") {
      		alert("人气值不能为空!");
      		return false;
      	}
	 	var flag = reg.test(popularity);
	 	if(!flag) {
	 		alert("人气值只能输入数字！");
	 		return false;
	 	}
      }
      if(receiveType == 3) {
      	var userId = $("#userId").val();
      	if(userId == "") {
      		alert("用户ID不能为空!");
      		return false;
      	}
      }
      
      var send_time = $("input[name=send_time]:checked").val();
      if(send_time == 1) {
      	var sendTime = $("#sendTime").val();
      	if(sendTime == ""){
	    	alert("发送时间不能为空！");
	        return false;
	    }
      }
      
      var linkType = $("input[name=linkType]:checked").val();
      if(linkType != '') {
      	if(linkType == 2) {
	    	var topicObject = $("#topicObject").val();
      		//话题
      		if(topicObject == "") {
		    	alert("话题ID不能为空！");
		        return false;
	    	}
	    	var flag = reg.test(topicObject);
		 	if(!flag) {
		 		alert("话题ID只能输入数字！");
		 		return false;
		 	}
      	}
      	
      	if(linkType == 3) {
	    	var showObject = $("#showObject").val();
      		//秀
      		if(showObject == "") {
		    	alert("秀ID不能为空！");
		        return false;
	    	}
	    	var flag = reg.test(showObject);
		 	if(!flag) {
		 		alert("秀ID只能输入数字！");
		 		return false;
		 	}
      	}
      	
      	if(linkType == 1) {
      		//Url
      		var urlObject = $("#urlObject").val();
      		if(urlObject == "") {
		    	alert("Url地址不能为空！");
		        return false;
	    	}
	    	
	    	var urlStr = urlObject.match(/http:\/\/.+/); 
			if (urlStr == null){ 
				alert('你输入的URL无效'); 
				return false; 
			}
      	}
      	
      }
      
      if(linkType == 2) {
        var topicId = $("#topicObject").val();
      	$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/messageManager/checkTopicEffectivity?format=json",
			data : {"topicId":topicId},
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
						   var data = objs.data;
						   if(data.result) {
						   		submit();
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
      } else if(linkType == 3) {
      	var showId = $("#showObject").val();
      	$.ajax({
			type : "GET",
			url : "${rc.getContextPath()}/messageManager/checkShowEffectivity?format=json",
			data : {"showId":showId},
            dataType: "text",
			success : function(data, textStatus) {
			   if (isNaN(data)) {
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0")
						{
						   var data = objs.data;
						   if(data.result) {
						   		submit();
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
      } else {
      	 //提交信息 
      	submit();
      }
      
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#saveMenuForm").submit();   
   }
</script>
</html>