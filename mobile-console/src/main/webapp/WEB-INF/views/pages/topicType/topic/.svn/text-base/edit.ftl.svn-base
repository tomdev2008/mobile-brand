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
<div class="adminMain_main" style="width:600px;">
<table class="adminMain_top" style="width:600px;">
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

<form id="editTopicForm" name="editTopicForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/topic/update">
<div class="adminMain_wrap" style="width:600px;">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix" style="width:600px;">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>系统管理</dd>
			<dd><a href="${rc.getContextPath()}/topic/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">卖场管理</a></dd>
			<dd class="last"><h3>编辑卖场</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑卖场</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场ID：</th>
				<td class="tdBox">
				 <input type="text" name="activity_id" id="activity_id"   value="${topic.activity_id!''}"  readOnly="true" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场名称：</th>
				<td class="tdBox">
					<input type="text" name="name" id="name" value="${(topic.name)!''}" style="width:300px;" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标题：</th>
				<td class="tdBox">
					<input type="text" name="title" id="title" value="${(topic.title)!''}" style="width:300px;" maxlength="100"/>
				</td>
			</tr>
			<#if topic.contentType==3>
				<tr>
					<th class="thList" scope="row"><span class="red">*</span>折扣：</th>
					<td class="tdBox">
						<input type="text" name="discount" id="discount" value="${(topic.discount)!''}" style="width:100px;" maxlength="10"/>折起
						&nbsp;&nbsp;
						<span style="color:gray">注：填写阿拉伯1-9之间数字，支持小数；如：2；2.5</span>
					</td>
				</tr>				
			</#if>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场类型：</th>
				<td class="tdBox">
					<input type="radio" name="contentType" id="contentType_01" value="1" <#if topic.contentType?? && topic.contentType == 1>checked="true"</#if> disabled="disabled"/><label>促销</label>
					<input type="radio" name="contentType" id="contentType_02" value="2" <#if topic.contentType?? && topic.contentType == 2>checked="true"</#if> disabled="disabled" /><label>HTML5</label>
					<input type="radio" name="contentType" id="contentType_02" value="3" <#if topic.contentType?? && topic.contentType == 3>checked="true"</#if> disabled="disabled" /><label>特卖</label>
					<input type="radio" name="contentType" id="contentType_02" value="4" <#if topic.contentType?? && topic.contentType == 4>checked="true"</#if> disabled="disabled" /><label>卖场集</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场分类(旧)：</th>
				<td class="tdBox">
				<input type="radio" name="topicTypeId" id="type_01" value="1" <#if topic.topicTypeId?? && topic.topicTypeId == 1>checked="true"</#if>/><label>中性</label>
				<input type="radio" name="topicTypeId" id="type_02" value="2" <#if topic.topicTypeId?? && topic.topicTypeId == 2>checked="true"</#if>/><label>男士</label>
				<input type="radio" name="topicTypeId" id="type_03" value="3" <#if topic.topicTypeId?? && topic.topicTypeId == 3>checked="true"</#if>/><label>女士</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场分类：</th>
				<td class="tdBox">
					<input type="hidden" name="topicTypeRela" id="topicTypeRela" />
				    <#if topic.contentType==1>
					   <#if (topicTypeList?? && topicTypeList?size > 0)>
						   <#list topicTypeList as topicType>
						   		<input type="checkbox" name="topicType" id="type_${topicType.id}" value="${topicType.id}" <#if topicType.status==0>disabled="disabled"</#if> <#if topicType.name="特卖">disabled="disabled"</#if> <#if topicType.existsActivity == 1>checked="true"</#if> />
						   		<label <#if topicType.status==0>style="color:#ABABAB;"</#if>>${topicType.name}</label>		 		
						   </#list>
					   <#else>
					     <font color="red">没有查询到卖场分类数据</font>
					   </#if>
					<#elseif topic.contentType==3>
					   <#if (topicTypeList?? && topicTypeList?size > 0)>
						   <#list topicTypeList as topicType>
						   		<input type="checkbox" name="topicType" id="type_${topicType.id}" value="${topicType.id}" <#if topicType.name="特卖">checked="true"<#else>disabled="disabled"</#if> />
						   		<label <#if topicType.status==0>style="color:#ABABAB;"</#if>>${topicType.name}</label>		 		
						   </#list>
					   <#else>
					     <font color="red">没有查询到卖场分类数据</font>
					   </#if>
					<#else>
					   <#if (topicTypeList?? && topicTypeList?size > 0)>
						   <#list topicTypeList as topicType>
						   		<input type="checkbox" name="topicType" id="type_${topicType.id}" value="${topicType.id}" <#if topicType.status==0>disabled="disabled"</#if> <#if topicType.existsActivity == 1>checked="true"</#if> />
						   		<label <#if topicType.status==0>style="color:#ABABAB;"</#if>>${topicType.name}</label>		 		
						   </#list>
					   <#else>
					     <font color="red">没有查询到卖场分类数据</font>
					   </#if>
					</#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>开始时间：</th>
				<td class="tdBox">
					<input name="start_time_s" id="start_time_s" type="text"  value="<#if topic.start_time??>${topic.start_time?string('yyyy-MM-dd HH:mm:ss')}</#if>"  maxlength="11" style="width:300px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>结束时间：</th>
				<td class="tdBox">
					<input name="end_time_s" id="end_time_s" type="text" value="<#if topic.end_time??>${topic.end_time?string('yyyy-MM-dd HH:mm:ss')}</#if>"  maxlength="11" style="width:300px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>已设广告图片地址：</th>
				<td class="tdBox">
					<label  style="width:300px;">  ${(topic.mobile_pic)!''} </label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">广告图片地址：</th>
				<td class="tdBox">
					<input type="file" name="mobile_pic_f" id="mobile_pic_f" value="${(topic.mobile_pic)!''}" style="width:300px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">已设卖场顶部焦点图：</th>
				<td class="tdBox">
					<label  style="width:300px;">  ${(topic.bannerPic)!''} </label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">卖场顶部焦点图：</th>
				<td class="tdBox">
					<input type="file" name="banner_pic_f" id="banner_pic_f" value="${(topic.bannerPic)!''}" style="width:300px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>卖场形式：</th>
				<td class="tdBox">
	            	<select name="displayStyle" id="displayStyle">
			           <option value="1"  <#if topic.displayStyle?? && topic.displayStyle ==1> selected="selected"  </#if> >横向</option>
				       <option value="2"  <#if topic.displayStyle?? && topic.displayStyle ==2> selected="selected"  </#if> >竖向</option>
				    </select> 
				    &nbsp;&nbsp;
					<span style="color:gray">注：横向卖场图片尺寸：750x328px；竖向卖场图片尺寸：290x355px</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">排序（降序）：</th>
				<td class="tdBox">
	             <input type="text" name="order_seq" id="order_seq" style="width:300px;" value="${topic.order_seq!''}"/>
	     
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">卖场是否显示：</th>
				<td class="tdBox">
	            	<select name="display" id="display">
					           <option value="Y"   selected="selected" >显示</option>
						       <option value="N"  >不显示</option>
				   </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">卖场是否置顶：</th>
				<td class="tdBox">
	            	<select name="topFlag" id="topFlag">
			           <option value="0" <#if topic.topFlag?? && topic.topFlag ==0> selected="selected"  </#if> >否</option>
					   <option value="1" <#if topic.topFlag?? && topic.topFlag ==1> selected="selected"  </#if> >是</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">商品列表默认列数：</th>
				<td class="tdBox">
	            	<select name="display_col_num" id="display_col_num">
					           <option value="1"  <#if topic.display_col_num?? && topic.display_col_num ==1> selected="selected"  </#if> >1列</option>
						       <option value="2"  <#if topic.display_col_num?? && topic.display_col_num ==2> selected="selected"  </#if> >2列</option>
				   </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">已属卖场集：</th>
				<td class="tdBox">
					<div class="adminContent clearfix">
						<table width="100%" class="table_bg01 table_hg01">
							<thead>
								<tr>
									<th width="30%">卖场ID</th>
									<th width="40%">卖场名称</th>
									<th width="30%">操作</th>
								</tr>
							</thead>
							<tbody>
							    <#if (topicList?size > 0)>
							    <#list topicList as topic>
							      <tr>
									<td>${topic.activity_id!''}</td>
									<td>${topic.name!''}</td>
									<td><a href="javascript:void(0);" title="移出卖场集" onclick="deleteActivitySet(${topic.activity_id})">移出卖场集</a></td>    
								 </tr>	
							   </#list>
							   <#else>
							     <tr><td colspan="13"><font color="red">没有记录</font></td></tr>
							   </#if>				
							</tbody>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return go();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="window.close();"  class="ml10"> <img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
      var seq= $("#order_seq").val();
      var discount = $("#discount").val();
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
	   
		if(isNaN(seq)){
			alert("请输入数字!!");
			return false;
		}
		if(discount != null && discount != '') {
			if(isNaN(discount)){
				alert("请输入数字!!");
				return false;
			}
			if(discount >= 10) {
				alert("请输入小于10的折扣！");
				return false;
			}
			if(discount == 0) {
				alert("请输入大于0的折扣！");
				return false;
			}
		}
	   
	   if(pic!=""){
		   	if(pic.toLowerCase().indexOf(".jpg")<0&&pic.toLowerCase().indexOf(".gif")<0&&pic.toLowerCase().indexOf(".png")<0){
		   		alert("请上传.jpg || .gif || .png类型的图片！");
		   		return false;
		   }
	   }
      //提交信息 
       submit();
    }
    
    //用Ajax提交信息
	function submit(){
	 $("#editTopicForm").submit();   
   }
   
   //移出卖场集
   function deleteActivitySet(set_id) {
   	 var act_id = $("#activity_id").val();
   	 if(window.confirm('确定移出本卖场集吗?')){
			$.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/topic/deleteActivitySet?format=json",
				data : {"set_id":set_id, "act_id":act_id},
                dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0")
							{
							   alert("移出卖场集成功！");
							   //修改成后,刷新列表页
							   location.href = "${rc.getContextPath()}/topic/edit?activity_id="+act_id;
							}
						}
					}
				},
				error : function(data) {
					showErrorMsg(true,data);
				}
		});
	}
   }
</script>
</html>