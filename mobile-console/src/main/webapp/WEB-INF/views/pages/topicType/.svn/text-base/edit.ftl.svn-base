<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>

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

<form id="saveMenuForm" name="saveMenuForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/topicType/update">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>配置管理</dd>
			<dd><a href="${rc.getContextPath()}/topicType/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">卖场分类管理</a></dd>
			<dd class="last"><h3>编辑卖场分类</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑分类</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>分类ID：</th>
				<td class="tdBox">
					<input type="text" name="id" id="id" value="${topicType.id!''}" readonly="true" style="background-color:#f5f5f5;" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>分类名称：</th>
				<td class="tdBox">
					<input type="text" name="name" id="name" value="${topicType.name!''}" maxlength="100" 
						<#if topicType.id?? && topicType.id ==1> readonly style="background-color:#f5f5f5;" </#if>  
						<#if topicType.id?? && topicType.id ==1000> readonly style="background-color:#f5f5f5;" </#if> />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">排序（降序）：</th>
				<td class="tdBox">
	            	<input type="text" name="orderSeq" id="orderSeq" maxlength="4" value="${topicType.orderSeq!''}" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
					<input type="radio" name="status" id="statusType_01" value="1" <#if topicType.status?? && topicType.status ==1> checked="true" </#if> /><label>启用</label>
					<input type="radio" name="status" id="statusType_02" value="0" <#if topicType.status?? && topicType.status ==0> checked="true" </#if> /><label>停用</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">分类跳转URL：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td style="width:185px;"><input type="radio" name="urlStatus" id="urlStatus_01" value="1" <#if topicType.urlStatus?? && topicType.urlStatus ==1> checked="true" </#if> onclick="changeUrlStatus(1)"/><label>启用</label>
	            									 <input type="radio" name="urlStatus" id="urlStatus_02" value="0" <#if topicType.urlStatus?? && topicType.urlStatus ==0> checked="true" </#if> onclick="changeUrlStatus(2)"/><label>停用</label>
	            			</td>
							<td>
								<span style="color:gray">注：启用后将优先跳转至本URL地址，不展示该分类下的卖场数据；
								<br />URL以http://开头，如：http://m.xiu.com/cx/88108.html</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr id="url_tr" <#if topicType.urlStatus?? && topicType.urlStatus == 0>style="display:none"</#if>>
				<th class="thList" scope="row"><span class="red">*</span>URL地址：</th>
				<td class="tdBox"><input type="text" id="url" name="url" value="${topicType.url!''}" />
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row">启用时间段：</th>
				<td class="tdBox">
				<input name="beginTime" type="text" id="beginDate" value="<#if topicType.beginDate??>${topicType.beginDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'%y-%M-%d  00:00:00'})"  />
                --
					<input name="endTime" type="text" id="endDate" value="<#if topicType.endDate??>${topicType.endDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:00',minDate:'%y-%M-%d  00:00:00'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
				当选择了启用时间段，则达到该时间段卖场分类会自动更新为启用，不在时间段内则更新为停用。<br/>
				当未选择启用时间段，则通过选择状态值来控制卖场启用或者停用				
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return go();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="window.close();" class="ml10"> <img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
   function changeUrlStatus(type) {
		if(type == 1) {
			$("#url_tr").show();
		} else if(type == 2) {
			$("#url_tr").hide();
			$("#url").val("");
		}
	}
	 
   //验证信息
    function go(){
      var name=$("#name").val();
      var seq = $("#orderSeq").val();
      var url = $("#url").val();
      name=name.replace(/\ +/g,"");//去掉空格
      if(name==""){
        alert("分类名称不能为空！");
        return false;
      }
      
      if($("#urlStatus_01").attr("checked")) {
	     if(url == null || undefined == url || url == '') {
	       alert("URL地址不能为空！");
	       return ;
	     } else {
	     	var urlStr = url.match(/http:\/\/.+/); 
			if (urlStr == null){ 
				alert('输入的URL地址无效！'); 
				return false; 
			}
	     }
	  }
	  
	  var beginDate=$("#beginDate").val();
	  var endDate=$("#endDate").val();
	  var status = $('input[name="status"]:checked ').val();
	  if((beginDate!=''&&endDate=='')||(beginDate==''&&endDate!='')){
     		alert('开始时间和结束时间必须同时录入'); 
			return false; 
	  }else if(beginDate!=''&&endDate!=''&&status==1){
	        var now = new Date()
	        beginDate=new Date(beginDate.replace("-", "/").replace("-", "/")); 
	        if(beginDate>now){
               alert('开始时间还未到,状态不能为启用,系统会定时根据开始时间和结束时间更新状态'); 
			   return false; 
	        }
	  }
	   
	  
      
      //提交信息 
       submit();
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#saveMenuForm").submit();   
   }
</script>
</html>