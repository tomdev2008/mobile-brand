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

<form id="saveTopicForm" name="saveTopicForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/topic/save">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>活动平台</dd>
			<dd><a href="${rc.getContextPath()}/topic/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">卖场管理</a></dd>
			<dd class="last"><h3>添加H5卖场</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加H5卖场</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场名称：</th>
				<td class="tdBox">
					<input type="text" name="name" id="name" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标题：</th>
				<td class="tdBox">
					<input type="text" name="title" id="title" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场类型：</th>
				<td class="tdBox">
					<input type="radio" name="contentType" id="contentType_01" value="1" disabled="disabled"/><label>促销</label>
					<input type="radio" name="contentType" id="contentType_02" value="2" checked="true" /><label>HTML5</label>
					<input type="radio" name="contentType" id="contentType_03" value="3" disabled="disabled" /><label>特卖</label>
					<input type="radio" name="contentType" id="contentType_04" value="4" disabled="disabled" /><label>卖场集</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场分类(旧)：</th>
				<td class="tdBox">
				<input type="radio" name="topicTypeId" id="type_01" value="1" checked="true"/><label>中性</label>
				<input type="radio" name="topicTypeId" id="type_02" value="2" /><label>男士</label>
				<input type="radio" name="topicTypeId" id="type_03" value="3" /><label>女士</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场分类：</th>
				<td class="tdBox">
					<input type="hidden" name="topicTypeRela" id="topicTypeRela" />
					<#if (topicTypeList?? && topicTypeList?size > 0)>
					   <#list topicTypeList as topicType>
					   		<input type="checkbox" name="topicType" id="type_${topicType.id}" value="${topicType.id}" <#if topicType.status==0>disabled="disabled"</#if> />
					   		<label <#if topicType.status==0>style="color:#ABABAB;"</#if>>${topicType.name}</label>		 		
					   </#list>
					<#else>
					   <font color="red">没有查询到卖场分类数据</font>
				   </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>开始时间：</th>
				<td class="tdBox">
					<input name="start_time_s" id="start_time_s" type="text"  maxlength="11"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>结束时间：</th>
				<td class="tdBox">
					<input name="end_time_s" id="end_time_s" type="text" maxlength="11"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>广告图片：</th>
				<td class="tdBox">
					<input type="file" name="mobile_pic_f" id="mobile_pic_f"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">卖场焦点图：</th>
				<td class="tdBox">
					<input type="file" name="banner_pic_f" id="banner_pic_f"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场形式：</th>
				<td class="tdBox">
	            	<input type="radio" name="displayStyle" id="type_01" value="1" checked="true" /><label>横向</label>
					<input type="radio" name="displayStyle" id="type_02" value="2" /><label>竖向</label>
					&nbsp;&nbsp;
					<span style="color:gray">注：横向卖场图片尺寸：750x328px；竖向卖场图片尺寸：290x355px</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>排序（降序）：</th>
				<td class="tdBox">
	            	<input type="text" name="order_seq" id="order_seq" value="0" maxlength="4"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">卖场是否显示：</th>
				<td class="tdBox">
	            	<select name="display" id="display">
			           <option value="Y">显示</option>
				       <option value="N">不显示</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">卖场是否置顶：</th>
				<td class="tdBox">
	            	<select name="topFlag" id="topFlag">
				       <option value="0">否</option>
			           <option value="1">是</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">商品列表默认列数：</th>
				<td class="tdBox">
	            	<select name="display_col_num" id="display_col_num">
						<option value="1">1列</option>
						<option value="2">2列</option>
				   </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场页面URL地址：</th>
				<td class="tdBox">
					<input type="text" name="URL" id="URL"/>
				</td>
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
    
    
   //验证信息
    function go(){
      var name=$("#name").val();
      var startTime=$("#start_time_s").val();
      var endTime=$("#end_time_s").val();
      var pic=$("#mobile_pic_f").val();
      var seq = $("#order_seq").val();
      var URL =$("#URL").val();
      var topicTypeRela = "";
      
      var regBlank = /^\s+$/;	//验证规则
      if(name==""){
        alert("名称不能为空！");
        return false;
      } else {
      	 var flag = regBlank.test(name);
	 	 if(flag) {
		    alert("卖场名称不能都为空格！");
		 	return false;
		 }
      }
      
      var title = $("#title").val();
	  if(title != null && title != "") {
	 	 var flag = regBlank.test(title);
	 	 if(flag) {
		    alert("卖场标题不能都为空格！");
		 	return false;
		 }
	  }
      
      //卖场分类
	  $("[name=topicType]:checkbox").each(function() {
	  	if($(this).is(":checked")) {
	  		topicTypeRela = topicTypeRela + $(this).val() + ",";
	  	}
	  });
	  if(topicTypeRela != "") {
	    topicTypeRela = topicTypeRela.substr(0,topicTypeRela.length - 1);
	  } else {
	  	alert("请选择卖场所属分类！");
	  	return false;
	  }
	  $("#topicTypeRela").val(topicTypeRela);
	  
      if(startTime==""){
        alert("开始时间不能为空！");
        return false;
      }
      if(endTime==""){
        alert("结束时间不能为空！");
        return false;
      }
       var start_date_val = new Date(startTime.replace(/-/g,"/"));
	   var end_date_val = new Date(endTime.replace(/-/g,"/"));
	   if(start_date_val > end_date_val){
	       alert("结束日期要大于等于开始日期！");
           return false;
	   }
	   var currend_date_val = new Date();
	   if(currend_date_val >= new Date(end_date_val.getTime() + 1 * 24 * 60 * 60 * 1000)){
	        alert("结束要大于当前日期！");
            return false;
	   }
	   
	   if(seq==""){
         alert("排序号不能为空！");
         return false;
       }
       
		if(isNaN(seq)){
			alert("请输入数字!!");
			return false;
		}
	   
	   if(pic!=""){
		   	if(pic.toLowerCase().indexOf(".jpg")<0&&pic.toLowerCase().indexOf(".gif")<0&&pic.toLowerCase().indexOf(".png")<0){
		   		alert("请上传.jpg || .gif || .png类型的图片！");
		   		return false;
		   }
	   }
	   
	   if(URL==""){
         alert("卖场页面URL不能为空！");
         return false;
       }
       var urlStr = URL.match(/http:\/\/.+/); 
		if (urlStr == null){ 
			alert('你输入的URL无效'); 
			return false; 
		}
		
      //提交信息 
       submit();
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#saveTopicForm").submit();   
   }
</script>
</html>