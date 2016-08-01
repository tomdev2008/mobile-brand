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
			<h3 class="topTitle fb f14">编辑精选品牌信息</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="editForm" name="editForm" action="${rc.getContextPath()}/wellChosenBrand/update" enctype="multipart/form-data" method="post">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>活动平台</dd>
			<dd><a href="javascript:void(0);" onclick="return goBack();">精选品牌</a></dd>
			<dd class="last"><h3>编辑精选品牌</h3>
			</dd>
			<div class="wapbt" style="float:right;border-bottom:0px;">
				<label>
					<a href="javascript:void(0);" title="返回" class="btn" onclick="return goBack();" style="width:50px;text-align:center;height:22px;" onmouseout="this.style.background='#FFFFFF'" onmouseover="this.style.background='#EAEAEA'" >返&nbsp;&nbsp;回</a>
				</label>
			</div>
		</dl>
		</fieldset>
	</div>
	<!--导航end-->
	
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">编辑精选品牌</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">品牌名称<span class="red">*</span>：</th>
				<td class="tdBox">
				<input type="text" name="brandName" id="brandName" value="${wellChosenBrandVo.brandName}" readonly="readonly"  style="width:300px;height:24px;">
				<input type="hidden" id="id" name="id" value="${wellChosenBrandVo.id}"/>
					
				</td>
				
			</tr>
			<tr>
				<th class="thList" scope="row">品牌编码<span class="red">*</span>：</th>
				<td class="tdBox">
					<input type="text" id="brandCode" name="brandCode" value="${wellChosenBrandVo.brandCode}" readonly="readonly" style="width:300px;height:24px;"/>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">品牌排序<span class="red">*</span>：</th>
				<td class="tdBox">
					<input type="text" name="orderSequence" id="orderSequence" value="${wellChosenBrandVo.orderSequence}" onkeyup='clearNoNum(this)' style="width:300px;height:24px;"/>
				</td>
			</tr>
			<tr id="content_tr">
				<th class="thList" scope="row">已设banner图片url：</th>
				<td class="tdBox">
				 <#if wellChosenBrandVo.bannerPic??> 
					 <input type="text" id="bannerPic" value="${wellChosenBrandVo.bannerPic}" style="width:300px;height:24px;" readonly="readonly"/> 
				 </#if>
				</td>
			</tr>
			<tr id="content_tr">
				<th class="thList" scope="row">banner图片url：</th>
				<td class="tdBox">
				  <input type="file" id="bannerPicFile" name="bannerPicFile" /><p style="color:red">(图片宽：720，高：260)</p>
				</td>
			</tr>
			 
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="return checkMsg();"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="return goBack();"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
</div>
</body>
<script type="text/javascript">

	jQuery(document).ready(function () {
		
	});
	function clearNoNum(obj)
	{
	  obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
	  obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
	  obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
	  obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	}
	function checkMsg(){
		var orderSequence=$("#orderSequence").val();
		if(null==orderSequence||undefined==orderSequence){
			alert('请填写排序！');
			return;
		}else if(orderSequence>10000||orderSequence<1){
			alert('排序范围在1-10000之间！');
			return;
		}	
		 $("#editForm").submit();
	}
	
	function updateMsg(){
		var id=$("#id").val();
		var orderSequence=$("#orderSequence").val();
		var params={
			"id":id,
			"orderSequence":orderSequence
		};
	    $.ajax({
			type : "POST",
			url : "${rc.getContextPath()}/wellChosenBrand/update?format=json",
			data : params,
	        dataType: "text",
			success : function(data, textStatus) {
				if (isNaN(data)) {              
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0"){
							alert('修改成功！');
							//保存成后,刷新列表页
							window.opener.location.reload();
							self.close();
						}else{
							alert(objs.data);
							window.opener.location.reload();
							self.close();
				         }
					}
				}
			},
			error : function(data) {
				alert("修改异常！");
				window.opener.location.reload();
				self.close();
			}
		});
	}
	function goBack(){
		//window.opener.location.reload();
		self.close();
		//location.href="${rc.getContextPath()}/wellChosenBrand/list";
	}
    
</script>
</html>