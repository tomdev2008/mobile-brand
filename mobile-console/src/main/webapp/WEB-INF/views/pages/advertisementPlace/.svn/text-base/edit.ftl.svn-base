<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
</head>
<body>
<div class="adminMain_main">
<table class="adminMain_top">
	<tbody>
		<tr>
			<td class="td01"></td>
			<td class="td02">
			<h3 class="topTitle fb f14">编辑广告位</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="editAdvPlaceForm" name="editAdvPlaceForm">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>WAP管理</dd>
			<dd>广告位管理</dd>
			<dd class="last"><h3>编辑广告位</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑广告位</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>广告位CODE：</th>
				<td class="tdBox">
					<input type="text" name="code" id="name" value="${advertisementPlace.code!''}" style="width:300px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>广告位名称：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" id="id" value="${advertisementPlace.id!''}"/>
					<input type="text" name="name" id="name" value="${advertisementPlace.name!''}" style="width:300px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">广告位备注：</th>
				<td class="tdBox">
					<input type="text" name="memo" id="memo" maxlength="11" value="${advertisementPlace.memo!''}" style="width:300px;"/>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkAdvertisementPlace();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnAdvertisementPlaceList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
    function checkAdvertisementPlace(){
      var name=$("#name").val();
     // var memo=$("#memo").val();
      if(name==""){
        alert("广告位名称不能为空！");
        return false;
      }
      /*if(memo==""){
        alert("广告位备注不能为空！");
        return false;
      }*/
      //提交信息  
      checkInfo();
    }
    
    //用Ajax提交信息
	function checkInfo(){
	    var params=$("#editAdvPlaceForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/advertisementPlace/edit?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/advertisementPlace/list";
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
	
	//取消事件，返回广告位管理列表
	function returnAdvertisementPlaceList(){
	  location.href = "${rc.getContextPath()}/advertisementPlace/list";
	}
</script>
</html>