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
			<h3 class="topTitle fb f14">编辑角色</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="addRoleForm" name="addRoleForm">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>系统管理</dd>
			<dd><a href="${rc.getContextPath()}/role/list?role_name=${(role_name!'')?html}&status=${(status!'')?html}">角色管理</a></dd>
			<dd class="last"><h3>编辑角色</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑角色</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>角色名称：</th>
				<td class="tdBox">
				    <input type="hidden" name="id" value="${role.id!''}"/>
				    <input type="hidden" name="oldroleName" value="${role.roleName!''}"/>
				    <input type="hidden" name="role_menu" id="role_menu"/>
					<input type="text" name="roleName" id="roleName" value="${role.roleName!''}" style="width:300px;"/>
					<span id="roleName_c" style="color: red"></span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">角色描述：</th>
				<td class="tdBox">
					<textarea id="roleDesc" name="roleDesc" style="width:300px;height:100px;">${(role.roleDesc!'')?html}</textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
                   <#if role?? && role.status == 1>
				     <input type="radio" name="status" id="status_01" value="1" checked="true"/><label>启用</label>
				   <#else>
				     <input type="radio" name="status" id="status_01" value="1" /><label>启用</label> 
				   </#if>
				   <#if role?? && role.status == 0>
				     <input type="radio" name="status" id="status_02" value="0" checked="true"/><label>停用</label>
				   <#else>
				     <input type="radio" name="status" id="status_02" value="0"/><label>停用</label>
				   </#if>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">角色权限：</th>
				<td class="tdBox">
					 <#if menulist??>
                            <#list menulist as menu>
                               <input type="checkbox" value="${menu.id!''}" id="menu_${menu.id}" class="menu_${menu.id}" onchange="selectMenu(this.checked,'menu_${menu.id}');" 
                               <#if rolemenulist??>
                                <#list rolemenulist as rolemenu>
                                 <#if menu.id == rolemenu.menuId>
                                  checked="true"
                                 </#if>
                                </#list>
                               </#if>
                                /><label>&nbsp;<font style="font-weight:bold;font-size:11pt;">${menu.menuName!''}</font>&nbsp;</label><p>
                               </br>
                               <#if menu.mlist??>
                                 <p style="margin-left:53px;">
                                   <#list menu.mlist as _menu>
					                 <input type="checkbox" value="${_menu.id!''}" class="menu_${menu.id}" onchange="selectParentMenu('menu_${menu.id}');"
					                 <#if rolemenulist??>
                                       <#list rolemenulist as rolemenu>
                                        <#if _menu.id == rolemenu.menuId>
                                         checked="true"
                                        </#if>
                                       </#list>
                                    </#if>
					                 /><label>&nbsp;${_menu.menuName!''}&nbsp;</label>
                                   </#list>
                                 </p>
                               </#if>
                             </br>
                         </#list>
                       </#if>
				</td>
			</tr>
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkRole();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return returnRoleList();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">
    //选中一级菜单时，默认全选子类菜单
    function selectMenu(checked,values){
      if(checked == false){
        $("."+values).attr("checked",false);//全部不选中
      }else{
        $("."+values).attr("checked",true);//全部选中
      }
    }
    //选中子类菜单时，默认将其父类菜单选中
    function selectParentMenu(parm){
      $("#"+parm).attr("checked",true);
    }

    //验证信息
    function checkRole(){
      var roleName=$("#roleName").val();
      if(roleName==""){
        $("#roleName_c").text("角色名称不能为空！");
        return false;
      }
      var role_menu="";//权限
      $("input[type='checkbox']:checkbox:checked").each(function(){ 
           if(role_menu==""){
              role_menu+=$(this).val();
           }else{
             role_menu+=","+$(this).val(); 
           }
           
        }) 
      //将对角色赋予的权限给隐藏域，以便提交表单  
      $("#role_menu").val(role_menu); 
      //提交信息  
      checkInfo();
    }
    
    //用Ajax提交登录信息
	function checkInfo(){
	    var params=$("#addRoleForm").serialize();
	    $.ajax({
					type : "POST",
					url : "${rc.getContextPath()}/role/edit?format=json",
					data : params,
	                dataType: "text",
					success : function(data, textStatus) {
					   if (isNaN(data)) {              
							var objs =  $.parseJSON(data);
							if (objs != null) {
								if(objs.scode == "0")
								{
								   //保存成后,刷新列表页
								   location.href = "${rc.getContextPath()}/role/list";
								}else{
								  $("#roleName").focus();
								  $("#roleName_c").text(objs.data);
				            	}
							}
						}
					},
					error : function(data) {
						showErrorMsg(true,data);
					}
			});
	}
	
	//取消事件，返回角色管理列表
	function returnRoleList(){
	  location.href = "${rc.getContextPath()}/role/list?role_name=${(role_name!'')?html}&status=${(status!'')?html}";
	}
</script>
</html>