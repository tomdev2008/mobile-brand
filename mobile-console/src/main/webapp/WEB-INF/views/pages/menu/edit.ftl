<!DOCTYPE html >
<html>
<head>
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
			<h3 class="topTitle fb f14">编辑菜单</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="addMenuForm" name="addMenuForm">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>系统管理</dd>
			<dd><a href="${rc.getContextPath()}/menu/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">菜单管理</a></dd>
			<dd class="last"><h3>编辑菜单</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑菜单</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>菜单名称：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${menu.id!''}"/>
				    <input type="hidden" name="oldmenuName" value="${menu.menuName!''}"/>
					<input type="text" name="menuName" id="menuName" style="width:300px;" value="${menu.menuName!''}"/>
					<span id="menuName_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">菜单URL：</th>
				<td class="tdBox">
					<input type="text" name="menuUrl" id="menuUrl" style="width:300px;" value="${menu.menuUrl!''}"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
				   <#if menu?? && menu.status == 1>
				     <input type="radio" name="status" id="status_01" value="1" checked="true"/><label>启用</label>
				   <#else>
				     <input type="radio" name="status" id="status_01" value="1" /><label>启用</label> 
				   </#if>
				   <#if menu?? && menu.status == 0>
				     <input type="radio" name="status" id="status_02" value="0" checked="true"/><label>停用</label>
				   <#else>
				     <input type="radio" name="status" id="status_02" value="0"/><label>停用</label>
				   </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">上级菜单：</th>
				<td class="tdBox">
				   <select name="parentId" id="parentId" style="width:130px;">
                      <option value="">--请选择--</option>
                       <#list menulist as _menu>
                          <#if menu.parentId?? && (_menu.id == menu.parentId)>
                             <option value="${_menu.id}" selected>${_menu.menuName}</option>
                          <#else>
                             <option value="${_menu.id}">${_menu.menuName}</option>
                          </#if>
                       </#list>
                   </select>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">备注：</th>
				<td class="tdBox">
					<textarea id="memo" name="memo" style="width:500px;height:100px;" >${(menu.memo!'')?html}</textarea>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkMenu();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnMenuList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
    function checkMenu(){
      var menuName=$("#menuName").val();
      var status=$("input[name=status]:checked").val();
      if(menuName==""){
        $("#menuName_c").text("菜单名称不能为空！");
        return false;
      }
      checkInfo();
    }
    
    //用Ajax提交信息
	function checkInfo(){
	    var params=$("#addMenuForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/menu/edit?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/menu/list";
								}else{
								  $("#menuName").focus();
								  $("#menuName_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
	
	//取消事件，返回菜单管理列表
	function returnMenuList(){
	  location.href = "${rc.getContextPath()}/menu/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}";
	}
</script>
</html>