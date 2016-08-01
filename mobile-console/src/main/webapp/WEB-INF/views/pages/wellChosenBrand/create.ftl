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
			<h3 class="topTitle fb f14">添加精选品牌</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<form id="editForm" name="editForm" action="${rc.getContextPath()}/wellChosenBrand/add" enctype="multipart/form-data" method="post">
<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>活动平台</dd>
			<dd><a href="javascript:void(0);" onclick="return goBack();" >精选品牌</a></dd>
			<dd class="last"><h3>添加精选品牌</h3>
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
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20">添加精选品牌</span></th>
			</tr>
			<tr>
				<th class="thList" scope="row">品牌名称<span class="red">*</span>：</th>
				<td class="tdBox">
				<select name="brandName" id="brandName" onchange="showBrand();return false;">
					<option value="-1">请选择品牌</option>
				</select>
					<input type="text" name="brandNameText" id="brandNameText" value="" style="width:100px;height:24px;"/>
					 <label>
						    <a href="javascript:void(0);" title="查询" class="btn" onclick="getBrandName();return false;" style="width:50px;text-align:center;height:22px;" >查询</a>
					 </label>
					<input type="hidden" id="brandId" name="brandId"/>
					<input type="hidden" id="brandCode" name="brandCode"/>
				</td>
				
			</tr>
			<tr>
				<th class="thList" scope="row">品牌排序<span class="red">*</span>：</th>
				<td class="tdBox">
					<input type="text" name="orderSequence" id="orderSequence"  onkeyup="clearNoNum(this);return false;" style="width:300px;height:24px;"/>
				</td>
			</tr>
			<tr id="content_tr">
				<th class="thList" scope="row">品牌商品列表页BANNER图：</th>
				<td class="tdBox">
					<input type="file" id="bannerPicFile" name="bannerPicFile" /><p style="color:red">(图片宽：720，高：260)</p>
				</td>
			</tr>
			 
			<tr>
				<th valign="top" class="thList" scope="row">&nbsp;</th>
				<td class="tdBox">
					<a href="javascript:void(0)" title="提交" onclick="checkMsg();return false;"><img src="${rc.getContextPath()}/resources/manager/images/button_ok32.gif" class="mtb10" /></a> 
					<a href="javascript:void(0)" title="取消" onclick="goBack();return false;"class="ml10"><img src="${rc.getContextPath()}/resources/manager/images/button_cancel32.gif" class="mtb10" /></a>
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
		var brandId=$("#brandId").val();
		var brandCode=$("#brandCode").val();
		var brandName=$("#brandName").val();
		var orderSequence=$("#orderSequence").val();
		if(null==brandName||-1==brandName||null==brandCode||null==brandId){
			alert('请选择品牌！');
			return;
		}
		if(null==orderSequence||undefined==orderSequence){
			alert('请填写排序！');
			return;
		}else if(orderSequence>10000||orderSequence<1){
			alert('排序范围在1-10000之间！');
			return;
		}
		$("#editForm").submit();
	}
	
	function saveMsg(){
	var params=$("#editForm").serialize();
	    $.ajax({
			type : "POST",
			url : "${rc.getContextPath()}/wellChosenBrand/add?format=json",
			data : params,
	        dataType: "text",
			success : function(data, textStatus) {
				if (isNaN(data)) {              
					var objs =  $.parseJSON(data);
					if (objs != null) {
						if(objs.scode == "0"){
							alert('保存成功！');
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
				alert("保存异常！");
				window.opener.location.reload();
				self.close();
			}
		});
	    
	}
	
	var brandList='';
	function getBrandName(){
		//先清空下拉，相关信息
		$("#brandId").val('');
		$("#brandCode").val('');
		$("#brandName").val('');
		$("#brandImg").src='';
		 var brandName=$("#brandNameText").val();
		 if(""==brandName||null==brandName){
			 alert("请输入搜索的品牌名称！");
			 return;
		 }else{
			 var param={
			 	"brandName":brandName
			 };
		 $.ajax({
				type : "GET",
				url : "${rc.getContextPath()}/wellChosenBrand/toAdd?format=json",
				data : param,
		        dataType: "text",
				success : function(data, textStatus) {
					if (isNaN(data)) {              
						var objs =  $.parseJSON(data);
						if (objs != null) {
							brandList=objs.data;
							var opt='<option value="-1">请选择品牌</option>';
							//将值赋给下拉框
							$.each(brandList,function(i,n){
							opt+="<option value="+n.brandId+">"+n.mainName+"</option>";
						});
						$("#brandName").html(opt);
						}else{
						brandList='';
						alert('没有查到相应的品牌');
						}
					}
				},
				error : function(data) {
				alert('查相应的品牌异常');
				}
			});
	 
	 }
		 
	}
	function showBrand(){
	var brandId=$("#brandName").val();
	$.each(brandList,function(i,n){
		if(brandId==n.brandId){
			$("#brandId").val(n.brandId);
			$("#brandCode").val(n.brandCode);
		}		
	});
	}
	function goBack(){
		//window.opener.location.reload();
		self.close();
	}
    
</script>
</html>