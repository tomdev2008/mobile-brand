<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" />
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery.colorpicker.js"></script>
<style>
.btns_2 {
    border: 1px solid #babac0;
    line-height: 48px;
    padding:5px;
}

</style>
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

<form id="saveMenuForm" name="saveMenuForm" enctype="multipart/form-data"  method="post" action="${rc.getContextPath()}/findMenu/update">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>配置管理</dd>
			<dd><a href="${rc.getContextPath()}/findMenu/list?menu_name=${(menu_name!'')?html}&menu_status=${(menu_status!'')?html}">发现频道管理</a></dd>
			<dd class="last"><h3>编辑栏目</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%"  class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑栏目</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>栏目ID：</th>
				<td class="tdBox">
					<input type="text" name="id" id="id" value="${findMenu.id!''}" readonly="true" style="background-color:#f5f5f5;" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>栏目名称：</th>
				<td class="tdBox">
					<input type="text" name="name" id="name" value="${findMenu.name!''}" maxlength="100"/>
					&nbsp;&nbsp;
					<span style="color:gray">注：建议控制在6个汉字以内</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">名称字体颜色：</th>
				<td class="tdBox" style="vertical-align:middle;">
					<input type="text" name="fontColor" id="fontColor" value="${findMenu.fontColor!''}" maxlength="7" />
					<input type="text" id="colorBlock" style="width:19px; background-color:${findMenu.fontColor!''};" readonly="true" />
					<img src="${rc.getContextPath()}/resources/common/images/colorpicker.png" id="backgroundColorChoose" style="cursor:pointer; padding-bottom:4px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">背景颜色：</th>
				<td class="tdBox" style="vertical-align:middle;">
					<input type="text" name="bgColor" id="bgColor" value="${findMenu.bgColor!''}" maxlength="7" />
					<input type="text" id="bgColorBlock" style="width:19px; background-color:${findMenu.bgColor!''};" readonly="true" />
					<img src="${rc.getContextPath()}/resources/common/images/colorpicker.png" id="bgBackgroundColorChoose" style="cursor:pointer; padding-bottom:4px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>已设栏目图标地址：</th>
				<td class="tdBox">
					<label style="width:300px;">${(findMenu.iconUrl)!''} </label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">栏目图标：</th>
				<td class="tdBox">
					<input type="file" name="icon_url_f" id="icon_url_f"/>
					&nbsp;&nbsp;
					<span style="color:gray">注：尺寸100px*100px，支持jpg、png</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>已设栏目点击事件图标地址：</th>
				<td class="tdBox">
					<label style="width:300px;">${(findMenu.iconClickUrl)!''} </label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">栏目点击事件图标：</th>
				<td class="tdBox">
					<input type="file" name="icon_click_url" id="icon_click_url"/>
					&nbsp;&nbsp;
					<span style="color:gray">注：尺寸100px*100px，支持jpg、png</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">广告语：</th>
				<td class="tdBox">
					<input type="text" name="slogan" id="slogan" value="${findMenu.slogan!''}" />
					&nbsp;&nbsp;
					<span style="color:gray">注：建议控制在10个汉字以内</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>所属区块：</th>
				<td class="tdBox">
	            	<select name="block" id="block">
						<option value="1" <#if findMenu.block?? && findMenu.block ==1> selected="selected" </#if>>区块1</option>
						<option value="2" <#if findMenu.block?? && findMenu.block ==2> selected="selected" </#if>>区块2</option>
						<option value="3" <#if findMenu.block?? && findMenu.block ==3> selected="selected" </#if>>区块3</option>
						<option value="4" <#if findMenu.block?? && findMenu.block ==4> selected="selected" </#if>>区块4</option>
						<option value="5" <#if findMenu.block?? && findMenu.block ==5> selected="selected" </#if>>区块5</option>
						<option value="6" <#if findMenu.block?? && findMenu.block ==6> selected="selected" </#if>>区块6</option>
						<option value="7" <#if findMenu.block?? && findMenu.block ==7> selected="selected" </#if>>区块7</option>
						<option value="8" <#if findMenu.block?? && findMenu.block ==8> selected="selected" </#if>>区块8</option>
						<option value="9" <#if findMenu.block?? && findMenu.block ==9> selected="selected" </#if>>区块9</option>
						<option value="10" <#if findMenu.block?? && findMenu.block ==10> selected="selected" </#if>>区块10</option>
						<option value="11" <#if findMenu.block?? && findMenu.block ==11> selected="selected" </#if>>区块11(首页引导)</option>
						<option value="12" <#if findMenu.block?? && findMenu.block ==12> selected="selected" </#if>>区块12(App导航栏)</option>
						<option value="13" <#if findMenu.block?? && findMenu.block ==13> selected="selected" </#if>>区块13(用户中心功能模块)</option>
				   </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>排序（降序）：</th>
				<td class="tdBox">
	            	<input type="text" name="orderSeq" id="orderSeq" maxlength="4" value="${findMenu.orderSeq!''}" />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>栏目类型：</th>
				<td class="tdBox">
	            	<input type="radio" name="type" id="type_01" value="1" <#if findMenu.type?? && findMenu.type ==1> checked="true" </#if> disabled="disabled" /><label>Native</label>
					<input type="radio" name="type" id="type_02" value="2" <#if findMenu.type?? && findMenu.type ==2> checked="true" </#if> disabled="disabled" /><label>H5</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><span class="red">*</span>App URL：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td><input type="text" name="url" id="url" value="${findMenu.url!''}" /></td>
							<td style="padding-left:25px;">
								<span style="color:gray">注：H5栏目以http://开头，如：http://m.xiu.com/cx/88108.html；
								<br />Native栏目以xiuApp://开头，如：xiuApp://xiu.app.index/openwith?page=recommend</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">Wap URL：</th>
				<td class="tdBox">
					<table>
						<tr>
							<td><input type="text" name="wapUrl" id="wapUrl" value="${findMenu.wapUrl!''}" /></td>
							<td style="padding-left:25px;">
								<span style="color:gray">如：http://m.xiu.com/cx/88108.html</span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">动画效果：</th>
				<td class="tdBox">
					<input type="radio" name="animation" id="animation_01" value="0" <#if findMenu.animation?? && findMenu.animation ==0> checked="true" </#if> /><label>无</label>
					<input type="radio" name="animation" id="animation_02" value="1" <#if findMenu.animation?? && findMenu.animation ==1> checked="true" </#if> /><label>抖动</label>
					<input type="radio" name="animation" id="animation_03" value="2" <#if findMenu.animation?? && findMenu.animation ==2> checked="true" </#if> /><label>放大显示</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">状态：</th>
				<td class="tdBox">
					<input type="radio" name="status" id="statusType_01" value="1" <#if findMenu.status?? && findMenu.status ==1> checked="true" </#if> /><label>启用</label>
					<input type="radio" name="status" id="statusType_02" value="0" <#if findMenu.status?? && findMenu.status ==0> checked="true" </#if> /><label>停用</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">是否需要登录：</th>
				<td class="tdBox">
					<input type="radio" name="isNeedLogin" id="isNeedLogin_01" value="0" <#if findMenu.isNeedLogin?? && findMenu.isNeedLogin ==0> checked="true" </#if> /><label>不需要</label>
					<input type="radio" name="isNeedLogin" id="isNeedLogin_02" value="1" <#if findMenu.isNeedLogin?? && findMenu.isNeedLogin ==1> checked="true" </#if> /><label>需要</label>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">支持版本：</th>
				<td class="tdBox">
					<input type="radio" name="supportVersion" id="supportVersion_01" value="0"<#if findMenu.supportVersion?? && findMenu.supportVersion==0> checked="true"</#if> onclick="hideVersion()"/><label>ALL</label>
					<input type="radio" name="supportVersion" id="supportVersion_02" value="1" onclick="showVersion()" <#if findMenu.supportVersion?? && findMenu.supportVersion==1> checked="true"</#if>/><label>指定版本</label>
				</td>
			</tr>
			<tr id="show_version" <#if findMenu.supportVersion?? && findMenu.supportVersion==0> style="display:none"</#if>>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
					<table class="version_ul">
						<#if (findMenu.listVersion?? && (findMenu.listVersion)?size>0)>
						<#list findMenu.listVersion as listVersion>
							<tr class="version_li">
								<td><label>系统</label>
									<select name="appSystem" id="block">
										<option value="${listVersion.appSystem!''}">${listVersion.appSystem!''}</option>
										<#if listVersion.appSystem=='iPhone'>
											<option value="Android">Android</option>
										<#elseif listVersion.appSystem=='Android'>
											<option value="iPhone">iPhone</option>
										</#if>
									</select>
								</td>
								<td><label>下载来源</label><input type="text" name="appSource" value="${listVersion.appSource!''}"/></td>
								<td><label>最低版本</label><input type="text" name="startVersion" value="${listVersion.startVersion!''}"/></td>
								<td><label>到</label><input type="text" name="lastVersion" value="${listVersion.lastVersion!''}"/><label>之间</label></td>
								<td style="padding-left:10px;"><input class="userOperBtn" type="button" value="删除" onclick="delSupportVersion(this)"></td>
							</tr>
						</#list>
						<#else>
							<tr class="version_li">
								<td><label>系统</label>
									<select name="appSystem" id="block">
										<option value="iPhone">iPhone</option>
										<option value="Android">Android</option>
									</select>
								</td>
								<td><label>下载来源</label><input type="text" name="appSource"/></td>
								<td><label>最低版本</label><input type="text" name="startVersion"/></td>
								<td><label>到</label><input type="text" name="lastVersion"/><label>之间</label></td>
								<td style="padding-left:10px;"><input class="userOperBtn" type="button" value="删除" onclick="delSupportVersion(this)"></td>
							</tr>
						</#if>
					</table>
					<div><a id="add_sku" class="btns_2 block" onclick="addSupportVersion()">+ 添加</a>
						<span style="color:red">注：不填写则为全部</span>
					</div>
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
    function showVersion(){
		$("#show_version").css("display","");
	}
	function hideVersion(){
		$("#show_version").css("display","none");
	}
    function delSupportVersion(obj){
	  var parentDivs=$(obj).parents(".version_li");
	  var parentDiv=$(parentDivs[0]);
	  $(parentDiv).remove();
	}
	function addSupportVersion(){
		var sku_html = "";
		sku_html = sku_html + "<tr class='version_li'>";
		sku_html = sku_html + "<td><label>系统</label><select name='appSystem' id='appSystem'><option value='iPhone'>iPhone</option><option value='Android'>Android</option></select></td>";
		sku_html = sku_html + "<td><label>下载来源</label><input type='text' name='appSource'/></td>";
		sku_html = sku_html + "<td><label>最低版本</label><input type='text' name='startVersion'/></td>";
		sku_html = sku_html + "<td><label>到</label><input type='text' name='lastVersion'/><label>之间</label></td>";
		sku_html = sku_html + "<td style='padding-left:10px;'><input class='userOperBtn' type='button' value='删除' onclick='delSupportVersion(this)'></td>";
		sku_html = sku_html + "</tr>";
		$(".version_ul").append(sku_html);
	}
    $("#backgroundColorChoose").colorpicker({
	    fillcolor:true,
	    target:$("#fontColor"),
	    success:function(o,color) {
	    	$("#colorBlock").css("background-color",color);
	    },
	    reset:function(o) {
	    	var colorValue = $("#fontColor").val();
	    	$("#colorBlock").css("background-color",colorValue);
	    }
	});
    
        
    $("#bgBackgroundColorChoose").colorpicker({
	    fillcolor:true,
	    target:$("#bgColor"),
	    success:function(o,color) {
	    	$("#bgColorBlock").css("background-color",color);
	    },
	    reset:function(o) {
	    	$("#bgColorBlock").css("background-color","black");
	    }
	});
    
   //验证信息
    function go(){
      var name=$("#name").val();
      var pic=$("#icon_url_f").val();
      var seq = $("#orderSeq").val();
      var colorText = $("#fontColor").val();
      var url =$("#url").val();
      var wapUrl = $("#wapUrl").val();
      
      if(name==""){
        alert("栏目名称不能为空！");
        return false;
      }
      
      if(colorText != "") {
      	var firstColorText = colorText.substr(0,1);
      	if(firstColorText != '#') {
      		alert("请选择正确的颜色代码！");
      		return false;
      	}
      	var reg = /^#[A-Za-z0-9]+$/;	//验证规则
      	var flag = reg.test(colorText);
      	if(!flag) {
	 		alert("请选择正确的颜色代码！");
	 		return false;
	 	}
	 	if(colorText.length == 4 || colorText.length == 7) {
	 	} else {
	 		alert("请选择正确的颜色代码");
	 		return false;
	 	}
      }
      
	   if(pic!=""){
		   if(pic.toLowerCase().indexOf(".jpg")<0&&pic.toLowerCase().indexOf(".png")<0){
		     alert("请上传.jpg || .gif || .png类型的图片！");
		   	 return false;
		   }
	   } 
	   
       if(seq==""){
         alert("排序号不能为空！");
         return false;
       }
       
		if(isNaN(seq)){
			alert("排序请输入数字!!");
			return false;
		}
	   
	   if(url==""){
         alert("App URL地址不能为空！");
         return false;
       }
       var findMenuType=$("input[name='type']:checked").val();
       var urlStr = url.match(/(http|xiuApp):\/\/.+/); 
       var urlMsg="";
       if(findMenuType==1){
         urlStr=url.match(/(xiuApp):\/\/.+/); 
         urlMsg="当前选择的栏目类型是Native类型,App URL需以xiuApp://开头";
       }else if(findMenuType==2){
         urlStr=url.match(/(http):\/\/.+/); 
         urlMsg="当前选择的栏目类型是H5类型,App URL需以http://开头";
       }
		if (urlStr == null){ 
			alert(urlMsg); 
			return false; 
		}
		
		if(wapUrl != "") {
			var wapUrlStr = wapUrl.match(/http:\/\/.+/); 
			if (wapUrlStr == null){ 
				alert('输入的Wap URL地址无效！'); 
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