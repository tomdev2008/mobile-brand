<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/drag.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/resize.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/drawImage.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery.form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/drawImage.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>
<style type="text/css">
.inputstyle{
     position: absolute;
    opacity: 0;
     font-size: 37px;
    filter: alpha(opacity=0);
    cursor: pointer;
     width: 56px;
}
.inputImg{
	 margin-left: 0;
    margin-top: 2px;
}
#img_ul li{
	 float: left;
	  margin-right: 5px;
}
.imagA{
	font-size: 20px;
	padding: 8px 12px;
	border: 1px solid #b8b8b8;
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
			<h3 class="topTitle fb f14">秀添加</h3>
			</td>
			<td class="td03"></td>
		</tr>
	</tbody>
</table>

<div class="adminMain_wrap">
	<div class="adminUp_wrap">
	    <!-- 导航 -->
		<dl class="adminPath clearfix">
			<dt>您现在的位置：</dt>
			<dd><a href="${rc.getContextPath()}/menu/menu_main">走秀后台管理</a></dd>
			<dd>秀客管理</dd>
			<dd class="last"><h3>秀添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addShowForm" name="addShowForm" method="post" action="${rc.getContextPath()}/show/add">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="2" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>发布人：</th>
				<td class="tdBox">
					 	<select name="userId" id="userId" >
					 	<option value="" >请选择</option>
					 		<#if (whiteList?? && whiteList?size > 0)>
								<#list whiteList as item>
									<option value="${item.userId!''}">${item.userId!''},${item.userName!''}</option>
								</#list>
							</#if>
				          </select> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>同款图：</th>
				<td class="tdBox">
					<ul id="img_ul" class="ui-sortable">
					</ul>
					<span style="float: left; margin-left: 10px; margin-top: 20px;">
						<a herf="javascript:void(0);" onclick="addImage(1)" >
						<span class="imagA" >1:1</span></a>
						<a herf="javascript:void(0);" onclick="addImage(2)">
						<span class="imagA">3:4</span></a>
						<a herf="javascript:void(0);" onclick="addImage(3)">
						<span class="imagA">4:3</span></a>
					</span>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">商品名称：</th>
				<td class="tdBox" id="goodsNameTd">
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">文本：</th>
				<td class="tdBox">
				<textarea id="content" style="width:500px;height:80px;" name="content" maxlength="500"></textarea>
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>标签：</th>
				<td class="tdBox">
					<#include "/common/label.ftl">  
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>排序值：</th>
				<td class="tdBox">
                      <input name="orderSeq" id="orderSeq" value="100"/>
				</td>
				<input type="hidden" name="pictureList" id="pictureList"/>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>发布时间：</th>
				<td class="tdBox">
                       <input name="publishTime" type="text" id="publishTime" value=""  maxlength="20" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"></th>
				<td class="tdBox">
                    <p class="fl">
						<input type="button" onclick="add();" class="userOperBtn" value="确认提交"></input> 
					</p>
				</td>
			</tr>
		</table>
	</div>
</form>
</div>
</div>
<div id="uploadImgDiv" class="showbox"> 
    <form action="${rc.getContextPath()}/show/uploadPicture" enctype="multipart/form-data" method="post" name="uploadproofFrm" id="uploadproofFrm"  theme="simple">
		<input  type="file" id="file" style="display:none;"  name="file" accept="image/*" class="timage"  onchange="changePicture(this);return false;">
		<span id="uploadtype"></span>
		<input type="hidden" name="idPicUrl" id="idPicUrl"/>
		<input type="hidden" name="leftWidth" id="leftWidth"/>
		<input type="hidden" name="topWidth" id="topWidth"/>
		<input type="hidden" name="dragDivWidth" id="dragDivWidth"/>
		<input type="hidden" name="dragDivHeight" id="dragDivHeight"/>
		<table class="pesonalTab uploadtab" style="table-layout: fixed;display:none;margin-top:10px;" id="pictrueTab">
			<tr>
				 <td width="500" align="center">
				 	<div style="height:500px;width:500px;">
						<div id="bgDiv">
					        <div id="dragDiv">
					          <div id="rRightDown"> </div>
					          <div id="rLeftDown"> </div>
					          <div id="rRightUp"> </div>
					          <div id="rLeftUp"> </div>
					          <div id="rRight"> </div>
					          <div id="rLeft"> </div>
					          <div id="rUp"> </div>
					          <div id="rDown"></div>
					        </div>
					    </div>							 	
				 	</div>
				</td>
			</tr>
		</table>
		<input id="ratioType" name="ratioType" type="hidden" value="1"/>
		<div class="btnsdiv">
		<input value="确定" class="userOperBtn panelBtn" id="savePicture"  type="button" onclick="saveP();"/> 
		<input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
		</div>
	</form>
</div>
<div id="findGoodsDiv" class="showbox"> 
	<div>
		<span>标签类型：</span><select id="tagType" onchange="tageTypeChange();">
			<option value="">请选择</option>
			<option value="1">自定义标签</option>
			<option value="2">品牌标签</option>
			<option value="3">商品标签</option>
		</select>
	</div>
	<div id="tageNameCus" style="display:none;">标签名：<input id="tageNameCuss" /></div>
	<div id="tageName" style="display:none;">标签名：<input  id="tagName" list="brandTry"/></div>
	<datalist id="brandTry">
		<#if (brandList?? && brandList?size > 0)>
			<#list brandList as item>
				<option value="${item.brandName!''},${item.brandId!''}">${item.brandName!''}</option>
			</#list>
		</#if>
	</datalist>
	<div id="goodSn" style="display:none;">商品走秀码: <input type="text" name="item_goodsSn" id="item_goodsSn" onblur="addContentGoodsAjax()"/> </div>
	<div id="goodPrc" style="display:none;">商品图片:<span id="item_goods_img_span"><img id="item_goods_img" width="100px"/></span> </div>
	<div id="goodName" style="display:none;">商品名称: <span id="item_goods_title_span"></span></div>
	<input type="hidden" id="item_goodId"/>
	<div class="red" id="item_goods_error_msg"></div>
	<div class="btnsdiv">
	<input value="确定" class="userOperBtn panelBtn" id="goodsImgBut" type="button" onclick="addContentGoods()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
	</div>
</div>
<input type="hidden" value="${status!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>

</body>
<script type="text/javascript">
var contextPath="${rc.getContextPath()}";
$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("添加成功");
	   location.href="${rc.getContextPath()}/show/showList";
	 }else if(msg!=""){
	   alert(msg);
	 }
});
//图片上传添加裁剪图
var ic;
var ratio=1;
function addImage(obj){
	ratio=obj;
	$("#ratioType").val(obj);
	$("#file").click();
}
function popUp(a,b){
		//修改绑定
		var arg = {
			id:"uploadImgDiv",
			title:"编辑图片",
			height:b,
			width:a
	}
	$("#idPicUrl").removeAttr("value");
	$("#leftWidth").removeAttr("value");
	$("#topWidth").removeAttr("value");
	$("#dragDivWidth").removeAttr("value");
	$("#dragDivHeight").removeAttr("value");
	$("#bgDiv").removeAttr("style");
	$("#dragDiv").removeAttr("style");
	$("#bgDiv img").remove();
	showPanels(arg);
	isIc();
}
function isIc(){
	 var imgUrl = $$("idPicUrl").value;
	 ic = new ImgCropper("bgDiv", "dragDiv", imgUrl, {
		Color: "#fefefe",
		Resize: true,
		Scale:true,
		Min:		true,//是否最小宽高限制(为true时下面min参数有用)
		minWidth:	10,//最小宽度
		minHeight:	10,//最小高度
		Right: "rRight", Left: "rLeft", Up:	"rUp", Down: "rDown",
		RightDown: "rRightDown", LeftDown: "rLeftDown", RightUp: "rRightUp", LeftUp: "rLeftUp",
		Preview: "viewDiv", viewWidth: 120, viewHeight: 120
	});
	ic.SetPos();
}

	function add(){
		var userId=$("#userId").val();//发布人
		var pictures=$("#pictures").val();//同款图
		var label=$("#labelIds").val();//标签
		//获取图片信息
		var img_li=$(".img_li");
	    var label_i=img_li.length;
	    var showObject={};
	    var showModels=new Array();
	    for(var i=0;i<label_i;i++){
	    	var pictureModel={};//图片对象
	    	var _target=img_li.eq(i);
	    	var pictures=_target.find("#pictures").val();//图片地址
	    	var ratioTypes=_target.find("#ratioTypes").val();//图片比例
	    	pictureModel.originalPicUrl=pictures;
	    	pictureModel.ratioType=ratioTypes;
	    	var dragDiv=_target.find("#dragDivss");
	    	var a=dragDiv.length;
	    	var tagArr=new Array();
	    	for(var j=0;j<a;j++){
	    	//创建tag对象
	    		var tagModel={};
	    		var div=dragDiv.eq(j);
	    		var xx = new Array();
	    		var yy = new Array();
	    		var leftData=div.css("left");
	    		xx=leftData.split("p"); //x轴数据
	    		var topData=div.css("top");
	    		yy=topData.split("p"); //y轴数据
	    		zyPosition=div.find("#zyPosition").val();//图片y轴
	    		zxPosition=div.find("#zxPosition").val();//图片x轴
	    		var tagTypes=div.find("#tagTypes").val();//类型
	    		if(tagTypes==3){
	    			var tagNames=$("#goodsName").val();
	    		}else{
	    			var tagNames=div.find("#tagNames").val();//名称
	    		}
	    		var objectId=div.find("#objectId").val();//objectId;
	    		tagModel.xPosition=(xx[0]-zxPosition)/2;//对象属性
	    		tagModel.yPosition=(yy[0]-zyPosition)/4;
	    		tagModel.name=tagNames;
	    		tagModel.type=tagTypes;
	    		if(objectId!="/"){
	    			tagModel.objectId=objectId;
	    		}
	    		tagArr[j]=tagModel;
	    	}
	    	pictureModel.tagList=tagArr;
	    	showModels[i]=pictureModel;
	    }
	    showObject.pictureList=showModels;
		
		if(userId==''){
			alert("请选择发布人");
			return;
		}
		if(pictures=='' || pictures==null){
			alert("同款图不能为空");
			return;
		}
		if(label=='' || label==null){
			alert("标签不能为空");
			return;
		}
		showObject.userId=userId;
		var show=window.JSON.stringify(showObject);
		$("#pictureList").val(show);
	   $("#addShowForm").submit();
	}
	
	//图片上添加标签或是商品
	var parentDiv="";
	var x="";
	var y="";
	var zx="";
	var zy="";
	function addGoodSn(event,obj){
	//位置包含滚动条
		var e=event || window.event;
		 var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
		 var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
		x = e.pageX || e.clientX + scrollX;
		y = e.pageY || e.clientY + scrollY;
		zx=obj.offsetLeft+183;
		zy=obj.offsetTop+177;
		 //修改绑定
	var arg = {
		id:"findGoodsDiv",
		title:"标签",
		height:300,
		width:600
	}
	var parentDivs=$(obj).parents(".img_li");
	parentDiv=$(parentDivs[0]);
	 $("#tageName").css("display","none");
	  $("#tageNameCus").css("display","none");
	 $("#goodSn").css("display","none");
	 $("#goodPrc").css("display","none");
	 $("#goodName").css("display","none");
	 $("#item_goods_error_msg").html("");
	 $("#item_goods_title_span").html("");
	 $("#item_goods_img").attr("src","");
	 $("#goodsImgBut").removeAttr("disabled"); 
	 $("#tagName").val("");
	 $("#tageNameCuss").val("");
	showPanel(arg);
	}
	function tageTypeChange(){
		var type=$("#tagType").val();
		if(type==1){
			$("#tageNameCus").css("display","block");
			$("#tageName").css("display","none");
			$("#goodSn").css("display","none");
	 		$("#goodPrc").css("display","none");
	 		$("#goodName").css("display","none");
		}else if(type==2){
			$("#tageNameCus").css("display","none");
			$("#tageName").css("display","block");
			$("#goodSn").css("display","none");
	 		$("#goodPrc").css("display","none");
	 		$("#goodName").css("display","none");
		}else{
			$("#tageName").css("display","none");
			$("#tageNameCus").css("display","none");
			$("#goodSn").css("display","block");
			$("#goodPrc").css("display","block");
			$("#goodName").css("display","block");
		}
	}
	//异步请求品牌标签
	function ajaxBrand(){
		 $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/show/getAllBrands?format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode == "0"){
								alert(objs.data.brandName);
			            	}
						}
					}
				},
				error : function(data) {
				    $("#goodsImgBut").removeAttr("disabled"); 
				}
			}); 
	}
	function addContentGoodsAjax(){
	   var goodsSn=$("#item_goodsSn").val();
	   	$("#item_goods_error_msg").html("");
	   if(goodsSn.length==0){
	    alert("请输入商品走秀码");
	    return false;
	   }
    $("#goodsImgBut").attr('disabled',"true");
   var isSuccess=false;
	    $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/subject/checkGoodsSn?goodsSn=" + goodsSn +"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				   if (isNaN(data)) {
						var objs =  $.parseJSON(data);
						if (objs != null) {
							if(objs.scode != "0")
							{
						       $("#item_goods_error_msg").html("商品不存在(PS:需输入商品走秀码不是商品ID)");
			            	}else{
						       $("#item_goods_title_span").html(objs.data.goodsName);
						       	// 设置商品图片
								var goodsImage = "http://image4.zoshow.com/upload/goods" + objs.data.strDate + "/" +
													objs.data.goodsSn + "/" + objs.data.mainSku + "/g1_402_536.jpg";
						       $("#item_goods_img").attr("src",goodsImage);
						       $("#item_goodId").val(objs.data.goodsId);
			            		isSuccess=true;
			            		 $("#goodsImgBut").removeAttr("disabled"); 
			            	}
						}
					}
				},
				error : function(data) {
				    $("#goodsImgBut").removeAttr("disabled"); 
				}
			}); 
	return isSuccess;
}
function addContentGoods(){
	var type=$("#tagType").val();
	if(type==''){
		alert("请选择标签类型");
		return;
	}
	var objectId="";
	var tagName="";
	var names="";
	if(type==1){
		tagName=$("#tageNameCuss").val();
		names=tagName;
		if(tagName==''){
			alert("请输入标签名称");
			return;
		}
		if(tagName.length>20){
			tagName=tagName.substr(0,20);
		}
	}
	if(type==2){
		tagName=$("#tagName").val();
		if(tagName==''){
			alert("请输入标签名称");
			return;
		}
		var tag=new Array();
		tag=tagName.split(",");
		objectId=tag[1];
		tagName=tag[0];
		names=tagName;
		if(tagName>20){
			tagName=tagName.substr(0,20);
		}
	}
	if(type==3){
		tagName=$("#item_goods_title_span").text();
		names=tagName;
		if(tagName==''){
			alert("商品名称不能为空");
			return;
		}
		if(tagName.length>20){
			tagName=tagName.substr(0,20);
		}
		objectId=$("#item_goodId").val();
		var goodSku="";
		goodSku = goodSku + "<input type='text' id='goodsName' name='goodsName' style='width: 500px;height:25px;' value='"+names+"' />";
		$("#goodsNameTd").append(goodSku);
	}
	var sku_html = "";
	sku_html = sku_html + "<div id='dragDivss' style='position:absolute;left:"+x+"px;top:"+y+"px;border:solid black;'>";
	sku_html = sku_html + "<div style='font-family:sans-serif;font-weight:bold;background-color: #000000;color: #ffffff;' ondblclick='deleteTag(this)' onmousedown='drag (this.parentNode,event);' >"+tagName;
	sku_html = sku_html + "</div>";
	sku_html = sku_html + "<input type='hidden' id='tagTypes' name='tagTypes' value="+type+" />";
	sku_html = sku_html + "<input type='hidden' id='tagNames' name='tagNames' value='"+names+"' />";
	sku_html = sku_html + "<input type='hidden' id='objectId' name='objectId' value="+objectId+" />";
	sku_html = sku_html + "<input type='hidden' id='zyPosition'  value="+zy+" />";
	sku_html = sku_html + "<input type='hidden' id='zxPosition'  value="+zx+" />";
	sku_html = sku_html + "</div>";
	parentDiv.append(sku_html);
	parentDiv="";
	x="";
	y="";
	showPanelClose();
}
//删除图片
function deleteImg(obj){
	var parentDivs=$(obj).parents(".img_li");
	var parentDiv=$(parentDivs[0]);
	$(parentDiv).remove();
}
//双击删除标识
function deleteTag(obj){
	var parentDivs=$(obj).parents("#dragDivss");
	var parentDiv=$(parentDivs[0]);
	$(parentDiv).remove();
}
</script>
</html>