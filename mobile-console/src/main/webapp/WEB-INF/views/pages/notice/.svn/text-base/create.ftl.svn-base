<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
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
			<h3 class="topTitle fb f14">添加公告</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="addNoticeForm" name="addNoticeForm"  action="${rc.getContextPath()}/notice/save">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>WAP管理</dd>
			<dd>公告管理</dd>
			<dd class="last"><h3>添加公告</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加公告</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>公告位名称：</th>
				<td class="tdBox">
				    <input type="hidden" name="adv_place_id" id="adv_place_id" value="${(advertisement.adv_place_id)!''}"/>
					<input type="text" name="advPlaceName" id="advPlaceName" value="${(advertisement.advPlaceName)!''}" style="width:300px;" readonly="true"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>公告内容：</th>
				<td class="tdBox">
				<textarea rows="6" cols="120" name="memo" id="memo" value="${(advertisement.memo)!''}" style="width:300px;" />${(advertisement.memo)!''}</textarea>	
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>开始时间：</th>
				<td class="tdBox">
					<input name="startTime" id="startTime" type="text"  value="<#if advertisement.start_time??>${advertisement.start_time?string('yyyy-MM-dd HH:mm:ss')}</#if>"  maxlength="11" style="width:300px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>结束时间：</th>
				<td class="tdBox">
					<input name="endTime" id="endTime" type="text" value="<#if advertisement.end_time??>${advertisement.end_time?string('yyyy-MM-dd HH:mm:ss')}</#if>"  maxlength="11" style="width:300px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">是否显示：</th>
				<td class="tdBox">
				    <input type="checkbox" name="effect" id="effect" checked="true"/>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkNotice(${(advertisement.adv_place_id)!''},'${(advertisement.advPlaceName)!''}');"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnNoticeList(${(advertisement.adv_place_id)!''},'${(advertisement.advPlaceName)!''}');"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
    function checkNotice(advPlaceId,advPlaceName){
      var memo=$("#memo").val();
      var startTime=$("#startTime").val();
      var endTime=$("#endTime").val();
      if(memo==""){
        alert("公告内容不能为空！");
        return false;
      }
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
	   if(start_date_val >= end_date_val){
	       alert("结束日期要大于开始日期！");
           return false;
	   }
	   var currend_date_val = new Date();
	   if(currend_date_val >= end_date_val){
	        alert("结束要大于当前日期！");
            return false;
	   }
     
      //提交信息 
       checkInfo(advPlaceId,advPlaceName);
    }
    
    //用Ajax提交信息
	function checkInfo(advPlaceId,advPlaceName){
	    var params=$("#addNoticeForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/notice/save?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/notice/list?advPlaceId="+advPlaceId+"&advPlaceName="+encodeURI(encodeURI(advPlaceName));
								}else{
								   $("#name").focus();
								   alert(objs.data);
								  //$("#name_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
	
	//取消事件，返回用户管理列表
	function returnNoticeList(advPlaceId,advPlaceName){
	  location.href = "${rc.getContextPath()}/notice/list?advPlaceId="+advPlaceId+"&advPlaceName="+encodeURI(encodeURI(advPlaceName));
	}
</script>

</html>