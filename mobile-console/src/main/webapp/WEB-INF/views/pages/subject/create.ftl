<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery-1.6.2.min.js" ></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/jquery_form.js"></script>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/base.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/common.css" /></head>
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/manager/css/page_admin_main.css">
<link   type="text/css" rel="stylesheet" href="${rc.getContextPath()}/resources/show/css/show.css">
<script type="text/javascript" src="${rc.getContextPath()}/resources/common/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-common-util.js"></script>
<script type="text/javascript"  src="${rc.getContextPath()}/resources/common/js/xiu-validate.js"></script>

<style>
	
.btns_2 {
    border: 1px solid #babac0;
    line-height: 48px;
    padding:5px;
}
#labelName{
	height: 30px;
}
.addBtn{
	display:none;
	float: left
}
.insertBtn{
	display:block;
	float: left
}
.content_item{
	width:100%
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
			<h3 class="topTitle fb f14">专题添加</h3>
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
			<dd><a href="${rc.getContextPath()}/subject/list">专题管理</a></dd>
			<dd class="last"><h3>专题添加</h3>
			</dd>
		</dl>
	</div>
	<!--导航end-->
<form id="addSubjectForm" name="addSubjectForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/subject/save">
	<!--菜单内容-->
	<div class="adminContent clearfix">
		<table width="100%" class="table_bg05">
			<tr>
				<th colspan="3" class="thTitle" scope="row"><span class="cBlue f14 fb pl20"></span></th>
			</tr>

			<tr>
				<th class="thList" scope="row"><font class="red">*</font>名称：</th>
				<td class="tdBox">
					  <input  name="name" id="name" /> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row">标题：</th>
				<td class="tdBox">
					  <input  name="title" id="title"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>类型：</th>
				<td class="tdBox">
	 					<input type="radio" name="displayStytle" class="displayStytle" value="1"    checked="checked" />竖专题(大)
	 					<input type="radio" name="displayStytle" class="displayStytle" value="2"    />横专题(小)</td> 
					 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>排序值：</th>
				<td class="tdBox">
                      <input  name="orderSeq" id="orderSeq" value="100"/> 	<font class="gray">按该值正序排序,值小在前</font>	
                      <div id="timemsg"></div>		
               </td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>专题图片：</th>
				<td class="tdBox">
					  <input type="file" name="out_pic" id="out_pic"/> 
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>开始时间：</th>
				<td class="tdBox">
				<input name="beginTime" type="text" id="beginTime" value=""  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>结束时间：</th>
				<td class="tdBox">
					<input name="endTime" type="text" id="endTime" value=""  maxlength="11" style="width:120px;"  onfocus="WdatePicker({skin:'default',dateFmt:'yyyy-MM-dd 23:59:59',minDate:'%y-%M-%d 00:00:00'})"  />
				</td>
			</tr>
	        <tr>
				<th class="thList" scope="row">标签：</th>
				<td class="tdBox">
					<#include "/common/label.ftl">  
				</td>
			</tr>
			<tr>
				<th class="thList" scope="row"><font class="red">*</font>内容：</th>
				<td class="tdBox">
				<div>
					<div id="contentDiv" style="  width: 750px;height: 600px;border: 1px solid #ddd; float:left; overflow: scroll;">
					    
					</div>
					<div style="vertical-align:left; width: 100px;height: 600px;float:left;">
						<input type="button" onclick="addContentText()" class="userOperBtn" value="添加文字"></input> </br>
						<input type="button" onclick="addContentImgPanel()" class="userOperBtn" value="添加图片"></input> </br>
						<input type="button" onclick="addContentGoodsPanel()" class="userOperBtn" value="添加商品"></input> </br>
					</div>
				</div>
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
<input type="hidden" value="${status!''}" id="updateStatus"/>
<input type="hidden" value="${msg!''}" id="msg"/>
<div id="uploadImgDiv" class="showbox"> 
        <form id="addItemImgSubjectForm" name="addItemImgSubjectForm" enctype="multipart/form-data" method="post" action="${rc.getContextPath()}/subject/saveImg">
			<div>请选择图片: <input style="width:400px"  type="file" name="item_pic" id="item_pic"/> </div>
			<div id="uplaodMsg"></div>
			<div class="btnsdiv">
			<input value="确定" class="userOperBtn panelBtn" id="imgBut"  type="button" onclick="addContentImgAjax()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
			</div>
		</form>
</div>
<div id="findGoodsDiv" class="showbox"> 
			<div>商品走秀码: <input type="text" name="item_goodsSn" id="item_goodsSn" onblur="addContentGoodsAjax()"/> </div>
			<div>商品图片:<span id="item_goods_img_span"><img id="item_goods_img" width="100px"/></span> </div>
			<div>商品名称: <span id="item_goods_title_span"></span></div>
			<div class="red" id="item_goods_error_msg"></div>
			<div class="btnsdiv">
			<input value="确定" class="userOperBtn panelBtn" id="goodsImgBut" type="button" onclick="addContentGoods()"/> <input value="取消" class="userOperBtn panelBtn" type="button"  onclick="showPanelClose()"/> 
			</div>
</div>
</body>
<script type="text/javascript">
var parentDiv="";
$(function(){
	 var status=$("#updateStatus").val();
	   var msg=$("#msg").val();
	 if(status==1){
	   alert("添加成功");
	   location.href="${rc.getContextPath()}/subject/list";
	 }else if(msg!=""){
	   alert(msg);
	 }
});
function addContentText(){
	  var textDiv= $('<div></div>'); 
	  textDiv.attr("name","content_item");
	  textDiv.addClass("content_item");
	  textDiv.html('<input type="hidden" name="subjectItemType" value="1" />'
	  +'<input type="hidden" name="subjectItem" class="subjectItem" value="" />'
	  +'<textarea class="content_text" style="overflow-y: hidden;height:30px;width:470px" onpropertychange="this.style.height = this.scrollHeight+ \'px\';" oninput="this.style.height = this.scrollHeight + \'px\';">输入文字</textarea>'
	  +'<select  name="urlType" id="status" style="width:60px;margin-left:8px;margin-right:8px;float: left;">'
	+'<option value="URL">URL</option><option value="卖场">卖场</option><option value="卖场集">卖场集</option>'
	+'<option value="商品分类">商品分类</option><option value="品牌">品牌</option><option value="话题">话题</option>'
	+'<option value="秀">秀</option><option value="商品">商品</option><option value="专题">专题</option></select><input name="subjectUrl" value=" " style="height:20px;float: left;"/>');
	  createDelBut(textDiv);
	  textDiv.appendTo($("#contentDiv"));   
	  updateButDisplay(); 
}
function addBoxText(obj){
	var textDiv= $('<div></div>');
	textDiv.attr("name","content_item");
	textDiv.addClass("content_item");
	textDiv.html('<input type="hidden" name="subjectItemType" value="1" />'
	  +'<input type="hidden" name="subjectItem" class="subjectItem" value="" />'
	  +'<textarea class="content_text" style="overflow-y: hidden;height:30px;width:470px" onpropertychange="this.style.height = this.scrollHeight+ \'px\';" oninput="this.style.height = this.scrollHeight + \'px\';">输入文字</textarea>'
	 +' <select name="urlType" id="status" style="width:60px;margin-left:8px;margin-right:8px;float: left;">'
	+'<option value="URL">URL</option><option value="卖场">卖场</option><option value="卖场集">卖场集</option>'
	+'<option value="商品分类">商品分类</option><option value="品牌">品牌</option><option value="话题">话题</option>'
	+'<option value="秀">秀</option><option value="商品">商品</option><option value="专题">专题</option></select><input name="subjectUrl" value="" style="height:20px;float: left;"/>');
	createDelBut(textDiv);
	var parentDivs=$(obj).parents(".content_item");
	var parentDivSa=$(parentDivs[0]);
	textDiv.insertAfter(parentDivSa);
	parentDiv="";
	updateButDisplay();
}
function addBoxImg(obj){
    //修改绑定
	var arg = {
		id:"uploadImgDiv",
		title:"插入内容图片",
		height:200,
		width:500
	}
	$("#imgBut").removeAttr("disabled"); 
	var parentDivs=$(obj).parents(".content_item");
	parentDiv=$(parentDivs[0]);
	showPanel(arg);
}
function addContentImgPanel(){
    //修改绑定
	var arg = {
		id:"uploadImgDiv",
		title:"插入内容图片",
		height:200,
		width:500
	}
	$("#imgBut").removeAttr("disabled"); 
	showPanel(arg);
}
function addSubject(obj){
	var parentDivs=$(obj).parents(".content_item_but");
	var parentDiv=$(parentDivs[0]);
	parentDiv.find(".insertBtn").css("display","none");
	parentDiv.find(".addBtn").css("display","block");
	
}
function addContentImgAjax(){
  var item_pic=$("#item_pic").val();
  if(item_pic.length==""){
    alert("请选择图片");
    return;
  }
  $("#imgBut").attr('disabled',"true");
	    $("#addItemImgSubjectForm").ajaxSubmit({
                    type:'post',
                    url:'${rc.getContextPath()}/subject/saveImg',
                    dataType: "json",
                    success:function(data){
                              var imgsrc=data.fileName;
                              var realWidth;//真实的宽度
                              var realHeight;//真实的高度
                              	$("<img/>").attr("src", imgsrc).load(function() {
										realWidth = this.width;
										realHeight = this.height;
		                              imgsrc=imgsrc.substring(imgsrc.indexOf("com/")+4);
		                              imgsrc=imgsrc+"?param1="+realWidth+"&param2="+realHeight;
		                        	  var textDiv= $('<div> <input type="hidden" name="subjectItemType" value="2"><input type="hidden" name="subjectItem" value="'+imgsrc+'"></div>'); 
									  textDiv.attr("name","content_item");
									  textDiv.addClass("content_item");
									  var img= $('<img style="width:300px;height:400px;"></img>'); 
									  img.addClass("content_img");
									  img.attr("src",data.fileName);
									  img.appendTo(textDiv);
									  var sel=$('<select  name="urlType" id="status" style="width:155px;margin-left:8px;margin-right:8px;float: left;">'
											+'<option value="URL">URL</option><option value="卖场">卖场</option><option value="卖场集">卖场集</option>'
											+'<option value="商品分类">商品分类</option><option value="品牌">品牌</option><option value="话题">话题</option>'
											+'<option value="秀">秀</option><option value="商品">商品</option><option value="专题">专题</option></select><input name="subjectUrl" value="" style="height:20px;float: left;"/>');
								     sel.insertAfter(img);
									  createDelBut(textDiv);
									  if(parentDiv==""){
									  	textDiv.appendTo($("#contentDiv")); 
									  }else{
									  	textDiv.insertAfter(parentDiv);
									  	parentDiv="";
									  }
									  updateButDisplay(); 
									  showPanelClose();
									  $("#imgBut").removeAttr("disabled"); 
							   });
                    },
                    error:function(XmlHttpRequest,textStatus,errorThrown){
                        $("#imgBut").removeAttr("disabled"); 
                    }
                });
}


function addContentGoodsPanel(){
    //修改绑定
	var arg = {
		id:"findGoodsDiv",
		title:"插入商品",
		height:300,
		width:600
	}
	 $("#item_goods_error_msg").html("");
	 $("#item_goods_title_span").html("");
	 $("#item_goods_img").attr("src","");
	 $("#goodsImgBut").removeAttr("disabled"); 
	showPanel(arg);
}
var parentsDiv="";
function addBoxGood(obj){
    //修改绑定
	var arg = {
		id:"findGoodsDiv",
		title:"插入商品",
		height:300,
		width:600
	}
	 $("#item_goods_error_msg").html("");
	 $("#item_goods_title_span").html("");
	 $("#item_goods_img").attr("src","");
	$("#goodsImgBut").removeAttr("disabled"); 
	 var parentDivs=$(obj).parents(".content_item");
	 parentsDiv=$(parentDivs[0]);
	showPanel(arg);
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
	var goodsIsExists=addContentGoodsAjax();
	if(goodsIsExists){
	          var goodSn=$("#item_goodsSn").val();
	          var imgsrc=$("#item_goods_img").attr("src");
	    	  var textDiv= $('<div> <input type="hidden" name="subjectItemType" value="3"><input type="hidden" name="subjectItem" value="'+goodSn+'"></div>'); 
			  textDiv.attr("name","content_item");
			  textDiv.addClass("content_item");
			  var img= $('<img style="width:300px;height:400px;"></img>'); 
			  img.addClass("content_img");
			  img.attr("src",imgsrc);
			  img.appendTo(textDiv);
			  var sel=$('<select  name="urlType" id="status" style="width:155px;margin-left:8px;margin-right:8px;float: left;">'
						+'<option value="URL">URL</option><option value="卖场">卖场</option><option value="卖场集">卖场集</option>'
						+'<option value="商品分类">商品分类</option><option value="品牌">品牌</option><option value="话题">话题</option>'
						+'<option value="秀">秀</option><option value="商品">商品</option><option value="专题">专题</option></select><input name="subjectUrl" value="" style="height:20px;float: left;"/>');
			  sel.insertAfter(img);
			  createDelBut(textDiv);
			  if(parentsDiv==""){
			  	textDiv.appendTo($("#contentDiv"));
			  }else{
			  	textDiv.insertAfter(parentsDiv);
				parentsDiv="";
			  }
			  updateButDisplay(); 
			  showPanelClose();
	}
}
function createDelBut(divobj){
	  var buttons= $('<div class="content_item_but"><input type="button" class="upBtn itemBtn userOperBtn" onclick="upSubjectItem(this)" value="上移"/><input type="button" onclick="downSubjectItem(this)" class="downBtn itemBtn userOperBtn" value="下移"/> <input type="button" onclick="delSubjectItem(this)" class=" userOperBtn" value="删除"></input><input type="button" class="userOperBtn insertBtn" onclick="addSubject(this)" value="插入"/>'
	  +'<input type="button" class="addBtn userOperBtn" onclick="addBoxText(this)" value="新增文字"/><input class="addBtn userOperBtn" value="新增图片" type="button" onclick="addBoxImg(this)"/><input value="新增商品" class="addBtn userOperBtn " type="button" onclick="addBoxGood(this)"/></div>'); 
	  buttons.appendTo(divobj);  
}
function upSubjectItem(obj){
  var parentDivs=$(obj).parents(".content_item");
  var parentDiv=$(parentDivs[0]);
  var prevDiv=$(parentDiv).prev();
   $(prevDiv).before(parentDiv); 
   updateButDisplay(); 
}

function downSubjectItem(obj){
  var parentDivs=$(obj).parents(".content_item");
  var parentDiv=$(parentDivs[0]);
  var nextDiv=$(parentDiv).next();
   $(nextDiv).after(parentDiv); 
  updateButDisplay(); 
}

function delSubjectItem(obj){
  var parentDivs=$(obj).parents(".content_item");
  var parentDiv=$(parentDivs[0]);
  $(parentDiv).remove();
    updateButDisplay(); 
}


function updateButDisplay(){
   var content_item_buts=$(".content_item_but");
   var itemNum=content_item_buts.length;
 	  $(".content_item_but").find(".itemBtn").css("display","");
 	  $(content_item_buts[0]).find(".upBtn").css("display","none");
 	  $(content_item_buts[itemNum-1]).find(".downBtn").css("display","none");
  }

	function add(){
	    var name =$("#name").val();
	    var title=$("#title").val();
	    var displayStytle = $('input[name="displayStytle"]:checked ').val();
	    var out_pic=$("#out_pic").val();
	    var beginTime=$("#beginTime").val();
	    var endTime=$("#endTime").val();
	    var pic=$("#adv_pic_f").val();
	    if(name==""||beginTime==""||endTime==""||out_pic==""){
	      alert("请输入完整数据");
	      return ;
	    }
	    if(name.length>50){
	   		alert("名称不能大于50个字符");
	   		return false;
	    }
	    if(title.length>50){
	   		alert("标题不能大于50个字符");
	   		return false;
	    }
	    
	     if(!compareDate(beginTime,endTime)){
	       alert("开始时间不能大于结束时间");
	      return ;
	    }
	   
	   var items=$(".content_item");
	   if(items.length==0){
	   	  alert("请输入内容");
	      return ;
	   }
       //内容字段处理
       var texts=$(".content_text");
       for(var i=0;i<texts.length;i++){
          var textval=$(texts[i]).val();
          if(textval.length>3000){
             alert("内容的第"+(i+1)+"个文字项文字过长,文字项输入不能超过3000字，建议拆分成多个文字项");
	         return ;
          }
          $(texts[i]).parents('.content_item').find(".subjectItem").val(textval);
       }
	   //标签处理
	   	var labelnames=$(".show_li");
	    var label_i=labelnames.length;
	    var skuArr=new Array();
    	for(var i=0;i<label_i;i++){
    		var _target=labelnames.eq(i);
    		var labelId=_target.find("#labelName").val();
    		if(labelId!=''){
    			skuArr[i]=labelId;
    		}
    	}
    	$(".subject_label").val(skuArr);
	  $("#addSubjectForm").submit();
	}
	
	
	
function getTimesByOrderSeq(){
   var orderSeq=$("#orderSeq").val();
   var displayStytle = $('input[name="displayStytle"]:checked ').val();
   $("#timemsg").html("");
	    $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/subject/getTimesByOrderSeq?orderSeq=" + orderSeq +"&displayStytle="+displayStytle+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
				          data=data.replace(/"/gm,'');
				         var displayStytleName="";
				            if(displayStytle==1){
				               displayStytleName="竖专题";
				            }else  if(displayStytle==2){
				               displayStytleName="横专题";
				            }
				            if(data!=""){
					              data=displayStytleName+"类型下该排序值已经分配的日期:<br/>"+data;
				            	$("#timemsg").html(data);
					       }else{
					            $("#timemsg").html("在"+displayStytleName+"下该排序值还没生成专题");
					       }
				},
				error : function(data) {
				}
			}); 
	return isSuccess;
}
	
	
	
function checkTimesByOrderSeq(){
   var orderSeq=$("#orderSeq").val();
   var displayStytle = $('input[name="displayStytle"]:checked ').val();
   var beginTime=$("#beginTime").val();
   var endTime=$("#endTime").val();
   var isSuccess=false;
	    $.ajax({
				type : "GET",
				async: false,
				url : "${rc.getContextPath()}/subject/checkTimesByOrderSeq?orderSeq=" + orderSeq +"&displayStytle="+displayStytle+"&beginTime="+beginTime+"&endTime="+endTime+"&format=json",
	            dataType: "text",
				success : function(data, textStatus) {
                            if(data!=0){
                               alert("该专题时间安排与其他类型和排序值相同的专题有时间重叠冲突,请检查");
                            }else{
                              isSuccess=true;
                            }
				},
				error : function(data) {
				}
			}); 
	return isSuccess;
}
	
	
</script>
</html>